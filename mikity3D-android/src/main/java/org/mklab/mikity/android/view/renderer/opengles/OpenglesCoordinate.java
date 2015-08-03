package org.mklab.mikity.android.view.renderer.opengles;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;


/**
 * OpenGL ESの座標系を表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesCoordinate extends Coordinate {
  /**
   * 新しく生成された<code>OpenglesCoordinate</code>オブジェクトを初期化します。
   */
  public OpenglesCoordinate() {
    super();
  }
  
  /**
   * 新しく生成された<code>OpenglesCoordinate</code>オブジェクトを初期化します。
   * @param translation 並進
   */
  public OpenglesCoordinate(TranslationModel translation) {
    super(translation);
  }
  
  /**
   * 新しく生成された<code>OpenglesCoordinate</code>オブジェクトを初期化します。
   * @param rotation 回転
   */
  public OpenglesCoordinate(RotationModel rotation) {
    super(rotation);
  }
  
  /**
   * 新しく生成された<code>OpenglesCoordinate</code>オブジェクトを初期化します。
   * @param translation 並進
   * @param rotation 回転
   */
  public OpenglesCoordinate(TranslationModel translation, RotationModel rotation) {
    super(translation, rotation);
  }
  
  
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
}
