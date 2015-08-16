/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesPrimitive;
import org.mklab.mikity.model.graphic.QuadPolygonObject;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;

/**
 * 四角ポリゴンをOpenGLESで表したクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesQuadPolygon extends AbstractOpenglesPrimitive {
  /**
   * 新しく生成された<code>OpenglesQuadPolygon</code>オブジェクトを初期化します。
   * @param polygon 四角形ポリゴン
   */
  public OpenglesQuadPolygon(QuadPolygonModel polygon) {
    super(new QuadPolygonObject(polygon));
  }
}


