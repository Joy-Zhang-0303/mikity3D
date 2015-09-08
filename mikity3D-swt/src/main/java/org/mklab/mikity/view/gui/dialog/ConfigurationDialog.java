/*
 * Created on 2005/02/01
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
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
import org.mklab.mikity.model.xml.simplexml.config.DataUnitModel;
import org.mklab.mikity.model.xml.simplexml.config.EyeModel;
import org.mklab.mikity.model.xml.simplexml.config.LightModel;
import org.mklab.mikity.model.xml.simplexml.config.LookAtPointModel;
import org.mklab.mikity.model.xml.simplexml.config.ModelUnitModel;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;


/**
 * Configurationの設定を行うダイアログを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/01
 */
public class ConfigurationDialog {
  Shell sShell = null;
  private ColorSelectorButton colorSelector;
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

  /**
   * 新しく生成された<code>ConfigurationDialog</code>オブジェクトを初期化します。
   * @param parentShell 親シェル
   * @param configuration 設定
   * @param modeler モデラー
   */
  public ConfigurationDialog(Shell parentShell, ConfigurationModel configuration, JoglModeler modeler) {
    this.configuration = configuration;
    this.modeler = modeler;

    this.sShell = new Shell(parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    this.sShell.setText("Configuration Dialog"); //$NON-NLS-1$
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 560));

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
    final Label backgroundLabel = new Label(editGroup, SWT.NONE);
    backgroundLabel.setText(Messages.getString("ConfigDialog.13")); //$NON-NLS-1$
    createColorSelectorButton(editGroup);
  }

  /**
   * ソースデータの単位を設定するグループを生成します。
   * 
   * @param parent 親
   */
  private void createDataUnit(Group parent) {
    final Group dataUnitGroup = new Group(parent, SWT.NONE);
    final GridLayout dataUnitLayout = new GridLayout();
    dataUnitLayout.numColumns = 5;
    dataUnitGroup.setText(Messages.getString("ConfigDialog.5")); //$NON-NLS-1$
    final GridData dataUnitData = new GridData(GridData.FILL_HORIZONTAL);
    dataUnitData.horizontalSpan = 2;
    dataUnitGroup.setLayoutData(dataUnitData);
    dataUnitGroup.setLayout(dataUnitLayout);

    createDataLengthUnitCombo(dataUnitGroup);
    
    final Label space = new Label(dataUnitGroup, SWT.NONE);
    final GridData data = new GridData();
    data.widthHint = 60;
    space.setLayoutData(data);
    
    createDataAngleUnitCombo(dataUnitGroup);
  }

  /**
   * モデルの単位を設定するグループを生成しまうｓ。
   * 
   * @param parent 親
   */
  private void createModelUnit(Group parent) {
    final Group modelUnitGroup = new Group(parent, SWT.NONE);
    final GridLayout modeUnitLayout = new GridLayout();
    modeUnitLayout.numColumns = 5;
    modelUnitGroup.setText(Messages.getString("ConfigDialog.2")); //$NON-NLS-1$
    final GridData modelUnitData = new GridData(GridData.FILL_HORIZONTAL);
    modelUnitData.horizontalSpan = 2;
    modelUnitGroup.setLayoutData(modelUnitData);
    modelUnitGroup.setLayout(modeUnitLayout);

    createModelLengthUnit(modelUnitGroup);
    
    final Label space = new Label(modelUnitGroup, SWT.NONE);
    final GridData data = new GridData();
    data.widthHint = 60;
    space.setLayoutData(data);
    
    createModelAngleUnit(modelUnitGroup);
  }

  /**
   * 光源の位置を設定するグループを生成します。
   */
  private void createLightGroup(Group parent) {
    final Group lightGroup = new Group(parent, SWT.NONE);
    lightGroup.setText(Messages.getString("ConfigDialog.8")); //$NON-NLS-1$

    final GridData lightData = new GridData(GridData.FILL_HORIZONTAL);
    lightData.horizontalSpan = 2;
    final GridLayout lightLayout = new GridLayout(7, true);
    lightGroup.setLayout(lightLayout);
    lightGroup.setLayoutData(lightData);

    final LightModel light = this.configuration.getLight();
    
    this.lightX = new ParameterInputBox(lightGroup, SWT.NONE, "  (", "" + light.getX()); //$NON-NLS-1$ //$NON-NLS-2$
    this.lightY = new ParameterInputBox(lightGroup, SWT.NONE, ",  ", "" + light.getY()); //$NON-NLS-1$ //$NON-NLS-2$
    this.lightZ = new ParameterInputBox(lightGroup, SWT.NONE, ",  ", "" + light.getZ()); //$NON-NLS-1$ //$NON-NLS-2$

    final Label lightRightLabel = new Label(lightGroup, SWT.NONE);
    final GridData lightRightData = new GridData();
    lightRightData.widthHint = 10;
    lightRightLabel.setLayoutData(lightRightData);
    lightRightLabel.setText(")"); //$NON-NLS-1$
  }

  /**
   * 注視点の位置を設定するグループを生成します。
   */
  private void createLookAtPoint(Group parent) {
    final Group lookAtPointGroup = new Group(parent, SWT.NONE);
    lookAtPointGroup.setText(Messages.getString("ConfigDialog.11")); //$NON-NLS-1$

    final GridData lookAtPointGroupData = new GridData(GridData.FILL_HORIZONTAL);
    lookAtPointGroupData.horizontalSpan = 2;
    final GridLayout viewLayout = new GridLayout(7, true);
    lookAtPointGroup.setLayout(viewLayout);
    lookAtPointGroup.setLayoutData(lookAtPointGroupData);

    final LookAtPointModel lookAtPoint = this.configuration.getLookAtPoint();

    this.lookAtPointX = new ParameterInputBox(lookAtPointGroup, SWT.NONE, "  (", "" + lookAtPoint.getX()); //$NON-NLS-1$//$NON-NLS-2$
    this.lookAtPointY = new ParameterInputBox(lookAtPointGroup, SWT.NONE, ",  ", "" + lookAtPoint.getY()); //$NON-NLS-1$ //$NON-NLS-2$
    this.lookAtPointZ = new ParameterInputBox(lookAtPointGroup, SWT.NONE, ",  ", "" + lookAtPoint.getZ()); //$NON-NLS-1$ //$NON-NLS-2$

    final Label lookAtPointRightLabel = new Label(lookAtPointGroup, SWT.NONE);
    final GridData lookAtPointRightData = new GridData();
    lookAtPointRightLabel.setLayoutData(lookAtPointRightData);
    lookAtPointRightLabel.setText(")"); //$NON-NLS-1$
  }

  /**
   * 視点の位置を設定するグループを生成します。
   */
  private void createEye(Group parent) {
    final Group eyeGroup = new Group(parent, SWT.NONE);
    eyeGroup.setText(Messages.getString("ConfigDialog.12")); //$NON-NLS-1$

    final GridData eyeGroupData = new GridData(GridData.FILL_HORIZONTAL);
    eyeGroupData.horizontalSpan = 2;
    final GridLayout viewLayout2 = new GridLayout(7, true);
    eyeGroup.setLayout(viewLayout2);
    eyeGroup.setLayoutData(eyeGroupData);

    final EyeModel eye = this.configuration.getEye();
    
    this.eyeX = new ParameterInputBox(eyeGroup, SWT.NONE, "  (", "" + eye.getX()); //$NON-NLS-1$ //$NON-NLS-2$
    this.eyeY = new ParameterInputBox(eyeGroup, SWT.NONE, ",  ", "" + eye.getY()); //$NON-NLS-1$ //$NON-NLS-2$
    this.eyeZ = new ParameterInputBox(eyeGroup, SWT.NONE, ",  ", "" + eye.getZ()); //$NON-NLS-1$ //$NON-NLS-2$

    final Label eyeRightLabel = new Label(eyeGroup, SWT.NONE);
    final GridData eyePositionRightData = new GridData();
    eyeRightLabel.setLayoutData(eyePositionRightData);
    eyeRightLabel.setText(")"); //$NON-NLS-1$
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
    
    final Button applyButton = new Button(composite, SWT.NONE);
    applyButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    applyButton.setText(Messages.getString("ConfigurationDialog.0")); //$NON-NLS-1$
    applyButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (containsOnlyNumbers() == false) {
          createMessageBoxForNonNumericInput();
          return;
        }
        
        ConfigurationDialog.this.modeler.setChanged(ConfigurationDialog.this.isChanged());
        
        updateConfigurationParameters();
        ConfigurationDialog.this.modeler.updateDisplay();
      }

    });
    
    final Button closeButton = new Button(composite, SWT.NONE);
    closeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    closeButton.setText(Messages.getString("ConfigDialog.17")); //$NON-NLS-1$
    closeButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (ConfigurationDialog.this.isChanged() == false) {
          ConfigurationDialog.this.sShell.close();
          return;
        }
        
        final MessageBox message = new MessageBox(ConfigurationDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        message.setMessage(Messages.getString("EditPrimitiveDialog.26")); //$NON-NLS-1$
        final int yesNo = message.open();
        if (yesNo == SWT.YES) {
         ConfigurationDialog.this.sShell.close();
        }
      }
    });
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
    this.colorSelector = new ColorSelectorButton(parent);
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
    if (this.colorSelector.isChanged) {
      return true;
    }
    
    if (this.lightX.isChanged()) {
      return true;
    }
    if (this.lightY.isChanged()) {
      return true;
    }
    if (this.lightZ.isChanged()) {
      return true;
    }

    if (this.eyeX.isChanged()) {
      return true;
    }
    
    if (this.eyeY.isChanged()) {
      return true;
    }
    
    if (this.eyeZ.isChanged()) {
      return true;
    }

    if (this.lookAtPointX.isChanged()) {
      return true;
    }
    if (this.lookAtPointY.isChanged()) {
      return true;
    }
    if (this.lookAtPointZ.isChanged()) {
      return true;
    }
    
    return false;
  }
}