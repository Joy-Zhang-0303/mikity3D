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
public class OpenglesCylinder extends AbstractOpenglesObject {
  /** 底面の半径 */
  private float radius;

  /** 高さ */
  private float height;

  /** 分割数 */
  private int div;

  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl10) {
    //頂点配列の有効化
    gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);

    //デプステストの有効化
    gl10.glEnable(GL10.GL_DEPTH_TEST);

    final float[] vertices = new float[(this.div * 2 + 2) * 3];

    // TODO 描画がおかしいですが、これ以上考えても今は案が出てこないのでPushしました。
    // TODO vertexsかindexsの配列がおかしいのかもしれません。
    //頂点バッファの生成
    vertices[0] = 0.0f;
    vertices[1] = this.height / 2.0f;
    vertices[2] = 0.0f;
    
    for (int i = 1; i <= this.div; i++) {
      final double theta = 2.0 * Math.PI / this.div * i;
      vertices[i * 3] = this.radius * (float)Math.cos(theta);
      vertices[i * 3 + 1] = this.height / 2.0f;
      vertices[i * 3 + 2] = this.radius * (float)Math.sin(theta);
    }
    
    vertices[3 + this.div * 3] = 0.0f;
    vertices[4 + this.div * 3] = -this.height / 2.0f;
    vertices[5 + this.div * 3] = 0.0f;
    
    for (int i = 1; i <= this.div; i++) {
      final double theta = 2.0 * Math.PI / this.div * i;
      vertices[i * 3 + 3 + this.div * 3] = this.radius * (float)Math.cos(theta);
      vertices[i * 3 + 4 + this.div * 3] = -this.height / 2.0f;
      vertices[i * 3 + 5 + this.div * 3] = this.radius * (float)Math.sin(theta);
    }

    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = new byte[this.div * 12];
    
    for (int i = 1; i <= this.div; i++) {
      indices[3 * i - 3] = 0;
    }
    
    for (int i = 1; i <= this.div; i++) {
      indices[3 * i - 2] = (byte)i;
    }
    
    for (int i = 1; i <= this.div-1; i++) {
      indices[3 * i - 1] = (byte)(i + 1);
    }
    
    indices[3 * this.div - 1] = 1;
    
    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 3 + 3 * i - 3] = (byte)(1 + this.div);
    }
    
    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 3 + 3 * i - 2] = (byte)(i + 1 + this.div);
    }
    
    for (int i = 1; i <= this.div-1; i++) {
      indices[this.div * 3 + 3 * i - 1] = (byte)(i + 2 + this.div);
    }
    
    indices[this.div * 6 - 1] = (byte)(this.div + 2);

    //側面

    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 6 + 3 * i - 3] = (byte)(this.div + 1 + i);
    }
    
    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 6 + 3 * i - 2] = (byte)i;
    }
    
    for (int i = 1; i <= this.div - 1; i++) {
      indices[this.div * 6 + 3 * i - 1] = (byte)(i + 1);
    }

    indices[this.div * 9 - 1] = 1;

    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 9 + 3 * i - 3] = (byte)(i);
    }

    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 9 + 3 * i - 2] = (byte)(i + 1 + this.div);
    }
    
    for (int i = 1; i <= this.div - 1; i++) {
      indices[this.div * 9 + 3 * i - 1] = (byte)(i + 2 + this.div);
    }

    indices[this.div * 12 - 1] = (byte)(this.div + 1);

    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    applyColor(gl10);

 

    //頂点バッファの指定 
    gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

    indexBuffer.position(0);
    gl10.glDrawElements(GL10.GL_TRIANGLE_STRIP,indices.length,GL10.GL_UNSIGNED_BYTE, indexBuffer);
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 底面の半径
   * @param height 高さ
   */
  public void setSize(float radius, float height) {
    this.radius = radius;
    this.height = height;
  }
  
  /**
   * 分割数を設定します。
   * @param div 分割数
   */
  public void setDiv(int div) {
    this.div = div;
  }
}
