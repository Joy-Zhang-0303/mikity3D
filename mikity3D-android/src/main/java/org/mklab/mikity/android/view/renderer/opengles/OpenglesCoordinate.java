package org.mklab.mikity.android.view.renderer.opengles;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.AbstractCoordinate;


/**
 * OpenGL ESの座標系を表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesCoordinate extends AbstractCoordinate {
  /**
   * GLによる座標変換を適用します。
   * @param gl10 GL10クラス
   */
  public void apply(GL10 gl10) {
    gl10.glTranslatef(this.translation.getX(), this.translation.getY(), this.translation.getZ());
    gl10.glRotatef((float)Math.toDegrees(this.rotation.getX()), 1.0f, 0.0f, 0.0f);
    gl10.glRotatef((float)Math.toDegrees(this.rotation.getY()), 0.0f, 1.0f, 0.0f);
    gl10.glRotatef((float)Math.toDegrees(this.rotation.getZ()), 0.0f, 0.0f, 1.0f);
  }
}
