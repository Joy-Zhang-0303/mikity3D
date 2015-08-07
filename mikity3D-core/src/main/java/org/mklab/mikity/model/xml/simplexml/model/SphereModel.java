package org.mklab.mikity.model.xml.simplexml.model;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * 球を表すクラスです。
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/20 02:51:56 $
 */
@Root(name="sphere")
public class SphereModel implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  /** radius */
  @Attribute(name="radius")
  private float radius;

  /** division */
  @Attribute(name="division")
  private int division;

  /** translation */
  @Element(name="translation", required=false)
  private TranslationModel translation;

  /** rotation */
  @Element(name="rotation", required=false)
  private RotationModel rotation;
  
  /** color */
  @Element(name="color")
  private ColorModel color;

  /**_ransparent */
  @Attribute(name="transparent", required=false)
  private boolean transparent;


  /** propertyChangeListeners */
  private java.util.Vector<PropertyChangeListener> propertyChangeListeners;

  /**
   * コンストラクター
   */
  public SphereModel() {
    this.color = new ColorModel("red"); //$NON-NLS-1$
    this.transparent = false;
    this.propertyChangeListeners = new Vector<>();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public SphereModel clone() {
    try {
      final SphereModel ans = (SphereModel)super.clone();
      if (this.translation != null) {
        ans.translation = this.translation.clone();
      }
      if (this.rotation != null) {
        ans.rotation = this.rotation.clone();
      }
      
      ans.color = this.color.clone();

      return ans;
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.color == null) ? 0 : this.color.hashCode());
    result = prime * result + this.division;
    result = prime * result + ((this.propertyChangeListeners == null) ? 0 : this.propertyChangeListeners.hashCode());
    result = prime * result + Float.floatToIntBits(this.radius);
    result = prime * result + ((this.rotation == null) ? 0 : this.rotation.hashCode());
    result = prime * result + ((this.translation == null) ? 0 : this.translation.hashCode());
    result = prime * result + (this.transparent ? 1231 : 1237);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SphereModel other = (SphereModel)obj;
    if (this.color == null) {
      if (other.color != null) return false;
    } else if (!this.color.equals(other.color)) return false;
    if (this.division != other.division) return false;
    if (this.propertyChangeListeners == null) {
      if (other.propertyChangeListeners != null) return false;
    } else if (!this.propertyChangeListeners.equals(other.propertyChangeListeners)) return false;
    if (Float.floatToIntBits(this.radius) != Float.floatToIntBits(other.radius)) return false;
    if (this.rotation == null) {
      if (other.rotation != null) return false;
    } else if (!this.rotation.equals(other.rotation)) return false;
    if (this.translation == null) {
      if (other.translation != null) return false;
    } else if (!this.translation.equals(other.translation)) return false;
    if (this.transparent != other.transparent) return false;
    return true;
  }

  /**
   * Method addPropertyChangeListenerRegisters a PropertyChangeListener with this class.
   * 
   * @param pcl The PropertyChangeListener to register.
   */
  public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
    this.propertyChangeListeners.addElement(pcl);
  }

//  /**
//   * Returns the value of field 'color'.
//   * 
//   * @return the value of field 'color'.
//   */
//  public String getColorName() {
//    return this.color.getName();
//  }
  
  /**
   * Returns the value of field 'color'.
   * 
   * @return the value of field 'color'.
   */
  public ColorModel getColor() {
    return this.color;
  }


  /**
   * Returns the value of field 'div'.
   * 
   * @return the value of field 'div'.
   */
  public int getDivision() {
    return this.division;
  }

  /**
   * Returns the value of field 'location'.
   * 
   * @return the value of field 'location'.
   */
  public TranslationModel getTranslation() {
    return this.translation;
  }

  /**
   * Returns the value of field 'r'.
   * 
   * @return the value of field 'r'.
   */
  public float getRadius() {
    return this.radius;
  }

  /**
   * Returns the value of field 'rotation'.
   * 
   * @return the value of field 'rotation'.
   */
  public RotationModel getRotation() {
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

//  /**
//   * Sets the value of field 'color'.
//   * 
//   * @param color the value of field 'color'.
//   */
//  public void setColorName(String color) {
//    this.color = new ColorModel(color);
//  }
  
  /**
   * Sets the value of field 'color'.
   * 
   * @param color the value of field 'color'.
   */
  public void setColor(ColorModel color) {
    this.color = color;
  }


  /**
   * Sets the value of field 'div'.
   * 
   * @param division the value of field 'div'.
   */
  public void setDivision(int division) {
    this.division = division;
  }

  /**
   * Sets the value of field 'location'.
   * 
   * @param translation the value of field 'location'.
   */
  public void setTranslation(TranslationModel translation) {
    this.translation = translation;
  }

  /**
   * Sets the value of field 'r'.
   * 
   * @param radius the value of field 'r'.
   */
  public void setRadius(float radius) {
    this.radius = radius;
  }

  /**
   * Sets the value of field 'rotation'.
   * 
   * @param rotation the value of field 'rotation'.
   */
  public void setRotation(RotationModel rotation) {
    this.rotation = rotation;
  }

  /**
   * Sets the value of field 'transparent'.
   * 
   * @param transparent the value of field 'transparent'.
   */
  public void setTransparent(boolean transparent) {
    this.transparent = transparent;
  }

}
