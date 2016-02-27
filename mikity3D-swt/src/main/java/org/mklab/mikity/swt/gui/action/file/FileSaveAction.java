/*
 * Created on 2004/10/10
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 */
package org.mklab.mikity.swt.gui.action.file;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.swt.gui.MessagegUtil;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * モデリングデータを保存するアクションです。
 * 
 * @author Yusuke Tsutsui
 */
public class FileSaveAction extends Action {

  private ModelingWindow window;

  /**
   * 新しく生成された<code>FileSaveAction</code>オブジェクトを初期化します。
   * 
   * @param window ウィンドウ
   */
  public FileSaveAction(final ModelingWindow window) {
    setText(Messages.getString("FileSaveAction.0")); //$NON-NLS-1$
    this.window = window;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final File file = this.window.getModelFile();
    
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
      
      this.window.setModelFile(newFile);
    }
    
    try {
      this.window.saveModelFile();
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
  }
}