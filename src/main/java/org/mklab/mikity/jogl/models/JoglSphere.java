package org.mklab.mikity.jogl.models;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;

/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglSphere extends AbstractJoglObject {
  /** 半径 */
  private float radius;

  /** 分割数 */
  private int div;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

    //デプステストの有効化
    gl.glEnable(GL.GL_DEPTH_TEST);

    // TODO this._div=16までしか対応していない。
    this.div = 16;
    final int grid = this.div;
    final int grid1 = grid + 1;
    final float incV = 2 * this.radius / grid;
    final int incU = 360 / grid;
    
    //頂点バッファの生成
    final float[] vertices = new float[(2 + (grid1 - 2) * grid) * 3];
    int count1 = 0;
    vertices[count1++] = 0.0f;
    vertices[count1++] = -this.radius;
    vertices[count1++] = 0.0f;

    for (int i = 1; i < grid1 - 1; ++i) {
      final float y = i * incV - this.radius;
      final float r = (float)Math.sqrt(this.radius * this.radius - y * y);
      for (int j = 0; j < grid; ++j) {
        final float theta = (float)(j * incU * Math.PI / 180);
        vertices[count1++] = (float)(r * Math.cos(theta));
        vertices[count1++] = y;
        vertices[count1++] = (float)(r * Math.sin(theta));
      }
    }

    vertices[count1++] = 0.0f;
    vertices[count1++] = this.radius;
    vertices[count1++] = 0.0f;

    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //test
    // for(int i = 0; i <= vertexs.length-1; i++){
    //  System.out.println("vertexs["+i+"] = " + vertexs[i]); //$NON-NLS-1$ //$NON-NLS-2$
    //}

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

    //test
    //for(int i = 0; i <= indexs.length-1; i++){
    // System.out.println("indexs["+ i +"] = " + indexs[i]);  //$NON-NLS-1$//$NON-NLS-2$
    //}

    applyColor(gl);

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
   * @param div 分割数
   */
  public void setDiv(int div) {
    this.div = div;
  }
}
