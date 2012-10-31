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
   * @param parameter グループのDHパラメーター
   */
  void setDHParameter(DHParameter parameter);

  /**
   * リンクのパラメータを設定します。
   * @param parameter リンクパラメータ
   */
   void setLinkParameter(final LinkParameter parameter);
}
