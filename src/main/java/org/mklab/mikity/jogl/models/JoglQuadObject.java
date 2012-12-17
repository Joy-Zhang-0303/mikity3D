package org.mklab.mikity.jogl.models;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglQuadObject extends AbstractJoglObject {
  private float[][] _point = new float[4][3];

  /** 色 */
  @XmlAttribute
  private String _color;

  /** 頂点バッファ */
  private FloatBuffer vertexBuffer;
  /** インデックスバッファ */
  private ByteBuffer indexBuffer;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    if (this._color != null) {
      applyColor(gl, this._color);
    }

    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

    gl.glDisable(GL.GL_CULL_FACE);

    //頂点バッファの生成
    final float[] vertices = {this._point[0][0], this._point[0][1], this._point[0][2], this._point[1][0], this._point[1][1], this._point[1][2], this._point[2][0], this._point[2][1], this._point[2][2],
        this._point[3][0], this._point[3][1], this._point[3][2],};
    this.vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = {0, 1, 2, 0, 2, 3};
    this.indexBuffer = makeByteBuffer(indices);

    //頂点バッファの指定
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, this.vertexBuffer);

    //プリミティブの描画
    gl.glDrawElements(GL.GL_TRIANGLES, 6, GL.GL_UNSIGNED_BYTE, this.indexBuffer);

    gl.glPopMatrix();
  }
  
  /**
   * float配列をFloatBufferに変換します。
   * 
   * @param array
   * @return
   */
  private FloatBuffer makeFloatBuffer(float[] array) {
    final FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    buffer.put(array).position(0);
    return buffer;
  }
  
  /**
   * byte配列をByteBufferに変換します。
   * 
   * @param array
   * @return
   */
  private ByteBuffer makeByteBuffer(byte[] array) {
    final ByteBuffer buffer = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
    buffer.put(array).position(0);
    return buffer;
  }

  /**
   * 頂点を設定します。
   * @param vertices 頂点
   */
  public void setVertices(float[][] vertices) {
    this._point = vertices;
  }

  /**
   * 色を設定します。
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }
}
