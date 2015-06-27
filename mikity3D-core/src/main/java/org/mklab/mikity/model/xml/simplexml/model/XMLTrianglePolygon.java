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
  @ElementList(type=Translation.class, inline=true, required=true)
  private List<Translation> points;
 
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
  
  private Vector3[] normal = new Vector3[3];

  private Matrix4 matrix;


  /**
   * 新しく生成された<code>XMLTrianglePolygon</code>オブジェクトを初期化します。
   */
  public XMLTrianglePolygon() {
    this.points = new ArrayList<>(3);
    this.color = "orange"; //$NON-NLS-1$
    this.matrix = new Matrix4(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * @param number 座標番号
   * @param x X座標
   * @param y Y座標
   * @param z Z座標
   */
  public void setPointLocation(int number, float x, float y, float z) {
    this.points.get(number).setX(x);
    this.points.get(number).setY(y);
    this.points.get(number).setZ(z);
    setNormalVector();
  }

  /**
   * @param location1 座標1
   * @param location2 座標2
   * @param location3 座標3
   */
  public void setPointLocations(Translation location1, Translation location2, Translation location3) {
    this.points.set(0, location1);
    this.points.set(1, location2);
    this.points.set(2, location3);
    setNormalVector();
  }

  /**
   * @param points 座標
   */
  public void setPointLocations(List<Translation> points) {
    this.points = points;
    setNormalVector();
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
   * @param nromalVector 法線ベクトル
   */
  public void setNormalVector(Translation nromalVector) {
    this.normal[0] = new Vector3(nromalVector.getX(), nromalVector.getY(), nromalVector.getZ());
    this.normal[1] = new Vector3(nromalVector.getX(), nromalVector.getY(), nromalVector.getZ());
    this.normal[2] = new Vector3(nromalVector.getX(), nromalVector.getY(), nromalVector.getZ());
  }

  /**
   * @param normalVectors 法線ベクトル
   */
  public void setNormalVector(Vector3[] normalVectors) {
    this.normal = normalVectors;
  }

  /**
   *  
   */
  private void setNormalVector() {
    Vector3 v1 = new Vector3(this.points.get(1).getX() - this.points.get(0).getX(), this.points.get(1).getY() - this.points.get(0).getY(), this.points.get(1).getZ() - this.points.get(0).getZ());
    Vector3 v2 = new Vector3(this.points.get(2).getX() - this.points.get(1).getX(), this.points.get(2).getY() - this.points.get(1).getY(), this.points.get(2).getZ() - this.points.get(1).getZ());
    Vector3 normalVector = v1.cross(v2).normalize();
    this.normal[0] = normalVector;
    this.normal[1] = normalVector;
    this.normal[2] = normalVector;
  }

  /**
   * @param matrix 行列
   */
  public void setMatrix(Matrix4 matrix) {
    this.matrix = matrix;
  }

  /**
   * @param number 座標番号
   * @return x location
   */
  public float getPointX(int number) {
    return this.points.get(number).getX();
  }

  /**
   * @param number 座標番号
   * @return y location
   */
  public float getPointY(int number) {
    return this.points.get(number).getY();
  }

  /**
   * @param number 座標番号
   * @return z location
   */
  public float getPointZ(int number) {
    return this.points.get(number).getZ();
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
  public Vector3[] getNormalVectors() {
    setNormalVector();
    return this.normal;
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
