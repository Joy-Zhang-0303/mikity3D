package org.mklab.mikity.swt.renderer;

import com.jogamp.opengl.GL2;


/**
 * JOGLのオブジェクトを表すインターフェースです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/13
 */
public interface JoglObject {
  
  /**
   * オブジェクトを表示します。
   * 
   * @param gl GL
   */
  void display(GL2 gl);
  
  /**
   * 座標軸を描画するか設定します。
   * 
   * @param isShowingAxis 座標軸を描画するならばtrue
   */
  void setShowingAxis(boolean isShowingAxis);
}
