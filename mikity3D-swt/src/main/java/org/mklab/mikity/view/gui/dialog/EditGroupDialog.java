/*
 * Created on 2005/02/03
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.SourceModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;


/**
 * グループの設定を行うダイアログを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/03
 */
public class EditGroupDialog implements EditModelDialog {
  Shell sShell = null;
  private Shell parentShell = null;
  GroupModel targetGroup;
  
  ParameterInputBox groupName;
  
  ParameterInputBox translationX;
  ParameterInputBox translationY;
  ParameterInputBox translationZ;
  ParameterInputBox rotationX;
  ParameterInputBox rotationY;
  ParameterInputBox rotationZ;

  ParameterInputBox translationXsourceId;
  ParameterInputBox translationYsourceId;
  ParameterInputBox translationZsourceId;
  ParameterInputBox rotationXsourceId;
  ParameterInputBox rotationYsourceId;
  ParameterInputBox rotationZsourceId;

  ParameterInputBox translationXsourceNumber;
  ParameterInputBox translationYsourceNumber;
  ParameterInputBox translationZsourceNumber;
  ParameterInputBox rotationXsourceNumber;
  ParameterInputBox rotationYsourceNumber;
  ParameterInputBox rotationZsourceNumber;

  private boolean editable;
  //private Label statusLabel;
  
  JoglModeler modeler;
  SceneGraphTree tree;

  /**
   * 新しく生成された<code>EditGroupDialog</code>オブジェクトを初期化します。
   * @param parentShell 親のシェル
   * @param targetGroup グループ
   * @param editable 編集可能性
   * @param tree ツリー
   * @param modeler モデラー
   */
  public EditGroupDialog(Shell parentShell, GroupModel targetGroup, boolean editable, SceneGraphTree tree, JoglModeler modeler) {
    this.parentShell = parentShell;
    this.targetGroup = targetGroup;
    this.editable = editable;
    this.tree = tree;
    this.modeler = modeler;
    
    this.tree.setIsModifyingObject(true);
    
    createSShell();
  }

  /**
   * シェルを生成します。
   */
  private void createSShell() {
    // SWT.APPLICATION_MODAL→このシェルを閉じないと、親シェルを編集できなくする
    //this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(450, 420));
    this.sShell.setText(Messages.getString("GroupConfigDialogLink.0")); //$NON-NLS-1$
    final GridLayout layout = new GridLayout();
    layout.numColumns = 3;
    this.sShell.setLayout(layout);

    this.groupName = new ParameterInputBox(this.sShell, SWT.LEFT, Messages.getString("GroupConfigDialogLink.1"), "root"); //$NON-NLS-1$ //$NON-NLS-2$
    this.groupName.setTextWidth(150);

    if (this.targetGroup.getName() != null) {
      this.groupName.setStringValue(this.targetGroup.getName());
      this.groupName.setIsChanged(false);
    }
    
    addShellListener();

    createParameterBoxes();

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
        EditGroupDialog.this.tree.setIsModifyingObject(false);
      }
      
      public void shellActivated(ShellEvent arg0) {
        // nothing to do
      }
    });
  }

  /**
   * パラメータを設定するボックスを生成します。
   */
  private void createParameterBoxes() {
    final Group parameterGroup = new Group(this.sShell, SWT.NONE);
    final GridLayout layout = new GridLayout();
    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.horizontalSpan = 4;
    layout.numColumns = 4;
    parameterGroup.setText(Messages.getString("GroupConfigDialogLink.11")); //$NON-NLS-1$
    parameterGroup.setLayout(layout);
    parameterGroup.setLayoutData(data);

    final GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
    data.widthHint = 80;
    final Label label1 = new Label(parameterGroup, SWT.CENTER);
    label1.setText(Messages.getString("GroupConfigDialogLink.12")); //$NON-NLS-1$
    label1.setLayoutData(gridData1);

    final GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
    gridData2.widthHint = 65;
    final Label label2 = new Label(parameterGroup, SWT.CENTER);
    label2.setText(Messages.getString("GroupConfigDialogLink.13")); //$NON-NLS-1$
    label2.setLayoutData(gridData2);

    final GridData gridData3 = new GridData(GridData.FILL_HORIZONTAL);
    gridData3.widthHint = 30;
    final Label label3 = new Label(parameterGroup, SWT.CENTER);
    label3.setText(Messages.getString("EditGroupDialog.7")); //$NON-NLS-1$
    label3.setLayoutData(gridData3);

    final GridData gridData4 = new GridData(GridData.FILL_HORIZONTAL);
    gridData4.widthHint = 30;
    final Label label4 = new Label(parameterGroup, SWT.CENTER);
    label4.setText(Messages.getString("GroupConfigDialogLink.14")); //$NON-NLS-1$
    label4.setLayoutData(gridData4);

    int style;
    if (this.editable == true) {
      style = SWT.NONE;
    } else {
      style = SWT.READ_ONLY;
    }

    final TranslationModel translation = this.targetGroup.getTranslation();
    final String tX, tY, tZ;
    
    if (translation != null) {
      tX = "" + translation.getX(); //$NON-NLS-1$
      tY = "" + translation.getY(); //$NON-NLS-1$
      tZ = "" + translation.getZ(); //$NON-NLS-1$
    } else {
      tX = "0"; //$NON-NLS-1$
      tY = "0"; //$NON-NLS-1$
      tZ = "0"; //$NON-NLS-1$
    }

    final RotationModel rotation = this.targetGroup.getRotation();
    final String rX, rY, rZ;
    
    if (rotation != null) {
      rX = "" + rotation.getX(); //$NON-NLS-1$
      rY = "" + rotation.getY(); //$NON-NLS-1$
      rZ = "" + rotation.getZ(); //$NON-NLS-1$
    } else {
      rX = "0"; //$NON-NLS-1$
      rY = "0"; //$NON-NLS-1$
      rZ = "0"; //$NON-NLS-1$
    }

    this.translationX = new ParameterInputBox(parameterGroup, style, Messages.getString("EditGroupDialog.0"), tX);  //$NON-NLS-1$
    this.translationXsourceId = new ParameterInputBox(parameterGroup, style, 0);
    this.translationXsourceNumber = new ParameterInputBox(parameterGroup, style, 0);

    this.translationY = new ParameterInputBox(parameterGroup, style, Messages.getString("EditGroupDialog.1"), tY);  //$NON-NLS-1$
    this.translationYsourceId = new ParameterInputBox(parameterGroup, style, 0);
    this.translationYsourceNumber = new ParameterInputBox(parameterGroup, style, 0);
    
    this.translationZ = new ParameterInputBox(parameterGroup, style, Messages.getString("EditGroupDialog.2"), tZ);  //$NON-NLS-1$
    this.translationZsourceId = new ParameterInputBox(parameterGroup, style, 0);
    this.translationZsourceNumber = new ParameterInputBox(parameterGroup, style, 0);

    this.rotationX = new ParameterInputBox(parameterGroup, style, Messages.getString("EditGroupDialog.3"), rX);  //$NON-NLS-1$
    this.rotationXsourceId = new ParameterInputBox(parameterGroup, style, 0);
    this.rotationXsourceNumber = new ParameterInputBox(parameterGroup, style, 0);

    this.rotationY = new ParameterInputBox(parameterGroup, style, Messages.getString("EditGroupDialog.4"), rY);  //$NON-NLS-1$
    this.rotationYsourceId = new ParameterInputBox(parameterGroup, style, 0);
    this.rotationYsourceNumber = new ParameterInputBox(parameterGroup, style, 0);

    this.rotationZ = new ParameterInputBox(parameterGroup, style, Messages.getString("EditGroupDialog.5"), rZ);  //$NON-NLS-1$
    this.rotationZsourceId = new ParameterInputBox(parameterGroup, style, 0);
    this.rotationZsourceNumber = new ParameterInputBox(parameterGroup, style, 0);

    setAnimationInDialog();
  }

  /**
   * アニメーションの 番号を表示させる
   */
  private void setAnimationInDialog() {
    final AnimationModel[] animations = this.targetGroup.getAnimations();

    for (final AnimationModel animation : animations) {
      final String target = animation.getTarget();
      final SourceModel source = animation.getSource();
      final String id = source.getId();
      final String number = "" + source.getNumber(); //$NON-NLS-1$

      if (target.equals("translationX")) { //$NON-NLS-1$
        this.translationXsourceId.setStringValue(id);
        this.translationXsourceNumber.setStringValue(number);
        
        this.translationXsourceId.setIsChanged(false);
        this.translationXsourceNumber.setIsChanged(false);
      } else if (target.equals("translationY")) { //$NON-NLS-1$
        this.translationYsourceId.setStringValue(id);
        this.translationYsourceNumber.setStringValue(number);
        
        this.translationYsourceId.setIsChanged(false);
        this.translationYsourceNumber.setIsChanged(false);
      } else if (target.equals("translationZ")) { //$NON-NLS-1$
        this.translationZsourceId.setStringValue(id);
        this.translationZsourceNumber.setStringValue(number);
        
        this.translationZsourceId.setIsChanged(false);
        this.translationZsourceNumber.setIsChanged(false);
      } else if (target.equals("rotationX")) { //$NON-NLS-1$
        this.rotationXsourceId.setStringValue(id);
        this.rotationXsourceNumber.setStringValue(number);
        
        this.rotationXsourceId.setIsChanged(false);
        this.rotationXsourceNumber.setIsChanged(false);
      } else if (target.equals("rotationY")) { //$NON-NLS-1$
        this.rotationYsourceId.setStringValue(id);
        this.rotationYsourceNumber.setStringValue(number);
        
        this.rotationYsourceId.setIsChanged(false);
        this.rotationYsourceNumber.setIsChanged(false);
      } else if (target.equals("rotationZ")) { //$NON-NLS-1$
        this.rotationZsourceId.setStringValue(id);        
        this.rotationZsourceNumber.setStringValue(number);
        
        this.rotationZsourceId.setIsChanged(false);
        this.rotationZsourceNumber.setIsChanged(false);
      }
    }
  }

  private void createButtonComposite() {
    final Composite composite = new Composite(this.sShell, SWT.NONE);
    
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 3;
    composite.setLayoutData(gridData);

    final GridLayout compLayout = new GridLayout(2, true);
    composite.setLayout(compLayout);
  
    final Button applyButton = new Button(this.sShell, SWT.NONE);
    applyButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    applyButton.setText(Messages.getString("EditGroupDialog.6")); //$NON-NLS-1$
    applyButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        EditGroupDialog.this.modeler.setChanged(EditGroupDialog.this.isChanged());
        
        updateModelParameters();
        EditGroupDialog.this.tree.updateTree();
        EditGroupDialog.this.modeler.updateDisplay();
      }

    });
    
    final Button closeButton = new Button(this.sShell, SWT.NONE);
    closeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    closeButton.setText(Messages.getString("GroupConfigDialogLink.9")); //$NON-NLS-1$
    closeButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (EditGroupDialog.this.isChanged() == false) {
          EditGroupDialog.this.sShell.close();
          return;
        }

        final MessageBox message = new MessageBox(EditGroupDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        message.setMessage(Messages.getString("EditPrimitiveDialog.26")); //$NON-NLS-1$
        final int yesNo = message.open();
        if (yesNo == SWT.YES) {
          EditGroupDialog.this.sShell.close();
        }
        
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  public void updateModelParameters() {
    this.targetGroup.setName(this.groupName.getStringValue());
    this.targetGroup.clearAnimations();
    addAnimation("translationX", this.translationXsourceId, this.translationXsourceNumber); //$NON-NLS-1$
    addAnimation("translationY", this.translationYsourceId, this.translationYsourceNumber); //$NON-NLS-1$
    addAnimation("translationZ", this.translationZsourceId, this.translationZsourceNumber); //$NON-NLS-1$
    addAnimation("rotationX", this.rotationXsourceId, this.rotationXsourceNumber); //$NON-NLS-1$
    addAnimation("rotationY", this.rotationYsourceId, this.rotationYsourceNumber); //$NON-NLS-1$
    addAnimation("rotationZ", this.rotationZsourceId, this.rotationZsourceNumber); //$NON-NLS-1$
    
    final TranslationModel translation = new TranslationModel(this.translationX.getFloatValue(), this.translationY.getFloatValue(), this.translationZ.getFloatValue());
    final RotationModel rotation = new RotationModel(this.rotationX.getFloatValue(), this.rotationY.getFloatValue(), this.rotationZ.getFloatValue());
    
    this.targetGroup.setTranslation(translation);
    this.targetGroup.setRotation(rotation);
  }


  /**
   * Animationを追加します。
   * 
   * @param parameterName パラメータの名前
   * @param sourceNumber データの番号
   */
  void addAnimation(final String parameterName, final ParameterInputBox sourceId, final ParameterInputBox sourceNumber) {
    if (sourceNumber.getIntValue() != 0) {
      final SourceModel source = new SourceModel();
      source.setId(sourceId.getStringValue());
      source.setNumber(sourceNumber.getIntValue());
      final AnimationModel animation = new AnimationModel();
      animation.setTarget(parameterName);
      animation.setSource(source);
      this.targetGroup.add(animation);
    }
  }

  /**
   * 数字のみが入力されているか判定します。
   * 
   * @return 数字のみが入力されていればtrue，そうでなければfalse
   */
  boolean containsOnlyNumbers() {
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
    if (this.translationXsourceNumber.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationYsourceNumber.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationZsourceNumber.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationXsourceNumber.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationYsourceNumber.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationZsourceNumber.containsOnlyNumbers() == false) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void open() {
    this.sShell.open();
//    final Display display = this.sShell.getDisplay();
//    
//    while (!this.sShell.isDisposed()) {
//      if (!display.readAndDispatch()) {
//        display.sleep();
//      }
//    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isChanged() {
    if (this.groupName.isChanged()) {
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

    if (this.translationXsourceId.isChanged()) {
      return true;
    }
    if (this.translationYsourceId.isChanged()) {
      return true;
    }
    if (this.translationZsourceId.isChanged()) {
      return true;
    }
    if (this.rotationXsourceId.isChanged()) {
      return true;
    }
    if (this.rotationYsourceId.isChanged()) {
      return true;
    }
    if (this.rotationZsourceId.isChanged()) {
      return true;
    }

    if (this.translationXsourceNumber.isChanged()) {
      return true;
    }
    if (this.translationYsourceNumber.isChanged()) {
      return true;
    }
    if (this.translationZsourceNumber.isChanged()) {
      return true;
    }
    if (this.rotationXsourceNumber.isChanged()) {
      return true;
    }
    if (this.rotationYsourceNumber.isChanged()) {
      return true;
    }
    if (this.rotationZsourceNumber.isChanged()) {
      return true;
    }
    
    return false;
  }

}