/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Rotation.java,v 1.1 2007/08/03 03:30:27 morimune Exp $
 */

package org.mklab.mikity.xml.model;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlAttribute;


/**
 * Class Rotation.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class Rotation implements java.io.Serializable {

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
  private float _xrotate;

  /**
   * keeps track of state for field: _xrotate
   */
  private boolean _has_xrotate;

  /**
   * Field _yrotate
   */
  @XmlAttribute
  private float _yrotate;

  /**
   * keeps track of state for field: _yrotate
   */
  private boolean _has_yrotate;

  /**
   * Field _zrotate
   */
  @XmlAttribute
  private float _zrotate;

  /**
   * keeps track of state for field: _zrotate
   */
  private boolean _has_zrotate;

  // ----------------/
  // - Constructors -/
  // ----------------/

  /**
   * コンストラクター
   */
  public Rotation() {
    super();
  } // -- org.mklab.mikity.xml.Rotation()

  /**
   * 新しく生成された<code>Rotation</code>オブジェクトを初期化します。
   * 
   * @param x
   * @param y
   * @param z
   */
  public Rotation(float x, float y, float z) {
    super();
    this._xrotate = x;
    this._yrotate = y;
    this._zrotate = z;
  } // -- org.mklab.mikity.xml.Rotation()

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Method deleteXrotate
   */
  public void deleteXrotate() {
    this._has_xrotate = false;
  } // -- void deleteXrotate()

  /**
   * Method deleteYrotate
   */
  public void deleteYrotate() {
    this._has_yrotate = false;
  } // -- void deleteYrotate()

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

    if (obj instanceof Rotation) {

      Rotation temp = (Rotation)obj;
      if (this._xrotate != temp._xrotate) return false;
      if (this._has_xrotate != temp._has_xrotate) return false;
      if (this._yrotate != temp._yrotate) return false;
      if (this._has_yrotate != temp._has_yrotate) return false;
      if (this._zrotate != temp._zrotate) return false;
      if (this._has_zrotate != temp._has_zrotate) return false;
      return true;
    }
    return false;
  } // -- boolean equals(java.lang.Object)

  /**
   * Returns the value of field 'xrotate'.
   * 
   * @return the value of field 'xrotate'.
   */
  public float loadXrotate() {
    return this._xrotate;
  } // -- float getXrotate()

  /**
   * Returns the value of field 'yrotate'.
   * 
   * @return the value of field 'yrotate'.
   */
  public float loadYrotate() {
    return this._yrotate;
  } // -- float getYrotate()

  /**
   * Returns the value of field 'zrotate'.
   * 
   * @return the value of field 'zrotate'.
   */
  public float loadZrotate() {
    return this._zrotate;
  } // -- float getZrotate()

  /**
   * Method hasXrotate
   * 
   * @return has_xrotate
   */
  public boolean hasXrotate() {
    return this._has_xrotate;
  } // -- boolean hasXrotate()

  /**
   * Method hasYrotate
   * 
   * @return has_yrotate
   */
  public boolean hasYrotate() {
    return this._has_yrotate;
  } // -- boolean hasYrotate()

  /**
   * Method hasZrotate
   * 
   * @return has_zrotate
   */
  public boolean hasZrotate() {
    return this._has_zrotate;
  } // -- boolean hasZrotate()

  /**
   * Sets the value of field 'xrotate'.
   * 
   * @param xrotate
   *        the value of field 'xrotate'.
   */
  public void setXrotate(float xrotate) {
    this._xrotate = xrotate;
    this._has_xrotate = true;
  } // -- void setXrotate(float)

  /**
   * Sets the value of field 'yrotate'.
   * 
   * @param yrotate
   *        the value of field 'yrotate'.
   */
  public void setYrotate(float yrotate) {
    this._yrotate = yrotate;
    this._has_yrotate = true;
  } // -- void setYrotate(float)

  /**
   * Sets the value of field 'zrotate'.
   * 
   * @param zrotate
   *        the value of field 'zrotate'.
   */
  public void setZrotate(float zrotate) {
    this._zrotate = zrotate;
    this._has_zrotate = true;
  } // -- void setZrotate(float)
}
