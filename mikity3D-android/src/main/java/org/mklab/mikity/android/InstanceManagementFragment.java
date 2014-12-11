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
  
  private boolean[] getParamArray = new boolean[7];
  private String modelFileName;
  private String timeDataName;
  private String[] fileNameArray = new String[2];
  
  protected void setParameter(boolean isSelectedModelFile, boolean isSelectButton
      , boolean isQuickButton, boolean isSlowButton, boolean isStopButton
      , boolean isLoadModelButton, boolean isPlayButton) {
//    this.isSelectedModelFile = isSelectedModelFile;
//    this.isSelectButton = isSelectButton;
//    this.isQuickButton = isQuickButton;
//    this.isSlowButton = isSlowButton;
//    this.isStopButton = isStopButton;
//    this.isLoadModelButton = isLoadModelButton;
//    this.isPlayButton = isPlayButton;
//    setParameterArray();
  }
  
  protected void setFileName(String modelFileName, String timeDataName) {
//    this.modelFileName = modelFileName;
//    this.timeDataName = timeDataName;
//    setFileNameArray();
  }
  
  protected boolean[] getParameter() {
    return this.getParamArray; 
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.instance_management_fragment, container, false);
    return view;
  }
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    setRetainInstance(true);
  }
  
  private void setParameterArray() {
//    this.getParamArray[0] = this.isSelectedModelFile;
//    this.getParamArray[1] = this.isSelectButton;
//    this.getParamArray[2] = this.isQuickButton;
//    this.getParamArray[3] = this.isSlowButton;
//    this.getParamArray[4] = this.isStopButton;
//    this.getParamArray[5] = this.isLoadModelButton;
//    this.getParamArray[6] = this.isPlayButton;
  }
  
  private void setFileNameArray() {
//    this.fileNameArray[0] = this.modelFileName;
//    this.fileNameArray[1] = this.timeDataName;
  }
  
  protected String[] getFileNameArray() {
    return this.fileNameArray;
  }
}