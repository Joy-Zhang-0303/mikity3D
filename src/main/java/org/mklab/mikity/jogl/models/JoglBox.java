package org.mklab.mikity.jogl.models;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;


/**
 * XMLBOXをJOGLで表したクラスです
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/26
 */
public class JoglBox extends AbstractJoglObject {
  /** _xsize */
  @XmlAttribute
  private float _xsize;

  /** _ysize */
  @XmlAttribute
  private float _ysize;

  /** _zsize */
  @XmlAttribute
  private float _zsize;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

    //デプステストの有効化
    gl.glEnable(GL.GL_DEPTH_TEST);

    final float[] vertices = {this._xsize / 2, this._ysize / 2, this._zsize / 2, -this._xsize / 2, this._ysize / 2, this._zsize / 2, -this._xsize / 2, -this._ysize / 2, this._zsize / 2,
        this._xsize / 2, -this._ysize / 2, this._zsize / 2, this._xsize / 2, this._ysize / 2, -this._zsize / 2, -this._xsize / 2, this._ysize / 2, -this._zsize / 2, -this._xsize / 2,
        -this._ysize / 2, -this._zsize / 2, this._xsize / 2, -this._ysize / 2, -this._zsize / 2,};
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = {0, 4, 1, 5, 2, 6, 3, 7, 0, 4, 4, 7, 5, 6, 0, 1, 3, 2};
    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    //頂点バッファの指定
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);

    applyColor(gl);

    indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP, 10, GL.GL_UNSIGNED_BYTE, indexBuffer);

    indexBuffer.position(10);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP, 4, GL.GL_UNSIGNED_BYTE, indexBuffer);

    indexBuffer.position(14);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP, 4, GL.GL_UNSIGNED_BYTE, indexBuffer);

    gl.glPopMatrix();
  }

  /**
   * 大きさを設定します。
   * 
   * @param xSize xの長さ
   * @param ySize yの長さ
   * @param zSize zの長さ
   */
  public void setSize(float xSize, float ySize, float zSize) {
    this._xsize = xSize;
    this._ysize = ySize;
    this._zsize = zSize;
  }
}
