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
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * 円柱の編集するエディタを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/02/09
 */
public class CylinderEditor extends AbstractPrimitiveEditor {
  /** 半径。 */
  private ParameterInputBox radius;
  /** 高さ。 */
  private ParameterInputBox height;
  /** 奥行き。 */
  private ParameterInputBox depth;

  /**
   * 新しく生成された<code>CylinderEditor</code>オブジェクトを初期化します。
   * @param parent 親のシェル
   * @param primitive プリミティブ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public CylinderEditor(Composite parent, PrimitiveModel primitive, SceneGraphTree tree, JoglModeler modeler) {
    super(parent, primitive, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createParameterBoxes(Group parameterGroup) {
    this.primitiveType.setText(Messages.getString("EditPrimitiveDialog.29")); //$NON-NLS-1$
    
    final CylinderModel cylinder = (CylinderModel)this.primitive;
    
    this.radius = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.35"), "" + cylinder.getRadius()); //$NON-NLS-1$//$NON-NLS-2$

    final Label radiusUnit = new Label(parameterGroup, SWT.NONE);
    radiusUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(radiusUnit, 1);

    this.height = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.36"), "" + cylinder.getHeight()); //$NON-NLS-1$//$NON-NLS-2$

    final Label heightUnit = new Label(parameterGroup, SWT.NONE);
    heightUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(heightUnit, 1);

    this.depth = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.37"), "" + cylinder.getDivision()); //$NON-NLS-1$//$NON-NLS-2$
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