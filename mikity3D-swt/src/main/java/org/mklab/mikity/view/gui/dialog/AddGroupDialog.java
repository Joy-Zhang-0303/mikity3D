/*
 * Created on 2005/02/10
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.view.gui.ParameterInputBox;


/**
 * groupを追加するためのダイアログを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/10
 */
public class AddGroupDialog extends ApplicationWindow {

  /** */
  GroupModel group;

  /**
   * 新しく生成された<code>AddGroupDialog</code>オブジェクトを初期化します。
   * @param parentShell 親シェル
   * @param group グループ
   */
  public AddGroupDialog(Shell parentShell, GroupModel group) {
    super(parentShell);
    this.group = group;
    setShellStyle(SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.CLOSE);
    setBlockOnOpen(true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Control createContents(Composite parent) {
    final Composite composite = new Composite(parent, SWT.NONE);
    final GridLayout layout = new GridLayout(2, false);
    composite.setLayout(layout);

    new ParameterInputBox(composite, SWT.READ_ONLY, Messages.getString("AddGroupDialog.0"), this.group.getName()); //$NON-NLS-1$
    final ParameterInputBox groupParam = new ParameterInputBox(composite, SWT.NONE, Messages.getString("AddGroupDialog.1"), Messages.getString("AddGroupDialog.2")); //$NON-NLS-1$ //$NON-NLS-2$

    createButtons(composite, groupParam);
    
    return composite;
  }

  /**
   * ボタンを生成します。
   * 
   * @param composite コンポジット
   * @param groupParam グループ名
   */
  private void createButtons(final Composite composite, final ParameterInputBox groupParam) {
    final Button okButton = new Button(composite, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("AddGroupDialog.3")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        final GroupModel newGroup = new GroupModel();
        newGroup.setName(groupParam.getText());
        AddGroupDialog.this.group.addGroup(newGroup);
        close();
      }
    });

    final Button cancelButton = new Button(composite, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("AddGroupDialog.4")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        close();
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setText(Messages.getString("AddGroupDialog.5")); //$NON-NLS-1$
  }
}