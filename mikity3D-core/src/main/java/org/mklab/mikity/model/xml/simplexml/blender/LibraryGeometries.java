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

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.simpleframework.xml.ElementList;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Library_geometries要素)です。
 * 
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
public class LibraryGeometries {

  @ElementList
  private List<Geometry> geometries;

  private GroupModel scene;

  private BlenderSceneGraphCreater creater;

  /**
   * コンストラクタ
   */
  public LibraryGeometries() {
    this.geometries = new ArrayList<>();
    this.creater = new BlenderSceneGraphCreater();
  }

  /**
   * Blenderデータから作成したポリゴンをまとめたグループをまとめて返す
   * 
   * @return　Blenderデータによるポリゴンをまとめたグループ一覧
   */
  public GroupModel getBlenderPolygonGroup() {
    GroupModel group = new GroupModel();
    for (int i = 0; i < this.geometries.size(); i++) {
      group.add(this.geometries.get(i).getBlenderPolygonGroup());
    }

    if (this.scene.getGroups().size() != 0) {
      this.creater.checkGroupName(group, this.scene);
      group = this.creater.getSceneGraph();
    }

    return group;
  }

  /**
   * @param libraryVisualScenes ノード関連
   */
  public void setLibraryVisualScenes(LibraryVisualScenes libraryVisualScenes) {
    this.scene = libraryVisualScenes.getScene();
    for (int i = 0; i < this.geometries.size(); i++) {
      this.geometries.get(i).setLibraryVisualScenes(libraryVisualScenes);
    }
  }
}
