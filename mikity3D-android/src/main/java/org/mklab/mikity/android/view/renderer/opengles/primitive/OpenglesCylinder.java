/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesObject;
import org.mklab.mikity.model.graphic.CylinderObject;


/**
 * 円柱を表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesCylinder extends AbstractOpenglesObject {
  /**
   * 新しく生成された<code>OpenglesCylinder</code>オブジェクトを初期化します。
   */
  public OpenglesCylinder() {
    super(new CylinderObject());
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 底面の半径
   * @param height 高さ
   */
  public void setSize(float radius, float height) {
    ((CylinderObject)this.object).setSize(radius, height);
  }

  /**
   * 分割数を設定します。
   * 
   * @param division 分割数
   */
  public void setDivision(int division) {
    ((CylinderObject)this.object).setDivision(division);
  }
}
