package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.model.graphic.ConeObject;
import org.mklab.mikity.view.renderer.jogl.AbstractJoglPrimitive;

/**
 * コーンをJOGLで表したクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglCone extends AbstractJoglPrimitive {
  /**
   * 新しく生成された<code>JoglCone</code>オブジェクトを初期化します。
   */
  public JoglCone() {
    super(new ConeObject());
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 底面の半径
   * @param hight 高さ
   */
  public void setSize(float radius, float hight) {
    ((ConeObject)this.object).setSize(radius, hight);
  }

  /**
   * 分割数を設定します。
   * 
   * @param division 分割数
   */
  public void setDivision(int division) {
    ((ConeObject)this.object).setDivision(division);
  }

}
