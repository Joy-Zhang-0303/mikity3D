package org.mklab.mikity.model.xml.simplexml.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Vector;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Cylinderを表すクラスです。
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/20 02:51:56 $
 */
@Root(name="cylinder")
public class CylinderModel implements PrimitiveModel, Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  /** radius */
  @Attribute(name="radius")
  protected float radius;

  /** height */
  @Attribute(name="height")
  protected float height;

  /** division */
  @Attribute(name="division")
  protected int division;

  /** translation */
  @Element(name="translation", required=false)
  protected TranslationModel translation;

  /** rotation */
  @Element(name="rotation", required=false)
  protected RotationModel rotation;
  
  /** color */
  @Element(name="color")
  private ColorModel color;

  /** transparent */
  protected boolean transparent;
  
  private int preservedAlpha;

  /** Field propertyChangeListeners */
  protected Vector<PropertyChangeListener> propertyChangeListeners;

  /**
   * コンストラクター
   */
  public CylinderModel() {
    this.color = new ColorModel("red"); //$NON-NLS-1$
    this.preservedAlpha = this.color.getAlpha();
    this.transparent = false;
    this.propertyChangeListeners = new Vector<>();
  }
  
  /**
   * 新しく生成された<code>CylinderModel</code>オブジェクトを初期化します。
   * @param radius 半径
   * @param height 高さ
   * @param division 分割数
   */
  public CylinderModel(float radius, float height, int division) {
    this();
    this.radius = radius;
    this.height = height;
    this.division = division;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public CylinderModel clone() {
    try {
      final CylinderModel ans = (CylinderModel)super.clone();
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
  public PrimitiveModel createClone() {
    return clone();
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
    result = prime * result + Float.floatToIntBits(this.height);
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
    CylinderModel other = (CylinderModel)obj;
    if (this.color == null) {
      if (other.color != null) return false;
    } else if (!this.color.equals(other.color)) return false;
    if (this.division != other.division) return false;
    if (Float.floatToIntBits(this.height) != Float.floatToIntBits(other.height)) return false;
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
  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.propertyChangeListeners.addElement(pcl);
  }

  /**
   * {@inheritDoc}
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
   * Returns the value of field 'height'.
   * 
   * @return the value of field 'height'.
   */
  public float getHeight() {
    return this.height;
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  public RotationModel getRotation() {
    return this.rotation;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTransparent() {
    return this.transparent;
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
    PropertyChangeEvent event = new PropertyChangeEvent(this, fieldName, oldValue, newValue);

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
  public boolean removePropertyChangeListener(PropertyChangeListener pcl) {
    return this.propertyChangeListeners.removeElement(pcl);
  }

  /**
   * {@inheritDoc}
   */
  public void setColor(ColorModel color) {
    this.color = color;
    this.preservedAlpha = color.getAlpha();
  }

  /**
   * Sets the value of field 'div'.
   * 
   * @param div the value of field 'div'.
   */
  public void setDivision(int div) {
    this.division = div;
  }

  /**
   * Sets the value of field 'height'.
   * 
   * @param height the value of field 'height'.
   */
  public void setHeight(float height) {
    this.height = height;
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  public void setRotation(RotationModel rotation) {
    this.rotation = rotation;
  }

  /**
   * {@inheritDoc}
   */
  public void setTransparent(boolean transparent) {
    this.transparent = transparent;
    if (transparent) {
      this.color.setAlpha(this.preservedAlpha/2);
    } else {
      this.color.setAlpha(this.preservedAlpha);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "cylinder (radius=" + this.radius + ", height=" + this.height + ", division=" + this.division + ", color=" + this.color + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ 
  }
}
