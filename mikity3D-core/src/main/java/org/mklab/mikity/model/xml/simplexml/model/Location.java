package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * Class Location.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/19 10:39:36 $
 */
@Root(name="point")
public class Location implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** z */
  @Attribute(name="z")
  private float z;
  
  /** y */
  @Attribute(name="y")
  private float y;

  /** x */
  @Attribute(name="x")
  private float x;

  /** keeps track of state for field: _x */
  private boolean hasX;

  /** keeps track of state for field: _y */
  private boolean hasY;
  
  /** keeps track of state for field: _z */
  private boolean hasZ;

  /**
   * 新しく生成された<code>Location</code>オブジェクトを初期化します。
   */
  public Location() {
    super();
  }
  
  /**
   * 新しく生成された<code>Location</code>オブジェクトを初期化します。
   * 
   * @param x x座標
   * @param y y座標
   * @param z z座標
   */
  public Location(float x, float y, float z) {
    this.hasX = true;
    this.hasY = true;
    this.hasZ = true;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Method deleteX
   */
  public void deleteX() {
    this.hasX = false;
  } 

  /**
   * Method deleteY
   */
  public void deleteY() {
    this.hasY = false;
  }

  /**
   * Method deleteZ
   */
  public void deleteZ() {
    this.hasZ = false;
  }

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
  @SuppressWarnings("unqualified-field-access")
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (hasX ? 1231 : 1237);
    result = prime * result + (hasY ? 1231 : 1237);
    result = prime * result + (hasZ ? 1231 : 1237);
    result = prime * result + Float.floatToIntBits(x);
    result = prime * result + Float.floatToIntBits(y);
    result = prime * result + Float.floatToIntBits(z);
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
    Location other = (Location)obj;
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

  /**
   * Method hasX
   * 
   * @return has_x
   */
  public boolean hasX() {
    return this.hasX;
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
   * Method hasZ
   * 
   * @return has_z
   */
  public boolean hasZ() {
    return this.hasZ;
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
