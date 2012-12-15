/*
 * Created on 2005/02/01
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.dialog;

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
import org.mklab.mikity.gui.ParameterInputBox;
import org.mklab.mikity.xml.JamastConfig;
import org.mklab.mikity.xml.config.Background;
import org.mklab.mikity.xml.config.DataUnit;
import org.mklab.mikity.xml.config.Light;
import org.mklab.mikity.xml.config.ModelUnit;
import org.mklab.mikity.xml.config.View;


/**
 * <ConfigDialog>の設定を行うクラス
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/01
 */
public class ConfigDialog {

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

  private ParameterInputBox viewX;
  private ParameterInputBox viewY;
  private ParameterInputBox viewZ;

  private ParameterInputBox viewXrotate;
  private ParameterInputBox viewYrotate;
  private ParameterInputBox viewZrotate;

  private JamastConfig config;

  /**
   * コンストラクター
   * 
   * @param parentShell 親シェル
   * @param config 設定
   */
  public ConfigDialog(Shell parentShell, JamastConfig config) {
    this.parentShell = parentShell;
    this.config = config;
    createSShell();
  }

  /**
   * Shellの作成
   */
  private void createSShell() {
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    this.sShell.setText("ConfigDialog"); //$NON-NLS-1$
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 450));

    final GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    this.sShell.setLayout(layout);

    createEditGroup();
    createMainButtonComp();
  }

  /**
   * <config>の中身を編集できるグループを作成する
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

    final Label lKakko = new Label(lightPointGroup, SWT.NONE);
    lKakko.setText(Messages.getString("ConfigDialog.9")); //$NON-NLS-1$
    final GridData xData = new GridData(GridData.FILL_HORIZONTAL);
    xData.horizontalSpan = 7;
    lKakko.setLayoutData(xData);

    this.lightX = new ParameterInputBox(lightPointGroup, SWT.NONE, "  (", "0.2"); //$NON-NLS-1$ //$NON-NLS-2$
    this.lightY = new ParameterInputBox(lightPointGroup, SWT.NONE, ",  ", "-0.8"); //$NON-NLS-1$ //$NON-NLS-2$
    this.lightZ = new ParameterInputBox(lightPointGroup, SWT.NONE, ",  ", "-0.8"); //$NON-NLS-1$ //$NON-NLS-2$

    final Label kakko = new Label(lightPointGroup, SWT.NONE);
    final GridData labelData = new GridData();
    labelData.widthHint = 10;
    kakko.setLayoutData(labelData);
    kakko.setText(")"); //$NON-NLS-1$

    // 視点の位置の指定
    final Group viewPointGroup = new Group(this.editGroup, SWT.NONE);
    viewPointGroup.setText(Messages.getString("ConfigDialog.10")); //$NON-NLS-1$

    final GridData viewData = new GridData(GridData.FILL_HORIZONTAL);
    viewData.horizontalSpan = 2;
    final GridLayout viewLayout = new GridLayout(7, true);
    viewPointGroup.setLayout(viewLayout);
    viewPointGroup.setLayoutData(viewData);

    final Label vRollLabel = new Label(viewPointGroup, SWT.NONE);
    vRollLabel.setText(Messages.getString("ConfigDialog.11")); //$NON-NLS-1$
    final GridData vRollData = new GridData(GridData.FILL_HORIZONTAL);
    vRollData.horizontalSpan = 7;
    vRollLabel.setLayoutData(vRollData);

    this.viewXrotate = new ParameterInputBox(viewPointGroup, SWT.NONE, "  (", "-0.2"); //$NON-NLS-1$//$NON-NLS-2$
    this.viewYrotate = new ParameterInputBox(viewPointGroup, SWT.NONE, ",  ", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.viewZrotate = new ParameterInputBox(viewPointGroup, SWT.NONE, ",  ", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$

    final Label vKakko2 = new Label(viewPointGroup, SWT.NONE);
    final GridData vKakkoData2 = new GridData();
    vKakko2.setLayoutData(vKakkoData2);
    vKakko2.setText(")"); //$NON-NLS-1$

    final Label vLabel = new Label(viewPointGroup, SWT.NONE);
    vLabel.setText(Messages.getString("ConfigDialog.12")); //$NON-NLS-1$
    final GridData vData = new GridData(GridData.FILL_HORIZONTAL);
    vData.horizontalSpan = 7;
    vLabel.setLayoutData(vData);

    this.viewX = new ParameterInputBox(viewPointGroup, SWT.NONE, "  (", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.viewY = new ParameterInputBox(viewPointGroup, SWT.NONE, ",  ", "0.3"); //$NON-NLS-1$ //$NON-NLS-2$
    this.viewZ = new ParameterInputBox(viewPointGroup, SWT.NONE, ",  ", "1.0"); //$NON-NLS-1$ //$NON-NLS-2$

    final Label vKakko = new Label(viewPointGroup, SWT.NONE);
    final GridData vKakkoData = new GridData();
    vKakko.setLayoutData(vKakkoData);
    vKakko.setText(")"); //$NON-NLS-1$

    // 背景色の選択
    final Label setColorLabel = new Label(this.editGroup, SWT.NONE);
    setColorLabel.setText(Messages.getString("ConfigDialog.13")); //$NON-NLS-1$
    createColorCombo();

    getParameter();

  }

  /**
   * XMLファイルから値を読み込む
   */
  private void getParameter() {
    if (this.config.loadLight() != null) {
      Light light = this.config.loadLight();
      this.lightX.setText("" + light.loadX()); //$NON-NLS-1$
      this.lightY.setText("" + light.loadY()); //$NON-NLS-1$
      this.lightZ.setText("" + light.loadZ()); //$NON-NLS-1$
    }
    if (this.config.loadView() != null) {
      View view = this.config.loadView();
      this.viewX.setText("" + view.loadX()); //$NON-NLS-1$
      this.viewY.setText("" + view.loadY()); //$NON-NLS-1$
      this.viewZ.setText("" + view.loadZ()); //$NON-NLS-1$
      this.viewXrotate.setText("" + view.loadXrotate()); //$NON-NLS-1$
      this.viewYrotate.setText("" + view.loadYrotate()); //$NON-NLS-1$
      this.viewZrotate.setText("" + view.loadZrotate()); //$NON-NLS-1$
    }
    if (this.config.loadModelUnit() != null) {
      if (this.config.loadModelUnit().loadAngle() != null) {
        this.modelAngleUnitCombo.setText(this.config.loadModelUnit().loadAngle());
      }
      if (this.config.loadModelUnit().loadLength() != null) {
        this.modelLengthUnitCombo.setText(this.config.loadModelUnit().loadLength());
      }
    }
    if (this.config.loadDataUnit() != null) {
      if (this.config.loadDataUnit().loadAngle() != null) {
        this.dataAngleUnitCombo.setText(this.config.loadDataUnit().loadAngle());
      }
      if (this.config.loadDataUnit().loadLength() != null) {
        this.dataLengthUnitCombo.setText(this.config.loadDataUnit().loadLength());
      }
    }
    if (this.config.loadBackground() == null) {
      this.colorCombo.setText("white"); //$NON-NLS-1$
    } else {
      this.colorCombo.setText(this.config.loadBackground().loadColor());
    }
  }

  /**
   * 視点座標に数字以外の文字が入っていたときに出すメッセージボックスの作成
   */
  void createMesBox() {
    final MessageBox mesBox = new MessageBox(this.sShell, SWT.ICON_WARNING);
    mesBox.setMessage(Messages.getString("ConfigDialog.14")); //$NON-NLS-1$
    mesBox.setText(Messages.getString("ConfigDialog.15")); //$NON-NLS-1$
    mesBox.open();
  }

  /**
   * メインボタンのあるコンポジットを作成する
   */
  private void createMainButtonComp() {
    final Composite comp = new Composite(this.sShell, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    comp.setLayout(layout);

    final Button okButton = new Button(comp, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("ConfigDialog.16")); //$NON-NLS-1$

    final Button cancelButton = new Button(comp, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("ConfigDialog.17")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        ConfigDialog.this.sShell.close();
      }
    });

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        // MainWindow.setBGColor(colorCombo.getText());

        // もし適当な値ならば採用
        if (check()) {
          // Vector3f viewVector = new
          // Vector3f(lightX.getFloatValue(),lightY.getFloatValue(),lightZ.
          // getFloatValue());
          // MainWindow.setLightVector(new Vector3f(lightX.getFloatValue(),
          // lightY.getFloatValue(), lightZ.getFloatValue()));
          setParameter();
          ConfigDialog.this.sShell.close();
        } else {
          createMesBox();
        }
      }

    });
  }

  /**
   * 入力したパラメータの値をXMLファイルに書き込む
   */
  void setParameter() {
    final Light light = new Light();
    light.setX(this.lightX.getFloatValue());
    light.setY(this.lightY.getFloatValue());
    light.setZ(this.lightZ.getFloatValue());
    this.config.setLight(light);
    final View view = new View();
    view.setX(this.viewX.getFloatValue());
    view.setY(this.viewY.getFloatValue());
    view.setZ(this.viewZ.getFloatValue());
    view.setXrotate(this.viewXrotate.getDoubleValue());
    view.setYrotate(this.viewYrotate.getDoubleValue());
    view.setZrotate(this.viewZrotate.getDoubleValue());
    this.config.setView(view);

    if (this.config.loadBackground() == null) {
      if (this.colorCombo.getText() != "white") { //$NON-NLS-1$
        final Background background = new Background();
        background.setColor(this.colorCombo.getText());
        this.config.setBackground(background);
      }
    } else {
      this.config.loadBackground().setColor(this.colorCombo.getText());
    }

    if (this.config.loadModelUnit() == null) {
      if (this.modelAngleUnitCombo.getText() != "radian") { //$NON-NLS-1$
        final ModelUnit modelUnit = new ModelUnit();
        modelUnit.setAngle(this.modelAngleUnitCombo.getText());
        modelUnit.setLength(this.modelLengthUnitCombo.getText());
        this.config.setModelUnit(modelUnit);
      }
    } else {
      this.config.loadModelUnit().setAngle(this.modelAngleUnitCombo.getText());
      this.config.loadModelUnit().setLength(this.modelLengthUnitCombo.getText());
    }

    if (this.config.loadDataUnit() == null) {
      if (this.dataAngleUnitCombo.getText() != "radian") { //$NON-NLS-1$
        final DataUnit dataUnit = new DataUnit();
        dataUnit.setAngle(this.dataAngleUnitCombo.getText());
        dataUnit.setLength(this.dataLengthUnitCombo.getText());
        this.config.setDataUnit(dataUnit);
      }
    } else {
      this.config.loadDataUnit().setAngle(this.dataAngleUnitCombo.getText());
      this.config.loadDataUnit().setLength(this.dataLengthUnitCombo.getText());
    }

  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean check() {
    if (this.lightX.checkParam() == false) {
      return false;
    }
    if (this.lightY.checkParam() == false) {
      return false;
    }
    if (this.lightZ.checkParam() == false) {
      return false;
    }
    if (this.viewX.checkParam() == false) {
      return false;
    }
    if (this.viewY.checkParam() == false) {
      return false;
    }
    if (this.viewZ.checkParam() == false) {
      return false;
    }
    if (this.viewXrotate.checkParam() == false) {
      return false;
    }
    if (this.viewYrotate.checkParam() == false) {
      return false;
    }
    if (this.viewZrotate.checkParam() == false) {
      return false;
    }
    return true;
  }

  /**
   * Shellを開く
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
   * 背景の色を指定するコンボボックスの作成
   */
  private void createColorCombo() {
    this.colorCombo = new Combo(this.editGroup, SWT.READ_ONLY);
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    this.colorCombo.setLayoutData(gridData);
    final String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
    this.colorCombo.setItems(COLORS);
  }

  /**
   * モデルの長さの単位を指定するコンボボックスの作成
   * 
   * @param group
   */
  private void createModelLengthUnitCombo(Group group) {
    this.modelLengthUnitCombo = new Combo(group, SWT.READ_ONLY);
    final GridData lengthData = new GridData(GridData.FILL_HORIZONTAL);
    this.modelLengthUnitCombo.setLayoutData(lengthData);
    final String[] LENGTH = {"m"}; //$NON-NLS-1$
    // 現時点では変更不能。増やしたい。
    // String[] LENGTH = {"m", "cm", "mm"};
    this.modelLengthUnitCombo.setItems(LENGTH);
    this.modelLengthUnitCombo.setText("m"); //$NON-NLS-1$
  }

  /**
   * データの長さの単位を指定するコンボボックスの作成
   * 
   * @param group
   */
  private void createDataLengthUnitCombo(Group group) {
    this.dataLengthUnitCombo = new Combo(group, SWT.READ_ONLY);
    final GridData lengthData = new GridData(GridData.FILL_HORIZONTAL);
    this.dataLengthUnitCombo.setLayoutData(lengthData);
    final String[] LENGTH = {"m"}; //$NON-NLS-1$
    // 現時点では変更不能。増やしたい。
    // String[] LENGTH = {"m", "cm", "mm"};
    this.dataLengthUnitCombo.setItems(LENGTH);
    this.dataLengthUnitCombo.setText("m"); //$NON-NLS-1$
  }

  /**
   * モデルの角度の単位を指定するコンボボックスの作成
   * 
   * @param group
   */
  private void createModelAngleUnitCombo(Group group) {
    this.modelAngleUnitCombo = new Combo(group, SWT.READ_ONLY);
    final GridData angleData = new GridData(GridData.FILL_HORIZONTAL);
    this.modelAngleUnitCombo.setLayoutData(angleData);
    final String[] ANGLES = {"radian"}; //$NON-NLS-1$
    // 現時点では変更不能
    // String[] ANGLES = {"degree", "radian"};
    this.modelAngleUnitCombo.setItems(ANGLES);
    this.modelAngleUnitCombo.setText("radian"); //$NON-NLS-1$
  }

  /**
   * データの角度の単位を指定するコンボボックスの作成
   * 
   * @param group
   */
  private void createDataAngleUnitCombo(Group group) {
    this.dataAngleUnitCombo = new Combo(group, SWT.READ_ONLY);
    final GridData angleData = new GridData(GridData.FILL_HORIZONTAL);
    this.dataAngleUnitCombo.setLayoutData(angleData);
    final String[] ANGLES = {"radian"}; //$NON-NLS-1$
    // 現時点では変更不能
    // String[] ANGLES = {"degree", "radian"};
    this.dataAngleUnitCombo.setItems(ANGLES);
    this.dataAngleUnitCombo.setText("radian"); //$NON-NLS-1$
  }

}