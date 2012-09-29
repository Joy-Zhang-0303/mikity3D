/*
 * Created on 2005/01/12
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.LinkParameter;
import org.mklab.nfc.matrix.Matrix;


/**
 * 現在の再生時刻に近い時間の行列データのデータを取得するためのクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/01/12
 */
public class ClosenessDataPicker extends DataPicker {

  /**
   * コンストラクター
   * 
   * @param data data
   */
  public ClosenessDataPicker(Matrix data) {
    super(data);
  }

  /**
   * DHパラメータを取得する。
   * 
   * @see org.mklab.mikity.picker.DataPicker#getDHParameter(double)
   */
  @Override
  public DHParameter getDHParameter(double time) {
    int col = getColumn(time);
    return this.params[col - 1];
  }

  /**
   * リンクパラメータを取得する。
   * 
   * @see org.mklab.mikity.picker.DataPicker#getLinkParameter(double)
   */
  @Override
  public LinkParameter getLinkParameter(double time) {
    int col = getColumn(time);
    return this.link[col - 1];
  }

}
