/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.picker;

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

    final int movableObjectSize = data.getColumnSize();
    
    this.dhParameters = new DHParameter[movableObjectSize];
    for (int i = 0; i < movableObjectSize; i++) {
      this.dhParameters[i] = new DHParameter();
    }
    
    this.coordinateParameters = new CoordinateParameter[movableObjectSize];
    for (int i = 0; i < movableObjectSize; i++) {
      this.coordinateParameters[i] = new CoordinateParameter();
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public final void pickup(DHParameterType type, int dataIndex) {
    if (this.data.getRowSize() < dataIndex) {
      throw new IllegalArgumentException(); 
    }

    switch (type) {
      case A:
        for (int i = 0; i < this.dhParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          this.dhParameters[i].setA(value / dataScale);
        }
        break;
      case ALPHA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          if (dataIsRadian) {
            this.dhParameters[i].setAlpha(value);
          } else {
            this.dhParameters[i].setAlpha(Math.toRadians(value));
          }
        }
        break;
      case D:
        for (int i = 0; i < this.dhParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          this.dhParameters[i].setD(value / dataScale);
        }
        break;
      case THETA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
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
  public final void pickup(CoordinateParameterType type, int dataIndex) {
    if (this.data.getRowSize() < dataIndex) {
      throw new IllegalAccessError();
    }

    switch (type) {
      case X:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          this.coordinateParameters[i].setX(value / dataScale);
        }
        break;
      case Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          this.coordinateParameters[i].setY(value / dataScale);
        }
        break;
      case Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          this.coordinateParameters[i].setZ(value / dataScale);
        }
        break;
      case ANGLE_X:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setAngleX(value);
          } else {
            this.coordinateParameters[i].setAngleX(Math.toRadians(value));
          }
        }
        break;
      case ANGLE_Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setAngleY(value);
          } else {
            this.coordinateParameters[i].setAngleY(Math.toRadians(value));
          }
        }
        break;
      case ANGLE_Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setAngleZ(value);
          } else {
            this.coordinateParameters[i].setAngleZ(Math.toRadians(value));
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
          final double a = this.dhParameters[i].getA();
          this.dhParameters[i].setA(a + value / modelScale);
        }
        break;
      case ALPHA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          final double alpha = this.dhParameters[i].getAlpha();
          if (Util.radian) {
            this.dhParameters[i].setAlpha(alpha + value);
          } else {
            this.dhParameters[i].setAlpha(alpha + Math.toRadians(value));
          }
        }

        break;
      case D:
        for (int i = 0; i < this.dhParameters.length; i++) {
          final double d = this.dhParameters[i].getD();
          this.dhParameters[i].setD(d + value / modelScale);
        }
        break;
      case THETA:
        for (int i = 0; i < this.dhParameters.length; i++) {
          final double theta = this.dhParameters[i].getTheta();
          if (Util.radian) {
            this.dhParameters[i].setTheta(theta + value );
          } else {
            this.dhParameters[i].setTheta(theta + Math.toRadians(value));
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
          final double x = this.coordinateParameters[i].getX();
          this.coordinateParameters[i].setX(x + value / modelScale);
        }
        break;
      case Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double y = this.coordinateParameters[i].getY();
          this.coordinateParameters[i].setY(y + value / modelScale );
        }
        break;
      case Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double z = this.coordinateParameters[i].getZ();
          this.coordinateParameters[i].setZ(z + value / modelScale);
        }
        break;
      case ANGLE_X:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double angleX = this.coordinateParameters[i].getAngleX();
          if (Util.radian) {
            this.coordinateParameters[i].setAngleX(angleX + value);
          } else {
            this.coordinateParameters[i].setAngleX(angleX + Math.toRadians(value));
          }
        }
        break;
      case ANGLE_Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double angleY = this.coordinateParameters[i].getAngleY();
          if (Util.radian) {
            this.coordinateParameters[i].setAngleY(angleY + value);
          } else {
            this.coordinateParameters[i].setAngleY(angleY + Math.toRadians(value));
          }
        }
        break;
      case ANGLE_Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double angleZ = this.coordinateParameters[i].getAngleZ();
          if (Util.radian) {
            this.coordinateParameters[i].setAngleZ(angleZ + value);
          } else {
            this.coordinateParameters[i].setAngleZ(angleZ + Math.toRadians(value));
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
  
  /**
   * データを返します。
   * @return データ
   */
  DoubleMatrix getData() {
    return this.data;
  }
}