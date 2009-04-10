/*
 * Created on 2005/01/24
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.task;

import java.util.TimerTask;

import org.mklab.mikity.MovableGroupManager;
import org.mklab.mikity.gui.SimulationViewer;


/**
 * @author miki
 * @version $Revision: 1.6 $.2005/01/24 アニメーションを実行する
 */
public class AnimationTask extends TimerTask {

  private double currentTime = 0.0;
  private double speed = 1.0;
  private long startTime = System.currentTimeMillis();
  private double endTime = 0.0;
  private MovableGroupManager manager;

  /**
   * コンストラクター
   * 
   * @param endTime
   * @param manager グループマネージャー
   */
  public AnimationTask(double endTime, MovableGroupManager manager) {
    this.endTime = endTime;
    this.manager = manager;
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
   * @param speed
   */
  public void setSpeed(double speed) {
    this.speed = speed;
  }

  /**
   * @see java.lang.Runnable#run() 再生ボタンが押されると実行する
   */
  @Override
  public void run() {

    // さらに今の時間からstartTimeを引いた差分をdiffTimeに入れる
    double diffTime = (scheduledExecutionTime() - this.startTime) / 1000.0;

    this.startTime = System.currentTimeMillis();

    // currentTimeにdiffTime*speedを足す speedは１で現実と同じ時間経過
    this.currentTime += diffTime * this.speed;

    if (this.manager.getDH()) {
      this.manager.processStimulusDH(this.currentTime);
    } else if (this.manager.getLink()) {
      this.manager.processStimulus(this.currentTime);
    }

    // SimulationViewer.setCurrentTime(currentTime);

    if (this.currentTime > this.endTime) {
      SimulationViewer.playable = true;
      cancel();
    }

  }

  /**
   * @return 現在の時刻
   */
  public double getCurrentTime() {
    return this.currentTime;
  }

  /**
   * アニメーションの現在の時刻を設定します。
   * 
   * @param t
   */
  public void setCurrentTime(double t) {
    this.currentTime = t;
  }
}
