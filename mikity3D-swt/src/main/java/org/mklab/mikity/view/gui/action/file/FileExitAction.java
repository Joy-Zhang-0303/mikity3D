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

  private ModelingWindow window;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   * 
   */
  public FileExitAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("FileExitAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("FileExitAction.1")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    if (this.window.isDirty()) {
      int yesNo = MessagegUtil.showYesNoCancel(this.window.getShell(), Messages.getString("FileExitAction.2")); //$NON-NLS-1$
      switch (yesNo) {
        case SWT.YES:
//          try {
            new FileSaveAction(this.window).run();
//            final FileDialog dialog = new FileDialog(this.window.getShell());
//            final String path = dialog.getFileName();
//            this.window.setFile(path);
//            this.window.saveFile();
//          } catch (Mikity3dSerializeDeserializeException e) {
//            throw new RuntimeException(e);
//          }
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