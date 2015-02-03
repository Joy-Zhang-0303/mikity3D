/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Mikity3Dモデルのグループ名を管理するためのクラスです。
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class GroupName extends GroupManager {
  
  private List<GroupManager> items = new ArrayList<>();
  private String groupName;
  
  /**
   * 新しく生成された<code>GroupName</code>オブジェクトを初期化します。
   * @param name グループ名
   * @param parent 親
   */
  public GroupName(String name, GroupManager parent) {
  	super(parent);
    this.groupName = name;
  }
  
  /**
   * @see org.mklab.mikity.model.searcher.GroupManager#addItems(org.mklab.mikity.model.searcher.GroupManager)
   */
  @Override
  public void addItems(GroupManager item) {
    this.items.add(item);
  }
  
  /**
   * @see org.mklab.mikity.model.searcher.GroupManager#removeNullElements()
   */
  @Override
  public void removeNullElements() {
    this.items.removeAll(Collections.singleton(null));
  }
  
  /**
   * @see org.mklab.mikity.model.searcher.GroupManager#trimToSizeArray()
   */
  @Override
  public void trimToSizeArray() {
    ((ArrayList<GroupManager>)this.items).trimToSize();
  }
  
  /**
   * @see org.mklab.mikity.model.searcher.GroupManager#getItems()
   */
  @Override
  public List<GroupManager> getItems() {
    return this.items;
  }
  
  /**
   * グループ名を返します。
   * @return groupName グループ名
   */
  public String getGroupName() {
    return this.groupName;
  }
}
