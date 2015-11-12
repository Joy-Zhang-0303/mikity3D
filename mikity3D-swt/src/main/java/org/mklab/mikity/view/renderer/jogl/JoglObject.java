package org.mklab.mikity.view.renderer.jogl;

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

}
