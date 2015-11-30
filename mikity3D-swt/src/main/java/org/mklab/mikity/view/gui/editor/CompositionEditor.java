/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.mklab.mikity.model.xml.simplexml.model.CompositionModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.Messages;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * 三角形の面による合成オブジェクトの編集するエディタを表すクラスです。
 * 
 */
public class CompositionEditor extends AbstractObjectEditor {
  /** 幅。 */
  private ParameterInputBox widthValue;
  /** 高さ。*/
  private ParameterInputBox heightValue;
  /** 奥行き。 */
  private ParameterInputBox depthValue;
  /** 比率保存用ボタン。 */
  private Button preservingRatio;

  /**
   * 新しく生成された<code>CompositionEditor</code>オブジェクトを初期化します。
   * @param parent 親のシェル
   * @param primitive プリミティブ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public CompositionEditor(Composite parent, ObjectModel primitive, SceneGraphTree tree, JoglModeler modeler) {
    super(parent, primitive, tree, modeler);
  }

  /**
   * {@inheritDoc}
   */
  public void createParameterBoxes(Group parameterGroup) {
    this.objectType.setText(Messages.getString("CompositionEditor.0")); //$NON-NLS-1$
    
    final CompositionModel compostion = (CompositionModel)this.object;
    
    final ParameterInputBox sizeValue = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("CompositionEditor.1"), "" + compostion.getSize()); //$NON-NLS-1$ //$NON-NLS-2$
    sizeValue.setEditable(false);
       
    this.widthValue = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("CompositionEditor.2"), "" + compostion.getWidth()); //$NON-NLS-1$ //$NON-NLS-2$

    final Label widthUnit = new Label(parameterGroup, SWT.NONE);
    widthUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(widthUnit, 1);
    
    this.heightValue = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("CompositionEditor.3"), "" + compostion.getHeight()); //$NON-NLS-1$ //$NON-NLS-2$

    final Label heightUnit = new Label(parameterGroup, SWT.NONE);
    heightUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(heightUnit, 1);
    
    this.depthValue = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("CompositionEditor.5"), "" + compostion.getDepth()); //$NON-NLS-1$ //$NON-NLS-2$

    final Label depthUnit = new Label(parameterGroup, SWT.NONE);
    depthUnit.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(depthUnit, 1);
    
    final Label preservingRatioLabel = new Label(parameterGroup, SWT.NONE);
    preservingRatioLabel.setText(Messages.getString("CompositionEditor.4")); //$NON-NLS-1$
    setGridLayout(preservingRatioLabel, 1);
    
    this.preservingRatio = new Button(parameterGroup, SWT.CHECK);
    this.preservingRatio.setSelection(true);
    this.preservingRatio.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetDefaultSelected(@SuppressWarnings("unused") SelectionEvent e) {
        // nothing to do
      }

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        modifyText(null);
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  public void updateModelParameters() {
    final float width =  this.widthValue.getFloatValue();
    final float compositionWidth = ((CompositionModel)this.object).getWidth();

    final float height =  this.heightValue.getFloatValue();
    final float compositionHeight = ((CompositionModel)this.object).getHeight();
    
    final float depth =  this.depthValue.getFloatValue();
    final float compositionDepth = ((CompositionModel)this.object).getDepth();

    if (width != compositionWidth || height != compositionHeight || depth != compositionDepth) {
      if (this.preservingRatio.getSelection() == false) {
        ((CompositionModel)this.object).scale(depth/compositionDepth, width/compositionWidth, height/compositionHeight);
      } else {
        float scale = 1;
        if (height != compositionHeight) {
          scale = height/compositionHeight;
        }
        if (depth != compositionDepth) {
          scale = depth/compositionDepth;
        }
        if (width != compositionWidth) {
          scale = width/compositionWidth;
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
    this.widthValue.setValue("" + compostion.getWidth()); //$NON-NLS-1$
    this.heightValue.setValue("" + compostion.getHeight()); //$NON-NLS-1$
    this.depthValue.setValue("" + compostion.getDepth()); //$NON-NLS-1$
  }
}