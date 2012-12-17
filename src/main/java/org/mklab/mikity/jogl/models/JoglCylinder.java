package org.mklab.mikity.jogl.models;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/31
 */
public class JoglCylinder extends AbstractJoglObject {
  /** 半径 */
  @XmlAttribute
  private float _r;

  /** 高さ */
  @XmlAttribute
  private float _height;

  /** 分割数 */
  @XmlAttribute
  private int _div;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    //頂点配列の有効化
    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

    //デプステストの有効化
    gl.glEnable(GL.GL_DEPTH_TEST);

    final float[] vertices = new float[(this._div * 2 + 2) * 3];

    // TODO 描画がおかしいですが、これ以上考えても今は案が出てこないのでPushしました。
    // TODO vertexsかindexsの配列がおかしいのかもしれません。
    //頂点バッファの生成
    vertices[0] = 0.0f;
    vertices[1] = this._height / 2.0f;
    vertices[2] = 0.0f;
    
    for (int i = 1; i <= this._div; i++) {
      final double theta = 2.0 * Math.PI / this._div * i;
      vertices[i * 3] = this._r * (float)Math.cos(theta);
      vertices[i * 3 + 1] = this._height / 2.0f;
      vertices[i * 3 + 2] = this._r * (float)Math.sin(theta);
    }
    
    vertices[3 + this._div * 3] = 0.0f;
    vertices[4 + this._div * 3] = -this._height / 2.0f;
    vertices[5 + this._div * 3] = 0.0f;
    
    for (int i = 1; i <= this._div; i++) {
      final double theta = 2.0 * Math.PI / this._div * i;
      vertices[i * 3 + 3 + this._div * 3] = this._r * (float)Math.cos(theta);
      vertices[i * 3 + 4 + this._div * 3] = -this._height / 2.0f;
      vertices[i * 3 + 5 + this._div * 3] = this._r * (float)Math.sin(theta);
    }

    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = new byte[this._div * 12];
    
    for (int i = 1; i <= this._div; i++) {
      indices[3 * i - 3] = 0;
    }
    
    for (int i = 1; i <= this._div; i++) {
      indices[3 * i - 2] = (byte)i;
    }
    
    for (int i = 1; i <= this._div-1; i++) {
      indices[3 * i - 1] = (byte)(i + 1);
    }
    
    indices[3 * this._div - 1] = 1;
    
    for (int i = 1; i <= this._div; i++) {
      indices[this._div * 3 + 3 * i - 3] = (byte)(1 + this._div);
    }
    
    for (int i = 1; i <= this._div; i++) {
      indices[this._div * 3 + 3 * i - 2] = (byte)(i + 1 + this._div);
    }
    
    for (int i = 1; i <= this._div-1; i++) {
      indices[this._div * 3 + 3 * i - 1] = (byte)(i + 2 + this._div);
    }
    
    indices[this._div * 6 - 1] = (byte)(this._div + 2);

    //側面

    for (int i = 1; i <= this._div; i++) {
      indices[this._div * 6 + 3 * i - 3] = (byte)(this._div + 1 + i);
    }
    
    for (int i = 1; i <= this._div; i++) {
      indices[this._div * 6 + 3 * i - 2] = (byte)i;
    }
    
    for (int i = 1; i <= this._div - 1; i++) {
      indices[this._div * 6 + 3 * i - 1] = (byte)(i + 1);
    }

    indices[this._div * 9 - 1] = 1;

    for (int i = 1; i <= this._div; i++) {
      indices[this._div * 9 + 3 * i - 3] = (byte)(i);
    }

    for (int i = 1; i <= this._div; i++) {
      indices[this._div * 9 + 3 * i - 2] = (byte)(i + 1 + this._div);
    }
    
    for (int i = 1; i <= this._div - 1; i++) {
      indices[this._div * 9 + 3 * i - 1] = (byte)(i + 2 + this._div);
    }

    indices[this._div * 12 - 1] = (byte)(this._div + 1);

    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    applyColor(gl);

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
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);

    indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP,indices.length,GL.GL_UNSIGNED_BYTE, indexBuffer);
    
    gl.glPopMatrix();
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 半径
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
}
