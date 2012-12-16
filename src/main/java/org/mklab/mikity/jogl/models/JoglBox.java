package org.mklab.mikity.jogl.models;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;

import org.mklab.mikity.jogl.JoglObject;


/**
 * XMLBOXをJOGLで表したクラスです
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/26
 */
public class JoglBox implements JoglObject {
  /** _xsize */
  @XmlAttribute
  private float _xsize;

  /** _ysize */
  @XmlAttribute
  private float _ysize;

  /** _zsize */
  @XmlAttribute
  private float _zsize;

  /** 色 */
  @XmlAttribute
  private String _color;

  /** 頂点バッファ　 */
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

    final float[] vertices = {this._xsize / 2, this._ysize / 2, this._zsize / 2, -this._xsize / 2, this._ysize / 2, this._zsize / 2, -this._xsize / 2, -this._ysize / 2, this._zsize / 2,
        this._xsize / 2, -this._ysize / 2, this._zsize / 2, this._xsize / 2, this._ysize / 2, -this._zsize / 2, -this._xsize / 2, this._ysize / 2, -this._zsize / 2, -this._xsize / 2,
        -this._ysize / 2, -this._zsize / 2, this._xsize / 2, -this._ysize / 2, -this._zsize / 2,};
    this.vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = {0, 4, 1, 5, 2, 6, 3, 7, 0, 4, 4, 7, 5, 6, 0, 1, 3, 2};
    this.indexBuffer = makeByteBuffer(indices);

    //頂点バッファの指定
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, this.vertexBuffer);

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

    this.indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP, 10, GL.GL_UNSIGNED_BYTE, this.indexBuffer);

    this.indexBuffer.position(10);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP, 4, GL.GL_UNSIGNED_BYTE, this.indexBuffer);

    this.indexBuffer.position(14);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP, 4, GL.GL_UNSIGNED_BYTE, this.indexBuffer);

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

  /**
   * 色を設定します。
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }

}
