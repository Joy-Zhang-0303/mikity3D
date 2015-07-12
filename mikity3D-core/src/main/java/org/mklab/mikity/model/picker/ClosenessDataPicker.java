/*
 * Created on 2005/01/12
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.picker;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.nfc.matrix.DoubleMatrix;
import org.mklab.nfc.matrix.IntMatrix;
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
  private int getDataNumber(double t) {
    final DoubleMatrix times = getData().getRowVector(1);
    final DoubleMatrix timeDifferences = times.subtractElementWise(t).absElementWise();
    final IntMatrix rowColumn = timeDifferences.minimumRowWise().getIndices();
    return rowColumn.getIntElement(1);
  }
  
//  /**
//   * {@inheritDoc}
//   */
//  public DHParameter getDHParameter(double t) {
//    final int number = getDataNumber(t);
//    return this.dhParameters[number - 1];
//  }

  /**
   * {@inheritDoc}
   */
  public CoordinateParameter getCoordinateParameter(double t) {
    final int number = getDataNumber(t);
    return this.coordinateParameters[number - 1];
  }

}
