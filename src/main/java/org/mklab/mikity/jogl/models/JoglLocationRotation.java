package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglLocationRotation implements JoglCoordinate {
  /** x軸周りの回転 */
  @XmlAttribute
  private float _xrotate;

  /** y軸周りの回転 */
  @XmlAttribute
  private float _yrotate;

  /** z軸周りの回転 */
  @XmlAttribute
  private float _zrotate;

  /** x */
  @XmlAttribute
  private float _x;

  /** y*/
  @XmlAttribute
  private float _y;

  /** z */
  @XmlAttribute
  private float _z;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    gl.glPushMatrix();
    gl.glTranslatef(this._x, this._y, this._z);
    gl.glPushMatrix();
    
    if (this._xrotate != 0.0f) {
      gl.glRotatef(this._xrotate, 1.0f, 0.0f, 0.0f);
    }
    if (this._yrotate != 0.0f) {
      gl.glRotatef(this._yrotate, 0.0f, 1.0f, 0.0f);
    }
    if (this._zrotate != 0.0f) {
      gl.glRotatef(this._zrotate, 0.0f, 0.0f, 1.0f);
    }
  }

  /**
   * @param x x座標
   * @param y y座標
   * @param z z座標
   */
  public void setLocation(float x, float y, float z) {
    this._x = x;
    this._y = y;
    this._z = z;
  }

  /**
   * x-y-z軸周りの回転を設定します。
   * 
   * @param xRotation x軸周りの回転
   * @param yRotation y軸周りの回転
   * @param zRotation z軸周りの回転
   */
  public void setRotation(float xRotation, float yRotation, float zRotation) {
    this._xrotate = xRotation;
    this._yrotate = yRotation;
    this._zrotate = zRotation;
  }
  
//  /**
//   * x-y-z座標を設定します。
//   * 
//   * @param x x座標
//   * @param y y座標
//   * @param z z座標
//   * @param xRotation x軸周りの回転
//   * @param yRotation y軸周りの回転
//   * @param zRotation z軸周りの回転
//   */
//  public void setLocRot(float x, float y, float z, float xRotation, float yRotation, float zRotation) {
//    this._x = x;
//    this._y = y;
//    this._z = z;
//    this._xrotate = xRotation;
//    this._yrotate = yRotation;
//    this._zrotate = zRotation;
//  }
}
