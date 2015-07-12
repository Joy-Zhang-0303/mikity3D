/*
 * Created on 2005/01/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

/**
 * グループを表すインターフェイスです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/01/14
 */
public interface ObjectGroup {
  /**
   * 名前を設定します。
   * @param name 名前
   */
  void setName(String name);

  /**
   * グループの座標パラメータを設定します。
   * @param parameter 座標パラメータ
   */
   void setCoordinateParameter(CoordinateParameter parameter);
}
