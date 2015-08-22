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
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * 三角錐の編集を行うダイアログを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/02/09
 */
public class EditConeDialog extends AbstractEditPrimitiveDialog {
  private ParameterInputBox radius;
  private ParameterInputBox height;
  private ParameterInputBox division;
  
  private Label radiusUnit;
  private Label heightUnit;

  
  /**
   * コンストラクター
   * 
   * @param parentShell 親のシェル
   * @param primitive プリミティブ
   * @param group グループ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public EditConeDialog(Shell parentShell, ConeModel primitive, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    super(parentShell, primitive, group, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPrameterBoxes(Group parameterGroup) {
    this.primitiveType.setText(Messages.getString("EditPrimitiveDialog.31")); //$NON-NLS-1$
    
    final ConeModel cone = (ConeModel)this.primitive;
    
    this.radius = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.40"), "" + cone.getRadisu()); //$NON-NLS-1$//$NON-NLS-2$
    
    this.radiusUnit = new Label(parameterGroup, SWT.NONE);
    this.radiusUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.radiusUnit, 1);

    this.height = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.41"), "" + cone.getHeight()); //$NON-NLS-1$//$NON-NLS-2$
    
    this.heightUnit = new Label(parameterGroup, SWT.NONE);
    this.heightUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.heightUnit, 1);

    this.division = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.42"), "" + cone.getDivision()); //$NON-NLS-1$//$NON-NLS-2$
    
    final Label label5 = new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5, 3);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void updateModelParameters() {
    final ConeModel cone = (ConeModel)this.primitive;
    cone.setRadius(this.radius.getFloatValue());
    cone.setHeight(this.height.getFloatValue());
    cone.setDivision(this.division.getIntValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isChanged() {
    if (super.isChanged()) {
      return true;
    }
    
    if (this.radius.isChanged()) {
      return true;
    }
    if (this.height.isChanged()) {
      return true;
    }
    if (this.division.isChanged()) {
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
    
    if (this.radius.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.height.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.division.containsOnlyNumbers() == false) {
      return false;
    }

    return true;
  }

}