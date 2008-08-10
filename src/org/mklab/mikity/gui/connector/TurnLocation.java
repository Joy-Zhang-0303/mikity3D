/*
 * $Id: TurnLocation.java,v 1.3 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.gui.connector.CosSin;


/**
 * プリミティブの回転に応じてコネクタの位置を決定するクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $. 2006/07/13
 */
public class TurnLocation {

  /** 正弦余弦演算 */
  private CosSin cossin;

  /** 乗算後の変換行列 */
  private float cMatrix[][] = new float[3][3];

  /** X軸回転変換行列 */
  private float matrixX[][] = new float[3][3];
  /** Y軸回転変換行列 */
  private float matrixY[][] = new float[3][3];
  /** Z軸回転変換行列 */
  private float matrixZ[][] = new float[3][3];

  /** コサインX */
  private float cosx;
  /** サインX */
  private float sinx;
  /** コサインY */
  private float cosy;
  /** サインY */
  private float siny;
  /** コサインX */
  private float cosz;
  /** サインX */
  private float sinz;

  /** コネクタの位置座標1 */
  private Location newLoc1;
  /** コネクタの位置座標2 */
  private Location newLoc2;
  /** コネクタの位置座標3 */
  private Location newLoc3;

  /**
   * コンストラクタ
   * 
   * @param xSize
   *        　幅
   * @param ySize
   *        　高さ
   * @param zSize
   *        　奥行き
   * @param xRot
   *        　X軸回転
   * @param yRot
   *        　Y軸回転
   * @param zRot
   *        　Z軸回転
   */
  public TurnLocation(float xSize, float ySize, float zSize, float xRot, float yRot, float zRot) {
    this.cMatrix[0][0] = xSize;
    this.cMatrix[0][1] = 0.0f;
    this.cMatrix[0][2] = 0.0f;
    this.cMatrix[1][0] = 0.0f;
    this.cMatrix[1][1] = ySize;
    this.cMatrix[1][2] = 0.0f;
    this.cMatrix[2][0] = 0.0f;
    this.cMatrix[2][1] = 0.0f;
    this.cMatrix[2][2] = zSize;

    this.cossin = new CosSin(xRot, yRot, zRot);

    this.cosx = this.cossin.getCosX();
    this.sinx = this.cossin.getSinX();
    this.cosy = this.cossin.getCosY();
    this.siny = this.cossin.getSinY();
    this.cosz = this.cossin.getCosZ();
    this.sinz = this.cossin.getSinZ();

    this.newLoc1 = new Location();
    this.newLoc2 = new Location();
    this.newLoc3 = new Location();

    createMatrixX();
    createMatrixY();
    createMatrixZ();

    multiplyMatrix(this.cMatrix[0][0], this.cMatrix[0][1], this.cMatrix[0][2], this.cMatrix[1][0], this.cMatrix[1][1], this.cMatrix[1][2], this.cMatrix[2][0], this.cMatrix[2][1], this.cMatrix[2][2],
        this.matrixX[0][0], this.matrixX[0][1], this.matrixX[0][2], this.matrixX[1][0], this.matrixX[1][1], this.matrixX[1][2], this.matrixX[2][0], this.matrixX[2][1], this.matrixX[2][2]);
    multiplyMatrix(this.cMatrix[0][0], this.cMatrix[0][1], this.cMatrix[0][2], this.cMatrix[1][0], this.cMatrix[1][1], this.cMatrix[1][2], this.cMatrix[2][0], this.cMatrix[2][1], this.cMatrix[2][2],
        this.matrixY[0][0], this.matrixY[0][1], this.matrixY[0][2], this.matrixY[1][0], this.matrixY[1][1], this.matrixY[1][2], this.matrixY[2][0], this.matrixY[2][1], this.matrixY[2][2]);
    multiplyMatrix(this.cMatrix[0][0], this.cMatrix[0][1], this.cMatrix[0][2], this.cMatrix[1][0], this.cMatrix[1][1], this.cMatrix[1][2], this.cMatrix[2][0], this.cMatrix[2][1], this.cMatrix[2][2],
        this.matrixZ[0][0], this.matrixZ[0][1], this.matrixZ[0][2], this.matrixZ[1][0], this.matrixZ[1][1], this.matrixZ[1][2], this.matrixZ[2][0], this.matrixZ[2][1], this.matrixZ[2][2]);

    setNewLocation1(this.cMatrix[0][0], this.cMatrix[0][1], this.cMatrix[0][2]);
    setNewLocation2(this.cMatrix[1][0], this.cMatrix[1][1], this.cMatrix[1][2]);
    setNewLocation3(this.cMatrix[2][0], this.cMatrix[2][1], this.cMatrix[2][2]);
  }

  /**
   * X軸回転変換行列の生成
   */
  private void createMatrixX() {
    this.matrixX[0][0] = 1.0f;
    this.matrixX[0][1] = 0.0f;
    this.matrixX[0][2] = 0.0f;
    this.matrixX[1][0] = 0.0f;
    this.matrixX[1][1] = this.cosx;
    this.matrixX[1][2] = this.sinx;
    this.matrixX[2][0] = 0.0f;
    this.matrixX[2][1] = -this.sinx;
    this.matrixX[2][2] = this.cosx;
  }

  /**
   * X軸回転変換行列の生成
   */
  private void createMatrixY() {
    this.matrixY[0][0] = this.cosy;
    this.matrixY[0][1] = 0.0f;
    this.matrixY[0][2] = -this.siny;
    this.matrixY[1][0] = 0.0f;
    this.matrixY[1][1] = 1.0f;
    this.matrixY[1][2] = 0.0f;
    this.matrixY[2][0] = this.siny;
    this.matrixY[2][1] = 0.0f;
    this.matrixY[2][2] = this.cosy;
  }

  /**
   * X軸回転変換行列の生成
   */
  private void createMatrixZ() {
    this.matrixZ[0][0] = this.cosz;
    this.matrixZ[0][1] = this.sinz;
    this.matrixZ[0][2] = 0.0f;
    this.matrixZ[1][0] = -this.sinz;
    this.matrixZ[1][1] = this.cosz;
    this.matrixZ[1][2] = 0.0f;
    this.matrixZ[2][0] = 0.0f;
    this.matrixZ[2][1] = 0.0f;
    this.matrixZ[2][2] = 1.0f;
  }

  /**
   * 行列の乗算
   * 
   * @param a1
   * @param b1
   * @param c1
   * @param d1
   * @param e1
   * @param f1
   * @param g1
   * @param h1
   * @param i1
   * @param a2
   * @param b2
   * @param c2
   * @param d2
   * @param e2
   * @param f2
   * @param g2
   * @param h2
   * @param i2
   */
  private void multiplyMatrix(float a1, float b1, float c1, float d1, float e1, float f1, float g1, float h1, float i1, float a2, float b2, float c2, float d2, float e2, float f2, float g2, float h2,
      float i2) {

    this.cMatrix[0][0] = a1 * a2 + b1 * d2 + c1 * g2;
    this.cMatrix[0][1] = a1 * b2 + b1 * e2 + c1 * h2;
    this.cMatrix[0][2] = a1 * c2 + b1 * f2 + c1 * i2;
    this.cMatrix[1][0] = d1 * a2 + e1 * d2 + f1 * g2;
    this.cMatrix[1][1] = d1 * b2 + e1 * e2 + f1 * h2;
    this.cMatrix[1][2] = d1 * c2 + e1 * f2 + f1 * i2;
    this.cMatrix[2][0] = g1 * a2 + h1 * d2 + i1 * g2;
    this.cMatrix[2][1] = g1 * b2 + h1 * e2 + i1 * h2;
    this.cMatrix[2][2] = g1 * c2 + h1 * f2 + i1 * i2;
  }

  /**
   * コネクタの位置1の設定
   * 
   * @param x1
   *        　X座標
   * @param y1
   *        　Y座標
   * @param z1
   *        　Z座標
   */
  private void setNewLocation1(float x1, float y1, float z1) {
    this.newLoc1.setX(x1);
    this.newLoc1.setY(y1);
    this.newLoc1.setZ(z1);
  }

  /**
   * コネクタの位置2の設定
   * 
   * @param x2
   *        　X座標
   * @param y2
   *        　Y座標
   * @param z2
   *        　Z座標
   */
  private void setNewLocation2(float x2, float y2, float z2) {
    this.newLoc2.setX(x2);
    this.newLoc2.setY(y2);
    this.newLoc2.setZ(z2);
  }

  /**
   * コネクタの位置3の設定
   * 
   * @param x3
   *        　X座標
   * @param y3
   *        　Y座標
   * @param z3
   *        　Z座標
   */
  private void setNewLocation3(float x3, float y3, float z3) {
    this.newLoc3.setX(x3);
    this.newLoc3.setY(y3);
    this.newLoc3.setZ(z3);
  }

  /**
   * コネクタの位置座標1を返す
   * 
   * @return newLoc1 コネクタの位置座標1
   */
  public Location getNewLocation1() {
    return this.newLoc1;
  }

  /**
   * コネクタの位置座標2を返す
   * 
   * @return　newLoc2　コネクタの位置座標2
   */
  public Location getNewLocation2() {
    return this.newLoc2;
  }

  /**
   * コネクタの位置座標3を返す
   * 
   * @return　newLoc2　コネクタの位置座標3
   */
  public Location getNewLocation3() {
    return this.newLoc3;
  }
}
