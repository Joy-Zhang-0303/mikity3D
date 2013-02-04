/*
 * Created on 2012/12/17
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.renderer.jogl.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;

import org.mklab.mikity.view.renderer.jogl.JoglObject;


/**
 * {@link JoglObject}の抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2012/12/17
 */
public abstract class AbstractJoglObject implements JoglObject {
  /** 色 */
  private String color;

  /**
   * 色を設定します。
   * @param color 色
   */
  public void setColor(String color) {
    this.color = color;
  }

  /**
   * 色を適用します。
   * 
   * @param gl GL　
   */
  public void applyColor(GL gl) {
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
      gl.glColor4f(0.25f, 0.25f, 0.25f, 1.0f);
    } else if (this.color.equals("black")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
    } else if (this.color.equals("red")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
    } else if (this.color.equals("pink")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.69f, 0.69f, 1.0f);
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
      gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
    }
  }

  /**
   * float配列をFloatBufferに変換します。
   * 
   * @param array 変換元
   * @return 変換結果
   */
  FloatBuffer makeFloatBuffer(float[] array) {
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
  ByteBuffer makeByteBuffer(byte[] array) {
    final ByteBuffer buffer = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
    buffer.put(array).position(0);
    return buffer;
  }
}
