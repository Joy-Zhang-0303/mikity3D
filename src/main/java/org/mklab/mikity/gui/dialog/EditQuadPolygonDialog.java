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
  Shell sShell;
  private XMLQuadPolygon quad;
  private String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$

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
   * 
   * @param parentShell 親のシェル
   * @param quad ポリゴン
   * @param group グループ
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
    this.sShell.setText(Messages.getString("EditQuadPolygonDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);

    Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("EditQuadPolygonDialog.1") + this.groupName); //$NON-NLS-1$
    setGridLayout(groupLabel, 2);

    this.primLabel = new Label(this.sShell, SWT.NONE);
    setGridLayout(this.primLabel, 2);

    Group beforeGroup = new Group(this.sShell, SWT.NONE);
    beforeGroup.setText(Messages.getString("EditQuadPolygonDialog.2")); //$NON-NLS-1$
    setGridLayout(beforeGroup, 1);
    GridLayout beforeLayout = new GridLayout(2, true);
    beforeGroup.setLayout(beforeLayout);

    this.paramX_1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.3"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramY_1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.4"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramZ_1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.5"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    Label label1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label1, 2);

    this.paramX_2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.6"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramY_2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.7"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramZ_2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.8"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    Label label2 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label2, 2);
    this.paramX_3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点3 X座標", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.paramY_3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.9"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramZ_3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.10"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    Label label3 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label3, 2);

    this.paramX_4 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.11"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramY_4 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.12"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramZ_4 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.13"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.color = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "color", ""); //$NON-NLS-1$ //$NON-NLS-2$
    Label label4 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label4, 2);

    this.paramR_X = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.14"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramR_Y = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.15"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramR_Z = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.16"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    Label labelR1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelR1, 2);

    this.paramL_X = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.17"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramL_Y = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.18"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.paramL_Z = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditQuadPolygonDialog.19"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    Label labelL1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelL1, 2);

    this.afterGroup = new Group(this.sShell, SWT.NONE);
    this.afterGroup.setText(Messages.getString("EditQuadPolygonDialog.20")); //$NON-NLS-1$
    setGridLayout(this.afterGroup, 1);
    GridLayout afterLayout = new GridLayout(3, false);
    this.afterGroup.setLayout(afterLayout);

    this.newParamX_1 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newParamY_1 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newParamZ_1 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    Label label5 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5, 3);

    this.newParamX_2 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newParamY_2 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newParamZ_2 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    Label label6 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label6, 3);

    this.newParamX_3 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newParamY_3 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newParamZ_3 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    Label label7 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label7, 3);

    this.newParamX_4 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newParamY_4 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newParamZ_4 = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    Label colorLabel4 = new Label(this.afterGroup, SWT.RIGHT);
    colorLabel4.setText("→"); //$NON-NLS-1$
    setGridLayout(colorLabel4, 1);
    this.colorCombo = new ColorComboBox(this.afterGroup, this.COLORS);
    this.colorCombo.createColorCombo();
    Label label8 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label8, 3);

    this.newParamR_X = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.newParamR_Y = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.newParamR_Z = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$

    Label labelR2 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelR2, 3);

    this.newParamL_X = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newParamL_Y = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newParamL_Z = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    Label labelL2 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
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
    final Composite comp = new Composite(this.sShell, SWT.NONE);
    setGridLayout(comp, 2);

    GridLayout compLayout = new GridLayout(2, true);
    comp.setLayout(compLayout);

    Button okButton = new Button(comp, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("EditQuadPolygonDialog.40")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        // 数字以外が入っていないかを判断
        if (Check()) {
          MessageBox mesBox = new MessageBox(EditQuadPolygonDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage(Messages.getString("EditQuadPolygonDialog.41")); //$NON-NLS-1$
          mesBox.setText(Messages.getString("EditQuadPolygonDialog.42")); //$NON-NLS-1$
          int result = mesBox.open();
          if (result == SWT.YES) {
            setParam();
            EditQuadPolygonDialog.this.sShell.close();
          }
        } else {
          MessageBox mgb = new MessageBox(EditQuadPolygonDialog.this.sShell, SWT.ICON_WARNING);
          mgb.setMessage(Messages.getString("EditQuadPolygonDialog.43")); //$NON-NLS-1$
          mgb.setText(Messages.getString("EditQuadPolygonDialog.44")); //$NON-NLS-1$
          mgb.open();
        }
      }
    });

    Button cancelButton = new Button(comp, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("EditQuadPolygonDialog.45")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        MessageBox mesBox = new MessageBox(EditQuadPolygonDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        mesBox.setMessage(Messages.getString("EditQuadPolygonDialog.46")); //$NON-NLS-1$
        mesBox.setText(Messages.getString("EditQuadPolygonDialog.47")); //$NON-NLS-1$
        int result = mesBox.open();
        if (result == SWT.YES) {
          EditQuadPolygonDialog.this.sShell.close();
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
    if (this.newParamX_1.checkParam() == false) {
      return false;
    }
    if (this.newParamY_1.checkParam() == false) {
      return false;
    }
    if (this.newParamZ_1.checkParam() == false) {
      return false;
    }
    if (this.newParamX_2.checkParam() == false) {
      return false;
    }
    if (this.newParamY_2.checkParam() == false) {
      return false;
    }
    if (this.newParamZ_2.checkParam() == false) {
      return false;
    }
    if (this.newParamX_3.checkParam() == false) {
      return false;
    }
    if (this.newParamY_3.checkParam() == false) {
      return false;
    }
    if (this.newParamZ_3.checkParam() == false) {
      return false;
    }
    if (this.newParamX_4.checkParam() == false) {
      return false;
    }
    if (this.newParamY_4.checkParam() == false) {
      return false;
    }
    if (this.newParamZ_4.checkParam() == false) {
      return false;
    }
    if (this.newParamR_X.checkParam() == false) {
      return false;
    }
    if (this.newParamR_Y.checkParam() == false) {
      return false;
    }
    if (this.newParamR_Z.checkParam() == false) {
      return false;
    }
    if (this.newParamL_X.checkParam() == false) {
      return false;
    }
    if (this.newParamL_Y.checkParam() == false) {
      return false;
    }
    if (this.newParamL_Z.checkParam() == false) {
      return false;
    }
    return true;
  }

  /**
   * パラメータを変更する
   */
  void setParam() {
    Location[] newLoc = new Location[4];

    newLoc[0] = new Location(this.newParamX_1.getFloatValue(), this.newParamY_1.getFloatValue(), this.newParamZ_1.getFloatValue());
    newLoc[1] = new Location(this.newParamX_2.getFloatValue(), this.newParamY_2.getFloatValue(), this.newParamZ_2.getFloatValue());
    newLoc[2] = new Location(this.newParamX_3.getFloatValue(), this.newParamY_3.getFloatValue(), this.newParamZ_3.getFloatValue());
    newLoc[3] = new Location(this.newParamX_4.getFloatValue(), this.newParamY_4.getFloatValue(), this.newParamZ_4.getFloatValue());

    String newColor = this.colorCombo.getColorComboBox().getText();

    this.quad.setPointLocations(newLoc);
    this.quad.setColor(newColor);
    this.quad.setRotation(new Rotation(this.newParamR_X.getFloatValue(), this.newParamR_Y.getFloatValue(), this.newParamR_Z.getFloatValue()));
    this.quad.setLocation(new Location(this.newParamL_X.getFloatValue(), this.newParamL_Y.getFloatValue(), this.newParamL_Z.getFloatValue()));
  }

  /**
   * 各頂点の座標値を色を入れる　変更後の欄にはデフォルトで変更前の値を入力
   */
  private void detectPrim() {
    this.paramX_1.setText("" + this.quad.loadPointLocationX(0)); //$NON-NLS-1$
    this.paramY_1.setText("" + this.quad.loadPointLocationY(0)); //$NON-NLS-1$
    this.paramZ_1.setText("" + this.quad.loadPointLocationZ(0)); //$NON-NLS-1$
    this.newParamX_1.setText("" + this.quad.loadPointLocationX(0)); //$NON-NLS-1$
    this.newParamY_1.setText("" + this.quad.loadPointLocationY(0)); //$NON-NLS-1$
    this.newParamZ_1.setText("" + this.quad.loadPointLocationZ(0)); //$NON-NLS-1$

    this.paramX_2.setText("" + this.quad.loadPointLocationX(1)); //$NON-NLS-1$
    this.paramY_2.setText("" + this.quad.loadPointLocationY(1)); //$NON-NLS-1$
    this.paramZ_2.setText("" + this.quad.loadPointLocationZ(1)); //$NON-NLS-1$
    this.newParamX_2.setText("" + this.quad.loadPointLocationX(1)); //$NON-NLS-1$
    this.newParamY_2.setText("" + this.quad.loadPointLocationY(1)); //$NON-NLS-1$
    this.newParamZ_2.setText("" + this.quad.loadPointLocationZ(1)); //$NON-NLS-1$

    this.paramX_3.setText("" + this.quad.loadPointLocationX(2)); //$NON-NLS-1$
    this.paramY_3.setText("" + this.quad.loadPointLocationY(2)); //$NON-NLS-1$
    this.paramZ_3.setText("" + this.quad.loadPointLocationZ(2)); //$NON-NLS-1$
    this.newParamX_3.setText("" + this.quad.loadPointLocationX(2)); //$NON-NLS-1$
    this.newParamY_3.setText("" + this.quad.loadPointLocationY(2)); //$NON-NLS-1$
    this.newParamZ_3.setText("" + this.quad.loadPointLocationZ(2)); //$NON-NLS-1$

    this.paramX_4.setText("" + this.quad.loadPointLocationX(3)); //$NON-NLS-1$
    this.paramY_4.setText("" + this.quad.loadPointLocationY(3)); //$NON-NLS-1$
    this.paramZ_4.setText("" + this.quad.loadPointLocationZ(3)); //$NON-NLS-1$
    this.newParamX_4.setText("" + this.quad.loadPointLocationX(3)); //$NON-NLS-1$
    this.newParamY_4.setText("" + this.quad.loadPointLocationY(3)); //$NON-NLS-1$
    this.newParamZ_4.setText("" + this.quad.loadPointLocationZ(3)); //$NON-NLS-1$
    this.color.setText(this.quad.loadColor());
    this.colorCombo.getColorComboBox().setText(this.quad.loadColor());

    if (this.quad.loadRotation() != null) {
      this.paramR_X.setText("" + this.quad.loadRotation().loadXrotate()); //$NON-NLS-1$
      this.paramR_Y.setText("" + this.quad.loadRotation().loadYrotate()); //$NON-NLS-1$
      this.paramR_Z.setText("" + this.quad.loadRotation().loadZrotate()); //$NON-NLS-1$
      this.newParamR_X.setText("" + this.quad.loadRotation().loadXrotate()); //$NON-NLS-1$
      this.newParamR_Y.setText("" + this.quad.loadRotation().loadYrotate()); //$NON-NLS-1$
      this.newParamR_Z.setText("" + this.quad.loadRotation().loadZrotate()); //$NON-NLS-1$
    } else {
      this.paramR_X.setText("" + 0.0); //$NON-NLS-1$
      this.paramR_Y.setText("" + 0.0); //$NON-NLS-1$
      this.paramR_Z.setText("" + 0.0); //$NON-NLS-1$
      this.newParamR_X.setText("" + 0.0); //$NON-NLS-1$
      this.newParamR_Y.setText("" + 0.0); //$NON-NLS-1$
      this.newParamR_Z.setText("" + 0.0); //$NON-NLS-1$
    }

    if (this.quad.loadLocation() != null) {
      this.paramL_X.setText("" + this.quad.loadLocation().loadX()); //$NON-NLS-1$
      this.paramL_Y.setText("" + this.quad.loadLocation().loadY()); //$NON-NLS-1$
      this.paramL_Z.setText("" + this.quad.loadLocation().loadZ()); //$NON-NLS-1$
      this.newParamL_X.setText("" + this.quad.loadLocation().loadX()); //$NON-NLS-1$
      this.newParamL_Y.setText("" + this.quad.loadLocation().loadY()); //$NON-NLS-1$
      this.newParamL_Z.setText("" + this.quad.loadLocation().loadZ()); //$NON-NLS-1$
    } else {
      this.paramL_X.setText("" + 0.0); //$NON-NLS-1$
      this.paramL_Y.setText("" + 0.0); //$NON-NLS-1$
      this.paramL_Z.setText("" + 0.0); //$NON-NLS-1$
      this.newParamL_X.setText("" + 0.0); //$NON-NLS-1$
      this.newParamL_Y.setText("" + 0.0); //$NON-NLS-1$
      this.newParamL_Z.setText("" + 0.0); //$NON-NLS-1$
    }

  }
}
