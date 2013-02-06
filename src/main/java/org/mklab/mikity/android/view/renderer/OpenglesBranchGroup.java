package org.mklab.mikity.android.view.renderer;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;


/**
 * {@link OpenglesObject}のブランチグループを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesBranchGroup implements OpenglesObject {

  /** オブジェクトのリスト */
  private List<OpenglesObject> objects;
  /** 座標系 */
  private List<OpenglesTransformGroup> groups;
  
  
  /**
   * 新しく生成された<code>JoglBranchGroup</code>オブジェクトを初期化します。
   */
  public OpenglesBranchGroup() {
    this.objects = new ArrayList<OpenglesObject>();
    this.groups = new ArrayList<OpenglesTransformGroup>();
  }

  /**
   * オブジェクトを追加します。
   * 
   * @param object オブジェクト
   */
  public void addChild(OpenglesObject object) {
    this.objects.add(object);
  }

  /**
   * 座標系を追加します。
   * 
   * @param group 座標系
   */
  public void addChild(OpenglesTransformGroup group) {
    this.groups.add(group);
  }

  
  

  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl10) {
    for (final OpenglesObject object : this.objects) {
      object.display(gl10);
    }

    for (final OpenglesTransformGroup group : this.groups) {
      group.apply(gl10);
    }

  }
}
