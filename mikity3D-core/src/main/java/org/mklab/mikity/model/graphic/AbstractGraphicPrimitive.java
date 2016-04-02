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
package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;

/**
 * グラフィックプリミティブを表す抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/07/11
 */
public abstract class AbstractGraphicPrimitive extends AbstractGraphicObject {
  /** オブジェクト。 */
  protected ObjectModel object;
  
  /**
   * 新しく生成された<code>GraphicPrimitive</code>オブジェクトを初期化します。
   * @param object オブジェクト
   */
  public AbstractGraphicPrimitive(ObjectModel object) {
    this.object = object;
  }
  
  /**
   * 色を設定します。
   * 
   * @param color 色
   */
  public void setColor(ColorModel color) {
    this.object.setColor(color);
  }
  
  /**
   * 色を返します。
   * 
   * @return 色
   */
  public ColorModel getColor() {
    return this.object.getColor();
  }
  
  /**
   * 透明性を設定します。
   * 
   * @param isTransparent 透明性
   */
  public void setTransparent(boolean isTransparent) {
    this.object.setTransparent(isTransparent);
  }
  
  /**
   * 透明性を判定します。
   * 
   * @return 透明ならばtrue、そうでなければfalse
   */
  public boolean isTransparent() {
    return this.object.isTransparent();
  }
}
