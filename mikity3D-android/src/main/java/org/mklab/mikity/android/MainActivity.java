/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import org.mklab.mikity.android.model.SceneGraphTree;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * メインとなるアクティビティクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/01/16
 */
public class MainActivity extends AppCompatActivity {

  final private static int REQUEST_CODE_LOAD_MODEL_DATA_FILE = 0;
  final private static int REQUEST_CODE_LOAD_SOURCE_DATA_FILE = 1;
  final private static int REQUEST_CODE_SAVE_MODEL_DATA_FILE = 2;

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

  /** センサーサービス。 */
  SensorService sensorService;

  /** ソースID。 */
  private String sourceIdForIntent;

  /** モデルデータのURI。 */
  Uri modelFileUri;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    
    createFragments();
    
    createNewModelData();

    showMainMenuFragment();

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
   * 各フラグメントを生成します。
   */
  private void createFragments() {
    final FragmentManager manager = getSupportFragmentManager();
    this.canvasFragment = (CanvasFragment)manager.findFragmentById(R.id.fragment_canvas);
    
    this.mainMenuFragment = new MainMenuFragment();
    this.fileSelectionFragment = new FileSelectionFragment();
    this.sampleSelectionFragment = new SampleSelectionFragment();
    this.settingsFragment = new SettingsFragment();
    this.sceneGraphTreeFragment = new SceneGraphTree();
  }

  /**
   * MainMenuFragmentを表示します。
   */
  private void showMainMenuFragment() {
    final FragmentManager manager = getSupportFragmentManager();
    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.addToBackStack(null);

    transaction.add(R.id.fragment_navigation_drawer, this.mainMenuFragment);
    transaction.commit();
  }

  /**
   * FileSelectionFragmentを表示します。
   */
  void showFileSelectionFragment() {
    final FragmentManager manager = getSupportFragmentManager();
    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.addToBackStack(null);

    transaction.replace(R.id.fragment_navigation_drawer, this.fileSelectionFragment);
    transaction.commit();
  }
  
  /**
   * 新しいモデルデータを生成します。 
   */
  void createNewModelData() {
    if (this.canvasFragment.isModelChanged() == false) {
      this.canvasFragment.createNewModelData();
      this.fileSelectionFragment.reset();
      this.sampleSelectionFragment.reset();
      this.modelFileUri = null;
      return;
    }
    
    final DialogFragment fragment = new DialogFragment() {
      /**
       * {@inheritDoc}
       */
      @Override
      public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.modelIsChanged) + " " + getString(R.string.willYouSave)) //$NON-NLS-1$
        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            if (MainActivity.this.modelFileUri == null) {
              sendFileChooseIntentForSavingModel();
            } else {
              MainActivity.this.saveModelData();
            }
            MainActivity.this.canvasFragment.createNewModelData();
            MainActivity.this.modelFileUri = null;
            MainActivity.this.fileSelectionFragment.reset();
          }
        })
        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            MainActivity.this.canvasFragment.createNewModelData();
            MainActivity.this.modelFileUri = null;
            MainActivity.this.fileSelectionFragment.reset();
          }
        });
        return builder.create();
      }
    };
    
    fragment.show(getSupportFragmentManager(), "confirmDialog"); //$NON-NLS-1$
  }

  /**
   * SampleSelectionFragmentを表示します。
   */
  void showSampleSelectionFragment() {
    final FragmentManager manager = getSupportFragmentManager();
    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.addToBackStack(null);

    transaction.replace(R.id.fragment_navigation_drawer, this.sampleSelectionFragment);
    transaction.commit();
  }

  /**
   * SettingsFragmentを表示します。
   */
  void showSettingsFragment() {
    final FragmentManager manager = getSupportFragmentManager();
    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.addToBackStack(null);

    transaction.replace(R.id.fragment_navigation_drawer, this.settingsFragment);
    transaction.commit();
  }

  /**
   * SceneGraphTreeFragmentを表示します。
   */
  void showSceneGraphTreeFragment() {
    this.sceneGraphTreeFragment.setModel(this.canvasFragment.root.getScene(0));
    this.sceneGraphTreeFragment.setModeler(this.canvasFragment.modeler);
    this.canvasFragment.modeler.setSceneGraphTree(this.sceneGraphTreeFragment);
    
    final FragmentManager manager = getSupportFragmentManager();
    final FragmentTransaction transaction = manager.beginTransaction();
    transaction.addToBackStack(null);

    transaction.replace(R.id.fragment_navigation_drawer, this.sceneGraphTreeFragment);
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
      this.canvasFragment.prepareAnimation();
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
      this.canvasFragment.prepareAnimation();
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
    // スクリーンのサイズ調整が済んでいない場合は調整します。
    if (this.canvasFragment.isInitialScreenSize == false) {
      final GLSurfaceView view = (GLSurfaceView)findViewById(R.id.glview1);
      final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view.getWidth(), view.getHeight());
      view.setLayoutParams(params);
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
  protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
    super.onActivityResult(requestCode, resultCode, resultData);

    switch (requestCode) {
      case REQUEST_CODE_LOAD_MODEL_DATA_FILE:
        if (resultCode == RESULT_OK && resultData != null) {
          final Uri uri = resultData.getData();

          final int takeFlags = resultData.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
          getContentResolver().takePersistableUriPermission(uri, takeFlags);

          loadModelData(uri);
        }
        break;
      case REQUEST_CODE_LOAD_SOURCE_DATA_FILE:
        if (resultCode == RESULT_OK && resultData != null) {
          final Uri uri = resultData.getData();
          loadSourceData(uri, this.sourceIdForIntent);
        }
        break;
      case REQUEST_CODE_SAVE_MODEL_DATA_FILE:
        if (resultCode == RESULT_OK && resultData != null) {
          final Uri uri = resultData.getData();
          saveAsModelData(uri);
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
   * モデルデータをファイルに上書き保存します。
   */
  public void saveModelData() {
    if (this.modelFileUri == null) {
      sendFileChooseIntentForSavingModel();
      return;
    }

    final boolean savedSuccessfully = this.fileSelectionFragment.saveModelData(this.modelFileUri);

    if (savedSuccessfully) {
      Toast toast = Toast.makeText(this, getString(R.string.savedSuccessfully), Toast.LENGTH_LONG);
      toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
      toast.show();
    }
  }
  
  /**
   * モデルデータを名前を付けてファイルに保存します。
   * @param uri モデルデータのURI
   */
  public void saveAsModelData(Uri uri) {
    this.modelFileUri = uri;

    final boolean savedSuccessfully = this.fileSelectionFragment.saveModelData(this.modelFileUri);

    if (savedSuccessfully) {
      Toast toast = Toast.makeText(this, getString(R.string.savedSuccessfully), Toast.LENGTH_LONG);
      toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
      toast.show();
    }
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
   * ファイルエクスプローラーにモデルデータ読み込みのためのインテントを発行します。
   */
  public void sendFileChooseIntentForLoadingModel() {
    final Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.setType("*/*"); //$NON-NLS-1$
    startActivityForResult(intent, REQUEST_CODE_LOAD_MODEL_DATA_FILE);
  }

  /**
   * ファイルエクスプローラーにソースデータ読み込みのためのインテントを発行します。
   * 
   * @param sourceId ソースID
   */
  public void sendFileChooseIntentForLoadingSource(String sourceId) {
    final Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.setType("*/*"); //$NON-NLS-1$
    this.sourceIdForIntent = sourceId;
    startActivityForResult(intent, REQUEST_CODE_LOAD_SOURCE_DATA_FILE);
  }
  
  /**
   * ファイルエクスプローラーにモデルデータ保存のためのインテントを発行します。
   */
  public void sendFileChooseIntentForSavingModel() {
    final Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
    intent.setType("*/*"); //$NON-NLS-1$
    intent.putExtra(Intent.EXTRA_TITLE, "temp.m3d"); //$NON-NLS-1$
    startActivityForResult(intent, REQUEST_CODE_SAVE_MODEL_DATA_FILE);
  }

}
