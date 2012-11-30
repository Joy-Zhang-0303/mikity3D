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

  /**
   * Field _r
   */
  @XmlAttribute
  private float _r;

  /**
   * Field _div
   */
  @XmlAttribute
  private int _div;

  /** */
  protected String _color;

  private FloatBuffer vertexBuffer;//頂点バッファ
  private ByteBuffer indexBuffer;//インデックスバッファ
  


  
  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {
    //GLUT glut = new GLUT();
    
    //test
    this._div = 9;
    this._r = 2;
    
    int cnt;
    int incV = (int)(2*this._r/this._div);
    int incU = 360/this._div;
    
    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
 
    //デプステストの有効化
    gl.glEnable(GL.GL_DEPTH_TEST);
    

    //頂点バッファの生成
    float[] vertexs = new float[(2 + (this._div-1)*this._div)*3];
    cnt = 0;
    vertexs[cnt++] = 0.0f;
    vertexs[cnt++] = -this._r;
    vertexs[cnt++] = 0.0f;
    
    double d = this._r;
    double y, t, r;
    for(int iv =1; iv<this._div; ++iv){
      y = iv * incV - d;
      r = Math.sqrt(d * d - y * y);
      for(int iu = 0; iu < this._div; ++iu){
        t = iu * incU * Math.PI/180;
        vertexs[cnt++] = (float)(r*Math.cos(t));
        vertexs[cnt++] = (float)y;
        vertexs[cnt++] = (float)(r * Math.sin(t));
      }
    }
    
    vertexs[cnt++] = 0.0f;
    vertexs[cnt++] = this._r;
    vertexs[cnt++] = 0.0f;
    
    this.vertexBuffer = makeFloatBuffer(vertexs);

    
    //test
    for(int i = 0; i <= (2+(this._div-1)*this._div)*3-1; i++){
      System.out.println("vertexs["+i+"] = " + vertexs[i]);
    }

    
    
    //インデックスバッファの生成
    byte[] indexs = new byte[((this._div-1)*this._div*2)*3];
    cnt = 0;
    for(int iu = 0; iu < this._div; ++iu){
      indexs[cnt++] = 0;
      indexs[cnt++] = (byte)((iu + 1)% this._div + 1);
      indexs[cnt++] = (byte)(iu + 1);
    }
    
    for(int iv = 1; iv < this._div + 1 - 2; ++iv){
      for(int iu = 0; iu < this._div; ++iu){
        int m = (iv - 1) * this._div;
        
        //TriangleA
        indexs[cnt++] = (byte)(iu + 1 + m);
        indexs[cnt++] = (byte)((iu + 1)% this._div + 1 + m);
        indexs[cnt++] = (byte)(iu + 1 + this._div + m);
        
        //TriangleB
        indexs[cnt++] = (byte)((iu + 1)% this._div + 1 + this._div + m);
        indexs[cnt++] = (byte)(iu + 1 + this._div + m);
        indexs[cnt++] = (byte)((iu + 1)% this._div + 1 + m);
      }
    }
    
    int n = (2 + (this._div + 1 - 2)*this._div)-1;
    for(int iu = n - this._div; iu < n; ++ iu){
      indexs[cnt++] = (byte)iu;
      indexs[cnt++] = (byte)(iu % this._div + n - this._div);
      indexs[cnt++] = (byte)n;
    }
    
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

   //glut.glutSolidSphere(this._r, this._div, this._div);

    //頂点バッファの指定 
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, this.vertexBuffer);

    this.indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP,((this._div-1)*this._div*2)*3,GL.GL_UNSIGNED_BYTE,this.indexBuffer);
         
    gl.glPopMatrix();
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
   * @param div 分割数
   * @param r 半径
   */
  public void setSize(int div, float r) {

    this._div = div;
    this._r = r;

  }

  /**
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }
}
