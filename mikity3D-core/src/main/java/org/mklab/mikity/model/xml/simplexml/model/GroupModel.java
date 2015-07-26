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
   * Method addLinkdata
   * 
   * @param animation リンクデータ
   */
  public void add(AnimationModel animation) {
    this.animations.add(animation);
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
   * Method addXMLCone
   * 
   * @param cone コーン
   */
  public void add(ConeModel cone) {
    this.cones.add(cone);
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
   * Method addXMLSphere
   * 
   * @param sphere スフィア
   */
  public void add(SphereModel sphere) {
    this.spheres.add(sphere);
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
   * Method addXMLQuadPolygon
   * 
   * @param quadPolygon 四角形のポリゴン
   */
  public void add(QuadPolygonModel quadPolygon) {
    this.quadPolygons.add(quadPolygon);
  } 

//  /**
//   * Method clearGroup
//   */
//  public void clearGroup() {
//    this.groups.clear();
//  } 

  /**
   * Clear Linkdata.
   */
  public void clearAnimations() {
    this.animations.clear();
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
}
