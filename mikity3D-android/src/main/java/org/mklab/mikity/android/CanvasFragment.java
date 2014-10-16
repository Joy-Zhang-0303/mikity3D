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

import org.mklab.mikity.android.view.renderer.OpenglesModelRenderer;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dConfiguration;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.openintents.intents.OIFileManager;

import roboguice.fragment.RoboFragment;
import android.app.Fragment;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * @author soda
 * @version $Revision$, 2014/10/10
 * モデル描画用のフラグメントです。
 */
public class CanvasFragment extends RoboFragment {
  
  GLSurfaceView glView;
  private boolean mIsInitScreenSize;
  public OpenglesModelRenderer modelRenderer;
  private ScaleGestureDetector gesDetect;
  private boolean rotationing;
  private boolean scaling;
  private double scaleValue = 1;
  float prevX = 0;
  float prevY = 0;
  private View canvasActivity;
  
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
    
    // ScaleGestureDetecotorクラスのインスタンス生成
    this.gesDetect = new ScaleGestureDetector(this.getActivity(), this.onScaleGestureListener);
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
}
