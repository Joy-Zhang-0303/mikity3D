/*
 * Created on 2015/08/02
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.renderer.jogl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.mklab.mikity.model.graphic.GraphicPrimitive;
import org.mklab.mikity.model.graphic.AbstractGraphicPrimitive;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLPointerFunc;


/**
 * JOGLのプリミティブを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/02
 */
public class JoglPrimitive implements JoglObject {
  /** グラフィックオブジェクト。 */
  private GraphicPrimitive object;
  
  /**
   * 新しく生成された<code>AbstractJoglObject</code>オブジェクトを初期化します。
   * @param object グラフィックオブジェクト
   */
  public JoglPrimitive(GraphicPrimitive object) {
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
    final ColorModel color = ((AbstractGraphicPrimitive)this.object).getColor();
    gl.glColor4f(color.getRf(), color.getGf(), color.getBf(), color.getAlphaf());
  }
  
  /**
   * 透明性を適用します。
   * 
   * @param gl GL
   */
  private void applyTransparency(GL2 gl) {
    if (((AbstractGraphicPrimitive)this.object).isTransparent()) {
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
}
