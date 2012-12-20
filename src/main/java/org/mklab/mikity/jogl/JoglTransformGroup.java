package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import org.mklab.mikity.jogl.models.JoglCoordinate;
import org.mklab.mikity.jogl.models.JoglLocation;
import org.mklab.mikity.jogl.models.JoglRotation;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.MovableGroup;


/**
 * JOGLの座標系のグループを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglTransformGroup implements JoglCoordinate, MovableGroup {
  /** オブジェクトのリスト */
  private List<JoglObject> objects;
  /** トランスフォームグループのリスト */
  private List<JoglTransformGroup> transformGroups;
  /** 座標系 */
  private JoglCoordinate coordinate;

  /**
   * 新しく生成された<code>JoglTransformGroup</code>オブジェクトを初期化します。
   */
  public JoglTransformGroup() {
    this.objects = new ArrayList<JoglObject>();
    this.transformGroups = new ArrayList<JoglTransformGroup>();
  }

  /**
   * オブジェクトを追加します
   * 
   * @param child オブジェクト
   */
  public void addChild(JoglObject child) {
    this.objects.add(child);
  }

  /**
   * トランスフォームグループを追加します
   * 
   * @param group トランスフォームグループ
   */
  public void addChild(JoglTransformGroup group) {
    this.transformGroups.add(group);
  }

  /**
   * 座標系を追加します
   * 
   * @param corrdinate 座標系
   */
  public void setCoordinate(JoglCoordinate corrdinate) {
    this.coordinate = corrdinate;
  }

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    if (this.coordinate != null) {
      this.coordinate.apply(gl);
    }

    for (final JoglObject object : this.objects) {
      object.apply(gl);
    }

    for (final JoglTransformGroup group : this.transformGroups) {
      group.apply(gl);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setDHParameter(DHParameter parameter) {
    // TODO 自動生成されたメソッド・スタブ
  }

  /**
   * {@inheritDoc}
   */
  public void setCoordinateParameter(CoordinateParameter parameter) {
    final double locX = parameter.getX();
    final double locY = parameter.getY();
    final double locZ = parameter.getZ();
    final double rotX = parameter.getRotationX();
    final double rotY = parameter.getRotationY();
    final double rotZ = parameter.getRotationZ();

    //System.out.println("lx, ly, lz(set)(this)=" + locX + "," + locY + "," + locZ + ":" + this);
    //System.out.println("rx, ry, rz(set)(this)=" + rotX + "," + rotY + "," + rotZ + ":" + this);

    if (locX != 0 || locY != 0 || locZ != 0) {
      if (this.coordinate != null) {
        if (this.coordinate instanceof JoglLocation) {
          ((JoglLocation)this.coordinate).translate((float)locX, (float)locY, (float)locZ);
        }
      }
    }

    if (rotX != 0 || rotY != 0 || rotZ != 0) {
      if (this.coordinate != null) {
        if (this.coordinate instanceof JoglRotation) {
          ((JoglRotation)this.coordinate).rotate((float)rotX, (float)rotY, (float)rotZ);
        }
      }
    }
    
    for (final JoglTransformGroup group : this.transformGroups) {
      group.setCoordinateParameter(parameter);
    }
  }
}
