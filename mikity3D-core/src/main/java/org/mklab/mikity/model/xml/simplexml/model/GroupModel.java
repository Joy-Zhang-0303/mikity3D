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
public class GroupModel implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  
  /** name */
  @Attribute(name="name")
  private String name;
  
  /** Translations */
  @Element(name="translation", required=false)
  private TranslationModel translation;

  /** Rotations */
  @Element(name="rotation", required=false)
  private RotationModel rotation;

  /** Animations */
  @ElementList(type=AnimationModel.class, inline=true, required=false)
  private List<AnimationModel> animations;
  
  /** Boxes */
  @ElementList(type=BoxModel.class, inline=true, required=false)
  private List<BoxModel> boxes;

  /** Cylinders */
  @ElementList(type=CylinderModel.class, inline=true, required=false)
  private List<CylinderModel> cylinders;

  /** Spheres */
  @ElementList(type=SphereModel.class, inline=true, required=false)
  private List<SphereModel> spheres;

  /** Cones */
  @ElementList(type=ConeModel.class, inline=true, required=false)
  private List<ConeModel> cones;

  /** TrianglePolygons */
  @ElementList(type=TrianglePolygonModel.class, inline=true,  required=false)
  private List<TrianglePolygonModel> trianglePolygons;

  /** QuadPolygons */
  @ElementList(type=QuadPolygonModel.class, inline=true, required=false)
  private List<QuadPolygonModel> quadPolygons;

  /** Groups */
  @ElementList(type=GroupModel.class, inline=true, required=false)
  private List<GroupModel> groups;

  /**
   * 新しく生成された<code>GroupModel</code>オブジェクトを初期化します。
   */
  public GroupModel() {
    this.name = ""; //$NON-NLS-1$
    this.boxes = new ArrayList<>();
    this.cylinders = new ArrayList<>();
    this.spheres = new ArrayList<>();
    this.cones = new ArrayList<>();
    this.trianglePolygons = new ArrayList<>();
    this.quadPolygons = new ArrayList<>(); 

    this.animations = new ArrayList<>();
    this.groups = new ArrayList<>();
    this.translation = new TranslationModel();
    this.rotation = new RotationModel();
  } 

  /**
   * Method addGroup
   * 
   * @param group グループ
   */
  public void add(GroupModel group) {
    this.groups.add(group);
  } 

  /**
   * Method addGroup
   * 
   * @param index インデックス
   * @param group グループ
   */
  public void add(int index, GroupModel group) {
    this.groups.add(index, group);
  } 

  /**
   * Method addLinkdata
   * 
   * @param animation リンクデータ
   */
  public void add(AnimationModel animation) {
    this.animations.add(animation);
  } 

  /**
   * Method addLinkdata
   * 
   * @param index インデックス
   * @param animation リンクデータ
   */
  public void add(int index, AnimationModel animation) {
    this.animations.add(index, animation);
  } 

  /**
   * Method addXMLBox
   * 
   * @param box ボックス
   */
  public void add(BoxModel box) {
    this.boxes.add(box);
  } 

  /**
   * Method addXMLBox
   * 
   * @param index インデックス
   * @param box ボックス
   */
  public void add(int index, BoxModel box) {
    this.boxes.add(index, box);
  } 

  /**
   * Method addXMLCone
   * 
   * @param cone コーン
   */
  public void add(ConeModel cone) {
    this.cones.add(cone);
  } 

  /**
   * Method addXMLCone
   * 
   * @param index インデックス
   * @param cone コーン
   */
  public void add(int index, ConeModel cone) {
    this.cones.add(index, cone);
  } 
  
  /**
   * Method addXMLCylinder
   * 
   * @param cylinder シリンダー
   */
  public void add(CylinderModel cylinder) {
    this.cylinders.add(cylinder);
  } 

  /**
   * Method addXMLCylinder
   * 
   * @param index インデックス
   * @param cylinder シリンダー
   */
  public void add(int index, CylinderModel cylinder) {
    this.cylinders.add(index, cylinder);
  } 

  /**
   * Method addXMLSphere
   * 
   * @param sphere スフィア
   */
  public void add(SphereModel sphere) {
    this.spheres.add(sphere);
  } 

  /**
   * Method addXMLSphere
   * 
   * @param index インデックス
   * @param sphere スフィア
   */
  public void add(int index, SphereModel sphere) {
    this.spheres.add(index, sphere);
  } 

  /**
   * Method addXMLTrianglePolygon
   * 
   * @param trianglePolygon 三角形のポリゴン
   */
  public void add(TrianglePolygonModel trianglePolygon) {
    this.trianglePolygons.add(trianglePolygon);
  } 
  
  /**
   * Method addXMLTrianglePolygon
   * 
   * @param index インデックス
   * @param trianglePolygon 三角形のポリゴン
   */
  public void add(int index, TrianglePolygonModel trianglePolygon) {
    this.trianglePolygons.add(index, trianglePolygon);
  } 

  /**
   * Method addXMLQuadPolygon
   * 
   * @param quadPolygon 四角形のポリゴン
   */
  public void add(QuadPolygonModel quadPolygon) {
    this.quadPolygons.add(quadPolygon);
  } 

  /**
   * Method addXMLQuadPolygon
   * 
   * @param index インデックス
   * @param quadPolygon 四角形のポリゴン
   */
  public void add(int index, QuadPolygonModel quadPolygon) {
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
  public void clearAnimation() {
    this.animations.clear();
  } 

  /**
   * Method clearXMLBox
   */
  public void clearBox() {
    this.boxes.clear();
  } 

  /**
   * Method clearXMLCone
   */
  public void clearCone() {
    this.cones.clear();
  } 

  /**
   * Method clearXMLCylinder
   */
  public void clearCylinder() {
    this.cylinders.clear();
  } 

  /**
   * Method clearXMLSphere
   */
  public void clearSphere() {
    this.spheres.clear();
  } 

  /**
   * Method clearXMLTrianglePolygon
   */
  public void clearTrianglePolygon() {
    this.trianglePolygons.clear();
  } 

  /**
   * Method clearXMLQuadPolygon
   */
  public void clearQuadPolygon() {
    this.quadPolygons.clear();
  } 

  /**
   * Method getGroup
   * 
   * @param index インデックス
   * @return グループ
   */
  public GroupModel getGroup(int index) {
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
    result = prime * result + ((this.animations == null) ? 0 : this.animations.hashCode());
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
    GroupModel other = (GroupModel)obj;
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
    if (this.animations == null) {
      if (other.animations != null) {
        return false;
      }
    } else if (!this.animations.equals(other.animations)) {
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
  public GroupModel[] getGroups() {
    final int size = this.groups.size();
    final GroupModel[] localGroups = new GroupModel[size];
    for (int i = 0; i < size; i++) {
      localGroups[i] = this.groups.get(i);
    }
    return localGroups;
  } 

  /**
   * Method getLinkdata
   * 
   * @param index インデックス
   * @return _linkdataList.get(index)
   */
  public AnimationModel getAnimation(int index) {
    if ((index < 0) || (index > this.animations.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.animations.get(index);
  } 

  /**
   * Method getLinkdata
   * 
   * @return mArray
   */
  public AnimationModel[] getAnimations() {
    final int size = this.animations.size();
    final AnimationModel[] linkDatas = new AnimationModel[size];
    for (int i = 0; i < size; i++) {
      linkDatas[i] = this.animations.get(i);
    }
    return linkDatas;
  } 

  /**
   * Returns the value of field 'translation'.
   * 
   * @return the value of field 'translation'.
   */
  public TranslationModel getTranslation() {
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
  public RotationModel getRotation() {
    return this.rotation;
  } 

  /**
   * Method getXMLBox
   * 
   * @param index インデックス
   * @return _XMLBoxList.get(index)
   */
  public BoxModel getBox(int index) {
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
  public BoxModel[] getBoxes() {
    final int size = this.boxes.size();
    final BoxModel[] localBoxes = new BoxModel[size];
    for (int i = 0; i < size; i++) {
      localBoxes[i] = this.boxes.get(i);
    }
    return localBoxes;
  } 

  /**
   * Method getXMLCone
   * 
   * @param index インデックス
   * @return _XMLConeList.get(index)
   */
  public ConeModel getCone(int index) {
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
  public ConeModel[] getCones() {
    final int size = this.cones.size();
    final ConeModel[] localCones = new ConeModel[size];
    for (int i = 0; i < size; i++) {
      localCones[i] = this.cones.get(i);
    }
    return localCones;
  } 

  /**
   * Method getXMLCylinder
   * 
   * @param index インデックス
   * @return _XMLCylinderList.get(index)
   */
  public CylinderModel getCylinder(int index) {
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
  public CylinderModel[] getCylinders() {
    final int size = this.cylinders.size();
    final CylinderModel[] localCylinders = new CylinderModel[size];
    for (int i = 0; i < size; i++) {
      localCylinders[i] = this.cylinders.get(i);
    }
    return localCylinders;
  } 

  /**
   * Method getXMLSphere
   * 
   * @param index インデックス
   * @return _XMLSphereList.get(index)
   */
  public SphereModel getSphere(int index) {
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
  public SphereModel[] getSpheres() {
    final int size = this.spheres.size();
    final SphereModel[] localSpheres = new SphereModel[size];
    for (int i = 0; i < size; i++) {
      localSpheres[i] = this.spheres.get(i);
    }
    return localSpheres;
  } 

  /**
   * Method getXMLTrianglePolygon
   * 
   * @param index インデックス
   * @return _XMLTrianglePolygonList.get(index)
   */
  public TrianglePolygonModel getTrianglePolygon(int index) {
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
  public TrianglePolygonModel[] getTrianglePolygons() {
    final int size = this.trianglePolygons.size();
    final TrianglePolygonModel[] localPolygons = new TrianglePolygonModel[size];
    for (int i = 0; i < size; i++) {
      localPolygons[i] = this.trianglePolygons.get(i);
    }
    return localPolygons;
  } 

  /**
   * Method getXMLQuadPolygon
   * 
   * @param index インデックス
   * @return _XMLQuadPolygonList.get(index)
   */
  public QuadPolygonModel getQuadPolygon(int index) {
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
  public QuadPolygonModel[] getQuadPolygons() {
    final int size = this.quadPolygons.size();
    final QuadPolygonModel[] localQuadPolygons = new QuadPolygonModel[size];
    for (int i = 0; i < size; i++) {
      localQuadPolygons[i] = this.quadPolygons.get(i);
    }
    return localQuadPolygons;
  } 

  /**
   * Method removeGroup
   * 
   * @param group グループ
   * @return removed
   */
  public boolean remove(GroupModel group) {
    boolean removed = this.groups.remove(group);
    return removed;
  } 

  /**
   * Method removeXMLBox
   * 
   * @param box ボックス
   * @return removed
   */
  public boolean remove(BoxModel box) {
    boolean removed = this.boxes.remove(box);
    return removed;
  } 

  /**
   * Method removeXMLCone
   * 
   * @param cone コーン
   * @return removed
   */
  public boolean remove(ConeModel cone) {
    boolean removed = this.cones.remove(cone);
    return removed;
  } 

  /**
   * Method removeXMLCylinder
   * 
   * @param cylinder シリンダー
   * @return removed
   */
  public boolean remove(CylinderModel cylinder) {
    boolean removed = this.cylinders.remove(cylinder);
    return removed;
  } 

  /**
   * Method removeXMLSphere
   * 
   * @param sphere スフィア
   * @return removed
   */
  public boolean remove(SphereModel sphere) {
    boolean removed = this.spheres.remove(sphere);
    return removed;
  } 
  /**
   * Method removeXMLTrianglePolygon
   * 
   * @param trianglePolygon 三角形のポリゴン
   * @return removed
   */
  public boolean remove(TrianglePolygonModel trianglePolygon) {
    boolean removed = this.trianglePolygons.remove(trianglePolygon);
    return removed;
  } 

  /**
   * Method removeXMLQuadPolygon
   * 
   * @param quadPolygon 四角形のポリゴン
   * @return removed
   */
  public boolean remove(QuadPolygonModel quadPolygon) {
    boolean removed = this.quadPolygons.remove(quadPolygon);
    return removed;
  } 
  
  /**
   * Method removeXMLBox
   * @param animation アニメーション 
   * @return removed
   */
  public boolean remove(AnimationModel animation) {
    boolean removed = this.animations.remove(animation);
    return removed;
  } 

//  /**
//   * Method setGroup
//   * 
//   * @param index インデックス
//   * @param group グループ
//   */
//  public void setGroup(int index, GroupModel group) {
//    if ((index < 0) || (index > this.groups.size())) {
//      throw new IndexOutOfBoundsException();
//    }
//    this.groups.set(index, group);
//  } 

//  /**
//   * Method setGroup
//   * 
//   * @param argGroups グループの配列
//   */
//  public void setObjects(GroupModel[] argGroups) {
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
//  public void setGroups(List<GroupModel> argGroups) {
//    this.groups.clear();
//    for (int i = 0; i < argGroups.size(); i++) {
//      this.groups.add(argGroups.get(i));
//    }
//  } 

//  /**
//   * Method setLinkdata
//   * 
//   * @param index インデックス
//   * @param animation リンクデータ
//   */
//  public void setAnimation(int index, AnimationModel animation) {
//    if ((index < 0) || (index > this.animations.size())) {
//      throw new IndexOutOfBoundsException();
//    }
//    this.animations.set(index, animation);
//  } 

//  /**
//   * Method setLinkdata
//   * 
//   * @param animations リンクデータの配列
//   */
//  public void setAnimations(AnimationModel[] animations) {
//    this.animations.clear();
//    for (int i = 0; i < animations.length; i++) {
//      this.animations.add(animations[i]);
//    }
//  } 

  /**
   * Sets the value of field 'translation'.
   * 
   * @param translation the value of field 'translation'.
   */
  public void setTranslation(TranslationModel translation) {
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
  public void setRotation(RotationModel rotation) {
    this.rotation = rotation;
  } 

//  /**
//   * Method setXMLBox
//   * 
//   * @param index インデックス
//   * @param box ボックス
//   */
//  public void setObject(int index, BoxModel box) {
//    if ((index < 0) || (index > this.boxes.size())) {
//      throw new IndexOutOfBoundsException();
//    }
//    this.boxes.set(index, box);
//  } 
//
//  /**
//   * Method setXMLBox
//   * 
//   * @param argBoxes ボックスの配列
//   */
//  public void setObjects(BoxModel[] argBoxes) {
//    this.boxes.clear();
//    for (int i = 0; i < argBoxes.length; i++) {
//      this.boxes.add(argBoxes[i]);
//    }
//  } 

//  /**
//   * Method setXMLCone
//   * 
//   * @param index インデックス
//   * @param cone コーン
//   */
//  public void setObject(int index, ConeModel cone) {
//    if ((index < 0) || (index > this.cones.size())) {
//      throw new IndexOutOfBoundsException();
//    }
//    this.cones.set(index, cone);
//  } 

//  /**
//   * Method setXMLCone
//   * 
//   * @param argCones コーンの配列
//   */
//  public void setObjects(ConeModel[] argCones) {
//    this.cones.clear();
//    for (int i = 0; i < argCones.length; i++) {
//      this.cones.add(argCones[i]);
//    }
//  } 

//  /**
//   * Method setXMLCylinder
//   * 
//   * @param index インデックス
//   * @param cylinder シリンダー
//   */
//  public void setObject(int index, CylinderModel cylinder) {
//    if ((index < 0) || (index > this.cylinders.size())) {
//      throw new IndexOutOfBoundsException();
//    }
//    this.cylinders.set(index, cylinder);
//  } 
//
//  /**
//   * Method setXMLCylinder
//   * 
//   * @param argCylinders シリンダーの配列
//   */
//  public void setObjects(CylinderModel[] argCylinders) {
//    this.cylinders.clear();
//    for (int i = 0; i < argCylinders.length; i++) {
//      this.cylinders.add(argCylinders[i]);
//    }
//  } 

//  /**
//   * Method setXMLSphere
//   * 
//   * @param index インデックス
//   * @param sphere スフィア
//   */
//  public void setObject(int index, SphereModel sphere) {
//    if ((index < 0) || (index > this.spheres.size())) {
//      throw new IndexOutOfBoundsException();
//    }
//    this.spheres.set(index, sphere);
//  } 
//
//  /**
//   * Method setXMLSphere
//   * 
//   * @param argSpheres スフィア配列
//   */
//  public void setObjects(SphereModel[] argSpheres) {
//    this.spheres.clear();
//    for (int i = 0; i < argSpheres.length; i++) {
//      this.spheres.add(argSpheres[i]);
//    }
//  } 

//  /**
//   * Method setXMLTrianglePolygon
//   * 
//   * @param index インデックス
//   * @param trianglePolygon 三角形のポリゴン
//   */
//  public void setObject(int index, TrianglePolygonModel trianglePolygon) {
//    if ((index < 0) || (index > this.trianglePolygons.size())) {
//      throw new IndexOutOfBoundsException();
//    }
//    this.trianglePolygons.set(index, trianglePolygon);
//  } 
//
//  /**
//   * Method setXMLTrianglePolygon
//   * 
//   * @param argTrianglePolygons 三角形のポリゴンの配列
//   */
//  public void setObjects(TrianglePolygonModel[] argTrianglePolygons) {
//    this.trianglePolygons.clear();
//    for (int i = 0; i < argTrianglePolygons.length; i++) {
//      this.trianglePolygons.add(argTrianglePolygons[i]);
//    }
//  } 

//  /**
//   * Method setXMLQuadPolygon
//   * 
//   * @param index インデックス
//   * @param quadPolygon 四角形のポリゴン
//   */
//  public void setObject(int index, QuadPolygonModel quadPolygon) {
//    if ((index < 0) || (index > this.quadPolygons.size())) {
//      throw new IndexOutOfBoundsException();
//    }
//    this.quadPolygons.set(index, quadPolygon);
//  } 
//
//  /**
//   * Method setXMLQuadPolygon
//   * 
//   * @param argQuadPolygons 四角形ポリゴンの配列
//   */
//  public void setObjects(QuadPolygonModel[] argQuadPolygons) {
//    this.quadPolygons.clear();
//    for (int i = 0; i < argQuadPolygons.length; i++) {
//      this.quadPolygons.add(argQuadPolygons[i]);
//    }
//  } 
}
