/*
 * Created on 2015/07/11
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
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
    final float gridMin = -1f;
    final float gridMax = 1f;
    final float gridInterval = this.object.getGridInterval();

    final int lineSize = ((int)Math.ceil((gridMax - gridMin)/gridInterval) + 1)*2;
    initializeArrays(lineSize);
    
    float y = gridMin;
    for (int i = 0; y <= gridMax; i++, y = gridMin + i*gridInterval) {
      appendVertices(new float[]{gridMin, y, 0});
      appendVertices(new float[]{gridMax, y, 0});
    }
    
    float x = gridMin;
    for (int i = 0; x <= gridMax; i++, x = gridMin + i*gridInterval) {
      appendVertices(new float[]{x, gridMin, 0});
      appendVertices(new float[]{x, gridMax, 0});
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
