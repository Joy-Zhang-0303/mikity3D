/*
 * $Id: AddQuadPolygonDialog.java,v 1.4 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.dialog;

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
import org.mklab.mikity.gui.ParameterInputBox;
import org.mklab.mikity.gui.UnitLabel;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLQuadPolygon;

/**
 * 四角形ポリゴンを作るためのダイアログのクラス
 * @author SHOGO
 * @version $Revision: 1.4 $. 2008/01/28
 */
public class AddQuadPolygonDialog {
	private Shell parentShell;
	private Shell sShell;

	private ParameterInputBox paramX_1;
	private ParameterInputBox paramY_1;
	private ParameterInputBox paramZ_1;
	private ParameterInputBox paramX_2;
	private ParameterInputBox paramY_2;
	private ParameterInputBox paramZ_2;
	private ParameterInputBox paramX_3;
	private ParameterInputBox paramY_3;
	private ParameterInputBox paramZ_3;
	private ParameterInputBox paramX_4;
	private ParameterInputBox paramY_4;
	private ParameterInputBox paramZ_4;
	private ParameterInputBox rotX;
	private ParameterInputBox rotY;
	private ParameterInputBox rotZ;
	private ParameterInputBox locX;
	private ParameterInputBox locY;
	private ParameterInputBox locZ;

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
		angleUnit = UnitLabel.getUnit("modelAngle");
		lengthUnit = UnitLabel.getUnit("modelLength");
		createSShell();
	}

	/**
	 * シェルの作成
	 */
	private void createSShell() {
		sShell = new Shell(parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		sShell.setSize(new org.eclipse.swt.graphics.Point(300, 650));
		sShell.setText("四角形ポリゴンの追加");
		sShell.setLayout(layout);

		Label groupLabel = new Label(sShell, SWT.LEFT);
		groupLabel.setText("所属グループ  :  " + group.loadName());
		GridData gLabelData = new GridData(GridData.FILL_HORIZONTAL);
		gLabelData.horizontalSpan = 3;
		groupLabel.setLayoutData(gLabelData);

		GridData vertexData = new GridData(GridData.FILL_HORIZONTAL);
		org.eclipse.swt.widgets.Group vertexGroup = new org.eclipse.swt.widgets.Group(sShell, SWT.NONE);
		vertexGroup.setText("四角形ポリゴンの頂点座標");
		layout = new GridLayout();
		layout.numColumns = 3;
		vertexGroup.setLayout(layout);
		vertexData = new GridData(GridData.FILL_HORIZONTAL);
		vertexData.horizontalSpan = 3;
		vertexGroup.setLayoutData(vertexData);

		paramX_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点1 X座標", "0.3");
		paramY_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点1 Y座標", "0.3");
		paramZ_1 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点1 Z座標", "0.0");
		Label label1 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(label1,2);

		paramX_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点2 X座標", "-0.3");
		paramY_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点2 Y座標", "0.3");
		paramZ_2 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点2 Z座標", "0.0");
		Label label2 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(label2,2);
		paramX_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点3 X座標", "-0.3");
		paramY_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点3 Y座標", "-0.3");
		paramZ_3 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点3 Z座標", "0.0");
		Label label3 = new Label(vertexGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(label3,2);
		paramX_4 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点4 X座標", "0.3");
		paramY_4 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点4 Y座標", "-0.3");
		paramZ_4 = new ParameterInputBox(vertexGroup, SWT.READ_ONLY, "頂点4 Z座標", "0.0");


		GridData labelData2 = new GridData(GridData.FILL_HORIZONTAL);
		Label colorLabel = new Label(sShell, SWT.RIGHT);
		colorLabel.setText("color");
		colorLabel.setLayoutData(labelData2);
		createColorCombo();

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

		createButtonComp();
	}

	/**
	 * 完了ボタンを生成する
	 */
	private void createButtonComp(){


		Button okButton = new Button(sShell, SWT.NONE);
		okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		okButton.setText("四角形ポリゴンを追加");

		okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

				if (Check()) {
					//dc.checkDuplication(,.getLocation,group);
					addPolygon();
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
	 * ポリゴンの生成
	 */
	private void addPolygon(){
		XMLQuadPolygon quad = new XMLQuadPolygon();
		Rotation rot = new Rotation();
		Location loc = new Location();

		Location vertex1 = new Location(
				paramX_1.getFloatValue(),
				paramY_1.getFloatValue(),
				paramZ_1.getFloatValue());
		Location vertex2 = new Location(
				paramX_2.getFloatValue(),
				paramY_2.getFloatValue(),
				paramZ_2.getFloatValue());
		Location vertex3 = new Location(
				paramX_3.getFloatValue(),
				paramY_3.getFloatValue(),
				paramZ_3.getFloatValue());
		Location vertex4 = new Location(
				paramX_4.getFloatValue(),
				paramY_4.getFloatValue(),
				paramZ_4.getFloatValue());
		quad.setPointLocations(vertex1,vertex2,vertex3,vertex4);
		if (getRot(rot) != null) {
			quad.setRotation(getRot(rot));
		}
		if (getLoc(loc) != null) {
			quad.setLocation(getLoc(loc));
		}
		quad.setColor(colorCombo.getText());
		group.addXMLQuadPolygon(quad);
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
	 * 数字以外が入力されていたらfalseを返す
	 * 
	 * @return boolean
	 */
	private boolean Check() {
		if (paramX_1.checkParam() == false) {
			return false;
		}
		if (paramY_1.checkParam() == false) {
			return false;
		}
		if (paramZ_1.checkParam() == false) {
			return false;
		}
		if (rotX.checkParam() == false) {
			return false;
		}
		if (paramX_2.checkParam() == false) {
			return false;
		}
		if (paramY_2.checkParam() == false) {
			return false;
		}
		if (paramZ_2.checkParam() == false) {
			return false;
		}
		if (paramX_3.checkParam() == false) {
			return false;
		}
		if (paramY_3.checkParam() == false) {
			return false;
		}
		if (paramZ_3.checkParam() == false) {
			return false;
		}
		if (paramX_4.checkParam() == false) {
			return false;
		}
		if (paramY_4.checkParam() == false) {
			return false;
		}
		if (paramZ_4.checkParam() == false) {
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
	 * コンボボックス colorCombo プリミティブの色を選択
	 */
	private void createColorCombo() {
		colorCombo = new Combo(sShell, SWT.READ_ONLY);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		colorCombo.setLayoutData(gridData);
		String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"};
		colorCombo.setItems(COLORS);
		colorCombo.setText("blue");
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
}
