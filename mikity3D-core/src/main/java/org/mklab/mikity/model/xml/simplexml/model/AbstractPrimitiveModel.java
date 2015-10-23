/*
 * Created on 2015/08/25
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Vector;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.core.Commit;


/**
 * プリミティブを表す抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/25
 */
public abstract class AbstractPrimitiveModel implements PrimitiveModel, Serializable, Cloneable {
  /** */
  private static final long serialVersionUID = 1L;
  /** color */
  @Element(name = "color")
  protected ColorModel color;
  
  /** translation */
  @Element(name = "translation", required = false)
  protected TranslationModel translation;
  
  /** rotation */
  @Element(name = "rotation", required = false)
  protected RotationModel rotation;
  
  /** transparent */
  protected boolean transparent;

  /** アルファ値のバックアップ。 */
  protected int preservedAlpha;
  
  /** Field propertyChangeListeners */
  protected Vector<PropertyChangeListener> propertyChangeListeners;
  
  /**
   * 新しく生成された<code>AbstractPrimitiveModel</code>オブジェクトを初期化します。
   */
  public AbstractPrimitiveModel() {
    this.transparent = false;
    this.propertyChangeListeners = new Vector<>();
  }
  
  /**
   * デシリアライズの後処理をします。
   */
  @Commit
  protected void buildAfterDeserialization() {
    this.preservedAlpha = getColor().getAlpha();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractPrimitiveModel clone() {
    try {
      final AbstractPrimitiveModel ans = (AbstractPrimitiveModel)super.clone();
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
    result = prime * result + ((this.color == null) ? 0 : this.color.hashCode());
    result = prime * result + this.preservedAlpha;
    result = prime * result + ((this.propertyChangeListeners == null) ? 0 : this.propertyChangeListeners.hashCode());
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
    AbstractPrimitiveModel other = (AbstractPrimitiveModel)obj;
    if (this.color == null) {
      if (other.color != null) return false;
    } else if (!this.color.equals(other.color)) return false;
    if (this.preservedAlpha != other.preservedAlpha) return false;
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
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ColorModel getColor() {
    return this.color;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setColor(ColorModel color) {
    this.color = color;
    this.preservedAlpha = color.getAlpha();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TranslationModel getTranslation() {
    return this.translation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RotationModel getRotation() {
    return this.rotation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTransparent() {
    return this.transparent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTranslation(TranslationModel translation) {
    this.translation = translation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRotation(RotationModel rotation) {
    this.rotation = rotation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTransparent(boolean transparent) {
    this.transparent = transparent;
    if (transparent) {
      this.color.setAlpha(this.preservedAlpha/2);
    } else {
      this.color.setAlpha(this.preservedAlpha);
    }
  }

  /**
   * Registers a PropertyChangeListener with this class.
   * 
   * @param listener The PropertyChangeListener to register.
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    this.propertyChangeListeners.addElement(listener);
  }

  /**
   * Notifies all registered PropertyChangeListeners when a bound property's value changes.
   * 
   * @param fieldName the name of the property that has changed.
   * @param newValue the new value of the property.
   * @param oldValue the old value of the property.
   */
  protected void notifyPropertyChangeListeners(String fieldName, Object oldValue, Object newValue) {
    if (this.propertyChangeListeners == null) return;
    final PropertyChangeEvent event = new PropertyChangeEvent(this, fieldName, oldValue, newValue);
  
    for (int i = 0; i < this.propertyChangeListeners.size(); i++) {
      (this.propertyChangeListeners.elementAt(i)).propertyChange(event);
    }
  }

  /**
   * Removes the given PropertyChangeListener from this classes list of ProperyChangeListeners.
   * 
   * @param listener The PropertyChangeListener to remove.
   * @return true if the given PropertyChangeListener was removed.
   */
  public boolean removePropertyChangeListener(PropertyChangeListener listener) {
    return this.propertyChangeListeners.removeElement(listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PrimitiveModel createClone() {
    return clone();
  }

}
