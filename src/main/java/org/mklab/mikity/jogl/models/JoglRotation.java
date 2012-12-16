package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/08
 */
public class JoglRotation implements JoglCoordinate {
  /** x軸周りの回転 */
  @XmlAttribute
  private float _xrotate;

  /** y軸周りの回転 */
  @XmlAttribute
  private float _yrotate;

  /** z軸周りの回転 */
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
   * @param xRotation x軸軸周りの回転
   * @param yRotation y軸軸周りの回転
   * @param zRotation z軸軸周りの回転
   */
  public void setRotation(float xRotation, float yRotation, float zRotation) {
    this._xrotate = xRotation;
    this._yrotate = yRotation;
    this._zrotate = zRotation;
  }

  /**
   * 回転します。
   * 
   * @param xRotation x軸周りの回転
   * @param yRotation y軸周りの回転
   * @param zRotation z軸周りの回転
   */
  public void rotate(float xRotation, float yRotation, float zRotation) {
    this._xrotate += xRotation;
    this._yrotate += yRotation;
    this._zrotate += zRotation;
  }
}
