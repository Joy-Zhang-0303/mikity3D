package org.mklab.mikity.jogl;

import javax.media.opengl.GL;


/**
 * 座標系を表すインターフェースです。
 * @author iwamoto
 * @version $Revision$, 2012/01/19
 */
public interface JoglCoordinate {
  /**
   * 座標系を設定します。
   * @param gl GL
   */
  void Transform(GL gl);
  
  /**
   * @param object オブジェクト
   */
  void addChild(JoglObject object);
}
