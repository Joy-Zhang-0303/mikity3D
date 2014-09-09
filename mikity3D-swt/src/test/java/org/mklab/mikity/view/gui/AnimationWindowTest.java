/*
 * Created on 2014/09/05
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.view.gui.AnimationWindow;



/**
 * @author soda
 * @version $Revision$, 2014/09/05
 * AnimationWindowクラスのためのテストクラスです。
 */
public class AnimationWindowTest {

  AnimationWindow am;
  /**
   * 初期化メソッドです。
   */
  @Before
  public void init() {
    this.am = new AnimationWindow(null);
  }
  /**
   * ルートの値が正しいか否かを判断するためのテストです。
   */
  @Test
  public void testGetRoot() {
    String modelFileName = "../mikity3D-sample/src/main/resources/pendulum/pendulum/pendulum.xml"; //$NON-NLS-1$
    Mikity3d root;
    try {
      root = new Mikity3dFactory().loadFile(new File(modelFileName));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
    this.am.setRoot(root);
    assertThat(this.am.getRoot(), is(root));
  }
  
  /**
   * ファイル読み込みのテストメソッドです。
   *    */
  @Test
  public void testLoadFile() {
    //Mikity3dFactory m3f = new Mikity3dFactory();
   // m3f.loadFile(null);
  }
}

//@throws IOException ファイルを読み込めない場合
//@throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合 