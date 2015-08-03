
package org.mklab.mikity.android.view.renderer.opengles;

import javax.microedition.khronos.opengles.GL10;


/**
 * OpenGLESのオブジェクトを表すインターフェースです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public interface OpenglesObject {

  /**
   * オブジェクトを表示します。
   * @param gl10 GL10クラス
   */
  void display(GL10 gl10);
  
}
