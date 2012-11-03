/*
 * $Id: FileImportAction.java,v 1.2 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.action.file;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.gui.ModelingWindow;


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
    super();
    this.window = window;
    setText(Messages.getString("FileImportAction.0")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    FileDialog dialog = new FileDialog(this.window.getShell());
    // ファイルを選択させる
    String fileName = dialog.open();
    if (fileName == null) {
      return;
    }
    this.window.setFile(fileName);
    try {
      this.window.importFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }
}
