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
   * @param type パラメータのタイプ
   * @param dataNumber データ番号
   */
  void readAndSetParameter(DHParameterType type, int dataNumber);

  /**
   * @param type パラメータのタイプ
   * @param dataNumber データ番号
   */
  void readAndSetParameter(CoordinateParameterType type, int dataNumber);

  /**
   * initialTranformに値を設定します。
   * 
   * @param type パラメータのタイプ
   * @param value 値
   */
  void setParameter(DHParameterType type, double value);

  /**
   * initialTranformに値を設定します。
   * 
   * @param type パラメータのタイプ
   * @param value 値
   */
  void setParameter(CoordinateParameterType type, double value);

  /**
   * 与えられた時刻に最も近いデータが存在する時刻に対応するデータ番号を返します。
   * 
   * @param t 時刻
   * @return 与えられた時刻に最も近いデータが存在する時刻に対応するデータ番号
   */
  int getDataNumber(double t);

//  /**
//   * @param row データの番号
//   * @param t 時刻
//   * @return 時刻tのときのdataNumber番のデータ
//   */
//  double getValue(int row, double t);

//  /**
//   * @param row 行
//   * @param column 列
//   * @return data.getElement(row, column)
//   */
//  double getValue(int row, int column);

  /**
   * @return data.getColSize()
   */
  int getDataCount();

  /**
   * @return data.getElement(1, getDataCount())
   */
  double getEndTime();

  /**
   * シミュレーションの開始時刻を返す。
   * 
   * @return シミュレーションの開始時刻
   */
  double getStartTime();

}