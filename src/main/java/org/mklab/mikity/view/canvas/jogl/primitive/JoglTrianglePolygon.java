package org.mklab.mikity.view.canvas.jogl.primitive;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglTrianglePolygon extends AbstractJoglObject {
  /** 頂点 */
  private float[][] points = new float[3][3];

  /**
   * {@inheritDoc}
   */
  public void display(GL gl) {
    applyColor(gl);

    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

    gl.glDisable(GL.GL_CULL_FACE);

    //頂点バッファの生成
    final float[] vertices = {this.points[0][0], this.points[0][1], this.points[0][2], this.points[1][0], this.points[1][1], this.points[1][2], this.points[2][0], this.points[2][1], this.points[2][2],};

    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

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
}
