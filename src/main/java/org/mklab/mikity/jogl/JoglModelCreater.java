package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.xml.model.Group;


/**
 * treeに登録されているシーングラフのモデルからJoglのモデルを作る。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/25
 */
public class JoglModelCreater {

  /**
   * オブジェクトのグループを生成します。
   * 
   * @param group グループ
   * 
   * @return オブジェクトのグループ
   */
  public JoglBranchGroup[] create(Group[] group) {
    final JoglBranchGroup bg = new JoglBranchGroup();

    final JoglTransformGroup tg = new JoglTransformGroup();
    for (int i = 0; i < group.length; i++) {
      final JoglTransformGroup child = JoglPrimitiveFactory.create(group[i]);
      tg.addChild(child);
    }

    bg.addChild(tg); ///////
    
    final List<JoglBranchGroup> groups = new ArrayList<JoglBranchGroup>();
    groups.add(bg);

    return groups.toArray(new JoglBranchGroup[groups.size()]);
  }

}