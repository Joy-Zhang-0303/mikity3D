/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;

import java.util.List;


/**
 * Mikity3Dモデルのグループとリンクを管理するためのクラスです。
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class GroupManager {
	
	private GroupManager parent;
	
	/**
	 * 新しく生成された<code>GroupManager</code>オブジェクトを初期化します。
	 * @param parent 親GroupManager(GroupName)
	 */
	public GroupManager(GroupManager parent) {
		this.parent = parent;
	}
  
  /**
   * 親の登録をするためのメソッドです。
   */
  @SuppressWarnings("static-method")
  public void parentRegister() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * 要素を追加するためのメソッドです。
   * @param item 要素
   */
  @SuppressWarnings("static-method")
  public void addItems(GroupManager item) {
    throw new UnsupportedOperationException();
  }
  
  /**
   * Null要素を削除するためのメソッドです。
   */
  @SuppressWarnings("static-method")
  public void removeNullElements() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * 
   */
  @SuppressWarnings("static-method")
  public void trimToSizeArray() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * 要素を取得するメソッドです。
   * @return 要素
   */
  @SuppressWarnings("static-method")
  public List<GroupManager> getItems() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * 親を取得するメソッドです。
   * @return parent 親
   */
  public GroupManager getParent() {
  	return this.parent;
  }
  
}