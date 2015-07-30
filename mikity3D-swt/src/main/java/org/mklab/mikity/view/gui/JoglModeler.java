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
import org.mklab.mikity.model.ObjectGroupManager;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.view.renderer.jogl.JoglObjectRenderer;


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
  private JoglObjectRenderer renderer;
  /** オブジェクトグループのマネージャ。 */
  private ObjectGroupManager manager;
  
  /** ルート。 */
  Mikity3DModel root;
  
  /** 変更されていればtrue。 */
  private boolean isChanged = false;

  /**
   * 新しく生成された<code>AbstractModeler</code>オブジェクトを初期化します。
   * @param parent 親
   * @param root ルート
   */
  public JoglModeler(Composite parent, final Mikity3DModel root) {
    super(parent, SWT.None);
    this.root = root;
    this.setLayout(new GridLayout());
    this.setLayoutData(new GridData(GridData.FILL_BOTH));

    // SashForm 画面を垂直に広げることができる
    final SashForm sash = new SashForm(this, SWT.NONE);
    sash.setLayoutData(new GridData(GridData.FILL_BOTH));
    sash.setLayout(new GridLayout());

    createCanvasComposite(sash);
    createTreeComposite(sash);
  }

  /**
   * 3Dグラフィックスを表示するキャンバスを生成します。
   * 
   * @param composite 親コンポジット
   */
  private void createCanvasComposite(Composite composite) {
    final GridData gridData = new GridData(GridData.FILL_BOTH);
    final Composite canvasComposite = new Composite(composite, SWT.EMBEDDED);
    canvasComposite.setLayout(new GridLayout());
    canvasComposite.setLayoutData(gridData);
    createModelCanvas(canvasComposite);
  }

  /**
   * Treeを表示するコンポジットを作成します。
   * 
   * @param composite 親コンポジット
   */
  private void createTreeComposite(Composite composite) {
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    final Group group = new Group(composite, SWT.NONE);
    group.setLayout(layout);

    final GridData data = new GridData(GridData.FILL_BOTH);
    data.widthHint = 10;
    group.setLayoutData(data);
    group.setText(Messages.getString("Modeler.0")); //$NON-NLS-1$

    this.tree = new SceneGraphTree(group, this, this.root.getScene(0));
    updateRenderer();
  }

  /**
   * ツリーのルートを設定します。
   * 
   * @param root ツリーのルート
   */
  public void setModel(Mikity3DModel root) {
    this.root = root;
    this.isChanged = false;
    this.tree.setModel(root.getScene(0));
    updateRenderer();
  }

  /**
   * シーングラフツリーにプリミティブのデータを追加します。
   */
  public void fillTree() {
    this.tree.fillTree();
  }

  /**
   * RootGroupとConfigurationをCanvasに設定します。
   */
  public void updateRenderer() {
    final GroupModel[] rootGroups = this.tree.getModel().getGroups();
    final ConfigurationModel configuration = this.root.getConfiguration(0);
    
    this.manager.clearObjectGroups();
    this.renderer.setRootGroups(rootGroups, this.manager);
    this.renderer.setConfiguration(configuration);
  }

  /**
   * キャンバスを生成します。
   * 
   * @param canvasComposite ビュワーコンポジット
   */
  private void createModelCanvas(Composite canvasComposite) {
    this.renderer = new JoglObjectRenderer();
    this.manager = new ObjectGroupManager();
    
    final Frame frame = SWT_AWT.new_Frame(canvasComposite);
    frame.add(this.renderer);
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
  public Mikity3DModel getRoot() {
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