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
public class JoglQuadObject implements JoglObject {

  private float[][] _point = new float[4][3];
  
  @XmlAttribute
  private String _color;

  private FloatBuffer vertexBuffer;//頂点バッファ
  private ByteBuffer indexBuffer;//インデックスバッファ
  
  
  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {
    //float x, y, z;
    
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

    /*
    gl.glBegin(GL.GL_QUADS);
    for (int i = 0; i < 4; i++) {
      x = this._point[i][0];
      y = this._point[i][1];
      z = this._point[i][2];
      gl.glVertex3f(x, y, z);
    }
    gl.glEnd();
    */
    
    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
    
    gl.glDisable(GL.GL_CULL_FACE);

   //頂点バッファの生成
    float[] vertexs = {
        this._point[0][0], this._point[0][1], this._point[0][2],
        this._point[1][0], this._point[1][1], this._point[1][2],
        this._point[2][0], this._point[2][1], this._point[2][2],
        this._point[3][0], this._point[3][1], this._point[3][2],
    };
    this.vertexBuffer = makeFloatBuffer(vertexs);
    
    //インデックスバッファの生成
    byte[] indexs = {0,1,2,0,2,3};
    this.indexBuffer = makeByteBuffer(indexs);

    //頂点バッファの指定
    gl.glVertexPointer(3,GL.GL_FLOAT,0,this.vertexBuffer);
    
    //プリミティブの描画
    gl.glDrawElements(GL.GL_TRIANGLES, 6, GL.GL_UNSIGNED_BYTE, this.indexBuffer);
    
    gl.glPopMatrix();
  }

  //float配列をFloatBufferに変換
  private static FloatBuffer makeFloatBuffer(float[] array) {
    FloatBuffer fb = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    fb.put(array).position(0);
    return fb;

  }
  //byte配列をByteBufferに変換
  private static ByteBuffer makeByteBuffer(byte[] array) {
    ByteBuffer bb = ByteBuffer.allocateDirect(array.length).order(
        ByteOrder.nativeOrder());
    bb.put(array).position(0);
    return bb;
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
