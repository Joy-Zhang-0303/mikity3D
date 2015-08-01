package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.model.graphic.SphereObject;
import org.mklab.mikity.view.renderer.jogl.AbstractJoglPrimitive;

/**
 * 球をJOGLで表したクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglSphere extends AbstractJoglPrimitive {
  /**
   * 新しく生成された<code>JoglSphere</code>オブジェクトを初期化します。
   */
  public JoglSphere() {
    super(new SphereObject());
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 半径
   */
  public void setSize(float radius) {
    ((SphereObject)this.object).setSize(radius);
  }
  
  /**
   * 分割数を設定します。
   * @param division 分割数
   */
  public void setDivision(int division) {
    ((SphereObject)this.object).setDivision(division);
  }
}
