/*
 * Created on 2005/01/24
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.control;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.TimerTask;

import org.mklab.mikity.control.AnimationTaskListener;
import org.mklab.mikity.model.ObjectGroupManager;
import org.mklab.mikity.view.renderer.ModelRenderer;

import android.os.SystemClock;


/**
 * アニメーションを実行するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.6 $.2005/01/24
 */
public class AnimationTask extends TimerTask {

  /** アニメーションの速度倍率(1.0のときに実時間で再生) */
  private double speedScale = 1.0;
  /** グループマネージャ　 */
  private ObjectGroupManager manager;
  /** リスナー　 */
  private List<AnimationTaskListener> listeners = new ArrayList<AnimationTaskListener>();
  /** 現在の時間　 */
  private double currentTime = 0.0;
  /** 開始時間　 */
  private final double initialTime;
  /** 終了時間　 */
  private double endTime = 0.0;
  /** 最後の更新時間 */
//  private long lastUpdatedTimeMillis = SystemClock.uptimeMillis();
  /** モデルキャンバス　 */
  private ModelRenderer renderer;
  /** 遅延時間*/
  private long delayTime;

  /**
   * コンストラクター
   * 
   * @param initialTime 開始時間
   * @param endTime 終了時間
   * @param manager グループマネージャー
   * @param canvas モデルキャンバス
   * @param delayTime 遅延時間
   */
  public AnimationTask(double initialTime, double endTime, ObjectGroupManager manager, ModelRenderer canvas, long delayTime) {
    this.endTime = endTime;
    this.currentTime = initialTime;
    this.manager = manager;
    this.initialTime = initialTime;
    this.renderer = canvas;
    this.delayTime = delayTime;
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
   * 再生ボタンが押されるとされます。 {@inheritDoc}
   */
  @Override
  public void run() {
    if (this.currentTime == this.initialTime) {
      setUpAnimation();
    }
    
    final long ctime = SystemClock.uptimeMillis()-this.delayTime;
    this.currentTime = this.speedScale*(ctime- this.initialTime)/1000;

    if (this.manager.hasCoordinateParameter()) {
      try {
        this.manager.updateObjectGroups(this.currentTime);
      } catch(ConcurrentModificationException e) {
        //　再生中に再読み込みが何度も押されすぎたら落ちてしまうので、そのエラーをキャッチしています。
        e.getMessage();
      }
    }

    if (this.renderer.isRequiredToCallDisplay()) {
      this.renderer.updateDisplay();
    }

    if (this.currentTime > this.endTime) {
      cancel();
      tearDownAnimation();
    }
  }

  /**
   * アニメーションの現在の時刻を返します。
   * 
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
