package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.model.graphic.ConeObject;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
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
   * @param cone モデル
   */
  public JoglCone(ConeModel cone) {
    super(new ConeObject(cone));
  }
}
