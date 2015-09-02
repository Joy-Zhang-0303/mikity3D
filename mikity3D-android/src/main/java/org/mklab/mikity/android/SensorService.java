/*
 * Created on 2015/09/02
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorManager;


/**
 * センサーによるサービスを提供するクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/09/02
 */
public class SensorService {
  /** センサーマネージャー */
  SensorManager sensorManager;
  
  /** キャンバスフラグメント。 */
  CanvasFragment canvasFragment;

  /**
   * 新しく生成された<code>SensorService</code>オブジェクトを初期化します。
   * @param sensorManager センサーマネージャー
   * @param canvasFragment キャンバスフラグメント
   */
  public SensorService(SensorManager sensorManager, CanvasFragment canvasFragment) {
    this.sensorManager = sensorManager;
    this.canvasFragment = canvasFragment;
  }
  
  /**
   * センサーリスナーを登録解除します。
   */
  public void unregisterSensorListener() {
    this.sensorManager.unregisterListener(this.canvasFragment);
  }
  
  /**
   * センサーリスナーを登録します。
   */
  public void registerSensorListener() {
    final List<Sensor> accelerometers = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
    if (accelerometers.size() > 0) {
      this.sensorManager.registerListener(this.canvasFragment, accelerometers.get(0), SensorManager.SENSOR_DELAY_UI);
    }

    final List<Sensor> magneticFields = this.sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
    if (magneticFields.size() > 0) {
      this.sensorManager.registerListener(this.canvasFragment, magneticFields.get(0), SensorManager.SENSOR_DELAY_UI);
    }
  }
}
