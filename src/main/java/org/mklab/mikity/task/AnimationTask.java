/*
 * Created on 2005/01/24
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.task;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.mklab.mikity.gui.ModelCanvas;
import org.mklab.mikity.gui.AnimationWindow;
import org.mklab.mikity.jogl.JoglModelCanvas;
import org.mklab.mikity.model.MovableGroupManager;


/**
 * アニメーションを実行するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.6 $.2005/01/24 
 */
public class AnimationTask extends TimerTask {
  private double currentTime = 0.0;
  private double speed = 1.0;
  private long startTime = System.currentTimeMillis();
  private double endTime = 0.0;
  private MovableGroupManager manager;
  private List<AnimationTaskListener> listeners = new ArrayList<AnimationTaskListener>();
  private final double initialTime;
  
  private ModelCanvas canvas;
  
  /**
   * コンストラクター
   * 
   * @param initialTime アニメーションのスタート時間
   * @param endTime 終了時刻
   * @param manager グループマネージャー
   * @param canvas モデルキャンバス
   */
  public AnimationTask(double initialTime, double endTime, MovableGroupManager manager, ModelCanvas canvas) {
    this.endTime = endTime;
    this.currentTime = initialTime;
    this.manager = manager;
    this.initialTime = initialTime;
    this.canvas = canvas;
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
   * アニメーションを行う速度を返します。 速度は1.0のときに実時間で再生します。
   * 
   * @return 現在の速度
   */
  public double getSpeed() {
    return this.speed;
  }

  /**
   * アニメーションを行う速度を設定します。 速度が1.0のときに実時間で再生します。
   * 
   * @param speed スピード
   */
  public void setSpeed(double speed) {
    this.speed = speed;
  }

  /**
   * @see java.lang.Runnable#run() 再生ボタンが押されると実行する
   */
  @Override
  public void run() {
    if (this.currentTime == this.initialTime) {
      fireAnimationStarted();
    }

    double diffTime = (scheduledExecutionTime() - this.startTime) / 1000.0;
    this.startTime = System.currentTimeMillis();
    this.currentTime += diffTime * this.speed;

    if (this.manager.hasDHParameter()) {
      this.manager.performAnimationWithDHParameter(this.currentTime);
    } else if (this.manager.hasCoordinateParameter()) {
      this.manager.performAnimationWithCoordinateParameter(this.currentTime);
    }

    
    if (this.canvas instanceof JoglModelCanvas) {
      ((JoglModelCanvas)this.canvas).display();
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
      listener.taskDone();
    }
  }

  /**
   * アニメーションの開始を通知します。
   */
  private void fireAnimationStarted() {
    for (final AnimationTaskListener listener : this.listeners) {
      listener.taskStarted();
    }
  }
}
