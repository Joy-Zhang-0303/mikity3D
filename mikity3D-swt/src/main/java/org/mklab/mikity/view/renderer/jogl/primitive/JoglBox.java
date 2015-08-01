package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.model.graphic.BoxObject;
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
   */
  public JoglBox() {
    super(new BoxObject());
  }

  /**
   * 大きさを設定します。
   * 
   * @param width 幅
   * @param height 高さ
   * @param depth 奥行
   */
  public void setSize(float width, float height, float depth) {
    ((BoxObject)this.object).setSize(width, height, depth);
  }

}
