/*
 * Created on 2015/08/17
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Root;


/**
 * NULLを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/17
 */
@Root(name="null")
public class NullModel implements PrimitiveModel {
  private static final NullModel instancel = new NullModel();
  
  /**
   * 新しく生成された<code>NullModel</code>オブジェクトを初期化します。
   */
  private NullModel() {
    // TODO 自動生成されたコンストラクター・スタブ
  }

  /**
   * インスタンスを返します。
   * 
   * @return インスタンス
   */
  public static NullModel getInstance() {
    return NullModel.instancel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof NullModel) {
      return true;
    }
    return false;
  }
  

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  
  /**
   * {@inheritDoc}
   */
  public ColorModel getColor() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public void setColor(@SuppressWarnings("unused") ColorModel color) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public TranslationModel getTranslation() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public void setTranslation(@SuppressWarnings("unused") TranslationModel translation) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public RotationModel getRotation() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public void setRotation(@SuppressWarnings("unused") RotationModel rotation) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTransparent() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public void setTransparent(@SuppressWarnings("unused") boolean isTransparent) {
    // nothing do do
  }

  /**
   * {@inheritDoc}
   */
  public PrimitiveModel createClone() {
    return null;
  }

}
