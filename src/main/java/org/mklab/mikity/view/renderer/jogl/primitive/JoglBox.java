package org.mklab.mikity.view.renderer.jogl.primitive;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;

import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;


/**
 * XMLBOXをJOGLで表したクラスです
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/26
 */
public class JoglBox extends AbstractJoglObject {
  /** 幅 */
  private float xSize;

  /** 奥行 */
  private float ySize;

  /** 高さ */
  private float zSize;

  /**
   * {@inheritDoc}
   */
  public void display(GL gl) {
    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

    //デプステストの有効化
    gl.glEnable(GL.GL_DEPTH_TEST);

    final float[] vertices = {this.xSize / 2, this.ySize / 2, this.zSize / 2, -this.xSize / 2, this.ySize / 2, this.zSize / 2, -this.xSize / 2, -this.ySize / 2, this.zSize / 2,
        this.xSize / 2, -this.ySize / 2, this.zSize / 2, this.xSize / 2, this.ySize / 2, -this.zSize / 2, -this.xSize / 2, this.ySize / 2, -this.zSize / 2, -this.xSize / 2,
        -this.ySize / 2, -this.zSize / 2, this.xSize / 2, -this.ySize / 2, -this.zSize / 2,};
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
  }

  /**
   * 大きさを設定します。
   * 
   * @param xSize 幅
   * @param ySize 高さ
   * @param zSize 奥行
   */
  public void setSize(float xSize, float ySize, float zSize) {
    this.xSize = xSize;
    this.ySize = ySize;
    this.zSize = zSize;
  }
}
