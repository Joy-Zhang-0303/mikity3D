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
import org.mklab.mikity.xml.model.LinkData;


/**
 * グループの設定を行うクラス(DHパラメータ未使用)
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/03
 */
public class GroupConfigDialogLink {

  Shell sShell = null;
  private Shell parentShell = null;
  org.mklab.mikity.xml.model.Group group;

  ParameterInputBox groupName;
  ParameterInputBox locX;
  ParameterInputBox locY;
  ParameterInputBox locZ;
  ParameterInputBox rotX;
  ParameterInputBox rotY;
  ParameterInputBox rotZ;
  ParameterInputBox columnLocX;
  ParameterInputBox columnLocY;
  ParameterInputBox columnLocZ;
  ParameterInputBox columnRotX;
  ParameterInputBox columnRotY;
  ParameterInputBox columnRotZ;

  private boolean editable;
  private Label statusLabel;

  /**
   * コンストラクター
   * 
   * @param parentShell 親のシェル
   * @param group グループ
   * @param editable 編集可能性
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
    // SWT.APPLICATION_MODAL→このシェルを閉じないと、親シェルを編集できなくする
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(350, 375));
    this.sShell.setText(Messages.getString("GroupConfigDialogLink.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);
    // groupName = new ParameterInputBox(sShell, SWT.NONE, "Group名",
    // group.getName());
    this.groupName = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("GroupConfigDialogLink.1"), "root"); //$NON-NLS-1$ //$NON-NLS-2$

    System.out.println("group : " + this.group); //$NON-NLS-1$
    if (this.group.loadName() != null) {
      this.groupName.setText(this.group.loadName());
    }
    createGroup();

    Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("GroupConfigDialogLink.4")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (Check()) {
          MessageBox mesBox = new MessageBox(GroupConfigDialogLink.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage(Messages.getString("GroupConfigDialogLink.5")); //$NON-NLS-1$
          mesBox.setText(Messages.getString("GroupConfigDialogLink.6")); //$NON-NLS-1$
          int result = mesBox.open();
          if (result == SWT.YES) {
            GroupConfigDialogLink.this.group.setName(GroupConfigDialogLink.this.groupName.getText());
            GroupConfigDialogLink.this.group.clearLinkdata();
            addLinkData(GroupConfigDialogLink.this.locX, GroupConfigDialogLink.this.columnLocX);
            addLinkData(GroupConfigDialogLink.this.locY, GroupConfigDialogLink.this.columnLocY);
            addLinkData(GroupConfigDialogLink.this.locZ, GroupConfigDialogLink.this.columnLocZ);
            addLinkData(GroupConfigDialogLink.this.rotX, GroupConfigDialogLink.this.columnRotX);
            addLinkData(GroupConfigDialogLink.this.rotY, GroupConfigDialogLink.this.columnRotY);
            addLinkData(GroupConfigDialogLink.this.rotZ, GroupConfigDialogLink.this.columnRotZ);
            GroupConfigDialogLink.this.sShell.close();
          }
        } else {
          MessageBox mgb = new MessageBox(GroupConfigDialogLink.this.sShell, SWT.ICON_WARNING);
          mgb.setMessage(Messages.getString("GroupConfigDialogLink.7")); //$NON-NLS-1$
          mgb.setText(Messages.getString("GroupConfigDialogLink.8")); //$NON-NLS-1$
        }
      }
    });

    Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("GroupConfigDialogLink.9")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        // キャンセルが選択されたら、変更しないでシェルを閉じる
        GroupConfigDialogLink.this.sShell.close();
      }
    });

    createStatusBar();

    if (this.editable == false) {
      setStatus(Messages.getString("GroupConfigDialogLink.10")); //$NON-NLS-1$
    }
  }

  /**
   * グループのLinkdataを追加する
   * 
   * @param dh
   * @param col
   */
  void addLinkData(final ParameterInputBox dh, final ParameterInputBox col) {
    if (dh.getFloatValue() != 0.0 || col.getIntValue() != 0) {
      LinkData linkdata = new LinkData();
      linkdata.setTargetName(dh.getLabelText());
      if (dh.getFloatValue() != 0.0) {
        linkdata.setInitialValue(dh.getFloatValue());
      }
      if (col.getIntValue() != 0) {
        linkdata.setDataNumber(col.getIntValue());
      }
      this.group.addLinkdata(linkdata);
    }
  }

  /**
   * グループのパラメータを表示させる
   */
  private void createGroup() {
    int style;
    if (this.editable == true) {
      style = SWT.NONE;
    } else {
      style = SWT.READ_ONLY;
    }

    Group paramGroup = new Group(this.sShell, SWT.NONE);
    GridLayout layout = new GridLayout();
    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.horizontalSpan = 2;
    layout.numColumns = 3;
    paramGroup.setText(Messages.getString("GroupConfigDialogLink.11")); //$NON-NLS-1$
    paramGroup.setLayout(layout);
    paramGroup.setLayoutData(data);

    GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
    data.widthHint = 40;
    Label label1 = new Label(paramGroup, SWT.RIGHT);
    label1.setText(Messages.getString("GroupConfigDialogLink.12")); //$NON-NLS-1$
    label1.setLayoutData(gridData1);

    GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
    gridData2.widthHint = 65;
    Label label2 = new Label(paramGroup, SWT.RIGHT);
    label2.setText(Messages.getString("GroupConfigDialogLink.13")); //$NON-NLS-1$
    label2.setLayoutData(gridData2);

    GridData gridData3 = new GridData(GridData.FILL_HORIZONTAL);
    gridData3.widthHint = 65;
    Label label3 = new Label(paramGroup, SWT.RIGHT);
    label3.setText(Messages.getString("GroupConfigDialogLink.14")); //$NON-NLS-1$
    label3.setLayoutData(gridData3);

    this.locX = new ParameterInputBox(paramGroup, style, "locationX", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnLocX = new ParameterInputBox(paramGroup, style, 0);

    this.locY = new ParameterInputBox(paramGroup, style, "locationY", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnLocY = new ParameterInputBox(paramGroup, style, 0);

    this.locZ = new ParameterInputBox(paramGroup, style, "locationZ", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnLocZ = new ParameterInputBox(paramGroup, style, 0);

    this.rotX = new ParameterInputBox(paramGroup, style, "rotationX", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnRotX = new ParameterInputBox(paramGroup, style, 0);

    this.rotY = new ParameterInputBox(paramGroup, style, "rotationY", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnRotY = new ParameterInputBox(paramGroup, style, 0);

    this.rotZ = new ParameterInputBox(paramGroup, style, "rotationZ", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnRotZ = new ParameterInputBox(paramGroup, style, 0);

    setParam();
  }

  /**
   * ステータスバーを作る
   */
  private void createStatusBar() {
    this.statusLabel = new Label(this.sShell, SWT.BORDER);
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 2;
    this.statusLabel.setLayoutData(gridData);
    setStatus(""); //$NON-NLS-1$
  }

  /**
   * ステータスを設定する
   * 
   * @param message メッセージ
   */
  public void setStatus(String message) {
    this.statusLabel.setText(Messages.getString("GroupConfigDialogLink.15") + message); //$NON-NLS-1$
  }

  /**
   * Linkdata の column を表示させる
   */
  private void setParam() {
    LinkData[] linkdata = this.group.getLinkData();

    for (int i = 0; i < linkdata.length; i++) {
      String target = linkdata[i].loadTargetName();
      // if(linkdata[i].hasColumn()){
      // column = "" + linkdata[i].getColumn();
      // } else{
      // column = "0";
      // }
      String column = linkdata[i].hasDataNumber() ? "" + linkdata[i].loadDataNumber() : "0"; //$NON-NLS-1$ //$NON-NLS-2$
      String constant = linkdata[i].hasInitialValue() ? "" + linkdata[i].loadInitialValue() : "0"; //$NON-NLS-1$ //$NON-NLS-2$
      if (target.equals("locationX")) { //$NON-NLS-1$
        this.columnLocX.setText(column);
        this.locX.setText(constant);
      } else if (target.equals("locationY")) { //$NON-NLS-1$
        this.columnLocY.setText(column);
        this.locY.setText(constant);
      } else if (target.equals("locationZ")) { //$NON-NLS-1$
        this.columnLocZ.setText(column);
        this.locZ.setText(constant);
      } else if (target.equals("rotationX")) { //$NON-NLS-1$
        this.columnRotX.setText(column);
        this.rotX.setText(constant);
      } else if (target.equals("rotationY")) { //$NON-NLS-1$
        this.columnRotY.setText(column);
        this.rotY.setText(constant);
      } else if (target.equals("rotationZ")) { //$NON-NLS-1$
        this.columnRotZ.setText(column);
        this.rotZ.setText(constant);
      }
    }
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean Check() {
    if (this.locX.checkParam() == false) {
      return false;
    }
    if (this.locY.checkParam() == false) {
      return false;
    }
    if (this.locZ.checkParam() == false) {
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
    if (this.columnLocX.checkParam() == false) {
      return false;
    }
    if (this.columnLocY.checkParam() == false) {
      return false;
    }
    if (this.columnLocZ.checkParam() == false) {
      return false;
    }
    if (this.columnRotX.checkParam() == false) {
      return false;
    }
    if (this.columnRotY.checkParam() == false) {
      return false;
    }
    if (this.columnRotZ.checkParam() == false) {
      return false;
    }
    return true;
  }

  /**
   * シェルを開く 開いている間、親シェルの処理を止める
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
}