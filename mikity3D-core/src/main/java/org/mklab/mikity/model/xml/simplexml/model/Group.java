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
  @Element(name="translation")
  private Translation translation;

  /** rotation */
  @Element(name="rotation")
  private Rotation rotation;
  
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
    this.name = ""; //$NON-NLS-1$
    this.boxes = new ArrayList<>();
    this.cylinders = new ArrayList<>();
    this.spheres = new ArrayList<>();
    this.cones = new ArrayList<>();
    this.trianglePolygons = new ArrayList<>();
    this.quadPolygons = new ArrayList<>(); 

    this.links = new ArrayList<>();
    this.groups = new ArrayList<>();
    this.translation = new Translation();
    this.rotation = new Rotation();
  } 

  /**
   * Method addGroup
   * 
   * @param group グループ
   */
  public void addGroup(Group group) {
    this.groups.add(group);
  } 

  /**
   * Method addGroup
   * 
   * @param index インデックス
   * @param group グループ
   */
  public void addGroup(int index, Group group) {
    this.groups.add(index, group);
  } 

  /**
   * Method addLinkdata
   * 
   * @param linkdata リンクデータ
   */
  public void addLinkData(LinkData linkdata) {
    this.links.add(linkdata);
  } 

  /**
   * Method addLinkdata
   * 
   * @param index インデックス
   * @param linkdata リンクデータ
   */
  public void addLinkData(int index, LinkData linkdata) {
    this.links.add(index, linkdata);
  } 

  /**
   * Method addXMLBox
   * 
   * @param box ボックス
   */
  public void addXMLBox(XMLBox box) {
    this.boxes.add(box);
  } 

  /**
   * Method addXMLBox
   * 
   * @param index インデックス
   * @param box ボックス
   */
  public void addXMLBox(int index, XMLBox box) {
    this.boxes.add(index, box);
  } 

  /**
   * Method addXMLCone
   * 
   * @param cone コーン
   */
  public void addXMLCone(XMLCone cone) {
    this.cones.add(cone);
  } 

  /**
   * Method addXMLCone
   * 
   * @param index インデックス
   * @param cone コーン
   */
  public void addXMLCone(int index, XMLCone cone) {
    this.cones.add(index, cone);
  } 
  
  /**
   * Method addXMLCylinder
   * 
   * @param cylinder シリンダー
   */
  public void addXMLCylinder(XMLCylinder cylinder) {
    this.cylinders.add(cylinder);
  } 

  /**
   * Method addXMLCylinder
   * 
   * @param index インデックス
   * @param cylinder シリンダー
   */
  public void addXMLCylinder(int index, XMLCylinder cylinder) {
    this.cylinders.add(index, cylinder);
  } 

  /**
   * Method addXMLSphere
   * 
   * @param sphere スフィア
   */
  public void addXMLSphere(XMLSphere sphere) {
    this.spheres.add(sphere);
  } 

  /**
   * Method addXMLSphere
   * 
   * @param index インデックス
   * @param sphere スフィア
   */
  public void addXMLSphere(int index, XMLSphere sphere) {
    this.spheres.add(index, sphere);
  } 

  /**
   * Method addXMLTrianglePolygon
   * 
   * @param trianglePolygon 三角形のポリゴン
   */
  public void addXMLTrianglePolygon(XMLTrianglePolygon trianglePolygon) {
    this.trianglePolygons.add(trianglePolygon);
  } 
  
  /**
   * Method addXMLTrianglePolygon
   * 
   * @param index インデックス
   * @param trianglePolygon 三角形のポリゴン
   */
  public void addXMLTrianglePolygon(int index, XMLTrianglePolygon trianglePolygon) {
    this.trianglePolygons.add(index, trianglePolygon);
  } 

  /**
   * Method addXMLQuadPolygon
   * 
   * @param quadPolygon 四角形のポリゴン
   */
  public void addXMLQuadPolygon(XMLQuadPolygon quadPolygon) {
    this.quadPolygons.add(quadPolygon);
  } 

  /**
   * Method addXMLQuadPolygon
   * 
   * @param index インデックス
   * @param quadPolygon 四角形のポリゴン
   */
  public void addXMLQuadPolygon(int index, XMLQuadPolygon quadPolygon) {
    this.quadPolygons.add(index, quadPolygon);
  } 

  /**
   * Method clearGroup
   */
  public void clearGroup() {
    this.groups.clear();
  } 

  /**
   * Clear Linkdata.
   */
  public void clearLinkData() {
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
   * @return グループ
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
    result = prime * result + ((this.boxes == null) ? 0 : this.boxes.hashCode());
    result = prime * result + ((this.cones == null) ? 0 : this.cones.hashCode());
    result = prime * result + ((this.cylinders == null) ? 0 : this.cylinders.hashCode());
    result = prime * result + ((this.quadPolygons == null) ? 0 : this.quadPolygons.hashCode());
    result = prime * result + ((this.spheres == null) ? 0 : this.spheres.hashCode());
    result = prime * result + ((this.trianglePolygons == null) ? 0 : this.trianglePolygons.hashCode());
    result = prime * result + ((this.groups == null) ? 0 : this.groups.hashCode());
    result = prime * result + ((this.links == null) ? 0 : this.links.hashCode());
    result = prime * result + ((this.translation == null) ? 0 : this.translation.hashCode());
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
    if (this.translation == null) {
      if (other.translation != null) {
        return false;
      }
    } else if (!this.translation.equals(other.translation)) {
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
  public Group[] getGroups() {
    final int size = this.groups.size();
    final Group[] localGroups = new Group[size];
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
  public List<Group> getGroupsAsReference() {
    return this.groups;
  } 

  /**
   * Method getGroupCount
   * 
   * @return _groupList.size()
   */
  public int getGroupSize() {
    return this.groups.size();
  } 

  /**
   * Method getLinkdata
   * 
   * @param index インデックス
   * @return _linkdataList.get(index)
   */
  public LinkData getLinkData(int index) {
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
  public LinkData[] getLinkData() {
    final int size = this.links.size();
    final LinkData[] linkDatas = new LinkData[size];
    for (int i = 0; i < size; i++) {
      linkDatas[i] = this.links.get(i);
    }
    return linkDatas;
  } 

//  /**
//   * Method getLinkdataAsReferenceReturns a reference to 'linkdata'. No type checking is performed on any modications to the Collection.
//   * 
//   * @return returns a reference to the Collection.
//   */
//  public List<LinkData> getLinkDataAsReference() {
//    return this.links;
//  } 

  /**
   * Method getLinkdataCount
   * 
   * @return _linkdataList.size()
   */
  public int getLinkDataSize() {
    return this.links.size();
  } 

  /**
   * Returns the value of field 'translation'.
   * 
   * @return the value of field 'translation'.
   */
  public Translation getTranslation() {
    return this.translation;
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
  public Rotation getRotation() {
    return this.rotation;
  } 

  /**
   * Method getXMLBox
   * 
   * @param index インデックス
   * @return _XMLBoxList.get(index)
   */
  public XMLBox getXMLBox(int index) {
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
  public XMLBox[] getXMLBoxes() {
    final int size = this.boxes.size();
    final XMLBox[] localBoxes = new XMLBox[size];
    for (int i = 0; i < size; i++) {
      localBoxes[i] = this.boxes.get(i);
    }
    return localBoxes;
  } 

//  /**
//   * Method getXMLBoxAsReferenceReturns a reference to 'XMLBox'. No type checking is performed on any modications to the Collection.
//   * 
//   * @return returns a reference to the Collection.
//   */
//  public List<XMLBox> getXMLBoxesAsReference() {
//    return this.boxes;
//  } 

  /**
   * Method getXMLBoxCount
   * 
   * @return _XMLBoxList.size()
   */
  public int getXMLBoxSize() {
    return this.boxes.size();
  } 

  /**
   * Method getXMLCone
   * 
   * @param index インデックス
   * @return _XMLConeList.get(index)
   */
  public XMLCone getXMLCone(int index) {
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
  public XMLCone[] getXMLCones() {
    final int size = this.cones.size();
    final XMLCone[] localCones = new XMLCone[size];
    for (int i = 0; i < size; i++) {
      localCones[i] = this.cones.get(i);
    }
    return localCones;
  } 

//  /**
//   * Method getXMLConeAsReferenceReturns a reference to 'XMLCone'. No type checking is performed on any modications to the Collection.
//   * 
//   * @return returns a reference to the Collection.
//   */
//  public List<XMLCone> getXMLConesAsReference() {
//    return this.cones;
//  } 

  /**
   * Method getXMLConeCount
   * 
   * @return _XMLConeList.size()
   */
  public int getXMLConeSize() {
    return this.cones.size();
  } 

  /**
   * Method getXMLCylinder
   * 
   * @param index インデックス
   * @return _XMLCylinderList.get(index)
   */
  public XMLCylinder getXMLCylinder(int index) {
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
  public XMLCylinder[] getXMLCylinders() {
    final int size = this.cylinders.size();
    final XMLCylinder[] localCylinders = new XMLCylinder[size];
    for (int i = 0; i < size; i++) {
      localCylinders[i] = this.cylinders.get(i);
    }
    return localCylinders;
  } 

//  /**
//   * Method getXMLCylinderAsReferenceReturns a reference to 'XMLCylinder'. No type checking is performed on any modications to the Collection.
//   * 
//   * @return returns a reference to the Collection.
//   */
//  public List<XMLCylinder> getXMLCylindersAsReference() {
//    return this.cylinders;
//  } 

  /**
   * Method getXMLCylinderCount
   * 
   * @return _XMLCylinderList.size()
   */
  public int getXMLCylinderSize() {
    return this.cylinders.size();
  } 

  /**
   * Method getXMLSphere
   * 
   * @param index インデックス
   * @return _XMLSphereList.get(index)
   */
  public XMLSphere getXMLSphere(int index) {
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
  public XMLSphere[] getXMLSpheres() {
    final int size = this.spheres.size();
    final XMLSphere[] localSpheres = new XMLSphere[size];
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
  public List<XMLSphere> getXMLSpheresAsReference() {
    return this.spheres;
  } 

  /**
   * Method getXMLSphereCount
   * 
   * @return _XMLSphereList.size()
   */
  public int getXMLSphereSize() {
    return this.spheres.size();
  } 

  /**
   * Method getXMLTrianglePolygon
   * 
   * @param index インデックス
   * @return _XMLTrianglePolygonList.get(index)
   */
  public XMLTrianglePolygon getXMLTrianglePolygon(int index) {
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
  public XMLTrianglePolygon[] getXMLTrianglePolygons() {
    final int size = this.trianglePolygons.size();
    final XMLTrianglePolygon[] localTrianglePolygons = new XMLTrianglePolygon[size];
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
  public java.util.List<XMLTrianglePolygon> getXMLTrianglePolygonsAsReference() {
    return this.trianglePolygons;
  } 

  /**
   * Method getXMLTrianglePolygonCount
   * 
   * @return _XMLTrianglePolygonList.size()
   */
  public int getXMLTrianglePolygonSize() {
    return this.trianglePolygons.size();
  } 

  /**
   * Method getXMLQuadPolygon
   * 
   * @param index インデックス
   * @return _XMLQuadPolygonList.get(index)
   */
  public XMLQuadPolygon getXMLQuadPolygon(int index) {
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
  public XMLQuadPolygon[] getXMLQuadPolygons() {
    final int size = this.quadPolygons.size();
    final XMLQuadPolygon[] localQuadPolygons = new XMLQuadPolygon[size];
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
  public List<XMLQuadPolygon> getXMLQuadPolygonsAsReference() {
    return this.quadPolygons;
  } 

  /**
   * Method getXMLQuadPolygonCount
   * 
   * @return _XMLQuadPolygonList.size()
   */
  public int getXMLQuadPolygonSize() {
    return this.quadPolygons.size();
  } 

  /**
   * Method removeGroup
   * 
   * @param group グループ
   * @return removed
   */
  public boolean removeGroup(Group group) {
    boolean removed = this.groups.remove(group);
    return removed;
  } 

  /**
   * Method removeXMLBox
   * 
   * @param box ボックス
   * @return removed
   */
  public boolean removeXMLBox(XMLBox box) {
    boolean removed = this.boxes.remove(box);
    return removed;
  } 

  /**
   * Method removeXMLCone
   * 
   * @param cone コーン
   * @return removed
   */
  public boolean removeXMLCone(XMLCone cone) {
    boolean removed = this.cones.remove(cone);
    return removed;
  } 

  /**
   * Method removeXMLCylinder
   * 
   * @param cylinder シリンダー
   * @return removed
   */
  public boolean removeXMLCylinder(XMLCylinder cylinder) {
    boolean removed = this.cylinders.remove(cylinder);
    return removed;
  } 

  /**
   * Method removeXMLSphere
   * 
   * @param sphere スフィア
   * @return removed
   */
  public boolean removeXMLSphere(XMLSphere sphere) {
    boolean removed = this.spheres.remove(sphere);
    return removed;
  } 
  /**
   * Method removeXMLTrianglePolygon
   * 
   * @param trianglePolygon 三角形のポリゴン
   * @return removed
   */
  public boolean removeXMLTrianglePolygon(XMLTrianglePolygon trianglePolygon) {
    boolean removed = this.trianglePolygons.remove(trianglePolygon);
    return removed;
  } 

  /**
   * Method removeXMLQuadPolygon
   * 
   * @param quadPolygon 四角形のポリゴン
   * @return removed
   */
  public boolean removeXMLQuadPolygon(XMLQuadPolygon quadPolygon) {
    boolean removed = this.quadPolygons.remove(quadPolygon);
    return removed;
  } 

  /**
   * Method setGroup
   * 
   * @param index インデックス
   * @param group グループ
   */
  public void setGroup(int index, Group group) {
    if ((index < 0) || (index > this.groups.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.groups.set(index, group);
  } 

  /**
   * Method setGroup
   * 
   * @param argGroups グループの文字列
   */
  public void setGroups(Group[] argGroups) {
    this.groups.clear();
    for (int i = 0; i < argGroups.length; i++) {
      this.groups.add(argGroups[i]);
    }
  } 

  /**
   * Method setGroupSets the value of 'group' by copying the given ArrayList.
   * 
   * @param argGroups the Vector to copy.
   */
  public void setGroups(List<Group> argGroups) {
    this.groups.clear();
    for (int i = 0; i < argGroups.size(); i++) {
      this.groups.add(argGroups.get(i));
    }
  } 

  /**
   * Method setLinkdata
   * 
   * @param index インデックス
   * @param link リンクデータ
   */
  public void setLinkData(int index, LinkData link) {
    if ((index < 0) || (index > this.links.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.links.set(index, link);
  } 

  /**
   * Method setLinkdata
   * 
   * @param links リンクデータの文字列
   */
  public void setLinks(LinkData[] links) {
    this.links.clear();
    for (int i = 0; i < links.length; i++) {
      this.links.add(links[i]);
    }
  } 

  /**
   * Sets the value of field 'translation'.
   * 
   * @param translation the value of field 'translation'.
   */
  public void setTranslation(Translation translation) {
    this.translation = translation;
  } 

  /**
   * 名前を設定します。
   * 
   * @param name 名前
   */
  public void setName(String name) {
    this.name = name;
  } 

  /**
   * Sets the value of field 'rotation'.
   * 
   * @param rotation the value of field 'rotation'.
   */
  public void setRotation(Rotation rotation) {
    this.rotation = rotation;
  } 

  /**
   * Method setXMLBox
   * 
   * @param index インデックス
   * @param box ボックス
   */
  public void setXMLBox(int index, XMLBox box) {
    if ((index < 0) || (index > this.boxes.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.boxes.set(index, box);
  } 

  /**
   * Method setXMLBox
   * 
   * @param argBoxes ボックスの文字列
   */
  public void setXMLBoxes(XMLBox[] argBoxes) {
    this.boxes.clear();
    for (int i = 0; i < argBoxes.length; i++) {
      this.boxes.add(argBoxes[i]);
    }
  } 

  /**
   * Method setXMLCone
   * 
   * @param index インデックス
   * @param cone コーン
   */
  public void setXMLCone(int index, XMLCone cone) {
    if ((index < 0) || (index > this.cones.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.cones.set(index, cone);
  } 

  /**
   * Method setXMLCone
   * 
   * @param argCones コーンの文字列
   */
  public void setXMLCones(XMLCone[] argCones) {
    this.cones.clear();
    for (int i = 0; i < argCones.length; i++) {
      this.cones.add(argCones[i]);
    }
  } 

  /**
   * Method setXMLCylinder
   * 
   * @param index インデックス
   * @param cylinder シリンダー
   */
  public void setXMLCylinder(int index, XMLCylinder cylinder) {
    if ((index < 0) || (index > this.cylinders.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.cylinders.set(index, cylinder);
  } 

  /**
   * Method setXMLCylinder
   * 
   * @param argCylinders シリンダーの文字列
   */
  public void setXMLCylinders(XMLCylinder[] argCylinders) {
    this.cylinders.clear();
    for (int i = 0; i < argCylinders.length; i++) {
      this.cylinders.add(argCylinders[i]);
    }
  } 

  /**
   * Method setXMLSphere
   * 
   * @param index インデックス
   * @param sphere スフィア
   */
  public void setXMLSphere(int index, XMLSphere sphere) {
    if ((index < 0) || (index > this.spheres.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.spheres.set(index, sphere);
  } 

  /**
   * Method setXMLSphere
   * 
   * @param argSpheres スフィアの文字列
   */
  public void setXMLSpheres(XMLSphere[] argSpheres) {
    this.spheres.clear();
    for (int i = 0; i < argSpheres.length; i++) {
      this.spheres.add(argSpheres[i]);
    }
  } 

  /**
   * Method setXMLTrianglePolygon
   * 
   * @param index インデックス
   * @param trianglePolygon 三角形のポリゴン
   */
  public void setXMLTrianglePolygon(int index, XMLTrianglePolygon trianglePolygon) {
    if ((index < 0) || (index > this.trianglePolygons.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.trianglePolygons.set(index, trianglePolygon);
  } 

  /**
   * Method setXMLTrianglePolygon
   * 
   * @param argTrianglePolygons 三角形のポリゴンの文字列
   */
  public void setXMLTrianglePolygons(XMLTrianglePolygon[] argTrianglePolygons) {
    this.trianglePolygons.clear();
    for (int i = 0; i < argTrianglePolygons.length; i++) {
      this.trianglePolygons.add(argTrianglePolygons[i]);
    }
  } 

  /**
   * Method setXMLQuadPolygon
   * 
   * @param index インデックス
   * @param quadPolygon 四角形のポリゴン
   */
  public void setXMLQuadPolygon(int index, XMLQuadPolygon quadPolygon) {
    if ((index < 0) || (index > this.quadPolygons.size())) {
      throw new IndexOutOfBoundsException();
    }
    this.quadPolygons.set(index, quadPolygon);
  } 

  /**
   * Method setXMLQuadPolygon
   * 
   * @param argQuadPolygons 四角形ポリゴンの文字列
   */
  public void setXMLQuadPolygons(XMLQuadPolygon[] argQuadPolygons) {
    this.quadPolygons.clear();
    for (int i = 0; i < argQuadPolygons.length; i++) {
      this.quadPolygons.add(argQuadPolygons[i]);
    }
  } 
}
