/*
 * Created on 2014/12/10
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.android.view.renderer.OpenglesModelRenderer;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.nfc.matrix.Matrix;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectFragment;


public class NavigationDrawerFragment extends RoboFragment {
  
  protected static final String LOGTAG = null;
  private boolean mIsInitScreenSize;

  /** アニメーション用タスク */
  AnimationTask animationTask;

  /** */
  public static boolean playable = true;

  /** */
  private double endTime;

  private Mikity3d root;

  /** 等間隔の時間を保存しとく配列 */
  double[] timeTable;

  Timer timer = new Timer();

  private Matrix data;

  /** */
  MovableGroupManager manager;

  /** ModelCanvas */
  private OpenglesModelRenderer modelRenderer;

  // DEBUG textview
  TextView testTextView;
  TextView filePathView;
  TextView modelFilePathView;

  /** 前回のタッチのｘ座標 */
  float prevX = 0;
  /** 前回のタッチのｙ座標 */
  float prevY = 0;
  /** アニメーションの再生速度 丸め誤差を防ぐために１０で割る必要があります。 */
  private int animationSpeed = 10;

  private final int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 0;
  private final int REQUEST_CODE_PICK_TIME_DATA_FILE = 1;
  /** 時系列データのファイルパス */
  private String filePath;
  /** 　ピンチイン、ピンチアウト用のジェスチャー */
  private ScaleGestureDetector gesDetect = null;
  /** スケーリング中かどうかのフラグ */
  private boolean scaling;
  /** 回転中かどうかのフラグ */
  private boolean rotationing;
  /** 3Dオブジェクトの大きさの値 */
  private double scaleValue = 1;
  /** アニメーションスピードを表示するエディットテキスト */
  public EditText animationSpeedTextEdit;
  /** 3Dモデルのインプットストリーム */
  private InputStream inputModelFile;
  /** 3Dモデルが選ばれて表示されたかどうかのフラグ */
  protected boolean isSelectedModelFile;
  /** モデルを参照するときに押されるボタン */
  Button selectButton;
  /** アニメーションスピードを早くするときに押されるボタン */
  Button quickButton;
  /** アニメーションスピードを遅くするときに押されるボタン */
  Button slowButton;
  /** アニメーションを停止するときに押されるボタン */
  Button stopButton;
  /** モデルファイル読み込むときに押されるボタン */
  Button loadModelButton;
  /** アニメーションを再生するボタン */
  Button playButton;
  /** センサーマネージャー */
  private SensorManager sensorManager;
  private boolean registerAccerlerometer;
  private boolean registerMagneticFieldSensor;
  /** センサーからの加速度を格納する配列 */
  private float[] accels = new float[3];
  /** センサーからの地磁気を格納する配列 */
  private float[] magneticFields = new float[3];
  /** センサーから算出した端末の角度を格納する配列 */
  private float[] orientations = new float[3];
  /** 角度の基準値 */
  private float[] prevOrientations = new float[3];
  /** 端末の角度を3Dオブジェクトに反映させるかどうかのトグル */
  private ToggleButton gyroToggleButton;
  /** 端末の角度を3Dオブジェクトに反映させるかどうかのフラグ */
  private boolean useOrientationSensor = false;
  /** */
  private float[] prevAccerlerometer = new float[3];
  /** 加速度を3Dオブジェクトに反映させるかどうかのトグル */
  private ToggleButton accelerToggleButton;
  /** 加速度のローパスフィルターのxの値 */
  private double lowPassX;
  /** 加速度のローパスフィルターのｙの値 */
  private double lowPassY;
  /** 加速度のローパスフィルターのzの値 */
  private double lowPassZ = 9.45;
  /** 加速度のハイパスフィルターのZの値 */
  private double rawAz;
  /** 前回加速度センサーを3Dオブジェクトに使用したときの時間 */
  private long useAccelerOldTime = 0L;
  /** 加速度センサーの値を3Dオブジェクトに反映させるかどうか */
  private boolean useAccelerSensor = false;

  private ActionBarDrawerToggle mDrawerToggle;
  private DrawerLayout mDrawer;
  private ToggleButton rotateTogguleButton;
  private Configuration config;

  private Button sampleModelButton;
  boolean isSelectedsampleModel;
  boolean isSelectedsampleData;
  InputStream inputDataFile;
  private String timeDataName;
  private String modelFileName;
  @InjectFragment(R.id.fragment_instance_management)
  InstanceManagementFragment imFragment;
  protected Button setModelColumnNumberButton;
  CanvasActivity canvasActivity;
  public int animationTextSpeed;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.navigation_drawer_fragment, container, false);
    
    this.canvasActivity = (CanvasActivity)getActivity();
    
    //モデルデータ選択ボタンの表示
    this.loadModelButton = (Button)view.findViewById(R.id.modelSelectButton);
    //時系列選択ボタンの配置
    this.selectButton = (Button)view.findViewById(R.id.timeSelectButton);
    this.quickButton = (Button)view.findViewById(R.id.quickButton);
    this.slowButton = (Button)view.findViewById(R.id.slowButton);
    this.playButton = (Button)view.findViewById(R.id.button1);
    this.stopButton = (Button)view.findViewById(R.id.button2);

    this.selectButton.setEnabled(false);
    this.quickButton.setEnabled(false);
    this.slowButton.setEnabled(false);
    this.playButton.setEnabled(false);
    this.stopButton.setEnabled(false);

    this.loadModelButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.sendIntent(REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
      }
    });

    this.animationSpeedTextEdit = (EditText)view.findViewById(R.id.animationSpeedEditText);
    this.animationSpeedTextEdit.clearFocus();
    this.animationSpeedTextEdit.setText(Double.toString(this.animationSpeed / 10));
    this.animationSpeedTextEdit.clearFocus();

    //ファイルパスビューの配置

//    this.filePathView = new TextView(this.canvasActivity);
//    this.modelFilePathView = new TextView(this.canvasActivity);
    this.filePathView = (TextView)view.findViewById(R.id.filePathView);
    this.modelFilePathView = (TextView)view.findViewById(R.id.modelPathView);

    this.testTextView = new TextView(this.canvasActivity);
    this.testTextView = (TextView)view.findViewById(R.id.textView1);

    //再生速度の設定
    this.quickButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.animationSpeed += 1;
        NavigationDrawerFragment.this.animationSpeedTextEdit.setText("" + (double)NavigationDrawerFragment.this.animationSpeed / 10); //$NON-NLS-1$
        if (NavigationDrawerFragment.this.animationTask != null) NavigationDrawerFragment.this.animationTask.setSpeedScale(NavigationDrawerFragment.this.canvasActivity.animationSpeed / 10);
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.setAnimationSpeed();
      }
    });

    this.selectButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.sendIntent(REQUEST_CODE_PICK_TIME_DATA_FILE);
      }
    });

    // イベントリスナー
    this.playButton.setOnClickListener(new View.OnClickListener() {

      // コールバックメソッド
      public void onClick(View view) {
        NavigationDrawerFragment.this.canvasActivity.canvasFragment.runAnimation();
      }
    });

    this.slowButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.canvasActivity.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.canvasActivity.animationSpeed -= 1;
        if (NavigationDrawerFragment.this.canvasActivity.animationSpeed < 0) NavigationDrawerFragment.this.canvasActivity.animationSpeed = 0;
        NavigationDrawerFragment.this.canvasActivity.animationSpeedTextEdit.setText(Double.toString((double)NavigationDrawerFragment.this.canvasActivity.animationSpeed / 10));
        if (NavigationDrawerFragment.this.canvasActivity.animationTask != null) NavigationDrawerFragment.this.canvasActivity.animationTask.setSpeedScale(NavigationDrawerFragment.this.canvasActivity.animationSpeed / 10);
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.setAnimationSpeed();
      }
    });

    this.stopButton.setOnClickListener(new View.OnClickListener() {

      // コールバックメソッド
      public void onClick(View view) {
        NavigationDrawerFragment.this.canvasActivity.canvasFragment.timer.cancel();
        NavigationDrawerFragment.this.canvasActivity.canvasFragment.playable = false;
      }
    });

    this.gyroToggleButton = (ToggleButton)view.findViewById(R.id.gyroToggleButton);
    this.gyroToggleButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        if (NavigationDrawerFragment.this.canvasActivity.gyroToggleButton.isChecked()) {
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
        if (NavigationDrawerFragment.this.canvasActivity.accelerToggleButton.isChecked()) {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.useAccelerSensor = true;

        } else {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.useAccelerSensor = false;
        }
      }

    });
    this.rotateTogguleButton = (ToggleButton)view.findViewById(R.id.rotateLayoutButton);
    this.rotateTogguleButton.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.controlRotate();
      }
    });

    this.sampleModelButton = (Button)view.findViewById(R.id.sampleModelButton);
    this.sampleModelButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.canvasFragment.changeModelConfig();
        NavigationDrawerFragment.this.canvasActivity.sendIntent(REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
      }

    });
    this.canvasActivity.ndFragment = this;
    return view;
  }
  
  public void setAnimationSpeed() {
    this.canvasActivity.animationSpeed = this.animationSpeed;
  }
  
  protected void saveInstanceonActivity() {
    this.imFragment.setParameter(isSelectedModelFile, selectButton.isEnabled(), quickButton.isEnabled(), 
        slowButton.isEnabled(), stopButton.isEnabled(), loadModelButton.isEnabled(),
        playButton.isEnabled());
    this.imFragment.setFileName(this.modelFileName, this.timeDataName);
  }
  
  public void restoreInstanceonActivity() {
    setParameterFromArray();
    setFileNameArray();
    this.modelFilePathView.setText(this.modelFileName);
    this.filePathView.setText(this.timeDataName);
  }
  
  protected void setParameterFromArray() {
    boolean[] paramArray = this.imFragment.getParameter();
    this.isSelectedModelFile = paramArray[0];
    this.selectButton.setEnabled(paramArray[1]);
    this.quickButton.setEnabled(paramArray[2]);
    this.slowButton.setEnabled(paramArray[3]);
    this.stopButton.setEnabled(paramArray[4]);
    this.loadModelButton.setEnabled(paramArray[5]);
    this.playButton.setEnabled(paramArray[6]);
  }
  
  protected void setFileNameArray() {
    String[] fileNameArray = this.imFragment.getFileNameArray();
    this.modelFileName = fileNameArray[0];
    this.timeDataName = fileNameArray[1];
  }
  
  protected void loadDataUri(Uri uri) {
    String timeDataPath;
    if (uri != null && "content".equals(uri.getScheme())) {
      // ストリームを直接URIから取り出します。
      try {
        this.inputDataFile = this.canvasActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      Cursor cursor = this.canvasActivity.getContentResolver().query(uri, null, null, null, null);
      cursor.moveToFirst();
      this.timeDataName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      //Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_SHORT).show();
      Log.d("URI", uri.toString());
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      timeDataPath = uri.getPath();
      this.canvasActivity.canvasFragment.setTimeDataPath(timeDataPath);
      //      this.filePathView.setText(new File(timeDataPath).getName());
      try {
        this.inputDataFile = new FileInputStream(timeDataPath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      String[] parts = timeDataPath.split("/");
      this.timeDataName = parts[parts.length - 1];
    }
    this.filePathView.setText(this.timeDataName);
  }
  
  protected void loadModelUri(Uri uri) {
    String modelFilePath;
    if (uri != null && "content".equals(uri.getScheme())) {
      // ストリームを直接URIから取り出します。
      try {
        this.inputModelFile = this.canvasActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      Cursor cursor = this.canvasActivity.getContentResolver().query(uri, null, null, null, null);
      cursor.moveToFirst();
      this.modelFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      //      Toast.makeText(getBaseContext(), cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)), Toast.LENGTH_SHORT).show();
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      modelFilePath = uri.getPath();
      this.canvasActivity.canvasFragment.setModelFilePath(modelFilePath);
      try {
        this.inputModelFile = new FileInputStream(modelFilePath);
        //        this.modelFilePathView.setText(new File(modelFilePath).getName());
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      String[] parts = modelFilePath.split("/");
      this.modelFileName = parts[parts.length - 1];
      Toast.makeText(this.canvasActivity.getBaseContext(), modelFileName, Toast.LENGTH_SHORT).show();
    }
    Log.d("URI", uri.toString());
    this.modelFilePathView.setText(this.modelFileName);
    this.timeDataName = "...";
    this.filePathView.setText(this.timeDataName);
    try {
      this.canvasActivity.canvasFragment.loadModelFile(this.inputModelFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
    this.isSelectedModelFile = true;
    this.selectButton.setEnabled(true);
    this.quickButton.setEnabled(true);
    this.slowButton.setEnabled(true);
    this.playButton.setEnabled(true);
    this.stopButton.setEnabled(true);
  }
}
