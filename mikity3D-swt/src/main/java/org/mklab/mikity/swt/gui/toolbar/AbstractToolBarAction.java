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
package org.mklab.mikity.swt.gui.toolbar;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.swt.gui.JoglModeler;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * ツールバーのアクションを表す抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/07/22
 */
public abstract class AbstractToolBarAction extends Action {
  /**　モデリングウィンドウ。   */
  ModelingWindow window;
  
  /** モデラー。 */
  JoglModeler modeler;
  
  /**
   * 新しく生成された<code>AbstractToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   * @param name 名前
   */
  public AbstractToolBarAction(ModelingWindow window, String name) {
    this.window = window;
    setText(name);
  }
  
  /**
   * モデラーを設定します。
   * @param modeler モデラー
   */
  public void setModeler(JoglModeler modeler) {
    this.modeler = modeler;
  }
  
  /**
   * 選択されているグループを設定します。
   * 
   * @param selectedGroup 選択されているグループ
   */
  public void setSelectedGroup(GroupModel selectedGroup) { 
    this.modeler.setSelectedGroup(selectedGroup);
  }
  
  /**
   * 選択されているオブジェクトを設定します。
   * 
   * @param selectedObject 選択されているオブジェクト
   */
  public void setSelectedObject(Object selectedObject) { 
    this.modeler.setSelectedObject(selectedObject);
  }

  /**
   * 追加したオブジェクトの情報をキャンバスとツリーに追加します。
   */
  protected void update() {
    this.modeler.bindModelToTree();
    this.modeler.updateRenderer();
    this.modeler.updatePropertyEditor();
  }
}
