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
package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBox;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.SceneGraphTree;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;

import android.widget.TableLayout;

/**
 * 三角錐を編集するためのエディタを表すラグメントです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/26
 */
public class ConeEditor extends AbstractObjectEditor {
  /** 半径。 */
  private ParameterInputBox radius;
  /** 高さ。 */
  private ParameterInputBox height;
  /** 分割数。 */
  private ParameterInputBox division;

  /**
   * 新しく生成された<code>ConeEditorFragment</code>オブジェクトを初期化します。
   * @param object オブジェクト
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public ConeEditor(ObjectModel object, SceneGraphTree tree, OpenglesModeler modeler) {
    super(object, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  public void createParameterBoxes(final TableLayout parameters) {
    this.objectType.setText(getString(R.string.objectType) + ": " + getString(R.string.cone)); //$NON-NLS-1$
    
    final ConeModel cone = (ConeModel)this.object;
    
    this.radius = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.radius);
    this.radius.setName(R.string.radius);
    this.radius.setValue(String.valueOf(cone.getRadisu()));
    this.radius.setUnit("[m]"); //$NON-NLS-1$

    this.height = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.height);
    this.height.setName(R.string.height);
    this.height.setValue(String.valueOf(cone.getHeight()));
    this.height.setUnit("[m]"); //$NON-NLS-1$

    this.division = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.division);
    this.division.setName(R.string.division);
    this.division.setValue(String.valueOf(cone.getDivision()));
    this.division.setUnit(""); //$NON-NLS-1$
  }

  /**
   * パラメータを更新します。
   */
  public void updateModelParameters() {
    final ConeModel box = (ConeModel)this.object;
    box.setRadius(this.radius.getFloatValue());
    box.setHeight(this.height.getFloatValue());
    box.setDivision(this.division.getIntValue());
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