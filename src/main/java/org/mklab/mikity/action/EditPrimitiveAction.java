/*
 * Created on 2005/02/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action;

import org.eclipse.jface.action.Action;


/**
 * プリミティブの編集を行うクラスです。
 * 
 * @author miki
 * @version $Revision: 1.3 $.2005/02/14
 */
public class EditPrimitiveAction extends Action {
  /**
   * コンストラクター
   */
  public EditPrimitiveAction() {
    setText(Messages.getString("EditPrimitiveAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("EditPrimitiveAction.1")); //$NON-NLS-1$
  }

  /**
   * プリミティブを編集します。
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    System.out.println(Messages.getString("EditPrimitiveAction.2")); //$NON-NLS-1$
  }

}
