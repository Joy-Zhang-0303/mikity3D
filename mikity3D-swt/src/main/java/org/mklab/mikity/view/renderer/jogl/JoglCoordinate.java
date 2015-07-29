package org.mklab.mikity.view.renderer.jogl;

import javax.media.opengl.GL2;

import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;


/**
 * JOGLの座標系を表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglCoordinate {
//  /** x軸周りの回転角度[rad] */
//  private float rotationX;
//
//  /** y軸周りの回転角度[rad] */
//  private float rotationY;
//
//  /** z軸周りの回転角度[rad] */
//  private float rotationZ;
//
//  /** x軸方向の移動距離[m] */
//  private float x;
//
//  /** y軸方向の移動距離[m] */
//  private float y;
//
//  /** z軸方向の移動距離[m] */
//  private float z;
  
  
  /** 並進。 */
  private TranslationModel translation = new TranslationModel();
  
  /** 回転。 */
  private RotationModel rotation = new RotationModel();
  

//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public int hashCode() {
//    final int prime = 31;
//    int result = 1;
//    result = prime * result + Float.floatToIntBits(this.rotationX);
//    result = prime * result + Float.floatToIntBits(this.rotationY);
//    result = prime * result + Float.floatToIntBits(this.rotationZ);
//    result = prime * result + Float.floatToIntBits(this.x);
//    result = prime * result + Float.floatToIntBits(this.y);
//    result = prime * result + Float.floatToIntBits(this.z);
//    return result;
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj) return true;
//    if (obj == null) return false;
//    if (getClass() != obj.getClass()) return false;
//    JoglCoordinate other = (JoglCoordinate)obj;
//    if (Float.floatToIntBits(this.rotationX) != Float.floatToIntBits(other.rotationX)) return false;
//    if (Float.floatToIntBits(this.rotationY) != Float.floatToIntBits(other.rotationY)) return false;
//    if (Float.floatToIntBits(this.rotationZ) != Float.floatToIntBits(other.rotationZ)) return false;
//    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) return false;
//    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) return false;
//    if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) return false;
//    return true;
//  }
 
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

//  /**
//   * x軸, y軸, z軸方向の距離を設定します。
//   * 
//   * @param x x座標方向の距離
//   * @param y y座標方向の距離
//   * @param z z座標方向の距離
//   */
//  public void setTranslation(float x, float y, float z) {
//    this.x = x;
//    this.y = y;
//    this.z = z;
//  }

//  /**
//   * x-y-z軸周りの回転角度を設定します。
//   * 
//   * @param rotationX x軸周りの回転角度
//   * @param rotationY y軸周りの回転角度
//   * @param rotationZ z軸周りの回転角度
//   */
//  public void setRotation(float rotationX, float rotationY, float rotationZ) {
//    this.rotationX = rotationX;
//    this.rotationY = rotationY;
//    this.rotationZ = rotationZ;
//  }
  
  /**
   * x軸, y軸, z軸方向の並進を設定します。
   * 
   * @param translation 並進 
   */
  public void setTranslation(TranslationModel translation) {
//    this.x = translation.getX();
//    this.y = translation.getY();
//    this.z = translation.getZ();
    this.translation = translation;
  }

  /**
   * x-y-z軸周りの回転角度を設定します。
   * 
   * @param rotation 回転
   */
  public void setRotation(RotationModel rotation) {
//    this.rotationX = rotation.getX();
//    this.rotationY = rotation.getY();
//    this.rotationZ = rotation.getZ();
    this.rotation = rotation;
  }

  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "(" + this.translation.getX() + ", " + this.translation.getY() + ", " + this.translation.getZ() + ", " + this.rotation.getX() + ", " + this.rotation.getY() + ", " + this.rotation.getZ() +  ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
  }
}
