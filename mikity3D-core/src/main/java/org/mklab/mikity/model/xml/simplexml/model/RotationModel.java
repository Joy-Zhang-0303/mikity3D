package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * 回転を表すクラスです。
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
@Root(name="rotation")
public class RotationModel implements java.io.Serializable, Cloneable {

  private static final long serialVersionUID = 1L;

  /** X軸周りの回転 */
  @Attribute(name = "x")
  private float x;

  /** Y軸周りの回転 */
  @Attribute(name = "y")
  private float y;

  /** Z軸周りの回転 */
  @Attribute(name = "z")
  private float z;

  /**
   * 新しく生成された<code>Rotation</code>オブジェクトを初期化します。
   */
  public RotationModel() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }

  /**
   * 新しく生成された<code>Rotation</code>オブジェクトを初期化します。
   * 
   * @param x Ｘ軸周りの回転
   * @param y Y軸周りの回転
   * @param z Z軸周りの回転
   */
  public RotationModel(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public RotationModel clone() {
    try {
      return (RotationModel)super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
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
    RotationModel other = (RotationModel)obj;
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
   * X軸周りの回転を返します。
   * 
   * @return X軸周りの回転
   */
  public float getX() {
    return this.x;
  }

  /**
   * Y軸周りの回転を返します。
   * 
   * @return Y軸周りの回転
   */
  public float getY() {
    return this.y;
  }

  /**
   * Z軸周りの回転を返します。
   * 
   * @return Z軸周りの回転
   */
  public float getZ() {
    return this.z;
  }

  /**
   * X軸周りの回転を設定します。
   * 
   * @param x X軸周りの回転
   */
  public void setX(float x) {
    this.x = x;
  }

  /**
   * Y軸周りの回転を設定します。
   * 
   * @param y Y軸ｋ周りの回転
   */
  public void setY(float y) {
    this.y = y;
  }

  /**
   * Z軸周りの回転を設定します。
   * 
   * @param z Z軸周りの回転
   */
  public void setZ(float z) {
    this.z = z;
  }
  
  /**
   * (0,0,0)であるか判定します。
   * @return (0,0,0)ならばtrue，そうでなければfalse
   */
  public boolean isZero() {
    return this.x == 0 && this.y == 0 && this.z == 0;
  }
}
