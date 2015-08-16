package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.model.graphic.QuadPolygonObject;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.view.renderer.jogl.AbstractJoglPrimitive;


/**
 * 四角形ポリゴンをJOGLで表したクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglQuadPolygon extends AbstractJoglPrimitive {
  /**
   * 新しく生成された<code>JoglQuadPolygon</code>オブジェクトを初期化します。
   * @param polygon 四角形ポリゴン
   */
  public JoglQuadPolygon(QuadPolygonModel polygon) {
    super(new QuadPolygonObject(polygon));
  }
}
