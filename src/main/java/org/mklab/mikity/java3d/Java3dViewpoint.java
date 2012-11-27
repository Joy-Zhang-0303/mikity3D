package org.mklab.mikity.java3d;

//**********************************************************************
//MyViewpointクラス                                       *
//**********************************************************************
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Vector3f;

import org.mklab.mikity.xml.config.View;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;


/**
 * 視点を表すクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $.2005/11/22
 */
public class Java3dViewpoint {
  private TransformGroup vpTg = null;

  /**
   * コンストラクター
   * 
   * @param angle4f アングル
   * @param vector3f ベクトル
   * @param universe2 空間
   */
  public Java3dViewpoint(AxisAngle4f angle4f, Vector3f vector3f, SimpleUniverse universe2) {
    final ViewingPlatform viewPoint = universe2.getViewingPlatform();
    this.vpTg = viewPoint.getViewPlatformTransform();

    // ビューポイントのセット
    setViewpoint(angle4f, vector3f);
  }

  /**
   * 
   * コンストラクター
   * 
   * @param uni 空間
   * @param view 視点
   * @param mouseOperationType マウスの操作タイプ
   */
  public Java3dViewpoint(SimpleUniverse uni, View view, int mouseOperationType) {
    final ViewingPlatform vp = uni.getViewingPlatform();
    
    this.vpTg = vp.getViewPlatformTransform();
    if (mouseOperationType == 0) {
      // 視点方向の設定
      final Transform3D transform1 = new Transform3D();
      transform1.setRotation(new AxisAngle4f(1.0f, 0.0f, 0.0f, (float)Math.toRadians(view.loadXrotate())));
      final Transform3D transform2 = new Transform3D();
      transform2.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, (float)Math.toRadians(view.loadYrotate())));
      final Transform3D transform3 = new Transform3D();
      transform3.setRotation(new AxisAngle4f(0.0f, 0.0f, 1.0f, (float)Math.toRadians(view.loadZrotate())));
      // 視点位置の設定
      final Transform3D transform4 = new Transform3D();
      transform4.setTranslation(new Vector3f(view.loadX(), view.loadY(), view.loadZ()));

      // 変換行列の乗算
      transform1.mul(transform2);
      transform1.mul(transform3);
      transform1.mul(transform4);

      // 座標変換されえた視点座標系の設定
      this.vpTg.setTransform(transform1);
    }
  }

  /**
   * 視点の設定を行う。
   * 
   * @param angle 視点
   * @param position 位置
   */
  public void setViewpoint(AxisAngle4f angle, Vector3f position) {
    // 視点位置＆方向を設定
    final Transform3D transform1 = new Transform3D();
    transform1.setRotation(angle);
    final Transform3D transform2 = new Transform3D();
    transform2.setTranslation(position);

    // ２つの変換行列の乗算（angle -> positionの順）
    transform2.mul(transform1);

    // 座標変換された視点座標系の設定
    this.vpTg.setTransform(transform2);
  }

}