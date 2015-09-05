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
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.model.searcher.ExcecuteSearchGroup;
import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.searcher.GroupNameManager;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.SceneModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

import roboguice.fragment.RoboFragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.DialogFragment;
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
  TextView modelPathView;
  /** ソースのファイルパス。 */
  TextView sourcePathView;

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
  Button sourceSelectButton;
  /** モデルデータ読み込むときに押されるボタン */
  Button modelSelectButton;

  /** 端末の角度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton rotationSensorButton;
  /** 加速度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton accelerometerButton;
  /** */
  ToggleButton rotationLockButton;  

  /** ソースデータ再読み込みのためのボタン */
  Button sourceReloadButton;
  /** ソースデータを削除するためのボタン */
  Button sourceDeleteButton;
  /** ソース番号を変更するためのボタン */
  Button sourceNumberChangeButton;

  /** Assets0のソースを読み込むためのボタン。 */
  Button assetsSource0Button;
  /** Assets1のソースを読み込むためのボタン。 */
  Button assetsSource1Button;
  /** Assets2のソースを読み込むためのボタン。 */
  Button assetsSource2Button;
  /** Assets3のソースを読み込むためのボタン。 */
  Button assetsSource3Button;

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View mainView = inflater.inflate(R.layout.navigation_drawer_fragment, container, false);

    this.canvasActivity = (CanvasActivity)getActivity();

    createAnimationSpeedComponent(mainView);

    createModelComponent(mainView);
    
    createSourceComponent(mainView);

    createSensorComponent(mainView);

    createSampleComponent(mainView);
       
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
    this.modelSelectButton = (Button)mainView.findViewById(R.id.modelSelectButton);
    this.modelSelectButton.setOnClickListener(new View.OnClickListener() {
      final int REQUEST_CODE = CanvasActivity.REQUEST_CODE_PICK_FILE_OR_DIRECTORY;

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        NavigationDrawerFragment.this.canvasActivity.sendFileChooseIntent(this.REQUEST_CODE);
      }
    });
    
    this.modelPathView = (TextView)mainView.findViewById(R.id.modelPathView);
    this.modelPathView.setMovementMethod(ScrollingMovementMethod.getInstance());
  }

  private void createSourceComponent(final View mainView) {
    this.sourceSelectButton = (Button)mainView.findViewById(R.id.sourceSelectButton);
    this.sourceSelectButton.setEnabled(false);
    this.sourceSelectButton.setOnClickListener(new View.OnClickListener() {
      final int REQUEST_CODE = CanvasActivity.REQUEST_CODE_PICK_SOURCE_DATA_FILE;

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        NavigationDrawerFragment.this.canvasActivity.sendFileChooseIntent(this.REQUEST_CODE);
      }
    });
    
    this.sourcePathView = (TextView)mainView.findViewById(R.id.sourcePathView);

    this.sourceDeleteButton = (Button)mainView.findViewById(R.id.sourceDeleteButton);
    this.sourceDeleteButton.setEnabled(false);
    this.sourceDeleteButton.setOnClickListener(new View.OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        if (NavigationDrawerFragment.this.canvasActivity.canvasFragment.sourceData.size() != 0) {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.sourceData.clear();
          final String sourceFileName = "..."; //$NON-NLS-1$
          NavigationDrawerFragment.this.sourcePathView.setText(sourceFileName);
        }
      }
    });

    this.sourceReloadButton = (Button)mainView.findViewById(R.id.sourceReloadButton);
    this.sourceReloadButton.setEnabled(false);
    this.sourceReloadButton.setOnClickListener(new View.OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        if (NavigationDrawerFragment.this.canvasActivity.canvasFragment.sourceData.size() != 0) {
          final String id = "0"; //$NON-NLS-1$
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.addSource(id);
        }
      }
    });
    
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

  private void createSampleComponent(final View mainView) {
    final Button assetsModelButton = (Button)mainView.findViewById(R.id.assetsModelButton);
    assetsModelButton.setOnClickListener(new OnClickListener() {
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
    
    this.assetsSource0Button = (Button)mainView.findViewById(R.id.assetsSource0Button);
    this.assetsSource0Button.setEnabled(false);
    this.assetsSource0Button.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        final String id = "0"; //$NON-NLS-1$
        final AssetsListViewFragment fragment = new AssetsListViewFragment(id);
        fragment.setActivity(NavigationDrawerFragment.this.canvasActivity);
        fragment.setIsModelData(false);
        fragment.setFragmentManager(manager);
        transaction.replace(R.id.fragment_navigation_drawer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });
    
    this.assetsSource1Button = (Button)mainView.findViewById(R.id.assetsSource1Button);
    this.assetsSource1Button.setEnabled(false);
    this.assetsSource1Button.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        final String id = "1"; //$NON-NLS-1$
        final AssetsListViewFragment fragment = new AssetsListViewFragment(id);
        fragment.setActivity(NavigationDrawerFragment.this.canvasActivity);
        fragment.setIsModelData(false);
        fragment.setFragmentManager(manager);
        transaction.replace(R.id.fragment_navigation_drawer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });
    
    this.assetsSource2Button = (Button)mainView.findViewById(R.id.assetsSource2Button);
    this.assetsSource2Button.setEnabled(false);
    this.assetsSource2Button.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        final String id = "2"; //$NON-NLS-1$
        final AssetsListViewFragment fragment = new AssetsListViewFragment(id);
        fragment.setActivity(NavigationDrawerFragment.this.canvasActivity);
        fragment.setIsModelData(false);
        fragment.setFragmentManager(manager);
        transaction.replace(R.id.fragment_navigation_drawer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });
    
    this.assetsSource3Button = (Button)mainView.findViewById(R.id.assetsSource3Button);
    this.assetsSource3Button.setEnabled(false);
    this.assetsSource3Button.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        final String id = "3"; //$NON-NLS-1$
        final AssetsListViewFragment fragment = new AssetsListViewFragment(id);
        fragment.setActivity(NavigationDrawerFragment.this.canvasActivity);
        fragment.setIsModelData(false);
        fragment.setFragmentManager(manager);
        transaction.replace(R.id.fragment_navigation_drawer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });
    
    final TextView assetsSourceTextView = new TextView(getActivity());
    assetsSourceTextView.setText("Source(4)"); //$NON-NLS-1$
    
    final Button assetsSourceButton = new Button(getActivity());
    assetsSourceButton.setEnabled(false);
    assetsSourceButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        final String id = "4"; //$NON-NLS-1$
        final AssetsListViewFragment fragment = new AssetsListViewFragment(id);
        fragment.setActivity(NavigationDrawerFragment.this.canvasActivity);
        fragment.setIsModelData(false);
        fragment.setFragmentManager(manager);
        transaction.replace(R.id.fragment_navigation_drawer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });
    
    
    
    final LinearLayout assetsSourceLayout = new LinearLayout(getActivity());
    assetsSourceLayout.addView(assetsSourceTextView);
    assetsSourceLayout.addView(assetsSourceButton);
    
    ((LinearLayout)mainView.findViewById(R.id.layout_fragment_navigation_drawer)).addView(assetsSourceLayout);
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
    
    this.sourcePathView.setText(sourceFileName);
    
    this.canvasActivity.canvasFragment.loadSourceData(sourceStream,  uri.getPath(), this.sourceId);
    // sourceStream has been already closed in the loadSourceData method. 
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

    this.modelPathView.setText(modelFileName);
    final String sourceFileName = "..."; //$NON-NLS-1$
    this.sourcePathView.setText(sourceFileName);

    try {
      this.canvasActivity.canvasFragment.loadModelData(modelStream);
      setButtonEnabled(true);
      modelStream.close();
      
      if (this.canvasActivity.canvasFragment.sourceData.size() != 0) {
        this.canvasActivity.canvasFragment.sourceData.clear();
      }
    } catch (Mikity3dSerializeDeserializeException e) {
      setExceptionDailogFragment("please select model file."); //$NON-NLS-1$
      setButtonEnabled(false);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @param message 例外メッセージ
   */
  void setExceptionDailogFragment(String message) {
    final DialogFragment fragment = new ExceptionDialogFragment();
    ((ExceptionDialogFragment)fragment).setMessage(message);
    fragment.show(getFragmentManager(), "exceptionDialogFragment"); //$NON-NLS-1$
  }

  /**
   * @param enabled
   */
  void setButtonEnabled(boolean enabled) {
    this.isSelectedModelFile = enabled;
    this.sourceSelectButton.setEnabled(enabled);
    this.quickButton.setEnabled(enabled);
    this.slowButton.setEnabled(enabled);
    this.sourceNumberChangeButton.setEnabled((enabled));
    this.sourceReloadButton.setEnabled(enabled);
    this.sourceDeleteButton.setEnabled(enabled);
    this.assetsSource0Button.setEnabled(enabled);
    this.assetsSource1Button.setEnabled(enabled);
    this.assetsSource2Button.setEnabled(enabled);
    this.assetsSource3Button.setEnabled(enabled);
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
