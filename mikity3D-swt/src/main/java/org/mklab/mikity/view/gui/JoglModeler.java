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
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.view.renderer.jogl.JoglModelRenderer;


/**
 * モデラーを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.22 $.2004/12/03
 */
public class JoglModeler extends Composite {
  /** シーングラフツリー。 */
  SceneGraphTree tree;
  /** レンダラー。 */
  private JoglModelRenderer renderer;
  /** ルート。 */
  Mikity3d root;
  /** */
  Frame awtFrame;
  
  /** 変更されていればtrue。 */
  private boolean isChanged = false;
  
  private Group treeViewerGroup;

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
   * 3Dグラフィックスを表示するコンポジットを生成します。
   * 
   * @param composite 親コンポジット
   */
  private void createViewerComposite(Composite composite) {
    final GridData gridData = new GridData(GridData.FILL_BOTH);
    final Composite viewerComposite = new Composite(composite, SWT.EMBEDDED);
    viewerComposite.setLayout(new GridLayout());
    viewerComposite.setLayoutData(gridData);
    createModelCanvas(viewerComposite);
  }

  /**
   * Treeを表示するコンポジットを作成します。
   * 
   * @param composite 親コンポジット
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

  /**
   * ツリーのルートを設定します。
   * 
   * @param root ツリーのルート
   */
  public void setModel(Mikity3d root) {
    this.root = root;
    this.isChanged = false;
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
    org.mklab.mikity.model.xml.simplexml.model.GroupModel[] children = this.tree.getModel().getGroups();
    this.renderer.setChildren(children);
    this.renderer.setConfiguration(this.root.getConfiguration(0));
  }

  /**
   * キャンバスを生成します。
   * 
   * @param viewerComposite ビュワーコンポジット
   */
  private void createModelCanvas(Composite viewerComposite) {
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
  
  /**
   * 選択されているグループを返します。
   * 
   * @return 選択されているグループ
   */
  public GroupModel getTargetGroup() {
    return this.tree.getTargetGroup();
  }
  
  /**
   * シーングラフのルートを返します。
   * 
   * @return root
   */
  public Mikity3d getRoot() {
    return this.root;
  }
  
  /**
   * 変更されたか設定します。
   * 
   * @param isChanged 変更されている場合true
   */
  public void setChanged(final boolean isChanged) {
    this.isChanged = isChanged;
  }
  
  /**
   * 変更されたか判定します。
   * 
   * @return 変更されている場合true
   */
  public boolean isChanged() {
    return this.isChanged;
  }
}