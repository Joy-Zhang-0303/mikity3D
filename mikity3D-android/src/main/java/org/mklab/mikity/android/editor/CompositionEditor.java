/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBox;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.SceneGraphTree;
import org.mklab.mikity.model.xml.simplexml.model.CompositionModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 三角形の面による合成オブジェクトの編集するエディタを表すクラスです。
 * 
 */
public class CompositionEditor extends AbstractObjectEditor {
  /** Facetの数。 */
  private ParameterInputBox size;
  /** 幅。 */
  private ParameterInputBox width;
  /** 高さ。*/
  private ParameterInputBox height;
  /** 奥行き。 */
  private ParameterInputBox depth;
  
  /** 比率を保存するためのチェックボックス。 */
  private CheckBox preservingRatio;

  /**
   * 新しく生成された<code>CompositionEditor</code>オブジェクトを初期化します。
   * @param object オブジェクト
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public CompositionEditor(ObjectModel object, SceneGraphTree tree, OpenglesModeler modeler) {
    super(object, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  public void createParameterBoxes(final TableLayout parameters) {
    this.objectType.setText(getString(R.string.objectType) + ": " + getString(R.string.composition)); //$NON-NLS-1$
    
    final CompositionModel compostion = (CompositionModel)this.object;
       
    this.size = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.size);
    this.size.setName(R.string.size_of_facets);
    this.size.setValue(String.valueOf(compostion.getSize()));
    this.size.setUnit(""); //$NON-NLS-1$

    this.width = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.width);
    this.width.setName(R.string.width);
    this.width.setValue(String.valueOf(compostion.getWidth()));
    this.width.setUnit("[m]"); //$NON-NLS-1$

    this.height = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.height);
    this.height.setName(R.string.height);
    this.height.setValue(String.valueOf(compostion.getHeight()));
    this.height.setUnit("[m]"); //$NON-NLS-1$
    
    this.depth = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.depth);
    this.depth.setName(R.string.depth);
    this.depth.setValue(String.valueOf(compostion.getDepth()));
    this.depth.setUnit("[m]"); //$NON-NLS-1$

    final TableRow preservingRatioLayout = new TableRow(getContext());
    parameters.addView(preservingRatioLayout);
    
    final TextView preservingRatioLabel = new TextView(getContext());
    preservingRatioLabel.setText(R.string.preserve_ratio);
    preservingRatioLabel.setTextColor(Color.BLACK);
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