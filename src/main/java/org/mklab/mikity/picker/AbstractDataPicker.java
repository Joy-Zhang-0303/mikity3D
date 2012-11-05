/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.java3d.Java3dModelCanvas;
import org.mklab.mikity.java3d.MyTransformGroup;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.nfc.matrix.DoubleMatrix;
import org.mklab.nfc.matrix.Matrix;


/**
 * データを抽出するため抽象クラスです。
 * @author miki
 * @version $Revision: 1.7 $.2005/01/17
 */
public abstract class AbstractDataPicker implements DataPicker {

  private DoubleMatrix data;
  /** */
  protected DHParameter[] dhParameters;
  /** */
  protected CoordinateParameter[] coordinateParameters;
  /** */
  protected MyTransformGroup trans;

  private static int dataScale = 1;
  private static int modelScale = 1;

  private static boolean dataIsRadian = true;
  /** */
  static boolean modelIsRadian = true;

  /**
   * コンストラクター
   * 
   * @param data データ
   */
  public AbstractDataPicker(Matrix data) {
    this.data = (DoubleMatrix)data;

    this.dhParameters = new DHParameter[data.getColumnSize()];
    for (int i = 0; i < this.dhParameters.length; i++) {
      this.dhParameters[i] = new DHParameter();
    }
    this.coordinateParameters = new CoordinateParameter[data.getColumnSize()];
    for (int i = 0; i < this.coordinateParameters.length; i++) {
      this.coordinateParameters[i] = new CoordinateParameter();
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public final void addMoveTypeDH(int moveType, int row) {
    if (this.data.getRowSize() < row) {
      // 
    }

    switch (moveType) {
      case DHParameter.A:
        for (int i = 0; i < this.dhParameters.length; i++) {
          this.dhParameters[i].setA(this.data.getElement(row, i + 1).doubleValue() / dataScale);
        }
        break;
      case DHParameter.ALPHA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          if (dataIsRadian) {
            this.dhParameters[i].setAlpha(this.data.getElement(row, i + 1).doubleValue());
          } else {
            this.dhParameters[i].setAlpha(Math.toRadians(this.data.getElement(row, i + 1).doubleValue()));
          }
        }
        break;
      case DHParameter.D:
        for (int i = 0; i < this.dhParameters.length; i++) {
          this.dhParameters[i].setD(this.data.getElement(row, i + 1).doubleValue() / dataScale);
        }
        break;
      case DHParameter.THETA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          if (dataIsRadian) {
            this.dhParameters[i].setTheta(this.data.getElement(row, i + 1).doubleValue());
          } else {
            this.dhParameters[i].setTheta(Math.toRadians(this.data.getElement(row, i + 1).doubleValue()));
          }
        }
        break;
      default:
        throw new AssertionError(Messages.getString("DataPicker.0")); //$NON-NLS-1$
    }
  }

  /**
   * {@inheritDoc}
   */
  public final void addMoveType(int moveType, int row) {
    if (this.data.getRowSize() < row) {
      //
    }

    switch (moveType) {
      case CoordinateParameter.LOCX:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setLocX(this.data.getElement(row, i + 1).doubleValue() / dataScale);
        }
        break;
      case CoordinateParameter.LOCY:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setLocY(this.data.getElement(row, i + 1).doubleValue() / dataScale);
        }
        break;
      case CoordinateParameter.LOCZ:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setLocZ(this.data.getElement(row, i + 1).doubleValue() / dataScale);
        }
        break;
      case CoordinateParameter.ROTX:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          if (dataIsRadian) {
            this.coordinateParameters[i].setRotX(this.data.getElement(row, i + 1).doubleValue());
          } else {
            this.coordinateParameters[i].setRotX(Math.toRadians(this.data.getElement(row, i + 1).doubleValue()));
          }
        }
        break;
      case CoordinateParameter.ROTY:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          if (dataIsRadian) {
            this.coordinateParameters[i].setRotY(this.data.getElement(row, i + 1).doubleValue());
          } else {
            this.coordinateParameters[i].setRotY(Math.toRadians(this.data.getElement(row, i + 1).doubleValue()));
          }
        }
        break;
      case CoordinateParameter.ROTZ:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          if (dataIsRadian) {
            this.coordinateParameters[i].setRotZ(this.data.getElement(row, i + 1).doubleValue());
          } else {
            this.coordinateParameters[i].setRotZ(Math.toRadians(this.data.getElement(row, i + 1).doubleValue()));
          }
        }
        break;
      default:
        throw new AssertionError(Messages.getString("DataPicker.1")); //$NON-NLS-1$
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setDHParameter(int type, double value) {
    switch (type) {
      case DHParameter.A:
        for (int i = 0; i < this.dhParameters.length; i++) {
          this.dhParameters[i].setA(value / modelScale + this.dhParameters[i].getA());
        }
        break;
      case DHParameter.ALPHA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          // どうにかする。
          if (Java3dModelCanvas.radian) {
            this.dhParameters[i].setAlpha(value + this.dhParameters[i].getAlpha());
          } else {
            this.dhParameters[i].setAlpha(Math.toRadians(value) + this.dhParameters[i].getAlpha());
          }
        }

        break;
      case DHParameter.D:
        for (int i = 0; i < this.dhParameters.length; i++) {
          this.dhParameters[i].setD(value / modelScale + this.dhParameters[i].getD());
        }
        break;
      case DHParameter.THETA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          if (Java3dModelCanvas.radian) {
            this.dhParameters[i].setTheta(value + this.dhParameters[i].getTheta());
          } else {
            this.dhParameters[i].setTheta(Math.toRadians(value) + this.dhParameters[i].getTheta());
          }
        }
        break;
      default:
        throw new AssertionError(Messages.getString("DataPicker.2")); //$NON-NLS-1$
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setCoordinateParameter(int type, double value) {
    switch (type) {
      case CoordinateParameter.LOCX:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setLocX(value / modelScale + this.coordinateParameters[i].getLocX());
        }
        break;
      case CoordinateParameter.LOCY:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setLocY(value / modelScale + this.coordinateParameters[i].getLocY());
        }
        break;
      case CoordinateParameter.LOCZ:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setLocZ(value / modelScale + this.coordinateParameters[i].getLocZ());
        }
        break;
      case CoordinateParameter.ROTX:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          if (Java3dModelCanvas.radian) {
            this.coordinateParameters[i].setRotX(value + this.coordinateParameters[i].getRotX());
          } else {
            this.coordinateParameters[i].setRotX(Math.toRadians(value) + this.coordinateParameters[i].getRotX());
          }
        }
        break;
      case CoordinateParameter.ROTY:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          if (Java3dModelCanvas.radian) {
            this.coordinateParameters[i].setRotY(value + this.coordinateParameters[i].getRotY());
          } else {
            this.coordinateParameters[i].setRotY(Math.toRadians(value) + this.coordinateParameters[i].getRotY());
          }
        }
        break;
      case CoordinateParameter.ROTZ:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          if (Java3dModelCanvas.radian) {
            this.coordinateParameters[i].setRotZ(value + this.coordinateParameters[i].getRotZ());
          } else {
            this.coordinateParameters[i].setRotZ(Math.toRadians(value) + this.coordinateParameters[i].getRotZ());
          }
        }
        break;
      default:
        throw new AssertionError(Messages.getString("DataPicker.3")); //$NON-NLS-1$
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getColumn(double time) {
    // 時系列データからそれに最も近い時間のある行を返す。
    DoubleMatrix error = this.data.getRowVector(1).subtractElementWise(time).absElementWise();
    return error.minimumRowWise().getIndices().getIntElement(1);
  }

  /**
   * {@inheritDoc}
   */
  public double getValue(int row, double time) {
    return getValue(row, getColumn(time));
  }

  /**
   * {@inheritDoc}
   */
  public double getValue(int row, int col) {
    return this.data.getElement(row, col).doubleValue();
  }

  /**
   * {@inheritDoc}
   */
  public int getDataCount() {
    return this.data.getColumnSize();
  }

  /**
   * {@inheritDoc}
   */
  public double getEndTime() {
    return this.data.getElement(1, getDataCount()).doubleValue();
  }

  /**
   * {@inheritDoc}
   */
  public double getStartTime() {
    return this.data.getElement(1, 1).doubleValue();
  }

//  /**
//   * @param dScale スケール
//   * @param dRadian 角度
//   * @param mRadian 角度
//   * @param mScale スケール
//   */
//  public static void setScale(int dScale, boolean dRadian, int mScale, boolean mRadian) {
//    // dataScale = dScale;
//    dataIsRadian = dRadian;
//    modelScale = mScale;
//    modelIsRadian = mRadian;
//  }
}