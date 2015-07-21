/*
 * Created on 2012/12/17
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.renderer.jogl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLPointerFunc;

import org.mklab.mikity.model.graphic.GraphicObject;

/**
 * JOGLのオブジェクトを表すの抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2012/12/17
 */
public abstract class AbstractJoglObject implements JoglObject {
  /** グラフィックオブジェクト。 */
  protected GraphicObject object;
  
  /**
   * 新しく生成された<code>AbstractJoglObject</code>オブジェクトを初期化します。
   * @param object グラフィックオブジェクト
   */
  public AbstractJoglObject(GraphicObject object) {
    this.object = object;
  }
  
  /**
   * {@inheritDoc}
   */
  public void display(GL2 gl) {
    applyTransparency(gl);
    applyColor(gl);
    drawTrianglePolygons(gl);
  }

  /**
   * 色を適用します。
   * 
   * @param gl GL　
   */
  private void applyColor(GL2 gl) {
    final String color = this.object.getColor();
    
    if (color == null) {
      return;
    }
    
    if (color.equals("white")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    } else if (color.equals("lightGray")) { //$NON-NLS-1$
      gl.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
    } else if (color.equals("gray")) { //$NON-NLS-1$
      gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
    } else if (color.equals("darkGray")) { //$NON-NLS-1$
      gl.glColor4f(0.25f, 0.25f, 0.25f, 1.0f);
    } else if (color.equals("black")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
    } else if (color.equals("red")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
    } else if (color.equals("pink")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.69f, 0.69f, 1.0f);
    } else if (color.equals("orange")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.78f, 0.0f, 1.0f);
    } else if (color.equals("yellow")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
    } else if (color.equals("green")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
    } else if (color.equals("magenta")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
    } else if (color.equals("cyan")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
    } else if (color.equals("blue")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
    }
  }
  
  /**
   * 透明性を適用します。
   * 
   * @param gl GL
   */
  private void applyTransparency(GL2 gl) {
    if (this.object.isTransparent()) {
      gl.glEnable(GL.GL_BLEND); // ブレンドを有効にします
      gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    } else {
      gl.glEnable(GL.GL_BLEND); // ブレンドを有効にします
      gl.glBlendFunc(GL.GL_ONE, GL.GL_ZERO);
    }
  }
  
  /**
   * 三角形ポリゴンを描画します。
   * 
   * @param gl GL
   */
  private void drawTrianglePolygons(GL2 gl) {
    final float[] vertexArray = this.object.getVertexArray();
    final float[] normalVectorArray = this.object.getNormalVectorArray();
    
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertexArray);
    final FloatBuffer normalBuffer = makeFloatBuffer(normalVectorArray);

    // 法線配列の有効化
    gl.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
    // 法線バッファの指定
    gl.glNormalPointer(GL.GL_FLOAT, 0, normalBuffer);
    
    // 頂点配列の有効化
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
    // 頂点バッファの指定
    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);

    final int number = vertexArray.length/3;
    gl.glDrawArrays(GL.GL_TRIANGLES, 0, number);
  }
  
  /**
   * float配列をFloatBufferに変換します。
   * 
   * @param array 変換元
   * @return 変換結果
   */
  private FloatBuffer makeFloatBuffer(float[] array) {
    final FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    buffer.put(array).position(0);
    return buffer;
  }

  /**
   * byte配列をByteBufferに変換します。
   * 
   * @param array 変換元
   * @return 変換結果
   */
  @SuppressWarnings("unused")
  private ByteBuffer makeByteBuffer(byte[] array) {
    final ByteBuffer buffer = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
    buffer.put(array).position(0);
    return buffer;
  }

  /**
   * 色を設定します。
   * 
   * @param color 色
   */
  public void setColor(String color) {
    this.object.setColor(color);
  }
  
  /**
   * 透明性を設定します。
   * 
   * @param transparent 透明性
   */
  public void setTransparent(boolean transparent) {
    this.object.setTransparent(transparent);
  }

}
