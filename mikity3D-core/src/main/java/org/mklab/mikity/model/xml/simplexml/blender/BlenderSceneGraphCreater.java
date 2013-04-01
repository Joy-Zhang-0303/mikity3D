/*
 * $Id: BlenderSceneGraphCreater.java,v 1.1 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;

/**
 * Blenderのシーングラフの生成器です。
 * @author koga
 * @version $Revision$, 2008/08/10
 */
public class BlenderSceneGraphCreater {

  private Group sceneGraph;

  /**
   * 新しく生成された<code>BlenderSceneGraphCreater</code>オブジェクトを初期化します。
   */
  public BlenderSceneGraphCreater() {
    this.sceneGraph = new Group();
  }

  /**
   * @param rootGroup グループ
   * @param scene シーン
   */
  public void checkGroupName(Group rootGroup, Group scene) {
    final List<Group> groups = rootGroup.getGroupsAsReference();
    
    for (int i = 0; i < groups.size(); i++) {
      if (groups.get(i).getName().equals(scene.getName())) {
        final List<XMLTrianglePolygon> trianglePolygons = rootGroup.getGroups()[i].getXMLTrianglePolygonsAsReference();
        final List<XMLQuadPolygon> quadPolygons = rootGroup.getGroups()[i].getXMLQuadPolygonsAsReference();
        addTrianglePolygons(scene, trianglePolygons);
        addQuadPolygons(scene, quadPolygons);
      }
    }

    final List<Group> children = scene.getGroupsAsReference();
    if (children != null) {
      for (final Group child : children) {
        checkGroupName(rootGroup, child);
      }
    }
    this.sceneGraph = scene;
  }

  /**
   * @param scene
   * @param polygons
   */
  private void addTrianglePolygons(Group scene, List<XMLTrianglePolygon> polygons) {
    for (final XMLTrianglePolygon polygon : polygons) {
      scene.addXMLTrianglePolygon(polygon);
    }
  }

  /**
   * @param scene
   * @param polygons
   */
  private void addQuadPolygons(Group scene, List<XMLQuadPolygon> polygons) {
    for (final XMLQuadPolygon polygon : polygons) {
      scene.addXMLQuadPolygon(polygon);
    }
  }

  /**
   * @return scene
   */
  public Group getSceneGraph() {
    return this.sceneGraph;
  }
}
