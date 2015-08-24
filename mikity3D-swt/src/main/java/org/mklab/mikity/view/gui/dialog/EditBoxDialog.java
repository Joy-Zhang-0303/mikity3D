/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * 直方体の編集を行うダイアログを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/02/09
 */
public class EditBoxDialog extends AbstractEditPrimitiveDialog {
  private ParameterInputBox width;
  private ParameterInputBox height;
  private ParameterInputBox depth;
  
  private Label widthUnit;
  private Label heightUnit;
  private Label depthUnit;

  /**
   * 新しく生成された<code>EditBoxDialog</code>オブジェクトを初期化します。
   * @param parentShell 親のシェル
   * @param primitive プリミティブ
   * @param group グループ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public EditBoxDialog(Shell parentShell, BoxModel primitive, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    super(parentShell, primitive, group, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPrameterBoxes(Group parameterGroup) {
    this.primitiveType.setText(Messages.getString("EditPrimitiveDialog.28")); //$NON-NLS-1$
    
    final BoxModel box = (BoxModel)this.primitive;
    
    this.width = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.32"), "" + box.getWidth()); //$NON-NLS-1$//$NON-NLS-2$
    
    this.widthUnit = new Label(parameterGroup, SWT.NONE);
    this.widthUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.widthUnit, 1);

    this.height = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.33"), "" + box.getHeight()); //$NON-NLS-1$//$NON-NLS-2$
    
    this.heightUnit = new Label(parameterGroup, SWT.NONE);
    this.heightUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.heightUnit, 1);

    this.depth = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.34"), "" + box.getDepth()); //$NON-NLS-1$//$NON-NLS-2$
    
    this.depthUnit = new Label(parameterGroup, SWT.NONE);
    this.depthUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.depthUnit, 1);   
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void updateModelParameters() {
    final BoxModel box = (BoxModel)this.primitive;
    box.setWidth(this.width.getFloatValue());
    box.setHeight(this.height.getFloatValue());
    box.setDepth(this.depth.getFloatValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isChanged() {
    if (super.isChanged()) {
      return true;
    }
    
    if (this.width.isChanged()) {
      return true;
    }
    if (this.height.isChanged()) {
      return true;
    }
    if (this.depth.isChanged()) {
      return true;
    }
    
    return false;
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