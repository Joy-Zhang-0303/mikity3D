/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import org.mklab.mikity.android.model.SceneGraphTree;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
public class MainActivity extends AppCompatActivity {

  final static int REQUEST_CODE_PICK_MODEL_DATA_FILE = 0;
  final static int REQUEST_CODE_PICK_SOURCE_DATA_FILE = 1;

  /** NavigationDrawer用のアクションバートグル */
  private ActionBarDrawerToggle drawerToggle;

  /** CanvasFragment */
  CanvasFragment canvasFragment;

  /** SettingsFragment */
  SettingsFragment settingsFragment;

  /** MainMenuFragment */
  MainMenuFragment mainMenuFragment;

  /** FileSelectionFragment */
  FileSelectionFragment fileSelectionFragment;

  /** SampleSelectionFragment */
  SampleSelectionFragment sampleSelectionFragment;

  /** SceneGraphTree */
  SceneGraphTree sceneGraphTreeFragment;

  /** 停止ボタンが押すことができるならばtrue */
  private boolean isStopButtonPushable;

  SensorService sensorService;

  /** ソースID。 */
  private String sourceIdForIntent;

  /** モデルデータのURI。 */
  private Uri modelFileUri;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    createCanvasFragment();

    createMainMenuFragment();

    final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.layout_drawer_layout);
    this.drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
    this.drawerToggle.syncState();
    drawerLayout.setDrawerListener(this.drawerToggle);

    final ActionBar actionBar = getSupportActionBar();
    actionBar.setLogo(R.drawable.icon);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setDisplayUseLogoEnabled(true);
    actionBar.setDisplayShowTitleEnabled(false);

    this.sensorService = new SensorService((SensorManager)getSystemService(SENSOR_SERVICE), this.canvasFragment);

    startIntentByExternalActivity();

    // ステータスバーを消す
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
  }

  /**
   * MainMenuFragmentを生成します。
   */
  private void createMainMenuFragment() {
    final FragmentManager manager = getSupportFragmentManager();
    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.addToBackStack(null);

    if (this.mainMenuFragment != null) {
      transaction.remove(this.mainMenuFragment);
      this.mainMenuFragment = null;
    }

    this.mainMenuFragment = new MainMenuFragment();
    transaction.add(R.id.fragment_navigation_drawer, this.mainMenuFragment);
    transaction.commit();
  }

  /**
   * CanvasFragmentを生成します。
   */
  private void createCanvasFragment() {
    final FragmentManager manager = getSupportFragmentManager();
    this.canvasFragment = (CanvasFragment)manager.findFragmentById(R.id.fragment_canvas);
  }

  /**
   * FileSelectionFragmentを生成します。
   */
  void createFileSelectionFragment() {
    final FragmentManager manager = getSupportFragmentManager();
    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.addToBackStack(null);

    if (this.fileSelectionFragment == null) {
      this.fileSelectionFragment = new FileSelectionFragment();
    }

    transaction.replace(R.id.fragment_navigation_drawer, this.fileSelectionFragment);
    transaction.commit();
  }

  /**
   * SampleSelectionFragmentを生成します。
   */
  void createSampleSelectionFragment() {
    final FragmentManager manager = getSupportFragmentManager();
    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.addToBackStack(null);

    if (this.sampleSelectionFragment == null) {
      this.sampleSelectionFragment = new SampleSelectionFragment();
    }

    transaction.replace(R.id.fragment_navigation_drawer, this.sampleSelectionFragment);
    transaction.commit();
  }

  /**
   * SettingsFragmentを生成します。
   */
  void createSettingsFragment() {
    final FragmentManager manager = getSupportFragmentManager();
    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.addToBackStack(null);

    if (this.settingsFragment == null) {
      this.settingsFragment = new SettingsFragment();
    }

    transaction.replace(R.id.fragment_navigation_drawer, this.settingsFragment);
    transaction.commit();
  }

  /**
   * SceneGraphTreeFragmentを生成します。
   */
  void createSceneGraphTreeFragment() {
    if (this.canvasFragment.root == null) {
      return;
    }

    final FragmentManager manager = getSupportFragmentManager();
    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.addToBackStack(null);

    if (this.sceneGraphTreeFragment == null) {
      this.sceneGraphTreeFragment = new SceneGraphTree();
    }

    this.sceneGraphTreeFragment.setModel(this.canvasFragment.root.getScene(0));

    transaction.replace(R.id.fragment_navigation_drawer, this.sceneGraphTreeFragment);
    transaction.commit();

    this.canvasFragment.modeler.setSceneGraphTree(this.sceneGraphTreeFragment);
    this.sceneGraphTreeFragment.setModeler(this.canvasFragment.modeler);
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
    final String sourceId = intent.getStringExtra("sourceId"); //$NON-NLS-1$
    loadSourceData(sourcePath, sourceId);
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

    if (item.getItemId() == R.id.menu_play) {
      if (this.canvasFragment.playable) {
        this.isStopButtonPushable = true;
      }
      this.canvasFragment.runAnimation();
    } else if (item.getItemId() == R.id.menu_stop) {
      if (this.isStopButtonPushable) {
        this.isStopButtonPushable = false;
        this.canvasFragment.stopAnimation();
      }
    } else if (item.getItemId() == R.id.menu_pause) {
      if (this.isStopButtonPushable) {
        this.isStopButtonPushable = false;
        this.canvasFragment.pauseAnimation();
      }
    } else if (item.getItemId() == R.id.menu_repeat) {
      if (this.canvasFragment.playable) {
        this.isStopButtonPushable = true;
      }
      this.canvasFragment.repeatAnimation();
    } else if (item.getItemId() == R.id.menu_reset_to_initial_state) {
      this.canvasFragment.resetToInitialState();
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
    super.onCreateOptionsMenu(menu);
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
          
          final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
          getContentResolver().takePersistableUriPermission(uri, takeFlags);

          loadModelData(uri);
        }
        break;
      case REQUEST_CODE_PICK_SOURCE_DATA_FILE:
        if (resultCode == RESULT_OK && data != null) {
          final Uri uri = data.getData();
          loadSourceData(uri, this.sourceIdForIntent);
        }
        break;
      default:
        break;
    }
  }

  /**
   * ソースデータを読み込みます。
   * 
   * @param uri ソースデータのパス
   * @param sourceId ソースID
   */
  private void loadSourceData(Uri uri, String sourceId) {
    this.fileSelectionFragment.loadSourceData(uri, sourceId);
  }

  /**
   * モデルデータを読み込みます。
   * 
   * @param uri モデルデータのURI
   */
  private void loadModelData(Uri uri) {
    this.fileSelectionFragment.loadModelData(uri);
    this.canvasFragment.objectRenderer.updateDisplay();

    this.modelFileUri = uri;
  }

  /**
   * モデルデータをを保存します。
   */
  public void saveModelData() {
    this.fileSelectionFragment.saveModelData(this.modelFileUri);
  }

  /**
   * 画面回転時の処理を行います。
   */
  public void controlRotation() {
    final Configuration configuration = getResources().getConfiguration();
    final int rotation = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    boolean isReverse = false;

    if (this.settingsFragment.rotationLockButton.isChecked()) {
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
      MainActivity.this.canvasFragment.objectRenderer.updateDisplay();
    } else {
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    }
  }

  /**
   * ファイルエクスプローラーにモデルデータのためのインテントを発行します。
   * 
   * @param requestCode インテント時のリクエストコード
   */
  public void sendFileChooseIntentForModel(int requestCode) {
    //final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    final Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.setType("*/*"); //$NON-NLS-1$
    startActivityForResult(intent, requestCode);
  }

  /**
   * ファイルエクスプローラーにソースデータのためのインテントを発行します。
   * 
   * @param requestCode インテント時のリクエストコード
   * @param sourceId ソースID
   */
  public void sendFileChooseIntentForSource(int requestCode, String sourceId) {
    //final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    final Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.setType("*/*"); //$NON-NLS-1$
    this.sourceIdForIntent = sourceId;
    startActivityForResult(intent, requestCode);
  }
}
