package org.mklab.mikity.swt.gui.action.display;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.swt.gui.JoglModeler;
import org.mklab.mikity.swt.gui.action.Messages;


/**
 * モデルへの操作をリセットし初期状態へ戻すアクションを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/07/18
 */
public class ResetToInitialStateAction extends Action {
  /** モデラー。 */
  JoglModeler modeler;

  /**
   * 新しく生成された<code>ResetOperationAction</code>オブジェクトを初期化します。
   */
  public ResetToInitialStateAction() {
    setText(Messages.getString("ResetToInitialStateAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("ResetToInitialStateAction.1")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    this.modeler.resetToInitialState();
  }
  
  /**
   * モデラーを設定します。
   * @param modeler モデラー
   */
  public void setModeler(JoglModeler modeler) {
    this.modeler = modeler;
  }
}