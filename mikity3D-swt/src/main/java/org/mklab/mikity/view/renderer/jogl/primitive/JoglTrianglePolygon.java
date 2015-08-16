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

//  /**
//   * 3個の頂点を設定します。
//   * @param vertices 3個の頂点
//   */
//  public void setVertices(float[][] vertices) {
//    ((TrianglePolygonObject)this.object).setVertices(vertices);
//  }
//  
//  /**
//   * 法線ベクトルを設定します。
//   * @param normalVector 法線ベクトル
//   */
//  public void setNormalVector(float[] normalVector) {
//    ((TrianglePolygonObject)this.object).setNormalVector(normalVector);
//  }

}
