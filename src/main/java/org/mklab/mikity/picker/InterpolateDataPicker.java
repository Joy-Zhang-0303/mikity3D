/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.LinkParameter;
import org.mklab.nfc.matrix.Matrix;


/**
 * @author miki
 * @version $Revision: 1.3 $.2005/01/17 再生する際に必要なデータを補間して使用する
 */
public class InterpolateDataPicker extends DataPicker {

  /**
   * コンストラクター　行列を読み込む
   * 
   * @param data data
   */
  public InterpolateDataPicker(Matrix data) {
    super(data);
  }

  /**
   * 与えられた時間のデータが無い際に、 前後のデータの比を用いてデータを補間 return 補間した値
   * 
   * @see org.mklab.mikity.picker.DataPicker#getDHParameter(double)
   */
  @Override
  public DHParameter getDHParameter(double time) {
    return null;
  }

  /**
   * 与えられた時間のデータが無い際に、 前後のデータの比を用いてデータを補間 return 補間した値
   * 
   * @see org.mklab.mikity.picker.DataPicker#getLinkParameter(double)
   */
  @Override
  public LinkParameter getLinkParameter(double time) {
    return null;
  }
}
