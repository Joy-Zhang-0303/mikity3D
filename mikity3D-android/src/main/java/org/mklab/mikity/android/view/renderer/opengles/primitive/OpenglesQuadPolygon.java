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
 * 四角ポリゴンを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesQuadPolygon extends AbstractOpenglesObject {
  /** 頂点。 */
  private float[][] points = new float[4][3];

  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl) {
    applyColor(gl);

    // 頂点配列の有効化
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

    // 頂点バッファの生成
    float x0 = this.points[0][2];
    float y0 = this.points[0][0];
    float z0 = this.points[0][1];
    
    float x1 = this.points[1][2];
    float y1 = this.points[1][0];
    float z1 = this.points[1][1];
    
    float x2 = this.points[2][2];
    float y2 = this.points[2][0];
    float z2 = this.points[2][1];
    
    float x3 = this.points[3][2];
    float y3 = this.points[3][0];
    float z3 = this.points[3][1];
    
    final float[] vertices = {x0, y0, z0, x1, y1, z1, x2, y2, z2, x3, y3, z3};
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    // インデックスバッファの生成
    final byte[] indices = {0, 1, 2, 0, 2, 3};
    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    // 頂点バッファの指定
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

    // プリミティブの描画
    gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_BYTE, indexBuffer);
  } 

  /**
   * 頂点を設定します。
   * @param points 頂点
   */
  public void setPoints(float[][] points) {
    this.points = points;
  }
}


