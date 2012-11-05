/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.nfc.matrix.Matrix;


/**
 * 与えられた時間のデータが無い際に、 前後のデータの比を用いてデータを補間して返すクラスです。 
 * 
 * @author miki
 * @version $Revision: 1.3 $.2005/01/17 再生する際に必要なデータを補間して使用する
 */
public class InterpolateDataPicker extends AbstractDataPicker {

  /**
   * コンストラクター　行列を読み込む
   * 
   * @param data data
   */
  public InterpolateDataPicker(Matrix data) {
    super(data);
  }

  /**
   * {@inheritDoc}
   */
  public DHParameter getDHParameter(double t) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public CoordinateParameter getCoordinateParameter(double t) {
    return null;
  }
}
