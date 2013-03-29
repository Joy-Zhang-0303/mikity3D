package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;


/**
 * 回転を表すクラスです。
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class Rotation implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  /** xrotate */
  @Attribute(name = "xrotation")
  private float xRotation;

  /** yrotate */
  @Attribute(name = "yrotation")
  private float yRotation;

  /** zrotate */
  @Attribute(name = "zrotation")
  private float zRotation;

  /**
   * 新しく生成された<code>Rotation</code>オブジェクトを初期化します。
   */
  public Rotation() {
    super();
  }

  /**
   * 新しく生成された<code>Rotation</code>オブジェクトを初期化します。
   * 
   * @param xRotation x座標
   * @param yRotation y座標
   * @param zRotation z座標
   */
  public Rotation(float xRotation, float yRotation, float zRotation) {
    this.xRotation = xRotation;
    this.yRotation = yRotation;
    this.zRotation = zRotation;
  }

  /**
   * Returns the value of field 'xrotate'.
   * 
   * @return the value of field 'xrotate'.
   */
  public float getXrotation() {
    return this.xRotation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Float.floatToIntBits(this.xRotation);
    result = prime * result + Float.floatToIntBits(this.yRotation);
    result = prime * result + Float.floatToIntBits(this.zRotation);
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
    if (Float.floatToIntBits(this.xRotation) != Float.floatToIntBits(other.xRotation)) {
      return false;
    }
    if (Float.floatToIntBits(this.yRotation) != Float.floatToIntBits(other.yRotation)) {
      return false;
    }
    if (Float.floatToIntBits(this.zRotation) != Float.floatToIntBits(other.zRotation)) {
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
    return this.yRotation;
  }

  /**
   * Returns the value of field 'zrotate'.
   * 
   * @return the value of field 'zrotate'.
   */
  public float getZrotation() {
    return this.zRotation;
  }

  /**
   * Sets the value of field 'xrotate'.
   * 
   * @param xRotation the value of field 'xrotate'.
   */
  public void setXrotation(float xRotation) {
    this.xRotation = xRotation;
  }

  /**
   * Sets the value of field 'yrotate'.
   * 
   * @param yRotation the value of field 'yrotate'.
   */
  public void setYrotation(float yRotation) {
    this.yRotation = yRotation;
  }

  /**
   * Sets the value of field 'zrotate'.
   * 
   * @param zrotation the value of field 'zrotate'.
   */
  public void setZrotation(float zrotation) {
    this.zRotation = zrotation;
  }
}
