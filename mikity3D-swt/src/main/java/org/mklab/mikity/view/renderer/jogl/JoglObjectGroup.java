package org.mklab.mikity.view.renderer.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.ObjectGroup;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

/**
 * オブジェクトグループを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglObjectGroup implements ObjectGroup, JoglObject {
  /** オブジェクト。 */
  private List<JoglObject> objects = new ArrayList<>();
  /** 座標系の基準。 */
  private JoglCoordinate baseCoordinate;
  /** 座標系。 */
  private JoglCoordinate coordinate = new JoglCoordinate();
  /** 名前。 */
  private String name;
  /** ID。 */
  private int id = 0;
  /** シリアル番号。 */
  private static int serialID = 0;
  /** モデルデータ。 */
  private GroupModel group;

  /**
   * 新しく生成された<code>JoglObjectGroup</code>オブジェクトを初期化します。
   * @param id ID
   * @param group モデルデータ
   */
  private JoglObjectGroup(int id, GroupModel group) {
    this.id = id;
    this.group = group;
  }
  
  /**
   * ファクトリーメソッドです。
   * @return グループ
   */
  public static JoglObjectGroup create() {
    return new JoglObjectGroup(serialID++, null);
  }

  /**
   * ファクトリーメソッドです。
   * @param group モデルデータ
   * @return グループ
   */
  public static JoglObjectGroup create(GroupModel group) {
    return new JoglObjectGroup(serialID++, group);
  }
  
  /**
   * {@inheritDoc}
   */
  public GroupModel getGroup() {
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
    JoglObjectGroup other = (JoglObjectGroup)obj;
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
  public void addChild(JoglObject child) {
    this.objects.add(child);
  }


  /**
   * 座標系の基準を設定します。
   * 
   * @param coordinate 座標系の基準
   */
  public void setBaseCoordinate(JoglCoordinate coordinate) {
    this.baseCoordinate = coordinate; 
  }

  /**
   * {@inheritDoc}
   */
  public void display(GL2 gl) {
    gl.glPushMatrix();
    
    if (this.baseCoordinate != null) {
      this.baseCoordinate.apply(gl);
    }

    if (this.coordinate != null) {
      this.coordinate.apply(gl);
    }

    for (final JoglObject object : this.objects) {
      object.display(gl);
    }
    
    gl.glPopMatrix();
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
    final double translationZ = parameter.getTranslationZ();
    
    this.coordinate.setTranslation((float)translationX, (float)translationY, (float)translationZ);

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
