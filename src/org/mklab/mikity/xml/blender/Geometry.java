/*
 * $Id: Geometry.java,v 1.6 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.mklab.mikity.xml.model.Group;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Geometry要素)
 * 
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
public class Geometry {

  @XmlAttribute
  private String name;
  @XmlElement
  private Mesh mesh;

  /**
   * コンストラクタ
   */
  public Geometry() {
    this.mesh = new Mesh();
  }

  /**
   * Blenderデータから作成したポリゴンをまとめたグループ
   * 
   * @return　group　Blenderデータからのポリゴンをまとめたグループ
   */
  public Group getBlenderPolygonGroup() {
    Group group = this.mesh.getBlenderPolygonGroup();
    if (this.name.indexOf("-G") != -1) {
      this.name = this.name.substring(0, this.name.indexOf("-G"));
    }
    group.setName(this.name);
    return group;
  }

  /**
   * @param library_visual_scenes
   */
  public void setLibraryVisualScenes(Library_visual_scenes library_visual_scenes) {
    if (this.name.indexOf("-G") != -1) {
      this.name = this.name.substring(0, this.name.indexOf("-G"));
    }
    this.mesh.setLibraryVisualScenes(library_visual_scenes, this.name);
  }

}
