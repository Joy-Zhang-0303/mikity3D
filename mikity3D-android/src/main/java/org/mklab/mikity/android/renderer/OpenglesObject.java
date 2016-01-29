
package org.mklab.mikity.android.renderer;

import javax.microedition.khronos.opengles.GL10;


/**
 * OpenGL ESのオブジェクトを表すインターフェースです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public interface OpenglesObject {
  /**
   * オブジェクトを表示します。
   * @param gl GL
   */
  void display(GL10 gl);
  
  /**
   * 座標軸を描画するか設定します。
   * 
   * @param isShowingAxis 座標軸を描画するならばtrue
   */
  void setShowingAxis(boolean isShowingAxis);
}
