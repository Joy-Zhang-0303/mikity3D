package org.mklab.mikity.model.xml.simplexml.model;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Class XMLBox.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/20 02:51:56 $
 */
@Root(name="box")
public class XMLBox implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** _xsize */
  @Attribute(name="xsize")
  private float _xsize;

  /** keeps track of state for field: _xsize */
  private boolean _has_xsize;

  /** _ysize */
  @Attribute(name="ysize")
  private float _ysize;

  /** keeps track of state for field: _ysize */
  private boolean _has_ysize;

  /** _zsize */
  @Attribute(name="zsize")
  private float _zsize;

  /** keeps track of state for field: _zsize */
  private boolean _has_zsize;

  /** _color */
  @Attribute(name="color")
  private java.lang.String _color;

  /** _transparent */
  @Attribute(name="transparent")
  private boolean _transparent;

  /** keeps track of state for field: _transparent */
  private boolean _has_transparent;

  /** _rotation */
  @Element(name="rotation", required=false)
  private org.mklab.mikity.model.xml.simplexml.model.Rotation _rotation;

  /** _location */
  @Element(name="location")
  private org.mklab.mikity.model.xml.simplexml.model.Location _location;

  /** propertyChangeListeners */
  private java.util.Vector<PropertyChangeListener> propertyChangeListeners;

  /**
   * コンストラクター
   */
  public XMLBox() {
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
    result = prime * result + (this._has_transparent ? 1231 : 1237);
    result = prime * result + (this._has_xsize ? 1231 : 1237);
    result = prime * result + (this._has_ysize ? 1231 : 1237);
    result = prime * result + (this._has_zsize ? 1231 : 1237);
    result = prime * result + ((this._location == null) ? 0 : this._location.hashCode());
    result = prime * result + ((this._rotation == null) ? 0 : this._rotation.hashCode());
    result = prime * result + (this._transparent ? 1231 : 1237);
    result = prime * result + Float.floatToIntBits(this._xsize);
    result = prime * result + Float.floatToIntBits(this._ysize);
    result = prime * result + Float.floatToIntBits(this._zsize);
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
    XMLBox other = (XMLBox)obj;
    if (this._color == null) {
      if (other._color != null) {
        return false;
      }
    } else if (!this._color.equals(other._color)) {
      return false;
    }
    if (this._has_transparent != other._has_transparent) {
      return false;
    }
    if (this._has_xsize != other._has_xsize) {
      return false;
    }
    if (this._has_ysize != other._has_ysize) {
      return false;
    }
    if (this._has_zsize != other._has_zsize) {
      return false;
    }
    if (this._location == null) {
      if (other._location != null) {
        return false;
      }
    } else if (!this._location.equals(other._location)) {
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
    if (Float.floatToIntBits(this._xsize) != Float.floatToIntBits(other._xsize)) {
      return false;
    }
    if (Float.floatToIntBits(this._ysize) != Float.floatToIntBits(other._ysize)) {
      return false;
    }
    if (Float.floatToIntBits(this._zsize) != Float.floatToIntBits(other._zsize)) {
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
   * Returns the value of field 'location'.
   * 
   * @return the value of field 'location'.
   */
  public org.mklab.mikity.model.xml.simplexml.model.Location getLocation() {
    return this._location;
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
   * Returns the value of field 'xsize'.
   * 
   * @return the value of field 'xsize'.
   */
  public float getXsize() {
    return this._xsize;
  }

  /**
   * Returns the value of field 'ysize'.
   * 
   * @return the value of field 'ysize'.
   */
  public float getYsize() {
    return this._ysize;
  }

  /**
   * Returns the value of field 'zsize'.
   * 
   * @return the value of field 'zsize'.
   */
  public float getZsize() {
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
  public void setLocation(org.mklab.mikity.model.xml.simplexml.model.Location location) {
    this._location = location;
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
