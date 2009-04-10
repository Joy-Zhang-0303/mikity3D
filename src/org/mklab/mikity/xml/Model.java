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
  } // -- org.mklab.mikity.xml.Model()

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Method addGroup
   * 
   * @param vGroup
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addGroup(org.mklab.mikity.xml.model.Group vGroup) throws java.lang.IndexOutOfBoundsException {
    this._groupList.add(vGroup);
  } // -- void addGroup(org.mklab.mikity.xml.Group)

  /**
   * Method addGroup
   * 
   * @param index
   * @param vGroup
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addGroup(int index, org.mklab.mikity.xml.model.Group vGroup) throws java.lang.IndexOutOfBoundsException {
    this._groupList.add(index, vGroup);
  } // -- void addGroup(int, org.mklab.mikity.xml.Group)

  /**
   * Method clearGroup
   */
  public void clearGroup() {
    this._groupList.clear();
  } // -- void clearGroup()

  //  /**
  //   * Method enumerateGroup
  //   * 
  //   * @return org.exolab.castor.util.IteratorEnumeration(_groupList.iterator())
  //   */
  //  public java.util.Enumeration enumerateGroup() {
  //    return new org.exolab.castor.util.IteratorEnumeration(this._groupList.iterator());
  //  } // -- java.util.Enumeration enumerateGroup()

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj
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
  } // -- boolean equals(java.lang.Object)

  /**
   * Method getGroup
   * 
   * @param index
   * @return (org.mklab.mikity.xml.Group) _groupList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.model.Group loadGroup(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._groupList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._groupList.get(index);
  } // -- org.mklab.mikity.xml.Group getGroup(int)

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
  } // -- org.mklab.mikity.xml.Group[] getGroup()

  /**
   * Method getGroupAsReferenceReturns a reference to 'group'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<Group> loadGroupAsReference() {
    return this._groupList;
  } // -- java.util.ArrayList getGroupAsReference()

  /**
   * Method getGroupCount
   * 
   * @return _groupList.size()
   */
  public int loadGroupCount() {
    return this._groupList.size();
  } // -- int getGroupCount()

  /**
   * Method removeGroup
   * 
   * @param vGroup
   * @return removed
   */
  public boolean removeGroup(org.mklab.mikity.xml.model.Group vGroup) {
    boolean removed = this._groupList.remove(vGroup);
    return removed;
  } // -- boolean removeGroup(org.mklab.mikity.xml.Group)

  /**
   * Method setGroup
   * 
   * @param index
   * @param vGroup
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setGroup(int index, org.mklab.mikity.xml.model.Group vGroup) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._groupList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._groupList.set(index, vGroup);
  } // -- void setGroup(int, org.mklab.mikity.xml.Group)

  /**
   * Method setGroup
   * 
   * @param groupArray
   */
  public void setGroup(org.mklab.mikity.xml.model.Group[] groupArray) {
    // -- copy array
    this._groupList.clear();
    for (int i = 0; i < groupArray.length; i++) {
      this._groupList.add(groupArray[i]);
    }
  } // -- void setGroup(org.mklab.mikity.xml.Group)

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
  } // -- void setGroup(java.util.ArrayList)

  /**
   * Method setGroupAsReferenceSets the value of 'group' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param groupCollection the ArrayList to copy.
   */
  public void setGroupAsReference(java.util.ArrayList<Group> groupCollection) {
    this._groupList = groupCollection;
  } // -- void setGroupAsReference(java.util.ArrayList)

}
