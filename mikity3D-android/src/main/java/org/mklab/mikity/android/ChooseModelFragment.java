/*
 * Created on 2015/02/05
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.android.model.SceneGraphTree;
import org.mklab.mikity.model.searcher.ExcecuteSearchGroup;
import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.searcher.GroupNameManager;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.SceneModel;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * NavigationDrawerでモデルを選択するためのフラグメントです。
 * 
 * @author hirae
 * @version $Revision$, 2015/02/15
 */
public class ChooseModelFragment extends Fragment {

  static final String LOGTAG = null;

  /**
   * 新しく生成された<code>NavigationDrawerFragment</code>オブジェクトを初期化します。
   */
  public ChooseModelFragment() {
    // nothing to do
  }

  CanvasActivity canvasActivity;

  /** モデルファイルのパス。 */
  TextView modelFileNameView;

  /** サンプルのモデルファイルのパス。 */
  TextView sampleModelFileNameView;

  /** ソースID。 */
  String sourceId = null;

  /** サンプルソースID。 */
  String sampleSourceId = null;

//  /** アニメーション用タスク。 */
//  AnimationTask animationTask;
//
//  /** アニメーションの再生速度倍率 */
//  int animationSpeedRate = 1000;
//
//  /** アニメーションスピード。 */
//  EditText animationSpeedTextEdit;

  /** 3Dモデルが選ばれて表示されたならばtrue。 */
  boolean isSelectedModelFile;

//  /** アニメーションスピードを早くするためのボタン。 */
//  ImageButton quickButton;
//  /** アニメーションスピードを遅くするためのボタン。 */
//  ImageButton slowButton;

  /** ソースファイルを選択するためのボタン。 */
  List<Button> sourceSelectButtons = new ArrayList<Button>();
  /** ソースファイルを再読み込みするためのボタン。 */
  List<Button> sourceReloadButtons = new ArrayList<Button>();
  /** ソースファイルのパス。 */
  Map<String, TextView> sourceFileNameViews = new HashMap<String, TextView>();

  /** ソース番号を変更するためのボタン。 */
  //Button editModelButton;

//  /** 端末の角度を3Dオブジェクトに反映させるならばtrue。 */
//  CompoundButton rotationSensorButton;
//  /** 加速度を3Dオブジェクトに反映させるならばtrue。 */
//  CompoundButton accelerometerButton;
//  /** 端末の回転を許可するならばtrue。 */
//  CompoundButton rotationLockButton;
//  /** グリッドを表示するならばtrue。 */
//  CompoundButton gridShowingButton;
//  /** 座標軸を表示するならばtrue。 */
//  CompoundButton axisShowingButton;

  /** サンプルのソースファイルのパス。 */
  Map<String, TextView> sampleSourceFileNameViews = new HashMap<String, TextView>();
  /** サンプルのソースファイルを選択するためのボタン */
  List<Button> sampleSourceSelectButtons = new ArrayList<Button>();

  AssetsListViewFragment sampleModelViewFragment;

  AssetsListViewFragment sampleSourceViewFragment;

  SceneGraphTree sceneGraphTreeFragment;

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_choose_model, container, false);

    final Button backButton = (Button)view.findViewById(R.id.settingsBackButton);
    backButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
      }
    });

    this.canvasActivity = (CanvasActivity)getActivity();

    createModelComponent(view);
    
    createSampleModelComponent(view);

    return view;
  }

  private void createModelComponent(final View mainView) {
    final Button modelButton = (Button)mainView.findViewById(R.id.modelSelectButton);
    modelButton.setOnClickListener(new View.OnClickListener() {

      final int REQUEST_CODE = CanvasActivity.REQUEST_CODE_PICK_MODEL_DATA_FILE;

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        ChooseModelFragment.this.canvasActivity.sendFileChooseIntent(this.REQUEST_CODE);
      }
    });

    this.modelFileNameView = (TextView)mainView.findViewById(R.id.modelFileNameView);
    this.modelFileNameView.setMovementMethod(ScrollingMovementMethod.getInstance());
  }

  private void createSourceComponent() {
    final List<GroupModel> rootGroups = this.canvasActivity.canvasFragment.root.getScene(0).getGroups();
    final Set<String> ids = getAllIds(rootGroups);

    final LinearLayout sources = ((LinearLayout)getActivity().findViewById(R.id.layout_sources));
    sources.removeAllViews();
    this.sourceSelectButtons.clear();
    this.sourceReloadButtons.clear();
    this.sourceFileNameViews.clear();

    for (final String id : ids) {
      final LinearLayout source = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.source, null);
      sources.addView(source);

      final Button selectButton = (Button)source.findViewById(R.id.sourceSelectButton);
      selectButton.setText(getString(R.string.source) + "(" + id + ")"); //$NON-NLS-1$ //$NON-NLS-2$
      this.sourceSelectButtons.add(selectButton);

      selectButton.setEnabled(false);
      selectButton.setOnClickListener(new View.OnClickListener() {

        final int REQUEST_CODE = CanvasActivity.REQUEST_CODE_PICK_SOURCE_DATA_FILE;

        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          ChooseModelFragment.this.sourceId = id;
          ChooseModelFragment.this.canvasActivity.sendFileChooseIntent(this.REQUEST_CODE);
        }
      });

      final TextView fileNameView = (TextView)source.findViewById(R.id.sourceFileNameView);
      this.sourceFileNameViews.put(id, fileNameView);

      final Button reloadButton = (Button)source.findViewById(R.id.sourceReloadButton);
      this.sourceReloadButtons.add(reloadButton);

      reloadButton.setEnabled(false);
      reloadButton.setOnClickListener(new View.OnClickListener() {

        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          if (ChooseModelFragment.this.canvasActivity.canvasFragment.sourceData.containsKey(id)) {
            ChooseModelFragment.this.canvasActivity.canvasFragment.addSource(id);
          }
        }
      });
    }

  }

  private void createSampleModelComponent(final View mainView) {
    final Button modelButton = (Button)mainView.findViewById(R.id.sampleModelSelectButton);
    modelButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        ChooseModelFragment.this.sampleSourceId = null;

        final FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);

        if (ChooseModelFragment.this.sampleModelViewFragment == null) {
          ChooseModelFragment.this.sampleModelViewFragment = new AssetsListViewFragment();
        }

        ChooseModelFragment.this.sampleModelViewFragment.setActivity(ChooseModelFragment.this.canvasActivity);
        //ChooseModelFragment.this.sampleModelViewFragment.setFragmentManager(manager);
        ChooseModelFragment.this.sampleModelViewFragment.setIsModelData(true);
        transaction.add(R.id.fragment_navigation_drawer, ChooseModelFragment.this.sampleModelViewFragment);
        transaction.commit();
      }
    });

    this.sampleModelFileNameView = (TextView)mainView.findViewById(R.id.sampleModelFileNameView);
    this.sampleModelFileNameView.setMovementMethod(ScrollingMovementMethod.getInstance());
  }

  /**
   * 全ての含まれるアニメーソンを返します。
   * 
   * @param groups グループ
   * 
   * @return 全ての含まれるアニメーソン
   */
  private List<AnimationModel> getAllAnimation(List<GroupModel> groups) {
    final List<AnimationModel> allAnimations = new ArrayList<AnimationModel>();

    for (final GroupModel group : groups) {
      final AnimationModel[] animations = group.getAnimations();
      for (final AnimationModel animation : animations) {
        if (animation.exists()) {
          allAnimations.add(animation);
        }
      }

      allAnimations.addAll(getAllAnimation(group.getGroups()));
    }

    return allAnimations;
  }

  /**
   * グループ以下に含まれるアニメーションのソースIDを返します。
   * 
   * @param ｇroups グループ
   * @return グループ以下に含まれるアニメーションのソースIDを返します。
   */
  private Set<String> getAllIds(final List<GroupModel> ｇroups) {
    final List<AnimationModel> allAnimations = getAllAnimation(ｇroups);

    final Set<String> ids = new TreeSet<String>();
    for (final AnimationModel animation : allAnimations) {
      ids.add(animation.getSource().getId());
    }
    return ids;
  }

  /**
   * サンプルのソースを読み込むボタンを生成します。
   */
  void createSampleSourceComponent() {
    final List<GroupModel> rootGroups = this.canvasActivity.canvasFragment.root.getScene(0).getGroups();
    final Set<String> ids = getAllIds(rootGroups);

    final LinearLayout sources = ((LinearLayout)getActivity().findViewById(R.id.layout_sample_sources));
    sources.removeAllViews();

    this.sampleSourceSelectButtons.clear();
    this.sampleSourceFileNameViews.clear();

    for (final String id : ids) {
      final LinearLayout source = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.sample_source, null);
      sources.addView(source);

      final Button selectButton = (Button)source.findViewById(R.id.sampleSourceSelectButton);
      selectButton.setText(getString(R.string.source) + "(" + id + ")"); //$NON-NLS-1$ //$NON-NLS-2$
      this.sampleSourceSelectButtons.add(selectButton);

      selectButton.setEnabled(false);
      selectButton.setOnClickListener(new OnClickListener() {

        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          ChooseModelFragment.this.sampleSourceId = id;

          final FragmentManager manager = getActivity().getSupportFragmentManager();
          final FragmentTransaction transaction = manager.beginTransaction();
          transaction.addToBackStack(null);

          if (ChooseModelFragment.this.sampleSourceViewFragment == null) {
            ChooseModelFragment.this.sampleSourceViewFragment = new AssetsListViewFragment();
          }
          
          ChooseModelFragment.this.sampleSourceViewFragment.setActivity(ChooseModelFragment.this.canvasActivity);
          //ChooseModelFragment.this.sampleSourceViewFragment.setFragmentManager(manager);
          ChooseModelFragment.this.sampleSourceViewFragment.setIsModelData(false);
          transaction.add(R.id.fragment_navigation_drawer, ChooseModelFragment.this.sampleSourceViewFragment);
          transaction.commit();
        }
      });

      final TextView sourceFileNameView = (TextView)source.findViewById(R.id.sampleSourceFileNameView);
      this.sampleSourceFileNameViews.put(id, sourceFileNameView);

    }
  }

  /**
   * GroupManagerを取得します。
   * 
   * @return result GroupManager
   */
  GroupManager getGroupManager() {
    final Mikity3DModel root = this.canvasActivity.canvasFragment.root;
    final SceneModel model = root.getScene(0);
    final List<GroupModel> groupArray = model.getGroups();
    final GroupModel group = groupArray.get(0);
    final ExcecuteSearchGroup search = new ExcecuteSearchGroup();
    final GroupNameManager manager = new GroupNameManager(group.getName(), null);
    final GroupManager result = search.searchGroupRecursion(group, manager);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  /**
   * ストリームからサンプルソースデータを取り出します。
   * 
   * @param input ソースの入力ストリーム
   * @param filePath ソースのファイルパス
   */
  public void loadSampleSourceData(final InputStream input, final String filePath) {
    this.canvasActivity.canvasFragment.loadSourceData(input, filePath, this.sampleSourceId);

    final String[] parts = filePath.split("/"); //$NON-NLS-1$
    final String sourceFileName = parts[parts.length - 1];
    this.sampleSourceFileNameViews.get(this.sampleSourceId).setText(sourceFileName);
  }

  /**
   * 時間データをURIから読み込みます。
   * 
   * @param uri 時間データURI
   */
  void loadSourceData(Uri uri) {
    if (uri == null) {
      return;
    }

    final String sourceFileName;
    final InputStream sourceStream;

    if ("content".equals(uri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {
        sourceStream = this.canvasActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final Cursor cursor2 = this.canvasActivity.getContentResolver().query(uri, null, null, null, null);
      cursor2.moveToFirst();
      sourceFileName = cursor2.getString(cursor2.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      cursor2.close();
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      final String sourceDataFilePath = uri.getPath();
      try {
        sourceStream = new FileInputStream(sourceDataFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final String[] parts = sourceDataFilePath.split("/"); //$NON-NLS-1$
      sourceFileName = parts[parts.length - 1];
    }

    this.sourceFileNameViews.get(this.sourceId).setText(sourceFileName);

    this.canvasActivity.canvasFragment.loadSourceData(sourceStream, uri.getPath(), this.sourceId);
    // sourceStream has been already closed in the loadSourceData method. 
  }

  void loadSampleModelData(InputStream modelStream, String modelFilePath) throws Mikity3dSerializeDeserializeException {
    this.canvasActivity.canvasFragment.loadModelData(modelStream);

    final String[] parts = modelFilePath.split("/"); //$NON-NLS-1$
    final String modelFileName = parts[parts.length - 1];
    this.sampleModelFileNameView.setText(modelFileName);

    final String sampleSourceFileName = "..."; //$NON-NLS-1$
    for (final TextView view : this.sampleSourceFileNameViews.values()) {
      view.setText(sampleSourceFileName);
    }
  }

  /**
   * モデルをURIから読み込みます。
   * 
   * @param uri モデルURI
   */
  void loadModelData(Uri uri) {
    if (uri == null) {
      return;
    }

    final String modelFileName;
    final InputStream modelStream;

    if ("content".equals(uri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {
        modelStream = this.canvasActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final Cursor cursor = this.canvasActivity.getContentResolver().query(uri, null, null, null, null);
      cursor.moveToFirst();
      modelFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      cursor.close();
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      final String modelFilePath = uri.getPath();
      try {
        modelStream = new FileInputStream(modelFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final String[] parts = modelFilePath.split("/"); //$NON-NLS-1$
      modelFileName = parts[parts.length - 1];
    }

    this.modelFileNameView.setText(modelFileName);
    final String sourceFileName = "..."; //$NON-NLS-1$
    for (final TextView view : this.sourceFileNameViews.values()) {
      view.setText(sourceFileName);
    }

    try {
      this.canvasActivity.canvasFragment.loadModelData(modelStream);
      modelStream.close();

      if (this.canvasActivity.canvasFragment.sourceData.size() != 0) {
        this.canvasActivity.canvasFragment.sourceData.clear();
      }

      createSourceComponent();
      
      setButtonEnabled(true);

    } catch (Mikity3dSerializeDeserializeException e) {
      showAlertMessageInDialog("please select model file."); //$NON-NLS-1$
      setButtonEnabled(false);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 警告メッセージを表示します。
   * 
   * @param message メッセージ
   */
  void showAlertMessageInDialog(String message) {
    final AlertDialogFragment dialog = new AlertDialogFragment();
    dialog.setMessage(message);
    dialog.show(this.canvasActivity.getSupportFragmentManager(), "alertDialogFragment"); //$NON-NLS-1$
  }

  /**
   * @param enabled
   */
  void setButtonEnabled(boolean enabled) {
    this.isSelectedModelFile = enabled;

    for (Button button : this.sourceSelectButtons) {
      button.setEnabled(enabled);
    }

    for (Button button : this.sourceReloadButtons) {
      button.setEnabled(enabled);
    }

    for (Button button : this.sampleSourceSelectButtons) {
      button.setEnabled(enabled);
    }
  }

  /**
   * ソース番号を変更します。
   * 
   * @param targetNumbers グループの階層を所持したリスト
   * @param childPosition アニメーションデータがある場所
   * @param number 設定する番号
   */
  void changeSourceNumber(List<Integer> targetNumbers, int childPosition, int number) {
    final SceneModel scene = this.canvasActivity.canvasFragment.root.getScene(0);
    final List<GroupModel> topGroups = scene.getGroups();
    GroupModel group = topGroups.get(0);

    for (final Integer targetNumber : targetNumbers) {
      group = group.getGroups().get(targetNumber.intValue());
    }

    group.getAnimations()[childPosition].getSource().setNumber(number);
    this.canvasActivity.canvasFragment.prepareObjectGroupManager();
    this.canvasActivity.canvasFragment.prepareRenderer();
  }

  /**
   * @param inputURL URL
   */
  void unzipFile(Uri inputURL) {
    if (inputURL == null) {
      return;
    }

    // ストリームを直接URIから取り出します。
    try {
      final InputStream zipFile = this.canvasActivity.getContentResolver().openInputStream(inputURL);
      final ZipInputStream zipInput = new ZipInputStream(new BufferedInputStream(zipFile));

      ZipEntry zipEntry;
      while ((zipEntry = zipInput.getNextEntry()) != null) {
        final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("/sdcard/mikity3d" + zipEntry.getName())); //$NON-NLS-1$
        int data;
        while ((data = zipInput.read()) != -1) {
          out.write(data);
        }
        out.close();
      }
      zipInput.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
