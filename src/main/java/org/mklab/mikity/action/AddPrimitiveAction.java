/*
 * Created on 2005/02/10
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.ModelingWindow;
import org.mklab.mikity.gui.dialog.AddPrimitiveDialog;
import org.mklab.mikity.xml.Jamast;


/**
 * プリミティブの追加を行うクラスです。
 * 
 * @author miki
 * @version $Revision: 1.6 $.2005/02/10
 */
public class AddPrimitiveAction extends Action {

  /** ウィンド */
  private ModelingWindow window;

  /** ダイアログ */
  private AddPrimitiveDialog dialog;

  /**
   * 新しく生成された<code>AddPrimitiveAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public AddPrimitiveAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("AddPrimitiveAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("AddPrimitiveAction.1")); //$NON-NLS-1$
  }

  /**
   * プリミティブを追加します。
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    final Jamast root = this.window.getRoot();
    this.dialog = new AddPrimitiveDialog(this.window.getShell(), root.loadModel(0).loadGroup(0));
  }
}
