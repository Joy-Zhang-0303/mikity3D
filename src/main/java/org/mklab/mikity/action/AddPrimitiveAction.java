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

  /** ウィンド */
  private MainWindow window;

  /** ダイアログ */
  AddPrimitiveDialog dialog;

  private CollisionCanceller canceller;

  /**
   * 新しく生成された<code>AddPrimitiveAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   * @param canceller 重複防止
   */
  public AddPrimitiveAction(final MainWindow window, CollisionCanceller canceller) {
    super();
    this.window = window;
    this.canceller = canceller;
    setText(Messages.getString("AddPrimitiveAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("AddPrimitiveAction.1")); //$NON-NLS-1$
  }

  /**
   * プリミティブの追加を実行する
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    System.out.println(Messages.getString("AddPrimitiveAction.2")); //$NON-NLS-1$
    Jamast root = MainWindow.getRoot();
    this.dialog = new AddPrimitiveDialog(this.window.getShell(), root.loadModel(0).loadGroup(0), this.canceller);
  }
}
