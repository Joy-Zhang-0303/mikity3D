/*
 * $Id: Collada.java,v 1.6 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * COLLADAデータを読み込むためのクラス(ルート要素)です。
 * 
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
@Root
public class Collada {

  @Element
  private LibraryGeometries library_geometries;
  @Element
  private LibraryVisualScenes library_visual_scenes;

  /**
   * コンストラクタ
   */
  public Collada() {
    this.library_visual_scenes = new LibraryVisualScenes();
    this.library_geometries = new LibraryGeometries();
  }

  /**
   * Colladaデータから作成したシーングラフのグループを返します。
   * 
   * @return　g　のポリゴンをまとめたグループ
   */
  public Group getColladaPolygonGroup() {
    this.library_visual_scenes.createMatrix();
    this.library_geometries.setLibraryVisualScenes(this.library_visual_scenes);
    return this.library_geometries.getBlenderPolygonGroup();
  }
}
