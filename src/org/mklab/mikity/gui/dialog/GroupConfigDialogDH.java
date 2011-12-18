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
 * グループの設定を行うクラス(DHパラメータ使用)
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/03
 */
public class GroupConfigDialogDH {

  Shell sShell = null;
  private Shell parentShell = null;
  org.mklab.mikity.xml.model.Group group;

  ParameterInputBox groupName;
  ParameterInputBox a;
  ParameterInputBox alpha;
  ParameterInputBox d;
  ParameterInputBox theta;
  ParameterInputBox columnA;
  ParameterInputBox columnAlpha;
  ParameterInputBox columnD;
  ParameterInputBox columnTheta;

  private boolean editable;
  private Label statusLabel;

  /**
   * コンストラクター
   * 
   * @param parentShell
   * @param group
   * @param editable
   */
  public GroupConfigDialogDH(Shell parentShell, org.mklab.mikity.xml.model.Group group, boolean editable) {
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
    //this.sShell.setSize(new org.eclipse.swt.graphics.Point(350, 350));
    this.sShell.setText("Groupパラメータの編集");
    this.sShell.setLayout(layout);
    // groupName = new ParameterInputBox(sShell, SWT.NONE, "Group名",
    // group.getName());
    this.groupName = new ParameterInputBox(this.sShell, SWT.NONE, "Group名", "root"); //$NON-NLS-2$

    System.out.println("group : " + this.group); //$NON-NLS-1$
    if (this.group.loadName() != null) {
      this.groupName.setText(this.group.loadName());
    }
    createGroup();

    Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText("変更を保存");

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (Check()) {
          MessageBox mesBox = new MessageBox(GroupConfigDialogDH.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage("変更します");
          mesBox.setText("確認");
          int result = mesBox.open();
          if (result == SWT.YES) {
            GroupConfigDialogDH.this.group.setName(GroupConfigDialogDH.this.groupName.getText());
            GroupConfigDialogDH.this.group.clearLinkdata();
            addLinkData(GroupConfigDialogDH.this.a, GroupConfigDialogDH.this.columnA);
            addLinkData(GroupConfigDialogDH.this.d, GroupConfigDialogDH.this.columnD);
            addLinkData(GroupConfigDialogDH.this.theta, GroupConfigDialogDH.this.columnTheta);
            addLinkData(GroupConfigDialogDH.this.alpha, GroupConfigDialogDH.this.columnAlpha);
            GroupConfigDialogDH.this.sShell.close();
          }
        } else {
          MessageBox mgb = new MessageBox(GroupConfigDialogDH.this.sShell, SWT.ICON_WARNING);
          mgb.setMessage("パラメータ数字以外が入っています。\n 入力しなおして下さい。");
          mgb.setText("Warning!!");
        }
      }
    });

    Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText("キャンセル");

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        // キャンセルが選択されたら、変更しないでシェルを閉じる
        GroupConfigDialogDH.this.sShell.close();
      }
    });

    createStatusBar();

    if (this.editable == false) {
      setStatus("このグループはルートなので値を指定できません");
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
      Linkdata linkdata = new Linkdata();
      linkdata.setTarget(dh.getLabelText());
      if (dh.getFloatValue() != 0.0) {
        linkdata.setConst(dh.getFloatValue());
      }
      if (col.getIntValue() != 0) {
        linkdata.setColumn(col.getIntValue());
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
    paramGroup.setText("DHParameter");
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
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 2;
    this.statusLabel.setLayoutData(gridData);
    setStatus(""); //$NON-NLS-1$
  }

  /**
   * ステータスを設定する
   * 
   * @param msg
   */
  public void setStatus(String msg) {
    this.statusLabel.setText("ステータス: " + msg);
  }

  /**
   * Linkdata の column を表示させる
   */
  private void setParam() {
    Linkdata[] linkdata = this.group.loadLinkdata();

    for (int i = 0; i < linkdata.length; i++) {
      String target = linkdata[i].loadTarget();
      // if(linkdata[i].hasColumn()){
      // column = "" + linkdata[i].getColumn();
      // } else{
      // column = "0";
      // }
      String column = linkdata[i].hasColumn() ? "" + linkdata[i].loadColumn() : "0"; //$NON-NLS-1$ //$NON-NLS-2$
      String constant = linkdata[i].hasConst() ? "" + linkdata[i].loadConst() : "0"; //$NON-NLS-1$ //$NON-NLS-2$
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
    if (this.a.checkParam() == false) {
      return false;
    }
    if (this.alpha.checkParam() == false) {
      return false;
    }
    if (this.d.checkParam() == false) {
      return false;
    }
    if (this.theta.checkParam() == false) {
      return false;
    }
    if (this.columnA.checkParam() == false) {
      return false;
    }
    if (this.columnAlpha.checkParam() == false) {
      return false;
    }
    if (this.columnD.checkParam() == false) {
      return false;
    }
    if (this.columnTheta.checkParam() == false) {
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