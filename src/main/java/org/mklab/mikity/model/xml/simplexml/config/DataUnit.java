package org.mklab.mikity.model.xml.simplexml.config;

import org.simpleframework.xml.Attribute;


/**
 * Class DataUnit.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:28 $
 */
public class DataUnit implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** _angle */
  @Attribute(name="angle")
  private java.lang.String _angle;

  /** _length */
  @Attribute(name="length")
  private java.lang.String _length;

  /**
   * Returns the value of field 'angle'.
   * 
   * @return the value of field 'angle'.
   */
  public java.lang.String getAngle() {
    return this._angle;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this._angle == null) ? 0 : this._angle.hashCode());
    result = prime * result + ((this._length == null) ? 0 : this._length.hashCode());
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
    DataUnit other = (DataUnit)obj;
    if (this._angle == null) {
      if (other._angle != null) {
        return false;
      }
    } else if (!this._angle.equals(other._angle)) {
      return false;
    }
    if (this._length == null) {
      if (other._length != null) {
        return false;
      }
    } else if (!this._length.equals(other._length)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of field 'length'.
   * 
   * @return the value of field 'length'.
   */
  public java.lang.String getLength() {
    return this._length;
  }

  /**
   * Sets the value of field 'angle'.
   * 
   * @param angle the value of field 'angle'.
   */
  public void setAngle(java.lang.String angle) {
    this._angle = angle;
  }

  /**
   * Sets the value of field 'length'.
   * 
   * @param length the value of field 'length'.
   */
  public void setLength(java.lang.String length) {
    this._length = length;
  }
}
