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
  /** データ。 */
  private DoubleMatrix data;
  /** DHパラメータ */
  protected DHParameter[] dhParameters;
  /** 座標パラメータ */
  protected CoordinateParameter[] coordinateParameters;

  private static int dataScale = 1;
  private static int modelScale = 1;
  private static boolean dataIsRadian = true;

  /**
   * 新しく生成された<code>AbstractDataPicker</code>オブジェクトを初期化します。
   * @param data data データ
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
      case TRANSLATION_X:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          this.coordinateParameters[i].setTranslationX(value / dataScale);
        }
        break;
      case TRANSLATION_Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          this.coordinateParameters[i].setTranslationY(value / dataScale);
        }
        break;
      case TRANSLATION_Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          this.coordinateParameters[i].setTranslationZ(value / dataScale);
        }
        break;
      case ROTATION_X:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setRotationX(value);
          } else {
            this.coordinateParameters[i].setRotationX(Math.toRadians(value));
          }
        }
        break;
      case ROTATION_Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setRotationY(value);
          } else {
            this.coordinateParameters[i].setRotationY(Math.toRadians(value));
          }
        }
        break;
      case ROTATION_Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double value = this.data.getElement(dataIndex, i + 1).doubleValue();
          if (dataIsRadian) {
            this.coordinateParameters[i].setRotationZ(value);
          } else {
            this.coordinateParameters[i].setRotationZ(Math.toRadians(value));
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
      case TRANSLATION_X:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double x = this.coordinateParameters[i].getTranslationX();
          this.coordinateParameters[i].setTranslationX(x + value / modelScale);
        }
        break;
      case TRANSLATION_Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double y = this.coordinateParameters[i].getTranslationY();
          this.coordinateParameters[i].setTranslationY(y + value / modelScale );
        }
        break;
      case TRANSLATION_Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double z = this.coordinateParameters[i].getTranslationZ();
          this.coordinateParameters[i].setTranslationZ(z + value / modelScale);
        }
        break;
      case ROTATION_X:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          final double angleX = this.coordinateParameters[i].getRotationX();
          if (Util.radian) {
            this.coordinateParameters[i].setRotationX(angleX + value);
          } else {
            this.coordinateParameters[i].setRotationX(angleX + Math.toRadians(value));
          }
        }
        break;
      case ROTATION_Y:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double angleY = this.coordinateParameters[i].getRotationY();
          if (Util.radian) {
            this.coordinateParameters[i].setRotationY(angleY + value);
          } else {
            this.coordinateParameters[i].setRotationY(angleY + Math.toRadians(value));
          }
        }
        break;
      case ROTATION_Z:
        for (int i = 0; i < this.coordinateParameters.length; i++) {
          double angleZ = this.coordinateParameters[i].getRotationZ();
          if (Util.radian) {
            this.coordinateParameters[i].setRotationZ(angleZ + value);
          } else {
            this.coordinateParameters[i].setRotationZ(angleZ + Math.toRadians(value));
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