/*
 * Created on 2015/09/02
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;

import org.mklab.mikity.android.renderer.OpenglesObjectRenderer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;


/**
 * センサーによるサービスを提供するクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/09/02
 */
public class SensorService implements SensorEventListener {
  /** センサーマネージャー */
  private SensorManager sensorManager;
  
  /** キャンバスフラグメント。 */
  private CanvasFragment canvasFragment;
  
  /** センサーからの地磁気を格納する配列 */
  private float[] magneticFieldValues = new float[3];
  /** センサーからの加速度を格納する配列 */
  private float[] accelerometerValues = new float[3];

  /** 角度の基準値 */
  private float[] prevOrientations = new float[3];
  
  /** 回転センサーを利用するならばtrue */
  boolean useRotationSensor = false;

  /** 加速度のローパスフィルターのxの値 */
  private double lowPassX = 0;
  /** 加速度のローパスフィルターのｙの値 */
  private double lowPassY = 0;
  /** 加速度のローパスフィルターのzの値 */
  private double lowPassZ = 9.45;
  /** 加速度のハイパスフィルターのZの値 */
  private double rawAz;
  /** 前回加速度センサーを3Dオブジェクトに使用したときの時間 */
  private long useAccelerOldTime = 0L;

  /** 加速度センサーを利用するならばtrue */
  boolean useAccelerometer = false;

  /**
   * 新しく生成された<code>SensorService</code>オブジェクトを初期化します。
   * @param sensorManager センサーマネージャー
   * @param canvasFragment キャンバスフラグメント
   */
  public SensorService(SensorManager sensorManager, CanvasFragment canvasFragment) {
    this.sensorManager = sensorManager;
    this.canvasFragment = canvasFragment;
    
    for (int i = 0; i < 3; i++) {
      this.prevOrientations[i] = 0.0f;
      this.magneticFieldValues[i] = 0;
      this.accelerometerValues[i] = 0;
    }
  }
  
  /**
   * センサーリスナーを登録解除します。
   */
  public void unregisterSensorListener() {
    this.sensorManager.unregisterListener(this);
  }
  
  /**
   * センサーリスナーを登録します。
   */
  public void registerSensorListener() {
    final List<Sensor> accelerometers = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
    if (accelerometers.size() > 0) {
      this.sensorManager.registerListener(this, accelerometers.get(0), SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    final List<Sensor> magneticFields = this.sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
    if (magneticFields.size() > 0) {
      this.sensorManager.registerListener(this, magneticFields.get(0), SensorManager.SENSOR_DELAY_NORMAL);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public void onSensorChanged(SensorEvent event) {
    final OpenglesObjectRenderer objectRenderer = this.canvasFragment.getObjectRender();
    
    final int sensorType = event.sensor.getType();

    if (sensorType == Sensor.TYPE_ACCELEROMETER) {
      for (int i = 0; i < 3; i++) {
        this.accelerometerValues[i] = event.values[i];
      }
    }

    if (sensorType == Sensor.TYPE_MAGNETIC_FIELD) {
      for (int i = 0; i < 3; i++) {
        this.magneticFieldValues[i] = event.values[i];
      }
    }

    if (this.useAccelerometer && sensorType == Sensor.TYPE_ACCELEROMETER) {
      // Low Pass Filter
      this.lowPassX = this.lowPassX + event.values[0];
      this.lowPassY = this.lowPassY + event.values[1];
      this.lowPassZ = (0.1 * event.values[2] + 0.9 * this.lowPassZ);

      this.rawAz = event.values[2] - this.lowPassZ;

      final long nowTime = SystemClock.uptimeMillis();
      final long interval = nowTime - this.useAccelerOldTime;

      if (interval > 300) {
        final int accelerSensorThreshold = 5;
        
        if (this.rawAz > accelerSensorThreshold) {
          for (int i = 0; i < 10000; i++) {
            if (this.canvasFragment.scaleValue < 20.0) {
              this.canvasFragment.scaleValue += 0.00002;
              objectRenderer.setScale((float)this.canvasFragment.scaleValue);
              objectRenderer.updateDisplay();
            }
          }
          this.useAccelerOldTime = nowTime;
        }

        if (this.rawAz < -accelerSensorThreshold) {
          for (int i = 0; i < 10000; i++) {
            if (this.canvasFragment.scaleValue > 0.05) {
              this.canvasFragment.scaleValue -= 0.00002;
              objectRenderer.setScale((float)this.canvasFragment.scaleValue);
              objectRenderer.updateDisplay();
            }
          }
          this.useAccelerOldTime = nowTime;
        }
      }

      objectRenderer.setScale((float)this.canvasFragment.scaleValue);
    }

    if (this.useRotationSensor) {
      final float[] matrixR = new float[9];
      final float[] matrixI = new float[9];
      SensorManager.getRotationMatrix(matrixR, matrixI, this.accelerometerValues, this.magneticFieldValues);
      
      // センサーから算出した端末の角度を格納する配列 
      final float[] orientations = new float[3];
      SensorManager.getOrientation(matrixR, orientations);
      
      final float[] diffOrientations = new float[3];
      for (int i = 0; i < orientations.length; i++) {
        diffOrientations[i] = orientations[i] - this.prevOrientations[i];
      }
      
      for (int i = 0; i < orientations.length; i++) {
        this.prevOrientations[i] = orientations[i];
      }
      
      objectRenderer.rotateY(diffOrientations[2]*60);
      objectRenderer.rotateZ(diffOrientations[0]*60);
    }

    objectRenderer.updateDisplay();
  }
  
  /**
   * {@inheritDoc}
   */
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    //nothing to do
  }
}
