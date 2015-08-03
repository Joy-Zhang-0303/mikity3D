
package org.mklab.mikity.android.view.renderer.opengles;

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
  
}
