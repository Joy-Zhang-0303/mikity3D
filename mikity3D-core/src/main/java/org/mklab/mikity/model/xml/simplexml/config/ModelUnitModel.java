package org.mklab.mikity.model.xml.simplexml.config;

import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.simpleframework.xml.Attribute;


/**
 * モデルの単位を表すクラスです。
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class ModelUnitModel implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  /** 角度の単位 */
  @Attribute(name="angle")
  private String angleUnit;

  /** 長さの単位 */
  @Attribute(name="length")
  private String lengthUnit;
  
  /**
   * 新しく生成された<code>ModelUnit</code>オブジェクトを初期化します。
   */
  public ModelUnitModel() {
    this.angleUnit = "radian"; //$NON-NLS-1$
    this.lengthUnit = "m"; //$NON-NLS-1$
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public ModelUnitModel clone() {
    try {
      return (ModelUnitModel)super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
  }

  /**
   * 角度の単位を返します。
   * 
   * @return 角度の単位
   */
  public String getAngleUnit() {
    return this.angleUnit;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.angleUnit == null) ? 0 : this.angleUnit.hashCode());
    result = prime * result + ((this.lengthUnit == null) ? 0 : this.lengthUnit.hashCode());
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
    ModelUnitModel other = (ModelUnitModel)obj;
    if (this.angleUnit == null) {
      if (other.angleUnit != null) {
        return false;
      }
    } else if (!this.angleUnit.equals(other.angleUnit)) {
      return false;
    }
    if (this.lengthUnit == null) {
      if (other.lengthUnit != null) {
        return false;
      }
    } else if (!this.lengthUnit.equals(other.lengthUnit)) {
      return false;
    }
    return true;
  }

  /**
   * 長さの単位を返します。
   * 
   * @return 長さの単位
   */
  public String getLengthUnit() {
    return this.lengthUnit;
  }

  /**
   * 角度の単位を設定します。
   * 
   * @param angleUnit 角度の単位
   */
  public void setAngleUnit(String angleUnit) {
    this.angleUnit = angleUnit;
  }

  /**
   * 長さの単位を設定します。
   * 
   * @param lengthUnit 長さの単位
   */
  public void setLengthUnit(String lengthUnit) {
    this.lengthUnit = lengthUnit;
  }
}
