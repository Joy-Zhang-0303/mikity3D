/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
public class CanvasActivity extends FragmentActivity {

  final static int REQUEST_CODE_PICK_MODEL_DATA_FILE = 0;
  final static int REQUEST_CODE_PICK_SOURCE_DATA_FILE = 1;

  /** NavigationDrawer用のアクションバートグル */
  private ActionBarDrawerToggle drawerToggle;

  /** CanvasFragment */
  CanvasFragment canvasFragment;

  /** NavigationDrawerFragment */
  NavigationDrawerFragment ndFragment;

  /** 停止ボタンが押すことができるならばtrue */
  private boolean isStopButtonPushable;
  
  SensorService sensorService;
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    setContentView(R.layout.canvas);
    
    createCanvasFragment();
    createNavigationDrawerFragment();
    
    final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.layout_activity_canvas);
    this.drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.menu, R.string.open, R.string.close);
    this.drawerToggle.syncState();
    drawerLayout.setDrawerListener(this.drawerToggle);
    
    final ActionBar actionBar = getActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setLogo(getResources().getDrawable(R.drawable.icon));
    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayUseLogoEnabled(true);
    actionBar.setDisplayShowTitleEnabled(false);
    
    this.sensorService = new SensorService((SensorManager)getSystemService(SENSOR_SERVICE), this.canvasFragment);
    
    startIntentByExternalActivity();
  }

  /**
   * CanvasFragmentを生成します。 
   */
  private void createCanvasFragment() {
    final FragmentManager manager = getSupportFragmentManager();
    
    if (manager.findFragmentById(R.id.fragment_canvas) != null) {
      this.canvasFragment = (CanvasFragment)manager.findFragmentById(R.id.fragment_canvas);
      return;
    }
    
    this.canvasFragment = new CanvasFragment();

    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.replace( R.id.fragment_canvas, this.canvasFragment);
    transaction.commit();
  }
  
  /**
   * NavigationDrawerFragmentを生成します。 
   */
  private void createNavigationDrawerFragment() {
    final FragmentManager manager = getSupportFragmentManager();
    
    if (manager.findFragmentById(R.id.fragment_navigation_drawer) != null) {
      this.ndFragment = (NavigationDrawerFragment)manager.findFragmentById(R.id.fragment_navigation_drawer);
      return;
    }
    
    this.ndFragment = new NavigationDrawerFragment();

    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.add( R.id.fragment_navigation_drawer, this.ndFragment);
    transaction.show(this.ndFragment);
    transaction.commit();
  }
  
  /**
   * 外部アクティビティからのモデルパス、データパスを渡されるインテントに対応したメソッドです。
   */
  private void startIntentByExternalActivity() {
    final String intentPath = "path"; //$NON-NLS-1$
    final String intentDataPath = "data"; //$NON-NLS-1$

    final Intent intent = getIntent();
    if (intent.getBundleExtra(intentPath) == null) {
      return;
    }

    final Bundle bundle = intent.getBundleExtra(intentPath);
    if (bundle.getParcelable(intentPath) == null) {
      return;
    }

    final Uri modelPath = bundle.getParcelable(intentPath);
    loadModelData(modelPath);
    if (bundle.getParcelable(intentDataPath) == null) {
      return;
    }
      
    final Uri sourcePath = bundle.getParcelable(intentDataPath);
    loadSourceData(sourcePath);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    this.drawerToggle.syncState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onConfigurationChanged(Configuration configuration) {
    super.onConfigurationChanged(configuration);
    this.drawerToggle.onConfigurationChanged(configuration);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (this.drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }

    switch (item.getItemId()) {
      case R.id.menu_play:
        if (this.canvasFragment.playable) {
          this.isStopButtonPushable = true;
        }
        this.canvasFragment.runAnimation();
        break;
      case R.id.menu_stop:
        if (this.isStopButtonPushable) {
          this.isStopButtonPushable = false;
          this.canvasFragment.stopAnimation();
        }
        break;
      case R.id.menu_pause:
        if (this.isStopButtonPushable) {
          this.isStopButtonPushable = false;
          this.canvasFragment.pauseAnimation();
        }
        break;
      case R.id.menu_repeat:
        if (this.canvasFragment.playable) {
          this.isStopButtonPushable = true;
        }
        this.canvasFragment.repeatAnimation();
        break;
      case R.id.menu_reset_to_initial_state:
        this.canvasFragment.resetToInitialState();
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
    if (this.canvasFragment.isInitialScreenSize == false) {
      final GLSurfaceView glSurfaceView = (GLSurfaceView)findViewById(R.id.glview1);
      final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(glSurfaceView.getWidth(), glSurfaceView.getHeight());
      glSurfaceView.setLayoutParams(params);
    }
    super.onWindowFocusChanged(hasFocus);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onResume() {
    this.sensorService.registerSensorListener();

    this.canvasFragment.glView.onResume();
    super.onResume();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPause() {
    this.sensorService.unregisterSensorListener();
    
    this.canvasFragment.glView.onPause();
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
   * This is called after the file manager finished. {@inheritDoc}
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case REQUEST_CODE_PICK_MODEL_DATA_FILE:
        if (resultCode == RESULT_OK && data != null) {
          final Uri uri = data.getData();
          loadModelData(uri);
        }
        break;
      case REQUEST_CODE_PICK_SOURCE_DATA_FILE:
        if (resultCode == RESULT_OK && data != null) {
          final Uri uri = data.getData();
          loadSourceData(uri);
        }
        break;
      case 3:
        if (resultCode == RESULT_OK && data != null) {
          final Uri uri = data.getData();
          this.ndFragment.unzipFile(uri);
        }
        break;
      default:
        break;
    }
  }

  /**
   * 時間データを読み込みます。
   * 
   * @param path 時間データのパス
   */
  private void loadSourceData(Uri path) {
    this.ndFragment.loadSourceData(path);
  }

  /**
   * モデルデータを読み込みます。
   * 
   * @param path モデルデータのパス
   */
  private void loadModelData(Uri path) {
    this.ndFragment.loadModelData(path);

    this.ndFragment.sourceId = null;
    this.canvasFragment.objectRenderer.updateDisplay();
  }

  /**
   * 画面回転時の処理を行います。
   */
  public void controlRotation() {
    final Configuration configuration = getResources().getConfiguration();
    final int rotation = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    boolean isReverse = false;
    
    if (this.ndFragment.rotationLockButton.isChecked()) {
      switch (rotation) {
        case Surface.ROTATION_180:
        case Surface.ROTATION_270:
          isReverse = true;
          break;
        default:
          isReverse = false;
          break;
      }

      if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
      CanvasActivity.this.canvasFragment.objectRenderer.updateDisplay();
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
    final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("*/*"); //$NON-NLS-1$
    startActivityForResult(intent, requestCode);
  }
}
