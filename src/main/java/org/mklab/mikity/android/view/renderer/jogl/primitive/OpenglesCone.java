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
public class OpenglesCone extends AbstractOpenglesObject {

  /** 底面の半径 */
  protected float radius;

  /** 高さ */
  protected float height;

  /** 分割数 */
  protected int div;

  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl10) {
    //頂点配列の有効化
    gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);

    //デプステストの有効化
    gl10.glEnable(GL10.GL_DEPTH_TEST);

    final float[] vertices = new float[(this.div + 2) * 3];

    // TODO 描画は出来てますが、Normal3fを使うとバグがでます。
    // TODO vertexs[]かindexs[]にどこか問題があると思います。

    //頂点バッファの生成
    vertices[0] = 0.0f;
    vertices[1] = this.height / 2.0f;
    vertices[2] = 0.0f;

    for (int i = 1; i <= this.div; i++) {
      final double theta = 2.0 * Math.PI / this.div * i;
      vertices[i * 3] = this.radius * (float)Math.cos(theta);
      vertices[i * 3 + 1] = -this.height / 2.0f;
      vertices[i * 3 + 2] = this.radius * (float)Math.sin(theta);
    }

    vertices[3 + this.div * 3] = 0.0f;
    vertices[4 + this.div * 3] = -this.height / 2.0f;
    vertices[5 + this.div * 3] = 0.0f;

    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = new byte[this.div * 6];

    for (int i = 1; i <= this.div; i++) {
      indices[3 * i - 3] = 0;
    }

    for (int i = 1; i <= this.div; i++) {
      indices[3 * i - 2] = (byte)i;
    }

    for (int i = 1; i <= (this.div - 1); i++) {
      indices[3 * i - 1] = (byte)(i + 1);
    }

    indices[3 * this.div - 1] = 1;

    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 3 + 3 * i - 3] = (byte)(this.div + 1);
    }

    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 3 + 3 * i - 2] = (byte)(i);
    }

    for (int i = 1; i <= this.div - 1; i++) {
      indices[this.div * 3 + 3 * i - 1] = (byte)(i + 1);
    }

    indices[this.div * 6 - 1] = 1;

    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    applyColor(gl10);

    //頂点バッファの指定 
    gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

    //    gl.glNormal3f(0.0f, 1.0f, 0.0f);
    //    this.indexBuffer.position(0);
    //    gl.glDrawElements(GL.GL_TRIANGLE_STRIP,this._div*3,GL.GL_UNSIGNED_BYTE,this.indexBuffer);
    //    
    //    gl.glNormal3f(0.0f, -1.0f, 0.0f);   
    //    this.indexBuffer.position(this._div*3);
    //    gl.glDrawElements(GL.GL_TRIANGLE_STRIP,this._div*3,GL.GL_UNSIGNED_BYTE,this.indexBuffer);

    indexBuffer.position(0);
    gl10.glDrawElements(GL10.GL_TRIANGLE_STRIP, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 底面の半径
   * @param hight 高さ
   */
  public void setSize(float radius, float hight) {
    this.radius = radius;
    this.height = hight;
  }

  /**
   * 分割数を設定します。
   * 
   * @param div 分割数
   */
  public void setDiv(int div) {
    this.div = div;
  }
}
