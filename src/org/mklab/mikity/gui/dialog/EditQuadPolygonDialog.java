/*
 * $Id: EditQuadPolygonDialog.java,v 1.7 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.gui.ParameterInputBox;
import org.mklab.mikity.gui.UnitLabel;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLQuadPolygon;


/**
 * 四角形ポリゴンの編集を行うクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.7 $. 2007/11/15
 */
public class EditQuadPolygonDialog {

  private Shell parentShell;
  private Shell sShell;
  private XMLQuadPolygon quad;
  private String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"};

  private String groupName;
  private Group afterGroup;
  private Label primLabel;
  private ParameterInputBox paramX_1, paramY_1, paramZ_1;
  private ParameterInputBox paramX_2, paramY_2, paramZ_2;
  private ParameterInputBox paramX_3, paramY_3, paramZ_3;
  private ParameterInputBox paramX_4, paramY_4, paramZ_4;
  private ParameterInputBox color;
  private ColorComboBox colorCombo;

  private ParameterInputBox newParamX_1, newParamY_1, newParamZ_1;
  private ParameterInputBox newParamX_2, newParamY_2, newParamZ_2;
  private ParameterInputBox newParamX_3, newParamY_3, newParamZ_3;
  private ParameterInputBox newParamX_4, newParamY_4, newParamZ_4;
  private ParameterInputBox paramL_X, paramL_Y, paramL_Z;
  private ParameterInputBox newParamL_X, newParamL_Y, newParamL_Z;
  private ParameterInputBox paramR_X, paramR_Y, paramR_Z;
  private ParameterInputBox newParamR_X, newParamR_Y, newParamR_Z;

  /**
   * コンストラクター
   * @param parentShell 
   * @param quad 
   * @param group 
   */
  public EditQuadPolygonDialog(Shell parentShell, XMLQuadPolygon quad, org.mklab.mikity.xml.model.Group group) {
    this.parentShell = parentShell;
    this.quad = quad;
    this.groupName = group.loadName();
    createSShell();
    detectPrim();
  }

  /**
   * コンストラクター
   */
  public void EditTrianglePolygonDialog() {
    // nothing to do
  }

  /**
   * シェルを開く
   */
  public void open() {
    this.sShell.open();
    Display display = this.sShell.getDisplay();
    while (!this.sShell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * シェルを作成
   */
  private void createSShell() {
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 720));
    this.sShell.setText("四角形ポリゴンの編集");
    this.sShell.setLayout(layout);

    Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText("所属グループ  :  " + this.groupName);
    setGridLayout(groupLabel, 2);

    this.primLabel = new Label(this.sShell, SWT.NONE);
    setGridLayout(this.primLabel, 2);

    Group beforeGroup = new Group(this.sShell, SWT.NONE);
    beforeGroup.setText("変更前");
    setGridLayout(beforeGroup, 1);
    GridLayout beforeLayout = new GridLayout(2, true);
    beforeGroup.setLayout(beforeLayout);

    this.paramX_1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点1 X座標", "0.0");
    this.paramY_1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点1 Y座標", "0.0");
    this.paramZ_1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点1 Z座標", "0.0");
    Label label1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label1, 2);

    this.paramX_2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点2 X座標", "0.0");
    this.paramY_2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点2 Y座標", "0.0");
    this.paramZ_2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点2 Z座標", "0.0");
    Label label2 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label2, 2);
    this.paramX_3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点3 X座標", "0.0");
    this.paramY_3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点3 Y座標", "0.0");
    this.paramZ_3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点3 Z座標", "0.0");
    Label label3 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label3, 2);

    this.paramX_4 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点4 X座標", "0.0");
    this.paramY_4 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点4 Y座標", "0.0");
    this.paramZ_4 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点4 Z座標", "0.0");
    this.color = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "color", "");
    Label label4 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label4, 2);

    this.paramR_X = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "X軸回転", "0.0");
    this.paramR_Y = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Y軸回転", "0.0");
    this.paramR_Z = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Z軸回転", "0.0");
    Label labelR1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelR1, 2);

    this.paramL_X = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "X軸並進", "0.0");
    this.paramL_Y = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Y軸並進", "0.0");
    this.paramL_Z = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Z軸並進", "0.0");
    Label labelL1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelL1, 2);

    this.afterGroup = new Group(this.sShell, SWT.NONE);
    this.afterGroup.setText("変更後");
    setGridLayout(this.afterGroup, 1);
    GridLayout afterLayout = new GridLayout(3, false);
    this.afterGroup.setLayout(afterLayout);

    this.newParamX_1 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");
    this.newParamY_1 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");
    this.newParamZ_1 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");

    Label label5 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5, 3);

    this.newParamX_2 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");
    this.newParamY_2 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");
    this.newParamZ_2 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");

    Label label6 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label6, 3);

    this.newParamX_3 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");
    this.newParamY_3 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");
    this.newParamZ_3 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");

    Label label7 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label7, 3);

    this.newParamX_4 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");
    this.newParamY_4 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");
    this.newParamZ_4 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelLength");

    Label colorLabel4 = new Label(this.afterGroup, SWT.RIGHT);
    colorLabel4.setText("→");
    setGridLayout(colorLabel4, 1);
    this.colorCombo = new ColorComboBox(this.afterGroup, this.COLORS);
    this.colorCombo.createColorCombo();
    Label label8 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label8, 3);

    this.newParamR_X = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelAngle");
    this.newParamR_Y = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelAngle");
    this.newParamR_Z = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(this.afterGroup, "modelAngle");

    Label labelR2 = new Label(afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelR2, 3);

    newParamL_X = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(afterGroup, "modelLength");
    newParamL_Y = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(afterGroup, "modelLength");
    newParamL_Z = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(afterGroup, "modelLength");

    Label labelL2 = new Label(afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelL2, 3);

    // UnitLabel test = new UnitLabel(afterGroup, "modelAngle");

    createButtonComp();
  }

  /**
   * レイアウトマネージャGridLayoutを設定
   * 
   * @param control
   * @param hSpan
   */
  private void setGridLayout(Control control, int hSpan) {
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = hSpan;
    control.setLayoutData(gridData);
  }

  /**
   * 変更を決定するButtonを作成
   */
  private void createButtonComp() {
    final Composite comp = new Composite(sShell, SWT.NONE);
    setGridLayout(comp, 2);

    GridLayout compLayout = new GridLayout(2, true);
    comp.setLayout(compLayout);

    Button okButton = new Button(comp, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText("変更");

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        // 数字以外が入っていないかを判断
        if (Check()) {
          MessageBox mesBox = new MessageBox(sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage("変更します");
          mesBox.setText("確認");
          int result = mesBox.open();
          if (result == SWT.YES) {
            setParam();
            sShell.close();
          }
        } else {
          MessageBox mgb = new MessageBox(sShell, SWT.ICON_WARNING);
          mgb.setMessage("パラメータ数字以外が入っています。\n 入力しなおして下さい。");
          mgb.setText("Warning!!");
          mgb.open();
        }
      }
    });

    Button cancelButton = new Button(comp, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText("キャンセル");

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        MessageBox mesBox = new MessageBox(sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        mesBox.setMessage("変更を中止して終了します");
        mesBox.setText("確認");
        int result = mesBox.open();
        if (result == SWT.YES) {
          sShell.close();
        }
      }
    });
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  private boolean Check() {
    if (newParamX_1.checkParam() == false) {
      return false;
    }
    if (newParamY_1.checkParam() == false) {
      return false;
    }
    if (newParamZ_1.checkParam() == false) {
      return false;
    }
    if (newParamX_2.checkParam() == false) {
      return false;
    }
    if (newParamY_2.checkParam() == false) {
      return false;
    }
    if (newParamZ_2.checkParam() == false) {
      return false;
    }
    if (newParamX_3.checkParam() == false) {
      return false;
    }
    if (newParamY_3.checkParam() == false) {
      return false;
    }
    if (newParamZ_3.checkParam() == false) {
      return false;
    }
    if (newParamX_4.checkParam() == false) {
      return false;
    }
    if (newParamY_4.checkParam() == false) {
      return false;
    }
    if (newParamZ_4.checkParam() == false) {
      return false;
    }
    if (newParamR_X.checkParam() == false) {
      return false;
    }
    if (newParamR_Y.checkParam() == false) {
      return false;
    }
    if (newParamR_Z.checkParam() == false) {
      return false;
    }
    if (newParamL_X.checkParam() == false) {
      return false;
    }
    if (newParamL_Y.checkParam() == false) {
      return false;
    }
    if (newParamL_Z.checkParam() == false) {
      return false;
    }
    return true;
  }

  /**
   * パラメータを変更する
   */
  private void setParam() {
    Location[] newLoc = new Location[4];

    newLoc[0] = new Location(newParamX_1.getFloatValue(), newParamY_1.getFloatValue(), newParamZ_1.getFloatValue());
    newLoc[1] = new Location(newParamX_2.getFloatValue(), newParamY_2.getFloatValue(), newParamZ_2.getFloatValue());
    newLoc[2] = new Location(newParamX_3.getFloatValue(), newParamY_3.getFloatValue(), newParamZ_3.getFloatValue());
    newLoc[3] = new Location(newParamX_4.getFloatValue(), newParamY_4.getFloatValue(), newParamZ_4.getFloatValue());

    String newColor = colorCombo.getColorComboBox().getText();

    quad.setPointLocations(newLoc);
    quad.setColor(newColor);
    quad.setRotation(new Rotation(newParamR_X.getFloatValue(), newParamR_Y.getFloatValue(), newParamR_Z.getFloatValue()));
    quad.setLocation(new Location(newParamL_X.getFloatValue(), newParamL_Y.getFloatValue(), newParamL_Z.getFloatValue()));
  }

  /**
   * 各頂点の座標値を色を入れる　変更後の欄にはデフォルトで変更前の値を入力
   */
  private void detectPrim() {
    paramX_1.setText("" + quad.loadPointLocationX(0));
    paramY_1.setText("" + quad.loadPointLocationY(0));
    paramZ_1.setText("" + quad.loadPointLocationZ(0));
    newParamX_1.setText("" + quad.loadPointLocationX(0));
    newParamY_1.setText("" + quad.loadPointLocationY(0));
    newParamZ_1.setText("" + quad.loadPointLocationZ(0));

    paramX_2.setText("" + quad.loadPointLocationX(1));
    paramY_2.setText("" + quad.loadPointLocationY(1));
    paramZ_2.setText("" + quad.loadPointLocationZ(1));
    newParamX_2.setText("" + quad.loadPointLocationX(1));
    newParamY_2.setText("" + quad.loadPointLocationY(1));
    newParamZ_2.setText("" + quad.loadPointLocationZ(1));

    paramX_3.setText("" + quad.loadPointLocationX(2));
    paramY_3.setText("" + quad.loadPointLocationY(2));
    paramZ_3.setText("" + quad.loadPointLocationZ(2));
    newParamX_3.setText("" + quad.loadPointLocationX(2));
    newParamY_3.setText("" + quad.loadPointLocationY(2));
    newParamZ_3.setText("" + quad.loadPointLocationZ(2));

    paramX_4.setText("" + quad.loadPointLocationX(3));
    paramY_4.setText("" + quad.loadPointLocationY(3));
    paramZ_4.setText("" + quad.loadPointLocationZ(3));
    newParamX_4.setText("" + quad.loadPointLocationX(3));
    newParamY_4.setText("" + quad.loadPointLocationY(3));
    newParamZ_4.setText("" + quad.loadPointLocationZ(3));
    color.setText(quad.loadColor());
    colorCombo.getColorComboBox().setText(quad.loadColor());

    if (quad.loadRotation() != null) {
      paramR_X.setText("" + quad.loadRotation().loadXrotate());
      paramR_Y.setText("" + quad.loadRotation().loadYrotate());
      paramR_Z.setText("" + quad.loadRotation().loadZrotate());
      newParamR_X.setText("" + quad.loadRotation().loadXrotate());
      newParamR_Y.setText("" + quad.loadRotation().loadYrotate());
      newParamR_Z.setText("" + quad.loadRotation().loadZrotate());
    } else {
      paramR_X.setText("" + 0.0);
      paramR_Y.setText("" + 0.0);
      paramR_Z.setText("" + 0.0);
      newParamR_X.setText("" + 0.0);
      newParamR_Y.setText("" + 0.0);
      newParamR_Z.setText("" + 0.0);
    }

    if (quad.loadLocation() != null) {
      paramL_X.setText("" + quad.loadLocation().loadX());
      paramL_Y.setText("" + quad.loadLocation().loadY());
      paramL_Z.setText("" + quad.loadLocation().loadZ());
      newParamL_X.setText("" + quad.loadLocation().loadX());
      newParamL_Y.setText("" + quad.loadLocation().loadY());
      newParamL_Z.setText("" + quad.loadLocation().loadZ());
    } else {
      paramL_X.setText("" + 0.0);
      paramL_Y.setText("" + 0.0);
      paramL_Z.setText("" + 0.0);
      newParamL_X.setText("" + 0.0);
      newParamL_Y.setText("" + 0.0);
      newParamL_Z.setText("" + 0.0);
    }

  }
}
