/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.dialog;

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
import org.mklab.mikity.gui.ParameterInputBox;
import org.mklab.mikity.gui.UnitLabel;
import org.mklab.mikity.gui.collision.CollisionCanceller;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLSphere;


/**
 * プリミティブの追加を行うクラス
 * 
 * @author miki
 * @version $Revision: 1.3 $.2005/02/09
 */
public class AddPrimitiveDialog {

  private Shell parentShell;
  Shell sShell;

  private ParameterInputBox param1;
  private ParameterInputBox param2;
  private ParameterInputBox param3;
  private Label unitLabel1;
  private Label unitLabel2;
  private Label unitLabel3;
  private ParameterInputBox rotX;
  private ParameterInputBox rotY;
  private ParameterInputBox rotZ;
  private ParameterInputBox locX;
  private ParameterInputBox locY;
  private ParameterInputBox locZ;
  private Combo primCombo;
  private Combo colorCombo;
  private Group group;
  private String angleUnit;
  private String lengthUnit;

  int selectedIndex = boxFlag;
  private static final int boxFlag = 0;
  private static final int cylFlag = 1;
  private static final int sphFlag = 2;
  private static final int coneFlag = 3;

  // private CollisionCanceller dc;

  /**
   * コンストラクター
   * 
   * @param parentShell
   *        親シェル
   * @param group
   *        グループ
   * @param dc
   */
  public AddPrimitiveDialog(Shell parentShell, Group group, CollisionCanceller dc) {
    this.parentShell = parentShell;
    this.group = group;
    this.angleUnit = UnitLabel.getUnit("modelAngle"); //$NON-NLS-1$
    this.lengthUnit = UnitLabel.getUnit("modelLength"); //$NON-NLS-1$
    createSShell();

    // this.dc = dc;
  }

  /**
   * シェルの作成
   */
  private void createSShell() {
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 3;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(280, 400));
    this.sShell.setText(Messages.getString("AddPrimitiveDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);

    Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("AddPrimitiveDialog.1") + this.group.loadName()); //$NON-NLS-1$
    GridData gLabelData = new GridData(GridData.FILL_HORIZONTAL);
    gLabelData.horizontalSpan = 3;
    groupLabel.setLayoutData(gLabelData);

    Label primLabel = new Label(this.sShell, SWT.RIGHT);
    primLabel.setText("primitive"); //$NON-NLS-1$
    GridData labelData = new GridData(GridData.FILL_HORIZONTAL);
    labelData.widthHint = 80;
    primLabel.setLayoutData(labelData);
    createPrimCombo();

    this.param1 = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("AddPrimitiveDialog.2"), "0.2");  //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel1 = new Label(this.sShell, SWT.NONE);
    this.unitLabel1.setText(this.lengthUnit);

    this.param2 = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("AddPrimitiveDialog.3"), "0.2");  //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel2 = new Label(this.sShell, SWT.NONE);
    this.unitLabel2.setText(this.lengthUnit);

    this.param3 = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("AddPrimitiveDialog.4"), "0.2");  //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel3 = new Label(this.sShell, SWT.NONE);
    this.unitLabel3.setText(this.lengthUnit);

    GridData rotData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group rotateGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    rotateGroup.setText(Messages.getString("AddPrimitiveDialog.5")); //$NON-NLS-1$
    layout = new GridLayout();
    layout.numColumns = 3;
    rotateGroup.setLayout(layout);
    rotData = new GridData(GridData.FILL_HORIZONTAL);
    rotData.horizontalSpan = 3;
    rotateGroup.setLayoutData(rotData);

    this.rotX = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.6"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    Label unitLabelRX = new Label(rotateGroup, SWT.NONE);
    unitLabelRX.setText(this.angleUnit + Messages.getString("AddPrimitiveDialog.7")); //$NON-NLS-1$
    this.rotY = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.8"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    Label unitLabelRY = new Label(rotateGroup, SWT.NONE);
    unitLabelRY.setText(this.angleUnit + Messages.getString("AddPrimitiveDialog.9")); //$NON-NLS-1$
    this.rotZ = new ParameterInputBox(rotateGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.10"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    Label unitLabelRZ = new Label(rotateGroup, SWT.NONE);
    unitLabelRZ.setText(this.angleUnit + Messages.getString("AddPrimitiveDialog.11")); //$NON-NLS-1$

    GridData locData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group locationGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    locationGroup.setText(Messages.getString("AddPrimitiveDialog.12")); //$NON-NLS-1$
    layout = new GridLayout();
    layout.numColumns = 3;
    locationGroup.setLayout(layout);
    locData = new GridData(GridData.FILL_HORIZONTAL);
    locData.horizontalSpan = 3;
    locationGroup.setLayoutData(locData);

    this.locX = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.13"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    Label unitLabelLX = new Label(locationGroup, SWT.NONE);
    unitLabelLX.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locY = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.14"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    Label unitLabelLY = new Label(locationGroup, SWT.NONE);
    unitLabelLY.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.locZ = new ParameterInputBox(locationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.15"), "0");  //$NON-NLS-1$//$NON-NLS-2$
    Label unitLabelLZ = new Label(locationGroup, SWT.NONE);
    unitLabelLZ.setText(this.lengthUnit + " "); //$NON-NLS-1$

    GridData labelData2 = new GridData(GridData.FILL_HORIZONTAL);
    Label colorLabel = new Label(this.sShell, SWT.RIGHT);
    colorLabel.setText("color"); //$NON-NLS-1$
    colorLabel.setLayoutData(labelData2);
    createColorCombo();

    Label spaceLabel = new Label(this.sShell, SWT.NONE);
    spaceLabel.setText(" "); //$NON-NLS-1$

    Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("AddPrimitiveDialog.16")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (Check()) {
          // dc.checkDuplication(,.getLocation,group);
          addPrim();
          AddPrimitiveDialog.this.sShell.close();
        } else {
          MessageBox mgb = new MessageBox(AddPrimitiveDialog.this.sShell, SWT.ICON_WARNING);
          mgb.setMessage(Messages.getString("AddPrimitiveDialog.17")); //$NON-NLS-1$
          mgb.setText(Messages.getString("AddPrimitiveDialog.18")); //$NON-NLS-1$
        }
      }
    });

    Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("AddPrimitiveDialog.19")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        MessageBox mesBox = new MessageBox(AddPrimitiveDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        mesBox.setMessage(Messages.getString("AddPrimitiveDialog.20")); //$NON-NLS-1$
        mesBox.setText(Messages.getString("AddPrimitiveDialog.21")); //$NON-NLS-1$
        int result = mesBox.open();
        if (result == SWT.YES) {
          AddPrimitiveDialog.this.sShell.close();
        }
      }
    });
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean Check() {
    if (this.param1.checkParam() == false) {
      return false;
    }
    if (this.param2.checkParam() == false) {
      return false;
    }
    if (this.param3.checkParam() == false) {
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
  void addPrim() {
    Rotation rot = new Rotation();
    Location loc = new Location();

    switch (this.selectedIndex) {
      case boxFlag:
        XMLBox box = new XMLBox();
        box.setXsize(this.param1.getFloatValue());
        box.setYsize(this.param2.getFloatValue());
        box.setZsize(this.param3.getFloatValue());
        if (getRot(rot) != null) {
          box.setRotation(getRot(rot));
        }
        if (getLoc(loc) != null) {
          box.setLocation(getLoc(loc));
        }
        box.setColor(this.colorCombo.getText());
        // dc.checkCollision(box,box.loadLocation(),group);
        box.setLocation(loc);
        this.group.addXMLBox(box);

        break;
      case cylFlag:
        XMLCylinder cyl = new XMLCylinder();
        cyl.setR(this.param1.getFloatValue());
        cyl.setHeight(this.param2.getFloatValue());
        cyl.setDiv(setDiv(this.param3));
        if (getRot(rot) != null) {
          cyl.setRotation(getRot(rot));
        }
        if (getLoc(loc) != null) {
          cyl.setLocation(getLoc(loc));
        }
        cyl.setColor(this.colorCombo.getText());
        // dc.checkCollision(cyl,cyl.loadLocation(),group);
        cyl.setLocation(loc);
        this.group.addXMLCylinder(cyl);

        break;
      case sphFlag:
        XMLSphere sph = new XMLSphere();
        sph.setR(this.param1.getFloatValue());
        sph.setDiv(setDiv(this.param2));
        if (getRot(rot) != null) {
          sph.setRotation(getRot(rot));
        }
        if (getLoc(loc) != null) {
          sph.setLocation(getLoc(loc));
        }
        sph.setColor(this.colorCombo.getText());
        // dc.checkCollision(sph,sph.loadLocation(),group);
        sph.setLocation(loc);
        this.group.addXMLSphere(sph);

        break;
      case coneFlag:
        XMLCone cone = new XMLCone();
        cone.setR(this.param1.getFloatValue());
        cone.setHeight(this.param2.getFloatValue());
        cone.setDiv(setDiv(this.param3));
        if (getRot(rot) != null) {
          cone.setRotation(getRot(rot));
        }
        if (getLoc(loc) != null) {
          cone.setLocation(getLoc(loc));
        }
        cone.setColor(this.colorCombo.getText());
        // dc.checkCollision(cone,cone.loadLocation(),group);
        cone.setLocation(loc);
        this.group.addXMLCone(cone);

        break;
    }

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
    Display display = this.sShell.getDisplay();
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
      Combo combo = (Combo)e.widget;
      AddPrimitiveDialog.this.selectedIndex = combo.getSelectionIndex();

      if (AddPrimitiveDialog.this.selectedIndex == boxFlag) {
        boxLabel();
      } else if (AddPrimitiveDialog.this.selectedIndex == cylFlag) {
        cylLabel();
      } else if (AddPrimitiveDialog.this.selectedIndex == sphFlag) {
        sphLabel();
      } else if (AddPrimitiveDialog.this.selectedIndex == coneFlag) {
        coneLabel();
      }
    }
  }

  /**
   * 選ばれているprimitiveがBoxのとき
   */
  void boxLabel() {
    this.param1.setLabelText(Messages.getString("AddPrimitiveDialog.22")); //$NON-NLS-1$
    this.param2.setLabelText(Messages.getString("AddPrimitiveDialog.23")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.param3.setLabelText(Messages.getString("AddPrimitiveDialog.24")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.param3.setVisible(true);
  }

  /**
   * Cylinderのとき
   */
  void cylLabel() {
    this.param1.setLabelText(Messages.getString("AddPrimitiveDialog.25")); //$NON-NLS-1$
    this.param2.setLabelText(Messages.getString("AddPrimitiveDialog.26")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.param3.setLabelText(Messages.getString("AddPrimitiveDialog.27")); //$NON-NLS-1$
    this.unitLabel3.setText(" "); //$NON-NLS-1$
    this.param3.setVisible(true);
    if (this.param3.getIntValue() < 3) {
      this.param3.setText("30"); //$NON-NLS-1$
    } else {
      this.param3.setText("" + this.param3.getIntValue()); //$NON-NLS-1$
    }
  }

  /**
   * Sphereのとき
   */
  void sphLabel() {
    this.param1.setLabelText(Messages.getString("AddPrimitiveDialog.28")); //$NON-NLS-1$
    this.param2.setLabelText(Messages.getString("AddPrimitiveDialog.29")); //$NON-NLS-1$
    this.unitLabel2.setText(" "); //$NON-NLS-1$
    this.param3.setVisible(false);
    this.unitLabel3.setText(" "); //$NON-NLS-1$
    if (this.param2.getIntValue() < 3) {
      this.param2.setText(Messages.getString("AddPrimitiveDialog.30")); //$NON-NLS-1$
    } else {
      this.param2.setText("" + this.param2.getIntValue()); //$NON-NLS-1$
    }
  }

  /**
   * Coneのとき
   */
  void coneLabel() {
    this.param1.setLabelText(Messages.getString("AddPrimitiveDialog.31")); //$NON-NLS-1$
    this.param2.setLabelText(Messages.getString("AddPrimitiveDialog.32")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.param3.setLabelText(Messages.getString("AddPrimitiveDialog.33")); //$NON-NLS-1$
    this.unitLabel3.setText(" "); //$NON-NLS-1$
    this.param3.setVisible(true);
    if (this.param3.getIntValue() < 3) {
      this.param3.setText("30"); //$NON-NLS-1$
    } else {
      this.param3.setText("" + this.param3.getIntValue()); //$NON-NLS-1$
    }
  }

  /**
   * コンボボックス primCombo プリミティブを選択
   */
  private void createPrimCombo() {
    this.primCombo = new Combo(this.sShell, SWT.READ_ONLY);
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 2;
    this.primCombo.setLayoutData(gridData);
    String[] PRIMITIVES = {"Box", "Cylinder", "Sphere", "Cone"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    this.primCombo.setItems(PRIMITIVES);
    // デフォルトはBox
    this.primCombo.setText("Box"); //$NON-NLS-1$
    this.primCombo.addSelectionListener(new PComboCheck());
  }

  /**
   * コンボボックス colorCombo プリミティブの色を選択
   */
  private void createColorCombo() {
    this.colorCombo = new Combo(this.sShell, SWT.READ_ONLY);
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 2;
    this.colorCombo.setLayoutData(gridData);
    String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
    this.colorCombo.setItems(COLORS);
    this.colorCombo.setText("red"); //$NON-NLS-1$
  }
}