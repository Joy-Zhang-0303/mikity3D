/*
 * $Id: Library_geometries.java,v 1.6 2008/02/03 04:25:00 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
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
      group.addGroup(this.geometries.get(i).getBlenderPolygonGroup());
    }

    if (this.scene.getGroups().length != 0) {
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
