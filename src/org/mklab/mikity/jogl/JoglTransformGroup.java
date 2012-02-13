package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import org.mklab.mikity.jogl.models.JoglCoordinate;


/**
 * JOGLの座標系のグループを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglTransformGroup implements JoglCoordinate {

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
  
}
