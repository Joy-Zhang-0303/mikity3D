package org.mklab.mikity.view.renderer.java3d;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

/**
 * 方向指示灯を表すクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $.2005/11/22
 */
public class Java3dDirectionalLight extends BranchGroup {

  /** */
  private double radius = 100.0f;

  /**
   * コンストラクター
   * 
   * @param color 色
   * @param orientaion 方向
   */
  public Java3dDirectionalLight(Color3f color, Vector3f orientaion) {
    final DirectionalLight light = new DirectionalLight(color, orientaion);

    // 影響範囲の設定
    final BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), this.radius);
    light.setInfluencingBounds(bounds);

    // 光源オブジェクトの接続
    this.addChild(light);
  }
}