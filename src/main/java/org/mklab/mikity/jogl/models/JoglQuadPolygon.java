package org.mklab.mikity.jogl.models;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglQuadPolygon extends AbstractJoglObject {
  /** 頂点 */
  private float[][] points = new float[4][3];

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    applyColor(gl);

    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

    gl.glDisable(GL.GL_CULL_FACE);

    //頂点バッファの生成
    final float[] vertices = {this.points[0][0], this.points[0][1], this.points[0][2], this.points[1][0], this.points[1][1], this.points[1][2], this.points[2][0], this.points[2][1], this.points[2][2],
        this.points[3][0], this.points[3][1], this.points[3][2],};
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = {0, 1, 2, 0, 2, 3};
    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    //頂点バッファの指定
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);

    //プリミティブの描画
    gl.glDrawElements(GL.GL_TRIANGLES, 6, GL.GL_UNSIGNED_BYTE, indexBuffer);

    //gl.glPopMatrix();
  } 

  /**
   * 頂点を設定します。
   * @param points 頂点
   */
  public void setPoints(float[][] points) {
    this.points = points;
  }
}
