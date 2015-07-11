/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesObject;
import org.mklab.mikity.model.graphic.SphereObject;

/**
 * 球を表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class OpenglesSphere extends AbstractOpenglesObject {
  /**
   * 新しく生成された<code>OpenglesSphere</code>オブジェクトを初期化します。
   */
  public OpenglesSphere() {
    super(new SphereObject());
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 半径
   */
  public void setSize(float radius) {
    ((SphereObject)this.object).setSize(radius);
  }
  
  /**
   * 分割数を設定します。
   * @param division 分割数
   */
  public void setDivision(int division) {
    ((SphereObject)this.object).setDivision(division);
  }
}