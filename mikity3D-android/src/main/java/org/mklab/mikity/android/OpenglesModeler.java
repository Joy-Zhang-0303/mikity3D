/*
 * Created on 2015/1/20
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;

import org.mklab.mikity.android.model.SceneGraphTreeFragment;
import org.mklab.mikity.android.view.renderer.opengles.OpenglesObjectRenderer;
import org.mklab.mikity.model.GroupObjectManager;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

/**
 * モデラーを表すクラスです。
 */
public class OpenglesModeler {
  /** シーングラフツリー。 */
  private SceneGraphTreeFragment tree;
  /** レンダラー。 */
  private OpenglesObjectRenderer renderer;
  /** オブジェクトグループのマネージャ。 */
  private GroupObjectManager manager;
  
  /** ルート。 */
  private Mikity3DModel root;
  
  /** 変更されていればtrue。 */
  private boolean isChanged = false;

  /**
   * 新しく生成された<code>AbstractModeler</code>オブジェクトを初期化します。
   * @param renderer レンダラー
   */
  public OpenglesModeler(OpenglesObjectRenderer renderer) {
    this.renderer = renderer;
  }
  
  /**
   * オブジェクトグループマネージャ を設定します。
   * 
   * @param manager オブジェクトグループマネージャ 
   */
  public void setManager(GroupObjectManager manager) {
    this.manager = manager;
  }
  
  /**
   * モデルのルートを設定します。
   * 
   * @param root ルート
   */
  public void setRoot(Mikity3DModel root) {
    this.root = root;
  }
  
  /**
   * シーングラフツリーを設定します。
   * 
   * @param tree シーングラフツリー
   */
  public void setTree(SceneGraphTreeFragment tree) {
    this.tree = tree;
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
   * 選択されているグループを設定します。
   * 
   * @param selectedGroup 選択されているグループ
   */
  public void setSelectedGroup(GroupModel selectedGroup) {
    this.tree.setSelectedGroup(selectedGroup);
  }
  
  /**
   * 選択されているオブジェクトを設定します。
   * 
   * @param selectedObject 選択されているオブジェクト
   */
  public void setSelectedObject(Object selectedObject) {
    this.tree.setSelectedObject(selectedObject);
  }

  /**
   * モデルをシーングラフツリーにバインドし(結びつけ)ます。
   */
  public void bindModelToTree() {
    this.tree.bindModelToTree();
  }

  /**
   * RootGroupとConfigurationをCanvasに設定します。
   */
  public void updateRenderer() {
    final List<GroupModel> rootGroups = this.tree.getModel().getGroups();
    final ConfigurationModel configuration = this.root.getConfiguration(0);
    
    this.manager.clearGroupObjects();
    this.renderer.setRootGroups(rootGroups, this.manager);
    this.renderer.setConfiguration(configuration);
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
  public GroupModel getSelectedGroup() {
    return this.tree.getSelectedGroup();
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