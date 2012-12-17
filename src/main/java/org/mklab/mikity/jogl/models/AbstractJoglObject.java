/*
 * Created on 2012/12/17
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.jogl.models;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;

import org.mklab.mikity.jogl.JoglObject;


/**
 * {@link JoglObject}の抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2012/12/17
 */
public abstract class AbstractJoglObject implements JoglObject {

  /**
   * 色を適用します。
   * 
   * @param gl GL　
   * @param color 色
   */
  public void applyColor(GL gl, String color) {
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
}
