package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglLocRot implements JoglCoordinate {
  /** _xrotate */
  @XmlAttribute
  private float _xrotate;

  /** _yrotate */
  @XmlAttribute
  private float _yrotate;

  /** _zrotate */
  @XmlAttribute
  private float _zrotate;

  /** _x */
  @XmlAttribute
  private float _x;

  /** _y*/
  @XmlAttribute
  private float _y;

  /** _z */
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
   * @param xloc x座標
   * @param yloc y座標
   * @param zloc z座標
   * @param xrot x軸に対しての回転
   * @param yrot y軸に対しての回転
   * @param zrot z軸に対しての回転
   */
  public void setLocRot(float xloc, float yloc, float zloc, float xrot, float yrot, float zrot) {
    this._x = xloc;
    this._y = yloc;
    this._z = zloc;
    this._xrotate = xrot;
    this._yrotate = yrot;
    this._zrotate = zrot;
  }
}
