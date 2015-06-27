package org.mklab.mikity.android.view.renderer;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.MovableGroup;


/**
 * OpenGL ESの座標系のグループを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesTransformGroup implements MovableGroup {

  /** オブジェクトのリスト */
  private List<OpenglesObject> objects;
  /** トランスフォームグループのリスト */
  private List<OpenglesTransformGroup> groups;
  /** 座標系の初期値 */
  private OpenglesCoordinate initialCoordinate;
  /** 座標系 */
  private OpenglesCoordinate coordinate = new OpenglesCoordinate();
  /** 名前 */
  private String name;

  /**
   * 新しく生成された<code>JoglTransformGroup</code>オブジェクトを初期化します。
   */
  public OpenglesTransformGroup() {
    this.objects = new ArrayList<OpenglesObject>();
    this.groups = new ArrayList<OpenglesTransformGroup>();
  }

  /**
   * オブジェクトを追加します。
   * 
   * @param child オブジェクト
   */
  public void addChild(OpenglesObject child) {
    this.objects.add(child);
  }

  /**
   * トランスフォームグループを追加します。
   * 
   * @param child トランスフォームグループ
   */
  public void addChild(OpenglesTransformGroup child) {
    this.groups.add(child);
  }

  /**
   * 座標系の初期値を設定します。
   * 
   * @param coordinate 座標系の初期値
   */
  public void setInitialCoordinate(OpenglesCoordinate coordinate) {
    this.initialCoordinate = coordinate;
  }

  /**
   * @param gl10 GL10クラス
   */
  public void apply(GL10 gl10) {
    gl10.glPushMatrix();

    if (this.initialCoordinate != null) {
      this.initialCoordinate.apply(gl10);
    }

    if (this.coordinate != null) {
      this.coordinate.apply(gl10);
    }

    for (final OpenglesObject object : this.objects) {
      object.display(gl10);
    }

    for (final OpenglesTransformGroup group : this.groups) {
      group.apply(gl10);
    }

    gl10.glPopMatrix();
  }

  /**
   * {@inheritDoc}
   */
  public void setDHParameter(DHParameter parameter) {
    if (this.coordinate == null) {
      return;
    }

    /* 座標系Σ(i-1)からΣiへの変換   */
    // 1.xi軸に沿ってa(i-1)だけ並進
    // 2.x(i-1)軸回りにα(i-1)だけ回転
    // 3.ziに沿ってdiだけ並進   
    // 4.zi軸回りにθiだけ回転

    final double a = parameter.getA();
    final double d = parameter.getD();

    this.coordinate.setLocation((float)a, 0, (float)d);

    final double alpha = parameter.getAlpha();
    final double theta = parameter.getTheta();

    this.coordinate.setRotation((float)alpha, 0, (float)theta);
  }

  /**
   * {@inheritDoc}
   */
  public void setCoordinateParameter(CoordinateParameter parameter) {
    if (this.coordinate == null) {
      return;
    }

    final double x = parameter.getTranslationX();
    final double y = parameter.getTranslationY();
    final double z = parameter.getTranslationZ();

    this.coordinate.setLocation((float)x, (float)y, (float)z);

    final double angleX = parameter.getRotationX();
    final double angleY = parameter.getRotationY();
    final double angleZ = parameter.getRotationZ();

    this.coordinate.setRotation((float)angleX, (float)angleY, (float)angleZ);
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

    if (this.coordinate == null) {
      return this.name;
    }

    return this.name + this.coordinate;
  }

}
