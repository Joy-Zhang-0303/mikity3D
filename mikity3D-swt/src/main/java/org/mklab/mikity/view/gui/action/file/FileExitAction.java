/*
 * Created on 2004/10/10
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 */
package org.mklab.mikity.view.gui.action.file;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.mklab.mikity.view.gui.MessagegUtil;
import org.mklab.mikity.view.gui.ModelingWindow;


/**
 * 終了処理を行うアクションです。
 * 
 * @author Yusuke Tsutsui
 * @version $Revision: 1.3 $.
 */
public class FileExitAction extends Action {

  /** ウィンドウ */
  private ModelingWindow window;

  /**
   * 新しく生成された<code>FileExitAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public FileExitAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("FileExitAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("FileExitAction.1")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    if (this.window.isChanged()) {
      int yesNo = MessagegUtil.showYesNoCancel(this.window.getShell(), Messages.getString("FileExitAction.2")); //$NON-NLS-1$
      switch (yesNo) {
        case SWT.YES:
            new FileSaveAction(this.window).run();
          break;
        case SWT.NO:
          break;
        case SWT.CANCEL:
          return;
        default:
          throw new IllegalArgumentException();
      }
    }
    this.window.close();
  }
}