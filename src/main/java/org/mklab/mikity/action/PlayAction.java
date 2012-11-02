/*
 * Created on 2005/02/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action;

import org.eclipse.jface.action.Action;


/**
 * シミュレーションの再生を行うクラスです。
 * 
 * @author miki
 * @version $Revision: 1.3 $.2005/02/14
 */
public class PlayAction extends Action {

  /**
   * コンストラクター
   */
  public PlayAction() {
    setText(Messages.getString("PlayAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("PlayAction.1")); //$NON-NLS-1$
  }

  /**
   * シミュレーションを再生します。
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    // TODO Auto-generated method stub
    System.out.println(Messages.getString("PlayAction.2")); //$NON-NLS-1$
  }
}
