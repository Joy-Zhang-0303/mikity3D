/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.sampler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mklab.mikity.model.CoordinateParameterType;
import org.mklab.mikity.model.xml.simplexml.model.SourceModel;
import org.mklab.nfc.matrix.DoubleMatrix;


/**
 * データを抽出するため抽象クラスです。
 * 
 * @author miki
 * @version $Revision: 1.7 $.2005/01/17
 */
public abstract class AbstractDataSampler implements DataSampler {
  /** 関連するデータが存在する時刻に対応するデータ番号。 */
  static Map<String,Integer> timeNumbers = new HashMap<>();
  
  /** 座標パラメータの型。 */
  List<CoordinateParameterType> types = new ArrayList<>();
  
  /** ソース。 */
  List<SourceModel> sources = new ArrayList<>();
  
  /** ソースデータ。 */
  Map<String, DoubleMatrix> sourceData = new HashMap<>();
  
  /** データの個数。 */
  private int dataSize = 0;
  /** 開始時間。 */
  private double startTime = 0;
  /** 終了時間。 */
  private double endTime = 0;

  /**
   * 新しく生成された<code>AbstractDataSampler</code>オブジェクトを初期化します。
   * @param sourceData ソースデータ
   */
  public AbstractDataSampler(Map<String,DoubleMatrix> sourceData) {
    this.sourceData = sourceData;
  }

  /**
   * {@inheritDoc}
   */
  public final void sample(CoordinateParameterType type, SourceModel source) {
    this.types.add(type);
    this.sources.add(source);
    
    final DoubleMatrix data = this.sourceData.get(source.getId());

    if (this.sources.size() == 1) {
      this.dataSize = data.getColumnSize();
      this.startTime = data.getElement(1, 1).doubleValue();
      this.endTime =  data.getElement(1, data.getColumnSize()).doubleValue();
    } else {
      this.dataSize = Math.max(this.dataSize, data.getColumnSize());
      this.startTime = Math.min(this.startTime, data.getElement(1, 1).doubleValue());
      this.endTime = Math.max(this.endTime, data.getElement(1, data.getColumnSize()).doubleValue());
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getDataSize() {
    return this.dataSize;
  }

  /**
   * {@inheritDoc}
   */
  public double getEndTime() {
    return this.endTime;
  }

  /**
   * {@inheritDoc}
   */
  public double getStartTime() {
    return this.startTime;
  }
  
  /**
   * 関連するデータが存在する時刻に対応するデータ番号を初期化します。
   */
  public static void clearTimeNumbers() {
    AbstractDataSampler.timeNumbers.clear();
  }
}