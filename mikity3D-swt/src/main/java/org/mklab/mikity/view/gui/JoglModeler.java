/*
 * Created on 2004/12/03
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui;

import java.awt.Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.view.renderer.jogl.JoglModelRenderer;


/**
 * モデラーを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.22 $.2004/12/03
 */
public class JoglModeler extends Composite {

  /** シーングラフツリー。 */
  protected SceneGraphTree tree;
  /** ルート。 */
  protected Mikity3d root;
  /** */
  protected Frame awtFrame;
  private Group treeViewerGroup;
  
  private JoglModelRenderer renderer;

  /**
   * 新しく生成された<code>AbstractModeler</code>オブジェクトを初期化します。
   * @param parent 親
   * @param root ルート
   */
  public JoglModeler(Composite parent, final Mikity3d root) {
    super(parent, SWT.None);
    this.root = root;
    this.setLayout(new GridLayout());
    this.setLayoutData(new GridData(GridData.FILL_BOTH));

    // SashForm 画面を垂直に広げることができる
    final SashForm sash = new SashForm(this, SWT.NONE);
    sash.setLayoutData(new GridData(GridData.FILL_BOTH));
    sash.setLayout(new GridLayout());

    createViewerComposite(sash);
    createTreeComposite(sash);
  }

  /**
   * 3Dグラフィックスを表示するcompositeを作成します。
   * 
   * @param composite
   */
  private void createViewerComposite(Composite composite) {
    final GridData gridData = new GridData(GridData.FILL_BOTH);
    final Composite viewerComposite = new Composite(composite, SWT.EMBEDDED);
    viewerComposite.setLayout(new GridLayout());
    viewerComposite.setLayoutData(gridData);
    createModelCanvas(viewerComposite);
  }

  /**
   * Treeを表示するcompositeを作成します。
   * 
   * @param composite
   */
  private void createTreeComposite(Composite composite) {
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    this.treeViewerGroup = new Group(composite, SWT.NONE);
    this.treeViewerGroup.setLayout(layout);

    final GridData data = new GridData(GridData.FILL_BOTH);
    data.widthHint = 10;
    this.treeViewerGroup.setLayoutData(data);
    this.treeViewerGroup.setText(Messages.getString("Modeler.0")); //$NON-NLS-1$

    this.tree = new SceneGraphTree(this.treeViewerGroup, this, this.root.getModel(0));
    createViewer();
  }

//  /**
//   * 編集機能を持ったコンポジットを作成します。
//   * 
//   * @param composite コンポジット
//   */
//  public void createEditComposite(Composite composite) {
//    final GridData gridData = new GridData(GridData.FILL_BOTH);
//    final Composite editComposite = new Composite(composite, SWT.EMBEDDED);
//    editComposite.setLayoutData(gridData);
//  }
//
//  /**
//   * 編集機能を持ったコンポジットを作成します。
//   * 
//   * @param composite コンポジット
//   */
//  public void createEditGroupComposite(Composite composite) {
//    final GridData gridData = new GridData(GridData.FILL_BOTH);
//    final Composite editGroupComposite = new Composite(composite, SWT.EMBEDDED);
//    editGroupComposite.setLayout(new GridLayout());
//    editGroupComposite.setLayoutData(gridData);
//  }

//  /**
//   * 状態の表示、編集を行うアプリケーションを表示するcompositeを作成します。
//   * 
//   * @param comp コンポジット
//   */
//  public void createParmComposite(Composite comp) {
//    final Composite composite = new Composite(comp, SWT.NONE);
//    final GridLayout layout = new GridLayout(1, true);
//    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
//    composite.setLayout(layout);
//    composite.setLayoutData(data);
//
//    // Groupを追加するボタン
//    final Button addGroupButton = new Button(composite, SWT.NONE);
//    addGroupButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//    addGroupButton.setText(Messages.getString("Modeler.1")); //$NON-NLS-1$
//
//    // //いつか実装できたらすてきだね
//    // //undoボタン
//    // Button undoButton = new Button(composite, SWT.NONE);
//    // undoButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//    // undoButton.setText("元に戻す");
//
//    final Button xzButton = new Button(composite, SWT.NONE);
//    xzButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//    xzButton.setText(Messages.getString("Modeler.2")); //$NON-NLS-1$
//
//    // DHパラメータを設定する
//    final Button dhButton = new Button(composite, SWT.NONE);
//    dhButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//    dhButton.setText(Messages.getString("Modeler.3")); //$NON-NLS-1$
//
//    final Button primitiveAddButton = new Button(composite, SWT.NONE);
//    primitiveAddButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//    primitiveAddButton.setText(Messages.getString("Modeler.4")); //$NON-NLS-1$
//
//    final Button primitiveEditButton = new Button(composite, SWT.NONE);
//    primitiveEditButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//    primitiveEditButton.setText(Messages.getString("Modeler.5")); //$NON-NLS-1$
//
//    // 保存して終了ボタン
//    final Button closeButton = new Button(composite, SWT.NONE);
//    closeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//    closeButton.setText(Messages.getString("Modeler.6")); //$NON-NLS-1$
//
//    // キャンセルボタン
//    final Button cancelButton = new Button(composite, SWT.NONE);
//    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//    cancelButton.setText(Messages.getString("Modeler.7")); //$NON-NLS-1$
//
//    xzButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
//      /**
//       * {@inheritDoc}
//       */
//      @Override
//      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//        // nothing to do
//      }
//    });
//
//    addGroupButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
//      /**
//       * {@inheritDoc}
//       */
//      @Override
//      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//        final org.mklab.mikity.model.xml.simplexml.model.Group group = JoglModeler.this.tree.getTargetGroup();
//        if (group == null) {
//          MessagegUtil.show(getShell(), Messages.getString("Modeler.8")); //$NON-NLS-1$
//          return;
//        }
//
//        final AddGroupDialog dialog = new AddGroupDialog(getShell(), group);
//        dialog.open();
//
//        JoglModeler.this.tree.fillTree();
//        createViewer();
//      }
//    });
//
//    dhButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
//      /**
//       * {@inheritDoc}
//       */
//      @Override
//      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//
//        final org.mklab.mikity.model.xml.simplexml.model.Group group = JoglModeler.this.tree.getTargetGroup();
//        if (group == null) {
//          MessagegUtil.show(getShell(), Messages.getString("Modeler.9")); //$NON-NLS-1$
//          return;
//        }
//
//        final org.mklab.mikity.model.xml.simplexml.model.LinkData[] linkdata = group.getLinkData();
//        if (linkdata.length == 0) {
//          final MessageBox messageBox = new MessageBox(getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
//          messageBox.setMessage(Messages.getString("Modeler.10")); //$NON-NLS-1$
//          messageBox.setText(Messages.getString("Modeler.11")); //$NON-NLS-1$
//          int result = messageBox.open();
//          if (result == SWT.YES) {
//            final EditGroupWithDHParameterDialog groupConf = new EditGroupWithDHParameterDialog(getShell(), group, JoglModeler.this.tree.isGroupEditable());
//            groupConf.open();
//          } else if (result == SWT.NO) {
//            EditGroupDialog groupConf = new EditGroupDialog(getShell(), group, JoglModeler.this.tree.isGroupEditable(), JoglModeler.this.tree, JoglModeler.this);
//            groupConf.open();
//          }
//        }
//
//        for (int i = 0; i < linkdata.length; i++) {
//          if (linkdata[i].hasCoordinateParameter()) {
//            final EditGroupDialog groupConf = new EditGroupDialog(getShell(), group, JoglModeler.this.tree.isGroupEditable(), JoglModeler.this.tree, JoglModeler.this);
//            groupConf.open();
//            break;
//          }
//        }
//
//        JoglModeler.this.tree.fillTree();
//        createViewer();
//      }
//    });
//
//    primitiveAddButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
//      /**
//       * {@inheritDoc}
//       */
//      @Override
//      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//
//        final org.mklab.mikity.model.xml.simplexml.model.Group group = JoglModeler.this.tree.getTargetGroup();
//        if (group == null) {
//          final MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING);
//          messageBox.setText(Messages.getString("Modeler.12")); //$NON-NLS-1$
//          messageBox.setMessage(Messages.getString("Modeler.13")); //$NON-NLS-1$
//          messageBox.open();
//          return;
//        }
//
//        final AddPrimitiveDialog dialog = new AddPrimitiveDialog(getShell(), group);
//        dialog.open();
//
//        JoglModeler.this.tree.fillTree();
//        createViewer();
//      }
//    });
//
//    primitiveEditButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
//      /**
//       * {@inheritDoc}
//       */
//      @Override
//      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//
//        final Object primitive = JoglModeler.this.tree.getSelectedData();
//        final org.mklab.mikity.model.xml.simplexml.model.Group group = JoglModeler.this.tree.getTargetGroup();
//
//        if (primitive == null) {
//          final MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING);
//          messageBox.setText(Messages.getString("Modeler.14")); //$NON-NLS-1$
//          messageBox.setMessage(Messages.getString("Modeler.15")); //$NON-NLS-1$
//          messageBox.open();
//          return;
//        }
//
//        final EditPrimitiveDialog dialog = new EditPrimitiveDialog(getShell(), primitive, group, JoglModeler.this.tree,  JoglModeler.this);
//        dialog.open();
//
//        JoglModeler.this.tree.fillTree();
//        createViewer();
//
//      }
//    });
//  }

  /**
   * ツリーのルートを設定します。
   * 
   * @param root ツリーのルート
   */
  public void setModel(Mikity3d root) {
    this.root = root;
    this.tree.setModel(root.getModel(0));
    createViewer();
  }

  /**
   * シーングラフツリーにプリミティブのデータを追加します。
   */
  public void fillTree() {
    this.tree.fillTree();
  }

  /**
   * GroupをCanvasに読み込み、Frameに追加します。
   */
  public void createViewer() {
    org.mklab.mikity.model.xml.simplexml.model.Group[] children = this.tree.getModel().getGroups();
    this.renderer.setChildren(children);
    this.renderer.setConfiguration(this.root.getConfiguration(0));
  }

  /**
   * キャンバスを生成します。
   * 
   * @param viewerComposite ビュワーコンポジット
   */
  public void createModelCanvas(Composite viewerComposite) {
    this.awtFrame = SWT_AWT.new_Frame(viewerComposite);
    this.renderer = new JoglModelRenderer();
    this.awtFrame.add(this.renderer);
  }
  
  /**
   * モデルをリセットし、初期状態に戻します。 
   */
  public void resetToInitialState() {
    this.renderer.resetToInitialState();
    this.renderer.updateDisplay();
  }
 
  /**
   * 再描画します。 
   */
  public void updateDisplay() {
    this.renderer.updateDisplay();
  }
}