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
  private Shell parentShell = null;
  private Group editGroup;
  private Combo colorCombo;
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
    this.parentShell = parentShell;
    this.configuration = configuration;
    this.modeler = modeler;
    createSShell();
  }

  /**
   * Shellを生成します。
   */
  private void createSShell() {
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    this.sShell.setText("ConfigurationDialog"); //$NON-NLS-1$
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 450));

    final GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    this.sShell.setLayout(layout);

    createEditGroup();
    createMainButtonComp();
  }

  /**
   * 編集用のグループを生成します。
   */
  private void createEditGroup() {
    this.editGroup = new Group(this.sShell, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    this.editGroup.setLayout(layout);
    final GridData data = new GridData(GridData.FILL_BOTH);
    this.editGroup.setLayoutData(data);
    this.editGroup.setText(Messages.getString("ConfigDialog.0")); //$NON-NLS-1$

    // 取り扱うデータの単位の設定を行うグループ
    final Group unitGroup = new Group(this.editGroup, SWT.NONE);
    final GridLayout unitLayout = new GridLayout();
    unitLayout.numColumns = 4;
    unitGroup.setText(Messages.getString("ConfigDialog.1")); //$NON-NLS-1$
    final GridData unitData = new GridData(GridData.FILL_HORIZONTAL);
    unitData.horizontalSpan = 2;
    unitGroup.setLayoutData(unitData);
    unitGroup.setLayout(unitLayout);

    // モデルの単位
    final Label modelUnitLabel = new Label(unitGroup, SWT.NONE);
    final GridData modelData = new GridData(GridData.FILL_HORIZONTAL);
    modelData.horizontalSpan = 4;
    modelUnitLabel.setText(Messages.getString("ConfigDialog.2")); //$NON-NLS-1$
    modelUnitLabel.setLayoutData(modelData);

    final Label modelAngleLabel = new Label(unitGroup, SWT.NONE);
    modelAngleLabel.setText(Messages.getString("ConfigDialog.3")); //$NON-NLS-1$
    createModelAngleUnitCombo(unitGroup);

    final Label modelLengthLabel = new Label(unitGroup, SWT.NONE);
    modelLengthLabel.setText(Messages.getString("ConfigDialog.4")); //$NON-NLS-1$
    createModelLengthUnitCombo(unitGroup);

    // 時系列データの単位
    final Label dataUnitLabel = new Label(unitGroup, SWT.NONE);
    final GridData dataData = new GridData(GridData.FILL_HORIZONTAL);
    dataData.horizontalSpan = 4;
    dataUnitLabel.setText(Messages.getString("ConfigDialog.5")); //$NON-NLS-1$
    dataUnitLabel.setLayoutData(dataData);

    final Label dataAngleLabel = new Label(unitGroup, SWT.NONE);
    dataAngleLabel.setText(Messages.getString("ConfigDialog.6")); //$NON-NLS-1$
    createDataAngleUnitCombo(unitGroup);

    final Label dataLengthLabel = new Label(unitGroup, SWT.NONE);
    dataLengthLabel.setText(Messages.getString("ConfigDialog.7")); //$NON-NLS-1$
    createDataLengthUnitCombo(unitGroup);

    // 光源の位置の指定
    final Group lightPointGroup = new Group(this.editGroup, SWT.NONE);
    lightPointGroup.setText(Messages.getString("ConfigDialog.8")); //$NON-NLS-1$

    final GridData lightData = new GridData(GridData.FILL_HORIZONTAL);
    lightData.horizontalSpan = 2;
    final GridLayout lightLayout = new GridLayout(7, true);
    lightPointGroup.setLayout(lightLayout);
    lightPointGroup.setLayoutData(lightData);

    this.lightX = new ParameterInputBox(lightPointGroup, SWT.NONE, "  (", "10.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.lightY = new ParameterInputBox(lightPointGroup, SWT.NONE, ",  ", "10.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.lightZ = new ParameterInputBox(lightPointGroup, SWT.NONE, ",  ", "20.0"); //$NON-NLS-1$ //$NON-NLS-2$

    final Label kakko = new Label(lightPointGroup, SWT.NONE);
    final GridData labelData = new GridData();
    labelData.widthHint = 10;
    kakko.setLayoutData(labelData);
    kakko.setText(")"); //$NON-NLS-1$

    // 視点の位置の指定
    final Group eyePointGroup = new Group(this.editGroup, SWT.NONE);
    eyePointGroup.setText(Messages.getString("ConfigDialog.10")); //$NON-NLS-1$

    final GridData eyeData = new GridData(GridData.FILL_HORIZONTAL);
    eyeData.horizontalSpan = 2;
    final GridLayout viewLayout = new GridLayout(7, true);
    eyePointGroup.setLayout(viewLayout);
    eyePointGroup.setLayoutData(eyeData);

    final Label vRollLabel = new Label(eyePointGroup, SWT.NONE);
    vRollLabel.setText(Messages.getString("ConfigDialog.11")); //$NON-NLS-1$
    final GridData vRollData = new GridData(GridData.FILL_HORIZONTAL);
    vRollData.horizontalSpan = 7;
    vRollLabel.setLayoutData(vRollData);

    this.lookAtPointX = new ParameterInputBox(eyePointGroup, SWT.NONE, "  (", "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    this.lookAtPointY = new ParameterInputBox(eyePointGroup, SWT.NONE, ",  ", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.lookAtPointZ = new ParameterInputBox(eyePointGroup, SWT.NONE, ",  ", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$

    final Label vKakko2 = new Label(eyePointGroup, SWT.NONE);
    final GridData vKakkoData2 = new GridData();
    vKakko2.setLayoutData(vKakkoData2);
    vKakko2.setText(")"); //$NON-NLS-1$

    final Label vLabel = new Label(eyePointGroup, SWT.NONE);
    vLabel.setText(Messages.getString("ConfigDialog.12")); //$NON-NLS-1$
    final GridData vData = new GridData(GridData.FILL_HORIZONTAL);
    vData.horizontalSpan = 7;
    vLabel.setLayoutData(vData);

    this.eyeX = new ParameterInputBox(eyePointGroup, SWT.NONE, "  (", "5.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.eyeY = new ParameterInputBox(eyePointGroup, SWT.NONE, ",  ", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.eyeZ = new ParameterInputBox(eyePointGroup, SWT.NONE, ",  ", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$

    final Label vKakko = new Label(eyePointGroup, SWT.NONE);
    final GridData vKakkoData = new GridData();
    vKakko.setLayoutData(vKakkoData);
    vKakko.setText(")"); //$NON-NLS-1$

    // 背景色の選択
    final Label setColorLabel = new Label(this.editGroup, SWT.NONE);
    setColorLabel.setText(Messages.getString("ConfigDialog.13")); //$NON-NLS-1$
    createColorCombo();

    setParametersToDialog();

  }

  /**
   * XMLファイルからデータをダイアログに設定します。
   */
  private void setParametersToDialog() {
    if (this.configuration.getLight() != null) {
      final LightModel light = this.configuration.getLight();
      this.lightX.setText("" + light.getX()); //$NON-NLS-1$
      this.lightY.setText("" + light.getY()); //$NON-NLS-1$
      this.lightZ.setText("" + light.getZ()); //$NON-NLS-1$
    }

    if (this.configuration.getEye() != null) {
      final EyeModel eye = this.configuration.getEye();
      this.eyeX.setText("" + eye.getX()); //$NON-NLS-1$
      this.eyeY.setText("" + eye.getY()); //$NON-NLS-1$
      this.eyeZ.setText("" + eye.getZ()); //$NON-NLS-1$
    }
    
    if (this.configuration.getLookAtPoint() != null) {
      final LookAtPointModel lookAtPoint = this.configuration.getLookAtPoint();
      this.lookAtPointX.setText("" + lookAtPoint.getX()); //$NON-NLS-1$
      this.lookAtPointY.setText("" + lookAtPoint.getY()); //$NON-NLS-1$
      this.lookAtPointZ.setText("" + lookAtPoint.getZ()); //$NON-NLS-1$
    }

    if (this.configuration.getModelUnit() != null) {
      if (this.configuration.getModelUnit().getAngleUnit() != null) {
        this.modelAngleUnitCombo.setText(this.configuration.getModelUnit().getAngleUnit());
      }
      if (this.configuration.getModelUnit().getLengthUnit() != null) {
        this.modelLengthUnitCombo.setText(this.configuration.getModelUnit().getLengthUnit());
      }
    }

    if (this.configuration.getDataUnit() != null) {
      if (this.configuration.getDataUnit().getAngle() != null) {
        this.dataAngleUnitCombo.setText(this.configuration.getDataUnit().getAngle());
      }
      if (this.configuration.getDataUnit().getLength() != null) {
        this.dataLengthUnitCombo.setText(this.configuration.getDataUnit().getLength());
      }
    }

    if (this.configuration.getBackground() == null) {
      this.colorCombo.setText("white"); //$NON-NLS-1$
    } else {
      this.colorCombo.setText(this.configuration.getBackground().getColor());
    }
  }

  /**
   * 視点座標に数字以外の文字が入っていたときに出すメッセージボックスを生成します。
   */
  void createMessageBox() {
    final MessageBox messsageBox = new MessageBox(this.sShell, SWT.ICON_WARNING);
    messsageBox.setMessage(Messages.getString("ConfigDialog.14")); //$NON-NLS-1$
    messsageBox.setText(Messages.getString("ConfigDialog.15")); //$NON-NLS-1$
    messsageBox.open();
  }

  /**
   * メインボタンのあるコンポジットを生成します。
   */
  private void createMainButtonComp() {
    final Composite comp = new Composite(this.sShell, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 3;
    comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    comp.setLayout(layout);

    final Button okButton = new Button(comp, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("ConfigDialog.16")); //$NON-NLS-1$
    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (check()) {
          getParametersFromDialog();
          ConfigurationDialog.this.modeler.updateDisplay();
          ConfigurationDialog.this.sShell.close();
        } else {
          createMessageBox();
        }
      }

    });
    
    final Button cancelButton = new Button(comp, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("ConfigDialog.17")); //$NON-NLS-1$
    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        ConfigurationDialog.this.sShell.close();
      }
    });
    
    final Button applyButton = new Button(comp, SWT.NONE);
    applyButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    applyButton.setText(Messages.getString("ConfigurationDialog.0")); //$NON-NLS-1$
    applyButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (check()) {
          getParametersFromDialog();
          ConfigurationDialog.this.modeler.updateDisplay();
        } else {
          createMessageBox();
        }
      }

    });
  }

  /**
   * 入力したパラメータの値をXMLファイルに書き込む
   */
  void getParametersFromDialog() {
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
      if (this.colorCombo.getText() != "white") { //$NON-NLS-1$
        final BackgroundModel background = new BackgroundModel();
        background.setColor(this.colorCombo.getText());
        this.configuration.setBackground(background);
      }
    } else {
      this.configuration.getBackground().setColor(this.colorCombo.getText());
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
  boolean check() {
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
   * 背景の色を指定するコンボボックスを作成します。
   */
  private void createColorCombo() {
    this.colorCombo = new Combo(this.editGroup, SWT.READ_ONLY);
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    this.colorCombo.setLayoutData(gridData);
    final String[] colors = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
    this.colorCombo.setItems(colors);
  }

  /**
   * モデルの長さの単位を指定するコンボボックスを作成します。
   * 
   * @param group
   */
  private void createModelLengthUnitCombo(Group group) {
    this.modelLengthUnitCombo = new Combo(group, SWT.READ_ONLY);
    final GridData lengthData = new GridData(GridData.FILL_HORIZONTAL);
    this.modelLengthUnitCombo.setLayoutData(lengthData);
    final String[] length = {"m"}; //$NON-NLS-1$
    // String[] LENGTH = {"m", "cm", "mm"};
    this.modelLengthUnitCombo.setItems(length);
    this.modelLengthUnitCombo.setText("m"); //$NON-NLS-1$
  }

  /**
   * データの長さの単位を指定するコンボボックスを作成します。
   * 
   * @param group
   */
  private void createDataLengthUnitCombo(Group group) {
    this.dataLengthUnitCombo = new Combo(group, SWT.READ_ONLY);
    final GridData lengthData = new GridData(GridData.FILL_HORIZONTAL);
    this.dataLengthUnitCombo.setLayoutData(lengthData);
    final String[] length = {"m"}; //$NON-NLS-1$
    // String[] LENGTH = {"m", "cm", "mm"};
    this.dataLengthUnitCombo.setItems(length);
    this.dataLengthUnitCombo.setText("m"); //$NON-NLS-1$
  }

  /**
   * モデルの角度の単位を指定するコンボボックスを作成します。
   * 
   * @param group
   */
  private void createModelAngleUnitCombo(Group group) {
    this.modelAngleUnitCombo = new Combo(group, SWT.READ_ONLY);
    final GridData angleData = new GridData(GridData.FILL_HORIZONTAL);
    this.modelAngleUnitCombo.setLayoutData(angleData);
    final String[] angles = {"radian"}; //$NON-NLS-1$
    // String[] ANGLES = {"degree", "radian"};
    this.modelAngleUnitCombo.setItems(angles);
    this.modelAngleUnitCombo.setText("radian"); //$NON-NLS-1$
  }

  /**
   * データの角度の単位を指定するコンボボックスを作成します。
   * 
   * @param group
   */
  private void createDataAngleUnitCombo(Group group) {
    this.dataAngleUnitCombo = new Combo(group, SWT.READ_ONLY);
    final GridData angleData = new GridData(GridData.FILL_HORIZONTAL);
    this.dataAngleUnitCombo.setLayoutData(angleData);
    final String[] angles = {"radian"}; //$NON-NLS-1$
    // String[] ANGLES = {"degree", "radian"};
    this.dataAngleUnitCombo.setItems(angles);
    this.dataAngleUnitCombo.setText("radian"); //$NON-NLS-1$
  }

}