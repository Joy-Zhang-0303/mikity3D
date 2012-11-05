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
//  /** */
//  public static final int LOCX = 1;
//  /** */
//  public static final int LOCY = 2;
//  /** */
//  public static final int LOCZ = 3;
//  /** */
//  public static final int ROTX = 4;
//  /** */
//  public static final int ROTY = 5;
//  /** */
//  public static final int ROTZ = 6;

  private double locX, locY, locZ, rotX, rotY, rotZ;

  /**
   * 新しく生成された<code>CoordinateParameter</code>オブジェクトを初期化します。
   */
  public CoordinateParameter() {
    this(0, 0, 0, 0, 0, 0);
  }

  /**
   * コンストラクタ
   * 
   * @param locX X座標並進
   * @param locY Y座標並進
   * @param locZ Z座標並進
   * @param rotX X座標回転
   * @param rotY Y座標回転
   * @param rotZ Z座標回転
   */
  public CoordinateParameter(double locX, double locY, double locZ, double rotX, double rotY, double rotZ) {
    this.locX = locX;
    this.locY = locY;
    this.locZ = locZ;
    this.rotX = rotX;
    this.rotY = rotY;
    this.locZ = rotZ;
  }

  /**
   * X座標並進の値を返します。
   * 
   * @return X座標並進の値
   */
  public double getLocX() {
    return this.locX;
  }

  /**
   * X座標並進の値を設定します。
   * 
   * @param locX X座標
   */
  public void setLocX(double locX) {
    this.locX = locX;
  }

  /**
   * Y座標並進の値を返します。
   * 
   * @return Y座標並進の値
   */
  public double getLocY() {
    return this.locY;
  }

  /**
   * Y座標並進の値を設定します。
   * 
   * @param locY Y座標
   */
  public void setLocY(double locY) {
    this.locY = locY;
  }

  /**
   * Z座標並進の値を返します。
   * 
   * @return Z座標並進の値
   */
  public double getLocZ() {
    return this.locZ;
  }

  /**
   * Z座標並進の値を設定します。
   * 
   * @param locZ Z座標
   */
  public void setLocZ(double locZ) {
    this.locZ = locZ;
  }

  /**
   * X座標回転の値を返します。
   * 
   * @return X座標回転の値
   */
  public double getRotX() {
    return this.rotX;
  }

  /**
   * X座標回転の値を設定します。
   * 
   * @param rotX X座標回転の値
   */
  public void setRotX(double rotX) {
    this.rotX = rotX;
  }

  /**
   * Y座標回転の値を返します。
   * 
   * @return Y座標回転の値
   */
  public double getRotY() {
    return this.rotY;
  }

  /**
   * Y座標回転の値を設定します。
   * 
   * @param rotY Y座標回転の値
   */
  public void setRotY(double rotY) {
    this.rotY = rotY;
  }

  /**
   * Z座標回転の値を返します。
   * 
   * @return Z座標回転の値
   */
  public double getRotZ() {
    return this.rotZ;
  }

  /**
   * Z座標回転の値を設定します。
   * 
   * @param rotZ Z座標回転の値
   */
  public void setRotZ(double rotZ) {
    this.rotZ = rotZ;
  }
}
