package org.mklab.mikity.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.mklab.mikity.android.view.renderer.OpenglesModelRenderer;
//import org.mklab.mikity.control.AnimationTask;
import org.mklab.mikity.control.AnimationTaskListener;
import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dConfiguration;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;
import org.mklab.nfc.matrix.Matrix;
import org.mklab.nfc.matx.MatxMatrix;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.openintents.intents.*;


/**
 * @author ohashi
 * @version $Revision$, 2013/08/23
 */
public class MainActivity extends Activity implements SensorEventListener {

  // private static String TAG = "3Dmikity-android";

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
  private int animationSpeed = 10;

  private final int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 0;

  /** 時系列データのファイルパス */
  private String filePath;

  /** 　ピンチイン、ピンチアウト用のジェスチャー */
  private ScaleGestureDetector gesDetect = null;

  /** スケーリング中かどうかのフラグ */
  private boolean scaling;
  private boolean rotationing;

  private double scaleValue = 1;
  private EditText animationSpeedTextEdit;
  private InputStream inputModelFile;
  protected boolean isSelectedModelFile;

  private Button selectButton;
  private Button quickButton;
  private Button slowButton;
  private Button stopButton;
  private Button loadModelButton;
  private Button playButton;

  private SensorManager sensorManager;
  private boolean registerAccerlerometer;
  private boolean registerMagneticFieldSensor;
  private float[] accels = new float[3];
  private float[] magneticFields = new float[3];
  private float[] orientations = new float[3];
  private float[] prevOrientations = new float[3];
  private ToggleButton gyroToggleButton;
  private boolean useGyroScope = false;

  /**
   * 
   * @param modelFile モデルファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
  private void loadModelFile(File modelFile) throws IOException, Mikity3dSerializeDeserializeException {
    this.root = new Mikity3dFactory().loadFile(modelFile);
    this.manager = new MovableGroupManager(this.root);
    this.modelRenderer = new OpenglesModelRenderer(this.glView);
  }

  /**
   * 
   * @param modelFile モデルファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
  private void loadModelFile(InputStream input) throws IOException, Mikity3dSerializeDeserializeException {
    this.root = new Mikity3dFactory().loadFile(input);
    this.manager = new MovableGroupManager(this.root);
    Group[] children = this.root.getModel(0).getGroups();
    this.modelRenderer.setChildren(children);
    Mikity3dConfiguration configuration = this.root.getConfiguration(0);
    this.modelRenderer.setConfiguration(configuration);

    this.manager.setLogCat(new LogCatImpl()); //LogCatのセット

  }

  private void loadTimeData() throws FileNotFoundException, IOException {
    InputStream mat1 = new FileInputStream(this.filePath);
    setTimeData(mat1);
    mat1.close();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // GLViewを取り出す
    this.glView = (GLSurfaceView)this.findViewById(R.id.glview1);

    Resources res = this.getResources();

    this.sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    this.registerAccerlerometer = false;
    this.registerMagneticFieldSensor = false;
    for (int i = 0; i < 3; i++) {
      this.accels[i] = 0.0f;
      this.magneticFields[i] = 0.0f;
      this.orientations[i] = 0.0f;
      this.prevOrientations[i] = 0.0f;

    }

    //this.inputModelFile = res.openRawResource(R.raw.pendulum);
    final OIFileManager fileManager = new OIFileManager(this);
    this.modelRenderer = new OpenglesModelRenderer(this.glView);

    //モデルデータ選択ボタンの表示
    this.loadModelButton = (Button)findViewById(R.id.modelSelectButton);
    //時系列選択ボタンの配置
    this.selectButton = (Button)findViewById(R.id.timeSelectButton);
    this.quickButton = (Button)findViewById(R.id.quickButton);
    this.slowButton = (Button)findViewById(R.id.slowButton);
    this.playButton = (Button)findViewById(R.id.button1);
    this.stopButton = (Button)findViewById(R.id.button2);

    this.selectButton.setEnabled(false);
    this.quickButton.setEnabled(false);
    this.slowButton.setEnabled(false);
    this.playButton.setEnabled(false);
    this.stopButton.setEnabled(false);

    this.loadModelButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        MainActivity.this.isSelectedModelFile = false;
        fileManager.getFilePath();
      }
    });

    // 描画のクラスを登録する
    this.glView.setRenderer(this.modelRenderer);
    this.mIsInitScreenSize = false;

    // 任意のタイミングで再描画する設定
    this.glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    this.animationSpeedTextEdit = (EditText)findViewById(R.id.editText1);
    this.animationSpeedTextEdit.clearFocus();
    this.animationSpeedTextEdit.setText(Double.toString(this.animationSpeed / 10));
    this.animationSpeedTextEdit.clearFocus();

    //ファイルパスビューの配置

    this.filePathView = new TextView(this);
    this.modelFilePathView = new TextView(this);
    this.filePathView = (TextView)findViewById(R.id.filePathView);
    this.modelFilePathView = (TextView)findViewById(R.id.modelPathView);

    this.testTextView = new TextView(this);
    this.testTextView = (TextView)findViewById(R.id.textView1);

    // ScaleGestureDetecotorクラスのインスタンス生成
    this.gesDetect = new ScaleGestureDetector(this, this.onScaleGestureListener);

    //再生速度の設定
    this.quickButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        MainActivity.this.animationSpeed = (int)(Double.parseDouble(MainActivity.this.animationSpeedTextEdit.getText().toString()) * 10);
        MainActivity.this.animationSpeed += 1;
        MainActivity.this.animationSpeedTextEdit.setText("" + (double)MainActivity.this.animationSpeed / 10);
        if (MainActivity.this.animationTask != null) MainActivity.this.animationTask.setSpeedScale(MainActivity.this.animationSpeed / 10);
      }
    });
    this.slowButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        MainActivity.this.animationSpeed = (int)(Double.parseDouble(MainActivity.this.animationSpeedTextEdit.getText().toString()) * 10);
        MainActivity.this.animationSpeed -= 1;
        if (MainActivity.this.animationSpeed < 0) MainActivity.this.animationSpeed = 0;
        MainActivity.this.animationSpeedTextEdit.setText(Double.toString((double)MainActivity.this.animationSpeed / 10));
        if (MainActivity.this.animationTask != null) MainActivity.this.animationTask.setSpeedScale(MainActivity.this.animationSpeed / 10);
      }
    });

    this.selectButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        fileManager.getFilePath();

      }
    });

    // イベントリスナー
    this.playButton.setOnClickListener(new View.OnClickListener() {

      // コールバックメソッド
      public void onClick(View view) {
        runAnimation();
      }
    });

    this.stopButton.setOnClickListener(new View.OnClickListener() {

      // コールバックメソッド
      public void onClick(View view) {
        MainActivity.this.timer.cancel();
        playable = true;
      }
    });

    this.gyroToggleButton = (ToggleButton)findViewById(R.id.gyroToggleButton);
    this.gyroToggleButton.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        if (MainActivity.this.gyroToggleButton.isChecked()) useGyroScope = true;
        else useGyroScope = false;

      }
    });

    Intent intent = getIntent();
    if (intent.getStringArrayListExtra("aaa") != null) {
      Log.d("mikity", "Path if");
      ArrayList<String> data = intent.getStringArrayListExtra("aaa");
      Toast.makeText(this, "Launch ounter application", Toast.LENGTH_LONG).show();

      String modelFilePath = data.get(0);
      String timeDataPath = data.get(1);

      try {
        this.inputModelFile = new FileInputStream(modelFilePath);
        this.modelFilePathView.setText(new File(modelFilePath).getName());

      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }

      try {
        loadModelFile(this.inputModelFile);
      } catch (IOException e) {
        // TODO 自動生成された catch ブロック
        throw new RuntimeException(e);
      } catch (Mikity3dSerializeDeserializeException e) {
        // TODO 自動生成された catch ブロック
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
      setTimeData(mat);

      try {
        mat.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

  }

  /** 表示されるときに呼ばれる */
  @Override
  public void onWindowFocusChanged(boolean hasFocus) {

    // スクリーンサイズ調整が済んでいない場合は調整する
    if (this.mIsInitScreenSize == false) {
      Resources resources = getResources();
      Configuration config = resources.getConfiguration();
      int size;
      switch (config.orientation) {
        case Configuration.ORIENTATION_PORTRAIT:
          size = this.glView.getWidth();
          break;
        case Configuration.ORIENTATION_LANDSCAPE:
          size = this.glView.getHeight();
          break;
        default:
          throw new IllegalStateException("It is not a portrait or landscape"); //$NON-NLS-1$
      }
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
      this.glView.setLayoutParams(params);
    }
    super.onWindowFocusChanged(hasFocus);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onResume() {
    if (!this.registerAccerlerometer) {
      List<Sensor> sensors = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
      if (sensors.size() > 0) {
        this.registerAccerlerometer = this.sensorManager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_UI);
      }
    }
    if (!this.registerMagneticFieldSensor) {
      List<Sensor> sensors = this.sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
      if (sensors.size() > 0) {
        this.registerMagneticFieldSensor = this.sensorManager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_UI);
      }
    }

    this.glView.onResume();
    super.onResume();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPause() {

    this.glView.onPause();
    if (this.registerAccerlerometer || this.registerMagneticFieldSensor) {
      this.sensorManager.unregisterListener(this);

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
    // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.main,menu);
    return true;
  }

  /**
   * 実行時間バーを設定する。
   * 
   * @param input 時系列データのインプットストリーム
   * 
   */
  public void setTimeData(final InputStream input) {
    try {
      this.data = MatxMatrix.readMatFormat(new InputStreamReader(input));
      input.close();

      this.manager.setData(this.data);

      final Group rootGroup = this.root.getModel(0).getGroup(0);
      checkLinkParameterType(rootGroup);

      final int dataSize = this.manager.getDataSize();

      this.timeTable = new double[dataSize];

      this.endTime = this.manager.getEndTime();
      for (int i = 0; i < this.timeTable.length; i++) {
        this.timeTable[i] = this.endTime * ((double)i / this.timeTable.length);
      }

    } catch (FileNotFoundException e) {

      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  private void checkLinkParameterType(Group parent) {
    final Group[] groups = parent.getGroups();
    for (final Group group : groups) {
      final LinkData[] links = group.getLinkData();
      for (final LinkData link : links) {
        if (link.hasDHParameter()) {
          this.manager.setHasDHParameter(true);
        } else if (link.hasCoordinateParameter()) {
          this.manager.setHasCoordinateParameter(true);
        }
      }

      checkLinkParameterType(group);
    }
  }

  /**
   * アニメーションを開始します。
   */
  /**
   * 
   */
  public void runAnimation() {
    long startTime = SystemClock.uptimeMillis();

    this.animationSpeed = (int)(Double.parseDouble(MainActivity.this.animationSpeedTextEdit.getText().toString()) * 10);
    if (playable == false) {
      this.timer.cancel();
    }

    if (this.data == null || this.timeTable == null) {
      return;
    }

    playable = false;

    this.endTime = this.manager.getEndTime();
    this.animationTask = new AnimationTask(startTime, this.endTime, this.manager, this.modelRenderer);
    this.animationTask.setSpeedScale((double)this.animationSpeed / 10);
    this.animationTask.addAnimationTaskListener(new AnimationTaskListener() {

      /**
       * {@inheritDoc}
       */
      public void tearDownAnimation() {
        playable = true;
      }

      /**
       * {@inheritDoc}
       */
      public void setUpAnimation() {
        // nothing to do
      }
    });

    this.timer = new Timer();
    this.timer.schedule(this.animationTask, 0, 30);
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
          // obtain the filename
          if (this.isSelectedModelFile) {
            String timeDataPath = data.getData().getPath();
            // this.filePathView.setText(new File(timeDataPath).getName());
            this.filePathView.setText(timeDataPath);

            loadtimeSeriesData(timeDataPath);

          } else {
            String modelFilePath = data.getData().getPath();
            try {
              this.inputModelFile = new FileInputStream(modelFilePath);
              //this.modelFilePathView.setText(new File(modelFilePath).getName());
              this.modelFilePathView.setText(modelFilePath);

            } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
            }

            try {
              loadModelFile(this.inputModelFile);
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
          }
        }

        break;
    }
  }

  private void loadtimeSeriesData(final String filePath) {
    AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {

      ProgressDialog progressDialog;

      @Override
      protected void onPreExecute() {
        this.progressDialog = new ProgressDialog(MainActivity.this);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.progressDialog.setMessage("Now Loading..."); //$NON-NLS-1$
        this.progressDialog.show();
      }

      @Override
      protected Void doInBackground(String... arg0) {
        InputStream mat1;
        try {
          mat1 = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        }
        setTimeData(mat1);
        try {
          mat1.close();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        return null;
      }

      @Override
      protected void onPostExecute(Void result) {
        this.progressDialog.dismiss();

      }

    };
    task.execute();
  }

  // スケールジェスチャーイベントを取得
  private final SimpleOnScaleGestureListener onScaleGestureListener = new SimpleOnScaleGestureListener() {

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
      MainActivity.this.rotationing = false;
      MainActivity.this.scaling = true;
      if (MainActivity.this.scaleValue - (1.0 - MainActivity.this.gesDetect.getScaleFactor()) > 0.2) {
        MainActivity.this.modelRenderer.setScale((float)(MainActivity.this.scaleValue - (1.0f - MainActivity.this.gesDetect.getScaleFactor())));
      }
      MainActivity.this.prevX = MainActivity.this.gesDetect.getFocusX();
      MainActivity.this.prevY = MainActivity.this.gesDetect.getFocusY();

      return super.onScale(detector);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {

      MainActivity.this.scaling = true;

      // MainActivity.this.testTextView.setText(Double.toString(MainActivity.this.scaleValue));
      return super.onScaleBegin(detector);
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

      MainActivity.this.scaling = false;
      setScaleValue(MainActivity.this.scaleValue - (1.0 - MainActivity.this.gesDetect.getScaleFactor()));
      MainActivity.this.prevX = MainActivity.this.gesDetect.getFocusX();
      MainActivity.this.prevY = MainActivity.this.gesDetect.getFocusY();

      super.onScaleEnd(detector);
    }
  };

  /**
   * {@inheritDoc}
   */
  // タッチ操作の種類によってイベントを取得する
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    float transferAmountX;
    float transferAmountY;
    int touchCount = event.getPointerCount();
    // タッチイベントをScaleGestureDetector#onTouchEventメソッドに
    this.gesDetect.onTouchEvent(event);

    switch (event.getAction()) {
    // タッチした
      case MotionEvent.ACTION_DOWN:
        // this.testTextView.setText("touched!  x:" + event.getX() + " y:" + event.getY()); //$NON-NLS-1$//$NON-NLS-2$
        this.rotationing = true;
        this.prevX = event.getX();
        this.prevY = event.getY();
        break;

      // タッチしたまま移動
      case MotionEvent.ACTION_MOVE:
        transferAmountX = event.getX() - this.prevX;
        transferAmountY = event.getY() - this.prevY;
        this.prevX = event.getX();
        this.prevY = event.getY();

        if (transferAmountX > 300) transferAmountX = 300;
        if (transferAmountX < -300) transferAmountX = -300;

        if (transferAmountY > 300) transferAmountY = 300;
        if (transferAmountY < -300) transferAmountY = -300;

        if ((this.rotationing) && (touchCount == 1)) {
          this.modelRenderer.setRotation(transferAmountX, transferAmountY);
        }
        if ((touchCount == 2) && (!this.scaling)) {
          this.modelRenderer.setTranslationY(-transferAmountX / 1000.0f);
          this.modelRenderer.setTranslationX(transferAmountY / 1000.0f);
          this.rotationing = false;
        }
        this.rotationing = true;
        break;

      // タッチが離れた
      case MotionEvent.ACTION_UP:
        this.prevX = event.getX();
        this.prevY = event.getY();
        break;

      // タッチがキャンセルされた
      case MotionEvent.ACTION_CANCEL:
        break;

      default:
        break;
    }

    this.modelRenderer.updateDisplay();
    return super.onTouchEvent(event);
  }

  protected void setScaleValue(double d) {
    this.scaleValue = d;
  }

  /**
   * {@inheritDoc}
   */
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // TODO 自動生成されたメソッド・スタブ

  }

  /**
   * {@inheritDoc}
   */
  public void onSensorChanged(SensorEvent event) {
    if (this.useGyroScope) {

      if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        for (int i = 0; i < 3; i++)
          this.accels[i] = event.values[i];
      } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
        for (int i = 0; i < 3; i++)
          this.magneticFields[i] = event.values[i];
      }

      NumberFormat numberFormat = NumberFormat.getInstance();
      numberFormat.setMaximumFractionDigits(2);

      float[] R = new float[9];
      float[] I = new float[9];

      SensorManager.getRotationMatrix(R, I, this.accels, this.magneticFields);
      SensorManager.getOrientation(R, this.orientations);

      float[] diffOrientations = new float[3];

      for (int i = 0; i < this.orientations.length; i++) {

        if ((prevOrientations[i] > 0 && orientations[i] < 0) || (prevOrientations[i] < 0 && orientations[i] > 0)) diffOrientations[i] = this.orientations[i] + this.prevOrientations[i];
        else diffOrientations[i] = this.orientations[i] - this.prevOrientations[i];

        if (Math.abs(diffOrientations[i]) < 0.05) diffOrientations[i] = (float)0.0;

      }

      for (int i = 0; i < this.orientations.length; i++) {
        this.prevOrientations[i] = this.orientations[i];
      }

      this.testTextView.setText("Azimuth:" + Math.toDegrees(orientations[0]) + " \nPitch:" + Math.toDegrees(orientations[1]) + "\nRoll:" + Math.toDegrees(orientations[2]));

      this.modelRenderer.setRotation(0, (float)Math.toDegrees(diffOrientations[2] * 3.0));
      // this.modelRenderer.setRotation((float)Math.toDegrees(-(diffOrientations[1] +diffOrientations[0]* (float)3.0, 0);
      //if (orientations[2] > -90 && orientations[2] < 10)
      this.modelRenderer.setRotation((float)Math.toDegrees(-diffOrientations[0] * 3.0), 0);

      this.modelRenderer.updateDisplay();
    }
  }

}