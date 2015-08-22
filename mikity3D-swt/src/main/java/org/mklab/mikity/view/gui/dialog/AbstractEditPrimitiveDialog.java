/*
 * Created on 2015/08/22
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * プリミティブを編集するダイアログを表す抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/22
 */
public abstract class AbstractEditPrimitiveDialog {
  Shell parentShell;
  /** */
  Shell sShell;
  PrimitiveModel primitive;
  String groupName;

  JoglModeler modeler;
  SceneGraphTree tree;
  
  ColorSelectorButton colorSelector;
  
  Label primitiveLabel;
  Label unitLabel1;
  Label unitLabel2;
  Label unitLabel3;

  ParameterInputBox parameter1;
  ParameterInputBox parameter2;
  ParameterInputBox parameter3;

  ParameterInputBox rotationX;
  ParameterInputBox rotationY;
  ParameterInputBox rotationZ;

  ParameterInputBox translationX;
  ParameterInputBox translationY;
  ParameterInputBox translationZ;

  /**
   * @param group
   */
  abstract void createPrameterBoxes(Group group);

  /**
   * ボックスにパラメータを設定します。
   */
  abstract void setParametersInBoxes();
  
  /**
   * プリミティブのパラメータを更新します。
   */
  abstract void updateModelParameters();
  
  /**
   * コンストラクター
   * 
   * @param parentShell 親のシェル
   * @param primitive プリミティブ
   * @param group グループ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public AbstractEditPrimitiveDialog(Shell parentShell, PrimitiveModel primitive, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    this.parentShell = parentShell;
    this.primitive = primitive;
    this.groupName = group.getName();
    this.tree = tree;
    this.modeler = modeler;
    
    this.tree.setIsModifyingObject(true);
    
    createSShell();
    setParametersInDialog();
  }
  
  /**
   * シェルを開く
   */
  public void open() {
    this.sShell.open();
//    Display display = this.sShell.getDisplay();
//    while (!this.sShell.isDisposed()) {
//      if (!display.readAndDispatch()) {
//        display.sleep();
//      }
//    }
  }
  
  
  /**
   * シェルを作成
   */
  @SuppressWarnings({"unused"})
  private void createSShell() {
    //this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(350, 550));
    this.sShell.setText(Messages.getString("EditPrimitiveDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);
    
    addShellListener();

    final Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("EditPrimitiveDialog.1") + this.groupName); //$NON-NLS-1$
    setGridLayout(groupLabel, 2);

    this.primitiveLabel = new Label(this.sShell, SWT.NONE);
    setGridLayout(this.primitiveLabel, 2);

    Group parameterGroup = new Group(this.sShell, SWT.NONE);
    parameterGroup.setText(Messages.getString("EditPrimitiveDialog.9")); //$NON-NLS-1$
    setGridLayout(parameterGroup, 1);

    final GridLayout newValueLayout = new GridLayout(3, false);
    parameterGroup.setLayout(newValueLayout);
    
    final Label colorLabel = new Label(parameterGroup, SWT.LEFT);
    colorLabel.setText(Messages.getString("EditPrimitiveDialog.10")); //$NON-NLS-1$
    final GridData gridData = new GridData();
    gridData.minimumWidth = 200;
    colorLabel.setLayoutData(gridData);
    setGridLayout(colorLabel, 1);

    this.colorSelector = new ColorSelectorButton(parameterGroup);
    
    final Label spaceLabel = new Label(parameterGroup, SWT.NONE);
    spaceLabel.setText(" "); //$NON-NLS-1$
    setGridLayout(spaceLabel, 1);
    
    createPrameterBoxes(parameterGroup);

    this.translationX = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.6"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.translationY = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.7"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.translationZ = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.8"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    final Label label6 = new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label6, 3);

    this.rotationX = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.3"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
    this.rotationY = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.4"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
    this.rotationZ = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.5"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$

    createButtonComposite();
  }
  
  /**
   * Shellのリスナーを追加します。 
   */
  private void addShellListener() {
    this.sShell.addShellListener(new ShellListener() {
      public void shellIconified(ShellEvent arg0) {
        // nothing to do
      }
      
      public void shellDeiconified(ShellEvent arg0) {
        // nothing to do
      }
      
      public void shellDeactivated(ShellEvent arg0) {
        // nothing to do
      }
      
      public void shellClosed(ShellEvent arg0) {
        AbstractEditPrimitiveDialog.this.tree.setIsModifyingObject(false);
      }
      
      public void shellActivated(ShellEvent arg0) {
        // nothing to do
      }
    });
  }

  
  /**
   * 変更を決定するButtonを作成します。
   */
  private void createButtonComposite() {
    final Composite comp = new Composite(this.sShell, SWT.NONE);
    setGridLayout(comp, 3);

    final GridLayout compLayout = new GridLayout(3, true);
    comp.setLayout(compLayout);
    
    final Button okButton = new Button(comp, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("EditPrimitiveDialog.20")); //$NON-NLS-1$
    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (containsOnlyNumbers() == false) {
          final MessageBox message = new MessageBox(AbstractEditPrimitiveDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("EditPrimitiveDialog.23")); //$NON-NLS-1$
          message.setText(Messages.getString("EditPrimitiveDialog.24")); //$NON-NLS-1$
          message.open();
          return;
        }

        updatePrimitiveParameters();
        AbstractEditPrimitiveDialog.this.tree.updateTree();
        AbstractEditPrimitiveDialog.this.modeler.updateDisplay();
        AbstractEditPrimitiveDialog.this.sShell.close();
      }
    });

    final Button cancelButton = new Button(comp, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("EditPrimitiveDialog.25")); //$NON-NLS-1$
    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (AbstractEditPrimitiveDialog.this.isChanged() == false) {
          AbstractEditPrimitiveDialog.this.sShell.close();
          return;
        }
        
        final MessageBox message = new MessageBox(AbstractEditPrimitiveDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        message.setMessage(Messages.getString("EditPrimitiveDialog.26")); //$NON-NLS-1$
        final int yesNo = message.open();
        if (yesNo == SWT.YES) {
          AbstractEditPrimitiveDialog.this.sShell.close();
        }
      }
    });
    
    final Button applyButton = new Button(comp, SWT.NONE);
    applyButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    applyButton.setText(Messages.getString("EditPrimitiveDialog.11")); //$NON-NLS-1$
    applyButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        updatePrimitiveParameters();
        AbstractEditPrimitiveDialog.this.tree.updateTree();
        AbstractEditPrimitiveDialog.this.modeler.updateDisplay();
      }

    });
  }

  
  /**
   * ダイアログにパラメータを設定します。
   */
  void setParametersInDialog() {
    final RotationModel rotation = this.primitive.getRotation();
    final TranslationModel translation = this.primitive.getTranslation();
    if (rotation != null) {
      setRotationInDialog(rotation);
    }
    if (translation != null) {
      setTranslationInDialog(translation);
    }
    
    this.colorSelector.setColor(this.primitive.getColor());
    
    setParametersInBoxes();
  }
  
  /**
   * 角度をダイアログに設定します。
   * 
   * @param rotation 角度
   */
  private void setRotationInDialog(RotationModel rotation) {
    this.rotationX.setStringValue("" + rotation.getX()); //$NON-NLS-1$
    this.rotationY.setStringValue("" + rotation.getY()); //$NON-NLS-1$
    this.rotationZ.setStringValue("" + rotation.getZ()); //$NON-NLS-1$
  }

  /**
   * 並進量をダイアログに設定します。
   * 
   * @param translation 並進量
   */
  private void setTranslationInDialog(TranslationModel translation) {
    this.translationX.setStringValue("" + translation.getX()); //$NON-NLS-1$
    this.translationY.setStringValue("" + translation.getY()); //$NON-NLS-1$
    this.translationZ.setStringValue("" + translation.getZ()); //$NON-NLS-1$
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
    if (this.parameter1.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.parameter2.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.parameter3.isVisible() && this.parameter3.containsOnlyNumbers() == false) {
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
   * パラメータが変更されているか判定します。
   * 
   * @return パラメータが変更されていればtrue
   */
  public boolean isChanged() {
    if (this.colorSelector.isChanged) {
      return true;
    }
    if (this.translationX.isChanged()) {
      return true;
    }
    if (this.translationY.isChanged()) {
      return true;
    }
    if (this.translationZ.isChanged()) {
      return true;
    }
    if (this.rotationX.isChanged()) {
      return true;
    }
    if (this.rotationY.isChanged()) {
      return true;
    }
    if (this.rotationZ.isChanged()) {
      return true;
    }
    
    return false;
  }
}
