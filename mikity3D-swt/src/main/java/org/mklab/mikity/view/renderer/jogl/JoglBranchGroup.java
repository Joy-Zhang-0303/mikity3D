package org.mklab.mikity.view.renderer.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;


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
  private List<JoglTransformGroup> groups;

  /**
   * 新しく生成された<code>JoglBranchGroup</code>オブジェクトを初期化します。
   */
  public JoglBranchGroup() {
    this.objects = new ArrayList<>();
    this.groups = new ArrayList<>();
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
   * @param group 座標系
   */
  public void addChild(JoglTransformGroup group) {
    this.groups.add(group);
  }

  /**
   * {@inheritDoc}
   */
  public void display(GL2 gl) {
    for (final JoglObject object : this.objects) {
      object.display(gl);

    }

    for (final JoglTransformGroup group : this.groups) {
      group.display(gl);
    }

  }

}
