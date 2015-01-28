/*
 * Created on 2015/01/27
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.search;


/**
 * グループ名検索のためのクラスです。
 * @author soda
 * @version $Revision$, 2015/01/27
 */
@SuppressWarnings("serial")
public class GroupNameSearchOfModel extends SearchOfModel {
  
  private String groupName;

  /**
   * グループ名を返します。
   * @return groupName
   */
  public String getGroupName() {
    return this.groupName;
  }
  
  /**
   * グループ名を設定します。
   * @param name グループ名
   */
  public void setGroupName(String name) {
    this.groupName = name;
  }
}
