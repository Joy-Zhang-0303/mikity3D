package org.mklab.mikity.view.renderer.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;


/**
 * treeに登録されているシーングラフのモデルからJoglのオブジェクトを生成します。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/25
 */
public class JoglObjectFatory {

  /**
   * オブジェクトのグループを生成します。
   * 
   * @param groups グループ
   * 
   * @return オブジェクトのグループ
   */
  public List<JoglObjectGroup> create(final GroupModel[] groups) {
    final List<JoglObjectGroup> objectGroups = new ArrayList<>();
    for (final GroupModel rootGroup : groups) {
      final JoglObjectGroup objectGroup = new JoglObjectGroupFactory().create(rootGroup);
      objectGroups.add(objectGroup);
    }

    return objectGroups;
  }

}