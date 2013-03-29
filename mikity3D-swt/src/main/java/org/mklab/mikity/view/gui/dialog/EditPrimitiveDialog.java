/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

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
import org.mklab.mikity.model.xml.simplexml.model.Location;
import org.mklab.mikity.model.xml.simplexml.model.Rotation;
import org.mklab.mikity.model.xml.simplexml.model.XMLBox;
import org.mklab.mikity.model.xml.simplexml.model.XMLCone;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;
import org.mklab.mikity.model.xml.simplexml.model.XMLSphere;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * プリミティブの編集を行うクラス
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/02/09
 */
public class EditPrimitiveDialog {

  private Shell parentShell;
  /** */
  Shell sShell;
  private Object primitive;
  private String groupName;
  private String[] colors = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
  private ColorComboBox colorCombo;
  private Group afterGroup;
  private Label primitiveLabel;
  private ParameterInputBox parameter1;
  private ParameterInputBox parameter2;
  private ParameterInputBox parameter3;
  private Label uLabel1;
  private Label uLabel2;
  private Label uLabel3;
  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;
  private ParameterInputBox locationX;
  private ParameterInputBox locationY;
  private ParameterInputBox locationZ;
  private ParameterInputBox color;

  private ParameterInputBox newParam1;
  private ParameterInputBox newParam2;
  private ParameterInputBox newParam3;
  private ParameterInputBox newRotationX;
  private ParameterInputBox newRotationY;
  private ParameterInputBox newRotationZ;
  private ParameterInputBox newLocationX;
  private ParameterInputBox newLocationY;
  private ParameterInputBox newLocationZ;

  private boolean rotationB = true;
  private boolean locationB = true;
  private boolean rotationA = true;
  private boolean locationA = true;

  /**
   * コンストラクター
   * 
   * @param parent 親のシェル
   * @param primitive プリミティブ
   * @param group グループ
   */
  public EditPrimitiveDialog(Shell parent, Object primitive, org.mklab.mikity.model.xml.simplexml.model.Group group) {
    this.parentShell = parent;
    this.primitive = primitive;
    this.groupName = group.getName();
    createSShell();
    detectPrimitve();
  }

  /**
   * コンストラクター
   */
  public EditPrimitiveDialog() {
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
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(350, 500));
    this.sShell.setText(Messages.getString("EditPrimitiveDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);

    final Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("EditPrimitiveDialog.1") + this.groupName); //$NON-NLS-1$
    setGridLayout(groupLabel, 2);

    this.primitiveLabel = new Label(this.sShell, SWT.NONE);
    setGridLayout(this.primitiveLabel, 2);

    final Group beforeGroup = new Group(this.sShell, SWT.NONE);
    beforeGroup.setText(Messages.getString("EditPrimitiveDialog.2")); //$NON-NLS-1$
    setGridLayout(beforeGroup, 1);
    final GridLayout beforeLayout = new GridLayout(2, true);
    beforeGroup.setLayout(beforeLayout);

    this.parameter1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "", ""); //$NON-NLS-1$ //$NON-NLS-2$
    this.parameter2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "", ""); //$NON-NLS-1$ //$NON-NLS-2$
    this.parameter3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "", ""); //$NON-NLS-1$ //$NON-NLS-2$
    final Label label = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);

    setGridLayout(label, 2);

    this.rotationX = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.3"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    this.rotationY = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.4"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    this.rotationZ = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.5"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label label2 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);

    setGridLayout(label2, 2);
    this.locationX = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.6"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    this.locationY = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.7"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    this.locationZ = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.8"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label label3 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);

    setGridLayout(label3, 2);

    this.color = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "color", ""); //$NON-NLS-1$ //$NON-NLS-2$

    this.afterGroup = new Group(this.sShell, SWT.NONE);
    this.afterGroup.setText(Messages.getString("EditPrimitiveDialog.9")); //$NON-NLS-1$
    setGridLayout(this.afterGroup, 1);

    final GridLayout afterLayout = new GridLayout(3, false);
    this.afterGroup.setLayout(afterLayout);
    this.newParam1 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", ""); //$NON-NLS-1$//$NON-NLS-2$
    this.uLabel1 = new Label(this.afterGroup, SWT.NONE);
    this.uLabel1.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.uLabel1, 1);

    this.newParam2 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", ""); //$NON-NLS-1$//$NON-NLS-2$
    this.uLabel2 = new Label(this.afterGroup, SWT.NONE);
    this.uLabel2.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.uLabel2, 1);

    this.newParam3 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", ""); //$NON-NLS-1$//$NON-NLS-2$
    this.uLabel3 = new Label(this.afterGroup, SWT.NONE);
    this.uLabel3.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.uLabel3, 1);

    this.newRotationX = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.newRotationY = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.newRotationZ = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    final Label label5 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5, 3);

    this.newLocationX = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newLocationY = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newLocationZ = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    final Label label6 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label6, 3);

    final Label colorLabel = new Label(this.afterGroup, SWT.RIGHT);
    colorLabel.setText("→"); //$NON-NLS-1$
    setGridLayout(colorLabel, 1);

    this.colorCombo = new ColorComboBox(this.afterGroup, this.colors);
    this.colorCombo.createColorCombo();
    final Label spaceLabel = new Label(this.afterGroup, SWT.NONE);
    spaceLabel.setText(" "); //$NON-NLS-1$
    setGridLayout(spaceLabel, 1);

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
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = hSpan;
    control.setLayoutData(gridData);
  }

  /**
   * 変更を決定するButtonを作成
   */
  private void createButtonComp() {
    final Composite comp = new Composite(this.sShell, SWT.NONE);
    setGridLayout(comp, 2);

    final GridLayout compLayout = new GridLayout(2, true);
    comp.setLayout(compLayout);

    final Button okButton = new Button(comp, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("EditPrimitiveDialog.20")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        // 数字以外が入っていないかを判断
        if (Check()) {
          final MessageBox mesBox = new MessageBox(EditPrimitiveDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage(Messages.getString("EditPrimitiveDialog.21")); //$NON-NLS-1$
          mesBox.setText(Messages.getString("EditPrimitiveDialog.22")); //$NON-NLS-1$
          int result = mesBox.open();
          if (result == SWT.YES) {
            setParam();
            EditPrimitiveDialog.this.sShell.close();
          }
        } else {
          final MessageBox mgb = new MessageBox(EditPrimitiveDialog.this.sShell, SWT.ICON_WARNING);
          mgb.setMessage(Messages.getString("EditPrimitiveDialog.23")); //$NON-NLS-1$
          mgb.setText(Messages.getString("EditPrimitiveDialog.24")); //$NON-NLS-1$
          mgb.open();
        }
      }
    });

    final Button cancelButton = new Button(comp, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("EditPrimitiveDialog.25")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        final MessageBox mesBox = new MessageBox(EditPrimitiveDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        mesBox.setMessage(Messages.getString("EditPrimitiveDialog.26")); //$NON-NLS-1$
        mesBox.setText(Messages.getString("EditPrimitiveDialog.27")); //$NON-NLS-1$
        int result = mesBox.open();
        if (result == SWT.YES) {
          EditPrimitiveDialog.this.sShell.close();
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
    if (this.newParam1.checkParam() == false) {
      return false;
    }
    if (this.newParam2.checkParam() == false) {
      return false;
    }
    if (this.parameter3.isVisible() && this.newParam3.isVisible() && this.newParam3.checkParam() == false) {
      return false;
    }
    if (this.newRotationX.checkParam() == false) {
      return false;
    }
    if (this.newRotationY.checkParam() == false) {
      return false;
    }
    if (this.newRotationZ.checkParam() == false) {
      return false;
    }
    if (this.newLocationX.checkParam() == false) {
      return false;
    }
    if (this.newLocationY.checkParam() == false) {
      return false;
    }
    if (this.newLocationZ.checkParam() == false) {
      return false;
    }
    return true;
  }

  /**
   * パラメータを変更する
   */
  void setParam() {
    final Rotation rot = new Rotation();
    final Location loc = new Location();

    if (this.newRotationX.getFloatValue() == 0 && this.newRotationY.getFloatValue() == 0 && this.newRotationZ.getFloatValue() == 0) {
      this.rotationA = false;
    }
    if (this.newLocationX.getFloatValue() == 0 && this.newLocationY.getFloatValue() == 0 && this.newLocationZ.getFloatValue() == 0) {
      this.locationA = false;
    }

    if (this.primitive instanceof XMLBox) {
      final XMLBox box = (XMLBox)this.primitive;
      box.setXsize(this.newParam1.getFloatValue());
      box.setYsize(this.newParam2.getFloatValue());
      box.setZsize(this.newParam3.getFloatValue());
      if (this.rotationB == false) {
        if (this.rotationA == true) {
          box.setRotation(setRotation(rot));
        }
      } else {
        setRotation(box.getRotation());
      }
      if (this.locationB == false) {
        if (this.locationA == true) {
          box.setLocation(setLocation(loc));
        }
      } else {
        setLocation(box.getLocation());
      }
      box.setColor(this.colorCombo.getColorComboBox().getText());
    } else if (this.primitive instanceof XMLCylinder) {
      final XMLCylinder cyl = (XMLCylinder)this.primitive;
      cyl.setRadius(this.newParam1.getFloatValue());
      cyl.setHeight(this.newParam2.getFloatValue());
      cyl.setDiv(setDiv(this.newParam3));

      if (this.rotationB == false) {
        if (this.rotationA == true) {
          cyl.setRotation(setRotation(rot));
        }
      } else {
        setRotation(cyl.getRotation());
      }
      if (this.locationB == false) {
        if (this.locationA == true) {
          cyl.setLocation(setLocation(loc));
        }
      } else {
        setLocation(cyl.getLocation());
      }
      cyl.setColor(this.colorCombo.getColorComboBox().getText());
    } else if (this.primitive instanceof XMLSphere) {
      final XMLSphere sph = (XMLSphere)this.primitive;
      sph.setRadius(this.newParam1.getFloatValue());
      sph.setDiv(setDiv(this.newParam2));
      if (this.rotationB == false) {
        if (this.rotationA == true) {
          sph.setRotation(setRotation(rot));
        }
      } else {
        setRotation(sph.getRotation());
      }
      if (this.locationB == false) {
        if (this.locationA == true) {
          sph.setLocation(setLocation(loc));
        }
      } else {
        setLocation(sph.getLocation());
      }
      sph.setColor(this.colorCombo.getColorComboBox().getText());
    } else if (this.primitive instanceof XMLCone) {
      final XMLCone cone = (XMLCone)this.primitive;
      cone.setRadius(this.newParam1.getFloatValue());
      cone.setHeight(this.newParam2.getFloatValue());
      cone.setDiv(setDiv(this.newParam3));
      if (this.rotationB == false) {
        if (this.rotationA == true) {
          cone.setRotation(setRotation(rot));
        }
      } else {
        setRotation(cone.getRotation());
      }
      if (this.locationB == false) {
        if (this.locationA == true) {
          cone.setLocation(setLocation(loc));
        }
      } else {
        setLocation(cone.getLocation());
      }
      cone.setColor(this.colorCombo.getColorComboBox().getText());
    }

  }

  /**
   * Rotationを設定 受け取ったRotationを変更に応じて設定
   * 
   * @param rotation
   * @return rot
   */
  private Rotation setRotation(Rotation rotation) {
    rotation.setXrotation(this.newRotationX.getFloatValue());
    rotation.setYrotation(this.newRotationY.getFloatValue());
    rotation.setZrotation(this.newRotationZ.getFloatValue());
    return rotation;

  }

  /**
   * Locationを設定 受け取ったLocationを変更に応じて設定
   * 
   * @param location
   * @return loc
   */
  private Location setLocation(Location location) {
    location.setX(this.newLocationX.getFloatValue());
    location.setY(this.newLocationY.getFloatValue());
    location.setZ(this.newLocationZ.getFloatValue());
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
   * primitiveの型を判断し、値を入れる
   */
  private void detectPrimitve() {
    if (this.primitive instanceof XMLBox) {
      final XMLBox box = (XMLBox)this.primitive;
      this.parameter1.setText("" + box.getXsize()); //$NON-NLS-1$
      this.parameter2.setText("" + box.getYsize()); //$NON-NLS-1$
      this.parameter3.setText("" + box.getZsize()); //$NON-NLS-1$
      this.newParam1.setText("" + box.getXsize()); //$NON-NLS-1$
      this.newParam2.setText("" + box.getYsize()); //$NON-NLS-1$
      this.newParam3.setText("" + box.getZsize()); //$NON-NLS-1$
      final Rotation rotation = box.getRotation();
      final Location location = box.getLocation();
      if (rotation == null) {
        // 変換前にRotationが存在しなかったことを示す
        this.rotationB = false;
      } else {
        getRotation(rotation);
      }
      if (location == null) {
        // 変換前にLocationが存在しなかったことを示す
        this.locationB = false;
      } else {
        getLocation(location);
      }
      setBoxLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.28")); //$NON-NLS-1$
      this.color.setText(box.getColor());
      this.colorCombo.getColorComboBox().setText(box.getColor());
    } else if (this.primitive instanceof XMLCylinder) {
      final XMLCylinder cylinder = (XMLCylinder)this.primitive;
      this.parameter1.setText("" + cylinder.getRadius()); //$NON-NLS-1$
      this.parameter2.setText("" + cylinder.getHeight()); //$NON-NLS-1$
      this.parameter3.setText("" + cylinder.getDiv()); //$NON-NLS-1$
      this.newParam1.setText("" + cylinder.getRadius()); //$NON-NLS-1$
      this.newParam2.setText("" + cylinder.getHeight()); //$NON-NLS-1$
      this.newParam3.setText("" + cylinder.getDiv()); //$NON-NLS-1$
      final Rotation rotation = cylinder.getRotation();
      final Location location = cylinder.getLocation();
      if (rotation == null) {
        this.rotationB = false;
      } else {
        getRotation(rotation);
      }
      if (location == null) {
        this.locationB = false;
      } else {
        getLocation(location);
      }
      setCylinderLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.29")); //$NON-NLS-1$
      this.color.setText(cylinder.getColor());
      this.colorCombo.getColorComboBox().setText(cylinder.getColor());
    } else if (this.primitive instanceof XMLSphere) {
      final XMLSphere sphere = (XMLSphere)this.primitive;
      this.parameter1.setText("" + sphere.getRadius()); //$NON-NLS-1$
      this.parameter2.setText("" + sphere.getDiv()); //$NON-NLS-1$
      this.newParam1.setText("" + sphere.getRadius()); //$NON-NLS-1$
      this.newParam2.setText("" + sphere.getDiv()); //$NON-NLS-1$
      final Rotation rotation = sphere.getRotation();
      final Location location = sphere.getLocation();
      if (rotation == null) {
        this.rotationB = false;
      } else {
        getRotation(rotation);
      }
      if (location == null) {
        this.locationB = false;
      } else {
        getLocation(location);
      }
      setSphereLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.30")); //$NON-NLS-1$
      this.color.setText(sphere.getColor());
      this.colorCombo.getColorComboBox().setText(sphere.getColor());
    } else if (this.primitive instanceof XMLCone) {
      final XMLCone cone = (XMLCone)this.primitive;
      this.parameter1.setText("" + cone.getRadisu()); //$NON-NLS-1$
      this.parameter2.setText("" + cone.getHeight()); //$NON-NLS-1$
      this.parameter3.setText("" + cone.getDiv()); //$NON-NLS-1$
      this.newParam1.setText("" + cone.getRadisu()); //$NON-NLS-1$
      this.newParam2.setText("" + cone.getHeight()); //$NON-NLS-1$
      this.newParam3.setText("" + cone.getDiv()); //$NON-NLS-1$
      final Rotation rotation = cone.getRotation();
      final Location location = cone.getLocation();
      if (rotation == null) {
        this.rotationB = false;
      } else {
        getRotation(rotation);
      }
      if (location == null) {
        this.locationB = false;
      } else {
        getLocation(location);
      }
      setConeLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.31")); //$NON-NLS-1$
      this.color.setText(cone.getColor());
      this.colorCombo.getColorComboBox().setText(cone.getColor());
    }
  }

  /**
   * primitiveがBoxのとき
   */
  public void setBoxLabel() {
    this.parameter1.setLabelText(Messages.getString("EditPrimitiveDialog.32")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("EditPrimitiveDialog.33")); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("EditPrimitiveDialog.34")); //$NON-NLS-1$
    this.parameter3.setVisible(true);
    this.newParam3.setVisible(true);
    this.uLabel2.setVisible(true);
    this.uLabel3.setVisible(true);
  }

  /**
   * primitiveがCylinderのとき
   */
  public void setCylinderLabel() {
    this.parameter1.setLabelText(Messages.getString("EditPrimitiveDialog.35")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("EditPrimitiveDialog.36")); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("EditPrimitiveDialog.37")); //$NON-NLS-1$
    this.parameter3.setVisible(true);
    int div = 0;
    if (this.parameter3.getIntValue() < 3) {
      div = 30;
    } else {
      div = this.parameter3.getIntValue();
    }
    this.parameter3.setText("" + div); //$NON-NLS-1$
    this.newParam3.setVisible(true);
    this.newParam3.setText("" + div); //$NON-NLS-1$
    this.uLabel2.setVisible(true);
    this.uLabel3.setVisible(false);
  }

  /**
   * primitiveがSphereのとき
   */
  public void setSphereLabel() {
    this.parameter1.setLabelText(Messages.getString("EditPrimitiveDialog.38")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("EditPrimitiveDialog.39")); //$NON-NLS-1$
    this.parameter3.setVisible(false);
    int div = 0;
    if (this.parameter2.getIntValue() < 3) {
      div = 30;
    } else {
      div = this.parameter2.getIntValue();
    }
    this.parameter2.setText("" + div); //$NON-NLS-1$
    this.newParam2.setText("" + div); //$NON-NLS-1$
    this.newParam3.setVisible(false);
    this.uLabel2.setVisible(false);
    this.uLabel3.setVisible(false);
  }

  /**
   * primitiveがConeのとき
   */
  public void setConeLabel() {
    this.parameter1.setLabelText(Messages.getString("EditPrimitiveDialog.40")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("EditPrimitiveDialog.41")); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("EditPrimitiveDialog.42")); //$NON-NLS-1$
    this.parameter3.setVisible(true);
    int div = 0;
    if (this.parameter3.getIntValue() < 3) {
      div = 30;
    } else {
      div = this.parameter3.getIntValue();
    }
    this.parameter3.setText("" + div); //$NON-NLS-1$
    this.newParam3.setVisible(true);
    this.newParam3.setText("" + div); //$NON-NLS-1$
    this.uLabel2.setVisible(true);
    this.uLabel3.setVisible(false);
  }

  /**
   * 平行移動、回転移動の値を読み取る 空の場合はゼロを入れる
   * 
   * @param rotation
   */
  private void getRotation(Rotation rotation) {
    this.rotationX.setText("" + rotation.getXrotation()); //$NON-NLS-1$
    this.newRotationX.setText("" + rotation.getXrotation()); //$NON-NLS-1$
    this.rotationY.setText("" + rotation.getYrotation()); //$NON-NLS-1$
    this.newRotationY.setText("" + rotation.getYrotation()); //$NON-NLS-1$
    this.rotationZ.setText("" + rotation.getZrotation()); //$NON-NLS-1$
    this.newRotationZ.setText("" + rotation.getZrotation()); //$NON-NLS-1$
  }

  private void getLocation(Location location) {
    this.locationX.setText("" + location.getX()); //$NON-NLS-1$
    this.newLocationX.setText("" + location.getX()); //$NON-NLS-1$
    this.locationY.setText("" + location.getY()); //$NON-NLS-1$
    this.newLocationY.setText("" + location.getY()); //$NON-NLS-1$
    this.locationZ.setText("" + location.getZ()); //$NON-NLS-1$
    this.newLocationZ.setText("" + location.getZ()); //$NON-NLS-1$
  }
}