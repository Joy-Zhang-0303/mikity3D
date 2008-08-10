/*
 * Created on 2005/02/10
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action.file;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.mklab.mikity.gui.MainWindow;
import org.mklab.mikity.xml.Config;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.Model;


/**
 * モデリングデータを新規作成するアクションです。
 * 
 * @author miki
 * @version $Revision: 1.6 $.2005/02/10
 */
public class FileNewAction extends Action {

  private MainWindow window;

  /**
   * コンストラクター
   * 
   * @param window
   */
  public FileNewAction(final MainWindow window) {
    super();
    this.window = window;
    setText("新規作成");
    setToolTipText("新しいモデルを作成します。");
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    // ファイル名を決定
    FileDialog dialog = new FileDialog(this.window.getShell());
    dialog.setText("新規ファイルを作成");
    String fileName = dialog.open();
    if (fileName == null) {
      return;
    }
    File file = new File(fileName);
    try {
      // 新しいファイルを作成、うまく作成できれば
      if (file.createNewFile()) {
        this.window.setFile(fileName);
        this.window.load();
      } else {
        // 新規作成したいが、もともとその名前のファイルが存在するとき
        MessageBox msg = new MessageBox(this.window.getShell(), SWT.YES | SWT.NO);
        msg.setText("上書き確認");
        msg.setMessage("ファイルが存在します。ファイルを上書きしても良いですか？");
        int ret = msg.open();
        if (ret == SWT.YES) {
          // createNewModelFile(file);
          // Jamast root = createEmptyModel();
          this.window.setFile(fileName);
          this.window.load();
        } else if (ret == SWT.NO) {
          return;
        }
        return;
      }
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  /**
   * @return root
   */
  public static Jamast createEmptyModel() {
    Jamast root = new Jamast();
    Model tmpModel;
    root.addConfig(new Config());
    root.addModel(tmpModel = new Model());
    org.mklab.mikity.xml.model.Group rootGroup = new org.mklab.mikity.xml.model.Group();
    rootGroup.setName("Group0");
    tmpModel.addGroup(rootGroup);
    return root;
  }
}