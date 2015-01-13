/*
 * Created on 2015/01/09
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import android.test.ActivityInstrumentationTestCase2;


public class NavigationDrawerFragmentTest extends ActivityInstrumentationTestCase2<CanvasActivity> {

  public NavigationDrawerFragmentTest(Class<CanvasActivity> activityClass) {
    super(activityClass);
  }
  
  public NavigationDrawerFragmentTest() {
    super(CanvasActivity.class);
  }

  protected CanvasActivity activity;
  protected NavigationDrawerFragment fragment;
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    this.activity = this.getActivity();
    this.fragment = new NavigationDrawerFragment();
  }
  
  public void testSetButtonEnabled() {
    this.fragment.setButtonEnabled(true);
    assertTrue(this.fragment.selectButton.isEnabled() == true);
  }
  
}
