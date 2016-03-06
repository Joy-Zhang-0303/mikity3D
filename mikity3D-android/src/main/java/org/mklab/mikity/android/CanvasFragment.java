/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.android.renderer.OpenglesObjectRenderer;
import org.mklab.mikity.control.AnimationTaskListener;
import org.mklab.mikity.model.GroupObjectManager;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DMarshaller;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.config.EyeModel;
import org.mklab.mikity.model.xml.simplexml.config.LightModel;
import org.mklab.mikity.model.xml.simplexml.config.LookAtPointModel;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.nfc.matrix.DoubleMatrix;
import org.mklab.nfc.matx.MatxMatrix;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
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
public class CanvasFragment extends Fragment {

  /** ビュー。 */
  View view;
  /** GLのためのビュー。 */
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

  Timer timer = new Timer();

  private double[] timeTable;

  /** アニメーションの再生速度倍率 */
  int animationSpeedRate = 1000;

  /** アニメーションの開始時間 */
  private long startTime;
  /** アニメーションの終了時間。 */
  private double stopTime;
  /** アニメーションの一時停止時間 */
  private long pausedTime;
  /** アニメーションの遅延時間 */
  private long delayTime;

  /** Mikity3dモデル */
  Mikity3DModel root;

  GroupObjectManager manager;

  OpenglesModeler modeler;

  Map<String, DoubleMatrix> sourceData = new HashMap<String, DoubleMatrix>();

  boolean playable = true;
  AnimationTask animationTask;

  /** プログレスダイアログ */
  ProgressDialog progressDialog;

  /** ポーズボタンが押されたならばtrue */
  boolean isPaused = false;

  /** 繰り返し再生中ならばtrue */
  boolean isRepeating = false;

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.view = inflater.inflate(R.layout.fragment_canvas, container, false);
    this.glView = (GLSurfaceView)this.view.findViewById(R.id.glview1);
    this.getResources();

    final ConfigurationModel configuration = new ConfigurationModel();
    configuration.setEye(new EyeModel(5.0f, 0.0f, 0.0f));
    configuration.setLookAtPoiint(new LookAtPointModel(0.0f, 0.0f, 0.0f));
    configuration.setLight(new LightModel(10.0f, 10.0f, 20.0f));

    this.objectRenderer = new OpenglesObjectRenderer(this.glView, configuration);
    this.modeler = new OpenglesModeler(this.objectRenderer);

    this.glView.setRenderer(this.objectRenderer);
    this.isInitialScreenSize = false;

    // 任意のタイミングで再描画する設定
    this.glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

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
      final double newScale = (CanvasFragment.this.scaleValue - (1.0 - CanvasFragment.this.gestureDetector.getScaleFactor())) / scalingRate;
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

  /**
   * モデルデータを入力ストリームから読み込みます。
   * 
   * @param input モデルファイル
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
  public void loadModelData(InputStream input) throws Mikity3dSerializeDeserializeException {
    this.root = new Mikity3dFactory().loadFile(input);
    this.manager = new GroupObjectManager();
    
    prepareRenderer();
    prepareModeler();

    prepareAnimation();
  }

  /**
   * アニメーションの準備を行います。
   */
  public void prepareAnimation() {
    final List<GroupModel> rootGroups = this.root.getScene(0).getGroups();
    if (hasAnimation(rootGroups)) {
      this.manager.setHasAnimation(true);
    }
  }
  
  /**
   * 新しいモデルデータを準備します。
   */
  public void createNewModelData() {
    this.root = Mikity3dFactory.createModel();
    this.manager = new GroupObjectManager();
    
    prepareRenderer();
    prepareModeler();

    prepareAnimation();
  }

  /**
   * モデルデータを出力ストリームへ出力します。
   * 
   * @param output 出力ストリーム
   * @throws Mikity3dSerializeDeserializeException ファイルに保存できない場合  
   */
  public void saveModelData(OutputStream output) throws Mikity3dSerializeDeserializeException {
    final Mikity3DMarshaller marshaller = new Mikity3DMarshaller(this.root);
    marshaller.marshal(output);
    this.modeler.setIsChanged(false);
  }

  /**
   * モデラーを準備します。
   */
  private void prepareModeler() {
    this.modeler.setRoot(this.root);
    this.modeler.setManager(this.manager);
    this.modeler.setIsChanged(false);
  }

  /**
   * ストリームからソースデータをバックグランドで取り出します。
   * 
   * @param input ソースの入力ストリーム
   * @param filePath ソースのファイルパス
   * @param sourceId ソースID
   */
  public void loadSourceDataInBackground(final InputStream input, final String filePath, final String sourceId) {
    final AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {

      /**
       * {@inheritDoc}
       */
      @Override
      protected void onPreExecute() {
        CanvasFragment.this.progressDialog = new ProgressDialog(getActivity());
        CanvasFragment.this.progressDialog.setCanceledOnTouchOutside(false);
        CanvasFragment.this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        CanvasFragment.this.progressDialog.setMessage(getString(R.string.now_loading));
        CanvasFragment.this.progressDialog.show();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected Void doInBackground(String... arg0) {
        loadSourceData(input, filePath, sourceId);

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
   * @param filePath ソースファイルのパス
   * @param sourceId ソースファイルID
   */
  void loadSourceData(final InputStream input, final String filePath, final String sourceId) {
    try {
      final DoubleMatrix data;
      
      if (filePath.toLowerCase().endsWith(".mat") || filePath.startsWith("/document")) { //$NON-NLS-1$ //$NON-NLS-2$
        data = (DoubleMatrix)MatxMatrix.readMatFormat(new InputStreamReader(input));
      } else if (filePath.toLowerCase().endsWith(".csv") || filePath.startsWith("/document")) { //$NON-NLS-1$ //$NON-NLS-2$
        data = DoubleMatrix.readCsvFormat(new InputStreamReader(input)).transpose();
      } else if (filePath.toLowerCase().endsWith(".txt") || filePath.startsWith("/document")) { //$NON-NLS-1$ //$NON-NLS-2$
        data = DoubleMatrix.readSsvFormat(new InputStreamReader(input)).transpose();
      } else {
        data = DoubleMatrix.readSsvFormat(new InputStreamReader(input)).transpose();
      }

      this.sourceData.put(sourceId, data);
    } catch (IOException e) {
      if (this.progressDialog != null) {
        this.progressDialog.dismiss();
      }

      showMessageInDialog("Please select proper source file."); //$NON-NLS-1$
    }
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
      showMessageInDialog(message);
    } catch (IllegalArgumentException e) {
      if (this.progressDialog != null) {
        this.progressDialog.dismiss();
      }

      final String message = "Please select proper source data or set source number to data size or lower."; //$NON-NLS-1$
      showMessageInDialog(message);
    }
  }

  private void prepareTimeTable() {
    final int dataSize = this.manager.getDataSize();
    this.timeTable = new double[dataSize];
    this.stopTime = this.manager.getStopTime();
    for (int i = 0; i < this.timeTable.length; i++) {
      this.timeTable[i] = this.stopTime * ((double)i / this.timeTable.length);
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
  public GroupObjectManager getObjectGroupManager() {
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
   * アニメーションを繰り返し再生します。
   */
  public void repeatAnimation() {
    this.isRepeating = true;
    runAnimation();
  }

  /**
   * アニメーションを開始します。
   */
  public void runAnimation() {
    if (this.manager == null) {
      showMessageInDialog(getString(R.string.modelDataIsNotReady));
      return;
    }
    if (this.manager.isSourceReady() == false) {
      showMessageInDialog(getString(R.string.sourceDataIsNotReady));
      return;
    }

    if (this.playable == false) {
      this.timer.cancel();
    }

    if (this.isPaused == false) {
      this.startTime = SystemClock.uptimeMillis();
      this.delayTime = 0;
    }

    this.manager.prepareMovingGroupObjects();

    prepareTimeTable();

    this.stopTime = this.manager.getStopTime();

    if (this.isPaused) {
      this.delayTime += SystemClock.uptimeMillis() - this.pausedTime;
    }
    this.isPaused = false;

    this.animationTask = new AnimationTask(this.startTime, this.stopTime, getObjectGroupManager(), getObjectRender(), this.delayTime);
    this.animationTask.setSpeedScale(this.animationSpeedRate / 1000.0);
    this.animationTask.addAnimationTaskListener(new AnimationTaskListener() {

      /**
       * {@inheritDoc}
       */
      public void tearDownAnimation() {
        CanvasFragment.this.playable = true;
        if (CanvasFragment.this.isRepeating) {
          runAnimation();
        }
      }

      /**
       * {@inheritDoc}
       */
      public void setUpAnimation() {
        // nothing to do
      }
    });

    this.playable = false;
    this.timer = new Timer();
    this.timer.schedule(this.animationTask, 0, 30);
  }

  /**
   * アニメーションを停止します。
   */
  public void stopAnimation() {
    this.timer.cancel();
    this.playable = true;
    this.isPaused = false;
    this.isRepeating = false;
  }

  /**
   * アニメーションを一時停止します。
   */
  public void pauseAnimation() {
    if (this.isPaused) {
      return;
    }

    if (this.animationTask != null) {
      this.timer.cancel();
      this.isPaused = true;
      this.playable = true;
      setPuasedTime(SystemClock.uptimeMillis());
    }
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
  private void prepareRenderer() {
    final List<GroupModel> rootGroups = this.root.getScene(0).getGroups();
    final ConfigurationModel configuration = this.root.getConfiguration(0);

    this.manager.clearGroupObjects();
    this.objectRenderer.setRootGroups(rootGroups, this.manager);
    this.objectRenderer.setConfiguration(configuration);
  }

  /**
   * モデルをリセットし、初期状態に戻します。
   */
  public void resetToInitialState() {
    this.objectRenderer.resetToInitialState();
    this.objectRenderer.updateDisplay();
  }

  /**
   * グリッドを表示するか設定します。
   * 
   * @param isGridShowing 表示するならばtrue
   */
  public void setGridShowing(boolean isGridShowing) {
    if (this.root == null) {
      return;
    }
    this.root.getConfiguration(0).getBaseCoordinate().setGridShowing(isGridShowing);
  }

  /**
   * グリッドを表示するか判定します。
   * 
   * @return 表示するならばtrue
   */
  public boolean isGridShowing() {
    if (this.root == null) {
      return false;
    }
    return this.root.getConfiguration(0).getBaseCoordinate().isGridShowing();
  }

  /**
   * 座標軸を表示するか設定します。
   * 
   * @param isAxisShowing 表示するならばtrue
   */
  public void setAxisShowing(boolean isAxisShowing) {
    if (this.root == null) {
      return;
    }
    this.root.getConfiguration(0).getBaseCoordinate().setAxisShowing(isAxisShowing);
  }

  /**
   * 座標軸を表示するか判定します。
   * 
   * @return 表示するならばtrue
   */
  public boolean isAxisShowing() {
    if (this.root == null) {
      return false;
    }
    return this.root.getConfiguration(0).getBaseCoordinate().isAxisShowing();
  }

//  /**
//   * ObjectGroupManagerを準備します。
//   */
//  private void prepareObjectGroupManager() {
//    this.manager = new GroupObjectManager();
//  }

  /**
   * アクティビティの画面のレイアウトを取得し、設定します。
   */
  private void configurateDirection() {
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

  /**
   * ダイアログメッセージを表示します。
   * 
   * @param message メッセージ
   */
  private void showMessageInDialog(String message) {
    final MessageDialogFragment dialog = new MessageDialogFragment();
    final Bundle arguments = new Bundle();
    arguments.putString("message", message); //$NON-NLS-1$
    dialog.setArguments(arguments);
    dialog.show(getActivity().getSupportFragmentManager(), "messageDialogFragment"); //$NON-NLS-1$
  }

  /**
   * モデルデータが変更されているか判定します。
   * 
   * @return モデルデータが変更されていればtrue
   */
  public boolean isModelChanged() {
    if (this.modeler == null) {
      return false;
    }
    return this.modeler.isChanged();
  }
}
