/*
 * Created on 2003/11/14
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 */
package org.mklab.mikity.action.file;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.gui.ModelingWindow;
import org.mklab.mikity.util.MessagegUtil;


/**
 * モデリングデータを別名保存するクラスです。
 * 
 * @author Yusuke Tsutsui
 * 
 */
public class FileSaveAsAction extends Action {

  private ModelingWindow window;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   * 
   */
  public FileSaveAsAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("FileSaveAsAction.0")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    FileDialog dialog = new FileDialog(this.window.getShell(), SWT.SAVE);
    dialog.setText(Messages.getString("FileSaveAsAction.1")); //$NON-NLS-1$
    dialog.setFilterExtensions(new String[] {"*.xml", "*.*"}); //$NON-NLS-1$//$NON-NLS-2$

    String filePath = dialog.open();
    if (filePath == null) {
      // キャンセル
      return;
    }
    File file = new File(filePath);
    if (file.exists()) {
      int yesno = MessagegUtil.showYesNo(this.window.getShell(), Messages.getString("FileSaveAsAction.2")); //$NON-NLS-1$
      if (yesno != SWT.YES) {
        return;
      }
    }
    this.window.setFile(filePath);
    System.out.println("filepath:" + filePath); //$NON-NLS-1$
    this.window.save();
  }
}