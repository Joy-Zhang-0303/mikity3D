/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Model.java,v 1.15 2007/08/03 03:30:27 morimune Exp $
 */

package org.mklab.mikity.xml;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.xml.model.Group;


/**
 * Class Model.
 * 
 * @version $Revision: 1.15 $ $Date: 2007/08/03 03:30:27 $
 */
public class Model implements java.io.Serializable {

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * Field _groupList
   */
  @XmlElement
  private java.util.ArrayList<Group> _groupList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  /**
   * コンストラクター
   */
  public Model() {
    super();
    this._groupList = new ArrayList<Group>();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Method addGroup
   * 
   * @param vGroup グループ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addGroup(org.mklab.mikity.xml.model.Group vGroup) throws java.lang.IndexOutOfBoundsException {
    this._groupList.add(vGroup);
  }

  /**
   * Method addGroup
   * 
   * @param index インデックス
   * @param vGroup グループ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addGroup(int index, org.mklab.mikity.xml.model.Group vGroup) throws java.lang.IndexOutOfBoundsException {
    this._groupList.add(index, vGroup);
  }

  /**
   * Method clearGroup
   */
  public void clearGroup() {
    this._groupList.clear();
  }

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj オブジェクト
   */
  @Override
  public boolean equals(java.lang.Object obj) {
    if (this == obj) return true;

    if (obj instanceof Model) {

      Model temp = (Model)obj;
      if (this._groupList != null) {
        if (temp._groupList == null) return false;
        else if (!(this._groupList.equals(temp._groupList))) return false;
      } else if (temp._groupList != null) return false;
      return true;
    }
    return false;
  } 

  /**
   * Method getGroup
   * 
   * @param index グループのインデックス
   * @return (org.mklab.mikity.xml.Group) _groupList.get(index)
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public org.mklab.mikity.xml.model.Group loadGroup(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._groupList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._groupList.get(index);
  } 

  /**
   * Method getGroup
   * 
   * @return mArray
   */
  public org.mklab.mikity.xml.model.Group[] loadGroup() {
    int size = this._groupList.size();
    org.mklab.mikity.xml.model.Group[] mArray = new org.mklab.mikity.xml.model.Group[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._groupList.get(index);
    }
    return mArray;
  } 

  /**
   * Method getGroupAsReferenceReturns a reference to 'group'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<Group> loadGroupAsReference() {
    return this._groupList;
  } 

  /**
   * Method getGroupCount
   * 
   * @return _groupList.size()
   */
  public int loadGroupCount() {
    return this._groupList.size();
  } 

  /**
   * Method removeGroup
   * 
   * @param vGroup グループ
   * @return removed
   */
  public boolean removeGroup(org.mklab.mikity.xml.model.Group vGroup) {
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
  public void setGroup(int index, org.mklab.mikity.xml.model.Group vGroup) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
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
  public void setGroup(org.mklab.mikity.xml.model.Group[] groupArray) {
    // -- copy array
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
  public void setGroup(java.util.ArrayList<Group> groupCollection) {
    // -- copy collection
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
  public void setGroupAsReference(java.util.ArrayList<Group> groupCollection) {
    this._groupList = groupCollection;
  } 

}
