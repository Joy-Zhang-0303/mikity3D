package org.mklab.mikity.xml.config;

import javax.xml.bind.annotation.XmlAttribute;


/**
 * Class View.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class View implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Field _xrotate
   */
  @XmlAttribute
  private double _xrotate;

  /**
   * keeps track of state for field: _xrotate
   */
  private boolean _has_xrotate;

  /**
   * Field _yrotate
   */
  @XmlAttribute
  private double _yrotate;

  /**
   * keeps track of state for field: _yrotate
   */
  private boolean _has_yrotate;

  /**
   * Field _zrotate
   */
  @XmlAttribute
  private double _zrotate;

  /**
   * keeps track of state for field: _zrotate
   */
  private boolean _has_zrotate;

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
   * Method deleteXrotate
   */
  public void deleteXrotate() {
    this._has_xrotate = false;
  }

  /**
   * Method deleteY
   */
  public void deleteY() {
    this._has_y = false;
  }

  /**
   * Method deleteYrotate
   */
  public void deleteYrotate() {
    this._has_yrotate = false;
  }

  /**
   * Method deleteZ
   */
  public void deleteZ() {
    this._has_z = false;
  }

  /**
   * Method deleteZrotate
   */
  public void deleteZrotate() {
    this._has_zrotate = false;
  }

//  /**
//   * Note: hashCode() has not been overriden
//   * 
//   * @param obj オブジェクト
//   */
//  @Override
//  public boolean equals(java.lang.Object obj) {
//    if (this == obj) return true;
//
//    if (obj instanceof View) {
//
//      View temp = (View)obj;
//      if (this._xrotate != temp._xrotate) return false;
//      if (this._has_xrotate != temp._has_xrotate) return false;
//      if (this._yrotate != temp._yrotate) return false;
//      if (this._has_yrotate != temp._has_yrotate) return false;
//      if (this._zrotate != temp._zrotate) return false;
//      if (this._has_zrotate != temp._has_zrotate) return false;
//      if (this._x != temp._x) return false;
//      if (this._has_x != temp._has_x) return false;
//      if (this._y != temp._y) return false;
//      if (this._has_y != temp._has_y) return false;
//      if (this._z != temp._z) return false;
//      if (this._has_z != temp._has_z) return false;
//      return true;
//    }
//    return false;
//  }

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
    result = prime * result + (this._has_xrotate ? 1231 : 1237);
    result = prime * result + (this._has_y ? 1231 : 1237);
    result = prime * result + (this._has_yrotate ? 1231 : 1237);
    result = prime * result + (this._has_z ? 1231 : 1237);
    result = prime * result + (this._has_zrotate ? 1231 : 1237);
    result = prime * result + Float.floatToIntBits(this._x);
    long temp;
    temp = Double.doubleToLongBits(this._xrotate);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    result = prime * result + Float.floatToIntBits(this._y);
    temp = Double.doubleToLongBits(this._yrotate);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    result = prime * result + Float.floatToIntBits(this._z);
    temp = Double.doubleToLongBits(this._zrotate);
    result = prime * result + (int)(temp ^ (temp >>> 32));
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
    View other = (View)obj;
    if (this._has_x != other._has_x) {
      return false;
    }
    if (this._has_xrotate != other._has_xrotate) {
      return false;
    }
    if (this._has_y != other._has_y) {
      return false;
    }
    if (this._has_yrotate != other._has_yrotate) {
      return false;
    }
    if (this._has_z != other._has_z) {
      return false;
    }
    if (this._has_zrotate != other._has_zrotate) {
      return false;
    }
    if (Float.floatToIntBits(this._x) != Float.floatToIntBits(other._x)) {
      return false;
    }
    if (Double.doubleToLongBits(this._xrotate) != Double.doubleToLongBits(other._xrotate)) {
      return false;
    }
    if (Float.floatToIntBits(this._y) != Float.floatToIntBits(other._y)) {
      return false;
    }
    if (Double.doubleToLongBits(this._yrotate) != Double.doubleToLongBits(other._yrotate)) {
      return false;
    }
    if (Float.floatToIntBits(this._z) != Float.floatToIntBits(other._z)) {
      return false;
    }
    if (Double.doubleToLongBits(this._zrotate) != Double.doubleToLongBits(other._zrotate)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of field 'xrotate'.
   * 
   * @return the value of field 'xrotate'.
   */
  public double loadXrotate() {
    return this._xrotate;
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
   * Returns the value of field 'yrotate'.
   * 
   * @return the value of field 'yrotate'.
   */
  public double loadYrotate() {
    return this._yrotate;
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
   * Returns the value of field 'zrotate'.
   * 
   * @return the value of field 'zrotate'.
   */
  public double loadZrotate() {
    return this._zrotate;
  }

  /**
   * Method hasX
   * 
   * @return has_x
   */
  public boolean hasX() {
    return this._has_x;
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
   * Method hasY
   * 
   * @return has_y
   */
  public boolean hasY() {
    return this._has_y;
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
   * Method hasZ
   * 
   * @return has_z
   */
  public boolean hasZ() {
    return this._has_z;
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
   * Sets the value of field 'x'.
   * 
   * @param x the value of field 'x'.
   */
  public void setX(float x) {
    this._x = x;
    this._has_x = true;
  }

  /**
   * Sets the value of field 'xrotate'.
   * 
   * @param xrotate the value of field 'xrotate'.
   */
  public void setXrotate(double xrotate) {
    this._xrotate = xrotate;
    this._has_xrotate = true;
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
   * Sets the value of field 'yrotate'.
   * 
   * @param yrotate the value of field 'yrotate'.
   */
  public void setYrotate(double yrotate) {
    this._yrotate = yrotate;
    this._has_yrotate = true;
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

  /**
   * Sets the value of field 'zrotate'.
   * 
   * @param zrotate the value of field 'zrotate'.
   */
  public void setZrotate(double zrotate) {
    this._zrotate = zrotate;
    this._has_zrotate = true;
  }
}
