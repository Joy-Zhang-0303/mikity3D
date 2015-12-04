/*
 * Created on 2015/10/23
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * 軸モデルを表すクラスです。
 * 
 * @version $Revision$, 2015/10/23
 */
@Root(name="axis")
public class AxisModel extends AbstractObjectModel {
  /** */
  private static final long serialVersionUID = 1L;

  /** radius */
  @Attribute(name="radius")
  protected float radius;

  /** height */
  @Attribute(name="height")
  protected float height;

  /** division */
  @Attribute(name="division")
  protected int division;
  
  /**
   * 新しく生成された<code>AxisModel</code>オブジェクトを初期化します。
   */
  public AxisModel() {
    
  }
  
  /**
   * 新しく生成された<code>AxisModel</code>オブジェクトを初期化します。
   * @param radius 半径
   * @param height 高さ
   * @param division 分割数
   */
  public AxisModel(float radius, float height, int division) {
    this.radius = radius;
    this.height = height;
    this.division = division;
  }
  
  /**
   * デフォルトのモデルを生成します。
   * 
   * @return デフォルトのモデル
   */
  public static AxisModel createDefault() {
    return new AxisModel(0.006f, 0.36f, 36);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public AxisModel clone() {
    final AxisModel ans = this.clone();
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
    
    AxisModel other = (AxisModel)obj;
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
   * {@inheritDoc}
   */
  public float getHeight() {
    return this.height;
  }

  /**
   * 半径を返します。
   * 
   * @return 半径
   */
  public float getRadius() {
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
   * 半径を設定します。
   * 
   * @param radius 半径
   */
  public void setRadius(float radius) {
    this.radius = radius;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "axis (radius=" + this.radius + ", height=" + this.height + ", division=" + this.division + ", color=" + this.color + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toShortString() {
    return "axis"; //$NON-NLS-1$
  }
  
  /**
   * 軸のヘッドの半径を返します。
   * 
   * @return 軸のヘッドの半径
   */
  public float getHeaderRadius() {
    return 3*this.radius;
  }
  
  /**
   * 軸のヘッドの高さを返します。
   * 
   * @return 軸のヘッドの高さ
   */
  public float getHeaderHeight() {
    return 6*this.radius;
  }

  /**
   * {@inheritDoc}
   */
  public float getWidth() {
    return 2*getHeaderRadius();
  }

  /**
   * {@inheritDoc}
   */
  public float getDepth() {
    return 2*getHeaderRadius();
  }
}
