/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.model.xml.model.Location;
import org.mklab.mikity.model.xml.model.Rotation;
import org.mklab.mikity.model.xml.model.XMLBox;
import org.mklab.mikity.model.xml.model.XMLCone;
import org.mklab.mikity.model.xml.model.XMLCylinder;
import org.mklab.mikity.model.xml.model.XMLSphere;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * プリミティブの追加を行うクラス
 * 
 * @author miki
 * @version $Revision: 1.3 $.2005/02/09
 */
public class AddPrimitiveDialog {

  private Shell parentShell;
  Shell sShell;

  private ParameterInputBox parameter1;
  private ParameterInputBox parameter2;
  private ParameterInputBox parameter3;
  private Label unitLabel1;
  private Label unitLabel2;
  private Label unitLabel3;
  private ParameterInputBox rotX;
  private ParameterInputBox rotY;
  private ParameterInputBox rotZ;
  private ParameterInputBox locX;
  private ParameterInputBox locY;
  private ParameterInputBox locZ;
  private Combo primitiveCombo;
  private Combo colorCombo;
  private Group group;
  private String angleUnit;
  private String lengthUnit;

  int selectedIndex = boxFlag;
  private static final int boxFlag = 0;
  private static final int cylinderFlag = 1;
  private static final int sphereFlag = 2;
  private static final int coneFlag = 3;

  // private CollisionCanceller dc;

  /**
   * コンストラクター
   * 
   * @param parentShell 親シェル
   * @param group グループ
   */
  public AddPrimitiveDialog(Shell parentShell, Group group) {
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
    layout1.numColumns = 3;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(280, 400));
    this.sShell.setText(Messages.getString("AddPrimitiveDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout1);

    final Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("AddPrimitiveDialog.1") + this.group.loadName()); //$NON-NLS-1$
    final GridData gLabelData = new GridData(GridData.FILL_HORIZONTAL);
    gLabelData.horizontalSpan = 3;
    groupLabel.setLayoutData(gLabelData);

    final Label primLabel = new Label(this.sShell, SWT.RIGHT);
    primLabel.setText("primitive"); //$NON-NLS-1$
    final GridData labelData = new GridData(GridData.FILL_HORIZONTAL);
    labelData.widthHint = 80;
    primLabel.setLayoutData(labelData);
    createPrimitiveCombo();

    this.parameter1 = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("AddPrimitiveDialog.2"), "0.2"); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel1 = new Label(this.sShell, SWT.NONE);
    this.unitLabel1.setText(this.lengthUnit);

    this.parameter2 = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("AddPrimitiveDialog.3"), "0.2"); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel2 = new Label(this.sShell, SWT.NONE);
    this.unitLabel2.setText(this.lengthUnit);

    this.parameter3 = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("AddPrimitiveDialog.4"), "0.2"); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel3 = new Label(this.sShell, SWT.NONE);
    this.unitLabel3.setText(this.lengthUnit);

    GridData rotationData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group rotateGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    rotateGroup.setText(Messages.getString("AddPrimitiveDialog.5")); //$NON-NLS-1$
    final GridLayout layout2 = new GridLayout();
    layout2.numColumns = 3;
    rotateGroup.setLayout(layout2);
    rotationData = new GridData(GridData.FILL_HORIZONTAL);
    rotationData.horizontalSpan = 3;
    rotateGroup.setLayoutData(rotationData);

    this.rotX = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.6"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRX = new Label(rotateGroup, SWT.NONE);
    unitLabelRX.setText(this.angleUnit + Messages.getString("AddPrimitiveDialog.7")); //$NON-NLS-1$
    this.rotY = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.8"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRY = new Label(rotateGroup, SWT.NONE);
    unitLabelRY.setText(this.angleUnit + Messages.getString("AddPrimitiveDialog.9")); //$NON-NLS-1$
    this.rotZ = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.10"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRZ = new Label(rotateGroup, SWT.NONE);
    unitLabelRZ.setText(this.angleUnit + Messages.getString("AddPrimitiveDialog.11")); //$NON-NLS-1$

    GridData locationData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group locationGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    locationGroup.setText(Messages.getString("AddPrimitiveDialog.12")); //$NON-NLS-1$
    final GridLayout layout3 = new GridLayout();
    layout3.numColumns = 3;
    locationGroup.setLayout(layout3);
    locationData = new GridData(GridData.FILL_HORIZONTAL);
    locationData.horizontalSpan = 3;
    locationGroup.setLayoutData(locationData);

    this.locX = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.13"), "0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLX = new Label(locationGroup, SWT.NONE);
    unitLabelLX.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locY = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.14"), "0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLY = new Label(locationGroup, SWT.NONE);
    unitLabelLY.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locZ = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.15"), "0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLZ = new Label(locationGroup, SWT.NONE);
    unitLabelLZ.setText(this.lengthUnit + " "); //$NON-NLS-1$

    final GridData labelData2 = new GridData(GridData.FILL_HORIZONTAL);
    final Label colorLabel = new Label(this.sShell, SWT.RIGHT);
    colorLabel.setText("color"); //$NON-NLS-1$
    colorLabel.setLayoutData(labelData2);
    createColorCombo();

    final Label spaceLabel = new Label(this.sShell, SWT.NONE);
    spaceLabel.setText(" "); //$NON-NLS-1$

    final Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("AddPrimitiveDialog.16")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (checkParamters()) {
          // dc.checkDuplication(,.getLocation,group);
          addPrimitive();
          AddPrimitiveDialog.this.sShell.close();
        } else {
          MessageBox messageBox = new MessageBox(AddPrimitiveDialog.this.sShell, SWT.ICON_WARNING);
          messageBox.setMessage(Messages.getString("AddPrimitiveDialog.17")); //$NON-NLS-1$
          messageBox.setText(Messages.getString("AddPrimitiveDialog.18")); //$NON-NLS-1$
        }
      }
    });

    final Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("AddPrimitiveDialog.19")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        final MessageBox messageBox = new MessageBox(AddPrimitiveDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        messageBox.setMessage(Messages.getString("AddPrimitiveDialog.20")); //$NON-NLS-1$
        messageBox.setText(Messages.getString("AddPrimitiveDialog.21")); //$NON-NLS-1$
        int result = messageBox.open();
        if (result == SWT.YES) {
          AddPrimitiveDialog.this.sShell.close();
        }
      }
    });
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean 数字以外が入力されていればfalse
   */
  boolean checkParamters() {
    if (this.parameter1.checkParam() == false) {
      return false;
    }
    if (this.parameter2.checkParam() == false) {
      return false;
    }
    if (this.parameter3.checkParam() == false) {
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
   * プリミティブを追加する
   */
  void addPrimitive() {
    final Rotation rot = new Rotation();
    final Location location = new Location();

    switch (this.selectedIndex) {
      case boxFlag:
        final XMLBox box = new XMLBox();
        box.setXsize(this.parameter1.getFloatValue());
        box.setYsize(this.parameter2.getFloatValue());
        box.setZsize(this.parameter3.getFloatValue());
        if (getRotation(rot) != null) {
          box.setRotation(getRotation(rot));
        }
        if (getLocation(location) != null) {
          box.setLocation(getLocation(location));
        }
        box.setColor(this.colorCombo.getText());
        // dc.checkCollision(box,box.loadLocation(),group);
        box.setLocation(location);
        this.group.addXMLBox(box);

        break;
      case cylinderFlag:
        final XMLCylinder cylinder = new XMLCylinder();
        cylinder.setRadius(this.parameter1.getFloatValue());
        cylinder.setHeight(this.parameter2.getFloatValue());
        cylinder.setDiv(setDiv(this.parameter3));
        if (getRotation(rot) != null) {
          cylinder.setRotation(getRotation(rot));
        }
        if (getLocation(location) != null) {
          cylinder.setLocation(getLocation(location));
        }
        cylinder.setColor(this.colorCombo.getText());
        // dc.checkCollision(cyl,cyl.loadLocation(),group);
        cylinder.setLocation(location);
        this.group.addXMLCylinder(cylinder);

        break;
      case sphereFlag:
        final XMLSphere sphere = new XMLSphere();
        sphere.setRadius(this.parameter1.getFloatValue());
        sphere.setDiv(setDiv(this.parameter2));
        if (getRotation(rot) != null) {
          sphere.setRotation(getRotation(rot));
        }
        if (getLocation(location) != null) {
          sphere.setLocation(getLocation(location));
        }
        sphere.setColor(this.colorCombo.getText());
        // dc.checkCollision(sph,sph.loadLocation(),group);
        sphere.setLocation(location);
        this.group.addXMLSphere(sphere);

        break;
      case coneFlag:
        final XMLCone cone = new XMLCone();
        cone.setRadius(this.parameter1.getFloatValue());
        cone.setHeight(this.parameter2.getFloatValue());
        cone.setDiv(setDiv(this.parameter3));
        if (getRotation(rot) != null) {
          cone.setRotation(getRotation(rot));
        }
        if (getLocation(location) != null) {
          cone.setLocation(getLocation(location));
        }
        cone.setColor(this.colorCombo.getText());
        // dc.checkCollision(cone,cone.loadLocation(),group);
        cone.setLocation(location);
        this.group.addXMLCone(cone);
        break;
      default:
        throw new IllegalArgumentException();
    }

  }

  /**
   * Rotationの値を設定 param Rotation return Rotation
   * 
   * @param rotation
   * @return rot
   */
  private Rotation getRotation(Rotation rotation) {
    if (this.rotX.getFloatValue() == 0 && this.rotY.getFloatValue() == 0 && this.rotZ.getFloatValue() == 0) {
      return null;
    }
    rotation.setXrotate(this.rotX.getFloatValue());
    rotation.setYrotate(this.rotY.getFloatValue());
    rotation.setZrotate(this.rotZ.getFloatValue());
    return rotation;
  }

  /**
   * Locationの値を設定 param Location return Location
   * 
   * @param location
   * @return loc
   */
  private Location getLocation(Location location) {
    if (this.locX.getFloatValue() == 0 && this.locY.getFloatValue() == 0 && this.locZ.getFloatValue() == 0) {
      return null;
    }
    location.setX(this.locX.getFloatValue());
    location.setY(this.locY.getFloatValue());
    location.setZ(this.locZ.getFloatValue());
    return location;
  }

  /**
   * 分割数が3より小さいときに変わりに30にする
   * 
   * @param param
   * @return div
   */
  private int setDiv(ParameterInputBox param) {
    int div = (int)param.getDoubleValue();
    if (div < 3) {
      // 3以下なので、30を返します
      div = 30;
    }
    return div;
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

  class PComboCheck extends SelectionAdapter {
    /**
     * 選択されたプリミティブによってLabel,TextBoxを変更するための flagとなるselectedIndex
     */
    @Override
    public void widgetSelected(SelectionEvent e) {
      final Combo combo = (Combo)e.widget;
      AddPrimitiveDialog.this.selectedIndex = combo.getSelectionIndex();

      if (AddPrimitiveDialog.this.selectedIndex == boxFlag) {
        boxLabel();
      } else if (AddPrimitiveDialog.this.selectedIndex == cylinderFlag) {
        cylinderLabel();
      } else if (AddPrimitiveDialog.this.selectedIndex == sphereFlag) {
        sphereLabel();
      } else if (AddPrimitiveDialog.this.selectedIndex == coneFlag) {
        coneLabel();
      }
    }
  }

  /**
   * 選ばれているprimitiveがBoxのとき
   */
  void boxLabel() {
    this.parameter1.setLabelText(Messages.getString("AddPrimitiveDialog.22")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("AddPrimitiveDialog.23")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("AddPrimitiveDialog.24")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.parameter3.setVisible(true);
  }

  /**
   * Cylinderのとき
   */
  void cylinderLabel() {
    this.parameter1.setLabelText(Messages.getString("AddPrimitiveDialog.25")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("AddPrimitiveDialog.26")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("AddPrimitiveDialog.27")); //$NON-NLS-1$
    this.unitLabel3.setText(" "); //$NON-NLS-1$
    this.parameter3.setVisible(true);
    if (this.parameter3.getIntValue() < 3) {
      this.parameter3.setText("30"); //$NON-NLS-1$
    } else {
      this.parameter3.setText("" + this.parameter3.getIntValue()); //$NON-NLS-1$
    }
  }

  /**
   * Sphereのとき
   */
  void sphereLabel() {
    this.parameter1.setLabelText(Messages.getString("AddPrimitiveDialog.28")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("AddPrimitiveDialog.29")); //$NON-NLS-1$
    this.unitLabel2.setText(" "); //$NON-NLS-1$
    this.parameter3.setVisible(false);
    this.unitLabel3.setText(" "); //$NON-NLS-1$
    if (this.parameter2.getIntValue() < 3) {
      this.parameter2.setText(Messages.getString("AddPrimitiveDialog.30")); //$NON-NLS-1$
    } else {
      this.parameter2.setText("" + this.parameter2.getIntValue()); //$NON-NLS-1$
    }
  }

  /**
   * Coneのとき
   */
  void coneLabel() {
    this.parameter1.setLabelText(Messages.getString("AddPrimitiveDialog.31")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("AddPrimitiveDialog.32")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("AddPrimitiveDialog.33")); //$NON-NLS-1$
    this.unitLabel3.setText(" "); //$NON-NLS-1$
    this.parameter3.setVisible(true);
    if (this.parameter3.getIntValue() < 3) {
      this.parameter3.setText("30"); //$NON-NLS-1$
    } else {
      this.parameter3.setText("" + this.parameter3.getIntValue()); //$NON-NLS-1$
    }
  }

  /**
   * コンボボックス primCombo プリミティブを選択
   */
  private void createPrimitiveCombo() {
    this.primitiveCombo = new Combo(this.sShell, SWT.READ_ONLY);
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 2;
    this.primitiveCombo.setLayoutData(gridData);
    final String[] primitives = {"Box", "Cylinder", "Sphere", "Cone"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    this.primitiveCombo.setItems(primitives);
    // デフォルトはBox
    this.primitiveCombo.setText("Box"); //$NON-NLS-1$
    this.primitiveCombo.addSelectionListener(new PComboCheck());
  }

  /**
   * コンボボックス colorCombo プリミティブの色を選択
   */
  private void createColorCombo() {
    this.colorCombo = new Combo(this.sShell, SWT.READ_ONLY);
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 2;
    this.colorCombo.setLayoutData(gridData);
    final String[] colors = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
    this.colorCombo.setItems(colors);
    this.colorCombo.setText("red"); //$NON-NLS-1$
  }
}