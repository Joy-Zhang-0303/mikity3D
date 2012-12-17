package org.mklab.mikity.jogl.models;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglTriangleObject extends AbstractJoglObject {
  /** 頂点 */
  private float[][] _point = new float[3][3];

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    applyColor(gl);

    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

    gl.glDisable(GL.GL_CULL_FACE);

    //頂点バッファの生成
    final float[] vertices = {this._point[0][0], this._point[0][1], this._point[0][2], this._point[1][0], this._point[1][1], this._point[1][2], this._point[2][0], this._point[2][1], this._point[2][2],};

    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);
    gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3); //プリミティブの描画

    gl.glPopMatrix();
  }

  /**
   * 頂点を設定します。
   * @param vertices 頂点
   */
  public void setVertices(float[][] vertices) {
    this._point = vertices;
  }
}
