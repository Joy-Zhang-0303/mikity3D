package org.mklab.mikity.view.renderer.jogl.primitive;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLPointerFunc;

import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;


/**
 * 三角形ポリゴンをJOGLで表したクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglTrianglePolygon extends AbstractJoglObject {
  /** 頂点 (v0, v1, v2)(反時計回り) */
  private float[][] vertices = new float[3][3];
  
  /** 法線ベクトル */
  private float[] normalVector = new float[3];

  /**
   * {@inheritDoc}
   */
  public void display(GL2 gl) {
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
    
    final float[] vertexArray = {x0, y0, z0, x1, y1, z1, x2, y2, z2};
    
    final float nx = this.normalVector[0];
    final float ny = this.normalVector[1];
    final float nz = this.normalVector[2];
    
    final float[] normalVectorArray = {nx,ny,nz,nx,ny,nz,nx,ny,nz};

    final FloatBuffer vertexBuffer = makeFloatBuffer(vertexArray);
    final FloatBuffer normalBuffer = makeFloatBuffer(normalVectorArray);

    // 法線配列の有効化
    gl.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
    // 法線バッファの指定
    gl.glNormalPointer(GL.GL_FLOAT, 0, normalBuffer);
    
    // 頂点配列の有効化
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
    // 頂点バッファの指定
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);

    gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3);
  }

  /**
   * 3個の頂点を設定します。
   * @param vertices 3個の頂点
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
