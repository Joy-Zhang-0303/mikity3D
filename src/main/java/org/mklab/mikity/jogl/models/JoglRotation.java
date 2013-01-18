package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;

/**
 * @author iwamoto
 * @version $Revision$, 2012/02/08
 */
public class JoglRotation implements JoglCoordinate {
  /** x軸周りの回転[rad] */
  private float rotationX;

  /** y軸周りの回転[rad] */
  private float rotationY;

  /** z軸周りの回転[rad] */
  private float rotationZ;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    if (this.rotationX != 0.0f) {
      gl.glRotatef((float)Math.toDegrees(this.rotationX), 1.0f, 0.0f, 0.0f);
    }
    if (this.rotationY != 0.0f) {
      gl.glRotatef((float)Math.toDegrees(this.rotationY), 0.0f, 1.0f, 0.0f);
    }
    if (this.rotationZ != 0.0f) {
      gl.glRotatef((float)Math.toDegrees(this.rotationZ), 0.0f, 0.0f, 1.0f);
    }
  }

  /**
   * @param rotationX x軸軸周りの回転
   * @param rotationY y軸軸周りの回転
   * @param rotationZ z軸軸周りの回転
   */
  public void setRotation(float rotationX, float rotationY, float rotationZ) {
    this.rotationX = rotationX;
    this.rotationY = rotationY;
    this.rotationZ = rotationZ;
  }

  /**
   * 回転します。
   * 
   * @param dRotationX x軸周りの回転
   * @param dRotationY y軸周りの回転
   * @param dRotationZ z軸周りの回転
   */
  public void rotate(float dRotationX, float dRotationY, float dRotationZ) {
    this.rotationX += dRotationX;
    this.rotationY += dRotationY;
    this.rotationZ += dRotationZ;
  }
}
