/*
 * Created on 2014/09/05
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;

/**
 * {@link AnimationWindow}のテストケースクラスです。
 * 
 * @author soda
 * @version $Revision$, 2014/09/05
 * AnimationWindowクラスのためのテストクラスです。
 */
public class AnimationWindowTest {
  private AnimationWindow am;

  /**
   * 初期化メソッドです。
   */
  @Before
  public void setUp() {
    this.am = new AnimationWindow(null);
  }

  /**
   * {@link AnimationWindow#getRoot()}のテストメソッドです。
   */
  @Test
  public void testGetRoot() {
    final String modelFileName = "src/test/resources/org/mklab/mikity/view/gui/pendulum.m3d"; //$NON-NLS-1$
    final Mikity3d expected;
    try {
      expected = new Mikity3dFactory().loadFile(new File(modelFileName));
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
    
    this.am.setRoot(expected);
    final Mikity3d actual = this.am.getRoot();
    
    assertTrue(actual.equals(expected));
  }
  
  /**
   * {@link AnimationWindow#createRoot(String)}のテストメソッドです。
   */
  @Test
  public void testCreateRoot() {
    final String modelFileName = "src/test/resources/org/mklab/mikity/view/gui/pendulum.m3d"; //$NON-NLS-1$
    final Mikity3d expected;
    try {
      expected = new Mikity3dFactory().loadFile(new File(modelFileName));
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
    
    this.am.createRoot(modelFileName);
    final Mikity3d actual = this.am.getRoot();
    
    assertTrue(actual.equals(expected));
  }
  
  /**
   * {@link AnimationWindow#createRoot(String)}のテストメソッドです。
   */
  @Test(expected=RuntimeException.class)
  public void testExceptionForCreateRoot() {
    this.am.createRoot("filePath"); //$NON-NLS-1$
  }
}
