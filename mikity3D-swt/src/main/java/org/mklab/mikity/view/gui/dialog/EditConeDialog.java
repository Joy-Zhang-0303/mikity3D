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
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * プリミティブの編集を行うクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/02/09
 */
public class EditConeDialog extends AbstractEditPrimitiveDialog {
  /**
   * コンストラクター
   * 
   * @param parentShell 親のシェル
   * @param primitive プリミティブ
   * @param group グループ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public EditConeDialog(Shell parentShell, PrimitiveModel primitive, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    super(parentShell, primitive, group, tree, modeler);
  }

  /**
   * パラメータのボックスを生成します。
   * 
   * @param parameterGroup パラメータグループ
   */
  @Override
  public void createPrameterBoxes(Group parameterGroup) {
    this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.31")); //$NON-NLS-1$
    
    final ConeModel cone = (ConeModel)this.primitive;
    
    this.parameter1 = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.40"), "" + cone.getRadisu()); //$NON-NLS-1$//$NON-NLS-2$
    //this.parameter1.setName(); //$NON-NLS-1$
    //this.parameter1.setStringValue(); //$NON-NLS-1$
    
    
    this.unitLabel1 = new Label(parameterGroup, SWT.NONE);
    this.unitLabel1.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.unitLabel1, 1);

    this.parameter2 = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.41"), "" + cone.getHeight()); //$NON-NLS-1$//$NON-NLS-2$
    //this.parameter2.setName(); //$NON-NLS-1$
    //this.parameter2.setStringValue(); //$NON-NLS-1$
    
    
    this.unitLabel2 = new Label(parameterGroup, SWT.NONE);
    this.unitLabel2.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.unitLabel2, 1);

    this.parameter3 = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.42"), "" + cone.getDivision()); //$NON-NLS-1$//$NON-NLS-2$
    //this.parameter3.setName(); //$NON-NLS-1$
    //this.parameter3.setStringValue(); //$NON-NLS-1$
    
    
//    this.unitLabel3 = new Label(parameterGroup, SWT.NONE);
//    this.unitLabel3.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
//    setGridLayout(this.unitLabel3, 1);
    
    final Label label5 = new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5, 3);
  }

  /**
   * ボックスにパラメータを設定します。
   */
  @Override
  void setParametersInBoxes() {
//    this.parameter3.setVisible(true);
//    this.unitLabel2.setVisible(true);
//    this.unitLabel3.setVisible(false);
  }


  /**
   * プリミティブのパラメータを更新します。
   */
  @Override
  void updateModelParameters() {
    final ConeModel cone = (ConeModel)this.primitive;
    cone.setRadius(this.parameter1.getFloatValue());
    cone.setHeight(this.parameter2.getFloatValue());
    cone.setDivision(this.parameter3.getIntValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isChanged() {
    if (super.isChanged()) {
      return true;
    }
    
    if (this.parameter1.isChanged()) {
      return true;
    }
    if (this.parameter2.isChanged()) {
      return true;
    }
    if (this.parameter3.isChanged()) {
      return true;
    }
    
    return false;
  }

}