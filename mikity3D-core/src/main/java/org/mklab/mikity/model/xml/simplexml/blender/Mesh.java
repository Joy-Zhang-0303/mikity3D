/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadrangleModel;
import org.mklab.mikity.model.xml.simplexml.model.TriangleModel;
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
  private GroupModel blenderGroup;

  private Matrix4 matrix;

  /**
   * コンストラクタ
   */
  public Mesh() {
    this.sources = new ArrayList<>();
    this.polygons = new Polygons();
    this.triangles = new Triangle();
    this.polylist = new Polylist();
    this.blenderGroup = new GroupModel();
    this.matrix = new Matrix4(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * Blenderデータから作成したポリゴンをまとめたグループを返します。
   * 
   * @return　Blenderデータから作成したポリゴンをまとめたグループ
   */
  public GroupModel getBlenderPolygonGroup() {
    createBlenderPolygon();
    return this.blenderGroup;
  }

  /**
   * 頂点座標を頂点の組み合わせからポリゴンを作成する。 作成したポリゴンはグループに追加します。
   */
  private void createBlenderPolygon() {
    final List<VertexModel> vertexLocations = this.sources.get(0).getVertexLocation();
    //final List<VertexModel> normalVector = this.sources.get(1).getNormalLocation();

    if (this.polylist.getP() == null) {
      this.matrix.setElement(0, 3, 0.0f);
      this.matrix.setElement(1, 3, 0.0f);
      this.matrix.setElement(2, 3, 0.0f);
    }
    
    final List<int[]> indexNumber = this.polygons.getIndexNumber();
    for (int i = 0; i < indexNumber.size(); i++) {
      final int[] index = indexNumber.get(i);
      if (index.length == 3) {
        final TriangleModel triangle = new TriangleModel();
        triangle.setVertices(vertexLocations.get(index[0]), vertexLocations.get(index[1]), vertexLocations.get(index[2]));
        //triangle.setMatrix(this.matrix);
        //triangle.setNormalVector(normalVector.get(i));
        this.blenderGroup.add(triangle);
      } else if (index.length == 4) {
        final QuadrangleModel quad = new QuadrangleModel();
        quad.setVertices(vertexLocations.get(index[0]), vertexLocations.get(index[1]), vertexLocations.get(index[2]), vertexLocations.get(index[3]));
        //quad.setMatrix(this.matrix);
        //quad.setNormalVector(normalVector.get(i));
        this.blenderGroup.add(quad);
      }
    }

    if (this.polylist.getP() != null) {
      final List<int[]> indexNumber2 = this.polylist.getPolylistIndices();
      for (int i = 0; i < indexNumber2.size(); i++) {
        int[] index = indexNumber2.get(i);
        if (index.length == 3) {
          TriangleModel triangle = new TriangleModel();
          triangle.setVertices(vertexLocations.get(index[0]), vertexLocations.get(index[1]), vertexLocations.get(index[2]));
          //triangle.setMatrix(this.matrix);
          //triangle.setNormalVector(normalVector.get(i));
          this.blenderGroup.add(triangle);
        } else if (index.length == 4) {
          QuadrangleModel quad = new QuadrangleModel();
          quad.setVertices(vertexLocations.get(index[0]), vertexLocations.get(index[1]), vertexLocations.get(index[2]), vertexLocations.get(index[3]));
          //quad.setMatrix(this.matrix);
          //quad.setNormalVector(normalVector.get(i));
          this.blenderGroup.add(quad);
        }
      }
    }

    if (this.triangles.getP() != null) {
      final List<int[]> indexNumber3 = this.triangles.getTriangleIndices();
      for (int i = 0; i < indexNumber3.size(); i++) {
        final TriangleModel triangle = new TriangleModel();
        final int[] index = indexNumber3.get(i);
        triangle.setVertices(vertexLocations.get(index[0]), vertexLocations.get(index[1]), vertexLocations.get(index[2]));
        //triangle.setMatrix(this.matrix);
        //triangle.setNormalVector(normalVector.get(i));
        this.blenderGroup.add(triangle);
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
