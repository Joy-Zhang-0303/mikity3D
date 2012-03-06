/*
 * Created on 2005/02/18
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.MainWindow;


/**
 * @author Yusuke Tsutsui
 * @version $Revision: 1.2 $.2005/02/18
 */
public class ModelEditorOpenAction extends Action {

  /** */
  MainWindow window;

  /**
   * 新しく生成された<code>ModelEditorOpenAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public ModelEditorOpenAction(final MainWindow window) {
    this.window = window;
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
  // Modeler modeler = new Modeler(window.getShell(),window.getRoot());
  // modeler.open();
  // window.setDirty(true);
  }
}
