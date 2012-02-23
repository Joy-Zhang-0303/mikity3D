/*
 * Created on 2005/02/10
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.dialog;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.gui.ParameterInputBox;
import org.mklab.mikity.xml.model.Group;


/**
 * groupを追加する
 * 
 * @author miki
 * @version $Revision: 1.1 $.2005/02/10
 */
public class AddGroupDialog extends ApplicationWindow {

  /** */
  Group group;

  /**
   * コンストラクター
   * 
   * @param parentShell 親シェル
   * @param group グループ
   */
  public AddGroupDialog(Shell parentShell, Group group) {
    super(parentShell);
    this.group = group;
    setShellStyle(SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.CLOSE);
    setBlockOnOpen(true);
    // createSShell();
  }

  /**
   * コンテンツ作成
   * 
   * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents(Composite parent) {
    Composite comp = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout(2, false);
    comp.setLayout(layout);

    new ParameterInputBox(comp, SWT.READ_ONLY, Messages.getString("AddGroupDialog.0"), this.group.loadName()); //$NON-NLS-1$
    final ParameterInputBox groupParam = new ParameterInputBox(comp, SWT.NONE, Messages.getString("AddGroupDialog.1"), Messages.getString("AddGroupDialog.2")); //$NON-NLS-1$ //$NON-NLS-2$

    Button okButton = new Button(comp, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("AddGroupDialog.3")); //$NON-NLS-1$

    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        Group newGroup = new Group();
        newGroup.setName(groupParam.getText());
        AddGroupDialog.this.group.addGroup(newGroup);
        close();
      }
    });

    Button cancelButton = new Button(comp, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("AddGroupDialog.4")); //$NON-NLS-1$

    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        close();
      }
    });
    return comp;
  }

  /**
   * ぐるーぷのついかをおこなうしぇるを作成する
   * 
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setText(Messages.getString("AddGroupDialog.5")); //$NON-NLS-1$
  }
}