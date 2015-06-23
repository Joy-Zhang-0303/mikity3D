package org.mklab.mikity.view.renderer.jogl.primitive;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLPointerFunc;

import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;


/**
 * シリンダーをGLで表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/31
 */
public class JoglCylinder extends AbstractJoglObject {
  /** 底面の半径 */
  private float radius;

  /** 高さ */
  private float height;

  /** 分割数 */
  private int division;

  /**
   * {@inheritDoc}
   */
  public void display(GL2 gl) {
    applyColor(gl);
    applyTransparency(gl);
    
    // 頂点配列の有効化
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);

    // デプステストの有効化
    gl.glEnable(GL.GL_DEPTH_TEST);

    // 表と裏を両方表示する
    gl.glDisable(GL.GL_CULL_FACE);
    
    final float[] vertices = new float[(this.division * 2 + 2) * 3];
    
    //頂点バッファの生成
    
    // 上面の中心点(0)
    vertices[0] = 0.0f;
    vertices[1] = this.height / 2.0f;
    vertices[2] = 0.0f;

    // 上面の周上の点(1 - div)
    for (int i = 1; i <= this.division; i++) {
      final double theta = 2.0 * Math.PI / this.division * i;
      vertices[i * 3 + 0] = (float)(this.radius * Math.cos(theta));
      vertices[i * 3 + 1] = this.height / 2.0f;
      vertices[i * 3 + 2] = (float)(this.radius * Math.sin(theta));
    }
    
    // 下面の中心点(div+1)
    vertices[this.division * 3 + 3] = 0.0f;
    vertices[this.division * 3 + 4] = -this.height / 2.0f;
    vertices[this.division * 3 + 5] = 0.0f;

    // 下面の周上の点([div+1+1] - [div+1+div+1])
    for (int i = 1; i <= this.division; i++) {
      final double theta = 2.0 * Math.PI / this.division * i;
      vertices[this.division * 3 + i * 3 + 3] = (float)(this.radius * Math.cos(theta));
      vertices[this.division * 3 + i * 3 + 4] = -this.height / 2.0f;
      vertices[this.division * 3 + i * 3 + 5] = (float)(this.radius * Math.sin(theta));
    }

    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = new byte[this.division * 12];

    // 上面(中心点)
    for (int i = 1; i <= this.division; i++) {
      indices[3 * i - 3] = 0;
    }

    for (int i = 1; i <= this.division; i++) {
      indices[3 * i - 2] = (byte)i;
    }
 
    for (int i = 1; i <= this.division - 1; i++) {
      indices[3 * i - 1] = (byte)(i + 1);
    }

    indices[this.division *3 - 1] = 1;

    // 下面(中心点)
    for (int i = 1; i <= this.division; i++) {
      indices[this.division * 3 + 3 * i - 3] = (byte)(this.division + 1);
    }

    for (int i = 1; i <= this.division; i++) {
      indices[this.division * 3 + 3 * i - 2] = (byte)(this.division + 1 + i);
    }

    for (int i = 1; i <= this.division - 1; i++) {
      indices[this.division * 3 + 3 * i - 1] = (byte)(this.division + 1 + i + 1);
    }

    indices[this.division * 6 - 1] = (byte)(this.division + 1 + 1);

    // 側面
    // 左上半分の三角形
    for (int i = 1; i <= this.division; i++) {
      indices[this.division * 6 + 3 * i - 3] = (byte)(this.division + 1 + i);
    }

    for (int i = 1; i <= this.division; i++) {
      indices[this.division * 6 + 3 * i - 2] = (byte)i;
    }

    for (int i = 1; i <= this.division - 1; i++) {
      indices[this.division * 6 + 3 * i - 1] = (byte)(i + 1);
    }

    indices[this.division * 9 - 1] = 1;

    // 右下半分の三角形
    for (int i = 1; i <= this.division; i++) {
      indices[this.division * 9 + 3 * i - 3] = (byte)(i);
    }

    for (int i = 1; i <= this.division; i++) {
      indices[this.division * 9 + 3 * i - 2] = (byte)(this.division + i + 1);
    }

    for (int i = 1; i <= this.division - 1; i++) {
      indices[this.division * 9 + 3 * i - 1] = (byte)(this.division + i + 2);
    }

    indices[this.division * 12 - 1] = (byte)(this.division + 2);

    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    // 頂点バッファの指定 
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);

    indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP, indices.length, GL.GL_UNSIGNED_BYTE, indexBuffer);
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 底面の半径
   * @param height 高さ
   */
  public void setSize(float radius, float height) {
    this.radius = radius;
    this.height = height;
  }

  /**
   * 分割数を設定します。
   * 
   * @param division 分割数
   */
  public void setDivision(int division) {
    this.division = division;
  }

}
