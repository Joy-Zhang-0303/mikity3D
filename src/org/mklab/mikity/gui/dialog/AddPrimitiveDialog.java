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
 * @author miki
 * @version $Revision: 1.3 $.2005/02/09
 */
public class AddPrimitiveDialog {

  private Shell parentShell;
  private Shell sShell;

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
  

  private int selectedIndex = boxFlag;
  private static final int boxFlag = 0;
  private static final int cylFlag = 1;
  private static final int sphFlag = 2;
  private static final int coneFlag = 3;
  
  //private CollisionCanceller dc;

  /**
   * コンストラクター
   * 
   * @param parentShell 親シェル
   * @param group グループ
   */
  public AddPrimitiveDialog(Shell parentShell, Group group, CollisionCanceller dc) {
    this.parentShell = parentShell;
    this.group = group;
    angleUnit = UnitLabel.getUnit("modelAngle");
    lengthUnit = UnitLabel.getUnit("modelLength");
    createSShell();
    
    //this.dc = dc;
  }

  /**
   * シェルの作成
   */
  private void createSShell() {
    sShell = new Shell(parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 3;
    sShell.setSize(new org.eclipse.swt.graphics.Point(280, 400));
    sShell.setText("Primitiveの追加");
    sShell.setLayout(layout);

    Label groupLabel = new Label(sShell, SWT.LEFT);
    groupLabel.setText("追加するグループ  :  " + group.loadName());
    GridData gLabelData = new GridData(GridData.FILL_HORIZONTAL);
    gLabelData.horizontalSpan = 3;
    groupLabel.setLayoutData(gLabelData);

    Label primLabel = new Label(sShell, SWT.RIGHT);
    primLabel.setText("primitive");
    GridData labelData = new GridData(GridData.FILL_HORIZONTAL);
    labelData.widthHint = 80;
    primLabel.setLayoutData(labelData);
    createPrimCombo();

    param1 = new ParameterInputBox(sShell, SWT.NONE, "幅", "0.2");
    unitLabel1 = new Label(sShell, SWT.NONE);
    unitLabel1.setText(lengthUnit);
    
    param2 = new ParameterInputBox(sShell, SWT.NONE, "高さ", "0.2");
    unitLabel2 = new Label(sShell, SWT.NONE);
    unitLabel2.setText(lengthUnit);
    
    param3 = new ParameterInputBox(sShell, SWT.NONE, "奥行き", "0.2");
    unitLabel3 = new Label(sShell, SWT.NONE);
    unitLabel3.setText(lengthUnit);

    GridData rotData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group rotateGroup = new org.eclipse.swt.widgets.Group(sShell, SWT.NONE);
    rotateGroup.setText("回転移動");
    layout = new GridLayout();
    layout.numColumns = 3;
    rotateGroup.setLayout(layout);
    rotData = new GridData(GridData.FILL_HORIZONTAL);
    rotData.horizontalSpan = 3;
    rotateGroup.setLayoutData(rotData);

    rotX = new ParameterInputBox(rotateGroup, SWT.NONE, "X軸周り", "0.0");
    Label unitLabelRX = new Label(rotateGroup, SWT.NONE);
    unitLabelRX.setText(angleUnit + " ");
    rotY = new ParameterInputBox(rotateGroup, SWT.NONE, "Y軸周り", "0.0");
    Label unitLabelRY = new Label(rotateGroup, SWT.NONE);
    unitLabelRY.setText(angleUnit + " ");
    rotZ = new ParameterInputBox(rotateGroup, SWT.NONE, "Z軸周り", "0.0");
    Label unitLabelRZ = new Label(rotateGroup, SWT.NONE);
    unitLabelRZ.setText(angleUnit + " ");
    
    GridData locData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group locationGroup = new org.eclipse.swt.widgets.Group(sShell, SWT.NONE);
    locationGroup.setText("平行移動");
    layout = new GridLayout();
    layout.numColumns = 3;
    locationGroup.setLayout(layout);
    locData = new GridData(GridData.FILL_HORIZONTAL);
    locData.horizontalSpan = 3;
    locationGroup.setLayoutData(locData);

    locX = new ParameterInputBox(locationGroup, SWT.NONE, "X軸方向", "0");
    Label unitLabelLX = new Label(locationGroup, SWT.NONE);
    unitLabelLX.setText(lengthUnit + " ");
    locY = new ParameterInputBox(locationGroup, SWT.NONE, "Y軸方向", "0");
    Label unitLabelLY = new Label(locationGroup, SWT.NONE);
    unitLabelLY.setText(lengthUnit + " ");
    locZ = new ParameterInputBox(locationGroup, SWT.NONE, "Z軸方向", "0");
    Label unitLabelLZ = new Label(locationGroup, SWT.NONE);
    unitLabelLZ.setText(lengthUnit + " ");

    GridData labelData2 = new GridData(GridData.FILL_HORIZONTAL);
    Label colorLabel = new Label(sShell, SWT.RIGHT);
    colorLabel.setText("color");
    colorLabel.setLayoutData(labelData2);
    createColorCombo();
    
    Label spaceLabel = new Label(sShell, SWT.NONE);
    spaceLabel.setText(" ");
    
    Button okButton = new Button(sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText("プリミティブを追加");

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (Check()) {
        	//dc.checkDuplication(,.getLocation,group);
          addPrim();
          sShell.close();
        } else {
          MessageBox mgb = new MessageBox(sShell, SWT.ICON_WARNING);
          mgb.setMessage("パラメータ数字以外が入っています。\n 入力しなおして下さい。");
          mgb.setText("Warning!!");
        }
      }
    });

    Button cancelButton = new Button(sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(" キャンセル ");

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        MessageBox mesBox = new MessageBox(sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        mesBox.setMessage("変更を中止して終了します");
        mesBox.setText("確認");
        int result = mesBox.open();
        if (result == SWT.YES) {
          sShell.close();
        }
      }
    });
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  private boolean Check() {
    if (param1.checkParam() == false) {
      return false;
    }
    if (param2.checkParam() == false) {
      return false;
    }
    if (param3.checkParam() == false) {
      return false;
    }
    if (rotX.checkParam() == false) {
      return false;
    }
    if (rotY.checkParam() == false) {
      return false;
    }
    if (rotZ.checkParam() == false) {
      return false;
    }
    if (locX.checkParam() == false) {
      return false;
    }
    if (locY.checkParam() == false) {
      return false;
    }
    if (locZ.checkParam() == false) {
      return false;
    }
    return true;
  }

  /**
   * プリミティブを追加する
   */
  private void addPrim() {
    Rotation rot = new Rotation();
    Location loc = new Location();

    switch (selectedIndex) {
      case boxFlag:
        XMLBox box = new XMLBox();
        box.setXsize(param1.getFloatValue());
        box.setYsize(param2.getFloatValue());
        box.setZsize(param3.getFloatValue());
        if (getRot(rot) != null) {
          box.setRotation(getRot(rot));
        }
        if (getLoc(loc) != null) {
          box.setLocation(getLoc(loc));
        }
        box.setColor(colorCombo.getText());
        //dc.checkCollision(box,box.loadLocation(),group);
        box.setLocation(loc);
		group.addXMLBox(box);
       
        break;
      case cylFlag:
        XMLCylinder cyl = new XMLCylinder();
        cyl.setR(param1.getFloatValue());
        cyl.setHeight(param2.getFloatValue());
        cyl.setDiv(setDiv(param3));
        if (getRot(rot) != null) {
          cyl.setRotation(getRot(rot));
        }
        if (getLoc(loc) != null) {
          cyl.setLocation(getLoc(loc));
        }
        cyl.setColor(colorCombo.getText());
      //dc.checkCollision(cyl,cyl.loadLocation(),group);
        cyl.setLocation(loc);
		group.addXMLCylinder(cyl);
        
        break;
      case sphFlag:
        XMLSphere sph = new XMLSphere();
        sph.setR(param1.getFloatValue());
        sph.setDiv(setDiv(param2));
        if (getRot(rot) != null) {
          sph.setRotation(getRot(rot));
        }
        if (getLoc(loc) != null) {
          sph.setLocation(getLoc(loc));
        }
        sph.setColor(colorCombo.getText());
     //dc.checkCollision(sph,sph.loadLocation(),group);
        sph.setLocation(loc);
		group.addXMLSphere(sph);
       
        break;
      case coneFlag:
        XMLCone cone = new XMLCone();
        cone.setR(param1.getFloatValue());
        cone.setHeight(param2.getFloatValue());
        cone.setDiv(setDiv(param3));
        if (getRot(rot) != null) {
          cone.setRotation(getRot(rot));
        }
        if (getLoc(loc) != null) {
          cone.setLocation(getLoc(loc));
        }
        cone.setColor(colorCombo.getText());
      //dc.checkCollision(cone,cone.loadLocation(),group);
        cone.setLocation(loc);
		group.addXMLCone(cone);
        
        break;
    }

  }

  /**
   * Rotationの値を設定 param Rotation return Rotation
   * @param rot
   * @return rot
   */
  private Rotation getRot(Rotation rot) {
    if (rotX.getFloatValue() == 0 && rotY.getFloatValue() == 0 && rotZ.getFloatValue() == 0) {
      return null;
    }
    rot.setXrotate(rotX.getFloatValue());
    rot.setYrotate(rotY.getFloatValue());
    rot.setZrotate(rotZ.getFloatValue());
    return rot;

  }

  /**
   * Locationの値を設定 param Location return Location
   * @param loc
   * @return loc
   */
  private Location getLoc(Location loc) {
    if (locX.getFloatValue() == 0 && locY.getFloatValue() == 0 && locZ.getFloatValue() == 0) {
      return null;
    }
    loc.setX(locX.getFloatValue());
    loc.setY(locY.getFloatValue());
    loc.setZ(locZ.getFloatValue());
    return loc;
  }

  /**
   * 分割数が3より小さいときに変わりに30にする
   * @param param
   * @return div
   */
  private int setDiv(ParameterInputBox param) {
    int div = (int)param.getDoubleValue();
    if (div < 3) {
      //3以下なので、30を返します
      div = 30;
    }
    return div;
  }

  /**
   * shellを開く
   */
  public void open() {
    sShell.open();
    Display display = sShell.getDisplay();
    while (!sShell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  class PComboCheck extends SelectionAdapter {

    /**
     * 選択されたプリミティブによってLabel,TextBoxを変更するための flagとなるselectedIndex
     */
    public void widgetSelected(SelectionEvent e) {
      Combo combo = (Combo)e.widget;
      selectedIndex = combo.getSelectionIndex();

      if (selectedIndex == boxFlag) {
        boxLabel();
      } else if (selectedIndex == cylFlag) {
        cylLabel();
      } else if (selectedIndex == sphFlag) {
        sphLabel();
      } else if (selectedIndex == coneFlag) {
        coneLabel();
      }
    }
  }

  /**
   * 選ばれているprimitiveがBoxのとき
   */
  private void boxLabel() {
    param1.setLabelText("幅");
    param2.setLabelText("高さ");
    unitLabel2.setText(lengthUnit + " ");
    param3.setLabelText("奥行き");
    unitLabel2.setText(lengthUnit + " ");    
    param3.setVisible(true);
  }

  /**
   * Cylinderのとき
   */
  private void cylLabel() {
    param1.setLabelText("半径");
    param2.setLabelText("高さ");
    unitLabel2.setText(lengthUnit + " ");
    param3.setLabelText("分割数");
    unitLabel3.setText(" ");
    param3.setVisible(true);
    if (param3.getIntValue() < 3) {
      param3.setText("30");
    } else {
      param3.setText("" + param3.getIntValue());
    }
  }

  /**
   * Sphereのとき
   */
  private void sphLabel() {
    param1.setLabelText("半径");
    param2.setLabelText("分割数");
    unitLabel2.setText(" ");
    param3.setVisible(false);
    unitLabel3.setText(" ");
    if (param2.getIntValue() < 3) {
      param2.setText("30");
    } else {
      param2.setText("" + param2.getIntValue());
    }
  }

  /**
   * Coneのとき
   */
  private void coneLabel() {
    param1.setLabelText("半径");
    param2.setLabelText("高さ");
    unitLabel2.setText(lengthUnit + " ");
    param3.setLabelText("分割数");
    unitLabel3.setText(" ");
    param3.setVisible(true);
    if (param3.getIntValue() < 3) {
      param3.setText("30");
    } else {
      param3.setText("" + param3.getIntValue());
    }
  }

  /**
   * コンボボックス primCombo プリミティブを選択
   */
  private void createPrimCombo() {
    primCombo = new Combo(sShell, SWT.READ_ONLY);
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 2;
    primCombo.setLayoutData(gridData);
    String[] PRIMITIVES = {"Box", "Cylinder", "Sphere", "Cone"};
    primCombo.setItems(PRIMITIVES);
    //デフォルトはBox
    primCombo.setText("Box");
    primCombo.addSelectionListener(new PComboCheck());
  }

  /**
   * コンボボックス colorCombo プリミティブの色を選択
   */
  private void createColorCombo() {
    colorCombo = new Combo(sShell, SWT.READ_ONLY);
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 2;
    colorCombo.setLayoutData(gridData);
    String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"};
    colorCombo.setItems(COLORS);
    colorCombo.setText("red");
  }
}