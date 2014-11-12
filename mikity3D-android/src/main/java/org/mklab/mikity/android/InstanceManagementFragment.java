/*
 * Created on 2014/11/12
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import roboguice.fragment.RoboFragment;


/**
 * @author soda
 * @version $Revision$, 2014/11/12
 * Activityのfragmentに関する、保持したい情報を管理するためのfragmentです。
 */
public class InstanceManagementFragment extends RoboFragment {
  /** 3Dモデルが選ばれて表示されたかどうかのフラグ */
  protected boolean isSelectedModelFile;
  /** モデルを参照するときに押されるボタン */
  private boolean isSelectButton;
  /** アニメーションスピードを早くするときに押されるボタン */
  private boolean isQuickButton;
  /** アニメーションスピードを遅くするときに押されるボタン */
  private boolean isSlowButton;
  /** アニメーションを停止するときに押されるボタン */
  private boolean isStopButton;
  /** モデルファイル読み込むときに押されるボタン */
  private boolean isLoadModelButton;
  /** アニメーションを再生するボタン */
  private boolean isPlayButton;
  
  private boolean[] getArray = new boolean[7];
  
  protected void setParameter(boolean isSelectedModelFile, boolean isSelectButton
      , boolean isQuickButton, boolean isSlowButton, boolean isStopButton
      , boolean isLoadModelButton, boolean isPlayButton) {
    this.isSelectedModelFile = isSelectedModelFile;
    this.isSelectButton = isSelectButton;
    this.isQuickButton = isQuickButton;
    this.isSlowButton = isSlowButton;
    this.isStopButton = isStopButton;
    this.isLoadModelButton = isLoadModelButton;
    this.isPlayButton = isPlayButton;
    setParameterArray();
  }
  
  protected boolean[] getParameter() {
    return this.getArray; 
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.instance_management_fragment, container, false);
    return view;
  }
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }
  
  private void setParameterArray() {
    this.getArray[0] = this.isSelectedModelFile;
    this.getArray[1] = this.isSelectButton;
    this.getArray[2] = this.isQuickButton;
    this.getArray[3] = this.isSlowButton;
    this.getArray[4] = this.isStopButton;
    this.getArray[5] = this.isLoadModelButton;
    this.getArray[6] = this.isPlayButton;
  }
}