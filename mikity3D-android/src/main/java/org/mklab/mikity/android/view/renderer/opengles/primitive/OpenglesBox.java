/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesObject;


/**
 * ボックスを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesBox extends AbstractOpenglesObject {
  /** 幅 。*/
  private float width;
  /** 高さ 。*/
  private float height;
  /** 奥行。 */
  private float depth;

  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl) {
    applyColor(gl);
    
    //   v7 -- v3
    //  /      /
    // v4 -- v0
    //
    //   v6 -- v2
    //  /      /
    // v5 -- v1
    
    //   v5 -- v4
    //  /      /
    // v1 -- v0
    //
    //   v6 -- v7
    //  /      /
    // v2 -- v3
    
    float x0 = this.depth / 2;
    float y0 = this.width / 2;
    float z0 = this.height / 2;
    
    float x1 = this.depth / 2;
    float y1 = -this.width / 2;
    float z1 = this.height / 2;

    float x2 = this.depth / 2;
    float y2 = -this.width / 2;
    float z2 = -this.height / 2;
    
    float x3 = this.depth / 2;
    float y3 = this.width / 2;
    float z3 = -this.height / 2;
    
    float x4 = -this.depth / 2;
    float y4 = this.width / 2;
    float z4 = this.height / 2;
    
    float x5 = -this.depth / 2;
    float y5 = -this.width / 2;
    float z5 = this.height / 2;
    
    float x6 = -this.depth / 2;
    float y6 = -this.width / 2;
    float z6 = -this.height / 2;
    
    float x7 = -this.depth / 2;
    float y7 = this.width / 2;
    float z7 = -this.height / 2;
    
    //final float[] vertices = {x0, y0, z0, x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, x5, y5, z5, x6, y6, z6, x7, y7, z7};
    final float[] vertices = {
        x0, y0, z0, x4, y4, z4, x1, y1, z1,
        x1, y1, z1, x4, y4, z4, x5, y5, z5, 
        x1, y1, z1, x5, y5, z5, x2, y2, z2,
        x5, y5, z5, x6, y6, z6, x2, y2, z2,
        x2, y2, z2, x6, y6, z6, x3, y3, z3,
        x6, y6, z6, x7, y7, z7, x3, y3, z3,
        x3, y3, z3, x7, y7, z7, x0, y0, z0,
        x7, y7, z7, x4, y4, z4, x0, y0, z0,
        x4, y4, z4, x7, y7, z7, x5, y5, z5,
        x7, y7, z7, x6, y6, z6, x5, y5, z5,
        x0, y0, z0, x1, y1, z1, x3, y3, z3,
        x1, y1, z1, x2, y2, z2, x3, y3, z3};
    
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    // インデックスバッファの生成
    //final byte[] indices = {0, 4, 1, 5, 2, 6, 3, 7, 0, 4, 4, 7, 5, 6, 0, 1, 3, 2};
    final byte[] indices = 
      {0, 1, 2, 
        3, 4, 5,
        6, 7, 8,
        9, 10, 11,
        12, 13, 14,
        15, 16, 17,
        18, 19, 20,
        21, 22, 23,
        24, 25, 26,
        27, 28, 29,
        30, 31, 32,
        33, 34, 35};
    
//    {0, 4, 1, 
//      4, 5, 1,
//      1, 5, 2,
//      5, 6, 2,
//      2, 6, 3,
//      6, 7, 3,
//      3, 7, 0,
//      7, 4, 0,
//      4, 7, 5,
//      7, 6, 5,
//      0, 1, 3,
//      1, 2, 3};
    
    final ByteBuffer indexBuffer = makeByteBuffer(indices);
    
    final float[] normals = 
      {0, 0, 1,
        0, 0, 1,
        0, 0, 1,
        0, 0, 1,
        0, 0, 1,
        0, 0, 1,
        0, -1, 0,
        0, -1, 0,
        0, -1, 0,
        0, -1, 0,
        0, -1, 0,
        0, -1, 0,
        0, 0, -1,
        0, 0, -1,
        0, 0, -1,
        0, 0, -1,
        0, 0, -1,
        0, 0, -1,
        0, 1, 0,
        0, 1, 0,
        0, 1, 0,
        0, 1, 0,
        0, 1, 0,
        0, 1, 0,
        -1, 0, 0,
        -1, 0, 0,
        -1, 0, 0,
        -1, 0, 0,
        -1, 0, 0,
        -1, 0, 0,
        1, 0, 0,
        1, 0, 0,
        1, 0, 0,
        1, 0, 0,
        1, 0, 0,
        1, 0, 0};

    final FloatBuffer normalsBuffer = makeFloatBuffer(normals);

    //法線配列の有効化
    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
    
    gl.glNormalPointer(GL10.GL_FLOAT, 0, normalsBuffer);
    
    // 頂点配列の有効化
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    
    // 頂点バッファの指定
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

    indexBuffer.position(0);
    gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, indexBuffer);

//    indexBuffer.position(10);
//    gl10.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indexBuffer);
//
//    indexBuffer.position(14);
//    gl10.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indexBuffer);
  }

  /**
   * 大きさを設定します。
   * 
   * @param width 幅
   * @param height 高さ
   * @param depth 奥行
   */
  public void setSize(float width, float height, float depth) {
    this.width = width;
    this.height = height;
    this.depth = depth;
  }
}
