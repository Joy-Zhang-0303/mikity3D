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
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Mesh要素)です。
 * 
 * モデルの頂点座標やどの頂点を使用してポリゴンを作成しているかを取得します。
 * 
 * @author SHOGO
 * @version $Revision: 1.7 $. 2007/11/30
 */
public class Mesh {

  @ElementList
  private List<Source> sources;
  @Element
  private Polygons polygons;
  @Element
  private Triangle triangles;
  @Element
  private Polylist polylist;

  /** ポリゴンをまとめるためのグループ */
  private Group blenderGroup;

  private Matrix4 matrix;

  /**
   * コンストラクタ
   */
  public Mesh() {
    this.sources = new ArrayList<>();
    this.polygons = new Polygons();
    this.triangles = new Triangle();
    this.polylist = new Polylist();
    this.blenderGroup = new Group();
    this.matrix = new Matrix4(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * Blenderデータから作成したポリゴンをまとめたグループを返します。
   * 
   * @return　Blenderデータから作成したポリゴンをまとめたグループ
   */
  public Group getBlenderPolygonGroup() {
    createBlenderPolygon();
    return this.blenderGroup;
  }

  /**
   * 頂点座標を頂点の組み合わせからポリゴンを作成する。 作成したポリゴンはグループに追加します。
   */
  private void createBlenderPolygon() {
    final List<Location> vertexLocations = this.sources.get(0).getVertexLocation();
    final List<Location> normalVector = this.sources.get(1).getNormalLocation();

    if (this.polylist.getP() == null) {
      this.matrix.setElement(0, 3, 0.0f);
      this.matrix.setElement(1, 3, 0.0f);
      this.matrix.setElement(2, 3, 0.0f);
    }
    
    final List<int[]> indexNumber = this.polygons.getIndexNumber();
    for (int i = 0; i < indexNumber.size(); i++) {
      final int[] index = indexNumber.get(i);
      if (index.length == 3) {
        final XMLTrianglePolygon triangle = new XMLTrianglePolygon();
        triangle.setPointLocations(vertexLocations.get(index[0]), vertexLocations.get(index[1]), vertexLocations.get(index[2]));
        triangle.setMatrix(this.matrix);
        triangle.setNormalVector(normalVector.get(i));
        this.blenderGroup.addXMLTrianglePolygon(triangle);
      } else if (index.length == 4) {
        final XMLQuadPolygon quad = new XMLQuadPolygon();
        quad.setPointLocations(vertexLocations.get(index[0]), vertexLocations.get(index[1]), vertexLocations.get(index[2]), vertexLocations.get(index[3]));
        quad.setMatrix(this.matrix);
        quad.setNormalVector(normalVector.get(i));
        this.blenderGroup.addXMLQuadPolygon(quad);
      }
    }

    if (this.polylist.getP() != null) {
      final List<int[]> indexNumber2 = this.polylist.getPolylistIndices();
      for (int i = 0; i < indexNumber2.size(); i++) {
        int[] index = indexNumber2.get(i);
        if (index.length == 3) {
          XMLTrianglePolygon triangle = new XMLTrianglePolygon();
          triangle.setPointLocations(vertexLocations.get(index[0]), vertexLocations.get(index[1]), vertexLocations.get(index[2]));
          triangle.setMatrix(this.matrix);
          triangle.setNormalVector(normalVector.get(i));
          this.blenderGroup.addXMLTrianglePolygon(triangle);
        } else if (index.length == 4) {
          XMLQuadPolygon quad = new XMLQuadPolygon();
          quad.setPointLocations(vertexLocations.get(index[0]), vertexLocations.get(index[1]), vertexLocations.get(index[2]), vertexLocations.get(index[3]));
          quad.setMatrix(this.matrix);
          quad.setNormalVector(normalVector.get(i));
          this.blenderGroup.addXMLQuadPolygon(quad);
        }
      }
    }

    if (this.triangles.getP() != null) {
      final List<int[]> indexNumber3 = this.triangles.getTriangleIndices();
      for (int i = 0; i < indexNumber3.size(); i++) {
        final XMLTrianglePolygon triangle = new XMLTrianglePolygon();
        final int[] index = indexNumber3.get(i);
        triangle.setPointLocations(vertexLocations.get(index[0]), vertexLocations.get(index[1]), vertexLocations.get(index[2]));
        triangle.setMatrix(this.matrix);
        triangle.setNormalVector(normalVector.get(i));
        this.blenderGroup.addXMLTrianglePolygon(triangle);
      }
    }
  }

  /**
   * @param libraryVisualScenes ノード関連
   * @param name 名前
   */
  public void setLibraryVisualScenes(LibraryVisualScenes libraryVisualScenes, String name) {
    final List<String> names = libraryVisualScenes.getNodeNames();
    final List<Matrix4> matries = libraryVisualScenes.getMatrices();
    for (int i = 0; i < names.size(); i++) {
      if (names.get(i) != null && names.get(i).equals(name)) {
        this.matrix = matries.get(i);
      }
    }
  }

}
