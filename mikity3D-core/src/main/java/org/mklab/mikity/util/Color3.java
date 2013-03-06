/*
 * Created on 2013/01/27
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;


/**
 * 色を表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2013/01/27
 */
public class Color3 {
  /** 赤成分 */
  float r;
  /** 緑成分 */
  float g;
  /** 青成分 */
  float b;
  
  /**
   * 新しく生成された<code>Color3</code>オブジェクトを初期化します。
   * @param r 赤成分
   * @param g 緑成分
   * @param b 青成分
   */
  public Color3(float r, float g, float b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  
  /**
   * 赤成分を返します。
   * @return 赤成分
   */
  public float getR() {
    return this.r;
  }

  
  /**
   * 緑成分を返します。
   * @return 緑成分
   */
  public float getG() {
    return this.g;
  }

  
  /**
   * 青成分を返します。
   * @return 青成分
   */
  public float getB() {
    return this.b;
  }

}
