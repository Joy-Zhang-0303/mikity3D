/*
 * Created on 2003/11/14
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 */
package org.mklab.mikity.action.file;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.gui.MainWindow;
import org.mklab.mikity.util.MsgUtil;


/**
 * モデリングデータを別名保存するクラスです。
 * 
 * @author Yusuke Tsutsui
 * 
 */
public class FileSaveAsAction extends Action {

  private MainWindow window;

  /**
   * コンストラクター
   * 
   * @param window
   * 
   */
  public FileSaveAsAction(final MainWindow window) {
    this.window = window;
    setText("別名保存(&A)");
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    FileDialog dialog = new FileDialog(this.window.getShell(), SWT.SAVE);
    dialog.setText("ファイルを保存");
    dialog.setFilterExtensions(new String[] {"*.xml", "*.*"});  //$NON-NLS-1$//$NON-NLS-2$

    String filePath = dialog.open();
    if (filePath == null) {
      // キャンセル
      return;
    }
    File file = new File(filePath);
    if (file.exists()) {
      int yesno = MsgUtil.showYesNoMsg(this.window.getShell(), "ファイルが存在します。上書きしますか？");
      if (yesno != SWT.YES) {
        return;
      }
    }
    this.window.setFile(filePath);
    System.out.println("filepath:" + filePath); //$NON-NLS-1$
    this.window.save();
  }
}