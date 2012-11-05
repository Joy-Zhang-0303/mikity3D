package org.mklab.mikity.java3d;

import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.MovableGroup;
import org.mklab.mikity.model.CoordinateParameter;


/**
 * TransformGroupを拡張したクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.17 $.2005/11/22
 */
public class Java3dTransformGroup extends TransformGroup implements MovableGroup {
  /**
   * コンストラクター
   */
  public Java3dTransformGroup() {
    setCapability(Group.ALLOW_CHILDREN_EXTEND);
    setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
  }

  /**
   * モデルの拡大縮小の演算を行う。
   * 
   * @param scale 大きさ
   */
  public void scale(Vector3f scale) {
    Vector3d scale3d = new Vector3d();
    scale3d.x = scale.x;
    scale3d.y = scale.y;
    scale3d.z = scale.z;

    // 座標系の変換行列の取得
    final Transform3D transform1 = new Transform3D();
    this.getTransform(transform1);

    // 新たなスケール行列の設定
    final Transform3D transform2 = new Transform3D();
    transform2.setScale(scale3d);

    // スケール行列の乗算
    transform2.mul(transform1);

    // 座標系にスケール変換の設定
    this.setTransform(transform2);
  }

  /**
   * モデルの回転移動の演算を行う。
   * 
   * @param angle 角度
   */
  public void rotate(AxisAngle4f angle) {
    // 座標系の変換行列の取得
    Transform3D transform1 = new Transform3D();
    this.getTransform(transform1);

    // 新たな回転行列の設定
    Transform3D transform2 = new Transform3D();
    transform2.setRotation(angle);

    // 回転行列の乗算
    transform2.mul(transform1);

    // 座標系に回転変換の設定
    this.setTransform(transform2);
  }

  /**
   * モデルの回転移動の演算を行う。
   * 
   * @param matrix 変換行列
   */
  public void rotate(Matrix3f matrix) {
    // 座標系の変換行列の取得
    Transform3D transform1 = new Transform3D();
    this.getTransform(transform1);

    // 新たな回転行列の設定
    Transform3D transform2 = new Transform3D();
    transform2.setRotation(matrix);

    // 回転行列の乗算
    transform2.mul(transform1);

    // 座標系に回転変換の設定
    this.setTransform(transform2);
  }

  /**
   * モデルの平行移動の演算を行う。
   * 
   * @param translation 移動
   */
  public void translate(Vector3f translation) {
    // 座標系の変換行列の取得
    final Transform3D transform1 = new Transform3D();
    this.getTransform(transform1);

    // 新たな平行移動行列の設定
    final Transform3D transform2 = new Transform3D();
    transform2.setTranslation(translation);

    // 平行移動行列の乗算
    transform2.mul(transform1);

    // 座標系に平行移動変換の設定
    this.setTransform(transform2);
  }

  /**
   * @see javax.media.j3d.Group#addChild(javax.media.j3d.Node)
   */
  @Override
  public void addChild(Node node) {
    // ノードのトランスフォーム・グループへの接続
    super.addChild(node);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCoordinateParameter(final CoordinateParameter parameter) {
    final Transform3D transform = new Transform3D();

    final Transform3D translate1 = new Transform3D();
    final double rotY = parameter.getRotY();
    translate1.setRotation(new AxisAngle4d(0.0, 1.0, 0.0, rotY));
    transform.mul(translate1);

    final Transform3D translate2 = new Transform3D();
    final double locY = parameter.getLocY();
    translate2.setTranslation(new Vector3d(0.0, locY, 0.0));
    transform.mul(translate2);

    final Transform3D translate3 = new Transform3D();
    final double locX = parameter.getLocX();
    translate3.setTranslation(new Vector3d(locX, 0.0, 0.0));
    transform.mul(translate3);

    final Transform3D translate4 = new Transform3D();
    final double rotX = parameter.getRotX();
    translate4.setRotation(new AxisAngle4d(1.0, 0.0, 0.0, rotX));
    transform.mul(translate4);

    final Transform3D translate5 = new Transform3D();
    final double locZ = parameter.getLocZ();
    translate5.setTranslation(new Vector3d(0.0, 0.0, locZ));
    transform.mul(translate5);

    final Transform3D translate6 = new Transform3D();
    final double rotZ = parameter.getRotZ();
    translate6.setRotation(new AxisAngle4d(0.0, 0.0, 1.0, rotZ));
    transform.mul(translate6);

    setTransform(transform);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDHParameter(final DHParameter parameter) {
    /*
     * DHParameterとは。 座標系Σ(i-1)からΣiへの変換は
     * 
     * 1.xi軸に沿ってa(i-1)だけ並進
     * 
     * 2.x(i-1)軸回りにα(i-1)だけ回転 
     * 
     * 3.ziに沿ってdiだけ並進 
     * 
     * 4.zi軸回りにθiだけ回転
     */
    final Transform3D transform = new Transform3D();
    
    // 1.xi軸に沿ってa(i-1)だけ並進
    final Transform3D translate1 = new Transform3D();
    final double a = parameter.getA();
    translate1.setTranslation(new Vector3d(a, 0.0, 0.0));
    transform.mul(translate1);

    // 2.x(i-1)軸回りにα(i-1)だけ回転
    final Transform3D translate2 = new Transform3D();
    final double alpha = parameter.getAlpha();
    translate2.setRotation(new AxisAngle4d(1.0, 0.0, 0.0, alpha));
    transform.mul(translate2);
    
    // 3.ziに沿ってdiだけ並進
    final Transform3D translate3 = new Transform3D();
    final double d = parameter.getD();
    translate3.setTranslation(new Vector3d(0.0, 0.0, d));
    transform.mul(translate3);
    
    // 4.zi軸回りにθiだけ回転
    final Transform3D translate4 = new Transform3D();
    final double theta = parameter.getTheta();
    translate4.setRotation(new AxisAngle4d(0.0, 0.0, 1.0, theta));
    transform.mul(translate4);

    setTransform(transform);
  }
}