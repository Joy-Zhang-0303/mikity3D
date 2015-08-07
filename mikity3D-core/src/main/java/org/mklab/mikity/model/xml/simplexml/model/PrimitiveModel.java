/*
 * Created on 2015/08/07
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.model;


/**
 * プリミティブを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/07
 */
public interface PrimitiveModel {
  /**
   * 色を返します。
   * 
   * @return 色
   */
  ColorModel getColor();
  
  /**
   * 色を瀬ってします。
   * @param color 色
   */
  void setColor(ColorModel color);
  
  /**
   * 並進を返します。
   * 
   * @return 並進
   */
  TranslationModel getTranslation();
  
  /**
   * 並進を設定します。
   * 
   * @param translation 並進
   */
  void setTranslation(TranslationModel translation);
  
  /**
   * 回転を返します。
   * 
   * @return 回転
   */
  RotationModel getRotation();
  
  /**
   * 回転を設定します。
   * 
   * @param rotation 回転
   */
  void setRotation(RotationModel rotation);
  
  /**
   * 透明性を判定します。
   * 
   * @return 透明ならばtrue
   */
  boolean isTransparent();
  
  /**
   * 透明性を設定します。
   * 
   * @param isTransparent 透明ならばtrue
   */
  void setTransparent(boolean isTransparent);
}
