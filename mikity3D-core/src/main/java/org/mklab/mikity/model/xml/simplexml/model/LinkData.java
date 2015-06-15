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
  private String targetName;

  /** 定数の値 */
  @Attribute(name="const")
  private double constantValue;

  /** データの列番号 */
  @Attribute(name="column")
  private int columnNumber;
  
  /**
   * 新しく生成された<code>LinkData</code>オブジェクトを初期化します。
   */
  public LinkData() {
    this.targetName = ""; //$NON-NLS-1$
  }
  

  /**
   * データの列番号を返します。
   * 
   * @return データの列番号
   */
  public int getColumnNumber() {
    return this.columnNumber;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.columnNumber;
    long temp;
    temp = Double.doubleToLongBits(this.constantValue);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    result = prime * result + ((this.targetName == null) ? 0 : this.targetName.hashCode());
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
    if (this.columnNumber != other.columnNumber) {
      return false;
    }
    if (Double.doubleToLongBits(this.constantValue) != Double.doubleToLongBits(other.constantValue)) {
      return false;
    }
    if (this.targetName == null) {
      if (other.targetName != null) {
        return false;
      }
    } else if (!this.targetName.equals(other.targetName)) {
      return false;
    }
    return true;
  }

  /**
   * 定数の値を返します。
   * 
   * @return 定数の値
   */
  public double getConstantValue() {
    return this.constantValue;
  }

  /**
   * 対象となるパラメータの名前を返します。
   * 
   * @return 対象となるパラメータの名前
   */
  public String getTargetName() {
    return this.targetName;
  }

  /**
   * データの列番号をもつか判別します。
   * 
   * @return データの列番号をもつならばtrue
   */
  public boolean hasColumnNumber() {
    if (this.columnNumber != 0) {
      return true;
    }
    return false;
  }

  /**
   * 定数をもつか判別します。
   * 
   * @return 定数をもつならばtrue
   */
  public boolean hasConstantValue() {
    if (this.constantValue != 0.0) {
      return true;
    }
    return false;
  }

  /**
   * データの列番号を設定します。
   * 
   * @param columnNumber データの列番号
   */
  public void setColumnNumber(int columnNumber) {
    this.columnNumber = columnNumber;
  }

  /**
   * 定数を設定します。
   * 
   * @param constantValue 定数
   */
  public void setConstantValue(double constantValue) {
    this.constantValue = constantValue;
  }

  /**
   * 対象となるパラメータの名前を設定します。
   * 
   * @param target 対象となるパラメータの名前
   */
  public void setTargetName(String target) {
    this.targetName = target;
  }

  /**
   * DHパラメータをもつか判別します。
   * @return DHパラメータを持つならばtrue
   */
  public boolean hasDHParameter() {
    if (this.targetName.equals("a") || this.targetName.equals("alpha") || this.targetName.equals("d") || this.targetName.equals("theta")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
      return true;
    }
    return false;
  }

  /**
   * 座標パラメータをもつか判別します。
   * @return 座標パラメータをもつならばtrue
   */
  public boolean hasCoordinateParameter() {
    if (this.targetName.equals("locationX") || this.targetName.equals("locationY") || this.targetName.equals("locationZ") || this.targetName.equals("rotationX") || this.targetName.equals("rotationY") //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        || this.targetName.equals("rotationZ")) { //$NON-NLS-1$
      return true;
    }
    return false;
  }
}
