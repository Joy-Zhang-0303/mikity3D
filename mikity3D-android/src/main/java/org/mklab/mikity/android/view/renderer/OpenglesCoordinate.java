package org.mklab.mikity.android.view.renderer;

import javax.microedition.khronos.opengles.GL10;


/**
 * OpenGL ESの座標系を表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesCoordinate {
  /** x軸周りの回転角度[rad] */
  private float rotationX;
  /** y軸周りの回転角度[rad] */
  private float rotationY;
  /** z軸周りの回転角度[rad] */
  private float rotationZ;
  /** x座標[m] */
  private float x;
  /** y座標[m] */
  private float y;
  /** z座標[m] */
  private float z;

  /**
   * GLによる座標変換を適用します。
   * @param gl10 GL10クラス
   */
  public void apply(GL10 gl10) {
    gl10.glTranslatef(this.x, this.y, this.z);
    gl10.glRotatef((float)Math.toDegrees(this.rotationX), 1.0f, 0.0f, 0.0f);
    gl10.glRotatef((float)Math.toDegrees(this.rotationY), 0.0f, 1.0f, 0.0f);
    gl10.glRotatef((float)Math.toDegrees(this.rotationZ), 0.0f, 0.0f, 1.0f);
  }

  /**
   * 座標を設定します。
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
   * x-y-z軸周りの回転角度を設定します。
   * 
   * @param rotationX x軸周りの回転角度[rad]
   * @param rotationY y軸周りの回転角度[rad]
   * @param rotationZ z軸周りの回転角度[rad]
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
