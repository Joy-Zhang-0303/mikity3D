/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.jogl.primitive;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;


/**
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesBox extends AbstractOpenglesObject {

  /** 幅 */
  private float xSize;

  /** 奥行 */
  private float ySize;

  /** 高さ */
  private float zSize;

  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl10) {
    //頂点配列の有効化
    gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);

    //デプステストの有効化
    gl10.glEnable(GL10.GL_DEPTH_TEST);

    final float[] vertices = {this.xSize / 2, this.ySize / 2, this.zSize / 2, -this.xSize / 2, this.ySize / 2, this.zSize / 2, -this.xSize / 2, -this.ySize / 2, this.zSize / 2, this.xSize / 2,
        -this.ySize / 2, this.zSize / 2, this.xSize / 2, this.ySize / 2, -this.zSize / 2, -this.xSize / 2, this.ySize / 2, -this.zSize / 2, -this.xSize / 2, -this.ySize / 2, -this.zSize / 2,
        this.xSize / 2, -this.ySize / 2, -this.zSize / 2,};
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = {0, 4, 1, 5, 2, 6, 3, 7, 0, 4, 4, 7, 5, 6, 0, 1, 3, 2};
    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    //頂点バッファの指定
    gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

    applyColor(gl10);

    indexBuffer.position(0);
    gl10.glDrawElements(GL10.GL_TRIANGLE_STRIP, 10, GL10.GL_UNSIGNED_BYTE, indexBuffer);

    indexBuffer.position(10);
    gl10.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indexBuffer);

    indexBuffer.position(14);
    gl10.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indexBuffer);
  }

  /**
   * 大きさを設定します。
   * 
   * @param xSize 幅
   * @param ySize 高さ
   * @param zSize 奥行
   */
  public void setSize(float xSize, float ySize, float zSize) {
    this.xSize = xSize;
    this.ySize = ySize;
    this.zSize = zSize;
  }
}
