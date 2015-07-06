package org.mklab.mikity.view.renderer.jogl.primitive;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLPointerFunc;

import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;


/**
 * 四角形ポリゴンをGLで表したクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglQuadPolygon extends AbstractJoglObject {

  /** 頂点 */
  private float[][] points = new float[4][3];

  /**
   * {@inheritDoc}
   */
  public void display(GL2 gl) {
    applyColor(gl);
    applyTransparency(gl);

    //頂点配列の有効化
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);

    // 表と裏を両方表示する
    //gl.glDisable(GL.GL_CULL_FACE);

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
    
    float x3 = this.points[3][2];
    float y3 = this.points[3][0];
    float z3 = this.points[3][1];
    
    final float[] vertices = {x0, y0, z0, x1, y1, z1, x2, y2, z2, x3, y3, z3};
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = {0, 1, 2, 0, 2, 3};
    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    //頂点バッファの指定
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);

    //プリミティブの描画
    gl.glDrawElements(GL.GL_TRIANGLES, 6, GL.GL_UNSIGNED_BYTE, indexBuffer);
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
