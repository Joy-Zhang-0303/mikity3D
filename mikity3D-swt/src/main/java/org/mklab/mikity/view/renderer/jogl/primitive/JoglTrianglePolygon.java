package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.model.graphic.TrianglePolygonObject;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;
import org.mklab.mikity.view.renderer.jogl.AbstractJoglPrimitive;


/**
 * 三角形ポリゴンをJOGLで表したクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglTrianglePolygon extends AbstractJoglPrimitive {
  /**
   * 新しく生成された<code>JoglTrianglePolygon</code>オブジェクトを初期化します。
   * @param triangle 三角形ポリゴン
   */
  public JoglTrianglePolygon(TrianglePolygonModel triangle) {
    super(new TrianglePolygonObject(triangle));
  }
}
