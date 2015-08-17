/*
 * Created on 2015/08/01
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.graphic;

/**
 * グラフィックプリミティブを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/01
 */
public interface GraphicPrimitive {

  /**
   * 頂点配列を返します。
   * 
   * @return 頂点配列
   */
  float[] getVertexArray();

  /**
   * 頂点配列の長さを返します。
   * 
   * @return 頂点配列の長さ
   */
  int getVertexArrayLength();

  
  /**
   * 法線ベクトル配列を返します。
   * 
   * @return 法線ベクトル配列
   */
  float[] getNormalVectorArray();

  /**
   * 法線ベクトル配列の長さを返します。
   * 
   * @return 法線ベクトル配列の長さ
   */
  int getNormalVectorArrayLength();
}