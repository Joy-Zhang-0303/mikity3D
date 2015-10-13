/*
 * Created on 2014/12/10
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
import org.mklab.mikity.model.searcher.ExcecuteSearchGroup;
import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.searcher.GroupNameManager;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.SceneModel;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

import roboguice.fragment.RoboFragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


/**
 * NavigationDrawerでメニューを表示するためのフラグメントです。
 * 
 * @author soda
 * @version $Revision$, 2015/01/16
 */
public class NavigationDrawerFragment extends RoboFragment {
  static final String LOGTAG = null;
  
  CanvasActivity canvasActivity;

  /** モデルのファイルパス。 */
  TextView modelFileNameView;

  /** サンプルモデルのファイルパス。 */
  TextView sampleModelFileNameView;

  /** ソースID。 */
  String sourceId = null;

  /** アニメーション用タスク */
  AnimationTask animationTask;
  
  /** アニメーションの再生速度 丸め誤差を防ぐために10で割る必要があります。 */
  int animationSpeed = 10;

  /** アニメーションスピードを表示するエディットテキスト */
  EditText animationSpeedTextEdit;
  
  /** 3Dモデルが選ばれて表示されたかどうかのフラグ */
  boolean isSelectedModelFile;
   
  /** アニメーションスピードを早くするときに押されるボタン */
  Button quickButton;
  /** アニメーションスピードを遅くするときに押されるボタン */
  Button slowButton;
    
  /** モデルを選択するときに押されるボタン */
  List<Button> sourceSelectButtons = new ArrayList<Button>();
  /** ソースデータ再読み込みのためのボタン */
  List<Button> sourceReloadButtons = new ArrayList<Button>();
  /** ソースデータを削除するためのボタン */
  List<Button> sourceDeleteButtons = new ArrayList<Button>();
  /** ソースのファイルパス。 */
  Map<String,TextView> sourceFileNameViews = new HashMap<String,TextView>();

  /** サンプルソースのファイルパス。 */
  Map<String,TextView> sampleSourceFileNameViews = new HashMap<String,TextView>();

  /** 端末の角度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton rotationSensorButton;
  /** 加速度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton accelerometerButton;
  /** */
  ToggleButton rotationLockButton;  

  /** ソース番号を変更するためのボタン */
  Button sourceNumberChangeButton;

  List<Button> sampleSourceButtons = new ArrayList<Button>();
  
  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View mainView = inflater.inflate(R.layout.navigation_drawer_fragment, container, false);

    this.canvasActivity = (CanvasActivity)getActivity();

    createAnimationSpeedComponent(mainView);

    createModelComponent(mainView);
    
    createSourceNumberChangeComponent(mainView);

    createSensorComponent(mainView);

    createSampleModelComponent(mainView);
       
    //TODO setRetainInstance()を使っても、activityでこのfragmentを保持しているため、処理が被っている。要修正
    this.canvasActivity.ndFragment = this;
    return mainView;
  }
  
  private void createAnimationSpeedComponent(final View mainView) {
    this.slowButton = (Button)mainView.findViewById(R.id.slowButton);
    this.slowButton.setEnabled(false);
    this.slowButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.animationSpeed -= 1;
        if (NavigationDrawerFragment.this.animationSpeed < 0) NavigationDrawerFragment.this.animationSpeed = 0;
        NavigationDrawerFragment.this.animationSpeedTextEdit.setText(Double.toString((double)NavigationDrawerFragment.this.animationSpeed / 10));
        if (NavigationDrawerFragment.this.animationTask != null) NavigationDrawerFragment.this.animationTask.setSpeedScale(NavigationDrawerFragment.this.animationSpeed / 10);
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
      }
    });
    
    this.animationSpeedTextEdit = (EditText)mainView.findViewById(R.id.animationSpeedEditText);
    this.animationSpeedTextEdit.clearFocus();
    this.animationSpeedTextEdit.setText(Double.toString(this.animationSpeed / 10));
    this.animationSpeedTextEdit.clearFocus();

    this.quickButton = (Button)mainView.findViewById(R.id.quickButton);
    this.quickButton.setEnabled(false);
    this.quickButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.animationSpeed += 1;
        NavigationDrawerFragment.this.animationSpeedTextEdit.setText("" + (double)NavigationDrawerFragment.this.animationSpeed / 10); //$NON-NLS-1$
        if (NavigationDrawerFragment.this.animationTask != null) NavigationDrawerFragment.this.animationTask.setSpeedScale(NavigationDrawerFragment.this.animationSpeed / 10);
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
      }
    });
  }

  private void createModelComponent(final View mainView) {
    final Button modelButton = (Button)mainView.findViewById(R.id.modelSelectButton);
    modelButton.setOnClickListener(new View.OnClickListener() {
      final int REQUEST_CODE = CanvasActivity.REQUEST_CODE_PICK_FILE_OR_DIRECTORY;

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        NavigationDrawerFragment.this.canvasActivity.sendFileChooseIntent(this.REQUEST_CODE);
      }
    });
    
    this.modelFileNameView = (TextView)mainView.findViewById(R.id.modelPathView);
    this.modelFileNameView.setMovementMethod(ScrollingMovementMethod.getInstance());
  }

  private void createSourceComponent() {
    final List<GroupModel> rootGroups = this.canvasActivity.canvasFragment.root.getScene(0).getGroups();
    final Set<String> ids = getAllIds(rootGroups);  
    
    final LinearLayout sources = ((LinearLayout)getActivity().findViewById(R.id.layout_sources));
    sources.removeAllViews();
    this.sourceSelectButtons.clear();
    this.sourceDeleteButtons.clear();
    this.sourceReloadButtons.clear();
    this.sourceFileNameViews.clear();
    
    for (final String id : ids) {
      final LinearLayout source = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.source, null);
      sources.addView(source);
    
      final TextView sourcePathLabel = (TextView)source.findViewById(R.id.sourcePathLabel);
      sourcePathLabel.setText("Source(" + id + ")"); //$NON-NLS-1$ //$NON-NLS-2$
      
      final Button sourceSelectButton = (Button)source.findViewById(R.id.sourceSelectButton);
      this.sourceSelectButtons.add(sourceSelectButton);
      
      sourceSelectButton.setEnabled(false);
      sourceSelectButton.setOnClickListener(new View.OnClickListener() {
        final int REQUEST_CODE = CanvasActivity.REQUEST_CODE_PICK_SOURCE_DATA_FILE;
        
        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          NavigationDrawerFragment.this.sourceId = id;
          NavigationDrawerFragment.this.canvasActivity.sendFileChooseIntent(this.REQUEST_CODE);
        }
      });
    
      final TextView sourcePathView = (TextView)source.findViewById(R.id.sourcePathView);
      this.sourceFileNameViews.put(id,sourcePathView);

      final Button sourceDeleteButton = (Button)source.findViewById(R.id.sourceDeleteButton);
      this.sourceDeleteButtons.add(sourceDeleteButton);
      
      sourceDeleteButton.setEnabled(false);
      sourceDeleteButton.setOnClickListener(new View.OnClickListener() {
        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          if (NavigationDrawerFragment.this.canvasActivity.canvasFragment.sourceData.containsKey(id)) { 
            NavigationDrawerFragment.this.canvasActivity.canvasFragment.sourceData.remove(id);
            final String sourceFileName = "..."; //$NON-NLS-1$
            NavigationDrawerFragment.this.sourceFileNameViews.get(id).setText(sourceFileName);
          }
        }
      });
      
      final Button sourceReloadButton = (Button)source.findViewById(R.id.sourceReloadButton);
      this.sourceReloadButtons.add(sourceReloadButton);
      
      sourceReloadButton.setEnabled(false);
      sourceReloadButton.setOnClickListener(new View.OnClickListener() {
        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          if (NavigationDrawerFragment.this.canvasActivity.canvasFragment.sourceData.containsKey(id)) {
            NavigationDrawerFragment.this.canvasActivity.canvasFragment.addSource(id);
          }
        }
      });
    }
    

  }

  private void createSourceNumberChangeComponent(final View mainView) {
    this.sourceNumberChangeButton = (Button)mainView.findViewById(R.id.sourceNumberChangeButton);
    this.sourceNumberChangeButton.setEnabled(false);
    this.sourceNumberChangeButton.setOnClickListener(new OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        final SourceNumberFragment fragment = new SourceNumberFragment();
        if (NavigationDrawerFragment.this.canvasActivity.canvasFragment.root != null) {
          fragment.setGroupManager(getGroupManager());
          fragment.setNavigationDrawerFragment(NavigationDrawerFragment.this);

          transaction.replace(R.id.fragment_navigation_drawer, fragment);
          transaction.addToBackStack(null);
          transaction.commit();
        }
      }
    });
  }

  private void createSensorComponent(final View mainView) {
    this.rotationSensorButton = (ToggleButton)mainView.findViewById(R.id.rotationSensorButton);
    this.rotationSensorButton.setOnClickListener(new OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        if (NavigationDrawerFragment.this.rotationSensorButton.isChecked()) {
          NavigationDrawerFragment.this.canvasActivity.sensorService.useRotationSensor = true;
        } else {
          NavigationDrawerFragment.this.canvasActivity.sensorService.useRotationSensor = false;
        }

      }
    });
    
    this.accelerometerButton = (ToggleButton)mainView.findViewById(R.id.accelerometerButton);
    this.accelerometerButton.setOnClickListener(new OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        if (NavigationDrawerFragment.this.accelerometerButton.isChecked()) {
          NavigationDrawerFragment.this.canvasActivity.sensorService.useAccelerometer = true;
        } else {
          NavigationDrawerFragment.this.canvasActivity.sensorService.useAccelerometer = false;
        }
      }

    });

    this.rotationLockButton = (ToggleButton)mainView.findViewById(R.id.rotationLockButton);
    this.rotationLockButton.setOnClickListener(new OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        NavigationDrawerFragment.this.canvasActivity.controlRotation();
      }
    });
  }

  private void createSampleModelComponent(final View mainView) {
    final Button sampleModelButton = (Button)mainView.findViewById(R.id.sampleModelButton);
    sampleModelButton.setOnClickListener(new OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        final AssetsListViewFragment fragment = new AssetsListViewFragment(null);
        fragment.setActivity(NavigationDrawerFragment.this.canvasActivity);
        fragment.setIsModelData(true);
        fragment.setFragmentManager(manager);
        transaction.replace(R.id.fragment_navigation_drawer, fragment);
        transaction.addToBackStack(null);
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
    
    final LinearLayout sampleSources = ((LinearLayout)getActivity().findViewById(R.id.layout_sample_sources));
    sampleSources.removeAllViews();
    this.sampleSourceButtons.clear();
    this.sampleSourceFileNameViews.clear();
    
    for (final String id : ids) {
      final LinearLayout sampleSource = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.sample_source, null);
      sampleSources.addView(sampleSource);
      
      final TextView sampleSourceTextView = (TextView)sampleSource.findViewById(R.id.sampleSourceText);
      sampleSourceTextView.setText("Source(" + id + ")"); //$NON-NLS-1$ //$NON-NLS-2$
      
      final Button sampleSourceButton = (Button)sampleSource.findViewById(R.id.sampleSourceButton); 
      this.sampleSourceButtons.add(sampleSourceButton);
      
      sampleSourceButton.setEnabled(false);
      sampleSourceButton.setOnClickListener(new OnClickListener() {
        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          final FragmentManager manager = getFragmentManager();
          final FragmentTransaction transaction = manager.beginTransaction();
          final AssetsListViewFragment fragment = new AssetsListViewFragment(id);
          fragment.setActivity(NavigationDrawerFragment.this.canvasActivity);
          fragment.setIsModelData(false);
          fragment.setFragmentManager(manager);
          transaction.replace(R.id.fragment_navigation_drawer, fragment);
          transaction.addToBackStack(null);
          transaction.commit();
        }
      });
      
      final TextView sampleSourceFileNameView = (TextView)sampleSource.findViewById(R.id.sampleSourceFileNameView);
      this.sampleSourceFileNameViews.put(id, sampleSourceFileNameView);

    }
  }

  /**
   * GroupManagerを取得します。
   * 
   * @return result GroupManager
   */
  public GroupManager getGroupManager() {
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
   * @param sampleSourceId ソースID
   */
  public void loadSampleSourceData(final InputStream input, final String filePath, final String sampleSourceId) {
    this.canvasActivity.canvasFragment.loadSourceData(input, filePath, sampleSourceId);    
    
    final String[] parts = filePath.split("/"); //$NON-NLS-1$
    final String sourceFileName = parts[parts.length - 1];
    this.sampleSourceFileNameViews.get(sampleSourceId).setText(sourceFileName);
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
      final  Cursor cursor = this.canvasActivity.getContentResolver().query(uri, null, null, null, null);
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
    dialog.show(getFragmentManager(), "alertDialogFragment"); //$NON-NLS-1$
  }

  /**
   * @param enabled
   */
  void setButtonEnabled(boolean enabled) {
    this.isSelectedModelFile = enabled;
    this.quickButton.setEnabled(enabled);
    this.slowButton.setEnabled(enabled);
    this.sourceNumberChangeButton.setEnabled((enabled));

    for (Button button : this.sourceSelectButtons) {
      button.setEnabled(enabled);
    }
    for (Button button : this.sourceReloadButtons) {
      button.setEnabled(enabled);
    }
    for (Button button : this.sourceDeleteButtons) {
      button.setEnabled(enabled);
    }   
    for (Button button : this.sampleSourceButtons) {
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

    for (Integer targetNumber : targetNumbers) {
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
