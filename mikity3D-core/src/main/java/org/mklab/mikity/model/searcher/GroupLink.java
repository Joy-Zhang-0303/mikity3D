/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;


public class GroupLink extends GroupManager {

  private int columnNumber;
  private String targetName;
  private GroupManager parent;
  
  public GroupLink(int column, String target, GroupManager parentGroupName) {
    this.columnNumber = column;
    this.targetName = target;
    this.parent = parentGroupName;
  }
  
  public int getColumn() {
    return this.columnNumber;
  }
  
  public String getTarget() {
    return this.targetName;
  }
  
}
