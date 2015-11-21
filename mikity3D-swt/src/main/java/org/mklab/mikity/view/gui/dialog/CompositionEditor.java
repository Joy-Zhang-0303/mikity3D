/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.Messages;
import org.mklab.mikity.view.gui.SceneGraphTree;


/**
 * 三角形の面による合成オブジェクトの編集するエディタを表すクラスです。
 * 
 */
public class CompositionEditor extends AbstractPrimitiveEditor {
  /**
   * 新しく生成された<code>CompositionEditor</code>オブジェクトを初期化します。
   * @param parent 親のシェル
   * @param primitive プリミティブ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public CompositionEditor(Composite parent, ObjectModel primitive, SceneGraphTree tree, JoglModeler modeler) {
    super(parent, primitive, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  public void createParameterBoxes(@SuppressWarnings("unused") Group parameterGroup) {
    this.primitiveType.setText(Messages.getString("CompositionEditor.0")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  public void updateModelParameters() {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  @Override
  boolean containsOnlyNumbers() {
    if (super.containsOnlyNumbers() == false) {
      return false;
    }

    return true;
  }
}