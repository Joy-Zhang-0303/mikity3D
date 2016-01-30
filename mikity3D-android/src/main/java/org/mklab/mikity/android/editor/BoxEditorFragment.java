package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.ParameterInputBoxLayout;
import org.mklab.mikity.android.R;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    this.object = object;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_box_editor, container, false);
    
    final LinearLayout parameters = ((LinearLayout)view.findViewById(R.id.layout_parameters));
    if (parameters != null) {
      parameters.removeAllViews();
    }
    
    final Button backButton = (Button)view.findViewById(R.id.backButton);
    backButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
      }
    });

    createColorBoxes(parameters);
    createParameterBoxes(parameters);
    createTranslationBoxes(parameters);
    createRotationBoxes(parameters);
    
    return view;
  }

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