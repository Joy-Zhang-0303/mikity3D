package org.mklab.mikity.java3d;

//**********************************************************************
//             MyTransformGroupクラス（暫定版） *
//**********************************************************************
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
import org.mklab.mikity.model.IMovableGroup;
import org.mklab.mikity.model.LinkParameter;


/**
 * TransformGroupに関するクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.17 $.2005/11/22
 */
public class MyTransformGroup extends TransformGroup implements IMovableGroup {

  /**
   * コンストラクター
   */
  // Matrix4d initialMatrix = new Matrix4d();
  // private DHParameter dhParameter;
  /**
   * コンストラクター
   */
  public MyTransformGroup() {
    setCapability(Group.ALLOW_CHILDREN_EXTEND);
    setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
  }

  /**
   * モデルの拡大縮小の乗算を行う。
   * 
   * @param scale
   */
  public void mulScale(Vector3f scale) {
    // スケールのdouble型化
    Vector3d scale3d = new Vector3d();
    scale3d.x = scale.x;
    scale3d.y = scale.y;
    scale3d.z = scale.z;

    // 座標系の変換行列の取得
    Transform3D transform1 = new Transform3D();
    this.getTransform(transform1);

    // 新たなスケール行列の設定
    Transform3D transform2 = new Transform3D();
    transform2.setScale(scale3d);

    // スケール行列の乗算
    transform2.mul(transform1);

    // 座標系にスケール変換の設定
    this.setTransform(transform2);
  }

  /**
   * モデルの回転移動の乗算を行う。
   * 
   * @param angle
   */
  public void mulRotation(AxisAngle4f angle) {
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
   * モデルの回転移動の乗算を行う。
   * 
   * @param matrix
   */
  public void mulRotation(Matrix3f matrix) {
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
   * モデルの平行移動の乗算を行う。
   * 
   * @param translation
   */
  public void mulTranslation(Vector3f translation) {
    // 座標系の変換行列の取得
    Transform3D transform1 = new Transform3D();
    this.getTransform(transform1);

    // 新たな平行移動行列の設定
    Transform3D transform2 = new Transform3D();
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
   * リンクパラメータを設定する。
   * 
   * @see org.mklab.mikity.model.IMovableGroup#setLinkParameter(org.mklab.mikity.model.LinkParameter)
   */
  public void setLinkParameter(final LinkParameter link) {
    double locX = link.getLocX();
    double locY = link.getLocY();
    double locZ = link.getLocZ();
    double rotX = link.getRotX();
    double rotY = link.getRotY();
    double rotZ = link.getRotZ();

    // Matrix4d k = (Matrix4d)initialMatrix.clone();
    Transform3D initialTransform = new Transform3D();

    Transform3D translate = new Transform3D();
    translate.setRotation(new AxisAngle4d(0.0, 1.0, 0.0, rotY));
    initialTransform.mul(translate);

    translate = new Transform3D();
    translate.setTranslation(new Vector3d(0.0, locY, 0.0));
    initialTransform.mul(translate);

    translate = new Transform3D();
    translate.setTranslation(new Vector3d(locX, 0.0, 0.0));
    initialTransform.mul(translate);

    translate = new Transform3D();
    translate.setRotation(new AxisAngle4d(1.0, 0.0, 0.0, rotX));
    initialTransform.mul(translate);

    translate = new Transform3D();
    translate.setTranslation(new Vector3d(0.0, 0.0, locZ));
    initialTransform.mul(translate);

    translate = new Transform3D();
    translate.setRotation(new AxisAngle4d(0.0, 0.0, 1.0, rotZ));
    initialTransform.mul(translate);

    setTransform(initialTransform);
  }

  /**
   * DHパラメータを設定する。
   * 
   * @see org.mklab.mikity.model.IMovableGroup#setDHParameter(org.mklab.mikity.model.DHParameter)
   */
  public void setDHParameter(final DHParameter param) {
    /*
     * DHParameterとは。 座標系Σ(i-1)からΣiへの変換は
     * 
     * 1.xi軸に沿ってa(i-1)だけ並進 2.x(i-1)軸回りにα(i-1)だけ回転 3.ziに沿ってdiだけ並進 4.zi軸回りにθiだけ回転
     */

    double a = param.getA();
    double alpha = param.getAlpha();
    double d = param.getD();
    double theta = param.getTheta();

    // Matrix4d k = (Matrix4d)initialMatrix.clone();
    Transform3D initialTransform = new Transform3D();

    // initialTransform.set(k);
    Transform3D translate = new Transform3D();
    // 1.xi軸に沿ってa(i-1)だけ並進
    // translate.setTranslation(new Vector3d(a/1000,0.0,0.0));
    translate.setTranslation(new Vector3d(a, 0.0, 0.0));
    initialTransform.mul(translate);
    // 2.x(i-1)軸回りにα(i-1)だけ回転
    translate = new Transform3D();
    translate.setRotation(new AxisAngle4d(1.0, 0.0, 0.0, alpha));
    initialTransform.mul(translate);
    // 3.ziに沿ってdiだけ並進
    translate = new Transform3D();
    translate.setTranslation(new Vector3d(0.0, 0.0, d));
    initialTransform.mul(translate);
    // 4.zi軸回りにθiだけ回転
    translate = new Transform3D();
    translate.setRotation(new AxisAngle4d(0.0, 0.0, 1.0, theta));
    initialTransform.mul(translate);

    setTransform(initialTransform);
  }
}