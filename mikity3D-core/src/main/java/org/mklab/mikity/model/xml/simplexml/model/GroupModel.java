package org.mklab.mikity.model.xml.simplexml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Persist;


/**
 * Class Group.
 * 
 * @version $Revision: 1.4 $ $Date: 2007/12/13 10:01:55 $
 */
@Root(name = "group")
public class GroupModel implements Serializable, Cloneable {

  private static final long serialVersionUID = 1L;

  /** name */
  @Attribute(name = "name")
  private String name;

  /** Translations */
  @Element(name = "translation", required = false)
  private TranslationModel translation;

  /** Rotations */
  @Element(name = "rotation", required = false)
  private RotationModel rotation;

  /** Animations */
  @ElementList(type = AnimationModel.class, inline = true, required = false)
  private List<AnimationModel> animations;

  /** Primitives */
  @ElementListUnion({
    @ElementList(entry = "box", inline = true, type = BoxModel.class),
    @ElementList(entry = "capsule", inline = true, type = CapsuleModel.class),
    @ElementList(entry = "cone", inline = true, type = ConeModel.class), 
    @ElementList(entry = "cylinder", inline = true, type = CylinderModel.class),
    @ElementList(entry = "sphere", inline = true, type = SphereModel.class),
    @ElementList(entry = "trianglePolygon", inline = true, type = TrianglePolygonModel.class), 
    @ElementList(entry = "quadPolygon", inline = true, type = QuadPolygonModel.class),
    @ElementList(entry = "null", inline = true, type = NullModel.class)})
  private List<PrimitiveModel> primitives;

  /** Groups */
  @ElementList(type = GroupModel.class, inline = true, required = false)
  private List<GroupModel> groups;
  
  /**
   * 新しく生成された<code>GroupModel</code>オブジェクトを初期化します。
   */
  public GroupModel() {
    this(""); //$NON-NLS-1$
  }

  /**
   * 新しく生成された<code>GroupModel</code>オブジェクトを初期化します。
   * 
   * @param name 名前
   */
  public GroupModel(String name) {
    this.name = name;
    this.primitives = new ArrayList<>();
    this.groups = new ArrayList<>();
    this.animations = new ArrayList<>();
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
      
      ans.primitives = new ArrayList<>();
      for (final PrimitiveModel primitive : this.primitives) {
        ans.primitives.add(primitive.createClone());
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
    this.primitives.add(primitive);
  }
  
  /**
   * シリアライゼーションの準備を行います。
   */
  @Persist
  public void prepare() {
    if (this.primitives.size() == 0) {
      this.primitives.add(NullModel.getInstance());
    }
    
    if (this.primitives.size() > 1) {
      final List<PrimitiveModel> removingPrimitive = new ArrayList<>();
      
      for (PrimitiveModel primitive : this.primitives) {
        if (primitive instanceof NullModel) {
          removingPrimitive.add(primitive);
        }
      }

      for (PrimitiveModel primitive : removingPrimitive) {
        this.primitives.remove(primitive);
      }
    }
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
    result = prime * result + ((this.primitives == null) ? 0 : this.primitives.hashCode());  
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
    if (this.primitives == null) {
      if (other.primitives != null) {
        return false;
      }
    } else if (!this.primitives.equals(other.primitives)) {
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
   * プリミティブを返します。
   * 
   * @return プリミティブ
   */
  public List<PrimitiveModel> getPrimitives() {
    return this.primitives;
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
    boolean removed = this.primitives.remove(primitive);
    return removed;
  }

  /**
   * Method removeXMLBox
   * 
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
    for (final AnimationModel animation : getAnimations()) {
      if (animation.exists()) {
        if (animationProperty.length() > 0) {
          animationProperty += ", "; //$NON-NLS-1$
        }
        animationProperty += "animation(target=" + animation.getTarget() + ", source(id=" + animation.getSource().getId() + ", number=" + animation.getSource().getNumber() + "))"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$ //$NON-NLS-4$
      }
    }
    return getName() + " (" + animationProperty + ")"; //$NON-NLS-1$//$NON-NLS-2$
  }
}
