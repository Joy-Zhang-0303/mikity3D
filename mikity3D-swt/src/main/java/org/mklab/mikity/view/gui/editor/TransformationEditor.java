/*
 * Created on 2005/02/01
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.model.CompositionModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;


/**
 * 変換を実施するためのダイアログを表すクラスです。
 * 
 */
public class TransformationEditor implements ModifyKeyListener {
  Shell sShell = null;

  private ParameterInputBox scaleX;
  private ParameterInputBox scaleY;
  private ParameterInputBox scaleZ;

  private ParameterInputBox translationX;
  private ParameterInputBox translationY;
  private ParameterInputBox translationZ;

  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;

  private CompositionModel object;
  
  JoglModeler modeler;
  
  /** 値が変更されていればtrue。 */
  private boolean isChanged = false;
  /** 保存ボタン。 */
  Button saveButton;

  /**
   * 新しく生成された<code>ConfigurationDialog</code>オブジェクトを初期化します。
   * @param parentShell 親シェル
   * @param object 設定
   * @param modeler モデラー
   */
  public TransformationEditor(Shell parentShell, CompositionModel object, JoglModeler modeler) {
    this.object = object;
    this.modeler = modeler;

    this.sShell = new Shell(parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    this.sShell.setText(Messages.getString("TransformationEditor.4")); //$NON-NLS-1$
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 560));

    final GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    this.sShell.setLayout(layout);

    createEditGroup(this.sShell);
    createMainButtonComposite(this.sShell);
  }

  /**
   * 変換用のグループを生成します。
   */
  private void createEditGroup(Shell parent) {
    final Group editGroup = new Group(parent, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    editGroup.setLayout(layout);
    final GridData data = new GridData(GridData.FILL_BOTH);
    editGroup.setLayoutData(data);
    editGroup.setText(Messages.getString("TransformationEditor.3")); //$NON-NLS-1$

    createTranslation(editGroup);
    
    createRotation(editGroup);
    
    createScale(editGroup);
  }

  /**
   * 並進変換を設定するグループを生成します。
   */
  private void createTranslation(Group parent) {
    final Group group = new Group(parent, SWT.NONE);
    group.setText(Messages.getString("TransformationEditor.0")); //$NON-NLS-1$

    final GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
    groupData.horizontalSpan = 2;
    final GridLayout layout = new GridLayout(7, true);
    group.setLayout(layout);
    group.setLayoutData(groupData);
    
    this.translationX = new ParameterInputBox(group, this, SWT.NONE, "  (", "" + 0); //$NON-NLS-1$ //$NON-NLS-2$
    this.translationY = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + 0); //$NON-NLS-1$ //$NON-NLS-2$
    this.translationZ = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + 0); //$NON-NLS-1$ //$NON-NLS-2$

    final Label label = new Label(group, SWT.NONE);
    final GridData labelData = new GridData();
    label.setLayoutData(labelData);
    label.setText(")"); //$NON-NLS-1$
  }

  /**
   * 回転変換を設定するグループを生成します。
   */
  private void createRotation(Group parent) {
    final Group group = new Group(parent, SWT.NONE);
    group.setText(Messages.getString("TransformationEditor.1")); //$NON-NLS-1$

    final GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
    groupData.horizontalSpan = 2;
    final GridLayout layout = new GridLayout(7, true);
    group.setLayout(layout);
    group.setLayoutData(groupData);

    this.rotationX = new ParameterInputBox(group, this, SWT.NONE, "  (", "" + 0); //$NON-NLS-1$//$NON-NLS-2$
    this.rotationY = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + 0); //$NON-NLS-1$ //$NON-NLS-2$
    this.rotationZ = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + 0); //$NON-NLS-1$ //$NON-NLS-2$

    final Label label = new Label(group, SWT.NONE);
    final GridData data = new GridData();
    label.setLayoutData(data);
    label.setText(")"); //$NON-NLS-1$
  }

  /**
   * スケール(拡大縮小)を設定するグループを生成します。
   */
  private void createScale(Group parent) {
    final Group group = new Group(parent, SWT.NONE);
    group.setText(Messages.getString("TransformationEditor.2")); //$NON-NLS-1$

    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.horizontalSpan = 2;
    final GridLayout layout = new GridLayout(7, true);
    group.setLayout(layout);
    group.setLayoutData(data);
    
    this.scaleX = new ParameterInputBox(group, this, SWT.NONE, "  (", "" + 1); //$NON-NLS-1$ //$NON-NLS-2$
    this.scaleY = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + 1); //$NON-NLS-1$ //$NON-NLS-2$
    this.scaleZ = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + 1); //$NON-NLS-1$ //$NON-NLS-2$

    final Label label = new Label(group, SWT.NONE);
    final GridData lightData = new GridData();
    lightData.widthHint = 10;
    label.setLayoutData(lightData);
    label.setText(")"); //$NON-NLS-1$
  }

  /**
   * 視点座標に数字以外の文字が入っていたときに出すメッセージボックスを生成します。
   */
  void createMessageBoxForNonNumericInput() {
    final MessageBox messsageBox = new MessageBox(this.sShell, SWT.ICON_WARNING);
    messsageBox.setMessage(Messages.getString("ConfigDialog.14")); //$NON-NLS-1$
    messsageBox.setText(Messages.getString("ConfigDialog.15")); //$NON-NLS-1$
    messsageBox.open();
  }

  /**
   * メインボタンのあるコンポジットを生成します。
   */
  private void createMainButtonComposite(Shell parent) {
    final Composite composite = new Composite(parent, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    composite.setLayout(layout);
    
    this.saveButton = new Button(composite, SWT.NONE);
    this.saveButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    this.saveButton.setText(Messages.getString("ConfigurationDialog.0")); //$NON-NLS-1$
    this.saveButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(@SuppressWarnings("unused") org.eclipse.swt.events.SelectionEvent e) {
        saveParameters();        
      }

    });
    this.saveButton.setEnabled(false);
    
    final Button closeButton = new Button(composite, SWT.NONE);
    closeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    closeButton.setText(Messages.getString("ConfigDialog.17")); //$NON-NLS-1$
    closeButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(@SuppressWarnings("unused") org.eclipse.swt.events.SelectionEvent e) {
        if (TransformationEditor.this.isChanged() == false) {
          TransformationEditor.this.sShell.close();
          return;
        }
        
        final MessageBox message = new MessageBox(TransformationEditor.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        message.setMessage(Messages.getString("EditPrimitiveDialog.26")); //$NON-NLS-1$
        final int yesNo = message.open();
        if (yesNo == SWT.YES) {
         TransformationEditor.this.sShell.close();
        }
      }
    });
  }
  
  /**
   * パラメータを保存します。
   */
  void saveParameters() {
    if (containsOnlyNumbers() == false) {
      createMessageBoxForNonNumericInput();
      return;
    }
    
    this.modeler.setIsChanged(this.modeler.isChanged() || isChanged());
    
    updateObjectParameters();
    this.modeler.updatePropertyEditor();
    this.modeler.updateRenderer();
    //this.modeler.updateDisplay();
    
    this.isChanged = false;
    this.saveButton.setEnabled(false);
  }

  /**
   * Configurationのパラメータを更新します。
   */
  void updateObjectParameters() {
    final float dx = this.translationX.getFloatValue();
    final float dy = this.translationY.getFloatValue();
    final float dz = this.translationZ.getFloatValue();
    if (dx != 0 || dy != 0 || dz != 0) { 
      this.object.translate(dx, dy, dz);
    }

    final float rx = this.rotationX.getFloatValue();
    final float ry = this.rotationY.getFloatValue();
    final float rz = this.rotationZ.getFloatValue();
    if (rx != 0) { 
      this.object.rotateX(rx);
    }
    if (ry != 0) { 
      this.object.rotateY(ry);
    }
    if (rz != 0) { 
      this.object.rotateZ(rz);
    }

    final float sx = this.scaleX.getFloatValue();
    final float sy = this.scaleY.getFloatValue();
    final float sz = this.scaleZ.getFloatValue();
    if (sx != 1 || sy != 1 || sz != 1) {
      this.object.scale(sx, sy, sz);
    }
    
    resetTransformationParameters();
  }
  
  /**
   * 変換パラメータをリセットします。
   */
  private void resetTransformationParameters() {
    this.translationX.setValue("0"); //$NON-NLS-1$
    this.translationY.setValue("0"); //$NON-NLS-1$
    this.translationZ.setValue("0"); //$NON-NLS-1$
    
    this.rotationX.setValue("0"); //$NON-NLS-1$
    this.rotationY.setValue("0"); //$NON-NLS-1$
    this.rotationZ.setValue("0"); //$NON-NLS-1$
    
    this.scaleX.setValue("1"); //$NON-NLS-1$
    this.scaleY.setValue("1"); //$NON-NLS-1$
    this.scaleZ.setValue("1"); //$NON-NLS-1$
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean containsOnlyNumbers() {
    if (this.scaleX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.scaleY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.scaleZ.containsOnlyNumbers() == false) {
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
    if (this.rotationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationZ.containsOnlyNumbers() == false) {
      return false;
    }
    return true;
  }

  /**
   * Shellを開きます。
   */
  public void open() {
    this.sShell.open();
    final Display display = this.sShell.getDisplay();
    while (!this.sShell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * パラメータが変更されたか判定します。
   * 
   * @return パラメータが変更されていればtrue
   */
  public boolean isChanged() {
    return this.isChanged;
  }
  
  /**
   * {@inheritDoc}
   */
  public void modifyText(@SuppressWarnings("unused") ModifyEvent e) {
    if (this.saveButton != null) {
      this.isChanged = true;
      this.saveButton.setEnabled(true);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public void keyPressed(@SuppressWarnings("unused") KeyEvent e) {
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