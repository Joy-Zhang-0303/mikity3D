/*
 * Created on 2005/01/24
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.task;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.mklab.mikity.gui.SimulationViewer;
import org.mklab.mikity.model.MovableGroupManager;


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
  private List<AnimationTaskListener> listenerList = new ArrayList<AnimationTaskListener>();
  private final double initialTime;

  /**
   * コンストラクター
   * 
   * @param initialTime アニメーションのスタート時間
   * @param endTime 終了時刻
   * @param manager グループマネージャー
   */
  public AnimationTask(double initialTime, double endTime, MovableGroupManager manager) {
    this.endTime = endTime;
    this.currentTime = initialTime;
    this.manager = manager;

    this.initialTime = initialTime;
  }

  /**
   * コンストラクター
   * 
   * @param endTime 終了時刻
   * @param manager グループマネージャー
   */
  public AnimationTask(double endTime, MovableGroupManager manager) {
    this(0, endTime, manager);
  }

  /**
   * {@link AnimationTaskListener}を登録します。
   * 
   * @param l リスナ
   */
  public void addAnimationTaskListener(AnimationTaskListener l) {
    this.listenerList.add(l);
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
    if (this.currentTime == this.initialTime) {
      fireAnimationStarted();
    }

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
    //    else if(this.manager.getDH()&&this.manager.getLink()){
    //      this.manager.processStimulus(this.currentTime);
    //    }

    // SimulationViewer.setCurrentTime(currentTime);

    if (this.currentTime > this.endTime) {
      SimulationViewer.playable = true;
      cancel();
      fireAnimationDone();
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

  /**
   * アニメーションの完了を通知します。
   */
  private void fireAnimationDone() {
    for (AnimationTaskListener l : this.listenerList) {
      l.taskDone();
    }
  }

  /**
   * アニメーションの開始を通知します。
   */
  private void fireAnimationStarted() {
    for (AnimationTaskListener l : this.listenerList) {
      l.taskStarted();
    }
  }
}
