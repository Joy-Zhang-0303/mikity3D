/*
 * Created on 2004/10/10
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 */
package org.mklab.mikity.view.gui.action.file;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.view.gui.MessagegUtil;
import org.mklab.mikity.view.gui.ModelingWindow;


/**
 * モデリングデータを保存するアクションです。
 * 
 * @author Yusuke Tsutsui
 */
public class FileSaveAction extends Action {

  private ModelingWindow window;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   * 
   */
  public FileSaveAction(final ModelingWindow window) {
    setText(Messages.getString("FileSaveAction.0")); //$NON-NLS-1$
    this.window = window;
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    final File file = this.window.getFile();
    
    if (file == null) {
      final FileDialog dialog = new FileDialog(this.window.getShell(), SWT.SAVE);
      dialog.setText(Messages.getString("FileSaveAsAction.1")); //$NON-NLS-1$
      dialog.setFilterExtensions(new String[] {"*.m3d", "*.*"}); //$NON-NLS-1$//$NON-NLS-2$
      String filePath = dialog.open();
      if (filePath == null) {
        return;
      }
      
      if (filePath.endsWith(".m3d") == false) { //$NON-NLS-1$
        filePath = filePath + ".m3d"; //$NON-NLS-1$
      }
      
      final File newFile = new File(filePath);
      if (newFile.exists()) {
        int yesNo = MessagegUtil.showYesNo(this.window.getShell(), Messages.getString("FileSaveAsAction.2")); //$NON-NLS-1$
        if (yesNo != SWT.YES) {
          return;
        }
      }
      
      this.window.setFile(newFile);
    }
    
    try {
      this.window.saveFile();
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
  }
}