/*
 * $Id: BlenderSceneGraphCreater.java,v 1.1 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;

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
        final List<TrianglePolygonModel> trianglePolygons = rootGroup.getGroups().get(i).getTrianglePolygons();
        final List<QuadPolygonModel> quadPolygons = rootGroup.getGroups().get(i).getQuadPolygons();
        addTrianglePolygons(scene, trianglePolygons);
        addQuadPolygons(scene, quadPolygons);
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

  /**
   * @param scene
   * @param polygons
   */
  private void addTrianglePolygons(GroupModel scene, List<TrianglePolygonModel> polygons) {
    for (final TrianglePolygonModel polygon : polygons) {
      scene.add(polygon);
    }
  }

  /**
   * @param scene
   * @param polygons
   */
  private void addQuadPolygons(GroupModel scene, List<QuadPolygonModel> polygons) {
    for (final QuadPolygonModel polygon : polygons) {
      scene.add(polygon);
    }
  }

  /**
   * @return scene
   */
  public GroupModel getSceneGraph() {
    return this.sceneGraph;
  }
}
