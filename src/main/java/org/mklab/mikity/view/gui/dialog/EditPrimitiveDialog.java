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
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.UnitLabel;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLSphere;


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
  private Label primLabel;
  private ParameterInputBox param1;
  private ParameterInputBox param2;
  private ParameterInputBox param3;
  private Label uLabel1;
  private Label uLabel2;
  private Label uLabel3;
  private ParameterInputBox rotX;
  private ParameterInputBox rotY;
  private ParameterInputBox rotZ;
  private ParameterInputBox locX;
  private ParameterInputBox locY;
  private ParameterInputBox locZ;
  private ParameterInputBox color;

  private ParameterInputBox newParam1;
  private ParameterInputBox newParam2;
  private ParameterInputBox newParam3;
  private ParameterInputBox newRotX;
  private ParameterInputBox newRotY;
  private ParameterInputBox newRotZ;
  private ParameterInputBox newLocX;
  private ParameterInputBox newLocY;
  private ParameterInputBox newLocZ;

  private boolean rotB = true;
  private boolean locB = true;
  private boolean rotA = true;
  private boolean locA = true;

  /**
   * コンストラクター
   * 
   * @param parent 親のシェル
   * @param primitive プリミティブ
   * @param group グループ
   */
  public EditPrimitiveDialog(Shell parent, Object primitive, org.mklab.mikity.xml.model.Group group) {
    this.parentShell = parent;
    this.primitive = primitive;
    this.groupName = group.loadName();
    createSShell();
    detectPrim();
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

    this.primLabel = new Label(this.sShell, SWT.NONE);
    setGridLayout(this.primLabel, 2);

    final Group beforeGroup = new Group(this.sShell, SWT.NONE);
    beforeGroup.setText(Messages.getString("EditPrimitiveDialog.2")); //$NON-NLS-1$
    setGridLayout(beforeGroup, 1);
    final GridLayout beforeLayout = new GridLayout(2, true);
    beforeGroup.setLayout(beforeLayout);

    this.param1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "", ""); //$NON-NLS-1$ //$NON-NLS-2$
    this.param2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "", ""); //$NON-NLS-1$ //$NON-NLS-2$
    this.param3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "", ""); //$NON-NLS-1$ //$NON-NLS-2$
    final Label label = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);

    setGridLayout(label, 2);

    this.rotX = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.3"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.rotY = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.4"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.rotZ = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.5"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label2 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);

    setGridLayout(label2, 2);
    this.locX = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.6"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.locY = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.7"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.locZ = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditPrimitiveDialog.8"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label3 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);

    setGridLayout(label3, 2);

    this.color = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "color", ""); //$NON-NLS-1$ //$NON-NLS-2$

    this.afterGroup = new Group(this.sShell, SWT.NONE);
    this.afterGroup.setText(Messages.getString("EditPrimitiveDialog.9")); //$NON-NLS-1$
    setGridLayout(this.afterGroup, 1);

    final GridLayout afterLayout = new GridLayout(3, false);
    this.afterGroup.setLayout(afterLayout);
    this.newParam1 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "");  //$NON-NLS-1$//$NON-NLS-2$
    this.uLabel1 = new Label(this.afterGroup, SWT.NONE);
    this.uLabel1.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.uLabel1, 1);

    this.newParam2 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "");  //$NON-NLS-1$//$NON-NLS-2$
    this.uLabel2 = new Label(this.afterGroup, SWT.NONE);
    this.uLabel2.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.uLabel2, 1);

    this.newParam3 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "");  //$NON-NLS-1$//$NON-NLS-2$
    this.uLabel3 = new Label(this.afterGroup, SWT.NONE);
    this.uLabel3.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.uLabel3, 1);

    this.newRotX = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.newRotY = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.newRotZ = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    final Label label5 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5, 3);

    this.newLocX = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newLocY = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newLocZ = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
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
    if (this.param3.isVisible() && this.newParam3.isVisible() && this.newParam3.checkParam() == false) {
      return false;
    }
    if (this.newRotX.checkParam() == false) {
      return false;
    }
    if (this.newRotY.checkParam() == false) {
      return false;
    }
    if (this.newRotZ.checkParam() == false) {
      return false;
    }
    if (this.newLocX.checkParam() == false) {
      return false;
    }
    if (this.newLocY.checkParam() == false) {
      return false;
    }
    if (this.newLocZ.checkParam() == false) {
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

    if (this.newRotX.getFloatValue() == 0 && this.newRotY.getFloatValue() == 0 && this.newRotZ.getFloatValue() == 0) {
      this.rotA = false;
    }
    if (this.newLocX.getFloatValue() == 0 && this.newLocY.getFloatValue() == 0 && this.newLocZ.getFloatValue() == 0) {
      this.locA = false;
    }

    if (this.primitive instanceof XMLBox) {
      final XMLBox box = (XMLBox)this.primitive;
      box.setXsize(this.newParam1.getFloatValue());
      box.setYsize(this.newParam2.getFloatValue());
      box.setZsize(this.newParam3.getFloatValue());
      if (this.rotB == false) {
        if (this.rotA == true) {
          box.setRotation(setRot(rot));
        }
      } else {
        setRot(box.loadRotation());
      }
      if (this.locB == false) {
        if (this.locA == true) {
          box.setLocation(setLoc(loc));
        }
      } else {
        setLoc(box.loadLocation());
      }
      box.setColor(this.colorCombo.getColorComboBox().getText());
    } else if (this.primitive instanceof XMLCylinder) {
      final XMLCylinder cyl = (XMLCylinder)this.primitive;
      cyl.setR(this.newParam1.getFloatValue());
      cyl.setHeight(this.newParam2.getFloatValue());
      cyl.setDiv(setDiv(this.newParam3));

      if (this.rotB == false) {
        if (this.rotA == true) {
          cyl.setRotation(setRot(rot));
        }
      } else {
        setRot(cyl.loadRotation());
      }
      if (this.locB == false) {
        if (this.locA == true) {
          cyl.setLocation(setLoc(loc));
        }
      } else {
        setLoc(cyl.loadLocation());
      }
      cyl.setColor(this.colorCombo.getColorComboBox().getText());
    } else if (this.primitive instanceof XMLSphere) {
      final XMLSphere sph = (XMLSphere)this.primitive;
      sph.setR(this.newParam1.getFloatValue());
      sph.setDiv(setDiv(this.newParam2));
      if (this.rotB == false) {
        if (this.rotA == true) {
          sph.setRotation(setRot(rot));
        }
      } else {
        setRot(sph.loadRotation());
      }
      if (this.locB == false) {
        if (this.locA == true) {
          sph.setLocation(setLoc(loc));
        }
      } else {
        setLoc(sph.loadLocation());
      }
      sph.setColor(this.colorCombo.getColorComboBox().getText());
    } else if (this.primitive instanceof XMLCone) {
      final XMLCone cone = (XMLCone)this.primitive;
      cone.setR(this.newParam1.getFloatValue());
      cone.setHeight(this.newParam2.getFloatValue());
      cone.setDiv(setDiv(this.newParam3));
      if (this.rotB == false) {
        if (this.rotA == true) {
          cone.setRotation(setRot(rot));
        }
      } else {
        setRot(cone.loadRotation());
      }
      if (this.locB == false) {
        if (this.locA == true) {
          cone.setLocation(setLoc(loc));
        }
      } else {
        setLoc(cone.loadLocation());
      }
      cone.setColor(this.colorCombo.getColorComboBox().getText());
    }

  }

  /**
   * Rotationを設定 受け取ったRotationを変更に応じて設定
   * 
   * @param rot
   * @return rot
   */
  private Rotation setRot(Rotation rot) {
    rot.setXrotate(this.newRotX.getFloatValue());
    rot.setYrotate(this.newRotY.getFloatValue());
    rot.setZrotate(this.newRotZ.getFloatValue());
    return rot;

  }

  /**
   * Locationを設定 受け取ったLocationを変更に応じて設定
   * 
   * @param loc
   * @return loc
   */
  private Location setLoc(Location loc) {
    loc.setX(this.newLocX.getFloatValue());
    loc.setY(this.newLocY.getFloatValue());
    loc.setZ(this.newLocZ.getFloatValue());
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
   * primitiveの型を判断し、値を入れる
   */
  private void detectPrim() {
    if (this.primitive instanceof XMLBox) {
      final XMLBox box = (XMLBox)this.primitive;
      this.param1.setText("" + box.loadXsize()); //$NON-NLS-1$
      this.param2.setText("" + box.loadYsize()); //$NON-NLS-1$
      this.param3.setText("" + box.loadZsize()); //$NON-NLS-1$
      this.newParam1.setText("" + box.loadXsize()); //$NON-NLS-1$
      this.newParam2.setText("" + box.loadYsize()); //$NON-NLS-1$
      this.newParam3.setText("" + box.loadZsize()); //$NON-NLS-1$
      final Rotation rot = box.loadRotation();
      final Location loc = box.loadLocation();
      if (rot == null) {
        // 変換前にRotationが存在しなかったことを示す
        this.rotB = false;
      } else {
        getRot(rot);
      }
      if (loc == null) {
        // 変換前にLocationが存在しなかったことを示す
        this.locB = false;
      } else {
        getLoc(loc);
      }
      boxLabel();
      this.primLabel.setText(Messages.getString("EditPrimitiveDialog.28")); //$NON-NLS-1$
      this.color.setText(box.loadColor());
      this.colorCombo.getColorComboBox().setText(box.loadColor());
    } else if (this.primitive instanceof XMLCylinder) {
      final XMLCylinder cyl = (XMLCylinder)this.primitive;
      this.param1.setText("" + cyl.loadR()); //$NON-NLS-1$
      this.param2.setText("" + cyl.loadHeight()); //$NON-NLS-1$
      this.param3.setText("" + cyl.loadDiv()); //$NON-NLS-1$
      this.newParam1.setText("" + cyl.loadR()); //$NON-NLS-1$
      this.newParam2.setText("" + cyl.loadHeight()); //$NON-NLS-1$
      this.newParam3.setText("" + cyl.loadDiv()); //$NON-NLS-1$
      final Rotation rot = cyl.loadRotation();
      final Location loc = cyl.loadLocation();
      if (rot == null) {
        this.rotB = false;
      } else {
        getRot(rot);
      }
      if (loc == null) {
        this.locB = false;
      } else {
        getLoc(loc);
      }
      cylLabel();
      this.primLabel.setText(Messages.getString("EditPrimitiveDialog.29")); //$NON-NLS-1$
      this.color.setText(cyl.loadColor());
      this.colorCombo.getColorComboBox().setText(cyl.loadColor());
    } else if (this.primitive instanceof XMLSphere) {
      final XMLSphere sph = (XMLSphere)this.primitive;
      this.param1.setText("" + sph.loadR()); //$NON-NLS-1$
      this.param2.setText("" + sph.loadDiv()); //$NON-NLS-1$
      this.newParam1.setText("" + sph.loadR()); //$NON-NLS-1$
      this.newParam2.setText("" + sph.loadDiv()); //$NON-NLS-1$
      final Rotation rot = sph.loadRotation();
      final Location loc = sph.loadLocation();
      if (rot == null) {
        this.rotB = false;
      } else {
        getRot(rot);
      }
      if (loc == null) {
        this.locB = false;
      } else {
        getLoc(loc);
      }
      sphLabel();
      this.primLabel.setText(Messages.getString("EditPrimitiveDialog.30")); //$NON-NLS-1$
      this.color.setText(sph.loadColor());
      this.colorCombo.getColorComboBox().setText(sph.loadColor());
    } else if (this.primitive instanceof XMLCone) {
      final XMLCone cone = (XMLCone)this.primitive;
      this.param1.setText("" + cone.loadR()); //$NON-NLS-1$
      this.param2.setText("" + cone.loadHeight()); //$NON-NLS-1$
      this.param3.setText("" + cone.loadDiv()); //$NON-NLS-1$
      this.newParam1.setText("" + cone.loadR()); //$NON-NLS-1$
      this.newParam2.setText("" + cone.loadHeight()); //$NON-NLS-1$
      this.newParam3.setText("" + cone.loadDiv()); //$NON-NLS-1$
      final Rotation rot = cone.loadRotation();
      final Location loc = cone.loadLocation();
      if (rot == null) {
        this.rotB = false;
      } else {
        getRot(rot);
      }
      if (loc == null) {
        this.locB = false;
      } else {
        getLoc(loc);
      }
      coneLabel();
      this.primLabel.setText(Messages.getString("EditPrimitiveDialog.31")); //$NON-NLS-1$
      this.color.setText(cone.loadColor());
      this.colorCombo.getColorComboBox().setText(cone.loadColor());
    } else if (this.primitive instanceof XMLConnector) {
      //
    }
  }

  /**
   * primitiveがBoxのとき
   */
  public void boxLabel() {
    this.param1.setLabelText(Messages.getString("EditPrimitiveDialog.32")); //$NON-NLS-1$
    this.param2.setLabelText(Messages.getString("EditPrimitiveDialog.33")); //$NON-NLS-1$
    this.param3.setLabelText(Messages.getString("EditPrimitiveDialog.34")); //$NON-NLS-1$
    this.param3.setVisible(true);
    this.newParam3.setVisible(true);
    this.uLabel2.setVisible(true);
    this.uLabel3.setVisible(true);
  }

  /**
   * primitiveがCylinderのとき
   */
  public void cylLabel() {
    this.param1.setLabelText(Messages.getString("EditPrimitiveDialog.35")); //$NON-NLS-1$
    this.param2.setLabelText(Messages.getString("EditPrimitiveDialog.36")); //$NON-NLS-1$
    this.param3.setLabelText(Messages.getString("EditPrimitiveDialog.37")); //$NON-NLS-1$
    this.param3.setVisible(true);
    int div = 0;
    if (this.param3.getIntValue() < 3) {
      div = 30;
    } else {
      div = this.param3.getIntValue();
    }
    this.param3.setText("" + div); //$NON-NLS-1$
    this.newParam3.setVisible(true);
    this.newParam3.setText("" + div); //$NON-NLS-1$
    this.uLabel2.setVisible(true);
    this.uLabel3.setVisible(false);
  }

  /**
   * primitiveがSphereのとき
   */
  public void sphLabel() {
    this.param1.setLabelText(Messages.getString("EditPrimitiveDialog.38")); //$NON-NLS-1$
    this.param2.setLabelText(Messages.getString("EditPrimitiveDialog.39")); //$NON-NLS-1$
    this.param3.setVisible(false);
    int div = 0;
    if (this.param2.getIntValue() < 3) {
      div = 30;
    } else {
      div = this.param2.getIntValue();
    }
    this.param2.setText("" + div); //$NON-NLS-1$
    this.newParam2.setText("" + div); //$NON-NLS-1$
    this.newParam3.setVisible(false);
    this.uLabel2.setVisible(false);
    this.uLabel3.setVisible(false);
  }

  /**
   * primitiveがConeのとき
   */
  public void coneLabel() {
    this.param1.setLabelText(Messages.getString("EditPrimitiveDialog.40")); //$NON-NLS-1$
    this.param2.setLabelText(Messages.getString("EditPrimitiveDialog.41")); //$NON-NLS-1$
    this.param3.setLabelText(Messages.getString("EditPrimitiveDialog.42")); //$NON-NLS-1$
    this.param3.setVisible(true);
    int div = 0;
    if (this.param3.getIntValue() < 3) {
      div = 30;
    } else {
      div = this.param3.getIntValue();
    }
    this.param3.setText("" + div); //$NON-NLS-1$
    this.newParam3.setVisible(true);
    this.newParam3.setText("" + div); //$NON-NLS-1$
    this.uLabel2.setVisible(true);
    this.uLabel3.setVisible(false);
  }

  /**
   * 平行移動、回転移動の値を読み取る 空の場合はゼロを入れる
   * 
   * @param rot
   */
  private void getRot(Rotation rot) {
    if (rot.hasXrotate() || rot.loadXrotate() != 0.0f) {
      this.rotX.setText("" + rot.loadXrotate()); //$NON-NLS-1$
      this.newRotX.setText("" + rot.loadXrotate()); //$NON-NLS-1$
    }
    if (rot.hasYrotate() || rot.loadYrotate() != 0.0f) {
      this.rotY.setText("" + rot.loadYrotate()); //$NON-NLS-1$
      this.newRotY.setText("" + rot.loadYrotate()); //$NON-NLS-1$
    }
    if (rot.hasZrotate() || rot.loadXrotate() != 0.0f) {
      this.rotZ.setText("" + rot.loadZrotate()); //$NON-NLS-1$
      this.newRotZ.setText("" + rot.loadZrotate()); //$NON-NLS-1$
    }
  }

  private void getLoc(Location loc) {
    if (loc.hasX() || loc.loadX() != 0.0f) {
      this.locX.setText("" + loc.loadX()); //$NON-NLS-1$
      this.newLocX.setText("" + loc.loadX()); //$NON-NLS-1$
    }
    if (loc.hasY() || loc.loadY() != 0.0f) {
      this.locY.setText("" + loc.loadY()); //$NON-NLS-1$
      this.newLocY.setText("" + loc.loadY()); //$NON-NLS-1$
    }
    if (loc.hasZ() || loc.loadZ() != 0.0f) {
      this.locZ.setText("" + loc.loadZ()); //$NON-NLS-1$
      this.newLocZ.setText("" + loc.loadZ()); //$NON-NLS-1$
    }
  }
}