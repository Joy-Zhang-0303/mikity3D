/*
 * Created on 2005/02/10
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action.file;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.mklab.mikity.gui.ModelingWindow;
import org.mklab.mikity.xml.JamastConfig;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.JamastModel;


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
    super();
    this.window = window;
    setText(Messages.getString("FileNewAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("FileNewAction.1")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    // ファイル名を決定
    FileDialog dialog = new FileDialog(this.window.getShell());
    dialog.setText(Messages.getString("FileNewAction.2")); //$NON-NLS-1$
    String fileName = dialog.open();
    if (fileName == null) {
      return;
    }
    File file = new File(fileName);
    try {
      // 新しいファイルを作成、うまく作成できれば
      if (file.createNewFile()) {
        try {
          this.window.setFile(fileName);
          this.window.load();
        } catch (JAXBException e) {
          throw new RuntimeException(e);
        }
      } else {
        // 新規作成したいが、もともとその名前のファイルが存在するとき
        MessageBox msg = new MessageBox(this.window.getShell(), SWT.YES | SWT.NO);
        msg.setText(Messages.getString("FileNewAction.3")); //$NON-NLS-1$
        msg.setMessage(Messages.getString("FileNewAction.4")); //$NON-NLS-1$
        int ret = msg.open();
        if (ret == SWT.YES) {
          // createNewModelFile(file);
          // Jamast root = createEmptyModel();
          try {
            this.window.setFile(fileName);
            this.window.load();
          } catch (JAXBException e) {
            throw new RuntimeException(e);
          }
        } else if (ret == SWT.NO) {
          return;
        }
        return;
      }
    } catch (IOException e1) {
      throw new RuntimeException(e1);
    }
  }

  /**
   * @return root
   */
  public static Jamast createEmptyModel() {
    Jamast root = new Jamast();
    JamastModel tmpModel;
    root.addConfig(new JamastConfig());
    root.addModel(tmpModel = new JamastModel());
    org.mklab.mikity.xml.model.Group rootGroup = new org.mklab.mikity.xml.model.Group();
    rootGroup.setName(Messages.getString("FileNewAction.5")); //$NON-NLS-1$
    tmpModel.addGroup(rootGroup);
    return root;
  }
}