/*
 * $Id: XMLQuadPolygon.java,v 1.13 2008/02/13 08:05:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.model;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.xml.bind.annotation.*;


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

  private Vector3f[] _normal = new Vector3f[4];

  private Matrix4f _matrix;

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
    this._color = "orange";
    this._matrix = new Matrix4f(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * @param pointNum
   * @param x
   * @param y
   * @param z
   */
  public void setPointLocation(int pointNum, float x, float y, float z) {
    this._point[pointNum].setX(x);
    this._point[pointNum].setY(y);
    this._point[pointNum].setZ(z);
    setNormalVector();
  }

  /**
   * @param loc1
   * @param loc2
   * @param loc3
   * @param loc4
   */
  public void setPointLocations(Location loc1, Location loc2, Location loc3, Location loc4) {
    this._point[0] = loc1;
    this._point[1] = loc2;
    this._point[2] = loc3;
    this._point[3] = loc4;
    setNormalVector();
  }

  /**
   * @param point
   */
  public void setPointLocations(Location[] point) {
    this._point = point;
    setNormalVector();
  }

  /**
   * @param c
   */
  public void setColor(String c) {
    this._color = c;
  }

  /**
   * @param loc
   */
  public void setLocation(Location loc) {
    this._location = loc;
  }

  /**
   * @param rot
   */
  public void setRotation(Rotation rot) {
    this._rotation = rot;
  }

  /**
   * @param matrix
   */
  public void setMatrix(Matrix4f matrix) {
    this._matrix = matrix;
  }

  /**
   * 
   */
  public void setNormalVector() {
    // 修正
    Vector3f v1 = new Vector3f(this._point[1].loadX() - this._point[0].loadX(), this._point[1].loadY() - this._point[0].loadY(), this._point[1].loadZ() - this._point[0].loadZ());
    Vector3f v2 = new Vector3f(this._point[2].loadX() - this._point[1].loadX(), this._point[2].loadY() - this._point[1].loadY(), this._point[2].loadZ() - this._point[1].loadZ());

    Vector3f n = new Vector3f();
    n.cross(v1, v2);
    n.normalize();

    this._normal[0] = n;
    this._normal[1] = n;
    this._normal[2] = n;
    this._normal[3] = n;
  }

  /**
   * @param n
   */
  public void setNormalVector(Vector3f[] n) {
    this._normal = n;
  }

  /**
   * @param loc
   */
  public void setNormalVector(Location loc) {
    this._normal[0] = new Vector3f(loc.loadX(), loc.loadY(), loc.loadZ());
    this._normal[1] = new Vector3f(loc.loadX(), loc.loadY(), loc.loadZ());
    this._normal[2] = new Vector3f(loc.loadX(), loc.loadY(), loc.loadZ());
    this._normal[3] = new Vector3f(loc.loadX(), loc.loadY(), loc.loadZ());
  }

  /**
   * @param pointNum
   * @return
   */
  public float loadPointLocationX(int pointNum) {
    return this._point[pointNum].loadX();
  }

  /**
   * @param pointNum
   * @return
   */
  public float loadPointLocationY(int pointNum) {
    return this._point[pointNum].loadY();
  }

  /**
   * @param pointNum
   * @return
   */
  public float loadPointLocationZ(int pointNum) {
    return this._point[pointNum].loadZ();
  }

  /**
   * @return
   */
  public String loadColor() {
    return this._color;
  }

  /**
   * @return
   */
  public Location loadLocation() {
    return this._location;
  }

  /**
   * @return
   */
  public Rotation loadRotation() {
    return this._rotation;
  }

  /**
   * @return
   */
  public Vector3f[] loadNormalVector() {
    setNormalVector();
    return this._normal;
  }

  /**
   * @return
   */
  public Matrix4f loadMatrix() {
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
   * @param transparent
   *        the value of field 'transparent'.
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
