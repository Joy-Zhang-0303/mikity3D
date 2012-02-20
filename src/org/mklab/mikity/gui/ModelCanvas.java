/*
 * Created on 2012/02/19
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui;

import org.mklab.mikity.xml.model.Group;


/**
 * モデルのキャンバスを表すインターフェースです。
 * @author koga
 * @version $Revision$, 2012/02/19
 */
public interface ModelCanvas {
  /**
   * fileからXMLを読み込む
   */
  void load();
  
  /**
   * @param groups いったんトップグループの全てを消してから書き込む
   */
  void setChild(Group[] groups);
}
