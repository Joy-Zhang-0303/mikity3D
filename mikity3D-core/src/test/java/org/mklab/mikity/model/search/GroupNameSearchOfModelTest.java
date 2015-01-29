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
 * GroupNameSearchOfModelのテストクラスです。
 * @author soda
 * @version $Revision$, 2015/01/28
 */
public class GroupNameSearchOfModelTest {
  
  private GroupName groupNameModel;
  
  /**
   * 初期化メソッドです。
   */
  @Before
  public void setUp() {
    this.groupNameModel = new GroupName();
  }
  
  /**
   * グループ名のゲットセットのテストメソッドです。
   */
  @Test
  public void testGetSetGroupName() {
    String groupName = "test"; //$NON-NLS-1$
    this.groupNameModel.setGroupName(groupName);
    String testGroupName = this.groupNameModel.getGroupName();
    assertThat(groupName, equalTo(testGroupName));
  }
}
