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
import org.mklab.mikity.model.searcher.GroupName;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dModel;
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
  ObjectGroupManager manager;

  TextView dataFilePathView;
  TextView modelFilePathView;

  /** 前回のタッチのｘ座標 */
  float prevX = 0;
  /** 前回のタッチのｙ座標 */
  float prevY = 0;
  /** アニメーションの再生速度 丸め誤差を防ぐために１０で割る必要があります。 */
  int animationSpeed = 10;

  /** アニメーションスピードを表示するエディットテキスト */
  EditText animationSpeedTextEdit;
  /** 3Dモデルのインプットストリーム */
  private InputStream inputModelDataFile;
  /** 3Dモデルが選ばれて表示されたかどうかのフラグ */
  boolean isSelectedModelFile;
  /** モデルを参照するときに押されるボタン */
  Button selectButton;
  /** アニメーションスピードを早くするときに押されるボタン */
  Button quickButton;
  /** アニメーションスピードを遅くするときに押されるボタン */
  Button slowButton;
  /** モデルファイル読み込むときに押されるボタン */
  Button loadModelButton;
  /** 端末の角度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton gyroToggleButton;
  /** 加速度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton accelerToggleButton;
  ToggleButton rotateTogguleButton;
  InputStream inputTimeDataFile;
  String timeDataName;
  private String modelFileName;
  CanvasActivity canvasActivity;
  /** アニメーションスピードテキスト用のスピード */
  int animationTextSpeed;
  /** 時間データ再読み込みのためのボタン */
  Button reloadButton;
  /** 時間データを削除するためのボタン */
  Button timeDataDeleteButton;
  private Cursor cursor;
  private Cursor cursor2;
  /** サンプルモデル読み込みのためのボタン */
  Button sampleModelButton;
  /** カラムを入れ替えるためのボタン */
  Button setNumberButton;
  /** ストリーム */
  InputStream input;
  private Button unzipSaveButton;
  private Button assetsModelButton;
  Button assetsTimeButton;

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.navigation_drawer_fragment, container, false);

    this.canvasActivity = (CanvasActivity)getActivity();

    //モデルデータ選択ボタンの表示
    this.loadModelButton = (Button)view.findViewById(R.id.modelSelectButton);
    //時系列選択ボタンの配置
    this.selectButton = (Button)view.findViewById(R.id.timeSelectButton);
    this.quickButton = (Button)view.findViewById(R.id.quickButton);
    this.slowButton = (Button)view.findViewById(R.id.slowButton);
    this.reloadButton = (Button)view.findViewById(R.id.reloadButton);
    this.timeDataDeleteButton = (Button)view.findViewById(R.id.timeDataDeleteButton);
    //    this.sampleModelButton = (Button)view.findViewById(R.id.setSampleModelButton);
    this.setNumberButton = (Button)view.findViewById(R.id.sampleSetColumnButton);

    this.selectButton.setEnabled(false);
    this.quickButton.setEnabled(false);
    this.slowButton.setEnabled(false);
    this.reloadButton.setEnabled(false);
    this.timeDataDeleteButton.setEnabled(false);
    this.setNumberButton.setEnabled(false);

    this.loadModelButton.setOnClickListener(new View.OnClickListener() {

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
    this.dataFilePathView = (TextView)view.findViewById(R.id.filePathView);
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

    this.selectButton.setOnClickListener(new View.OnClickListener() {

      final int REQUEST_CODE = CanvasActivity.REQUEST_CODE_PICK_TIME_DATA_FILE;

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.sendFileChooseIntent(this.REQUEST_CODE);
      }
    });

    this.reloadButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view1) {
        if (NavigationDrawerFragment.this.canvasActivity.canvasFragment.data != null) {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.setTimeData();
        }
      }
    });

    this.timeDataDeleteButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view1) {
        if (NavigationDrawerFragment.this.canvasActivity.canvasFragment.data != null) {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.data = null;
          NavigationDrawerFragment.this.timeDataName = "..."; //$NON-NLS-1$
          NavigationDrawerFragment.this.dataFilePathView.setText(NavigationDrawerFragment.this.timeDataName);
        }
      }
    });

    this.slowButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
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
      public void onClick(View v) {
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
      public void onClick(View v) {
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
      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.controlRotation();
      }
    });

    this.setNumberButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final ListColumnFragment fragment = new ListColumnFragment();
        if (NavigationDrawerFragment.this.canvasActivity.canvasFragment.root != null) {
          fragment.setGroupManager(getGroupManager());
          fragment.setNavigationDrawerFragment(NavigationDrawerFragment.this);

          fragmentTransaction.replace(R.id.fragment_navigation_drawer, fragment);
          fragmentTransaction.addToBackStack(null);
          fragmentTransaction.commit();
        }
      }
    });

    this.assetsModelButton = (Button)view.findViewById(R.id.assetsModelButtonView);
    this.assetsModelButton.setOnClickListener(new OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final AssetsListViewFragment fragment = new AssetsListViewFragment();
        fragment.setActivity(NavigationDrawerFragment.this.canvasActivity);
        fragment.setIsModelData(true);
        fragment.setFragmentManager(fragmentManager);
        fragmentTransaction.replace(R.id.fragment_navigation_drawer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
      }
    });

    this.assetsTimeButton = (Button)view.findViewById(R.id.assetsTimeButtonView);
    this.assetsTimeButton.setEnabled(false);
    this.assetsTimeButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final AssetsListViewFragment fragment = new AssetsListViewFragment();
        fragment.setActivity(NavigationDrawerFragment.this.canvasActivity);
        fragment.setIsModelData(false);
        fragment.setFragmentManager(fragmentManager);
        fragmentTransaction.replace(R.id.fragment_navigation_drawer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
    final Mikity3d root = this.canvasActivity.canvasFragment.root;
    final Mikity3dModel model = root.getModel(0);
    final GroupModel[] groupArray = model.getGroups();
    final GroupModel group = groupArray[0];
    final ExcecuteSearchGroup search = new ExcecuteSearchGroup();
    final GroupName groupManager = new GroupName(group.getName(), null);
    final GroupManager result = search.searchGroupRecursion(group, groupManager);
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
  void loadTimeData(Uri uri) {
    if (uri == null) {
      return;
    }

    if ("content".equals(uri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {
        this.inputTimeDataFile = this.canvasActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      this.cursor2 = this.canvasActivity.getContentResolver().query(uri, null, null, null, null);
      this.cursor2.moveToFirst();
      this.timeDataName = this.cursor2.getString(this.cursor2.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      final String timeDataFilePath = uri.getPath();
      this.canvasActivity.canvasFragment.setTimeDataPath(timeDataFilePath);
      try {
        this.inputTimeDataFile = new FileInputStream(timeDataFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final String[] parts = timeDataFilePath.split("/"); //$NON-NLS-1$
      this.timeDataName = parts[parts.length - 1];
    }
    
    this.dataFilePathView.setText(this.timeDataName);
    
    this.canvasActivity.canvasFragment.setTimeDataUri(uri);
    this.canvasActivity.canvasFragment.loadTimeData(this.inputTimeDataFile);
    // inputTimeDataFile has been already closed in the loadTimeData method. 
    // this.inputTimeDataFile.close();
    this.inputTimeDataFile = null;

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
        this.inputModelDataFile = this.canvasActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      this.cursor = this.canvasActivity.getContentResolver().query(uri, null, null, null, null);
      this.cursor.moveToFirst();
      this.modelFileName = this.cursor.getString(this.cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      final String modelDataFilePath = uri.getPath();
      this.canvasActivity.canvasFragment.setModelFilePath(modelDataFilePath);
      try {
        this.inputModelDataFile = new FileInputStream(modelDataFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final String[] parts = modelDataFilePath.split("/"); //$NON-NLS-1$
      this.modelFileName = parts[parts.length - 1];
    }

    this.modelFilePathView.setText(this.modelFileName);
    this.timeDataName = "..."; //$NON-NLS-1$
    this.dataFilePathView.setText(this.timeDataName);

    try {
      this.canvasActivity.canvasFragment.loadModelData(this.inputModelDataFile);
      setButtonEnabled(true);
      this.inputModelDataFile.close();
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
    final DialogFragment dialogFragment = new ExceptionDialogFragment();
    ((ExceptionDialogFragment)dialogFragment).setMessage(message);
    dialogFragment.show(getFragmentManager(), "exceptionDialogFragment"); //$NON-NLS-1$
  }

  /**
   * @param flag
   */
  void setButtonEnabled(boolean flag) {
    this.isSelectedModelFile = flag;
    this.selectButton.setEnabled(flag);
    this.quickButton.setEnabled(flag);
    this.slowButton.setEnabled(flag);
    this.setNumberButton.setEnabled((flag));
    this.reloadButton.setEnabled(flag);
    this.timeDataDeleteButton.setEnabled(flag);
    this.assetsTimeButton.setEnabled(flag);
  }

  /**
   * 番号を入れ替えます。
   * 
   * @param targetNumbers グループの階層を所持したリスト
   * @param childPosition アニメーションデータがある場所
   * @param number 入れ替える番号
   */
  void changeModelNumber(List<Integer> targetNumbers, int childPosition, int number) {
    final Mikity3dModel model = this.canvasActivity.canvasFragment.root.getModel(0);
    final GroupModel[] groups = model.getGroups();
    GroupModel group = groups[0];

    for (Integer targetNumber : targetNumbers) {
      //group = group.getGroup(targetNumber.intValue());
      group = group.getGroups()[targetNumber.intValue()];
    }
    //group.getAnimation(childPosition).setNumber(number);
    group.getAnimations()[childPosition].setNumber(number);
    this.canvasActivity.canvasFragment.configurateModel();
    this.canvasActivity.canvasFragment.setGroupManager();
  }

  /**
   * @param uri URL
   */
  void unzipSaveFile(Uri uri) {
    if (uri == null) {
      return;
    }

    // ストリームを直接URIから取り出します。
    try {
      final InputStream zipFile = this.canvasActivity.getContentResolver().openInputStream(uri);
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
