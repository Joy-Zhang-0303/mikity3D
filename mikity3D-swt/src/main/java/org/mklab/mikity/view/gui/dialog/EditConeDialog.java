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
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
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
    this.parameter1 = new ParameterInputBox(parameterGroup, SWT.NONE, "", ""); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel1 = new Label(parameterGroup, SWT.NONE);
    this.unitLabel1.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.unitLabel1, 1);

    this.parameter2 = new ParameterInputBox(parameterGroup, SWT.NONE, "", ""); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel2 = new Label(parameterGroup, SWT.NONE);
    this.unitLabel2.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.unitLabel2, 1);

    this.parameter3 = new ParameterInputBox(parameterGroup, SWT.NONE, "", ""); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel3 = new Label(parameterGroup, SWT.NONE);
    this.unitLabel3.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.unitLabel3, 1);
    
    final Label label5 = new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5, 3);
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
   * ボックスにパラメータを設定します。
   */
  @Override
  void setParametersInBoxes() {
    final ConeModel cone = (ConeModel)this.primitive;
    this.parameter1.setStringValue("" + cone.getRadisu()); //$NON-NLS-1$
    this.parameter2.setStringValue("" + cone.getHeight()); //$NON-NLS-1$
    this.parameter3.setStringValue("" + cone.getDivision()); //$NON-NLS-1$
    setConeLabel();
    this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.31")); //$NON-NLS-1$
  }

  /**
   * primitiveがConeのとき
   */
  public void setConeLabel() {
    this.parameter1.setName(Messages.getString("EditPrimitiveDialog.40")); //$NON-NLS-1$
    this.parameter2.setName(Messages.getString("EditPrimitiveDialog.41")); //$NON-NLS-1$
    this.parameter3.setName(Messages.getString("EditPrimitiveDialog.42")); //$NON-NLS-1$

    int division = 0;
    if (this.parameter3.getIntValue() < 3) {
      division = 3;
    } else {
      division = this.parameter3.getIntValue();
    }
    this.parameter3.setStringValue("" + division); //$NON-NLS-1$

    this.parameter3.setVisible(true);
    this.unitLabel2.setVisible(true);
    this.unitLabel3.setVisible(false);
  }
}