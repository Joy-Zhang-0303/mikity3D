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
import org.mklab.mikity.xml.Config;
import org.mklab.mikity.xml.config.Background;
import org.mklab.mikity.xml.config.DataUnit;
import org.mklab.mikity.xml.config.Light;
import org.mklab.mikity.xml.config.ModelUnit;
import org.mklab.mikity.xml.config.View;


/**
 * <ConfigDialog>の設定を行うクラス
 * @author miki
 * @version $Revision: 1.1 $.2005/02/01
 */
public class ConfigDialog {

  private Shell sShell = null;
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

  private Config config;

  /**
   * コンストラクター
   * 
   * @param parentShell 親シェル
   * @param config 設定
   */
  public ConfigDialog(Shell parentShell, Config config) {
    this.parentShell = parentShell;
    this.config = config;
    createSShell();
  }

  /**
   * Shellの作成
   */
  private void createSShell() {
    sShell = new Shell(parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    sShell.setText("ConfigDialog");
    sShell.setSize(new org.eclipse.swt.graphics.Point(400, 450));

    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    sShell.setLayout(layout);

    createEditGroup();
    createMainButtonComp();
  }

  /**
   * <config>の中身を編集できるグループを作成する
   */
  private void createEditGroup() {
    editGroup = new Group(sShell, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    editGroup.setLayout(layout);
    GridData data = new GridData(GridData.FILL_BOTH);
    editGroup.setLayoutData(data);
    editGroup.setText("Configの編集");

    //取り扱うデータの単位の設定を行うグループ
    Group unitGroup = new Group(editGroup, SWT.NONE);
    GridLayout unitLayout = new GridLayout();
    unitLayout.numColumns = 4;
    unitGroup.setText("単位設定");
    GridData unitData = new GridData(GridData.FILL_HORIZONTAL);
    unitData.horizontalSpan = 2;
    unitGroup.setLayoutData(unitData);
    unitGroup.setLayout(unitLayout);

    //モデルの単位
    Label modelUnitLabel = new Label(unitGroup, SWT.NONE);
    GridData modelData = new GridData(GridData.FILL_HORIZONTAL);
    modelData.horizontalSpan = 4;
    modelUnitLabel.setText("・モデルの単位");
    modelUnitLabel.setLayoutData(modelData);

    Label modelAngleLabel = new Label(unitGroup, SWT.NONE);
    modelAngleLabel.setText(" 角度の単位");
    createModelAngleUnitCombo(unitGroup);

    Label modelLengthLabel = new Label(unitGroup, SWT.NONE);
    modelLengthLabel.setText(" 長さの単位");
    createModelLengthUnitCombo(unitGroup);

    //時系列データの単位
    Label dataUnitLabel = new Label(unitGroup, SWT.NONE);
    GridData dataData = new GridData(GridData.FILL_HORIZONTAL);
    dataData.horizontalSpan = 4;
    dataUnitLabel.setText("・時系列データの単位");
    dataUnitLabel.setLayoutData(dataData);

    Label dataAngleLabel = new Label(unitGroup, SWT.NONE);
    dataAngleLabel.setText(" 角度の単位");
    createDataAngleUnitCombo(unitGroup);

    Label dataLengthLabel = new Label(unitGroup, SWT.NONE);
    dataLengthLabel.setText(" 長さの単位");
    createDataLengthUnitCombo(unitGroup);

    //光源の位置の指定
    Group lightPointGroup = new Group(editGroup, SWT.NONE);
    lightPointGroup.setText("光源の位置の指定");

    GridData lightData = new GridData(GridData.FILL_HORIZONTAL);
    lightData.horizontalSpan = 2;
    GridLayout lightLayout = new GridLayout(7, true);
    lightPointGroup.setLayout(lightLayout);
    lightPointGroup.setLayoutData(lightData);

    Label lKakko = new Label(lightPointGroup, SWT.NONE);
    lKakko.setText("・平行移動：(x,y,z)で指定してください。");
    GridData xData = new GridData(GridData.FILL_HORIZONTAL);
    xData.horizontalSpan = 7;
    lKakko.setLayoutData(xData);

    lightX = new ParameterInputBox(lightPointGroup, SWT.NONE, "  (", "0.2");
    lightY = new ParameterInputBox(lightPointGroup, SWT.NONE, ",  ", "-0.8");
    lightZ = new ParameterInputBox(lightPointGroup, SWT.NONE, ",  ", "-0.8");

    Label kakko = new Label(lightPointGroup, SWT.NONE);
    GridData labelData = new GridData();
    labelData.widthHint = 10;
    kakko.setLayoutData(labelData);
    kakko.setText(")");

    //視点の位置の指定
    Group viewPointGroup = new Group(editGroup, SWT.NONE);
    viewPointGroup.setText("視点の位置の指定");

    GridData viewData = new GridData(GridData.FILL_HORIZONTAL);
    viewData.horizontalSpan = 2;
    GridLayout viewLayout = new GridLayout(7, true);
    viewPointGroup.setLayout(viewLayout);
    viewPointGroup.setLayoutData(viewData);

    Label vRollLabel = new Label(viewPointGroup, SWT.NONE);
    vRollLabel.setText("・視点の向き ：(x,y,z)で回転角度を指定してください。[°]");
    GridData vRollData = new GridData(GridData.FILL_HORIZONTAL);
    vRollData.horizontalSpan = 7;
    vRollLabel.setLayoutData(vRollData);

    viewXrotate = new ParameterInputBox(viewPointGroup, SWT.NONE, "  (", "-0.2");
    viewYrotate = new ParameterInputBox(viewPointGroup, SWT.NONE, ",  ", "0.0");
    viewZrotate = new ParameterInputBox(viewPointGroup, SWT.NONE, ",  ", "0.0");

    Label vKakko2 = new Label(viewPointGroup, SWT.NONE);
    GridData vKakkoData2 = new GridData();
    vKakko2.setLayoutData(vKakkoData2);
    vKakko2.setText(")");

    Label vLabel = new Label(viewPointGroup, SWT.NONE);
    vLabel.setText("・平行移動：(x,y,z)で指定してください。");
    GridData vData = new GridData(GridData.FILL_HORIZONTAL);
    vData.horizontalSpan = 7;
    vLabel.setLayoutData(vData);

    viewX = new ParameterInputBox(viewPointGroup, SWT.NONE, "  (", "0.0");
    viewY = new ParameterInputBox(viewPointGroup, SWT.NONE, ",  ", "0.3");
    viewZ = new ParameterInputBox(viewPointGroup, SWT.NONE, ",  ", "1.0");

    Label vKakko = new Label(viewPointGroup, SWT.NONE);
    GridData vKakkoData = new GridData();
    vKakko.setLayoutData(vKakkoData);
    vKakko.setText(")");

    //背景色の選択
    Label setColorLabel = new Label(editGroup, SWT.NONE);
    setColorLabel.setText("・背景色を選択");
    createColorCombo();

    getParameter();

  }

  /**
   * XMLファイルから値を読み込む
   */
  private void getParameter() {
    if (config.loadLight() != null) {
      Light light = config.loadLight();
      lightX.setText("" + light.loadX());
      lightY.setText("" + light.loadY());
      lightZ.setText("" + light.loadZ());
    }
    if (config.loadView() != null) {
      View view = config.loadView();
      viewX.setText("" + view.loadX());
      viewY.setText("" + view.loadY());
      viewZ.setText("" + view.loadZ());
      viewXrotate.setText("" + view.loadXrotate());
      viewYrotate.setText("" + view.loadYrotate());
      viewZrotate.setText("" + view.loadZrotate());
    }
    if (config.loadModelUnit() != null) {
      if (config.loadModelUnit().loadAngle() != null) {
        modelAngleUnitCombo.setText(config.loadModelUnit().loadAngle());
      }
      if (config.loadModelUnit().loadLength() != null) {
        modelLengthUnitCombo.setText(config.loadModelUnit().loadLength());
      }
    }
    if (config.loadDataUnit() != null) {
      if (config.loadDataUnit().loadAngle() != null) {
        dataAngleUnitCombo.setText(config.loadDataUnit().loadAngle());
      }
      if (config.loadDataUnit().loadLength() != null) {
        dataLengthUnitCombo.setText(config.loadDataUnit().loadLength());
      }
    }
    if (config.loadBackground() == null) {
      colorCombo.setText("white");
    } else {
      colorCombo.setText(config.loadBackground().loadColor());
    }
  }

  /**
   * 視点座標に数字以外の文字が入っていたときに出すメッセージボックスの作成
   */
  private void createMesBox() {
    MessageBox mesBox = new MessageBox(sShell, SWT.ICON_WARNING);
    mesBox.setMessage("数字以外が入力されてます。入力しなおしてください。");
    mesBox.setText("WARNING!!");
    mesBox.open();
  }

  /**
   * メインボタンのあるコンポジットを作成する
   */
  private void createMainButtonComp() {
    Composite comp = new Composite(sShell, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    comp.setLayout(layout);

    Button okButton = new Button(comp, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText("変更して終了");

    Button cancelButton = new Button(comp, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText("キャンセル");

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        sShell.close();
      }
    });

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        //MainWindow.setBGColor(colorCombo.getText());

        //もし適当な値ならば採用
        if (check()) {
          //Vector3f viewVector = new
          // Vector3f(lightX.getFloatValue(),lightY.getFloatValue(),lightZ.getFloatValue());
          // MainWindow.setLightVector(new Vector3f(lightX.getFloatValue(),
          // lightY.getFloatValue(), lightZ.getFloatValue()));
          setParameter();
          sShell.close();
        } else {
          createMesBox();
        }
      }

    });
  }

  /**
   * 入力したパラメータの値をXMLファイルに書き込む
   */
  private void setParameter() {
    Light light = new Light();
    light.setX(lightX.getFloatValue());
    light.setY(lightY.getFloatValue());
    light.setZ(lightZ.getFloatValue());
    config.setLight(light);
    View view = new View();
    view.setX(viewX.getFloatValue());
    view.setY(viewY.getFloatValue());
    view.setZ(viewZ.getFloatValue());
    view.setXrotate(viewXrotate.getDoubleValue());
    view.setYrotate(viewYrotate.getDoubleValue());
    view.setZrotate(viewZrotate.getDoubleValue());
    config.setView(view);

    if (config.loadBackground() == null) {
      if (colorCombo.getText() != "white") {
        Background background = new Background();
        background.setColor(colorCombo.getText());
        config.setBackground(background);
      }
    } else {
      config.loadBackground().setColor(colorCombo.getText());
    }

    if (config.loadModelUnit() == null) {
      if (modelAngleUnitCombo.getText() != "radian") {
        ModelUnit modelUnit = new ModelUnit();
        modelUnit.setAngle(modelAngleUnitCombo.getText());
        modelUnit.setLength(modelLengthUnitCombo.getText());
        config.setModelUnit(modelUnit);
      }
    } else {
      config.loadModelUnit().setAngle(modelAngleUnitCombo.getText());
      config.loadModelUnit().setLength(modelLengthUnitCombo.getText());
    }

    if (config.loadDataUnit() == null) {
      if (dataAngleUnitCombo.getText() != "radian") {
        DataUnit dataUnit = new DataUnit();
        dataUnit.setAngle(dataAngleUnitCombo.getText());
        dataUnit.setLength(dataLengthUnitCombo.getText());
        config.setDataUnit(dataUnit);
      }
    } else {
      config.loadDataUnit().setAngle(dataAngleUnitCombo.getText());
      config.loadDataUnit().setLength(dataLengthUnitCombo.getText());
    }

  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  private boolean check() {
    if (lightX.checkParam() == false) {
      return false;
    }
    if (lightY.checkParam() == false) {
      return false;
    }
    if (lightZ.checkParam() == false) {
      return false;
    }
    if (viewX.checkParam() == false) {
      return false;
    }
    if (viewY.checkParam() == false) {
      return false;
    }
    if (viewZ.checkParam() == false) {
      return false;
    }
    if (viewXrotate.checkParam() == false) {
      return false;
    }
    if (viewYrotate.checkParam() == false) {
      return false;
    }
    if (viewZrotate.checkParam() == false) {
      return false;
    }
    return true;
  }

  /**
   * Shellを開く
   */
  public void open() {
    sShell.open();
    Display display = sShell.getDisplay();
    while (!sShell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * 背景の色を指定するコンボボックスの作成
   */
  private void createColorCombo() {
    colorCombo = new Combo(editGroup, SWT.READ_ONLY);
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    colorCombo.setLayoutData(gridData);
    String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"};
    colorCombo.setItems(COLORS);
  }

  /**
   * モデルの長さの単位を指定するコンボボックスの作成
   * @param group
   */
  private void createModelLengthUnitCombo(Group group) {
    modelLengthUnitCombo = new Combo(group, SWT.READ_ONLY);
    GridData lengthData = new GridData(GridData.FILL_HORIZONTAL);
    modelLengthUnitCombo.setLayoutData(lengthData);
    String[] LENGTH = {"m"};
    //現時点では変更不能。増やしたい。
    //String[] LENGTH = {"m", "cm", "mm"};
    modelLengthUnitCombo.setItems(LENGTH);
    modelLengthUnitCombo.setText("m");
  }

  /**
   * データの長さの単位を指定するコンボボックスの作成
   * @param group
   */
  private void createDataLengthUnitCombo(Group group) {
    dataLengthUnitCombo = new Combo(group, SWT.READ_ONLY);
    GridData lengthData = new GridData(GridData.FILL_HORIZONTAL);
    dataLengthUnitCombo.setLayoutData(lengthData);
    String[] LENGTH = {"m"};
    //現時点では変更不能。増やしたい。
    //String[] LENGTH = {"m", "cm", "mm"};
    dataLengthUnitCombo.setItems(LENGTH);
    dataLengthUnitCombo.setText("m");
  }

  /**
   * モデルの角度の単位を指定するコンボボックスの作成
   * @param group
   */
  private void createModelAngleUnitCombo(Group group) {
    modelAngleUnitCombo = new Combo(group, SWT.READ_ONLY);
    GridData angleData = new GridData(GridData.FILL_HORIZONTAL);
    modelAngleUnitCombo.setLayoutData(angleData);
    String[] ANGLES = {"radian"};
    //現時点では変更不能
    //String[] ANGLES = {"degree", "radian"};
    modelAngleUnitCombo.setItems(ANGLES);
    modelAngleUnitCombo.setText("radian");
  }

  /**
   * データの角度の単位を指定するコンボボックスの作成
   * @param group
   */
  private void createDataAngleUnitCombo(Group group) {
    dataAngleUnitCombo = new Combo(group, SWT.READ_ONLY);
    GridData angleData = new GridData(GridData.FILL_HORIZONTAL);
    dataAngleUnitCombo.setLayoutData(angleData);
    String[] ANGLES = {"radian"};
    //現時点では変更不能
    //String[] ANGLES = {"degree", "radian"};
    dataAngleUnitCombo.setItems(ANGLES);
    dataAngleUnitCombo.setText("radian");
  }

}