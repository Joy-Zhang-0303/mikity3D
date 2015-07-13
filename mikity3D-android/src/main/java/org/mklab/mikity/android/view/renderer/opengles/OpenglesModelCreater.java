/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.Group;

/**
 * モデルの生成器です。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesModelCreater {

  /**
   * オブジェクトのグループを生成します。
   * 
   * @param groups グループ
   * 
   * @return オブジェクトのグループ
   */
  public List<OpenglesObjectGroup> create(final Group[] groups) {
    final List<OpenglesObjectGroup> objectGroups = new ArrayList<OpenglesObjectGroup>();
    for (final Group group : groups) {
      final OpenglesObjectGroup child = new OpenglesObjectGroupFactory().create(group);
      objectGroups.add(child);
    }
    
    return objectGroups;
  }

}
