package org.mklab.mikity.model.xml.jaxb.model;

import javax.xml.bind.annotation.XmlAttribute;


/**
 * Class Rotation.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class Rotation implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Field _xrotate
   */
  @XmlAttribute
  private float _xrotate;

  /**
   * keeps track of state for field: _xrotate
   */
  private boolean _has_xrotate;

  /**
   * Field _yrotate
   */
  @XmlAttribute
  private float _yrotate;

  /**
   * keeps track of state for field: _yrotate
   */
  private boolean _has_yrotate;

  /**
   * Field _zrotate
   */
  @XmlAttribute
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
   * @param x x座標
   * @param y y座標
   * @param z z座標
   */
  public Rotation(float x, float y, float z) {
    super();
    this._xrotate = x;
    this._yrotate = y;
    this._zrotate = z;
  }

  /**
   * Method deleteXrotate
   */
  public void deleteXrotate() {
    this._has_xrotate = false;
  }

  /**
   * Method deleteYrotate
   */
  public void deleteYrotate() {
    this._has_yrotate = false;
  }

  /**
   * Method deleteZrotate
   */
  public void deleteZrotate() {
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
  public float loadZrotate() {
    return this._zrotate;
  }

  /**
   * Method hasXrotate
   * 
   * @return has_xrotate
   */
  public boolean hasXrotate() {
    return this._has_xrotate;
  }

  /**
   * Method hasYrotate
   * 
   * @return has_yrotate
   */
  public boolean hasYrotate() {
    return this._has_yrotate;
  }

  /**
   * Method hasZrotate
   * 
   * @return has_zrotate
   */
  public boolean hasZrotate() {
    return this._has_zrotate;
  }

  /**
   * Sets the value of field 'xrotate'.
   * 
   * @param xrotate the value of field 'xrotate'.
   */
  public void setXrotate(float xrotate) {
    this._xrotate = xrotate;
    this._has_xrotate = true;
  }

  /**
   * Sets the value of field 'yrotate'.
   * 
   * @param yrotate the value of field 'yrotate'.
   */
  public void setYrotate(float yrotate) {
    this._yrotate = yrotate;
    this._has_yrotate = true;
  }

  /**
   * Sets the value of field 'zrotate'.
   * 
   * @param zrotate the value of field 'zrotate'.
   */
  public void setZrotate(float zrotate) {
    this._zrotate = zrotate;
    this._has_zrotate = true;
  }
}
