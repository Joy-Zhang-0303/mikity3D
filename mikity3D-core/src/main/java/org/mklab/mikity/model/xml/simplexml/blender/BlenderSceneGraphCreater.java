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
 * @author koga
 * @version $Revision$, 2008/08/10
 */
public class BlenderSceneGraphCreater {

  private Group scene;

  /**
   * 新しく生成された<code>BlenderSceneGraphCreater</code>オブジェクトを初期化します。
   */
  public BlenderSceneGraphCreater() {
    this.scene = new Group();
  }

  /**
   * @param rootGroup グループ
   * @param argScene シーン
   */
  public void checkGroupName(Group rootGroup, Group argScene) {
    final List<Group> groups = rootGroup.getGroupAsReference();
    
    for (int i = 0; i < groups.size(); i++) {
      if (groups.get(i).getName().equals(argScene.getName())) {
        final List<XMLTrianglePolygon> trianglePolygons = rootGroup.getGroups()[i].getXMLTrianglePolygonAsReference();
        final List<XMLQuadPolygon> quadPolygons = rootGroup.getGroups()[i].getXMLQuadPolygonAsReference();
        addTrianglePolygons(argScene, trianglePolygons);
        addQuadPolygons(argScene, quadPolygons);
      }
    }

    final List<Group> children = argScene.getGroupAsReference();
    if (children != null) {
      for (final Group child : children) {
        checkGroupName(rootGroup, child);
      }
    }
    this.scene = argScene;
  }

  /**
   * @param argScene
   * @param polygons
   */
  private void addTrianglePolygons(Group argScene, List<XMLTrianglePolygon> polygons) {
    for (final XMLTrianglePolygon polygon : polygons) {
      argScene.addXMLTrianglePolygon(polygon);
    }
  }

  /**
   * @param argScene
   * @param polygons
   */
  private void addQuadPolygons(Group argScene, List<XMLQuadPolygon> polygons) {
    for (final XMLQuadPolygon polygon : polygons) {
      argScene.addXMLQuadPolygon(polygon);
    }
  }

  /**
   * @return scene
   */
  public Group getScene() {
    return this.scene;
  }
}
