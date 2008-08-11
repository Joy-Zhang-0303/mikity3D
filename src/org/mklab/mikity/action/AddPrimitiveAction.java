/*
 * Created on 2005/02/10
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.MainWindow;
import org.mklab.mikity.gui.collision.CollisionCanceller;
import org.mklab.mikity.gui.dialog.AddPrimitiveDialog;
import org.mklab.mikity.xml.Jamast;


/**
 * プリミティブの追加を行うクラス
 * 
 * @author miki
 * @version $Revision: 1.6 $.2005/02/10
 */
public class AddPrimitiveAction extends Action {

  private MainWindow window;

  AddPrimitiveDialog dialog;

  private CollisionCanceller dc;

  /**
   * コンストラクター
   * 
   * @param window
   * @param dc
   * 
   */
  public AddPrimitiveAction(final MainWindow window, CollisionCanceller dc) {
    super();
    this.window = window;
    this.dc = dc;
    setText("プリミティブの追加");
    setToolTipText("選択したグループにプリミティブを追加します。");
  }

  /**
   * プリミティブの追加を実行する
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    System.out.println("プリミティブを追加(´∩ω∩｀) ");
    Jamast root = MainWindow.getRoot();
    this.dialog = new AddPrimitiveDialog(this.window.getShell(), root.loadModel(0).loadGroup(0), this.dc);
  }
}
