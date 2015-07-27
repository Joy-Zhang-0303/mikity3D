package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * アニメーションを表すクラスです。
 * 
 * @version $Revision: 1.3 $ $Date: 2008/02/03 04:25:09 $
 */
@Root(name="animation")
public class AnimationModel implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  /** 対象となるパラメータの名前 */
  @Attribute(name="target", required=true)
  private String target;

//  /** データの番号 */
//  @Attribute(name="number")
//  private int number;
  
  /** Source */
  @Element(name="source", required=true)
  private SourceModel source;
  
  /**
   * 新しく生成された<code>AnimationModel</code>オブジェクトを初期化します。
   */
  public AnimationModel() {
    this.target = ""; //$NON-NLS-1$
    //this.number = 0;
    this.source = new SourceModel();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public AnimationModel clone() {
    try {
      final AnimationModel ans = (AnimationModel)super.clone();
      ans.source = this.source.clone();
      return ans;
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
  }

//  /**
//   * データの番号を返します。
//   * 
//   * @return データの番号
//   */
//  public int getNumber() {
//    return this.number;
//  }

//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public int hashCode() {
//    final int prime = 31;
//    int result = 1;
//    result = prime * result + this.number;
//    result = prime * result + ((this.target == null) ? 0 : this.target.hashCode());
//    return result;
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj) {
//      return true;
//    }
//    
//    if (obj == null) {
//      return false;
//    }
//    
//    if (getClass() != obj.getClass()) {
//      return false;
//    }
//    
//    AnimationModel other = (AnimationModel)obj;
//    if (this.number != other.number) {
//      return false;
//    }
//
//    if (this.target == null) {
//      if (other.target != null) {
//        return false;
//      }
//    } else if (!this.target.equals(other.target)) {
//      return false;
//    }
//    return true;
//  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.source == null) ? 0 : this.source.hashCode());
    result = prime * result + ((this.target == null) ? 0 : this.target.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    AnimationModel other = (AnimationModel)obj;
    if (this.source == null) {
      if (other.source != null) return false;
    } else if (!this.source.equals(other.source)) return false;
    if (this.target == null) {
      if (other.target != null) return false;
    } else if (!this.target.equals(other.target)) return false;
    return true;
  }

  /**
   * 対象となるパラメータの名前を返します。
   * 
   * @return 対象となるパラメータの名前
   */
  public String getTarget() {
    return this.target;
  }

//  /**
//   * データの番号を設定します。
//   * 
//   * @param number データの番号
//   */
//  public void setNumber(int number) {
//    this.number = number;
//  }

  /**
   * 対象となるパラメータの名前を設定します。
   * 
   * @param target 対象となるパラメータの名前
   */
  public void setTarget(String target) {
    this.target = target;
  }

//  /**
//   * データの番号をもつか判別します。
//   * 
//   * @return データの番号をもつならばtrue
//   */
//  private boolean hasNumber() {
//    if (this.number != 0) {
//      return true;
//    }
//    return false;
//  }
  
  /**
   * ターゲットをもつか判別します。
   * 
   * @return ターゲットをもつならばtrue
   */
  private boolean hasTarget() {
    if (this.target.equals("translationX") || this.target.equals("translationY") || this.target.equals("translationZ") || this.target.equals("rotationX") || this.target.equals("rotationY") //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        || this.target.equals("rotationZ")) { //$NON-NLS-1$
      return true;
    }
    return false;
  }
  
  /**
   * アニメーションが存在するか判定します。
   * 
   * @return アニメーションが存在するならばtrue
   */
  public boolean exists() {
    return hasTarget() && this.source.hasNumber();
  }
  
  /**
   * ソースを返します。
   * 
   * @return ソース
   */
  public SourceModel getSource() {
    return this.source;
  }
  
  /**
   * ソースを設定します。
   * 
   * @param source ソース
   */
  public void setSource(SourceModel source) {
    this.source = source;
  }
  
}
