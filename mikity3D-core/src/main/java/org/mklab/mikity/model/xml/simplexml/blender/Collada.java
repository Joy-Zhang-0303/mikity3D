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

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * COLLADAデータを読み込むためのクラス(ルート要素)です。
 * 
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
@Root
public class Collada {

  @Element
  private LibraryGeometries libraryGeometries;
  @Element
  private LibraryVisualScenes libraryVisualScenes;

  /**
   * 新しく生成された<code>Collada</code>オブジェクトを初期化します。
   */
  public Collada() {
    this.libraryVisualScenes = new LibraryVisualScenes();
    this.libraryGeometries = new LibraryGeometries();
  }

  /**
   * Colladaデータから作成したシーングラフのグループを返します。
   * 
   * @return　ポリゴンをまとめたグループ
   */
  public GroupModel getColladaPolygonGroup() {
    this.libraryVisualScenes.createMatrix();
    this.libraryGeometries.setLibraryVisualScenes(this.libraryVisualScenes);
    return this.libraryGeometries.getBlenderPolygonGroup();
  }
}
