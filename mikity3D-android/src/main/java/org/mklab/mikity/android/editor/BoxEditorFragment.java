package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.ParameterInputBoxLayout;
import org.mklab.mikity.android.R;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;

import android.widget.LinearLayout;

/**
 * 直方体を編集するためのエディタを表すラグメントです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/26
 */
public class BoxEditorFragment extends AbstractObjectEditorFragment {
  /** 幅。 */
  private ParameterInputBoxLayout width;
  /** 高さ。 */
  private ParameterInputBoxLayout height;
  /** 奥行き。 */
  private ParameterInputBoxLayout depth;
  
  /**
   * 新しく生成された<code>BoxEditorFragment</code>オブジェクトを初期化します。
   * @param object オブジェクト
   */
  public BoxEditorFragment(BoxModel object) {
    super(object);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void createParameterBoxes(final LinearLayout parameters) {
    final BoxModel box = (BoxModel)this.object;

    this.width = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.width);
    this.width.setName(R.string.width);
    this.width.setValue(String.valueOf(box.getWidth()));
    this.width.setUnit("[m]"); //$NON-NLS-1$

    this.height = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.height);
    this.height.setName(R.string.height);
    this.height.setValue(String.valueOf(box.getHeight()));
    this.height.setUnit("[m]"); //$NON-NLS-1$

    this.depth = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
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
  
  boolean containsOnlyNumbers() {
//    if (super.containsOnlyNumbers() == false) {
//      return false;
//    }
    
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
}