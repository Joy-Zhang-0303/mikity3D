package org.mklab.mikity.model.xml.simplexml.config;

import org.simpleframework.xml.Attribute;


/**
 * Class Light.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class Light implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

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
//   * Method deleteY
//   */
//  public void deleteY() {
//    this.hasY = false;
//  }
//
//  /**
//   * Method deleteZ
//   */
//  public void deleteZ() {
//    this.hasZ = false;
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
    result = prime * result + (this.hasY ? 1231 : 1237);
    result = prime * result + (this.hasZ ? 1231 : 1237);
    result = prime * result + Float.floatToIntBits(this.x);
    result = prime * result + Float.floatToIntBits(this.y);
    result = prime * result + Float.floatToIntBits(this.z);
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
    if (this.hasX != other.hasX) {
      return false;
    }
    if (this.hasY != other.hasY) {
      return false;
    }
    if (this.hasZ != other.hasZ) {
      return false;
    }
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
      return false;
    }
    if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
      return false;
    }
    return true;
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
   * Returns the value of field 'z'.
   * 
   * @return the value of field 'z'.
   */
  public float getZ() {
    return this.z;
  }

//  /**
//   * Method hasX
//   * 
//   * @return hasX
//   */
//  public boolean hasX() {
//    return this.hasX;
//  }
//
//  /**
//   * Method hasY
//   * 
//   * @return hasY
//   */
//  public boolean hasY() {
//    return this.hasY;
//  }
//
//  /**
//   * Method hasZ
//   * 
//   * @return hasZ
//   */
//  public boolean hasZ() {
//    return this.hasZ;
//  }

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
   * Sets the value of field 'y'.
   * 
   * @param y the value of field 'y'.
   */
  public void setY(float y) {
    this.y = y;
    this.hasY = true;
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
}
