package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * カプセルを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/27
 */
@Root(name="capsule")
public class CapsuleModel extends AbstractPrimitiveModel {
  private static final long serialVersionUID = 1L;

  /** radius */
  @Attribute(name="radius")
  protected float radius;

  /** height */
  @Attribute(name="height")
  protected float height;

  /** division */
  @Attribute(name="division")
  protected int division;

  /**
   * 新しく生成された<code>CapsuleModel</code>オブジェクトを初期化します。
   */
  public CapsuleModel() {
    // nothing to do
  }

  /**
   * 新しく生成された<code>CapsuleModel</code>オブジェクトを初期化します。
   * @param radius 半径
   * @param height 高さ
   * @param division 分割数
   */
  public CapsuleModel(float radius, float height, int division) {
    this.radius = radius;
    this.height = height;
    this.division = division;
    this.color = new ColorModel("blue"); //$NON-NLS-1$
    this.preservedAlpha = this.color.getAlpha();
  }
  
  /**
   * デフォルトのモデルを生成します。
   * 
   * @return デフォルトのモデル
   */
  public static CapsuleModel createDefault() {
    return new CapsuleModel(0.1f, 0.4f, 36);
  }
  
  
  /**
   * {@inheritDoc}
   */
  @Override
  public CapsuleModel clone() {
    final CapsuleModel ans = (CapsuleModel)super.clone();
    return ans;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + this.division;
    result = prime * result + Float.floatToIntBits(this.height);
    result = prime * result + Float.floatToIntBits(this.radius);
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
    if (super.equals(obj) == false) return false;
    
    CapsuleModel other = (CapsuleModel)obj;
    if (this.division != other.division) return false;
    if (Float.floatToIntBits(this.height) != Float.floatToIntBits(other.height)) return false;
    if (Float.floatToIntBits(this.radius) != Float.floatToIntBits(other.radius)) return false;
    return true;
  }

  /**
   * 分割数を返します。
   * 
   * @return 分割数
   */
  public int getDivision() {
    return this.division;
  }

  /**
   * 高さを返します。
   * 
   * @return 高さ
   */
  public float getHeight() {
    return this.height;
  }

  /**
   * 半径を返します。
   * 
   * @return 半径
   */
  public float getRadius() {
    return this.radius;
  }

  /**
   * 分割数を設定します。
   * 
   * @param division 分割数
   */
  public void setDivision(int division) {
    this.division = division;
  }

  /**
   * 高さを設定します。
   * 
   * @param height 高さ
   */
  public void setHeight(float height) {
    this.height = height;
  }

  /**
   * 半径を設定します。
   * 
   * @param radius 半径
   */
  public void setRadius(float radius) {
    this.radius = radius;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "capsule (radius=" + this.radius + ", height=" + this.height + ", division=" + this.division + ", color=" + this.color + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ 
  }
  
  /**
   * {@inheritDoc}
   */
  public String toShortString() {
    return "capsule"; //$NON-NLS-1$
  }
  
}
