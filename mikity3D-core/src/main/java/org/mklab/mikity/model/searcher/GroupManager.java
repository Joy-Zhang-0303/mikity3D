/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;

import java.util.List;


public class GroupManager {
	
	private GroupManager parent;
	
	public GroupManager(GroupManager parent) {
		this.parent = parent;
	}
  
  public void parentRegister() {
    throw new UnsupportedOperationException();
  }
  
  public void addItems(GroupManager item) {
    throw new UnsupportedOperationException();
  }
  
  public void removeNullElements() {
    throw new UnsupportedOperationException();
  }
  
  public void trimToSizeArray() {
    throw new UnsupportedOperationException();
  }
  
  public List<GroupManager> getItems() {
    throw new UnsupportedOperationException();
  }
  
  public GroupManager getParent() {
  	return this.parent;
  }
  
}