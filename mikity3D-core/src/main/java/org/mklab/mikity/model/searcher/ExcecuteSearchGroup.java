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
        final GroupModel newGroup = group.getGroup(groupCount);
        final String groupName = newGroup.getName();
        final GroupName groupNames = new GroupName(groupName, parents);
        parents.addItems(searchGroupRecursion(newGroup, groupNames));
        
        int linkCount = 0;
        while (true) {
          try {
            items.add(searchLinkData(group, linkCount, parents));
            linkCount++;
          } catch (Exception e) {
            break;
          }
        }
        groupCount++;
      } catch (Exception e) {
        //"Index: 0, Size: 0" Windows上で発生
        //"Invalid index 0, size is 0" Android上で発生
        if (e.getMessage().equals("Index: 0, Size: 0") || e.getMessage().equals("Invalid index 0, size is 0")) { //$NON-NLS-1$ //$NON-NLS-2$
          int linkCount = 0;
          while (true) {
            try {
              items.add(searchLinkData(group, linkCount, parents));
              linkCount++;
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
   * リンクデータリスト内を検索するメソッドです。
   * 
   * @param group グループリスト
   * @param linkCount カウント
   * @param parents 親リスト
   * @return links LinkSearchOfModel
   */
  private GroupManager searchLinkData(GroupModel group, int linkCount, GroupManager parents) {
    final AnimationModel data = group.getAnimation(linkCount);
    final int column = data.getNumber();
    final String target = data.getTarget();
    final GroupLink links = new GroupLink(column, target, parents);
    return links;
  }
}
