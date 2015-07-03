package org.mklab.mikity.view.renderer.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.Group;


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
  public List<JoglObjectGroup> create(final Group[] groups) {
    final List<JoglObjectGroup> objectGroups = new ArrayList<>();
    for (final Group group : groups) {
      final JoglObjectGroup child = JoglPrimitiveFactory.create(group);
      objectGroups.add(child);
    }

    return objectGroups;
  }

}