/*
 * $Id: PrimitiveRange.java,v 1.2 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.collision;

import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLSphere;


/**
 * プリミティブが存在する範囲に関するクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2006/07/26
 */
public class PrimitiveRange {

  /**
   * プリミティブが存在する範囲
   */
  private float range;

  /**
   * コンストラクタ
   */
  public PrimitiveRange() {
    // nothing to do
  }

  /**
   * プリミティブが存在する範囲を設定する
   * 
   * @param prim
   *        プリミティブ
   */
  public void setRange(Object prim) {
    if (prim instanceof XMLBox) {
      XMLBox box = (XMLBox)prim;
      this.range = calculateRange(box.loadXsize(), box.loadYsize(), box.loadZsize());
    } else if (prim instanceof XMLCone) {
      XMLCone cone = (XMLCone)prim;
      this.range = calculateRange(cone.loadR(), cone.loadHeight());
    } else if (prim instanceof XMLCylinder) {
      XMLCylinder cylinder = (XMLCylinder)prim;
      this.range = calculateRange(cylinder.loadR(), cylinder.loadHeight());
    } else if (prim instanceof XMLSphere) {
      XMLSphere sphere = (XMLSphere)prim;
      this.range = sphere.loadR();
    }
  }

  /**
   * プリミティブが存在する範囲を返す。
   * 
   * @return range 存在範囲
   */
  public float getRange() {
    return this.range;
  }

  /**
   * プリミティブが直方体の場合の存在範囲を計算する。
   * 
   * @param x
   *        　直方体の幅
   * @param y
   *        　直方体の高さ
   * @param z
   *        　直方体の奥行き
   * @return rg　存在範囲
   */
  private float calculateRange(float x, float y, float z) {
    float rg = (float)Math.sqrt(Math.pow(x * 0.5f, 2) + Math.pow(y * 0.5f, 2) + Math.pow(z * 0.5f, 2));
    return rg;
  }

  /**
   * プリミティブが円柱や円錐の場合の存在範囲を計算する。
   * 
   * @param r
   *        　円柱・円錐の半径
   * @param h
   *        　円柱・円錐の高さ
   * @return rg 存在範囲
   */
  private float calculateRange(float r, float h) {
    float rg = (float)Math.sqrt(Math.pow(r, 2) + Math.pow(h * 0.5f, 2));
    return rg;
  }
}