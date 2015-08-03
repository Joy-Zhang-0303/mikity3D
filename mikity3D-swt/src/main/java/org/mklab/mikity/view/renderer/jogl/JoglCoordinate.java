package org.mklab.mikity.view.renderer.jogl;

import javax.media.opengl.GL2;

import org.mklab.mikity.model.AbstractCoordinate;


/**
 * JOGLの座標系を表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglCoordinate extends AbstractCoordinate {
  /**
   * GLによる座標変換を適用します。
   * @param gl GL
   */
  public void apply(GL2 gl) {
    gl.glTranslatef(this.translation.getX(), this.translation.getY(), this.translation.getZ());
    gl.glRotatef((float)Math.toDegrees(this.rotation.getX()), 1.0f, 0.0f, 0.0f);
    gl.glRotatef((float)Math.toDegrees(this.rotation.getY()), 0.0f, 1.0f, 0.0f);
    gl.glRotatef((float)Math.toDegrees(this.rotation.getZ()), 0.0f, 0.0f, 1.0f);
  }
}
