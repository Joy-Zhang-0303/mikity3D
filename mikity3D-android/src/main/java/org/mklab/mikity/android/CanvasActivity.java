/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Timer;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.model.MovableGroupManager;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class CanvasActivity extends RoboFragmentActivity {

  protected static final String LOGTAG = null;
  GLSurfaceView glView;

  /** アニメーション用タスク */
  AnimationTask animationTask;

  /** */
  public static boolean playable = true;

  /** 等間隔の時間を保存しとく配列 */
  double[] timeTable;

  Timer timer = new Timer();

  /** */
  MovableGroupManager manager;

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
  /** アニメーションスピードを表示するエディットテキスト */
  public EditText animationSpeedTextEdit;
  /** 3Dモデルが選ばれて表示されたかどうかのフラグ */
  protected boolean isSelectedModelFile;
  private boolean registerAccerlerometer;
  private boolean registerMagneticFieldSensor;
  /** 端末の角度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton gyroToggleButton;
  /** 加速度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton accelerToggleButton;
  private ActionBarDrawerToggle mDrawerToggle;
  private DrawerLayout mDrawer;
  private Configuration config;
  @InjectFragment(R.id.fragment_canvas)
  CanvasFragment canvasFragment;
  boolean isSelectedsampleModel;
  boolean isSelectedsampleData;
  InputStream inputDataFile;
  public NavigationDrawerFragment ndFragment;
  public ModelColumnNumberFragment mcnFragment;
  Intent i;
  Uri sUri;
  Bundle bundle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.canvas);
    
//    startByExternalActivity();

    this.mDrawer = (DrawerLayout)findViewById(R.id.layout_activity_canvas);
    this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawer, R.drawable.menu, R.string.drawer_open, R.string.drawer_close);
    this.mDrawerToggle.syncState();
    this.mDrawer.setDrawerListener(this.mDrawerToggle);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setLogo(getResources().getDrawable(R.drawable.icon));
    getActionBar().setHomeButtonEnabled(true);
    getActionBar().setDisplayUseLogoEnabled(true);
    startSampleIntent();
  }
  
  private void startSampleIntent() {
    i = getIntent();
//    if(i.getData() != null) {
//      sUri = i.getData();
//      Toast.makeText(this, i.toString(), Toast.LENGTH_LONG).show();
//      loadModelUri(sUri);
//    }
    if(i.getBundleExtra("path") != null) {
      bundle = i.getBundleExtra("path");
//      Uri[] urim = (Uri[])bundle.getParcelableArray("path");
      Uri mPath = bundle.getParcelable("path");
      loadModelUri(mPath);
      Uri dPath = bundle.getParcelable("data");
      loadDataUri(dPath);
    }
  }

  private void startByExternalActivity() {
    //外部アプリからの起動
//    Intent intent = getIntent();
//    if (intent.getStringArrayListExtra("org.mklab.mikity.android.ModelDataPathAndTimeDataPath") != null) { //$NON-NLS-1$
//      ArrayList<String> data = intent.getStringArrayListExtra("org.mklab.mikity.android.ModelDataPathAndTimeDataPath"); //$NON-NLS-1$
//      Toast.makeText(this, "Launch ounter application", Toast.LENGTH_LONG).show(); //$NON-NLS-1$
//
//      String modelFilePath = data.get(0);
//      String timeDataPath = data.get(1);
//
//      try {
//        this.inputModelFile = new FileInputStream(modelFilePath);
//        this.modelFilePathView.setText(new File(modelFilePath).getName());
//
//      } catch (FileNotFoundException e) {
//        throw new RuntimeException(e);
//      }
//
//      try {
//        this.canvasFragment.loadModelFile(this.inputModelFile);
//      } catch (IOException e) {
//        throw new RuntimeException(e);
//      } catch (Mikity3dSerializeDeserializeException e) {
//
//        throw new RuntimeException(e);
//      }
//
//      this.isSelectedModelFile = true;
//
//      this.selectButton.setEnabled(true);
//      this.quickButton.setEnabled(true);
//      this.slowButton.setEnabled(true);
//      this.playButton.setEnabled(true);
//      this.stopButton.setEnabled(true);
//      this.modelRenderer.updateDisplay();
//
//      this.filePathView.setText(new File(timeDataPath).getName());
//
//      InputStream mat;
//      try {
//        mat = new FileInputStream(timeDataPath);
//      } catch (FileNotFoundException e) {
//        throw new RuntimeException(e);
//      }
//      this.canvasFragment.setTimeData(mat);
//
//      try {
//        mat.close();
//      } catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//    }
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    this.mDrawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    this.mDrawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (this.mDrawerToggle.onOptionsItemSelected(item)) {
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
            loadModelUri(uri);
        }
        break;
      case REQUEST_CODE_PICK_TIME_DATA_FILE:
        if (resultCode == RESULT_OK && data != null) {
          Uri uri = data.getData();

            loadDataUri(uri);
            
//          } catch (IOException e) {
//            Toast.makeText(this, "Please select time data", Toast.LENGTH_SHORT).show();
//            this.ndFragment.timeDataName = "";
//            this.ndFragment.filePathView.setText(this.ndFragment.timeDataName);
//          }
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
    this.canvasFragment.setTimeDataUri(uri);
  }

  private void loadModelUri(Uri uri) {
    this.ndFragment.loadModelUri(uri);
    
    this.canvasFragment.timeDataUri = null;
    this.canvasFragment.modelRenderer.updateDisplay();
  }
  
  public void controlRotate() {
    this.config = getResources().getConfiguration();
    int rotation = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    boolean isReverse = false;
    if (this.ndFragment.rotateTogguleButton.isChecked()) {
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
  
  protected void setMCNFragmnet(ModelColumnNumberFragment mcnFragment) {
	this.ndFragment.setGroupNameList(this.canvasFragment.root);
    this.mcnFragment = mcnFragment;
  }
}
