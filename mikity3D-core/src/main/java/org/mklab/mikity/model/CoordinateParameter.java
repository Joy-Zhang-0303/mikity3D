/*
 * $Id: LinkParameter.java,v 1.2 2008/02/29 14:43:29 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model;

import org.mklab.mikity.model.sampler.Messages;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;

/**
 * 座標パラメータを表すクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2008/02/29
 */
public class CoordinateParameter {
  /** X軸方向への並進距離。 */
  private double translationX = 0;
  /** Y軸方向への並進距離。 */
  private double translationY = 0;
  /** Z軸方向への並進距離。 */
  private double translationZ = 0;
  /** X軸周りの回転角度。 */
  private double rotationX = 0;
  /** Y軸周りの回転角度。*/
  private double rotationY = 0;
  /** Z軸ｋ周りの回転角度。 */
  private double rotationZ = 0;

  /**
   * X軸方向への並進距離を設定します。
   * 
   * @param translationX X軸方向への並進距離
   */
  public void setTranslationX(double translationX) {
    this.translationX = translationX;
  }

  /**
   * Y軸方向への並進距離の値を設定します。
   * 
   * @param translationY Y軸方向への並進距離
   */
  public void setTranslationY(double translationY) {
    this.translationY = translationY;
  }

  /**
   * Z軸方向への並進距離の値を設定します。
   * 
   * @param translationZ Z軸方向への並進距離
   */
  public void setTranslationZ(double translationZ) {
    this.translationZ = translationZ;
  }

  /**
   * X軸周りの回転角度を設定します。
   * 
   * @param rotationX X軸周りの回転角度
   */
  public void setRotationX(double rotationX) {
    this.rotationX = rotationX;
  }

  /**
   * Y軸周りの回転角度を設定します。
   * 
   * @param rotationY Y軸周りの回転角度
   */
  public void setRotationY(double rotationY) {
    this.rotationY = rotationY;
  }

  /**
   * Z軸周りの回転角度を設定します。
   * 
   * @param rotationZ Z軸周りの回転角度
   */
  public void setRotationZ(double rotationZ) {
    this.rotationZ = rotationZ;
  }
  
  /**
   * 並進を返します。
   * 
   * @return 並進
   */
  public TranslationModel getTranslation() {
    return new TranslationModel((float)this.translationX, (float)this.translationY, (float)this.translationZ);
  }
  
  /**
   * 回転を返します。
   * 
   * @return 回転
   */
  public RotationModel getRotation() {
    return new RotationModel((float)this.rotationX, (float)this.rotationY, (float)this.rotationZ);
  }
  
  /**
   * 値を設定します。
   * 
   * @param value 値
   * @param type パラメータの型
   */
  public void setValue(double value, CoordinateParameterType type) {
    switch (type) {
      case TRANSLATION_X:
        setTranslationX(value);
        break;
      case TRANSLATION_Y:
        setTranslationY(value);
        break;
      case TRANSLATION_Z:
        setTranslationZ(value);
        break;
      case ROTATION_X:
        setRotationX(value);
        break;
      case ROTATION_Y:
        setRotationY(value);
        break;
      case ROTATION_Z:
        setRotationZ(value);
        break;
      default:
        throw new AssertionError(Messages.getString("DataPicker.1")); //$NON-NLS-1$
    }
  }
}
