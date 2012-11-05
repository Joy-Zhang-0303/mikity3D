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
   * @param type タイプ
   * @param row 行
   */
  void addMoveTypeDH(DHParameterType type, int row);

  /**
   * @param type タイプ
   * @param row 行
   */
  void addMoveTypeCoordinate(CoordinateParameterType type, int row);

  /**
   * initialTranformに固定値をセットする
   * @param type タイプ
   * @param value 値
   */
  void setDHParameter(DHParameterType type, double value);

  /**
   * @param type セットタイプ
   * @param value initialTranformに固定値をセットする
   */
  void setCoordinateParameter(CoordinateParameterType type, double value);

  /**
   * @param t 時間を与えると、それに最も近い時間のある行rowを返す
   * @return getValue(row, getColumn(time))
   */
  int getColumn(double t);

  /**
   * @param row データの行数（何番目のデータか）
   * @param t 時刻
   * @return 時刻timeのときのrow行目のデータ
   */
  double getValue(int row, double t);

  /**
   * @param row 行
   * @param column 列
   * @return data.getElement(row, column)
   */
  double getValue(int row, int column);

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