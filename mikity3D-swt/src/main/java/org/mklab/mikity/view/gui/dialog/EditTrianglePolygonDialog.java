/*
 * $Id: EditTrianglePolygonDialog.java,v 1.9 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import java.util.Arrays;

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
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * 三角形ポリゴンを編集するクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.9 $. 2008/02/29
 */
public class EditTrianglePolygonDialog {

  private Shell parentShell;
  Shell sShell;
  private XMLTrianglePolygon triangle;
  private String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$

  private String groupName;
  private Group afterGroup;
  private Label primitiveLabel;
  private ParameterInputBox vertex1X;
  private ParameterInputBox vertex1Y;
  private ParameterInputBox vertex1Z;
  
  private ParameterInputBox vertex2X;
  private ParameterInputBox vertex2Y;
  private ParameterInputBox vertex2Z;
  
  private ParameterInputBox vertex3X;
  private ParameterInputBox vertex3Y;
  private ParameterInputBox vertex3Z;
  
  private ParameterInputBox color;
  private ColorComboBox colorCombo;

  private ParameterInputBox newVertex1X;
  private ParameterInputBox newVertex1Y;
  private ParameterInputBox newVertex1Z;
  
  private ParameterInputBox newVertex2X;
  private ParameterInputBox newVertex2Y;
  private ParameterInputBox newVertex2Z;
  
  private ParameterInputBox newVertex3X;
  private ParameterInputBox newVertex3Y;
  private ParameterInputBox newVertex3Z;
  
  private ParameterInputBox leftVertexX;
  private ParameterInputBox leftVertexY;
  private ParameterInputBox leftVertexZ;
  
  private ParameterInputBox newLeftVertexX;
  private ParameterInputBox newLeftVertexY;
  private ParameterInputBox newLeftVertexZ;
  
  private ParameterInputBox rightVertexX;
  private ParameterInputBox rightVertexY;
  private ParameterInputBox rightVertexZ;
  
  private ParameterInputBox newRightVertexX;
  private ParameterInputBox newRightVertexY;
  private ParameterInputBox newRightVertexZ;

  /**
   * コンストラクター
   * 
   * @param parentShell 親のシェル
   * @param triangle ポリゴン
   * @param group グループ
   */
  public EditTrianglePolygonDialog(Shell parentShell, XMLTrianglePolygon triangle, org.mklab.mikity.model.xml.simplexml.model.Group group) {
    this.parentShell = parentShell;
    this.triangle = triangle;
    this.groupName = group.getName();
    createSShell();
    detectPrim();
  }

  /**
   * コンストラクター
   */
  public EditTrianglePolygonDialog() {
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
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 600));
    this.sShell.setText(Messages.getString("EditTrianglePolygonDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);

    final Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("EditTrianglePolygonDialog.1") + this.groupName); //$NON-NLS-1$
    setGridLayout(groupLabel, 2);

    this.primitiveLabel = new Label(this.sShell, SWT.NONE);
    setGridLayout(this.primitiveLabel, 2);

    final Group beforeGroup = new Group(this.sShell, SWT.NONE);
    beforeGroup.setText(Messages.getString("EditTrianglePolygonDialog.2")); //$NON-NLS-1$
    setGridLayout(beforeGroup, 1);
    final GridLayout beforeLayout = new GridLayout(2, true);
    beforeGroup.setLayout(beforeLayout);

    this.vertex1X = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.3"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex1Y = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.4"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex1Z = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.5"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label1, 2);

    this.vertex2X = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.6"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex2Y = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.7"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex2Z = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.8"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label label2 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label2, 2);
    this.vertex3X = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.9"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex3Y = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.10"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.vertex3Z = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.11"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.color = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "color", ""); //$NON-NLS-1$ //$NON-NLS-2$
    final Label label3 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label3, 2);

    this.rightVertexX = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.12"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.rightVertexY = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.13"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.rightVertexZ = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.14"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label labelR1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelR1, 2);

    this.leftVertexX = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.15"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.leftVertexY = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.16"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    this.leftVertexZ = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, Messages.getString("EditTrianglePolygonDialog.17"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    final Label labelL1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelL1, 2);

    this.afterGroup = new Group(this.sShell, SWT.NONE);
    this.afterGroup.setText(Messages.getString("EditTrianglePolygonDialog.18")); //$NON-NLS-1$
    setGridLayout(this.afterGroup, 1);
    final GridLayout afterLayout = new GridLayout(3, false);
    this.afterGroup.setLayout(afterLayout);

    this.newVertex1X = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex1Y = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex1Z = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    final Label label4 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label4, 3);

    this.newVertex2X = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex2Y = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex2Z = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    final Label label5 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5, 3);

    this.newVertex3X = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex3Y = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex3Z = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    final Label colorLabel = new Label(this.afterGroup, SWT.RIGHT);
    colorLabel.setText("→"); //$NON-NLS-1$
    setGridLayout(colorLabel, 1);
    this.colorCombo = new ColorComboBox(this.afterGroup, this.COLORS);
    this.colorCombo.createColorCombo();
    final Label label6 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label6, 3);

    this.newRightVertexX = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.newRightVertexY = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.newRightVertexZ = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$

    final Label labelR2 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelR2, 3);

    this.newLeftVertexX = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newLeftVertexY = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newLeftVertexZ = new ParameterInputBox(this.afterGroup, SWT.NONE, "→", "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    final Label labelL2 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
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
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = hSpan;
    control.setLayoutData(gridData);
  }

  /**
   * 変更を決定するButtonを作成
   */
  private void createButtonComp() {
    final Composite composite = new Composite(this.sShell, SWT.NONE);
    setGridLayout(composite, 2);

    final GridLayout compLayout = new GridLayout(2, true);
    composite.setLayout(compLayout);

    final Button okButton = new Button(composite, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("EditTrianglePolygonDialog.35")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        // 数字以外が入っていないかを判断
        if (Check()) {
          final MessageBox message = new MessageBox(EditTrianglePolygonDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          message.setMessage(Messages.getString("EditTrianglePolygonDialog.36")); //$NON-NLS-1$
          message.setText(Messages.getString("EditTrianglePolygonDialog.37")); //$NON-NLS-1$
          int yesNo = message.open();
          if (yesNo == SWT.YES) {
            setParam();
            EditTrianglePolygonDialog.this.sShell.close();
          }
        } else {
          final MessageBox message = new MessageBox(EditTrianglePolygonDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("EditTrianglePolygonDialog.38")); //$NON-NLS-1$
          message.setText(Messages.getString("EditTrianglePolygonDialog.39")); //$NON-NLS-1$
          message.open();
        }
      }
    });

    final Button cancelButton = new Button(composite, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("EditTrianglePolygonDialog.40")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        final MessageBox message = new MessageBox(EditTrianglePolygonDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        message.setMessage(Messages.getString("EditTrianglePolygonDialog.41")); //$NON-NLS-1$
        message.setText(Messages.getString("EditTrianglePolygonDialog.42")); //$NON-NLS-1$
        int yesNo = message.open();
        if (yesNo == SWT.YES) {
          EditTrianglePolygonDialog.this.sShell.close();
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
    if (this.newVertex1X.checkParam() == false) {
      return false;
    }
    if (this.newVertex1Y.checkParam() == false) {
      return false;
    }
    if (this.newVertex1Z.checkParam() == false) {
      return false;
    }
    if (this.newVertex2X.checkParam() == false) {
      return false;
    }
    if (this.newVertex2Y.checkParam() == false) {
      return false;
    }
    if (this.newVertex2Z.checkParam() == false) {
      return false;
    }
    if (this.newVertex3X.checkParam() == false) {
      return false;
    }
    if (this.newVertex3Y.checkParam() == false) {
      return false;
    }
    if (this.newVertex3Z.checkParam() == false) {
      return false;
    }
    if (this.newRightVertexX.checkParam() == false) {
      return false;
    }
    if (this.newRightVertexY.checkParam() == false) {
      return false;
    }
    if (this.newRightVertexZ.checkParam() == false) {
      return false;
    }
    if (this.newLeftVertexX.checkParam() == false) {
      return false;
    }
    if (this.newLeftVertexY.checkParam() == false) {
      return false;
    }
    if (this.newLeftVertexZ.checkParam() == false) {
      return false;
    }
    return true;
  }

  /**
   * パラメータを変更する
   */
  void setParam() {
    final Location[] newLocation = new Location[3];

    newLocation[0] = new Location(this.newVertex1X.getFloatValue(), this.newVertex1Y.getFloatValue(), this.newVertex1Z.getFloatValue());
    newLocation[1] = new Location(this.newVertex2X.getFloatValue(), this.newVertex2Y.getFloatValue(), this.newVertex2Z.getFloatValue());
    newLocation[2] = new Location(this.newVertex3X.getFloatValue(), this.newVertex3Y.getFloatValue(), this.newVertex3Z.getFloatValue());

    String newColor = this.colorCombo.getColorComboBox().getText();

    this.triangle.setPointLocations(Arrays.asList(newLocation[0], newLocation[1], newLocation[2]));
    this.triangle.setColor(newColor);
    this.triangle.setRotation(new Rotation(this.newRightVertexX.getFloatValue(), this.newRightVertexY.getFloatValue(), this.newRightVertexZ.getFloatValue()));
    this.triangle.setLocation(new Location(this.newLeftVertexX.getFloatValue(), this.newLeftVertexY.getFloatValue(), this.newLeftVertexZ.getFloatValue()));
  }

  /**
   * 各頂点の座標値を色を入れる　変更後の欄にはデフォルトで変更前の値を入力
   */
  private void detectPrim() {
    this.vertex1X.setText("" + this.triangle.getPointLocationX(0)); //$NON-NLS-1$
    this.vertex1Y.setText("" + this.triangle.getPointLocationY(0)); //$NON-NLS-1$
    this.vertex1Z.setText("" + this.triangle.getPointLocationZ(0)); //$NON-NLS-1$
    this.newVertex1X.setText("" + this.triangle.getPointLocationX(0)); //$NON-NLS-1$
    this.newVertex1Y.setText("" + this.triangle.getPointLocationY(0)); //$NON-NLS-1$
    this.newVertex1Z.setText("" + this.triangle.getPointLocationZ(0)); //$NON-NLS-1$

    this.vertex2X.setText("" + this.triangle.getPointLocationX(1)); //$NON-NLS-1$
    this.vertex2Y.setText("" + this.triangle.getPointLocationY(1)); //$NON-NLS-1$
    this.vertex2Z.setText("" + this.triangle.getPointLocationZ(1)); //$NON-NLS-1$
    this.newVertex2X.setText("" + this.triangle.getPointLocationX(1)); //$NON-NLS-1$
    this.newVertex2Y.setText("" + this.triangle.getPointLocationY(1)); //$NON-NLS-1$
    this.newVertex2Z.setText("" + this.triangle.getPointLocationZ(1)); //$NON-NLS-1$

    this.vertex3X.setText("" + this.triangle.getPointLocationX(2)); //$NON-NLS-1$
    this.vertex3Y.setText("" + this.triangle.getPointLocationY(2)); //$NON-NLS-1$
    this.vertex3Z.setText("" + this.triangle.getPointLocationZ(2)); //$NON-NLS-1$
    this.newVertex3X.setText("" + this.triangle.getPointLocationX(2)); //$NON-NLS-1$
    this.newVertex3Y.setText("" + this.triangle.getPointLocationY(2)); //$NON-NLS-1$
    this.newVertex3Z.setText("" + this.triangle.getPointLocationZ(2)); //$NON-NLS-1$
    this.color.setText(this.triangle.getColor());
    this.colorCombo.getColorComboBox().setText(this.triangle.getColor());

    if (this.triangle.getRotation() != null) {
      this.rightVertexX.setText("" + this.triangle.getRotation().getXrotation()); //$NON-NLS-1$
      this.rightVertexY.setText("" + this.triangle.getRotation().getYrotation()); //$NON-NLS-1$
      this.rightVertexZ.setText("" + this.triangle.getRotation().getZrotation()); //$NON-NLS-1$
      this.newRightVertexX.setText("" + this.triangle.getRotation().getXrotation()); //$NON-NLS-1$
      this.newRightVertexY.setText("" + this.triangle.getRotation().getYrotation()); //$NON-NLS-1$
      this.newRightVertexZ.setText("" + this.triangle.getRotation().getZrotation()); //$NON-NLS-1$
    } else {
      this.rightVertexX.setText("" + 0.0); //$NON-NLS-1$
      this.rightVertexY.setText("" + 0.0); //$NON-NLS-1$
      this.rightVertexZ.setText("" + 0.0); //$NON-NLS-1$
      this.newRightVertexX.setText("" + 0.0); //$NON-NLS-1$
      this.newRightVertexY.setText("" + 0.0); //$NON-NLS-1$
      this.newRightVertexZ.setText("" + 0.0); //$NON-NLS-1$
    }

    if (this.triangle.getLocation() != null) {
      this.leftVertexX.setText("" + this.triangle.getLocation().getX()); //$NON-NLS-1$
      this.leftVertexY.setText("" + this.triangle.getLocation().getY()); //$NON-NLS-1$
      this.leftVertexZ.setText("" + this.triangle.getLocation().getZ()); //$NON-NLS-1$
      this.newLeftVertexX.setText("" + this.triangle.getLocation().getX()); //$NON-NLS-1$
      this.newLeftVertexY.setText("" + this.triangle.getLocation().getY()); //$NON-NLS-1$
      this.newLeftVertexZ.setText("" + this.triangle.getLocation().getZ()); //$NON-NLS-1$
    } else {
      this.leftVertexX.setText("" + 0.0); //$NON-NLS-1$
      this.leftVertexY.setText("" + 0.0); //$NON-NLS-1$
      this.leftVertexZ.setText("" + 0.0); //$NON-NLS-1$
      this.newLeftVertexX.setText("" + 0.0); //$NON-NLS-1$
      this.newLeftVertexY.setText("" + 0.0); //$NON-NLS-1$
      this.newLeftVertexZ.setText("" + 0.0); //$NON-NLS-1$
    }
  }
}
