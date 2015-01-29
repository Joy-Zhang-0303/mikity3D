/*
 * Created on 2015/01/26
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.search;

import java.util.ArrayList;


/**
 * モデル検索のための抽象クラスです。
 * @author soda
 * @version $Revision$, 2015/01/27
 */
@SuppressWarnings("serial")
public class GroupManager extends ArrayList<GroupManager> {
  
  /**親を表すフィールドです 。*/
  private GroupManager parent;

  public GroupManager() {
    super(1);
  }
  
  /**
   * 親を設定します。
   * @param parentSearchModel 親
   */
  public void setParent(GroupManager parentSearchModel) {
    this.parent = parentSearchModel;
  }

  /**
   * 親を返します。
   * @return parent
   */
  public GroupManager getParent() {
    return this.parent;
  }
}
