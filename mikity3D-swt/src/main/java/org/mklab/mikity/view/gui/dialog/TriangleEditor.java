/*
 * $Id: EditTrianglePolygonDialog.java,v 1.9 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.TriangleModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;

/**
 * 三角形を編集するエディタを表すクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.9 $. 2008/02/29
 */
public class TriangleEditor extends AbstractObjectEditor {
  /** 頂点1のX座標。 */
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
   * 新しく生成された<code>EditTriangleDialog</code>オブジェクトを初期化します。
   * 
   * @param parent 親のシェル
   * @param primitive ポリゴン
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public TriangleEditor(Composite parent, ObjectModel primitive, SceneGraphTree tree, JoglModeler modeler) {
    super(parent, primitive, tree, modeler);
  }

  /**
   * @param parameterGroup パラメータグループ
   */
  @SuppressWarnings("unused")
  public void createParameterBoxes(final Group parameterGroup) {
    this.objectType.setText(Messages.getString("EditTrianglePolygonDialog.21")); //$NON-NLS-1$
    
    final TriangleModel polygon = (TriangleModel)this.object;
    
    final VertexModel vertex1 = polygon.getVertex(0);
    this.vertex1X = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.3"), "" + vertex1.getX());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex1Y = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.4"), "" + vertex1.getY());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex1Z = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.5"), "" + vertex1.getZ());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    final VertexModel vertex2 = polygon.getVertex(1);
    this.vertex2X = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.6"), "" + vertex2.getX());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex2Y = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.7"), "" + vertex2.getY());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex2Z = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.8"), "" + vertex2.getZ());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    final VertexModel vertex3 = polygon.getVertex(2);    
    this.vertex3X = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.9"), "" + vertex3.getX());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex3Y = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.10"), "" + vertex3.getY());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex3Z = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.11"), "" + vertex3.getZ());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean containOnlyNumbers() {
    if (super.isChanged()) {
      return true;
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
  public void updateModelParameters() {
    final TriangleModel polygon = (TriangleModel)this.object;
    
    final VertexModel[] vertices = new VertexModel[3];

    vertices[0] = new VertexModel(this.vertex1X.getFloatValue(), this.vertex1Y.getFloatValue(), this.vertex1Z.getFloatValue());
    vertices[1] = new VertexModel(this.vertex2X.getFloatValue(), this.vertex2Y.getFloatValue(), this.vertex2Z.getFloatValue());
    vertices[2] = new VertexModel(this.vertex3X.getFloatValue(), this.vertex3Y.getFloatValue(), this.vertex3Z.getFloatValue());

    polygon.setVertices(Arrays.asList(vertices[0], vertices[1], vertices[2]));
  }

  /**
   * {@inheritDoc}
   */
  public void updateEditor() {
    // nothing to do
  }
}
