package org.mklab.mikity.model.xml.simplexml.model;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Class XMLCone.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/20 02:51:56 $
 */
@Root(name="_XMLConeList")
public class XMLCone implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** _r */
  @Attribute
  private float _r;

  /** keeps track of state for field: _r */
  private boolean _has_r;

  /** _height */
  @Attribute
  private float _height;

  /** keeps track of state for field: _height */
  private boolean _has_height;

  /** _div */
  @Attribute
  private int _div;

  /** Keeps track of state for field: _div */
  private boolean _has_div;

  /** _color */
  @Attribute
  private java.lang.String _color;

  /**_transparent */
  private boolean _transparent;

  /** keeps track of state for field: _transparent */
  private boolean _has_transparent;

  /** _rotation */
  @Element
  private org.mklab.mikity.model.xml.simplexml.model.Rotation _rotation;

  /** _location */
  @Element
  private org.mklab.mikity.model.xml.simplexml.model.Location _location;

  /** propertyChangeListeners */
  private java.util.Vector<PropertyChangeListener> propertyChangeListeners;

  /**
   * コンストラクター
   */
  public XMLCone() {
    super();
    this.propertyChangeListeners = new Vector<PropertyChangeListener>();
  }
  
  /**
   * Method addPropertyChangeListenerRegisters a PropertyChangeListener with this class.
   * 
   * @param pcl The PropertyChangeListener to register.
   */
  public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
    this.propertyChangeListeners.addElement(pcl);
  }

  /**
   * Method deleteDiv
   */
  public void deleteDiv() {
    this._has_div = false;
  }

  /**
   * Method deleteHeight
   */
  public void deleteHeight() {
    this._has_height = false;
  }

  /**
   * Method deleteR
   */
  public void deleteR() {
    this._has_r = false;
  }

  /**
   * Method deleteTransparent
   */
  public void deleteTransparent() {
    this._has_transparent = false;
  }

  /**
   * Returns the value of field 'color'.
   * 
   * @return the value of field 'color'.
   */
  public java.lang.String getColor() {
    return this._color;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this._color == null) ? 0 : this._color.hashCode());
    result = prime * result + this._div;
    result = prime * result + (this._has_div ? 1231 : 1237);
    result = prime * result + (this._has_height ? 1231 : 1237);
    result = prime * result + (this._has_r ? 1231 : 1237);
    result = prime * result + (this._has_transparent ? 1231 : 1237);
    result = prime * result + Float.floatToIntBits(this._height);
    result = prime * result + ((this._location == null) ? 0 : this._location.hashCode());
    result = prime * result + Float.floatToIntBits(this._r);
    result = prime * result + ((this._rotation == null) ? 0 : this._rotation.hashCode());
    result = prime * result + (this._transparent ? 1231 : 1237);
    result = prime * result + ((this.propertyChangeListeners == null) ? 0 : this.propertyChangeListeners.hashCode());
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
    XMLCone other = (XMLCone)obj;
    if (this._color == null) {
      if (other._color != null) {
        return false;
      }
    } else if (!this._color.equals(other._color)) {
      return false;
    }
    if (this._div != other._div) {
      return false;
    }
    if (this._has_div != other._has_div) {
      return false;
    }
    if (this._has_height != other._has_height) {
      return false;
    }
    if (this._has_r != other._has_r) {
      return false;
    }
    if (this._has_transparent != other._has_transparent) {
      return false;
    }
    if (Float.floatToIntBits(this._height) != Float.floatToIntBits(other._height)) {
      return false;
    }
    if (this._location == null) {
      if (other._location != null) {
        return false;
      }
    } else if (!this._location.equals(other._location)) {
      return false;
    }
    if (Float.floatToIntBits(this._r) != Float.floatToIntBits(other._r)) {
      return false;
    }
    if (this._rotation == null) {
      if (other._rotation != null) {
        return false;
      }
    } else if (!this._rotation.equals(other._rotation)) {
      return false;
    }
    if (this._transparent != other._transparent) {
      return false;
    }
    if (this.propertyChangeListeners == null) {
      if (other.propertyChangeListeners != null) {
        return false;
      }
    } else if (!this.propertyChangeListeners.equals(other.propertyChangeListeners)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of field 'div'.
   * 
   * @return the value of field 'div'.
   */
  public int getDiv() {
    return this._div;
  }

  /**
   * Returns the value of field 'height'.
   * 
   * @return the value of field 'height'.
   */
  public float getHeight() {
    return this._height;
  }

  /**
   * Returns the value of field 'location'.
   * 
   * @return the value of field 'location'.
   */
  public org.mklab.mikity.model.xml.simplexml.model.Location getLocation() {
    return this._location;
  }

  /**
   * Returns the value of field 'r'.
   * 
   * @return the value of field 'r'.
   */
  public float getRadisu() {
    return this._r;
  }

  /**
   * Returns the value of field 'rotation'.
   * 
   * @return the value of field 'rotation'.
   */
  public org.mklab.mikity.model.xml.simplexml.model.Rotation getRotation() {
    return this._rotation;
  }

  /**
   * Returns the value of field 'transparent'.
   * 
   * @return the value of field 'transparent'.
   */
  public boolean getTransparent() {
    return this._transparent;
  }

  /**
   * Method hasDiv
   * 
   * @return has_div
   */
  public boolean hasDiv() {
    return this._has_div;
  }

  /**
   * Method hasHeight
   * 
   * @return has_height
   */
  public boolean hasHeight() {
    return this._has_height;
  }

  /**
   * Method hasR
   * 
   * @return has_r
   */
  public boolean hasRadisu() {
    return this._has_r;
  }

  /**
   * Method hasTransparent
   * 
   * @return has_tranparent
   */
  public boolean hasTransparent() {
    return this._has_transparent;
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
   * Sets the value of field 'div'.
   * 
   * @param div the value of field 'div'.
   */
  public void setDiv(int div) {
    this._div = div;
    this._has_div = true;
  }

  /**
   * Sets the value of field 'height'.
   * 
   * @param height the value of field 'height'.
   */
  public void setHeight(float height) {
    this._height = height;
    this._has_height = true;
  }

  /**
   * Sets the value of field 'location'.
   * 
   * @param location the value of field 'location'.
   */
  public void setLocation(org.mklab.mikity.model.xml.simplexml.model.Location location) {
    this._location = location;
  }

  /**
   * Sets the value of field 'r'.
   * 
   * @param r the value of field 'r'.
   */
  public void setRadius(float r) {
    this._r = r;
    this._has_r = true;
  }

  /**
   * Sets the value of field 'rotation'.
   * 
   * @param rotation the value of field 'rotation'.
   */
  public void setRotation(org.mklab.mikity.model.xml.simplexml.model.Rotation rotation) {
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
}
