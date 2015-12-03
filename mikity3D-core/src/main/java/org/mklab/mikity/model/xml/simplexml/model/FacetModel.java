/*
 * $Id: XMLTrianglePolygon.java,v 1.15 2008/02/13 08:05:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.util.Vector3;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;


/**
 * 三角形の面を表すクラスです。
 * 
 * @author koga
 */
@Root(name = "facet")
public class FacetModel implements Serializable, Cloneable {
  /** */
  private static final long serialVersionUID = 1L;

  /** 頂点のリスト。  */
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
   * 新しく生成された<code>FacetModel</code>オブジェクトを初期化します。
   */
  public FacetModel() {
    // nothing to do
  }

  /**
   * 新しく生成された<code>TriangleModel</code>オブジェクトを初期化します。
   * 
   * @param vertex0 頂点0
   * @param vertex1 頂点1
   * @param vertex2 頂点2
   */
  public FacetModel(VertexModel vertex0, VertexModel vertex1, VertexModel vertex2) {
    this.vertices = new ArrayList<>(3);
    setVertices(vertex0, vertex1, vertex2);

    updateMinMax();
  }
  
  /**
   * X,Y,Zの最小値と最大値を更新します。
   */
  private void updateMinMax() {
    if (this.vertices.size() == 0) {
      return;
    }
    
    this.xMin = this.xMax = this.vertices.get(0).getX();
    this.yMin = this.yMax = this.vertices.get(0).getY();
    this.zMin = this.zMax = this.vertices.get(0).getZ();

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
   * デシリアライズの後処理をします。
   */
  @Commit
  protected void buildAfterDeserialization() {
    updateMinMax();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FacetModel clone() {
    try {
      FacetModel ans;
      ans = (FacetModel)super.clone();
      ans.normalVector = this.normalVector.clone();
      ans.vertices = new ArrayList<>();
      for (final VertexModel vertex : this.vertices) {
        ans.vertices.add(vertex.clone());
      }
      
      ans.updateMinMax();
      
      return ans;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
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
    
    FacetModel other = (FacetModel)obj;
    if (this.normalVector == null) {
      if (other.normalVector != null) {
        return false;
      }
    } else if (!this.normalVector.equals(other.normalVector)) {
      return false;
    }
    if (this.vertices == null) {
      if (other.vertices != null) {
        return false;
      }
    } else if (!this.vertices.equals(other.vertices)) {
      return false;
    }
    
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
    return "facet"; //$NON-NLS-1$
  }
  
  /**
   * 拡大縮小します。
   * 
   * @param scaleX X軸方向の拡大縮小率
   * @param scaleY Y軸方向の拡大縮小率
   * @param scaleZ Z軸方向の拡大縮小率
   */
  public void scale(float scaleX, float scaleY, float scaleZ) {
    for (final VertexModel vertex : this.vertices) {
      vertex.scale(scaleX, scaleY, scaleZ);
    }
    
    updateMinMax();
  }
  
  /**
   * X軸周りに回転します。
   * 
   * @param angle 回転角度
   */
  public void rotateX(float angle) {
    for (final VertexModel vertex : this.vertices) {
      vertex.rotateX(angle);
    }
    
    updateMinMax();
  }
  
  /**
   * Y軸周りに回転します。
   * 
   * @param angle 回転角度
   */
  public void rotateY(float angle) {
    for (final VertexModel vertex : this.vertices) {
      vertex.rotateY(angle);
    }
    
    updateMinMax();
  }
  
  /**
   * Z軸周りに回転します。
   * 
   * @param angle 回転角度
   */
  public void rotateZ(float angle) {
    for (final VertexModel vertex : this.vertices) {
      vertex.rotateZ(angle);
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
    for (final VertexModel vertex : this.vertices) {
      vertex.translate(dx, dy, dz);
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
}
