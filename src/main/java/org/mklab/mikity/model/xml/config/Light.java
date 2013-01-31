package org.mklab.mikity.model.xml.config;

import javax.xml.bind.annotation.XmlAttribute;


/**
 * Class Light.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class Light implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Field _x
   */
  @XmlAttribute
  private float _x;

  /**
   * keeps track of state for field: _x
   */
  private boolean _has_x;

  /**
   * Field _y
   */
  @XmlAttribute
  private float _y;

  /**
   * keeps track of state for field: _y
   */
  private boolean _has_y;

  /**
   * Field _z
   */
  @XmlAttribute
  private float _z;

  /**
   * keeps track of state for field: _z
   */
  private boolean _has_z;

  /**
   * Method deleteX
   */
  public void deleteX() {
    this._has_x = false;
  }

  /**
   * Method deleteY
   */
  public void deleteY() {
    this._has_y = false;
  }

  /**
   * Method deleteZ
   */
  public void deleteZ() {
    this._has_z = false;
  }

  /**
   * Returns the value of field 'x'.
   * 
   * @return the value of field 'x'.
   */
  public float loadX() {
    return this._x;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this._has_x ? 1231 : 1237);
    result = prime * result + (this._has_y ? 1231 : 1237);
    result = prime * result + (this._has_z ? 1231 : 1237);
    result = prime * result + Float.floatToIntBits(this._x);
    result = prime * result + Float.floatToIntBits(this._y);
    result = prime * result + Float.floatToIntBits(this._z);
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
    Light other = (Light)obj;
    if (this._has_x != other._has_x) {
      return false;
    }
    if (this._has_y != other._has_y) {
      return false;
    }
    if (this._has_z != other._has_z) {
      return false;
    }
    if (Float.floatToIntBits(this._x) != Float.floatToIntBits(other._x)) {
      return false;
    }
    if (Float.floatToIntBits(this._y) != Float.floatToIntBits(other._y)) {
      return false;
    }
    if (Float.floatToIntBits(this._z) != Float.floatToIntBits(other._z)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of field 'y'.
   * 
   * @return the value of field 'y'.
   */
  public float loadY() {
    return this._y;
  }

  /**
   * Returns the value of field 'z'.
   * 
   * @return the value of field 'z'.
   */
  public float loadZ() {
    return this._z;
  }

  /**
   * Method hasX
   * 
   * @return hasX
   */
  public boolean hasX() {
    return this._has_x;
  }

  /**
   * Method hasY
   * 
   * @return hasY
   */
  public boolean hasY() {
    return this._has_y;
  }

  /**
   * Method hasZ
   * 
   * @return hasZ
   */
  public boolean hasZ() {
    return this._has_z;
  }

  /**
   * Sets the value of field 'x'.
   * 
   * @param x the value of field 'x'.
   */
  public void setX(float x) {
    this._x = x;
    this._has_x = true;
  }

  /**
   * Sets the value of field 'y'.
   * 
   * @param y the value of field 'y'.
   */
  public void setY(float y) {
    this._y = y;
    this._has_y = true;
  }

  /**
   * Sets the value of field 'z'.
   * 
   * @param z the value of field 'z'.
   */
  public void setZ(float z) {
    this._z = z;
    this._has_z = true;
  }
}
