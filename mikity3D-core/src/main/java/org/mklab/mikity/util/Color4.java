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
public class Color4 {
  /** 赤成分 */
  private int r;
  /** 緑成分 */
  private int g;
  /** 青成分 */
  private int b;
  /** アルファ値 */
  private int alpha = 255;
  
  /**
   * 新しく生成された<code>Color3</code>オブジェクトを初期化します。
   * @param r 赤成分
   * @param g 緑成分
   * @param b 青成分
   */
  public Color4(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * 新しく生成された<code>Color3</code>オブジェクトを初期化します。
   * @param r 赤成分
   * @param g 緑成分
   * @param b 青成分
   * @param alpha アルファ値
   */
  public Color4(int r, int g, int b, int alpha) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.alpha = alpha;
  }

  
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.alpha;
    result = prime * result + this.b;
    result = prime * result + this.g;
    result = prime * result + this.r;
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Color4 other = (Color4)obj;
    if (this.alpha != other.alpha) return false;
    if (this.b != other.b) return false;
    if (this.g != other.g) return false;
    if (this.r != other.r) return false;
    return true;
  }

  /**
   * 赤成分を返します。
   * 
   * @return 赤成分
   */
  public float getRf() {
    return this.r/255f;
  }

  
  /**
   * 緑成分を返します。
   * 
   * @return 緑成分
   */
  public float getGf() {
    return this.g/255f;
  }

  
  /**
   * 青成分を返します。
   * 
   * @return 青成分
   */
  public float getBf() {
    return this.b/255f;
  }
  
  /**
   * アルファ値を返します。
   * 
   * @return アルファ値
   */
  public float getAlphaf() {
    return this.alpha/255f;
  }
  
  /**
   * 赤成分を返します。
   * 
   * @return 赤成分
   */
  public int getR() {
    return this.r;
  }

  
  /**
   * 緑成分を返します。
   * 
   * @return 緑成分
   */
  public int getG() {
    return this.g;
  }

  
  /**
   * 青成分を返します。
   * 
   * @return 青成分
   */
  public int getB() {
    return this.b;
  }
  
  /**
   * アルファ値を返します。
   * 
   * @return アルファ値
   */
  public int getAlpha() {
    return this.alpha;
  }
}
