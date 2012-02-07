package org.mklab.mikity.jogl;

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
  
  /**
   * @param coordinate 座標系
   */
  void addChild(JoglCoordinate coordinate);

}
