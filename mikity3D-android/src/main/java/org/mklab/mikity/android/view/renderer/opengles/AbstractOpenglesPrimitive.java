/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.graphic.GraphicPrimitive;
import org.mklab.mikity.util.Color4;
import org.mklab.mikity.util.ColorConstant;

/**
 * OpenGL ESのプリミティブを表す抽象クラスです。
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public abstract class  AbstractOpenglesPrimitive extends AbstractOpenglesObject {
  /**
   * 新しく生成された<code>AbstractJoglObject</code>オブジェクトを初期化します。
   * @param object グラフィックオブジェクト
   */
  public AbstractOpenglesPrimitive(GraphicPrimitive object) {
    super(object);
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
    final String color = ((GraphicPrimitive)this.object).getColor();
    
    if (color == null) {
      return;
    }
    
    final Color4 value = ColorConstant.getColor(color);
    gl.glColor4f(value.getRf(), value.getGf(), value.getBf(), value.getAlphaf());
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

