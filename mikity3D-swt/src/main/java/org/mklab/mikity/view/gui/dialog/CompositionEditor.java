/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.mklab.mikity.model.xml.simplexml.model.CompositionModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.Messages;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


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
  public void createParameterBoxes(Group parameterGroup) {
    this.primitiveType.setText(Messages.getString("CompositionEditor.0")); //$NON-NLS-1$
    
    final CompositionModel compostion = (CompositionModel)this.primitive;
    
    final ParameterInputBox sizeValue = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("CompositionEditor.1"), "" + compostion.getSize()); //$NON-NLS-1$ //$NON-NLS-2$
    sizeValue.setEditable(false);
       
    final ParameterInputBox widthValue = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("CompositionEditor.2"), "" + compostion.getWidth());  //$NON-NLS-1$//$NON-NLS-2$
    widthValue.setEditable(false);

    final Label widthUnit = new Label(parameterGroup, SWT.NONE);
    widthUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(widthUnit, 1);
    
    final ParameterInputBox heightValue = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("CompositionEditor.3"), "" + compostion.getHeight()); //$NON-NLS-1$ //$NON-NLS-2$
    heightValue.setEditable(false);

    final Label heightUnit = new Label(parameterGroup, SWT.NONE);
    heightUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(heightUnit, 1);
    
    final ParameterInputBox depthValue = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("CompositionEditor.5"), "" + compostion.getDepth()); //$NON-NLS-1$ //$NON-NLS-2$
    depthValue.setEditable(false);

    final Label depthUnit = new Label(parameterGroup, SWT.NONE);
    depthUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(depthUnit, 1);
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