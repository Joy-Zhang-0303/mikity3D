package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * リンクデータを表すクラスです。
 * 
 * @version $Revision: 1.3 $ $Date: 2008/02/03 04:25:09 $
 */
@Root(name="linkData")
public class LinkData implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** 対象となるパラメータの名前 */
  @Attribute(name="target")
  private String target;

  /** データの番号 */
  @Attribute(name="number")
  private int number;
  
  /**
   * 新しく生成された<code>LinkData</code>オブジェクトを初期化します。
   */
  public LinkData() {
    this.target = ""; //$NON-NLS-1$
    this.number = 0;
  }
  

  /**
   * データの番号を返します。
   * 
   * @return データの番号
   */
  public int getNumber() {
    return this.number;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.number;
    result = prime * result + ((this.target == null) ? 0 : this.target.hashCode());
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
    
    LinkData other = (LinkData)obj;
    if (this.number != other.number) {
      return false;
    }

    if (this.target == null) {
      if (other.target != null) {
        return false;
      }
    } else if (!this.target.equals(other.target)) {
      return false;
    }
    return true;
  }

  /**
   * 対象となるパラメータの名前を返します。
   * 
   * @return 対象となるパラメータの名前
   */
  public String getTarget() {
    return this.target;
  }

  /**
   * データの番号をもつか判別します。
   * 
   * @return データの番号をもつならばtrue
   */
  public boolean hasNumber() {
    if (this.number != 0) {
      return true;
    }
    return false;
  }

  /**
   * データの番号を設定します。
   * 
   * @param number データの番号
   */
  public void setNumber(int number) {
    this.number = number;
  }

  /**
   * 対象となるパラメータの名前を設定します。
   * 
   * @param target 対象となるパラメータの名前
   */
  public void setTarget(String target) {
    this.target = target;
  }

  /**
   * 座標パラメータをもつか判別します。
   * @return 座標パラメータをもつならばtrue
   */
  public boolean hasCoordinateParameter() {
    if (this.target.equals("translationX") || this.target.equals("translationY") || this.target.equals("translationZ") || this.target.equals("rotationX") || this.target.equals("rotationY") //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        || this.target.equals("rotationZ")) { //$NON-NLS-1$
      return true;
    }
    return false;
  }
}
