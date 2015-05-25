package org.mklab.mikity.view.renderer.jogl.primitive;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLPointerFunc;

import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;


/**
 * シリンダーをGLで表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/31
 */
public class JoglCylinder extends AbstractJoglObject {

  /** 底面の半径 */
  private float radius;

  /** 高さ */
  private float height;

  /** 分割数 */
  private int div;

  /**
   * {@inheritDoc}
   */
  public void display(GL2 gl) {
    // 頂点配列の有効化
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);

    // デプステストの有効化
    gl.glEnable(GL.GL_DEPTH_TEST);

    final float[] vertices = new float[(this.div * 2 + 2) * 3];

    // TODO 描画がおかしいですが、これ以上考えても今は案が出てこないのでPushしました。
    // TODO vertexsかindexsの配列がおかしいのかもしれません。
    
    //頂点バッファの生成
    
    // 上面の中心点(0)
    vertices[0] = 0.0f;
    vertices[1] = this.height / 2.0f;
    vertices[2] = 0.0f;

    // 上面の周上の点(1 - div)
    for (int i = 1; i <= this.div; i++) {
      final double theta = 2.0 * Math.PI / this.div * i;
      vertices[i * 3 + 0] = (float)(this.radius * Math.cos(theta));
      vertices[i * 3 + 1] = this.height / 2.0f;
      vertices[i * 3 + 2] = (float)(this.radius * Math.sin(theta));
    }
    
    // 下面の中心点(div+1)
    vertices[this.div * 3 + 3] = 0.0f;
    vertices[this.div * 3 + 4] = -this.height / 2.0f;
    vertices[this.div * 3 + 5] = 0.0f;

    // 下面の周上の点([div+1+1] - [div+1+div+1])
    for (int i = 1; i <= this.div; i++) {
      final double theta = 2.0 * Math.PI / this.div * i;
      vertices[this.div * 3 + i * 3 + 3] = (float)(this.radius * Math.cos(theta));
      vertices[this.div * 3 + i * 3 + 4] = -this.height / 2.0f;
      vertices[this.div * 3 + i * 3 + 5] = (float)(this.radius * Math.sin(theta));
    }

    final FloatBuffer vertexBuffer = makeFloatBuffer(vertices);

    //インデックスバッファの生成
    final byte[] indices = new byte[this.div * 12];

    // 上面(中心点)
    for (int i = 1; i <= this.div; i++) {
      indices[3 * i - 3] = 0;
    }

    for (int i = 1; i <= this.div; i++) {
      indices[3 * i - 2] = (byte)i;
    }
 
    for (int i = 1; i <= this.div - 1; i++) {
      indices[3 * i - 1] = (byte)(i + 1);
    }

    indices[this.div *3 - 1] = 1;

    // 下面(中心点)
    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 3 + 3 * i - 3] = (byte)(this.div + 1);
    }

    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 3 + 3 * i - 2] = (byte)(this.div + 1 + i);
    }

    for (int i = 1; i <= this.div - 1; i++) {
      indices[this.div * 3 + 3 * i - 1] = (byte)(this.div + 1 + i + 1);
    }

    indices[this.div * 6 - 1] = (byte)(this.div + 1 + 1);

    // 側面
    // 左上半分の三角形
    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 6 + 3 * i - 3] = (byte)(this.div + 1 + i);
    }

    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 6 + 3 * i - 2] = (byte)i;
    }

    for (int i = 1; i <= this.div - 1; i++) {
      indices[this.div * 6 + 3 * i - 1] = (byte)(i + 1);
    }

    indices[this.div * 9 - 1] = 1;

    // 右下半分の三角形
    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 9 + 3 * i - 3] = (byte)(i);
    }

    for (int i = 1; i <= this.div; i++) {
      indices[this.div * 9 + 3 * i - 2] = (byte)(this.div + i + 1);
    }

    for (int i = 1; i <= this.div - 1; i++) {
      indices[this.div * 9 + 3 * i - 1] = (byte)(this.div + i + 2);
    }

    indices[this.div * 12 - 1] = (byte)(this.div + 2);

    final ByteBuffer indexBuffer = makeByteBuffer(indices);

    applyColor(gl);

    /*
    // 上面
    gl.glBegin(GL.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f, 1.0f, 0.0f);
    gl.glVertex3f(0.0f, this._height / 2.0f, 0.0f);
    for (i = this._div; i >= 0; i--) {
      ang = 2.0 * PAI / this._div * i;
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      gl.glVertex3f(this._r * (float)Math.cos(ang), this._height / 2.0f, this._r * (float)Math.sin(ang));
    }
    gl.glEnd();

    // 側面を作成
    gl.glBegin(GL.GL_QUAD_STRIP);
    for (i = this._div; i >= 0; i--) {
      ang = 2.0 * PAI / this._div * i;
      gl.glNormal3f((float)Math.cos(ang), 0.0f, (float)Math.sin(ang));
      gl.glVertex3f(this._r * (float)Math.cos(ang), this._height / 2.0f, this._r * (float)Math.sin(ang));
      gl.glVertex3f(this._r * (float)Math.cos(ang), -this._height / 2.0f, this._r * (float)Math.sin(ang));
    }
    gl.glEnd();

    // 底面
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

    // 頂点バッファの指定 
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);

    indexBuffer.position(0);
    gl.glDrawElements(GL.GL_TRIANGLE_STRIP, indices.length, GL.GL_UNSIGNED_BYTE, indexBuffer);
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 底面の半径
   * @param height 高さ
   */
  public void setSize(float radius, float height) {
    this.radius = radius;
    this.height = height;
  }

  /**
   * 分割数を設定します。
   * 
   * @param div 分割数
   */
  public void setDiv(int div) {
    this.div = div;
  }

}
