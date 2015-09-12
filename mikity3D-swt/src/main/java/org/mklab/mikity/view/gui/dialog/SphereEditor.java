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
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * 球を編集するエディタを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/02/09
 */
public class SphereEditor extends AbstractPrimitiveEditor {
  private ParameterInputBox radius;
  private ParameterInputBox division;
  private Label radiusUnit;
  
  /**
   * コンストラクター
   * 
   * @param parent 親のシェル
   * @param primitive プリミティブ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public SphereEditor(Composite parent, PrimitiveModel primitive, SceneGraphTree tree, JoglModeler modeler) {
    super(parent, primitive, tree, modeler);
  }
  
   /**
   * {@inheritDoc}
   */
  public void setCompositeSize(Composite composite) {
     composite.setSize(new org.eclipse.swt.graphics.Point(350, 530));
   }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void createParameterBoxes(Group parameterGroup) {
    this.primitiveType.setText(Messages.getString("EditPrimitiveDialog.30")); //$NON-NLS-1$
    final SphereModel sphere = (SphereModel)this.primitive;
    
    this.radius = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.38"), "" + sphere.getRadius()); //$NON-NLS-1$//$NON-NLS-2$

    this.radiusUnit = new Label(parameterGroup, SWT.NONE);
    this.radiusUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.radiusUnit, 1);

    this.division = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.39"), "" + sphere.getDivision()); //$NON-NLS-1$//$NON-NLS-2$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateModelParameters() {
    final SphereModel sphere = (SphereModel)this.primitive;
    sphere.setRadius(this.radius.getFloatValue());
    sphere.setDivision(this.division.getIntValue());
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
    if (this.division.containsOnlyNumbers() == false) {
      return false;
    }

    return true;
  }

}