/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.openintents.intents.OIFileManager;

import roboguice.fragment.RoboFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * @author soda
 * @version $Revision$, 2014/10/10
 * モデル描画用のフラグメントです。
 */
public class CanvasFragment extends RoboFragment implements OnTouchListener {
  
  GLSurfaceView glView;
  private boolean mIsInitScreenSize;
  public OpenglesModelRenderer modelRenderer;
  ScaleGestureDetector gesDetect = null;
  boolean rotationing;
  boolean scaling;
  private double scaleValue = 1;
  float prevX = 0;
  float prevY = 0;
  private View canvasActivity;
  public CanvasActivity activity;
  Timer timer = new Timer();
  
  
  /**
   * @param savedInstanceState Bundle
   * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    
    View view = inflater.inflate(R.layout.canvas_fragment, container, false);
//    //this.inputModelFile = res.openRawResource(R.raw.pendulum);
//    //final OIFileManager fileManager = new OIFileManager(this);
    this.glView = (GLSurfaceView)view.findViewById(R.id.glview1);
    this.getResources();
    this.modelRenderer = new OpenglesModelRenderer(this.glView);
//// // 描画のクラスを登録する
    this.glView.setRenderer(this.modelRenderer);
    this.mIsInitScreenSize = false;
    
    // 任意のタイミングで再描画する設定
    this.glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    this.activity = (CanvasActivity)getActivity();
    
    // ScaleGestureDetecotorクラスのインスタンス生成
    this.gesDetect = new ScaleGestureDetector(this.getActivity(), this.onScaleGestureListener);
//    view.setOnTouchListener(new View.OnTouchListener() {
//      
//      public boolean onTouch(View v, MotionEvent event) {
//        // TODO Auto-generated method stub
//        return false;
//      }
//    })
    return view;
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
  private Mikity3d root;
  private MovableGroupManager manager;
  private Matrix data;
  private double[] timeTable;
  private double endTime;
  private int animationSpeed;
  private boolean playable;
  private AnimationTask animationTask;
  
  protected void setScaleValue(double d) {
    this.scaleValue = d;
  }
  
  /**
   * 現在の呼び出し元アクティビティのビューを格納するメソッドです。
   */
  public void setActivity() {
    //this.canvasActivity = ActivitygetActivity().findViewById(R.id.activity_canvas);
  }
  
  /**
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
  public void loadModelFile(InputStream input) throws IOException, Mikity3dSerializeDeserializeException {
    this.root = new Mikity3dFactory().loadFile(input);
    this.manager = new MovableGroupManager(this.root);
    Group[] children = this.root.getModel(0).getGroups();
    this.modelRenderer.setChildren(children);
    Mikity3dConfiguration configuration = this.root.getConfiguration(0);
    this.modelRenderer.setConfiguration(configuration);

    this.manager.setLogCat(new LogCatImpl()); //LogCatのセット

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
  
  public void loadtimeSeriesData(final String filePath) {
    AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {

      ProgressDialog progressDialog;

      @Override
      protected void onPreExecute() {
        this.progressDialog = new ProgressDialog(getActivity());
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
  public MovableGroupManager getManager() {
    return this.manager;
  }
  public OpenglesModelRenderer getModelRender() {
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

    this.animationSpeed = (int)(Double.parseDouble(this.activity.animationSpeedTextEdit.getText().toString()) * 10);
    if (this.playable == false) {
      this.timer.cancel();
    }

    if (this.data == null || this.timeTable == null) {
      return;
    }

    this.playable = false;

    this.endTime = this.manager.getEndTime();
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

  public boolean onTouch(View v, MotionEvent event) {

    return false;
  }
  
  private OnTouchListener touchListener = new OnTouchListener() {





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
      return CanvasFragment.this.gesDetect.onTouchEvent(event);
      //return true;
    }
    
  };
  
  
}
