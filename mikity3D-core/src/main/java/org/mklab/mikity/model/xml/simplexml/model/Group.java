package org.mklab.mikity.model.xml.simplexml.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Class Group.
 * 
 * @version $Revision: 1.4 $ $Date: 2007/12/13 10:01:55 $
 */
@Root(name="group")
public class Group implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  
  /** name */
  @Attribute(name="name")
  private String name;
  
  /** location */
  @Element(name="location")
  private org.mklab.mikity.model.xml.simplexml.model.Location location;

  /** rotation */
  @Element(name="rotation")
  private org.mklab.mikity.model.xml.simplexml.model.Rotation rotation;
  
  /** Boxes */
  @ElementList(type=XMLBox.class, inline=true, required=false)
  private List<XMLBox> boxes;

  /** Cylinders */
  @ElementList(type=XMLCylinder.class, inline=true, required=false)
  private List<XMLCylinder> cylinders;

  /** Spheres */
  @ElementList(type=XMLSphere.class, inline=true, required=false)
  private List<XMLSphere> spheres;

  /** Cones */
  @ElementList(type=XMLCone.class, inline=true, required=false)
  private List<XMLCone> cones;

  /** TrianglePolygons */
  @ElementList(type=XMLTrianglePolygon.class, inline=true,  required=false)
  private List<XMLTrianglePolygon> trianglePolygons;

  /** QuadPolygons */
  @ElementList(type=XMLQuadPolygon.class, inline=true, required=false)
  private List<XMLQuadPolygon> quadPolygons;

  /** links */
  @ElementList(type=LinkData.class, inline=true, required=false)
  private List<LinkData> links;

  /** groups */
  @ElementList(type=Group.class, inline=true, required=false)
  private List<Group> groups;

  /**
   * コンストラクター
   */
  public Group() {
    this.boxes = new ArrayList<XMLBox>();
    this.cylinders = new ArrayList<XMLCylinder>();
    this.spheres = new ArrayList<XMLSphere>();
    this.cones = new ArrayList<XMLCone>();
    this.trianglePolygons = new ArrayList<XMLTrianglePolygon>();
    this.quadPolygons = new ArrayList<XMLQuadPolygon>();

    this.links = new ArrayList<LinkData>();
    this.groups = new ArrayList<Group>();
    this.location = new Location();
    this.rotation = new Rotation();
  } 

  /**
   * Method addGroup
   * 
   * @param vGroup グループ
   */
  public void addGroup(org.mklab.mikity.model.xml.simplexml.model.Group vGroup) {
    this.groups.add(vGroup);
  } 

  /**
   * Method addGroup
   * 
   * @param index インデックス
   * @param vGroup グループ
   */
  public void addGroup(int index, org.mklab.mikity.model.xml.simplexml.model.Group vGroup) {
    this.groups.add(index, vGroup);
  } 

  /**
   * Method addLinkdata
   * 
   * @param vLinkdata リンクデータ
   */
  public void addLinkdata(org.mklab.mikity.model.xml.simplexml.model.LinkData vLinkdata) {
    this.links.add(vLinkdata);
  } 

  /**
   * Method addLinkdata
   * 
   * @param index インデックス
   * @param vLinkdata リンクデータ
   */
  public void addLinkdata(int index, org.mklab.mikity.model.xml.simplexml.model.LinkData vLinkdata) {
    this.links.add(index, vLinkdata);
  } 

  /**
   * Method addXMLBox
   * 
   * @param vXMLBox ボックス
   */
  public void addXMLBox(org.mklab.mikity.model.xml.simplexml.model.XMLBox vXMLBox) {
    this.boxes.add(vXMLBox);
  } 

  /**
   * Method addXMLBox
   * 
   * @param index インデックス
   * @param vXMLBox ボックス
   */
  public void addXMLBox(int index, org.mklab.mikity.model.xml.simplexml.model.XMLBox vXMLBox) {
    this.boxes.add(index, vXMLBox);
  } 

  /**
   * Method addXMLCone
   * 
   * @param vXMLCone コーン
   */
  public void addXMLCone(org.mklab.mikity.model.xml.simplexml.model.XMLCone vXMLCone) {
    this.cones.add(vXMLCone);
  } 

  /**
   * Method addXMLCone
   * 
   * @param index インデックス
   * @param vXMLCone コーン
   */
  public void addXMLCone(int index, org.mklab.mikity.model.xml.simplexml.model.XMLCone vXMLCone) {
    this.cones.add(index, vXMLCone);
  } 
  
  /**
   * Method addXMLCylinder
   * 
   * @param vXMLCylinder シリンダー
   */
  public void addXMLCylinder(org.mklab.mikity.model.xml.simplexml.model.XMLCylinder vXMLCylinder) {
    this.cylinders.add(vXMLCylinder);
  } 

  /**
   * Method addXMLCylinder
   * 
   * @param index インデックス
   * @param vXMLCylinder シリンダー
   */
  public void addXMLCylinder(int index, org.mklab.mikity.model.xml.simplexml.model.XMLCylinder vXMLCylinder) {
    this.cylinders.add(index, vXMLCylinder);
  } 

  /**
   * Method addXMLSphere
   * 
   * @param vXMLSphere スフィア
   */
  public void addXMLSphere(org.mklab.mikity.model.xml.simplexml.model.XMLSphere vXMLSphere) {
    this.spheres.add(vXMLSphere);
  } 

  /**
   * Method addXMLSphere
   * 
   * @param index インデックス
   * @param vXMLSphere スフィア
   */
  public void addXMLSphere(int index, org.mklab.mikity.model.xml.simplexml.model.XMLSphere vXMLSphere) {
    this.spheres.add(index, vXMLSphere);
  } 

  /**
   * Method addXMLTrianglePolygon
   * 
   * @param vXMLTrianglePolygon 三角形のポリゴン
   */
  public void addXMLTrianglePolygon(org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon vXMLTrianglePolygon) {
    this.trianglePolygons.add(vXMLTrianglePolygon);
  } 
  
  /**
   * Method addXMLTrianglePolygon
   * 
   * @param index インデックス
   * @param vXMLTrianglePolygon 三角形のポリゴン
   */
  public void addXMLTrianglePolygon(int index, org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon vXMLTrianglePolygon) {
    this.trianglePolygons.add(index, vXMLTrianglePolygon);
  } 

  /**
   * Method addXMLQuadPolygon
   * 
   * @param vXMLQuadPolygon 四角形のポリゴン
   */
  public void addXMLQuadPolygon(org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon vXMLQuadPolygon) {
    this.quadPolygons.add(vXMLQuadPolygon);
  } 

  /**
   * Method addXMLQuadPolygon
   * 
   * @param index インデックス
   * @param vXMLQuadPolygon 四角形のポリゴン
   */
  public void addXMLQuadPolygon(int index, org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon vXMLQuadPolygon) {
    this.quadPolygons.add(index, vXMLQuadPolygon);
  } 

  /**
   * Method clearGroup
   */
  public void clearGroup() {
    this.groups.clear();
  } 

  /**
   * Method clearLinkdata
   */
  public void clearLinkdata() {
    this.links.clear();
  } 

  /**
   * Method clearXMLBox
   */
  public void clearXMLBox() {
    this.boxes.clear();
  } 

  /**
   * Method clearXMLCone
   */
  public void clearXMLCone() {
    this.cones.clear();
  } 

  /**
   * Method clearXMLCylinder
   */
  public void clearXMLCylinder() {
    this.cylinders.clear();
  } 

  /**
   * Method clearXMLSphere
   */
  public void clearXMLSphere() {
    this.spheres.clear();
  } 

  /**
   * Method clearXMLTrianglePolygon
   */
  public void clearXMLTrianglePolygon() {
    this.trianglePolygons.clear();
  } 

  /**
   * Method clearXMLQuadPolygon
   */
  public void clearXMLQuadPolygon() {
    this.quadPolygons.clear();
  } 

  /**
   * Method getGroup
   * 
   * @param index インデックス
   * @return _groupList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.Group getGroup(int index) {
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
    result = prime * result + ((this.boxes == null) ? 0 : this.boxes.hashCode());
    result = prime * result + ((this.cones == null) ? 0 : this.cones.hashCode());
    result = prime * result + ((this.cylinders == null) ? 0 : this.cylinders.hashCode());
    result = prime * result + ((this.quadPolygons == null) ? 0 : this.quadPolygons.hashCode());
    result = prime * result + ((this.spheres == null) ? 0 : this.spheres.hashCode());
    result = prime * result + ((this.trianglePolygons == null) ? 0 : this.trianglePolygons.hashCode());
    result = prime * result + ((this.groups == null) ? 0 : this.groups.hashCode());
    result = prime * result + ((this.links == null) ? 0 : this.links.hashCode());
    result = prime * result + ((this.location == null) ? 0 : this.location.hashCode());
    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.rotation == null) ? 0 : this.rotation.hashCode());
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
    if (this.boxes == null) {
      if (other.boxes != null) {
        return false;
      }
    } else if (!this.boxes.equals(other.boxes)) {
      return false;
    }
    if (this.cones == null) {
      if (other.cones != null) {
        return false;
      }
    } else if (!this.cones.equals(other.cones)) {
      return false;
    }
    if (this.cylinders == null) {
      if (other.cylinders != null) {
        return false;
      }
    } else if (!this.cylinders.equals(other.cylinders)) {
      return false;
    }
    if (this.quadPolygons == null) {
      if (other.quadPolygons != null) {
        return false;
      }
    } else if (!this.quadPolygons.equals(other.quadPolygons)) {
      return false;
    }
    if (this.spheres == null) {
      if (other.spheres != null) {
        return false;
      }
    } else if (!this.spheres.equals(other.spheres)) {
      return false;
    }
    if (this.trianglePolygons == null) {
      if (other.trianglePolygons != null) {
        return false;
      }
    } else if (!this.trianglePolygons.equals(other.trianglePolygons)) {
      return false;
    }
    if (this.groups == null) {
      if (other.groups != null) {
        return false;
      }
    } else if (!this.groups.equals(other.groups)) {
      return false;
    }
    if (this.links == null) {
      if (other.links != null) {
        return false;
      }
    } else if (!this.links.equals(other.links)) {
      return false;
    }
    if (this.location == null) {
      if (other.location != null) {
        return false;
      }
    } else if (!this.location.equals(other.location)) {
      return false;
    }
    if (this.name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!this.name.equals(other.name)) {
      return false;
    }
    if (this.rotation == null) {
      if (other.rotation != null) {
        return false;
      }
    } else if (!this.rotation.equals(other.rotation)) {
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
    final int size = this.groups.size();
    final org.mklab.mikity.model.xml.simplexml.model.Group[] localGroups = new org.mklab.mikity.model.xml.simplexml.model.Group[size];
    for (int i = 0; i < size; i++) {
      localGroups[i] = this.groups.get(i);
    }
    return localGroups;
  } 

  /**
   * Method getGroupAsReferenceReturns a reference to 'group'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<Group> getGroupAsReference() {
    return this.groups;
  } 

  /**
   * Method getGroupCount
   * 
   * @return _groupList.size()
   */
  public int getGroupCount() {
    return this.groups.size();
  } 

  /**
   * Method getLinkdata
   * 
   * @param index インデックス
   * @return _linkdataList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.LinkData getLinkData(int index) {
    if ((index < 0) || (index > this.links.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.links.get(index);
  } 

  /**
   * Method getLinkdata
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.LinkData[] getLinkData() {
    final int size = this.links.size();
    final org.mklab.mikity.model.xml.simplexml.model.LinkData[] linkDatas = new org.mklab.mikity.model.xml.simplexml.model.LinkData[size];
    for (int i = 0; i < size; i++) {
      linkDatas[i] = this.links.get(i);
    }
    return linkDatas;
  } 

  /**
   * Method getLinkdataAsReferenceReturns a reference to 'linkdata'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<LinkData> getLinkDataAsReference() {
    return this.links;
  } 

  /**
   * Method getLinkdataCount
   * 
   * @return _linkdataList.size()
   */
  public int getLinkDataCount() {
    return this.links.size();
  } 

  /**
   * Returns the value of field 'location'.
   * 
   * @return the value of field 'location'.
   */
  public org.mklab.mikity.model.xml.simplexml.model.Location getLocation() {
    return this.location;
  } 

  /**
   * Returns the value of field 'name'.
   * 
   * @return the value of field 'name'.
   */
  public String getName() {
    return this.name;
  } 

  /**
   * Returns the value of field 'rotation'.
   * 
   * @return the value of field 'rotation'.
   */
  public org.mklab.mikity.model.xml.simplexml.model.Rotation getRotation() {
    return this.rotation;
  } 

  /**
   * Method getXMLBox
   * 
   * @param index インデックス
   * @return _XMLBoxList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLBox getXMLBox(int index) {
    if ((index < 0) || (index > this.boxes.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.boxes.get(index);
  } 

  /**
   * Method getXMLBox
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLBox[] getXMLBox() {
    final int size = this.boxes.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLBox[] localBoxes = new org.mklab.mikity.model.xml.simplexml.model.XMLBox[size];
    for (int i = 0; i < size; i++) {
      localBoxes[i] = this.boxes.get(i);
    }
    return localBoxes;
  } 

  /**
   * Method getXMLBoxAsReferenceReturns a reference to 'XMLBox'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<XMLBox> getXMLBoxAsReference() {
    return this.boxes;
  } 

  /**
   * Method getXMLBoxCount
   * 
   * @return _XMLBoxList.size()
   */
  public int getXMLBoxCount() {
    return this.boxes.size();
  } 

  /**
   * Method getXMLCone
   * 
   * @param index インデックス
   * @return _XMLConeList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLCone getXMLCone(int index) {
    if ((index < 0) || (index > this.cones.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.cones.get(index);
  } 

  /**
   * Method getXMLCone
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLCone[] getXMLCone() {
    final int size = this.cones.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLCone[] localCones = new org.mklab.mikity.model.xml.simplexml.model.XMLCone[size];
    for (int i = 0; i < size; i++) {
      localCones[i] = this.cones.get(i);
    }
    return localCones;
  } 

  /**
   * Method getXMLConeAsReferenceReturns a reference to 'XMLCone'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<XMLCone> getXMLConeAsReference() {
    return this.cones;
  } 

  /**
   * Method getXMLConeCount
   * 
   * @return _XMLConeList.size()
   */
  public int getXMLConeCount() {
    return this.cones.size();
  } 

  /**
   * Method getXMLCylinder
   * 
   * @param index インデックス
   * @return _XMLCylinderList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLCylinder getXMLCylinder(int index) {
    if ((index < 0) || (index > this.cylinders.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.cylinders.get(index);
  } 

  /**
   * Method getXMLCylinder
   * 
   * @return _XMLCylinderList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLCylinder[] getXMLCylinder() {
    final int size = this.cylinders.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLCylinder[] localCylinders = new org.mklab.mikity.model.xml.simplexml.model.XMLCylinder[size];
    for (int i = 0; i < size; i++) {
      localCylinders[i] = this.cylinders.get(i);
    }
    return localCylinders;
  } 

  /**
   * Method getXMLCylinderAsReferenceReturns a reference to 'XMLCylinder'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<XMLCylinder> getXMLCylinderAsReference() {
    return this.cylinders;
  } 

  /**
   * Method getXMLCylinderCount
   * 
   * @return _XMLCylinderList.size()
   */
  public int getXMLCylinderCount() {
    return this.cylinders.size();
  } 

  /**
   * Method getXMLSphere
   * 
   * @param index インデックス
   * @return _XMLSphereList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLSphere getXMLSphere(int index) {
    if ((index < 0) || (index > this.spheres.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.spheres.get(index);
  } 

  /**
   * Method getXMLSphere
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLSphere[] getXMLSphere() {
    final int size = this.spheres.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLSphere[] localSpheres = new org.mklab.mikity.model.xml.simplexml.model.XMLSphere[size];
    for (int i = 0; i < size; i++) {
      localSpheres[i] = this.spheres.get(i);
    }
    return localSpheres;
  } 

  /**
   * Method getXMLSphereAsReferenceReturns a reference to 'XMLSphere'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<XMLSphere> getXMLSphereAsReference() {
    return this.spheres;
  } 

  /**
   * Method getXMLSphereCount
   * 
   * @return _XMLSphereList.size()
   */
  public int getXMLSphereCount() {
    return this.spheres.size();
  } 

  /**
   * Method getXMLTrianglePolygon
   * 
   * @param index インデックス
   * @return _XMLTrianglePolygonList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon getXMLTrianglePolygon(int index) {
    if ((index < 0) || (index > this.trianglePolygons.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.trianglePolygons.get(index);
  } 

  /**
   * Method getXMLTrianglePolygon
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon[] getXMLTrianglePolygon() {
    final int size = this.trianglePolygons.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon[] localTrianglePolygons = new org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon[size];
    for (int i = 0; i < size; i++) {
      localTrianglePolygons[i] = this.trianglePolygons.get(i);
    }
    return localTrianglePolygons;
  } 

  /**
   * Method getXMLTrianglePolygonAsReferenceReturns a reference to 'XMLTrianglePolygon'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.List<XMLTrianglePolygon> getXMLTrianglePolygonAsReference() {
    return this.trianglePolygons;
  } 

  /**
   * Method getXMLTrianglePolygonCount
   * 
   * @return _XMLTrianglePolygonList.size()
   */
  public int getXMLTrianglePolygonCount() {
    return this.trianglePolygons.size();
  } 

  /**
   * Method getXMLQuadPolygon
   * 
   * @param index インデックス
   * @return _XMLQuadPolygonList.get(index)
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon getXMLQuadPolygon(int index) {
    if ((index < 0) || (index > this.quadPolygons.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.quadPolygons.get(index);
  } 

  /**
   * Method getXMLQuadPolygon
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon[] getXMLQuadPolygon() {
    final int size = this.quadPolygons.size();
    final org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon[] localQuadPolygons = new org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon[size];
    for (int i = 0; i < size; i++) {
      localQuadPolygons[i] = this.quadPolygons.get(i);
    }
    return localQuadPolygons;
  } 

  /**
   * Method getXMLQuadPolygonAsReferenceReturns a reference to 'XMLQuadPolygon'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<XMLQuadPolygon> getXMLQuadPolygonAsReference() {
    return this.quadPolygons;
  } 

  /**
   * Method getXMLQuadPolygonCount
   * 
   * @return _XMLQuadPolygonList.size()
   */
  public int getXMLQuadPolygonCount() {
    return this.quadPolygons.size();
  } 

  /**
   * Method removeGroup
   * 
   * @param vGroup グループ
   * @return removed
   */
  public boolean removeGroup(org.mklab.mikity.model.xml.simplexml.model.Group vGroup) {
    boolean removed = this.groups.remove(vGroup);
    return removed;
  } 

  /**
   * Method removeLinkdata
   * 
   * @param vLinkdata リンクデータ
   * @return removed
   */
  public boolean removeLinkdata(org.mklab.mikity.model.xml.simplexml.model.LinkData vLinkdata) {
    boolean removed = this.links.remove(vLinkdata);
    return removed;
  } 

  /**
   * Method removeXMLBox
   * 
   * @param vXMLBox ボックス
   * @return removed
   */
  public boolean removeXMLBox(org.mklab.mikity.model.xml.simplexml.model.XMLBox vXMLBox) {
    boolean removed = this.boxes.remove(vXMLBox);
    return removed;
  } 

  /**
   * Method removeXMLCone
   * 
   * @param vXMLCone コーン
   * @return removed
   */
  public boolean removeXMLCone(org.mklab.mikity.model.xml.simplexml.model.XMLCone vXMLCone) {
    boolean removed = this.cones.remove(vXMLCone);
    return removed;
  } 

  /**
   * Method removeXMLCylinder
   * 
   * @param vXMLCylinder シリンダー
   * @return removed
   */
  public boolean removeXMLCylinder(org.mklab.mikity.model.xml.simplexml.model.XMLCylinder vXMLCylinder) {
    boolean removed = this.cylinders.remove(vXMLCylinder);
    return removed;
  } 

  /**
   * Method removeXMLSphere
   * 
   * @param vXMLSphere スフィア
   * @return removed
   */
  public boolean removeXMLSphere(org.mklab.mikity.model.xml.simplexml.model.XMLSphere vXMLSphere) {
    boolean removed = this.spheres.remove(vXMLSphere);
    return removed;
  } 
  /**
   * Method removeXMLTrianglePolygon
   * 
   * @param vXMLTrianglePolygon 三角形のポリゴン
   * @return removed
   */
  public boolean removeXMLTrianglePolygon(org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon vXMLTrianglePolygon) {
    boolean removed = this.trianglePolygons.remove(vXMLTrianglePolygon);
    return removed;
  } 

  /**
   * Method removeXMLQuadPolygon
   * 
   * @param vXMLQuadPolygon 四角形のポリゴン
   * @return removed
   */
  public boolean removeXMLQuadPolygon(org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon vXMLQuadPolygon) {
    boolean removed = this.quadPolygons.remove(vXMLQuadPolygon);
    return removed;
  } 

  /**
   * Method setGroup
   * 
   * @param index インデックス
   * @param vGroup グループ
   */
  public void setGroup(int index, org.mklab.mikity.model.xml.simplexml.model.Group vGroup) {
    if ((index < 0) || (index > this.groups.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.groups.set(index, vGroup);
  } 

  /**
   * Method setGroup
   * 
   * @param groupArray グループの文字列
   */
  public void setGroup(org.mklab.mikity.model.xml.simplexml.model.Group[] groupArray) {
    // -- copy array
    this.groups.clear();
    for (int i = 0; i < groupArray.length; i++) {
      this.groups.add(groupArray[i]);
    }
  } 

  /**
   * Method setGroupSets the value of 'group' by copying the given ArrayList.
   * 
   * @param groupCollection the Vector to copy.
   */
  public void setGroup(List<Group> groupCollection) {
    // -- copy collection
    this.groups.clear();
    for (int i = 0; i < groupCollection.size(); i++) {
      this.groups.add(groupCollection.get(i));
    }
  } 

  /**
   * Method setGroupAsReferenceSets the value of 'group' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param groupCollection the ArrayList to copy.
   */
  public void setGroupAsReference(List<Group> groupCollection) {
    this.groups = groupCollection;
  } 

  /**
   * Method setLinkdata
   * 
   * @param index インデックス
   * @param vLinkdata リンクデータ
   */
  public void setLinkData(int index, org.mklab.mikity.model.xml.simplexml.model.LinkData vLinkdata) {
    if ((index < 0) || (index > this.links.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.links.set(index, vLinkdata);
  } 

  /**
   * Method setLinkdata
   * 
   * @param links リンクデータの文字列
   */
  public void setLinks(org.mklab.mikity.model.xml.simplexml.model.LinkData[] links) {
    this.links.clear();
    for (int i = 0; i < links.length; i++) {
      this.links.add(links[i]);
    }
  } 
  
  /**
   * Method setLinkdataAsReferenceSets the value of 'linkdata' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param linkdataCollection the ArrayList to copy.
   */
  public void setLinkDataAsReference(List<LinkData> linkdataCollection) {
    this.links = linkdataCollection;
  } 

  /**
   * Sets the value of field 'location'.
   * 
   * @param location the value of field 'location'.
   */
  public void setLocation(org.mklab.mikity.model.xml.simplexml.model.Location location) {
    this.location = location;
  } 

  /**
   * Sets the value of field 'name'.
   * 
   * @param name the value of field 'name'.
   */
  public void setName(java.lang.String name) {
    this.name = name;
  } 

  /**
   * Sets the value of field 'rotation'.
   * 
   * @param rotation the value of field 'rotation'.
   */
  public void setRotation(org.mklab.mikity.model.xml.simplexml.model.Rotation rotation) {
    this.rotation = rotation;
  } 

  /**
   * Method setXMLBox
   * 
   * @param index インデックス
   * @param vXMLBox ボックス
   */
  public void setXMLBox(int index, org.mklab.mikity.model.xml.simplexml.model.XMLBox vXMLBox) {
    if ((index < 0) || (index > this.boxes.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.boxes.set(index, vXMLBox);
  } 

  /**
   * Method setXMLBox
   * 
   * @param XMLBoxArray ボックスの文字列
   */
  public void setXMLBox(org.mklab.mikity.model.xml.simplexml.model.XMLBox[] XMLBoxArray) {
    // -- copy array
    this.boxes.clear();
    for (int i = 0; i < XMLBoxArray.length; i++) {
      this.boxes.add(XMLBoxArray[i]);
    }
  } 

  /**
   * Method setXMLBoxAsReferenceSets the value of 'XMLBox' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLBoxCollection the ArrayList to copy.
   */
  public void setXMLBoxAsReference(List<XMLBox> XMLBoxCollection) {
    this.boxes = XMLBoxCollection;
  } 

  /**
   * Method setXMLCone
   * 
   * @param index インデックス
   * @param vXMLCone コーン
   */
  public void setXMLCone(int index, org.mklab.mikity.model.xml.simplexml.model.XMLCone vXMLCone) {
    if ((index < 0) || (index > this.cones.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.cones.set(index, vXMLCone);
  } 

  /**
   * Method setXMLCone
   * 
   * @param XMLConeArray コーンの文字列
   */
  public void setXMLCone(org.mklab.mikity.model.xml.simplexml.model.XMLCone[] XMLConeArray) {
    // -- copy array
    this.cones.clear();
    for (int i = 0; i < XMLConeArray.length; i++) {
      this.cones.add(XMLConeArray[i]);
    }
  } 

  /**
   * Method setXMLConeAsReferenceSets the value of 'XMLCone' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLConeCollection the ArrayList to copy.
   */
  public void setXMLConeAsReference(List<XMLCone> XMLConeCollection) {
    this.cones = XMLConeCollection;
  } 

  /**
   * Method setXMLCylinder
   * 
   * @param index インデックス
   * @param vXMLCylinder シリンダー
   */
  public void setXMLCylinder(int index, org.mklab.mikity.model.xml.simplexml.model.XMLCylinder vXMLCylinder) {
    if ((index < 0) || (index > this.cylinders.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.cylinders.set(index, vXMLCylinder);
  } 

  /**
   * Method setXMLCylinder
   * 
   * @param XMLCylinderArray シリンダーの文字列
   */
  public void setXMLCylinder(org.mklab.mikity.model.xml.simplexml.model.XMLCylinder[] XMLCylinderArray) {
    // -- copy array
    this.cylinders.clear();
    for (int i = 0; i < XMLCylinderArray.length; i++) {
      this.cylinders.add(XMLCylinderArray[i]);
    }
  } 

  /**
   * Method setXMLCylinderAsReferenceSets the value of 'XMLCylinder' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLCylinderCollection the ArrayList to copy.
   */
  public void setXMLCylinderAsReference(List<XMLCylinder> XMLCylinderCollection) {
    this.cylinders = XMLCylinderCollection;
  } 

  /**
   * Method setXMLSphere
   * 
   * @param index インデックス
   * @param vXMLSphere スフィア
   */
  public void setXMLSphere(int index, org.mklab.mikity.model.xml.simplexml.model.XMLSphere vXMLSphere) {
    if ((index < 0) || (index > this.spheres.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.spheres.set(index, vXMLSphere);
  } 

  /**
   * Method setXMLSphere
   * 
   * @param XMLSphereArray スフィアの文字列
   */
  public void setXMLSphere(org.mklab.mikity.model.xml.simplexml.model.XMLSphere[] XMLSphereArray) {
    // -- copy array
    this.spheres.clear();
    for (int i = 0; i < XMLSphereArray.length; i++) {
      this.spheres.add(XMLSphereArray[i]);
    }
  } 

  /**
   * Method setXMLSphereAsReferenceSets the value of 'XMLSphere' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLSphereCollection the ArrayList to copy.
   */
  public void setXMLSphereAsReference(List<XMLSphere> XMLSphereCollection) {
    this.spheres = XMLSphereCollection;
  } 

  /**
   * Method setXMLTrianglePolygon
   * 
   * @param index インデックス
   * @param vXMLTrianglePolygon 三角形のポリゴン
   */
  public void setXMLTrianglePolygon(int index, org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon vXMLTrianglePolygon) {
    if ((index < 0) || (index > this.trianglePolygons.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.trianglePolygons.set(index, vXMLTrianglePolygon);
  } 

  /**
   * Method setXMLTrianglePolygon
   * 
   * @param XMLTrianglePolygonArray 三角形のポリゴンの文字列
   */
  public void setXMLTrianglePolygon(org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon[] XMLTrianglePolygonArray) {
    // -- copy array
    this.trianglePolygons.clear();
    for (int i = 0; i < XMLTrianglePolygonArray.length; i++) {
      this.trianglePolygons.add(XMLTrianglePolygonArray[i]);
    }
  } 

  /**
   * Method setXMLTrianglePolygonAsReferenceSets the value of 'XMLTrianglePolygon' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLTrianglePolygonCollection the ArrayList to copy.
   */
  public void setXMLTrianglePolygonAsReference(List<XMLTrianglePolygon> XMLTrianglePolygonCollection) {
    this.trianglePolygons = XMLTrianglePolygonCollection;
  } 

  /**
   * Method setXMLQuadPolygon
   * 
   * @param index インデックス
   * @param vXMLQuadPolygon 四角形のポリゴン
   */
  public void setXMLQuadPolygon(int index, org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon vXMLQuadPolygon) {
    if ((index < 0) || (index > this.quadPolygons.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.quadPolygons.set(index, vXMLQuadPolygon);
  } 

  /**
   * Method setXMLQuadPolygon
   * 
   * @param XMLQuadPolygonArray 四角形ポリゴンの文字列
   */
  public void setXMLQuadPolygon(org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon[] XMLQuadPolygonArray) {
    // -- copy array
    this.quadPolygons.clear();
    for (int i = 0; i < XMLQuadPolygonArray.length; i++) {
      this.quadPolygons.add(XMLQuadPolygonArray[i]);
    }
  } 

  /**
   * Method setXMLQuadPolygonAsReference the value of 'XMLQuadPolygon' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLQuadPolygonCollection the ArrayList to copy.
   */
  public void setXMLQuadPolygonAsReference(List<XMLQuadPolygon> XMLQuadPolygonCollection) {
    this.quadPolygons = XMLQuadPolygonCollection;
  } 
}
