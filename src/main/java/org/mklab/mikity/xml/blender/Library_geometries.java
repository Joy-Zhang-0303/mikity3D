/*
 * $Id: Library_geometries.java,v 1.6 2008/02/03 04:25:00 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.xml.model.Group;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Library_geometries要素)
 * 
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
public class Library_geometries {

  @XmlElement
  private ArrayList<Geometry> geometry;

  private Group scene;

  private BlenderSceneGraphCreater bsgc;

  /**
   * コンストラクタ
   */
  public Library_geometries() {
    this.geometry = new ArrayList<Geometry>();
    this.bsgc = new BlenderSceneGraphCreater();
  }

  /**
   * Blenderデータから作成したポリゴンをまとめたグループをまとめて返す
   * 
   * @return　group　Blenderデータによるポリゴンをまとめたグループ一覧
   */
  public Group getBlenderPolygonGroup() {
    Group group = new Group();
    for (int i = 0; i < this.geometry.size(); i++) {
      group.addGroup(this.geometry.get(i).getBlenderPolygonGroup());
    }

    if (this.scene.loadGroupCount() != 0) {
      this.bsgc.checkGroupName(group, this.scene);
      group = this.bsgc.getScene();
    }

    return group;
  }

  /**
   * @param library_visual_scenes ノード関連
   */
  public void setLibraryVisualScenes(Library_visual_scenes library_visual_scenes) {
    this.scene = library_visual_scenes.getScene();
    for (int i = 0; i < this.geometry.size(); i++) {
      this.geometry.get(i).setLibraryVisualScenes(library_visual_scenes);
    }
  }
}
