/*
 * Created on 2005/01/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;

/**
 * グループを移動可能にするためのインターフェイス
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/01/14
 */
public interface IMovableGroup {

  /**
   * DHパラメーターを設定するとそのグループを動かすメソッド。
   * 
   * @param param
   *        グループのDHパラメーター
   */
  public void setDHParameter(DHParameter param);

  /**
   * @param link
   */
  public void setLinkParameter(final LinkParameter link);

}
