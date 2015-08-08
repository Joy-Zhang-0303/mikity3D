package org.mklab.mikity.model.xml.simplexml.config;

import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.simpleframework.xml.Element;


/**
 * 背景を表すクラスです。
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class BackgroundModel implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  /** color */
  @Element(name="color")
  private ColorModel color;
  
  /**
   * 新しく生成された<code>Background</code>オブジェクトを初期化します。
   */
  public BackgroundModel() {
    this.color = new ColorModel("white"); //$NON-NLS-1$
  }
  
  /**
   * 新しく生成された<code>Background</code>オブジェクトを初期化します。
   * @param color 背景色
   */
  public BackgroundModel(String color) {
    this.color = new ColorModel(color);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public BackgroundModel clone() {
    try {
      final BackgroundModel ans = (BackgroundModel)super.clone();
      ans.color = this.color.clone();
      return ans;
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
    result = prime * result + ((this.color == null) ? 0 : this.color.hashCode());
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
    BackgroundModel other = (BackgroundModel)obj;
    if (this.color == null) {
      if (other.color != null) return false;
    } else if (!this.color.equals(other.color)) return false;
    return true;
  }
  
  /**
   * 色を返します。
   * 
   * @return 色
   */
  public ColorModel getColor() {
    return this.color;
  }
  
  /**
   * 色を設定します。
   * 
   * @param color 色
   */
  public void setColor(ColorModel color) {
    this.color = color;
  }

}
