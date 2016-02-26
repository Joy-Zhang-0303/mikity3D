/*
 * Created on 2015/02/05
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * モデルデータとソースデータを選択するためのフラグメントです。
 * 
 * @author hirae
 * @version $Revision$, 2015/02/15
 */
public class FileSelectionFragment extends Fragment {
  MainActivity mainActivity;
  
  CanvasFragment canvasFragment;

  /** モデルファイル名。 */
  String modelFileName = "..."; //$NON-NLS-1$
  /** モデルファイルのパス。 */
  TextView modelFileNameView;
  
  /** サンプルモデルファイル名。 */
  String sampleModelFileName = "..."; //$NON-NLS-1$
  /** サンプルのモデルファイルのパス。 */
  TextView sampleModelFileNameView;

  /** 3Dモデルが選ばれて表示されたならばtrue。 */
  boolean isSelectedModelFile;

  /** ソースファイルのパス。 */
  Map<String, TextView> sourceFileNameViews = new HashMap<String, TextView>();
  /** ソースファイル名。 */
  Map<String, String> sourceFileNames = new HashMap<String,String>();
  /** ソースファイルを選択するためのボタン。 */
  List<Button> sourceSelectButtons = new ArrayList<Button>();
  /** ソースファイルを再読み込みするためのボタン。 */
  List<Button> sourceReloadButtons = new ArrayList<Button>();

  /** サンプルのソースファイルのパス。 */
  Map<String, TextView> sampleSourceFileNameViews = new HashMap<String, TextView>();
  /** サンプルソースファイル名。 */
  Map<String, String> sampleSourceFileNames = new HashMap<String, String>();
  
  /** サンプルのソースファイルを選択するためのボタン */
  List<Button> sampleSourceSelectButtons = new ArrayList<Button>();

  AssetsListViewFragment sampleModelViewFragment;

  AssetsListViewFragment sampleSourceViewFragment;

  SceneGraphTree sceneGraphTreeFragment;

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    
    this.mainActivity = (MainActivity)getActivity();
    this.canvasFragment = this.mainActivity.canvasFragment;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View mainView = inflater.inflate(R.layout.fragment_file_selection, container, false);
    
    final Button backButton = (Button)mainView.findViewById(R.id.settingsBackButton);
    backButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
      }
    });

    createModelComponent(mainView);
    if (this.modelFileName.equals("...") == false) { //$NON-NLS-1$
      createSourceComponent(mainView);
    }
    
    createSampleModelComponent(mainView);
    if (this.sampleModelFileName.equals("...") == false) { //$NON-NLS-1$
      createSampleSourceComponent(mainView);
    }

    return mainView;
  }

  private void createModelComponent(View view) {
    final Button modelButton = (Button)view.findViewById(R.id.modelSelectButton);
    modelButton.setOnClickListener(new View.OnClickListener() {

      final int REQUEST_CODE = MainActivity.REQUEST_CODE_PICK_MODEL_DATA_FILE;

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        FileSelectionFragment.this.mainActivity.sendFileChooseIntentForModel(this.REQUEST_CODE);
      }
    });

    this.modelFileNameView = (TextView)view.findViewById(R.id.modelFileNameView);
    this.modelFileNameView.setText(this.modelFileName);
    this.modelFileNameView.setMovementMethod(ScrollingMovementMethod.getInstance());
  }

  private void createSourceComponent(View view) {
    final List<GroupModel> rootGroups = this.canvasFragment.root.getScene(0).getGroups();
    final Set<String> sourceIds = getAllSourceIds(rootGroups);

    final LinearLayout sources =  (LinearLayout)view.findViewById(R.id.layout_sources);
    sources.removeAllViews();
    this.sourceSelectButtons.clear();
    this.sourceReloadButtons.clear();
    this.sourceFileNameViews.clear();

    for (final String id : sourceIds) {
      final LinearLayout source = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.source, null);
      sources.addView(source);

      final Button selectButton = (Button)source.findViewById(R.id.sourceSelectButton);
      selectButton.setText(getString(R.string.source) + "(" + id + ")"); //$NON-NLS-1$ //$NON-NLS-2$
      this.sourceSelectButtons.add(selectButton);

      selectButton.setOnClickListener(new View.OnClickListener() {

        final int REQUEST_CODE = MainActivity.REQUEST_CODE_PICK_SOURCE_DATA_FILE;

        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          FileSelectionFragment.this.mainActivity.sendFileChooseIntentForSource(this.REQUEST_CODE, id);
        }
      });

      final TextView fileNameView = (TextView)source.findViewById(R.id.sourceFileNameView);
      if (this.sourceFileNames.containsKey(id)) {
        fileNameView.setText(this.sourceFileNames.get(id));
      }
      this.sourceFileNameViews.put(id, fileNameView);

      final Button reloadButton = (Button)source.findViewById(R.id.sourceReloadButton);
      this.sourceReloadButtons.add(reloadButton);

      reloadButton.setOnClickListener(new View.OnClickListener() {

        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          if (FileSelectionFragment.this.canvasFragment.sourceData.containsKey(id)) {
            FileSelectionFragment.this.canvasFragment.addSource(id);
          }
        }
      });
    }

  }

  private void createSampleModelComponent(View view) {
    final Button modelButton = (Button)view.findViewById(R.id.sampleModelSelectButton);
    modelButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);

        if (FileSelectionFragment.this.sampleModelViewFragment != null) {
          transaction.remove(FileSelectionFragment.this.sampleModelViewFragment);
          FileSelectionFragment.this.sampleModelViewFragment = null;
        }
        
        FileSelectionFragment.this.sampleModelViewFragment = new AssetsListViewFragment();
        final Bundle arguments = new Bundle();
        arguments.putBoolean("isModelData", true); //$NON-NLS-1$
        FileSelectionFragment.this.sampleModelViewFragment.setArguments(arguments);

        transaction.add(R.id.fragment_navigation_drawer, FileSelectionFragment.this.sampleModelViewFragment);
        transaction.commit();
      }
    });

    this.sampleModelFileNameView = (TextView)view.findViewById(R.id.sampleModelFileNameView);
    this.sampleModelFileNameView.setText(this.sampleModelFileName);
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
  private Set<String> getAllSourceIds(final List<GroupModel> ｇroups) {
    final List<AnimationModel> allAnimations = getAllAnimation(ｇroups);

    final Set<String> ids = new TreeSet<String>();
    for (final AnimationModel animation : allAnimations) {
      ids.add(animation.getSource().getId());
    }
    return ids;
  }
  
  /**
   * サンプルのソースを読み込むボタンを更新します。
   */
  public void updateSampleSourceComponent() {
    createSampleSourceComponent(getView());
  }

  /**
   * サンプルのソースを読み込むボタンを生成します。
   */
  void createSampleSourceComponent(View view) {
    final List<GroupModel> rootGroups = this.canvasFragment.root.getScene(0).getGroups();
    final Set<String> sourceIds = getAllSourceIds(rootGroups);

    final LinearLayout sources = (LinearLayout)view.findViewById(R.id.layout_sample_sources);
    sources.removeAllViews();

    this.sampleSourceSelectButtons.clear();
    this.sampleSourceFileNameViews.clear();

    for (final String id : sourceIds) {
      final LinearLayout source = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.sample_source, null);
      sources.addView(source);

      final Button selectButton = (Button)source.findViewById(R.id.sampleSourceSelectButton);
      selectButton.setText(getString(R.string.source) + "(" + id + ")"); //$NON-NLS-1$ //$NON-NLS-2$
      this.sampleSourceSelectButtons.add(selectButton);

      selectButton.setOnClickListener(new OnClickListener() {

        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          final FragmentManager manager = getActivity().getSupportFragmentManager();
          final FragmentTransaction transaction = manager.beginTransaction();
          transaction.addToBackStack(null);

          if (FileSelectionFragment.this.sampleSourceViewFragment != null) {
            transaction.remove(FileSelectionFragment.this.sampleSourceViewFragment);
            FileSelectionFragment.this.sampleSourceViewFragment = null;
          }
          
          FileSelectionFragment.this.sampleSourceViewFragment = new AssetsListViewFragment();
          final Bundle arguments = new Bundle();
          arguments.putString("sourceId", id); //$NON-NLS-1$
          arguments.putBoolean("isModelData", false); //$NON-NLS-1$
          FileSelectionFragment.this.sampleSourceViewFragment.setArguments(arguments);

          transaction.add(R.id.fragment_navigation_drawer, FileSelectionFragment.this.sampleSourceViewFragment);
          transaction.commit();
        }
      });

      final TextView sourceFileNameView = (TextView)source.findViewById(R.id.sampleSourceFileNameView);
      if (this.sampleSourceFileNames.containsKey(id)) {
        sourceFileNameView.setText(this.sampleSourceFileNames.get(id));
      }
      this.sampleSourceFileNameViews.put(id, sourceFileNameView);

    }
  }

  /**
   * GroupManagerを取得します。
   * 
   * @return result GroupManager
   */
  GroupManager getGroupManager() {
    final Mikity3DModel root = this.canvasFragment.root;
    final SceneModel model = root.getScene(0);
    final List<GroupModel> groupArray = model.getGroups();
    final GroupModel group = groupArray.get(0);
    final ExcecuteSearchGroup search = new ExcecuteSearchGroup();
    final GroupNameManager manager = new GroupNameManager(group.getName(), null);
    final GroupManager result = search.searchGroupRecursion(group, manager);
    return result;
  }

  /**
   * ストリームからサンプルソースデータを取り出します。
   * 
   * @param input ソースの入力ストリーム
   * @param filePath ソースのファイルパス
   * @param sourceId ソースID
   */
  public void loadSampleSourceData(final InputStream input, final String filePath, String sourceId) {
    this.canvasFragment.loadSourceData(input, filePath, sourceId);

    final String[] parts = filePath.split("/"); //$NON-NLS-1$
    final String sourceFileName = parts[parts.length - 1];
    this.sampleSourceFileNameViews.get(sourceId).setText(sourceFileName);
    this.sampleSourceFileNames.put(sourceId, sourceFileName);
  }

  /**
   * 時間データをURIから読み込みます。
   * 
   * @param uri 時間データURI
   * @param sourceId ソースID
   */
  void loadSourceData(Uri uri, String sourceId) {
    if (uri == null) {
      return;
    }

    final String sourceFileName;
    final InputStream sourceStream;

    if ("content".equals(uri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {
        sourceStream = this.mainActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final Cursor cursor2 = this.mainActivity.getContentResolver().query(uri, null, null, null, null);
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

    this.sourceFileNameViews.get(sourceId).setText(sourceFileName);
    this.sourceFileNames.put(sourceId, sourceFileName);

    this.canvasFragment.loadSourceData(sourceStream, uri.getPath(), sourceId);
    // sourceStream has been already closed in the loadSourceData method. 
  }

  void loadSampleModelData(InputStream modelStream, String modelFilePath) throws Mikity3dSerializeDeserializeException {
    this.canvasFragment.loadModelData(modelStream);

    final String[] parts = modelFilePath.split("/"); //$NON-NLS-1$
    this.sampleModelFileName = parts[parts.length - 1];
    this.sampleModelFileNameView.setText(this.sampleModelFileName);

    final String sampleSourceFileName = "..."; //$NON-NLS-1$
    for (final TextView view : this.sampleSourceFileNameViews.values()) {
      view.setText(sampleSourceFileName);
    }
    
    this.sampleSourceFileNames.clear();
    
    if (this.canvasFragment.sourceData.size() != 0) {
      this.canvasFragment.sourceData.clear();
    }
    this.isSelectedModelFile = true;
    updateSampleSourceComponent();
    setButtonEnabled(true);
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

    final InputStream modelStream;

    if ("content".equals(uri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {
        modelStream = this.mainActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final Cursor cursor = this.mainActivity.getContentResolver().query(uri, null, null, null, null);
      cursor.moveToFirst();
      this.modelFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
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
      this.modelFileName = parts[parts.length - 1];
    }

    this.modelFileNameView.setText(this.modelFileName);
    final String sourceFileName = "..."; //$NON-NLS-1$
    for (final TextView view : this.sourceFileNameViews.values()) {
      view.setText(sourceFileName);
    }
    this.sourceFileNames.clear();

    try {
      this.canvasFragment.loadModelData(modelStream);
      modelStream.close();

      if (this.canvasFragment.sourceData.size() != 0) {
        this.canvasFragment.sourceData.clear();
      }

      createSourceComponent(getView());
      
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
    final Bundle bundle = new Bundle();
    bundle.putString("message", message); //$NON-NLS-1$
    dialog.setArguments(bundle);
    dialog.show(this.mainActivity.getSupportFragmentManager(), "alertDialogFragment"); //$NON-NLS-1$
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
    final SceneModel scene = this.canvasFragment.root.getScene(0);
    final List<GroupModel> topGroups = scene.getGroups();
    GroupModel group = topGroups.get(0);

    for (final Integer targetNumber : targetNumbers) {
      group = group.getGroups().get(targetNumber.intValue());
    }

    group.getAnimations()[childPosition].getSource().setNumber(number);
    this.canvasFragment.prepareObjectGroupManager();
    this.canvasFragment.prepareRenderer();
  }
}
