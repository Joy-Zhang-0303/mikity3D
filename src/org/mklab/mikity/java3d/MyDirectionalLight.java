package org.mklab.mikity.java3d;

//**********************************************************************
//            MyDirectionalLightクラス（暫定版） *
//**********************************************************************
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;


/**
 * 方向指示灯に関するクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $.2005/11/22
 */
public class MyDirectionalLight extends BranchGroup {

  private double radius = MyUniverse.boundsRadius;

  /**
   * コンストラクター
   * 
   * @param color
   * @param direction
   */
  public MyDirectionalLight(Color3f color, Vector3f direction) {
    // 光源オブジェクトの生成
    DirectionalLight light = new DirectionalLight(color, direction);

    // 影響範囲の設定
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), this.radius);
    light.setInfluencingBounds(bounds);

    // 光源オブジェクトの接続
    this.addChild(light);
  }
}