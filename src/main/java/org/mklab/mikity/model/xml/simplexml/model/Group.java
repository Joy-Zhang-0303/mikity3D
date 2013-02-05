package org.mklab.mikity.model.xml.simplexml.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;


/**
 * Class Group.
 * 
 * @version $Revision: 1.4 $ $Date: 2007/12/13 10:01:55 $
 */
public class Group implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** _name */
  @Attribute
  private String _name;

  /** _XMLBoxList */
  @ElementList
  private List<XMLBox> _XMLBoxList;

  /** _XMLCylinderList */
  @ElementList
  private List<XMLCylinder> _XMLCylinderList;

  /** _XMLSphereList */
  @ElementList
  private List<XMLSphere> _XMLSphereList;

  /** _XMLConeList */
  @ElementList
  private List<XMLCone> _XMLConeList;

  /** _XMLTrianglePolygonList */
  @ElementList
  private List<XMLTrianglePolygon> _XMLTrianglePolygonList;

  /** _XMLQuadPolygonList */
  @ElementList
  private List<XMLQuadPolygon> _XMLQuadPolygonList;

  /** _location */
  @Element
  private org.mklab.mikity.model.xml.simplexml.model.Location _location;

  /** _rotation */
  @Element
  private org.mklab.mikity.model.xml.simplexml.model.Rotation _rotation;

  /** _linkdataList */
  @Element
  private List<LinkData> _linkdataList;

  /** _groupList */
  @Element
  private List<Group> _groupList;

  /**
   * コンストラクター
   */
  public Group() {
    super();
    this._XMLBoxList = new ArrayList<XMLBox>();
    this._XMLCylinderList = new ArrayList<XMLCylinder>();
    this._XMLSphereList = new ArrayList<XMLSphere>();
    this._XMLConeList = new ArrayList<XMLCone>();
    this._XMLTrianglePolygonList = new ArrayList<XMLTrianglePolygon>();
    this._XMLQuadPolygonList = new ArrayList<XMLQuadPolygon>();

    this._linkdataList = new ArrayList<LinkData>();
    this._groupList = new ArrayList<Group>();
    this._location = new Location();
    this._rotation = new Rotation();
  } 

  /**
   * Method addGroup
   * 
   * @param vGroup グループ
   */
  public void addGroup(org.mklab.mikity.model.xml.simplexml.model.Group vGroup) {
    this._groupList.add(vGroup);
  } 

  /**
   * Method addGroup
   * 
   * @param index インデックス
   * @param vGroup グループ
   */
  public void addGroup(int index, org.mklab.mikity.model.xml.simplexml.model.Group vGroup) {
    this._groupList.add(index, vGroup);
  } 

  /**
   * Method addLinkdata
   * 
   * @param vLinkdata リンクデータ
   */
  public void addLinkdata(org.mklab.mikity.model.xml.simplexml.model.LinkData vLinkdata) {
    this._linkdataList.add(vLinkdata);
  } 

  /**
   * Method addLinkdata
   * 
   * @param index インデックス
   * @param vLinkdata リンクデータ
   */
  public void addLinkdata(int index, org.mklab.mikity.model.xml.simplexml.model.LinkData vLinkdata) {
    this._linkdataList.add(index, vLinkdata);
  } 

  /**
   * Method addXMLBox
   * 
   * @param vXMLBox ボックス
   */
  public void addXMLBox(org.mklab.mikity.model.xml.simplexml.model.XMLBox vXMLBox) {
    this._XMLBoxList.add(vXMLBox);
  } 

  /**
   * Method addXMLBox
   * 
   * @param index インデックス
   * @param vXMLBox ボックス
   */
  public void addXMLBox(int index, org.mklab.mikity.model.xml.simplexml.model.XMLBox vXMLBox) {
    this._XMLBoxList.add(index, vXMLBox);
  } 

  /**
   * Method addXMLCone
   * 
   * @param vXMLCone コーン
   */
  public void addXMLCone(org.mklab.mikity.model.xml.simplexml.model.XMLCone vXMLCone) {
    this._XMLConeList.add(vXMLCone);
  } 

  /**
   * Method addXMLCone
   * 
   * @param index インデックス
   * @param vXMLCone コーン
   */
  public void addXMLCone(int index, org.mklab.mikity.model.xml.simplexml.model.XMLCone vXMLCone) {
    this._XMLConeList.add(index, vXMLCone);
  } 
  
  /**
   * Method addXMLCylinder
   * 
   * @param vXMLCylinder シリンダー
   */
  public void addXMLCylinder(org.mklab.mikity.model.xml.simplexml.model.XMLCylinder vXMLCylinder) {
    this._XMLCylinderList.add(vXMLCylinder);
  } 

  /**
   * Method addXMLCylinder
   * 
   * @param index インデックス
   * @param vXMLCylinder シリンダー
   */
  public void addXMLCylinder(int index, org.mklab.mikity.model.xml.simplexml.model.XMLCylinder vXMLCylinder) {
    this._XMLCylinderList.add(index, vXMLCylinder);
  } 

  /**
   * Method addXMLSphere
   * 
   * @param vXMLSphere スフィア
   */
  public void addXMLSphere(org.mklab.mikity.model.xml.simplexml.model.XMLSphere vXMLSphere) {
    this._XMLSphereList.add(vXMLSphere);
  } 

  /**
   * Method addXMLSphere
   * 
   * @param index インデックス
   * @param vXMLSphere スフィア
   */
  public void addXMLSphere(int index, org.mklab.mikity.model.xml.simplexml.model.XMLSphere vXMLSphere) {
    this._XMLSphereList.add(index, vXMLSphere);
  } 

  /**
   * Method addXMLTrianglePolygon
   * 
   * @param vXMLTrianglePolygon 三角形のポリゴン
   */
  public void addXMLTrianglePolygon(org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon vXMLTrianglePolygon) {
    this._XMLTrianglePolygonList.add(vXMLTrianglePolygon);
  } 
  
  /**
   * Method addXMLTrianglePolygon
   * 
   * @param index インデックス
   * @param vXMLTrianglePolygon 三角形のポリゴン
   */
  public void addXMLTrianglePolygon(int index, org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon vXMLTrianglePolygon) {
    this._XMLTrianglePolygonList.add(index, vXMLTrianglePolygon);
  } 

  /**
   * Method addXMLQuadPolygon
   * 
   * @param vXMLQuadPolygon 四角形のポリゴン
   */
  public void addXMLQuadPolygon(org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon vXMLQuadPolygon) {
    this._XMLQuadPolygonList.add(vXMLQuadPolygon);
  } 

  /**
   * Method addXMLQuadPolygon
   * 
   * @param index インデックス
   * @param vXMLQuadPolygon 四角形のポリゴン
   */
  public void addXMLQuadPolygon(int index, org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon vXMLQuadPolygon) {
    this._XMLQuadPolygonList.add(index, vXMLQuadPolygon);
  } 

  /**
   * Method clearGroup
   */
  public void clearGroup() {
    this._groupList.clear();
  } 

  /**
   * Method clearLinkdata
   */
  public void clearLinkdata() {
    this._linkdataList.clear();
  } 

  /**
   * Method clearXMLBox
   */
  public void clearXMLBox() {
    this._XMLBoxList.clear();
  } 

  /**
   * Method clearXMLCone
   */
  public void clearXMLCone() {
    this._XMLConeList.clear();
  } 

  /**
   * Method clearXMLCylinder
   */
  public void clearXMLCylinder() {
    this._XMLCylinderList.clear();
  } 

  /**
   * Method clearXMLSphere
   */
  public void clearXMLSphere() {
    this._XMLSphereList.clear();
  } 

  /**
   * Method clearXMLTrianglePolygon
   */
  public void clearXMLTrianglePolygon() {
    this._XMLTrianglePolygonList.clear();
  } 

  /**
   * Method clearXMLQuadPolygon
   */
  public void clearXMLQuadPolygon() {
    this._XMLQuadPolygonList.clear();
  } 

  /**
   * Method getGroup
   * 
   * @param index インデックス
   * @return _groupList.get(index)
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
    result = prime * result + ((this._XMLBoxList == null) ? 0 : this._XMLBoxList.hashCode());
    result = prime * result + ((this._XMLConeList == null) ? 0 : this._XMLConeList.hashCode());
    result = prime * result + ((this._XMLCylinderList == null) ? 0 : this._XMLCylinderList.hashCode());
    result = prime * result + ((this._XMLQuadPolygonList == null) ? 0 : this._XMLQuadPolygonList.hashCode());
    result = prime * result + ((this._XMLSphereList == null) ? 0 : this._XMLSphereList.hashCode());
    result = prime * result + ((this._XMLTrianglePolygonList == null) ? 0 : this._XMLTrianglePolygonList.hashCode());
    result = prime * result + ((this._groupList == null) ? 0 : this._groupList.hashCode());
    result = prime * result + ((this._linkdataList == null) ? 0 : this._linkdataList.hashCode());
    result = prime * result + ((this._location == null) ? 0 : this._location.hashCode());
    result = prime * result + ((this._name == null) ? 0 : this._name.hashCode());
    result = prime * result + ((this._rotation == null) ? 0 : this._rotation.hashCode());
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
    Group other = (Group)obj;
    if (this._XMLBoxList == null) {
      if (other._XMLBoxList != null) {
        return false;
      }
    } else if (!this._XMLBoxList.equals(other._XMLBoxList)) {
      return false;
    }
    if (this._XMLConeList == null) {
      if (other._XMLConeList != null) {
        return false;
      }
    } else if (!this._XMLConeList.equals(other._XMLConeList)) {
      return false;
    }
    if (this._XMLCylinderList == null) {
      if (other._XMLCylinderList != null) {
        return false;
      }
    } else if (!this._XMLCylinderList.equals(other._XMLCylinderList)) {
      return false;
    }
    if (this._XMLQuadPolygonList == null) {
      if (other._XMLQuadPolygonList != null) {
        return false;
      }
    } else if (!this._XMLQuadPolygonList.equals(other._XMLQuadPolygonList)) {
      return false;
    }
    if (this._XMLSphereList == null) {
      if (other._XMLSphereList != null) {
        return false;
      }
    } else if (!this._XMLSphereList.equals(other._XMLSphereList)) {
      return false;
    }
    if (this._XMLTrianglePolygonList == null) {
      if (other._XMLTrianglePolygonList != null) {
        return false;
      }
    } else if (!this._XMLTrianglePolygonList.equals(other._XMLTrianglePolygonList)) {
      return false;
    }
    if (this._groupList == null) {
      if (other._groupList != null) {
        return false;
      }
    } else if (!this._groupList.equals(other._groupList)) {
      return false;
    }
    if (this._linkdataList == null) {
      if (other._linkdataList != null) {
        return false;
      }
    } else if (!this._linkdataList.equals(other._linkdataList)) {
      return false;
    }
    if (this._location == null) {
      if (other._location != null) {
        return false;
      }
    } else if (!this._location.equals(other._location)) {
      return false;
    }
    if (this._name == null) {
      if (other._name != null) {
        return false;
      }
    } else if (!this._name.equals(other._name)) {
      return false;
    }
    if (this._rotation == null) {
      if (other._rotation != null) {
        return false;
      }
    } else if (!this._rotation.equals(other._rotation)) {
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
    final int size = this._groupList.size();
    final org.mklab.mikity.model.xml.simplexml.model.Group[] groups = new org.mklab.mikity.model.xml.simplexml.model.Group[size];
    for (int i = 0; i < size; i++) {
      groups[i] = this._groupList.get(i);
    }
    return groups;
  } 

  /**
   * Method getGroupAsReferenceReturns a reference to 'group'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<Group> getGroupAsReference() {
    return this._groupList;
  } 

  /**
   * Method getGroupCount
   * 
   * @return _groupList.size()
   */
  public int getGroupCount() {
    return this._groupList.size();
  } 

  /**
   * Method getLinkdata
   * 
   * @param index インデックス
   * @return _linkdataList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.LinkData getLinkData(int index) {
    if ((index < 0) || (index > this._linkdataList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._linkdataList.get(index);
  } 

  /**
   * Method getLinkdata
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.LinkData[] getLinkData() {
    final int size = this._linkdataList.size();
    final org.mklab.mikity.model.xml.simplexml.model.LinkData[] linkDatas = new org.mklab.mikity.model.xml.simplexml.model.LinkData[size];
    for (int i = 0; i < size; i++) {
      linkDatas[i] = this._linkdataList.get(i);
    }
    return linkDatas;
  } 

  /**
   * Method getLinkdataAsReferenceReturns a reference to 'linkdata'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<LinkData> getLinkDataAsReference() {
    return this._linkdataList;
  } 

  /**
   * Method getLinkdataCount
   * 
   * @return _linkdataList.size()
   */
  public int getLinkDataCount() {
    return this._linkdataList.size();
  } 

  /**
   * Returns the value of field 'location'.
   * 
   * @return the value of field 'location'.
   */
  public org.mklab.mikity.model.xml.simplexml.model.Location getLocation() {
    return this._location;
  } 

  /**
   * Returns the value of field 'name'.
   * 
   * @return the value of field 'name'.
   */
  public String loadName() {
    return this._name;
  } 

  /**
   * Returns the value of field 'rotation'.
   * 
   * @return the value of field 'rotation'.
   */
  public org.mklab.mikity.model.xml.simplexml.model.Rotation getRotation() {
    return this._rotation;
  } 

  /**
   * Method getXMLBox
   * 
   * @param index インデックス
   * @return _XMLBoxList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLBox getXMLBox(int index) {
    if ((index < 0) || (index > this._XMLBoxList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLBoxList.get(index);
  } 

  /**
   * Method getXMLBox
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLBox[] getXMLBox() {
    final int size = this._XMLBoxList.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLBox[] boxes = new org.mklab.mikity.model.xml.simplexml.model.XMLBox[size];
    for (int i = 0; i < size; i++) {
      boxes[i] = this._XMLBoxList.get(i);
    }
    return boxes;
  } 

  /**
   * Method getXMLBoxAsReferenceReturns a reference to 'XMLBox'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<XMLBox> getXMLBoxAsReference() {
    return this._XMLBoxList;
  } 

  /**
   * Method getXMLBoxCount
   * 
   * @return _XMLBoxList.size()
   */
  public int getXMLBoxCount() {
    return this._XMLBoxList.size();
  } 

  /**
   * Method getXMLCone
   * 
   * @param index インデックス
   * @return _XMLConeList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLCone getXMLCone(int index) {
    if ((index < 0) || (index > this._XMLConeList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLConeList.get(index);
  } 

  /**
   * Method getXMLCone
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLCone[] getXMLCone() {
    final int size = this._XMLConeList.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLCone[] cones = new org.mklab.mikity.model.xml.simplexml.model.XMLCone[size];
    for (int i = 0; i < size; i++) {
      cones[i] = this._XMLConeList.get(i);
    }
    return cones;
  } 

  /**
   * Method getXMLConeAsReferenceReturns a reference to 'XMLCone'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<XMLCone> getXMLConeAsReference() {
    return this._XMLConeList;
  } 

  /**
   * Method getXMLConeCount
   * 
   * @return _XMLConeList.size()
   */
  public int getXMLConeCount() {
    return this._XMLConeList.size();
  } 

  /**
   * Method getXMLCylinder
   * 
   * @param index インデックス
   * @return _XMLCylinderList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLCylinder getXMLCylinder(int index) {
    if ((index < 0) || (index > this._XMLCylinderList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLCylinderList.get(index);
  } 

  /**
   * Method getXMLCylinder
   * 
   * @return _XMLCylinderList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLCylinder[] getXMLCylinder() {
    final int size = this._XMLCylinderList.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLCylinder[] cylinders = new org.mklab.mikity.model.xml.simplexml.model.XMLCylinder[size];
    for (int i = 0; i < size; i++) {
      cylinders[i] = this._XMLCylinderList.get(i);
    }
    return cylinders;
  } 

  /**
   * Method getXMLCylinderAsReferenceReturns a reference to 'XMLCylinder'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<XMLCylinder> getXMLCylinderAsReference() {
    return this._XMLCylinderList;
  } 

  /**
   * Method getXMLCylinderCount
   * 
   * @return _XMLCylinderList.size()
   */
  public int getXMLCylinderCount() {
    return this._XMLCylinderList.size();
  } 

  /**
   * Method getXMLSphere
   * 
   * @param index インデックス
   * @return _XMLSphereList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLSphere getXMLSphere(int index) {
    if ((index < 0) || (index > this._XMLSphereList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLSphereList.get(index);
  } 

  /**
   * Method getXMLSphere
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLSphere[] getXMLSphere() {
    final int size = this._XMLSphereList.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLSphere[] spheres = new org.mklab.mikity.model.xml.simplexml.model.XMLSphere[size];
    for (int i = 0; i < size; i++) {
      spheres[i] = this._XMLSphereList.get(i);
    }
    return spheres;
  } 

  /**
   * Method getXMLSphereAsReferenceReturns a reference to 'XMLSphere'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<XMLSphere> getXMLSphereAsReference() {
    return this._XMLSphereList;
  } 

  /**
   * Method getXMLSphereCount
   * 
   * @return _XMLSphereList.size()
   */
  public int getXMLSphereCount() {
    return this._XMLSphereList.size();
  } 

  /**
   * Method getXMLTrianglePolygon
   * 
   * @param index インデックス
   * @return _XMLTrianglePolygonList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon getXMLTrianglePolygon(int index) {
    if ((index < 0) || (index > this._XMLTrianglePolygonList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLTrianglePolygonList.get(index);
  } 

  /**
   * Method getXMLTrianglePolygon
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon[] getXMLTrianglePolygon() {
    final int size = this._XMLTrianglePolygonList.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon[] trianglePolygons = new org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon[size];
    for (int i = 0; i < size; i++) {
      trianglePolygons[i] = this._XMLTrianglePolygonList.get(i);
    }
    return trianglePolygons;
  } 

  /**
   * Method getXMLTrianglePolygonAsReferenceReturns a reference to 'XMLTrianglePolygon'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.List<XMLTrianglePolygon> getXMLTrianglePolygonAsReference() {
    return this._XMLTrianglePolygonList;
  } 

  /**
   * Method getXMLTrianglePolygonCount
   * 
   * @return _XMLTrianglePolygonList.size()
   */
  public int getXMLTrianglePolygonCount() {
    return this._XMLTrianglePolygonList.size();
  } 

  /**
   * Method getXMLQuadPolygon
   * 
   * @param index インデックス
   * @return _XMLQuadPolygonList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon getXMLQuadPolygon(int index) {
    if ((index < 0) || (index > this._XMLQuadPolygonList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLQuadPolygonList.get(index);
  } 

  /**
   * Method getXMLQuadPolygon
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon[] getXMLQuadPolygon() {
    final int size = this._XMLQuadPolygonList.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon[] quadPolygons = new org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon[size];
    for (int i = 0; i < size; i++) {
      quadPolygons[i] = this._XMLQuadPolygonList.get(i);
    }
    return quadPolygons;
  } 

  /**
   * Method getXMLQuadPolygonAsReferenceReturns a reference to 'XMLQuadPolygon'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<XMLQuadPolygon> getXMLQuadPolygonAsReference() {
    return this._XMLQuadPolygonList;
  } 

  /**
   * Method getXMLQuadPolygonCount
   * 
   * @return _XMLQuadPolygonList.size()
   */
  public int getXMLQuadPolygonCount() {
    return this._XMLQuadPolygonList.size();
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
   * Method removeLinkdata
   * 
   * @param vLinkdata リンクデータ
   * @return removed
   */
  public boolean removeLinkdata(org.mklab.mikity.model.xml.simplexml.model.LinkData vLinkdata) {
    boolean removed = this._linkdataList.remove(vLinkdata);
    return removed;
  } 

  /**
   * Method removeXMLBox
   * 
   * @param vXMLBox ボックス
   * @return removed
   */
  public boolean removeXMLBox(org.mklab.mikity.model.xml.simplexml.model.XMLBox vXMLBox) {
    boolean removed = this._XMLBoxList.remove(vXMLBox);
    return removed;
  } 

  /**
   * Method removeXMLCone
   * 
   * @param vXMLCone コーン
   * @return removed
   */
  public boolean removeXMLCone(org.mklab.mikity.model.xml.simplexml.model.XMLCone vXMLCone) {
    boolean removed = this._XMLConeList.remove(vXMLCone);
    return removed;
  } 

  /**
   * Method removeXMLCylinder
   * 
   * @param vXMLCylinder シリンダー
   * @return removed
   */
  public boolean removeXMLCylinder(org.mklab.mikity.model.xml.simplexml.model.XMLCylinder vXMLCylinder) {
    boolean removed = this._XMLCylinderList.remove(vXMLCylinder);
    return removed;
  } 

  /**
   * Method removeXMLSphere
   * 
   * @param vXMLSphere スフィア
   * @return removed
   */
  public boolean removeXMLSphere(org.mklab.mikity.model.xml.simplexml.model.XMLSphere vXMLSphere) {
    boolean removed = this._XMLSphereList.remove(vXMLSphere);
    return removed;
  } 
  /**
   * Method removeXMLTrianglePolygon
   * 
   * @param vXMLTrianglePolygon 三角形のポリゴン
   * @return removed
   */
  public boolean removeXMLTrianglePolygon(org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon vXMLTrianglePolygon) {
    boolean removed = this._XMLTrianglePolygonList.remove(vXMLTrianglePolygon);
    return removed;
  } 

  /**
   * Method removeXMLQuadPolygon
   * 
   * @param vXMLQuadPolygon 四角形のポリゴン
   * @return removed
   */
  public boolean removeXMLQuadPolygon(org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon vXMLQuadPolygon) {
    boolean removed = this._XMLQuadPolygonList.remove(vXMLQuadPolygon);
    return removed;
  } 

  /**
   * Method setGroup
   * 
   * @param index インデックス
   * @param vGroup グループ
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
  public void setGroup(org.mklab.mikity.model.xml.simplexml.model.Group[] groupArray) {
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
  public void setGroup(List<Group> groupCollection) {
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
  public void setGroupAsReference(List<Group> groupCollection) {
    this._groupList = groupCollection;
  } 

  /**
   * Method setLinkdata
   * 
   * @param index インデックス
   * @param vLinkdata リンクデータ
   */
  public void setLinkData(int index, org.mklab.mikity.model.xml.simplexml.model.LinkData vLinkdata) {
    if ((index < 0) || (index > this._linkdataList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._linkdataList.set(index, vLinkdata);
  } 

  /**
   * Method setLinkdata
   * 
   * @param linkdataArray リンクデータの文字列
   */
  public void setLinkData(org.mklab.mikity.model.xml.simplexml.model.LinkData[] linkdataArray) {
    // -- copy array
    this._linkdataList.clear();
    for (int i = 0; i < linkdataArray.length; i++) {
      this._linkdataList.add(linkdataArray[i]);
    }
  } 
  
  /**
   * Method setLinkdataAsReferenceSets the value of 'linkdata' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param linkdataCollection the ArrayList to copy.
   */
  public void setLinkDataAsReference(List<LinkData> linkdataCollection) {
    this._linkdataList = linkdataCollection;
  } 

  /**
   * Sets the value of field 'location'.
   * 
   * @param location the value of field 'location'.
   */
  public void setLocation(org.mklab.mikity.model.xml.simplexml.model.Location location) {
    this._location = location;
  } 

  /**
   * Sets the value of field 'name'.
   * 
   * @param name the value of field 'name'.
   */
  public void setName(java.lang.String name) {
    this._name = name;
  } 

  /**
   * Sets the value of field 'rotation'.
   * 
   * @param rotation the value of field 'rotation'.
   */
  public void setRotation(org.mklab.mikity.model.xml.simplexml.model.Rotation rotation) {
    this._rotation = rotation;
  } 

  /**
   * Method setXMLBox
   * 
   * @param index インデックス
   * @param vXMLBox ボックス
   */
  public void setXMLBox(int index, org.mklab.mikity.model.xml.simplexml.model.XMLBox vXMLBox) {
    if ((index < 0) || (index > this._XMLBoxList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLBoxList.set(index, vXMLBox);
  } 

  /**
   * Method setXMLBox
   * 
   * @param XMLBoxArray ボックスの文字列
   */
  public void setXMLBox(org.mklab.mikity.model.xml.simplexml.model.XMLBox[] XMLBoxArray) {
    // -- copy array
    this._XMLBoxList.clear();
    for (int i = 0; i < XMLBoxArray.length; i++) {
      this._XMLBoxList.add(XMLBoxArray[i]);
    }
  } 

  /**
   * Method setXMLBoxAsReferenceSets the value of 'XMLBox' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLBoxCollection the ArrayList to copy.
   */
  public void setXMLBoxAsReference(List<XMLBox> XMLBoxCollection) {
    this._XMLBoxList = XMLBoxCollection;
  } 

  /**
   * Method setXMLCone
   * 
   * @param index インデックス
   * @param vXMLCone コーン
   */
  public void setXMLCone(int index, org.mklab.mikity.model.xml.simplexml.model.XMLCone vXMLCone) {
    if ((index < 0) || (index > this._XMLConeList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLConeList.set(index, vXMLCone);
  } 

  /**
   * Method setXMLCone
   * 
   * @param XMLConeArray コーンの文字列
   */
  public void setXMLCone(org.mklab.mikity.model.xml.simplexml.model.XMLCone[] XMLConeArray) {
    // -- copy array
    this._XMLConeList.clear();
    for (int i = 0; i < XMLConeArray.length; i++) {
      this._XMLConeList.add(XMLConeArray[i]);
    }
  } 

  /**
   * Method setXMLConeAsReferenceSets the value of 'XMLCone' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLConeCollection the ArrayList to copy.
   */
  public void setXMLConeAsReference(List<XMLCone> XMLConeCollection) {
    this._XMLConeList = XMLConeCollection;
  } 

  /**
   * Method setXMLCylinder
   * 
   * @param index インデックス
   * @param vXMLCylinder シリンダー
   */
  public void setXMLCylinder(int index, org.mklab.mikity.model.xml.simplexml.model.XMLCylinder vXMLCylinder) {
    if ((index < 0) || (index > this._XMLCylinderList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLCylinderList.set(index, vXMLCylinder);
  } 

  /**
   * Method setXMLCylinder
   * 
   * @param XMLCylinderArray シリンダーの文字列
   */
  public void setXMLCylinder(org.mklab.mikity.model.xml.simplexml.model.XMLCylinder[] XMLCylinderArray) {
    // -- copy array
    this._XMLCylinderList.clear();
    for (int i = 0; i < XMLCylinderArray.length; i++) {
      this._XMLCylinderList.add(XMLCylinderArray[i]);
    }
  } 

  /**
   * Method setXMLCylinderAsReferenceSets the value of 'XMLCylinder' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLCylinderCollection the ArrayList to copy.
   */
  public void setXMLCylinderAsReference(List<XMLCylinder> XMLCylinderCollection) {
    this._XMLCylinderList = XMLCylinderCollection;
  } 

  /**
   * Method setXMLSphere
   * 
   * @param index インデックス
   * @param vXMLSphere スフィア
   */
  public void setXMLSphere(int index, org.mklab.mikity.model.xml.simplexml.model.XMLSphere vXMLSphere) {
    if ((index < 0) || (index > this._XMLSphereList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLSphereList.set(index, vXMLSphere);
  } 

  /**
   * Method setXMLSphere
   * 
   * @param XMLSphereArray スフィアの文字列
   */
  public void setXMLSphere(org.mklab.mikity.model.xml.simplexml.model.XMLSphere[] XMLSphereArray) {
    // -- copy array
    this._XMLSphereList.clear();
    for (int i = 0; i < XMLSphereArray.length; i++) {
      this._XMLSphereList.add(XMLSphereArray[i]);
    }
  } 

  /**
   * Method setXMLSphereAsReferenceSets the value of 'XMLSphere' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLSphereCollection the ArrayList to copy.
   */
  public void setXMLSphereAsReference(List<XMLSphere> XMLSphereCollection) {
    this._XMLSphereList = XMLSphereCollection;
  } 

  /**
   * Method setXMLTrianglePolygon
   * 
   * @param index インデックス
   * @param vXMLTrianglePolygon 三角形のポリゴン
   */
  public void setXMLTrianglePolygon(int index, org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon vXMLTrianglePolygon) {
    if ((index < 0) || (index > this._XMLTrianglePolygonList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLTrianglePolygonList.set(index, vXMLTrianglePolygon);
  } 

  /**
   * Method setXMLTrianglePolygon
   * 
   * @param XMLTrianglePolygonArray 三角形のポリゴンの文字列
   */
  public void setXMLTrianglePolygon(org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon[] XMLTrianglePolygonArray) {
    // -- copy array
    this._XMLTrianglePolygonList.clear();
    for (int i = 0; i < XMLTrianglePolygonArray.length; i++) {
      this._XMLTrianglePolygonList.add(XMLTrianglePolygonArray[i]);
    }
  } 

  /**
   * Method setXMLTrianglePolygonAsReferenceSets the value of 'XMLTrianglePolygon' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLTrianglePolygonCollection the ArrayList to copy.
   */
  public void setXMLTrianglePolygonAsReference(List<XMLTrianglePolygon> XMLTrianglePolygonCollection) {
    this._XMLTrianglePolygonList = XMLTrianglePolygonCollection;
  } 

  /**
   * Method setXMLQuadPolygon
   * 
   * @param index インデックス
   * @param vXMLQuadPolygon 四角形のポリゴン
   */
  public void setXMLQuadPolygon(int index, org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon vXMLQuadPolygon) {
    if ((index < 0) || (index > this._XMLQuadPolygonList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLQuadPolygonList.set(index, vXMLQuadPolygon);
  } 

  /**
   * Method setXMLQuadPolygon
   * 
   * @param XMLQuadPolygonArray 四角形ポリゴンの文字列
   */
  public void setXMLQuadPolygon(org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon[] XMLQuadPolygonArray) {
    // -- copy array
    this._XMLQuadPolygonList.clear();
    for (int i = 0; i < XMLQuadPolygonArray.length; i++) {
      this._XMLQuadPolygonList.add(XMLQuadPolygonArray[i]);
    }
  } 

  /**
   * Method setXMLQuadPolygonAsReference the value of 'XMLQuadPolygon' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLQuadPolygonCollection the ArrayList to copy.
   */
  public void setXMLQuadPolygonAsReference(List<XMLQuadPolygon> XMLQuadPolygonCollection) {
    this._XMLQuadPolygonList = XMLQuadPolygonCollection;
  } 
}
