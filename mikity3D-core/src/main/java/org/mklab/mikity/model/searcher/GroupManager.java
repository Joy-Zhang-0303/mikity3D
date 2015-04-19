/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;

import java.util.List;


/**
 * Mikity3Dモデルのグループとリンクを管理するためのクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class GroupManager {
  /** 親。 */
  private GroupManager parent;

  /**
   * 新しく生成された<code>GroupManager</code>オブジェクトを初期化します。
   * 
   * @param parent 親GroupManager(GroupName)
   */
  public GroupManager(GroupManager parent) {
    this.parent = parent;
  }

  /**
   * 親を登録します。
   */
  public void registerParent() {
    throw new UnsupportedOperationException();
  }

  /**
   * 要素を追加します。
   * 
   * @param item 要素
   */
  public void addItems(GroupManager item) {
    throw new UnsupportedOperationException(item.toString());
  }

  /**
   * Null要素を削除します。
   */
  public void removeNullElements() {
    throw new UnsupportedOperationException();
  }

  /**
   * 
   */
  public void trimToSizeArray() {
    throw new UnsupportedOperationException();
  }

  /**
   * 要素を返します。
   * 
   * @return 要素
   */
  public List<GroupManager> getItems() {
    throw new UnsupportedOperationException();
  }

  /**
   * 親を返します。
   * 
   * @return parent 親
   */
  public GroupManager getParent() {
    return this.parent;
  }

}