/*
 * Created on 2012/12/17
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.renderer.jogl;

import org.mklab.mikity.model.graphic.GraphicPrimitive;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

/**
 * JOGLのプリミティブを表すの抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2012/12/17
 */
public abstract class AbstractJoglPrimitive extends AbstractJoglObject {
  /**
   * 新しく生成された<code>AbstractJoglObject</code>オブジェクトを初期化します。
   * @param object グラフィックオブジェクト
   */
  public AbstractJoglPrimitive(GraphicPrimitive object) {
    super(object);
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
//    final String color = ((GraphicPrimitive)this.object).getColorName();
//    
//    if (color == null) {
//      return;
//    }
//    
//    final Color4 value = ColorConstant.getColor(color);
//    gl.glColor4f(value.getRf(), value.getGf(), value.getBf(), value.getAlphaf());
    
    final ColorModel color = ((GraphicPrimitive)this.object).getColor();
    gl.glColor4f(color.getRf(), color.getGf(), color.getBf(), color.getAlphaf());
  }
  
  /**
   * 透明性を適用します。
   * 
   * @param gl GL
   */
  private void applyTransparency(GL2 gl) {
    if (((GraphicPrimitive)this.object).isTransparent()) {
      gl.glEnable(GL.GL_BLEND); // ブレンドを有効にします
      gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    } else {
      gl.glEnable(GL.GL_BLEND); // ブレンドを有効にします
      gl.glBlendFunc(GL.GL_ONE, GL.GL_ZERO);
    }
  }

//  /**
//   * 色を設定します。
//   * 
//   * @param colorName 色
//   */
//  public void setColorName(String colorName) {
//    ((GraphicPrimitive)this.object).setColorName(colorName);
//  }
  
  /**
   * 色を設定します。
   * 
   * @param color 色
   */
  public void setColor(ColorModel color) {
    ((GraphicPrimitive)this.object).setColor(color);
  }

  
  /**
   * 透明性を設定します。
   * 
   * @param transparent 透明性
   */
  public void setTransparent(boolean transparent) {
    ((GraphicPrimitive)this.object).setTransparent(transparent);
  }

}
