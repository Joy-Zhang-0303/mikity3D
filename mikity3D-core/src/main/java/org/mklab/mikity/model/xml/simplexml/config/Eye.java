package org.mklab.mikity.model.xml.simplexml.config;

import org.simpleframework.xml.Attribute;


/**
 * 視点を表すクラスです。
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class Eye implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  
  /** X軸方向の並進。 */
  @Attribute(name="x")
  private float x;

  /** Y軸方向の並進。 */
  @Attribute(name="y")
  private float y;

  /** Z軸方向の並進。 */
  @Attribute(name="z")
  private float z;
  
//  /** X軸周りの回転。 */
//  @Attribute(name="rotationX")
//  private double rotationX;
//
//  /** Y軸周りの回転。 */
//  @Attribute(name="rotationY")
//  private double rotationY;
//  
//  /** Z軸周りの回転。 */
//  @Attribute(name="rotationZ")
//  private double rotationZ;

  /**
   * 新しく生成された<code>Eye</code>オブジェクトを初期化します。
   */
  public Eye() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
//    this.rotationX = 0;
//    this.rotationY = 0;
//    this.rotationZ = 0;
  }
  
  /**
   * 新しく生成された<code>Eye</code>オブジェクトを初期化します。
   * @param x X軸方向の並進
   * @param y Y軸方向の並進
   * @param z Z軸方向の並進
   */
  public Eye(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
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
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Float.floatToIntBits(this.x);
    //long temp = Double.doubleToLongBits(this.rotationX);
    //result = prime * result + (int)(temp ^ (temp >>> 32));
    result = prime * result + Float.floatToIntBits(this.y);
    //temp = Double.doubleToLongBits(this.rotationY);
    //result = prime * result + (int)(temp ^ (temp >>> 32));
    result = prime * result + Float.floatToIntBits(this.z);
    //temp = Double.doubleToLongBits(this.rotationZ);
    //result = prime * result + (int)(temp ^ (temp >>> 32));
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
    Eye other = (Eye)obj;
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
//    if (Double.doubleToLongBits(this.rotationX) != Double.doubleToLongBits(other.rotationX)) {
//      return false;
//    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
      return false;
    }
//    if (Double.doubleToLongBits(this.rotationY) != Double.doubleToLongBits(other.rotationY)) {
//      return false;
//    }
    if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
      return false;
    }
//    if (Double.doubleToLongBits(this.rotationZ) != Double.doubleToLongBits(other.rotationZ)) {
//      return false;
//    }
    return true;
  }

//  /**
//   * Returns the value of field 'xrotate'.
//   * 
//   * @return the value of field 'xrotate'.
//   */
//  public double getRotationX() {
//    return this.rotationX;
//  }

  /**
   * Returns the value of field 'y'.
   * 
   * @return the value of field 'y'.
   */
  public float getY() {
    return this.y;
  }

//  /**
//   * Returns the value of field 'yrotate'.
//   * 
//   * @return the value of field 'yrotate'.
//   */
//  public double getRotationY() {
//    return this.rotationY;
//  }

  /**
   * Returns the value of field 'z'.
   * 
   * @return the value of field 'z'.
   */
  public float getZ() {
    return this.z;
  }

//  /**
//   * Returns the value of field 'zrotate'.
//   * 
//   * @return the value of field 'zrotate'.
//   */
//  public double getRotationZ() {
//    return this.rotationZ;
//  }

  /**
   * Sets the value of field 'x'.
   * 
   * @param x the value of field 'x'.
   */
  public void setX(float x) {
    this.x = x;
  }

//  /**
//   * Sets the value of field 'xrotate'.
//   * 
//   * @param rotationX the value of field 'xrotate'.
//   */
//  public void setRotationX(double rotationX) {
//    this.rotationX = rotationX;
//  }

  /**
   * Sets the value of field 'y'.
   * 
   * @param y the value of field 'y'.
   */
  public void setY(float y) {
    this.y = y;
  }

//  /**
//   * Sets the value of field 'yrotate'.
//   * 
//   * @param rotationY the value of field 'yrotate'.
//   */
//  public void setRotationY(double rotationY) {
//    this.rotationY = rotationY;
//  }

  /**
   * Sets the value of field 'z'.
   * 
   * @param z the value of field 'z'.
   */
  public void setZ(float z) {
    this.z = z;
  }

//  /**
//   * Sets the value of field 'zrotate'.
//   * 
//   * @param rotationZ the value of field 'zrotate'.
//   */
//  public void setRotationZ(double rotationZ) {
//    this.rotationZ = rotationZ;
//  }
}
