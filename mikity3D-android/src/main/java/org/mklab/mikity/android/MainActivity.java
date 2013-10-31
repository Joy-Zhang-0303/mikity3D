package org.mklab.mikity.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Timer;

import org.mklab.mikity.LogCatPrinter;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import org.openintents.intents.*;


/**
 * @author ohashi,Takuya
 * @version $Revision$, 2013/08/23
 */
public class MainActivity extends Activity {

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

  /** 前回のタッチのｘ座標 */
  float prevX = 0;
  /** 前回のタッチのｙ座標 */
  float prevY = 0;
  /** アニメーションの再生速度 丸め誤差を防ぐために１０で割る必要があります。*/
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
    this.modelRenderer = new OpenglesModelRenderer(this.glView);

    final Group[] children = this.root.getModel(0).getGroups();
    this.modelRenderer.setChildren(children);

    final Mikity3dConfiguration configuration = this.root.getConfiguration(0);
    this.modelRenderer.setConfiguration(configuration);

    /*
    Resources res = this.getResources();
    InputStream mat = res.openRawResource(R.raw.swingupsimulation);
    setTimeData(mat);
    mat.close();
    */
    // if (this.root.getConfig(0).getData() != null) {
    // setTimeData(new FileInputStream(this.root.getConfig(0).getData()));
    // }

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
    // this.glView = new GLSurfaceView(this);
    this.glView = (GLSurfaceView)this.findViewById(R.id.glview1);

    // xmlファイルを指定する
    final String modelFileName = "pendulum/pendulum/pendulum.xml"; //$NON-NLS-1$

    Resources res = this.getResources();

    InputStream input = res.openRawResource(R.raw.pendulum);

    final FileManager fileManager = new FileManager(this);

    try {
      loadModelFile(input);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }

    // 描画のクラスを登録する
    this.glView.setRenderer(this.modelRenderer);
    // this.glView.setRenderer(new GLRenderer());
    this.mIsInitScreenSize = false;

    // 任意のタイミングで再描画する設定
    this.glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    this.animationSpeedTextEdit = (EditText)findViewById(R.id.editText1);
    this.animationSpeedTextEdit.setText(Double.toString(this.animationSpeed/10));
    this.animationSpeedTextEdit.clearFocus();
    /*animationSpeedTextEdit.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        animationSpeedTextEdit.setFocusableInTouchMode(true);
        animationSpeedTextEdit.setFocusable(true);
        animationSpeedTextEdit.setEnabled(true);
      }
    });*/

    Button quickButton = (Button)findViewById(R.id.quickButton);
    Button slowButton = (Button)findViewById(R.id.slowButton);

    Button playButton = (Button)findViewById(R.id.button1);
    Button stopButton = (Button)findViewById(R.id.button2);
    // final RedrawHandler handler = new RedrawHandler(100);

    this.testTextView = new TextView(this);
    this.testTextView = (TextView)findViewById(R.id.textView1);

    // ScaleGestureDetecotorクラスのインスタンス生成
    this.gesDetect = new ScaleGestureDetector(this, this.onScaleGestureListener);

    //再生速度の設定
    quickButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        MainActivity.this.animationSpeed = (int)(Double.parseDouble(MainActivity.this.animationSpeedTextEdit.getText().toString())*10);
        MainActivity.this.animationSpeed += 1;
        MainActivity.this.animationSpeedTextEdit.setText(""+(double )MainActivity.this.animationSpeed/10);
        if (MainActivity.this.animationTask != null) MainActivity.this.animationTask.setSpeedScale(MainActivity.this.animationSpeed/10);
      }
    });
    slowButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        MainActivity.this.animationSpeed = (int)(Double.parseDouble(MainActivity.this.animationSpeedTextEdit.getText().toString())*10);
        MainActivity.this.animationSpeed -= 1;
        if(MainActivity.this.animationSpeed < 0 )
          MainActivity.this.animationSpeed = 0;
        MainActivity.this.animationSpeedTextEdit.setText(Double.toString((double)MainActivity.this.animationSpeed/10));
        if (MainActivity.this.animationTask != null) MainActivity.this.animationTask.setSpeedScale(MainActivity.this.animationSpeed/10);
      }
    });

    /* SeekBar varseekBar;
     varseekBar = (SeekBar)findViewById(R.id.seekBar1);
     varseekBar.setMax(20);
     varseekBar.setProgress(5);

     varseekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

       public void onStopTrackingTouch(SeekBar seekBar) {
         testTextView.setText("Release SeekBar");
       }

       public void onStartTrackingTouch(SeekBar seekBar) {
         testTextView.setText("Touch SeekBar");

       }

       public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
         testTextView.setText("SeekBar value:" + progress + "speed:" + animationSpeed);
         animationSpeed = progress + 1;
         if (animationTask != null) animationTask.setSpeedScale(animationSpeed);
       }
     });*/

    //時系列選択ボタンの配置
    Button selectButton = (Button)findViewById(R.id.selectButton);
    selectButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        fileManager.getFilePath();
      }
    });

    //ファイルパスビューの配置
    this.filePathView = new TextView(this);
    this.filePathView = (TextView)findViewById(R.id.filePathView);

    // イベントリスナー
    playButton.setOnClickListener(new View.OnClickListener() {

      // コールバックメソッド
      public void onClick(View view) {
        runAnimation();
      }
    });

    stopButton.setOnClickListener(new View.OnClickListener() {

      // コールバックメソッド
      public void onClick(View view) {
        MainActivity.this.timer.cancel();
        playable = true;
      }
    });
    //Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();

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
    super.onResume();
    this.glView.onResume();
    //Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPause() {
    super.onPause();
    this.glView.onPause();
    //Toast.makeText(this, "onPause", Toast.LENGTH_LONG).show();
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
   * @param file ファイル
   */
  public void setTimeData(final InputStream input) {
    try {
      // final FileInputStream input = new FileInputStream(file);
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
    this.manager.setLogCat(new LogCatImpl());
    this.animationSpeed = (int)(Double.parseDouble(MainActivity.this.animationSpeedTextEdit.getText().toString())*10);
    if (playable == false) {
      this.timer.cancel();
    }

    if (this.data == null || this.timeTable == null) {
      return;
    }

    playable = false;

    this.endTime = this.manager.getEndTime();
    this.animationTask = new AnimationTask(startTime, this.endTime, this.manager, this.modelRenderer);
    this.animationTask.setSpeedScale((double)this.animationSpeed/10);
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
          this.filePath = data.getData().getPath();

          this.filePathView.setText(this.filePath);
          //loadTimeData();
          loadtimeSeriesData();

        }

        break;
    }
  }

  private void loadtimeSeriesData() {
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
          mat1 = new FileInputStream(MainActivity.this.filePath);
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
      //MainActivity.testTextView.setText("Scalling");
      return super.onScale(detector);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
      // TODO Auto-generated method stub
      MainActivity.this.scaling = true;

      MainActivity.this.testTextView.setText(Double.toString(MainActivity.this.scaleValue));
      return super.onScaleBegin(detector);
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
      // TODO Auto-generated method stub
      MainActivity.this.scaling = false;
      setScaleValue(MainActivity.this.scaleValue - (1.0 - MainActivity.this.gesDetect.getScaleFactor()));
      MainActivity.this.prevX = MainActivity.this.gesDetect.getFocusX();
      MainActivity.this.prevY = MainActivity.this.gesDetect.getFocusY();
      // testTextView.setText(Double.toString(scaleValue));
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
        this.testTextView.setText("touched!  x:" + event.getX() + " y:" + event.getY()); //$NON-NLS-1$//$NON-NLS-2$
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

}


