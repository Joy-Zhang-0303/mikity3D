package org.mklab.mikity.view.gui.action.display;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.view.gui.ModelingWindow;
import org.mklab.mikity.view.gui.action.Messages;


/**
 * モデルへの操作をリセットし初期状態へ戻すアクションを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/07/18
 */
public class InitialStateAction extends Action {

  private ModelingWindow window;

  /**
   * 新しく生成された<code>ResetOperationAction</code>オブジェクトを初期化します。
   * @param window モデリングウィンドウ
   */
  public InitialStateAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("InitialStateAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("InitialStateAction.1")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    this.window.setInitialState();
  }
}