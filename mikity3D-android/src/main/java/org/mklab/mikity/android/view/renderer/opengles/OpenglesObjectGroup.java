package org.mklab.mikity.android.view.renderer.opengles;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.ObjectGroup;
import org.mklab.mikity.model.xml.simplexml.model.Group;


/**
 * オブジェクトグループを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesObjectGroup implements ObjectGroup, OpenglesObject {
  /** オブジェクト。 */
  private List<OpenglesObject> objects = new ArrayList<OpenglesObject>();
  /** 座標系の基準。 */
  private OpenglesCoordinate baseCoordinate;
  /** 座標系。 */
  private OpenglesCoordinate coordinate = new OpenglesCoordinate();
  /** 名前。 */
  private String name;
  /** ID。 */
  private int id = 0;
  /** シリアル番号。 */
  /** モデルデータ。 */
  private Group group;
  /** モデルデータ。 */
  private static int serialID = 0;

  /**
   * 新しく生成された<code>OpenglesObjectGroup</code>オブジェクトを初期化します。
   * @param id ID
   * @param group モデルデータ
   */
  private OpenglesObjectGroup(int id, Group group) {
    this.id = id;
    this.group = group;
  }
  
  /**
   * ファクトリーメソッドです。
   * @return {@OpenglesObjectGroup}
   */
  public static OpenglesObjectGroup create() {
    return new OpenglesObjectGroup(serialID++, null);
  }

  /**
   * ファクトリーメソッドです。
   * @param group モデルデータ
   * @return {@OpenglesObjectGroup}
   */
  public static OpenglesObjectGroup create(Group group) {
    return new OpenglesObjectGroup(serialID++, group);
  }
  
  /**
   * {@inheritDoc}
   */
  public Group getGroup() {
    return this.group;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.baseCoordinate == null) ? 0 : this.baseCoordinate.hashCode());
    result = prime * result + ((this.coordinate == null) ? 0 : this.coordinate.hashCode());
    result = prime * result + ((this.group == null) ? 0 : this.group.hashCode());
    result = prime * result + this.id;
    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.objects == null) ? 0 : this.objects.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    OpenglesObjectGroup other = (OpenglesObjectGroup)obj;
    if (this.baseCoordinate == null) {
      if (other.baseCoordinate != null) return false;
    } else if (!this.baseCoordinate.equals(other.baseCoordinate)) return false;
    if (this.coordinate == null) {
      if (other.coordinate != null) return false;
    } else if (!this.coordinate.equals(other.coordinate)) return false;
    if (this.group == null) {
      if (other.group != null) return false;
    } else if (!this.group.equals(other.group)) return false;
    if (this.id != other.id) return false;
    if (this.name == null) {
      if (other.name != null) return false;
    } else if (!this.name.equals(other.name)) return false;
    if (this.objects == null) {
      if (other.objects != null) return false;
    } else if (!this.objects.equals(other.objects)) return false;
    return true;
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
