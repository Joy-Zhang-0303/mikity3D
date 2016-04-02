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

import java.util.List;

import org.mklab.mikity.model.xml.simplexml.config.BaseCoordinateModel;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;

/**
 * グリッドオブジェクトを表すクラスです。
 */
public class GridObject {
  /** 頂点配列の参照位置。 */
  private int vertexPosition = 0;
    
  /** 頂点配列。 */
  private float vertexArray[];
  
  /** オブジェクト。 */
  protected BaseCoordinateModel object;
  
  /**
   * 新しく生成された<code>GraphicPrimitive</code>オブジェクトを初期化します。
   * @param object オブジェクト
   */
  public GridObject(BaseCoordinateModel object) {
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
    final float depthMin = -floorDepth/2;
    final float depthMax = floorDepth/2;
    final float widthMin = -floorWidth/2;
    final float widthMax = floorWidth/2;

    final int lineSize = ((int)Math.ceil((floorWidth)/gridSize) + 1) + ((int)Math.ceil((floorDepth)/gridSize) + 1);
    initializeArrays(lineSize);
    
    float y = widthMin;
    for (int i = 0; y <= widthMax; i++, y = widthMin + i*gridSize) {
      appendVertices(new float[]{depthMin, y, 0});
      appendVertices(new float[]{depthMax, y, 0});
    }
    
    float x = depthMin;
    for (int i = 0; x <= depthMax; i++, x = depthMin + i*gridSize) {
      appendVertices(new float[]{x, widthMin, 0});
      appendVertices(new float[]{x, widthMax, 0});
    }
  }

  /**
   * 頂点配列の長さを返します。
   * 
   * @return 頂点配列の長さ
   */
  public float[] getVertexArray() {
    return this.vertexArray;
  }

  /**
   * 頂点配列を返します。
   * 
   * @return 頂点配列
   */
  public int getVertexArrayLength() {
    return this.vertexArray.length;
  }
  
  /**
   * 色を返します。
   * 
   * @return 色
   */
  public ColorModel getColor() {
    return this.object.getGridColor();
  }
  
  /**
   * 頂点を頂点配列に追加します。
   * 
   * @param vertices 頂点
   */
  protected void appendVertices(List<VertexModel> vertices) {
    for (final VertexModel vertex : vertices) {
      this.vertexArray[this.vertexPosition++] = vertex.getX();
      this.vertexArray[this.vertexPosition++] = vertex.getY();
      this.vertexArray[this.vertexPosition++] = vertex.getZ();
    }
  }
  
  /**
   * 与えられた頂点配列をこのオブジェクトの頂点配列に追加します。
   * 
   * @param vertices 頂点
   */
  protected void appendVertices(float[] vertices) {
    for (int i = 0; i < vertices.length; i++) {
      this.vertexArray[this.vertexPosition++] = vertices[i];
    }
  }

  /**
   * 頂点配列と法線ベクトル配列を準備します。
   * 
   * @param lineSize 線の数 
   */
  protected void initializeArrays(int lineSize) {
    this.vertexPosition = 0;
    if (this.vertexArray == null || this.vertexArray.length != lineSize*3*3) {
      this.vertexArray = new float[lineSize*2*3];
    }
  }
}
