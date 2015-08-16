package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.model.graphic.SphereObject;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
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
   * @param sphere 球
   */
  public JoglSphere(SphereModel sphere) {
    super(new SphereObject(sphere));
  }
}
