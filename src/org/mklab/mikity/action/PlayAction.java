/*
 * Created on 2005/02/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action;

import org.eclipse.jface.action.Action;


/**
 * シミュレーションの再生を行うクラス
 * 
 * @author miki
 * @version $Revision: 1.3 $.2005/02/14
 */
public class PlayAction extends Action {

  /**
   * コンストラクター
   */
  public PlayAction() {
    setText("再生");
    setToolTipText("シミュレーションを再生します。");
  }

  /**
   * シミュレーションの再生を行うクラス
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    // TODO Auto-generated method stub
    System.out.println("シミュレーションを再生(`・ω・´) ");
  }
}
