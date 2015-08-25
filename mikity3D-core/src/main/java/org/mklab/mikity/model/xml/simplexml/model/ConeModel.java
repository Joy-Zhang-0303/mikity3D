package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * Coneを表すクラスです。
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/20 02:51:56 $
 */
@Root(name="cone")
public class ConeModel extends AbstractPrimitiveModel {
  private static final long serialVersionUID = 1L;

  /** radius */
  @Attribute(name="radius")
  private float radius;

  /** height */
  @Attribute(name="height")
  private float height;

  /** division */
  @Attribute(name="division")
  private int division;

  /**
   * コンストラクター
   */
  public ConeModel() {
    this.color = new ColorModel("red"); //$NON-NLS-1$
    this.preservedAlpha = this.color.getAlpha();
  }
  
  /**
   * 新しく生成された<code>ConeModel</code>オブジェクトを初期化します。
   * @param radius 半径
   * @param height 高さ
   * @param division 分割数
   */
  public ConeModel(float radius, float height, int division) {
    this();
    this.radius = radius;
    this.height = height;
    this.division = division;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public ConeModel clone() {
    final ConeModel ans = (ConeModel)super.clone();
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
    
    ConeModel other = (ConeModel)obj;
    if (this.division != other.division) return false;
    if (Float.floatToIntBits(this.height) != Float.floatToIntBits(other.height)) return false;
    if (Float.floatToIntBits(this.radius) != Float.floatToIntBits(other.radius)) return false;
    return true;
  }

  /**
   * Returns the value of field 'div'.
   * 
   * @return the value of field 'div'.
   */
  public int getDivision() {
    return this.division;
  }

  /**
   * Returns the value of field 'height'.
   * 
   * @return the value of field 'height'.
   */
  public float getHeight() {
    return this.height;
  }

  /**
   * Returns the value of field 'r'.
   * 
   * @return the value of field 'r'.
   */
  public float getRadisu() {
    return this.radius;
  }

  /**
   * Sets the value of field 'div'.
   * 
   * @param division the value of field 'div'.
   */
  public void setDivision(int division) {
    this.division = division;
  }

  /**
   * Sets the value of field 'height'.
   * 
   * @param height the value of field 'height'.
   */
  public void setHeight(float height) {
    this.height = height;
  }

  /**
   * Sets the value of field 'r'.
   * 
   * @param radius the value of field 'r'.
   */
  public void setRadius(float radius) {
    this.radius = radius;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "cone (radius=" + this.radius + ", height=" + this.height + ", divison=" + this.division + ", color=" + this.color + ")"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
  }
}
