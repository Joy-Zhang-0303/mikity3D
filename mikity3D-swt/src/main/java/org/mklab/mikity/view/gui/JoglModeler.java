/*
 * Created on 2004/12/03
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui;

import java.awt.Frame;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
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

    final SashForm sash = new SashForm(this, SWT.NONE);
    sash.setLayoutData(new GridData(GridData.FILL_BOTH));
    sash.setLayout(new GridLayout());
    
    createSceneGraphComposite(sash);
    createCanvasComposite(sash);
    
    updateRenderer();
  }

  /**
   * 3Dグラフィックスを表示するキャンバスを生成します。
   * 
   * @param parent 親コンポジット
   */
  private void createCanvasComposite(Composite parent) {
    final Composite canvas = new Composite(parent, SWT.EMBEDDED);
    canvas.setLayout(new GridLayout());
    canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
    createModelCanvas(canvas);
  }

  /**
   * シーングラフを表示するコンポジットを作成します。
   * 
   * @param parent 親コンポジット
   */
  private void createSceneGraphComposite(Composite parent) {
    final Composite sceneGraph = new Composite(parent, SWT.EMBEDDED);
    sceneGraph.setLayout(new GridLayout());
    sceneGraph.setLayoutData(new GridData(GridData.FILL_BOTH));
    this.tree = new SceneGraphTree(sceneGraph, this, this.root.getScene(0));
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
    final List<GroupModel> rootGroups = this.tree.getModel().getGroups();
    final ConfigurationModel configuration = this.root.getConfiguration(0);
    
    this.manager.clearObjectGroups();
    this.renderer.setRootGroups(rootGroups, this.manager);
    this.renderer.setConfiguration(configuration);
  }

  /**
   * キャンバスを生成します。
   * 
   * @param parent 親
   */
  private void createModelCanvas(Composite parent) {
    this.renderer = new JoglObjectRenderer();
    this.manager = new ObjectGroupManager();
    
    final Frame frame = SWT_AWT.new_Frame(parent);
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
  public void setIsChanged(final boolean isChanged) {
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