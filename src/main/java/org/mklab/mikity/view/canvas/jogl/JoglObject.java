package org.mklab.mikity.view.canvas.jogl;

import javax.media.opengl.GL;


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
  void display(GL gl);

}
