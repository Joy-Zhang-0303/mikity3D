/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GroupName extends GroupManager {
  
  private List<GroupManager> items = new ArrayList();
  private String groupName;
  private GroupManager parent;
  
  public GroupName(String name, GroupManager parent) {
    this.groupName = name;
    this.parent = parent;
  }
  
  @Override
  public void addItems(GroupManager item) {
    this.items.add(item);
  }
  
  @Override
  public void removeNullElements() {
    this.items.removeAll(Collections.singleton(null));
  }
  
  @Override
  public void trimToSizeArray() {
    ((ArrayList)this.items).trimToSize();
  }
  
  @Override
  public List<GroupManager> get() {
    return this.items;
  }
  
  public String getGroupName() {
    return this.groupName;
  }
}
