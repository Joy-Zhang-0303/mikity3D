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
import org.mklab.mikity.android.view.renderer.opengles.OpenglesObjectRenderer;
import org.mklab.mikity.control.AnimationTaskListener;
import org.mklab.mikity.model.ObjectGroupManager;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.nfc.matrix.DoubleMatrix;
import org.mklab.nfc.matx.MatxMatrix;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * モデル描画用のフラグメントを表すクラスです。
 * 
 * @author soda
 * @version $Revision$, 2014/10/10 
 */
public class CanvasFragment extends RoboFragment implements SensorEventListener {

  GLSurfaceView glView;
  boolean mIsInitScreenSize;
  
  /** レンダー */
  OpenglesObjectRenderer modelRenderer;
  
  /** scaleGestureDetector */
  ScaleGestureDetector gesDetect = null;
  
  boolean rotationing;
  boolean scaling;
  double scaleValue = 1;
  float prevX = 0;
  float prevY = 0;
  
  /** CanvasActivity */
  private CanvasActivity activity;
  
  Timer timer = new Timer();

  /** Mikity3dモデル */
  Mikity3DModel root;
  
  ObjectGroupManager manager;
  DoubleMatrix data;
  private double[] timeTable;
  private double endTime;
  private int animationSpeed;
  boolean playable;
  AnimationTask animationTask;
  /** センサーマネージャー */
  SensorManager sensorManager;
  /** センサーからの加速度を格納する配列 */
  private float[] accels = new float[3];
  /** センサーからの地磁気を格納する配列 */
  private float[] magneticFields = new float[3];
  /** センサーから算出した端末の角度を格納する配列 */
  private float[] orientations = new float[3];
  /** 角度の基準値 */
  private float[] prevOrientations = new float[3];
  /** 端末の角度を3Dオブジェクトに反映させるならばtrue */
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
  /** 加速度センサーの値を3Dオブジェクトに反映させるならばtrue */
  boolean useAccelerSensor = false;
  /** センサー */
  List<Sensor> sensors;
  /** モデルデータパス */
  private String modelFilePath;
  /** 時間データパス */
  private String timeDataPath;
  /** 時間データURI */
  protected Uri timeDataUri;
  /** 画面回転時に、時間データを読み込み中ならばtrue */
  protected boolean rotateTimeDataFlag = false;
  /** ビュー */
  @InjectView(R.id.layout_fragment_canvas)
  View view;
  /** プログレスダイアログ */
  ProgressDialog progressDialog;
  /** 無効な時間データが読み込まれたならばtrue */
  private boolean setIllegalTimeData = false;
  /** ポーズボタンが押されたならばtrue */
  boolean isPause = false;
  /** アニメーションの開始時間 */
  private long startTime;
  /** アニメーションの一時停止時間 */
  private long stopTime;
  /** アニメーションの待ち時間 */
  private long waitTime;

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if (this.view != null) {
      ViewGroup parent = (ViewGroup)this.view.getParent();
      parent.removeView(this.view);
      return this.view;
    }

    this.view = inflater.inflate(R.layout.canvas_fragment, container, false);
    this.glView = (GLSurfaceView)this.view.findViewById(R.id.glview1);
    this.getResources();
    this.modelRenderer = new OpenglesObjectRenderer(this.glView);
    // 描画のクラスを登録する
    this.glView.setRenderer(this.modelRenderer);
    this.mIsInitScreenSize = false;
    initField();
    // 任意のタイミングで再描画する設定
    this.glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    this.activity = (CanvasActivity)getActivity();

    // ScaleGestureDetecotorクラスのインスタンス生成
    this.gesDetect = new ScaleGestureDetector(this.getActivity(), this.onScaleGestureListener);
    
    // タッチ操作の種類によってイベントを取得する
    this.view.setOnTouchListener(new View.OnTouchListener() {

      /**
       * {@inheritDoc}
       */
      public boolean onTouch(View v, MotionEvent event) {
        final float transferAmountX;
        final float transferAmountY;
        final int touchCount = event.getPointerCount();
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
              CanvasFragment.this.modelRenderer.setRotationY(-transferAmountY);
              CanvasFragment.this.modelRenderer.setRotationZ(-transferAmountX);
            }
            if ((touchCount == 2) && (!CanvasFragment.this.scaling)) {
              final float Touch3DModelProportion = 1000.0f;
              CanvasFragment.this.modelRenderer.setTranslationY(transferAmountX / Touch3DModelProportion);
              CanvasFragment.this.modelRenderer.setTranslationZ(transferAmountY / Touch3DModelProportion);
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
    return this.view;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  // スケールジェスチャーイベントを取得
  private final SimpleOnScaleGestureListener onScaleGestureListener = new SimpleOnScaleGestureListener() {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
      CanvasFragment.this.rotationing = false;
      CanvasFragment.this.scaling = true;
      final double scalingFactor = 0.5;
      final double newScale = Math.max(0.01, CanvasFragment.this.scaleValue - (1.0 - CanvasFragment.this.gesDetect.getScaleFactor())) / scalingFactor;
      CanvasFragment.this.modelRenderer.setScale((float)newScale);
      CanvasFragment.this.prevX = CanvasFragment.this.gesDetect.getFocusX();
      CanvasFragment.this.prevY = CanvasFragment.this.gesDetect.getFocusY();

      return super.onScale(detector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
      CanvasFragment.this.scaling = true;
      return super.onScaleBegin(detector);
    }

    /**
     * {@inheritDoc}
     */
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
   * モデルデータをストリームから読み込みます。
   * 
   * @param input モデルファイル
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
  public void loadModelData(InputStream input) throws Mikity3dSerializeDeserializeException {
    this.root = new Mikity3dFactory().loadFile(input);
    prepareRenderer();
    setGroupManager();
  }

  /**
   * ストリームから時間データを取り出します。
   * 
   * @param input 時間データのパス
   */
  public void loadTimeData(final InputStream input) {
    final AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {

      /**
       * {@inheritDoc}
       */
      @Override
      protected void onPreExecute() {
        CanvasFragment.this.progressDialog = new ProgressDialog(getActivity());
        CanvasFragment.this.progressDialog.setCanceledOnTouchOutside(false);
        CanvasFragment.this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        CanvasFragment.this.progressDialog.setMessage("Now Loading..."); //$NON-NLS-1$
        CanvasFragment.this.progressDialog.show();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected Void doInBackground(String... arg0) {
        readTimeData(input);
        
        // input is closed in order to complete reading the data from the input stream.
        try {
          input.close();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        
        final String id = "1"; //$NON-NLS-1$
        
        setTimeData(id);
        return null;
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected void onPostExecute(Void result) {
        CanvasFragment.this.progressDialog.dismiss();
      }

    };
    
    task.execute();
  }

  /**
   * 時系列データを読み込みます。
   * 
   * @param input 時系列データのインプットストリーム
   */
  void readTimeData(final InputStream input) {
    try {
      this.data = (DoubleMatrix)MatxMatrix.readMatFormat(new InputStreamReader(input));
      this.setIllegalTimeData = false;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      if (this.progressDialog != null) {
        this.progressDialog.dismiss();
      }
      callExceptionDialogFragment("Please select data file."); //$NON-NLS-1$
    } catch (IllegalArgumentException e) {
      callExceptionDialogFragment("Please select proper data file or set column number size to data size or lower."); //$NON-NLS-1$
    } catch (IllegalAccessError e) {
      if (this.progressDialog != null) {
        this.progressDialog.dismiss();
      }
      final String message = "Time data size is not match model's column number." //$NON-NLS-1$
          + "\nPlease select proper time data or set proper column number."; //$NON-NLS-1$
      callExceptionDialogFragment(message);
      this.setIllegalTimeData = true;
    }
  }

  /**
   * 例外がキャッチされた時、その例外に対するダイアログを表示します。
   * 
   * @param message 例外に対する返答
   */
  public void callExceptionDialogFragment(String message) {
    final DialogFragment dialogFragment = new ExceptionDialogFragment();
    ((ExceptionDialogFragment)dialogFragment).setMessage(message);
    dialogFragment.show(getFragmentManager(), "exceptionDialogFragment"); //$NON-NLS-1$
  }

  /**
   * MovableGroupManagerに時間データを登録します。
   * @param id ID
   */
  public void setTimeData(String id) {
    try {
      this.manager.addSource(id, this.data);
    } catch (IllegalAccessError e) {
      if (this.progressDialog != null) {
        this.progressDialog.dismiss();
      }
      final String message = "Time data size is not match model's column number." //$NON-NLS-1$
          + "\nPlease select proper time data or set proper column number."; //$NON-NLS-1$
      final DialogFragment dialogFragment = new ExceptionDialogFragment();
      ((ExceptionDialogFragment)dialogFragment).setMessage(message);
      dialogFragment.show(getFragmentManager(), "exceptionDialogFragment"); //$NON-NLS-1$
      this.setIllegalTimeData = true;
    } catch (IllegalArgumentException e) {
      callExceptionDialogFragment("Please select proper data file or set column number to data size or lower."); //$NON-NLS-1$
    }

    final GroupModel rootGroup = this.root.getScene(0).getGroup(0);
    checkAnimation(rootGroup);

    final int dataSize = this.manager.getDataSize();

    this.timeTable = new double[dataSize];

    this.endTime = this.manager.getEndTime();
    for (int i = 0; i < this.timeTable.length; i++) {
      this.timeTable[i] = this.endTime * ((double)i / this.timeTable.length);
    }
    this.setIllegalTimeData = false;
  }

  private void checkAnimation(GroupModel parent) {
    final GroupModel[] groups = parent.getGroups();
    for (final GroupModel group : groups) {
      final AnimationModel[] animations = group.getAnimations();
      for (final AnimationModel animation : animations) {
        if (animation.exists()) {
          this.manager.setHasAnimation(true);
        }
      }
      checkAnimation(group);
    }
  }

  /**
   * MovableGroupManagerを返します。
   * 
   * @return manager
   */
  public ObjectGroupManager getManager() {
    return this.manager;
  }

  /**
   * モデルレンダラーを返します。
   * 
   * @return modelRenderer
   */
  public OpenglesObjectRenderer getModelRender() {
    return this.modelRenderer;
  }

  /**
   * アニメーションを開始します。
   */
  public void runAnimation() {
    if (this.isPause == false) {
      this.startTime = SystemClock.uptimeMillis();
      this.waitTime = 0;
    }
    this.animationSpeed = this.activity.ndFragment.animationSpeed;
    if (this.playable == false) {
      this.timer.cancel();
    }
    if (this.data == null || this.timeTable == null) {
      return;
    }
    this.playable = false;
    
    this.manager.prepareMovingGroups();
    
    this.endTime = this.manager.getEndTime();
    
    if (this.isPause) {
      this.waitTime += SystemClock.uptimeMillis() - this.stopTime;
    }
    
    this.isPause = false;
    this.animationTask = new AnimationTask(this.startTime, this.endTime, getManager(), getModelRender(), this.waitTime);
    this.animationTask.setSpeedScale((double)this.animationSpeed / 10);
    this.animationTask.addAnimationTaskListener(new AnimationTaskListener() {

      /**
       * {@inheritDoc}
       */
      public void tearDownAnimation() {
        CanvasFragment.this.playable = true;
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
   * {@inheritDoc}
   */
  public void onSensorChanged(SensorEvent event) {

    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
      for (int i = 0; i < 3; i++) {
        this.accels[i] = event.values[i];
      }

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
      for (int i = 0; i < 3; i++) {
        this.magneticFields[i] = event.values[i];
      }
    }

    if (this.useOrientationSensor) {
      final float[] R = new float[9];
      final float[] I = new float[9];
      SensorManager.getRotationMatrix(R, I, this.accels, this.magneticFields);

      SensorManager.getOrientation(R, this.orientations);

      final float[] diffOrientations = new float[3];

      for (int i = 0; i < this.orientations.length; i++) {
        diffOrientations[i] = this.orientations[i] - this.prevOrientations[i];
        if (Math.abs(diffOrientations[i]) < 0.05) {
          diffOrientations[i] = (float)0.0;
        }
      }

      for (int i = 0; i < this.orientations.length; i++) {
        this.prevOrientations[i] = this.orientations[i];

      }

      this.modelRenderer.setRotationY((float)Math.toDegrees(diffOrientations[2] * 3.5));
      this.modelRenderer.setRotationZ(0.0f);
    }
    this.modelRenderer.updateDisplay();
  }

  /**
   * {@inheritDoc}
   */
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    //nothing to do
  }

  /**
   * フィールドを初期化します。
   */
  public void initField() {
    this.sensorManager = (SensorManager)this.getActivity().getSystemService(Context.SENSOR_SERVICE);
    this.sensors = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
    for (int i = 0; i < 3; i++) {
      this.accels[i] = 0.0f;
      this.magneticFields[i] = 0.0f;
      this.orientations[i] = 0.0f;
      this.prevOrientations[i] = 0.0f;
      this.prevAccerlerometer[i] = 0.0f;
    }
  }

  /**
   * センサーを設定します。
   */
  public void setSensor() {
    this.sensors = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
  }

  /**
   * Mikity3dを返します。
   * 
   * @return root
   */
  public Mikity3DModel getRoot() {
    return this.root;
  }

  /**
   * レンダーを準備します。
   */
  public void prepareRenderer() {
    final GroupModel[] rootGroups = this.root.getScene(0).getGroups();
    final ConfigurationModel configuration = this.root.getConfiguration(0);

    this.manager.clearObjectGroups();
    this.modelRenderer.setRootGroups(rootGroups, this.manager);
    this.modelRenderer.setConfiguration(configuration);
  }

  /**
   * MovableGroupManagerを設定します。
   */
  protected void setGroupManager() {
    this.manager = new ObjectGroupManager();
  }

  /**
   * モデルファイルパスを設定します。
   * 
   * @param modelFilePath モデルファイルパス
   */
  public void setModelFilePath(String modelFilePath) {
    this.modelFilePath = modelFilePath;
  }

  /**
   * 時間データパスを設定します。
   * 
   * @param timeDataPath 時間データパス
   */
  public void setTimeDataPath(String timeDataPath) {
    this.timeDataPath = timeDataPath;
  }

  /**
   * 時間データのURIを設定します。
   * 
   * @param uri 時間データのURI
   */
  public void setTimeDataUri(Uri uri) {
    this.timeDataUri = uri;
  }

  /**
   * アクティビティの画面のレイアウトを取得し、設定します。
   */
  public void configurateDirection() {
    final DisplayMetrics displaymetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    final int width = displaymetrics.widthPixels;
    final int height = displaymetrics.heightPixels;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
    this.glView.setLayoutParams(params);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    if (this.progressDialog != null) {
      this.progressDialog.dismiss();
    }
    configurateDirection();
  }

  /**
   * 一時停止が押された時間を設定します。
   * 
   * @param stopTime 一時停止が押された時間
   */
  public void setStopTime(long stopTime) {
    this.stopTime = stopTime;
  }
}
