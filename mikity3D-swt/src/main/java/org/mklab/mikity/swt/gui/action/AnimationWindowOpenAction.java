/*
 * Created on 2005/02/18
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.swt.gui.action;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.swt.gui.AnimationWindow;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * アニメーション実行画面を展開するクラスです。
 * 
 * @author Yusuke Tsutsui
 * @version $Revision: 1.5 $.2005/02/18
 */
public class AnimationWindowOpenAction extends Action {
  /** モデリングウィンドウ。 */
  private ModelingWindow modelingWindow;

  /**
   * 新しく生成された<code>AnimationWindowOpenAction</code>オブジェクトを初期化します。
   * @param modelingWindow ウィンドウ
   */
  public AnimationWindowOpenAction(ModelingWindow modelingWindow) {
    setText(Messages.getString("AnimationWindowOpenAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("AnimationWindowOpenAction.1")); //$NON-NLS-1$
    this.modelingWindow = modelingWindow;
  }

  /**
   * アニメーション画面を展開します。
   * 
   * {@inheritDoc}
   */
  @Override
  public void run() {
    if (this.modelingWindow.getRoot() == Mikity3dFactory.getEmptyModel()) {
      return;
    }
    
    final AnimationWindow animationWindow = new AnimationWindow(this.modelingWindow.getShell(), this.modelingWindow.getRoot(), this.modelingWindow.getModelFile());
    this.modelingWindow.setAnimationWindow(animationWindow);
    animationWindow.open();
  }
}
