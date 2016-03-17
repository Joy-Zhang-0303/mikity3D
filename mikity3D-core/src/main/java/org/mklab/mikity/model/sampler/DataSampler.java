/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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