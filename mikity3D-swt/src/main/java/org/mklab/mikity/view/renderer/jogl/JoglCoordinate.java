package org.mklab.mikity.view.renderer.jogl;

import javax.media.opengl.GL2;

import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;


/**
 * JOGLの座標系を表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglCoordinate extends Coordinate {
  /**
   * 新しく生成された<code>JoglCoordinate</code>オブジェクトを初期化します。
   */
  public JoglCoordinate() {
    super();
  }
  
  /**
   * 新しく生成された<code>JoglCoordinate</code>オブジェクトを初期化します。
   * @param translation 並進
   */
  public JoglCoordinate(TranslationModel translation) {
    super(translation);
  }
  
  /**
   * 新しく生成された<code>JoglCoordinate</code>オブジェクトを初期化します。
   * @param rotation 回転
   */
  public JoglCoordinate(RotationModel rotation) {
    super(rotation);
  }
  
  /**
   * 新しく生成された<code>JoglCoordinate</code>オブジェクトを初期化します。
   * @param translation 並進
   * @param rotation 回転
   */
  public JoglCoordinate(TranslationModel translation, RotationModel rotation) {
    super(translation, rotation);
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
}
