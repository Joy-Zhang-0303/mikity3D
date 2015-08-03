/*
 * Created on 2015/08/03
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;


/**
 * 座標系を表す抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/03
 */
public abstract class AbstractCoordinate {
  /** 並進。 */
  protected TranslationModel translation = new TranslationModel();
  /** 回転。 */
  protected RotationModel rotation = new RotationModel();

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.rotation == null) ? 0 : this.rotation.hashCode());
    result = prime * result + ((this.translation == null) ? 0 : this.translation.hashCode());
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
    final AbstractCoordinate other = (AbstractCoordinate)obj;
    if (this.rotation == null) {
      if (other.rotation != null) return false;
    } else if (!this.rotation.equals(other.rotation)) return false;
    if (this.translation == null) {
      if (other.translation != null) return false;
    } else if (!this.translation.equals(other.translation)) return false;
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "(" + this.translation.getX() + ", " + this.translation.getY() + ", " + this.translation.getZ() + ", " + this.rotation.getX() + ", " + this.rotation.getY() + ", " + this.rotation.getZ() +  ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
  }
  
  /**
   * 並進を設定します。
   * 
   * @param translation 並進
   */
  public void setTranslation(TranslationModel translation) {
    this.translation = translation;
  }
  
  /**
   * 回転を設定します。
   * 
   * @param rotation 回転
   * 
   */
  public void setRotation(RotationModel rotation) {
    this.rotation = rotation;
  }

}
