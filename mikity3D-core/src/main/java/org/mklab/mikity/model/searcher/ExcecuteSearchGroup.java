/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.SourceModel;

/**
 * Mikity３Dモデルのグループを探索し、グループとアニメーションを管理するためのクラスです。
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
    int groupIndex = 0;
    
    while (true) {
      try {
        //final GroupModel newGroup = group.getGroup(groupCount);
        final GroupModel newGroup = group.getGroups()[groupIndex];
        final String groupName = newGroup.getName();
        final GroupNameManager groupNames = new GroupNameManager(groupName, parents);
        parents.addItems(searchGroupRecursion(newGroup, groupNames));
        
        int animationIndex = 0;
        while (true) {
          try {
            items.add(searchAnimation(group, animationIndex, parents));
            animationIndex++;
          } catch (Exception e) {
            break;
          }
        }
        groupIndex++;
      } catch (Exception e) {
        //"Index: 0, Size: 0" Windows上で発生
        //"Invalid index 0, size is 0" Android上で発生
        if (e.getMessage().equals("Index: 0, Size: 0") || e.getMessage().equals("Invalid index 0, size is 0")) { //$NON-NLS-1$ //$NON-NLS-2$
          int animationIndex = 0;
          while (true) {
            try {
              items.add(searchAnimation(group, animationIndex, parents));
              animationIndex++;
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
   * @param animationIndex カウント
   * @param parents 親リスト
   * @return links LinkSearchOfModel
   */
  private GroupManager searchAnimation(GroupModel group, int animationIndex, GroupManager parents) {
    final AnimationModel animation = group.getAnimations()[animationIndex];
    final SourceModel source = animation.getSource();
    final int number = source.getNumber();
    final String target = animation.getTarget();
    final GroupAnimationManager groupAnimation = new GroupAnimationManager(number, target, parents);
    return groupAnimation;
  }
}
