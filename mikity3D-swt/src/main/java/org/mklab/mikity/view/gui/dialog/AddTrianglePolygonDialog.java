/*
 * $Id: AddTrianglePolygonDialog.java,v 1.5 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * 三角形ポリゴンを作るためのダイアログのクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.5 $. 2008/01/28
 */
public class AddTrianglePolygonDialog {

  private Shell parentShell;
  Shell sShell;

  private ParameterInputBox vertex0x;
  private ParameterInputBox vertex0y;
  private ParameterInputBox vertex0z;
  
  private ParameterInputBox vertex1x;
  private ParameterInputBox vertex1y;
  private ParameterInputBox vertex1z;
  
  private ParameterInputBox vertex2x;
  private ParameterInputBox vertex2y;
  private ParameterInputBox vertex2z;
  
  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;
  
  private ParameterInputBox translationX;
  private ParameterInputBox translationY;
  private ParameterInputBox translationZ;

  private GroupModel targetGroup;
  private String angleUnit;
  private String lengthUnit;
  private ColorSelectorButton colorSelector;

  /**
   * コンストラクター
   * 
   * @param parentShell 親シェル
   * @param targetGroup グループ
   */
  public AddTrianglePolygonDialog(Shell parentShell, GroupModel targetGroup) {
    this.parentShell = parentShell;
    this.targetGroup = targetGroup;
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
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 780));
    this.sShell.setText(Messages.getString("AddTrianglePolygonDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout1);

    final Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("AddTrianglePolygonDialog.1") + this.targetGroup.getName()); //$NON-NLS-1$
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 3;
    groupLabel.setLayoutData(gridData);

    createNewPolygon();
    
    createButtonComp();
  }

  private void createNewPolygon() {
    GridData vertexData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group vertexGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    vertexGroup.setText(Messages.getString("AddTrianglePolygonDialog.2")); //$NON-NLS-1$
    GridLayout layout2 = new GridLayout();
    layout2.numColumns = 3;
    vertexGroup.setLayout(layout2);
    vertexData = new GridData(GridData.FILL_HORIZONTAL);
    vertexData.horizontalSpan = 3;
    vertexGroup.setLayoutData(vertexData);

    this.vertex0x = new ParameterInputBox(vertexGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.3"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex0y = new ParameterInputBox(vertexGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.4"), "-0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex0z = new ParameterInputBox(vertexGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.5"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label1 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label1, 2);

    this.vertex1x = new ParameterInputBox(vertexGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.6"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex1y = new ParameterInputBox(vertexGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.7"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex1z = new ParameterInputBox(vertexGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.8"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label2 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label2, 2);
    this.vertex2x = new ParameterInputBox(vertexGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.9"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex2y = new ParameterInputBox(vertexGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.10"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex2z = new ParameterInputBox(vertexGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.11"), "0.3");  //$NON-NLS-1$//$NON-NLS-2$

    final GridData labelData2 = new GridData(GridData.FILL_HORIZONTAL);
    final Label colorLabel = new Label(this.sShell, SWT.LEFT);
    colorLabel.setText("color"); //$NON-NLS-1$
    colorLabel.setLayoutData(labelData2);
    createColorCombo();

    GridData translatioonData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group translationGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    translationGroup.setText(Messages.getString("AddTrianglePolygonDialog.16")); //$NON-NLS-1$
    final GridLayout layout4 = new GridLayout();
    layout4.numColumns = 3;
    translationGroup.setLayout(layout4);
    translatioonData = new GridData(GridData.FILL_HORIZONTAL);
    translatioonData.horizontalSpan = 3;
    translationGroup.setLayoutData(translatioonData);

    this.translationX = new ParameterInputBox(translationGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.17"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLX = new Label(translationGroup, SWT.NONE);
    unitLabelLX.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.translationY = new ParameterInputBox(translationGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.18"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLY = new Label(translationGroup, SWT.NONE);
    unitLabelLY.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.translationZ = new ParameterInputBox(translationGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.19"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLZ = new Label(translationGroup, SWT.NONE);
    unitLabelLZ.setText(this.lengthUnit + " "); //$NON-NLS-1$

    GridData rotationData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group rotationGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    rotationGroup.setText(Messages.getString("AddTrianglePolygonDialog.12")); //$NON-NLS-1$
    GridLayout layout3 = new GridLayout();
    layout3.numColumns = 3;
    rotationGroup.setLayout(layout3);
    rotationData = new GridData(GridData.FILL_HORIZONTAL);
    rotationData.horizontalSpan = 3;
    rotationGroup.setLayoutData(rotationData);

    this.rotationX = new ParameterInputBox(rotationGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.13"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRX = new Label(rotationGroup, SWT.NONE);
    unitLabelRX.setText(this.angleUnit + " "); //$NON-NLS-1$
    this.rotationY = new ParameterInputBox(rotationGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.14"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRY = new Label(rotationGroup, SWT.NONE);
    unitLabelRY.setText(this.angleUnit + " "); //$NON-NLS-1$
    this.rotationZ = new ParameterInputBox(rotationGroup, SWT.NONE, Messages.getString("AddTrianglePolygonDialog.15"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRZ = new Label(rotationGroup, SWT.NONE);
    unitLabelRZ.setText(this.angleUnit + " "); //$NON-NLS-1$
  }

  /**
   * 完了ボタンを生成する
   */
  private void createButtonComp() {
    final Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("AddTrianglePolygonDialog.20")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (Check()) {
          addPolygon();
          AddTrianglePolygonDialog.this.sShell.close();
        } else {
          final MessageBox message = new MessageBox(AddTrianglePolygonDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("AddTrianglePolygonDialog.21")); //$NON-NLS-1$
          message.setText(Messages.getString("AddTrianglePolygonDialog.22")); //$NON-NLS-1$
        }
      }
    });

    final Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("AddTrianglePolygonDialog.23")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        final MessageBox message = new MessageBox(AddTrianglePolygonDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        message.setMessage(Messages.getString("AddTrianglePolygonDialog.24")); //$NON-NLS-1$
        message.setText(Messages.getString("AddTrianglePolygonDialog.25")); //$NON-NLS-1$
        int yesNo = message.open();
        if (yesNo == SWT.YES) {
          AddTrianglePolygonDialog.this.sShell.close();
        }
      }
    });
  }

  /**
   * ポリゴンの生成
   */
  void addPolygon() {
    final TrianglePolygonModel triangle = new TrianglePolygonModel();
    final RotationModel rotation = new RotationModel();
    final TranslationModel location = new TranslationModel();

    final VertexModel vertex0 = new VertexModel(this.vertex0x.getFloatValue(), this.vertex0y.getFloatValue(), this.vertex0z.getFloatValue());
    final VertexModel vertex1 = new VertexModel(this.vertex1x.getFloatValue(), this.vertex1y.getFloatValue(), this.vertex1z.getFloatValue());
    final VertexModel vertex2 = new VertexModel(this.vertex2x.getFloatValue(), this.vertex2y.getFloatValue(), this.vertex2z.getFloatValue());
    triangle.setVertices(vertex0, vertex1, vertex2);
    if (getRotation(rotation) != null) {
      triangle.setRotation(getRotation(rotation));
    }
    if (getLocation(location) != null) {
      triangle.setTranslation(getLocation(location));
    }
    final ColorModel color = this.colorSelector.getColor();
    triangle.setColor(color);
    this.targetGroup.add(triangle);
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
  private RotationModel getRotation(RotationModel rotation) {
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
  private TranslationModel getLocation(TranslationModel location) {
    if (this.translationX.getFloatValue() == 0 && this.translationY.getFloatValue() == 0 && this.translationZ.getFloatValue() == 0) {
      return null;
    }
    location.setX(this.translationX.getFloatValue());
    location.setY(this.translationY.getFloatValue());
    location.setZ(this.translationZ.getFloatValue());
    return location;
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean Check() {
    if (this.vertex0x.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex0y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex0z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex1x.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex1y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex1z.containsOnlyNumbers() == false) {
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
    if (this.rotationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationZ.containsOnlyNumbers() == false) {
      return false;
    }
    return true;
  }

  /**
   * コンボボックス colorCombo プリミティブの色を選択
   */
  private void createColorCombo() {
    this.colorSelector = new ColorSelectorButton(this.sShell);
    this.colorSelector.setColor(new ColorModel("red")); //$NON-NLS-1$
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
