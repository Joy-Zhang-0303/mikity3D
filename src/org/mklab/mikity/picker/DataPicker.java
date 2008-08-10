/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.DHParameter;
import org.mklab.mikity.LinkParameter;
import org.mklab.mikity.MyTransformGroup;
import org.mklab.mikity.gui.ModelCanvas;
import org.mklab.nfc.DoubleMatrix;
import org.mklab.nfc.Matrix;


/**
 * @author miki
 * @version $Revision: 1.7 $.2005/01/17
 */
public abstract class DataPicker {

  private DoubleMatrix data;
  protected DHParameter[] params;
  protected LinkParameter[] link;
  protected MyTransformGroup trans;

  private static int dataScale = 1;
  private static int modelScale = 1;

  private static boolean dataIsRadian = true;
  static boolean modelIsRadian = true;

  /**
   * コンストラクター
   * 
   * @param data
   */
  public DataPicker(Matrix data) {
    this.data = (DoubleMatrix)data;

    // 列の数の配列を作成
    this.params = new DHParameter[data.getColumnSize()];
    for (int i = 0; i < this.params.length; i++) {
      this.params[i] = new DHParameter();
    }
    this.link = new LinkParameter[data.getColumnSize()];
    for (int i = 0; i < this.link.length; i++) {
      this.link[i] = new LinkParameter();
    }
  }

  /**
   * @param moveType
   * @param row
   */
  public final void addMoveTypeDH(int moveType, int row) {
    if (this.data.getRowSize() < row) {

    }
    switch (moveType) {
      case DHParameter.A:
        for (int i = 0; i < this.params.length; i++) {
          this.params[i].setA(this.data.getElement(row, i + 1).doubleValue() / dataScale);
        }
        break;
      case DHParameter.ALPHA:
        for (int i = 0; i < this.params.length; i++) {
          if (dataIsRadian) {
            this.params[i].setAlpha(this.data.getElement(row, i + 1).doubleValue());
          } else {
            this.params[i].setAlpha(Math.toRadians(this.data.getElement(row, i + 1).doubleValue()));
          }
        }
        break;
      case DHParameter.D:
        for (int i = 0; i < this.params.length; i++) {
          this.params[i].setD(this.data.getElement(row, i + 1).doubleValue() / dataScale);
        }
        break;
      case DHParameter.THETA:
        for (int i = 0; i < this.params.length; i++) {
          if (dataIsRadian) {
            this.params[i].setTheta(this.data.getElement(row, i + 1).doubleValue());
          } else {
            this.params[i].setTheta(Math.toRadians(this.data.getElement(row, i + 1).doubleValue()));
          }
        }
        break;
      default:
        throw new AssertionError("a, alpha, d, theta以外の値を入れるなごら。");
    }
  }

  /**
   * @param moveType
   * @param row
   */
  public final void addMoveType(int moveType, int row) {
    if (this.data.getRowSize() < row) {

    }
    switch (moveType) {
      case LinkParameter.LOCX:
        for (int i = 0; i < this.link.length; i++) {
          this.link[i].setLocX(this.data.getElement(row, i + 1).doubleValue() / dataScale);
        }
        break;
      case LinkParameter.LOCY:
        for (int i = 0; i < this.link.length; i++) {
          this.link[i].setLocY(this.data.getElement(row, i + 1).doubleValue() / dataScale);
        }
        break;
      case LinkParameter.LOCZ:
        for (int i = 0; i < this.link.length; i++) {
          this.link[i].setLocZ(this.data.getElement(row, i + 1).doubleValue() / dataScale);
        }
        break;
      case LinkParameter.ROTX:
        for (int i = 0; i < this.link.length; i++) {
          if (dataIsRadian) {
            this.link[i].setRotX(this.data.getElement(row, i + 1).doubleValue());
          } else {
            this.link[i].setRotX(Math.toRadians(this.data.getElement(row, i + 1).doubleValue()));
          }
        }
        break;
      case LinkParameter.ROTY:
        for (int i = 0; i < this.link.length; i++) {
          if (dataIsRadian) {
            this.link[i].setRotY(this.data.getElement(row, i + 1).doubleValue());
          } else {
            this.link[i].setRotY(Math.toRadians(this.data.getElement(row, i + 1).doubleValue()));
          }
        }
        break;
      case LinkParameter.ROTZ:
        for (int i = 0; i < this.link.length; i++) {
          if (dataIsRadian) {
            this.link[i].setRotZ(this.data.getElement(row, i + 1).doubleValue());
          } else {
            this.link[i].setRotZ(Math.toRadians(this.data.getElement(row, i + 1).doubleValue()));
          }
        }
        break;
      default:
        throw new AssertionError("locX, locY, locZ, rotX, rotY, rotZ以外の値を入れるなごら。");
    }
  }

  /**
   * @param setType
   * @param constantValue
   *        initialTranformに固定値をセットする
   */
  public void setConstantValueDH(int setType, double constantValue) {
    switch (setType) {
      case DHParameter.A:
        for (int i = 0; i < this.params.length; i++) {
          this.params[i].setA(constantValue / modelScale + this.params[i].getA());
        }
        break;
      case DHParameter.ALPHA:
        for (int i = 0; i < this.params.length; i++) {
          // どうにかする。
          if (ModelCanvas.radian) {
            this.params[i].setAlpha(constantValue + this.params[i].getAlpha());
          } else {
            this.params[i].setAlpha(Math.toRadians(constantValue) + this.params[i].getAlpha());
          }
        }

        break;
      case DHParameter.D:
        for (int i = 0; i < this.params.length; i++) {
          this.params[i].setD(constantValue / modelScale + this.params[i].getD());
        }
        break;
      case DHParameter.THETA:
        for (int i = 0; i < this.params.length; i++) {
          if (ModelCanvas.radian) {
            this.params[i].setTheta(constantValue + this.params[i].getTheta());
          } else {
            this.params[i].setTheta(Math.toRadians(constantValue) + this.params[i].getTheta());
          }
        }
        break;
      default:
        throw new AssertionError("a, alpha, d, theta以外の値を入れるなごら。");
    }
  }

  /**
   * @param setType
   * @param constantValue
   *        initialTranformに固定値をセットする
   */
  public void setConstantValue(int setType, double constantValue) {
    switch (setType) {
      case LinkParameter.LOCX:
        for (int i = 0; i < this.link.length; i++) {
          this.link[i].setLocX(constantValue / modelScale + this.link[i].getLocX());
        }
        break;
      case LinkParameter.LOCY:
        for (int i = 0; i < this.link.length; i++) {
          this.link[i].setLocY(constantValue / modelScale + this.link[i].getLocY());
        }
        break;
      case LinkParameter.LOCZ:
        for (int i = 0; i < this.link.length; i++) {
          this.link[i].setLocZ(constantValue / modelScale + this.link[i].getLocZ());
        }
        break;
      case LinkParameter.ROTX:
        for (int i = 0; i < this.link.length; i++) {
          if (ModelCanvas.radian) {
            this.link[i].setRotX(constantValue + this.link[i].getRotX());
          } else {
            this.link[i].setRotX(Math.toRadians(constantValue) + this.link[i].getRotX());
          }
        }
        break;
      case LinkParameter.ROTY:
        for (int i = 0; i < this.link.length; i++) {
          if (ModelCanvas.radian) {
            this.link[i].setRotY(constantValue + this.link[i].getRotY());
          } else {
            this.link[i].setRotY(Math.toRadians(constantValue) + this.link[i].getRotY());
          }
        }
        break;
      case LinkParameter.ROTZ:
        for (int i = 0; i < this.link.length; i++) {
          if (ModelCanvas.radian) {
            this.link[i].setRotZ(constantValue + this.link[i].getRotZ());
          } else {
            this.link[i].setRotZ(Math.toRadians(constantValue) + this.link[i].getRotZ());
          }
        }
        break;
      default:
        throw new AssertionError("locX, locY, locZ, rotX, rotY, rotZ以外の値を入れるなごら。");
    }
  }

  /**
   * @param time
   *        時間を与えると、それに最も近い時間のある行rowを返す
   * @return getValue(row, getColumn(time))
   */
  public int getColumn(double time) {
    // 時系列データからそれに最も近い時間のある行を返す。
    return ((DoubleMatrix)this.data.getRowVector(1).subtractElementWise(time)).absElementWise().minimum().getColumn();
  }

  /**
   * @param row
   *        データの行数（何番目のデータか）
   * @param time
   *        時刻
   * @return 時刻timeのときのrow行目のデータ
   */
  public double getValue(int row, double time) {
    return getValue(row, getColumn(time));
  }

  /**
   * @param row
   * @param col
   * @return data.getElement(row, col)
   */
  public double getValue(int row, int col) {
    return this.data.getElement(row, col).doubleValue();
  }

  /**
   * @return data.getColSize()
   */
  public int getDataCount() {
    return this.data.getColumnSize();
  }

  /**
   * @return data.getElement(1, getDataCount())
   */
  public double getEndTime() {
    return this.data.getElement(1, getDataCount()).doubleValue();
  }

  /**
   * シミュレーションの開始時刻を返す。
   * 
   * @return シミュレーションの開始時刻
   */
  public double getStartTime() {
    return this.data.getElement(1, 1).doubleValue();
  }

  /**
   * @param time
   * @return unknown
   */
  public abstract DHParameter getDHParameter(double time);

  /**
   * @param time
   * @return unknown
   */
  public abstract LinkParameter getLinkParameter(double time);

  /**
   * @param dScale
   * @param dRadian
   * @param mRadian
   * @param mScale
   */
  public static void setScale(int dScale, boolean dRadian, int mScale, boolean mRadian) {
    // dataScale = dScale;
    dataIsRadian = dRadian;
    modelScale = mScale;
    modelIsRadian = mRadian;
  }
}