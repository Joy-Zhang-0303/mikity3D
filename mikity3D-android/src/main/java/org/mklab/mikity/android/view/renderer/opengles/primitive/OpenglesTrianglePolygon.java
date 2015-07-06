/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesObject;


/**
 * 四角ポリゴンを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesTrianglePolygon extends AbstractOpenglesObject {
  /** 頂点。 */
  private float[][] points = new float[3][3];

  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl10) {
    applyColor(gl10);

    // 頂点配列の有効化
    gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);

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
    
    final float[] vertices = {x0, y0, z0, x1, y1, z1, x2, y2, z2};

    final FloatBuffer buffer = makeFloatBuffer(vertices);

    gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, buffer);
    gl10.glDrawArrays(GL10.GL_TRIANGLES, 0, 3); //プリミティブの描画
  }

  /**
   * 頂点を設定します。
   * 
   * @param points 頂点
   */
  public void setPoints(float[][] points) {
    this.points = points;
  }
}
