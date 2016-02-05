package org.mklab.mikity.android.editor;

import java.util.Arrays;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBox;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.SceneGraphTree;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadrangleModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;

import android.widget.TableLayout;

/**
 * 四角形を編集するためのエディタを表すラグメントです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/26
 */
public class QuadrangleEditor extends AbstractObjectEditor {
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
  
  /** 頂点4のX座標。 */
  private ParameterInputBox vertex4X;
  /** 頂点4のY座標。 */
  private ParameterInputBox vertex4Y;
  /** 頂点4のZ座標。 */
  private ParameterInputBox vertex4Z;

  /**
   * 新しく生成された<code>QuadrangleEditorFragment</code>オブジェクトを初期化します。
   * @param object オブジェクト
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public QuadrangleEditor(ObjectModel object, SceneGraphTree tree, OpenglesModeler modeler) {
    super(object, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  public void createParameterBoxes(final TableLayout parameters) {
    this.objectType.setText(getString(R.string.objectType) + ": " + getString(R.string.quadrangle)); //$NON-NLS-1$
    
    final QuadrangleModel polygon = (QuadrangleModel)this.object;
    
    final VertexModel vertex1 = polygon.getVertex(0);

    this.vertex1X = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex1X);
    this.vertex1X.setName(R.string.vertex_1_x);
    this.vertex1X.setValue(String.valueOf(vertex1.getX()));
    this.vertex1X.setUnit("[m]"); //$NON-NLS-1$

    this.vertex1Y = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex1Y);
    this.vertex1Y.setName(R.string.vertex_1_y);
    this.vertex1Y.setValue(String.valueOf(vertex1.getY()));
    this.vertex1Y.setUnit("[m]"); //$NON-NLS-1$

    this.vertex1Z = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex1Z);
    this.vertex1Z.setName(R.string.vertex_1_z);
    this.vertex1Z.setValue(String.valueOf(vertex1.getZ()));
    this.vertex1Z.setUnit("[m]"); //$NON-NLS-1$
    
    final VertexModel vertex2 = polygon.getVertex(1);

    this.vertex2X = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex2X);
    this.vertex2X.setName(R.string.vertex_2_x);
    this.vertex2X.setValue(String.valueOf(vertex2.getX()));
    this.vertex2X.setUnit("[m]"); //$NON-NLS-1$

    this.vertex2Y = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex2Y);
    this.vertex2Y.setName(R.string.vertex_2_y);
    this.vertex2Y.setValue(String.valueOf(vertex2.getY()));
    this.vertex2Y.setUnit("[m]"); //$NON-NLS-1$

    this.vertex2Z = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex2Z);
    this.vertex2Z.setName(R.string.vertex_2_z);
    this.vertex2Z.setValue(String.valueOf(vertex2.getZ()));
    this.vertex2Z.setUnit("[m]"); //$NON-NLS-1$
    
    final VertexModel vertex3 = polygon.getVertex(2);

    this.vertex3X = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex3X);
    this.vertex3X.setName(R.string.vertex_3_x);
    this.vertex3X.setValue(String.valueOf(vertex3.getX()));
    this.vertex3X.setUnit("[m]"); //$NON-NLS-1$

    this.vertex3Y = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex3Y);
    this.vertex3Y.setName(R.string.vertex_3_y);
    this.vertex3Y.setValue(String.valueOf(vertex3.getY()));
    this.vertex3Y.setUnit("[m]"); //$NON-NLS-1$

    this.vertex3Z = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex3Z);
    this.vertex3Z.setName(R.string.vertex_3_z);
    this.vertex3Z.setValue(String.valueOf(vertex3.getZ()));
    this.vertex3Z.setUnit("[m]"); //$NON-NLS-1$

    final VertexModel vertex4 = polygon.getVertex(0);

    this.vertex4X = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex4X);
    this.vertex4X.setName(R.string.vertex_4_x);
    this.vertex4X.setValue(String.valueOf(vertex4.getX()));
    this.vertex4X.setUnit("[m]"); //$NON-NLS-1$

    this.vertex4Y = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex4Y);
    this.vertex4Y.setName(R.string.vertex_4_y);
    this.vertex4Y.setValue(String.valueOf(vertex4.getY()));
    this.vertex4Y.setUnit("[m]"); //$NON-NLS-1$

    this.vertex4Z = new ParameterInputBox(getContext(), this);
    parameters.addView(this.vertex4Z);
    this.vertex4Z.setName(R.string.vertex_4_z);
    this.vertex4Z.setValue(String.valueOf(vertex4.getZ()));
    this.vertex4Z.setUnit("[m]"); //$NON-NLS-1$
  }

  /**
   * パラメータを更新します。
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
  public void updateEditor() {
    // nothing to do
  }

}