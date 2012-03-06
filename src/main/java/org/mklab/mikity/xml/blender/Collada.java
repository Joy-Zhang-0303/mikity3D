/*
 * $Id: Collada.java,v 1.6 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.mklab.mikity.xml.model.Group;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(ルート要素)
 * 
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
@XmlRootElement
public class Collada {

  @XmlElement
  private Library_geometries library_geometries;
  @XmlElement
  private Library_visual_scenes library_visual_scenes;

  /**
   * コンストラクタ
   */
  public Collada() {
    this.library_visual_scenes = new Library_visual_scenes();
    this.library_geometries = new Library_geometries();
  }

  /**
   * Blenderデータから作成したシーングラフのグループを返す
   * 
   * @return　g　Blenderのポリゴンをまとめたグループ
   */
  public Group getBlenderPolygonGroup() {
    this.library_visual_scenes.createMatrix();
    this.library_geometries.setLibraryVisualScenes(this.library_visual_scenes);
    return this.library_geometries.getBlenderPolygonGroup();
  }
}
