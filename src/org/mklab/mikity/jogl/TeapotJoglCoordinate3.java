package org.mklab.mikity.jogl;

import javax.media.opengl.GL;


/**
 * {@link TeapotJoglObject}の座標系を表すクラスです。
 * @author iwamoto
 * @version $Revision$, 2012/01/19
 */
public class TeapotJoglCoordinate3 implements JoglCoordinate {

  /**
   * @see org.mklab.mikity.jogl.JoglCoordinate#Transform(javax.media.opengl.GL)
   */
  @Override
  public void Transform(GL gl) {
    gl.glRotatef(60f, 0.0f, 0.0f, 1.0f);
  }

  @Override
  public void addChild(JoglObject object) {
    // TODO Auto-generated method stub
    
  }

}
