/*
 * Created on 2005/01/24
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.control;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.mklab.mikity.model.GroupObjectManager;
import org.mklab.mikity.renderer.ObjectRenderer;


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
  private GroupObjectManager manager;
  /** リスナー　*/
  private List<AnimationTaskListener> listeners = new ArrayList<>();
  /** 現在の時間　*/
  private double currentTime = 0.0;
  /** 開始時間　*/
  private final double startTime;
  /** 終了時間　*/
  private double stopTime = 0.0;
  /** 最後の更新時間 */
  private long lastUpdatedTimeMillis = System.currentTimeMillis();
  /** モデルキャンバス　*/
  private ObjectRenderer renderer;

  /**
   * 新しく生成された<code>AnimationTask</code>オブジェクトを初期化します。
   * @param startTime 開始時間
   * @param stopTime 終了時間
   * @param manager グループマネージャー
   * @param canvas モデルキャンバス
   */
  public AnimationTask(double startTime, double stopTime, GroupObjectManager manager, ObjectRenderer canvas) {
    this.startTime = startTime;
    this.currentTime = startTime;
    this.stopTime = stopTime;
    this.manager = manager;
    this.renderer = canvas;
  }

  /**
   * {@link AnimationTaskListener}を登録します。
   * 
   * @param listener リスナー
   */
  public void addAnimationTaskListener(AnimationTaskListener listener) {
    this.listeners.add(listener);
  }

  /**
   * アニメーションの速度倍率を返します。 
   * 
   * 速度は1.0のときに実時間で再生します。
   * 
   * @return アニメーションの速度倍率
   */
  public double getSpeedScale() {
    return this.speedScale;
  }

  /**
   * アニメーションの速度倍率を設定します。 
   * 
   * 速度が1.0のときに実時間で再生します。
   * 
   * @param speedScale アニメーションの速度倍率
   */
  public void setSpeedScale(double speedScale) {
    this.speedScale = speedScale;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    if (this.currentTime == this.startTime) {
      setUpAnimation();
    }

    final double diffTime = (scheduledExecutionTime() - this.lastUpdatedTimeMillis) / 1000.0;
    this.lastUpdatedTimeMillis = System.currentTimeMillis();
    this.currentTime += diffTime * this.speedScale;
    
    this.currentTime = Math.max(this.currentTime, this.startTime);

    if (this.manager.hasAnimation()) {
      this.manager.updateGroupObjects(this.currentTime);
    }

    if (this.renderer.isRequiredToCallDisplay()) {
      this.renderer.updateDisplay();
    }

    if (this.currentTime > this.stopTime) {
      cancel();
      tearDownAnimation();
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
   * @param currentTime 現在の時刻
   */
  public void setCurrentTime(double currentTime) {
    this.currentTime = currentTime;
  }

  /**
   * アニメーションの完了を通知します。
   */
  private void tearDownAnimation() {
    for (final AnimationTaskListener listener : this.listeners) {
      listener.tearDownAnimation();
    }
  }

  /**
   * アニメーションの開始を通知します。
   */
  private void setUpAnimation() {
    for (final AnimationTaskListener listener : this.listeners) {
      listener.setUpAnimation();
    }
  }
}
