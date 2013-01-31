package org.mklab.mikity.view.java3d;

//**********************************************************************
//MyViewpointクラス                                       *
//**********************************************************************
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Vector3f;

import org.mklab.mikity.model.xml.config.View;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;


/**
 * 視点を表すクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $.2005/11/22
 */
public class Java3dViewpoint {
  private TransformGroup viewPointTranform = null;

  /**
   * コンストラクター
   * 
   * @param orientationAngle 視点の方向と角度
   * @param position 視点の位置
   * @param universe 空間
   */
  public Java3dViewpoint(AxisAngle4f orientationAngle, Vector3f position, SimpleUniverse universe) {
    final ViewingPlatform viewPoint = universe.getViewingPlatform();
    this.viewPointTranform = viewPoint.getViewPlatformTransform();

    // ビューポイントのセット
    setViewpoint(orientationAngle, position);
  }

  /**
   * 
   * コンストラクター
   * 
   * @param universe 空間
   * @param view 視点
   * @param type マウスの操作タイプ
   */
  public Java3dViewpoint(SimpleUniverse universe, View view, int type) {
    final ViewingPlatform viewPoint = universe.getViewingPlatform();
    
    this.viewPointTranform = viewPoint.getViewPlatformTransform();
    if (type == 0) {
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
      this.viewPointTranform.setTransform(transform1);
    }
  }

  /**
   * 視点の位置と方向を設定します。
   * 
   * @param orientationAngle 方向と角度
   * @param position 位置
   */
  public void setViewpoint(AxisAngle4f orientationAngle, Vector3f position) {
    final Transform3D transform1 = new Transform3D();
    transform1.setRotation(orientationAngle);
    final Transform3D transform2 = new Transform3D();
    transform2.setTranslation(position);

    // ２つの変換行列の乗算（angle -> positionの順）
    transform2.mul(transform1);

    // 座標変換された視点座標系の設定
    this.viewPointTranform.setTransform(transform2);
  }

}