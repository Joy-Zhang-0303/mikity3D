/*
 * Created on 2005/02/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.swt.gui.action.file;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * モデリングデータを読み込むアクションです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/02/14
 */
public class FileOpenAction extends Action {

  private ModelingWindow window;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   */
  public FileOpenAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("FileOpenAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("FileOpenAction.1")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final FileDialog dialog = new FileDialog(this.window.getShell());
    dialog.setFilterExtensions(new String[] {"*.m3d", "*.stl", "*.*"}); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    
    final String filePath = dialog.open();
    if (filePath == null) {
      return;
    }
    
    try {
      final File file = new File(filePath);
      this.window.setFile(file);
      this.window.loadFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}