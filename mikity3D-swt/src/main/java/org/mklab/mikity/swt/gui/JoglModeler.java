/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.swt.gui;

import java.awt.Frame;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.mklab.mikity.model.GroupObjectManager;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.swt.gui.editor.ModelPropertyEditor;
import org.mklab.mikity.swt.renderer.JoglObjectRenderer;


/**
 * モデラーを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.22 $.2004/12/03
 */
public class JoglModeler extends Composite {
  /** シーングラフツリー。 */
  SceneGraphTree tree;
  
  /** プロパティエディタ。 */
  ModelPropertyEditor editor;
  
  /** レンダラー。 */
  private JoglObjectRenderer renderer;
  /** オブジェクトグループのマネージャ。 */
  private GroupObjectManager manager;
  
  /** ルート。 */
  Mikity3DModel root;
  
  /** 変更されていればtrue。 */
  private boolean isChanged = false;

  /**
   * 新しく生成された<code>AbstractModeler</code>オブジェクトを初期化します。
   * @param composite コンポジット
   * @param root ルート
   */
  public JoglModeler(Composite composite, final Mikity3DModel root) {
    super(composite, SWT.None);
    this.root = root;
    this.setLayout(new GridLayout());
    this.setLayoutData(new GridData(GridData.FILL_BOTH));

    final SashForm sash = new SashForm(this, SWT.HORIZONTAL);
    sash.setLayoutData(new GridData(GridData.FILL_BOTH));
    sash.setLayout(new GridLayout());
    
    createSceneGraphComposite(sash);
    createCanvasComposite(sash);
    createModelPropertyEditor(sash);
    
    sash.setWeights(new int[]{25, 50, 25});
    
    updateRenderer();
    updatePropertyEditor();
  }

  /**
   * 3Dグラフィックスを表示するキャンバスを生成します。
   * 
   * @param parent 親コンポジット
   */
  private void createCanvasComposite(Composite parent) {
    final Composite composite = new Composite(parent, SWT.EMBEDDED | SWT.BORDER);
    composite.setLayout(new GridLayout());
    composite.setLayoutData(new GridData(GridData.FILL_BOTH));
    createModelCanvas(composite);
  }
  
  /**
   * シーングラフを表示するコンポジットを作成します。
   * 
   * @param parent 親コンポジット
   */
  private void createSceneGraphComposite(Composite parent) {
    final Composite composite = new Composite(parent, SWT.EMBEDDED | SWT.BORDER);
    composite.setLayout(new GridLayout());
    composite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
    
    final GridData data = new GridData();
    data.widthHint = 100;
    composite.setLayoutData(data);
    
    this.tree = new SceneGraphTree(composite, this, this.root.getScene(0));
  }
  
  private void createModelPropertyEditor(Composite parent) {
    final Composite composite = new Composite(parent, SWT.EMBEDDED | SWT.BORDER | SWT.SINGLE);
    composite.setLayout(new GridLayout());
    composite.setLayoutData(new GridData(GridData.FILL_VERTICAL));

    final GridData data = new GridData();
    data.widthHint = 100;
    composite.setLayoutData(data);
    
    this.editor = new ModelPropertyEditor(composite, this, this.tree);
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
    updatePropertyEditor();
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
   * プロパティエディタを更新します。
   */
  public void updatePropertyEditor() {
    final Object target = this.tree.getSelectedObject();
    this.editor.setTarget(target);
    this.editor.updateEditor();
  }

  /**
   * キャンバスを生成します。
   * 
   * @param parent 親
   */
  private void createModelCanvas(Composite parent) {
    this.renderer = new JoglObjectRenderer(this.root.getConfiguration(0));
    this.manager = new GroupObjectManager();
    
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