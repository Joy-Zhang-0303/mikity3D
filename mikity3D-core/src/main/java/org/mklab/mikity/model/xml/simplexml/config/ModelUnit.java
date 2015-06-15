package org.mklab.mikity.model.xml.simplexml.config;

import org.simpleframework.xml.Attribute;


/**
 * モデルの単位を表すクラスです。
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class ModelUnit implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** 角度の単位 */
  @Attribute(name="angle")
  private String angle;

  /** 長さの単位 */
  @Attribute(name="length")
  private String length;
  
  /**
   * 新しく生成された<code>ModelUnit</code>オブジェクトを初期化します。
   */
  public ModelUnit() {
    this.angle = "radian"; //$NON-NLS-1$
    this.length = "m"; //$NON-NLS-1$
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
    ModelUnit other = (ModelUnit)obj;
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
