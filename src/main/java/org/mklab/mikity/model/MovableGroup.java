/*
 * Created on 2005/01/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

/**
 * 移動可能なグループを表すインターフェイスです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/01/14
 */
public interface MovableGroup {

  /**
   * グループのDHパラメーターを設定します。
   * 
   * @param parameter DHパラメーター
   */
  void setDHParameter(DHParameter parameter);

  /**
   * グループの座標パラメータを設定します。
   * @param parameter 座標パラメータ
   */
   void setCoordinateParameter(CoordinateParameter parameter);
}
