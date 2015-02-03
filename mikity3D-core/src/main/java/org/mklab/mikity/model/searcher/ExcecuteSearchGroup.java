/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mklab.mikity.model.searcher.GroupName;
import org.mklab.mikity.model.searcher.GroupLink;
import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;

/**
 * Mikity３Dモデルのグループを探索し、グループとリンクを管理するためのメソッドです。
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class ExcecuteSearchGroup {

  private List<GroupManager> addList;

  /**
   * 新しく生成された<code>ExcecuteSearchGroup</code>オブジェクトを初期化します。
   */
  public ExcecuteSearchGroup() {
  }

  /**
   * 再帰的にモデルグループ内を検索するメソッドです。
   * 
   * @param group
   *          グループリスト
   * @param parentList
   *          サーチモデルリスト
   * @return GroupManager
   */
  public GroupManager searchGroupRecursion(Group group, GroupManager parentList) {
    this.addList = new ArrayList<>();
    int groupCount = 0;
    while (true) {
      Group newGroup;
      String groupName;
      try {
        newGroup = group.getGroup(groupCount);
        groupName = newGroup.getName();
        GroupName groupNameList = new GroupName(groupName, parentList);
        parentList.addItems(searchGroupRecursion(newGroup, groupNameList));
        int linkCount = 0;
        while (true) {
          try {
            this.addList.add(searchLinkData(group, linkCount, parentList));
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
              this.addList.add(searchLinkData(group, linkCount, parentList));
              linkCount++;
            } catch (Exception ee) {
              break;
            }
          }
        }        
        
        for (Iterator<GroupManager> itr = this.addList.iterator(); itr.hasNext();) {
          GroupManager item = itr.next();
            parentList.addItems(item);
        }
        this.addList = new ArrayList<>();
        break;
      }
    }
    return parentList;
  }

  /**
   * リンクデータリスト内を検索するメソッドです。
   * @param group グループリスト
   * @param linkCount カウント
   * @param parentList 親リスト
   * @return linkList LinkSearchOfModel
   */
  @SuppressWarnings("static-method")
  public GroupManager searchLinkData(Group group, int linkCount, GroupManager parentList) {
    LinkData data = group.getLinkData(linkCount);
    int column = data.getColumnNumber();
    String target = data.getTargetName();
    GroupLink linkList = new GroupLink(column, target, parentList);
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
