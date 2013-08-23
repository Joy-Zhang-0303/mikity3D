package org.mklab.mikity.android;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;

import org.mklab.mikity.android.view.renderer.OpenglesModelRenderer;
import org.mklab.mikity.control.AnimationTask;
import org.mklab.mikity.control.AnimationTaskListener;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.model.xml.JamastFactory;
import org.mklab.mikity.model.xml.JamastSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Jamast;
import org.mklab.mikity.model.xml.simplexml.JamastConfig;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;
import org.mklab.nfc.matrix.Matrix;
import org.mklab.nfc.matx.MatxMatrix;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author ohashi
 * @version $Revision$, 2013/02/05
 */
public class MainActivity extends Activity {

  //private static String TAG = "3Dmikity-android";

  private boolean mIsInitScreenSize;
  GLSurfaceView glView;

  /** アニメーション用タスク */
  AnimationTask animationTask;

  /** */
  public static boolean playable = true;

  /** */
  private double endTime;

  private Jamast root;

  /** 等間隔の時間を保存しとく配列 */
  double[] timeTable;

  Timer timer = new Timer();

  private Matrix data;

  /** */
  MovableGroupManager manager;

  /** ModelCanvas */
  private OpenglesModelRenderer modelRenderer;

  ///////////////AndroidLesson///////////////
  TextView testTextView;
  int i;
  float transferAmountX;
  float transferAmountY;
  float prevX = 0;
  float prevY = 0;

  //////////////
  /**
   * 
   * @param modelFile モデルファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws JamastSerializeDeserializeException ファイルを読み込めない場合
   */
  private void loadModelFile(File modelFile) throws IOException, JamastSerializeDeserializeException {
    this.root = new JamastFactory().loadFile(modelFile);
    this.manager = new MovableGroupManager(this.root);
    this.modelRenderer = new OpenglesModelRenderer(this.glView);
  }

  /**
   * 
   * @param modelFile モデルファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws JamastSerializeDeserializeException ファイルを読み込めない場合
   */
  private void loadModelFile(InputStream input) throws IOException, JamastSerializeDeserializeException {
    this.root = new JamastFactory().loadFile(input);
    this.manager = new MovableGroupManager(this.root);
    this.modelRenderer = new OpenglesModelRenderer(this.glView);

    final Group[] children = this.root.getModel(0).getGroups();
    this.modelRenderer.setChildren(children);

    final JamastConfig configuration = this.root.getConfig(0);
    this.modelRenderer.setConfiguration(configuration);

    Resources res = this.getResources();
    InputStream mat = res.openRawResource(R.raw.swingupsimulation);
    setTimeData(mat);
    mat.close();

    // if (this.root.getConfig(0).getData() != null) {
    //  setTimeData(new FileInputStream(this.root.getConfig(0).getData()));
    //}

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    //GLViewを取り出す
    //this.glView = new GLSurfaceView(this);
    this.glView = (GLSurfaceView)this.findViewById(R.id.glview1);

    //xmlファイルを指定する
    final String modelFileName = "pendulum/pendulum/pendulum.xml"; //$NON-NLS-1$

    Resources res = this.getResources();
    InputStream input = res.openRawResource(R.raw.pendulum);

    try {
      loadModelFile(input);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (JamastSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }

    //描画のクラスを登録する
    this.glView.setRenderer(this.modelRenderer);
    //this.glView.setRenderer(new GLRenderer());
    this.mIsInitScreenSize = false;

    //任意のタイミングで再描画する設定
    this.glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    Button playButton = (Button)findViewById(R.id.button1);
    Button stopButton = (Button)findViewById(R.id.button2);
    //final RedrawHandler handler = new RedrawHandler(100);

    //////////
    this.testTextView = new TextView(this);
    this.testTextView = (TextView)findViewById(R.id.textView1);
    //glView.setOnClickListener(new ClickListener());

    /////////////////

    //イベントリスナー
    playButton.setOnClickListener(new View.OnClickListener() {

      //コールバックメソッド
      public void onClick(View view) {
        runAnimation();
      }
    });

    stopButton.setOnClickListener(new View.OnClickListener() {

      //コールバックメソッド
      public void onClick(View view) {
        MainActivity.this.timer.cancel();
        playable = true;
      }
    });
    Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();
  }

  /** 表示されるときに呼ばれる */

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {

    //スクリーンサイズ調整が済んでいない場合は調整する
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
    Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPause() {
    super.onPause();
    this.glView.onPause();
    Toast.makeText(this, "onPause", Toast.LENGTH_LONG).show();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    //getMenuInflater().inflate(R.menu.main,menu);
    return true;
  }

  /**
   * 実行時間バーを設定する。
   * 
   * @param file ファイル
   */
  public void setTimeData(final InputStream input) {
    try {
      //final FileInputStream input = new FileInputStream(file);
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
  public void runAnimation() {
    if (playable == false) {
      this.timer.cancel();
    }

    if (this.data == null || this.timeTable == null) {
      return;
    }

    playable = false;

    this.endTime = this.manager.getEndTime();
    this.animationTask = new AnimationTask(0, this.endTime, this.manager, this.modelRenderer);
    this.animationTask.setSpeedScale(6);
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
    this.timer.schedule(this.animationTask, 0, 10);
  }

  /*
  class ClickListener implements View.OnClickListener{
    public void onClick(View v) {
      i++;
      testTextView.setText("touched!  x:" + v.getX()+" y:"+v.getY());
    }
  }
  */

  /**
   * {@inheritDoc}
   */
  //タッチ操作の種類によってイベントを取得する
  @Override
  public boolean onTouchEvent(MotionEvent event) {

    switch (event.getAction()) {
    //タッチした
      case MotionEvent.ACTION_DOWN:
        this.testTextView.setText("touched!  x:" + event.getX() + " y:" + event.getY()); //$NON-NLS-1$//$NON-NLS-2$
        this.prevX = event.getX();
        this.prevY = event.getY();
        break;

      //タッチしたまま移動
      case MotionEvent.ACTION_MOVE:
        this.transferAmountX = event.getX() - this.prevX;
        this.transferAmountY = event.getY() - this.prevY;
        this.prevX = event.getX();
        this.prevY = event.getY();

        if (transferAmountX > 300) transferAmountX = 300;
        if (transferAmountX < -300) transferAmountX = -300;

        if (transferAmountY > 300) transferAmountY = 300;
        if (transferAmountY < -300) transferAmountY = -300;

        this.testTextView.setText("touched and move  x_amount:" + this.transferAmountX + " y_amount:" + this.transferAmountY); //$NON-NLS-1$//$NON-NLS-2$
        
        this.modelRenderer.setRotation(this.transferAmountX, this.transferAmountY);
        break;

      //タッチが離れた
      case MotionEvent.ACTION_UP:
        break;

      //タッチがキャンセルされた
      case MotionEvent.ACTION_CANCEL:
        break;

      default:
        break;
    }

    this.modelRenderer.updateDisplay();
    return super.onTouchEvent(event);
  }

}
