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
   * データを読み込み，パラメータを設定します。
   * @param type パラメータのタイプ
   * @param dataNumber データ番号
   */
  void readDataAndSetParameter(DHParameterType type, int dataNumber);

  /**
   * データを読み込み，パラメータを設定します。
   * @param type パラメータのタイプ
   * @param dataNumber データ番号
   */
  void readDataAndSetParameter(CoordinateParameterType type, int dataNumber);

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
   * 与えられた時間に最も近いデータが存在する時刻に対応するデータ番号を返します。
   * 
   * @param t 時間
   * @return 与えられた時間に最も近いデータが存在する時刻に対応するデータ番号
   */
  int getDataNumber(double t);

  /**
   * データ数を返します。
   * @return データ数
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