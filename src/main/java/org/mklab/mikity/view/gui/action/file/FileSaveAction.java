/*
 * Created on 2004/10/10
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 */
package org.mklab.mikity.view.gui.action.file;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.model.xml.JamastSerializeDeserializeException;
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
    try {
      this.window.saveFile();
      new FileSaveAsAction(this.window).run();
    } catch (JamastSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
  }
}