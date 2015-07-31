/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.graphic.GraphicObject;
import org.mklab.mikity.util.Color3;
import org.mklab.mikity.util.ColorConstant;

/**
 * OpenGL ESのオブジェクトを表す抽象クラスです。
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public abstract class  AbstractOpenglesObject implements OpenglesObject {
  /** グラフィックオブジェクト。 */
  protected GraphicObject object;
  
  /**
   * 新しく生成された<code>AbstractJoglObject</code>オブジェクトを初期化します。
   * @param object グラフィックオブジェクト
   */
  public AbstractOpenglesObject(GraphicObject object) {
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
    final String color = this.object.getColor();
    
    if (color == null) {
      return;
    }
    
    final Color3 value = ColorConstant.getColor(color);
    
    gl.glColor4f(value.getR(), value.getG(), value.getB(), value.getAlpha());
    
//    if (color.equals("white")) { //$NON-NLS-1$
//      gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
//    } else if (color.equals("lightGray")) { //$NON-NLS-1$
//      gl.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
//    } else if (color.equals("gray")) { //$NON-NLS-1$
//      gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
//    } else if (color.equals("darkGray")) { //$NON-NLS-1$
//      gl.glColor4f(0.25f, 0.25f, 0.25f, 0.5f);
//    } else if (color.equals("black")) { //$NON-NLS-1$
//      gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
//    } else if (color.equals("red")) { //$NON-NLS-1$
//      gl.glColor4f(1.0f, 0.0f, 0.0f, 0.0f);
//    } else if (color.equals("pink")) { //$NON-NLS-1$
//      gl.glColor4f(1.0f, 0.69f, 0.69f, 0.5f);
//    } else if (color.equals("orange")) { //$NON-NLS-1$
//      gl.glColor4f(1.0f, 0.78f, 0.0f, 1.0f);
//    } else if (color.equals("yellow")) { //$NON-NLS-1$
//      gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
//    } else if (color.equals("green")) { //$NON-NLS-1$
//      gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
//    } else if (color.equals("magenta")) { //$NON-NLS-1$
//      gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
//    } else if (color.equals("cyan")) { //$NON-NLS-1$
//      gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
//    } else if (color.equals("blue")) { //$NON-NLS-1$
//      gl.glColor4f(0.0f, 0.0f, 1.0f, 0.0f);
//    }
  }
  
  /**
   * 透明性を適用します。
   * 
   * @param gl GL
   */
  private void applyTransparency(GL10 gl) {
    if (this.object.isTransparent()) {
      gl.glEnable(GL10.GL_BLEND); // ブレンドを有効にします
      gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
    } else {
      gl.glEnable(GL10.GL_BLEND); // ブレンドを有効にします
      gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ZERO);
    }
    
//    if (this.object.isTransparent()) {
//      gl.glEnable(GL10.GL_BLEND);
//      gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
//    }
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

