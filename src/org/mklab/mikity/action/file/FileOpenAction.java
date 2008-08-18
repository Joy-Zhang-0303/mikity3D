/*
 * Created on 2005/02/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action.file;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.gui.MainWindow;


/**
 * モデリングデータを読み込むアクションです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/02/14
 */
public class FileOpenAction extends Action {

  private MainWindow window;

  /**
   * コンストラクター
   * 
   * @param window
   */
  public FileOpenAction(final MainWindow window) {
    super();
    this.window = window;
    setText(Messages.getString("FileOpenAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("FileOpenAction.1")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    FileDialog dialog = new FileDialog(this.window.getShell());
    // ファイルを選択させる
    String fileName = dialog.open();
    if (fileName == null) {
      return;
    }
    this.window.setFile(fileName);
    this.window.load();
  }
}