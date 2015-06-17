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
 * グループの設定を行うクラス(DHパラメータ使用)
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/03
 */
public class GroupConfigWithDHParameterDialog {

  /** */
  Shell sShell = null;
  private Shell parentShell = null;
  org.mklab.mikity.model.xml.simplexml.model.Group group;

  /** */
  ParameterInputBox groupName;
  /** */
  ParameterInputBox a;
  /** */
  ParameterInputBox alpha;
  /** */
  ParameterInputBox d;
  /** */
  ParameterInputBox theta;
  /** */
  ParameterInputBox columnA;
  /** */
  ParameterInputBox columnAlpha;
  /** */
  ParameterInputBox columnD;
  /** */
  ParameterInputBox columnTheta;

  private boolean editable;
  private Label statusLabel;

  /**
   * コンストラクター
   * 
   * @param parentShell 親のシェル
   * @param group グループ
   * @param editable 編集可能性
   */
  public GroupConfigWithDHParameterDialog(Shell parentShell, org.mklab.mikity.model.xml.simplexml.model.Group group, boolean editable) {
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
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(350, 350));
    this.sShell.setText(Messages.getString("GroupConfigDialogDH.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);
    // groupName = new ParameterInputBox(sShell, SWT.NONE, "Group名",
    // group.getName());
    this.groupName = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("GroupConfigDialogDH.1"), "root");  //$NON-NLS-1$//$NON-NLS-2$

    System.out.println("group : " + this.group); //$NON-NLS-1$
    if (this.group.getName() != null) {
      this.groupName.setText(this.group.getName());
    }
    createGroup();

    final Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("GroupConfigDialogDH.2")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (Check()) {
          final MessageBox message = new MessageBox(GroupConfigWithDHParameterDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          message.setMessage(Messages.getString("GroupConfigDialogDH.3")); //$NON-NLS-1$
          message.setText(Messages.getString("GroupConfigDialogDH.4")); //$NON-NLS-1$
          int yesNo = message.open();
          if (yesNo == SWT.YES) {
            GroupConfigWithDHParameterDialog.this.group.setName(GroupConfigWithDHParameterDialog.this.groupName.getText());
            GroupConfigWithDHParameterDialog.this.group.clearLinkdata();
            addLinkData(GroupConfigWithDHParameterDialog.this.a, GroupConfigWithDHParameterDialog.this.columnA);
            addLinkData(GroupConfigWithDHParameterDialog.this.d, GroupConfigWithDHParameterDialog.this.columnD);
            addLinkData(GroupConfigWithDHParameterDialog.this.theta, GroupConfigWithDHParameterDialog.this.columnTheta);
            addLinkData(GroupConfigWithDHParameterDialog.this.alpha, GroupConfigWithDHParameterDialog.this.columnAlpha);
            GroupConfigWithDHParameterDialog.this.sShell.close();
          }
        } else {
          final MessageBox message = new MessageBox(GroupConfigWithDHParameterDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("GroupConfigDialogDH.5")); //$NON-NLS-1$
          message.setText(Messages.getString("GroupConfigDialogDH.6")); //$NON-NLS-1$
        }
      }
    });

    final Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("GroupConfigDialogDH.7")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        // キャンセルが選択されたら、変更しないでシェルを閉じる
        GroupConfigWithDHParameterDialog.this.sShell.close();
      }
    });

    createStatusBar();

    if (this.editable == false) {
      setStatus(Messages.getString("GroupConfigDialogDH.8")); //$NON-NLS-1$
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
    paramGroup.setText("DHParameter"); //$NON-NLS-1$
    paramGroup.setLayout(layout);
    paramGroup.setLayoutData(data);

    final GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
    data.widthHint = 40;
    final Label label1 = new Label(paramGroup, SWT.RIGHT);
    label1.setText(Messages.getString("GroupConfigDialogDH.10")); //$NON-NLS-1$
    label1.setLayoutData(gridData1);

    final GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
    gridData2.widthHint = 65;
    final Label label2 = new Label(paramGroup, SWT.RIGHT);
    label2.setText(Messages.getString("GroupConfigDialogDH.11")); //$NON-NLS-1$
    label2.setLayoutData(gridData2);

    final GridData gridData3 = new GridData(GridData.FILL_HORIZONTAL);
    gridData3.widthHint = 65;
    final Label label3 = new Label(paramGroup, SWT.RIGHT);
    label3.setText(Messages.getString("GroupConfigDialogDH.12")); //$NON-NLS-1$
    label3.setLayoutData(gridData3);

    this.a = new ParameterInputBox(paramGroup, style, "a", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnA = new ParameterInputBox(paramGroup, style, 0);

    this.alpha = new ParameterInputBox(paramGroup, style, "alpha", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnAlpha = new ParameterInputBox(paramGroup, style, 0);

    this.d = new ParameterInputBox(paramGroup, style, "d", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnD = new ParameterInputBox(paramGroup, style, 0);

    this.theta = new ParameterInputBox(paramGroup, style, "theta", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.columnTheta = new ParameterInputBox(paramGroup, style, 0);

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
    this.statusLabel.setText(Messages.getString("GroupConfigDialogDH.13") + message); //$NON-NLS-1$
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
      if (target.equals("a")) { //$NON-NLS-1$
        this.columnA.setText(column);
        this.a.setText(constant);
      } else if (target.equals("alpha")) { //$NON-NLS-1$
        this.columnAlpha.setText(column);
        this.alpha.setText(constant);
      } else if (target.equals("d")) { //$NON-NLS-1$
        this.columnD.setText(column);
        this.d.setText(constant);
      } else if (target.equals("theta")) { //$NON-NLS-1$
        this.columnTheta.setText(column);
        this.theta.setText(constant);
      }
    }
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean Check() {
    if (this.a.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.alpha.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.d.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.theta.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.columnA.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.columnAlpha.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.columnD.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.columnTheta.containsOnlyNumbers() == false) {
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