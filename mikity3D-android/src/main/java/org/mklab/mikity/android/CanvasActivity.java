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
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.LinearLayout;


/**
 * @author soda
 * @version $Revision$, 2015/01/16
 * Mikity3dのメインとなるアクティビティクラスです。
 */
public class CanvasActivity extends RoboFragmentActivity {

  final static int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 0;
  final static int REQUEST_CODE_PICK_TIME_DATA_FILE = 1;
  private boolean registerAccerlerometer;
  private boolean registerMagneticFieldSensor;
  /** NavigationDrawer用のアクションバートグル*/
  private ActionBarDrawerToggle mDrawerToggle;
  /** NavigationDrawerのレイアウト*/
  private DrawerLayout mDrawer;
  /** アクティビティのconfiguration*/
  private Configuration config;
  /** CanvasFragment*/
  @InjectFragment(R.id.fragment_canvas)
  CanvasFragment canvasFragment;
  /** NavigationDrawerFragment*/
  public NavigationDrawerFragment ndFragment;
  /** ModelColumnNunberFragment*/
  public ModelColumnNumberFragment mcnFragment;

  /**
   * @see roboguice.activity.RoboFragmentActivity#onCreate(android.os.Bundle)
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
    Intent i = getIntent();
    Bundle bundle;
    final String intentPath = "path"; //$NON-NLS-1$
    final String intentDataPath = "data"; //$NON-NLS-1$
    if(i.getBundleExtra(intentPath) != null) {
      bundle = i.getBundleExtra(intentPath);
      if(bundle.getParcelable(intentPath) != null) {
        Uri mPath = bundle.getParcelable(intentPath);
        loadModelUri(mPath);
      }
      if(bundle.getParcelable(intentDataPath) != null) {
        Uri dPath = bundle.getParcelable(intentDataPath);
        loadTimeDataUri(dPath);
      }
    }
  }

  
  /**
   * @see android.app.Activity#onPostCreate(android.os.Bundle)
   */
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    this.mDrawerToggle.syncState();
  }

  /**
   * @see roboguice.activity.RoboFragmentActivity#onConfigurationChanged(android.content.res.Configuration)
   */
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    this.mDrawerToggle.onConfigurationChanged(newConfig);
  }

  /**
   * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
   */
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
      this.canvasFragment.setSensor();
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
          loadTimeDataUri(uri);
        }
        break;
      default:
        break;
    }
  }

  /**
   * 時間データを読み込むためのメソッドです。
   * @param uri 時間データのURI
   */
  private void loadTimeDataUri(Uri uri) {
    this.ndFragment.loadDataUri(uri);
    this.canvasFragment.loadtimeSeriesData(this.ndFragment.inputDataFile);
    this.canvasFragment.setTimeDataUri(uri);
  }

  /**
   * モデルデータを読み込むためのメソッドです。
   * @param uri モデルデータのURI
   */
  private void loadModelUri(Uri uri) {
    this.ndFragment.loadModelUri(uri);
    
    this.canvasFragment.timeDataUri = null;
    this.canvasFragment.modelRenderer.updateDisplay();
  }
  
  /**
   * 画面回転時の処理をするメソッドです。
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
   * ファイルエクスプローラーにインテントを発行するメソッドです。
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
  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
  intent.setType("*/*"); //$NON-NLS-1$
  startActivityForResult(intent, requestCode);
//        } else {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("*/*");
//        startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
//    }
  }
  
  protected void setMCNFragmnet(ModelColumnNumberFragment mcnFragment) {
	this.ndFragment.setGroupNameList(this.canvasFragment.root);
    this.mcnFragment = mcnFragment;
  }
}
