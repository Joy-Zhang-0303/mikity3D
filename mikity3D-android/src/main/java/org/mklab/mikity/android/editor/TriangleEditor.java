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

import java.util.Arrays;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBox;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.SceneGraphTreeFragment;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.TriangleModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;

import android.widget.TableLayout;

/**
 * 三角形を編集するためのエディタを表すラグメントです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/26
 */
public class TriangleEditor extends AbstractObjectEditor {
  /** 頂点１のX座標。 */
  private ParameterInputBox vertex1X;
  /** 頂点1のY座標。 */
  private ParameterInputBox vertex1Y;
  /** 頂点1のZ座標。 */
  private ParameterInputBox vertex1Z;
  
  /** 頂点2のX座標。 */
  private ParameterInputBox vertex2X;
  /** 頂点2のY座標。 */
  private ParameterInputBox vertex2Y;
  /** 頂点2のZ座標。 */
  private ParameterInputBox vertex2Z;
  
  /** 頂点3のX座標。 */
  private ParameterInputBox vertex3X;
  /** 頂点3のY座標。 */
  private ParameterInputBox vertex3Y;
  /** 頂点3のZ座標。 */
  private ParameterInputBox vertex3Z;

  /**
   * 新しく生成された<code>TriangleEditorFragment</code>オブジェクトを初期化します。
   * @param object オブジェクト
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public TriangleEditor(ObjectModel object, SceneGraphTreeFragment tree, OpenglesModeler modeler) {
    super(object, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  public void createParameterBoxes(final TableLayout parameters) {
    this.objectType.setText(getString(R.string.objectType) + ": " + getString(R.string.triangle)); //$NON-NLS-1$
    
    final TriangleModel polygon = (TriangleModel)this.object;
    
    final VertexModel vertex1 = polygon.getVertex(0);

    this.vertex1X = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.vertex1X);
    this.vertex1X.setName(R.string.vertex_1_x);
    this.vertex1X.setValue(String.valueOf(vertex1.getX()));
    this.vertex1X.setUnit("[m]"); //$NON-NLS-1$

    this.vertex1Y = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.vertex1Y);
    this.vertex1Y.setName(R.string.vertex_1_y);
    this.vertex1Y.setValue(String.valueOf(vertex1.getY()));
    this.vertex1Y.setUnit("[m]"); //$NON-NLS-1$

    this.vertex1Z = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.vertex1Z);
    this.vertex1Z.setName(R.string.vertex_1_z);
    this.vertex1Z.setValue(String.valueOf(vertex1.getZ()));
    this.vertex1Z.setUnit("[m]"); //$NON-NLS-1$
    
    final VertexModel vertex2 = polygon.getVertex(1);

    this.vertex2X = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.vertex2X);
    this.vertex2X.setName(R.string.vertex_2_x);
    this.vertex2X.setValue(String.valueOf(vertex2.getX()));
    this.vertex2X.setUnit("[m]"); //$NON-NLS-1$

    this.vertex2Y = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.vertex2Y);
    this.vertex2Y.setName(R.string.vertex_2_y);
    this.vertex2Y.setValue(String.valueOf(vertex2.getY()));
    this.vertex2Y.setUnit("[m]"); //$NON-NLS-1$

    this.vertex2Z = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.vertex2Z);
    this.vertex2Z.setName(R.string.vertex_2_z);
    this.vertex2Z.setValue(String.valueOf(vertex2.getZ()));
    this.vertex2Z.setUnit("[m]"); //$NON-NLS-1$
    
    final VertexModel vertex3 = polygon.getVertex(2);

    this.vertex3X = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.vertex3X);
    this.vertex3X.setName(R.string.vertex_3_x);
    this.vertex3X.setValue(String.valueOf(vertex3.getX()));
    this.vertex3X.setUnit("[m]"); //$NON-NLS-1$

    this.vertex3Y = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.vertex3Y);
    this.vertex3Y.setName(R.string.vertex_3_y);
    this.vertex3Y.setValue(String.valueOf(vertex3.getY()));
    this.vertex3Y.setUnit("[m]"); //$NON-NLS-1$

    this.vertex3Z = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.vertex3Z);
    this.vertex3Z.setName(R.string.vertex_3_z);
    this.vertex3Z.setValue(String.valueOf(vertex3.getZ()));
    this.vertex3Z.setUnit("[m]"); //$NON-NLS-1$
  }

  /**
   * パラメータを更新します。
   */
  public void updateModelParameters() {
    final TriangleModel polygon = (TriangleModel)this.object;
    
    final VertexModel[] vertices = new VertexModel[3];

    vertices[0] = new VertexModel(this.vertex1X.getFloatValue(), this.vertex1Y.getFloatValue(), this.vertex1Z.getFloatValue());
    vertices[1] = new VertexModel(this.vertex2X.getFloatValue(), this.vertex2Y.getFloatValue(), this.vertex2Z.getFloatValue());
    vertices[2] = new VertexModel(this.vertex3X.getFloatValue(), this.vertex3Y.getFloatValue(), this.vertex3Z.getFloatValue());

    polygon.setVertices(Arrays.asList(vertices[0], vertices[1], vertices[2]));
  }
  
  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  public boolean containOnlyNumbers() {
    if (super.containsOnlyNumbers() == false) {
      return false;
    }

    if (this.vertex1X.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex1Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex1Z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex2X.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex2Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex2Z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex3X.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex3Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex3Z.containsOnlyNumbers() == false) {
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