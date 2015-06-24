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
import org.mklab.mikity.model.xml.simplexml.model.Translation;
import org.mklab.mikity.model.xml.simplexml.model.Rotation;
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
  ParameterInputBox translationX;
  ParameterInputBox translationY;
  ParameterInputBox translationZ;
  ParameterInputBox rotationX;
  ParameterInputBox rotationY;
  ParameterInputBox rotationZ;
  ParameterInputBox translationXdataNumber;
  ParameterInputBox translationYdataNumber;
  ParameterInputBox translationZdataNumber;
  ParameterInputBox rotationXdataNumber;
  ParameterInputBox rotationYdataNumber;
  ParameterInputBox rotationZdataNumber;

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

    this.translationX = new ParameterInputBox(parameterGroup, style, "translationX", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.translationXdataNumber = new ParameterInputBox(parameterGroup, style, 0);

    this.translationY = new ParameterInputBox(parameterGroup, style, "translationY", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.translationYdataNumber = new ParameterInputBox(parameterGroup, style, 0);

    this.translationZ = new ParameterInputBox(parameterGroup, style, "translationZ", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.translationZdataNumber = new ParameterInputBox(parameterGroup, style, 0);

    this.rotationX = new ParameterInputBox(parameterGroup, style, "rotationX", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.rotationXdataNumber = new ParameterInputBox(parameterGroup, style, 0);

    this.rotationY = new ParameterInputBox(parameterGroup, style, "rotationY", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.rotationYdataNumber = new ParameterInputBox(parameterGroup, style, 0);

    this.rotationZ = new ParameterInputBox(parameterGroup, style, "rotationZ", "0.0"); //$NON-NLS-1$ //$NON-NLS-2$
    this.rotationZdataNumber = new ParameterInputBox(parameterGroup, style, 0);

    setParametersInDialog();
  }

  private void createButtonComposite() {
    final Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("GroupConfigDialogLink.4")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (containsOnlyNumbers() == false) {
          final MessageBox message = new MessageBox(GroupConfigWithCoordinateParameterDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("GroupConfigDialogLink.7")); //$NON-NLS-1$
          message.setText(Messages.getString("GroupConfigDialogLink.8")); //$NON-NLS-1$
          message.open();
          return;
        }

        updateGroupParameters();
        GroupConfigWithCoordinateParameterDialog.this.sShell.close();
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
   * グループのパラメータを更新します。
   */
  void updateGroupParameters() {
    this.group.setName(this.groupName.getText());
    this.group.clearLinkData();
    addLinkData(this.translationX, this.translationXdataNumber);
    addLinkData(this.translationY, this.translationYdataNumber);
    addLinkData(this.translationZ, this.translationZdataNumber);
    addLinkData(this.rotationX, this.rotationXdataNumber);
    addLinkData(this.rotationY, this.rotationYdataNumber);
    addLinkData(this.rotationZ, this.rotationZdataNumber);
    
    final Translation translation = new Translation(this.translationX.getFloatValue(), this.translationY.getFloatValue(), this.translationZ.getFloatValue());
    final Rotation rotation = new Rotation(this.rotationX.getFloatValue(), this.rotationY.getFloatValue(), this.rotationZ.getFloatValue());
    
    this.group.setTranslation(translation);
    this.group.setRotation(rotation);
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
      linkData.setNumber(dataNumber.getIntValue());
      this.group.addLinkData(linkData);
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
   * ダイアログにパラメータを設定します。
   */
  private void setParametersInDialog() {
    setLinkDataInDialog();

    final Rotation rotation = this.group.getRotation();
    final Translation translation = this.group.getTranslation();

    if (rotation != null) {
      setRotationInDialog(rotation);
    }
    if (translation != null) {
      setTranslationInDialog(translation);
    }
  }

  /**
   * 角度をダイアログに設定します。
   * 
   * @param rotation 回転
   */
  private void setRotationInDialog(Rotation rotation) {
    this.rotationX.setText("" + rotation.getX()); //$NON-NLS-1$
    this.rotationY.setText("" + rotation.getY()); //$NON-NLS-1$
    this.rotationZ.setText("" + rotation.getZ()); //$NON-NLS-1$
  }

  /**
   * 並進量をダイアログに設定します。
   * 
   * @param translation 並進量
   */
  private void setTranslationInDialog(Translation translation) {
    this.translationX.setText("" + translation.getX()); //$NON-NLS-1$
    this.translationY.setText("" + translation.getY()); //$NON-NLS-1$
    this.translationZ.setText("" + translation.getZ()); //$NON-NLS-1$
  }

  /**
   * Linkdata の column を表示させる
   */
  private void setLinkDataInDialog() {
    final LinkData[] linkData = this.group.getLinkData();

    for (int i = 0; i < linkData.length; i++) {
      final String target = linkData[i].getTarget();
      final String dataNumber = linkData[i].hasNumber() ? "" + linkData[i].getNumber() : "0"; //$NON-NLS-1$ //$NON-NLS-2$

      if (target.equals("translationX")) { //$NON-NLS-1$
        this.translationXdataNumber.setText(dataNumber);
      } else if (target.equals("translationY")) { //$NON-NLS-1$
        this.translationYdataNumber.setText(dataNumber);
      } else if (target.equals("translationZ")) { //$NON-NLS-1$
        this.translationZdataNumber.setText(dataNumber);
      } else if (target.equals("rotationX")) { //$NON-NLS-1$
        this.rotationXdataNumber.setText(dataNumber);
      } else if (target.equals("rotationY")) { //$NON-NLS-1$
        this.rotationYdataNumber.setText(dataNumber);
      } else if (target.equals("rotationZ")) { //$NON-NLS-1$
        this.rotationZdataNumber.setText(dataNumber);
      }
    }
  }

  /**
   * 数字のみが入力されているか判定します。
   * 
   * @return 数字のみが入力されていればtrue，そうでなければfalse
   */
  boolean containsOnlyNumbers() {
    if (this.translationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationZ.containsOnlyNumbers() == false) {
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
    if (this.translationXdataNumber.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationYdataNumber.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationZdataNumber.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationXdataNumber.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationYdataNumber.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationZdataNumber.containsOnlyNumbers() == false) {
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