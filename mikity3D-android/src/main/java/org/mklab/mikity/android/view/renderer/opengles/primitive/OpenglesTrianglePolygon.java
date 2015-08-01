/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesPrimitive;
import org.mklab.mikity.model.graphic.TrianglePolygonObject;


/**
 * 四角ポリゴンをOpenGLESで表したクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesTrianglePolygon extends AbstractOpenglesPrimitive {
  /**
   * 新しく生成された<code>OpenglesTrianglePolygon</code>オブジェクトを初期化します。
   */
  public OpenglesTrianglePolygon() {
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
