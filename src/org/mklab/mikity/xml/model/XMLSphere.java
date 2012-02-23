/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: XMLSphere.java,v 1.2 2007/11/20 02:51:56 morimune Exp $
 */

package org.mklab.mikity.xml.model;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


/**
 * Class XMLSphere.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/20 02:51:56 $
 */
public class XMLSphere implements java.io.Serializable {

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Field _r
   */
  @XmlAttribute
  private float _r;

  /**
   * keeps track of state for field: _r
   */
  private boolean _has_r;

  /**
   * Field _div
   */
  @XmlAttribute
  private int _div;

  /**
   * keeps track of state for field: _div
   */
  private boolean _has_div;

  /**
   * Field _color
   */
  @XmlAttribute
  private java.lang.String _color;

  /**
   * Field _transparent
   */
  private boolean _transparent;

  /**
   * keeps track of state for field: _transparent
   */
  private boolean _has_transparent;

  /**
   * Field _rotation
   */
  @XmlElement
  private org.mklab.mikity.xml.model.Rotation _rotation;

  /**
   * Field _location
   */
  @XmlElement
  private org.mklab.mikity.xml.model.Location _location;

  /**
   * Field propertyChangeListeners
   */
  private java.util.Vector<PropertyChangeListener> propertyChangeListeners;

  // ----------------/
  // - Constructors -/
  // ----------------/

  /**
   * コンストラクター
   */
  public XMLSphere() {
    super();
    this.propertyChangeListeners = new Vector<PropertyChangeListener>();
  } // -- org.mklab.mikity.xml.XMLSphere()

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Method addPropertyChangeListenerRegisters a PropertyChangeListener with this class.
   * 
   * @param pcl The PropertyChangeListener to register.
   */
  public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
    this.propertyChangeListeners.addElement(pcl);
  } // -- void addPropertyChangeListener(java.beans.PropertyChangeListener)

  /**
   * Method deleteDiv
   */
  public void deleteDiv() {
    this._has_div = false;
  } // -- void deleteDiv()

  /**
   * Method deleteR
   */
  public void deleteR() {
    this._has_r = false;
  } // -- void deleteR()

  /**
   * Method deleteTransparent
   */
  public void deleteTransparent() {
    this._has_transparent = false;
  } // -- void deleteTransparent()

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj オブジェクト
   */
  @Override
  public boolean equals(java.lang.Object obj) {
    if (this == obj) return true;

    if (obj instanceof XMLSphere) {

      XMLSphere temp = (XMLSphere)obj;
      if (this._r != temp._r) return false;
      if (this._has_r != temp._has_r) return false;
      if (this._div != temp._div) return false;
      if (this._has_div != temp._has_div) return false;
      if (this._color != null) {
        if (temp._color == null) return false;
        else if (!(this._color.equals(temp._color))) return false;
      } else if (temp._color != null) return false;
      if (this._transparent != temp._transparent) return false;
      if (this._has_transparent != temp._has_transparent) return false;
      if (this._rotation != null) {
        if (temp._rotation == null) return false;
        else if (!(this._rotation.equals(temp._rotation))) return false;
      } else if (temp._rotation != null) return false;
      if (this._location != null) {
        if (temp._location == null) return false;
        else if (!(this._location.equals(temp._location))) return false;
      } else if (temp._location != null) return false;
      return true;
    }
    return false;
  } // -- boolean equals(java.lang.Object)

  /**
   * Returns the value of field 'color'.
   * 
   * @return the value of field 'color'.
   */
  public java.lang.String loadColor() {
    return this._color;
  } // -- java.lang.String getColor()

  /**
   * Returns the value of field 'div'.
   * 
   * @return the value of field 'div'.
   */
  public int loadDiv() {
    return this._div;
  } // -- int getDiv()

  /**
   * Returns the value of field 'location'.
   * 
   * @return the value of field 'location'.
   */
  public org.mklab.mikity.xml.model.Location loadLocation() {
    return this._location;
  } // -- org.mklab.mikity.xml.Location getLocation()

  /**
   * Returns the value of field 'r'.
   * 
   * @return the value of field 'r'.
   */
  public float loadR() {
    return this._r;
  } // -- float getR()

  /**
   * Returns the value of field 'rotation'.
   * 
   * @return the value of field 'rotation'.
   */
  public org.mklab.mikity.xml.model.Rotation loadRotation() {
    return this._rotation;
  } // -- org.mklab.mikity.xml.Rotation getRotation()

  /**
   * Returns the value of field 'transparent'.
   * 
   * @return the value of field 'transparent'.
   */
  public boolean loadTransparent() {
    return this._transparent;
  } // -- boolean getTransparent()

  /**
   * Method hasDiv
   * 
   * @return has_div
   */
  public boolean hasDiv() {
    return this._has_div;
  } // -- boolean hasDiv()

  /**
   * Method hasR
   * 
   * @return has_r
   */
  public boolean hasR() {
    return this._has_r;
  } // -- boolean hasR()

  /**
   * Method hasTransparent
   * 
   * @return has_transparent
   */
  public boolean hasTransparent() {
    return this._has_transparent;
  } // -- boolean hasTransparent()

  /**
   * Method notifyPropertyChangeListenersNotifies all registered PropertyChangeListeners when a bound property's value changes.
   * 
   * @param fieldName the name of the property that has changed.
   * @param newValue the new value of the property.
   * @param oldValue the old value of the property.
   */
  protected void notifyPropertyChangeListeners(java.lang.String fieldName, java.lang.Object oldValue, java.lang.Object newValue) {
    if (this.propertyChangeListeners == null) return;
    java.beans.PropertyChangeEvent event = new java.beans.PropertyChangeEvent(this, fieldName, oldValue, newValue);

    for (int i = 0; i < this.propertyChangeListeners.size(); i++) {
      (this.propertyChangeListeners.elementAt(i)).propertyChange(event);
    }
  } // -- void notifyPropertyChangeListeners(java.lang.String, java.lang.Object,

  // java.lang.Object)

  /**
   * Method removePropertyChangeListenerRemoves the given PropertyChangeListener from this classes list of ProperyChangeListeners.
   * 
   * @param pcl The PropertyChangeListener to remove.
   * @return true if the given PropertyChangeListener was removed.
   */
  public boolean removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
    return this.propertyChangeListeners.removeElement(pcl);
  } // -- boolean

  // removePropertyChangeListener(java.beans.PropertyChangeListener)

  /**
   * Sets the value of field 'color'.
   * 
   * @param color the value of field 'color'.
   */
  public void setColor(java.lang.String color) {
    this._color = color;
  } // -- void setColor(java.lang.String)

  /**
   * Sets the value of field 'div'.
   * 
   * @param div the value of field 'div'.
   */
  public void setDiv(int div) {
    this._div = div;
    this._has_div = true;
  } // -- void setDiv(int)

  /**
   * Sets the value of field 'location'.
   * 
   * @param location the value of field 'location'.
   */
  public void setLocation(org.mklab.mikity.xml.model.Location location) {
    this._location = location;
  } // -- void setLocation(org.mklab.mikity.xml.Location)

  /**
   * Sets the value of field 'r'.
   * 
   * @param r the value of field 'r'.
   */
  public void setR(float r) {
    this._r = r;
    this._has_r = true;
  } // -- void setR(float)

  /**
   * Sets the value of field 'rotation'.
   * 
   * @param rotation the value of field 'rotation'.
   */
  public void setRotation(org.mklab.mikity.xml.model.Rotation rotation) {
    this._rotation = rotation;
  } // -- void setRotation(org.mklab.mikity.xml.Rotation)

  /**
   * Sets the value of field 'transparent'.
   * 
   * @param transparent the value of field 'transparent'.
   */
  public void setTransparent(boolean transparent) {
    this._transparent = transparent;
    this._has_transparent = true;
  } // -- void setTransparent(boolean)

}
