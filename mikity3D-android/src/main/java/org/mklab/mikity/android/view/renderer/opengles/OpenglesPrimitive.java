/*
 * Created on 2015/08/02
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.graphic.GraphicObject;
import org.mklab.mikity.model.graphic.GraphicPrimitive;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;

/**
 * OpenGL ESのオブジェクトを表す抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/02
 */
public class OpenglesPrimitive implements OpenglesObject {
  /** グラフィックオブジェクト。 */
  private GraphicObject object;
  
  /**
   * 新しく生成された<code>AbstractOpenglesObject</code>オブジェクトを初期化します。
   * @param object グラフィックオブジェクト
   */
  public OpenglesPrimitive(GraphicObject object) {
    this.object = object;
  }
  
  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl) {
    applyTransparency(gl);
    applyColor(gl);
    drawTrianglePolygons(gl);
  }

  /**
   * 色を適用します。
   * 
   * @param gl GL　
   */
  private void applyColor(GL10 gl) {
    final ColorModel color = ((GraphicPrimitive)this.object).getColor();
    gl.glColor4f(color.getRf(), color.getGf(), color.getBf(), color.getAlphaf());

  }
  
  /**
   * 透明性を適用します。
   * 
   * @param gl GL
   */
  private void applyTransparency(GL10 gl) {
    if (((GraphicPrimitive)this.object).isTransparent()) {
      gl.glEnable(GL10.GL_BLEND); // ブレンドを有効にします
      gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
    } else {
      gl.glEnable(GL10.GL_BLEND); // ブレンドを有効にします
      gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ZERO);
    }
  }
  
  /**
   * 三角形ポリゴンを描画します。
   * 
   * @param gl GL
   */
  private void drawTrianglePolygons(GL10 gl) {
    final float[] vertexArray = this.object.getVertexArray();
    final float[] normalVectorArray = this.object.getNormalVectorArray();
    
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertexArray);
    final FloatBuffer normalBuffer = makeFloatBuffer(normalVectorArray);
  
    // 法線配列の有効化
    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
    // 法線バッファの指定
    gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
    
    // 頂点配列の有効化
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    // 頂点バッファの指定
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
  
    final int number = vertexArray.length/3;
    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, number);
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
