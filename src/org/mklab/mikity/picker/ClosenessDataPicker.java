/*
 * Created on 2005/01/12
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.DHParameter;
import org.mklab.mikity.LinkParameter;
import org.mklab.nfc.Matrix;


/**
 * 現在の再生時刻に近い時間の行列データのデータを取得するためのクラス
 * @author miki
 * @version $Revision: 1.5 $.2005/01/12
 */
public class ClosenessDataPicker extends DataPicker {

  /**
   * コンストラクター
   * @param data
   */
  public ClosenessDataPicker(Matrix data){
    super(data);
  }
  
  /**
   * DHパラメータを取得する。
   * @see org.mklab.mikity.picker.DataPicker#getDHParameter(double)
   */
  public DHParameter getDHParameter(double time) {
    int col = getColumn(time);
    return params[col - 1];
  }
  
  /**
   * リンクパラメータを取得する。
   * @see org.mklab.mikity.picker.DataPicker#getLinkParameter(double)
   */
  public LinkParameter getLinkParameter(double time){
	  int col = getColumn(time);
	  return link[col - 1];
  }

}
