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

  //--------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _xsize
   */
  @XmlAttribute
  private float _xsize;

  /**
   * Field _ysize
   */
  @XmlAttribute
  private float _ysize;

  /**
   * Field _zsize
   */
  @XmlAttribute
  private float _zsize;

  @XmlAttribute
  private String _color;

  private FloatBuffer vertexBuffer;//頂点バッファ
  private ByteBuffer indexBuffer;//インデックスバッファ
  
  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {

    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
    
    //デプステストの有効化
    gl.glEnable(GL.GL_DEPTH_TEST);
    
    /*
    float[][] vertice = new float[][] { {this._xsize / 2, this._ysize / 2, this._zsize / 2}, {-this._xsize / 2, this._ysize / 2, this._zsize / 2},
        {-this._xsize / 2, -this._ysize / 2, this._zsize / 2}, {this._xsize / 2, -this._ysize / 2, this._zsize / 2}, {this._xsize / 2, this._ysize / 2, -this._zsize / 2},
        {-this._xsize / 2, this._ysize / 2, -this._zsize / 2}, {-this._xsize / 2, -this._ysize / 2, -this._zsize / 2}, {this._xsize / 2, -this._ysize / 2, -this._zsize / 2}};
   */
    
    float[] vertexs = {
        this._xsize / 2, this._ysize / 2, this._zsize / 2,
        -this._xsize / 2, this._ysize / 2, this._zsize / 2,
        -this._xsize / 2, -this._ysize / 2, this._zsize / 2,
        this._xsize / 2, -this._ysize / 2, this._zsize / 2,
        this._xsize / 2, this._ysize / 2, -this._zsize / 2,
        -this._xsize / 2, this._ysize / 2, -this._zsize / 2,
        -this._xsize / 2, -this._ysize / 2, -this._zsize / 2,
        this._xsize / 2, -this._ysize / 2, -this._zsize / 2,
    };
    this.vertexBuffer = makeFloatBuffer(vertexs);
    
    //インデックスバッファの生成
    byte[] indexs = {
        0,4,1,5,2,6,3,7,0,4,
        4,7,5,6,
        0,1,3,2
    };
    this.indexBuffer = makeByteBuffer(indexs);

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

    /*
    gl.glBegin(GL.GL_QUADS); // 四角形を描画することを宣言します 
    gl.glVertex3fv(vertice[0], 0);
    gl.glVertex3fv(vertice[1], 0);
    gl.glVertex3fv(vertice[2], 0);
    gl.glVertex3fv(vertice[3], 0); // 4つ目の頂点を指定するとポリゴンが描画されます

    gl.glVertex3fv(vertice[7], 0);
    gl.glVertex3fv(vertice[6], 0);
    gl.glVertex3fv(vertice[5], 0);
    gl.glVertex3fv(vertice[4], 0);

    gl.glVertex3fv(vertice[0], 0);
    gl.glVertex3fv(vertice[3], 0);
    gl.glVertex3fv(vertice[7], 0);
    gl.glVertex3fv(vertice[4], 0);

    gl.glVertex3fv(vertice[5], 0);
    gl.glVertex3fv(vertice[6], 0);
    gl.glVertex3fv(vertice[2], 0);
    gl.glVertex3fv(vertice[1], 0); // 4つ目の頂点を指定するとポリゴンが描画されます 

    gl.glVertex3fv(vertice[4], 0);
    gl.glVertex3fv(vertice[5], 0);
    gl.glVertex3fv(vertice[1], 0);
    gl.glVertex3fv(vertice[0], 0);

    gl.glVertex3fv(vertice[6], 0);
    gl.glVertex3fv(vertice[7], 0);
    gl.glVertex3fv(vertice[3], 0);
    gl.glVertex3fv(vertice[2], 0);

    gl.glEnd(); // 描画処理が終了しました
    */
    
    this.indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP,10,GL.GL_UNSIGNED_BYTE,this.indexBuffer);
    
    this.indexBuffer.position(10);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP,4,GL.GL_UNSIGNED_BYTE,this.indexBuffer);
    
    this.indexBuffer.position(14);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP,4,GL.GL_UNSIGNED_BYTE,this.indexBuffer);
    
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
   * 大きさの設定
   * 
   * @param x xの長さ
   * @param y yの長さ
   * @param z zの長さ
   */
  public void setSize(float x, float y, float z) {
    this._xsize = x;
    this._ysize = y;
    this._zsize = z;
  }

  /**
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }

}
