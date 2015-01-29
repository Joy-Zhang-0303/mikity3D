/*
 * Created on 2015/01/27
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.search;

import org.junit.Before;
import org.junit.Test;
import org.mklab.mikity.model.search.GroupManager;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


/**
 * SearchModelのテストクラスです。
 * @author soda
 * @version $Revision$, 2015/01/27
 */
public class SearchOfModelTest {
  
  private GroupManager searchModel;
  
  /**
   * 初期化メソッドです。
   */
  @Before
  public void init() {
    this.searchModel = new GroupManager();
  }
  
  /**
   * setParent()とgetParent()のテストメソッドです。
   */
  @Test
  public void testSetGetParent() {
    this.searchModel.setParent(this.searchModel);
    GroupManager testModel = this.searchModel.getParent();
    assertThat(this.searchModel, equalTo(testModel));
  }
}
