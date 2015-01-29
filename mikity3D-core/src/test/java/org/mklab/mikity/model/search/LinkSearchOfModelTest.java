/*
 * Created on 2015/01/28
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.search;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * LinkSearchOfModelのためのテストクラスです。
 * @author soda
 * @version $Revision$, 2015/01/28
 */
public class LinkSearchOfModelTest {
  
  private GroupLink linkModel;
  
  /**
   * 初期化メソッドです。
   */
  @Before
  public void setUp() {
    this.linkModel = new GroupLink();
  }
  /**
   * get,setColumnNumber()のテストメソッドです。
   */
  @Test
  public void testGetSetColumnNumber() {
    Integer column = new Integer(3);
    this.linkModel.setColumnNumber(column.intValue());
    Integer testColumn = new Integer(this.linkModel.getColumnNumber());
    assertThat(column, equalTo(testColumn));
  }
  /**
   * get,setTargetName()のテストメソッドです。
   */
  @Test
  public void testGetSetTargetName() {
    String target = "sinsi"; //$NON-NLS-1$
    this.linkModel.setTargetName(target);
    String testTarget = this.linkModel.getTartetName();
    assertThat(target, equalTo(testTarget));
  }

}
