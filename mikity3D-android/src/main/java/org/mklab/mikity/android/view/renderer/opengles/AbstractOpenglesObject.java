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

/**
 * OpenGL ESのオブジェクトを表す抽象クラスです。
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public abstract class  AbstractOpenglesObject implements OpenglesObject {
  /** 色。 */
  private String color;
  
  /** 透明性 */
  private boolean transparent = false;
  
  /** 頂点配列の参照位置。 */
  protected int vertexPosition = 0;
  
  /** 法線ベクトル配列の参照位置。 */
  protected int normalVectorPosition = 0;
    
  /** 頂点配列。 */
  protected float vertexArray[];
  
  /** 法線ベクトル配列。 */
  protected float normalVectorArray[];

  /**
   * 色を設定します。
   * @param color 色
   */
  public void setColor(String color) {
    this.color = color;
  }
  
  /**
   * 色を返します。
   * 
   * @return 色
   */
  public String getColor() {
    return this.color;
  }
  
  /**
   * 透明性を設定します。
   * @param transparent 透明性
   */
  public void setTransparent(boolean transparent) {
    this.transparent = transparent;
  }

  /**
   * 色を適用します。
   * 
   * @param gl GL　
   */
  public void applyColor(GL10 gl) {
    if (this.color == null) {
      return;
    }
    
    if (this.color.equals("white")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    } else if (this.color.equals("lightGray")) { //$NON-NLS-1$
      gl.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
    } else if (this.color.equals("gray")) { //$NON-NLS-1$
      gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
    } else if (this.color.equals("darkGray")) { //$NON-NLS-1$
      gl.glColor4f(0.25f, 0.25f, 0.25f, 0.5f);
    } else if (this.color.equals("black")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
    } else if (this.color.equals("red")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.0f, 0.0f, 0.0f);
    } else if (this.color.equals("pink")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.69f, 0.69f, 0.5f);
    } else if (this.color.equals("orange")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.78f, 0.0f, 1.0f);
    } else if (this.color.equals("yellow")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
    } else if (this.color.equals("green")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
    } else if (this.color.equals("magenta")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
    } else if (this.color.equals("cyan")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
    } else if (this.color.equals("blue")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 0.0f, 1.0f, 0.0f);
    }
  }
  
  /**
   * 透明性を適用します。
   * @param gl GL
   */
  public void applyTransparency(GL10 gl) {
    if (this.transparent) {
      gl.glEnable(GL10.GL_BLEND);
      gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
    }
  }

  /**
   * float配列をFloatBufferに変換します。
   * 
   * @param array 変換元
   * @return 変換結果
   */
  public FloatBuffer makeFloatBuffer(float[] array) {
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
  public ByteBuffer makeByteBuffer(byte[] array) {
    final ByteBuffer buffer = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
    buffer.put(array).position(0);
    return buffer;
  }
  
  /**
   * 頂点を頂点配列に追加します。
   * @param vertices 頂点
   */
  protected void appendVertices(float[][] vertices) {
    for (int i = 0; i < vertices.length; i++) {
      for (int j = 0; j < 3; j++) {
        this.vertexArray[this.vertexPosition++] = vertices[i][j];
      }
    }
  }

  /**
   * 三角形ポリゴンの法線ベクトルを法線ベクトル配列に3個追加します。
   * @param normalVector 法線ベクトル
   */
  protected void appendNormalVectorsOfTriangle(float[] normalVector) {
    for (int i = 0; i < 3; i++) {
      this.normalVectorArray[this.normalVectorPosition++] = normalVector[0];
      this.normalVectorArray[this.normalVectorPosition++] = normalVector[1];
      this.normalVectorArray[this.normalVectorPosition++] = normalVector[2];
    }
  }

  /**
   * 三角形ポリゴンを描画します。
   * @param gl GL
   */
  protected void drawTrianglePolygons(GL10 gl) {
    final FloatBuffer vertexBuffer = makeFloatBuffer(this.vertexArray);
    final FloatBuffer normalBuffer = makeFloatBuffer(this.normalVectorArray);
  
    // 法線配列の有効化
    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
    // 法線バッファの指定
    gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
    
    // 頂点配列の有効化
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    // 頂点バッファの指定
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
  
    final int number = this.vertexArray.length/3;
    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, number);
  }
}

