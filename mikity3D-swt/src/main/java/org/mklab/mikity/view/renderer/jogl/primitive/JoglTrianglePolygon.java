package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.model.graphic.TrianglePolygonObject;
import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;


/**
 * 三角形ポリゴンをJOGLで表したクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglTrianglePolygon extends AbstractJoglObject {
  /**
   * 新しく生成された<code>JoglTrianglePolygon</code>オブジェクトを初期化します。
   */
  public JoglTrianglePolygon() {
    super(new TrianglePolygonObject());
  }

  /**
   * 3個の頂点を設定します。
   * @param vertices 3個の頂点
   */
  public void setVertices(float[][] vertices) {
    ((TrianglePolygonObject)this.object).setVertices(vertices);
  }
  
  /**
   * 法線ベクトルを設定します。
   * @param normalVector 法線ベクトル
   */
  public void setNormalVector(float[] normalVector) {
    ((TrianglePolygonObject)this.object).setNormalVector(normalVector);
  }

}
