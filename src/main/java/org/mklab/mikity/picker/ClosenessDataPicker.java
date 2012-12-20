/*
 * Created on 2005/01/12
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.nfc.matrix.DoubleMatrix;
import org.mklab.nfc.matrix.Matrix;


/**
 * 現在の再生時刻に近い時間のデータを抽出するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/01/12
 */
public class ClosenessDataPicker extends AbstractDataPicker {
  /**
   * 新しく生成された<code>ClosenessDataPicker</code>オブジェクトを初期化します。
   * @param data データ
   */
  public ClosenessDataPicker(Matrix data) {
    super(data);
  }

  /**
   * 与えられた時間に最も近いデータが存在する時刻に対応するデータ番号を返します。
   * 
   * @param t 時間
   * @return 与えられた時間に最も近いデータが存在する時刻に対応するデータ番号
   */
  public int getDataNumber(double t) {
    final DoubleMatrix error = getData().getRowVector(1).subtractElementWise(t).absElementWise();
    return error.minimumRowWise().getIndices().getIntElement(1);
  }
  
  /**
   * {@inheritDoc}
   */
  public DHParameter getDHParameter(double t) {
    final int column = getDataNumber(t);
    return this.dhParameters[column - 1];
  }

  /**
   * {@inheritDoc}
   */
  public CoordinateParameter getCoordinateParameter(double t) {
    final int column = getDataNumber(t);
    return this.coordinateParameters[column - 1];
  }

}
