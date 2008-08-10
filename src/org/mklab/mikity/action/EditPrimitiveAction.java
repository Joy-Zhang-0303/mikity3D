/*
 * Created on 2005/02/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action;

import org.eclipse.jface.action.Action;


/**
 * プリミティブの編集を行うクラス
 * 
 * @author miki
 * @version $Revision: 1.3 $.2005/02/14
 */
public class EditPrimitiveAction extends Action {

  /**
   * コンストラクター
   */
  public EditPrimitiveAction() {
    setText("プリミティブの編集");
    setToolTipText("選択したプリミティブのパラメータを編集します。");
  }

  /**
   * プリミティブの編集を行う
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    System.out.println("プリミティブの編集(`・ω・´) ");
  }

}
