/*
 * $Id: AddQuadPolygonDialog.java,v 1.4 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

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
import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.model.xml.model.Location;
import org.mklab.mikity.model.xml.model.Rotation;
import org.mklab.mikity.model.xml.model.XMLQuadPolygon;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * 四角形ポリゴンを作るためのダイアログのクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2008/01/28
 */
public class AddQuadPolygonDialog {

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
  private ParameterInputBox paramX_4;
  private ParameterInputBox paramY_4;
  private ParameterInputBox paramZ_4;
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
  public AddQuadPolygonDialog(Shell parentShell, Group group) {
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
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(300, 650));
    this.sShell.setText(Messages.getString("AddQuadPolygonDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout1);

    final Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("AddQuadPolygonDialog.1") + this.group.loadName()); //$NON-NLS-1$
    final GridData gLabelData = new GridData(GridData.FILL_HORIZONTAL);
    gLabelData.horizontalSpan = 3;
    groupLabel.setLayoutData(gLabelData);

    GridData vertexData = new GridData(GridData.FILL_HORIZONTAL);
    final org.eclipse.swt.widgets.Group vertexGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    vertexGroup.setText(Messages.getString("AddQuadPolygonDialog.2")); //$NON-NLS-1$
    final GridLayout layout2 = new GridLayout();
    layout2.numColumns = 3;
    vertexGroup.setLayout(layout2);
    vertexData = new GridData(GridData.FILL_HORIZONTAL);
    vertexData.horizontalSpan = 3;
    vertexGroup.setLayoutData(vertexData);

    this.paramX_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.3"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramY_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.4"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramZ_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.5"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    Label label1 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label1, 2);

    this.paramX_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.6"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramY_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.7"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramZ_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.8"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label2 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label2, 2);
    this.paramX_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.9"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramY_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.10"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramZ_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.11"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label3 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label3, 2);
    this.paramX_4 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.12"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramY_4 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.13"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramZ_4 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.14"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$

    final GridData labelData2 = new GridData(GridData.FILL_HORIZONTAL);
    final Label colorLabel = new Label(this.sShell, SWT.RIGHT);
    colorLabel.setText("color"); //$NON-NLS-1$
    colorLabel.setLayoutData(labelData2);
    createColorCombo();

    GridData rotData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group rotateGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    rotateGroup.setText(Messages.getString("AddQuadPolygonDialog.16")); //$NON-NLS-1$
    final GridLayout layout3 = new GridLayout();
    layout3.numColumns = 3;
    rotateGroup.setLayout(layout3);
    rotData = new GridData(GridData.FILL_HORIZONTAL);
    rotData.horizontalSpan = 3;
    rotateGroup.setLayoutData(rotData);

    this.rotX = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.17"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRX = new Label(rotateGroup, SWT.NONE);
    unitLabelRX.setText(this.angleUnit + " "); //$NON-NLS-1$
    this.rotY = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.18"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRY = new Label(rotateGroup, SWT.NONE);
    unitLabelRY.setText(this.angleUnit + " "); //$NON-NLS-1$
    this.rotZ = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.19"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRZ = new Label(rotateGroup, SWT.NONE);
    unitLabelRZ.setText(this.angleUnit + " "); //$NON-NLS-1$

    GridData locData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group locationGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    locationGroup.setText(Messages.getString("AddQuadPolygonDialog.20")); //$NON-NLS-1$
    final GridLayout layout4 = new GridLayout();
    layout4.numColumns = 3;
    locationGroup.setLayout(layout4);
    locData = new GridData(GridData.FILL_HORIZONTAL);
    locData.horizontalSpan = 3;
    locationGroup.setLayoutData(locData);

    this.locX = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.21"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLX = new Label(locationGroup, SWT.NONE);
    unitLabelLX.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locY = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.22"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLY = new Label(locationGroup, SWT.NONE);
    unitLabelLY.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locZ = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.23"), "0");  //$NON-NLS-1$//$NON-NLS-2$
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
    okButton.setText(Messages.getString("AddQuadPolygonDialog.24")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (Check()) {
          // dc.checkDuplication(,.getLocation,group);
          addPolygon();
          AddQuadPolygonDialog.this.sShell.close();
        } else {
          MessageBox mgb = new MessageBox(AddQuadPolygonDialog.this.sShell, SWT.ICON_WARNING);
          mgb.setMessage(Messages.getString("AddQuadPolygonDialog.25")); //$NON-NLS-1$
          mgb.setText(Messages.getString("AddQuadPolygonDialog.26")); //$NON-NLS-1$
        }
      }
    });

    final Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("AddQuadPolygonDialog.27")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        final MessageBox mesBox = new MessageBox(AddQuadPolygonDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        mesBox.setMessage(Messages.getString("AddQuadPolygonDialog.28")); //$NON-NLS-1$
        mesBox.setText(Messages.getString("AddQuadPolygonDialog.29")); //$NON-NLS-1$
        int result = mesBox.open();
        if (result == SWT.YES) {
          AddQuadPolygonDialog.this.sShell.close();
        }
      }
    });
  }

  /**
   * ポリゴンの生成
   */
  void addPolygon() {
    final XMLQuadPolygon quad = new XMLQuadPolygon();
    final Rotation rot = new Rotation();
    final Location loc = new Location();

    final Location vertex1 = new Location(this.paramX_1.getFloatValue(), this.paramY_1.getFloatValue(), this.paramZ_1.getFloatValue());
    final Location vertex2 = new Location(this.paramX_2.getFloatValue(), this.paramY_2.getFloatValue(), this.paramZ_2.getFloatValue());
    final Location vertex3 = new Location(this.paramX_3.getFloatValue(), this.paramY_3.getFloatValue(), this.paramZ_3.getFloatValue());
    final Location vertex4 = new Location(this.paramX_4.getFloatValue(), this.paramY_4.getFloatValue(), this.paramZ_4.getFloatValue());
    quad.setPointLocations(vertex1, vertex2, vertex3, vertex4);
    if (getRot(rot) != null) {
      quad.setRotation(getRot(rot));
    }
    if (getLoc(loc) != null) {
      quad.setLocation(getLoc(loc));
    }
    quad.setColor(this.colorCombo.getText());
    this.group.addXMLQuadPolygon(quad);
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
    if (this.rotX.checkParam() == false) {
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
    if (this.paramX_4.checkParam() == false) {
      return false;
    }
    if (this.paramY_4.checkParam() == false) {
      return false;
    }
    if (this.paramZ_4.checkParam() == false) {
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
    this.colorCombo.setText("blue"); //$NON-NLS-1$
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
