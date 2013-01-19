/*
 * Created on 2012/11/05
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.CoordinateParameterType;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.DHParameterType;


/**
 * データを抽出器を表すインターフェースです。
 * @author koga
 * @version $Revision$, 2012/11/05
 */
public interface DataPicker {
  /**
   * 指定された時刻のDHパラメータを返します。
   * @param t 時刻
   * @return 指定された時刻のDHパラメータ
   */
  DHParameter getDHParameter(double t);

  /**
   * 指定された時刻の座標パラメータを返します。
   * @param t 時刻
   * @return 指定された時刻の座標パラメータ
   */
  CoordinateParameter getCoordinateParameter(double t);

  /**
   * データをピックアップし，パラメータを設定します。
   * @param type パラメータのタイプ
   * @param dataIndex データ番号
   */
  void pickup(DHParameterType type, int dataIndex);

  /**
   * データをピックアップし，パラメータを設定します。
   * @param type パラメータのタイプ
   * @param dataIndex データ番号
   */
  void pickup(CoordinateParameterType type, int dataIndex);

  /**
   * 値を設定します。
   * 
   * @param type パラメータのタイプ
   * @param value 値
   */
  void setParameter(DHParameterType type, double value);

  /**
   * 値を設定します。
   * 
   * @param type パラメータのタイプ
   * @param value 値
   */
  void setParameter(CoordinateParameterType type, double value);

  /**
   * データの個数を返します。
   * @return データの個数
   */
  int getDataSize();

  /**
   * 終了時間を返します。
   * @return 終了時間
   */
  double getEndTime();

  /**
   * 開始時間を返す。
   * 
   * @return 開始時間
   */
  double getStartTime();

}