/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesPrimitive;
import org.mklab.mikity.model.graphic.TrianglePolygonObject;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;


/**
 * 四角ポリゴンをOpenGLESで表したクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesTrianglePolygon extends AbstractOpenglesPrimitive {
  /**
   * 新しく生成された<code>OpenglesTrianglePolygon</code>オブジェクトを初期化します。
   * @param polygon 三角形ポリゴン
   */
  public OpenglesTrianglePolygon(TrianglePolygonModel polygon) {
    super(new TrianglePolygonObject(polygon));
  }
}
