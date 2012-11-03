/*
 * Created on 2005/02/02
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mklab.mikity.java3d.MyTransformGroup;


/**
 * Modelerにおける編集をサポートする座標軸を作成するために Group毎にtgを登録します。
 * 
 * @author miki
 * @version $Revision: 1.3 $.2005/02/02
 */
public class GroupManager {

  private static List<MyTransformGroup> groups = new ArrayList<MyTransformGroup>();
  private static Map<MyTransformGroup, String> pickMap = new HashMap<MyTransformGroup, String>();

  /**
   * 指定した名前のグループを追加します。
   * 
   * @param name 名前
   * @param group トランスフォームグループ
   */
  public static void addGroup(String name, MyTransformGroup group) {
    groups.add(group);
    pickMap.put(group, name);
  }

}
