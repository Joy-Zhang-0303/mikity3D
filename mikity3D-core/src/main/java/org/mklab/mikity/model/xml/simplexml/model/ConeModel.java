package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * 円錐(正多角錘)を表すクラスです。
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
   * 新しく生成された<code>ConeModel</code>オブジェクトを初期化します。
   */
  public ConeModel() {
    // nothing to do
  }
  
  /**
   * 新しく生成された<code>ConeModel</code>オブジェクトを初期化します。
   * @param radius 底面の半径
   * @param height 高さ
   * @param division 分割数
   */
  public ConeModel(float radius, float height, int division) {
    this.radius = radius;
    this.height = height;
    this.division = division;
    this.color = new ColorModel("green"); //$NON-NLS-1$
    this.preservedAlpha = this.color.getAlpha();
  }
  
  /**
   * デフォルトのモデルを生成します。
   * 
   * @return デフォルトのモデル
   */
  public static ConeModel createDefault() {
    return new ConeModel(0.1f, 0.1f, 36);
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
   * 底面を半径を返します。
   * 
   * @return 底面の半径
   */
  public float getRadisu() {
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
    return "cone (radius=" + this.radius + ", height=" + this.height + ", divison=" + this.division + ", color=" + this.color + ")"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
  }
}
