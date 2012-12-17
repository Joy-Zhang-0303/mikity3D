package org.mklab.mikity.jogl.models;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglCone extends AbstractJoglObject {
  /** 半径 */
  @XmlAttribute
  protected float _r;

  /** 高さ */
  @XmlAttribute
  protected float _height;

  /** 分割数 */
  @XmlAttribute
  protected int _div;

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

    final float[] vertices = new float[(this._div + 2) * 3];

    // TODO 描画は出来てますが、Normal3fを使うとバグがでます。
    // TODO vertexs[]かindexs[]にどこか問題があると思います。

    //頂点バッファの生成
    vertices[0] = 0.0f;
    vertices[1] = this._height / 2.0f;
    vertices[2] = 0.0f;

    for (int i = 1; i <= this._div; i++) {
      final double theta = 2.0 * Math.PI / this._div * i;
      vertices[i * 3] = this._r * (float)Math.cos(theta);
      vertices[i * 3 + 1] = -this._height / 2.0f;
      vertices[i * 3 + 2] = this._r * (float)Math.sin(theta);
    }

    vertices[3 + this._div * 3] = 0.0f;
    vertices[4 + this._div * 3] = -this._height / 2.0f;
    vertices[5 + this._div * 3] = 0.0f;

    this.vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = new byte[this._div * 6];

    for (int i = 1; i <= this._div; i++) {
      indices[3 * i - 3] = 0;
    }

    for (int i = 1; i <= this._div; i++) {
      indices[3 * i - 2] = (byte)i;
    }

    for (int i = 1; i <= (this._div - 1); i++) {
      indices[3 * i - 1] = (byte)(i + 1);
    }

    indices[3 * this._div - 1] = 1;

    for (int i = 1; i <= this._div; i++) {
      indices[this._div * 3 + 3 * i - 3] = (byte)(this._div + 1);
    }

    for (int i = 1; i <= this._div; i++) {
      indices[this._div * 3 + 3 * i - 2] = (byte)(i);
    }

    for (int i = 1; i <= this._div - 1; i++) {
      indices[this._div * 3 + 3 * i - 1] = (byte)(i + 1);
    }

    indices[this._div * 6 - 1] = 1;

    this.indexBuffer = makeByteBuffer(indices);

    if (this._color != null) {
      applyColor(gl, this._color);
    }

    //頂点バッファの指定 
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, this.vertexBuffer);

    /*    
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
        this.indexBuffer.position(0);
        gl.glDrawElements(GL.GL_TRIANGLE_STRIP,this._div*3,GL.GL_UNSIGNED_BYTE,this.indexBuffer);
     
        gl.glNormal3f(0.0f, -1.0f, 0.0f);   
        this.indexBuffer.position(this._div*3);
        gl.glDrawElements(GL.GL_TRIANGLE_STRIP,this._div*3,GL.GL_UNSIGNED_BYTE,this.indexBuffer);
    */
    this.indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP, indices.length, GL.GL_UNSIGNED_BYTE, this.indexBuffer);

    gl.glPopMatrix();
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 底面の半径
   * @param hight 高さ
   */
  public void setSize(float radius, float hight) {
    this._r = radius;
    this._height = hight;
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
