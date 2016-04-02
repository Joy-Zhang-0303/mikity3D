/*
 * Created on 2016/04/02
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.graphic;

import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.util.Vector3;


/**
 * グラフィックオブジェクトを表す抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2016/04/02
 */
public abstract class AbstractGraphicObject implements GraphicObject {
  /** 頂点配列の参照位置。 */
  private int vertexPosition = 0;
  
  /** 法線ベクトル配列の参照位置。 */
  private int normalVectorPosition = 0;
    
  /** 頂点配列。 */
  private float vertexArray[];
  
  /** 法線ベクトル配列。 */
  private float normalVectorArray[];
  
  /**
   * {@inheritDoc}
   */
  public float[] getVertexArray() {
    return this.vertexArray;
  }
  
  /**
   * {@inheritDoc}
   */
  public int getVertexArrayLength() {
    return this.vertexArray.length;
  }
  
  /**
   * {@inheritDoc}
   */
  public float[] getNormalVectorArray() {
    return this.normalVectorArray;
  }
  
  /**
   * {@inheritDoc}
   */
  public int getNormalVectorArrayLength() {
    return this.normalVectorArray.length;
  }

  /**
   * 頂点を頂点配列に追加します。
   * 
   * @param vertices 頂点
   */
  protected void appendVertices(float[][] vertices) {
    for (int i = 0; i < vertices.length; i++) {
      for (int j = 0; j < 3; j++) {
        this.vertexArray[this.vertexPosition++] = vertices[i][j];
      }
    }
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
   * 三角形ポリゴンの法線ベクトルを法線ベクトル配列に3個追加します。
   * 
   * @param normalVector 法線ベクトル
   */
  protected void appendNormalVectorsOfTriangle(float[] normalVector) {
    for (int i = 0; i < 3; i++) {
      this.normalVectorArray[this.normalVectorPosition++] = normalVector[0];
      this.normalVectorArray[this.normalVectorPosition++] = normalVector[1];
      this.normalVectorArray[this.normalVectorPosition++] = normalVector[2];
    }
  }
  
  /**
   * 三角形ポリゴンの法線ベクトルを法線ベクトル配列に追加します。
   * 
   * @param normalVector 法線ベクトル
   */
  protected void appendNormalVector(float[][] normalVector) {
    for (int i = 0; i < normalVector.length; i++) {
      for (int j = 0; j < 3; j++) {
        this.normalVectorArray[this.normalVectorPosition++] = normalVector[i][j];
      }
    }
  }
  
  /**
   * 与えられた法線ベクトルをこのオブジェクトの法線ベクトル配列に追加します。
   * 
   * @param normalVector 法線ベクトル
   */
  protected void appendNormalVector(float[] normalVector) {
    for (int i = 0; i < normalVector.length; i++) {
      this.normalVectorArray[this.normalVectorPosition++] = normalVector[i];
    }
  }
  
  /**
   * 与えられた法線ベクトルをこのオブジェクトの法線ベクトル配列に追加します。
   * 
   * @param normalVector 法線ベクトル
   */
  protected void appendNormalVector(Vector3 normalVector) {
    this.normalVectorArray[this.normalVectorPosition++] = normalVector.getX();
    this.normalVectorArray[this.normalVectorPosition++] = normalVector.getY();
    this.normalVectorArray[this.normalVectorPosition++] = normalVector.getZ();
  }

  /**
   * 頂点配列と法線ベクトル配列を準備します。
   * 
   * @param polygonSize ポリゴンの数 
   */
  protected void initializeArrays(int polygonSize) {
    this.vertexPosition = 0;
    this.normalVectorPosition = 0;
    if (this.vertexArray == null || this.vertexArray.length != polygonSize*3*3) {
      this.vertexArray = new float[polygonSize*3*3];
    }
    if (this.normalVectorArray == null || this.normalVectorArray.length != polygonSize*3*3) {
      this.normalVectorArray = new float[polygonSize*3*3];
    }
  }
}
