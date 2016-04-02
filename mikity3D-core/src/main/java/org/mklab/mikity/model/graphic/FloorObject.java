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
package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.config.BaseCoordinateModel;


/**
 * グリッドオブジェクトを表すクラスです。
 */
public class FloorObject {

  /** オブジェクト。 */
  protected BaseCoordinateModel object;

  /** 頂点配列の参照位置(薄い色)。 */
  private int vertexPositionBright = 0;
  /** 法線ベクトル配列の参照位置(薄い色)。 */
  private int normalVectorPositionBright = 0;
  /** 頂点配列(薄い色)。 */
  private float vertexArrayBright[];
  /** 法線ベクトル配列(薄い色)。 */
  private float normalVectorArrayBright[];

  /** 頂点配列の参照位置(濃い色)。 */
  private int vertexPositionDark = 0;
  /** 法線ベクトル配列の参照位置(濃い色)。 */
  private int normalVectorPositionDark = 0;
  /** 頂点配列(濃い色)。 */
  private float vertexArrayDark[];
  /** 法線ベクトル配列(濃い色)。 */
  private float normalVectorArrayDark[];

  /**
   * 新しく生成された<code>GraphicPrimitive</code>オブジェクトを初期化します。
   * 
   * @param object オブジェクト
   */
  public FloorObject(BaseCoordinateModel object) {
    this.object = object;

    updateVertexArray();
  }

  /**
   * 頂点配列を更新します。
   */
  private void updateVertexArray() {
    final float floorDepth = this.object.getFloorDepth();
    final float floorWidth = this.object.getFloorWidth();
    final float gridSize = this.object.getGridSize();

    final int polygonSize = ((int)Math.floor(floorWidth / gridSize / 2) * 2) * ((int)Math.floor(floorDepth / gridSize / 2) * 2);

    initializeArraysBright(polygonSize);
    initializeArraysDark(polygonSize);

    final int numberX = (int)(floorDepth / gridSize);
    final int numberY = (int)(floorWidth / gridSize);

    for (int j = -numberY / 2; j < numberY / 2; j++)
      for (int i = -numberX / 2; i < numberX / 2; i++) {
        final float x = i * gridSize + gridSize / 2;
        final float y = j * gridSize + gridSize / 2;

        final float x0 = gridSize / 2 + x;
        final float y0 = -gridSize / 2 + y;
        final float z0 = 0;

        final float x1 = gridSize / 2 + x;
        final float y1 = gridSize / 2 + y;
        final float z1 = 0;

        final float x2 = -gridSize / 2 + x;
        final float y2 = gridSize / 2 + y;
        final float z2 = 0;

        final float x3 = gridSize / 2 + x;
        final float y3 = -gridSize / 2 + y;
        final float z3 = 0;

        final float x4 = -gridSize / 2 + x;
        final float y4 = gridSize / 2 + y;
        final float z4 = 0;

        final float x5 = -gridSize / 2 + x;
        final float y5 = -gridSize / 2 + y;
        final float z5 = 0;

        final float nx = 0;
        final float ny = 0;
        final float nz = 1;

        final float[][] vertices = new float[][] { {x0, y0, z0}, {x1, y1, z1}, {x2, y2, z2}, {x3, y3, z3}, {x4, y4, z4}, {x5, y5, z5}};
        final float[][] normalVector = new float[][] { {nx, ny, nz}, {nx, ny, nz}, {nx, ny, nz}, {nx, ny, nz}, {nx, ny, nz}, {nx, ny, nz}};

        if ((i + j) % 2 == 0) {
          appendVerticesDark(vertices);
          appendNormalVectorDark(normalVector);
        } else {
          appendVerticesBright(vertices);
          appendNormalVectorBright(normalVector);
        }
      }
  }

  // 薄い色

  /**
   * 頂点配列(薄い色)の長さを返します。
   * 
   * @return 頂点配列(薄い色)の長さ
   */
  public float[] getVertexArrayBright() {
    return this.vertexArrayBright;
  }

  /**
   * 頂点配列(薄い色)を返します。
   * 
   * @return 頂点配列(薄い色)
   */
  public int getVertexArrayLengthBright() {
    return this.vertexArrayBright.length;
  }

  /**
   * 与えられた頂点配列をこのオブジェクトの頂点配列(薄い色)に追加します。
   * 
   * @param vertices 頂点
   */
  protected void appendVerticesBright(float[] vertices) {
    for (int i = 0; i < vertices.length; i++) {
      this.vertexArrayBright[this.vertexPositionBright++] = vertices[i];
    }
  }

  /**
   * 頂点を頂点配列(薄い)に追加します。
   * 
   * @param vertices 頂点
   */
  protected void appendVerticesBright(float[][] vertices) {
    for (int i = 0; i < vertices.length; i++) {
      for (int j = 0; j < 3; j++) {
        this.vertexArrayBright[this.vertexPositionBright++] = vertices[i][j];
      }
    }
  }
  
  /**
   * 法線ベクトル配列(薄い色)の長さを返します。
   * 
   * @return 法線ベクトル配列(薄い色)の長さ
   */
  public float[] getNormalVectorArrayBright() {
    return this.normalVectorArrayBright;
  }

  /**
   * 与えられた法線ベクトルをこのオブジェクトの法線ベクトル配列(薄い色)に追加します。
   * 
   * @param normalVector 法線ベクトル
   */
  protected void appendNormalVectorBright(float[] normalVector) {
    for (int i = 0; i < normalVector.length; i++) {
      this.normalVectorArrayBright[this.normalVectorPositionBright++] = normalVector[i];
    }
  }

  /**
   * 三角形ポリゴンの法線ベクトルを法線ベクトル配列(薄い)に追加します。
   * 
   * @param normalVector 法線ベクトル
   */
  protected void appendNormalVectorBright(float[][] normalVector) {
    for (int i = 0; i < normalVector.length; i++) {
      for (int j = 0; j < 3; j++) {
        this.normalVectorArrayBright[this.normalVectorPositionBright++] = normalVector[i][j];
      }
    }
  }

  /**
   * 頂点配列と法線ベクトル配列(薄い色)を準備します。
   * 
   * @param polygonSize ポリゴンの数
   */
  protected void initializeArraysBright(int polygonSize) {
    this.vertexPositionBright = 0;
    this.normalVectorPositionBright = 0;
    if (this.vertexArrayBright == null || this.vertexArrayBright.length != polygonSize * 3 * 3) {
      this.vertexArrayBright = new float[polygonSize * 3 * 3];
    }
    if (this.normalVectorArrayBright == null || this.normalVectorArrayBright.length != polygonSize * 3 * 3) {
      this.normalVectorArrayBright = new float[polygonSize * 3 * 3];
    }
  }

  // 濃い色 

  /**
   * 頂点配列(濃い色)の長さを返します。
   * 
   * @return 頂点配列(濃い色)の長さ
   */
  public float[] getVertexArrayDark() {
    return this.vertexArrayDark;
  }

  /**
   * 頂点配列(濃い色)を返します。
   * 
   * @return 頂点配列(濃い色)
   */
  public int getVertexArrayLengthDark() {
    return this.vertexArrayDark.length;
  }

  /**
   * 与えられた頂点配列をこのオブジェクトの頂点配列(濃い色)に追加します。
   * 
   * @param vertices 頂点
   */
  protected void appendVerticesDark(float[] vertices) {
    for (int i = 0; i < vertices.length; i++) {
      this.vertexArrayDark[this.vertexPositionDark++] = vertices[i];
    }
  }

  /**
   * 頂点を頂点配列(濃い)に追加します。
   * 
   * @param vertices 頂点
   */
  protected void appendVerticesDark(float[][] vertices) {
    for (int i = 0; i < vertices.length; i++) {
      for (int j = 0; j < 3; j++) {
        this.vertexArrayDark[this.vertexPositionDark++] = vertices[i][j];
      }
    }
  }
  
  /**
   * 法線ベクトル配列(濃い色)の長さを返します。
   * 
   * @return 法線ベクトル配列(濃い色)の長さ
   */
  public float[] getNormalVectorArrayDark() {
    return this.normalVectorArrayDark;
  }

  /**
   * 与えられた法線ベクトルをこのオブジェクトの法線ベクトル配列(濃い色)に追加します。
   * 
   * @param normalVector 法線ベクトル
   */
  protected void appendNormalVectorDark(float[] normalVector) {
    for (int i = 0; i < normalVector.length; i++) {
      this.normalVectorArrayDark[this.normalVectorPositionDark++] = normalVector[i];
    }
  }

  /**
   * 三角形ポリゴンの法線ベクトルを法線ベクトル配列(濃い)に追加します。
   * 
   * @param normalVector 法線ベクトル
   */
  protected void appendNormalVectorDark(float[][] normalVector) {
    for (int i = 0; i < normalVector.length; i++) {
      for (int j = 0; j < 3; j++) {
        this.normalVectorArrayDark[this.normalVectorPositionDark++] = normalVector[i][j];
      }
    }
  }

  /**
   * 頂点配列と法線ベクトル配列(濃い色)を準備します。
   * 
   * @param polygonSize ポリゴンの数
   */
  protected void initializeArraysDark(int polygonSize) {
    this.vertexPositionDark = 0;
    this.normalVectorPositionDark = 0;
    if (this.vertexArrayDark == null || this.vertexArrayDark.length != polygonSize * 3 * 3) {
      this.vertexArrayDark = new float[polygonSize * 3 * 3];
    }
    if (this.normalVectorArrayDark == null || this.normalVectorArrayDark.length != polygonSize * 3 * 3) {
      this.normalVectorArrayDark = new float[polygonSize * 3 * 3];
    }
  }
}
