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
package org.mklab.mikity.swt.gui.editor;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.mklab.mikity.swt.gui.JoglModeler;
import org.mklab.mikity.swt.gui.SceneGraphTree;


/**
 * モデルのプロパティを編集するエディタを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/09/12
 */
public class ModelPropertyEditor {
  /** */
  private JoglModeler modeler;
  
  /** シーングラフツリー。 */
  private SceneGraphTree tree;
  
  /** */
  private Composite parent;
  
  private ModelEditor editor;
  
  private Object target;
  
  /**
   * 新しく生成された<code>ModelPropertyEditor</code>オブジェクトを初期化します。
   * @param parent 親
   * @param modeler モデラー
   * @param tree シーングラフツリー
   */
  public ModelPropertyEditor(Composite parent, JoglModeler modeler, SceneGraphTree tree) {
    this.parent = parent;
    this.modeler = modeler;
    this.tree = tree;

    this.parent.setLayout(new GridLayout(1, true));
    this.editor = new GroupEditor(this.parent, this.modeler.getSelectedGroup(), true, null, this.modeler);
  }
  
  /**
   * 編集のターゲットを設定します。
   * 
   * @param target 編集のターゲット
   */
  public void setTarget(Object target) {
    if (this.target == target) {
      return;
    }
    
    this.target = target;
    
    if (this.editor != null) {
      for (final Control child : this.parent.getChildren()) {
        child.dispose();
      }
    }
    
    this.editor = ModelEditorFactory.create(this.target, this.parent, this.modeler.getSelectedGroup(), this.tree, this.modeler);
    
    this.parent.layout();
  }
  
  /**
   * エディタを更新し(モデルの変化をエディタに反映させ)ます。 
   */
  public void updateEditor() {
    this.editor.updateEditor();
  }
}
