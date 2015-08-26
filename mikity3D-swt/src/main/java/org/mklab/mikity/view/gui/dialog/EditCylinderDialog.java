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
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * 円柱の編集を行うダイアログを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/02/09
 */
public class EditCylinderDialog extends AbstractEditPrimitiveDialog {
  private ParameterInputBox radius;
  private ParameterInputBox height;
  private ParameterInputBox depth;
  
//  private Label radiusUnit;
//  private Label heightUnit;
  
  /**
   * コンストラクター
   * 
   * @param parentShell 親のシェル
   * @param primitive プリミティブ
   * @param group グループ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public EditCylinderDialog(Shell parentShell, PrimitiveModel primitive, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    super(parentShell, primitive, group, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createParameterBoxes(Group parameterGroup) {
    this.primitiveType.setText(Messages.getString("EditPrimitiveDialog.29")); //$NON-NLS-1$
    
    final CylinderModel cylinder = (CylinderModel)this.primitive;
    
    this.radius = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.35"), "" + cylinder.getRadius()); //$NON-NLS-1$//$NON-NLS-2$

    final Label radiusUnit = new Label(parameterGroup, SWT.NONE);
    radiusUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(radiusUnit, 1);

    this.height = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.36"), "" + cylinder.getHeight()); //$NON-NLS-1$//$NON-NLS-2$

    final Label heightUnit = new Label(parameterGroup, SWT.NONE);
    heightUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(heightUnit, 1);

    this.depth = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.37"), "" + cylinder.getDivision()); //$NON-NLS-1$//$NON-NLS-2$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateModelParameters() {
    final CylinderModel cylinder = (CylinderModel)this.primitive;
    cylinder.setRadius(this.radius.getFloatValue());
    cylinder.setHeight(this.height.getFloatValue());
    cylinder.setDivision(this.depth.getIntValue());
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
    
    if (this.radius.containsOnlyNumbers() == false) {
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