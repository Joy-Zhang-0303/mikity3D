/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: XMLBox.java,v 1.2 2007/11/20 02:51:56 morimune Exp $
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
 * Class XMLBox.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/20 02:51:56 $
 */
public class XMLBox implements java.io.Serializable {

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Field _xsize
   */
  @XmlAttribute
  private float _xsize;

  /**
   * keeps track of state for field: _xsize
   */
  private boolean _has_xsize;

  /**
   * Field _ysize
   */
  @XmlAttribute
  private float _ysize;

  /**
   * keeps track of state for field: _ysize
   */
  private boolean _has_ysize;

  /**
   * Field _zsize
   */
  @XmlAttribute
  private float _zsize;

  /**
   * keeps track of state for field: _zsize
   */
  private boolean _has_zsize;

  /**
   * Field _color
   */
  @XmlAttribute
  private java.lang.String _color;

  /**
   * Field _transparent
   */
  @XmlAttribute
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
  public XMLBox() {
    super();
    this.propertyChangeListeners = new Vector<PropertyChangeListener>();
  } 

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
  } 

  /**
   * Method deleteTransparent
   */
  public void deleteTransparent() {
    this._has_transparent = false;
  }

  /**
   * Method deleteXsize
   */
  public void deleteXsize() {
    this._has_xsize = false;
  }

  /**
   * Method deleteYsize
   */
  public void deleteYsize() {
    this._has_ysize = false;
  }

  /**
   * Method deleteZsize
   */
  public void deleteZsize() {
    this._has_zsize = false;
  }

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj オブジェクト
   */
  @Override
  public boolean equals(java.lang.Object obj) {
    if (this == obj) return true;

    if (obj instanceof XMLBox) {

      XMLBox temp = (XMLBox)obj;
      if (this._xsize != temp._xsize) return false;
      if (this._has_xsize != temp._has_xsize) return false;
      if (this._ysize != temp._ysize) return false;
      if (this._has_ysize != temp._has_ysize) return false;
      if (this._zsize != temp._zsize) return false;
      if (this._has_zsize != temp._has_zsize) return false;
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
   * Returns the value of field 'location'.
   * 
   * @return the value of field 'location'.
   */
  public org.mklab.mikity.xml.model.Location loadLocation() {
    return this._location;
  }

  /**
   * Returns the value of field 'rotation'.
   * 
   * @return the value of field 'rotation'.
   */
  public org.mklab.mikity.xml.model.Rotation loadRotation() {
    return this._rotation;
  }

  /**
   * Returns the value of field 'transparent'.
   * 
   * @return the value of field 'transparent'.
   */
  public boolean loadTransparent() {
    return this._transparent;
  }

  /**
   * Returns the value of field 'xsize'.
   * 
   * @return the value of field 'xsize'.
   */
  public float loadXsize() {
    return this._xsize;
  }

  /**
   * Returns the value of field 'ysize'.
   * 
   * @return the value of field 'ysize'.
   */
  public float loadYsize() {
    return this._ysize;
  }

  /**
   * Returns the value of field 'zsize'.
   * 
   * @return the value of field 'zsize'.
   */
  public float loadZsize() {
    return this._zsize;
  }

  /**
   * Method hasTransparent
   * 
   * @return has_transparent
   */
  public boolean hasTransparent() {
    return this._has_transparent;
  }

  /**
   * Method hasXsize
   * 
   * @return has_xsize
   */
  public boolean hasXsize() {
    return this._has_xsize;
  }

  /**
   * Method hasYsize
   * 
   * @return has_ysize
   */
  public boolean hasYsize() {
    return this._has_ysize;
  }

  /**
   * Method hasZsize
   * 
   * @return has_zsize
   */
  public boolean hasZsize() {
    return this._has_zsize;
  }

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
  }

  /**
   * Method removePropertyChangeListenerRemoves the given PropertyChangeListener from this classes list of ProperyChangeListeners.
   * 
   * @param pcl The PropertyChangeListener to remove.
   * @return true if the given PropertyChangeListener was removed.
   */
  public boolean removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
    return this.propertyChangeListeners.removeElement(pcl);
  }

  /**
   * Sets the value of field 'color'.
   * 
   * @param color the value of field 'color'.
   */
  public void setColor(java.lang.String color) {
    this._color = color;
  }

  /**
   * Sets the value of field 'location'.
   * 
   * @param location the value of field 'location'.
   */
  public void setLocation(org.mklab.mikity.xml.model.Location location) {
    this._location = location;
  }

  /**
   * Sets the value of field 'rotation'.
   * 
   * @param rotation the value of field 'rotation'.
   */
  public void setRotation(org.mklab.mikity.xml.model.Rotation rotation) {
    this._rotation = rotation;
  }

  /**
   * Sets the value of field 'transparent'.
   * 
   * @param transparent the value of field 'transparent'.
   */
  public void setTransparent(boolean transparent) {
    this._transparent = transparent;
    this._has_transparent = true;
  }

  /**
   * Sets the value of field 'xsize'.
   * 
   * @param xsize the value of field 'xsize'.
   */
  public void setXsize(float xsize) {
    this._xsize = xsize;
    this._has_xsize = true;
  }

  /**
   * Sets the value of field 'ysize'.
   * 
   * @param ysize the value of field 'ysize'.
   */
  public void setYsize(float ysize) {
    this._ysize = ysize;
    this._has_ysize = true;
  }

  /**
   * Sets the value of field 'zsize'.
   * 
   * @param zsize the value of field 'zsize'.
   */
  public void setZsize(float zsize) {
    this._zsize = zsize;
    this._has_zsize = true;
  }

}
