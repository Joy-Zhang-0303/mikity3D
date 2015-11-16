package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.model.NullModel;

/**
 * Nullオブジェクトを表わすクラスです
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/26
 */
public class NullObject extends AbstractGraphicObject {
  /**
   * 新しく生成された<code>NullObject</code>オブジェクトを初期化します。
   * @param model モデル
   */
  public NullObject(NullModel model) {
    super(model);
  }
}
