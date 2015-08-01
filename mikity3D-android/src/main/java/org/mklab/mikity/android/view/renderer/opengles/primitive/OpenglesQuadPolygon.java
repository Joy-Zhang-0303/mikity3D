/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesPrimitive;
import org.mklab.mikity.model.graphic.QuadPolygonObject;

/**
 * 四角ポリゴンをOpenGLESで表したクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesQuadPolygon extends AbstractOpenglesPrimitive {
  /**
   * 新しく生成された<code>OpenglesQuadPolygon</code>オブジェクトを初期化します。
   */
  public OpenglesQuadPolygon() {
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


