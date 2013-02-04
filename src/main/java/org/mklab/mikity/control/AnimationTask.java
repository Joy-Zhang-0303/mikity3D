/*
 * Created on 2005/01/24
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.control;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.view.gui.AnimationWindow;
import org.mklab.mikity.view.renderer.ModelRenderer;
import org.mklab.mikity.view.renderer.jogl.JoglModelRenderer;


/**
 * アニメーションを実行するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.6 $.2005/01/24 
 */
public class AnimationTask extends TimerTask {
  /** アニメーションの速度倍率(1.0のときに実時間で再生) */
  private double speedScale = 1.0;
  /** グループマネージャ　*/
  private MovableGroupManager manager;
  /** リスナー　*/
  private List<AnimationTaskListener> listeners = new ArrayList<AnimationTaskListener>();
  /** 現在の時間　*/
  private double currentTime = 0.0;
  /** 開始時間　*/
  private final double initialTime;
  /** 終了時間　*/
  private double endTime = 0.0;
  /** 最後の更新時間 */
  private long lastUpdatedTimeMillis = System.currentTimeMillis();
  /** モデルキャンバス　*/
  private ModelRenderer renderer;
  
  /**
   * コンストラクター
   * 
   * @param initialTime 開始時間
   * @param endTime 終了時間
   * @param manager グループマネージャー
   * @param canvas モデルキャンバス
   */
  public AnimationTask(double initialTime, double endTime, MovableGroupManager manager, ModelRenderer canvas) {
    this.endTime = endTime;
    this.currentTime = initialTime;
    this.manager = manager;
    this.initialTime = initialTime;
    this.renderer = canvas;
  }

  /**
   * {@link AnimationTaskListener}を登録します。
   * 
   * @param listener リスナ
   */
  public void addAnimationTaskListener(AnimationTaskListener listener) {
    this.listeners.add(listener);
  }

  /**
   * アニメーションの速度倍率を返します。 速度は1.0のときに実時間で再生します。
   * 
   * @return アニメーションの速度倍率
   */
  public double getSpeedScale() {
    return this.speedScale;
  }

  /**
   * アニメーションの速度倍率を設定します。 速度が1.0のときに実時間で再生します。
   * 
   * @param speedScale アニメーションの速度倍率
   */
  public void setSpeedScale(double speedScale) {
    this.speedScale = speedScale;
  }

  /**
   * @see java.lang.Runnable#run() 再生ボタンが押されると実行する
   */
  @Override
  public void run() {
    if (this.currentTime == this.initialTime) {
      fireAnimationStart();
    }

    final double diffTime = (scheduledExecutionTime() - this.lastUpdatedTimeMillis) / 1000.0;
    this.lastUpdatedTimeMillis = System.currentTimeMillis();
    this.currentTime += diffTime * this.speedScale;

    if (this.manager.hasDHParameter()) {
      this.manager.updateMovableGroupsWithDHParameter(this.currentTime);
    } else if (this.manager.hasCoordinateParameter()) {
      this.manager.updateMovableGroupsWithCoordinateParameter(this.currentTime);
    }

    if (this.renderer.isRequiredToCallDisplay()) {
      this.renderer.updateDisplay();
    }

    if (this.currentTime > this.endTime) {
      AnimationWindow.playable = true;
      cancel();
      fireAnimationDone();
    }
  }

  /**
   * アニメーションの現在の時刻を返します。
   * @return 現在の時刻
   */
  public double getCurrentTime() {
    return this.currentTime;
  }

  /**
   * アニメーションの現在の時刻を設定します。
   * 
   * @param t 現在の時刻
   */
  public void setCurrentTime(double t) {
    this.currentTime = t;
  }

  /**
   * アニメーションの完了を通知します。
   */
  private void fireAnimationDone() {
    for (final AnimationTaskListener listener : this.listeners) {
      listener.tearDownAnimation();
    }
  }

  /**
   * アニメーションの開始を通知します。
   */
  private void fireAnimationStart() {
    for (final AnimationTaskListener listener : this.listeners) {
      listener.setUpAnimation();
    }
  }
}
