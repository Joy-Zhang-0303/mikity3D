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

  @XmlAttribute
  private String _color;

  private float[][] _point = new float[3][3];

  private FloatBuffer vertexBuffer;//頂点バッファ

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {

    //float x, y, z;

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

    /* 
    gl.glBegin(GL.GL_TRIANGLES);
    for (int i = 0; i < 3; i++) {
     x = this._point[i][0];
     y = this._point[i][1];
     z = this._point[i][2];
     gl.glVertex3f(x, y, z);
    }
    
    gl.glVertex3f(this._point[0][0],this._point[0][1],this._point[0][2]);
    gl.glVertex3f(this._point[1][0],this._point[1][1],this._point[1][2]);
    gl.glVertex3f(this._point[2][0],this._point[2][1],this._point[2][2]);
    
    gl.glEnd();
    */
    
    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
    //頂点バッファの生成
    float[] vertexs = {
        this._point[0][0], this._point[0][1], this._point[0][2],
        this._point[1][0], this._point[1][1], this._point[1][2],
        this._point[2][0], this._point[2][1], this._point[2][2],
        };

    this.vertexBuffer = makeFloatBuffer(vertexs);

    gl.glVertexPointer(3, GL.GL_FLOAT, 0, this.vertexBuffer);
    gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3); //プリミティブの描画
  
    gl.glPopMatrix();

  }

  //float配列をFloatBufferに変換
  private static FloatBuffer makeFloatBuffer(float[] array) {
    FloatBuffer fb = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    fb.put(array).position(0);
    return fb;

  }

  /**
   * @param point 座標
   */
  public void setSize(float[][] point) {

    this._point = point;

  }

  /**
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }

}
