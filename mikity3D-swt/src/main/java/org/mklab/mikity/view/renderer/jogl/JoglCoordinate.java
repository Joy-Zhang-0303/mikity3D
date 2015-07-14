package org.mklab.mikity.view.renderer.jogl;

import javax.media.opengl.GL2;


/**
 * JOGLの座標系を表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglCoordinate {
  /** x軸周りの回転角度[rad] */
  private float rotationX;

  /** y軸周りの回転角度[rad] */
  private float rotationY;

  /** z軸周りの回転角度[rad] */
  private float rotationZ;

  /** x軸方向の移動距離[m] */
  private float x;

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Float.floatToIntBits(this.rotationX);
    result = prime * result + Float.floatToIntBits(this.rotationY);
    result = prime * result + Float.floatToIntBits(this.rotationZ);
    result = prime * result + Float.floatToIntBits(this.x);
    result = prime * result + Float.floatToIntBits(this.y);
    result = prime * result + Float.floatToIntBits(this.z);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    JoglCoordinate other = (JoglCoordinate)obj;
    if (Float.floatToIntBits(this.rotationX) != Float.floatToIntBits(other.rotationX)) return false;
    if (Float.floatToIntBits(this.rotationY) != Float.floatToIntBits(other.rotationY)) return false;
    if (Float.floatToIntBits(this.rotationZ) != Float.floatToIntBits(other.rotationZ)) return false;
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) return false;
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) return false;
    if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) return false;
    return true;
  }

  /** y軸方向の移動距離[m] */
  private float y;

  /** z軸方向の移動距離[m] */
  private float z;
  
  /**
   * GLによる座標変換を適用します。
   * @param gl GL
   */
  public void apply(GL2 gl) {
    gl.glTranslatef(this.x, this.y, this.z);
    gl.glRotatef((float)Math.toDegrees(this.rotationX), 1.0f, 0.0f, 0.0f);
    gl.glRotatef((float)Math.toDegrees(this.rotationY), 0.0f, 1.0f, 0.0f);
    gl.glRotatef((float)Math.toDegrees(this.rotationZ), 0.0f, 0.0f, 1.0f);
  }

  /**
   * x軸, y軸, z軸方向の距離を設定します。
   * 
   * @param x x座標方向の距離
   * @param y y座標方向の距離
   * @param z z座標方向の距離
   */
  public void setTranslation(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * x-y-z軸周りの回転角度を設定します。
   * 
   * @param rotationX x軸周りの回転角度
   * @param rotationY y軸周りの回転角度
   * @param rotationZ z軸周りの回転角度
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
