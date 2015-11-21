/*
 * Created on 2015/11/19
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import java.io.DataInputStream;
import java.io.IOException;


/**
 * STLの三角形の面を表すクラスです。
 *  
 * @author koga
 * @version $Revision$, 2015/11/19
 */
public class Facet {
  /** 法線ベクトル。 */
  private float[] normalVector = new float[3];
  
  /** 頂点1。 */
  private float[] vertex1 = new float[3];
  
  /** 頂点2。 */
  private float[] vertex2 = new float[3];

  /** 頂点3。 */
  private float[] vertex3 = new float[3];
  
  /** 未使用データ。 */
  private short data = 0;
  
  /**
   * データ入力ストリームからデータを読み込みます。
   * 
   * @param input データ入力ストリーム
   * @return 読み込んだデータから構成される三角形の面
   * @throws IOException データを読み込めない場合
   */
  public static Facet load(DataInputStream input) throws IOException {
    final Facet facet = new Facet();
    
    facet.normalVector[0] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));
    facet.normalVector[1] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));
    facet.normalVector[2] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));
    
    facet.vertex1[0] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));
    facet.vertex1[1] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));
    facet.vertex1[2] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));

    facet.vertex2[0] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));
    facet.vertex2[1] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));
    facet.vertex2[2] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));

    facet.vertex3[0] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));
    facet.vertex3[1] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));
    facet.vertex3[2] = Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));

    facet.data = Short.reverseBytes(input.readShort());
    
    return facet;
  }
  
  /**
   * 法線ベクトルを返します。
   * 
   * @return 法線ベクトル
   */
  public float[] getNormalVector() {
    return this.normalVector;
  }
  
  /**
   * 頂点1を返します。
   * @return 頂点1
   */
  public float[] getVertex1() {
    return this.vertex1;
  }
  
  /**
   * 頂点2を返します。
   * @return 頂点2
   */
  public float[] getVertex2() {
    return this.vertex2;
  }
  
  /**
   * 頂点3を返します。
   * @return 頂点3
   */
  public float[] getVertex3() {
    return this.vertex3;
  }

  /**
   * 未使用データを返します。
   * @return 未使用データ
   */
  public short getData() {
    return this.data;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    final String normal = "normal(" + this.normalVector[0] + "," + this.normalVector[1] + "," + this.normalVector[2] + ")";  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    final String v1 = "v1(" + this.vertex1[0] + "," + this.vertex1[1] + "," + this.vertex1[2] + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    final String v2 = "v2(" + this.vertex2[0] + "," + this.vertex2[1] + "," + this.vertex2[2] + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    final String v3 = "v3(" + this.vertex3[0] + "," + this.vertex3[1] + "," + this.vertex3[2] + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    return normal + "," + v1 + "," + v2 + "," + v3 + "," + String.format("0x%x", Short.valueOf(this.data));  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
  }
}
