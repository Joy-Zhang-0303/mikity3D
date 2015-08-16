package org.mklab.mikity.model.xml.simplexml.model;

import java.io.Serializable;
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
public class GroupModel implements Serializable, Cloneable {
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
    this(""); //$NON-NLS-1$
  }
  
  /**
   * 新しく生成された<code>GroupModel</code>オブジェクトを初期化します。
   * @param name 名前
   */
  public GroupModel(String name) {
    this.name = name;
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
   * {@inheritDoc}
   */
  @Override
  public GroupModel clone() {
    try {
      final GroupModel ans = (GroupModel)super.clone();
      if (this.translation != null) {
        ans.translation = this.translation.clone();
      }
      if (this.rotation != null) {
        ans.rotation = this.rotation.clone();
      }
      
      ans.boxes = new ArrayList<>();
      for (final BoxModel box : this.boxes) {
        ans.boxes.add(box.clone());
      }
      
      ans.cylinders = new ArrayList<>();
      for (final CylinderModel cylinder : this.cylinders) {
        ans.cylinders.add(cylinder.clone());
      }

      ans.spheres = new ArrayList<>();
      for (final SphereModel sphere : this.spheres) {
        ans.spheres.add(sphere.clone());
      }

      ans.cones = new ArrayList<>();
      for (final ConeModel cone : this.cones) {
        ans.cones.add(cone.clone());
      }

      ans.trianglePolygons = new ArrayList<>();
      for (final TrianglePolygonModel trianglePolygon : this.trianglePolygons) {
        ans.trianglePolygons.add(trianglePolygon.clone());
      }

      ans.quadPolygons = new ArrayList<>();
      for (final QuadPolygonModel quadPolygon : this.quadPolygons) {
        ans.quadPolygons.add(quadPolygon.clone());
      }

      ans.animations = new ArrayList<>();
      for (final AnimationModel animation : this.animations) {
        ans.animations.add(animation.clone());
      }

      ans.groups = new ArrayList<>();
      for (final GroupModel group : this.groups) {
        ans.groups.add(group.clone());
      }

      return ans;
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
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
   * プリミティブを追加します。
   * 
   * @param primitive プリミティブ
   */
  public void add(PrimitiveModel primitive) {
    if (primitive instanceof BoxModel) {
      this.boxes.add((BoxModel)primitive);
    } else if (primitive instanceof ConeModel) {
      this.cones.add((ConeModel)primitive);
    } else if (primitive instanceof CylinderModel) {
      this.cylinders.add((CylinderModel)primitive);
    } else if (primitive instanceof SphereModel) {
      this.spheres.add((SphereModel)primitive);
    } else if (primitive instanceof TrianglePolygonModel) {
      this.trianglePolygons.add((TrianglePolygonModel)primitive);
    } else if (primitive instanceof QuadPolygonModel) {
      this.quadPolygons.add((QuadPolygonModel)primitive);
    }  
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
  public List<GroupModel> getGroups() {
    return this.groups;
  } 

  /**
   * Method getLinkdata
   * 
   * @return mArray
   */
  public AnimationModel[] getAnimations() {
    final int size = this.animations.size();
    final AnimationModel[] localAnimations = new AnimationModel[size];
    for (int i = 0; i < size; i++) {
      localAnimations[i] = this.animations.get(i);
    }
    return localAnimations;
  } 
  
  /**
   * アニメーションが存在するか判定します。
   * 
   * @return アニメーションが存在すればtrue
   */
  public boolean hasAnimation() {
    for (final AnimationModel animation : this.animations) {
      if (animation != null && animation.exists()) {
        return true;
      }
    }
    
    return false;
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
  public List<BoxModel> getBoxes() {
    return this.boxes;
  } 

  /**
   * Method getXMLCone
   * 
   * @return mArray
   */
  public List<ConeModel> getCones() {
    return this.cones;
  }

  /**
   * Method getXMLCylinder
   * 
   * @return _XMLCylinderList.get(index)
   */
  public List<CylinderModel> getCylinders() {
    return this.cylinders;
  } 

  /**
   * Method getXMLSphere
   * 
   * @return mArray
   */
  public List<SphereModel> getSpheres() {
    return this.spheres;
  }

  /**
   * Method getXMLTrianglePolygon
   * 
   * @return mArray
   */
  public List<TrianglePolygonModel> getTrianglePolygons() {
    return this.trianglePolygons;
  } 

  /**
   * Method getXMLQuadPolygon
   * 
   * @return mArray
   */
  public List<QuadPolygonModel> getQuadPolygons() {
    return this.quadPolygons;
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
   * プリミティブを削除します。
   * 
   * @param primitive プリミティブ
   * @return removed
   */
  public boolean remove(PrimitiveModel primitive) {
    boolean removed = false;
    
    if (primitive instanceof BoxModel) {
      removed = this.boxes.remove(primitive);
    } else if (primitive instanceof ConeModel) {
      removed = this.cones.remove(primitive);
    } else if (primitive instanceof CylinderModel) {
      removed = this.cylinders.remove(primitive);
    } else if (primitive instanceof SphereModel) {
      removed = this.spheres.remove(primitive);
    } else if (primitive instanceof TrianglePolygonModel) {
      removed = this.trianglePolygons.remove(primitive);
    } else if (primitive instanceof QuadPolygonModel) {
      removed = this.quadPolygons.remove(primitive);
    } else {
      throw new IllegalArgumentException(primitive.getClass().toString());
    }

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
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    if (hasAnimation() == false) {
      return getName() + " (group)"; //$NON-NLS-1$
    }
    
    String animationProperty = ""; //$NON-NLS-1$
    for (final AnimationModel animation: getAnimations()) {
      if (animation.exists()) {
        if (animationProperty.length() > 0) {
          animationProperty += ", "; //$NON-NLS-1$
        }
        animationProperty += "animation(target=" +  animation.getTarget() + ", source(id=" + animation.getSource().getId() + ", number=" + animation.getSource().getNumber() + "))";   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$ //$NON-NLS-4$
      }
    }
    return getName() + " (" + animationProperty + ")";  //$NON-NLS-1$//$NON-NLS-2$
  }
}
