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

import org.mklab.mikity.model.xml.simplexml.model.CompositionModel;
import org.mklab.mikity.model.xml.simplexml.model.FacetModel;

/**
 * 三角形の面による合成オブジェクトを表わすクラスです
 * 
 */
public class CompositionObject extends AbstractGraphicObject {
  /**
   * 新しく生成された<code>CompositionObject</code>オブジェクトを初期化します。
   * @param composition モデル
   */
  public CompositionObject(CompositionModel composition) {
    super(composition);
    updatePolygons();
  }
  
  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    final int size = ((CompositionModel)this.object).getSize();
    final int polygonNumber = size*3;
    initializeArrays(polygonNumber);

    for (final FacetModel facet : ((CompositionModel)this.object).getFacets()) {
      appendVertices(facet.getVertices());
      appendNormalVector(facet.getNormalVector());
      appendNormalVector(facet.getNormalVector());
      appendNormalVector(facet.getNormalVector());
    }
    
  }
}
