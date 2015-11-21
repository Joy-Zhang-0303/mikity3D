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
    }
  }
}
