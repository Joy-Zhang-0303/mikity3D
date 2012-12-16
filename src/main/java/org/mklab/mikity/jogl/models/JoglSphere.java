package org.mklab.mikity.jogl.models;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;

import org.mklab.mikity.jogl.JoglObject;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglSphere implements JoglObject {
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
      if (this._color.equals("white")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
      } else if (this._color.equals("lightGray")) { //$NON-NLS-1$
        gl.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
      } else if (this._color.equals("gray")) { //$NON-NLS-1$
        gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
      } else if (this._color.equals("darkGray")) { //$NON-NLS-1$
        gl.glColor4f(0.25f, 0.25f, 0.25f, 1.0f);
      } else if (this._color.equals("black")) { //$NON-NLS-1$
        gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
      } else if (this._color.equals("red")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
      } else if (this._color.equals("pink")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.69f, 0.69f, 1.0f);
      } else if (this._color.equals("orange")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.78f, 0.0f, 1.0f);
      } else if (this._color.equals("yellow")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
      } else if (this._color.equals("green")) { //$NON-NLS-1$
        gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
      } else if (this._color.equals("magenta")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
      } else if (this._color.equals("cyan")) { //$NON-NLS-1$
        gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
      } else if (this._color.equals("blue")) { //$NON-NLS-1$
        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
      }
    }

    //頂点バッファの指定 
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, this.vertexBuffer);

    this.indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_FAN, indices.length, GL.GL_UNSIGNED_BYTE, this.indexBuffer);

    gl.glPopMatrix();
    gl.glPopMatrix();
  }

  /**
   * float配列をFloatBufferに変換
   * @param array
   * @return
   */
  private static FloatBuffer makeFloatBuffer(float[] array) {
    final FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    buffer.put(array).position(0);
    return buffer;
  }

  /**
   * byte配列をByteBufferに変換
   * @param array
   * @return
   */
  private static ByteBuffer makeByteBuffer(byte[] array) {
    final ByteBuffer buffer = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
    buffer.put(array).position(0);
    return buffer;
  }

  /**
   * @param div 分割数
   * @param radius 半径
   */
  public void setSize(int div, float radius) {
    this._div = div;
    this._r = radius;
  }

  /**
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }
}
