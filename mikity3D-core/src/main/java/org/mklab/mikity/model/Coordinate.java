/*
 * Created on 2015/08/03
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

import org.mklab.mikity.model.sampler.Messages;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;

/**
 * 座標系を表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/03
 */
public class Coordinate {
  /** 並進。 */
  protected TranslationModel translation = new TranslationModel();
  /** 回転。 */
  protected RotationModel rotation = new RotationModel();
  
  /**
   * 新しく生成された<code>Coordinate</code>オブジェクトを初期化します。
   */
  public Coordinate() {
    // nothing to do
  }
  
  /**
   * 新しく生成された<code>Coordinate</code>オブジェクトを初期化します。
   * @param translation 並進
   */
  public Coordinate(TranslationModel translation) {
    this.translation = translation; 
  }
  
  /**
   * 新しく生成された<code>Coordinate</code>オブジェクトを初期化します。
   * @param rotation 回転
   */
  public Coordinate(RotationModel rotation) {
    this.rotation = rotation;
  }
  
  /**
   * 新しく生成された<code>Coordinate</code>オブジェクトを初期化します。
   * @param translation 並進
   * @param rotation 回転
   */
  public Coordinate(TranslationModel translation, RotationModel rotation) {
    this.translation = translation;
    this.rotation = rotation;
  }

  
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
    final Coordinate other = (Coordinate)obj;
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
  
  /**
   * 並進を返します。
   * 
   * @return 並進
   */
  public TranslationModel getTranslation() {
    return this.translation;
  }
  
  /**
   * 回転を返します。
   * 
   * @return 回転
   */
  public RotationModel getRotation() {
    return this.rotation;
  }
   
  /**
   * X軸方向への並進距離を設定します。
   * 
   * @param translationX X軸方向への並進距離
   */
  public void setTranslationX(double translationX) {
    this.translation.setX((float)translationX);
  }

  /**
   * Y軸方向への並進距離の値を設定します。
   * 
   * @param translationY Y軸方向への並進距離
   */
  public void setTranslationY(double translationY) {
    this.translation.setY((float)translationY);
  }

  /**
   * Z軸方向への並進距離の値を設定します。
   * 
   * @param translationZ Z軸方向への並進距離
   */
  public void setTranslationZ(double translationZ) {
    this.translation.setZ((float)translationZ);
  }

  /**
   * X軸周りの回転角度を設定します。
   * 
   * @param rotationX X軸周りの回転角度
   */
  public void setRotationX(double rotationX) {
    this.rotation.setX((float)rotationX);
  }

  /**
   * Y軸周りの回転角度を設定します。
   * 
   * @param rotationY Y軸周りの回転角度
   */
  public void setRotationY(double rotationY) {
    this.rotation.setY((float)rotationY);
  }

  /**
   * Z軸周りの回転角度を設定します。
   * 
   * @param rotationZ Z軸周りの回転角度
   */
  public void setRotationZ(double rotationZ) {
    this.rotation.setZ((float)rotationZ);
  }

  /**
   * 値を設定します。
   * 
   * @param value 値
   * @param type パラメータの型
   */
  public void setValue(double value, CoordinateParameterType type) {
    switch (type) {
      case TRANSLATION_X:
        setTranslationX(value);
        break;
      case TRANSLATION_Y:
        setTranslationY(value);
        break;
      case TRANSLATION_Z:
        setTranslationZ(value);
        break;
      case ROTATION_X:
        setRotationX(value);
        break;
      case ROTATION_Y:
        setRotationY(value);
        break;
      case ROTATION_Z:
        setRotationZ(value);
        break;
      default:
        throw new AssertionError(Messages.getString("DataPicker.1")); //$NON-NLS-1$
    }
  }
}
