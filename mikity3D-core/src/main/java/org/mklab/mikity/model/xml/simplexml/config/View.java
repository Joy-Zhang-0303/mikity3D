package org.mklab.mikity.model.xml.simplexml.config;

import org.simpleframework.xml.Attribute;


/**
 * ビューを表すクラスです。
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class View implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** xRotation */
  @Attribute(name="xrotation")
  private double xRotation;

  /** yRotation */
  @Attribute(name="yrotation")
  private double yRotation;
  
  /** zRotation */
  @Attribute(name="zrotation")
  private double zRotation;

  /** x */
  @Attribute(name="x")
  private float x;

  /** y */
  @Attribute(name="y")
  private float y;

  /** z */
  @Attribute(name="z")
  private float z;

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
    result = prime * result + Float.floatToIntBits(this.x);
    long temp = Double.doubleToLongBits(this.xRotation);
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
   * Sets the value of field 'x'.
   * 
   * @param x the value of field 'x'.
   */
  public void setX(float x) {
    this.x = x;
  }

  /**
   * Sets the value of field 'xrotate'.
   * 
   * @param xRotation the value of field 'xrotate'.
   */
  public void setXrotation(double xRotation) {
    this.xRotation = xRotation;
  }

  /**
   * Sets the value of field 'y'.
   * 
   * @param y the value of field 'y'.
   */
  public void setY(float y) {
    this.y = y;
  }

  /**
   * Sets the value of field 'yrotate'.
   * 
   * @param yRotation the value of field 'yrotate'.
   */
  public void setYrotation(double yRotation) {
    this.yRotation = yRotation;
  }

  /**
   * Sets the value of field 'z'.
   * 
   * @param z the value of field 'z'.
   */
  public void setZ(float z) {
    this.z = z;
  }

  /**
   * Sets the value of field 'zrotate'.
   * 
   * @param zRotation the value of field 'zrotate'.
   */
  public void setZrotation(double zRotation) {
    this.zRotation = zRotation;
  }
}
