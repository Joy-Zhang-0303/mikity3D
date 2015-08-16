package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.model.graphic.BoxObject;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.view.renderer.jogl.AbstractJoglPrimitive;


/**
 * BOXをJOGLで表したクラスです
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/26
 */
public class JoglBox extends AbstractJoglPrimitive {
  /**
   * 新しく生成された<code>JoglBox</code>オブジェクトを初期化します。
   * @param box モデル
   */
  public JoglBox(BoxModel box) {
    super(new BoxObject(box));
  }
}
