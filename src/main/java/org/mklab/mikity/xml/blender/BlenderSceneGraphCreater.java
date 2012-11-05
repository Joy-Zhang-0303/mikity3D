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
   * @param g グループ
   * @param argScene シーン
   */
  public void checkGroupName(Group g, Group argScene) {
    ArrayList<Group> group = g.loadGroupAsReference();
    for (int i = 0; i < group.size(); i++) {
      if (group.get(i).loadName().equals(argScene.loadName())) {
        ArrayList<XMLTrianglePolygon> triangleList = g.loadGroups()[i].loadXMLTrianglePolygonAsReference();
        ArrayList<XMLQuadPolygon> quadList = g.loadGroups()[i].loadXMLQuadPolygonAsReference();
        addTrianglePolygonList(argScene, triangleList);
        addQuadPolygonList(argScene, quadList);
      }
    }

    ArrayList<Group> child = argScene.loadGroupAsReference();
    if (child != null) {
      for (int i = 0; i < child.size(); i++) {
        checkGroupName(g, child.get(i));
      }
    }
    this.scene = argScene;
  }

  /**
   * @param argScene
   * @param triangleList
   */
  private void addTrianglePolygonList(Group argScene, ArrayList<XMLTrianglePolygon> triangleList) {
    for (int k = 0; k < triangleList.size(); k++) {
      argScene.addXMLTrianglePolygon(triangleList.get(k));
    }
  }

  /**
   * @param argScene
   * @param quadList
   */
  private void addQuadPolygonList(Group argScene, ArrayList<XMLQuadPolygon> quadList) {
    for (int k = 0; k < quadList.size(); k++) {
      argScene.addXMLQuadPolygon(quadList.get(k));
    }
  }

  /**
   * @return scene
   */
  public Group getScene() {
    return this.scene;
  }
}
