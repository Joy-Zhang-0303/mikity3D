/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.sampler;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.CoordinateParameterType;
import org.mklab.nfc.matrix.DoubleMatrix;
import org.mklab.nfc.matrix.Matrix;


/**
 * データを抽出するため抽象クラスです。
 * 
 * @author miki
 * @version $Revision: 1.7 $.2005/01/17
 */
public abstract class AbstractDataSampler implements DataSampler {
  /** データ。 */
  private DoubleMatrix data;
  /** 座標パラメータ。  */
  protected CoordinateParameter[] parameters;

  private int dataScale = 1;
  //private int modelScale = 1;
  private boolean dataIsRadian = true;

  /**
   * 新しく生成された<code>AbstractDataSampler</code>オブジェクトを初期化します。
   * @param data data データ
   */
  public AbstractDataSampler(Matrix data) {
    this.data = (DoubleMatrix)data;

    final int objectSize = data.getColumnSize();
    
    this.parameters = new CoordinateParameter[objectSize];
    for (int i = 0; i < objectSize; i++) {
      this.parameters[i] = new CoordinateParameter();
    }
  }

  /**
   * {@inheritDoc}
   */
  public final void sample(CoordinateParameterType type, int index) {
    if (this.data.getRowSize() < index) {
      throw new IllegalAccessError();
    }

    switch (type) {
      case TRANSLATION_X:
        for (int i = 0; i < this.parameters.length; i++) {
          final double value = this.data.getElement(index, i + 1).doubleValue();
          this.parameters[i].setTranslationX(value / this.dataScale);
        }
        break;
      case TRANSLATION_Y:
        for (int i = 0; i < this.parameters.length; i++) {
          final double value = this.data.getElement(index, i + 1).doubleValue();
          this.parameters[i].setTranslationY(value / this.dataScale);
        }
        break;
      case TRANSLATION_Z:
        for (int i = 0; i < this.parameters.length; i++) {
          final double value = this.data.getElement(index, i + 1).doubleValue();
          this.parameters[i].setTranslationZ(value / this.dataScale);
        }
        break;
      case ROTATION_X:
        for (int i = 0; i < this.parameters.length; i++) {
          final double value = this.data.getElement(index, i + 1).doubleValue();
          if (this.dataIsRadian) {
            this.parameters[i].setRotationX(value);
          } else {
            this.parameters[i].setRotationX(Math.toRadians(value));
          }
        }
        break;
      case ROTATION_Y:
        for (int i = 0; i < this.parameters.length; i++) {
          final double value = this.data.getElement(index, i + 1).doubleValue();
          if (this.dataIsRadian) {
            this.parameters[i].setRotationY(value);
          } else {
            this.parameters[i].setRotationY(Math.toRadians(value));
          }
        }
        break;
      case ROTATION_Z:
        for (int i = 0; i < this.parameters.length; i++) {
          final double value = this.data.getElement(index, i + 1).doubleValue();
          if (this.dataIsRadian) {
            this.parameters[i].setRotationZ(value);
          } else {
            this.parameters[i].setRotationZ(Math.toRadians(value));
          }
        }
        break;
      default:
        throw new AssertionError(Messages.getString("DataPicker.1")); //$NON-NLS-1$
    }
  }

//  /**
//   * {@inheritDoc}
//   */
//  public void setParameter(CoordinateParameterType type, double value) {
//    switch (type) {
//      case TRANSLATION_X:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double translationX = this.parameters[i].getTranslationX();
//          this.parameters[i].setTranslationX(translationX + value / this.modelScale);
//        }
//        break;
//      case TRANSLATION_Y:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double translationY = this.parameters[i].getTranslationY();
//          this.parameters[i].setTranslationY(translationY + value / this.modelScale );
//        }
//        break;
//      case TRANSLATION_Z:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double translationZ = this.parameters[i].getTranslationZ();
//          this.parameters[i].setTranslationZ(translationZ + value / this.modelScale);
//        }
//        break;
//      case ROTATION_X:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double rotationX = this.parameters[i].getRotationX();
//          if (Util.radian) {
//            this.parameters[i].setRotationX(rotationX + value);
//          } else {
//            this.parameters[i].setRotationX(rotationX + Math.toRadians(value));
//          }
//        }
//        break;
//      case ROTATION_Y:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double rotationY = this.parameters[i].getRotationY();
//          if (Util.radian) {
//            this.parameters[i].setRotationY(rotationY + value);
//          } else {
//            this.parameters[i].setRotationY(rotationY + Math.toRadians(value));
//          }
//        }
//        break;
//      case ROTATION_Z:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double rotationZ = this.parameters[i].getRotationZ();
//          if (Util.radian) {
//            this.parameters[i].setRotationZ(rotationZ + value);
//          } else {
//            this.parameters[i].setRotationZ(rotationZ + Math.toRadians(value));
//          }
//        }
//        break;
//      default:
//        throw new AssertionError(Messages.getString("DataPicker.3")); //$NON-NLS-1$
//    }
//  }

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