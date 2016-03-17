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
import org.mklab.mikity.model.xml.simplexml.model.NullModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;

/**
 * Blenderのシーングラフの生成器です。
 * @author koga
 * @version $Revision$, 2008/08/10
 */
public class BlenderSceneGraphCreater {

  private GroupModel sceneGraph;

  /**
   * 新しく生成された<code>BlenderSceneGraphCreater</code>オブジェクトを初期化します。
   */
  public BlenderSceneGraphCreater() {
    this.sceneGraph = new GroupModel();
  }

  /**
   * @param rootGroup グループ
   * @param scene シーン
   */
  public void checkGroupName(GroupModel rootGroup, GroupModel scene) {
    final List<GroupModel> groups = rootGroup.getGroups();
    
    for (int i = 0; i < groups.size(); i++) {
      if (groups.get(i).getName().equals(scene.getName())) {
        final List<ObjectModel> primitives = rootGroup.getGroups().get(i).getObjects();
        addPrimitives(scene, primitives);
        
//        final List<TrianglePolygonModel> trianglePolygons = rootGroup.getGroups().get(i).getTrianglePolygons();
//        final List<QuadPolygonModel> quadPolygons = rootGroup.getGroups().get(i).getQuadPolygons();
//        addTrianglePolygons(scene, trianglePolygons);
//        addQuadPolygons(scene, quadPolygons);
      }
    }

    final List<GroupModel> children = scene.getGroups();
    if (children != null) {
      for (final GroupModel child : children) {
        checkGroupName(rootGroup, child);
      }
    }
    this.sceneGraph = scene;
  }

//  /**
//   * @param scene
//   * @param polygons
//   */
//  private void addTrianglePolygons(GroupModel scene, List<TrianglePolygonModel> polygons) {
//    for (final TrianglePolygonModel polygon : polygons) {
//      scene.add(polygon);
//    }
//  }
//
//  /**
//   * @param scene
//   * @param polygons
//   */
//  private void addQuadPolygons(GroupModel scene, List<QuadPolygonModel> polygons) {
//    for (final QuadPolygonModel polygon : polygons) {
//      scene.add(polygon);
//    }
//  }
  
  /**
   * @param scene
   * @param primitives
   */
  private void addPrimitives(GroupModel scene, List<ObjectModel> primitives) {
    for (final ObjectModel primitive : primitives) {
      if (primitive instanceof NullModel) {
        continue;
      }
      scene.add(primitive);
    }
  }

  /**
   * @return scene
   */
  public GroupModel getSceneGraph() {
    return this.sceneGraph;
  }
}
