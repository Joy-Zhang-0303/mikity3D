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

import org.mklab.mikity.util.Vector3;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;


/**
 * 三角形を表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2008/08/10
 */
@Root(name = "triangle")
public class TriangleModel extends AbstractObjectModel {

  /** */
  private static final long serialVersionUID = 1L;

  /** vertices */
  @ElementList(name="vertices",  type = VertexModel.class, inline = true, required = true)
  private List<VertexModel> vertices;

  /** 法線ベクトル。 */
  private Vector3 normalVector;
  
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
   * 新しく生成された<code>TriangleModel</code>オブジェクトを初期化します。
   */
  public TriangleModel() {
    // nothing to do
  }

  /**
   * 新しく生成された<code>TriangleModel</code>オブジェクトを初期化します。
   * 
   * @param vertex0 頂点0
   * @param vertex1 頂点1
   * @param vertex2 頂点2
   */
  public TriangleModel(VertexModel vertex0, VertexModel vertex1, VertexModel vertex2) {
    this.vertices = new ArrayList<>(3);
    setVertices(vertex0, vertex1, vertex2);
    setColor(new ColorModel("orange")); //$NON-NLS-1$
    this.preservedAlpha = this.color.getAlpha();
  }
  
  /**
   * デフォルトのモデルを生成します。
   * 
   * @return デフォルトのモデル
   */
  public static TriangleModel createDefault() {
    return new TriangleModel(new VertexModel(0.0f, -0.1f, 0.0f), new VertexModel(0.0f, 0.1f, 0.0f), new VertexModel(0.0f, 0.0f, 0.1f));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public TriangleModel clone() {
    final TriangleModel ans = (TriangleModel)super.clone();

    ans.normalVector = this.normalVector.clone();
    ans.vertices = new ArrayList<>();
    for (final VertexModel vertex : this.vertices) {
      ans.vertices.add(vertex.clone());
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
    result = prime * result + ((this.normalVector == null) ? 0 : this.normalVector.hashCode());
    result = prime * result + ((this.vertices == null) ? 0 : this.vertices.hashCode());
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
    
    TriangleModel other = (TriangleModel)obj;
    if (this.normalVector == null) {
      if (other.normalVector != null) return false;
    } else if (!this.normalVector.equals(other.normalVector)) return false;
    if (this.vertices == null) {
      if (other.vertices != null) return false;
    } else if (!this.vertices.equals(other.vertices)) return false;
    return true;
  }

  /**
   * 頂点を設定します。
   * 
   * @param number 頂点の番号(0-2)
   * @param x X座標
   * @param y Y座標
   * @param z Z座標
   */
  public void setVertex(int number, float x, float y, float z) {
    this.vertices.get(number).setX(x);
    this.vertices.get(number).setY(y);
    this.vertices.get(number).setZ(z);
    updateNormalVector();
    updateMinMax();
  }

  /**
   * 3個の頂点を設定します。
   * 
   * @param vertex0 頂点0
   * @param vertex1 頂点1
   * @param vertex2 頂点2
   */
  public void setVertices(VertexModel vertex0, VertexModel vertex1, VertexModel vertex2) {
    this.vertices.clear();
    this.vertices.add(vertex0);
    this.vertices.add(vertex1);
    this.vertices.add(vertex2);
    updateNormalVector();
    updateMinMax();
  }

  /**
   * 3個の頂点を返します。
   * 
   * @return 3個の頂点
   */
  public List<VertexModel> getVertices() {
    return this.vertices;
  }

  /**
   * 頂点を設定します。
   * 
   * @param vertices 頂点
   */
  public void setVertices(List<VertexModel> vertices) {
    this.vertices = vertices;
    updateNormalVector();
  }

  /**
   * デシリアライズの後処理をします。
   */
  @Override
  @Commit
  protected void buildAfterDeserialization() {
    super.buildAfterDeserialization();
    updateNormalVector();
    updateMinMax();
  }
  
  /**
   * 法線ベクトルを更新します。
   */
  public void updateNormalVector() {
    float x0 = this.vertices.get(0).getX();
    float y0 = this.vertices.get(0).getY();
    float z0 = this.vertices.get(0).getZ();

    float x1 = this.vertices.get(1).getX();
    float y1 = this.vertices.get(1).getY();
    float z1 = this.vertices.get(1).getZ();

    float x2 = this.vertices.get(2).getX();
    float y2 = this.vertices.get(2).getY();
    float z2 = this.vertices.get(2).getZ();

    final Vector3 v1 = new Vector3(x1 - x0, y1 - y0, z1 - z0);
    final Vector3 v2 = new Vector3(x2 - x0, y2 - y0, z2 - z0);
    this.normalVector = v1.cross(v2).normalize();
  }

  /**
   * 指定された頂点を返します。
   * 
   * @param number 頂点の番号(0-2)
   * @return 指定された頂点
   */
  public VertexModel getVertex(int number) {
    return this.vertices.get(number);
  }

  /**
   * 法線ベクトルを返します。
   * 
   * @return 法線ベクトル
   */
  public Vector3 getNormalVector() {
    if (this.normalVector == null) {
      updateNormalVector();
    }
    return this.normalVector;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "triangle (color=" + this.color + ")"; //$NON-NLS-1$ //$NON-NLS-2$
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toShortString() {
    return Messages.getString("TriangleModel.0"); //$NON-NLS-1$
  }
  
  /**
   * X,Y,Zの最小値と最大値を更新します。
   */
  private void updateMinMax() {
    final VertexModel vertex0 = this.vertices.get(0);
    this.xMin = vertex0.getX();
    this.xMax = vertex0.getX();
    this.yMin = vertex0.getY();
    this.yMax = vertex0.getY();
    this.zMin = vertex0.getZ();
    this.zMax = vertex0.getZ();

    updateMinMax(this.vertices.get(1));
    updateMinMax(this.vertices.get(2));
  }
  
  /**
   * X,Y,Zの最小値と最大値を更新します。
   * 
   * @param vertex 頂点
   */
  private void updateMinMax(VertexModel vertex) {
    this.xMin = Math.min(this.xMin, vertex.getX());
    this.xMax = Math.max(this.xMax, vertex.getX());
    this.yMin = Math.min(this.yMin, vertex.getY());
    this.yMax = Math.max(this.yMax, vertex.getY());
    this.zMin = Math.min(this.zMin, vertex.getZ());
    this.zMax = Math.max(this.zMax, vertex.getZ());
  }

  /**
   * {@inheritDoc}
   */
  public float getWidth() {
    return this.yMax - this.yMin;
  }

  /**
   * {@inheritDoc}
   */
  public float getDepth() {
    return this.xMax - this.xMin;
  }

  /**
   * {@inheritDoc}
   */
  public float getHeight() {
    return this.zMax - this.zMin;
  }

}
