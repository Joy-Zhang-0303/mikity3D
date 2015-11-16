/*
 * Created on 2005/01/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;


/**
 * グループオブジェクトを表すインターフェイスです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/01/14
 */
public interface GroupObject {

  /**
   * 名前を設定します。
   * 
   * @param name 名前
   */
  void setName(String name);

  /**
   * グループの座標を設定します。
   * 
   * @param coordinate 座標
   */
  void setCoordinate(Coordinate coordinate);

  /**
   * 関連するオブジェクトのグループを返します。
   * 
   * @return 関連するオブジェクトのグループ
   */
  GroupModel getGroup();
}
