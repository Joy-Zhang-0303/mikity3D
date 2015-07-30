/*
 * Created on 2014/09/05
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;

/**
 * {@link AnimationWindow}のテストケースクラスです。
 * 
 * @author soda
 * @version $Revision$, 2014/09/05
 * AnimationWindowクラスのためのテストクラスです。
 */
public class AnimationWindowTest {
  private AnimationWindow window;

  /**
   * 初期化メソッドです。
   */
  @Before
  public void setUp() {
    this.window = new AnimationWindow(null);
  }

  /**
   * {AnimationWindowgetRoot()}のテストメソッドです。
   */
  @Ignore
  @Test
  public void testGetRoot() {
    final String filePath = "src/test/resources/org/mklab/mikity/view/gui/pendulum.m3d"; //$NON-NLS-1$
    final Mikity3DModel expected;
    try {
      expected = new Mikity3dFactory().loadFile(new File(filePath));
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
    
    this.window.setRoot(expected);
    final Mikity3DModel actual = this.window.getRoot();
    
    assertTrue(actual.equals(expected));
  }
  
  /**
   * {AnimationWindow#createRoot(String)}のテストメソッドです。
   */
  @Ignore
  @Test
  public void testCreateRoot() {
    final String modelFileName = "src/test/resources/org/mklab/mikity/view/gui/pendulum.m3d"; //$NON-NLS-1$
    final Mikity3DModel expected;
    try {
      expected = new Mikity3dFactory().loadFile(new File(modelFileName));
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
    
    this.window.loadModel(modelFileName);
    final Mikity3DModel actual = this.window.getRoot();
    
    assertTrue(actual.equals(expected));
  }
  
  /**
   * {AnimationWindow#createRoot(String)}のテストメソッドです。
   */
  @Ignore
  @Test(expected=RuntimeException.class)
  public void testExceptionForCreateRoot() {
    this.window.loadModel("filePath"); //$NON-NLS-1$
  }
}
