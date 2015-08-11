/*
 * $Id: XMLTrianglePolygon.java,v 1.15 2008/02/13 08:05:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.model;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.util.Vector3;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


/**
 * 三角形ポリゴンを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2008/08/10
 */
@Root(name="trianglePolygon")
public class TrianglePolygonModel implements PrimitiveModel, Cloneable {
  /** vertices */
  @ElementList(type=VertexModel.class, inline=true, required=true)
  private List<VertexModel> vertices;
   
  /** color */
  @Element(name="color")
  private ColorModel color;

  /** transparent */
  @Attribute(name="transparent", required=false)
  private boolean transparent;

  /** translation */
  @Element(name="translation", required=false)
  private TranslationModel translation;
  
  /** rotation */
  @Element(name="rotation", required=false)
  private RotationModel rotation;
  
  /** 法線ベクトル。 */
  private Vector3 normalVector;

  /**
   * 新しく生成された<code>XMLTrianglePolygon</code>オブジェクトを初期化します。
   */
  public TrianglePolygonModel() {
    this.vertices = new ArrayList<>(3);
    this.color = new ColorModel("orange"); //$NON-NLS-1$
    this.transparent = false;
  }
  
  /**
   * 新しく生成された<code>TrianglePolygonModel</code>オブジェクトを初期化します。
   * @param vertex0 頂点0
   * @param vertex1 頂点1
   * @param vertex2 頂点2
   */
  public TrianglePolygonModel(VertexModel vertex0, VertexModel vertex1, VertexModel vertex2) {
    this();
    setVertices(vertex0, vertex1, vertex2);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public TrianglePolygonModel clone() {
    try {
      final TrianglePolygonModel ans = (TrianglePolygonModel)super.clone();
      if (this.translation != null) {
        ans.translation = this.translation.clone();
      }
      if (this.rotation != null) {
        ans.rotation = this.rotation.clone();
      }

      ans.color = this.color.clone();
      
      ans.normalVector = this.normalVector.clone();
      ans.vertices = new ArrayList<>();
      for (final VertexModel vertex : this.vertices) {
        ans.vertices.add(vertex.clone());
      }
      return ans;
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public PrimitiveModel createClone() {
    return clone();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.color == null) ? 0 : this.color.hashCode());
    result = prime * result + ((this.normalVector == null) ? 0 : this.normalVector.hashCode());
    result = prime * result + ((this.rotation == null) ? 0 : this.rotation.hashCode());
    result = prime * result + ((this.translation == null) ? 0 : this.translation.hashCode());
    result = prime * result + (this.transparent ? 1231 : 1237);
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
    TrianglePolygonModel other = (TrianglePolygonModel)obj;
    if (this.color == null) {
      if (other.color != null) return false;
    } else if (!this.color.equals(other.color)) return false;
    if (this.normalVector == null) {
      if (other.normalVector != null) return false;
    } else if (!this.normalVector.equals(other.normalVector)) return false;
    if (this.rotation == null) {
      if (other.rotation != null) return false;
    } else if (!this.rotation.equals(other.rotation)) return false;
    if (this.translation == null) {
      if (other.translation != null) return false;
    } else if (!this.translation.equals(other.translation)) return false;
    if (this.transparent != other.transparent) return false;
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
   * 頂点を設定します。
   * 
   * @param vertices 頂点
   */
  public void setVertices(List<VertexModel> vertices) {
    this.vertices = vertices;
    updateNormalVector();
  }

  /**
   * {@inheritDoc}
   */
  public void setColor(ColorModel color) {
    this.color = color;
  }

  /**
   * {@inheritDoc}
   */
  public void setTranslation(TranslationModel translation) {
    this.translation = translation;
  }

  /**
   * {@inheritDoc}
   */
  public void setRotation(RotationModel rotation) {
    this.rotation = rotation;
  }

  /**
   * 法線ベクトルを更新します。
   */
  private void updateNormalVector() {
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
   * {@inheritDoc}
   */
  public ColorModel getColor() {
    return this.color;
  }

  /**
   * {@inheritDoc}
   */
  public TranslationModel getTranslation() {
    return this.translation;
  }
  
  /**
   * {@inheritDoc}
   */
  public RotationModel getRotation() {
    return this.rotation;
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
  public void setTransparent(boolean transparent) {
    this.transparent = transparent;
    if (transparent) {
      this.color.setAlpha(127);
    } else {
      this.color.setAlpha(255);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTransparent() {
    return this.transparent;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "trianglePolygon (color=" + this.color + ")"; //$NON-NLS-1$ //$NON-NLS-2$
  }
}
