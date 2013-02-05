/*
 * $Id: BlenderSceneGraphCreater.java,v 1.1 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;

import java.util.List;

import org.mklab.mikity.model.xml.jaxb.model.Group;
import org.mklab.mikity.model.xml.jaxb.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.jaxb.model.XMLTrianglePolygon;


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
   * @param group グループ
   * @param argScene シーン
   */
  public void checkGroupName(Group group, Group argScene) {
    final List<Group> groups = group.getGroupAsReference();
    for (int i = 0; i < groups.size(); i++) {
      if (groups.get(i).loadName().equals(argScene.loadName())) {
        final List<XMLTrianglePolygon> trianglePolygons = group.getGroups()[i].getXMLTrianglePolygonAsReference();
        final List<XMLQuadPolygon> quadPolygons = group.getGroups()[i].getXMLQuadPolygonAsReference();
        addTrianglePolygons(argScene, trianglePolygons);
        addQuadPolygons(argScene, quadPolygons);
      }
    }

    final List<Group> child = argScene.getGroupAsReference();
    if (child != null) {
      for (int i = 0; i < child.size(); i++) {
        checkGroupName(group, child.get(i));
      }
    }
    this.scene = argScene;
  }

  /**
   * @param argScene
   * @param trianglePolygons
   */
  private void addTrianglePolygons(Group argScene, List<XMLTrianglePolygon> trianglePolygons) {
    for (int k = 0; k < trianglePolygons.size(); k++) {
      argScene.addXMLTrianglePolygon(trianglePolygons.get(k));
    }
  }

  /**
   * @param argScene
   * @param quadPolygons
   */
  private void addQuadPolygons(Group argScene, List<XMLQuadPolygon> quadPolygons) {
    for (int k = 0; k < quadPolygons.size(); k++) {
      argScene.addXMLQuadPolygon(quadPolygons.get(k));
    }
  }

  /**
   * @return scene
   */
  public Group getScene() {
    return this.scene;
  }
}
