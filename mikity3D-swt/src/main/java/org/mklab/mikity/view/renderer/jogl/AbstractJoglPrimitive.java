/*
 * Created on 2012/12/17
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.renderer.jogl;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import org.mklab.mikity.model.graphic.GraphicPrimitive;
import org.mklab.mikity.util.Color3;
import org.mklab.mikity.util.ColorConstant;

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
    final String color = ((GraphicPrimitive)this.object).getColor();
    
    if (color == null) {
      return;
    }
    
    final Color3 value = ColorConstant.getColor(color);
    gl.glColor4f(value.getR(), value.getG(), value.getB(), value.getAlpha());
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

  /**
   * 色を設定します。
   * 
   * @param color 色
   */
  public void setColor(String color) {
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
