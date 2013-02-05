/*
 * $Id: XMLTrianglePolygon.java,v 1.15 2008/02/13 08:05:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.model;

import org.mklab.mikity.util.Matrix4;
import org.mklab.mikity.util.Vector3;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;


/**
 * @author koga
 * @version $Revision$, 2008/08/10
 */
public class XMLTrianglePolygon {

  @ElementArray
  private Location[] _point = new Location[3];
  @Attribute
  private String _color;
  @Element
  private Location _location;
  @Element
  private Rotation _rotation;

  private Vector3[] _normal = new Vector3[3];

  private Matrix4 _matrix;

  /** _transparent */
  protected boolean _transparent;

  /** keeps track of state for field: _transparent */
  private boolean _has_transparent;

  /**
   * 新しく生成された<code>XMLTrianglePolygon</code>オブジェクトを初期化します。
   */
  public XMLTrianglePolygon() {
    for (int i = 0; i < this._point.length; i++) {
      this._point[i] = new Location();
    }
    this._color = "orange"; //$NON-NLS-1$
    this._matrix = new Matrix4(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    setNormalVector();
  }

  /**
   * @param number 座標番号
   * @param x X座標
   * @param y Y座標
   * @param z Z座標
   */
  public void setPointLocation(int number, float x, float y, float z) {
    this._point[number].setX(x);
    this._point[number].setY(y);
    this._point[number].setZ(z);
    setNormalVector();
  }

  /**
   * @param location1 座標1
   * @param location2 座標2
   * @param location3 座標3
   */
  public void setPointLocations(Location location1, Location location2, Location location3) {
    this._point[0] = location1;
    this._point[1] = location2;
    this._point[2] = location3;
    setNormalVector();
  }

  /**
   * @param points 座標
   */
  public void setPointLocations(Location[] points) {
    this._point = points;
    setNormalVector();
  }

  /**
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }

  /**
   * @param location 位置
   */
  public void setLocation(Location location) {
    this._location = location;
  }

  /**
   * @param rot 回転
   */
  public void setRotation(Rotation rot) {
    this._rotation = rot;
  }

  /**
   * @param nromalVector 法線ベクトル
   */
  public void setNormalVector(Location nromalVector) {
    this._normal[0] = new Vector3(nromalVector.getX(), nromalVector.getY(), nromalVector.loadZ());
    this._normal[1] = new Vector3(nromalVector.getX(), nromalVector.getY(), nromalVector.loadZ());
    this._normal[2] = new Vector3(nromalVector.getX(), nromalVector.getY(), nromalVector.loadZ());
  }

  /**
   * @param normalVectors 法線ベクトル
   */
  public void setNormalVector(Vector3[] normalVectors) {
    this._normal = normalVectors;
  }

  /**
   *  
   */
  public void setNormalVector() {
    Vector3 v1 = new Vector3(this._point[1].getX() - this._point[0].getX(), this._point[1].getY() - this._point[0].getY(), this._point[1].loadZ() - this._point[0].loadZ());
    Vector3 v2 = new Vector3(this._point[2].getX() - this._point[1].getX(), this._point[2].getY() - this._point[1].getY(), this._point[2].loadZ() - this._point[1].loadZ());
    Vector3 n = v1.cross(v2).normalize();
    this._normal[0] = n;
    this._normal[1] = n;
    this._normal[2] = n;
  }

  /**
   * @param matrix 行列
   */
  public void setMatrix(Matrix4 matrix) {
    this._matrix = matrix;
  }

  /**
   * @param number 座標番号
   * @return x location
   */
  public float getPointLocationX(int number) {
    return this._point[number].getX();
  }

  /**
   * @param number 座標番号
   * @return y location
   */
  public float getPointLocationY(int number) {
    return this._point[number].getY();
  }

  /**
   * @param number 座標番号
   * @return z location
   */
  public float getPointLocationZ(int number) {
    return this._point[number].loadZ();
  }

  /**
   * @return color
   */
  public String getColor() {
    return this._color;
  }

  /**
   * @return location
   */
  public Location getLocation() {
    return this._location;
  }

  /**
   * @return rotation
   */
  public Rotation getRotation() {
    return this._rotation;
  }

  /**
   * @return normal vector
   */
  public Vector3[] getNormalVectors() {
    setNormalVector();
    return this._normal;
  }

  /**
   * @return matrix
   */
  public Matrix4 getMatrix() {
    return this._matrix;
  }

  /**
   * Method deleteTransparent
   */
  public void deleteTransparent() {
    this._has_transparent = false;
  }

  /**
   * Sets the value of field 'transparent'.
   * 
   * @param transparent the value of field 'transparent'.
   */
  public void setTransparent(boolean transparent) {
    this._transparent = transparent;
    this._has_transparent = true;
  }

  /**
   * Returns the value of field 'transparent'.
   * 
   * @return the value of field 'transparent'.
   */
  public boolean getTransparent() {
    return this._transparent;
  }

  /**
   * Method hasTransparent
   * 
   * @return has_tranparent
   */
  public boolean hasTransparent() {
    return this._has_transparent;
  }
}
