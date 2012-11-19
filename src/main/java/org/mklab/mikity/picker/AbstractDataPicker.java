/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.CoordinateParameterType;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.DHParameterType;
import org.mklab.mikity.util.Util;
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
  public final void readDataAndSetParameter(DHParameterType type, int dataNumber) {
    if (this.data.getRowSize() < dataNumber) {
      throw new IllegalArgumentException(); 
    }

    switch (type) {
      case A:
        for (int i = 0; i < this.dhParameters.length; i++) {
          double value = this.data.getElement(dataNumber, i + 1).doubleValue();
          this.dhParameters[i].setA(value / dataScale);
        }
        break;
      case ALPHA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          double value = this.data.getElement(dataNumber, i + 1).doubleValue();
          if (dataIsRadian) {
            this.dhParameters[i].setAlpha(value);
          } else {
            this.dhParameters[i].setAlpha(Math.toRadians(value));
          }
        }
        break;
      case D:
        for (int i = 0; i < this.dhParameters.length; i++) {
          double value = this.data.getElement(dataNumber, i + 1).doubleValue();
          this.dhParameters[i].setD(value / dataScale);
        }
        break;
      case THETA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          double value = this.data.getElement(dataNumber, i + 1).doubleValue();
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
  public final void readDataAndSetParameter(CoordinateParameterType type, int dataNumber) {
    if (this.data.getRowSize() < dataNumber) {
      throw new IllegalAccessError();
    }

    switch (type) {
      case X:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(dataNumber, i + 1).doubleValue();
          this.coordinateParameters[i].setX(value / dataScale);
        }
        break;
      case Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(dataNumber, i + 1).doubleValue();
          this.coordinateParameters[i].setY(value / dataScale);
        }
        break;
      case Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(dataNumber, i + 1).doubleValue();
          this.coordinateParameters[i].setZ(value / dataScale);
        }
        break;
      case TH_X:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(dataNumber, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setThX(value);
          } else {
            this.coordinateParameters[i].setThX(Math.toRadians(value));
          }
        }
        break;
      case TH_Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(dataNumber, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setThY(value);
          } else {
            this.coordinateParameters[i].setThY(Math.toRadians(value));
          }
        }
        break;
      case TH_Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double value = this.data.getElement(dataNumber, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setThZ(value);
          } else {
            this.coordinateParameters[i].setThZ(Math.toRadians(value));
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
          if (Util.radian) {
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
          if (Util.radian) {
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
      case X:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setX(value / modelScale + this.coordinateParameters[i].getX());
        }
        break;
      case Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setY(value / modelScale + this.coordinateParameters[i].getY());
        }
        break;
      case Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          this.coordinateParameters[i].setZ(value / modelScale + this.coordinateParameters[i].getZ());
        }
        break;
      case TH_X:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          if (Util.radian) {
            this.coordinateParameters[i].setThX(value + this.coordinateParameters[i].getRotX());
          } else {
            this.coordinateParameters[i].setThX(Math.toRadians(value) + this.coordinateParameters[i].getRotX());
          }
        }
        break;
      case TH_Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          if (Util.radian) {
            this.coordinateParameters[i].setThY(value + this.coordinateParameters[i].getThY());
          } else {
            this.coordinateParameters[i].setThY(Math.toRadians(value) + this.coordinateParameters[i].getThY());
          }
        }
        break;
      case TH_Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          if (Util.radian) {
            this.coordinateParameters[i].setThZ(value + this.coordinateParameters[i].getThZ());
          } else {
            this.coordinateParameters[i].setThZ(Math.toRadians(value) + this.coordinateParameters[i].getThZ());
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
  public int getDataNumber(double t) {
    DoubleMatrix error = this.data.getRowVector(1).subtractElementWise(t).absElementWise();
    return error.minimumRowWise().getIndices().getIntElement(1);
  }

  /**
   * {@inheritDoc}
   */
  public int getDataSize() {
    return this.data.getColumnSize();
  }

  /**
   * {@inheritDoc}
   */
  public double getEndTime() {
    return this.data.getElement(1, getDataSize()).doubleValue();
  }

  /**
   * {@inheritDoc}
   */
  public double getStartTime() {
    return this.data.getElement(1, 1).doubleValue();
  }
}