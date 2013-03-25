package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;


/**
 * Class Rotation.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class Rotation implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** xrotate */
  @Attribute(name="xrotation")
  private float _xrotate;

  /** keeps track of state for field: _xrotate */
  private boolean _has_xrotate;

  /** yrotate */
  @Attribute(name="yrotation")
  private float _yrotate;

  /** keeps track of state for field: _yrotate */
  private boolean _has_yrotate;

  /** zrotate */
  @Attribute(name="zrotation")
  private float _zrotate;

  /**
   * keeps track of state for field: _zrotate
   */
  private boolean _has_zrotate;
   
  /**
   * 新しく生成された<code>Rotation</code>オブジェクトを初期化します。
   */
  public Rotation() {
    super();
  }

  /**
   * 新しく生成された<code>Rotation</code>オブジェクトを初期化します。
   * 
   * @param xRotation x座標
   * @param yRotation y座標
   * @param zRotation z座標
   */
  public Rotation(float xRotation, float yRotation, float zRotation) {
    this._xrotate = xRotation;
    this._yrotate = yRotation;
    this._zrotate = zRotation;
    this._has_xrotate = true;
    this._has_yrotate = true;
    this._has_zrotate = true;
  }

  /**
   * Method deleteXrotate
   */
  public void deleteXrotation() {
    this._has_xrotate = false;
  }

  /**
   * Method deleteYrotate
   */
  public void deleteYrotation() {
    this._has_yrotate = false;
  }

  /**
   * Method deleteZrotate
   */
  public void deleteZrotation() {
    this._has_zrotate = false;
  }

  /**
   * Returns the value of field 'xrotate'.
   * 
   * @return the value of field 'xrotate'.
   */
  public float getXrotation() {
    return this._xrotate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this._has_xrotate ? 1231 : 1237);
    result = prime * result + (this._has_yrotate ? 1231 : 1237);
    result = prime * result + (this._has_zrotate ? 1231 : 1237);
    result = prime * result + Float.floatToIntBits(this._xrotate);
    result = prime * result + Float.floatToIntBits(this._yrotate);
    result = prime * result + Float.floatToIntBits(this._zrotate);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Rotation other = (Rotation)obj;
    if (this._has_xrotate != other._has_xrotate) {
      return false;
    }
    if (this._has_yrotate != other._has_yrotate) {
      return false;
    }
    if (this._has_zrotate != other._has_zrotate) {
      return false;
    }
    if (Float.floatToIntBits(this._xrotate) != Float.floatToIntBits(other._xrotate)) {
      return false;
    }
    if (Float.floatToIntBits(this._yrotate) != Float.floatToIntBits(other._yrotate)) {
      return false;
    }
    if (Float.floatToIntBits(this._zrotate) != Float.floatToIntBits(other._zrotate)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of field 'yrotate'.
   * 
   * @return the value of field 'yrotate'.
   */
  public float getYrotation() {
    return this._yrotate;
  }

  /**
   * Returns the value of field 'zrotate'.
   * 
   * @return the value of field 'zrotate'.
   */
  public float getZrotation() {
    return this._zrotate;
  }

  /**
   * Method hasXrotate
   * 
   * @return has_xrotate
   */
  public boolean hasXrotation() {
    return this._has_xrotate;
  }

  /**
   * Method hasYrotate
   * 
   * @return has_yrotate
   */
  public boolean hasYrotation() {
    return this._has_yrotate;
  }

  /**
   * Method hasZrotate
   * 
   * @return has_zrotate
   */
  public boolean hasZrotation() {
    return this._has_zrotate;
  }

  /**
   * Sets the value of field 'xrotate'.
   * 
   * @param xRotation the value of field 'xrotate'.
   */
  public void setXrotation(float xRotation) {
    this._xrotate = xRotation;
    this._has_xrotate = true;
  }

  /**
   * Sets the value of field 'yrotate'.
   * 
   * @param yRotation the value of field 'yrotate'.
   */
  public void setYrotation(float yRotation) {
    this._yrotate = yRotation;
    this._has_yrotate = true;
  }

  /**
   * Sets the value of field 'zrotate'.
   * 
   * @param zrotation the value of field 'zrotate'.
   */
  public void setZrotation(float zrotation) {
    this._zrotate = zrotation;
    this._has_zrotate = true;
  }
}
