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
@Root(name="cone")
public class XMLCone implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** _r */
  @Attribute(name="r")
  private float radisu;

  /** keeps track of state for field: _r */
  private boolean hasRadisu;

  /** _height */
  @Attribute(name="height")
  private float hheight;

  /** keeps track of state for field: _height */
  private boolean hasHeight;

  /** _div */
  @Attribute(name="div")
  private int div;

  /** Keeps track of state for field: _div */
  private boolean hasDiv;

  /** _color */
  @Attribute(name="color")
  private java.lang.String color;

  /**_transparent */
  private boolean transparent;

  /** keeps track of state for field: _transparent */
  private boolean hasTransparent;

  /** _rotation */
  @Element(name="rotation")
  private org.mklab.mikity.model.xml.simplexml.model.Rotation rotation;

  /** _location */
  @Element(name="location")
  private org.mklab.mikity.model.xml.simplexml.model.Location location;

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
    this.hasDiv = false;
  }

  /**
   * Method deleteHeight
   */
  public void deleteHeight() {
    this.hasHeight = false;
  }

  /**
   * Method deleteR
   */
  public void deleteR() {
    this.hasRadisu = false;
  }

  /**
   * Method deleteTransparent
   */
  public void deleteTransparent() {
    this.hasTransparent = false;
  }

  /**
   * Returns the value of field 'color'.
   * 
   * @return the value of field 'color'.
   */
  public java.lang.String getColor() {
    return this.color;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.color == null) ? 0 : this.color.hashCode());
    result = prime * result + this.div;
    result = prime * result + (this.hasDiv ? 1231 : 1237);
    result = prime * result + (this.hasHeight ? 1231 : 1237);
    result = prime * result + (this.hasRadisu ? 1231 : 1237);
    result = prime * result + (this.hasTransparent ? 1231 : 1237);
    result = prime * result + Float.floatToIntBits(this.hheight);
    result = prime * result + ((this.location == null) ? 0 : this.location.hashCode());
    result = prime * result + Float.floatToIntBits(this.radisu);
    result = prime * result + ((this.rotation == null) ? 0 : this.rotation.hashCode());
    result = prime * result + (this.transparent ? 1231 : 1237);
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
    if (this.color == null) {
      if (other.color != null) {
        return false;
      }
    } else if (!this.color.equals(other.color)) {
      return false;
    }
    if (this.div != other.div) {
      return false;
    }
    if (this.hasDiv != other.hasDiv) {
      return false;
    }
    if (this.hasHeight != other.hasHeight) {
      return false;
    }
    if (this.hasRadisu != other.hasRadisu) {
      return false;
    }
    if (this.hasTransparent != other.hasTransparent) {
      return false;
    }
    if (Float.floatToIntBits(this.hheight) != Float.floatToIntBits(other.hheight)) {
      return false;
    }
    if (this.location == null) {
      if (other.location != null) {
        return false;
      }
    } else if (!this.location.equals(other.location)) {
      return false;
    }
    if (Float.floatToIntBits(this.radisu) != Float.floatToIntBits(other.radisu)) {
      return false;
    }
    if (this.rotation == null) {
      if (other.rotation != null) {
        return false;
      }
    } else if (!this.rotation.equals(other.rotation)) {
      return false;
    }
    if (this.transparent != other.transparent) {
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
    return this.div;
  }

  /**
   * Returns the value of field 'height'.
   * 
   * @return the value of field 'height'.
   */
  public float getHeight() {
    return this.hheight;
  }

  /**
   * Returns the value of field 'location'.
   * 
   * @return the value of field 'location'.
   */
  public org.mklab.mikity.model.xml.simplexml.model.Location getLocation() {
    return this.location;
  }

  /**
   * Returns the value of field 'r'.
   * 
   * @return the value of field 'r'.
   */
  public float getRadisu() {
    return this.radisu;
  }

  /**
   * Returns the value of field 'rotation'.
   * 
   * @return the value of field 'rotation'.
   */
  public org.mklab.mikity.model.xml.simplexml.model.Rotation getRotation() {
    return this.rotation;
  }

  /**
   * Returns the value of field 'transparent'.
   * 
   * @return the value of field 'transparent'.
   */
  public boolean getTransparent() {
    return this.transparent;
  }

  /**
   * Method hasDiv
   * 
   * @return has_div
   */
  public boolean hasDiv() {
    return this.hasDiv;
  }

  /**
   * Method hasHeight
   * 
   * @return has_height
   */
  public boolean hasHeight() {
    return this.hasHeight;
  }

  /**
   * Method hasR
   * 
   * @return has_r
   */
  public boolean hasRadisu() {
    return this.hasRadisu;
  }

  /**
   * Method hasTransparent
   * 
   * @return has_tranparent
   */
  public boolean hasTransparent() {
    return this.hasTransparent;
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
    this.color = color;
  }

  /**
   * Sets the value of field 'div'.
   * 
   * @param div the value of field 'div'.
   */
  public void setDiv(int div) {
    this.div = div;
    this.hasDiv = true;
  }

  /**
   * Sets the value of field 'height'.
   * 
   * @param height the value of field 'height'.
   */
  public void setHeight(float height) {
    this.hheight = height;
    this.hasHeight = true;
  }

  /**
   * Sets the value of field 'location'.
   * 
   * @param location the value of field 'location'.
   */
  public void setLocation(org.mklab.mikity.model.xml.simplexml.model.Location location) {
    this.location = location;
  }

  /**
   * Sets the value of field 'r'.
   * 
   * @param r the value of field 'r'.
   */
  public void setRadius(float r) {
    this.radisu = r;
    this.hasRadisu = true;
  }

  /**
   * Sets the value of field 'rotation'.
   * 
   * @param rotation the value of field 'rotation'.
   */
  public void setRotation(org.mklab.mikity.model.xml.simplexml.model.Rotation rotation) {
    this.rotation = rotation;
  }

  /**
   * Sets the value of field 'transparent'.
   * 
   * @param transparent the value of field 'transparent'.
   */
  public void setTransparent(boolean transparent) {
    this.transparent = transparent;
    this.hasTransparent = true;
  }
}
