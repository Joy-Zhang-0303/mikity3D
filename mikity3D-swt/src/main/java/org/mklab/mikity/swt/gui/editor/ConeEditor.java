/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.swt.gui.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.swt.gui.JoglModeler;
import org.mklab.mikity.swt.gui.ParameterInputBox;
import org.mklab.mikity.swt.gui.SceneGraphTree;
import org.mklab.mikity.swt.gui.UnitLabel;


/**
 * 三角錐を編集するエディタを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/02/09
 */
public class ConeEditor extends AbstractObjectEditor {
  /** 半径。 */
  private ParameterInputBox radius;
  /** 高さ。 */
  private ParameterInputBox height;
  /** 分割数。 */
  private ParameterInputBox division;

  /**
   * 新しく生成された<code>ConeEditor</code>オブジェクトを初期化します。
   * 
   * @param parent 親のシェル
   * @param primitive プリミティブ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public ConeEditor(Composite parent, ObjectModel primitive, SceneGraphTree tree, JoglModeler modeler) {
    super(parent, primitive, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createParameterBoxes(Group parameterGroup) {
    this.objectType.setText(Messages.getString("EditPrimitiveDialog.31")); //$NON-NLS-1$
    
    final ConeModel cone = (ConeModel)this.object;
    
    this.radius = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.40"), "" + cone.getRadisu()); //$NON-NLS-1$//$NON-NLS-2$
    
    final Label radiusUnit = new Label(parameterGroup, SWT.NONE);
    radiusUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(radiusUnit, 1);

    this.height = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.41"), "" + cone.getHeight()); //$NON-NLS-1$//$NON-NLS-2$
    
    final Label heightUnit = new Label(parameterGroup, SWT.NONE);
    heightUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(heightUnit, 1);

    this.division = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.42"), "" + cone.getDivision()); //$NON-NLS-1$//$NON-NLS-2$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateModelParameters() {
    final ConeModel cone = (ConeModel)this.object;
    cone.setRadius(this.radius.getFloatValue());
    cone.setHeight(this.height.getFloatValue());
    cone.setDivision(this.division.getIntValue());
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

  /**
   * {@inheritDoc}
   */
  public void updateEditor() {
    // nothing to do
  }

}