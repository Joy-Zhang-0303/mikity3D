/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.app.FragmentManager;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.android.view.renderer.OpenglesModelRenderer;
import org.mklab.mikity.control.AnimationTaskListener;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dConfiguration;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;
import org.mklab.nfc.matrix.Matrix;
import org.mklab.nfc.matx.MatxMatrix;
import org.openintents.intents.OIFileManager;

import roboguice.activity.RoboActivity;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectFragment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.provider.OpenableColumns;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.View;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class CanvasActivity extends RoboFragmentActivity {

  protected static final String LOGTAG = null;
  private boolean mIsInitScreenSize;
  GLSurfaceView glView;

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
  int animationSpeed = 10;

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
  private Button selectButton;
  /** アニメーションスピードを早くするときに押されるボタン */
  private Button quickButton;
  /** アニメーションスピードを遅くするときに押されるボタン */
  private Button slowButton;
  /** アニメーションを停止するときに押されるボタン */
  private Button stopButton;
  /** モデルファイル読み込むときに押されるボタン */
  private Button loadModelButton;
  /** アニメーションを再生するボタン */
  private Button playButton;
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
  ToggleButton gyroToggleButton;
  /** 端末の角度を3Dオブジェクトに反映させるかどうかのフラグ */
  private boolean useOrientationSensor = false;
  /** */
  private float[] prevAccerlerometer = new float[3];
  /** 加速度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton accelerToggleButton;
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
  @InjectFragment(R.id.fragment_canvas)
  CanvasFragment canvasFragment;
  private Button sampleModelButton;
  boolean isSelectedsampleModel;
  boolean isSelectedsampleData;
  InputStream inputDataFile;
  private String timeDataName;
  private String modelFileName;
  @InjectFragment(R.id.fragment_instance_management)
  InstanceManagementFragment imFragment;
  protected Button setModelColumnNumberButton;
  public NavigationDrawerFragment ndFragment;
  

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.canvas);
    //this.inputModelFile = res.openRawResource(R.raw.pendulum);
    final OIFileManager fileManager = new OIFileManager(this);

//    //モデルデータ選択ボタンの表示
//    this.loadModelButton = (Button)findViewById(R.id.modelSelectButton);
//    //時系列選択ボタンの配置
//    this.selectButton = (Button)findViewById(R.id.timeSelectButton);
//    this.quickButton = (Button)findViewById(R.id.quickButton);
//    this.slowButton = (Button)findViewById(R.id.slowButton);
//    this.playButton = (Button)findViewById(R.id.button1);
//    this.stopButton = (Button)findViewById(R.id.button2);
//
//    this.selectButton.setEnabled(false);
//    this.quickButton.setEnabled(false);
//    this.slowButton.setEnabled(false);
//    this.playButton.setEnabled(false);
//    this.stopButton.setEnabled(false);
//
//    this.loadModelButton.setOnClickListener(new View.OnClickListener() {
//
//      /**
//       * {@inheritDoc}
//       */
//      public void onClick(View v) {
//        //        if (CanvasActivity.this.canvasFragment.root != null) {
//        //          CanvasActivity.this.isSelectedModelFile = true;
//        //        } else {
//        //          CanvasActivity.this.isSelectedModelFile = false;
//        //        }
//        //        fileManager.getFilePath();
//        //      if(Build.VERSION.SDK_INT < 19) {        
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");
//        startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
//        //      } else {
//        //        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        //        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        //        intent.setType("*/*");
//        //        startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
//        //    }
//      }
//    });
//
//    this.animationSpeedTextEdit = (EditText)findViewById(R.id.animationSpeedEditText);
//    this.animationSpeedTextEdit.clearFocus();
//    this.animationSpeedTextEdit.setText(Double.toString(this.animationSpeed / 10));
//    this.animationSpeedTextEdit.clearFocus();
//
//    //ファイルパスビューの配置
//
//    this.filePathView = new TextView(this);
//    this.modelFilePathView = new TextView(this);
//    this.filePathView = (TextView)findViewById(R.id.filePathView);
//    this.modelFilePathView = (TextView)findViewById(R.id.modelPathView);
//
//    this.testTextView = new TextView(this);
//    this.testTextView = (TextView)findViewById(R.id.textView1);
//
//    //再生速度の設定
//    this.quickButton.setOnClickListener(new View.OnClickListener() {
//
//      /**
//       * {@inheritDoc}
//       */
//      public void onClick(View v) {
//        CanvasActivity.this.animationSpeed = (int)(Double.parseDouble(CanvasActivity.this.animationSpeedTextEdit.getText().toString()) * 10);
//        CanvasActivity.this.animationSpeed += 1;
//        CanvasActivity.this.animationSpeedTextEdit.setText("" + (double)CanvasActivity.this.animationSpeed / 10); //$NON-NLS-1$
//        if (CanvasActivity.this.animationTask != null) CanvasActivity.this.animationTask.setSpeedScale(CanvasActivity.this.animationSpeed / 10);
//      }
//    });
//
//    this.selectButton.setOnClickListener(new View.OnClickListener() {
//
//      public void onClick(View v) {
//        //        fileManager.getFilePath();
//        //      if(Build.VERSION.SDK_INT < 19) {        
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");
//        startActivityForResult(intent, REQUEST_CODE_PICK_TIME_DATA_FILE);
//        //      } else {
//        //        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        //        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        //        intent.setType("*/*");
//        //        startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
//      }
//      //    }
//    });
//
//    // イベントリスナー
//    this.playButton.setOnClickListener(new View.OnClickListener() {
//
//      // コールバックメソッド
//      public void onClick(View view) {
//        CanvasActivity.this.canvasFragment.runAnimation();
//      }
//    });
//
//    this.slowButton.setOnClickListener(new View.OnClickListener() {
//
//      /**
//       * {@inheritDoc}
//       */
//      public void onClick(View v) {
//        CanvasActivity.this.animationSpeed = (int)(Double.parseDouble(CanvasActivity.this.animationSpeedTextEdit.getText().toString()) * 10);
//        CanvasActivity.this.animationSpeed -= 1;
//        if (CanvasActivity.this.animationSpeed < 0) CanvasActivity.this.animationSpeed = 0;
//        CanvasActivity.this.animationSpeedTextEdit.setText(Double.toString((double)CanvasActivity.this.animationSpeed / 10));
//        if (CanvasActivity.this.animationTask != null) CanvasActivity.this.animationTask.setSpeedScale(CanvasActivity.this.animationSpeed / 10);
//      }
//    });
//
//    this.stopButton.setOnClickListener(new View.OnClickListener() {
//
//      // コールバックメソッド
//      public void onClick(View view) {
//        CanvasActivity.this.canvasFragment.timer.cancel();
//        CanvasActivity.this.canvasFragment.playable = false;
//      }
//    });
//
//    this.gyroToggleButton = (ToggleButton)findViewById(R.id.gyroToggleButton);
//    this.gyroToggleButton.setOnClickListener(new OnClickListener() {
//
//      /**
//       * {@inheritDoc}
//       */
//      public void onClick(View v) {
//        if (CanvasActivity.this.gyroToggleButton.isChecked()) {
//          CanvasActivity.this.canvasFragment.useOrientationSensor = true;
//        } else CanvasActivity.this.canvasFragment.useOrientationSensor = false;
//
//      }
//    });
//    this.accelerToggleButton = (ToggleButton)findViewById(R.id.accelerToggleButton);
//    this.accelerToggleButton.setOnClickListener(new OnClickListener() {
//
//      /**
//       * {@inheritDoc}
//       */
//      public void onClick(View v) {
//        if (CanvasActivity.this.accelerToggleButton.isChecked()) {
//          CanvasActivity.this.canvasFragment.useAccelerSensor = true;
//
//        } else {
//          CanvasActivity.this.canvasFragment.useAccelerSensor = false;
//        }
//      }
//
//    });
//    this.rotateTogguleButton = (ToggleButton)findViewById(R.id.rotateLayoutButton);
//    this.rotateTogguleButton.setOnClickListener(new OnClickListener() {
//
//      public void onClick(View v) {
//        CanvasActivity.this.config = getResources().getConfiguration();
//        int rotation = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRotation();
//        boolean isReverse = false;
//        if (CanvasActivity.this.rotateTogguleButton.isChecked()) {
//          switch (rotation) {
//            case Surface.ROTATION_180:
//            case Surface.ROTATION_270:
//              isReverse = true;
//              break;
//            default:
//              isReverse = false;
//              break;
//          }
//          if (CanvasActivity.this.config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            if (isReverse) {
//              setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
//            } else {
//              setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            }
//          } else {
//            if (isReverse) {
//              setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
//            } else {
//              setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//          }
//          CanvasActivity.this.canvasFragment.modelRenderer.updateDisplay();
//        } else {
//          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
//        }
//      }
//    });
//
//    this.sampleModelButton = (Button)findViewById(R.id.sampleModelButton);
//    this.sampleModelButton.setOnClickListener(new OnClickListener() {
//
//      /**
//       * {@inheritDoc}
//       */
//      public void onClick(View v) {
//        CanvasActivity.this.canvasFragment.changeModelConfig();
//        //        if(Build.VERSION.SDK_INT < 19) {        
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");
//        startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
//        //        } else {
//        //          Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        //          intent.addCategory(Intent.CATEGORY_OPENABLE);
//        //          intent.setType("*/*");
//        //          startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
//      }
//      //      }
//    });
//    
//    this.setModelColumnNumberButton = (Button)findViewById(R.id.setModelColumnNumberlButton);
//    this.setModelColumnNumberButton.setEnabled(false);
//    this.setModelColumnNumberButton.setOnClickListener(new OnClickListener() {
//
//      /**
//       * {@inheritDoc}
//       */
//      public void onClick(View v) {
//        
//        moveToSetModelColumn();
//        
//        }
//    });

    startByExternalActivity();

    this.mDrawer = (DrawerLayout)findViewById(R.id.layout_activity_canvas);
    this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawer, R.drawable.menu, R.string.drawer_open, R.string.drawer_close);
    this.mDrawer.setDrawerListener(this.mDrawerToggle);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);
    if (this.canvasFragment.manager != null) Log.d("onRestore:this.fragment.manager == null ???", this.canvasFragment.manager.toString());
    if (this.canvasFragment.manager == null) Log.d("onCreate:this.fragment.manager == null ???", "manager==null");
  }

  private void startByExternalActivity() {
    //外部アプリからの起動
    Intent intent = getIntent();
    if (intent.getStringArrayListExtra("org.mklab.mikity.android.ModelDataPathAndTimeDataPath") != null) { //$NON-NLS-1$
      ArrayList<String> data = intent.getStringArrayListExtra("org.mklab.mikity.android.ModelDataPathAndTimeDataPath"); //$NON-NLS-1$
      Toast.makeText(this, "Launch ounter application", Toast.LENGTH_LONG).show(); //$NON-NLS-1$

      String modelFilePath = data.get(0);
      String timeDataPath = data.get(1);

      try {
        this.inputModelFile = new FileInputStream(modelFilePath);
        this.modelFilePathView.setText(new File(modelFilePath).getName());

      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }

      try {
        this.canvasFragment.loadModelFile(this.inputModelFile);
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
      this.modelRenderer.updateDisplay();

      this.filePathView.setText(new File(timeDataPath).getName());

      InputStream mat;
      try {
        mat = new FileInputStream(timeDataPath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      this.canvasFragment.setTimeData(mat);

      try {
        mat.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    mDrawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
//    if (this.canvasFragment.progressDialog != null) {
//      this.canvasFragment.progressDialog.dismiss();
//    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /** 表示されるときに呼ばれる */
  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    // スクリーンサイズ調整が済んでいない場合は調整する
    if (this.canvasFragment.mIsInitScreenSize == false) {
      GLSurfaceView glSurfaceView = (GLSurfaceView)findViewById(R.id.glview1);
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(glSurfaceView.getWidth(), glSurfaceView.getHeight());
      glSurfaceView.setLayoutParams(params);
    }
    super.onWindowFocusChanged(hasFocus);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onResume() {
    if (!this.registerAccerlerometer) {
      //List<Sensor> sensors = this.canvasFragment.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
      this.canvasFragment.getSensor();
      if (this.canvasFragment.sensors.size() > 0) {
        this.registerAccerlerometer = this.canvasFragment.sensorManager.registerListener(this.canvasFragment, this.canvasFragment.sensors.get(0), SensorManager.SENSOR_DELAY_UI);
      }
    }
    if (!this.registerMagneticFieldSensor) {
      List<Sensor> sensors = this.canvasFragment.sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
      if (sensors.size() > 0) {
        this.registerMagneticFieldSensor = this.canvasFragment.sensorManager.registerListener(this.canvasFragment, sensors.get(0), SensorManager.SENSOR_DELAY_UI);
        this.canvasFragment.sensorManager.registerListener(this.canvasFragment, sensors.get(0), SensorManager.SENSOR_DELAY_UI);
      }
    }
    this.canvasFragment.glView.onResume();
    super.onResume();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPause() {
    this.canvasFragment.glView.onPause();
    if (this.registerAccerlerometer || this.registerMagneticFieldSensor) {
      this.canvasFragment.sensorManager.unregisterListener(this.canvasFragment);

      this.registerAccerlerometer = false;
      this.registerMagneticFieldSensor = false;
    }

    super.onPause();
  }

  /**
   * This is called after the file manager finished.
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case REQUEST_CODE_PICK_FILE_OR_DIRECTORY:
        if (resultCode == RESULT_OK && data != null) {
          Uri uri = data.getData();
          //readModelTimeData(uri);
          loadModelUri(uri);
        }
        break;
      case REQUEST_CODE_PICK_TIME_DATA_FILE:
        if (resultCode == RESULT_OK && data != null) {
          Uri uri = data.getData();
          //readModelTimeData(uri);
          loadDataUri(uri);
        }
      default:
        break;
    }
    if (this.canvasFragment.manager != null) Log.d("onRestore:this.fragment.manager == null ???", this.canvasFragment.manager.toString());
    if (this.canvasFragment.manager == null) Log.d("onCreate:this.fragment.manager == null ???", "manager==null");
  }

  /**
   * モデルデータと時間データをストリームとして取り出すためのメソッドです。
   * 
   * @param uri ファイルのuri
   */
  private void readModelTimeData(Uri uri) {
    // obtain the filename
    //          if (this.isSelectedModelFile && (new File(data.getData().getPath()).getName()).endsWith(".xml") == false) {
    if (this.ndFragment.isSelectedModelFile == true) {
      loadDataUri(uri);
    } else {
      loadModelUri(uri);
    }
  }

  private void loadDataUri(Uri uri) {
    this.ndFragment.loadDataUri(uri);
    
    this.canvasFragment.loadtimeSeriesData(this.ndFragment.inputDataFile);
    //this.canvasFragment.setTimeData(this.inputDataFile);
    this.canvasFragment.setTimeDataUri(uri);
    if (this.canvasFragment.manager != null) Log.d("onRestore:this.fragment.manager == null ???", this.canvasFragment.manager.toString());
    if (this.canvasFragment.manager == null) Log.d("onCreate:this.fragment.manager == null ???", "manager==null");
  }

  private void loadModelUri(Uri uri) {
    this.ndFragment.loadModelUri(uri);
    
    this.canvasFragment.timeDataUri = null;
    this.canvasFragment.modelRenderer.updateDisplay();
    if (this.canvasFragment.manager != null) Log.d("onRestore:this.fragment.manager == null ???", this.canvasFragment.manager.toString());
    if (this.canvasFragment.manager == null) Log.d("onCreate:this.fragment.manager == null ???", "manager==null");
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    
    this.ndFragment.saveInstanceonActivity();
    if (this.canvasFragment.manager != null) Log.d("onRestore:this.fragment.manager == null ???", this.canvasFragment.manager.toString());
    if (this.canvasFragment.manager == null) Log.d("onCreate:this.fragment.manager == null ???", "manager==null");
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    //this.canvasFragment.setModel();
    this.canvasFragment.setDirection();
//    this.ndFragment.setParameterFromArray();
//    this.ndFragment.setFileNameArray();
    if (this.canvasFragment.root != null) {
        //this.canvasFragment.setModel();
    }
    if (this.canvasFragment.timeDataUri != null) {
      //
      //loadDataUri(this.canvasFragment.timeDataUri);
      //this.canvasFragment.manager.setData(this.data);
    }
    this.ndFragment.restoreInstanceonActivity();
    this.canvasFragment.modelRenderer.updateDisplay();
    if (this.canvasFragment.manager != null) Log.d("onRestore:this.fragment.manager == null ???", this.canvasFragment.manager.toString());
    if (this.canvasFragment.manager == null) Log.d("onCreate:this.fragment.manager == null ???", "manager==null");
  }


  
  public void moveToSetModelColumn() {
  
//  Intent intent = new Intent(this, SetModelColumnNumberActivity.class);    
//  startActivity(intent);
//    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//    CanvasFragment fragment = new CanvasFragment();
//    fragmentTransaction.replace(R.id.fragment_canvas, fragment);
//    fragmentTransaction.addToBackStack(null);
//    fragmentTransaction.commit();
  }
  
  public void controlRotate() {
    this.config = getResources().getConfiguration();
    int rotation = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    boolean isReverse = false;
    if (CanvasActivity.this.rotateTogguleButton.isChecked()) {
      switch (rotation) {
        case Surface.ROTATION_180:
        case Surface.ROTATION_270:
          isReverse = true;
          break;
        default:
          isReverse = false;
          break;
      }
      if (CanvasActivity.this.config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        if (isReverse) {
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        } else {
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
      } else {
        if (isReverse) {
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        } else {
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
      }
      CanvasActivity.this.canvasFragment.modelRenderer.updateDisplay();
    } else {
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    }
  }
  
  public void sendIntent(int requestCode) {
//  if (CanvasActivity.this.canvasFragment.root != null) {
  //          CanvasActivity.this.isSelectedModelFile = true;
  //        } else {
  //          CanvasActivity.this.isSelectedModelFile = false;
  //        }
  //        fileManager.getFilePath();
  //      if(Build.VERSION.SDK_INT < 19) {        
  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
  intent.setType("*/*");
  startActivityForResult(intent, requestCode);
  //      } else {
  //        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
  //        intent.addCategory(Intent.CATEGORY_OPENABLE);
  //        intent.setType("*/*");
  //        startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
  //    }
  }
  
  public int getAnimationEditTextSpeed() {
    return this.animationSpeed;
  }
}
