package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglLocation implements JoglCoordinate {
  /** x */
  @XmlAttribute
  private float _x;

  /** y */
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
  }

  /**
   * 位置を設定します。
   * 
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
   * 平行移動します。
   * 
   * @param x x座用
   * @param y y座標
   * @param z z座標
   */
  public void translate(float x, float y, float z) {
    this._x += x;
    this._y += y;
    this._z += z;
  }
}
