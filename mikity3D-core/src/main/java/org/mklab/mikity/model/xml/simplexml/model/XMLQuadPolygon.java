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
 * @author koga
 * @version $Revision$, 2008/08/10
 */
@Root(name="quadPolygon")
public class XMLQuadPolygon {
  @ElementList(type=Location.class, inline=true, required=true)
  private List<Location> points;
  
  @Attribute(name="color")
  private String color;
  
  @Element(required=false)
  private Location location;
  
  @Element(required=false)
  private Rotation rotation;

  private Vector3[] normal = new Vector3[4];

  private Matrix4 matrix;

  /** transparent */
  protected boolean transparent;

  /**
   * 新しく生成された<code>XMLQuadPolygon</code>オブジェクトを初期化します。
   */
  public XMLQuadPolygon() {
    this.points = new ArrayList<Location>(4);
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
   * @param location4 座標4
   */
  public void setPointLocations(Location location1, Location location2, Location location3, Location location4) {
    this.points.set(0, location1);
    this.points.set(1, location2);
    this.points.set(2, location3);
    this.points.set(3, location4);
    setNormalVector();
  }

  /**
   * @param points 座標
   */
  public void setPointLocations(List<Location> points) {
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
   * @param location 位置
   */
  public void setLocation(Location location) {
    this.location = location;
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
  public void setNormalVector() {
    // 修正
    Vector3 v1 = new Vector3(this.points.get(1).getX() - this.points.get(0).getX(), this.points.get(1).getY() - this.points.get(0).getY(), this.points.get(1).getZ() - this.points.get(0).getZ());
    Vector3 v2 = new Vector3(this.points.get(2).getX() - this.points.get(1).getX(), this.points.get(2).getY() - this.points.get(1).getY(), this.points.get(2).getZ() - this.points.get(1).getZ());

    Vector3 n = v1.cross(v2).normalize();

    this.normal[0] = n;
    this.normal[1] = n;
    this.normal[2] = n;
    this.normal[3] = n;
  }

  /**
   * @param normalVectors 法線ベクトル
   */
  public void setNormalVector(Vector3[] normalVectors) {
    this.normal = normalVectors;
  }

  /**
   * @param location 位置
   */
  public void setNormalVector(Location location) {
    this.normal[0] = new Vector3(location.getX(), location.getY(), location.getZ());
    this.normal[1] = new Vector3(location.getX(), location.getY(), location.getZ());
    this.normal[2] = new Vector3(location.getX(), location.getY(), location.getZ());
    this.normal[3] = new Vector3(location.getX(), location.getY(), location.getZ());
  }

  /**
   * @param number 座標番号
   * @return x location
   */
  public float getPointLocationX(int number) {
    return this.points.get(number).getX();
  }

  /**
   * @param number 座標番号
   * @return y location
   */
  public float getPointLocationY(int number) {
    return this.points.get(number).getY();
  }

  /**
   * @param number 座標番号
   * @return z location
   */
  public float getPointLocationZ(int number) {
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
  public Location getLocation() {
    return this.location;
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
  public Vector3[] getNormalVector() {
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
