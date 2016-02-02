/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBoxLayout;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.GraphTree;
import org.mklab.mikity.model.xml.simplexml.model.CompositionModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 三角形の面による合成オブジェクトの編集するエディタを表すクラスです。
 * 
 */
public class CompositionEditor extends AbstractObjectEditor {
  /** Facetの数。 */
  private ParameterInputBoxLayout size;
  /** 幅。 */
  private ParameterInputBoxLayout width;
  /** 高さ。*/
  private ParameterInputBoxLayout height;
  /** 奥行き。 */
  private ParameterInputBoxLayout depth;
  
  /** 比率を保存するためのチェックボックス。 */
  private CheckBox preservingRatio;

  /**
   * 新しく生成された<code>CompositionEditor</code>オブジェクトを初期化します。
   * @param object オブジェクト
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public CompositionEditor(ObjectModel object, GraphTree tree, OpenglesModeler modeler) {
    super(object, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  public void createParameterBoxes(final LinearLayout parameters) {
    final CompositionModel compostion = (CompositionModel)this.object;
    
    final LayoutInflater layoutInflater = getActivity().getLayoutInflater();
       
    this.size = (ParameterInputBoxLayout)layoutInflater.inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.size);
    this.size.setName(R.string.size_of_facets);
    this.size.setValue(String.valueOf(compostion.getSize()));
    this.size.setUnit(""); //$NON-NLS-1$

    this.width = (ParameterInputBoxLayout)layoutInflater.inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.width);
    this.width.setName(R.string.width);
    this.width.setValue(String.valueOf(compostion.getWidth()));
    this.width.setUnit("[m]"); //$NON-NLS-1$

    this.height = (ParameterInputBoxLayout)layoutInflater.inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.height);
    this.height.setName(R.string.height);
    this.height.setValue(String.valueOf(compostion.getHeight()));
    this.height.setUnit("[m]"); //$NON-NLS-1$
    
    this.depth = (ParameterInputBoxLayout)layoutInflater.inflate(R.layout.parameter_input_box, null);
    parameters.addView(this.depth);
    this.depth.setName(R.string.depth);
    this.depth.setValue(String.valueOf(compostion.getDepth()));
    this.depth.setUnit("[m]"); //$NON-NLS-1$

    final LinearLayout preservingRatioLayout = new LinearLayout(getContext());
    preservingRatioLayout.setOrientation(LinearLayout.HORIZONTAL);
    parameters.addView(preservingRatioLayout);
    
    final TextView preservingRatioLabel = new TextView(getContext());
    preservingRatioLabel.setText(R.string.preserve_ratio);
    preservingRatioLabel.setTextColor(R.color.black);
    preservingRatioLayout.addView(preservingRatioLabel);
    
    this.preservingRatio = new CheckBox(getContext());
    this.preservingRatio.setChecked(true);
    preservingRatioLayout.addView(this.preservingRatio);
    
    this.preservingRatio.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
      public void onViewDetachedFromWindow(@SuppressWarnings("unused") View v) {
        // nothing to do
      }
      
      public void onViewAttachedToWindow(@SuppressWarnings("unused") View v) {
        // nothing to do
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  public void updateModelParameters() {
    final float widthValue =  this.width.getFloatValue();
    final float compositionWidth = ((CompositionModel)this.object).getWidth();

    final float heightValue =  this.height.getFloatValue();
    final float compositionHeight = ((CompositionModel)this.object).getHeight();
    
    final float depthValue =  this.depth.getFloatValue();
    final float compositionDepth = ((CompositionModel)this.object).getDepth();

    if (widthValue != compositionWidth || heightValue != compositionHeight || depthValue != compositionDepth) {
      if (this.preservingRatio.isChecked() == false) {
        ((CompositionModel)this.object).scale(depthValue/compositionDepth, widthValue/compositionWidth, heightValue/compositionHeight);
      } else {
        float scale = 1;
        if (heightValue != compositionHeight) {
          scale = heightValue/compositionHeight;
        }
        if (depthValue != compositionDepth) {
          scale = depthValue/compositionDepth;
        }
        if (widthValue != compositionWidth) {
          scale = widthValue/compositionWidth;
        }
        ((CompositionModel)this.object).scale(scale, scale, scale);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  boolean containsOnlyNumbers() {
    if (super.containsOnlyNumbers() == false) {
      return false;
    }

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void updateEditor() {
    final CompositionModel compostion = (CompositionModel)this.object;
    this.width.setValue("" + compostion.getWidth()); //$NON-NLS-1$
    this.height.setValue("" + compostion.getHeight()); //$NON-NLS-1$
    this.depth.setValue("" + compostion.getDepth()); //$NON-NLS-1$
  }
}