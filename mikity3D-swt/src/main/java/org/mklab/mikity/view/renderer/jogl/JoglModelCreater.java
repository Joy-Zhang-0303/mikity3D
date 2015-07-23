package org.mklab.mikity.view.renderer.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;


/**
 * treeに登録されているシーングラフのモデルからJoglのモデルを生成します。
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
  public List<JoglObjectGroup> create(final GroupModel[] groups) {
    final List<JoglObjectGroup> objectGroups = new ArrayList<>();
    for (final GroupModel group : groups) {
      final JoglObjectGroup child = new JoglObjectGroupFactory().create(group);
      objectGroups.add(child);
    }

    return objectGroups;
  }

}