/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import org.mklab.mikity.android.view.renderer.OpenglesModelRenderer;
import org.openintents.intents.OIFileManager;

import android.app.Fragment;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class CanvasFragment extends Fragment {
  
  GLSurfaceView glView;
  private boolean mIsInitScreenSize;
  private OpenglesModelRenderer modelRenderer;
  
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
//    
//    GLSurfaceView mGLView = new GLSurfaceView(null, null);
//    View view = inflater.inflate();
    
    return view;
  }
  
}
