/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: View.java,v 1.1 2007/08/03 03:30:27 morimune Exp $
 */

package org.mklab.mikity.xml.config;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlAttribute;


/**
 * Class View.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class View implements java.io.Serializable {

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Field _xrotate
   */
  @XmlAttribute
  private double _xrotate;

  /**
   * keeps track of state for field: _xrotate
   */
  private boolean _has_xrotate;

  /**
   * Field _yrotate
   */
  @XmlAttribute
  private double _yrotate;

  /**
   * keeps track of state for field: _yrotate
   */
  private boolean _has_yrotate;

  /**
   * Field _zrotate
   */
  @XmlAttribute
  private double _zrotate;

  /**
   * keeps track of state for field: _zrotate
   */
  private boolean _has_zrotate;

  /**
   * Field _x
   */
  @XmlAttribute
  private float _x;

  /**
   * keeps track of state for field: _x
   */
  private boolean _has_x;

  /**
   * Field _y
   */
  @XmlAttribute
  private float _y;

  /**
   * keeps track of state for field: _y
   */
  private boolean _has_y;

  /**
   * Field _z
   */
  @XmlAttribute
  private float _z;

  /**
   * keeps track of state for field: _z
   */
  private boolean _has_z;

  // ----------------/
  // - Constructors -/
  // ----------------/

  /**
   * コンストラクター
   */
  public View() {
    super();
  } // -- org.mklab.mikity.xml.View()

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Method deleteX
   */
  public void deleteX() {
    this._has_x = false;
  } // -- void deleteX()

  /**
   * Method deleteXrotate
   */
  public void deleteXrotate() {
    this._has_xrotate = false;
  } // -- void deleteXrotate()

  /**
   * Method deleteY
   */
  public void deleteY() {
    this._has_y = false;
  } // -- void deleteY()

  /**
   * Method deleteYrotate
   */
  public void deleteYrotate() {
    this._has_yrotate = false;
  } // -- void deleteYrotate()

  /**
   * Method deleteZ
   */
  public void deleteZ() {
    this._has_z = false;
  } // -- void deleteZ()

  /**
   * Method deleteZrotate
   */
  public void deleteZrotate() {
    this._has_zrotate = false;
  } // -- void deleteZrotate()

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj
   */
  @Override
  public boolean equals(java.lang.Object obj) {
    if (this == obj) return true;

    if (obj instanceof View) {

      View temp = (View)obj;
      if (this._xrotate != temp._xrotate) return false;
      if (this._has_xrotate != temp._has_xrotate) return false;
      if (this._yrotate != temp._yrotate) return false;
      if (this._has_yrotate != temp._has_yrotate) return false;
      if (this._zrotate != temp._zrotate) return false;
      if (this._has_zrotate != temp._has_zrotate) return false;
      if (this._x != temp._x) return false;
      if (this._has_x != temp._has_x) return false;
      if (this._y != temp._y) return false;
      if (this._has_y != temp._has_y) return false;
      if (this._z != temp._z) return false;
      if (this._has_z != temp._has_z) return false;
      return true;
    }
    return false;
  } // -- boolean equals(java.lang.Object)

  /**
   * Returns the value of field 'x'.
   * 
   * @return the value of field 'x'.
   */
  public float loadX() {
    return this._x;
  } // -- float getX()

  /**
   * Returns the value of field 'xrotate'.
   * 
   * @return the value of field 'xrotate'.
   */
  public double loadXrotate() {
    return this._xrotate;
  } // -- double getXrotate()

  /**
   * Returns the value of field 'y'.
   * 
   * @return the value of field 'y'.
   */
  public float loadY() {
    return this._y;
  } // -- float getY()

  /**
   * Returns the value of field 'yrotate'.
   * 
   * @return the value of field 'yrotate'.
   */
  public double loadYrotate() {
    return this._yrotate;
  } // -- double getYrotate()

  /**
   * Returns the value of field 'z'.
   * 
   * @return the value of field 'z'.
   */
  public float loadZ() {
    return this._z;
  } // -- float getZ()

  /**
   * Returns the value of field 'zrotate'.
   * 
   * @return the value of field 'zrotate'.
   */
  public double loadZrotate() {
    return this._zrotate;
  } // -- double getZrotate()

  /**
   * Method hasX
   * 
   * @return has_x
   */
  public boolean hasX() {
    return this._has_x;
  } // -- boolean hasX()

  /**
   * Method hasXrotate
   * 
   * @return has_xrotate
   */
  public boolean hasXrotate() {
    return this._has_xrotate;
  } // -- boolean hasXrotate()

  /**
   * Method hasY
   * 
   * @return has_y
   */
  public boolean hasY() {
    return this._has_y;
  } // -- boolean hasY()

  /**
   * Method hasYrotate
   * 
   * @return has_yrotate
   */
  public boolean hasYrotate() {
    return this._has_yrotate;
  } // -- boolean hasYrotate()

  /**
   * Method hasZ
   * 
   * @return has_z
   */
  public boolean hasZ() {
    return this._has_z;
  } // -- boolean hasZ()

  /**
   * Method hasZrotate
   * 
   * @return has_zrotate
   */
  public boolean hasZrotate() {
    return this._has_zrotate;
  } // -- boolean hasZrotate()

  /**
   * Sets the value of field 'x'.
   * 
   * @param x the value of field 'x'.
   */
  public void setX(float x) {
    this._x = x;
    this._has_x = true;
  } // -- void setX(float)

  /**
   * Sets the value of field 'xrotate'.
   * 
   * @param xrotate the value of field 'xrotate'.
   */
  public void setXrotate(double xrotate) {
    this._xrotate = xrotate;
    this._has_xrotate = true;
  } // -- void setXrotate(double)

  /**
   * Sets the value of field 'y'.
   * 
   * @param y the value of field 'y'.
   */
  public void setY(float y) {
    this._y = y;
    this._has_y = true;
  } // -- void setY(float)

  /**
   * Sets the value of field 'yrotate'.
   * 
   * @param yrotate the value of field 'yrotate'.
   */
  public void setYrotate(double yrotate) {
    this._yrotate = yrotate;
    this._has_yrotate = true;
  } // -- void setYrotate(double)

  /**
   * Sets the value of field 'z'.
   * 
   * @param z the value of field 'z'.
   */
  public void setZ(float z) {
    this._z = z;
    this._has_z = true;
  } // -- void setZ(float)

  /**
   * Sets the value of field 'zrotate'.
   * 
   * @param zrotate the value of field 'zrotate'.
   */
  public void setZrotate(double zrotate) {
    this._zrotate = zrotate;
    this._has_zrotate = true;
  } // -- void setZrotate(double)
}
