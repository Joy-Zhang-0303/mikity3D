/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;

import org.eclipse.swt.widgets.Display;
import org.mklab.mikity.gui.MainWindow;


/**
 * JAMAST(JAva3D Modeling And Simulation Tool)のメインクラス
 * @author Miki Koga(miki@mk.ces.kyutech.ac.jp)
 * @version $Revision: 1.2 $.2005/02/09
 */
public class Jamast {

  /**
   * @param args
   */
  public static void main(String[] args) {
    MainWindow main = new MainWindow();
    main.setBlockOnOpen(true);
    main.open();
    Display.getCurrent().dispose();
    System.exit(0);
  }
}
