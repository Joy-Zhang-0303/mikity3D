/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.util;



/**
 * 3次元ベクトルを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2013/01/27
 */
public class Vector3 implements java.io.Serializable, Cloneable {
  /** */
  private static final long serialVersionUID = 1L;
  /** x成分 */
  private float x;
  /** y成分 */
  private float y;
  /** z成分 */
  private float z;
  
  /**
   * 新しく生成された<code>Vector3</code>オブジェクトを初期化します。
   * @param x x成分
   * @param y y成分
   * @param z z成分
   */
  public Vector3(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Vector3 clone() {
    try {
      return (Vector3)super.clone();
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
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Vector3 other = (Vector3)obj;
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) return false;
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) return false;
    if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) return false;
    return true;
  }

  /**
   * 外積を返します。
   * @param b 第二ベクトル
   * @return 外積
   */
  public Vector3 cross(Vector3 b) {
    return new Vector3(this.y*b.z - this.z*b.y, this.z*b.x - this.x*b.z, this.x*b.y - this.y*b.x);
  }
  
  /**
   * 長さを返します。
   * @return 長さ
   */
  public float length() {
    return (float)Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
  }
  
  /**
   * 正規化したベクトルを返します。
   * @return 正規化したベクトル
   */
  public Vector3 normalize() {
    final float length = length();
    if (length == 0) {
      return new Vector3(0, 0, 0);
    }
    return new Vector3(this.x/length, this.y/length,  this.z/length);
  }

  
  /**
   * xを返します。
   * @return x 
   */
  public float getX() {
    return this.x;
  }

  
  /**
   * yを返します。
   * @return y
   */
  public float getY() {
    return this.y;
  }

  
  /**
   * zを返します。
   * @return z
   */
  public float getZ() {
    return this.z;
  }
  
  /**
   * 成分を配列として返します。
   * @return 成分を含む配列
   */
  public float[] array() {
    return new float[]{this.x, this.y, this.z};
  }
  
}
