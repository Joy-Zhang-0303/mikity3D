/*
 * Created on 2012/11/05
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;


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
   * @param moveType タイプ
   * @param row 行
   */
  void addMoveTypeDH(int moveType, int row);

  /**
   * @param moveType タイプ
   * @param row 行
   */
  void addMoveType(int moveType, int row);

  /**
   * initialTranformに固定値をセットする
   * @param type タイプ
   * @param value 値
   */
  void setConstantDHParameter(int type, double value);

  /**
   * @param type セットタイプ
   * @param value initialTranformに固定値をセットする
   */
  void setConstantCoordinateParameter(int type, double value);

  /**
   * @param time 時間を与えると、それに最も近い時間のある行rowを返す
   * @return getValue(row, getColumn(time))
   */
  int getColumn(double time);

  /**
   * @param row データの行数（何番目のデータか）
   * @param time 時刻
   * @return 時刻timeのときのrow行目のデータ
   */
  double getValue(int row, double time);

  /**
   * @param row 行
   * @param col 列
   * @return data.getElement(row, col)
   */
  double getValue(int row, int col);

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