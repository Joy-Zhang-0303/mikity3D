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
import org.mklab.mikity.model.graphic.GraphicObjectFactory;
import org.mklab.mikity.model.xml.simplexml.model.AxisModel;
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
  private GraphicObject[] axies;

  /**
   * 新しく生成された<code>JoglSingleObject</code>オブジェクトを初期化します。
   * @param object グラフィックオブジェクト
   */
  public JoglSingleObject(GraphicObject object) {
    this.object = object;
    
    this.axies = new GraphicObject[3];
    for (int i = 0; i < this.axies.length; i++) {
      this.axies[i] = GraphicObjectFactory.create(AxisModel.createDefault());
    }    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void display(GL2 gl) {
    applyTransparency(gl);
    drawAxies(gl);
    applyColor(gl);
    drawTrianglePolygons(gl);
  }

  /**
   * 色を適用します。
   * 
   * @param gl GL　
   */
  private void applyColor(GL2 gl) {
    final ColorModel color = ((AbstractGraphicObject)this.object).getColor();
    
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
   * 選択オブジェクトに座標を描画します。
   * 
   * @param gl GL
   */
  private void drawAxies(GL2 gl) {
    if (JoglObjectRenderer.isAxisDisplay == false) {
      return;
    }
    
    if (((AbstractGraphicObject)this.object).isTransparent()) {
      return;
    }
    
     //$NON-NLS-1$
    gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
    drawAxis(gl, this.axies[0], new ColorModel("red")); //$NON-NLS-1$
    gl.glRotatef(-90, 0.0f, 1.0f, 0.0f);

    gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);
    drawAxis(gl, this.axies[1], new ColorModel("green")); //$NON-NLS-1$;
    gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
    
    drawAxis(gl, this.axies[2], new ColorModel("blue")); //$NON-NLS-1$;
  }

  private void drawAxis(GL2 gl, GraphicObject axis, ColorModel color) {
    final float[] vertexArray = axis.getVertexArray();
    final float[] normalVectorArray = axis.getNormalVectorArray();
    
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

    gl.glColor4f(color.getRf(), color.getGf(), color.getBf(), color.getAlphaf());

    final int number = vertexArray.length/3;
    gl.glDrawArrays(GL.GL_TRIANGLES, 0, number);
    
  }

//  private void drawAxisY(GL2 gl, GraphicObject axis, ColorModel color) {
//    final float[] vertexArray = axis.getVertexArray();
//    final float[] normalVectorArray = axis.getNormalVectorArray();
//    
//    final FloatBuffer vertexBuffer = makeFloatBuffer(vertexArray);
//    final FloatBuffer normalBuffer = makeFloatBuffer(normalVectorArray);
//
//    // 法線配列の有効化
//    gl.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
//    // 法線バッファの指定
//    gl.glNormalPointer(GL.GL_FLOAT, 0, normalBuffer);
//    
//    // 頂点配列の有効化
//    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
//    // 頂点バッファの指定
//    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);
//    
//    gl.glColor4f(color.getRf(), color.getGf(), color.getBf(), color.getAlphaf());
//
//    final int number = vertexArray.length/3;
//    gl.glDrawArrays(GL.GL_TRIANGLES, 0, number);
//  }
//
//  private void drawAxisZ(GL2 gl, GraphicObject axis, ColorModel color) {
//    final float[] vertexArray = axis.getVertexArray();
//    final float[] normalVectorArray = axis.getNormalVectorArray();
//    
//    final FloatBuffer vertexBuffer = makeFloatBuffer(vertexArray);
//    final FloatBuffer normalBuffer = makeFloatBuffer(normalVectorArray);
//
//    // 法線配列の有効化
//    gl.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
//    // 法線バッファの指定
//    gl.glNormalPointer(GL.GL_FLOAT, 0, normalBuffer);
//    
//    // 頂点配列の有効化
//    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
//    // 頂点バッファの指定
//    gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);
//
//    gl.glColor4f(color.getRf(), color.getGf(), color.getBf(), color.getAlphaf());
//
//    final int number = vertexArray.length/3;
//    gl.glDrawArrays(GL.GL_TRIANGLES, 0, number);
//  }
  
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
