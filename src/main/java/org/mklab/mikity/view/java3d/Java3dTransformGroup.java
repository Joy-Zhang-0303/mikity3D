package org.mklab.mikity.view.java3d;

import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import org.mklab.mikity.model.model.CoordinateParameter;
import org.mklab.mikity.model.model.DHParameter;
import org.mklab.mikity.model.model.MovableGroup;


/**
 * {@link TransformGroup}を拡張したクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.17 $.2005/11/22
 */
public class Java3dTransformGroup extends TransformGroup implements MovableGroup {
  /** 名前 */
  private String name;
  
  /**
   * 新しく生成された<code>Java3dTransformGroup</code>オブジェクトを初期化します。
   */
  public Java3dTransformGroup() {
    setCapability(Group.ALLOW_CHILDREN_EXTEND);
    setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
  }

  /**
   * モデルの拡大縮小の演算を行います。
   * 
   * @param scale ケール
   */
  public void scale(Vector3f scale) {
    final Vector3d scale3d = new Vector3d();
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
   * モデルの回転移動の演算を行います。
   * 
   * @param orientationAngle 方向と角度
   */
  public void rotate(AxisAngle4f orientationAngle) {
    // 座標系の変換行列の取得
    final Transform3D transform1 = new Transform3D();
    this.getTransform(transform1);

    // 新たな回転行列の設定
    final Transform3D transform2 = new Transform3D();
    transform2.setRotation(orientationAngle);

    // 回転行列の乗算
    transform2.mul(transform1);

    // 座標系に回転変換の設定
    this.setTransform(transform2);
  }

  /**
   * モデルの回転移動の演算を行う。
   * 
   * @param matrix 回転移動の行列
   */
  public void rotate(Matrix3f matrix) {
    // 座標系の変換行列の取得
    final Transform3D transform1 = new Transform3D();
    this.getTransform(transform1);

    // 新たな回転行列の設定
    final Transform3D transform2 = new Transform3D();
    transform2.setRotation(matrix);

    // 回転行列の乗算
    transform2.mul(transform1);

    // 座標系に回転変換の設定
    this.setTransform(transform2);
  }

  /**
   * モデルの平行移動の演算を行います。
   * 
   * @param translation 平行移動
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

    final Transform3D rotationY = new Transform3D();
    final double angleY = parameter.getAngleY();
    rotationY.setRotation(new AxisAngle4d(0.0, 1.0, 0.0, angleY));
    transform.mul(rotationY);

    final Transform3D translationY = new Transform3D();
    final double y = parameter.getY();
    translationY.setTranslation(new Vector3d(0.0, y, 0.0));
    transform.mul(translationY);

    final Transform3D translationX = new Transform3D();
    final double x = parameter.getX();
    translationX.setTranslation(new Vector3d(x, 0.0, 0.0));
    transform.mul(translationX);

    final Transform3D rotationX = new Transform3D();
    final double angleX = parameter.getAngleX();
    rotationX.setRotation(new AxisAngle4d(1.0, 0.0, 0.0, angleX));
    transform.mul(rotationX);

    final Transform3D translationZ = new Transform3D();
    final double z = parameter.getZ();
    translationZ.setTranslation(new Vector3d(0.0, 0.0, z));
    transform.mul(translationZ);

    final Transform3D rotationZ = new Transform3D();
    final double angleZ = parameter.getAngleZ();
    rotationZ.setRotation(new AxisAngle4d(0.0, 0.0, 1.0, angleZ));
    transform.mul(rotationZ);

    setTransform(transform);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDHParameter(final DHParameter parameter) {
    /* 座標系Σ(i-1)からΣiへの変換   */
    final Transform3D transform = new Transform3D();
    
    // 1.xi軸に沿ってa(i-1)だけ並進
    final Transform3D translationA = new Transform3D();
    final double a = parameter.getA();
    translationA.setTranslation(new Vector3d(a, 0.0, 0.0));
    transform.mul(translationA);

    // 2.x(i-1)軸回りにα(i-1)だけ回転
    final Transform3D rotationAlpha = new Transform3D();
    final double alpha = parameter.getAlpha();
    rotationAlpha.setRotation(new AxisAngle4d(1.0, 0.0, 0.0, alpha));
    transform.mul(rotationAlpha);
    
    // 3.ziに沿ってdiだけ並進
    final Transform3D translationD = new Transform3D();
    final double d = parameter.getD();
    translationD.setTranslation(new Vector3d(0.0, 0.0, d));
    transform.mul(translationD);
    
    // 4.zi軸回りにθiだけ回転
    final Transform3D rotationTheta = new Transform3D();
    final double theta = parameter.getTheta();
    rotationTheta.setRotation(new AxisAngle4d(0.0, 0.0, 1.0, theta));
    transform.mul(rotationTheta);

    setTransform(transform);
  }

  /**
   * {@inheritDoc}
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    if (this.name == null) {
      return super.toString();
    }
    return this.name;
  }
}