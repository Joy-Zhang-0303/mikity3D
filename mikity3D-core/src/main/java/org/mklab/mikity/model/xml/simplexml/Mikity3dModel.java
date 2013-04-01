package org.mklab.mikity.model.xml.simplexml;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * モデルを表すクラスです。
 * 
 * @version $Revision: 1.15 $ $Date: 2007/08/03 03:30:27 $
 */
@Root(name="model")
public class Mikity3dModel implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  /** groups */
  @ElementList(type=Group.class, inline=true)
  private List<Group> groups;

  /**
   * コンストラクター
   */
  public Mikity3dModel() {
    this.groups = new ArrayList<Group>();
  }

  /**
   * Method addGroup
   * 
   * @param argGroup グループ
   */
  public void addGroup(Group argGroup) {
    this.groups.add(argGroup);
  }

//  /**
//   * Method addGroup
//   * 
//   * @param index インデックス
//   * @param argGroup グループ
//   */
//  public void addGroup(int index, Group argGroup) {
//    this.groups.add(index, argGroup);
//  }

//  /**
//   * Method clearGroup
//   */
//  public void clearGroups() {
//    this.groups.clear();
//  } 

  /**
   * Method getGroup
   * 
   * @param index グループのインデックス
   * @return (org.mklab.mikity.xml.Group) _groupList.get(index)
   */
  public Group getGroup(int index) {
    if ((index < 0) || (index > this.groups.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.groups.get(index);
  } 

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.groups == null) ? 0 : this.groups.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Mikity3dModel other = (Mikity3dModel)obj;
    if (this.groups == null) {
      if (other.groups != null) {
        return false;
      }
    } else if (!this.groups.equals(other.groups)) {
      return false;
    }
    return true;
  }

  /**
   * Method getGroup
   * 
   * @return mArray
   */
  public Group[] getGroups() {
    final int size = this.groups.size();
    final Group[] localGroups = new Group[size];
    for (int i = 0; i < size; i++) {
      localGroups[i] = this.groups.get(i);
    }
    return localGroups;
  } 

//  /**
//   * Method getGroupAsReferenceReturns a reference to 'group'. No type checking is performed on any modications to the Collection.
//   * 
//   * @return returns a reference to the Collection.
//   */
//  public List<Group> getGroupsAsReference() {
//    return this.groups;
//  } 

//  /**
//   * Method getGroupCount
//   * 
//   * @return _groupList.size()
//   */
//  public int getGroupSize() {
//    return this.groups.size();
//  } 

//  /**
//   * Method removeGroup
//   * 
//   * @param argGroup グループ
//   * @return removed
//   */
//  public boolean removeGroup(Group argGroup) {
//    boolean removed = this.groups.remove(argGroup);
//    return removed;
//  }

//  /**
//   * Method setGroup
//   * 
//   * @param index インデックス
//   * @param argGroup グループ
//   * @throws java.lang.IndexOutOfBoundsException 例外
//   */
//  public void setGroup(int index, Group argGroup) {
//    if ((index < 0) || (index > this.groups.size())) {
//      throw new IndexOutOfBoundsException();
//    }
//    this.groups.set(index, argGroup);
//  } 

//  /**
//   * Method setGroup
//   * 
//   * @param argGroups グループの文字列
//   */
//  public void setGroups(Group[] argGroups) {
//    this.groups.clear();
//    for (int i = 0; i < argGroups.length; i++) {
//      this.groups.add(argGroups[i]);
//    }
//  } 

//  /**
//   * Method setGroupSets the value of 'group' by copying the given ArrayList.
//   * 
//   * @param argGroups the Vector to copy.
//   */
//  public void setGroups(List<Group> argGroups) {
//    this.groups.clear();
//    for (int i = 0; i < argGroups.size(); i++) {
//      this.groups.add(argGroups.get(i));
//    }
//  } 

//  /**
//   * Method setGroupAsReferenceSets the value of 'group' by setting it to the given ArrayList. No type checking is performed.
//   * 
//   * @param argGroups the ArrayList to copy.
//   */
//  public void setGroupsAsReference(List<Group> argGroups) {
//    this.groups = argGroups;
//  } 

}
