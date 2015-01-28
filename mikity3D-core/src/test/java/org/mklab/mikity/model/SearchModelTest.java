/*
 * Created on 2015/01/27
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

import org.junit.Before;
import org.junit.Test;
import org.mklab.mikity.model.search.SearchOfModel;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


/**
 * SearchModelのテストクラスです。
 * @author soda
 * @version $Revision$, 2015/01/27
 */
public class SearchModelTest {
  
  private SearchOfModel searchModel;
  
  /**
   * 初期化メソッドです。
   */
  @Before
  public void init() {
    this.searchModel = new SearchOfModel();
  }
  
  /**
   * setParent()とgetParent()のテストメソッドです。
   */
  @Test
  public void testSetGetParent() {
    this.searchModel.setParent(this.searchModel);
    SearchOfModel testModel = this.searchModel.getParent();
    assertThat(this.searchModel, equalTo(testModel));
  }
}
