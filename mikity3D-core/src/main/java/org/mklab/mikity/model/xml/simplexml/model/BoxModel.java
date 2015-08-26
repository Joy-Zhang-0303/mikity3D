package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * 直方体を表すクラスです。
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/20 02:51:56 $
 */
@Root(name="box")
public class BoxModel extends AbstractPrimitiveModel {
  /** */
  private static final long serialVersionUID = 1L;

  /** width */
  @Attribute(name="width")
  private float width;

  /** height */
  @Attribute(name="height")
  private float height;

  /** depth */
  @Attribute(name="depth")
  private float depth;

  /**
   * 新しく生成された<code>BoxModel</code>オブジェクトを初期化します。
   */
  public BoxModel() {
    // nothing to do
  }
  
  /**
   * 新しく生成された<code>BoxModel</code>オブジェクトを初期化します。
   * @param width 幅
   * @param height 高さ
   * @param depth 奥行き
   */
  public BoxModel(float width, float height, float depth) {
    this.width = width;
    this.height = height;
    this.depth = depth;
    this.color = new ColorModel("red"); //$NON-NLS-1$
    this.preservedAlpha = this.color.getAlpha();
  }
  
  /**
   * デフォルトのモデルを生成します。
   * 
   * @return デフォルトのモデル
   */
  public static BoxModel createDefault() {
    return new BoxModel(0.1f, 0.1f, 0.1f);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public BoxModel clone() {
    final BoxModel ans = (BoxModel)super.clone();
    return ans;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Float.floatToIntBits(this.depth);
    result = prime * result + Float.floatToIntBits(this.height);
    result = prime * result + Float.floatToIntBits(this.width);
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

    BoxModel other = (BoxModel)obj;
    if (Float.floatToIntBits(this.depth) != Float.floatToIntBits(other.depth)) return false;
    if (Float.floatToIntBits(this.height) != Float.floatToIntBits(other.height)) return false;
    if (Float.floatToIntBits(this.width) != Float.floatToIntBits(other.width)) return false;
    return true;
  }

  /**
   * 幅を返します。
   * 
   * @return 幅
   */
  public float getWidth() {
    return this.width;
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
   * 奥行きを返します。
   * 
   * @return 奥行き
   */
  public float getDepth() {
    return this.depth;
  }

  /**
   * 幅を設定します。
   * 
   * @param width 幅
   */
  public void setWidth(float width) {
    this.width = width;
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
   * 奥行きを設定します。
   * 
   * @param depth 奥行き
   */
  public void setDepth(float depth) {
    this.depth = depth;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "box (width=" + this.width + ", height=" + this.height + ", depth=" + this.depth + ", color=" + this.color + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
  }
}
