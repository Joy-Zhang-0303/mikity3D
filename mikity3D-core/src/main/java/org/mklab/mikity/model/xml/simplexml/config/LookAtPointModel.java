package org.mklab.mikity.model.xml.simplexml.config;

import org.simpleframework.xml.Attribute;


/**
 * 注視点を表すクラスです。
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class LookAtPointModel implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 1L;
  
  /** X座標。 */
  @Attribute(name="x")
  private float x;

  /** Y座標。 */
  @Attribute(name="y")
  private float y;

  /** Z座標。 */
  @Attribute(name="z")
  private float z;

  /**
   * 新しく生成された<code>Eye</code>オブジェクトを初期化します。
   */
  public LookAtPointModel() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }
  
  /**
   * 新しく生成された<code>Eye</code>オブジェクトを初期化します。
   * @param x X座標
   * @param y Y座標
   * @param z Z座標
   */
  public LookAtPointModel(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public LookAtPointModel clone() {
    try {
      return (LookAtPointModel)super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
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
    LookAtPointModel other = (LookAtPointModel)obj;
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
   * Sets the value of field 'x'.
   * 
   * @param x the value of field 'x'.
   */
  public void setX(float x) {
    this.x = x;
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
   * Sets the value of field 'z'.
   * 
   * @param z the value of field 'z'.
   */
  public void setZ(float z) {
    this.z = z;
  }
}
