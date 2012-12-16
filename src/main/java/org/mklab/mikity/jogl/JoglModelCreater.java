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
   * @param groups グループ
   * 
   * @return オブジェクトのグループ
   */
  public JoglBranchGroup[] create(Group[] groups) {
    final JoglBranchGroup bg = new JoglBranchGroup();

    final JoglTransformGroup tg = new JoglTransformGroup();
    for (final Group group : groups) {
      final JoglTransformGroup child = JoglPrimitiveFactory.create(group);
      tg.addChild(child);
    }

    bg.addChild(tg); ///////
    
    final List<JoglBranchGroup> branchGroups = new ArrayList<JoglBranchGroup>();
    branchGroups.add(bg);

    return branchGroups.toArray(new JoglBranchGroup[branchGroups.size()]);
  }

}