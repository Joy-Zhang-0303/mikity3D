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

  /** target */
  @Attribute(name="target")
  private String targetName;

  /** constant value */
  @Attribute(name="const")
  private double constantValue;

  /** column number */
  @Attribute(name="column")
  private int columnNumber;

  //private boolean hasConstant;

  //private boolean hasColumnNumber;

  //private boolean hasDhParameter;

  //private boolean hasCoordinateParameter;

  /**
   * Returns the value of field 'column'.
   * 
   * @return the value of field 'column'.
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
    //result = prime * result + (this.hasColumnNumber ? 1231 : 1237);
    //result = prime * result + (this.hasConstant ? 1231 : 1237);
    //result = prime * result + (this.hasDhParameter ? 1231 : 1237);
    //result = prime * result + (this.hasCoordinateParameter ? 1231 : 1237);
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
//    if (this.hasColumnNumber != other.hasColumnNumber) {
//      return false;
//    }
//    if (this.hasConstant != other.hasConstant) {
//      return false;
//    }
//    if (this.hasDhParameter != other.hasDhParameter) {
//      return false;
//    }
//    if (this.hasCoordinateParameter != other.hasCoordinateParameter) {
//      return false;
//    }
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
   * Returns the value of field 'const'.
   * 
   * @return the value of field 'const'.
   */
  public double getConstantValue() {
    return this.constantValue;
  }

  /**
   * Returns the value of field 'target'.
   * 
   * @return the value of field 'target'.
   */
  public String getTargetName() {
    return this.targetName;
  }

  /**
   * Method hasColumn
   * 
   * @return has_colimn
   */
  public boolean hasColumnNumber() {
    if (this.columnNumber != 0) {
      return true;
    }
    return false;
  }

  /**
   * Method hasConst
   * 
   * @return has_const
   */
  public boolean hasConstantValue() {
    if (this.constantValue != 0.0) {
      return true;
    }
    return false;
  }

  /**
   * Sets the value of field 'column'.
   * 
   * @param column the value of field 'column'.
   */
  public void setColumnNumber(int column) {
    this.columnNumber = column;
  }

  /**
   * Sets the value of field 'const'.
   * 
   * @param constantValue 定数
   */
  public void setConstantValue(double constantValue) {
    this.constantValue = constantValue;
  }

  /**
   * Sets the value of field 'target'.
   * 
   * @param target the value of field 'target'.
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
