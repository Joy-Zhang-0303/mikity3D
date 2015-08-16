/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesPrimitive;
import org.mklab.mikity.model.graphic.CylinderObject;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;


/**
 * 円柱を表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesCylinder extends AbstractOpenglesPrimitive {
  /**
   * 新しく生成された<code>OpenglesCylinder</code>オブジェクトを初期化します。
   * @param cylinder シリンダー
   */
  public OpenglesCylinder(CylinderModel cylinder) {
    super(new CylinderObject(cylinder));
  }
}
