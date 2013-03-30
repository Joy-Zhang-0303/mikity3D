/*
 * $Id: Library_geometries.java,v 1.6 2008/02/03 04:25:00 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.simpleframework.xml.ElementList;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Library_geometries要素)
 * 
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
public class LibraryGeometries {

  @ElementList
  private List<Geometry> geometry;

  private Group scene;

  private BlenderSceneGraphCreater creater;

  /**
   * コンストラクタ
   */
  public LibraryGeometries() {
    this.geometry = new ArrayList<Geometry>();
    this.creater = new BlenderSceneGraphCreater();
  }

  /**
   * Blenderデータから作成したポリゴンをまとめたグループをまとめて返す
   * 
   * @return　Blenderデータによるポリゴンをまとめたグループ一覧
   */
  public Group getBlenderPolygonGroup() {
    Group group = new Group();
    for (int i = 0; i < this.geometry.size(); i++) {
      group.addGroup(this.geometry.get(i).getBlenderPolygonGroup());
    }

    if (this.scene.getGroupSize() != 0) {
      this.creater.checkGroupName(group, this.scene);
      group = this.creater.getScene();
    }

    return group;
  }

  /**
   * @param libraryVisualScenes ノード関連
   */
  public void setLibraryVisualScenes(LibraryVisualScenes libraryVisualScenes) {
    this.scene = libraryVisualScenes.getScene();
    for (int i = 0; i < this.geometry.size(); i++) {
      this.geometry.get(i).setLibraryVisualScenes(libraryVisualScenes);
    }
  }
}
