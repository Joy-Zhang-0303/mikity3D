/*
 * Created on 2016/01/31
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBoxLayout;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.SceneGraphTreeFragment;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;



/**
 * オブジェクトを編集するエディタを表すフラグメントです。
 * @author koga
 * @version $Revision$, 2016/01/31
 */
public abstract class AbstractObjectEditorFragment extends Fragment {
  ObjectModel object;
  OpenglesModeler modeler;
  SceneGraphTreeFragment tree;
  
  private ParameterInputBoxLayout colorR;
  private ParameterInputBoxLayout colorG;
  private ParameterInputBoxLayout colorB;
  private ParameterInputBoxLayout colorAlpha;
  
  private ParameterInputBoxLayout translationX;
  private ParameterInputBoxLayout translationY;
  private ParameterInputBoxLayout translationZ;
  
  private ParameterInputBoxLayout rotationX;
  private ParameterInputBoxLayout rotationY;
  private ParameterInputBoxLayout rotationZ;
  
  /**
   * パラメータを編集するボックスを生成します。
   * 
   * @param parameters パラメータを設定するレイアウト
   */
  abstract void createParameterBoxes(final LinearLayout parameters);
  
  /**
   * 新しく生成された<code>AbstractObjectEditorFragment</code>オブジェクトを初期化します。
   * @param object オブジェクト
   */
  public AbstractObjectEditorFragment(ObjectModel object) {
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
  
  private void createColorBoxes(LinearLayout parameters) {
    final ColorModel color = this.object.getColor();
    
    this.colorR = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.colorR);
    this.colorR.setName(R.string.color_r);
    this.colorR.setValue("" + color.getR()); //$NON-NLS-1$
    this.colorR.setUnit(""); //$NON-NLS-1$
    
    this.colorG = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.colorG);
    this.colorG.setName(R.string.color_g);
    this.colorG.setValue("" + color.getG()); //$NON-NLS-1$
    this.colorG.setUnit(""); //$NON-NLS-1$

    this.colorB = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.colorB);
    this.colorB.setName(R.string.color_b);
    this.colorB.setValue("" + color.getB()); //$NON-NLS-1$
    this.colorB.setUnit(""); //$NON-NLS-1$

    this.colorAlpha = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.colorAlpha);
    this.colorAlpha.setName(R.string.color_alpha);
    this.colorAlpha.setValue("" + color.getAlpha()); //$NON-NLS-1$
    this.colorAlpha.setUnit(""); //$NON-NLS-1$
  }

  private void createRotationBoxes(LinearLayout parameters) {
    final RotationModel rotation = this.object.getRotation();
    
    final String x;
    final String y;
    final String z;
    
    if (rotation != null) {
      x = "" + rotation.getX(); //$NON-NLS-1$
      y = "" + rotation.getY(); //$NON-NLS-1$
      z = "" + rotation.getZ(); //$NON-NLS-1$
    } else {
      x = "0"; //$NON-NLS-1$
      y = "0"; //$NON-NLS-1$
      z = "0"; //$NON-NLS-1$
    }
    
    this.rotationX = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.rotationX);
    this.rotationX.setName(R.string.rotation_wrt_x_axis);
    this.rotationX.setValue(x);
    this.rotationX.setUnit("[rad]"); //$NON-NLS-1$

    this.rotationY = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.rotationY);
    this.rotationY.setName(R.string.rotation_wrt_y_axis);
    this.rotationY.setValue(y);
    this.rotationY.setUnit("[rad]"); //$NON-NLS-1$
    
    this.rotationZ = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.rotationZ);
    this.rotationZ.setName(R.string.rotation_wrt_z_axis);
    this.rotationZ.setValue(z);
    this.rotationZ.setUnit("[rad]"); //$NON-NLS-1$
  }

  private void createTranslationBoxes(LinearLayout parameters) {
    final TranslationModel translation = this.object.getTranslation();
    
    final String x;
    final String y;
    final String z;
    if (translation != null) {
      x = "" + translation.getX(); //$NON-NLS-1$
      y = "" + translation.getY(); //$NON-NLS-1$
      z = "" + translation.getZ(); //$NON-NLS-1$
    } else {
      x = "0"; //$NON-NLS-1$
      y = "0"; //$NON-NLS-1$
      z = "0"; //$NON-NLS-1$
    }

    this.translationX = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.translationX);
    this.translationX.setName(R.string.translation_to_x_axis);
    this.translationX.setValue(x);
    this.translationX.setUnit("[m]"); //$NON-NLS-1$

    this.translationY = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.translationY);
    this.translationY.setName(R.string.translation_to_y_axis);
    this.translationY.setValue(y);
    this.translationY.setUnit("[m]"); //$NON-NLS-1$
    
    this.translationZ = (ParameterInputBoxLayout)getActivity().getLayoutInflater().inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.translationZ);
    this.translationZ.setName(R.string.translation_to_z_axis);
    this.translationZ.setValue(z);
    this.translationZ.setUnit("[m]"); //$NON-NLS-1$
  }
}
