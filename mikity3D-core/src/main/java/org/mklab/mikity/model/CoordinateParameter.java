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
  /** X座標 */
  private double x;
  /** Y座標 */
  private double y;
  /** Y座標 */
  private double z;
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
   * @param x X座標
   * @param y Y座標
   * @param z Z座標
   * @param angleX X軸周りの回転
   * @param angleY Y軸周りの回転
   * @param angleZ Z軸周りの回転
   */
  public CoordinateParameter(double x, double y, double z, double angleX, double angleY, double angleZ) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.rotationX = angleX;
    this.rotationY = angleY;
    this.rotationZ = angleZ;
  }

  /**
   * X座標の値を返します。
   * 
   * @return X座標の値
   */
  public double getX() {
    return this.x;
  }

  /**
   * X座標の値を設定します。
   * 
   * @param x X座標
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Y座標の値を返します。
   * 
   * @return Y座標の値
   */
  public double getY() {
    return this.y;
  }

  /**
   * Y座標の値を設定します。
   * 
   * @param y Y座標
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Z座標の値を返します。
   * 
   * @return Z座標の値
   */
  public double getZ() {
    return this.z;
  }

  /**
   * Z座標の値を設定します。
   * 
   * @param z Z座標
   */
  public void setZ(double z) {
    this.z = z;
  }

  /**
   * X軸周りの回転の値を返します。
   * 
   * @return X軸周りの回転の値
   */
  public double getAngleX() {
    return this.rotationX;
  }

  /**
   * X軸周りの回転の値を設定します。
   * 
   * @param angleX X軸周りの回転の値
   */
  public void setAngleX(double angleX) {
    this.rotationX = angleX;
  }

  /**
   * Y軸周りの回転の値を返します。
   * 
   * @return Y軸周りの回転の値
   */
  public double getAngleY() {
    return this.rotationY;
  }

  /**
   * Y軸周りの回転の値を設定します。
   * 
   * @param angleY Y軸周りの回転の値
   */
  public void setAngleY(double angleY) {
    this.rotationY = angleY;
  }

  /**
   * Z軸周りの回転の値を返します。
   * 
   * @return Z軸周りの回転の値
   */
  public double getAngleZ() {
    return this.rotationZ;
  }

  /**
   * Z軸周りの転の値を設定します。
   * 
   * @param angleZ Z軸周りの回転の値
   */
  public void setAngleZ(double angleZ) {
    this.rotationZ = angleZ;
  }
}
