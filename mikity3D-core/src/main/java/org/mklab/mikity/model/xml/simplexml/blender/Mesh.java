/*
 * $Id: Mesh.java,v 1.7 2008/02/03 04:25:00 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.Location;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;
import org.mklab.mikity.util.Matrix4;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Mesh要素) モデルの頂点座標やどの頂点を使用してポリゴンを作成しているかを取得する。
 * 
 * @author SHOGO
 * @version $Revision: 1.7 $. 2007/11/30
 */
public class Mesh {

  @ElementList
  private List<Source> source;
  @Element
  private Polygons polygons;
  @Element
  private Triangle triangles;
  @Element
  private Polylist polylist;

  /**
   * ポリゴンをまとめるためのグループ
   */
  private Group blenderGroup;

  private Matrix4 matrix;

  /**
   * コンストラクタ
   */
  public Mesh() {
    this.source = new ArrayList<Source>();
    this.polygons = new Polygons();
    this.triangles = new Triangle();
    this.polylist = new Polylist();
    this.blenderGroup = new Group();
    this.matrix = new Matrix4(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * Blenderデータから作成したポリゴンをまとめたグループ
   * 
   * @return　Blenderデータから作成したポリゴンをまとめたグループ
   */
  public Group getBlenderPolygonGroup() {
    createBlenderPolygon();
    return this.blenderGroup;
  }

  /**
   * 頂点座標を頂点の組み合わせからポリゴンを作成する。 作成したポリゴンはグループに追加する。
   */
  private void createBlenderPolygon() {
    final List<Location> vertexLocations = this.source.get(0).getVertexLocation();
    final List<Location> normalVector = this.source.get(1).getNormalLocation();
    List<int[]> indexNumber = this.polygons.getIndexNumber();

    if (this.polylist.loadP() == null) {
      this.matrix.setElement(0, 3, 0.0f);
      this.matrix.setElement(1, 3, 0.0f);
      this.matrix.setElement(2, 3, 0.0f);
    }
    for (int n = 0; n < indexNumber.size(); n++) {
      if (indexNumber.get(n).length == 3) {
        XMLTrianglePolygon triangle = new XMLTrianglePolygon();
        triangle.setPointLocations(vertexLocations.get(indexNumber.get(n)[0]), vertexLocations.get(indexNumber.get(n)[1]), vertexLocations.get(indexNumber.get(n)[2]));
        triangle.setMatrix(this.matrix);
        triangle.setNormalVector(normalVector.get(n));
        this.blenderGroup.addXMLTrianglePolygon(triangle);
      } else if (indexNumber.get(n).length == 4) {
        XMLQuadPolygon quad = new XMLQuadPolygon();
        quad.setPointLocations(vertexLocations.get(indexNumber.get(n)[0]), vertexLocations.get(indexNumber.get(n)[1]), vertexLocations.get(indexNumber.get(n)[2]),
            vertexLocations.get(indexNumber.get(n)[3]));
        quad.setMatrix(this.matrix);
        quad.setNormalVector(normalVector.get(n));
        this.blenderGroup.addXMLQuadPolygon(quad);
      }
    }
    if (this.polylist.loadP() != null) {
      indexNumber = this.polylist.getPolylistIndex();
      for (int n = 0; n < indexNumber.size(); n++) {
        if (indexNumber.get(n).length == 3) {
          XMLTrianglePolygon triangle = new XMLTrianglePolygon();
          triangle.setPointLocations(vertexLocations.get(indexNumber.get(n)[0]), vertexLocations.get(indexNumber.get(n)[1]), vertexLocations.get(indexNumber.get(n)[2]));
          triangle.setMatrix(this.matrix);
          triangle.setNormalVector(normalVector.get(n));
          this.blenderGroup.addXMLTrianglePolygon(triangle);
        } else if (indexNumber.get(n).length == 4) {
          XMLQuadPolygon quad = new XMLQuadPolygon();
          quad.setPointLocations(vertexLocations.get(indexNumber.get(n)[0]), vertexLocations.get(indexNumber.get(n)[1]), vertexLocations.get(indexNumber.get(n)[2]),
              vertexLocations.get(indexNumber.get(n)[3]));
          quad.setMatrix(this.matrix);
          quad.setNormalVector(normalVector.get(n));
          this.blenderGroup.addXMLQuadPolygon(quad);
        }
      }
    }
    if (this.triangles.loadP() != null) {
      indexNumber = this.triangles.getTriangleIndex();
      for (int n = 0; n < indexNumber.size(); n++) {
        XMLTrianglePolygon triangle = new XMLTrianglePolygon();
        triangle.setPointLocations(vertexLocations.get(indexNumber.get(n)[0]), vertexLocations.get(indexNumber.get(n)[1]), vertexLocations.get(indexNumber.get(n)[2]));
        triangle.setMatrix(this.matrix);
        triangle.setNormalVector(normalVector.get(n));
        this.blenderGroup.addXMLTrianglePolygon(triangle);
      }
    }
  }

  /**
   * @param library_visual_scenes ノード関連
   * @param name 名前
   */
  public void setLibraryVisualScenes(LibraryVisualScenes library_visual_scenes, String name) {
    final List<String> nameList = library_visual_scenes.getNodeNames();
    final List<Matrix4> matrixList = library_visual_scenes.getMatrices();
    for (int i = 0; i < nameList.size(); i++) {
      if (nameList.get(i) != null && nameList.get(i).equals(name)) {
        this.matrix = matrixList.get(i);
      }
    }
  }

}
