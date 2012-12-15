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
    final GridLayout layout1 = new GridLayout();
    layout1.numColumns = 2;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(300, 570));
    this.sShell.setText(Messages.getString("AddTrianglePolygonDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout1);

    final Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("AddTrianglePolygonDialog.1") + this.group.loadName()); //$NON-NLS-1$
    GridData gLabelData = new GridData(GridData.FILL_HORIZONTAL);
    gLabelData.horizontalSpan = 3;
    groupLabel.setLayoutData(gLabelData);

    GridData vertexData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group vertexGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    vertexGroup.setText(Messages.getString("AddTrianglePolygonDialog.2")); //$NON-NLS-1$
    GridLayout layout2 = new GridLayout();
    layout2.numColumns = 3;
    vertexGroup.setLayout(layout2);
    vertexData = new GridData(GridData.FILL_HORIZONTAL);
    vertexData.horizontalSpan = 3;
    vertexGroup.setLayoutData(vertexData);

    this.paramX_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddTrianglePolygonDialog.3"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramY_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddTrianglePolygonDialog.4"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramZ_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddTrianglePolygonDialog.5"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label1 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label1, 2);

    this.paramX_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddTrianglePolygonDialog.6"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramY_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddTrianglePolygonDialog.7"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramZ_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddTrianglePolygonDialog.8"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label2 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label2, 2);
    this.paramX_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddTrianglePolygonDialog.9"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramY_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddTrianglePolygonDialog.10"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramZ_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddTrianglePolygonDialog.11"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$

    final GridData labelData2 = new GridData(GridData.FILL_HORIZONTAL);
    final Label colorLabel = new Label(this.sShell, SWT.RIGHT);
    colorLabel.setText("color"); //$NON-NLS-1$
    colorLabel.setLayoutData(labelData2);
    createColorCombo();

    GridData rotData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group rotateGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    rotateGroup.setText(Messages.getString("AddTrianglePolygonDialog.12")); //$NON-NLS-1$
    GridLayout layout3 = new GridLayout();
    layout3.numColumns = 3;
    rotateGroup.setLayout(layout3);
    rotData = new GridData(GridData.FILL_HORIZONTAL);
    rotData.horizontalSpan = 3;
    rotateGroup.setLayoutData(rotData);

    this.rotX = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.13"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRX = new Label(rotateGroup, SWT.NONE);
    unitLabelRX.setText(this.angleUnit + " "); //$NON-NLS-1$
    this.rotY = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.14"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRY = new Label(rotateGroup, SWT.NONE);
    unitLabelRY.setText(this.angleUnit + " "); //$NON-NLS-1$
    this.rotZ = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.15"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRZ = new Label(rotateGroup, SWT.NONE);
    unitLabelRZ.setText(this.angleUnit + " "); //$NON-NLS-1$

    GridData locData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group locationGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    locationGroup.setText(Messages.getString("AddTrianglePolygonDialog.16")); //$NON-NLS-1$
    final GridLayout layout4 = new GridLayout();
    layout4.numColumns = 3;
    locationGroup.setLayout(layout4);
    locData = new GridData(GridData.FILL_HORIZONTAL);
    locData.horizontalSpan = 3;
    locationGroup.setLayoutData(locData);

    this.locX = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.17"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLX = new Label(locationGroup, SWT.NONE);
    unitLabelLX.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locY = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.18"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLY = new Label(locationGroup, SWT.NONE);
    unitLabelLY.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locZ = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.19"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLZ = new Label(locationGroup, SWT.NONE);
    unitLabelLZ.setText(this.lengthUnit + " "); //$NON-NLS-1$

    createButtonComp();
  }

  /**
   * 完了ボタンを生成する
   */
  private void createButtonComp() {
    final Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("AddTrianglePolygonDialog.20")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (Check()) {
          // dc.checkDuplication(,.getLocation,group);
          addPolygon();
          AddTrianglePolygonDialog.this.sShell.close();
        } else {
          final MessageBox mgb = new MessageBox(AddTrianglePolygonDialog.this.sShell, SWT.ICON_WARNING);
          mgb.setMessage(Messages.getString("AddTrianglePolygonDialog.21")); //$NON-NLS-1$
          mgb.setText(Messages.getString("AddTrianglePolygonDialog.22")); //$NON-NLS-1$
        }
      }
    });

    final Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("AddTrianglePolygonDialog.23")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        final MessageBox mesBox = new MessageBox(AddTrianglePolygonDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        mesBox.setMessage(Messages.getString("AddTrianglePolygonDialog.24")); //$NON-NLS-1$
        mesBox.setText(Messages.getString("AddTrianglePolygonDialog.25")); //$NON-NLS-1$
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
    final XMLTrianglePolygon triangle = new XMLTrianglePolygon();
    final Rotation rot = new Rotation();
    final Location loc = new Location();

    final Location vertex1 = new Location(this.paramX_1.getFloatValue(), this.paramY_1.getFloatValue(), this.paramZ_1.getFloatValue());
    final Location vertex2 = new Location(this.paramX_2.getFloatValue(), this.paramY_2.getFloatValue(), this.paramZ_2.getFloatValue());
    final Location vertex3 = new Location(this.paramX_3.getFloatValue(), this.paramY_3.getFloatValue(), this.paramZ_3.getFloatValue());
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
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
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
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    this.colorCombo.setLayoutData(gridData);
    final String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
    this.colorCombo.setItems(COLORS);
    this.colorCombo.setText("red"); //$NON-NLS-1$
  }

  /**
   * shellを開く
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
}
