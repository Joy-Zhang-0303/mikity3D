package org.mklab.mikity.model.xml.simplexml.config;

import org.simpleframework.xml.Attribute;


/**
 * 背景を表すクラスです。
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class Background implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  /** color */
  @Attribute(name="color")
  private String color;
  
  /**
   * 新しく生成された<code>Background</code>オブジェクトを初期化します。
   */
  public Background() {
    this.color = "white"; //$NON-NLS-1$
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.color == null) ? 0 : this.color.hashCode());
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
    Background other = (Background)obj;
    if (this.color == null) {
      if (other.color != null) {
        return false;
      }
    } else if (!this.color.equals(other.color)) {
      return false;
    }
    return true;
  }

  /**
   * 色を返します。
   * 
   * @return 色
   */
  public String getColor() {
    return this.color;
  }

  /**
   * 色を設定します。
   * 
   * @param color 色
   */
  public void setColor(String color) {
    this.color = color;
  }
}
