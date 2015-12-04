/*
 * Created on 2015/08/02
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.renderer.jogl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.mklab.mikity.model.graphic.AbstractGraphicObject;
import org.mklab.mikity.model.graphic.GraphicObject;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLPointerFunc;


/**
 * JOGLの単体オブジェクトを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/02
 */
public class JoglSingleObject implements JoglObject {
  /** グラフィックオブジェクト。 */
  private GraphicObject object;
  /** 座標軸。 */
  private GraphicObject[] axises;

  /**
   * 新しく生成された<code>JoglSingleObject</code>オブジェクトを初期化します。
   * @param object グラフィックオブジェクト
   * @param axises 座標軸オブジェクト
   */
  public JoglSingleObject(GraphicObject object, GraphicObject[] axises) {
    this.object = object;
    this.axises = axises;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void display(GL2 gl) {
    applyTransparency(gl);    
    applyColor(gl,  ((AbstractGraphicObject)this.object).getColor());
    drawObject(gl);

    if (JoglObjectRenderer.isAxisDisplay == false) {
      return;
    }
    
    if (((AbstractGraphicObject)this.object).isTransparent()) {
      return;
    }

    drawAxies(gl);
  }

  /**
   * 色を適用します。
   * 
   * @param gl GL　
   */
  private void applyColor(GL2 gl, ColorModel color) {
    gl.glColor4f(color.getRf(), color.getGf(), color.getBf(), color.getAlphaf());
  }
  
  /**
   * 透明性を適用します。
   * 
   * @param gl GL
   */
  private void applyTransparency(GL2 gl) {
//    if (((AbstractGraphicPrimitive)this.object).isTransparent()) {
      gl.glEnable(GL.GL_BLEND);
      gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
//    } else {
//      gl.glEnable(GL.GL_BLEND);
//      gl.glBlendFunc(GL.GL_ONE, GL.GL_ZERO);
//    }
  }
  
  /**
   * オブジェクトの座標軸を描画します。
   * 
   * @param gl GL
   */
  private void drawAxies(GL2 gl) {
    applyColor(gl, new ColorModel("red")); //$NON-NLS-1$
    gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
    drawAxis(gl, this.axises[0]);
    gl.glRotatef(-90, 0.0f, 1.0f, 0.0f);

    applyColor(gl, new ColorModel("green")); //$NON-NLS-1$
    gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);
    drawAxis(gl, this.axises[1]);
    gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
    
    applyColor(gl, new ColorModel("blue")); //$NON-NLS-1$
    drawAxis(gl, this.axises[2]);
  }

  /**
   * 座標軸を描画します。
   * 
   * @param gl GL
   * @param axis 座標軸
   */
  private void drawAxis(GL2 gl, GraphicObject axis) {
    final float[] vertexArray = axis.getVertexArray();
    final float[] normalVectorArray = axis.getNormalVectorArray();
    
    drawTrianglePolygons(gl, vertexArray, normalVectorArray);   
  }
  
  /**
   * オブジェクトを描画します。
   * 
   * @param gl GL
   */
  private void drawObject(GL2 gl) {
    final float[] vertexArray = this.object.getVertexArray();
    final float[] normalVectorArray = this.object.getNormalVectorArray();
    
    drawTrianglePolygons(gl, vertexArray, normalVectorArray);
  }

  /**
   *  三角形ポリゴンを描画します。
   *  
   * @param gl GL
   * @param vertexArray 頂点の成分配列
   * @param normalVectorArray 法線ベクトルの成分配列
   */
  private void drawTrianglePolygons(GL2 gl, final float[] vertexArray, final float[] normalVectorArray) {
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
