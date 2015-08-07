package org.mklab.mikity.model.xml.simplexml.model;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * BOXを表すクラスです。
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/20 02:51:56 $
 */
@Root(name="box")
public class BoxModel implements PrimitiveModel, java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  /** width */
  @Attribute(name="width")
  private float width;

  /** height */
  @Attribute(name="height")
  private float height;

  /** depth */
  @Attribute(name="depth")
  private float depth;

  /** translation */
  @Element(name="translation", required=false)
  private TranslationModel translation;

  /** rotation */
  @Element(name="rotation", required=false)
  private RotationModel rotation;
  
  /** color */
  @Element(name="color")
  private ColorModel color;

  /** transparent */
  @Attribute(name="transparent", required=false)
  private boolean transparent;

  /** propertyChangeListeners */
  private Vector<PropertyChangeListener> propertyChangeListeners;

  /**
   * コンストラクター
   */
  public BoxModel() {
    this.color = new ColorModel("red"); //$NON-NLS-1$
    this.transparent = false;
    this.propertyChangeListeners = new Vector<>();
  } 
  
  /**
   * {@inheritDoc}
   */
  @Override
  public BoxModel clone() {
    try {
      final BoxModel ans = (BoxModel)super.clone();
      if (this.translation != null) {
        ans.translation = this.translation.clone();
      }
      if (this.rotation != null) {
        ans.rotation = this.rotation.clone();
      }
      if (this.color != null) {
        ans.color = this.color.clone();
      }
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
    result = prime * result + Float.floatToIntBits(this.depth);
    result = prime * result + Float.floatToIntBits(this.height);
    result = prime * result + ((this.color == null) ? 0 : this.color.hashCode());
    result = prime * result + ((this.propertyChangeListeners == null) ? 0 : this.propertyChangeListeners.hashCode());
    result = prime * result + ((this.rotation == null) ? 0 : this.rotation.hashCode());
    result = prime * result + ((this.translation == null) ? 0 : this.translation.hashCode());
    result = prime * result + (this.transparent ? 1231 : 1237);
    result = prime * result + Float.floatToIntBits(this.width);
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
    BoxModel other = (BoxModel)obj;
    if (Float.floatToIntBits(this.depth) != Float.floatToIntBits(other.depth)) return false;
    if (Float.floatToIntBits(this.height) != Float.floatToIntBits(other.height)) return false;
    if (this.color == null) {
      if (other.color != null) return false;
    } else if (!this.color.equals(other.color)) return false;
    if (this.propertyChangeListeners == null) {
      if (other.propertyChangeListeners != null) return false;
    } else if (!this.propertyChangeListeners.equals(other.propertyChangeListeners)) return false;
    if (this.rotation == null) {
      if (other.rotation != null) return false;
    } else if (!this.rotation.equals(other.rotation)) return false;
    if (this.translation == null) {
      if (other.translation != null) return false;
    } else if (!this.translation.equals(other.translation)) return false;
    if (this.transparent != other.transparent) return false;
    if (Float.floatToIntBits(this.width) != Float.floatToIntBits(other.width)) return false;
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
   * Returns the value of field 'location'.
   * 
   * @return the value of field 'location'.
   */
  public TranslationModel getTranslation() {
    return this.translation;
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
  public boolean isTransparent() {
    return this.transparent;
  }

  /**
   * Returns the value of field 'xsize'.
   * 
   * @return the value of field 'xsize'.
   */
  public float getWidth() {
    return this.width;
  }

  /**
   * Returns the value of field 'ysize'.
   * 
   * @return the value of field 'ysize'.
   */
  public float getHeight() {
    return this.height;
  }

  /**
   * Returns the value of field 'zsize'.
   * 
   * @return the value of field 'zsize'.
   */
  public float getDepth() {
    return this.depth;
  }

  /**
   * Method notifyPropertyChangeListenersNotifies all registered PropertyChangeListeners when a bound property's value changes.
   * 
   * @param fieldName the name of the property that has changed.
   * @param newValue the new value of the property.
   * @param oldValue the old value of the property.
   */
  protected void notifyPropertyChangeListeners(String fieldName, Object oldValue, Object newValue) {
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
//   * @param colorName the value of field 'color'.
//   */
//  public void setColorName(String colorName) {
//    this.color = new ColorModel(colorName);
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
   * Sets the value of field 'location'.
   * 
   * @param translation the value of field 'location'.
   */
  public void setTranslation(TranslationModel translation) {
    this.translation = translation;
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

  /**
   * Sets the value of field 'xsize'.
   * 
   * @param width the value of field 'xsize'.
   */
  public void setWidth(float width) {
    this.width = width;
  }

  /**
   * Sets the value of field 'ysize'.
   * 
   * @param height the value of field 'ysize'.
   */
  public void setHeight(float height) {
    this.height = height;
  }

  /**
   * Sets the value of field 'zsize'.
   * 
   * @param depth the value of field 'zsize'.
   */
  public void setDepth(float depth) {
    this.depth = depth;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "box (width=" + this.width + ", height=" + this.height + ", depth=" + this.depth + ", color=" + this.color + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
  }
}
