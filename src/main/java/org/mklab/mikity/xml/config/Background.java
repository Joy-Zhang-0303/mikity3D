package org.mklab.mikity.xml.config;

import javax.xml.bind.annotation.XmlAttribute;


/**
 * Class Background.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class Background implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * Field _color
   */
  @XmlAttribute
  private java.lang.String _color;
  
//  /**
//   * Note: hashCode() has not been overriden
//   * 
//   * @param obj オブジェクト
//   */
//  @Override
//  public boolean equals(java.lang.Object obj) {
//    if (this == obj) return true;
//
//    if (obj instanceof Background) {
//
//      Background temp = (Background)obj;
//      if (this._color != null) {
//        if (temp._color == null) return false;
//        else if (!(this._color.equals(temp._color))) return false;
//      } else if (temp._color != null) return false;
//      return true;
//    }
//    return false;
//  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this._color == null) ? 0 : this._color.hashCode());
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
    if (this._color == null) {
      if (other._color != null) {
        return false;
      }
    } else if (!this._color.equals(other._color)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of field 'color'.
   * 
   * @return the value of field 'color'.
   */
  public java.lang.String loadColor() {
    return this._color;
  }

  /**
   * Sets the value of field 'color'.
   * 
   * @param color the value of field 'color'.
   */
  public void setColor(java.lang.String color) {
    this._color = color;
  }
}
