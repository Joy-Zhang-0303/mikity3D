/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer;

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
  public List<OpenglesTransformGroup> create(final Group[] groups) {
    final List<OpenglesTransformGroup> objectGroups = new ArrayList<OpenglesTransformGroup>();
    for (final Group group : groups) {
      final OpenglesTransformGroup child = OpenglesPrimitiveFactory.create(group);
      objectGroups.add(child);
    }
    
    return objectGroups;
  }

}
