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
  private double x;
  private double y;
  private double z;
  private double angleX;
  private double angleY;
  private double angleZ;

  /**
   * 新しく生成された<code>CoordinateParameter</code>オブジェクトを初期化します。
   */
  public CoordinateParameter() {
    this(0, 0, 0, 0, 0, 0);
  }

  /**
   * 新しく生成された<code>CoordinateParameter</code>オブジェクトを初期化します。
   * @param x X座標並進
   * @param y Y座標並進
   * @param z Z座標並進
   * @param angleX X座標回転
   * @param angleY Y座標回転
   * @param angleZ Z座標回転
   */
  public CoordinateParameter(double x, double y, double z, double angleX, double angleY, double angleZ) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.angleX = angleX;
    this.angleY = angleY;
    this.angleZ = angleZ;
  }

  /**
   * X座標並進の値を返します。
   * 
   * @return X座標並進の値
   */
  public double getX() {
    return this.x;
  }

  /**
   * X座標並進の値を設定します。
   * 
   * @param x X座標
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Y座標並進の値を返します。
   * 
   * @return Y座標並進の値
   */
  public double getY() {
    return this.y;
  }

  /**
   * Y座標並進の値を設定します。
   * 
   * @param y Y座標
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Z座標並進の値を返します。
   * 
   * @return Z座標並進の値
   */
  public double getZ() {
    return this.z;
  }

  /**
   * Z座標並進の値を設定します。
   * 
   * @param z Z座標
   */
  public void setZ(double z) {
    this.z = z;
  }

  /**
   * X座標回転の値を返します。
   * 
   * @return X座標回転の値
   */
  public double getAngleX() {
    return this.angleX;
  }

  /**
   * X座標回転の値を設定します。
   * 
   * @param angleX X座標回転の値
   */
  public void setAngleX(double angleX) {
    this.angleX = angleX;
  }

  /**
   * Y座標回転の値を返します。
   * 
   * @return Y座標回転の値
   */
  public double getAngleY() {
    return this.angleY;
  }

  /**
   * Y座標回転の値を設定します。
   * 
   * @param angleY Y座標回転の値
   */
  public void setAngleY(double angleY) {
    this.angleY = angleY;
  }

  /**
   * Z座標回転の値を返します。
   * 
   * @return Z座標回転の値
   */
  public double getAngleZ() {
    return this.angleZ;
  }

  /**
   * Z座標回転の値を設定します。
   * 
   * @param angleZ Z座標回転の値
   */
  public void setAngleZ(double angleZ) {
    this.angleZ = angleZ;
  }
}
