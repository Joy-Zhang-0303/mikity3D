package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;


/**
 * 四角形ポリゴンをJOGLで表したクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglQuadPolygon extends AbstractJoglObject {
  /**
   * 新しく生成された<code>JoglQuadPolygon</code>オブジェクトを初期化します。
   */
  public JoglQuadPolygon() {
    super(new QuadPolygonObject());
  }

  /**
   * 4個の頂点を設定します。
   * 
   * @param vertices 4個の頂点
   */
  public void setVertices(float[][] vertices) {
    ((QuadPolygonObject)this.object).setVertices(vertices);
  }
  
  /**
   * 法線ベクトルを設定します。
   * @param normalVector 法線ベクトル
   */
  public void setNormalVector(float[] normalVector) {
    ((QuadPolygonObject)this.object).setNormalVector(normalVector);
  }
}
