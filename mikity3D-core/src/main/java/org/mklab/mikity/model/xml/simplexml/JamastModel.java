package org.mklab.mikity.model.xml.simplexml;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Class Model.
 * 
 * @version $Revision: 1.15 $ $Date: 2007/08/03 03:30:27 $
 */
@Root(name="model")
public class JamastModel implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  /** _groupList */
  @ElementList(type=Group.class, inline=true)
  private List<Group> _groupList;

  /**
   * コンストラクター
   */
  public JamastModel() {
    this._groupList = new ArrayList<Group>();
  }

  /**
   * Method addGroup
   * 
   * @param vGroup グループ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addGroup(org.mklab.mikity.model.xml.simplexml.model.Group vGroup) {
    this._groupList.add(vGroup);
  }

  /**
   * Method addGroup
   * 
   * @param index インデックス
   * @param vGroup グループ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addGroup(int index, org.mklab.mikity.model.xml.simplexml.model.Group vGroup) {
    this._groupList.add(index, vGroup);
  }

  /**
   * Method clearGroup
   */
  public void clearGroups() {
    this._groupList.clear();
  } 

  /**
   * Method getGroup
   * 
   * @param index グループのインデックス
   * @return (org.mklab.mikity.xml.Group) _groupList.get(index)
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public org.mklab.mikity.model.xml.simplexml.model.Group getGroup(int index) {
    if ((index < 0) || (index > this._groupList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._groupList.get(index);
  } 

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this._groupList == null) ? 0 : this._groupList.hashCode());
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
    JamastModel other = (JamastModel)obj;
    if (this._groupList == null) {
      if (other._groupList != null) {
        return false;
      }
    } else if (!this._groupList.equals(other._groupList)) {
      return false;
    }
    return true;
  }

  /**
   * Method getGroup
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.Group[] getGroups() {
    int size = this._groupList.size();
    org.mklab.mikity.model.xml.simplexml.model.Group[] groups = new org.mklab.mikity.model.xml.simplexml.model.Group[size];
    for (int index = 0; index < size; index++) {
      groups[index] = this._groupList.get(index);
    }
    return groups;
  } 

  /**
   * Method getGroupAsReferenceReturns a reference to 'group'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<Group> getGroupsAsReference() {
    return this._groupList;
  } 

  /**
   * Method getGroupCount
   * 
   * @return _groupList.size()
   */
  public int getGroupSize() {
    return this._groupList.size();
  } 

  /**
   * Method removeGroup
   * 
   * @param vGroup グループ
   * @return removed
   */
  public boolean removeGroup(org.mklab.mikity.model.xml.simplexml.model.Group vGroup) {
    boolean removed = this._groupList.remove(vGroup);
    return removed;
  }

  /**
   * Method setGroup
   * 
   * @param index インデックス
   * @param vGroup グループ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void setGroup(int index, org.mklab.mikity.model.xml.simplexml.model.Group vGroup) {
    if ((index < 0) || (index > this._groupList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._groupList.set(index, vGroup);
  } 

  /**
   * Method setGroup
   * 
   * @param groupArray グループの文字列
   */
  public void setGroups(org.mklab.mikity.model.xml.simplexml.model.Group[] groupArray) {
    this._groupList.clear();
    for (int i = 0; i < groupArray.length; i++) {
      this._groupList.add(groupArray[i]);
    }
  } 

  /**
   * Method setGroupSets the value of 'group' by copying the given ArrayList.
   * 
   * @param groupCollection the Vector to copy.
   */
  public void setGroups(List<Group> groupCollection) {
    this._groupList.clear();
    for (int i = 0; i < groupCollection.size(); i++) {
      this._groupList.add(groupCollection.get(i));
    }
  } 

  /**
   * Method setGroupAsReferenceSets the value of 'group' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param groupCollection the ArrayList to copy.
   */
  public void setGroupsAsReference(List<Group> groupCollection) {
    this._groupList = groupCollection;
  } 

}
