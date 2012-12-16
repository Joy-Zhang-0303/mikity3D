package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/08
 */
public class JoglRotation implements JoglCoordinate {
  /** _xrotate */
  @XmlAttribute
  private float _xrotate;

  /** _yrotate */
  @XmlAttribute
  private float _yrotate;

  /** _zrotate */
  @XmlAttribute
  private float _zrotate;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
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
   * @param xRotation x軸に関する回転角
   * @param yRotation y軸に関する回転角
   * @param zRotation z軸に関する回転角
   */
  public void setRotation(float xRotation, float yRotation, float zRotation) {
    this._xrotate = xRotation;
    this._yrotate = yRotation;
    this._zrotate = zRotation;
  }

  /**
   * 回転します。
   * 
   * @param xRotation x軸に関する回転角
   * @param yRotation y軸に関する回転角
   * @param zRotation z軸に関する回転角
   */
  public void rotate(float xRotation, float yRotation, float zRotation) {
    this._xrotate += xRotation;
    this._yrotate += yRotation;
    this._zrotate += zRotation;
  }
}
