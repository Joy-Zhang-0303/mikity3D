package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;


/**
 * {@link JoglObject}のブランチグループを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/20
 */
public class JoglBranchGroup implements JoglObject {
  /** オブジェクトのリスト */
  private List<JoglObject> objects;
  /** 座標系のリスト */
  private List<JoglCoordinate> coodinates;
  /** 座標系  */
  private JoglCoordinate child;

  /**
   * Initialize the generated object of {@link JoglBranchGroup}.
   */
  public JoglBranchGroup() {
    this.objects = new ArrayList<JoglObject>();
    this.coodinates = new ArrayList<JoglCoordinate>();
  }

  /**
   * オブジェクトと座標系を合わせて追加します
   * 
   * @param object オブジェクト
   * @param corrdinate 座標系
   */
  public void addModels(JoglCoordinate corrdinate, JoglObject object) {
    this.coodinates.add(corrdinate);
    this.objects.add(object);
  }
  
  /**
   * オブジェクトを追加します
   * 
   * @param object オブジェクト
   */
  public void addObject(JoglObject object) {
    this.objects.add(object);
  }
  
  /**
   * 座標系を追加します
   * 
   * @param child 座標系
   */
  public void setChild(JoglCoordinate child) {
    this.child = child;
  }
  
  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {
    for (int i = 0; i < this.objects.size(); i++) {
      JoglObject object = this.objects.get(i);
      object.apply(gl);
    }
    if (this.child != null) {
      this.child.apply(gl);
    }
  }
}
