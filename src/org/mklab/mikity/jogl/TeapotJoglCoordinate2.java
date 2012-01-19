package org.mklab.mikity.jogl;

import javax.media.opengl.GL;


/**
 * {@link TeapotJoglObject}の座標系を表すクラスです。
 * @author iwamoto
 * @version $Revision$, 2012/01/19
 */
public class TeapotJoglCoordinate2 implements JoglCoordinate {

  /**
   * @see org.mklab.mikity.jogl.JoglCoordinate#setCoordinate(javax.media.opengl.GL)
   */
  @Override
  public void setCoordinate(GL gl) {
    gl.glTranslatef(2.0f, 0.0f, 0.0f);
    gl.glRotatef(180f, 0.0f, 1.0f, 0.0f);
  }

}
