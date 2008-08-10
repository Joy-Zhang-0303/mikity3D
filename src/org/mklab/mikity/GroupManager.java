/*
 * Created on 2005/02/02
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Modelerにおける編集をサポートする座標軸を作成するために、 Group毎にtgを登録したいと思った
 * 
 * @author miki
 * @version $Revision: 1.3 $.2005/02/02
 */
public class GroupManager {

  private static List<MyTransformGroup> groupList = new ArrayList<MyTransformGroup>();
  private static Map<MyTransformGroup, String> pickMap = new HashMap<MyTransformGroup, String>();

  /**
   * 指定した名前のグループの追加を行う。
   * 
   * @param name
   * @param tg
   */
  public static void addGroup(String name, MyTransformGroup tg) {
    groupList.add(tg);
    pickMap.put(tg, name);

  }

}
