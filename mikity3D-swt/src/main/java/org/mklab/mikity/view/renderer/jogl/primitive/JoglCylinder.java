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

//  /**
//   * 大きさを設定します。
//   * 
//   * @param radius 底面の半径
//   * @param height 高さ
//   */
//  public void setSize(float radius, float height) {
//    ((CylinderObject)this.object).setSize(radius, height);
//  }
//
//  /**
//   * 分割数を設定します。
//   * 
//   * @param division 分割数
//   */
//  public void setDivision(int division) {
//    ((CylinderObject)this.object).setDivision(division);
//  }
}
