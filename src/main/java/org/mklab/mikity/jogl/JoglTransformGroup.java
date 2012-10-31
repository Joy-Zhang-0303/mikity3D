package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import org.mklab.mikity.jogl.models.JoglCoordinate;
import org.mklab.mikity.jogl.models.JoglLocation;
import org.mklab.mikity.jogl.models.JoglRotation;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.MovableGroup;
import org.mklab.mikity.model.CoordinateParameter;


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
  /** 座標系のリスト */
  private JoglCoordinate coordinate;

  /**
   * Initialize the generated object of {@link JoglTransformGroup}.
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
   * @see org.mklab.mikity.jogl.models.JoglCoordinate#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {
    if (this.coordinate != null) {
      this.coordinate.apply(gl);
    }

    for (int i = 0; i < this.objects.size(); i++) {
      JoglObject object = this.objects.get(i);
      object.apply(gl);
    }

    for (int i = 0; i < this.transformGroups.size(); i++) {
      JoglTransformGroup tg = this.transformGroups.get(i);
      tg.apply(gl);
    }
  }

  /**
   * @see org.mklab.mikity.model.MovableGroup#setDHParameter(org.mklab.mikity.model.DHParameter)
   */
  @Override
  public void setDHParameter(DHParameter param) {
    // TODO 自動生成されたメソッド・スタブ
  }

  /**
   * @see org.mklab.mikity.model.MovableGroup#setLinkParameter(org.mklab.mikity.model.CoordinateParameter)
   */
  @Override
  public void setLinkParameter(CoordinateParameter link) {
    double locX = link.getLocX();
    double locY = link.getLocY();
    double locZ = link.getLocZ();
    double rotX = link.getRotX();
    double rotY = link.getRotY();
    double rotZ = link.getRotZ();

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
    
    for (JoglTransformGroup group : this.transformGroups) {
      group.setLinkParameter(link);
    }
  }
}
