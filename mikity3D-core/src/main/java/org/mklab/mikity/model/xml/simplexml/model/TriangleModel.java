/*
 * $Id: XMLTrianglePolygon.java,v 1.15 2008/02/13 08:05:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
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
public class TriangleModel extends AbstractPrimitiveModel {

  /** */
  private static final long serialVersionUID = 1L;

  /** vertices */
  @ElementList(name="vertices",  type = VertexModel.class, inline = true, required = true)
  private List<VertexModel> vertices;

  /** 法線ベクトル。 */
  private Vector3 normalVector;

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
    return new TriangleModel(new VertexModel(0.0f, -0.3f, 0.0f), new VertexModel(0.0f, 0.3f, 0.0f), new VertexModel(0.0f, 0.0f, 0.3f));
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
    return "triangle"; //$NON-NLS-1$
  }

}
