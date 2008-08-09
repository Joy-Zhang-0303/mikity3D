/*
 * Created on 2004/10/10
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 */
package org.mklab.mikity.action.file;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.gui.MainWindow;
import org.mklab.mikity.util.MsgUtil;


/**
 * 終了処理を行うアクションです。
 * 
 * @author Yusuke Tsutsui
 * @version $Revision: 1.3 $.
 */
public class FileExitAction extends Action {

  private MainWindow window;
  
  /**
   * コンストラクター
   * @param window 
   * 
   */
  public FileExitAction(final MainWindow window) {
    this.window = window;
    setText("終了(&Q)");
    setToolTipText("アプリケーションを終了します。");
  }
  
  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  public void run() {
    if(window.isDirty()){
      int ans = MsgUtil.showYesNoCancelMsg(window.getShell(),"ファイルが変更されています。保存しますか？");
      switch (ans) {
        case SWT.YES:
          FileDialog dialog = new FileDialog(window.getShell());
          String path = dialog.getFileName();
          window.setFile(path);
          window.save();          
          break;
        case SWT.NO:
          break;
        case SWT.CANCEL:
          return;
        default:
          throw new IllegalArgumentException();
      }
    }
    window.close();
  }
}