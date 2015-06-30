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
  @Attribute(name="translationX")
  private float translationX;

  /** Y軸方向の並進。 */
  @Attribute(name="translationY")
  private float translationY;

  /** Z軸方向の並進。 */
  @Attribute(name="translationZ")
  private float translationZ;
  
  /** X軸周りの回転。 */
  @Attribute(name="rotationX")
  private double rotationX;

  /** Y軸周りの回転。 */
  @Attribute(name="rotationY")
  private double rotationY;
  
  /** Z軸周りの回転。 */
  @Attribute(name="rotationZ")
  private double rotationZ;

  /**
   * 新しく生成された<code>Eye</code>オブジェクトを初期化します。
   */
  public Eye() {
    this.translationX = 0;
    this.translationY = 0;
    this.translationZ = 0;
    this.rotationX = 0;
    this.rotationY = 0;
    this.rotationZ = 0;
  }
  
  /**
   * 新しく生成された<code>Eye</code>オブジェクトを初期化します。
   * @param translationX X軸方向の並進
   * @param translationY Y軸方向の並進
   * @param translationZ Z軸方向の並進
   */
  public Eye(float translationX, float translationY, float translationZ) {
    this.translationX = translationX;
    this.translationY = translationY;
    this.translationZ = translationZ;
  }
  
  /**
   * Returns the value of field 'x'.
   * 
   * @return the value of field 'x'.
   */
  public float getTranslationX() {
    return this.translationX;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Float.floatToIntBits(this.translationX);
    long temp = Double.doubleToLongBits(this.rotationX);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    result = prime * result + Float.floatToIntBits(this.translationY);
    temp = Double.doubleToLongBits(this.rotationY);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    result = prime * result + Float.floatToIntBits(this.translationZ);
    temp = Double.doubleToLongBits(this.rotationZ);
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
    Eye other = (Eye)obj;
    if (Float.floatToIntBits(this.translationX) != Float.floatToIntBits(other.translationX)) {
      return false;
    }
    if (Double.doubleToLongBits(this.rotationX) != Double.doubleToLongBits(other.rotationX)) {
      return false;
    }
    if (Float.floatToIntBits(this.translationY) != Float.floatToIntBits(other.translationY)) {
      return false;
    }
    if (Double.doubleToLongBits(this.rotationY) != Double.doubleToLongBits(other.rotationY)) {
      return false;
    }
    if (Float.floatToIntBits(this.translationZ) != Float.floatToIntBits(other.translationZ)) {
      return false;
    }
    if (Double.doubleToLongBits(this.rotationZ) != Double.doubleToLongBits(other.rotationZ)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of field 'xrotate'.
   * 
   * @return the value of field 'xrotate'.
   */
  public double getRotationX() {
    return this.rotationX;
  }

  /**
   * Returns the value of field 'y'.
   * 
   * @return the value of field 'y'.
   */
  public float getTransaltionY() {
    return this.translationY;
  }

  /**
   * Returns the value of field 'yrotate'.
   * 
   * @return the value of field 'yrotate'.
   */
  public double getRotationY() {
    return this.rotationY;
  }

  /**
   * Returns the value of field 'z'.
   * 
   * @return the value of field 'z'.
   */
  public float getTransaltionZ() {
    return this.translationZ;
  }

  /**
   * Returns the value of field 'zrotate'.
   * 
   * @return the value of field 'zrotate'.
   */
  public double getRotationZ() {
    return this.rotationZ;
  }

  /**
   * Sets the value of field 'x'.
   * 
   * @param translationX the value of field 'x'.
   */
  public void setTranslationX(float translationX) {
    this.translationX = translationX;
  }

  /**
   * Sets the value of field 'xrotate'.
   * 
   * @param rotationX the value of field 'xrotate'.
   */
  public void setRotationX(double rotationX) {
    this.rotationX = rotationX;
  }

  /**
   * Sets the value of field 'y'.
   * 
   * @param translationY the value of field 'y'.
   */
  public void setTranslationY(float translationY) {
    this.translationY = translationY;
  }

  /**
   * Sets the value of field 'yrotate'.
   * 
   * @param rotationY the value of field 'yrotate'.
   */
  public void setRotationY(double rotationY) {
    this.rotationY = rotationY;
  }

  /**
   * Sets the value of field 'z'.
   * 
   * @param translationZ the value of field 'z'.
   */
  public void setTranslationZ(float translationZ) {
    this.translationZ = translationZ;
  }

  /**
   * Sets the value of field 'zrotate'.
   * 
   * @param rotationZ the value of field 'zrotate'.
   */
  public void setRotationZ(double rotationZ) {
    this.rotationZ = rotationZ;
  }
}
