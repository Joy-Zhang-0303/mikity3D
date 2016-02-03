package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBoxLayout;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.GraphTree;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;

import android.widget.TableLayout;

/**
 * 直方体を編集するためのエディタを表すラグメントです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/26
 */
public class BoxEditor extends AbstractObjectEditor {
  /** 幅。 */
  private ParameterInputBoxLayout width;
  /** 高さ。 */
  private ParameterInputBoxLayout height;
  /** 奥行き。 */
  private ParameterInputBoxLayout depth;
  
  /**
   * 新しく生成された<code>BoxEditorFragment</code>オブジェクトを初期化します。
   * @param object オブジェクト
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public BoxEditor(ObjectModel object, GraphTree tree, OpenglesModeler modeler) {
    super(object, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  public void createParameterBoxes(final TableLayout parameters) {
    final BoxModel box = (BoxModel)this.object;
    
    this.width = new ParameterInputBoxLayout(getContext());
    parameters.addView(this.width);
    this.width.setName(R.string.width);
    this.width.setValue(String.valueOf(box.getWidth()));
    this.width.setUnit("[m]"); //$NON-NLS-1$

    this.height = new ParameterInputBoxLayout(getContext());
    parameters.addView(this.height);
    this.height.setName(R.string.height);
    this.height.setValue(String.valueOf(box.getHeight()));
    this.height.setUnit("[m]"); //$NON-NLS-1$

    this.depth = new ParameterInputBoxLayout(getContext());
    parameters.addView(this.depth);
    this.depth.setName(R.string.depth);
    this.depth.setValue(String.valueOf(box.getDepth()));
    this.depth.setUnit("[m]"); //$NON-NLS-1$
  }

  /**
   * パラメータを更新します。
   */
  public void updateModelParameters() {
    final BoxModel box = (BoxModel)this.object;
    box.setWidth(this.width.getFloatValue());
    box.setHeight(this.height.getFloatValue());
    box.setDepth(this.depth.getFloatValue());
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  boolean containsOnlyNumbers() {
    if (super.containsOnlyNumbers() == false) {
      return false;
    }
    
    if (this.width.containsOnlyNumbers() == false) {
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
  
  /**
   * {@inheritDoc}
   */
  public void updateEditor() {
    // nothing to do
  }
}