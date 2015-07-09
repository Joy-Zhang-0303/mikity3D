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
  /** 頂点 */
  private float[][] points = new float[3][3];
  
  /** 法線ベクトル */
  private float[] normalVector = new float[3];

  /**
   * {@inheritDoc}
   */
  public void display(GL2 gl) {
    applyColor(gl);
    applyTransparency(gl);

    //頂点バッファの生成
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
    
    final float nx = this.normalVector[0];
    final float ny = this.normalVector[1];
    final float nz = this.normalVector[2];
    
    final float[] normals = {nx,ny,nz,nx,ny,nz,nx,ny,nz};

    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);
    final FloatBuffer normalBuffer = makeFloatBuffer(normals);

    // 法線配列の有効化
    gl.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
    // 法線バッファの指定
    gl.glNormalPointer(GL.GL_FLOAT, 0, normalBuffer);
    
    // 頂点配列の有効化
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
    // 頂点バッファの有効化
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);

    gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3); //プリミティブの描画
  }

  /**
   * 頂点を設定します。
   * @param points 頂点
   */
  public void setPoints(float[][] points) {
    this.points = points;
  }
  
  /**
   * 法線ベクトルを設定します。
   * @param normalVector 法線ベクトル
   */
  public void setNormalVector(float[] normalVector) {
    this.normalVector = normalVector;
  }

}
