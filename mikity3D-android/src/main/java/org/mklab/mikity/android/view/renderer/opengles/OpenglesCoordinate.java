package org.mklab.mikity.android.view.renderer.opengles;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;


/**
 * OpenGL ESの座標系を表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesCoordinate {
  /** 並進。 */
  private TranslationModel translation = new TranslationModel();
  
  /** 回転。 */
  private RotationModel rotation = new RotationModel();
  
//  /** x座標[m] */
//  private float x;
//  /** y座標[m] */
//  private float y;
//  /** z座標[m] */
//  private float z;
//  /** x軸周りの回転角度[rad] */
//  private float rotationX;
//  /** y軸周りの回転角度[rad] */
//  private float rotationY;
//  /** z軸周りの回転角度[rad] */
//  private float rotationZ;

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

//  /**
//   * 座標を設定します。
//   * 
//   * @param translationX x軸方向への並進
//   * @param translationY y軸方向への並進
//   * @param translationZ z軸方向への並進
//   */
//  public void setTranslation(float translationX, float translationY, float translationZ) {
//    this.x = translationX;
//    this.y = translationY;
//    this.z = translationZ;
//  }

//  /**
//   * x-y-z軸周りの回転角度を設定します。
//   * 
//   * @param rotationX x軸周りの回転角度[rad]
//   * @param rotationY y軸周りの回転角度[rad]
//   * @param rotationZ z軸周りの回転角度[rad]
//   */
//  public void setRotation(float rotationX, float rotationY, float rotationZ) {
//    this.rotationX = rotationX;
//    this.rotationY = rotationY;
//    this.rotationZ = rotationZ;
//  }
  
  /**
   * 並進を設定します。
   * @param translation 並進
   * 
   */
  public void setTranslation(TranslationModel translation) {
//    this.x = translation.getX();
//    this.y = translation.getY();
//    this.z = translation.getZ();
    this.translation = translation;
  }
  
  /**
   * x-y-z軸周りの回転角度を設定します。
   * @param rotation 回転
   * 
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
