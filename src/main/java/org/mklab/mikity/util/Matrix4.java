/*
 * Created on 2013/01/27
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;


/**
 * 4次の正方行列を表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2013/01/27
 */
public class Matrix4 {
  /** 成分 */
  private float[][] a = new float[4][4];
  
  /**
   * 新しく生成された<code>Matrix4</code>オブジェクトを初期化します。
   */
  public Matrix4() {
    this(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
  }
  
  /**
   * 新しく生成された<code>Matrix4</code>オブジェクトを初期化します。
   * @param a00 (1,1)成分
   * @param a01 (1,2)成分
   * @param a02 (1,3)成分
   * @param a03 (1,4)成分
   * @param a10 (2,1)成分
   * @param a11 (2,2)成分
   * @param a12 (2,3)成分
   * @param a13 (2,4)成分
   * @param a20 (3,1)成分
   * @param a21 (3,2)成分
   * @param a22 (3,3)成分
   * @param a23 (3,4)成分
   * @param a30 (4,1)成分
   * @param a31 (4,2)成分
   * @param a32 (4,3)成分
   * @param a33 (4,4)成分
   */
  public Matrix4(float a00, float a01, float a02, float a03, float a10, float a11, float a12, float a13, float a20, float a21, float a22, float a23, float a30, float a31, float a32, float a33) {
    this.a[0][0] = a00; 
    this.a[0][1] = a01; 
    this.a[0][2] = a02; 
    this.a[0][3] = a03; 

    this.a[1][0] = a10; 
    this.a[1][1] = a11; 
    this.a[1][2] = a12; 
    this.a[1][3] = a13; 

    this.a[2][0] = a20; 
    this.a[2][1] = a21; 
    this.a[2][2] = a22; 
    this.a[2][3] = a23; 

    this.a[3][0] = a30; 
    this.a[3][1] = a31; 
    this.a[3][2] = a32; 
    this.a[3][3] = a33; 
  }
  
  /**
   * (i,j)成分を返します。
   * @param i 指数1
   * @param j 指数2
   * @return (i,j)成分
   */
  public float getElement(int i, int j) {
    return this.a[i][j];
  }
  
  /**
   * (i,j)成分を設定します。
   * @param i 指数
   * @param j 指数
   * @param aij (i,j)成分
   */
  public void setElement(int i, int j, float aij) {
    this.a[i][j] = aij;
  }
  
  /**
   * 行成分を設定します。
   * @param i 行番号
   * @param row 行成分
   */
  public void setRow(int i, float[] row) {
    this.a[i][0] = row[0];
    this.a[i][1] = row[1];
    this.a[i][2] = row[2];
    this.a[i][3] = row[3];
  }
  
  /**
   * スケールを返します。
   * @return スケール
   */
  public float getScale() {
    return 1;
  }
}
