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
public class OpenglesQuadPolygon extends AbstractOpenglesObject {
  /** 頂点(v0, v1, v2, v3)(反時計回り)。 */
  private float[][] vertices = new float[4][3];
  
  /** 法線ベクトル */
  private float[] normalVector = new float[3];

  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl) {
    applyColor(gl);
    applyTransparency(gl);

    // 頂点バッファの生成
    float x0 = this.vertices[0][0];
    float y0 = this.vertices[0][1];
    float z0 = this.vertices[0][2];
    
    float x1 = this.vertices[1][0];
    float y1 = this.vertices[1][1];
    float z1 = this.vertices[1][2];
    
    float x2 = this.vertices[2][0];
    float y2 = this.vertices[2][1];
    float z2 = this.vertices[2][2];
    
    float x3 = this.vertices[3][0];
    float y3 = this.vertices[3][1];
    float z3 = this.vertices[3][2];
    
    final float[] vertexArray = {
        x0, y0, z0, x1, y1, z1, x2, y2, z2,
        x0, y0, z0, x2, y2, z2, x3, y3, z3};
    
    final float nx = this.normalVector[0];
    final float ny = this.normalVector[1];
    final float nz = this.normalVector[2];
    
    final float[] normalVectorArray = {nx,ny,nz,nx,ny,nz,nx,ny,nz,nx,ny,nz,nx,ny,nz,nx,ny,nz};
    
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertexArray);
    final FloatBuffer normalBuffer = makeFloatBuffer(normalVectorArray);

    // 法線配列の有効化
    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
    // 法線バッファの指定
    gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);

    // 頂点配列の有効化
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    // 頂点バッファの指定
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

    //プリミティブの描画
    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
  } 

  /**
   * 4個の頂点を設定します。
   * @param vertices 4個の頂点
   */
  public void setVertices(float[][] vertices) {
    this.vertices = vertices;
  }
  
  /**
   * 法線ベクトルを設定します。
   * @param normalVector 法線ベクトル
   */
  public void setNormalVector(float[] normalVector) {
    this.normalVector = normalVector;
  }
}


