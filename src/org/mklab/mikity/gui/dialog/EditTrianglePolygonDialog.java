/*
 * $Id: EditTrianglePolygonDialog.java,v 1.9 2008/02/29 13:51:56 morimune Exp $
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
import org.mklab.mikity.xml.model.XMLTrianglePolygon;

/**
 * 三角形ポリゴンを編集するクラス
 * @author SHOGO
 * @version $Revision: 1.9 $. 2008/02/29
 */
public class EditTrianglePolygonDialog {
	private Shell parentShell;
	private Shell sShell;
	private XMLTrianglePolygon triangle;
	private String[] COLORS = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"};

	private String groupName;
	private Group afterGroup;
	private Label primLabel;
	private ParameterInputBox paramX_1,paramY_1,paramZ_1;
	private ParameterInputBox paramX_2,paramY_2,paramZ_2;
	private ParameterInputBox paramX_3,paramY_3,paramZ_3;
	private ParameterInputBox color;
	private ColorComboBox colorCombo;

	private ParameterInputBox newParamX_1,newParamY_1,newParamZ_1;
	private ParameterInputBox newParamX_2,newParamY_2,newParamZ_2;
	private ParameterInputBox newParamX_3,newParamY_3,newParamZ_3;
	private ParameterInputBox paramL_X,paramL_Y,paramL_Z;
	private ParameterInputBox newParamL_X,newParamL_Y,newParamL_Z;
	private ParameterInputBox paramR_X,paramR_Y,paramR_Z;
	private ParameterInputBox newParamR_X,newParamR_Y,newParamR_Z;


	/**
	 * コンストラクター
	 */
	public EditTrianglePolygonDialog(Shell parentShell, XMLTrianglePolygon triangle, org.mklab.mikity.xml.model.Group group){
		this.parentShell = parentShell;
		this.triangle = triangle;
		groupName = group.loadName();
		createSShell();
		detectPrim();
	}

	/**
	 * コンストラクター
	 */
	public EditTrianglePolygonDialog(){}

	/**
	 * シェルを開く
	 */
	public void open(){
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
		sShell.setSize(new org.eclipse.swt.graphics.Point(400, 600));
		sShell.setText("三角形ポリゴンの編集");
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

		paramX_1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点1 X座標", "0.0");
		paramY_1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点1 Y座標", "0.0");
		paramZ_1 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点1 Z座標", "0.0");
		Label label1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(label1,2);

		paramX_2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点2 X座標", "0.0");
		paramY_2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点2 Y座標", "0.0");
		paramZ_2 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点2 Z座標", "0.0");
		Label label2 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(label2,2);
		paramX_3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点3 X座標", "0.0");
		paramY_3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点3 Y座標", "0.0");
		paramZ_3 = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "頂点3 Z座標", "0.0");
		color = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "color", "");
		Label label3 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(label3,2);
		
		paramR_X = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "X軸回転", "0.0");
		paramR_Y = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Y軸回転", "0.0");
		paramR_Z = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Z軸回転", "0.0");
		Label labelR1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(labelR1,2);
		
		paramL_X = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "X軸並進", "0.0");
		paramL_Y = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Y軸並進", "0.0");
		paramL_Z = new ParameterInputBox(beforeGroup, SWT.READ_ONLY, "Z軸並進", "0.0");
		Label labelL1 = new Label(beforeGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(labelL1,2);


		afterGroup = new Group(sShell, SWT.NONE);
		afterGroup.setText("変更後");
		setGridLayout(afterGroup,1);
		GridLayout afterLayout = new GridLayout(3,false);
		afterGroup.setLayout(afterLayout);

		newParamX_1 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");
		newParamY_1 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");
		newParamZ_1 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");

		Label label4 = new Label(afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(label4,3);

		newParamX_2 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");
		newParamY_2 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");
		newParamZ_2 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");

		Label label5 = new Label(afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(label5,3);

		newParamX_3 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");
		newParamY_3 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");
		newParamZ_3 = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");

		Label colorLabel = new Label(afterGroup, SWT.RIGHT);
		colorLabel.setText("→");
		setGridLayout(colorLabel,1);
		colorCombo = new ColorComboBox(afterGroup, COLORS);
		colorCombo.createColorCombo();
		Label label6 = new Label(afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(label6,3);
		
		newParamR_X = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelAngle");
		newParamR_Y = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelAngle");
		newParamR_Z = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelAngle");

		Label labelR2 = new Label(afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(labelR2,3);

		newParamL_X = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");
		newParamL_Y = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");
		newParamL_Z = new ParameterInputBox(afterGroup, SWT.NONE, "→", "0.0");
		new UnitLabel(afterGroup, "modelLength");

		Label labelL2 = new Label(afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		setGridLayout(labelL2,3);
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
		if (newParamX_1.checkParam() == false) {
			return false;
		}
		if (newParamY_1.checkParam() == false) {
			return false;
		}
		if (newParamZ_1.checkParam() == false) {
			return false;
		}
		if (newParamX_2.checkParam() == false) {
			return false;
		}
		if (newParamY_2.checkParam() == false) {
			return false;
		}
		if (newParamZ_2.checkParam() == false) {
			return false;
		}
		if (newParamX_3.checkParam() == false) {
			return false;
		}
		if (newParamY_3.checkParam() == false) {
			return false;
		}
		if (newParamZ_3.checkParam() == false) {
			return false;
		}
		if (newParamR_X.checkParam() == false) {
			return false;
		}
		if (newParamR_Y.checkParam() == false) {
			return false;
		}
		if (newParamR_Z.checkParam() == false) {
			return false;
		}
		if (newParamL_X.checkParam() == false) {
			return false;
		}
		if (newParamL_Y.checkParam() == false) {
			return false;
		}
		if (newParamL_Z.checkParam() == false) {
			return false;
		}
		return true;
	}


	/**
	 * パラメータを変更する
	 */
	private void setParam() {
		Location[] newLoc = new Location[3];

		newLoc[0] = new Location(
				newParamX_1.getFloatValue(),
				newParamY_1.getFloatValue(),
				newParamZ_1.getFloatValue());
		newLoc[1] = new Location(
				newParamX_2.getFloatValue(),
				newParamY_2.getFloatValue(),
				newParamZ_2.getFloatValue());
		newLoc[2] = new Location(
				newParamX_3.getFloatValue(),
				newParamY_3.getFloatValue(),
				newParamZ_3.getFloatValue());

		String newColor = colorCombo.getColorComboBox().getText();

		triangle.setPointLocations(newLoc);
		triangle.setColor(newColor);
		triangle.setRotation(new Rotation(
				newParamR_X.getFloatValue(),
				newParamR_Y.getFloatValue(),
				newParamR_Z.getFloatValue()));
		triangle.setLocation(new Location(
				newParamL_X.getFloatValue(),
				newParamL_Y.getFloatValue(),
				newParamL_Z.getFloatValue()));
	}

	/**
	 * 各頂点の座標値を色を入れる　変更後の欄にはデフォルトで変更前の値を入力
	 */
	private void detectPrim() {
		paramX_1.setText("" + triangle.loadPointLocationX(0));
		paramY_1.setText("" + triangle.loadPointLocationY(0));
		paramZ_1.setText("" + triangle.loadPointLocationZ(0));
		newParamX_1.setText("" + triangle.loadPointLocationX(0));
		newParamY_1.setText("" + triangle.loadPointLocationY(0));
		newParamZ_1.setText("" + triangle.loadPointLocationZ(0));
		
		paramX_2.setText("" + triangle.loadPointLocationX(1));
		paramY_2.setText("" + triangle.loadPointLocationY(1));
		paramZ_2.setText("" + triangle.loadPointLocationZ(1));
		newParamX_2.setText("" + triangle.loadPointLocationX(1));
		newParamY_2.setText("" + triangle.loadPointLocationY(1));
		newParamZ_2.setText("" + triangle.loadPointLocationZ(1));
		
		paramX_3.setText("" + triangle.loadPointLocationX(2));
		paramY_3.setText("" + triangle.loadPointLocationY(2));
		paramZ_3.setText("" + triangle.loadPointLocationZ(2));
		newParamX_3.setText("" + triangle.loadPointLocationX(2));
		newParamY_3.setText("" + triangle.loadPointLocationY(2));
		newParamZ_3.setText("" + triangle.loadPointLocationZ(2));
		color.setText(triangle.loadColor());
		colorCombo.getColorComboBox().setText(triangle.loadColor());
		
		if(triangle.loadRotation() != null){
			paramR_X.setText("" + triangle.loadRotation().loadXrotate());
			paramR_Y.setText("" + triangle.loadRotation().loadYrotate());
			paramR_Z.setText("" + triangle.loadRotation().loadZrotate());
			newParamR_X.setText("" + triangle.loadRotation().loadXrotate());
			newParamR_Y.setText("" + triangle.loadRotation().loadYrotate());
			newParamR_Z.setText("" + triangle.loadRotation().loadZrotate());
		}else{
			paramR_X.setText("" + 0.0);
			paramR_Y.setText("" + 0.0);
			paramR_Z.setText("" + 0.0);
			newParamR_X.setText("" + 0.0);
			newParamR_Y.setText("" + 0.0);
			newParamR_Z.setText("" + 0.0);
		}
		
		if(triangle.loadLocation() != null){
			paramL_X.setText("" + triangle.loadLocation().loadX());
			paramL_Y.setText("" + triangle.loadLocation().loadY());
			paramL_Z.setText("" + triangle.loadLocation().loadZ());
			newParamL_X.setText("" + triangle.loadLocation().loadX());
			newParamL_Y.setText("" + triangle.loadLocation().loadY());
			newParamL_Z.setText("" + triangle.loadLocation().loadZ());
		}else{
			paramL_X.setText("" + 0.0);
			paramL_Y.setText("" + 0.0);
			paramL_Z.setText("" + 0.0);
			newParamL_X.setText("" + 0.0);
			newParamL_Y.setText("" + 0.0);
			newParamL_Z.setText("" + 0.0);
		}
	}
}
