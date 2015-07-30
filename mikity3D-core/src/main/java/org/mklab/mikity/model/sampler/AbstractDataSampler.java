/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.sampler;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.CoordinateParameterType;
import org.mklab.nfc.matrix.DoubleMatrix;


/**
 * データを抽出するため抽象クラスです。
 * 
 * @author miki
 * @version $Revision: 1.7 $.2005/01/17
 */
public abstract class AbstractDataSampler implements DataSampler {
  /** データ。 */
  DoubleMatrix data;
  
  /** 座標パラメータの型。 */
  List<CoordinateParameterType> types = new ArrayList<>();
  
  /** データの番号。 */
  List<Integer> dataNumbers = new ArrayList<>();

  /**
   * 新しく生成された<code>AbstractDataSampler</code>オブジェクトを初期化します。
   * @param data データ
   */
  public AbstractDataSampler(DoubleMatrix data) {
    this.data = data;
  }

  /**
   * {@inheritDoc}
   */
  public final void sample(CoordinateParameterType type, int dataNumber) {
    this.types.add(type);
    this.dataNumbers.add(Integer.valueOf(dataNumber));
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