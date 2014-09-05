/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.widgets.Display;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.view.gui.AnimationWindow;

/**
 * アニメーションツールのメインクラスです。
 * 
 * @author Miki Koga(miki@mk.ces.kyutech.ac.jp)
 * @version $Revision: 1.2 $.2005/02/09
 */
public class Mikity3dAnimation {

  /**
   * メインメソッドです。
   * @param args コマンドライン引数
   * @throws IOException ファイルを読み込めない場合
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合 
   */
  public static void main(String[] args) throws IOException, Mikity3dSerializeDeserializeException {
    final String modelFileName = "../mikity3D-sample/src/main/resources/pendulum/pendulum/pendulum.xml";  //$NON-NLS-1$
    //final AnimationWindow main = new AnimationWindow(null, new File(modelFileName));
    final AnimationWindow main = new AnimationWindow(null);
    main.setRoot(new Mikity3dFactory().loadFile(new File(modelFileName)));
    main.setBlockOnOpen(true);
    main.open();
    Display.getCurrent().dispose();
  }
}
