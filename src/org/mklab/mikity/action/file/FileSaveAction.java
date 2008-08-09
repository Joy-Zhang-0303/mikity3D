/*
 * Created on 2004/10/10
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 */
package org.mklab.mikity.action.file;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.MainWindow;


/**
 * モデリングデータを保存するアクションです。
 * @author Yusuke Tsutsui
 */
public class FileSaveAction extends Action {

  private MainWindow window;
  
  /**
   * コンストラクター
   * @param window 
   * 
   */
  public FileSaveAction(final MainWindow window) {
    setText("ファイルを保存(&S)");
    this.window = window;
  }

  
  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  public void run() {
    try {
      window.save();
    } catch (IllegalArgumentException e) {
      new FileSaveAsAction(window).run();
    }
  }
}