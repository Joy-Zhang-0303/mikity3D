package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBoxLayout;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.SceneGraphTreeFragment;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class BoxEditorFragment extends Fragment {
  FragmentManager fragmentManager;
  
  ObjectModel object;
  OpenglesModeler modeler;
  SceneGraphTreeFragment tree;
  
  ParameterInputBoxLayout width;
  ParameterInputBoxLayout height;
  ParameterInputBoxLayout depth;
  
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

    createParameterBoxes(parameters);
    
    return view;
  }

  void createParameterBoxes(final LinearLayout parameters) {
    final BoxModel box = (BoxModel)this.object;

    this.width = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.width);
    final TextView widthName = (TextView)this.width.findViewById(R.id.parameterName);
    widthName.setText(R.string.width);
    final EditText widthValue = (EditText)this.width.findViewById(R.id.parameterValue);
    widthValue.setText(String.valueOf(box.getWidth()));
    final TextView widthUnit = (TextView)this.width.findViewById(R.id.parameterUnit);
    widthUnit.setText("[m]"); //$NON-NLS-1$

    this.height = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.height);
    final TextView heightName = (TextView)this.height.findViewById(R.id.parameterName);
    heightName.setText(R.string.height);
    final EditText heightValue = (EditText)this.height.findViewById(R.id.parameterValue);
    heightValue.setText(String.valueOf(box.getHeight()));
    final TextView heightUnit = (TextView)this.height.findViewById(R.id.parameterUnit);
    heightUnit.setText("[m]"); //$NON-NLS-1$

    this.depth = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.depth);
    final TextView depthName = (TextView)this.depth.findViewById(R.id.parameterName);
    depthName.setText(R.string.depth);
    final EditText depthValue = (EditText)this.depth.findViewById(R.id.parameterValue);
    depthValue.setText(String.valueOf(box.getDepth()));
    final TextView depthtUnit = (TextView)this.depth.findViewById(R.id.parameterUnit);
    depthtUnit.setText("[m]"); //$NON-NLS-1$
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
}