package org.mklab.mikity.model.xml.simplexml.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


/**
 * 三角形の面による合成モデルを表すクラスです。
 * 
 */
@Root(name="composition")
public class CompositionModel extends AbstractObjectModel {
  /** */
  private static final long serialVersionUID = 1L;

  /** size */
  @Attribute(name="size")
  private int size = 0;
  
  /** facets */
  @ElementList(name="facets",  type = FacetModel.class, inline = true, required = true)
  private List<FacetModel> facets;

  /**
   * 新しく生成された<code>CompositionModel</code>オブジェクトを初期化します。
   */
  public CompositionModel() {
    this.facets = new ArrayList<>();
    this.color = new ColorModel("orange"); //$NON-NLS-1$
    this.preservedAlpha = this.color.getAlpha();
  }
  
  /**
   * デフォルトのモデルを生成します。
   * 
   * @return デフォルトのモデル
   */
  public static CompositionModel createDefault() {
    return new CompositionModel();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public CompositionModel clone() {
    final CompositionModel ans = (CompositionModel)super.clone();
    return ans;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Float.floatToIntBits(this.size);
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

    CompositionModel other = (CompositionModel)obj;
    if (Float.floatToIntBits(this.size) != Float.floatToIntBits(other.size)) return false;
    return true;
  }

  /**
   * 三角形の面を追加します。
   * 
   * @param facet 三角形の面
   */
  public void add(FacetModel facet) {
    this.facets.add(facet);
    this.size++;
  }
  
  /**
   * 三角形の面の数を返します。
   * 
   * @return 三角形の面の数
   */
  public int getSize() {
    return this.size;
  }
  
  /**
   * 三角形の面を返します。
   * 
   * @return 三角形の面
   */
  public List<FacetModel> getFacets() {
    return this.facets;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "composition (size=" + this.size + ", color=" + this.color + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toShortString() {
    return Messages.getString("CompositionModel.0"); //$NON-NLS-1$
  }
}
