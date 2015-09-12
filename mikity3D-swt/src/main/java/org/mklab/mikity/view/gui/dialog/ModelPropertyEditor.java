/*
 * Created on 2015/09/12
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.SceneGraphTree;


/**
 * モデルのプロパティを編集するエディタを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/09/12
 */
public class ModelPropertyEditor {
  /** */
  JoglModeler modeler;
  
  /** シーングラフツリー。 */
  SceneGraphTree tree;
  
  /** */
  Composite parent;
  
  ModelEditor editor;
  
  Object target;
  
  /**
   * 新しく生成された<code>ModelPropertyEditor</code>オブジェクトを初期化します。
   * @param parent 親
   * @param modeler モデラー
   * @param tree シーングラフツリー
   */
  public ModelPropertyEditor(Composite parent, JoglModeler modeler, SceneGraphTree tree) {
    this.parent = parent;
    this.modeler = modeler;
    this.tree = tree;
    createEditor(parent);
  }
  
  private void createEditor(final Composite parent) {
    parent.setLayout(new GridLayout(1, true));
    this.editor = new GroupEditor(parent, this.modeler.getTargetGroup(), true, null, this.modeler);
  }
  
  /**
   * 編集のターゲットを設定します。
   * 
   * @param target 編集のターゲット
   */
  public void setTarget(Object target) {
    if (this.target == target) {
      return;
    }
    
    this.target = target;
    
    if (this.editor != null) {
      for (final Control child : this.parent.getChildren()) {
        child.dispose();
      }
    }
    
    this.editor = ModelEditorFactory.create(this.target, this.parent, this.modeler.getTargetGroup(), this.tree, this.modeler);
    
    this.parent.layout();
  }
}
