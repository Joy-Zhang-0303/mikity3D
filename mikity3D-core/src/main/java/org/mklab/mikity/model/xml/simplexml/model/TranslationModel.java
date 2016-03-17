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
package org.mklab.mikity.model.xml.simplexml.model;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * 並進を表すクラスです。
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/19 10:39:36 $
 */
@Root(name="translation")
public class TranslationModel implements Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  /** x軸方向の並進 */
  @Attribute(name="x")
  private float x;

  /** y軸方向の併進 */
  @Attribute(name="y")
  private float y;

  /** z軸方向の併進 */
  @Attribute(name="z")
  private float z;

  /**
   * 新しく生成された<code>Translation</code>オブジェクトを初期化します。
   */
  public TranslationModel() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }

  /**
   * 新しく生成された<code>Translation</code>オブジェクトを初期化します。
   * @param x x
   * @param y y 
   * @param z z
   */
  public TranslationModel(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TranslationModel clone() {
    try {
      return (TranslationModel)super.clone();
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
  @SuppressWarnings("unqualified-field-access")
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
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
    TranslationModel other = (TranslationModel)obj;
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
  
  /**
   * (0,0,0)であるか判定します。
   * @return (0,0,0)ならばtrue，そうでなければfalse
   */
  public boolean isZero() {
    return this.x == 0 && this.y == 0 && this.z == 0;
  }
}
