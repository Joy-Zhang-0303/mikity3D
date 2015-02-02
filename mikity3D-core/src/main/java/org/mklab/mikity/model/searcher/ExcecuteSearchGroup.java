/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mklab.mikity.model.searcher.GroupName;
import org.mklab.mikity.model.searcher.GroupLink;
import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dModel;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;

public class ExcecuteSearchGroup {

  private List<GroupManager> addList;

  public ExcecuteSearchGroup() {
  }

  /**
   * 再帰的にモデルグループ内を検索するメソッドです。
   * 
   * @param group
   *          グループリスト
   * @param parentList
   *          サーチモデルリスト
   * @return
   */
  public GroupManager searchGroupRecursion(Group group, GroupManager parentList) {
    this.addList = new ArrayList<GroupManager>();
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
//            parentList.addItems(searchLinkData(group, linkCount, parentList));
            linkCount++;
          } catch (Exception e) {
            break;
          }
        }
        groupCount++;
      } catch (Exception e) {
        if (e.getMessage().equals("Index: 0, Size: 0") || e.getMessage().equals("Invalid index 0, size is 0")) {
          int linkCount = 0;
          while (true) {
            try {
              this.addList.add(searchLinkData(group, linkCount, parentList));
//              parentList.addItems(searchLinkData(group, linkCount, parentList));
              linkCount++;
            } catch (Exception ee) {
              break;
            }
          }
        }
        
        
        for (Iterator itr = addList.iterator(); itr.hasNext();) {
          GroupManager item = (GroupManager) itr.next();
            parentList.addItems(item);
        }
        this.addList = new ArrayList<GroupManager>();
//        while(true) {
//          try {
////            parentList.addItems(item);
//          } catch(Exception ee) {
//            break;
//          }
//        }
        break;
      }
    }
    return parentList;
  }

  /**
   * リンクデータリスト内を検索するメソッドです。
   * 
   * @param group
   *          グループリスト
   * @param list
   *          サーチモデルリスト
   * @param linkCount
   *          カウント
   * @return linkList LinkSearchOfModel
   */
  public GroupManager searchLinkData(Group group, int linkCount, GroupManager parentList) {
    LinkData data = group.getLinkData(linkCount);
    int column = data.getColumnNumber();
    String target = data.getTargetName();
    GroupLink linkList = new GroupLink(column, target, parentList);

    return linkList;
  }

  public GroupManager searchLinkDataForArray(Group group, int linkCount, GroupManager parentList) {
    LinkData data = group.getLinkData(linkCount);
    int column = data.getColumnNumber();
    String target = data.getTargetName();
    GroupLink linkList = new GroupLink(column, target, parentList);

    return linkList;
  }

  public static void main(String[] args) {
    FileInputStream input;
    try {
      input = new FileInputStream(
          "../mikity3D-sample/src/main/resources/pendulum/pendulum/pendulum.m3d");
    } catch (FileNotFoundException e1) {
      throw new RuntimeException(e1);
    }
    try {
      Mikity3d root = new Mikity3dFactory().loadFile(input);
      sample(root);
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
  }

  private static void sample(Mikity3d root) {
    ExcecuteSearchGroup search = new ExcecuteSearchGroup();
    Mikity3dModel model = root.getModel(0);
    Group[] groupArray = model.getGroups();
    Group group = groupArray[0];
    GroupName manager = new GroupName(group.getName(), null);
    GroupManager result = search.searchGroupRecursion(group, manager);
    int a = 0;
    List<GroupManager> lists = result.getItems();
    for (Iterator itr = lists.iterator(); itr.hasNext();) {
      GroupManager item = (GroupManager) itr.next();
      if (item.getClass() == GroupName.class) {
        ((GroupName) item).getGroupName();
      } else {
        ((GroupLink) item).getColumn();
        ((GroupLink) item).getTarget();
      }
    }
  }
}
