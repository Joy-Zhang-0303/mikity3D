package org.mklab.mikity.model.xml.simplexml.config;

import org.simpleframework.xml.Attribute;


/**
 * Class DataUnit.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:28 $
 */
public class DataUnit implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** angle */
  @Attribute(name="angle")
  private String angle;

  /** length */
  @Attribute(name="length")
  private String length;

  /**
   * Returns the value of field 'angle'.
   * 
   * @return the value of field 'angle'.
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
    DataUnit other = (DataUnit)obj;
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
   * Returns the value of field 'length'.
   * 
   * @return the value of field 'length'.
   */
  public String getLength() {
    return this.length;
  }

  /**
   * Sets the value of field 'angle'.
   * 
   * @param angle the value of field 'angle'.
   */
  public void setAngle(String angle) {
    this.angle = angle;
  }

  /**
   * Sets the value of field 'length'.
   * 
   * @param length the value of field 'length'.
   */
  public void setLength(String length) {
    this.length = length;
  }
}
