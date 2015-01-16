/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.android.view.renderer.OpenglesModelRenderer;
import org.mklab.mikity.control.AnimationTaskListener;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dConfiguration;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;
import org.mklab.nfc.matrix.Matrix;
import org.mklab.nfc.matx.MatxMatrix;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * @author soda
 * @version $Revision$, 2014/10/10 モデル描画用のフラグメントです。
 */
public class CanvasFragment extends RoboFragment implements SensorEventListener {

  GLSurfaceView glView;
  boolean mIsInitScreenSize;
  public OpenglesModelRenderer modelRenderer;
  public ScaleGestureDetector gesDetect = null;
  boolean rotationing;
  boolean scaling;
  private double scaleValue = 1;
  float prevX = 0;
  float prevY = 0;
  public CanvasActivity activity;
  Timer timer = new Timer();
  public Mikity3d root;
  MovableGroupManager manager;
  Matrix data;
  private double[] timeTable;
  private double endTime;
  private int animationSpeed;
  boolean playable;
  private AnimationTask animationTask;
  /** センサーマネージャー */
  SensorManager sensorManager;
  //  private boolean registerAccerlerometer;
  //  private boolean registerMagneticFieldSensor;
  /** センサーからの加速度を格納する配列 */
  private float[] accels = new float[3];
  /** センサーからの地磁気を格納する配列 */
  private float[] magneticFields = new float[3];
  /** センサーから算出した端末の角度を格納する配列 */
  private float[] orientations = new float[3];
  /** 角度の基準値 */
  private float[] prevOrientations = new float[3];
  /** 端末の角度を3Dオブジェクトに反映させるかどうかのフラグ */
  boolean useOrientationSensor = false;
  /** */
  private float[] prevAccerlerometer = new float[3];
  /** 加速度のローパスフィルターのxの値 */
  private double lowPassX;
  /** 加速度のローパスフィルターのｙの値 */
  private double lowPassY;
  /** 加速度のローパスフィルターのzの値 */
  private double lowPassZ = 9.45;
  /** 加速度のハイパスフィルターのZの値 */
  private double rawAz;
  /** 前回加速度センサーを3Dオブジェクトに使用したときの時間 */
  private long useAccelerOldTime = 0L;
  /** 加速度センサーの値を3Dオブジェクトに反映させるかどうか */
  boolean useAccelerSensor = false;
  public List<Sensor> sensors;
  public CanvasFragment savedFragmentInstance;
  public String modelFilePath;
  public String timeDataPath;
  protected Uri timeDataUri;
  protected boolean rotateTimeDataFlag = false;
  @InjectView(R.id.layout_fragment_canvas)
  View view;
  ProgressDialog progressDialog;
  public int setModelCount = 0;
  public boolean setIllegalTimeData = false;
  public boolean setProperTimeData = false;

  /**
   * @param savedInstanceState Bundle
   * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if (view != null) {
      ViewGroup parent = (ViewGroup)view.getParent();
      parent.removeView(view);
      return this.view;
    }
    
    view = inflater.inflate(R.layout.canvas_fragment, container, false);
    //    //this.inputModelFile = res.openRawResource(R.raw.pendulum);
    //    //final OIFileManager fileManager = new OIFileManager(this);
    this.glView = (GLSurfaceView)view.findViewById(R.id.glview1);
    this.getResources();
    this.modelRenderer = new OpenglesModelRenderer(this.glView);
    //// // 描画のクラスを登録する
    this.glView.setRenderer(this.modelRenderer);
    this.mIsInitScreenSize = false;
    initField();
//    if(root!= null) {
//      setModel();
//    }

    // 任意のタイミングで再描画する設定
    this.glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    this.activity = (CanvasActivity)getActivity();

    // ScaleGestureDetecotorクラスのインスタンス生成
    this.gesDetect = new ScaleGestureDetector(this.getActivity(), this.onScaleGestureListener);
    // タッチ操作の種類によってイベントを取得する
    view.setOnTouchListener(new View.OnTouchListener() {

      public boolean onTouch(View v, MotionEvent event) {
        float transferAmountX;
        float transferAmountY;
        int touchCount = event.getPointerCount();
        // タッチイベントをScaleGestureDetector#onTouchEventメソッドに
        CanvasFragment.this.gesDetect.onTouchEvent(event);

        switch (event.getAction()) {
        // タッチした
          case MotionEvent.ACTION_DOWN:
            CanvasFragment.this.rotationing = true;
            CanvasFragment.this.prevX = event.getX();
            CanvasFragment.this.prevY = event.getY();
            break;

          // タッチしたまま移動
          case MotionEvent.ACTION_MOVE:
            transferAmountX = event.getX() - CanvasFragment.this.prevX;
            transferAmountY = event.getY() - CanvasFragment.this.prevY;
            CanvasFragment.this.prevX = event.getX();
            CanvasFragment.this.prevY = event.getY();

            if ((CanvasFragment.this.rotationing) && (touchCount == 1)) {
              CanvasFragment.this.modelRenderer.setRotation(transferAmountX, transferAmountY);
            }
            if ((touchCount == 2) && (!CanvasFragment.this.scaling)) {
              final float Touch3DModelProportion = 1000.0f;
              CanvasFragment.this.modelRenderer.setTranslationY(-transferAmountX / Touch3DModelProportion);
              CanvasFragment.this.modelRenderer.setTranslationX(transferAmountY / Touch3DModelProportion);
              CanvasFragment.this.rotationing = false;
            }
            CanvasFragment.this.rotationing = true;
            break;

          // タッチが離れた
          case MotionEvent.ACTION_UP:
            CanvasFragment.this.prevX = event.getX();
            CanvasFragment.this.prevY = event.getY();
            break;

          // タッチがキャンセルされた
          case MotionEvent.ACTION_CANCEL:
            break;

          default:
            break;
        }

        CanvasFragment.this.modelRenderer.updateDisplay();
        return true;
      }
    });
    return view;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  // スケールジェスチャーイベントを取得
  private final SimpleOnScaleGestureListener onScaleGestureListener = new SimpleOnScaleGestureListener() {

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
      CanvasFragment.this.rotationing = false;
      CanvasFragment.this.scaling = true;
      if (CanvasFragment.this.scaleValue - (1.0 - CanvasFragment.this.gesDetect.getScaleFactor()) > 0.2) {
        CanvasFragment.this.modelRenderer.setScale((float)(CanvasFragment.this.scaleValue - (1.0f - CanvasFragment.this.gesDetect.getScaleFactor())));
      }
      CanvasFragment.this.prevX = CanvasFragment.this.gesDetect.getFocusX();
      CanvasFragment.this.prevY = CanvasFragment.this.gesDetect.getFocusY();

      return super.onScale(detector);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {

      CanvasFragment.this.scaling = true;

      // MainActivity.this.testTextView.setText(Double.toString(MainActivity.this.scaleValue));
      return super.onScaleBegin(detector);
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

      CanvasFragment.this.scaling = false;
      setScaleValue(CanvasFragment.this.scaleValue - (1.0 - CanvasFragment.this.gesDetect.getScaleFactor()));
      CanvasFragment.this.prevX = CanvasFragment.this.gesDetect.getFocusX();
      CanvasFragment.this.prevY = CanvasFragment.this.gesDetect.getFocusY();

      super.onScaleEnd(detector);
    }
  };

  protected void setScaleValue(double d) {
    this.scaleValue = d;
  }

  /**
   * @param modelFile モデルファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
//  private void loadModelFile(File modelFile) throws IOException, Mikity3dSerializeDeserializeException {
//    this.root = new Mikity3dFactory().loadFile(modelFile);
//    this.manager = new MovableGroupManager(this.root);
//    this.modelRenderer = new OpenglesModelRenderer(this.glView);
//  }

  /**
   * 
   * @param modelFile モデルファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
  public void loadModelFile(InputStream input) throws IOException, Mikity3dSerializeDeserializeException {
    this.root = new Mikity3dFactory().loadFile(input);
    setModel();
    setGroupManager();
  }

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

  public void loadtimeSeriesData(final InputStream filePath) {
    AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {

//      ProgressDialog progressDialog;

      @Override
      protected void onPreExecute() {
        CanvasFragment.this.progressDialog = new ProgressDialog(getActivity());
        CanvasFragment.this.progressDialog.setCanceledOnTouchOutside(false);
        CanvasFragment.this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        CanvasFragment.this.progressDialog.setMessage("Now Loading..."); //$NON-NLS-1$
        CanvasFragment.this.progressDialog.show();
      }

      @Override
      protected Void doInBackground(String... arg0) {
        InputStream mat1;
        mat1 = filePath;
//        try {
//          mat1 = filePath;
//        } catch (FileNotFoundException e) {
//          throw new RuntimeException(e);
//        }
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
        CanvasFragment.this.progressDialog.dismiss();
      }

    };
    task.execute();
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
      setTimeData();
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
//      this.setProperTimeData = true;
      this.setIllegalTimeData = false;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      if(this.progressDialog != null) {
        this.progressDialog.dismiss();
      }
      callExceptionDialogFragment("Please select data file.");
    } catch (IllegalArgumentException e) {
      callExceptionDialogFragment("Please select proper data file or set column number size to data size or lower.");
    } catch (IllegalAccessError e) {
      if(this.progressDialog != null) {
        this.progressDialog.dismiss();
      }
      String message = "Time data size is not match model's column number."
          + "\nPlease select proper time data or set proper column number.";
      DialogFragment dialogFragment = new ExceptionDialogFragment();
      ((ExceptionDialogFragment)dialogFragment).setMessage(message);
      dialogFragment.show(getFragmentManager(), "exceptionDialogFragment");
      this.setIllegalTimeData = true;
    }
  }
  
  public void callExceptionDialogFragment(String message) {
    DialogFragment dialogFragment = new ExceptionDialogFragment();
    ((ExceptionDialogFragment)dialogFragment).setMessage(message);
    dialogFragment.show(getFragmentManager(), "exceptionDialogFragment");
//    this.activity.ndFragment.timeDataName = "";
//    this.activity.ndFragment.filePathView.setText(this.activity.ndFragment.timeDataName);
  }
  
  public void setTimeData() {
    try {
      this.manager.setData(this.data);
    } catch(IllegalAccessError e) {
      if(this.progressDialog != null) {
        this.progressDialog.dismiss();
      }
      String message = "Time data size is not match model's column number."
          + "\nPlease select proper time data or set proper column number.";
      DialogFragment dialogFragment = new ExceptionDialogFragment();
      ((ExceptionDialogFragment)dialogFragment).setMessage(message);
      dialogFragment.show(getFragmentManager(), "exceptionDialogFragment");
      this.setIllegalTimeData = true;
    } catch (IllegalArgumentException e) {
      callExceptionDialogFragment("Please select proper data file or set column number to data size or lower.");
    }
    this.setModelCount = 0;
    final Group rootGroup = this.root.getModel(0).getGroup(0);
    checkLinkParameterType(rootGroup);

    final int dataSize = this.manager.getDataSize();

    this.timeTable = new double[dataSize];

    this.endTime = this.manager.getEndTime();
    for (int i = 0; i < this.timeTable.length; i++) {
      this.timeTable[i] = this.endTime * ((double)i / this.timeTable.length);
    }
    this.setIllegalTimeData = false;
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

  public MovableGroupManager getManager() {
    Log.d("getManager", this.manager.toString());
    return this.manager;
  }

  public OpenglesModelRenderer getModelRender() {
    Log.d("getModelRender", this.modelRenderer.toString());
    return this.modelRenderer;
  }

  /**
   * アニメーションを開始します。
   */
  /**
   * 
   */
  public void runAnimation() {
    long startTime = SystemClock.uptimeMillis();
//    Log.d("Animation:manager_data", Integer.toString(this.manager.getDataSize()));
    
//    this.animationSpeed = (int)(Double.parseDouble(this.activity.animationSpeedTextEdit.getText().toString()) * 10);
    this.animationSpeed = this.activity.ndFragment.animationSpeed;
    if (this.playable == false) {
      this.timer.cancel();
    }

    if (this.data == null || this.timeTable == null) {
      return;
    }

    this.playable = false;

    this.endTime = this.manager.getEndTime();
    Log.d("endTime", Double.toString(this.manager.getEndTime()));
    //this.animationTask = new AnimationTask(startTime, this.endTime, this.manager, this.modelRenderer);
    this.animationTask = new AnimationTask(startTime, this.endTime, getManager(), getModelRender());
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

  public void onSensorChanged(SensorEvent event) {

    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
      for (int i = 0; i < 3; i++)
        this.accels[i] = event.values[i];

      if (this.useAccelerSensor) {

        // Low Pass Filter
        this.lowPassX = this.lowPassX + event.values[0];
        this.lowPassY = this.lowPassY + event.values[1];
        this.lowPassZ = (0.1 * event.values[2] + 0.9 * this.lowPassZ);

        this.rawAz = event.values[2] - this.lowPassZ;

        long nowTime = SystemClock.uptimeMillis();
        long interval = nowTime - this.useAccelerOldTime;

        if (interval > 300) {
          final int accelerSensorThreshold = 5;
          if (this.rawAz > accelerSensorThreshold) {
            for (int i = 0; i < 10000; i++) {
              if (this.scaleValue < 20.0) {
                this.scaleValue += 0.00002;
                this.modelRenderer.setScale((float)this.scaleValue);
                this.modelRenderer.updateDisplay();
              }
            }
            this.useAccelerOldTime = nowTime;
          }
          if (this.rawAz < -accelerSensorThreshold) {
            for (int i = 0; i < 10000; i++) {
              if (this.scaleValue > 0.05) {
                this.scaleValue -= 0.00002;
                this.modelRenderer.setScale((float)this.scaleValue);
                this.modelRenderer.updateDisplay();
              }
            }
            this.useAccelerOldTime = nowTime;
          }
        }

        this.modelRenderer.setScale((float)this.scaleValue);

      }

    } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
      for (int i = 0; i < 3; i++)
        this.magneticFields[i] = event.values[i];
    }

    if (this.useOrientationSensor) {
      float[] R = new float[9];
      float[] I = new float[9];
      SensorManager.getRotationMatrix(R, I, this.accels, this.magneticFields);

      SensorManager.getOrientation(R, this.orientations);

      float[] diffOrientations = new float[3];

      for (int i = 0; i < this.orientations.length; i++) {

        diffOrientations[i] = this.orientations[i] - this.prevOrientations[i];
        if (Math.abs(diffOrientations[i]) < 0.05) diffOrientations[i] = (float)0.0;
      }

      for (int i = 0; i < this.orientations.length; i++) {
        this.prevOrientations[i] = this.orientations[i];

      }

      this.modelRenderer.setRotation(0.0f, (float)Math.toDegrees(diffOrientations[2] * 3.5));
      //this.modelRenderer.setRotation((float)Math.toDegrees(diffOrientations[0] * 5.5), 0.0f);
    }
    this.modelRenderer.updateDisplay();
  }

  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }

  /**
   * フィールドの初期化を行うメソッドです。
   */
  public void initField() {
    this.sensorManager = (SensorManager)this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
    this.sensors = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
    //    this.registerAccerlerometer = false;
    //    this.registerMagneticFieldSensor = false;
    for (int i = 0; i < 3; i++) {
      this.accels[i] = 0.0f;
      this.magneticFields[i] = 0.0f;
      this.orientations[i] = 0.0f;
      this.prevOrientations[i] = 0.0f;
      this.prevAccerlerometer[i] = 0.0f;
    }
  }

  public void getSensor() {
    this.sensors = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
  }

  public Mikity3d getRoot() {
    return this.root;
  }

  public void setModel() {
    Group[] children = this.root.getModel(0).getGroups();
    this.modelRenderer.setChildren(children);
    Mikity3dConfiguration configuration = this.root.getConfiguration(0);
    this.modelRenderer.setConfiguration(configuration);
    this.setModelCount++;
//    this.activity.setModelColumnNumberButton.setEnabled(true);
  }

  protected void setGroupManager() {
    this.manager = new MovableGroupManager(this.root);
    this.manager.setLogCat(new LogCatImpl()); //LogCatのセット    
  }
  
  public void setModelFilePath(String modelFilePath) {
    this.modelFilePath = modelFilePath;
  }
  public void setTimeDataPath(String timeDataPath) {
    this.timeDataPath = timeDataPath;
  }
  
  public void setTimeDataUri(Uri uri) {
    this.timeDataUri = uri;
  }

  public void setDirection() {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    int width = displaymetrics.widthPixels;
    int height = displaymetrics.heightPixels;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
    glView.setLayoutParams(params); 
  }
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    if (this.progressDialog != null) {
    this.progressDialog.dismiss();
    }
    setDirection();
  }
}
