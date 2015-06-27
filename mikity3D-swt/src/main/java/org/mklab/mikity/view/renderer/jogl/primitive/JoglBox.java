package org.mklab.mikity.view.renderer.jogl.primitive;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLPointerFunc;

import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;


/**
 * BOXをGLで表したクラスです
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/26
 */
public class JoglBox extends AbstractJoglObject {

  /** 幅 */
  private float width;

  /** 高さ */
  private float height;

  /** 奥行 */
  private float depth;

  /**
   * {@inheritDoc}
   */
  public void display(GL2 gl) {
    applyColor(gl);
    applyTransparency(gl);

    //頂点配列の有効化
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);

    //デプステストの有効化
    gl.glEnable(GL.GL_DEPTH_TEST);

    // 表と裏を両方表示する
    gl.glDisable(GL.GL_CULL_FACE);

    float x0 = this.depth / 2;
    float y0 = this.width / 2;
    float z0 = this.height / 2;
    
    float x1 = this.depth / 2;
    float y1 = -this.width / 2;
    float z1 = this.height / 2;

    float x2 = this.depth / 2;
    float y2 = -this.width / 2;
    float z2 = -this.height / 2;
    
    float x3 = this.depth / 2;
    float y3 = this.width / 2;
    float z3 = -this.height / 2;
    
    float x4 = -this.depth / 2;
    float y4 = this.width / 2;
    float z4 = this.height / 2;
    
    float x5 = -this.depth / 2;
    float y5 = -this.width / 2;
    float z5 = this.height / 2;
    
    float x6 = -this.depth / 2;
    float y6 = -this.width / 2;
    float z6 = -this.height / 2;
    
    float x7 = -this.depth / 2;
    float y7 = this.width / 2;
    float z7 = -this.height / 2;
    
    final float[] vertices = {x0, y0, z0, x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, x5, y5, z5, x6, y6, z6, x7, y7, z7};
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = {0, 4, 1, 5, 2, 6, 3, 7, 0, 4, 4, 7, 5, 6, 0, 1, 3, 2};
    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    //頂点バッファの指定
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);

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
   * @param width 幅
   * @param height 高さ
   * @param depth 奥行
   */
  public void setSize(float width, float height, float depth) {
    this.width = width;
    this.height = height;
    this.depth = depth;
  }

}
