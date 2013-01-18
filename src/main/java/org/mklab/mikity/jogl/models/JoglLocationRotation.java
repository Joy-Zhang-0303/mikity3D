package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglLocationRotation implements JoglCoordinate {
  /** x軸周りの回転 */
  private float rotationX;

  /** y軸周りの回転 */
  private float rotationY;

  /** z軸周りの回転 */
  private float rotationZ;

  /** x */
  private float x;

  /** y*/
  private float y;

  /** z */
  private float z;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    //gl.glPushMatrix();
    
    gl.glTranslatef(this.x, this.y, this.z);
    
    if (this.rotationX != 0.0f) {
      gl.glRotatef(this.rotationX, 1.0f, 0.0f, 0.0f);
    }
    if (this.rotationY != 0.0f) {
      gl.glRotatef(this.rotationY, 0.0f, 1.0f, 0.0f);
    }
    if (this.rotationZ != 0.0f) {
      gl.glRotatef(this.rotationZ, 0.0f, 0.0f, 1.0f);
    }
    
    //gl.glPopMatrix();
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
