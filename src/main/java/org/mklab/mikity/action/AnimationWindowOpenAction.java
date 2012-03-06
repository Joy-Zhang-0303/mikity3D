/*
 * Created on 2005/02/18
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.action;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.MainWindow;
import org.mklab.mikity.gui.SceneGraphTree;
import org.mklab.mikity.gui.SimulationViewer;


/**
 * アニメーション実行画面を展開するクラスです。
 * 
 * @author Yusuke Tsutsui
 * @version $Revision: 1.5 $.2005/02/18
 */
public class AnimationWindowOpenAction extends Action {

  /** ウィンドウ */
  private MainWindow window;

  /**
   * 新しく生成された<code>AnimationWindowOpenAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public AnimationWindowOpenAction(MainWindow window) {
    setText(Messages.getString("AnimationWindowOpenAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("AnimationWindowOpenAction.1")); //$NON-NLS-1$
    this.window = window;
  }

  /**
   * アニメーション画面を展開する
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    SimulationViewer viewer = new SimulationViewer(this.window.getShell(), MainWindow.getRoot());
    SceneGraphTree tree = new SceneGraphTree();
    tree.setAllTransparent(MainWindow.getRoot().loadModel(0).loadGroup(0), false);
    viewer.open();
    this.window.setDirty(true);
  }
}
