/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.swt.gui.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.SourceModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.swt.gui.JoglModeler;
import org.mklab.mikity.swt.gui.ParameterInputBox;
import org.mklab.mikity.swt.gui.SceneGraphTree;


/**
 * グループを編集するエディタを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/03
 */
public class GroupEditor implements ModelEditor, ModifyKeyListener  {
  private GroupModel targetGroup;
  JoglModeler modeler;
  SceneGraphTree tree;
    
  /** 保存ボタン。 */
  private ParameterInputBox groupName;
  
  private ParameterInputBox translationX;
  private ParameterInputBox translationY;
  private ParameterInputBox translationZ;
  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;

  private ParameterInputBox translationXsourceId;
  private ParameterInputBox translationYsourceId;
  private ParameterInputBox translationZsourceId;
  private ParameterInputBox translationXsourceNumber;
  private ParameterInputBox translationYsourceNumber;
  private ParameterInputBox translationZsourceNumber;

  private ParameterInputBox rotationXsourceId;
  private ParameterInputBox rotationYsourceId;
  private ParameterInputBox rotationZsourceId;
  private ParameterInputBox rotationXsourceNumber;
  private ParameterInputBox rotationYsourceNumber;
  private ParameterInputBox rotationZsourceNumber;
  
  /** 保存ボタン。 */
  private Button saveButton;
  /** 値が変更されていればtrue。 */
  boolean isChanged = false;
  
  /** 値を変更可能ならばtrue。 */
  private boolean editable;
  
  /**
   * 新しく生成された<code>EditGroupDialog</code>オブジェクトを初期化します。
   * @param parent 親のシェル
   * @param targetGroup グループ
   * @param editable 編集可能性
   * @param tree ツリー
   * @param modeler モデラー
   */
  public GroupEditor(Composite parent, GroupModel targetGroup, boolean editable, SceneGraphTree tree, JoglModeler modeler) {
    this.targetGroup = targetGroup;
    this.editable = editable;
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
    layout.numColumns = 3;
    parent.setLayout(layout);

    this.groupName = new ParameterInputBox(parent, this, SWT.LEFT, Messages.getString("GroupConfigDialogLink.1"), "root"); //$NON-NLS-1$ //$NON-NLS-2$
    this.groupName.setTextWidth(150);

    if (this.targetGroup.getName() != null) {
      this.groupName.setValue(this.targetGroup.getName());
    }

    createCoordinateParameterBoxes(parent);
    
    createAnimationParameterBoxes(parent);

    createButtonComposite(parent);
    
    this.modeler.setFocus();
  }

  /**
   * 座標系のパラメータを設定するボックスを生成します。
   */
  private void createCoordinateParameterBoxes(Composite parent) {
    final Group group = new Group(parent, SWT.NONE);
    group.setText(Messages.getString("GroupConfigDialogLink.11")); //$NON-NLS-1$

    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    group.setLayout(layout);

    final GridData gridData0 = new GridData(GridData.FILL_HORIZONTAL);
    gridData0.widthHint = 80;
    gridData0.horizontalSpan = 2;
    group.setLayoutData(gridData0);
    
    final Label label1 = new Label(group, SWT.CENTER);
    label1.setText(Messages.getString("GroupConfigDialogLink.12")); //$NON-NLS-1$
    final GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
    label1.setLayoutData(gridData1);

    final Label label2 = new Label(group, SWT.CENTER);
    label2.setText(Messages.getString("GroupConfigDialogLink.13")); //$NON-NLS-1$
    final GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
    gridData2.widthHint = 65;
    label2.setLayoutData(gridData2);
    
    createCoordinateTranslationBoxes(group);
    createCoordinateRotationBoxes(group);
  }

  void createCoordinateRotationBoxes(final Group group) {
    int style;
    if (this.editable == true) {
      style = SWT.NONE;
    } else {
      style = SWT.READ_ONLY;
    }
    
    final RotationModel rotation = this.targetGroup.getRotation();
    final String x, y, z;
    
    if (rotation != null) {
      x = "" + rotation.getX(); //$NON-NLS-1$
      y = "" + rotation.getY(); //$NON-NLS-1$
      z = "" + rotation.getZ(); //$NON-NLS-1$
    } else {
      x = "0"; //$NON-NLS-1$
      y = "0"; //$NON-NLS-1$
      z = "0"; //$NON-NLS-1$
    }

    this.rotationX = new ParameterInputBox(group, this, style, Messages.getString("EditGroupDialog.3"), x);  //$NON-NLS-1$
    this.rotationY = new ParameterInputBox(group, this, style, Messages.getString("EditGroupDialog.4"), y);  //$NON-NLS-1$
    this.rotationZ = new ParameterInputBox(group, this, style, Messages.getString("EditGroupDialog.5"), z);  //$NON-NLS-1$
  }

  void createCoordinateTranslationBoxes(final Group group) {
    int style;
    if (this.editable == true) {
      style = SWT.NONE;
    } else {
      style = SWT.READ_ONLY;
    }
    
    final TranslationModel translation = this.targetGroup.getTranslation();
    final String x, y, z;
    
    if (translation != null) {
      x = "" + translation.getX(); //$NON-NLS-1$
      y = "" + translation.getY(); //$NON-NLS-1$
      z = "" + translation.getZ(); //$NON-NLS-1$
    } else {
      x = "0"; //$NON-NLS-1$
      y = "0"; //$NON-NLS-1$
      z = "0"; //$NON-NLS-1$
    }
   
    this.translationX = new ParameterInputBox(group, this, style, Messages.getString("EditGroupDialog.0"), x);  //$NON-NLS-1$
    this.translationY = new ParameterInputBox(group, this, style, Messages.getString("EditGroupDialog.1"), y);  //$NON-NLS-1$
    this.translationZ = new ParameterInputBox(group, this, style, Messages.getString("EditGroupDialog.2"), z);  //$NON-NLS-1$
  }
  
  /**
   * アニメーションのパラメータを設定するボックスを生成します。
   */
  private void createAnimationParameterBoxes(Composite parent) {
    final Group parameters = new Group(parent, SWT.NONE);
    parameters.setText(Messages.getString("EditGroupDialog.8")); //$NON-NLS-1$
    
    final GridLayout layout = new GridLayout();
    layout.numColumns = 3;
    parameters.setLayout(layout);
    
    final GridData gridData0 = new GridData(GridData.FILL_HORIZONTAL);
    gridData0.horizontalSpan = 3;
    gridData0.widthHint = 80;
    parameters.setLayoutData(gridData0);

    final Label name = new Label(parameters, SWT.CENTER);
    name.setText(Messages.getString("GroupConfigDialogLink.12")); //$NON-NLS-1$
    final GridData nameGridData = new GridData(GridData.FILL_HORIZONTAL);
    name.setLayoutData(nameGridData);

    final Label id = new Label(parameters, SWT.CENTER);
    id.setText(Messages.getString("EditGroupDialog.7")); //$NON-NLS-1$
    final GridData idGridData = new GridData(GridData.FILL_HORIZONTAL);
    idGridData.widthHint = 30;
    id.setLayoutData(idGridData);

    final Label number = new Label(parameters, SWT.CENTER);
    number.setText(Messages.getString("GroupConfigDialogLink.14")); //$NON-NLS-1$
    final GridData numberGridData = new GridData(GridData.FILL_HORIZONTAL);
    numberGridData.widthHint = 30;
    number.setLayoutData(numberGridData);

    createAnimationTranslationBoxes(parameters);
    createAnimationRotationBoxes(parameters);

    setAnimationInDialog();
  }

  void createAnimationRotationBoxes(final Group parameters) {
    int style;
    if (this.editable == true) {
      style = SWT.NONE;
    } else {
      style = SWT.READ_ONLY;
    }
    
    this.rotationXsourceId = new ParameterInputBox(parameters, this, style, Messages.getString("EditGroupDialog.3"), 0); //$NON-NLS-1$
    this.rotationXsourceNumber = new ParameterInputBox(parameters, this, style, 0);

    this.rotationYsourceId = new ParameterInputBox(parameters, this, style, Messages.getString("EditGroupDialog.4"), 0); //$NON-NLS-1$
    this.rotationYsourceNumber = new ParameterInputBox(parameters, this, style, 0);

    this.rotationZsourceId = new ParameterInputBox(parameters, this, style, Messages.getString("EditGroupDialog.5"), 0); //$NON-NLS-1$
    this.rotationZsourceNumber = new ParameterInputBox(parameters, this, style, 0);
  }

  void createAnimationTranslationBoxes(final Group parameters) {
    int style;
    if (this.editable == true) {
      style = SWT.NONE;
    } else {
      style = SWT.READ_ONLY;
    }
    
    this.translationXsourceId = new ParameterInputBox(parameters, this, style, Messages.getString("EditGroupDialog.0"), 0); //$NON-NLS-1$
    this.translationXsourceNumber = new ParameterInputBox(parameters, this, style, 0);

    this.translationYsourceId = new ParameterInputBox(parameters, this, style, Messages.getString("EditGroupDialog.1"), 0); //$NON-NLS-1$
    this.translationYsourceNumber = new ParameterInputBox(parameters, this, style, 0);
    
    this.translationZsourceId = new ParameterInputBox(parameters, this, style, Messages.getString("EditGroupDialog.2"), 0); //$NON-NLS-1$
    this.translationZsourceNumber = new ParameterInputBox(parameters, this, style, 0);
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
        this.translationXsourceId.setValue(id);
        this.translationXsourceNumber.setValue(number);
      } else if (target.equals("translationY")) { //$NON-NLS-1$
        this.translationYsourceId.setValue(id);
        this.translationYsourceNumber.setValue(number);
      } else if (target.equals("translationZ")) { //$NON-NLS-1$
        this.translationZsourceId.setValue(id);
        this.translationZsourceNumber.setValue(number);
      } else if (target.equals("rotationX")) { //$NON-NLS-1$
        this.rotationXsourceId.setValue(id);
        this.rotationXsourceNumber.setValue(number);
      } else if (target.equals("rotationY")) { //$NON-NLS-1$
        this.rotationYsourceId.setValue(id);
        this.rotationYsourceNumber.setValue(number);
      } else if (target.equals("rotationZ")) { //$NON-NLS-1$
        this.rotationZsourceId.setValue(id);        
        this.rotationZsourceNumber.setValue(number);
      }
    }
  }

  private void createButtonComposite(final Composite parent) {
    final Composite composite = new Composite(parent, SWT.NONE);
    
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 3;
    composite.setLayoutData(gridData);

    final GridLayout layout = new GridLayout(1, true);
    composite.setLayout(layout);
  
    this.saveButton = new Button(parent, SWT.NONE);
    this.saveButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    this.saveButton.setText(Messages.getString("EditGroupDialog.6")); //$NON-NLS-1$
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
      showAlertMessageInDialog();
      return;
    }
    
    updateModelParameters();
    this.tree.updateTree();

    this.modeler.setIsChanged(this.modeler.isChanged() || isChanged());
    this.modeler.updateDisplay();
    
    this.saveButton.setEnabled(false);
  }

  private void showAlertMessageInDialog() {
    final MessageBox message = new MessageBox(this.modeler.getShell(), SWT.ICON_WARNING);
    message.setMessage(Messages.getString("EditPrimitiveDialog.23")); //$NON-NLS-1$
    message.setText(Messages.getString("EditPrimitiveDialog.24")); //$NON-NLS-1$
    message.open();
    return;
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
  public boolean isChanged() {
    return this.isChanged;
  }

  /**
   * {@inheritDoc}
   */
  public void modifyText(ModifyEvent e) {
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

  /**
   * {@inheritDoc}
   */
  public void updateEditor() {
    // nothing to do
  }
}