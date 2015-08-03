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
  /** 並進。 */
  private TranslationModel translation = new TranslationModel();
  
  /** 回転。 */
  private RotationModel rotation = new RotationModel();
 
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.rotation == null) ? 0 : this.rotation.hashCode());
    result = prime * result + ((this.translation == null) ? 0 : this.translation.hashCode());
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
    final JoglCoordinate other = (JoglCoordinate)obj;
    if (this.rotation == null) {
      if (other.rotation != null) return false;
    } else if (!this.rotation.equals(other.rotation)) return false;
    if (this.translation == null) {
      if (other.translation != null) return false;
    } else if (!this.translation.equals(other.translation)) return false;
    return true;
  }


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

  
  /**
   * x軸, y軸, z軸方向の並進を設定します。
   * 
   * @param translation 並進 
   */
  public void setTranslation(TranslationModel translation) {
    this.translation = translation;
  }

  /**
   * x-y-z軸周りの回転角度を設定します。
   * 
   * @param rotation 回転
   */
  public void setRotation(RotationModel rotation) {
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
