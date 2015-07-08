package org.mklab.mikity.view.renderer.jogl.primitive;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLPointerFunc;

import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;

/**
 * 球をGLで表したクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglSphere extends AbstractJoglObject {
  /** 半径 */
  private float radius;

  /** 分割数 */
  private int division;

  /**
   * {@inheritDoc}
   */
  public void display(GL2 gl) {
    applyColor(gl);
    applyTransparency(gl);

    //頂点配列の有効化
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);

    // TODO this._div=16までしか対応していない。
    this.division = 16;
    final int grid = this.division;
    final int grid1 = grid + 1;
    final float incV = 2 * this.radius / grid;
    final float incU = 360.f / grid;
    
    // 頂点バッファの生成
    final float[] vertices = new float[(2 + (grid1 - 2) * grid) * 3];
    int count1 = 0;
    vertices[count1++] = 0.0f;
    vertices[count1++] = 0.0f;
    vertices[count1++] = -this.radius;

    for (int i = 1; i < grid1 - 1; ++i) {
      final float z = i * incV - this.radius;
      final float r = (float)Math.sqrt(this.radius * this.radius - z * z);
      for (int j = 0; j < grid; ++j) {
        final float theta = (float)(j * incU * Math.PI / 180);
        vertices[count1++] = (float)(r * Math.cos(theta));
        vertices[count1++] = (float)(r * Math.sin(theta));
        vertices[count1++] = z;
      }
    }

    vertices[count1++] = 0.0f;
    vertices[count1++] = 0.0f;
    vertices[count1++] = this.radius;

    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = new byte[((grid - 1) * grid * 2) * 3];
    int count2 = 0;
    for (int i = 0; i < grid; ++i) {
      indices[count2++] = 0;
      indices[count2++] = (byte)((i + 1) % grid + 1);
      indices[count2++] = (byte)(i + 1);
    }

    for (int i = 1; i < grid1 - 2; ++i) {
      for (int j = 0; j < grid; ++j) {
        int m = (i - 1) * grid;

        //TriangleA
        indices[count2++] = (byte)(j + 1 + m);
        indices[count2++] = (byte)((j + 1) % grid + 1 + m);
        indices[count2++] = (byte)(j + 1 + grid + m);

        //TriangleB
        indices[count2++] = (byte)((j + 1) % grid1 + grid + m);
        indices[count2++] = (byte)(j + 1 + grid + m);
        indices[count2++] = (byte)((j + 1) % grid + 1 + m);
      }
    }

    final int n = (2 + (grid1 - 2) * grid) - 1;
    for (int i = n - grid; i < n; ++i) {
      indices[count2++] = (byte)i;
      indices[count2++] = (byte)(i % grid + n - grid);
      indices[count2++] = (byte)n;
    }

    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    //頂点バッファの指定 
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);

    indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_FAN, indices.length, GL.GL_UNSIGNED_BYTE, indexBuffer);
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 半径
   */
  public void setSize(float radius) {
    this.radius = radius;
  }
  
  /**
   * 分割数を設定します。
   * @param division 分割数
   */
  public void setDivision(int division) {
    this.division = division;
  }

}
