/*
 * $Id: XMLQuadPolygon.java,v 1.13 2008/02/13 08:05:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.util.Matrix4;
import org.mklab.mikity.util.Vector3;


/**
 * @author koga
 * @version $Revision$, 2008/08/10
 */
public class XMLQuadPolygon {

  @XmlElement
  private Location[] _point = new Location[4];
  @XmlAttribute
  private String _color;
  @XmlElement
  private Location _location;
  @XmlElement
  private Rotation _rotation;

  private Vector3[] _normal = new Vector3[4];

  private Matrix4 _matrix;

  /**
   * Field _transparent
   */
  protected boolean _transparent;

  /**
   * keeps track of state for field: _transparent
   */
  protected boolean _has_transparent;

  /**
   * 新しく生成された<code>XMLQuadPolygon</code>オブジェクトを初期化します。
   */
  public XMLQuadPolygon() {
    for (int i = 0; i < this._point.length; i++) {
      this._point[i] = new Location();
    }
    this._color = "orange"; //$NON-NLS-1$
//    this._color = "blue"; //$NON-NLS-1$
    this._matrix = new Matrix4(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * @param pointNum 座標番号
   * @param x X座標
   * @param y Y座標
   * @param z Z座標
   */
  public void setPointLocation(int pointNum, float x, float y, float z) {
    this._point[pointNum].setX(x);
    this._point[pointNum].setY(y);
    this._point[pointNum].setZ(z);
    setNormalVector();
  }

  /**
   * @param loc1 座標1
   * @param loc2 座標2
   * @param loc3 座標3
   * @param loc4 座標4
   */
  public void setPointLocations(Location loc1, Location loc2, Location loc3, Location loc4) {
    this._point[0] = loc1;
    this._point[1] = loc2;
    this._point[2] = loc3;
    this._point[3] = loc4;
    setNormalVector();
  }

  /**
   * @param point 座標
   */
  public void setPointLocations(Location[] point) {
    this._point = point;
    setNormalVector();
  }

  /**
   * @param c 色
   */
  public void setColor(String c) {
    this._color = c;
  }

  /**
   * @param loc 位置
   */
  public void setLocation(Location loc) {
    this._location = loc;
  }

  /**
   * @param rot 回転
   */
  public void setRotation(Rotation rot) {
    this._rotation = rot;
  }

  /**
   * @param matrix 行列
   */
  public void setMatrix(Matrix4 matrix) {
    this._matrix = matrix;
  }

  /**
   * 
   */
  public void setNormalVector() {
    // 修正
    Vector3 v1 = new Vector3(this._point[1].loadX() - this._point[0].loadX(), this._point[1].loadY() - this._point[0].loadY(), this._point[1].loadZ() - this._point[0].loadZ());
    Vector3 v2 = new Vector3(this._point[2].loadX() - this._point[1].loadX(), this._point[2].loadY() - this._point[1].loadY(), this._point[2].loadZ() - this._point[1].loadZ());

    Vector3 n = v1.cross(v2).normalize();

    this._normal[0] = n;
    this._normal[1] = n;
    this._normal[2] = n;
    this._normal[3] = n;
  }

  /**
   * @param n 法線ベクトル
   */
  public void setNormalVector(Vector3[] n) {
    this._normal = n;
  }

  /**
   * @param loc 位置
   */
  public void setNormalVector(Location loc) {
    this._normal[0] = new Vector3(loc.loadX(), loc.loadY(), loc.loadZ());
    this._normal[1] = new Vector3(loc.loadX(), loc.loadY(), loc.loadZ());
    this._normal[2] = new Vector3(loc.loadX(), loc.loadY(), loc.loadZ());
    this._normal[3] = new Vector3(loc.loadX(), loc.loadY(), loc.loadZ());
  }

  /**
   * @param pointNum 座標番号
   * @return x location
   */
  public float loadPointLocationX(int pointNum) {
    return this._point[pointNum].loadX();
  }

  /**
   * @param pointNum 座標番号
   * @return y location
   */
  public float loadPointLocationY(int pointNum) {
    return this._point[pointNum].loadY();
  }

  /**
   * @param pointNum 座標番号
   * @return z location
   */
  public float loadPointLocationZ(int pointNum) {
    return this._point[pointNum].loadZ();
  }

  /**
   * @return color
   */
  public String loadColor() {
    return this._color;
  }

  /**
   * @return location
   */
  public Location loadLocation() {
    return this._location;
  }

  /**
   * @return rotation
   */
  public Rotation loadRotation() {
    return this._rotation;
  }

  /**
   * @return normal vector
   */
  public Vector3[] loadNormalVector() {
    setNormalVector();
    return this._normal;
  }

  /**
   * @return matrix
   */
  public Matrix4 loadMatrix() {
    return this._matrix;
  }

  /**
   * Method deleteTransparent
   */
  public void deleteTransparent() {
    this._has_transparent = false;
  } // -- void deleteTransparent()

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
  public boolean loadTransparent() {
    return this._transparent;
  } // -- boolean getTransparent()

  /**
   * Method hasTransparent
   * 
   * @return has_tranparent
   */
  public boolean hasTransparent() {
    return this._has_transparent;
  } // -- boolean hasTransparent()
}
