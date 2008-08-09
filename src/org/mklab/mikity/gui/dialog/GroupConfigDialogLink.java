/*
 * Created on 2005/02/03
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.gui.ParameterInputBox;
import org.mklab.mikity.xml.model.Linkdata;


/**
 * グループの設定を行うクラス(DHパラメータ未使用)
 * @author miki
 * @version $Revision: 1.1 $.2005/02/03
 */
public class GroupConfigDialogLink {

	private Shell sShell = null;
	private Shell parentShell = null;
	private org.mklab.mikity.xml.model.Group group;

	private ParameterInputBox groupName;
	private ParameterInputBox locX;
	private ParameterInputBox locY;
	private ParameterInputBox locZ;
	private ParameterInputBox rotX;
	private ParameterInputBox rotY;
	private ParameterInputBox rotZ;
	private ParameterInputBox columnLocX;
	private ParameterInputBox columnLocY;
	private ParameterInputBox columnLocZ;
	private ParameterInputBox columnRotX;
	private ParameterInputBox columnRotY;
	private ParameterInputBox columnRotZ;

	private boolean editable;
	private Label statusLabel;

	/**
	 * コンストラクター
	 * @param parentShell
	 * @param group
	 * @param editable
	 */
	public GroupConfigDialogLink(Shell parentShell, org.mklab.mikity.xml.model.Group group, boolean editable) {
		this.parentShell = parentShell;
		this.group = group;
		this.editable = editable;
		createSShell();
	}

	/**
	 * sShellの作成、変更を保存、キャンセルする
	 */
	private void createSShell() {
		//  SWT.APPLICATION_MODAL→このシェルを閉じないと、親シェルを編集できなくする
		sShell = new Shell(parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		sShell.setSize(new org.eclipse.swt.graphics.Point(320, 320));
		sShell.setText("Groupパラメータの編集");
		sShell.setLayout(layout);
		//groupName = new ParameterInputBox(sShell, SWT.NONE, "Group名", group.getName());
		groupName = new ParameterInputBox(sShell, SWT.NONE, "Group名", "root");

		System.out.println("group : " + group);
		if(group.loadName() != null){
			groupName.setText(group.loadName());
		}
		createGroup();

		Button okButton = new Button(sShell, SWT.NONE);
		okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		okButton.setText("変更を保存");

		okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

				if (Check()) {
					MessageBox mesBox = new MessageBox(sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
					mesBox.setMessage("変更します");
					mesBox.setText("確認");
					int result = mesBox.open();
					if (result == SWT.YES) {
						group.setName(groupName.getText());
						group.clearLinkdata();
						addLinkData(locX, columnLocX);
						addLinkData(locY, columnLocY);
						addLinkData(locZ, columnLocZ);
						addLinkData(rotX, columnRotX);
						addLinkData(rotY, columnRotY);
						addLinkData(rotZ, columnRotZ);
						sShell.close();
					}
				} else {
					MessageBox mgb = new MessageBox(sShell, SWT.ICON_WARNING);
					mgb.setMessage("パラメータ数字以外が入っています。\n 入力しなおして下さい。");
					mgb.setText("Warning!!");
				}
			}
		});

		Button cancelButton = new Button(sShell, SWT.NONE);
		cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cancelButton.setText("キャンセル");

		cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				//キャンセルが選択されたら、変更しないでシェルを閉じる
				sShell.close();
			}
		});

		createStatusBar();

		if (editable == false) {
			setStatus("このグループはルートなので値を指定できません");
		}
	}

	/**
	 * グループのLinkdataを追加する
	 * @param dh 
	 * @param col 
	 */
	private void addLinkData(final ParameterInputBox dh, final ParameterInputBox col) {
		if (dh.getFloatValue() != 0.0 || col.getIntValue() != 0) {
			Linkdata linkdata = new Linkdata();
			linkdata.setTarget(dh.getLabelText());
			if (dh.getFloatValue() != 0.0) {
				linkdata.setConst(dh.getFloatValue());
			}
			if (col.getIntValue() != 0) {
				linkdata.setColumn(col.getIntValue());
			}
			group.addLinkdata(linkdata);
		}
	}

	/**
	 * グループのパラメータを表示させる
	 */
	private void createGroup() {
		int style;
		if (editable == true) {
			style = SWT.NONE;
		} else {
			style = SWT.READ_ONLY;
		}

		Group paramGroup = new Group(sShell, SWT.NONE);
		GridLayout layout = new GridLayout();
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		layout.numColumns = 3;
		paramGroup.setText("リンク間パラメータ");
		paramGroup.setLayout(layout);
		paramGroup.setLayoutData(data);

		GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 40;
		Label label1 = new Label(paramGroup, SWT.RIGHT);
		label1.setText("パラメータ");
		label1.setLayoutData(gridData1);

		GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
		gridData2.widthHint = 65;
		Label label2 = new Label(paramGroup, SWT.RIGHT);
		label2.setText("初期値");
		label2.setLayoutData(gridData2);

		GridData gridData3 = new GridData(GridData.FILL_HORIZONTAL);
		gridData3.widthHint = 65;
		Label label3 = new Label(paramGroup, SWT.RIGHT);
		label3.setText("読み取る列");
		label3.setLayoutData(gridData3);

		locX = new ParameterInputBox(paramGroup, style, "locationX", "0");
		columnLocX = new ParameterInputBox(paramGroup, style, 0);

		locY = new ParameterInputBox(paramGroup, style, "locationY", "0");
		columnLocY = new ParameterInputBox(paramGroup, style, 0);

		locZ = new ParameterInputBox(paramGroup, style, "locationZ", "0");
		columnLocZ = new ParameterInputBox(paramGroup, style, 0);

		rotX = new ParameterInputBox(paramGroup, style, "rotationX", "0");
		columnRotX = new ParameterInputBox(paramGroup, style, 0);

		rotY = new ParameterInputBox(paramGroup, style, "rotationY", "0");
		columnRotY = new ParameterInputBox(paramGroup, style, 0);

		rotZ = new ParameterInputBox(paramGroup, style, "rotationZ", "0");
		columnRotZ = new ParameterInputBox(paramGroup, style, 0);

		setParam();
	}

	/**
	 * ステータスバーを作る
	 */
	private void createStatusBar() {
		statusLabel = new Label(sShell, SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		statusLabel.setLayoutData(gridData);
		setStatus("");
	}

	/**
	 * ステータスを設定する
	 * @param msg
	 */
	public void setStatus(String msg) {
		statusLabel.setText("ステータス: " + msg);
	}

	/**
	 * Linkdata の column を表示させる
	 */
	private void setParam() {
		Linkdata[] linkdata = group.loadLinkdata();

		for (int i = 0; i < linkdata.length; i++) {
			String target = linkdata[i].loadTarget();
			//if(linkdata[i].hasColumn()){
			//  column = "" + linkdata[i].getColumn();
			//} else{
			//  column = "0";
			//} 
			String column = linkdata[i].hasColumn() ? "" + linkdata[i].loadColumn() : "0";
			String constant = linkdata[i].hasConst() ? "" + linkdata[i].loadConst() : "0";
			if (target.equals("locationX")) {
				columnLocX.setText(column);
				locX.setText(constant);
			} else if (target.equals("locationY")) {
				columnLocY.setText(column);
				locY.setText(constant);
			} else if (target.equals("locationZ")) {
				columnLocZ.setText(column);
				locZ.setText(constant);
			} else if (target.equals("rotationX")) {
				columnRotX.setText(column);
				rotX.setText(constant);
			}else if (target.equals("rotationY")) {
				columnRotY.setText(column);
				rotY.setText(constant);
			} else if (target.equals("rotationZ")) {
				columnRotZ.setText(column);
				rotZ.setText(constant);
			}
		}
	}

	/**
	 * 数字以外が入力されていたらfalseを返す
	 * 
	 * @return boolean
	 */
	private boolean Check() {
		if (locX.checkParam() == false) {
			return false;
		}
		if (locY.checkParam() == false) {
			return false;
		}
		if (locZ.checkParam() == false) {
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
		if (columnLocX.checkParam() == false) {
			return false;
		}
		if (columnLocY.checkParam() == false) {
			return false;
		}
		if (columnLocZ.checkParam() == false) {
			return false;
		}
		if (columnRotX.checkParam() == false) {
			return false;
		}
		if (columnRotY.checkParam() == false) {
			return false;
		}
		if (columnRotZ.checkParam() == false) {
			return false;
		}
		return true;
	}

	/**
	 * シェルを開く 開いている間、親シェルの処理を止める
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