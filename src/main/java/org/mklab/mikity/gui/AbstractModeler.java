/*
 * Created on 2004/12/03
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui;

import java.awt.Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.mklab.mikity.gui.dialog.AddGroupDialog;
import org.mklab.mikity.gui.dialog.AddPrimitiveDialog;
import org.mklab.mikity.gui.dialog.EditPrimitiveDialog;
import org.mklab.mikity.gui.dialog.GroupConfigDialogDHParameter;
import org.mklab.mikity.gui.dialog.GroupConfigDialogCoordinateParameter;
import org.mklab.mikity.util.MessagegUtil;
import org.mklab.mikity.xml.Jamast;


/**
 * モデラーを表すクラスです。
 * 
 * Primitiveを作成し、Linkを作成するクラスです。
 * 
 * Treeからグループを読み込みます。
 * 
 * @author miki
 * @version $Revision: 1.22 $.2004/12/03
 */
public abstract class AbstractModeler extends Composite {
  /** */
  protected SceneGraphTree tree;
  /** ルート */
  protected Jamast root; 
  /** */
  protected Frame awtFrame;
  private Group treeViewerGroup;

  /**
   * コンストラクター
   * 
   * @param parent 親
   * @param style スタイル
   * @param root ルート
   */
  public AbstractModeler(Composite parent, int style, final Jamast root) {
    super(parent, style);
    this.root = root;
    this.setLayout(new GridLayout());
    this.setLayoutData(new GridData(GridData.FILL_BOTH));

    // SashForm 画面を垂直に広げることができる
    final SashForm sash = new SashForm(this, SWT.NONE);
    sash.setLayoutData(new GridData(GridData.FILL_BOTH));
    sash.setLayout(new GridLayout());

    createViewerComp(sash);
    createTreeComp(sash);
  }


  /**
   * 3Dグラフィックスを表示するcompositeを作成します。
   * 
   * @param composite
   */
  private void createViewerComp(Composite composite) {
    final GridData gridData = new GridData(GridData.FILL_BOTH);
    final Composite viewerComp = new Composite(composite, SWT.EMBEDDED);
    viewerComp.setLayout(new GridLayout());
    viewerComp.setLayoutData(gridData);
    createModelCanvas(viewerComp);
  }

  /**
   * Treeを表示するcompositeを作成します。
   * 
   * @param composite
   */
  private void createTreeComp(Composite composite) {
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    this.treeViewerGroup = new Group(composite, SWT.NONE);
    this.treeViewerGroup.setLayout(layout);

    final GridData data = new GridData(GridData.FILL_BOTH);
    data.widthHint = 10;
    this.treeViewerGroup.setLayoutData(data);
    this.treeViewerGroup.setText(Messages.getString("Modeler.0")); //$NON-NLS-1$

    this.tree = new SceneGraphTree(this.treeViewerGroup, this, this.root.loadModel(0));
    createViewer();
  }

  /**
   * 編集機能を持ったコンポジットを作成します。
   * 
   * @param composite コンポジット
   */
  public void createEditComp(Composite composite) {
    final GridData gridData = new GridData(GridData.FILL_BOTH);
    final Composite editComp = new Composite(composite, SWT.EMBEDDED);
    editComp.setLayoutData(gridData);
  }

  /**
   * 編集機能を持ったコンポジットを作成します。
   * 
   * @param composite コンポジット
   */
  public void createEditGroupComp(Composite composite) {
    final GridData gridData = new GridData(GridData.FILL_BOTH);
    final Composite editGroupComp = new Composite(composite, SWT.EMBEDDED);
    editGroupComp.setLayout(new GridLayout());
    editGroupComp.setLayoutData(gridData);
  }

  /**
   * 状態の表示、編集を行うアプリケーションを表示するcompositeを作成します。
   * 
   * @param comp コンポジット
   */
  public void createParmComp(Composite comp) {
    final Composite composite = new Composite(comp, SWT.NONE);
    final GridLayout layout = new GridLayout(1, true);
    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    composite.setLayout(layout);
    composite.setLayoutData(data);

    // Groupを追加するボタン
    final Button addGroupButton = new Button(composite, SWT.NONE);
    addGroupButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    addGroupButton.setText(Messages.getString("Modeler.1")); //$NON-NLS-1$

    // //いつか実装できたらすてきだね
    // //undoボタン
    // Button undoButton = new Button(composite, SWT.NONE);
    // undoButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    // undoButton.setText("元に戻す");

    final Button xzButton = new Button(composite, SWT.NONE);
    xzButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    xzButton.setText(Messages.getString("Modeler.2")); //$NON-NLS-1$

    // DHパラメータを設定する
    final Button dhButton = new Button(composite, SWT.NONE);
    dhButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    dhButton.setText(Messages.getString("Modeler.3")); //$NON-NLS-1$

    final Button primAddButton = new Button(composite, SWT.NONE);
    primAddButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    primAddButton.setText(Messages.getString("Modeler.4")); //$NON-NLS-1$

    final Button primEditButton = new Button(composite, SWT.NONE);
    primEditButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    primEditButton.setText(Messages.getString("Modeler.5")); //$NON-NLS-1$

    // 保存して終了ボタン
    final Button closeButton = new Button(composite, SWT.NONE);
    closeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    closeButton.setText(Messages.getString("Modeler.6")); //$NON-NLS-1$

    // キャンセルボタン
    final Button cancelButton = new Button(composite, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("Modeler.7")); //$NON-NLS-1$

    xzButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
      // nothing to do
      }
    });

    addGroupButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        final org.mklab.mikity.xml.model.Group group = AbstractModeler.this.tree.getSelectionGroup();
        if (group == null) {
          MessagegUtil.show(getShell(), Messages.getString("Modeler.8")); //$NON-NLS-1$
          return;
        }

        final AddGroupDialog dialog = new AddGroupDialog(getShell(), group);
        dialog.open();

        AbstractModeler.this.tree.fillTree();
        createViewer();
      }
    });

    dhButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        final org.mklab.mikity.xml.model.Group group = AbstractModeler.this.tree.getSelectionGroup();
        if (group == null) {
          MessagegUtil.show(getShell(), Messages.getString("Modeler.9")); //$NON-NLS-1$
          return;
        }
        
        final         org.mklab.mikity.xml.model.LinkData[] linkdata = group.getLinkData();
        if (linkdata.length == 0) {
          final MessageBox mesBox = new MessageBox(getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage(Messages.getString("Modeler.10")); //$NON-NLS-1$
          mesBox.setText(Messages.getString("Modeler.11")); //$NON-NLS-1$
          int result = mesBox.open();
          if (result == SWT.YES) {
            final GroupConfigDialogDHParameter groupConf = new GroupConfigDialogDHParameter(getShell(), group, AbstractModeler.this.tree.getGroupEditable());
            groupConf.open();
          } else if (result == SWT.NO) {
            GroupConfigDialogCoordinateParameter groupConf = new GroupConfigDialogCoordinateParameter(getShell(), group, AbstractModeler.this.tree.getGroupEditable());
            groupConf.open();
          }
        }

        for (int i = 0; i < linkdata.length; i++) {
          if (linkdata[i].hasDHParameter()) {
            final GroupConfigDialogDHParameter groupConf = new GroupConfigDialogDHParameter(getShell(), group, AbstractModeler.this.tree.getGroupEditable());
            groupConf.open();
            break;
          } else if (linkdata[i].hasCoordinateParameter()) {
            final GroupConfigDialogCoordinateParameter groupConf = new GroupConfigDialogCoordinateParameter(getShell(), group, AbstractModeler.this.tree.getGroupEditable());
            groupConf.open();
            break;
          }
        }

        AbstractModeler.this.tree.fillTree();
        createViewer();
      }
    });

    primAddButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        final org.mklab.mikity.xml.model.Group group = AbstractModeler.this.tree.getSelectionGroup();
        if (group == null) {
          final MessageBox box = new MessageBox(getShell(), SWT.ICON_WARNING);
          box.setText(Messages.getString("Modeler.12")); //$NON-NLS-1$
          box.setMessage(Messages.getString("Modeler.13")); //$NON-NLS-1$
          box.open();
          return;
        }

        final AddPrimitiveDialog dialog = new AddPrimitiveDialog(getShell(), group);
        dialog.open();

        AbstractModeler.this.tree.fillTree();
        createViewer();
      }
    });

    primEditButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        final Object prim = AbstractModeler.this.tree.getSelectionData();
        final org.mklab.mikity.xml.model.Group group = AbstractModeler.this.tree.getSelectionGroup();

        if (prim == null) {
          final MessageBox box = new MessageBox(getShell(), SWT.ICON_WARNING);
          box.setText(Messages.getString("Modeler.14")); //$NON-NLS-1$
          box.setMessage(Messages.getString("Modeler.15")); //$NON-NLS-1$
          box.open();
          return;
        }

        final EditPrimitiveDialog editPrim = new EditPrimitiveDialog(getShell(), prim, group);
        editPrim.open();

        AbstractModeler.this.tree.fillTree();
        createViewer();

      }
    });
  }

  /**
   * ツリーのルートを設定します。
   * @param root ツリーのルート
   */
  public void setModel(Jamast root) {
    this.root = root;
    this.tree.setModel(root.loadModel(0));
    createViewer();
  }

  /**
   * シーングラフツリーにプリミティブのデータを追加します。
   */
  public void fillTree() {
    this.tree.fillTree();
  }
  
  /**
   * GroupをsinsiCanvasに読み込ませ、Frameにaddします。
   */
  public abstract void createViewer();

  /**
   * @param viewerComp ビュワーコンポジット
   */
  public abstract void createModelCanvas(Composite viewerComp);
}