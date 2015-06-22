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
 * グループの設定を行うダイアログを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/03
 */
public class GroupConfigWithCoordinateParameterDialog {

  Shell sShell = null;
  private Shell parentShell = null;
  org.mklab.mikity.model.xml.simplexml.model.Group group;

  ParameterInputBox groupName;
  ParameterInputBox linkDataLocationX;
  ParameterInputBox linkDataLocationY;
  ParameterInputBox linkDataLocationZ;
  ParameterInputBox linkDataRotationX;
  ParameterInputBox linkDataRotationY;
  ParameterInputBox linkDataRotationZ;
  ParameterInputBox dataNumberLocationX;
  ParameterInputBox dataNumberLocationY;
  ParameterInputBox dataNumberLocationZ;
  ParameterInputBox dataNumberRotationX;
  ParameterInputBox dataNumberRotationY;
  ParameterInputBox dataNumberRotationZ;

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
    
    this.groupName = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("GroupConfigDialogLink.1"), "root"); //$NON-NLS-1$ //$NON-NLS-2$

    if (this.group.getName() != null) {
      this.groupName.setText(this.group.getName());
    }
    
    
    createLinkDataGroup();

    createButtonComposite();

    createStatusBar();

    if (this.editable == false) {
      setStatus(Messages.getString("GroupConfigDialogLink.10")); //$NON-NLS-1$
    }
  }
  

  /**
   * グループのパラメータを表示させる
   */
  private void createLinkDataGroup() {
    final Group parameterGroup = new Group(this.sShell, SWT.NONE);
    final GridLayout layout = new GridLayout();
    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.horizontalSpan = 2;
    layout.numColumns = 3;
    parameterGroup.setText(Messages.getString("GroupConfigDialogLink.11")); //$NON-NLS-1$
    parameterGroup.setLayout(layout);
    parameterGroup.setLayoutData(data);

    final GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
    data.widthHint = 40;
    final Label label1 = new Label(parameterGroup, SWT.CENTER);
    label1.setText(Messages.getString("GroupConfigDialogLink.12")); //$NON-NLS-1$
    label1.setLayoutData(gridData1);

    final GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
    gridData2.widthHint = 65;
    final Label label2 = new Label(parameterGroup, SWT.CENTER);
    label2.setText(Messages.getString("GroupConfigDialogLink.13")); //$NON-NLS-1$
    label2.setLayoutData(gridData2);

    final GridData gridData3 = new GridData(GridData.FILL_HORIZONTAL);
    gridData3.widthHint = 65;
    final Label label3 = new Label(parameterGroup, SWT.CENTER);
    label3.setText(Messages.getString("GroupConfigDialogLink.14")); //$NON-NLS-1$
    label3.setLayoutData(gridData3);

    int style;
    if (this.editable == true) {
      style = SWT.NONE;
    } else {
      style = SWT.READ_ONLY;
    }
    
    this.linkDataLocationX = new ParameterInputBox(parameterGroup, style, "locationX", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.dataNumberLocationX = new ParameterInputBox(parameterGroup, style, 0);

    this.linkDataLocationY = new ParameterInputBox(parameterGroup, style, "locationY", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.dataNumberLocationY = new ParameterInputBox(parameterGroup, style, 0);

    this.linkDataLocationZ = new ParameterInputBox(parameterGroup, style, "locationZ", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.dataNumberLocationZ = new ParameterInputBox(parameterGroup, style, 0);

    this.linkDataRotationX = new ParameterInputBox(parameterGroup, style, "rotationX", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.dataNumberRotationX = new ParameterInputBox(parameterGroup, style, 0);

    this.linkDataRotationY = new ParameterInputBox(parameterGroup, style, "rotationY", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.dataNumberRotationY = new ParameterInputBox(parameterGroup, style, 0);

    this.linkDataRotationZ = new ParameterInputBox(parameterGroup, style, "rotationZ", "0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.dataNumberRotationZ = new ParameterInputBox(parameterGroup, style, 0);

    setLinkDataParameters();
  }

  private void createButtonComposite() {
    final Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("GroupConfigDialogLink.4")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (containsOnlyNumbers()) {
          final MessageBox message = new MessageBox(GroupConfigWithCoordinateParameterDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          message.setMessage(Messages.getString("GroupConfigDialogLink.5")); //$NON-NLS-1$
          message.setText(Messages.getString("GroupConfigDialogLink.6")); //$NON-NLS-1$
          int yesNo = message.open();
          if (yesNo == SWT.YES) {
            GroupConfigWithCoordinateParameterDialog.this.group.setName(GroupConfigWithCoordinateParameterDialog.this.groupName.getText());
            GroupConfigWithCoordinateParameterDialog.this.group.clearLinkData();
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.linkDataLocationX, GroupConfigWithCoordinateParameterDialog.this.dataNumberLocationX);
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.linkDataLocationY, GroupConfigWithCoordinateParameterDialog.this.dataNumberLocationY);
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.linkDataLocationZ, GroupConfigWithCoordinateParameterDialog.this.dataNumberLocationZ);
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.linkDataRotationX, GroupConfigWithCoordinateParameterDialog.this.dataNumberRotationX);
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.linkDataRotationY, GroupConfigWithCoordinateParameterDialog.this.dataNumberRotationY);
            addLinkData(GroupConfigWithCoordinateParameterDialog.this.linkDataRotationZ, GroupConfigWithCoordinateParameterDialog.this.dataNumberRotationZ);
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
  }

  /**
   * Linkdataを追加します。
   * 
   * @param parameter パラメータ
   * @param dataNumber データの番号
   */
  void addLinkData(final ParameterInputBox parameter, final ParameterInputBox dataNumber) {
    if (dataNumber.getIntValue() != 0) {
      final LinkData linkData = new LinkData();
      linkData.setTarget(parameter.getLabelText());
      linkData.setBasis(parameter.getFloatValue());
      linkData.setNumber(dataNumber.getIntValue());
      this.group.addLinkdata(linkData);
    }
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
  private void setLinkDataParameters() {
    final LinkData[] linkdata = this.group.getLinkData();

    for (int i = 0; i < linkdata.length; i++) {
      final String target = linkdata[i].getTarget();
      final String dataNumber = linkdata[i].hasNumber() ? "" + linkdata[i].getNumber() : "0"; //$NON-NLS-1$ //$NON-NLS-2$
      final String basis = linkdata[i].hasBasis() ? "" + linkdata[i].getBasis() : "0"; //$NON-NLS-1$ //$NON-NLS-2$
      
      if (target.equals("locationX")) { //$NON-NLS-1$
        this.dataNumberLocationX.setText(dataNumber);
        this.linkDataLocationX.setText(basis);
      } else if (target.equals("locationY")) { //$NON-NLS-1$
        this.dataNumberLocationY.setText(dataNumber);
        this.linkDataLocationY.setText(basis);
      } else if (target.equals("locationZ")) { //$NON-NLS-1$
        this.dataNumberLocationZ.setText(dataNumber);
        this.linkDataLocationZ.setText(basis);
      } else if (target.equals("rotationX")) { //$NON-NLS-1$
        this.dataNumberRotationX.setText(dataNumber);
        this.linkDataRotationX.setText(basis);
      } else if (target.equals("rotationY")) { //$NON-NLS-1$
        this.dataNumberRotationY.setText(dataNumber);
        this.linkDataRotationY.setText(basis);
      } else if (target.equals("rotationZ")) { //$NON-NLS-1$
        this.dataNumberRotationZ.setText(dataNumber);
        this.linkDataRotationZ.setText(basis);
      }
    }
  }

  /**
   * 数字のみが入力されているか判定します。
   * 
   * @return 数字のみが入力されていればtrue，そうでなければfalse
   */
  boolean containsOnlyNumbers() {
    if (this.linkDataLocationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.linkDataLocationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.linkDataLocationZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.linkDataRotationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.linkDataRotationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.linkDataRotationZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.dataNumberLocationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.dataNumberLocationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.dataNumberLocationZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.dataNumberRotationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.dataNumberRotationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.dataNumberRotationZ.containsOnlyNumbers() == false) {
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