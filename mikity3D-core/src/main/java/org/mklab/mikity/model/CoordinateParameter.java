/*
 * $Id: LinkParameter.java,v 1.2 2008/02/29 14:43:29 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model;

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

//  /**
//   * 新しく生成された<code>CoordinateParameter</code>オブジェクトを初期化します。
//   */
//  public CoordinateParameter() {
//    this(0, 0, 0, 0, 0, 0);
//  }
//
//  /**
//   * 新しく生成された<code>CoordinateParameter</code>オブジェクトを初期化します。
//   * 
//   * @param translationX X軸方向への並進距離
//   * @param translationY Y軸方向への並進距離
//   * @param translationZ Z軸方向への並進距離
//   * @param rotationX X軸周りの回転角度
//   * @param rotationY Y軸周りの回転角度
//   * @param rotationZ Z軸周りの回転角度
//   */
//  public CoordinateParameter(double translationX, double translationY, double translationZ, double rotationX, double rotationY, double rotationZ) {
//    this.translationX = translationX;
//    this.translationY = translationY;
//    this.translationZ = translationZ;
//    this.rotationX = rotationX;
//    this.rotationY = rotationY;
//    this.rotationZ = rotationZ;
//  }

//  /**
//   * X軸方向への並進距離を返します。
//   * 
//   * @return X軸方向への並進距離
//   */
//  public double getTranslationX() {
//    return this.translationX;
//  }

  /**
   * X軸方向への並進距離を設定します。
   * 
   * @param translationX X軸方向への並進距離
   */
  public void setTranslationX(double translationX) {
    this.translationX = translationX;
  }

//  /**
//   * Y軸方向への並進距離の値を返します。
//   * 
//   * @return Y軸方向への並進距離の値
//   */
//  public double getTranslationY() {
//    return this.translationY;
//  }

  /**
   * Y軸方向への並進距離の値を設定します。
   * 
   * @param translationY Y軸方向への並進距離
   */
  public void setTranslationY(double translationY) {
    this.translationY = translationY;
  }

//  /**
//   * Z軸方向への並進距離の値を返します。
//   * 
//   * @return Z軸方向への並進距離の値
//   */
//  public double getTranslationZ() {
//    return this.translationZ;
//  }

  /**
   * Z軸方向への並進距離の値を設定します。
   * 
   * @param translationZ Z軸方向への並進距離
   */
  public void setTranslationZ(double translationZ) {
    this.translationZ = translationZ;
  }

//  /**
//   * X軸周りの回転角度を返します。
//   * 
//   * @return X軸周りの回転角度
//   */
//  public double getRotationX() {
//    return this.rotationX;
//  }

  /**
   * X軸周りの回転角度を設定します。
   * 
   * @param rotationX X軸周りの回転角度
   */
  public void setRotationX(double rotationX) {
    this.rotationX = rotationX;
  }

//  /**
//   * Y軸周りの回転角度を返します。
//   * 
//   * @return Y軸周りの回転角度
//   */
//  public double getRotationY() {
//    return this.rotationY;
//  }

  /**
   * Y軸周りの回転角度を設定します。
   * 
   * @param rotationY Y軸周りの回転角度
   */
  public void setRotationY(double rotationY) {
    this.rotationY = rotationY;
  }

//  /**
//   * Z軸周りの回転角度を返します。
//   * 
//   * @return Z軸周りの回角度
//   */
//  public double getRotationZ() {
//    return this.rotationZ;
//  }

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
  
//  /**
//   * 並進を設定します。
//   * @param translation 並進
//   */
//  public void setTranslation(TranslationModel translation) {
//    this.translationX = translation.getX();
//    this.translationY = translation.getY();
//    this.translationZ = translation.getZ();
//  }
  
  /**
   * 回転を返します。
   * 
   * @return 回転
   */
  public RotationModel getRotation() {
    return new RotationModel((float)this.rotationX, (float)this.rotationY, (float)this.rotationZ);
  }
  
//  /**
//   * 回転を設定します。
//   * 
//   * @param rotation 回転
//   */
//  public void setRotation(RotationModel rotation) {
//    this.rotationX = rotation.getX();
//    this.rotationY = rotation.getY();
//    this.rotationZ = rotation.getZ();
//  }

}
