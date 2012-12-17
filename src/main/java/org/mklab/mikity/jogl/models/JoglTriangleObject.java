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
public class JoglTriangleObject implements JoglObject {

  /** 色 */
  @XmlAttribute
  private String _color;

  private float[][] _point = new float[3][3];

  /** 頂点バッファ */
  private FloatBuffer vertexBuffer;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    if (this._color != null) {
      if (this._color == "white") { //$NON-NLS-1$
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
      } else if (this._color == "lightGray") { //$NON-NLS-1$
        gl.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
      } else if (this._color == "gray") { //$NON-NLS-1$
        gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
      } else if (this._color == "darkGray") { //$NON-NLS-1$
        gl.glColor4f(0.25f, 0.25f, 0.25f, 1.0f);
      } else if (this._color == "black") { //$NON-NLS-1$
        gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
      } else if (this._color == "red") { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
      } else if (this._color == "pink") { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.69f, 0.69f, 1.0f);
      } else if (this._color == "orange") { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.78f, 0.0f, 1.0f);
      } else if (this._color == "yellow") { //$NON-NLS-1$
        gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
      } else if (this._color == "green") { //$NON-NLS-1$
        gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
      } else if (this._color == "magenta") { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
      } else if (this._color == "cyan") { //$NON-NLS-1$
        gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
      } else if (this._color == "blue") { //$NON-NLS-1$
        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
      }
    }

    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

    gl.glDisable(GL.GL_CULL_FACE);

    //頂点バッファの生成
    final float[] vertices = {this._point[0][0], this._point[0][1], this._point[0][2], this._point[1][0], this._point[1][1], this._point[1][2], this._point[2][0], this._point[2][1], this._point[2][2],};

    this.vertexBuffer = makeFloatBuffer(vertices);

    gl.glVertexPointer(3, GL.GL_FLOAT, 0, this.vertexBuffer);
    gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3); //プリミティブの描画

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
