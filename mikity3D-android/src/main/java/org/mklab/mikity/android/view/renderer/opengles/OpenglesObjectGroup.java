package org.mklab.mikity.android.view.renderer.opengles;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.ObjectGroup;


/**
 * オブジェクトグループを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesObjectGroup implements ObjectGroup, OpenglesObject {
  /** オブジェクト。 */
  private List<OpenglesObject> objects;
//  /** サブグループ。 */
//  private List<OpenglesObjectGroup> groups;
  /** 座標系の基準。 */
  private OpenglesCoordinate baseCoordinate;
  /** 座標系。 */
  private OpenglesCoordinate coordinate = new OpenglesCoordinate();
  /** 名前。 */
  private String name;

  /**
   * 新しく生成された<code>OpenglesObjectGroup</code>オブジェクトを初期化します。
   */
  public OpenglesObjectGroup() {
    this.objects = new ArrayList<OpenglesObject>();
//    this.groups = new ArrayList<OpenglesObjectGroup>();
  }

  /**
   * オブジェクトを追加します。
   * 
   * @param child オブジェクト
   */
  public void addChild(OpenglesObject child) {
    this.objects.add(child);
  }

//  /**
//   * サブグループを追加します。
//   * 
//   * @param child サブグループ
//   */
//  public void addChild(OpenglesObjectGroup child) {
//    this.groups.add(child);
//  }

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

//    for (final OpenglesObjectGroup group : this.groups) {
//      group.display(gl10);
//    }

    gl10.glPopMatrix();
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
