/*
 * Created on 2012/11/05
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.sampler;

import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.CoordinateParameterType;
import org.mklab.mikity.model.xml.simplexml.model.SourceModel;


/**
 * データの抽出器を表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2012/11/05
 */
public interface DataSampler {
  /**
   * 指定された時刻の座標パラメータを返します。
   * 
   * @param t 時刻
   * @return 指定された時刻の座標パラメータ
   */
  Coordinate getCoordinate(double t);

  /**
   * 抽出するパラメータの型と番号を設定します。
   * 
   * @param type パラメータの型
   * @param source ソース
   */
  void sample(CoordinateParameterType type, SourceModel source);

  /**
   * データの個数を返します。
   * 
   * @return データの個数
   */
  int getDataSize();

  /**
   * 終了時間を返します。
   * 
   * @return 終了時間
   */
  double getEndTime();

  /**
   * 開始時間を返します。
   * 
   * @return 開始時間
   */
  double getStartTime();

}