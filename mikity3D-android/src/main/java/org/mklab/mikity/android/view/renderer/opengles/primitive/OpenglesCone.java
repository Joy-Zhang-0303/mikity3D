/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesPrimitive;
import org.mklab.mikity.model.graphic.ConeObject;


/**
 * 三角錐を表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesCone extends AbstractOpenglesPrimitive {
  /**
   * 新しく生成された<code>OpenglesCone</code>オブジェクトを初期化します。
   */
  public OpenglesCone() {
    super(new ConeObject());
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 底面の半径
   * @param hight 高さ
   */
  public void setSize(float radius, float hight) {
    ((ConeObject)this.object).setSize(radius, hight);
  }

  /**
   * 分割数を設定します。
   * 
   * @param division 分割数
   */
  public void setDivision(int division) {
    ((ConeObject)this.object).setDivision(division);
  }
}
