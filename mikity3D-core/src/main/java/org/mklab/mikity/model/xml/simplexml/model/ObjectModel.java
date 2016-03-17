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


/**
 * オブジェクトモデルを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/07
 */
public interface ObjectModel {
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
  
  /**
   * モデルを囲む直方体の幅を返します。
   * 
   * @return モデルを囲む直方体の幅
   */
  float getWidth();

  /**
   * モデルを囲む直方体の奥行きを返します。
   * 
   * @return モデルを囲む直方体の奥行き
   */
  float getDepth();
  
  /**
   * モデルを囲む直方体の高さを返します。
   * 
   * @return モデルを囲む直方体の高さ
   */
  float getHeight();
    
  /**
   * クローンを生成します。
   * 
   * @return クローン
   */
  ObjectModel createClone();
  
  /**
   * 短い文字列を返します。
   * 
   * @return 短い文字列
   */
  String toShortString();
}
