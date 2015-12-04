package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * 球を表すクラスです。
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/20 02:51:56 $
 */
@Root(name="sphere")
public class SphereModel extends AbstractObjectModel {
  private static final long serialVersionUID = 1L;

  /** radius */
  @Attribute(name="radius")
  private float radius;

  /** division */
  @Attribute(name="division")
  private int division;

  /**
   * 新しく生成された<code>SphereModel</code>オブジェクトを初期化します。
   */
  public SphereModel() {
    // nothing to do
  }
  
  /**
   * 新しく生成された<code>SphereModel</code>オブジェクトを初期化します。
   * @param radius 半径
   * @param division 分割数
   */
  public SphereModel(float radius, int division) {
    this.radius = radius;
    this.division = division;
    this.color = new ColorModel("yellow"); //$NON-NLS-1$
    this.preservedAlpha = this.color.getAlpha();
  }
  
  /**
   * デフォルトのモデルを生成します。
   * 
   * @return デフォルトのモデル
   */
  public static SphereModel createDefault() {
    return new SphereModel(0.1f, 36);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public SphereModel clone() {
    final SphereModel ans = (SphereModel)super.clone();
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
    if (super.equals(obj) == false) return false;
    
    if (getClass() != obj.getClass()) return false;
    SphereModel other = (SphereModel)obj;
    if (this.division != other.division) return false;
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
    return "sphere (radius=" + this.radius + ", division=" + this.division + ", color=" + this.color + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toShortString() {
    return Messages.getString("SphereModel.0"); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  public float getWidth() {
    return 2*this.radius;
  }

  /**
   * {@inheritDoc}
   */
  public float getDepth() {
    return 2*this.radius;
  }

  /**
   * {@inheritDoc}
   */
  public float getHeight() {
    return 2*this.radius;
  }

}
