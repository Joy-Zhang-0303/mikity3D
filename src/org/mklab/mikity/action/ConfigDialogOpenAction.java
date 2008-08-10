/*
 * Created on 2005/02/18
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.MainWindow;
import org.mklab.mikity.gui.dialog.ConfigDialog;


/**
 * 各種設定画面を展開するクラス
 * 
 * @author Yusuke Tsutsui
 * @version $Revision: 1.4 $.2005/02/18
 */
public class ConfigDialogOpenAction extends Action {

  private MainWindow window;

  /**
   * コンストラクター
   * 
   * @param window
   */
  public ConfigDialogOpenAction(final MainWindow window) {
    this.window = window;
    setText("設定(&O)");
    setToolTipText("各種設定画面を開きます。");
  }

  /**
   * 各種設定画面を展開する
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    ConfigDialog dialog = new ConfigDialog(this.window.getShell(), MainWindow.getRoot().loadConfig(0));
    dialog.open();
    this.window.setDirty(true);
  }
}