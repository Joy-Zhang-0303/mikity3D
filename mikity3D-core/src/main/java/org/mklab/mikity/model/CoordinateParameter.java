/*
 * $Id: LinkParameter.java,v 1.2 2008/02/29 14:43:29 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model;

/**
 * 座標パラメータを表すクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2008/02/29
 */
public class CoordinateParameter {
  /** X座標方向への並進距離 */
  private double translationX;
  /** Y座標方向への並進距離 */
  private double translationY;
  /** Z座標方向への並進距離 */
  private double translationZ;
  /** X軸周りの回転 */
  private double rotationX;
  /** Y軸周りの回転 */
  private double rotationY;
  /** Z軸ｋ周りの回転 */
  private double rotationZ;

  /**
   * 新しく生成された<code>CoordinateParameter</code>オブジェクトを初期化します。
   */
  public CoordinateParameter() {
    this(0, 0, 0, 0, 0, 0);
  }

  /**
   * 新しく生成された<code>CoordinateParameter</code>オブジェクトを初期化します。
   * @param translationX X軸方向への並進距離
   * @param translationY Y軸方向への並進距離
   * @param translationZ Z軸方向への並進距離
   * @param rotationX X軸周りの回転
   * @param rotationY Y軸周りの回転
   * @param rotationZ Z軸周りの回転
   */
  public CoordinateParameter(double translationX, double translationY, double translationZ, double rotationX, double rotationY, double rotationZ) {
    this.translationX = translationX;
    this.translationY = translationY;
    this.translationZ = translationZ;
    this.rotationX = rotationX;
    this.rotationY = rotationY;
    this.rotationZ = rotationZ;
  }

  /**
   * X軸方向への並進距離を返します。
   * 
   * @return X軸方向への並進距離
   */
  public double getTranslationX() {
    return this.translationX;
  }

  /**
   * X軸方向への並進距離を設定します。
   * 
   * @param translationX X軸方向への並進距離
   */
  public void setTranslationX(double translationX) {
    this.translationX = translationX;
  }

  /**
   * Y軸方向への並進距離の値を返します。
   * 
   * @return Y軸方向への並進距離の値
   */
  public double getTranslationY() {
    return this.translationY;
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
   * Z軸方向への並進距離の値を返します。
   * 
   * @return Z軸方向への並進距離の値
   */
  public double getTranslationZ() {
    return this.translationZ;
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
   * X軸周りの回転の値を返します。
   * 
   * @return X軸周りの回転の値
   */
  public double getRotationX() {
    return this.rotationX;
  }

  /**
   * X軸周りの回転の値を設定します。
   * 
   * @param rotationX X軸周りの回転の値
   */
  public void setRotationX(double rotationX) {
    this.rotationX = rotationX;
  }

  /**
   * Y軸周りの回転の値を返します。
   * 
   * @return Y軸周りの回転の値
   */
  public double getRotationY() {
    return this.rotationY;
  }

  /**
   * Y軸周りの回転の値を設定します。
   * 
   * @param rotationY Y軸周りの回転の値
   */
  public void setRotationY(double rotationY) {
    this.rotationY = rotationY;
  }

  /**
   * Z軸周りの回転の値を返します。
   * 
   * @return Z軸周りの回転の値
   */
  public double getRotationZ() {
    return this.rotationZ;
  }

  /**
   * Z軸周りの転の値を設定します。
   * 
   * @param rotationZ Z軸周りの回転の値
   */
  public void setRotationZ(double rotationZ) {
    this.rotationZ = rotationZ;
  }
}
