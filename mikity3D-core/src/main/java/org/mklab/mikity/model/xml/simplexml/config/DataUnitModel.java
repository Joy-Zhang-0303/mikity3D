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
package org.mklab.mikity.model.xml.simplexml.config;

import org.simpleframework.xml.Attribute;


/**
 * Class DataUnit.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:28 $
 */
public class DataUnitModel implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  /** angle */
  @Attribute(name="angle")
  private String angle;

  /** length */
  @Attribute(name="length")
  private String length;

  /**
   * 新しく生成された<code>DataUnit</code>オブジェクトを初期化します。
   */
  public DataUnitModel() {
    this.angle = "radian"; //$NON-NLS-1$
    this.length = "m"; //$NON-NLS-1$
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public DataUnitModel clone() {
    try {
      return (DataUnitModel)super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
  }
  
  /**
   * 角度の単位を返します。
   * 
   * @return 角度の単位
   */
  public String getAngle() {
    return this.angle;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.angle == null) ? 0 : this.angle.hashCode());
    result = prime * result + ((this.length == null) ? 0 : this.length.hashCode());
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
    DataUnitModel other = (DataUnitModel)obj;
    if (this.angle == null) {
      if (other.angle != null) {
        return false;
      }
    } else if (!this.angle.equals(other.angle)) {
      return false;
    }
    if (this.length == null) {
      if (other.length != null) {
        return false;
      }
    } else if (!this.length.equals(other.length)) {
      return false;
    }
    return true;
  }

  /**
   * 長さの単位を返します。
   * 
   * @return 長さの単位
   */
  public String getLength() {
    return this.length;
  }

  /**
   * 角度の単位を設定します。
   * 
   * @param angle 角度の単位
   */
  public void setAngle(String angle) {
    this.angle = angle;
  }

  /**
   * 長さの単位を設定します。
   * 
   * @param length 長さの単位
   */
  public void setLength(String length) {
    this.length = length;
  }
}
