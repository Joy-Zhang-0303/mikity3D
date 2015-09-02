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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import android.content.res.Configuration;
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
public class CanvasFragment extends RoboFragment {

  /** ビュー */
  @InjectView(R.id.layout_fragment_canvas)
  View view;

  GLSurfaceView glView;
  /** 初期のスクリーンサイズならばtrue */
  boolean isInitialScreenSize;

  /** レンダー */
  OpenglesObjectRenderer objectRenderer;

  /** scaleGestureDetector */
  ScaleGestureDetector gestureDetector = null;

  boolean rotating;
  boolean scaling;
  float prevX = 0;
  float prevY = 0;
  
  double scaleValue = 1;

  /** CanvasActivity */
  private CanvasActivity activity;

  Timer timer = new Timer();

  private double[] timeTable;

  /** アニメーションの開始時間 */
  private long startTime;
  /** アニメーションの終了時間。 */
  private double endTime;
  /** アニメーションの一時停止時間 */
  private long pausedTime;
  /** アニメーションの遅延時間 */
  private long delayTime;

  /** Mikity3dモデル */
  Mikity3DModel root;

  ObjectGroupManager manager;

  Map<String, DoubleMatrix> sourceData = new HashMap<String, DoubleMatrix>();

  boolean playable = true;
  AnimationTask animationTask;

  /** プログレスダイアログ */
  ProgressDialog progressDialog;
  /** 無効な時間データが読み込まれたならばtrue */
  private boolean setIllegalSourceData = false;
  /** ポーズボタンが押されたならばtrue */
  boolean isPaused = false;

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if (this.view != null) {
      final ViewGroup parent = (ViewGroup)this.view.getParent();
      parent.removeView(this.view);
      return this.view;
    }

    this.view = inflater.inflate(R.layout.canvas_fragment, container, false);
    this.glView = (GLSurfaceView)this.view.findViewById(R.id.glview1);
    this.getResources();
    this.objectRenderer = new OpenglesObjectRenderer(this.glView);

    this.glView.setRenderer(this.objectRenderer);
    this.isInitialScreenSize = false;

    // 任意のタイミングで再描画する設定
    this.glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    this.activity = (CanvasActivity)getActivity();

    this.gestureDetector = new ScaleGestureDetector(this.getActivity(), this.onScaleGestureListener);

    // タッチ操作の種類によってイベントを取得する
    this.view.setOnTouchListener(new View.OnTouchListener() {

      /**
       * {@inheritDoc}
       */
      public boolean onTouch(View v, MotionEvent event) {
        final int touchCount = event.getPointerCount();
        // タッチイベントをScaleGestureDetector#onTouchEventメソッドに
        CanvasFragment.this.gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
        // タッチした
          case MotionEvent.ACTION_DOWN:
            CanvasFragment.this.rotating = true;
            CanvasFragment.this.prevX = event.getX();
            CanvasFragment.this.prevY = event.getY();
            break;

          // タッチしたまま移動
          case MotionEvent.ACTION_MOVE:
            final float moveX = event.getX() - CanvasFragment.this.prevX;
            final float moveY = event.getY() - CanvasFragment.this.prevY;
            CanvasFragment.this.prevX = event.getX();
            CanvasFragment.this.prevY = event.getY();

            if ((CanvasFragment.this.rotating) && (touchCount == 1)) {
              float rotationY = moveY / 5;
              float rotationZ = moveX / 5;
              CanvasFragment.this.objectRenderer.rotateY(rotationY);
              CanvasFragment.this.objectRenderer.rotateZ(rotationZ);
            }
            if ((touchCount == 2) && (!CanvasFragment.this.scaling)) {
              float translationY = moveX / 2000;
              float translationZ = moveY / 2000;
              CanvasFragment.this.objectRenderer.translateY(translationY);
              CanvasFragment.this.objectRenderer.translateZ(translationZ);
              CanvasFragment.this.rotating = false;
            }
            CanvasFragment.this.rotating = true;
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

        CanvasFragment.this.objectRenderer.updateDisplay();
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
      CanvasFragment.this.rotating = false;
      CanvasFragment.this.scaling = true;
      
      final double scalingRate = 0.5;
      final double newScale = (CanvasFragment.this.scaleValue - (1.0 - CanvasFragment.this.gestureDetector.getScaleFactor()))/scalingRate;
      CanvasFragment.this.objectRenderer.setScale((float)Math.min(20.0, Math.max(0.05, newScale)));
      
      CanvasFragment.this.prevX = CanvasFragment.this.gestureDetector.getFocusX();
      CanvasFragment.this.prevY = CanvasFragment.this.gestureDetector.getFocusY();

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
      
      CanvasFragment.this.scaleValue = CanvasFragment.this.scaleValue - (1.0 - CanvasFragment.this.gestureDetector.getScaleFactor());

      CanvasFragment.this.prevX = CanvasFragment.this.gestureDetector.getFocusX();
      CanvasFragment.this.prevY = CanvasFragment.this.gestureDetector.getFocusY();
      super.onScaleEnd(detector);
    }
  };

//  protected void setScaleValue(double d) {
//    this.scaleValue = d;
//  }

  /**
   * モデルデータをストリームから読み込みます。
   * 
   * @param input モデルファイル
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
  public void loadModelData(InputStream input) throws Mikity3dSerializeDeserializeException {
    this.root = new Mikity3dFactory().loadFile(input);
    prepareObjectGroupManager();
    prepareRenderer();

    final List<GroupModel> rootGroups = this.root.getScene(0).getGroups();
    if (hasAnimation(rootGroups)) {
      this.manager.setHasAnimation(true);
    }
  }

  /**
   * ストリームからソースデータを取り出します。
   * 
   * @param input ソースの入力ストリーム
   * @param filePath ソースのファイルパス
   * @param sourceId ソースID
   */
  public void loadSourceData(final InputStream input, final String filePath, final String sourceId) {
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
        readSourceData(sourceId, input, filePath);

        // input is closed in order to complete reading the data from the input stream.
        try {
          input.close();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

        addSource(sourceId);
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
   * ソースデータを読み込みます。
   * 
   * @param input ソースデータのインプットストリーム
   */
  void readSourceData(final String sourceId, final InputStream input, final String sourceFilePath) {
    try {
      if (sourceFilePath.toLowerCase().endsWith(".mat") || sourceFilePath.startsWith("/document")) { //$NON-NLS-1$ //$NON-NLS-2$
        this.sourceData.put(sourceId, (DoubleMatrix)MatxMatrix.readMatFormat(new InputStreamReader(input)));
      } else {
        this.sourceData.put(sourceId, DoubleMatrix.readCsvFormat(new InputStreamReader(input)).transpose());
      }
      this.setIllegalSourceData = false;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      if (this.progressDialog != null) {
        this.progressDialog.dismiss();
      }
      callExceptionDialogFragment("Please select data file."); //$NON-NLS-1$
    } catch (IllegalArgumentException e) {
      callExceptionDialogFragment("Please select proper data file or set source number size to data size or lower."); //$NON-NLS-1$
    } catch (IllegalAccessError e) {
      if (this.progressDialog != null) {
        this.progressDialog.dismiss();
      }
      final String message = "Source data size is not match model's source number." //$NON-NLS-1$
          + "\nPlease select proper source data or set proper source number."; //$NON-NLS-1$
      callExceptionDialogFragment(message);
      this.setIllegalSourceData = true;
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
   * 
   * @param sourceId ソースID
   */
  public void addSource(String sourceId) {
    try {
      this.manager.addSource(sourceId, this.sourceData.get(sourceId));
    } catch (IllegalAccessError e) {
      if (this.progressDialog != null) {
        this.progressDialog.dismiss();
      }
      final String message = "Source data size is not match model's source number." //$NON-NLS-1$
          + "\nPlease select proper source data or set proper source number."; //$NON-NLS-1$
      final DialogFragment dialogFragment = new ExceptionDialogFragment();
      ((ExceptionDialogFragment)dialogFragment).setMessage(message);
      dialogFragment.show(getFragmentManager(), "exceptionDialogFragment"); //$NON-NLS-1$
      this.setIllegalSourceData = true;
    } catch (IllegalArgumentException e) {
      callExceptionDialogFragment("Please select proper data file or set source number to data size or lower."); //$NON-NLS-1$
    }

    this.setIllegalSourceData = false;
  }

  private void prepareTimeTable() {
    final int dataSize = this.manager.getDataSize();
    this.timeTable = new double[dataSize];
    this.endTime = this.manager.getStopTime();
    for (int i = 0; i < this.timeTable.length; i++) {
      this.timeTable[i] = this.endTime * ((double)i / this.timeTable.length);
    }
  }

  private boolean hasAnimation(List<GroupModel> groups) {
    for (final GroupModel group : groups) {
      final AnimationModel[] animations = group.getAnimations();
      for (final AnimationModel animation : animations) {
        if (animation.exists()) {
          return true;
        }
      }
      if (hasAnimation(group.getGroups())) {
        return true;
      }
    }

    return false;
  }

  /**
   * ObjectGroupManagerを返します。
   * 
   * @return ObjectGroupManager
   */
  public ObjectGroupManager getObjectGroupManager() {
    return this.manager;
  }

  /**
   * オブジェクトレンダラーを返します。
   * 
   * @return オブジェクトレンダラー
   */
  public OpenglesObjectRenderer getObjectRender() {
    return this.objectRenderer;
  }

  /**
   * アニメーションを開始します。
   */
  public void runAnimation() {
    if (this.isPaused == false) {
      this.startTime = SystemClock.uptimeMillis();
      this.delayTime = 0;
    }

    final int animationSpeed = this.activity.ndFragment.animationSpeed;

    if (this.playable == false) {
      this.timer.cancel();
    }
    this.playable = false;

    if (this.sourceData.size() == 0) {
      return;
    }

    if (this.manager.areMovingGroupsRead() == false) {
      return;
    }

    this.manager.prepareMovingGroups();

    prepareTimeTable();

    if (this.timeTable == null) {
      return;
    }

    this.endTime = this.manager.getStopTime();

    if (this.isPaused) {
      this.delayTime += SystemClock.uptimeMillis() - this.pausedTime;
    }

    this.isPaused = false;
    this.animationTask = new AnimationTask(this.startTime, this.endTime, getObjectGroupManager(), getObjectRender(), this.delayTime);
    this.animationTask.setSpeedScale(((double)animationSpeed) / 10);
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
   * モデルを返します。
   * 
   * @return モデル
   */
  public Mikity3DModel getRoot() {
    return this.root;
  }

  /**
   * レンダーを準備します。
   */
  public void prepareRenderer() {
    final List<GroupModel> rootGroups = this.root.getScene(0).getGroups();
    final ConfigurationModel configuration = this.root.getConfiguration(0);

    this.manager.clearObjectGroups();
    this.objectRenderer.setRootGroups(rootGroups, this.manager);
    this.objectRenderer.setConfiguration(configuration);
  }

  /**
   * ObjectGroupManagerを準備します。
   */
  protected void prepareObjectGroupManager() {
    this.manager = new ObjectGroupManager();
  }

  /**
   * アクティビティの画面のレイアウトを取得し、設定します。
   */
  public void configurateDirection() {
    final DisplayMetrics displayMetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

    final int width = displayMetrics.widthPixels;
    final int height = displayMetrics.heightPixels;
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
   * @param pausedTime 一時停止が押された時間
   */
  public void setPuasedTime(long pausedTime) {
    this.pausedTime = pausedTime;
  }
}
