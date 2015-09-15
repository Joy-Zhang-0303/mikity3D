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
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * 直方体の編集するエディタを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/02/09
 */
public class BoxEditor extends AbstractPrimitiveEditor {
  /** 幅。 */
  private ParameterInputBox width;
  /** 高さ。 */
  private ParameterInputBox height;
  /** 奥行き。 */
  private ParameterInputBox depth;
  
  /** 幅の単位。 */
  private Label widthUnit;
  /** 高さの単位。 */
  private Label heightUnit;
  /** 奥行きの単位。 */
  private Label depthUnit;
  
  /**
   * 新しく生成された<code>EditBoxDialog</code>オブジェクトを初期化します。
   * @param parent 親のシェル
   * @param primitive プリミティブ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public BoxEditor(Composite parent, PrimitiveModel primitive, SceneGraphTree tree, JoglModeler modeler) {
    super(parent, primitive, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createParameterBoxes(Group parameterGroup) {
    this.primitiveType.setText(Messages.getString("EditPrimitiveDialog.28")); //$NON-NLS-1$
    
    final BoxModel box = (BoxModel)this.primitive;
    
    this.width = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.32"), "" + box.getWidth()); //$NON-NLS-1$//$NON-NLS-2$
    
    this.widthUnit = new Label(parameterGroup, SWT.NONE);
    this.widthUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.widthUnit, 1);

    this.height = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.33"), "" + box.getHeight()); //$NON-NLS-1$//$NON-NLS-2$
    
    this.heightUnit = new Label(parameterGroup, SWT.NONE);
    this.heightUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.heightUnit, 1);

    this.depth = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.34"), "" + box.getDepth()); //$NON-NLS-1$//$NON-NLS-2$
    
    this.depthUnit = new Label(parameterGroup, SWT.NONE);
    this.depthUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.depthUnit, 1);   
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateModelParameters() {
    final BoxModel box = (BoxModel)this.primitive;
    box.setWidth(this.width.getFloatValue());
    box.setHeight(this.height.getFloatValue());
    box.setDepth(this.depth.getFloatValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  boolean containsOnlyNumbers() {
    if (super.containsOnlyNumbers() == false) {
      return false;
    }
    
    if (this.width.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.height.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.depth.containsOnlyNumbers() == false) {
      return false;
    }

    return true;
  }
}