/*
 * Created on 2015/07/11
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.model.ColorModel;

/**
 * グラフィックプリミティブを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/07/11
 */
public class GraphicPrimitive implements GraphicObject {
//  /** 色。 */
//  private String colorName;
  
  /** 色。 */
  private ColorModel color;
  
  /** 透明性。 */
  private boolean transparent = false;
  
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
  
//  /**
//   * 色を設定します。
//   * 
//   * @param colorName 色
//   */
//  public void setColorName(String colorName) {
//    this.colorName = colorName;
//  }
  
//  /**
//   * 色を返します。
//   * 
//   * @return 色
//   */
//  public String getColorName() {
//    return this.colorName;
//  }
  
  /**
   * 色を設定します。
   * 
   * @param color 色
   */
  public void setColor(ColorModel color) {
    this.color = color;
  }
  
  /**
   * 色を返します。
   * 
   * @return 色
   */
  public ColorModel getColor() {
    return this.color;
  }

  
  /**
   * 透明性を設定します。
   * 
   * @param transparent 透明性
   */
  public void setTransparent(boolean transparent) {
    this.transparent = transparent;
  }
  
  /**
   * 透明性を判定します。
   * 
   * @return 透明ならばtrue、そうでなければfalse
   */
  public boolean isTransparent() {
    return this.transparent;
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
