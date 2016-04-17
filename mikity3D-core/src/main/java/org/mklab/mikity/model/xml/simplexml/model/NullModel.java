/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.model.xml.simplexml.model;

import java.io.Serializable;

import org.simpleframework.xml.Root;


/**
 * NULLを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/17
 */
@Root(name="null")
public class NullModel implements ObjectModel, Serializable, Cloneable {
  /** */
  private static final long serialVersionUID = 1L;
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
  public NullModel clone() {
    return this;
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
  public void setColor(ColorModel color) {
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
  public void setTranslation(TranslationModel translation) {
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
  public void setRotation(RotationModel rotation) {
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
  public void setTransparent(boolean isTransparent) {
    // nothing do do
  }

  /**
   * {@inheritDoc}
   */
  public ObjectModel createClone() {
    return this;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toShortString() {
    return "null"; //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  public float getWidth() {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public float getDepth() {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public float getHeight() {
    return 0;
  }
}
