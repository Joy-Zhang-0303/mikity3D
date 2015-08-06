/*
 * $Id: XMLQuadPolygon.java,v 1.13 2008/02/13 08:05:19 morimune Exp $
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
 * 四角形ポリゴンを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2008/08/10
 */
@Root(name="quadPolygon")
public class QuadPolygonModel implements Cloneable {
  /** vertices */
  @ElementList(type=VertexModel.class, inline=true, required=true)
  private List<VertexModel> vertices;
  
  /** translation */
  @Element(name="translation", required=false)
  private TranslationModel translation;
  
  /** rotation */
  @Element(name="rotation", required=false)
  private RotationModel rotation;

  /** color */
  @Element(name="color")
  private ColorModel color;
  
  /** transparent */
  @Attribute(name="transparent", required=false)
  protected boolean transparent;

  /** 法線ベクトル。 */
  private Vector3 normalVector;

  /**
   * 新しく生成された<code>XMLQuadPolygon</code>オブジェクトを初期化します。
   */
  public QuadPolygonModel() {
    this.vertices = new ArrayList<>(4);
    this.color = new ColorModel("orange"); //$NON-NLS-1$
    this.transparent = false;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public QuadPolygonModel clone() {
    try {
      final QuadPolygonModel ans = (QuadPolygonModel)super.clone();
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
    QuadPolygonModel other = (QuadPolygonModel)obj;
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
   * @param number 頂点番号(0-3)
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
   * 頂点を設定します。
   * 
   * @param vertex0 頂点0
   * @param vertex1 頂点1
   * @param vertex2 頂点2
   * @param vertex3 頂点3
   */
  public void setVertices(VertexModel vertex0, VertexModel vertex1, VertexModel vertex2, VertexModel vertex3) {
    this.vertices.clear();
    this.vertices.add(vertex0);
    this.vertices.add(vertex1);
    this.vertices.add(vertex2);
    this.vertices.add(vertex3);
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
   * @param color 色
   */
  public void setColor(String color) {
    this.color = new ColorModel(color);
  } 

  /**
   * @param translation 位置
   */
  public void setTranslation(TranslationModel translation) {
    this.translation = translation;
  }

  /**
   * @param rotation 回転
   */
  public void setRotation(RotationModel rotation) {
    this.rotation = rotation;
  }

//  /**
//   * @param matrix 行列
//   */
//  public void setMatrix(Matrix4 matrix) {
//    this.matrix = matrix;
//  }

  /**
   * 
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
   * @param number 頂点の番号(0-3)
   * @return 指定された頂点
   */
  public VertexModel getVertex(int number) {
    return this.vertices.get(number); 
  }

  /**
   * @return color
   */
  public String getColor() {
    return this.color.getName();
  }

  /**
   * @return location
   */
  public TranslationModel getTranslation() {
    return this.translation;
  }

  /**
   * @return rotation
   */
  public RotationModel getRotation() {
    return this.rotation;
  }

  /**
   * @return normal vector
   */
  public Vector3 getNormalVector() {
    if (this.normalVector == null) {
      updateNormalVector();
    }
    return this.normalVector;
  }

//  /**
//   * @return matrix
//   */
//  public Matrix4 getMatrix() {
//    return this.matrix;
//  }

  /**
   * Sets the value of field 'transparent'.
   * 
   * @param transparent the value of field 'transparent'.
   */
  public void setTransparent(boolean transparent) {
    this.transparent = transparent;
  }

  /**
   * Returns the value of field 'transparent'.
   * 
   * @return the value of field 'transparent'.
   */
  public boolean getTransparent() {
    return this.transparent;
  }
}
