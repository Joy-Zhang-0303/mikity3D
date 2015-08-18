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
import java.util.Timer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.model.ObjectGroupManager;
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
  /** アニメーション用タスク */
  AnimationTask animationTask;

  /** */
  static boolean playable = true;

  /** 等間隔の時間を保存しとく配列 */
  double[] timeTable;

  Timer timer = new Timer();

  /** */
  ObjectGroupManager groupManager;

  TextView sourceFilePathView;
  TextView modelFilePathView;

  /** 前回のタッチのｘ座標 */
  float prevX = 0;
  /** 前回のタッチのｙ座標 */
  float prevY = 0;
  /** アニメーションの再生速度 丸め誤差を防ぐために１０で割る必要があります。 */
  int animationSpeed = 10;

  /** アニメーションスピードを表示するエディットテキスト */
  EditText animationSpeedTextEdit;
  
  /** モデルの入力ストリーム。 */
  InputStream modelStream;
  /** ソースの入力ストリーム。   */
  InputStream sourceStream;
  
  /** 3Dモデルが選ばれて表示されたかどうかのフラグ */
  boolean isSelectedModelFile;
  /** アニメーションスピードを早くするときに押されるボタン */
  Button quickButton;
  /** アニメーションスピードを遅くするときに押されるボタン */
  Button slowButton;
  
  /** モデルを選択するときに押されるボタン */
  Button modelSelectButton;
  /** モデルデータ読み込むときに押されるボタン */
  Button modelLoadButton;

  /** 端末の角度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton gyroToggleButton;
  /** 加速度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton accelerToggleButton;
  ToggleButton rotateTogguleButton;
  
  String sourceFileName;
  String modelFileName;
  
  CanvasActivity canvasActivity;
  /** アニメーションスピードテキスト用のスピード */
  int animationTextSpeed;

  /** ソースデータ再読み込みのためのボタン */
  Button sourceReloadButton;
  /** ソースデータを削除するためのボタン */
  Button sourceDeleteButton;
  /** ソース番号を変更するためのボタン */
  Button sourceNumberChangeButton;
  
  private Cursor cursor;
  private Cursor cursor2;
  
  /** ストリーム */
  InputStream input;

  /** Assetsのモデルを読み込むためのボタン。 */
  Button assetsModelButton;
  /** Assetsのソースを読み込むためのボタン。 */
  Button assetsSourceButton;

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.navigation_drawer_fragment, container, false);

    this.canvasActivity = (CanvasActivity)getActivity();

    //モデル選択ボタンの表示
    this.modelLoadButton = (Button)view.findViewById(R.id.modelSelectButton);
    //ソース選択ボタンの配置
    this.modelSelectButton = (Button)view.findViewById(R.id.timeSelectButton);
    this.quickButton = (Button)view.findViewById(R.id.quickButton);
    this.slowButton = (Button)view.findViewById(R.id.slowButton);
    this.sourceReloadButton = (Button)view.findViewById(R.id.reloadButton);
    this.sourceDeleteButton = (Button)view.findViewById(R.id.timeDataDeleteButton);
    // this.sampleModelButton = (Button)view.findViewById(R.id.setSampleModelButton);
    this.sourceNumberChangeButton = (Button)view.findViewById(R.id.sampleSetColumnButton);

    this.modelSelectButton.setEnabled(false);
    this.quickButton.setEnabled(false);
    this.slowButton.setEnabled(false);
    this.sourceReloadButton.setEnabled(false);
    this.sourceDeleteButton.setEnabled(false);
    this.sourceNumberChangeButton.setEnabled(false);

    this.modelLoadButton.setOnClickListener(new View.OnClickListener() {

      final int REQUEST_CODE = CanvasActivity.REQUEST_CODE_PICK_FILE_OR_DIRECTORY;

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.sendFileChooseIntent(this.REQUEST_CODE);
      }
    });

    this.animationSpeedTextEdit = (EditText)view.findViewById(R.id.animationSpeedEditText);
    this.animationSpeedTextEdit.clearFocus();
    this.animationSpeedTextEdit.setText(Double.toString(this.animationSpeed / 10));
    this.animationSpeedTextEdit.clearFocus();

    //ファイルパスビューの配置
    this.sourceFilePathView = (TextView)view.findViewById(R.id.filePathView);
    this.modelFilePathView = (TextView)view.findViewById(R.id.modelPathView);
    this.modelFilePathView.setMovementMethod(ScrollingMovementMethod.getInstance());

    //再生速度の設定
    this.quickButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.animationSpeed += 1;
        NavigationDrawerFragment.this.animationSpeedTextEdit.setText("" + (double)NavigationDrawerFragment.this.animationSpeed / 10); //$NON-NLS-1$
        if (NavigationDrawerFragment.this.animationTask != null) NavigationDrawerFragment.this.animationTask.setSpeedScale(NavigationDrawerFragment.this.animationSpeed / 10);
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
      }
    });

    this.modelSelectButton.setOnClickListener(new View.OnClickListener() {

      final int REQUEST_CODE = CanvasActivity.REQUEST_CODE_PICK_TIME_DATA_FILE;

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.sendFileChooseIntent(this.REQUEST_CODE);
      }
    });

    this.sourceReloadButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        if (NavigationDrawerFragment.this.canvasActivity.canvasFragment.data != null) {
          final String id = "0"; //$NON-NLS-1$
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.setTimeData(id);
        }
      }
    });

    this.sourceDeleteButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        if (NavigationDrawerFragment.this.canvasActivity.canvasFragment.data != null) {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.data = null;
          NavigationDrawerFragment.this.sourceFileName = "..."; //$NON-NLS-1$
          NavigationDrawerFragment.this.sourceFilePathView.setText(NavigationDrawerFragment.this.sourceFileName);
        }
      }
    });

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

    this.gyroToggleButton = (ToggleButton)view.findViewById(R.id.gyroToggleButton);
    this.gyroToggleButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        if (NavigationDrawerFragment.this.gyroToggleButton.isChecked()) {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.useOrientationSensor = true;
        } else NavigationDrawerFragment.this.canvasActivity.canvasFragment.useOrientationSensor = false;

      }
    });
    this.accelerToggleButton = (ToggleButton)view.findViewById(R.id.accelerToggleButton);
    this.accelerToggleButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        if (NavigationDrawerFragment.this.accelerToggleButton.isChecked()) {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.useAccelerSensor = true;

        } else {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.useAccelerSensor = false;
        }
      }

    });
    this.rotateTogguleButton = (ToggleButton)view.findViewById(R.id.rotateLayoutButton);
    this.rotateTogguleButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        NavigationDrawerFragment.this.canvasActivity.controlRotation();
      }
    });

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

    this.assetsModelButton = (Button)view.findViewById(R.id.assetsModelButtonView);
    this.assetsModelButton.setOnClickListener(new OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        final AssetsListViewFragment fragment = new AssetsListViewFragment();
        fragment.setActivity(NavigationDrawerFragment.this.canvasActivity);
        fragment.setIsModelData(true);
        fragment.setFragmentManager(manager);
        transaction.replace(R.id.fragment_navigation_drawer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });

    this.assetsSourceButton = (Button)view.findViewById(R.id.assetsTimeButtonView);
    this.assetsSourceButton.setEnabled(false);
    this.assetsSourceButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        final AssetsListViewFragment fragment = new AssetsListViewFragment();
        fragment.setActivity(NavigationDrawerFragment.this.canvasActivity);
        fragment.setIsModelData(false);
        fragment.setFragmentManager(manager);
        transaction.replace(R.id.fragment_navigation_drawer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });
    // fragmentの値をactivityで保持。
    //TODO setRetainInstance()を使っても、activityでこのfragmentを保持しているため、処理が被っている。要修正
    this.canvasActivity.ndFragment = this;
    return view;
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

    if ("content".equals(uri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {
        this.sourceStream = this.canvasActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      this.cursor2 = this.canvasActivity.getContentResolver().query(uri, null, null, null, null);
      this.cursor2.moveToFirst();
      this.sourceFileName = this.cursor2.getString(this.cursor2.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      final String timeDataFilePath = uri.getPath();
      this.canvasActivity.canvasFragment.setTimeDataPath(timeDataFilePath);
      try {
        this.sourceStream = new FileInputStream(timeDataFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final String[] parts = timeDataFilePath.split("/"); //$NON-NLS-1$
      this.sourceFileName = parts[parts.length - 1];
    }
    
    this.sourceFilePathView.setText(this.sourceFileName);
    
    this.canvasActivity.canvasFragment.setTimeDataUri(uri);
    this.canvasActivity.canvasFragment.loadTimeData(this.sourceStream);
    // inputTimeDataFile has been already closed in the loadTimeData method. 
    // this.inputTimeDataFile.close();
    this.sourceStream = null;

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

    if ("content".equals(uri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {
        this.modelStream = this.canvasActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      this.cursor = this.canvasActivity.getContentResolver().query(uri, null, null, null, null);
      this.cursor.moveToFirst();
      this.modelFileName = this.cursor.getString(this.cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      final String modelFilePath = uri.getPath();
      this.canvasActivity.canvasFragment.setModelFilePath(modelFilePath);
      try {
        this.modelStream = new FileInputStream(modelFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final String[] parts = modelFilePath.split("/"); //$NON-NLS-1$
      this.modelFileName = parts[parts.length - 1];
    }

    this.modelFilePathView.setText(this.modelFileName);
    this.sourceFileName = "..."; //$NON-NLS-1$
    this.sourceFilePathView.setText(this.sourceFileName);

    try {
      this.canvasActivity.canvasFragment.loadModelData(this.modelStream);
      setButtonEnabled(true);
      this.modelStream.close();
      if (this.canvasActivity.canvasFragment.data != null) {
        this.canvasActivity.canvasFragment.data = null;
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
    this.modelSelectButton.setEnabled(enabled);
    this.quickButton.setEnabled(enabled);
    this.slowButton.setEnabled(enabled);
    this.sourceNumberChangeButton.setEnabled((enabled));
    this.sourceReloadButton.setEnabled(enabled);
    this.sourceDeleteButton.setEnabled(enabled);
    this.assetsSourceButton.setEnabled(enabled);
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
    this.canvasActivity.canvasFragment.setGroupManager();
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
