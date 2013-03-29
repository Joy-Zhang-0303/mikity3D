package org.mklab.mikity.model.xml.simplexml.config;

import org.simpleframework.xml.Attribute;


/**
 * Class View.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class View implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** xRotation */
  @Attribute(name="xrotation")
  private double xRotation;

  /** Keep track of state for field: _xrotate */
  private boolean hasXrotation;

  /** yRotation */
  @Attribute(name="yrotation")
  private double yRotation;

  /** keeps track of state for field: _yrotate */
  private boolean hasYrotation;

  /** zRotation */
  @Attribute(name="zrotation")
  private double zRotation;

  /** keeps track of state for field: _zrotate */
  private boolean hasZrotation;

  /** x */
  @Attribute(name="x")
  private float x;

  /** keeps track of state for field: _x */
  private boolean hasX;

  /** y */
  @Attribute(name="y")
  private float y;

  /** keeps track of state for field: _y */
  private boolean hasY;

  /** z */
  @Attribute(name="z")
  private float z;

  /** keeps track of state for field: _z */
  private boolean hasZ;

//  /**
//   * Method deleteX
//   */
//  public void deleteX() {
//    this.hasX = false;
//  }
//
//  /**
//   * Method deleteXrotate
//   */
//  public void deleteXrotation() {
//    this.hasXrotation = false;
//  }
//
//  /**
//   * Method deleteY
//   */
//  public void deleteY() {
//    this.hasY = false;
//  }
//
//  /**
//   * Method deleteYrotate
//   */
//  public void deleteYrotation() {
//    this.hasYrotation = false;
//  }
//
//  /**
//   * Method deleteZ
//   */
//  public void deleteZ() {
//    this.hasZ = false;
//  }
//
//  /**
//   * Method deleteZrotate
//   */
//  public void deleteZrotation() {
//    this.hasZrotation = false;
//  }

  /**
   * Returns the value of field 'x'.
   * 
   * @return the value of field 'x'.
   */
  public float getX() {
    return this.x;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.hasX ? 1231 : 1237);
    result = prime * result + (this.hasXrotation ? 1231 : 1237);
    result = prime * result + (this.hasY ? 1231 : 1237);
    result = prime * result + (this.hasYrotation ? 1231 : 1237);
    result = prime * result + (this.hasZ ? 1231 : 1237);
    result = prime * result + (this.hasZrotation ? 1231 : 1237);
    result = prime * result + Float.floatToIntBits(this.x);
    long temp;
    temp = Double.doubleToLongBits(this.xRotation);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    result = prime * result + Float.floatToIntBits(this.y);
    temp = Double.doubleToLongBits(this.yRotation);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    result = prime * result + Float.floatToIntBits(this.z);
    temp = Double.doubleToLongBits(this.zRotation);
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
    if (this.hasX != other.hasX) {
      return false;
    }
    if (this.hasXrotation != other.hasXrotation) {
      return false;
    }
    if (this.hasY != other.hasY) {
      return false;
    }
    if (this.hasYrotation != other.hasYrotation) {
      return false;
    }
    if (this.hasZ != other.hasZ) {
      return false;
    }
    if (this.hasZrotation != other.hasZrotation) {
      return false;
    }
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.xRotation) != Double.doubleToLongBits(other.xRotation)) {
      return false;
    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
      return false;
    }
    if (Double.doubleToLongBits(this.yRotation) != Double.doubleToLongBits(other.yRotation)) {
      return false;
    }
    if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
      return false;
    }
    if (Double.doubleToLongBits(this.zRotation) != Double.doubleToLongBits(other.zRotation)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of field 'xrotate'.
   * 
   * @return the value of field 'xrotate'.
   */
  public double getXrotation() {
    return this.xRotation;
  }

  /**
   * Returns the value of field 'y'.
   * 
   * @return the value of field 'y'.
   */
  public float getY() {
    return this.y;
  }

  /**
   * Returns the value of field 'yrotate'.
   * 
   * @return the value of field 'yrotate'.
   */
  public double getYrotation() {
    return this.yRotation;
  }

  /**
   * Returns the value of field 'z'.
   * 
   * @return the value of field 'z'.
   */
  public float getZ() {
    return this.z;
  }

  /**
   * Returns the value of field 'zrotate'.
   * 
   * @return the value of field 'zrotate'.
   */
  public double getZrotation() {
    return this.zRotation;
  }

  /**
   * Method hasX
   * 
   * @return has_x
   */
  public boolean hasX() {
    return this.hasX;
  }

  /**
   * Method hasXrotate
   * 
   * @return has_xrotate
   */
  public boolean hasXrotation() {
    return this.hasXrotation;
  }

  /**
   * Method hasY
   * 
   * @return has_y
   */
  public boolean hasY() {
    return this.hasY;
  }

  /**
   * Method hasYrotate
   * 
   * @return has_yrotate
   */
  public boolean hasYrotation() {
    return this.hasYrotation;
  }

  /**
   * Method hasZ
   * 
   * @return has_z
   */
  public boolean hasZ() {
    return this.hasZ;
  }

  /**
   * Method hasZrotate
   * 
   * @return has_zrotate
   */
  public boolean hasZrotation() {
    return this.hasZrotation;
  }

  /**
   * Sets the value of field 'x'.
   * 
   * @param x the value of field 'x'.
   */
  public void setX(float x) {
    this.x = x;
    this.hasX = true;
  }

  /**
   * Sets the value of field 'xrotate'.
   * 
   * @param xRotation the value of field 'xrotate'.
   */
  public void setXrotation(double xRotation) {
    this.xRotation = xRotation;
    this.hasXrotation = true;
  }

  /**
   * Sets the value of field 'y'.
   * 
   * @param y the value of field 'y'.
   */
  public void setY(float y) {
    this.y = y;
    this.hasY = true;
  }

  /**
   * Sets the value of field 'yrotate'.
   * 
   * @param yRotation the value of field 'yrotate'.
   */
  public void setYrotation(double yRotation) {
    this.yRotation = yRotation;
    this.hasYrotation = true;
  }

  /**
   * Sets the value of field 'z'.
   * 
   * @param z the value of field 'z'.
   */
  public void setZ(float z) {
    this.z = z;
    this.hasZ = true;
  }

  /**
   * Sets the value of field 'zrotate'.
   * 
   * @param zRotation the value of field 'zrotate'.
   */
  public void setZrotation(double zRotation) {
    this.zRotation = zRotation;
    this.hasZrotation = true;
  }
}
