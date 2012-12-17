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
public class JoglSphere extends AbstractJoglObject {
  /** 半径 */
  @XmlAttribute
  private float _r;

  /** 分割数 */
  @XmlAttribute
  private int _div;

  /** 色 */
  protected String _color;

  /** 頂点バッファ */
  private FloatBuffer vertexBuffer;
  /** インデックスバッファ */
  private ByteBuffer indexBuffer;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

    //デプステストの有効化
    gl.glEnable(GL.GL_DEPTH_TEST);

    // TODO this._div=16までしか対応していない。
    this._div = 16;
    final float radius = this._r;
    final int grid = this._div;
    final int grid1 = grid + 1;
    final float incV = 2 * radius / grid;
    final int incU = 360 / grid;
    
    //頂点バッファの生成
    final float[] vertices = new float[(2 + (grid1 - 2) * grid) * 3];
    int count1 = 0;
    vertices[count1++] = 0.0f;
    vertices[count1++] = -radius;
    vertices[count1++] = 0.0f;

    for (int i = 1; i < grid1 - 1; ++i) {
      final float y = i * incV - radius;
      final float r = (float)Math.sqrt(radius * radius - y * y);
      for (int j = 0; j < grid; ++j) {
        final float theta = (float)(j * incU * Math.PI / 180);
        vertices[count1++] = (float)(r * Math.cos(theta));
        vertices[count1++] = y;
        vertices[count1++] = (float)(r * Math.sin(theta));
      }
    }

    vertices[count1++] = 0.0f;
    vertices[count1++] = radius;
    vertices[count1++] = 0.0f;

    this.vertexBuffer = makeFloatBuffer(vertices);

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

    this.indexBuffer = makeByteBuffer(indices);

    //test
    //for(int i = 0; i <= indexs.length-1; i++){
    // System.out.println("indexs["+ i +"] = " + indexs[i]);  //$NON-NLS-1$//$NON-NLS-2$
    //}

    if (this._color != null) {
      applyColor(gl, this._color);
    }

    //頂点バッファの指定 
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, this.vertexBuffer);

    this.indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_FAN, indices.length, GL.GL_UNSIGNED_BYTE, this.indexBuffer);

    gl.glPopMatrix();
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
   * 大きさを設定します。
   * 
   * @param radius 半径
   */
  public void setSize(float radius) {
    this._r = radius;
  }
  
  /**
   * 分割数を設定します。
   * @param div 分割数
   */
  public void setDiv(int div) {
    this._div = div;
  }

  /**
   * 色を設定します。
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }
}
