/*
 * Created on 2013/01/27
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;


/**
 * 3次元ベクトルを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2013/01/27
 */
public class Vector3 {
  /** x成分 */
  private float x;
  /** y成分 */
  private float y;
  /** z成分 */
  private float z;
  
  /**
   * 新しく生成された<code>Vector3</code>オブジェクトを初期化します。
   * @param x x成分
   * @param y y成分
   * @param z z成分
   */
  public Vector3(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  /**
   * 外積を返します。
   * @param b 第二ベクトル
   * @return 外積
   */
  public Vector3 cross(Vector3 b) {
    return new Vector3(this.y*b.z - this.z*b.y, this.z*b.x - this.x*b.z, this.x*b.y - this.y*b.x);
  }
  
  /**
   * 長さを返します。
   * @return 長さ
   */
  public float lenght() {
    return (float)Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
  }
  
  /**
   * 正規化したベクトルを返します。
   * @return 正規化したベクトル
   */
  public Vector3 normalize() {
    final float length = lenght();
    if (length == 0) {
      return new Vector3(0, 0, 0);
    }
    return new Vector3(this.x/length, this.y/length,  this.z/length);
  }

  
  /**
   * xを返します。
   * @return x 
   */
  public float getX() {
    return this.x;
  }

  
  /**
   * yを返します。
   * @return y
   */
  public float getY() {
    return this.y;
  }

  
  /**
   * zを返します。
   * @return z
   */
  public float getZ() {
    return this.z;
  }
  
}
