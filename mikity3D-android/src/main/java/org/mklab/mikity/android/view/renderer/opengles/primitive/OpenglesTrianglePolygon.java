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
 * 四角ポリゴンをOpenGLESで表したクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesTrianglePolygon extends AbstractOpenglesObject {
  /** 頂点(v0, v1, v2)(反時計回り)。 */
  private float[][] points = new float[3][3];
  
  /** 法線ベクトル */
  private float[] normalVector = new float[3];


  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl) {
    applyColor(gl);
    applyTransparency(gl);

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
    
    final float[] vertexArray = {x0, y0, z0, x1, y1, z1, x2, y2, z2};
    
    final float nx = this.normalVector[0];
    final float ny = this.normalVector[1];
    final float nz = this.normalVector[2];
    
    final float[] normalVectorArray = {nx,ny,nz,nx,ny,nz,nx,ny,nz};
    
    final FloatBuffer normalBuffer = makeFloatBuffer(normalVectorArray);
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertexArray);

    // 法線配列の有効化
    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
    // 法線バッファの指定
    gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
    
    // 頂点配列の有効化
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    // 頂点バッファの指定
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
    
    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
  }

  /**
   * 3個の頂点を設定します。
   * 
   * @param vertices 3個の頂点
   */
  public void setVertices(float[][] vertices) {
    this.points = vertices;
  }
  
  /**
   * 法線ベクトルを設定します。
   * @param normalVector 法線ベクトル
   */
  public void setNormalVector(float[] normalVector) {
    this.normalVector = normalVector;
  }
}
