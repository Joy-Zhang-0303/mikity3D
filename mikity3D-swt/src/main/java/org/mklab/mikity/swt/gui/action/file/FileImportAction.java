/*
 * $Id: FileImportAction.java,v 1.2 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.swt.gui.action.file;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.swt.gui.ModelingWindow;

/**
 * モデリングファイルデータを現在のツリーに追加するアクションです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/12/04
 */
public class FileImportAction extends Action {

  private ModelingWindow window;

  /**
   * 新しく生成された<code>FileImportAction</code>オブジェクトを初期化します。
   * 
   * @param window ウィンドウ
   */
  public FileImportAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("FileImportAction.0")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
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
      this.window.setModelFile(file);
      this.window.importModelFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
