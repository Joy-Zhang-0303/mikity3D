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
  private LibraryGeometries libraryGeometries;
  @Element
  private LibraryVisualScenes libraryVisualScenes;

  /**
   * 新しく生成された<code>Collada</code>オブジェクトを初期化します。
   */
  public Collada() {
    this.libraryVisualScenes = new LibraryVisualScenes();
    this.libraryGeometries = new LibraryGeometries();
  }

  /**
   * Colladaデータから作成したシーングラフのグループを返します。
   * 
   * @return　ポリゴンをまとめたグループ
   */
  public Group getColladaPolygonGroup() {
    this.libraryVisualScenes.createMatrix();
    this.libraryGeometries.setLibraryVisualScenes(this.libraryVisualScenes);
    return this.libraryGeometries.getBlenderPolygonGroup();
  }
}
