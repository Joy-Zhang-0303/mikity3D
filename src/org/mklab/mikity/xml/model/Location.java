/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Location.java,v 1.2 2007/11/19 10:39:36 morimune Exp $
 */

package org.mklab.mikity.xml.model;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlAttribute;


/**
 * Class Location.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/19 10:39:36 $
 */
public class Location implements java.io.Serializable {

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

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
  public Location() {
    super();
  } // -- org.mklab.mikity.xml.Location()


  /**
   * 新しく生成された<code>Location</code>オブジェクトを初期化します。
   * @param x
   * @param y
   * @param z
   */
  public Location(float x, float y, float z) {
    super();
    this._has_x = true;
    this._has_y = true;
    this._has_z = true;
    this._x = x;
    this._y = y;
    this._z = z;
  } // -- org.mklab.mikity.xml.Location()

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
   * Method deleteY
   */
  public void deleteY() {
    this._has_y = false;
  } // -- void deleteY()

  /**
   * Method deleteZ
   */
  public void deleteZ() {
    this._has_z = false;
  } // -- void deleteZ()

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj
   */
  @Override
  public boolean equals(java.lang.Object obj) {
    if (this == obj) return true;

    if (obj instanceof Location) {

      Location temp = (Location)obj;
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
   * Returns the value of field 'y'.
   * 
   * @return the value of field 'y'.
   */
  public float loadY() {
    return this._y;
  } // -- float getY()

  /**
   * Returns the value of field 'z'.
   * 
   * @return the value of field 'z'.
   */
  public float loadZ() {
    return this._z;
  } // -- float getZ()

  /**
   * Method hasX
   * 
   * @return has_x
   */
  public boolean hasX() {
    return this._has_x;
  } // -- boolean hasX()

  /**
   * Method hasY
   * 
   * @return has_y
   */
  public boolean hasY() {
    return this._has_y;
  } // -- boolean hasY()

  /**
   * Method hasZ
   * 
   * @return has_z
   */
  public boolean hasZ() {
    return this._has_z;
  } // -- boolean hasZ()

  /**
   * Sets the value of field 'x'.
   * 
   * @param x
   *        the value of field 'x'.
   */
  public void setX(float x) {
    this._x = x;
    this._has_x = true;
  } // -- void setX(float)

  /**
   * Sets the value of field 'y'.
   * 
   * @param y
   *        the value of field 'y'.
   */
  public void setY(float y) {
    this._y = y;
    this._has_y = true;
  } // -- void setY(float)

  /**
   * Sets the value of field 'z'.
   * 
   * @param z
   *        the value of field 'z'.
   */
  public void setZ(float z) {
    this._z = z;
    this._has_z = true;
  } // -- void setZ(float)
}
