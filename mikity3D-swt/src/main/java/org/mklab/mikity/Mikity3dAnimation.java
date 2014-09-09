/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;

import org.eclipse.swt.widgets.Display;
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
   */
  public static void main(String[] args) {
    final AnimationWindow main = new AnimationWindow(null);
    main.setBlockOnOpen(true);
    main.open();
    Display.getCurrent().dispose();
  }
}
