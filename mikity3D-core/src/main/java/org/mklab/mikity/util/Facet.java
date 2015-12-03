/*
 * Created on 2015/11/19
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;


/**
 * {@link STL}の三角形の面を表すクラスです。
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
  private short unusedData = 0;
  
  /**
   * データ入力ストリームからバイナリデータを読み込みます。
   * 
   * @param input データ入力ストリーム
   * @return 読み込んだデータから構成される三角形の面
   * @throws IOException データを読み込めない場合
   */
  public static Facet loadBinaryData(DataInputStream input) throws IOException {
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

    facet.unusedData = Short.reverseBytes(input.readShort());
    
    return facet;
  }
  
  /**
   * BufferedReaderからテキストデータを読み込みます。
   * 
   * @param input 入力
   * @return 読み込んだデータから構成される三角形の面
   * @throws IOException データを読み込めない場合
   */
  public static Facet loadTextData(BufferedReader input) throws IOException {
    final Facet facet = new Facet();
    
    final String line1 = input.readLine();
    final String[] line1Words = line1.trim().replaceAll("[ \\t]+", " ").split("[ ]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    
    if (line1Words.length != 5 || line1Words[0].equals("facet")==false || line1Words[1].equals("normal")==false) { //$NON-NLS-1$ //$NON-NLS-2$
      throw new IOException("The first line must have 'facet normal ni nj nk'"); //$NON-NLS-1$
    }
    
    final String line2 = input.readLine();
    if (line2.contains("outer loop") == false) { //$NON-NLS-1$
      throw new IOException("The second line must be '  outer loop'"); //$NON-NLS-1$
    }
    
    facet.vertex1 = loadTextVertex(input);
    facet.vertex2 = loadTextVertex(input);
    facet.vertex3 = loadTextVertex(input);
    
    final String line6 = input.readLine();
    if (line6.contains("endloop") == false) { //$NON-NLS-1$
      throw new IOException("The 6th line must be '  endloop'"); //$NON-NLS-1$
    }
    
    final String line7 = input.readLine();
    if (line7.contains("endfacet") == false) { //$NON-NLS-1$
      throw new IOException("The 7th line must be 'endfacet'"); //$NON-NLS-1$
    }
    
    return facet;
  }

  /**
   * BufferedReaderからテキスト形式のデータを読み込みます。
   * 
   * @param input 入力
   * @return 頂点の配列
   * @throws IOException データを読み込めない場合
   */
  private static float[] loadTextVertex(BufferedReader input) throws IOException {
    final String line = input.readLine();
    final String[] words = line.trim().replaceAll("[ \\t]+", " ").split("[ ]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    if (words.length != 4 || words[0].equals("vertex") == false) { //$NON-NLS-1$
      throw new IOException("The line must have 'vertex vx vy vz'"); //$NON-NLS-1$
    }
    float vx = Float.parseFloat(words[1]);
    float vy = Float.parseFloat(words[2]);
    float vz = Float.parseFloat(words[3]);
    
    return new float[]{vx, vy, vz};
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
   * 
   * @return 未使用データ
   */
  public short getUnusedData() {
    return this.unusedData;
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
    return normal + "," + v1 + "," + v2 + "," + v3 + "," + String.format("0x%x", Short.valueOf(this.unusedData));  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
  }
}
