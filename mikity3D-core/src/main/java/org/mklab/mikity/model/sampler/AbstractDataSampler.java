/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.sampler;

import java.util.ArrayList;
import java.util.List;

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
  DoubleMatrix data;
  /** 座標パラメータ。  */
  CoordinateParameter[] parameters;

//  private int dataScale = 1;
//
//  private boolean dataIsRadian = true;
  
  List<CoordinateParameterType> types = new ArrayList<>();
  
  List<Integer> dataNumbers = new ArrayList<>();

  /**
   * 新しく生成された<code>AbstractDataSampler</code>オブジェクトを初期化します。
   * @param data データ
   */
  public AbstractDataSampler(Matrix data) {
    this.data = (DoubleMatrix)data;

//    final int objectSize = data.getColumnSize();
//    
//    this.parameters = new CoordinateParameter[objectSize];
//    for (int i = 0; i < objectSize; i++) {
//      this.parameters[i] = new CoordinateParameter();
//    }
  }

  /**
   * {@inheritDoc}
   */
  public final void sample(CoordinateParameterType type, int dataNumber) {
    this.types.add(type);
    this.dataNumbers.add(Integer.valueOf(dataNumber));
    
//    if (this.data.getRowSize() < dataNumber) {
//      throw new IllegalAccessError();
//    }

//    switch (type) {
//      case TRANSLATION_X:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double value = this.data.getElement(dataNumber, i + 1).doubleValue();
//          this.parameters[i].setTranslationX(value / this.dataScale);
//        }
//        break;
//      case TRANSLATION_Y:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double value = this.data.getElement(dataNumber, i + 1).doubleValue();
//          this.parameters[i].setTranslationY(value / this.dataScale);
//        }
//        break;
//      case TRANSLATION_Z:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double value = this.data.getElement(dataNumber, i + 1).doubleValue();
//          this.parameters[i].setTranslationZ(value / this.dataScale);
//        }
//        break;
//      case ROTATION_X:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double value = this.data.getElement(dataNumber, i + 1).doubleValue();
//          if (this.dataIsRadian) {
//            this.parameters[i].setRotationX(value);
//          } else {
//            this.parameters[i].setRotationX(Math.toRadians(value));
//          }
//        }
//        break;
//      case ROTATION_Y:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double value = this.data.getElement(dataNumber, i + 1).doubleValue();
//          if (this.dataIsRadian) {
//            this.parameters[i].setRotationY(value);
//          } else {
//            this.parameters[i].setRotationY(Math.toRadians(value));
//          }
//        }
//        break;
//      case ROTATION_Z:
//        for (int i = 0; i < this.parameters.length; i++) {
//          final double value = this.data.getElement(dataNumber, i + 1).doubleValue();
//          if (this.dataIsRadian) {
//            this.parameters[i].setRotationZ(value);
//          } else {
//            this.parameters[i].setRotationZ(Math.toRadians(value));
//          }
//        }
//        break;
//      default:
//        throw new AssertionError(Messages.getString("DataPicker.1")); //$NON-NLS-1$
//    }
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