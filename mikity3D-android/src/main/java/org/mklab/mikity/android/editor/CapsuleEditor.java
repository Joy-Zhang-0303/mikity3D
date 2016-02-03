package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBoxLayout;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.GraphTree;
import org.mklab.mikity.model.xml.simplexml.model.CapsuleModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;

import android.widget.TableLayout;

/**
 * カプセル編集するためのエディタを表すラグメントです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/26
 */
public class CapsuleEditor extends AbstractObjectEditor {
  /** 半径。 */
  private ParameterInputBoxLayout radius;
  /** 高さ。 */
  private ParameterInputBoxLayout height;
  /** 分割数。 */
  private ParameterInputBoxLayout division;

  /**
   * 新しく生成された<code>CapsuleEditorFragment</code>オブジェクトを初期化します。
   * @param object オブジェクト
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public CapsuleEditor(ObjectModel object, GraphTree tree, OpenglesModeler modeler) {
    super(object, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  public void createParameterBoxes(final TableLayout parameters) {
    final CapsuleModel capsule = (CapsuleModel)this.object;
    
    this.radius = new ParameterInputBoxLayout(getContext());
    parameters.addView(this.radius);
    this.radius.setName(R.string.radius);
    this.radius.setValue(String.valueOf(capsule.getRadius()));
    this.radius.setUnit("[m]"); //$NON-NLS-1$

    this.height = new ParameterInputBoxLayout(getContext());
    parameters.addView(this.height);
    this.height.setName(R.string.height);
    this.height.setValue(String.valueOf(capsule.getHeight()));
    this.height.setUnit("[m]"); //$NON-NLS-1$

    this.division = new ParameterInputBoxLayout(getContext());
    parameters.addView(this.division);
    this.division.setName(R.string.division);
    this.division.setValue(String.valueOf(capsule.getDivision()));
    this.division.setUnit(""); //$NON-NLS-1$
  }

  /**
   * パラメータを更新します。
   */
  public void updateModelParameters() {
    final CapsuleModel capsule = (CapsuleModel)this.object;
    capsule.setRadius(this.radius.getFloatValue());
    capsule.setHeight(this.height.getFloatValue());
    capsule.setDivision(this.division.getIntValue());
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