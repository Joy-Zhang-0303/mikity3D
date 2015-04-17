/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;

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
  public GroupManager searchGroupRecursion(Group group, GroupManager parents) {
    List<GroupManager> managers = new ArrayList<>();
    int groupCount = 0;
    
    while (true) {
      try {
        final Group newGroup = group.getGroup(groupCount);
        final String groupName = newGroup.getName();
        final GroupName groupNames = new GroupName(groupName, parents);
        parents.addItems(searchGroupRecursion(newGroup, groupNames));
        
        int linkCount = 0;
        while (true) {
          try {
            managers.add(searchLinkData(group, linkCount, parents));
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
              managers.add(searchLinkData(group, linkCount, parents));
              linkCount++;
            } catch (Exception ee) {
              break;
            }
          }
        }

        for (GroupManager manager : managers) {
          parents.addItems(manager);
        }
        managers = new ArrayList<>();
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
   * @param parentList 親リスト
   * @return linkList LinkSearchOfModel
   */
  private GroupManager searchLinkData(Group group, int linkCount, GroupManager parentList) {
    final LinkData data = group.getLinkData(linkCount);
    final int column = data.getColumnNumber();
    final String target = data.getTargetName();
    final GroupLink linkList = new GroupLink(column, target, parentList);
    return linkList;
  }

  //TODO　不要なら消す
  //Windows上でデバッグするときに使用した
  //  public static void main(String[] args) {
  //    FileInputStream input;
  //    try {
  //      input = new FileInputStream(
  //          "../mikity3D-sample/src/main/resources/pendulum/pendulum/pendulum.m3d");
  //    } catch (FileNotFoundException e1) {
  //      throw new RuntimeException(e1);
  //    }
  //    try {‘
  //      Mikity3d root = new Mikity3dFactory().loadFile(input);
  //      sample(root);
  //    } catch (Mikity3dSerializeDeserializeException e) {
  //      throw new RuntimeException(e);
  //    }
  //  }
  //
  //  private static void sample(Mikity3d root) {
  //    ExcecuteSearchGroup search = new ExcecuteSearchGroup();
  //    Mikity3dModel model = root.getModel(0);
  //    Group[] groupArray = model.getGroups();
  //    Group group = groupArray[0];
  //    GroupName manager = new GroupName(group.getName(), null);
  //    GroupManager result = search.searchGroupRecursion(group, manager);
  //    int a = 0;
  //    List<GroupManager> lists = result.getItems();
  //    for (Iterator itr = lists.iterator(); itr.hasNext();) {
  //      GroupManager item = (GroupManager) itr.next();
  //      if (item.getClass() == GroupName.class) {
  //        ((GroupName) item).getGroupName();
  //      } else {
  //        ((GroupLink) item).getColumn();
  //        ((GroupLink) item).getTarget();
  //      }
  //    }
  //  }
}
