/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesPrimitive;
import org.mklab.mikity.model.graphic.BoxObject;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;


/**
 * ボックスを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesBox extends AbstractOpenglesPrimitive {
  /**
   * 新しく生成された<code>OpenglesBox</code>オブジェクトを初期化します。
   * @param box モデル
   */
  public OpenglesBox(BoxModel box) {
    super(new BoxObject(box));
  }
}
