/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
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
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLSphere;


/**
 * プリミティブの編集を行うクラス
 * @author miki
 * @version $Revision: 1.5 $.2005/02/09
 */
public class EditPrimitiveDialog {

  private Shell parentShell;
  private Shell sShell;
  private Object prim;
  private String groupName;
  private String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"};
  private ColorComboBox colorCombo;
  private Group afterGroup;
  private Label primLabel;
  private ParameterInputBox param1,param2,param3;
  private Label uLabel1,uLabel2,uLabel3;
  private ParameterInputBox rotX,rotY,rotZ;
  private ParameterInputBox locX,locY,locZ;
  private ParameterInputBox color;

  private ParameterInputBox newParam1,newParam2,newParam3;
  private ParameterInputBox newRotX,newRotY,newRotZ;
  private ParameterInputBox newLocX,newLocY,newLocZ;
  
  private boolean rotB = true;
  private boolean locB = true;
  private boolean rotA = true;
  private boolean locA = true;

  /**
   * コンストラクター
   * @param parentShell 
   * 
   * @param prim
   * @param group
   */
  public EditPrimitiveDialog(Shell parentShell, Object prim, org.mklab.mikity.xml.model.Group group) {
    this.parentShell = parentShell;
    this.prim = prim;
    groupName = group.loadName();
    createSShell();
    detectPrim();
  }

  /**
   * コンストラクター
   */
  public EditPrimitiveDialog() {}

  /**
   * シェルを開く
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

  /**
   *  シェルを作成
   */
  private void createSShell() {
    sShell = new Shell(parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    sShell.setSize(new org.eclipse.swt.graphics.Point(350, 400));
    sShell.setText("Primitiveの編集");
    sShell.setLayout(layout);

    Label groupLabel = new Label(sShell, SWT.LEFT);
    groupLabel.setText("所属グループ  :  " + groupName);
    setGridLayout(groupLabel,2);
    
    primLabel = new Label(sShell, SWT.NONE);
    setGridLayout(primLabel,2);
    
    Group beforeGroup = new Group(sShell, SWT.NONE);
    beforeGroup.setText("変更前");
    setGridLayout(beforeGroup, 1);
    GridLayout beforeLayout = new GridLayout(2, true);
    beforeGroup.setLayout(beforeLayout);

    param1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "", "");
    param2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "", "");
    param3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "", "");
    Label label = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    
    setGridLayout(label,2);
    
    rotX = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "X軸周り回転", "0.0");
    rotY = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Y軸周り回転", "0.0");
    rotZ = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Z軸周り回転", "0.0");
    Label label2 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    
    setGridLayout(label2,2);
    locX = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "X軸並進", "0.0");
    locY = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Y軸並進", "0.0");
    locZ = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Z軸並進", "0.0");
    Label label3 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    
    setGridLayout(label3,2);

    color = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "color", "");

    afterGroup = new Group(sShell, SWT.NONE);
    afterGroup.setText("変更後");
    setGridLayout(afterGroup,1);
    
    GridLayout afterLayout = new GridLayout(3,false);
    afterGroup.setLayout(afterLayout);
    newParam1 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "");
    uLabel1 = new Label(afterGroup, SWT.NONE);
    uLabel1.setText(UnitLabel.getUnit("modelLength"));
    setGridLayout(uLabel1,1);
    
    newParam2 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "");
    uLabel2 = new Label(afterGroup, SWT.NONE);
    uLabel2.setText(UnitLabel.getUnit("modelLength"));
    setGridLayout(uLabel2,1);
    
    newParam3 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "");
    uLabel3 = new Label(afterGroup, SWT.NONE);
    uLabel3.setText(UnitLabel.getUnit("modelLength"));
    setGridLayout(uLabel3,1);
    
    newRotX = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(afterGroup, "modelAngle");
    newRotY = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(afterGroup, "modelAngle");
    newRotZ = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(afterGroup, "modelAngle");
    Label label5 = new Label(afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5,3);
        
    newLocX = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(afterGroup, "modelLength");
    newLocY = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(afterGroup, "modelLength");
    newLocZ = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
    new UnitLabel(afterGroup, "modelLength");
    Label label6 = new Label(afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label6,3);
    
    Label colorLabel = new Label(afterGroup, SWT.RIGHT);
    colorLabel.setText("→");
    setGridLayout(colorLabel,1);
    
    colorCombo = new ColorComboBox(afterGroup,COLORS);
    colorCombo.createColorCombo();
    Label spaceLabel = new Label(afterGroup, SWT.NONE);
    spaceLabel.setText(" ");
    setGridLayout(spaceLabel,1);
    
    //UnitLabel test = new UnitLabel(afterGroup, "modelAngle");
    
    createButtonComp();
  }

  /**
   * レイアウトマネージャGridLayoutを設定
   * @param control
   * @param hSpan
   */
  private void setGridLayout(Control control,int hSpan){
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = hSpan;
    control.setLayoutData(gridData);
  }
  
  /**
   *  変更を決定するButtonを作成
   */
  private void createButtonComp() {
    final Composite comp = new Composite(sShell, SWT.NONE);
    setGridLayout(comp,2);
    
    GridLayout compLayout = new GridLayout(2, true);
    comp.setLayout(compLayout);

    Button okButton = new Button(comp, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText("変更");

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        //数字以外が入っていないかを判断
        if (Check()) {
          MessageBox mesBox = new MessageBox(sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage("変更します");
          mesBox.setText("確認");
          int result = mesBox.open();
          if (result == SWT.YES) {
            setParam();
            sShell.close();
          }
        } else {
          MessageBox mgb = new MessageBox(sShell, SWT.ICON_WARNING);
          mgb.setMessage("パラメータ数字以外が入っています。\n 入力しなおして下さい。");
          mgb.setText("Warning!!");
          mgb.open();
        }
      }
    });

    Button cancelButton = new Button(comp, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText("キャンセル");

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
   * @return boolean
   */
  private boolean Check() {
    if (newParam1.checkParam() == false) {
      return false;
    }
    if (newParam2.checkParam() == false) {
      return false;
    }
    if (param3.isVisible() && newParam3.isVisible() && newParam3.checkParam() == false) {
      return false;
    }
    if (newRotX.checkParam() == false) {
      return false;
    }
    if (newRotY.checkParam() == false) {
      return false;
    }
    if (newRotZ.checkParam() == false) {
      return false;
    }
    if (newLocX.checkParam() == false) {
      return false;
    }
    if (newLocY.checkParam() == false) {
      return false;
    }
    if (newLocZ.checkParam() == false) {
      return false;
    }
    return true;
  }

  
  /**
   * パラメータを変更する
   */
  private void setParam() {
    Rotation rot = new Rotation();
    Location loc = new Location();
    
    if (newRotX.getFloatValue() == 0 && newRotY.getFloatValue() == 0 && newRotZ.getFloatValue() == 0) {
      rotA = false;
    }
    if(newLocX.getFloatValue() == 0 && newLocY.getFloatValue() == 0 && newLocZ.getFloatValue() == 0){
      locA = false;
    }
    
    if (prim instanceof XMLBox) {
      XMLBox box = (XMLBox)prim;
      box.setXsize(newParam1.getFloatValue());
      box.setYsize(newParam2.getFloatValue());
      box.setZsize(newParam3.getFloatValue());
      if(rotB == false){
        if(rotA == true){
          box.setRotation(setRot(rot));
        }
      }else{
        setRot(box.loadRotation());
      }
      if(locB == false){
        if(locA == true){
          box.setLocation(setLoc(loc));
        }
      }else{
        setLoc(box.loadLocation());
      }
      box.setColor(colorCombo.getColorComboBox().getText());
    } else if (prim instanceof XMLCylinder) {
      XMLCylinder cyl = (XMLCylinder)prim;
      cyl.setR(newParam1.getFloatValue());
      cyl.setHeight(newParam2.getFloatValue());
      cyl.setDiv(setDiv(newParam3));
      
      if(rotB == false){
        if(rotA == true){
          cyl.setRotation(setRot(rot));
        }
      }else{
        setRot(cyl.loadRotation());
      }
      if(locB == false){
        if(locA == true){
          cyl.setLocation(setLoc(loc));
        }
      }else{
        setLoc(cyl.loadLocation());
      }
      cyl.setColor(colorCombo.getColorComboBox().getText());
    } else if (prim instanceof XMLSphere) {
      XMLSphere sph = (XMLSphere)prim;
      sph.setR(newParam1.getFloatValue());
      sph.setDiv(setDiv(newParam2));
      if(rotB == false){
        if(rotA == true){
          sph.setRotation(setRot(rot));
        }
      }else{
        setRot(sph.loadRotation());
      }
      if(locB == false){
        if(locA == true){
          sph.setLocation(setLoc(loc));
        }
      }else{
        setLoc(sph.loadLocation());
      }
      sph.setColor(colorCombo.getColorComboBox().getText());
    } else if (prim instanceof XMLCone) {
      XMLCone cone = (XMLCone)prim;
      cone.setR(newParam1.getFloatValue());
      cone.setHeight(newParam2.getFloatValue());
      cone.setDiv(setDiv(newParam3));
      if(rotB == false){
        if(rotA == true){
          cone.setRotation(setRot(rot));
        }
      }else{
        setRot(cone.loadRotation());
      }
      if(locB == false){
        if(locA == true){
          cone.setLocation(setLoc(loc));
        }
      }else{
        setLoc(cone.loadLocation());
      }
      cone.setColor(colorCombo.getColorComboBox().getText());
    }

  }

  /**
   * Rotationを設定
   * 受け取ったRotationを変更に応じて設定
   * @param rot
   * @return rot
   */
  private Rotation setRot(Rotation rot) {
    rot.setXrotate(newRotX.getFloatValue());
    rot.setYrotate(newRotY.getFloatValue());
    rot.setZrotate(newRotZ.getFloatValue());
    return rot;
    
  }
  
  /**
   * Locationを設定
   * 受け取ったLocationを変更に応じて設定
   * @param loc
   * @return loc
   */
  private Location setLoc(Location loc) {
    loc.setX(newLocX.getFloatValue());
    loc.setY(newLocY.getFloatValue());
    loc.setZ(newLocZ.getFloatValue());
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
   * primitiveの型を判断し、値を入れる
   */
  private void detectPrim() {
    if (prim instanceof XMLBox) {
      XMLBox box = (XMLBox)prim;
      param1.setText("" + box.loadXsize());
      param2.setText("" + box.loadYsize());
      param3.setText("" + box.loadZsize());
      newParam1.setText("" + box.loadXsize());
      newParam2.setText("" + box.loadYsize());
      newParam3.setText("" + box.loadZsize());
      Rotation rot = box.loadRotation();
      Location loc = box.loadLocation();
      if(rot == null){
        //変換前にRotationが存在しなかったことを示す
        rotB = false;
      }else{
        getRot(rot);
      }
      if(loc == null){
        //変換前にLocationが存在しなかったことを示す
        locB = false;
      }else{
        getLoc(loc);
      }
      boxLabel();
      primLabel.setText("対象となるプリミティブ  :  box");
      color.setText(box.loadColor());
      colorCombo.getColorComboBox().setText(box.loadColor());
    } else if (prim instanceof XMLCylinder) {
      XMLCylinder cyl = (XMLCylinder)prim;
      param1.setText("" + cyl.loadR());
      param2.setText("" + cyl.loadHeight());
      param3.setText("" + cyl.loadDiv());
      newParam1.setText("" + cyl.loadR());
      newParam2.setText("" + cyl.loadHeight());
      newParam3.setText("" + cyl.loadDiv());
      Rotation rot = cyl.loadRotation();
      Location loc = cyl.loadLocation();
      if(rot == null){
        rotB = false;
      }else{
        getRot(rot);
      }
      if(loc == null){
        locB = false;
      }else{
        getLoc(loc);
      }
      cylLabel();
      primLabel.setText("対象となるプリミティブ  :  cylinder");
      color.setText(cyl.loadColor());
      colorCombo.getColorComboBox().setText(cyl.loadColor());
    } else if (prim instanceof XMLSphere) {
      XMLSphere sph = (XMLSphere)prim;
      param1.setText("" + sph.loadR());
      param2.setText("" + sph.loadDiv());
      newParam1.setText("" + sph.loadR());
      newParam2.setText("" + sph.loadDiv());
      Rotation rot = sph.loadRotation();
      Location loc = sph.loadLocation();
      if(rot == null){
        rotB = false;
      }else{
        getRot(rot);
      }
      if(loc == null){
        locB = false;
      }else{
        getLoc(loc);
      }
      sphLabel();
      primLabel.setText("対象となるプリミティブ  :  sphere");
      color.setText(sph.loadColor());
      colorCombo.getColorComboBox().setText(sph.loadColor());
    } else if (prim instanceof XMLCone) {
      XMLCone cone = (XMLCone)prim;
      param1.setText("" + cone.loadR());
      param2.setText("" + cone.loadHeight());
      param3.setText("" + cone.loadDiv());
      newParam1.setText("" + cone.loadR());
      newParam2.setText("" + cone.loadHeight());
      newParam3.setText("" + cone.loadDiv());
      Rotation rot = cone.loadRotation();
      Location loc = cone.loadLocation();
      if(rot == null){
        rotB = false;
      }else{
        getRot(rot);
      }
      if(loc == null){
        locB = false;
      }else{
        getLoc(loc);
      }
      coneLabel();
      primLabel.setText("対象となるプリミティブ  :  cone");
      color.setText(cone.loadColor());
      colorCombo.getColorComboBox().setText(cone.loadColor());
    }else if (prim instanceof XMLConnector) {
    	
    }
  }

  /**
   * primitiveがBoxのとき
   */
  public void boxLabel() {
    param1.setLabelText("幅");
    param2.setLabelText("高さ");
    param3.setLabelText("奥行き");
    param3.setVisible(true);
    newParam3.setVisible(true);
    uLabel2.setVisible(true);
    uLabel3.setVisible(true);
  }

  /**
   * primitiveがCylinderのとき
   */
  public void cylLabel() {
    param1.setLabelText("半径");
    param2.setLabelText("高さ");
    param3.setLabelText("分割数");
    param3.setVisible(true);
    int div = 0;
    if (param3.getIntValue() < 3) {
      div = 30;
    } else {
      div = param3.getIntValue();
    }
    param3.setText("" + div);
    newParam3.setVisible(true);
    newParam3.setText("" + div);
    uLabel2.setVisible(true);
    uLabel3.setVisible(false);
  }

  /**
   * primitiveがSphereのとき
   */
  public void sphLabel() {
    param1.setLabelText("半径");
    param2.setLabelText("分割数");
    param3.setVisible(false);
    int div = 0;
    if (param2.getIntValue() < 3) {
      div = 30;
    } else {
      div = param2.getIntValue();
    }
    param2.setText("" + div);
    newParam2.setText("" + div);
    newParam3.setVisible(false);
    uLabel2.setVisible(false);
    uLabel3.setVisible(false);
  }

  /**
   * primitiveがConeのとき
   */
  public void coneLabel() {
    param1.setLabelText("半径");
    param2.setLabelText("高さ");
    param3.setLabelText("分割数");
    param3.setVisible(true);
    int div = 0;
    if (param3.getIntValue() < 3) {
      div = 30;
    } else {
      div = param3.getIntValue();
    }
    param3.setText("" + div);
    newParam3.setVisible(true);
    newParam3.setText("" + div);
    uLabel2.setVisible(true);
    uLabel3.setVisible(false);
  }

  /**
   * 平行移動、回転移動の値を読み取る 空の場合はゼロを入れる
   * @param rot
   */
  private void getRot(Rotation rot) {
    if (rot.hasXrotate() || rot.loadXrotate() !=0.0f) {
      rotX.setText("" + rot.loadXrotate());
      newRotX.setText("" + rot.loadXrotate());
    }
    if (rot.hasYrotate() || rot.loadYrotate() !=0.0f) {
      rotY.setText("" + rot.loadYrotate());
      newRotY.setText("" + rot.loadYrotate());
    }
    if (rot.hasZrotate() || rot.loadXrotate() !=0.0f) {
      rotZ.setText("" + rot.loadZrotate());
      newRotZ.setText("" + rot.loadZrotate());
    }
  }

  private void getLoc(Location loc) {
    if (loc.hasX() || loc.loadX() !=0.0f) {
      locX.setText("" + loc.loadX());
      newLocX.setText("" + loc.loadX());
    }
    if (loc.hasY() || loc.loadY() !=0.0f) {
      locY.setText("" + loc.loadY());
      newLocY.setText("" + loc.loadY());
    }
    if (loc.hasZ() || loc.loadZ() !=0.0f) {
      locZ.setText("" + loc.loadZ());
      newLocZ.setText("" + loc.loadZ());
    }
  }
}