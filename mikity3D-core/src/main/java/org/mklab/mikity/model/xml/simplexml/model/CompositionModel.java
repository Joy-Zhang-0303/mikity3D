/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.model.xml.simplexml.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;


/**
 * 三角形の面の集合による合成モデルを表すクラスです。
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
  
  /** x成分の最小値。 */
  private float xMin;
  /** x成分の最大値。 */
  private float xMax;
  /** y成分の最小値。 */
  private float yMin;
  /** y成分の最大値。 */
  private float yMax;
  /** z成分の最小値。 */
  private float zMin;
  /** z成分の最大値。 */
  private float zMax;

  /**
   * 新しく生成された<code>CompositionModel</code>オブジェクトを初期化します。
   */
  public CompositionModel() {
    this.facets = new ArrayList<>();
    this.color = new ColorModel("orange"); //$NON-NLS-1$
    this.preservedAlpha = this.color.getAlpha();
  }
  
  /**
   * デシリアライズの後処理をします。
   */
  @Override
  @Commit
  protected void buildAfterDeserialization() {
    super.buildAfterDeserialization();
    updateMinMax();
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
    ans.size = this.size;
    
    ans.facets = new ArrayList<>();
    for (final FacetModel facet : this.facets) {
      ans.facets.add(facet.clone());
    }
    
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
    result = prime * result + ((this.facets == null) ? 0 : this.facets.hashCode());
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
    if (super.equals(obj) == false) {
      return false;
    }

    CompositionModel other = (CompositionModel)obj;
    if (Float.floatToIntBits(this.size) != Float.floatToIntBits(other.size)) {
      return false;
    }
    
    if (this.facets == null) {
      if (other.facets != null) {
        return false;
      }
    } else if (!this.facets.equals(other.facets)) {
      return false;
    }
    
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

    if (this.size == 1) {
      this.xMin = facet.getXmin();
      this.xMax = facet.getXmax();
      this.yMin = facet.getYmin();
      this.yMax = facet.getYmax();
      this.zMin = facet.getZmin();
      this.zMax = facet.getZmax();
      return;
    }
    
    updateMinMax(facet);
  }
  
  /**
   * X,Y,Zの最小値と最大値を更新します。
   */
  private void updateMinMax() {
    if (this.size == 0) {
      return;
    }
    
    final FacetModel firstFacet = this.facets.get(0);
    this.xMin = firstFacet.getXmin();
    this.xMax = firstFacet.getXmax();
    this.yMin = firstFacet.getYmin();
    this.yMax = firstFacet.getYmax();
    this.zMin = firstFacet.getZmin();
    this.zMax = firstFacet.getZmax();
    
    for (final FacetModel facet : this.facets) {
      if (facet == firstFacet) {
        continue;
      }
      updateMinMax(facet);
    }
  }
  

  /**
   * X,Y,Zの最小値と最大値を更新します。
   * 
   * @param facet 三角形の面
   */
  private void updateMinMax(FacetModel facet) {
    this.xMin = Math.min(this.xMin, facet.getXmin());
    this.xMax = Math.max(this.xMax, facet.getXmax());
    this.yMin = Math.min(this.yMin, facet.getYmin());
    this.yMax = Math.max(this.yMax, facet.getYmax());
    this.zMin = Math.min(this.zMin, facet.getZmin());
    this.zMax = Math.max(this.zMax, facet.getZmax());
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
   * 三角形の面の集合を返します。
   * 
   * @return 三角形の面の集合
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
  
  /**
   * 拡大縮小します。
   * 
   * @param scaleX X軸方向の拡大縮小率
   * @param scaleY Y軸方向の拡大縮小率
   * @param scaleZ Z軸方向の拡大縮小率
   */
  public void scale(float scaleX, float scaleY, float scaleZ) {
    for (final FacetModel facet : this.facets) {
      facet.scale(scaleX, scaleY, scaleZ);
    }
    
    updateMinMax();
  }
  
  /**
   * 平行移動します。
   * 
   * @param dx x軸方向の移動量
   * @param dy y軸方向の移動量
   * @param dz z軸方向の移動量
   */
  public void translate(float dx, float dy, float dz) {
    for (final FacetModel facet : this.facets) {
      facet.translate(dx, dy, dz);
    }
    
    updateMinMax();
  }
  
  /**
   * X軸周りに回転します。
   * 
   * @param angle 回転角度
   */
  public void rotateX(float angle) {
    for (final FacetModel facet : this.facets) {
      facet.rotateX(angle);
    }
    
    updateMinMax();
  }

  /**
   * Y軸周りに回転します。
   * 
   * @param angle 回転角度
   */
  public void rotateY(float angle) {
    for (final FacetModel facet : this.facets) {
      facet.rotateY(angle);
    }
    
    updateMinMax();
  }
  
  /**
   * Z軸周りに回転します。
   * 
   * @param angle 回転角度
   */
  public void rotateZ(float angle) {
    for (final FacetModel facet : this.facets) {
      facet.rotateZ(angle);
    }
    
    updateMinMax();
  }
  
  /**
   * X成分の最小値を返します。
   * 
   * @return X成分の最小値
   */
  public float getXmin() {
    return this.xMin;
  }
  
  /**
   * X成分の最大値を返します。
   * 
   * @return X成分の最大値
   */
  public float getXmax() {
    return this.xMax;
  }
  
  /**
   * Y成分の最小値を返します。
   * 
   * @return Y成分の最小値
   */
  public float getYmin() {
    return this.yMin;
  }
  
  /**
   * Y成分の最大値を返します。
   * 
   * @return Y成分の最大値
   */
  public float getYmax() {
    return this.yMax;
  }
  
  /**
   * Z成分の最小値を返します。
   * 
   * @return Z成分の最小値
   */
  public float getZmin() {
    return this.zMin;
  }
  
  /**
   * Z成分の最大値を返します。
   * 
   * @return Z成分の最大値
   */
  public float getZmax() {
    return this.zMax;
  }
  
  /**
   * 合成モデルの囲む直方体の幅を返します。
   * 
   * @return 合成モデルの囲む直方体の幅
   */
  public float getWidth() {
    return this.yMax - this.yMin;
  }
  
  /**
   * 合成モデルの囲む直方体の奥行きを返します。
   * 
   * @return 合成モデルの囲む直方体の奥行き
   */
  public float getDepth() {
    return this.xMax - this.xMin;
  }
  
  /**
   * 合成モデルの囲む直方体の高さを返します。
   * 
   * @return 合成モデルの囲む直方体の高さ
   */
  public float getHeight() {
    return this.zMax - this.zMin;
  }
}
