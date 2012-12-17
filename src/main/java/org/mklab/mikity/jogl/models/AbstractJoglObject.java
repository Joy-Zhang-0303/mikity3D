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
import javax.xml.bind.annotation.XmlAttribute;

import org.mklab.mikity.jogl.JoglObject;


/**
 * {@link JoglObject}の抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2012/12/17
 */
public abstract class AbstractJoglObject implements JoglObject {
  /** 色 */
  @XmlAttribute
  private String _color;

  /**
   * 色を設定します。
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }

  /**
   * 色を適用します。
   * 
   * @param gl GL　
   */
  public void applyColor(GL gl) {
    if (this._color == null) {
      return;
    }
    
    if (this._color.equals("white")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    } else if (this._color.equals("lightGray")) { //$NON-NLS-1$
      gl.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
    } else if (this._color.equals("gray")) { //$NON-NLS-1$
      gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
    } else if (this._color.equals("darkGray")) { //$NON-NLS-1$
      gl.glColor4f(0.25f, 0.25f, 0.25f, 1.0f);
    } else if (this._color.equals("black")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
    } else if (this._color.equals("red")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
    } else if (this._color.equals("pink")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.69f, 0.69f, 1.0f);
    } else if (this._color.equals("orange")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.78f, 0.0f, 1.0f);
    } else if (this._color.equals("yellow")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
    } else if (this._color.equals("green")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
    } else if (this._color.equals("magenta")) { //$NON-NLS-1$
      gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
    } else if (this._color.equals("cyan")) { //$NON-NLS-1$
      gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
    } else if (this._color.equals("blue")) { //$NON-NLS-1$
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
