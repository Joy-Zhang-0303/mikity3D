/*
 * $Id: BlenderSceneGraphCreater.java,v 1.1 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.util.ArrayList;

import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


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
   * @param g
   * @param scene
   */
  public void checkGroupName(Group g, Group scene) {
    ArrayList<Group> group = g.loadGroupAsReference();
    for (int i = 0; i < group.size(); i++) {
      if (group.get(i).loadName().equals(scene.loadName())) {
        ArrayList<XMLTrianglePolygon> triangleList = g.loadGroup()[i].loadXMLTrianglePolygonAsReference();
        ArrayList<XMLQuadPolygon> quadList = g.loadGroup()[i].loadXMLQuadPolygonAsReference();
        addTrianglePolygonList(scene, triangleList);
        addQuadPolygonList(scene, quadList);
      }
    }

    ArrayList<Group> child = scene.loadGroupAsReference();
    if (child != null) {
      for (int i = 0; i < child.size(); i++) {
        checkGroupName(g, child.get(i));
      }
    }
    this.scene = scene;
  }

  /**
   * @param scene
   * @param triangleList
   */
  private void addTrianglePolygonList(Group scene, ArrayList<XMLTrianglePolygon> triangleList) {
    for (int k = 0; k < triangleList.size(); k++) {
      scene.addXMLTrianglePolygon(triangleList.get(k));
    }
  }

  /**
   * @param scene
   * @param quadList
   */
  private void addQuadPolygonList(Group scene, ArrayList<XMLQuadPolygon> quadList) {
    for (int k = 0; k < quadList.size(); k++) {
      scene.addXMLQuadPolygon(quadList.get(k));
    }
  }

  /**
   * @return
   */
  public Group getScene() {
    return this.scene;
  }
}
