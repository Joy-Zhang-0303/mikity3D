/*
 * $Id: AddTrianglePolygonDialog.java,v 1.5 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.gui.ParameterInputBox;
import org.mklab.mikity.gui.UnitLabel;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


/**
 * 三角形ポリゴンを作るためのダイアログのクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.5 $. 2008/01/28
 */
public class AddTrianglePolygonDialog {

  private Shell parentShell;
  Shell sShell;

  private ParameterInputBox paramX_1;
  private ParameterInputBox paramY_1;
  private ParameterInputBox paramZ_1;
  private ParameterInputBox paramX_2;
  private ParameterInputBox paramY_2;
  private ParameterInputBox paramZ_2;
  private ParameterInputBox paramX_3;
  private ParameterInputBox paramY_3;
  private ParameterInputBox paramZ_3;
  private ParameterInputBox rotX;
  private ParameterInputBox rotY;
  private ParameterInputBox rotZ;
  private ParameterInputBox locX;
  private ParameterInputBox locY;
  private ParameterInputBox locZ;

  private Group group;
  private String angleUnit;
  private String lengthUnit;
  private Combo colorCombo;

  /**
   * コンストラクター
   * 
   * @param parentShell 親シェル
   * @param group グループ
   */
  public AddTrianglePolygonDialog(Shell parentShell, Group group) {
    this.parentShell = parentShell;
    this.group = group;
    this.angleUnit = UnitLabel.getUnit("modelAngle"); //$NON-NLS-1$
    this.lengthUnit = UnitLabel.getUnit("modelLength"); //$NON-NLS-1$
    createSShell();
  }

  /**
   * シェルの作成
   */
  private void createSShell() {
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(300, 570));
    this.sShell.setText("三角形ポリゴンの追加");
    this.sShell.setLayout(layout);

    Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText("所属グループ  :  " + this.group.loadName());
    GridData gLabelData = new GridData(GridData.FILL_HORIZONTAL);
    gLabelData.horizontalSpan = 3;
    groupLabel.setLayoutData(gLabelData);

    GridData vertexData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group vertexGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    vertexGroup.setText("三角形ポリゴンの頂点座標");
    layout = new GridLayout();
    layout.numColumns = 3;
    vertexGroup.setLayout(layout);
    vertexData = new GridData(GridData.FILL_HORIZONTAL);
    vertexData.horizontalSpan = 3;
    vertexGroup.setLayoutData(vertexData);

    this.paramX_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点1 X座標", "0.3"); //$NON-NLS-2$
    this.paramY_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点1 Y座標", "0.3"); //$NON-NLS-2$
    this.paramZ_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点1 Z座標", "0.0"); //$NON-NLS-2$
    Label label1 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label1, 2);

    this.paramX_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点2 X座標", "-0.3"); //$NON-NLS-2$
    this.paramY_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点2 Y座標", "0.3"); //$NON-NLS-2$
    this.paramZ_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点2 Z座標", "0.0"); //$NON-NLS-2$
    Label label2 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label2, 2);
    this.paramX_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点3 X座標", "-0.3"); //$NON-NLS-2$
    this.paramY_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点3 Y座標", "-0.3"); //$NON-NLS-2$
    this.paramZ_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点3 Z座標", "0.0"); //$NON-NLS-2$

    GridData labelData2 = new GridData(GridData.FILL_HORIZONTAL);
    Label colorLabel = new Label(this.sShell, SWT.RIGHT);
    colorLabel.setText("color"); //$NON-NLS-1$
    colorLabel.setLayoutData(labelData2);
    createColorCombo();

    GridData rotData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group rotateGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    rotateGroup.setText("回転移動");
    layout = new GridLayout();
    layout.numColumns = 3;
    rotateGroup.setLayout(layout);
    rotData = new GridData(GridData.FILL_HORIZONTAL);
    rotData.horizontalSpan = 3;
    rotateGroup.setLayoutData(rotData);

    this.rotX = new ParameterInputBox(rotateGroup, SWT.NONE, "X軸周り", "0.0"); //$NON-NLS-2$
    Label unitLabelRX = new Label(rotateGroup, SWT.NONE);
    unitLabelRX.setText(this.angleUnit + " "); //$NON-NLS-1$
    this.rotY = new ParameterInputBox(rotateGroup, SWT.NONE, "Y軸周り", "0.0"); //$NON-NLS-2$
    Label unitLabelRY = new Label(rotateGroup, SWT.NONE);
    unitLabelRY.setText(this.angleUnit + " "); //$NON-NLS-1$
    this.rotZ = new ParameterInputBox(rotateGroup, SWT.NONE, "Z軸周り", "0.0"); //$NON-NLS-2$
    Label unitLabelRZ = new Label(rotateGroup, SWT.NONE);
    unitLabelRZ.setText(this.angleUnit + " "); //$NON-NLS-1$

    GridData locData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group locationGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    locationGroup.setText("平行移動");
    layout = new GridLayout();
    layout.numColumns = 3;
    locationGroup.setLayout(layout);
    locData = new GridData(GridData.FILL_HORIZONTAL);
    locData.horizontalSpan = 3;
    locationGroup.setLayoutData(locData);

    this.locX = new ParameterInputBox(locationGroup, SWT.NONE, "X軸方向", "0"); //$NON-NLS-2$
    Label unitLabelLX = new Label(locationGroup, SWT.NONE);
    unitLabelLX.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locY = new ParameterInputBox(locationGroup, SWT.NONE, "Y軸方向", "0"); //$NON-NLS-2$
    Label unitLabelLY = new Label(locationGroup, SWT.NONE);
    unitLabelLY.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locZ = new ParameterInputBox(locationGroup, SWT.NONE, "Z軸方向", "0"); //$NON-NLS-2$
    Label unitLabelLZ = new Label(locationGroup, SWT.NONE);
    unitLabelLZ.setText(this.lengthUnit + " "); //$NON-NLS-1$

    createButtonComp();
  }

  /**
   * 完了ボタンを生成する
   */
  private void createButtonComp() {

    Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText("三角形ポリゴンを追加");

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (Check()) {
          // dc.checkDuplication(,.getLocation,group);
          addPolygon();
          AddTrianglePolygonDialog.this.sShell.close();
        } else {
          MessageBox mgb = new MessageBox(AddTrianglePolygonDialog.this.sShell, SWT.ICON_WARNING);
          mgb.setMessage("パラメータ数字以外が入っています。\n 入力しなおして下さい。");
          mgb.setText("Warning!!");
        }
      }
    });

    Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(" キャンセル ");

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        MessageBox mesBox = new MessageBox(AddTrianglePolygonDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        mesBox.setMessage("変更を中止して終了します");
        mesBox.setText("確認");
        int result = mesBox.open();
        if (result == SWT.YES) {
          AddTrianglePolygonDialog.this.sShell.close();
        }
      }
    });
  }

  /**
   * ポリゴンの生成
   */
  void addPolygon() {
    XMLTrianglePolygon triangle = new XMLTrianglePolygon();
    Rotation rot = new Rotation();
    Location loc = new Location();

    Location vertex1 = new Location(this.paramX_1.getFloatValue(), this.paramY_1.getFloatValue(), this.paramZ_1.getFloatValue());
    Location vertex2 = new Location(this.paramX_2.getFloatValue(), this.paramY_2.getFloatValue(), this.paramZ_2.getFloatValue());
    Location vertex3 = new Location(this.paramX_3.getFloatValue(), this.paramY_3.getFloatValue(), this.paramZ_3.getFloatValue());
    triangle.setPointLocations(vertex1, vertex2, vertex3);
    if (getRot(rot) != null) {
      triangle.setRotation(getRot(rot));
    }
    if (getLoc(loc) != null) {
      triangle.setLocation(getLoc(loc));
    }
    triangle.setColor(this.colorCombo.getText());
    this.group.addXMLTrianglePolygon(triangle);
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
   * Rotationの値を設定 param Rotation return Rotation
   * 
   * @param rot
   * @return rot
   */
  private Rotation getRot(Rotation rot) {
    if (this.rotX.getFloatValue() == 0 && this.rotY.getFloatValue() == 0 && this.rotZ.getFloatValue() == 0) {
      return null;
    }
    rot.setXrotate(this.rotX.getFloatValue());
    rot.setYrotate(this.rotY.getFloatValue());
    rot.setZrotate(this.rotZ.getFloatValue());
    return rot;

  }

  /**
   * Locationの値を設定 param Location return Location
   * 
   * @param loc
   * @return loc
   */
  private Location getLoc(Location loc) {
    if (this.locX.getFloatValue() == 0 && this.locY.getFloatValue() == 0 && this.locZ.getFloatValue() == 0) {
      return null;
    }
    loc.setX(this.locX.getFloatValue());
    loc.setY(this.locY.getFloatValue());
    loc.setZ(this.locZ.getFloatValue());
    return loc;
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean Check() {
    if (this.paramX_1.checkParam() == false) {
      return false;
    }
    if (this.paramY_1.checkParam() == false) {
      return false;
    }
    if (this.paramZ_1.checkParam() == false) {
      return false;
    }
    if (this.paramX_2.checkParam() == false) {
      return false;
    }
    if (this.paramY_2.checkParam() == false) {
      return false;
    }
    if (this.paramZ_2.checkParam() == false) {
      return false;
    }
    if (this.paramX_3.checkParam() == false) {
      return false;
    }
    if (this.paramY_3.checkParam() == false) {
      return false;
    }
    if (this.paramZ_3.checkParam() == false) {
      return false;
    }
    if (this.rotX.checkParam() == false) {
      return false;
    }
    if (this.rotY.checkParam() == false) {
      return false;
    }
    if (this.rotZ.checkParam() == false) {
      return false;
    }
    if (this.locX.checkParam() == false) {
      return false;
    }
    if (this.locY.checkParam() == false) {
      return false;
    }
    if (this.locZ.checkParam() == false) {
      return false;
    }
    return true;
  }

  /**
   * コンボボックス colorCombo プリミティブの色を選択
   */
  private void createColorCombo() {
    this.colorCombo = new Combo(this.sShell, SWT.READ_ONLY);
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    this.colorCombo.setLayoutData(gridData);
    String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
    this.colorCombo.setItems(COLORS);
    this.colorCombo.setText("red"); //$NON-NLS-1$
  }

  /**
   * shellを開く
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
}
