/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.java3d.Java3dModelCanvas;
import org.mklab.mikity.java3d.MyTransformGroup;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.CoordinateParameterType;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.DHParameterType;
import org.mklab.nfc.matrix.DoubleMatrix;
import org.mklab.nfc.matrix.Matrix;


/**
 * データを抽出するため抽象クラスです。
 * @author miki
 * @version $Revision: 1.7 $.2005/01/17
 */
public abstract class AbstractDataPicker implements DataPicker {

  private DoubleMatrix data;
  /** DHパラメータ */
  protected DHParameter[] dhParameters;
  /** 座標パラメータ */
  protected CoordinateParameter[] coordinateParameters;
  /** */
  protected MyTransformGroup tg;

  private static int dataScale = 1;
  private static int modelScale = 1;
  private static boolean dataIsRadian = true;

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
  public final void addMoveType(DHParameterType type, int row) {
    if (this.data.getRowSize() < row) {
      throw new IllegalArgumentException(); 
    }

    switch (type) {
      case A:
        for (int i = 0; i < this.dhParameters.length; i++) {
          double value = this.data.getElement(row, i + 1).doubleValue();
          this.dhParameters[i].setA(value / dataScale);
        }
        break;
      case ALPHA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          double value = this.data.getElement(row, i + 1).doubleValue();
          if (dataIsRadian) {
            this.dhParameters[i].setAlpha(value);
          } else {
            this.dhParameters[i].setAlpha(Math.toRadians(value));
          }
        }
        break;
      case D:
        for (int i = 0; i < this.dhParameters.length; i++) {
          double value = this.data.getElement(row, i + 1).doubleValue();
          this.dhParameters[i].setD(value / dataScale);
        }
        break;
      case THETA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          double value = this.data.getElement(row, i + 1).doubleValue();
          if (dataIsRadian) {
            this.dhParameters[i].setTheta(value);
          } else {
            this.dhParameters[i].setTheta(Math.toRadians(value));
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
  public final void addMoveType(CoordinateParameterType type, int row) {
    if (this.data.getRowSize() < row) {
      throw new IllegalAccessError();
    }

    switch (type) {
      case LOCX:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(row, i + 1).doubleValue();
          this.coordinateParameters[i].setLocX(value / dataScale);
        }
        break;
      case LOCY:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(row, i + 1).doubleValue();
          this.coordinateParameters[i].setLocY(value / dataScale);
        }
        break;
      case LOCZ:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(row, i + 1).doubleValue();
          this.coordinateParameters[i].setLocZ(value / dataScale);
        }
        break;
      case ROTX:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(row, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setRotX(value);
          } else {
            this.coordinateParameters[i].setRotX(Math.toRadians(value));
          }
        }
        break;
      case ROTY:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(row, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setRotY(value);
          } else {
            this.coordinateParameters[i].setRotY(Math.toRadians(value));
          }
        }
        break;
      case ROTZ:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(row, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setRotZ(value);
          } else {
            this.coordinateParameters[i].setRotZ(Math.toRadians(value));
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
  public void setParameter(DHParameterType type, double value) {
    switch (type) {
      case A:
        for (int i = 0; i < this.dhParameters.length; i++) {
          this.dhParameters[i].setA(value / modelScale + this.dhParameters[i].getA());
        }
        break;
      case ALPHA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          // どうにかする。
          if (Java3dModelCanvas.radian) {
            this.dhParameters[i].setAlpha(value + this.dhParameters[i].getAlpha());
          } else {
            this.dhParameters[i].setAlpha(Math.toRadians(value) + this.dhParameters[i].getAlpha());
          }
        }

        break;
      case D:
        for (int i = 0; i < this.dhParameters.length; i++) {
          this.dhParameters[i].setD(value / modelScale + this.dhParameters[i].getD());
        }
        break;
      case THETA:
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
  public void setParameter(CoordinateParameterType type, double value) {
    switch (type) {
      case LOCX:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setLocX(value / modelScale + this.coordinateParameters[i].getLocX());
        }
        break;
      case LOCY:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setLocY(value / modelScale + this.coordinateParameters[i].getLocY());
        }
        break;
      case LOCZ:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setLocZ(value / modelScale + this.coordinateParameters[i].getLocZ());
        }
        break;
      case ROTX:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          if (Java3dModelCanvas.radian) {
            this.coordinateParameters[i].setRotX(value + this.coordinateParameters[i].getRotX());
          } else {
            this.coordinateParameters[i].setRotX(Math.toRadians(value) + this.coordinateParameters[i].getRotX());
          }
        }
        break;
      case ROTY:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          if (Java3dModelCanvas.radian) {
            this.coordinateParameters[i].setRotY(value + this.coordinateParameters[i].getRotY());
          } else {
            this.coordinateParameters[i].setRotY(Math.toRadians(value) + this.coordinateParameters[i].getRotY());
          }
        }
        break;
      case ROTZ:
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
  public int getColumn(double t) {
    // 時系列データからそれに最も近い時間のある行を返す。
    DoubleMatrix error = this.data.getRowVector(1).subtractElementWise(t).absElementWise();
    return error.minimumRowWise().getIndices().getIntElement(1);
  }

  /**
   * {@inheritDoc}
   */
  public double getValue(int row, double t) {
    return getValue(row, getColumn(t));
  }

  /**
   * {@inheritDoc}
   */
  public double getValue(int row, int column) {
    return this.data.getElement(row, column).doubleValue();
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
}