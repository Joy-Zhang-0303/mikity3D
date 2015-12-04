package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * 円柱(正多角柱)を表すクラスです。
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/20 02:51:56 $
 */
@Root(name="cylinder")
public class CylinderModel extends AbstractObjectModel {
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
   * 新しく生成された<code>CylinderModel</code>オブジェクトを初期化します。
   */
  public CylinderModel() {
    // nothing to do
  }
  
  /**
   * 新しく生成された<code>CylinderModel</code>オブジェクトを初期化します。
   * @param radius 底面の半径
   * @param height 高さ
   * @param division 分割数
   */
  public CylinderModel(float radius, float height, int division) {
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
  public static CylinderModel createDefault() {
    return new CylinderModel(0.1f, 0.1f, 36);
  }
  
  
  /**
   * {@inheritDoc}
   */
  @Override
  public CylinderModel clone() {
    final CylinderModel ans = (CylinderModel)super.clone();
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
    
    CylinderModel other = (CylinderModel)obj;
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
   * {@inheritDoc}
   */
  public float getHeight() {
    return this.height;
  }

  /**
   * 底面の半径を返します。
   * 
   * @return 底面の半径
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
   * 底面の半径を設定します。
   * 
   * @param radius 底面の半径
   */
  public void setRadius(float radius) {
    this.radius = radius;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "cylinder (radius=" + this.radius + ", height=" + this.height + ", division=" + this.division + ", color=" + this.color + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ 
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toShortString() {
    return Messages.getString("CylinderModel.0"); //$NON-NLS-1$
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

}
