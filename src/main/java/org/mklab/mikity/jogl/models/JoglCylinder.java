package org.mklab.mikity.jogl.models;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;

import org.mklab.mikity.jogl.JoglObject;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/31
 */
public class JoglCylinder implements JoglObject {

  /**
   * Field _r
   */
  @XmlAttribute
  protected float _r;

  /**
   * Field _height
   */
  @XmlAttribute
  protected float _height;

  /**
   * Field _div
   */
  @XmlAttribute
  protected int _div;

  @XmlAttribute
  private String _color;

  private FloatBuffer vertexBuffer;//頂点バッファ
  private ByteBuffer indexBuffer;//インデックスバッファ

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {

    int i;
    double ang;
    double PAI = 3.1415;

    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

    //デプステストの有効化
    gl.glEnable(GL.GL_DEPTH_TEST);

    float[] vertexs = new float[(this._div * 2 + 2) * 3];

    // TODO 描画がおかしいですが、これ以上考えても今は案が出てこないのでPushしました。vertexsかindexsの配列がおかしいのかもしれません。
    //頂点バッファの生成
    vertexs[0] = 0.0f;
    vertexs[1] = this._height / 2.0f;
    vertexs[2] = 0.0f;

    
    for (i = 1; i <= this._div; i++) {
      ang = 2.0 * PAI / this._div * i;
      vertexs[i * 3] = this._r * (float)Math.cos(ang);
      vertexs[i * 3 + 1] = this._height / 2.0f;
      vertexs[i * 3 + 2] = this._r * (float)Math.sin(ang);
    }

    
    vertexs[3 + this._div * 3] = 0.0f;
    vertexs[4 + this._div * 3] = -this._height / 2.0f;
    vertexs[5 + this._div * 3] = 0.0f;

    
    for (i = 1; i <= this._div; i++) {
      ang = 2.0 * PAI / this._div * i;
      vertexs[i * 3 + 3 + this._div * 3] = this._r * (float)Math.cos(ang);
      vertexs[i * 3 + 4 + this._div * 3] = -this._height / 2.0f;
      vertexs[i * 3 + 5 + this._div * 3] = this._r * (float)Math.sin(ang);
    }

    this.vertexBuffer = makeFloatBuffer(vertexs);

    //インデックスバッファの生成
    byte[] indexs = new byte[this._div * 12];
    
    for (i = 1; i <= this._div; i++) {
      indexs[3 * i - 3] = 0;
    }
    
    for (i = 1; i <= this._div; i++) {
      indexs[3 * i - 2] = (byte)i;
    }
    
    for (i = 1; i <= this._div-1; i++) {
      indexs[3 * i - 1] = (byte)(i + 1);
    }
    
    indexs[3 * this._div - 1] = 1;
    
    for (i = 1; i <= this._div; i++) {
      indexs[this._div * 3 + 3 * i - 3] = (byte)(1 + this._div);
    }
    
    for (i = 1; i <= this._div; i++) {
      indexs[this._div * 3 + 3 * i - 2] = (byte)(i + 1 + this._div);
    }
    
    for (i = 1; i <= this._div-1; i++) {
      indexs[this._div * 3 + 3 * i - 1] = (byte)(i + 2 + this._div);
    }
    
    indexs[this._div * 6 - 1] = (byte)(this._div + 2);

    //側面

    for (i = 1; i <= this._div; i++) {
      indexs[this._div * 6 + 3 * i - 3] = (byte)(this._div + 1 + i);
    }
    
    for (i = 1; i <= this._div; i++) {
      indexs[this._div * 6 + 3 * i - 2] = (byte)i;
    }
    
    for (i = 1; i <= this._div - 1; i++) {
      indexs[this._div * 6 + 3 * i - 1] = (byte)(i + 1);
    }

    indexs[this._div * 9 - 1] = 1;

    for (i = 1; i <= this._div; i++) {
      indexs[this._div * 9 + 3 * i - 3] = (byte)(i);
    }

    for (i = 1; i <= this._div; i++) {
      indexs[this._div * 9 + 3 * i - 2] = (byte)(i + 1 + this._div);
    }
    
    for (i = 1; i <= this._div - 1; i++) {
      indexs[this._div * 9 + 3 * i - 1] = (byte)(i + 2 + this._div);
    }

    indexs[this._div * 12 - 1] = (byte)(this._div + 1);

    this.indexBuffer = makeByteBuffer(indexs);

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
    gl.glBegin(GL.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f, 1.0f, 0.0f);
    gl.glVertex3f(0.0f, this._height / 2.0f, 0.0f);
    for (i = this._div; i >= 0; i--) {
      ang = 2.0 * PAI / this._div * i;
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      gl.glVertex3f(this._r * (float)Math.cos(ang), this._height / 2.0f, this._r * (float)Math.sin(ang));
    }
    gl.glEnd();
    //側面を作成
    gl.glBegin(GL.GL_QUAD_STRIP);
    for (i = this._div; i >= 0; i--) {
      ang = 2.0 * PAI / this._div * i;
      gl.glNormal3f((float)Math.cos(ang), 0.0f, (float)Math.sin(ang));
      gl.glVertex3f(this._r * (float)Math.cos(ang), this._height / 2.0f, this._r * (float)Math.sin(ang));
      gl.glVertex3f(this._r * (float)Math.cos(ang), -this._height / 2.0f, this._r * (float)Math.sin(ang));
    }
    gl.glEnd();

    gl.glBegin(GL.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f, -1.0f, 0.0f);
    gl.glVertex3f(0.0f, -this._height / 2.0f, 0.0f);
    for (i = 0; i <= this._div; i++) {
      ang = 2.0 * PAI / this._div * i;
      gl.glNormal3f(0.0f, -1.0f, 0.0f);
      gl.glVertex3f(this._r * (float)Math.cos(ang), -this._height / 2.0f, this._r * (float)Math.sin(ang));
    }
    gl.glEnd();
    */

    //頂点バッファの指定 
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, this.vertexBuffer);
    /*
    this.indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP, 3*this._div, GL.GL_UNSIGNED_BYTE, this.indexBuffer);
    
    this.indexBuffer.position(3*this._div);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP,3*this._div, GL.GL_UNSIGNED_BYTE, this.indexBuffer);
    
    this.indexBuffer.position(6*this._div);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP,6*this._div, GL.GL_UNSIGNED_BYTE, this.indexBuffer);
    */
    this.indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP,12*this._div,GL.GL_UNSIGNED_BYTE,this.indexBuffer);
    
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
    ByteBuffer bb = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
    bb.put(array).position(0);
    return bb;
  }

  /**
   * @param div 分割数
   * @param r 半径
   * @param hight 高さ
   */
  public void setSize(int div, float r, float hight) {
    this._div = div;
    this._r = r;
    this._height = hight;

  }

  /**
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }

}
