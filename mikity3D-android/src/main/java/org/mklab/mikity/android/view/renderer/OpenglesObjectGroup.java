package org.mklab.mikity.android.view.renderer;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.ObjectGroup;


/**
 * OpenGL ESの座標系のグループを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesObjectGroup implements ObjectGroup, OpenglesObject {

  /** オブジェクトのリスト */
  private List<OpenglesObject> objects;
  /** トランスフォームグループのリスト */
  private List<OpenglesObjectGroup> groups;
  /** 座標系の初期値 */
  private OpenglesCoordinate baseCoordinate;
  /** 座標系 */
  private OpenglesCoordinate coordinate = new OpenglesCoordinate();
  /** 名前 */
  private String name;

  /**
   * 新しく生成された<code>JoglTransformGroup</code>オブジェクトを初期化します。
   */
  public OpenglesObjectGroup() {
    this.objects = new ArrayList<OpenglesObject>();
    this.groups = new ArrayList<OpenglesObjectGroup>();
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
  public void addChild(OpenglesObjectGroup child) {
    this.groups.add(child);
  }

  /**
   * 座標系の基準を設定します。
   * 
   * @param coordinate 座標系の基準
   */
  public void setBaseCoordinate(OpenglesCoordinate coordinate) {
    this.baseCoordinate = coordinate;
  }

  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl10) {
    gl10.glPushMatrix();

    if (this.baseCoordinate != null) {
      this.baseCoordinate.apply(gl10);
    }

    if (this.coordinate != null) {
      this.coordinate.apply(gl10);
    }

    for (final OpenglesObject object : this.objects) {
      object.display(gl10);
    }

    for (final OpenglesObjectGroup group : this.groups) {
      group.display(gl10);
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

    this.coordinate.setTranslation((float)a, 0, (float)d);

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

    final double translationX = parameter.getTranslationX();
    final double translationY = parameter.getTranslationY();
    final double translatkonZ = parameter.getTranslationZ();

    this.coordinate.setTranslation((float)translationX, (float)translationY, (float)translatkonZ);

    final double rotationX = parameter.getRotationX();
    final double rotationY = parameter.getRotationY();
    final double rotationZ = parameter.getRotationZ();

    this.coordinate.setRotation((float)rotationX, (float)rotationY, (float)rotationZ);
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
