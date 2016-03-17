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
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.util.Matrix4;
import org.simpleframework.xml.Element;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(LibraryVisualScene要素)です。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/11/30
 */
public class LibraryVisualScenes {

  @Element
  private VisualScene visualScene;

  /**
   * 新しく生成された<code>LibraryVisualScenes</code>オブジェクトを初期化します。
   */
  public LibraryVisualScenes() {
    this.visualScene = new VisualScene();
  }

  /**
   * 各ノードの名前が追加されているリストを返します。
   * 
   * @return　各ノードの名前が追加されているリスト
   */
  public List<String> getNodeNames() {
    return this.visualScene.getNodeNames();
  }

  /**
   * 変換行列が追加されているリストを返します。
   * 
   * @return　変換行列が追加されているリスト
   */
  public List<Matrix4> getMatrices() {
    return this.visualScene.getTransformMatrices();
  }

  /**
   * 変換行列を生成します。
   */
  public void createMatrix() {
    this.visualScene.createTransformMatrix();
  }

  /**
   * @return scene
   */
  public GroupModel getScene() {
    return this.visualScene.getScene();
  }
}
