/*
 * $Id: XMLQuadPolygon.java,v 1.13 2008/02/13 08:05:19 morimune Exp $
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
 * 四角形ポリゴンを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2008/08/10
 */
@Root(name="quadPolygon")
public class XMLQuadPolygon {
  /** vertices */
  @ElementList(type=Vertex.class, inline=true, required=true)
  private List<Vertex> vertices;
  
  /** translation */
  @Element(name="translation", required=false)
  private Translation translation;
  
  /** rotation */
  @Element(name="rotation", required=false)
  private Rotation rotation;

  /** color */
  @Attribute(name="color")
  private String color;

  /** transparent */
  @Attribute(name="transparent", required=false)
  protected boolean transparent = false;

  /** 法線ベクトル。 */
  private Vector3 normalVector = new Vector3(0,0,1);

  private Matrix4 matrix;


  /**
   * 新しく生成された<code>XMLQuadPolygon</code>オブジェクトを初期化します。
   */
  public XMLQuadPolygon() {
    this.vertices = new ArrayList<>(4);
    this.color = "orange"; //$NON-NLS-1$
    this.matrix = new Matrix4(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * @param number 座標番号
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
   * @param location1 座標1
   * @param location2 座標2
   * @param location3 座標3
   * @param location4 座標4
   */
  public void setVertices(Vertex location1, Vertex location2, Vertex location3, Vertex location4) {
    this.vertices.set(0, location1);
    this.vertices.set(1, location2);
    this.vertices.set(2, location3);
    this.vertices.set(3, location4);
    updateNormalVector();
  }

  /**
   * @param vertices 座標
   */
  public void setVertices(List<Vertex> vertices) {
    this.vertices = vertices;
    updateNormalVector();
  }

  /**
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

  /**
   * @param matrix 行列
   */
  public void setMatrix(Matrix4 matrix) {
    this.matrix = matrix;
  }

  /**
   * 
   */
  public void updateNormalVector() {
    final Vector3 v1 = new Vector3(this.vertices.get(1).getX() - this.vertices.get(0).getX(), this.vertices.get(1).getY() - this.vertices.get(0).getY(), this.vertices.get(1).getZ() - this.vertices.get(0).getZ());
    final Vector3 v2 = new Vector3(this.vertices.get(2).getX() - this.vertices.get(1).getX(), this.vertices.get(2).getY() - this.vertices.get(1).getY(), this.vertices.get(2).getZ() - this.vertices.get(1).getZ());
    this.normalVector = v1.cross(v2).normalize();
  }

//  /**
//   * @param normalVector 法線ベクトル
//   */
//  public void setNormalVector(Vector3 normalVector) {
//    this.normalVector = normalVector;
//  }

//  /**
//   * @param translation 位置
//   */
//  public void setNormalVector(Translation translation) {
//    this.normalVector = new Vector3(translation.getX(), translation.getY(), translation.getZ());
//  }

  /**
   * @param number 座標番号
   * @return x location
   */
  public float getVertexX(int number) {
    return this.vertices.get(number).getX();
  }

  /**
   * @param number 座標番号
   * @return y location
   */
  public float getVertexY(int number) {
    return this.vertices.get(number).getY();
  }

  /**
   * @param number 座標番号
   * @return z location
   */
  public float getVertexZ(int number) {
    return this.vertices.get(number).getZ();
  }

  /**
   * @return color
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
   * @return normal vector
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
