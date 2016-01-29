/*
 * $Id: EditQuadPolygonDialog.java,v 1.7 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.swt.gui.editor;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadrangleModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.swt.gui.JoglModeler;
import org.mklab.mikity.swt.gui.ParameterInputBox;
import org.mklab.mikity.swt.gui.SceneGraphTree;
import org.mklab.mikity.swt.gui.UnitLabel;


/**
 * 四角形を編集するエディタを表すクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.7 $. 2007/11/15
 */
public class QuadrangleEditor extends AbstractObjectEditor {
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

  /** 頂点4のX座標。 */
  private ParameterInputBox vertex4X;
  /** 頂点4のY座標。 */
  private ParameterInputBox vertex4Y;
  /** 頂点4のZ座標。 */
  private ParameterInputBox vertex4Z;
  
  /**
   * 新しく生成された<code>QuadrangleEditor</code>オブジェクトを初期化します。
   * 
   * @param parent 親のシェル
   * @param primitive ポリゴン
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public QuadrangleEditor(Composite parent, ObjectModel primitive, SceneGraphTree tree, JoglModeler modeler) {
    super(parent, primitive, tree, modeler);
  }

  /**
   * @param parameterGroup パラメータグループ
   */
  @SuppressWarnings("unused")
  public void createParameterBoxes(final Group parameterGroup) {
    this.objectType.setText(Messages.getString("EditQuadPolygonDialog.23")); //$NON-NLS-1$

    final QuadrangleModel polygon = (QuadrangleModel)this.object;

    final VertexModel vertex1 = polygon.getVertex(0);
    this.vertex1X = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.3"), "" + vertex1.getX()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex1Y = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.4"), "" + vertex1.getY()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex1Z = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.5"), "" + vertex1.getZ()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    final VertexModel vertex2 = polygon.getVertex(1);
    this.vertex2X = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.6"), "" + vertex2.getX()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex2Y = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.7"), "" + vertex2.getY()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex2Z = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.8"), "" + vertex2.getZ()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    final VertexModel vertex3 = polygon.getVertex(2);
    this.vertex3X = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.21"), "" + vertex3.getX()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex3Y = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.9"), "" + vertex3.getY()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex3Z = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.10"), "" + vertex3.getZ()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    final VertexModel vertex4 = polygon.getVertex(3);
    this.vertex4X = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.11"), "" + vertex4.getX()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex4Y = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.12"), "" + vertex4.getY()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex4Z = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditQuadPolygonDialog.13"), "" + vertex4.getZ()); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
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
    if (this.vertex4X.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex4Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex4Z.containsOnlyNumbers() == false) {
      return false;
    }

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void updateModelParameters() {
    final QuadrangleModel polygon = (QuadrangleModel)this.object;

    final VertexModel[] vertices = new VertexModel[4];

    vertices[0] = new VertexModel(this.vertex1X.getFloatValue(), this.vertex1Y.getFloatValue(), this.vertex1Z.getFloatValue());
    vertices[1] = new VertexModel(this.vertex2X.getFloatValue(), this.vertex2Y.getFloatValue(), this.vertex2Z.getFloatValue());
    vertices[2] = new VertexModel(this.vertex3X.getFloatValue(), this.vertex3Y.getFloatValue(), this.vertex3Z.getFloatValue());
    vertices[3] = new VertexModel(this.vertex4X.getFloatValue(), this.vertex4Y.getFloatValue(), this.vertex4Z.getFloatValue());

    polygon.setVertices(Arrays.asList(vertices[0], vertices[1], vertices[2], vertices[3]));
  }

  /**
   * {@inheritDoc}
   */
  public void updateEditor() {
    // nothig to do
  }
}