/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;

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
import android.os.SystemClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.LinearLayout;


/**
 * Mikity3dのメインとなるアクティビティクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/01/16
 */
public class CanvasActivity extends RoboFragmentActivity {

  final static int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 0;
  final static int REQUEST_CODE_PICK_TIME_DATA_FILE = 1;
  private boolean registerAccerlerometer;
  private boolean registerMagneticFieldSensor;
  
  /** NavigationDrawer用のアクションバートグル */
  private ActionBarDrawerToggle mDrawerToggle;
  
  /** NavigationDrawerのレイアウト */
  private DrawerLayout mDrawer;
  
  /** アクティビティのconfiguration */
  private Configuration config;
  
  /** CanvasFragment */
  @InjectFragment(R.id.fragment_canvas)
  CanvasFragment canvasFragment;
  
  /** NavigationDrawerFragment */
  NavigationDrawerFragment ndFragment;

  /** 停止ボタンが押されたかの判定 */
  private boolean isPushedStopButton;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.canvas);

    this.mDrawer = (DrawerLayout)findViewById(R.id.layout_activity_canvas);
    this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawer, R.drawable.menu, R.string.drawer_open, R.string.drawer_close);
    this.mDrawerToggle.syncState();
    this.mDrawer.setDrawerListener(this.mDrawerToggle);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setLogo(getResources().getDrawable(R.drawable.icon));
    getActionBar().setHomeButtonEnabled(true);
    getActionBar().setDisplayUseLogoEnabled(true);

    startIntentByExternalActivity();
  }

  /**
   * 外部アクティビティからのモデルパス、データパスを渡されるインテントに対応したメソッドです。
   */
  private void startIntentByExternalActivity() {
    final String intentPath = "path"; //$NON-NLS-1$
    final String intentDataPath = "data"; //$NON-NLS-1$
    
    final Intent intent = getIntent();
    if (intent.getBundleExtra(intentPath) != null) {
      final Bundle bundle = intent.getBundleExtra(intentPath);
      if (bundle.getParcelable(intentPath) != null) {
        final Uri mPath = bundle.getParcelable(intentPath);
        loadModelUri(mPath);
        if (bundle.getParcelable(intentDataPath) != null) {
          final Uri dPath = bundle.getParcelable(intentDataPath);
          loadTimeDataUri(dPath);
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    this.mDrawerToggle.syncState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    this.mDrawerToggle.onConfigurationChanged(newConfig);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (this.mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    
    switch (item.getItemId()) {
      case R.id.menu_play:
        CanvasActivity.this.canvasFragment.runAnimation();
        this.isPushedStopButton = false;
        break;
      case R.id.menu_stop:
        CanvasActivity.this.canvasFragment.timer.cancel();
        CanvasActivity.this.canvasFragment.playable = false;
        this.isPushedStopButton = true;
        CanvasActivity.this.canvasFragment.isPause = false;
        break;
      case R.id.menu_pause:
        if (this.isPushedStopButton == false) {
          if (CanvasActivity.this.canvasFragment.isPause == false) {
            if (CanvasActivity.this.canvasFragment.animationTask != null) {
              CanvasActivity.this.canvasFragment.timer.cancel();
              CanvasActivity.this.canvasFragment.isPause = true;
              this.canvasFragment.setStopTime(SystemClock.uptimeMillis());
            }
          }
        }
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }
  
  /**
   * {@inheritDoc}
   */
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
      this.canvasFragment.setSensor();
      if (this.canvasFragment.sensors.size() > 0) {
        this.registerAccerlerometer = this.canvasFragment.sensorManager.registerListener(this.canvasFragment, this.canvasFragment.sensors.get(0), SensorManager.SENSOR_DELAY_UI);
      }
    }
    
    if (!this.registerMagneticFieldSensor) {
      final List<Sensor> sensors = this.canvasFragment.sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
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
   * {@inheritDoc}
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    final MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return true;
  }

  /**
   * This is called after the file manager finished.
   * {@inheritDoc}
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case REQUEST_CODE_PICK_FILE_OR_DIRECTORY:
        if (resultCode == RESULT_OK && data != null) {
          final Uri uri = data.getData();
          loadModelUri(uri);
        }
        break;
      case REQUEST_CODE_PICK_TIME_DATA_FILE:
        if (resultCode == RESULT_OK && data != null) {
          final Uri uri = data.getData();
          loadTimeDataUri(uri);
        }
        break;
      case 3:
        if (resultCode == RESULT_OK && data != null) {
          final Uri uri = data.getData();
          this.ndFragment.unzipSaveFile(uri);
        }
        break;
      default:
        break;
    }
  }

  /**
   * 時間データを読み込みます。
   * 
   * @param uri 時間データのURI
   */
  private void loadTimeDataUri(Uri uri) {
    this.ndFragment.loadDataUri(uri);
    this.canvasFragment.loadtimeSeriesData(this.ndFragment.inputDataFile);
    this.canvasFragment.setTimeDataUri(uri);
  }

  /**
   * モデルデータを読み込みます。
   * 
   * @param uri モデルデータのURI
   */
  private void loadModelUri(Uri uri) {
    this.ndFragment.loadModelUri(uri);

    this.canvasFragment.timeDataUri = null;
    this.canvasFragment.modelRenderer.updateDisplay();
  }

  /**
   * 画面回転時の処理を行います。
   */
  public void controlRotation() {
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

  /**
   * ファイルエクスプローラーにインテントを発行します。
   * 
   * @param requestCode インテント時のリクエストコード
   */
  public void sendFileChooseIntent(int requestCode) {
    //APIレベルによる処理
    //  if (CanvasActivity.this.canvasFragment.root != null) {
    //            CanvasActivity.this.isSelectedModelFile = true;
    //          } else {
    //            CanvasActivity.this.isSelectedModelFile = false;
    //          }
    //          fileManager.getFilePath();
    //        if(Build.VERSION.SDK_INT < 19) {        
    final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("*/*"); //$NON-NLS-1$
    startActivityForResult(intent, requestCode);
    //        } else {
    //        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    //        intent.addCategory(Intent.CATEGORY_OPENABLE);
    //        intent.setType("*/*");
    //        startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
    //    }
  }
}
