/*
 * Created on 2005/02/10
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.action.file;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.mklab.mikity.view.gui.ModelingWindow;


/**
 * モデリングデータを新規作成するアクションです。
 * 
 * @author miki
 * @version $Revision: 1.6 $.2005/02/10
 */
public class FileNewAction extends Action {

  private ModelingWindow window;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   */
  public FileNewAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("FileNewAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("FileNewAction.1")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    final FileDialog dialog = new FileDialog(this.window.getShell());
    dialog.setText(Messages.getString("FileNewAction.2")); //$NON-NLS-1$

    final String fileName = dialog.open();
    if (fileName == null) {
      return;
    }
    
    try {
      final File file = new File(fileName);
      if (file.createNewFile()) {
        this.window.setFile(fileName);
        this.window.loadFile();
      } else {
        // 新規作成したいが、もともとその名前のファイルが存在するとき
        final MessageBox messageBox = new MessageBox(this.window.getShell(), SWT.YES | SWT.NO);
        messageBox.setText(Messages.getString("FileNewAction.3")); //$NON-NLS-1$
        messageBox.setMessage(Messages.getString("FileNewAction.4")); //$NON-NLS-1$
        final int yesNo = messageBox.open();

        if (yesNo == SWT.YES) {
          this.window.setFile(fileName);
          this.window.loadFile();
        } else if (yesNo == SWT.NO) {
          return;
        }
        return;
      }
    } catch (IOException e1) {
      throw new RuntimeException(e1);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }
}