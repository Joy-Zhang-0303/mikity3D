/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;

import org.eclipse.swt.widgets.Display;
import org.mklab.mikity.gui.AnimationWindow;

/**
 * アニメーションツールのメインクラスです。
 * 
 * @author Miki Koga(miki@mk.ces.kyutech.ac.jp)
 * @version $Revision: 1.2 $.2005/02/09
 */
public class MikityAnimation {

  /**
   * メインメソッドです。
   * @param args コマンドライン引数
   */
  public static void main(String[] args) {
    final String modelFileName = "";  //$NON-NLS-1$
    final AnimationWindow main = new AnimationWindow(modelFileName);
    main.setBlockOnOpen(true);
    main.open();
    Display.getCurrent().dispose();
  }
}
