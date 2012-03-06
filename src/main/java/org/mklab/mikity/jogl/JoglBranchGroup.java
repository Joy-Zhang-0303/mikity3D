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
  /** 座標系 */
  private List<JoglTransformGroup> transfromGroups;

  /**
   * Initialize the generated object of {@link JoglBranchGroup}.
   */
  public JoglBranchGroup() {
    this.objects = new ArrayList<JoglObject>();
    this.transfromGroups = new ArrayList<JoglTransformGroup>();
  }

  /**
   * オブジェクトを追加します
   * 
   * @param object オブジェクト
   */
  public void addChild(JoglObject object) {
    this.objects.add(object);
  }

  /**
   * 座標系を追加します
   * 
   * @param tg 座標系
   */
  public void addChild(JoglTransformGroup tg) {
    this.transfromGroups.add(tg);
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
    for (int i = 0; i < this.transfromGroups.size(); i++) {
      JoglTransformGroup tg = this.transfromGroups.get(i);
      tg.apply(gl);
    }

  }
}
