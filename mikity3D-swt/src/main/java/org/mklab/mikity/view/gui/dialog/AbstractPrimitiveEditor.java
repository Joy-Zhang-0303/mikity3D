/*
 * Created on 2015/08/22
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * プリミティブを編集するエディタを表す抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/22
 */
public abstract class AbstractPrimitiveEditor implements PrimitiveEditor, ModifyKeyListener {
  ObjectModel primitive;

  JoglModeler modeler;
  SceneGraphTree tree;
  
  private ColorSelectorButton colorSelector;
  private ParameterInputBox alpha;
  
  Label primitiveType;
  
  /** 値が変更されていればtrue。 */
  boolean isChanged = false;
  
  /** 保存ボタン。 */
  Button saveButton;

  private ParameterInputBox translationX;
  private ParameterInputBox translationY;
  private ParameterInputBox translationZ;
  
  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;
  
  /**
   * 新しく生成された<code>AbstractEditPrimitiveDialog</code>オブジェクトを初期化します。
   * 
   * @param parent 親のシェル
   * @param primitive プリミティブ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public AbstractPrimitiveEditor(Composite parent, ObjectModel primitive, SceneGraphTree tree, JoglModeler modeler) {
    this.primitive = primitive;
    this.tree = tree;
    this.modeler = modeler;
    
    createComposite(parent);
  }

  /**
   * コンポジットを生成します。
   * 
   * @param parent 親
   */
  private void createComposite(Composite parent) {
    final GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    parent.setLayout(layout);

    createParameterBoxes(parent);
    
    createButtonComposite(parent);
  }

  private void createParameterBoxes(Composite parent) {
    this.primitiveType = new Label(parent, SWT.NONE);
    setGridLayout(this.primitiveType, 2);

    final Group group = new Group(parent, SWT.NONE);
    group.setText(Messages.getString("EditPrimitiveDialog.9")); //$NON-NLS-1$
    setGridLayout(group, 1);

    final GridLayout layout = new GridLayout(3, false);
    group.setLayout(layout);
    
    final Label colorLabel = new Label(group, SWT.LEFT);
    colorLabel.setText(Messages.getString("EditPrimitiveDialog.10")); //$NON-NLS-1$
    final GridData gridData = new GridData();
    gridData.minimumWidth = 200;
    colorLabel.setLayoutData(gridData);
    setGridLayout(colorLabel, 1);

    this.colorSelector = new ColorSelectorButton(group, this);
    this.colorSelector.setColor(this.primitive.getColor());

    this.alpha = new ParameterInputBox(group, this, SWT.NONE, Messages.getString("AbstractEditPrimitiveDialog.1"), "" + this.primitive.getColor().getAlpha()); //$NON-NLS-1$ //$NON-NLS-2$

    setGridLayout(new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL), 3);
    
    createParameterBoxes(group);

    setGridLayout(new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL), 3);
    
    createTranslationBoxes(group);
    
    setGridLayout(new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    createRotationBoxes(group);
  }

  @SuppressWarnings("unused")
  private void createRotationBoxes(Group parameterGroup) {
    final RotationModel rotation = this.primitive.getRotation();
    
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
    
    this.rotationX = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.3"), x); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
    this.rotationY = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.4"), y); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
    this.rotationZ = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.5"), z); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
  }

  @SuppressWarnings("unused")
  private void createTranslationBoxes(Group parameterGroup) {
    final TranslationModel translation = this.primitive.getTranslation();
    
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

    this.translationX = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.6"), x); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.translationY = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.7"), y); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.translationZ = new ParameterInputBox(parameterGroup, this, SWT.NONE, Messages.getString("EditPrimitiveDialog.8"), z); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
  }
  
  /**
   * 変更を決定するButtonを作成します。
   */
  private void createButtonComposite(final Composite parent) {
    final Composite composite = new Composite(parent, SWT.NONE);
    setGridLayout(composite, 1);

    final GridLayout layout = new GridLayout(1, true);
    composite.setLayout(layout);
    
    this.saveButton = new Button(composite, SWT.NONE);
    this.saveButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    this.saveButton.setText(Messages.getString("EditPrimitiveDialog.11")); //$NON-NLS-1$
    this.saveButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        saveParameters();
      }
    });
    
    this.saveButton.setEnabled(false);
  }
  
  /**
   * パラメータを保存します。 
   */
  void saveParameters() {
    if (containsOnlyNumbers() == false) {
      final MessageBox message = new MessageBox(this.modeler.getShell(), SWT.ICON_WARNING);
      message.setMessage(Messages.getString("EditPrimitiveDialog.23")); //$NON-NLS-1$
      message.setText(Messages.getString("EditPrimitiveDialog.24")); //$NON-NLS-1$
      message.open();
      return;
    }
    
    updatePrimitiveParameters();
    this.tree.updateTree();
    
    this.modeler.setIsChanged(this.modeler.isChanged() || isChanged());
    this.modeler.updateDisplay();
    
    this.saveButton.setEnabled(false);
  }
  
  /**
   * レイアウトマネージャGridLayoutを設定
   * 
   * @param control
   * @param hSpan
   */
  void setGridLayout(Control control, int hSpan) {
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = hSpan;
    control.setLayoutData(gridData);
  }
  
  /**
   * 数字のみが入力されているか判定します。
   * 
   * @return boolean 数字のみが入力されていればtrue、そうでなければfalse
   */
  boolean containsOnlyNumbers() {
    if (this.rotationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationZ.containsOnlyNumbers() == false) {
      return false;
    }
    return true;
  }

  /**
   * プリミティブのパラメータを更新します。
   */
  void updatePrimitiveParameters() {
    final ColorModel color = this.colorSelector.getColor();
    color.setAlpha(this.alpha.getIntValue());
    this.primitive.setColor(color);
    this.primitive.setTranslation(getTranslation());
    this.primitive.setRotation(getRotation());
    
    updateModelParameters();
  }
  
  /**
   * Rotationを設定 受け取ったRotationを変更に応じて設定
   * 
   * @return rotation
   */
  private RotationModel getRotation() {
    final RotationModel rotation = new RotationModel();
    rotation.setX(this.rotationX.getFloatValue());
    rotation.setY(this.rotationY.getFloatValue());
    rotation.setZ(this.rotationZ.getFloatValue());
    return rotation;
  }

  /**
   * Translationを返します。
   * 
   * @return translation
   */
  private TranslationModel getTranslation() {
    final TranslationModel translation = new TranslationModel();
    translation.setX(this.translationX.getFloatValue());
    translation.setY(this.translationY.getFloatValue());
    translation.setZ(this.translationZ.getFloatValue());
    return translation;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isChanged() {
    return this.isChanged;
  }
  
  /**
   * {@inheritDoc}
   */
  public void modifyText(ModifyEvent arg0) {
    if (this.saveButton != null) {
      this.isChanged = true;
      this.saveButton.setEnabled(true);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public void keyPressed(KeyEvent e) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void keyReleased(KeyEvent e) {
    if (e.character==SWT.CR){
      saveParameters();
    }
  }
}
