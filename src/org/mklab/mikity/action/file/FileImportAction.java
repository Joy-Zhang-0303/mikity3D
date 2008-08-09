/*
 * $Id: FileImportAction.java,v 1.2 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.action.file;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.gui.MainWindow;

/**
 * モデリングファイルデータを現在のツリーに追加するアクションです
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/12/04
 */
public class FileImportAction extends Action{
	private MainWindow window;
	public FileImportAction(final MainWindow window){
		 super();
		    this.window = window;
		    setText("インポート(&I)");
	}
	
	/**
	   * @see org.eclipse.jface.action.IAction#run()
	   */
	  public void run() {
	    FileDialog dialog = new FileDialog(window.getShell());
	    //ファイルを選択させる
	    String fileName = dialog.open();
	    if (fileName == null) {
	      return;
	    }
	    window.setFile(fileName);
	    window.importFile();
	  }
}
