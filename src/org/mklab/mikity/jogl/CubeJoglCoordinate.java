package org.mklab.mikity.jogl;

import javax.media.opengl.GL;


/**
 * {@link CubeJoglObject}の座標系を表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/19
 */
public class CubeJoglCoordinate implements JoglCoordinate {

  /**
   * @see org.mklab.mikity.jogl.JoglCoordinate#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {
    gl.glTranslatef(2.0f, 0.0f, 0.0f);
  }


}
