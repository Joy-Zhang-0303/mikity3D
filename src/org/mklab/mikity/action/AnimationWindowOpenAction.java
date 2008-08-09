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
 * アニメーション実行画面を展開するクラス。
 * @author Yusuke Tsutsui
 * @version $Revision: 1.5 $.2005/02/18
 */
public class AnimationWindowOpenAction extends Action {

  private MainWindow window;
  
  /**
   * コンストラクター
   * @param window
   */
  public AnimationWindowOpenAction(MainWindow window) {
    setText("アニメーションウインドウを開く(&A)");
    setToolTipText("現在のモデルのアニメーションを見るためにウインドウを開きます。");
    this.window = window;
  }
  
  /**
   * アニメーション画面を展開する
   * @see org.eclipse.jface.action.IAction#run()
   */
  public void run() {
    SimulationViewer viewer = new SimulationViewer(window.getShell(),MainWindow.getRoot());
    SceneGraphTree tree = new SceneGraphTree();
    tree.setAllTransparent(MainWindow.getRoot().loadModel(0).loadGroup(0), false);
    viewer.open();
    window.setDirty(true);
  }
}
