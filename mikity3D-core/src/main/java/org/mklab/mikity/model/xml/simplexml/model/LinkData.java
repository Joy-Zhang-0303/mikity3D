package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * Class Linkdata.
 * 
 * @version $Revision: 1.3 $ $Date: 2008/02/03 04:25:09 $
 */
@Root(name="linkData")
public class LinkData implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** _target */
  @Attribute(name="target")
  private java.lang.String target;

  /** _const */
  @Attribute(name="const")
  private double constant;

  /** keeps track of state for field: _const */
  private boolean hasConstant;

  /** _column */
  @Attribute(name="column")
  private int column;

  /** keeps track of state for field: _column */
  private boolean hasColumn;

  private boolean hasDh;

  private boolean hasLink;

  /**
   * Method deleteColumn
   */
  public void deleteDataNumber() {
    this.hasColumn = false;
  }

  /**
   * Method deleteConst
   */
  public void deleteInitialValue() {
    this.hasConstant = false;
  }

  /**
   * Returns the value of field 'column'.
   * 
   * @return the value of field 'column'.
   */
  public int getDataNumber() {
    return this.column;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.column;
    long temp;
    temp = Double.doubleToLongBits(this.constant);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    result = prime * result + (this.hasColumn ? 1231 : 1237);
    result = prime * result + (this.hasConstant ? 1231 : 1237);
    result = prime * result + (this.hasDh ? 1231 : 1237);
    result = prime * result + (this.hasLink ? 1231 : 1237);
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
    if (this.column != other.column) {
      return false;
    }
    if (Double.doubleToLongBits(this.constant) != Double.doubleToLongBits(other.constant)) {
      return false;
    }
    if (this.hasColumn != other.hasColumn) {
      return false;
    }
    if (this.hasConstant != other.hasConstant) {
      return false;
    }
    if (this.hasDh != other.hasDh) {
      return false;
    }
    if (this.hasLink != other.hasLink) {
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
   * Returns the value of field 'const'.
   * 
   * @return the value of field 'const'.
   */
  public double getInitialValue() {
    return this.constant;
  }

  /**
   * Returns the value of field 'target'.
   * 
   * @return the value of field 'target'.
   */
  public java.lang.String getTargetName() {
    return this.target;
  }

  /**
   * Method hasColumn
   * 
   * @return has_colimn
   */
  public boolean hasDataNumber() {
    if (this.column != 0) {
      this.hasColumn = true;
    }
    return this.hasColumn;
  }

  /**
   * Method hasConst
   * 
   * @return has_const
   */
  public boolean hasInitialValue() {
    if (this.constant != 0.0) {
      this.hasConstant = true;
    }
    return this.hasConstant;
  }

  /**
   * Sets the value of field 'column'.
   * 
   * @param column the value of field 'column'.
   */
  public void setDataNumber(int column) {
    this.column = column;
    this.hasColumn = true;
  }

  /**
   * Sets the value of field 'const'.
   * 
   * @param _const コンスト
   */
  public void setInitialValue(double _const) {
    this.constant = _const;
    this.hasConstant = true;
  }

  /**
   * Sets the value of field 'target'.
   * 
   * @param target the value of field 'target'.
   */
  public void setTargetName(java.lang.String target) {
    this.target = target;
  }

  /**
   * @return DHを持つならばtrue
   */
  public boolean hasDHParameter() {
    if (this.target.equals("a") || this.target.equals("alpha") || this.target.equals("d") || this.target.equals("theta")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
      this.hasDh = true;
    }
    return this.hasDh;
  }

  /**
   * @return linkをもつならばtrue
   */
  public boolean hasCoordinateParameter() {
    if (this.target.equals("locationX") || this.target.equals("locationY") || this.target.equals("locationZ") || this.target.equals("rotationX") || this.target.equals("rotationY") //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        || this.target.equals("rotationZ")) { //$NON-NLS-1$
      this.hasLink = true;
    }
    return this.hasLink;
  }
}
