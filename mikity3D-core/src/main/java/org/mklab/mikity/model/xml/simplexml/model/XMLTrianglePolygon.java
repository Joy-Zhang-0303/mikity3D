/*
 * $Id: XMLTrianglePolygon.java,v 1.15 2008/02/13 08:05:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.model;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.util.Matrix4;
import org.mklab.mikity.util.Vector3;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


/**
 * 三角形ポリゴンを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2008/08/10
 */
@Root(name="trianglePolygon")
public class XMLTrianglePolygon {
  /** vertices */
  @ElementList(type=Vertex.class, inline=true, required=true)
  private List<Vertex> vertices;
 
  /** color */
  @Attribute(name="color")
  private String color;

  /** transparent */
  @Attribute(name="transparent", required=false)
  private boolean transparent = false;

  /** translation */
  @Element(name="translation", required=false)
  private Translation translation;
  
  /** rotation */
  @Element(name="rotation", required=false)
  private Rotation rotation;
  
  private Vector3 normalVector = new Vector3(0,0,1);

  private Matrix4 matrix;

  /**
   * 新しく生成された<code>XMLTrianglePolygon</code>オブジェクトを初期化します。
   */
  public XMLTrianglePolygon() {
    this.vertices = new ArrayList<>(3);
    this.color = "orange"; //$NON-NLS-1$
    this.matrix = new Matrix4(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * 頂点を設定します。
   * 
   * @param number 頂点の番号(0-2)
   * @param x X座標
   * @param y Y座標
   * @param z Z座標
   */
  public void setVertex(int number, float x, float y, float z) {
    this.vertices.get(number).setX(x);
    this.vertices.get(number).setY(y);
    this.vertices.get(number).setZ(z);
    updateNormalVector();
  }

  /**
   * 3個の頂点を設定します。
   * 
   * @param vertex0 頂点0
   * @param vertex1 頂点1
   * @param vertex2 頂点2
   */
  public void setVertices(Vertex vertex0, Vertex vertex1, Vertex vertex2) {
    this.vertices.set(0, vertex0);
    this.vertices.set(1, vertex1);
    this.vertices.set(2, vertex2);
    updateNormalVector();
  }

  /**
   * 頂点を設定します。
   * 
   * @param vertices 頂点
   */
  public void setVertices(List<Vertex> vertices) {
    this.vertices = vertices;
    updateNormalVector();
  }

  /**
   * 色を設定します。
   * 
   * @param color 色
   */
  public void setColor(String color) {
    this.color = color;
  }

  /**
   * @param translation 位置
   */
  public void setTranslation(Translation translation) {
    this.translation = translation;
  }

  /**
   * @param rotation 回転
   */
  public void setRotation(Rotation rotation) {
    this.rotation = rotation;
  }

//  /**
//   * 法線ベクトルを設定します。
//   * 
//   * @param nromalVector 法線ベクトル
//   */
//  public void setNormalVector(Translation nromalVector) {
//    this.normalVector = new Vector3(nromalVector.getX(), nromalVector.getY(), nromalVector.getZ());
//  }

//  /**
//   * 法線ベクトルを設定します。
//   * 
//   * @param normalVector 法線ベクトル
//   */
//  public void setNormalVector(Vector3 normalVector) {
//    this.normalVector = normalVector;
//  }

  /**
   * 法線ベクトルを更新します。
   */
  private void updateNormalVector() {
    final Vector3 v1 = new Vector3(this.vertices.get(1).getX() - this.vertices.get(0).getX(), this.vertices.get(1).getY() - this.vertices.get(0).getY(), this.vertices.get(1).getZ() - this.vertices.get(0).getZ());
    final Vector3 v2 = new Vector3(this.vertices.get(2).getX() - this.vertices.get(1).getX(), this.vertices.get(2).getY() - this.vertices.get(1).getY(), this.vertices.get(2).getZ() - this.vertices.get(1).getZ());
    this.normalVector = v1.cross(v2).normalize();
  }

  /**
   * @param matrix 行列
   */
  public void setMatrix(Matrix4 matrix) {
    this.matrix = matrix;
  }
  
  /**
   * 指定された頂点を返します。
   * @param number 頂点の番号(0-2)
   * @return 指定された頂点
   */
  public Vertex getVertex(int number) {
    return this.vertices.get(number); 
  }

//  /**
//   * 指定された頂点のX座標を返します。
//   * 
//   * @param number 頂点の番号(0-2)
//   * @return x X座標
//   */
//  public float getVertexX(int number) {
//    return this.vertices.get(number).getX();
//  }
//
//  /**
//   * 指定された頂点のY座標を返します。
//   * 
//   * @param number 頂点の番号(0-2)
//   * @return y Y座標
//   */
//  public float getVertexY(int number) {
//    return this.vertices.get(number).getY();
//  }
//
//  /**
//   * 指定された頂点のZ座標を返します。
//   * 
//   * @param number 頂点の番号(0-2)
//   * @return z Z座標
//   */
//  public float getVertexZ(int number) {
//    return this.vertices.get(number).getZ();
//  }

  /**
   * 色を返します。
   * 
   * @return 色
   */
  public String getColor() {
    return this.color;
  }

  /**
   * @return location
   */
  public Translation getTranslation() {
    return this.translation;
  }

  /**
   * @return rotation
   */
  public Rotation getRotation() {
    return this.rotation;
  }

  /**
   * 法線ベクトルを返します。
   * 
   * @return 法線ベクトル
   */
  public Vector3 getNormalVector() {
    return this.normalVector;
  }

  /**
   * @return matrix
   */
  public Matrix4 getMatrix() {
    return this.matrix;
  }

  /**
   * Sets the value of field 'transparent'.
   * 
   * @param transparent the value of field 'transparent'.
   */
  public void setTransparent(boolean transparent) {
    this.transparent = transparent;
  }

  /**
   * Returns the value of field 'transparent'.
   * 
   * @return the value of field 'transparent'.
   */
  public boolean getTransparent() {
    return this.transparent;
  }
}
