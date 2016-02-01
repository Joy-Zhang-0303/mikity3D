package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBoxLayout;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.GraphTree;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;

import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * 円柱を編集するためのエディタを表すラグメントです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/26
 */
public class CylinderEditor extends AbstractObjectEditor {
  /** 半径。 */
  private ParameterInputBoxLayout radius;
  /** 高さ。 */
  private ParameterInputBoxLayout height;
  /** 分割数。 */
  private ParameterInputBoxLayout division;

  /**
   * 新しく生成された<code>CylinderEditorFragment</code>オブジェクトを初期化します。
   * @param object オブジェクト
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public CylinderEditor(ObjectModel object, GraphTree tree, OpenglesModeler modeler) {
    super(object, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  public void createParameterBoxes(final LinearLayout parameters) {
    final CylinderModel cylinder = (CylinderModel)this.object;

    final LayoutInflater layoutInflater = getActivity().getLayoutInflater();
    
    this.radius = (ParameterInputBoxLayout)layoutInflater.inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.radius);
    this.radius.setName(R.string.radius);
    this.radius.setValue(String.valueOf(cylinder.getRadius()));
    this.radius.setUnit("[m]"); //$NON-NLS-1$

    this.height = (ParameterInputBoxLayout)layoutInflater.inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.height);
    this.height.setName(R.string.height);
    this.height.setValue(String.valueOf(cylinder.getHeight()));
    this.height.setUnit("[m]"); //$NON-NLS-1$

    this.division = (ParameterInputBoxLayout)layoutInflater.inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.division);
    this.division.setName(R.string.division);
    this.division.setValue(String.valueOf(cylinder.getDivision()));
    this.division.setUnit(""); //$NON-NLS-1$
  }

  /**
   * パラメータを更新します。
   */
  public void updateModelParameters() {
    final CylinderModel cylinder = (CylinderModel)this.object;
    cylinder.setRadius(this.radius.getFloatValue());
    cylinder.setHeight(this.height.getFloatValue());
    cylinder.setDivision(this.division.getIntValue());
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