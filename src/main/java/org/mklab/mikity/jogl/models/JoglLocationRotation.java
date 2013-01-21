package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;


/**
 * JOGLの座標系を表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglLocationRotation implements JoglCoordinate {
  /** x軸周りの回転[rad] */
  private float rotationX;

  /** y軸周りの回転[rad] */
  private float rotationY;

  /** z軸周りの回転[rad] */
  private float rotationZ;

  /** x[m] */
  private float x;

  /** y[m] */
  private float y;

  /** z[m] */
  private float z;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    gl.glTranslatef(this.x, this.y, this.z);
    gl.glRotatef((float)Math.toDegrees(this.rotationX), 1.0f, 0.0f, 0.0f);
    gl.glRotatef((float)Math.toDegrees(this.rotationY), 0.0f, 1.0f, 0.0f);
    gl.glRotatef((float)Math.toDegrees(this.rotationZ), 0.0f, 0.0f, 1.0f);
  }

  /**
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
   * x-y-z軸周りの回転を設定します。
   * 
   * @param rotationX x軸周りの回転
   * @param rotationY y軸周りの回転
   * @param rotationZ z軸周りの回転
   */
  public void setRotation(float rotationX, float rotationY, float rotationZ) {
    this.rotationX = rotationX;
    this.rotationY = rotationY;
    this.rotationZ = rotationZ;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.rotationX + ", " + this.rotationY + ", " + this.rotationZ +  ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
  }
}
