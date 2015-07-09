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
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.Translation;
import org.mklab.mikity.model.xml.simplexml.model.Rotation;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
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

  private ParameterInputBox vertex1X;
  private ParameterInputBox vertex1y;
  private ParameterInputBox vertex1z;
  
  private ParameterInputBox vertex2x;
  private ParameterInputBox vertex2y;
  private ParameterInputBox vertex2z;
  
  private ParameterInputBox vertex3x;
  private ParameterInputBox vertex3y;
  private ParameterInputBox vertex3z;
  
  private ParameterInputBox vertex4x;
  private ParameterInputBox vertex4y;
  private ParameterInputBox vertex4z;
  
  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;
  private ParameterInputBox locationX;
  private ParameterInputBox locationY;
  private ParameterInputBox locationZ;

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
    groupLabel.setText(Messages.getString("AddQuadPolygonDialog.1") + this.group.getName()); //$NON-NLS-1$
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 3;
    groupLabel.setLayoutData(gridData);

    GridData vertexData = new GridData(GridData.FILL_HORIZONTAL);
    final org.eclipse.swt.widgets.Group vertexGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    vertexGroup.setText(Messages.getString("AddQuadPolygonDialog.2")); //$NON-NLS-1$
    final GridLayout layout2 = new GridLayout();
    layout2.numColumns = 3;
    vertexGroup.setLayout(layout2);
    vertexData = new GridData(GridData.FILL_HORIZONTAL);
    vertexData.horizontalSpan = 3;
    vertexGroup.setLayoutData(vertexData);

    this.vertex1X = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.3"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex1y = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.4"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex1z = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.5"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    Label label1 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label1, 2);

    this.vertex2x = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.6"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex2y = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.7"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex2z = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.8"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label2 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label2, 2);
    
    this.vertex3x = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.9"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex3y = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.10"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex3z = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.11"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label3 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label3, 2);
    this.vertex4x = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.12"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex4y = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.13"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex4z = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, Messages.getString("AddQuadPolygonDialog.14"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
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

    this.rotationX = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.17"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRX = new Label(rotateGroup, SWT.NONE);
    unitLabelRX.setText(this.angleUnit + " "); //$NON-NLS-1$
    this.rotationY = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.18"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRY = new Label(rotateGroup, SWT.NONE);
    unitLabelRY.setText(this.angleUnit + " "); //$NON-NLS-1$
    this.rotationZ = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.19"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
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

    this.locationX = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.21"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLX = new Label(locationGroup, SWT.NONE);
    unitLabelLX.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locationY = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.22"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLY = new Label(locationGroup, SWT.NONE);
    unitLabelLY.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locationZ = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddQuadPolygonDialog.23"), "0");  //$NON-NLS-1$//$NON-NLS-2$
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
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (Check()) {
          addPolygon();
          AddQuadPolygonDialog.this.sShell.close();
        } else {
          MessageBox message = new MessageBox(AddQuadPolygonDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("AddQuadPolygonDialog.25")); //$NON-NLS-1$
          message.setText(Messages.getString("AddQuadPolygonDialog.26")); //$NON-NLS-1$
        }
      }
    });

    final Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("AddQuadPolygonDialog.27")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        final MessageBox message = new MessageBox(AddQuadPolygonDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        message.setMessage(Messages.getString("AddQuadPolygonDialog.28")); //$NON-NLS-1$
        message.setText(Messages.getString("AddQuadPolygonDialog.29")); //$NON-NLS-1$
        int yesNo = message.open();
        if (yesNo == SWT.YES) {
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
    final Translation loc = new Translation();

    final Translation vertex1 = new Translation(this.vertex1X.getFloatValue(), this.vertex1y.getFloatValue(), this.vertex1z.getFloatValue());
    final Translation vertex2 = new Translation(this.vertex2x.getFloatValue(), this.vertex2y.getFloatValue(), this.vertex2z.getFloatValue());
    final Translation vertex3 = new Translation(this.vertex3x.getFloatValue(), this.vertex3y.getFloatValue(), this.vertex3z.getFloatValue());
    final Translation vertex4 = new Translation(this.vertex4x.getFloatValue(), this.vertex4y.getFloatValue(), this.vertex4z.getFloatValue());
    quad.setVertices(vertex1, vertex2, vertex3, vertex4);
    if (getRotation(rot) != null) {
      quad.setRotation(getRotation(rot));
    }
    if (getLocaion(loc) != null) {
      quad.setTranslation(getLocaion(loc));
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
   * @param rotation
   * @return rot
   */
  private Rotation getRotation(Rotation rotation) {
    if (this.rotationX.getFloatValue() == 0 && this.rotationY.getFloatValue() == 0 && this.rotationZ.getFloatValue() == 0) {
      return null;
    }
    rotation.setX(this.rotationX.getFloatValue());
    rotation.setY(this.rotationY.getFloatValue());
    rotation.setZ(this.rotationZ.getFloatValue());
    return rotation;

  }

  /**
   * Locationの値を設定 param Location return Location
   * 
   * @param location
   * @return loc
   */
  private Translation getLocaion(Translation location) {
    if (this.locationX.getFloatValue() == 0 && this.locationY.getFloatValue() == 0 && this.locationZ.getFloatValue() == 0) {
      return null;
    }
    location.setX(this.locationX.getFloatValue());
    location.setY(this.locationY.getFloatValue());
    location.setZ(this.locationZ.getFloatValue());
    return location;
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean Check() {
    if (this.vertex1X.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex1y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex1z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex2x.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex2y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex2z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex3x.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex3y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex3z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex4x.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex4y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex4z.containsOnlyNumbers() == false) {
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
    if (this.locationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.locationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.locationZ.containsOnlyNumbers() == false) {
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
