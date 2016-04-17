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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.config.BackgroundModel;
import org.mklab.mikity.model.xml.simplexml.config.BaseCoordinateModel;
import org.mklab.mikity.model.xml.simplexml.config.DataUnitModel;
import org.mklab.mikity.model.xml.simplexml.config.EyeModel;
import org.mklab.mikity.model.xml.simplexml.config.LightModel;
import org.mklab.mikity.model.xml.simplexml.config.LookAtPointModel;
import org.mklab.mikity.model.xml.simplexml.config.ModelUnitModel;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.Messages;
import org.mklab.mikity.swt.gui.JoglModeler;
import org.mklab.mikity.swt.gui.ParameterInputBox;


/**
 * Configurationの設定を編集するエディタを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/01
 */
public class ConfigurationEditor implements ModifyKeyListener {
  Shell sShell = null;
  private ColorSelectorButton colorSelector;

  private Button floorCheck;
  private Button shadowCheck;
  private Button baseAxisCheck;
  private Button gridCheck;
  
  private Combo modelLengthUnitCombo;
  private Combo modelAngleUnitCombo;
  private Combo dataLengthUnitCombo;
  private Combo dataAngleUnitCombo;

  private ParameterInputBox lightX;
  private ParameterInputBox lightY;
  private ParameterInputBox lightZ;

  private ParameterInputBox eyeX;
  private ParameterInputBox eyeY;
  private ParameterInputBox eyeZ;

  private ParameterInputBox lookAtPointX;
  private ParameterInputBox lookAtPointY;
  private ParameterInputBox lookAtPointZ;

  private ConfigurationModel configuration;
  
  JoglModeler modeler;
  
  /** 値が変更されていればtrue。 */
  private boolean isChanged = false;
  /** 保存ボタン。 */
  Button saveButton;

  /**
   * 新しく生成された<code>ConfigurationDialog</code>オブジェクトを初期化します。
   * @param parentShell 親シェル
   * @param configuration 設定
   * @param modeler モデラー
   */
  public ConfigurationEditor(Shell parentShell, ConfigurationModel configuration, JoglModeler modeler) {
    this.configuration = configuration;
    this.modeler = modeler;

    this.sShell = new Shell(parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    this.sShell.setText("Configuration Dialog"); //$NON-NLS-1$
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 590));

    final GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    this.sShell.setLayout(layout);

    createEditGroup(this.sShell);
    createMainButtonComposite(this.sShell);
  }

  /**
   * 編集用のグループを生成します。
   */
  private void createEditGroup(Shell parent) {
    final Group editGroup = new Group(parent, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    editGroup.setLayout(layout);
    final GridData data = new GridData(GridData.FILL_BOTH);
    editGroup.setLayoutData(data);
    editGroup.setText(Messages.getString("ConfigDialog.0")); //$NON-NLS-1$

    createEye(editGroup);
    
    createLookAtPoint(editGroup);
    
    createLightGroup(editGroup);
    
    createBaseCoordinate(editGroup);
    
    createModelUnit(editGroup);
    
    createDataUnit(editGroup);

    createBackground(editGroup);

    setParametersToDialog();
  }

  /**
   * 背景色を設定するグループを生成します。
   * 
   * @param editGroup 親
   */
  private void createBackground(final Group editGroup) {
    final Label label = new Label(editGroup, SWT.NONE);
    label.setText(Messages.getString("ConfigDialog.13")); //$NON-NLS-1$
    createColorSelectorButton(editGroup);
  }
  
  /**
   * 絶対座標を設定するグループを生成します。
   * 
   * @param editGroup 親
   */   
  private void createBaseCoordinate(final Group editGroup) {
    final Group group = new Group(editGroup, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 11;
    group.setText(Messages.getString("ConfigurationEditor.1")); //$NON-NLS-1$
    
    final GridData unitData = new GridData(GridData.FILL_HORIZONTAL);
    unitData.horizontalSpan = 2;
    group.setLayoutData(unitData);
    group.setLayout(layout);
    
    createFloorCombo(group);
    
    final Label space = new Label(group, SWT.NONE);
    final GridData spaceData = new GridData();
    spaceData.widthHint = 30;
    space.setLayoutData(spaceData);
    
    createShadowCombo(group);
    
    final Label space2 = new Label(group, SWT.NONE);
    final GridData spaceData2 = new GridData();
    spaceData2.widthHint = 30;
    space2.setLayoutData(spaceData2);
    
    createGridCombo(group);
    
    final Label space3 = new Label(group, SWT.NONE);
    final GridData spaceData3 = new GridData();
    spaceData3.widthHint = 30;
    space3.setLayoutData(spaceData3);

    createBaseAxisCombo(group);
  }
  
  private void createFloorCombo(final Group editGroup) {
    final BaseCoordinateModel baseCoordinate = this.configuration.getBaseCoordinate();
    
    final Label floorLabel = new Label(editGroup, SWT.NONE);
    floorLabel.setText(Messages.getString("ConfigurationEditor.2")); //$NON-NLS-1$
    this.floorCheck = new Button(editGroup, SWT.CHECK);
    this.floorCheck.setSelection(baseCoordinate.isFloorDrawing());
    this.floorCheck.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // nothing to do
      }

      @Override
      public void widgetSelected(SelectionEvent e) {
        modifyText(null);
      }
    });
  }
  
  private void createShadowCombo(final Group editGroup) {
    final BaseCoordinateModel baseCoordinate = this.configuration.getBaseCoordinate();
    
    final Label shadowLabel = new Label(editGroup, SWT.NONE);
    shadowLabel.setText(Messages.getString("ConfigurationEditor.3")); //$NON-NLS-1$
    this.shadowCheck = new Button(editGroup, SWT.CHECK);
    this.shadowCheck.setSelection(baseCoordinate.isShadowDrawing());
    this.shadowCheck.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // nothing to do
      }

      @Override
      public void widgetSelected(SelectionEvent e) {
        modifyText(null);
      }
    });
  }

  private void createBaseAxisCombo(final Group editGroup) {
    final BaseCoordinateModel baseCoordinate = this.configuration.getBaseCoordinate();
    
    final Label baseAxisLabel = new Label(editGroup, SWT.NONE);
    baseAxisLabel.setText(Messages.getString("ConfigDialog.18")); //$NON-NLS-1$
    this.baseAxisCheck = new Button(editGroup, SWT.CHECK);
    this.baseAxisCheck.setSelection(baseCoordinate.isAxisShowing());
    this.baseAxisCheck.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // nothing to do
      }

      @Override
      public void widgetSelected(SelectionEvent e) {
        modifyText(null);
      }
    });
  }
  
  private void createGridCombo(final Group editGroup) {
    final BaseCoordinateModel baseCoordinate = this.configuration.getBaseCoordinate();
    
    final Label gridLabel = new Label(editGroup, SWT.NONE);
    gridLabel.setText(Messages.getString("ConfigurationEditor.0")); //$NON-NLS-1$
    this.gridCheck = new Button(editGroup, SWT.CHECK);
    this.gridCheck.setSelection(baseCoordinate.isGridShowing());
    this.gridCheck.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // nothing to do
      }

      @Override
      public void widgetSelected(SelectionEvent e) {
        modifyText(null);
      }
    });
  }

  /**
   * ソースデータの単位を設定するグループを生成します。
   * 
   * @param parent 親
   */
  private void createDataUnit(Group parent) {
    final Group group = new Group(parent, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 5;
    group.setText(Messages.getString("ConfigDialog.5")); //$NON-NLS-1$
    
    final GridData unitData = new GridData(GridData.FILL_HORIZONTAL);
    unitData.horizontalSpan = 2;
    group.setLayoutData(unitData);
    group.setLayout(layout);

    createDataLengthUnitCombo(group);
    
    final Label space = new Label(group, SWT.NONE);
    final GridData spaceData = new GridData();
    spaceData.widthHint = 60;
    space.setLayoutData(spaceData);
    
    createDataAngleUnitCombo(group);
  }

  /**
   * モデルの単位を設定するグループを生成しまうｓ。
   * 
   * @param parent 親
   */
  private void createModelUnit(Group parent) {
    final Group group = new Group(parent, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 5;
    group.setText(Messages.getString("ConfigDialog.2")); //$NON-NLS-1$
    
    final GridData unitData = new GridData(GridData.FILL_HORIZONTAL);
    unitData.horizontalSpan = 2;
    group.setLayoutData(unitData);
    group.setLayout(layout);

    createModelLengthUnit(group);
    
    final Label space = new Label(group, SWT.NONE);
    final GridData spaceData = new GridData();
    spaceData.widthHint = 60;
    space.setLayoutData(spaceData);
    
    createModelAngleUnit(group);
  }

  /**
   * 光源の位置を設定するグループを生成します。
   */
  private void createLightGroup(Group parent) {
    final Group group = new Group(parent, SWT.NONE);
    group.setText(Messages.getString("ConfigDialog.8")); //$NON-NLS-1$

    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.horizontalSpan = 2;
    final GridLayout layout = new GridLayout(7, true);
    group.setLayout(layout);
    group.setLayoutData(data);

    final LightModel light = this.configuration.getLight();
    
    this.lightX = new ParameterInputBox(group, this, SWT.NONE, "  (", "" + light.getX()); //$NON-NLS-1$ //$NON-NLS-2$
    this.lightY = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + light.getY()); //$NON-NLS-1$ //$NON-NLS-2$
    this.lightZ = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + light.getZ()); //$NON-NLS-1$ //$NON-NLS-2$

    final Label label = new Label(group, SWT.NONE);
    final GridData lightData = new GridData();
    lightData.widthHint = 10;
    label.setLayoutData(lightData);
    label.setText(")"); //$NON-NLS-1$
  }

  /**
   * 注視点の位置を設定するグループを生成します。
   */
  private void createLookAtPoint(Group parent) {
    final Group group = new Group(parent, SWT.NONE);
    group.setText(Messages.getString("ConfigDialog.11")); //$NON-NLS-1$

    final GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
    groupData.horizontalSpan = 2;
    final GridLayout layout = new GridLayout(7, true);
    group.setLayout(layout);
    group.setLayoutData(groupData);

    final LookAtPointModel lookAtPoint = this.configuration.getLookAtPoint();

    this.lookAtPointX = new ParameterInputBox(group, this, SWT.NONE, "  (", "" + lookAtPoint.getX()); //$NON-NLS-1$//$NON-NLS-2$
    this.lookAtPointY = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + lookAtPoint.getY()); //$NON-NLS-1$ //$NON-NLS-2$
    this.lookAtPointZ = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + lookAtPoint.getZ()); //$NON-NLS-1$ //$NON-NLS-2$

    final Label label = new Label(group, SWT.NONE);
    final GridData data = new GridData();
    label.setLayoutData(data);
    label.setText(")"); //$NON-NLS-1$
  }

  /**
   * 視点の位置を設定するグループを生成します。
   */
  private void createEye(Group parent) {
    final Group group = new Group(parent, SWT.NONE);
    group.setText(Messages.getString("ConfigDialog.12")); //$NON-NLS-1$

    final GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
    groupData.horizontalSpan = 2;
    final GridLayout layout = new GridLayout(7, true);
    group.setLayout(layout);
    group.setLayoutData(groupData);

    final EyeModel eye = this.configuration.getEye();
    
    this.eyeX = new ParameterInputBox(group, this, SWT.NONE, "  (", "" + eye.getX()); //$NON-NLS-1$ //$NON-NLS-2$
    this.eyeY = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + eye.getY()); //$NON-NLS-1$ //$NON-NLS-2$
    this.eyeZ = new ParameterInputBox(group, this, SWT.NONE, ",  ", "" + eye.getZ()); //$NON-NLS-1$ //$NON-NLS-2$

    final Label label = new Label(group, SWT.NONE);
    final GridData labelData = new GridData();
    label.setLayoutData(labelData);
    label.setText(")"); //$NON-NLS-1$
  }

  /**
   * XMLファイルからデータをダイアログに設定します。
   */
  private void setParametersToDialog() {
    this.modelAngleUnitCombo.setText(this.configuration.getModelUnit().getAngleUnit());
    this.modelLengthUnitCombo.setText(this.configuration.getModelUnit().getLengthUnit());

    this.dataAngleUnitCombo.setText(this.configuration.getDataUnit().getAngle());
    this.dataLengthUnitCombo.setText(this.configuration.getDataUnit().getLength());

    final ColorModel color = this.configuration.getBackground().getColor();
    this.colorSelector.setColor(color);
    this.baseAxisCheck.setSelection(this.configuration.getBaseCoordinate().isAxisShowing());
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
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        saveParameters();        
      }

    });
    this.saveButton.setEnabled(false);
    
    final Button closeButton = new Button(composite, SWT.NONE);
    closeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    closeButton.setText(Messages.getString("ConfigDialog.17")); //$NON-NLS-1$
    closeButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (ConfigurationEditor.this.isChanged() == false) {
          ConfigurationEditor.this.sShell.close();
          return;
        }
        
        final MessageBox message = new MessageBox(ConfigurationEditor.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        message.setMessage(Messages.getString("EditPrimitiveDialog.26")); //$NON-NLS-1$
        final int yesNo = message.open();
        if (yesNo == SWT.YES) {
         ConfigurationEditor.this.sShell.close();
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
    
    updateConfigurationParameters();
    this.modeler.updateDisplay();
    
    this.isChanged = false;
    this.saveButton.setEnabled(false);
  }

  /**
   * Configurationのパラメータを更新します。
   */
  void updateConfigurationParameters() {
    final LightModel light = new LightModel();
    light.setX(this.lightX.getFloatValue());
    light.setY(this.lightY.getFloatValue());
    light.setZ(this.lightZ.getFloatValue());
    this.configuration.setLight(light);

    final EyeModel eye = new EyeModel();
    eye.setX(this.eyeX.getFloatValue());
    eye.setY(this.eyeY.getFloatValue());
    eye.setZ(this.eyeZ.getFloatValue());
    this.configuration.setEye(eye);
    
    final BaseCoordinateModel baseCoordinate = new BaseCoordinateModel();
    baseCoordinate.setAxisShowing(this.baseAxisCheck.getSelection());
    baseCoordinate.setGridShowing(this.gridCheck.getSelection());
    baseCoordinate.setFloorDrawing(this.floorCheck.getSelection());
    baseCoordinate.setShadowDrawing(this.shadowCheck.getSelection());
    this.configuration.setBaseAxis(baseCoordinate);
    
    final LookAtPointModel lookAtPoint = new LookAtPointModel();
    lookAtPoint.setX(this.lookAtPointX.getFloatValue());
    lookAtPoint.setY(this.lookAtPointY.getFloatValue());
    lookAtPoint.setZ(this.lookAtPointZ.getFloatValue());
    this.configuration.setLookAtPoiint(lookAtPoint);

    if (this.configuration.getBackground() == null) {
      if (this.colorSelector.getColor().toString() != "white") { //$NON-NLS-1$
        final BackgroundModel background = new BackgroundModel();
        final ColorModel color = this.colorSelector.getColor();
        background.setColor(color);
        this.configuration.setBackground(background);
      }
    } else {
      final ColorModel color = this.colorSelector.getColor();
      this.configuration.getBackground().setColor(color);
    }

    if (this.configuration.getModelUnit() == null) {
      if (this.modelAngleUnitCombo.getText() != "radian") { //$NON-NLS-1$
        final ModelUnitModel modelUnit = new ModelUnitModel();
        modelUnit.setAngleUnit(this.modelAngleUnitCombo.getText());
        modelUnit.setLengthUnit(this.modelLengthUnitCombo.getText());
        this.configuration.setModelUnit(modelUnit);
      }
    } else {
      this.configuration.getModelUnit().setAngleUnit(this.modelAngleUnitCombo.getText());
      this.configuration.getModelUnit().setLengthUnit(this.modelLengthUnitCombo.getText());
    }

    if (this.configuration.getDataUnit() == null) {
      if (this.dataAngleUnitCombo.getText() != "radian") { //$NON-NLS-1$
        final DataUnitModel dataUnit = new DataUnitModel();
        dataUnit.setAngle(this.dataAngleUnitCombo.getText());
        dataUnit.setLength(this.dataLengthUnitCombo.getText());
        this.configuration.setDataUnit(dataUnit);
      }
    } else {
      this.configuration.getDataUnit().setAngle(this.dataAngleUnitCombo.getText());
      this.configuration.getDataUnit().setLength(this.dataLengthUnitCombo.getText());
    }

  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean containsOnlyNumbers() {
    if (this.lightX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.lightY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.lightZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.eyeX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.eyeY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.eyeZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.lookAtPointX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.lookAtPointY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.lookAtPointZ.containsOnlyNumbers() == false) {
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
   * 背景の色を指定するボタンを生成します。
   */
  private void createColorSelectorButton(Group parent) {
    this.colorSelector = new ColorSelectorButton(parent, this);
  }

  /**
   * モデルの長さの単位を指定するコンボボックスを作成します。
   * 
   * @param parent
   */
  private void createModelLengthUnit(Group parent) {
    final Label name = new Label(parent, SWT.NONE);
    name.setText(Messages.getString("ConfigDialog.4")); //$NON-NLS-1$
    
    this.modelLengthUnitCombo = new Combo(parent, SWT.READ_ONLY);
    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    this.modelLengthUnitCombo.setLayoutData(data);
    final String[] length = {"m"}; //$NON-NLS-1$
    // String[] LENGTH = {"m", "cm", "mm"};
    this.modelLengthUnitCombo.setItems(length);
    this.modelLengthUnitCombo.setText("m"); //$NON-NLS-1$
  }

  /**
   * モデルの角度の単位を指定するコンボボックスを作成します。
   * 
   * @param group
   */
  private void createModelAngleUnit(Group group) {
    final Label name = new Label(group, SWT.NONE);
    name.setText(Messages.getString("ConfigDialog.3")); //$NON-NLS-1$
    
    this.modelAngleUnitCombo = new Combo(group, SWT.READ_ONLY);
    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    this.modelAngleUnitCombo.setLayoutData(data);
    final String[] angles = {"radian"}; //$NON-NLS-1$
    // String[] ANGLES = {"degree", "radian"};
    this.modelAngleUnitCombo.setItems(angles);
    this.modelAngleUnitCombo.setText("radian"); //$NON-NLS-1$
  }
  
  /**
   * データの長さの単位を指定するコンボボックスを作成します。
   * 
   * @param parent
   */
  private void createDataLengthUnitCombo(Group parent) {
    final Label name = new Label(parent, SWT.NONE);
    name.setText(Messages.getString("ConfigDialog.7")); //$NON-NLS-1$
    
    this.dataLengthUnitCombo = new Combo(parent, SWT.READ_ONLY);
    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    this.dataLengthUnitCombo.setLayoutData(data);
    final String[] length = {"m"}; //$NON-NLS-1$
    // String[] LENGTH = {"m", "cm", "mm"};
    this.dataLengthUnitCombo.setItems(length);
    this.dataLengthUnitCombo.setText("m"); //$NON-NLS-1$
  }

  /**
   * データの角度の単位を指定するコンボボックスを作成します。
   * 
   * @param group
   */
  private void createDataAngleUnitCombo(Group group) {
    final Label name = new Label(group, SWT.NONE);
    name.setText(Messages.getString("ConfigDialog.6")); //$NON-NLS-1$
    
    this.dataAngleUnitCombo = new Combo(group, SWT.READ_ONLY);
    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    this.dataAngleUnitCombo.setLayoutData(data);
    final String[] angles = {"radian"}; //$NON-NLS-1$
    // String[] ANGLES = {"degree", "radian"};
    this.dataAngleUnitCombo.setItems(angles);
    this.dataAngleUnitCombo.setText("radian"); //$NON-NLS-1$
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
}