/*
 * Created on 2015/07/11
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.graphic;

import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.util.Vector3;

/**
 * グラフィックオブジェクトを表す抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/07/11
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
  
  /** オブジェクト。 */
  protected ObjectModel object;
  
  /**
   * 新しく生成された<code>GraphicPrimitive</code>オブジェクトを初期化します。
   * @param object オブジェクト
   */
  public AbstractGraphicObject(ObjectModel object) {
    this.object = object;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public float[] getVertexArray() {
    return this.vertexArray;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int getVertexArrayLength() {
    return this.vertexArray.length;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public float[] getNormalVectorArray() {
    return this.normalVectorArray;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int getNormalVectorArrayLength() {
    return this.normalVectorArray.length;
  }
  
  /**
   * 色を設定します。
   * 
   * @param color 色
   */
  public void setColor(ColorModel color) {
    this.object.setColor(color);
  }
  
  /**
   * 色を返します。
   * 
   * @return 色
   */
  public ColorModel getColor() {
    return this.object.getColor();
  }

  
  /**
   * 透明性を設定します。
   * 
   * @param isTransparent 透明性
   */
  public void setTransparent(boolean isTransparent) {
    this.object.setTransparent(isTransparent);
  }
  
  /**
   * 透明性を判定します。
   * 
   * @return 透明ならばtrue、そうでなければfalse
   */
  public boolean isTransparent() {
    return this.object.isTransparent();
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
