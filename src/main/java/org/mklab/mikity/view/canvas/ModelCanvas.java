/*
 * Created on 2012/02/19
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.canvas;

import org.mklab.mikity.model.xml.model.Group;


/**
 * モデルのキャンバスを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2012/02/19
 */
public interface ModelCanvas {
  /**
   * XMLファイルを読み込みます。
   */
  void load();
  
  /**
   * 一旦トップグループの全てを消してから書き込みます。
   * @param groups グループ群 
   */
  void setChildren(Group[] groups);
}
