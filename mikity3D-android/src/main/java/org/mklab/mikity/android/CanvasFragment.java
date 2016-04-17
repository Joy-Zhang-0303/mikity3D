/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * モデル描画用のフラグメントを表すクラスです。
 * 
 * @author soda
 * @version $Revision$, 2014/10/10
 */
public class CanvasFragment extends Fragment implements View.OnTouchListener, OnScaleGestureListener {
  /** ビュー。 */
  private View view;
  /** GLのためのビュー。 */
  GLSurfaceView glView;

  /** 初期のスクリーンサイズならばtrue */
  boolean isInitialScreenSize;

  /** レンダー */
  OpenglesObjectRenderer objectRenderer;

  /** scaleGestureDetector */
  private ScaleGestureDetector gestureDetector = null;

  private boolean rotating;
  private boolean scaling;
  private float prevX = 0;
  private float prevY = 0;

  double scaleValue = 1;

  private Timer timer = new Timer();

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

  private GroupObjectManager manager;

  OpenglesModeler modeler;

  Map<String, DoubleMatrix> sourceData = new HashMap<String, DoubleMatrix>();

  boolean playable = true;
  private AnimationTask animationTask;

  /** プログレスダイアログ */
  ProgressDialog progressDialog;

  /** ポーズボタンが押されたならばtrue */
  private boolean isPaused = false;

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
    super.onCreateView(inflater, container, savedInstanceState);
    
    this.view = inflater.inflate(R.layout.fragment_canvas, container, false);
    this.view.setOnTouchListener(this);

    this.glView = (GLSurfaceView)this.view.findViewById(R.id.glview1);
    this.glView.setEGLConfigChooser(8, 8, 8, 8, 16, 8);
    
    getResources();

    final ConfigurationModel configuration = new ConfigurationModel();
    configuration.setEye(new EyeModel(5.0f, 0.0f, 0.0f));
    configuration.setLookAtPoiint(new LookAtPointModel(0.0f, 0.0f, 0.0f));
    configuration.setLight(new LightModel(10.0f, 10.0f, 20.0f));

    this.objectRenderer = new OpenglesObjectRenderer(this.glView, configuration);
    this.modeler = new OpenglesModeler(this.objectRenderer);
    
    this.glView.setRenderer(this.objectRenderer);
    this.glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);        
    this.isInitialScreenSize = false;

    createNewModelData();
    
    this.gestureDetector = new ScaleGestureDetector(this.getActivity(), this);

    return this.view;
  }
  
  /**
   * {@inheritDoc}
   * 
   * タッチ操作の種類によってイベントを取得します。
   */
  public boolean onTouch(View v, MotionEvent event) {
    final int touchCount = event.getPointerCount();

    // タッチイベントをScaleGestureDetector#onTouchEventメソッドに設定します。
    this.gestureDetector.onTouchEvent(event);

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        this.rotating = true;
        this.prevX = event.getX();
        this.prevY = event.getY();
        break;
      case MotionEvent.ACTION_MOVE:
        final float moveX = event.getX() - this.prevX;
        final float moveY = event.getY() - this.prevY;
        this.prevX = event.getX();
        this.prevY = event.getY();

        if ((this.rotating) && (touchCount == 1)) {
          float rotationY = moveY / 5;
          float rotationZ = moveX / 5;
          this.objectRenderer.rotateY(rotationY);
          this.objectRenderer.rotateZ(rotationZ);
        }

        if ((touchCount == 2) && (!this.scaling)) {
          float translationY = moveX / 2000;
          float translationZ = moveY / 2000;
          this.objectRenderer.translateY(translationY);
          this.objectRenderer.translateZ(translationZ);
          this.rotating = false;
        }
        this.rotating = true;
        break;
      case MotionEvent.ACTION_UP:
        this.prevX = event.getX();
        this.prevY = event.getY();
        break;
      case MotionEvent.ACTION_CANCEL:
        break;

      default:
        break;
    }

    this.objectRenderer.updateDisplay();
    return true;
  }
  
  /**
   * {@inheritDoc}
   */
  public boolean onScale(ScaleGestureDetector detector) {
    this.rotating = false;
    this.scaling = true;

    final double scalingRate = 0.5;
    final double newScale = (this.scaleValue - (1.0 - this.gestureDetector.getScaleFactor())) / scalingRate;
    this.objectRenderer.setScale((float)Math.min(20.0, Math.max(0.05, newScale)));

    this.prevX = this.gestureDetector.getFocusX();
    this.prevY = this.gestureDetector.getFocusY();

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean onScaleBegin(ScaleGestureDetector detector) {
    this.scaling = true;
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void onScaleEnd(ScaleGestureDetector detector) {
    this.scaling = false;

    this.scaleValue = this.scaleValue - (1.0 - this.gestureDetector.getScaleFactor());

    this.prevX = this.gestureDetector.getFocusX();
    this.prevY = this.gestureDetector.getFocusY();
  }
  
  /**
   * LoadModelDataTaskのコールバックを表すインターフェースです。
   * 
   * @author koga
   * @version $Revision$, 2016/03/07
   */
  public static interface LoadModelDataTaskCallback {
    /**
     * 成功した場合に呼ばれるメソッドです。 
     * @param input 入力ストリーム
     * @param fileName ファイル名
     */
    void onSuccessLoadModelData(InputStream input, String fileName);
    /**
     * 失敗した場合に呼ばれるメソッドです。
     * @param input 入力ストリーム
     * @param fileName ファイル名
     */
    void onFailedLoadModelData(InputStream input, String fileName);
  }
  
  /**
   * ストリームからモデルデータをバックグランドで取り出します。
   * 
   * @param input モデルの入力ストリーム
   * @param fileName ファイル名
   * @param callback コールバック
   */
  public void loadModelDataInBackground(final InputStream input, String fileName, LoadModelDataTaskCallback callback) {
    final LoadModelDataTask task = new LoadModelDataTask(input, fileName, callback); 
    task.execute();
  }

  /**
   * モデルデータを入力ストリームから読み込みます。
   * 
   * @param input モデルファイル
   */
  boolean loadModelData(InputStream input) {
    try {
      this.root = new Mikity3dFactory().loadFile(input);
      this.manager = new GroupObjectManager();
    } catch (@SuppressWarnings("unused") Mikity3dSerializeDeserializeException e) {
      showMessageInDialog(getString(R.string.please_select_model_file));
      return false;
    }
    
    return true;
  }
  
  class LoadModelDataTask extends AsyncTask<String, Void, Boolean> {
    private InputStream input;
    private String fileName;
    private LoadModelDataTaskCallback callback;
    
    public LoadModelDataTask(final InputStream input, String fileName, LoadModelDataTaskCallback callback) {
      this.input = input;
      this.fileName = fileName;
      this.callback = callback;
    }
    
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
    protected Boolean doInBackground(String... arg0) {
      final boolean result = loadModelData(this.input);
      return Boolean.valueOf(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPostExecute(Boolean result) {
      CanvasFragment.this.progressDialog.dismiss();
      
      if (result.booleanValue()) {
        final Toast toast = Toast.makeText(getActivity(), getString(R.string.loadedSuccessfully), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        toast.show();
        
        prepareRenderer();
        prepareModeler();
        prepareAnimation();
        
        this.callback.onSuccessLoadModelData(this.input, this.fileName);
      } else {
        this.callback.onFailedLoadModelData(this.input, this.fileName);
      }
    }    
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
   * ストリームへモデルデータをバックグランドで保存します。
   * 
   * @param output モデルの出力ストリーム
   * @param fileName ファイル名
   * @param callback コールバック
   */
  public void saveModelDataInBackground(final OutputStream output, String fileName, SaveModelDataTaskCallback callback) {
    final SaveModelDataTask task = new SaveModelDataTask(output, fileName, callback); 
    task.execute();
  }
  
  /**
   * モデルデータを出力ストリームへ出力します。
   * 
   * @param output 出力ストリーム
   */
  boolean saveModelData(OutputStream output) {
    try {
      final Mikity3DMarshaller marshaller = new Mikity3DMarshaller(this.root);
      marshaller.marshal(output);
      return true;
    } catch (Mikity3dSerializeDeserializeException e) {
      showMessageInDialog(e.getMessage());
      return false;
    }
  }
  
  class SaveModelDataTask extends AsyncTask<String, Void, Boolean> {
    private OutputStream output;
    private String fileName;
    private SaveModelDataTaskCallback callback;
    
    public SaveModelDataTask(final OutputStream output, String fileName, SaveModelDataTaskCallback callback) {
      this.output = output;
      this.fileName = fileName;
      this.callback = callback;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPreExecute() {
      CanvasFragment.this.progressDialog = new ProgressDialog(getActivity());
      CanvasFragment.this.progressDialog.setCanceledOnTouchOutside(false);
      CanvasFragment.this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      CanvasFragment.this.progressDialog.setMessage(getString(R.string.now_saving));
      CanvasFragment.this.progressDialog.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Boolean doInBackground(String... arg0) {
      final boolean result = saveModelData(this.output);
      return Boolean.valueOf(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPostExecute(Boolean result) {
      CanvasFragment.this.progressDialog.dismiss();
      
      if (result.booleanValue()) {
        final Toast toast = Toast.makeText(getActivity(), getString(R.string.savedSuccessfully), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        toast.show();
        
        CanvasFragment.this.modeler.setIsChanged(false);
        this.callback.onSuccessSaveModelData(this.output, this.fileName);
      } else {
        this.callback.onFailedSaveModelData(this.output, this.fileName);
      }
    }    
  }
  
  /**
   * SaveModelDataTaskのコールバックを表すインターフェースです。
   * 
   * @author koga
   * @version $Revision$, 2016/03/07
   */
  public static interface SaveModelDataTaskCallback {
    /**
     * 成功した場合に呼ばれるメソッドです。 
     * @param output 出力ストリーム
     * @param fileName ファイル名
     */
    void onSuccessSaveModelData(OutputStream output, String fileName);
    /**
     * 失敗した場合に呼ばれるメソッドです。
     * @param output 出力ストリーム
     * @param fileName ファイル名
     */
    void onFailedSaveModelData(OutputStream output, String fileName);
  }

  /**
   * モデラーを準備します。
   */
  void prepareModeler() {
    this.modeler.setRoot(this.root);
    this.modeler.setManager(this.manager);
    this.modeler.setIsChanged(false);
  }

  /**
   * ストリームからソースデータをバックグランドで取り出します。
   * 
   * @param input ソースの入力ストリーム
   * @param filePath ソースのファイルパス
   * @param fileName ソースのファイル名
   * @param sourceId ソースID
   * @param callback コールバック
   */
  public void loadSourceDataInBackground(final InputStream input, final String filePath, final String fileName, final String sourceId, LoadSourceDataTaskCallback callback) {
    final LoadSourceDataTask task = new LoadSourceDataTask(input, filePath, fileName, sourceId, callback); 
    task.execute();
  }
  
  /**
   * LoadSourceDataTaskのコールバックを表すインターフェースです。
   * 
   * @author koga
   * @version $Revision$, 2016/03/07
   */
  public static interface LoadSourceDataTaskCallback {
    /**
     * 成功した場合に呼ばれるメソッドです。 
     * @param sourceId ソースID
     * @param fileName ファイル名 
     */
    void onSuccessLoadSourceData(String sourceId, String fileName);
    /**
     * 失敗した場合に呼ばれるメソッドです。
     * @param sourceId ソースID
     * @param fileName ファイル名
     */
    void onFailedLoadSourceData(String sourceId, String fileName);
  }
  
  class LoadSourceDataTask extends AsyncTask<String, Void, Boolean> {
    private InputStream input;
    private String filePath;
    private String fileName;
    private String sourceId;
    private LoadSourceDataTaskCallback callback;
    
    public LoadSourceDataTask(final InputStream input, final String filePath, final String fileName, final String sourceId, LoadSourceDataTaskCallback callback) {
      this.input = input;
      this.filePath = filePath;
      this.fileName = fileName;
      this.sourceId = sourceId;
      this.callback = callback;
    }
    
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
    protected Boolean doInBackground(String... arg0) {
      final boolean result = loadSourceData(this.input, this.filePath, this.sourceId);

      // input is closed in order to complete reading the data from the input stream.
      try {
        this.input.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      return Boolean.valueOf(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPostExecute(Boolean result) {
      CanvasFragment.this.progressDialog.dismiss();
      
      if (result.booleanValue()) {
        final Toast toast = Toast.makeText(getActivity(), getString(R.string.loadedSuccessfully), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        toast.show();
        
        addSource(this.sourceId);
        this.callback.onSuccessLoadSourceData(this.sourceId, this.fileName);
      } else {
        this.callback.onFailedLoadSourceData(this.sourceId, this.fileName);
      }
    }    
  }

  /**
   * ソースデータを読み込みます。
   * 
   * @param input ソースデータのインプットストリーム
   * @param filePath ソースファイルのパス
   * @param sourceId ソースファイルID
   */
  boolean loadSourceData(final InputStream input, final String filePath, final String sourceId) {
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
      
      return true;
    } catch (@SuppressWarnings("unused") IOException e) {
      if (this.progressDialog != null) {
        this.progressDialog.dismiss();
      }

      showMessageInDialog(getString(R.string.please_select_source_file));
      
      return false;
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
    } catch (@SuppressWarnings("unused") IllegalAccessError e) {
      if (this.progressDialog != null) {
        this.progressDialog.dismiss();
      }

      final String message = "Source data size is not match model's source number." //$NON-NLS-1$
          + "\nPlease select proper source data or set proper source number."; //$NON-NLS-1$
      showMessageInDialog(message);
    } catch (@SuppressWarnings("unused") IllegalArgumentException e) {
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
  void prepareRenderer() {
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
   * 床を表示するか設定します。
   * 
   * @param isFloorDrawing 表示するならばtrue
   */
  public void setFloorDrawing(boolean isFloorDrawing) {
    if (this.root == null) {
      return;
    }
    this.root.getConfiguration(0).getBaseCoordinate().setFloorDrawing(isFloorDrawing);
  }
  

  /**
   * 床を表示するか判定します。
   * 
   * @return 表示するならばtrue
   */
  public boolean isFloorDrawing() {
    if (this.root == null) {
      return false;
    }
    return this.root.getConfiguration(0).getBaseCoordinate().isFloorDrawing();
  }
  
  
  /**
   * 影を表示するか設定します。
   * 
   * @param isShadowDrawing 表示するならばtrue
   */
  public void setShadowDrawing(boolean isShadowDrawing) {
    if (this.root == null) {
      return;
    }
    this.root.getConfiguration(0).getBaseCoordinate().setShadowDrawing(isShadowDrawing);
  }
  

  /**
   * 影を表示するか判定します。
   * 
   * @return 表示するならばtrue
   */
  public boolean isShadowDrawing() {
    if (this.root == null) {
      return false;
    }
    return this.root.getConfiguration(0).getBaseCoordinate().isShadowDrawing();
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
   * 環境データを設定します。
   * 
   * @param configuration 環境データ
   */
  public void setConfiguration(ConfigurationModel configuration) {
    this.objectRenderer.setConfiguration(configuration);
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
