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
   * 新しく生成された<code>JoglBranchGroup</code>オブジェクトを初期化します。
   */
  public JoglBranchGroup() {
    this.objects = new ArrayList<JoglObject>();
    this.transfromGroups = new ArrayList<JoglTransformGroup>();
  }

  /**
   * オブジェクトを追加します。
   * 
   * @param object オブジェクト
   */
  public void addChild(JoglObject object) {
    this.objects.add(object);
  }

  /**
   * 座標系を追加します。
   * 
   * @param tg 座標系
   */
  public void addChild(JoglTransformGroup tg) {
    this.transfromGroups.add(tg);
  }

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    for (final JoglObject object : this.objects) {
      object.apply(gl);
    }
    
    for (final JoglTransformGroup group : this.transfromGroups) {
      group.apply(gl);
    }

  }
}
