package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglLocation implements JoglCoordinate {
  /** x */
  private float x;

  /** y */
  private float y;

  /** z */
  private float z;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    //gl.glPushMatrix();
    gl.glTranslatef(this.x, this.y, this.z);
  }

  /**
   * 位置を設定します。
   * 
   * @param x x座標
   * @param y y座標
   * @param z z座標
   */
  public void setLocation(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  /**
   * 平行移動します。
   * 
   * @param dx x座用
   * @param dy y座標
   * @param dz z座標
   */
  public void translate(float dx, float dy, float dz) {
    this.x += dx;
    this.y += dy;
    this.z += dz;
  }
}
