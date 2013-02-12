/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.jogl.primitive;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesQuadPolygon extends AbstractOpenglesObject {
  /** 頂点 */
  private float[][] points = new float[4][3];

  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl10) {
    applyColor(gl10);

    //頂点配列の有効化
    gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);

    gl10.glDisable(GL10.GL_CULL_FACE);

    //頂点バッファの生成
    final float[] vertices = {this.points[0][0], this.points[0][1], this.points[0][2], this.points[1][0], this.points[1][1], this.points[1][2], this.points[2][0], this.points[2][1], this.points[2][2],
        this.points[3][0], this.points[3][1], this.points[3][2],};
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = {0, 1, 2, 0, 2, 3};
    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    //頂点バッファの指定
    gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

    //プリミティブの描画
    gl10.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_BYTE, indexBuffer);
  } 

  /**
   * 頂点を設定します。
   * @param points 頂点
   */
  public void setPoints(float[][] points) {
    this.points = points;
  }
}


