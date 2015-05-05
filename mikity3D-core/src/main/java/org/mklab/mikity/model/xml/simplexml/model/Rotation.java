package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;


/**
 * 回転を表すクラスです。
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class Rotation implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  /** X軸周りの回転 */
  @Attribute(name = "xrotation")
  private float xRotation;

  /** Y軸周りの回転 */
  @Attribute(name = "yrotation")
  private float yRotation;

  /** Z軸周りの回転 */
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
   * @param xRotation Ｘ軸周りの回転
   * @param yRotation Y軸周りの回転
   * @param zRotation Z軸周りの回転
   */
  public Rotation(float xRotation, float yRotation, float zRotation) {
    this.xRotation = xRotation;
    this.yRotation = yRotation;
    this.zRotation = zRotation;
  }

  /**
   * X軸周りの回転を返します。
   * 
   * @return X軸周りの回転
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
   * Y軸周りの回転を返します。
   * 
   * @return Y軸周りの回転
   */
  public float getYrotation() {
    return this.yRotation;
  }

  /**
   * Z軸周りの回転を返します。
   * 
   * @return Z軸周りの回転
   */
  public float getZrotation() {
    return this.zRotation;
  }

  /**
   * X軸周りの回転を設定します。
   * 
   * @param xRotation X軸周りの回転
   */
  public void setXrotation(float xRotation) {
    this.xRotation = xRotation;
  }

  /**
   * Y軸周りの回転を設定します。
   * 
   * @param yRotation Y軸ｋ周りの回転
   */
  public void setYrotation(float yRotation) {
    this.yRotation = yRotation;
  }

  /**
   * Z軸周りの回転を設定します。
   * 
   * @param zRotation Z軸周りの回転
   */
  public void setZrotation(float zRotation) {
    this.zRotation = zRotation;
  }
}
