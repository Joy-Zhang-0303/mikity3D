/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;

/**
 * Mikity３Dモデルのグループを探索し、グループとリンクを管理するためのメソッドです。
 * 
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class ExcecuteSearchGroup {
  /**
   * 再帰的にモデルグループ内を検索するメソッドです。
   * 
   * @param group グループリスト
   * @param parents サーチモデルリスト
   * @return GroupManager
   */
  public GroupManager searchGroupRecursion(GroupModel group, GroupManager parents) {
    List<GroupManager> items = new ArrayList<>();
    int groupCount = 0;
    
    while (true) {
      try {
        //final GroupModel newGroup = group.getGroup(groupCount);
        final GroupModel newGroup = group.getGroups()[groupCount];
        final String groupName = newGroup.getName();
        final GroupName groupNames = new GroupName(groupName, parents);
        parents.addItems(searchGroupRecursion(newGroup, groupNames));
        
        int animationCount = 0;
        while (true) {
          try {
            items.add(searchAnimation(group, animationCount, parents));
            animationCount++;
          } catch (Exception e) {
            break;
          }
        }
        groupCount++;
      } catch (Exception e) {
        //"Index: 0, Size: 0" Windows上で発生
        //"Invalid index 0, size is 0" Android上で発生
        if (e.getMessage().equals("Index: 0, Size: 0") || e.getMessage().equals("Invalid index 0, size is 0")) { //$NON-NLS-1$ //$NON-NLS-2$
          int animationCount = 0;
          while (true) {
            try {
              items.add(searchAnimation(group, animationCount, parents));
              animationCount++;
            } catch (Exception ee) {
              break;
            }
          }
        }

        for (GroupManager item : items) {
          parents.addItems(item);
        }
        items = new ArrayList<>();
        break;
      }
    }
    return parents;
  }

  /**
   * アニメーションデータリスト内を検索するメソッドです。
   * 
   * @param group グループリスト
   * @param animationCount カウント
   * @param parents 親リスト
   * @return links LinkSearchOfModel
   */
  private GroupManager searchAnimation(GroupModel group, int animationCount, GroupManager parents) {
    //final AnimationModel data = group.getAnimation(animationCount);
    final AnimationModel data = group.getAnimations()[animationCount];
    final int number = data.getNumber();
    final String target = data.getTarget();
    final GroupAnimation links = new GroupAnimation(number, target, parents);
    return links;
  }
}
