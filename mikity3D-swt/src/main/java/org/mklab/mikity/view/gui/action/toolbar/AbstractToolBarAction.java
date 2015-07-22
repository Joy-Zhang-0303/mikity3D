/*
 * Created on 2015/07/22
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.action.toolbar;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ModelingWindow;


/**
 * ツールバーのアクションを表す抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/07/22
 */
public abstract class AbstractToolBarAction extends Action {
  /**　モデリングウィンドウ。   */
  ModelingWindow window;
  
  /** モデラー。 */
  JoglModeler modeler;
  
  /**
   * 新しく生成された<code>AbstractToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   * @param name 名前
   */
  public AbstractToolBarAction(ModelingWindow window, String name) {
    this.window = window;
    setText(name);
  }
  
  /**
   * モデラーを設定します。
   * @param modeler モデラー
   */
  public void setModeler(JoglModeler modeler) {
    this.modeler = modeler;
  }

  /**
   * 追加したオブジェクトの情報をキャンバスとツリーに追加します。
   */
  protected void update() {
    this.modeler.fillTree();
    this.modeler.createViewer();
  }
}
