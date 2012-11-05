/*
 * Created on 2005/01/12
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.nfc.matrix.Matrix;


/**
 * 現在の再生時刻に近い時間のデータを抽出するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/01/12
 */
public class ClosenessDataPicker extends AbstractDataPicker {

  /**
   * コンストラクター
   * 
   * @param data data
   */
  public ClosenessDataPicker(Matrix data) {
    super(data);
  }

  /**
   * {@inheritDoc}
   */
  public DHParameter getDHParameter(double t) {
    int column = getColumn(t);
    return this.dhParameters[column - 1];
  }

  /**
   * {@inheritDoc}
   */
  public CoordinateParameter getCoordinateParameter(double t) {
    int column = getColumn(t);
    return this.coordinateParameters[column - 1];
  }

}
