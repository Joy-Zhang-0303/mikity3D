/*
 * Created on 2005/02/03
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;
import org.mklab.mikity.view.gui.ParameterInputBox;


/**
 * グループの設定を行うクラス(DHパラメータ未使用)
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/03
 */
public class GroupConfigWithCoordinateParameterDialog {

  Shell sShell = null;
  private Shell parentShell = null;
  org.mklab.mikity.model.xml.simplexml.model.Group group;

  ParameterInputBox groupName;
  ParameterInputBox locationX;
  ParameterInputBox locationY;
  ParameterInputBox locationZ;
  ParameterInputBox rotationX;
  ParameterInputBox rotationY;
  ParameterInputBox rotationZ;
  ParameterInputBox columnLocationX;
  ParameterInputBox columnLocationY;
  ParameterInputBox columnLocationZ;
  ParameterInputBox columnRotationX;
  ParameterInputBox columnRotationY;
  ParameterInputBox columnRotationZ;

  private boolean editable;
  private Label statusLabel;

  /**
   * コンストラクター
   * 
   * @param parentShell 親のシェル
   * @param group グループ
   * @param editable 編集可能性
   */
  public GroupConfigWithCoordinateParameterDialog(Shell parentShell, org.mklab.mikity.model.xml.simplexml.model.Group group, boolean editable) {
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
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(350, 375));
    this.sShell.setText(Messages.getString("GroupConfigDialogLink.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);
    // groupName = new ParameterInputBox(sShell, SWT.NONE, "Group名",
    // group.getName());
    this.groupName = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("GroupConfigDialogLink.1"), "root"); //$NON-NLS-1$ //$NON-NLS-2$

    System.out.println("group : " + this.group); //$NON-NLS-1$
    if (this.group.getName() != null) {
      this.groupName.setText(this.group.getName());
    }
    createGroup();

    final Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("GroupConfigDialogLink.4")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (Check()) {
          final MessageBox message = new MessageBox(GroupConfigWithCoordinateParameterDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          message.setMessage(Messages.getString("GroupConfigDialogLink.5")); //$NON-NLS-1$
          message.setText(Messages.getString("GroupConfigDialogLink.6")); //$NON-NLS-1$
          int yesNo = message.open();
          if (yesNo == SWT.YES) {
            GroupConfigWithCoordinateParameterDialog.this.group.setName(GroupConfigWithCoordinateParameterDialog.this.groupName.getText());
            GroupConfigWithCoordinateParameterDialog.this.group.clearLinkdata();
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.locationX, GroupConfigWithCoordinateParameterDialog.this.columnLocationX);
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.locationY, GroupConfigWithCoordinateParameterDialog.this.columnLocationY);
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.locationZ, GroupConfigWithCoordinateParameterDialog.this.columnLocationZ);
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.rotationX, GroupConfigWithCoordinateParameterDialog.this.columnRotationX);
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.rotationY, GroupConfigWithCoordinateParameterDialog.this.columnRotationY);
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.rotationZ, GroupConfigWithCoordinateParameterDialog.this.columnRotationZ);
            GroupConfigWithCoordinateParameterDialog.this.sShell.close();
          }
        } else {
          final MessageBox message = new MessageBox(GroupConfigWithCoordinateParameterDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("GroupConfigDialogLink.7")); //$NON-NLS-1$
          message.setText(Messages.getString("GroupConfigDialogLink.8")); //$NON-NLS-1$
        }
      }
    });

    final Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("GroupConfigDialogLink.9")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        // キャンセルが選択されたら、変更しないでシェルを閉じる
        GroupConfigWithCoordinateParameterDialog.this.sShell.close();
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
      final LinkData linkdata = new LinkData();
      linkdata.setTargetName(dh.getLabelText());
      if (dh.getFloatValue() != 0.0) {
        linkdata.setConstantValue(dh.getFloatValue());
      }
      if (col.getIntValue() != 0) {
        linkdata.setColumnNumber(col.getIntValue());
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

    final Group paramGroup = new Group(this.sShell, SWT.NONE);
    final GridLayout layout = new GridLayout();
    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.horizontalSpan = 2;
    layout.numColumns = 3;
    paramGroup.setText(Messages.getString("GroupConfigDialogLink.11")); //$NON-NLS-1$
    paramGroup.setLayout(layout);
    paramGroup.setLayoutData(data);

    final GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
    data.widthHint = 40;
    final Label label1 = new Label(paramGroup, SWT.RIGHT);
    label1.setText(Messages.getString("GroupConfigDialogLink.12")); //$NON-NLS-1$
    label1.setLayoutData(gridData1);

    final GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
    gridData2.widthHint = 65;
    final Label label2 = new Label(paramGroup, SWT.RIGHT);
    label2.setText(Messages.getString("GroupConfigDialogLink.13")); //$NON-NLS-1$
    label2.setLayoutData(gridData2);

    final GridData gridData3 = new GridData(GridData.FILL_HORIZONTAL);
    gridData3.widthHint = 65;
    final Label label3 = new Label(paramGroup, SWT.RIGHT);
    label3.setText(Messages.getString("GroupConfigDialogLink.14")); //$NON-NLS-1$
    label3.setLayoutData(gridData3);

    this.locationX = new ParameterInputBox(paramGroup, style, "locationX", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnLocationX = new ParameterInputBox(paramGroup, style, 0);

    this.locationY = new ParameterInputBox(paramGroup, style, "locationY", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnLocationY = new ParameterInputBox(paramGroup, style, 0);

    this.locationZ = new ParameterInputBox(paramGroup, style, "locationZ", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnLocationZ = new ParameterInputBox(paramGroup, style, 0);

    this.rotationX = new ParameterInputBox(paramGroup, style, "rotationX", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnRotationX = new ParameterInputBox(paramGroup, style, 0);

    this.rotationY = new ParameterInputBox(paramGroup, style, "rotationY", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnRotationY = new ParameterInputBox(paramGroup, style, 0);

    this.rotationZ = new ParameterInputBox(paramGroup, style, "rotationZ", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnRotationZ = new ParameterInputBox(paramGroup, style, 0);

    setParam();
  }

  /**
   * ステータスバーを作る
   */
  private void createStatusBar() {
    this.statusLabel = new Label(this.sShell, SWT.BORDER);
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
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
    final LinkData[] linkdata = this.group.getLinkData();

    for (int i = 0; i < linkdata.length; i++) {
      final String target = linkdata[i].getTargetName();
      final String column = linkdata[i].hasColumnNumber() ? "" + linkdata[i].getColumnNumber() : "0"; //$NON-NLS-1$ //$NON-NLS-2$
      final String constant = linkdata[i].hasConstantValue() ? "" + linkdata[i].getConstantValue() : "0"; //$NON-NLS-1$ //$NON-NLS-2$
      if (target.equals("locationX")) { //$NON-NLS-1$
        this.columnLocationX.setText(column);
        this.locationX.setText(constant);
      } else if (target.equals("locationY")) { //$NON-NLS-1$
        this.columnLocationY.setText(column);
        this.locationY.setText(constant);
      } else if (target.equals("locationZ")) { //$NON-NLS-1$
        this.columnLocationZ.setText(column);
        this.locationZ.setText(constant);
      } else if (target.equals("rotationX")) { //$NON-NLS-1$
        this.columnRotationX.setText(column);
        this.rotationX.setText(constant);
      } else if (target.equals("rotationY")) { //$NON-NLS-1$
        this.columnRotationY.setText(column);
        this.rotationY.setText(constant);
      } else if (target.equals("rotationZ")) { //$NON-NLS-1$
        this.columnRotationZ.setText(column);
        this.rotationZ.setText(constant);
      }
    }
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean Check() {
    if (this.locationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.locationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.locationZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.columnLocationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.columnLocationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.columnLocationZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.columnRotationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.columnRotationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.columnRotationZ.containsOnlyNumbers() == false) {
      return false;
    }
    return true;
  }

  /**
   * シェルを開く 開いている間、親シェルの処理を止める
   */
  public void open() {
    this.sShell.open();
    final Display display = this.sShell.getDisplay();
    while (!this.sShell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}