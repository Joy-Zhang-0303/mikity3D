/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesPrimitive;
import org.mklab.mikity.model.graphic.ConeObject;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;


/**
 * 三角錐を表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesCone extends AbstractOpenglesPrimitive {
  /**
   * 新しく生成された<code>OpenglesCone</code>オブジェクトを初期化します。
   * @param cone モデル
   */
  public OpenglesCone(ConeModel cone) {
    super(new ConeObject(cone));
  }
}
