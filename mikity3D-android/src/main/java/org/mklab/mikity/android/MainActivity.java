//package org.mklab.mikity.android;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//
//import org.mklab.mikity.android.view.renderer.OpenglesModelRenderer;
//import org.mklab.mikity.control.AnimationTaskListener;
//import org.mklab.mikity.android.control.AnimationTask;
//import org.mklab.mikity.model.MovableGroupManager;
//import org.mklab.mikity.model.xml.Mikity3dFactory;
//import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
//import org.mklab.mikity.model.xml.simplexml.Mikity3d;
//import org.mklab.mikity.model.xml.simplexml.Mikity3dConfiguration;
//import org.mklab.mikity.model.xml.simplexml.model.Group;
//import org.mklab.mikity.model.xml.simplexml.model.LinkData;
//import org.mklab.nfc.matrix.Matrix;
//import org.mklab.nfc.matx.MatxMatrix;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.opengl.GLSurfaceView;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.SystemClock;
//import android.support.v4.app.ActionBarDrawerToggle;
//import android.support.v4.widget.DrawerLayout;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.ScaleGestureDetector;
//import android.view.View;
//import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//import org.openintents.intents.*;
//
//
///**
// * @author ohashi
// * @version $Revision$, 2013/08/23
// */
//public class MainActivity extends Activity implements SensorEventListener {
//
//  // private static String TAG = "3Dmikity-android";
//
//  protected static final String LOGTAG = null;
//  private boolean mIsInitScreenSize;
//  GLSurfaceView glView;
//
//  /** アニメーション用タスク */
//  AnimationTask animationTask;
//
//  /** */
//  public static boolean playable = true;
//
//  /** */
//  private double endTime;
//
//  private Mikity3d root;
//
//  /** 等間隔の時間を保存しとく配列 */
//  double[] timeTable;
//
//  Timer timer = new Timer();
//
//  private Matrix data;
//
//  /** */
//  MovableGroupManager manager;
//
//  /** ModelCanvas */
//  private OpenglesModelRenderer modelRenderer;
//
//  // DEBUG textview
//  TextView testTextView;
//  TextView filePathView;
//  TextView modelFilePathView;
//
//  /** 前回のタッチのｘ座標 */
//  float prevX = 0;
//  /** 前回のタッチのｙ座標 */
//  float prevY = 0;
//  /** アニメーションの再生速度 丸め誤差を防ぐために１０で割る必要があります。 */
//  private int animationSpeed = 10;
//
//  private final int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 0;
//  /** 時系列データのファイルパス */
//  private String filePath;
//  /** 　ピンチイン、ピンチアウト用のジェスチャー */
//  private ScaleGestureDetector gesDetect = null;
//  /** スケーリング中かどうかのフラグ */
//  private boolean scaling;
//  /** 回転中かどうかのフラグ */
//  
//  
//  private boolean rotationing;
//  /** 3Dオブジェクトの大きさの値 */
//  private double scaleValue = 1;
//  /** アニメーションスピードを表示するエディットテキスト */
//  private EditText animationSpeedTextEdit;
//  /** 3Dモデルのインプットストリーム */
//  private InputStream inputModelFile;
//  /** 3Dモデルが選ばれて表示されたかどうかのフラグ */
//  protected boolean isSelectedModelFile;
//  /** モデルを参照するときに押されるボタン */
//  private Button selectButton;
//  /** アニメーションスピードを早くするときに押されるボタン */
//  private Button quickButton;
//  /** アニメーションスピードを遅くするときに押されるボタン */
//  private Button slowButton;
//  /** アニメーションを停止するときに押されるボタン */
//  private Button stopButton;
//  /** モデルファイル読み込むときに押されるボタン */
//  private Button loadModelButton;
//  /** アニメーションを再生するボタン */
//  private Button playButton;
//  /** センサーマネージャー */
//  private SensorManager sensorManager;
//  private boolean registerAccerlerometer;
//  private boolean registerMagneticFieldSensor;
//  /** センサーからの加速度を格納する配列 */
//  private float[] accels = new float[3];
//  /** センサーからの地磁気を格納する配列 */
//  private float[] magneticFields = new float[3];
//  /** センサーから算出した端末の角度を格納する配列 */
//  private float[] orientations = new float[3];
//  /** 角度の基準値 */
//  private float[] prevOrientations = new float[3];
//  /** 端末の角度を3Dオブジェクトに反映させるかどうかのトグル */
//  private ToggleButton gyroToggleButton;
//  /** 端末の角度を3Dオブジェクトに反映させるかどうかのフラグ */
//  private boolean useOrientationSensor = false;
//  /** */
//  private float[] prevAccerlerometer = new float[3];
//  /** 加速度を3Dオブジェクトに反映させるかどうかのトグル */
//  private ToggleButton accelerToggleButton;
//  /** 加速度のローパスフィルターのxの値 */
//  private double lowPassX;
//  /** 加速度のローパスフィルターのｙの値 */
//  private double lowPassY;
//  /** 加速度のローパスフィルターのzの値 */
//  private double lowPassZ = 9.45;
//  /** 加速度のハイパスフィルターのZの値 */
//  private double rawAz;
//  /** 前回加速度センサーを3Dオブジェクトに使用したときの時間 */
//  private long useAccelerOldTime = 0L;
//  /** 加速度センサーの値を3Dオブジェクトに反映させるかどうか*/
//  private boolean useAccelerSensor = false;
//
//  private ActionBarDrawerToggle mDrawerToggle;
//  private DrawerLayout mDrawer;
//
//  /**
//   * @param modelFile モデルファイル
//   * @throws IOException ファイルを読み込めない場合
//   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
//   */
//  private void loadModelFile(File modelFile) throws IOException, Mikity3dSerializeDeserializeException {
//    this.root = new Mikity3dFactory().loadFile(modelFile);
//    this.manager = new MovableGroupManager(this.root);
//    this.modelRenderer = new OpenglesModelRenderer(this.glView);
//  }
//
//  /**
//   * 
//   * @param modelFile モデルファイル
//   * @throws IOException ファイルを読み込めない場合
//   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
//   */
//  private void loadModelFile(InputStream input) throws IOException, Mikity3dSerializeDeserializeException {
//    this.root = new Mikity3dFactory().loadFile(input);
//    this.manager = new MovableGroupManager(this.root);
//    Group[] children = this.root.getModel(0).getGroups();
//    this.modelRenderer.setChildren(children);
//    Mikity3dConfiguration configuration = this.root.getConfiguration(0);
//    this.modelRenderer.setConfiguration(configuration);
//
//    this.manager.setLogCat(new LogCatImpl()); //LogCatのセット
//
//  }
//
//  /**
//   * @throws FileNotFoundException
//   * @throws IOException
//   */
//  private void loadTimeData() throws FileNotFoundException, IOException {
//    InputStream mat1 = new FileInputStream(this.filePath);
//    setTimeData(mat1);
//    mat1.close();
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.main);
//
//    // GLViewを取り出す
//    this.glView = (GLSurfaceView)this.findViewById(R.id.glview1);
//
//    this.getResources();
//
//    this.sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
//    this.registerAccerlerometer = false;
//    this.registerMagneticFieldSensor = false;
//    for (int i = 0; i < 3; i++) {
//      this.accels[i] = 0.0f;
//      this.magneticFields[i] = 0.0f;
//      this.orientations[i] = 0.0f;
//      this.prevOrientations[i] = 0.0f;
//      this.prevAccerlerometer[i] = 0.0f;
//
//    }
//
//    //this.inputModelFile = res.openRawResource(R.raw.pendulum);
//    final OIFileManager fileManager = new OIFileManager(this);
//    this.modelRenderer = new OpenglesModelRenderer(this.glView);
//
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
//      /**
//       * {@inheritDoc}
//       */
//      public void onClick(View v) {
//        MainActivity.this.isSelectedModelFile = false;
//        fileManager.getFilePath();
//      }
//    });
//
//    // 描画のクラスを登録する
//    this.glView.setRenderer(this.modelRenderer);
//    this.mIsInitScreenSize = false;
//
//    // 任意のタイミングで再描画する設定
//    this.glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
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
//    // ScaleGestureDetecotorクラスのインスタンス生成
//    this.gesDetect = new ScaleGestureDetector(this, this.onScaleGestureListener);
//
//    //再生速度の設定
//    this.quickButton.setOnClickListener(new View.OnClickListener() {
//      /**
//       * {@inheritDoc}
//       */
//      public void onClick(View v) {
//        MainActivity.this.animationSpeed = (int)(Double.parseDouble(MainActivity.this.animationSpeedTextEdit.getText().toString()) * 10);
//        MainActivity.this.animationSpeed += 1;
//        MainActivity.this.animationSpeedTextEdit.setText("" + (double)MainActivity.this.animationSpeed / 10); //$NON-NLS-1$
//        if (MainActivity.this.animationTask != null) MainActivity.this.animationTask.setSpeedScale(MainActivity.this.animationSpeed / 10);
//      }
//    });
//
//    this.slowButton.setOnClickListener(new View.OnClickListener() {
//      /**
//       * {@inheritDoc}
//       */
//      public void onClick(View v) {
//        MainActivity.this.animationSpeed = (int)(Double.parseDouble(MainActivity.this.animationSpeedTextEdit.getText().toString()) * 10);
//        MainActivity.this.animationSpeed -= 1;
//        if (MainActivity.this.animationSpeed < 0) MainActivity.this.animationSpeed = 0;
//        MainActivity.this.animationSpeedTextEdit.setText(Double.toString((double)MainActivity.this.animationSpeed / 10));
//        if (MainActivity.this.animationTask != null) MainActivity.this.animationTask.setSpeedScale(MainActivity.this.animationSpeed / 10);
//      }
//    });
//
//    this.selectButton.setOnClickListener(new View.OnClickListener() {
//
//      public void onClick(View v) {
//        fileManager.getFilePath();
//
//      }
//    });
//
//    // イベントリスナー
//    this.playButton.setOnClickListener(new View.OnClickListener() {
//      // コールバックメソッド
//      public void onClick(View view) {
//        runAnimation();
//      }
//    });
//
//    this.stopButton.setOnClickListener(new View.OnClickListener() {
//      // コールバックメソッド
//      public void onClick(View view) {
//        MainActivity.this.timer.cancel();
//        playable = true;
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
//        if (MainActivity.this.gyroToggleButton.isChecked()) {
//          MainActivity.this.useOrientationSensor = true;
//        } else MainActivity.this.useOrientationSensor = false;
//
//      }
//    });
//
//    this.accelerToggleButton = (ToggleButton)findViewById(R.id.accelerToggleButton);
//    this.accelerToggleButton.setOnClickListener(new OnClickListener() {
//
//      /**
//       * {@inheritDoc}
//       */
//      public void onClick(View v) {
//        if (MainActivity.this.accelerToggleButton.isChecked()) {
//          MainActivity.this.useAccelerSensor = true;
//          
//        } else {
//          MainActivity.this.useAccelerSensor = false;
//          
//
//        }
//      }
//
//    });
//
//    //外部アプリからの起動
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
//        loadModelFile(this.inputModelFile);
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
//      setTimeData(mat);
//
//      try {
//        mat.close();
//      } catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//    }
//
////    ((Button)findViewById(R.id.drawer_button)).setOnClickListener((OnClickListener)this);
//    
//    this.mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//    this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawer,
//            R.drawable.icon, R.string.drawer_open,
//            R.string.drawer_close) {
//        @Override
//        public void onDrawerClosed(View drawerView) {
//            Log.i(LOGTAG, "onDrawerClosed");
//        }
// 
//        @Override
//        public void onDrawerOpened(View drawerView) {
//            Log.i(LOGTAG, "onDrawerOpened");
//        }
// 
//        @Override
//        public void onDrawerSlide(View drawerView, float slideOffset) {
//            super.onDrawerSlide(drawerView, slideOffset);
//            Log.i(LOGTAG, "onDrawerSlide : " + slideOffset);
//        }
// 
//        @Override
//        public void onDrawerStateChanged(int newState) {
//            Log.i(LOGTAG, "onDrawerStateChanged  new state : " + newState);
//        }
//    };
//      this.mDrawer.setDrawerListener(this.mDrawerToggle);
//    getActionBar().setDisplayHomeAsUpEnabled(true);
//    getActionBar().setHomeButtonEnabled(true);
//    
//  }
//  
//  @Override
//  protected void onPostCreate(Bundle savedInstanceState) {
//      super.onPostCreate(savedInstanceState);
//      mDrawerToggle.syncState();
//  }
//   
//  @Override
//  public void onConfigurationChanged(Configuration newConfig) {
//      super.onConfigurationChanged(newConfig);
//      mDrawerToggle.onConfigurationChanged(newConfig);
//  }
//   
//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//   
//      // ActionBarDrawerToggleにandroid.id.home(up ナビゲーション)を渡す。
//      if (mDrawerToggle.onOptionsItemSelected(item)) {
//          return true;
//      }
//   
//      return super.onOptionsItemSelected(item);
//  }
//   
//  public void onClick(View v) {
//      mDrawer.closeDrawers();
//  }
//  
//  /** 表示されるときに呼ばれる */
//  @Override
//  public void onWindowFocusChanged(boolean hasFocus) {
//
//    // スクリーンサイズ調整が済んでいない場合は調整する
//    if (this.mIsInitScreenSize == false) {
//      Resources resources = getResources();
//      Configuration config = resources.getConfiguration();
//      int size;
//      switch (config.orientation) {
//        case Configuration.ORIENTATION_PORTRAIT:
//          size = this.glView.getWidth();
//          break;
//        case Configuration.ORIENTATION_LANDSCAPE:
//          size = this.glView.getHeight();
//          break;
//        default:
//          throw new IllegalStateException("It is not a portrait or landscape"); //$NON-NLS-1$
//      }
//      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(this.glView.getWidth()-1, this.glView.getHeight());
//      //TODO drawerLayoutを実装した状態かつ画面を横向きにした状態では、横幅いっぱいにビューを広げるとなぜかうまく描画されません。
//      //TODO そのための対策として、今はgetWidth()-1としています。
//      this.glView.setLayoutParams(params);
//    }
//    super.onWindowFocusChanged(hasFocus);
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public void onResume() {
//    if (!this.registerAccerlerometer) {
//      List<Sensor> sensors = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
//      if (sensors.size() > 0) {
//        this.registerAccerlerometer = this.sensorManager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_UI);
//      }
//    }
//    if (!this.registerMagneticFieldSensor) {
//      List<Sensor> sensors = this.sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
//      if (sensors.size() > 0) {
//        this.registerMagneticFieldSensor = this.sensorManager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_UI);
//      }
//    }
//
//    this.glView.onResume();
//    super.onResume();
//
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public void onPause() {
//
//    this.glView.onPause();
//    if (this.registerAccerlerometer || this.registerMagneticFieldSensor) {
//      this.sensorManager.unregisterListener(this);
//
//      this.registerAccerlerometer = false;
//      this.registerMagneticFieldSensor = false;
//    }
//
//    super.onPause();
//
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    // Inflate the menu; this adds items to the action bar if it is present.
//    // getMenuInflater().inflate(R.menu.main,menu);
//    return true;
//  }
//
//  /**
//   * 実行時間バーを設定する。
//   * 
//   * @param input 時系列データのインプットストリーム
//   * 
//   */
//  public void setTimeData(final InputStream input) {
//    try {
//      this.data = MatxMatrix.readMatFormat(new InputStreamReader(input));
//      input.close();
//
//      this.manager.setData(this.data);
//
//      final Group rootGroup = this.root.getModel(0).getGroup(0);
//      checkLinkParameterType(rootGroup);
//
//      final int dataSize = this.manager.getDataSize();
//
//      this.timeTable = new double[dataSize];
//
//      this.endTime = this.manager.getEndTime();
//      for (int i = 0; i < this.timeTable.length; i++) {
//        this.timeTable[i] = this.endTime * ((double)i / this.timeTable.length);
//      }
//
//    } catch (FileNotFoundException e) {
//
//      throw new RuntimeException(e);
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//
//  }
//
//  private void checkLinkParameterType(Group parent) {
//    final Group[] groups = parent.getGroups();
//    for (final Group group : groups) {
//      final LinkData[] links = group.getLinkData();
//      for (final LinkData link : links) {
//        if (link.hasDHParameter()) {
//          this.manager.setHasDHParameter(true);
//        } else if (link.hasCoordinateParameter()) {
//          this.manager.setHasCoordinateParameter(true);
//        }
//      }
//
//      checkLinkParameterType(group);
//    }
//  }
//
//  /**
//   * アニメーションを開始します。
//   */
//  /**
//   * 
//   */
//  public void runAnimation() {
//    long startTime = SystemClock.uptimeMillis();
//
//    this.animationSpeed = (int)(Double.parseDouble(MainActivity.this.animationSpeedTextEdit.getText().toString()) * 10);
//    if (playable == false) {
//      this.timer.cancel();
//    }
//
//    if (this.data == null || this.timeTable == null) {
//      return;
//    }
//
//    playable = false;
//
//    this.endTime = this.manager.getEndTime();
//    this.animationTask = new AnimationTask(startTime, this.endTime, this.manager, this.modelRenderer);
//    this.animationTask.setSpeedScale((double)this.animationSpeed / 10);
//    this.animationTask.addAnimationTaskListener(new AnimationTaskListener() {
//
//      /**
//       * {@inheritDoc}
//       */
//      public void tearDownAnimation() {
//        playable = true;
//      }
//
//      /**
//       * {@inheritDoc}
//       */
//      public void setUpAnimation() {
//        // nothing to do
//      }
//    });
//
//    this.timer = new Timer();
//    this.timer.schedule(this.animationTask, 0, 30);
//  }
//
//  /**
//   * This is called after the file manager finished.
//   */
//  @Override
//  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//
//    switch (requestCode) {
//      case REQUEST_CODE_PICK_FILE_OR_DIRECTORY:
//        if (resultCode == RESULT_OK && data != null) {
//          // obtain the filename
//          if (this.isSelectedModelFile) {
//            String timeDataPath = data.getData().getPath();
//            this.filePathView.setText(new File(timeDataPath).getName());
//
//            loadtimeSeriesData(timeDataPath);
//
//          } else {
//            String modelFilePath = data.getData().getPath();
//            try {
//              this.inputModelFile = new FileInputStream(modelFilePath);
//              this.modelFilePathView.setText(new File(modelFilePath).getName());
//
//            } catch (FileNotFoundException e) {
//              throw new RuntimeException(e);
//            }
//
//            try {
//              loadModelFile(this.inputModelFile);
//            } catch (IOException e) {
//              throw new RuntimeException(e);
//            } catch (Mikity3dSerializeDeserializeException e) {
//              throw new RuntimeException(e);
//            }
//            this.isSelectedModelFile = true;
//
//            this.selectButton.setEnabled(true);
//            this.quickButton.setEnabled(true);
//            this.slowButton.setEnabled(true);
//            this.playButton.setEnabled(true);
//            this.stopButton.setEnabled(true);
//            this.modelRenderer.updateDisplay();
//          }
//        }
//
//        break;
//    }
//  }
//
//  private void loadtimeSeriesData(final String filePath) {
//    AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
//
//      ProgressDialog progressDialog;
//
//      @Override
//      protected void onPreExecute() {
//        this.progressDialog = new ProgressDialog(MainActivity.this);
//        this.progressDialog.setCanceledOnTouchOutside(false);
//        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        this.progressDialog.setMessage("Now Loading..."); //$NON-NLS-1$
//        this.progressDialog.show();
//      }
//
//      @Override
//      protected Void doInBackground(String... arg0) {
//        InputStream mat1;
//        try {
//          mat1 = new FileInputStream(filePath);
//        } catch (FileNotFoundException e) {
//          throw new RuntimeException(e);
//        }
//        setTimeData(mat1);
//        try {
//          mat1.close();
//        } catch (IOException e) {
//          throw new RuntimeException(e);
//        }
//        return null;
//      }
//
//      @Override
//      protected void onPostExecute(Void result) {
//        this.progressDialog.dismiss();
//
//      }
//
//    };
//    task.execute();
//  }
//
//  // スケールジェスチャーイベントを取得
//  private final SimpleOnScaleGestureListener onScaleGestureListener = new SimpleOnScaleGestureListener() {
//
//    @Override
//    public boolean onScale(ScaleGestureDetector detector) {
//      MainActivity.this.rotationing = false;
//      MainActivity.this.scaling = true;
//      if (MainActivity.this.scaleValue - (1.0 - MainActivity.this.gesDetect.getScaleFactor()) > 0.2) {
//        MainActivity.this.modelRenderer.setScale((float)(MainActivity.this.scaleValue - (1.0f - MainActivity.this.gesDetect.getScaleFactor())));
//      }
//      MainActivity.this.prevX = MainActivity.this.gesDetect.getFocusX();
//      MainActivity.this.prevY = MainActivity.this.gesDetect.getFocusY();
//
//      return super.onScale(detector);
//    }
//
//    @Override
//    public boolean onScaleBegin(ScaleGestureDetector detector) {
//
//      MainActivity.this.scaling = true;
//
//      // MainActivity.this.testTextView.setText(Double.toString(MainActivity.this.scaleValue));
//      return super.onScaleBegin(detector);
//    }
//
//    @Override
//    public void onScaleEnd(ScaleGestureDetector detector) {
//
//      MainActivity.this.scaling = false;
//      setScaleValue(MainActivity.this.scaleValue - (1.0 - MainActivity.this.gesDetect.getScaleFactor()));
//      MainActivity.this.prevX = MainActivity.this.gesDetect.getFocusX();
//      MainActivity.this.prevY = MainActivity.this.gesDetect.getFocusY();
//
//      super.onScaleEnd(detector);
//    }
//  };
//
//  /**
//   * {@inheritDoc}
//   */
//  // タッチ操作の種類によってイベントを取得する
//  @Override
//  public boolean onTouchEvent(MotionEvent event) {
//    float transferAmountX;
//    float transferAmountY;
//    int touchCount = event.getPointerCount();
//    // タッチイベントをScaleGestureDetector#onTouchEventメソッドに
//    this.gesDetect.onTouchEvent(event);
//
//    switch (event.getAction()) {
//    // タッチした
//      case MotionEvent.ACTION_DOWN:
//        this.rotationing = true;
//        this.prevX = event.getX();
//        this.prevY = event.getY();
//        break;
//
//      // タッチしたまま移動
//      case MotionEvent.ACTION_MOVE:
//        transferAmountX = event.getX() - this.prevX;
//        transferAmountY = event.getY() - this.prevY;
//        this.prevX = event.getX();
//        this.prevY = event.getY();
//
//       
//
//        if ((this.rotationing) && (touchCount == 1)) {
//          this.modelRenderer.setRotation(transferAmountX, transferAmountY);
//        }
//        if ((touchCount == 2) && (!this.scaling)) {
//          final float Touch3DModelProportion = 1000.0f;
//          this.modelRenderer.setTranslationY(-transferAmountX / Touch3DModelProportion);
//          this.modelRenderer.setTranslationX(transferAmountY / Touch3DModelProportion);
//          this.rotationing = false;
//        }
//        this.rotationing = true;
//        break;
//
//      // タッチが離れた
//      case MotionEvent.ACTION_UP:
//        this.prevX = event.getX();
//        this.prevY = event.getY();
//        break;
//
//      // タッチがキャンセルされた
//      case MotionEvent.ACTION_CANCEL:
//        break;
//
//      default:
//        break;
//    }
//
//    this.modelRenderer.updateDisplay();
//    return super.onTouchEvent(event);
//  }
//
//  protected void setScaleValue(double d) {
//    this.scaleValue = d;
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  public void onAccuracyChanged(Sensor sensor, int accuracy) {
//    // TODO 自動生成されたメソッド・スタブ
//
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  /**
//   * {@inheritDoc}
//   */
//  public void onSensorChanged(SensorEvent event) {
//
//    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//      for (int i = 0; i < 3; i++)
//        this.accels[i] = event.values[i];
//
//      if (this.useAccelerSensor) {
//
//        // Low Pass Filter
//        this.lowPassX = this.lowPassX + event.values[0];
//        this.lowPassY = this.lowPassY + event.values[1];
//        this.lowPassZ = (0.1 * event.values[2] + 0.9 * this.lowPassZ);
//
//        this.rawAz = event.values[2] - this.lowPassZ;
//
//        long nowTime = SystemClock.uptimeMillis();
//        long interval = nowTime - this.useAccelerOldTime;
//
//        if (interval > 300) {
//          final int accelerSensorThreshold = 5;
//          if (this.rawAz > accelerSensorThreshold) {
//            for (int i = 0; i < 10000; i++) {
//              if (this.scaleValue < 20.0) {
//                this.scaleValue += 0.00002;
//                this.modelRenderer.setScale((float)this.scaleValue);
//                this.modelRenderer.updateDisplay();
//              }
//            }
//            this.useAccelerOldTime = nowTime;
//          }
//          if (this.rawAz < -accelerSensorThreshold) {
//            for (int i = 0; i < 10000; i++) {
//              if (this.scaleValue > 0.05) {
//                this.scaleValue -= 0.00002;
//                this.modelRenderer.setScale((float)this.scaleValue);
//                this.modelRenderer.updateDisplay();
//              }
//            }
//            this.useAccelerOldTime = nowTime;
//          }
//        }
//
//        this.modelRenderer.setScale((float)this.scaleValue);
//
//      }
//
//    } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
//      for (int i = 0; i < 3; i++)
//        this.magneticFields[i] = event.values[i];
//    }
//
//    if (this.useOrientationSensor) {
//      float[] R = new float[9];
//      float[] I = new float[9];
//      SensorManager.getRotationMatrix(R, I, this.accels, this.magneticFields);
//
//      SensorManager.getOrientation(R, this.orientations);
//
//      float[] diffOrientations = new float[3];
//
//      for (int i = 0; i < this.orientations.length; i++) {
//
//        diffOrientations[i] = this.orientations[i] - this.prevOrientations[i];
//        if (Math.abs(diffOrientations[i]) < 0.05) diffOrientations[i] = (float)0.0;
//      }
//
//      for (int i = 0; i < this.orientations.length; i++) {
//        this.prevOrientations[i] = this.orientations[i];
//
//      }
//
//      this.modelRenderer.setRotation(0.0f, (float)Math.toDegrees(diffOrientations[2] * 3.5));
//      //this.modelRenderer.setRotation((float)Math.toDegrees(diffOrientations[0] * 5.5), 0.0f);
//    }
//
//    this.modelRenderer.updateDisplay();
//  }
//
//}