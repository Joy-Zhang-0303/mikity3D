package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.model.graphic.CylinderObject;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.view.renderer.jogl.AbstractJoglPrimitive;


/**
 * シリンダーをJOGLで表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/31
 */
public class JoglCylinder extends AbstractJoglPrimitive {
  /**
   * 新しく生成された<code>JoglCylinder</code>オブジェクトを初期化します。
   * @param cylinder シリンダー
   */
  public JoglCylinder(CylinderModel cylinder) {
    super(new CylinderObject(cylinder));
  }
}
