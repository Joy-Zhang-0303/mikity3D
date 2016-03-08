/*
 * Created on 2015/11/11
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.config;

import java.io.Serializable;

import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.simpleframework.xml.Element;

/**
 * 絶対座標を表すクラスです。
 * 
 * @author eguchi
 * @version $Revision$, 2015/11/11
 */
public class BaseCoordinateModel implements Serializable, Cloneable {
  private static final long serialVersionUID = 1L;
  
  /** グリッドの色 */
  @Element(name="gridColor")
  private ColorModel gridColor;
  
  /** グリッドの間隔 */
  @Element(name="gridSize")
  private float gridInverval;
  
  /** 表示するならばtrue。  */
  @Element(name="isAxisShowing")
  private boolean isAxisShowing;

  /** 表示するならばtrue。  */
  @Element(name="isGridShowing")
  private boolean isGridShowing;

  /**
   * 新しく生成された<code>BaseCoordinateModel</code>オブジェクトを初期化します。
   */
  public BaseCoordinateModel() {
    this.gridColor = new ColorModel(20, 20, 20);
    this.gridInverval = 0.1f;
    this.isAxisShowing = true;
    this.isGridShowing = true;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public BaseCoordinateModel clone() {
    try {
      final BaseCoordinateModel ans = (BaseCoordinateModel)super.clone();
      ans.gridColor = this.gridColor.clone();
      ans.gridInverval = this.gridInverval;
      ans.isAxisShowing = this.isAxisShowing;
      ans.isGridShowing = this.isGridShowing;
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
    result = prime * result + ((this.gridColor == null) ? 0 : this.gridColor.hashCode());
    result = prime * result + Float.floatToIntBits(this.gridInverval);
    result = prime * result + (this.isAxisShowing ? 1231 : 1237);
    result = prime * result + (this.isGridShowing ? 1231 : 1237);
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
    BaseCoordinateModel other = (BaseCoordinateModel)obj;
    if (this.gridColor == null) {
      if (other.gridColor != null) {
        return false;
      }
    } else if (!this.gridColor.equals(other.gridColor)) {
      return false;
    }
    if (Float.floatToIntBits(this.gridInverval) != Float.floatToIntBits(other.gridInverval)) {
      return false;
    }
    if (this.isAxisShowing != other.isAxisShowing) {
      return false;
    }
    if (this.isGridShowing != other.isGridShowing) {
      return false;
    }
    return true;
  }

  /**
   * グリッド間隔を返します。
   * 
   * @return グリッド間隔
   */
  public float getGridInterval() {
    return this.gridInverval;
  }

  
  /**
   * グリッド間隔を設定します。
   * 
   * @param gridInverval グリッド間隔
   */
  public void setGridInterval(float gridInverval) {
    this.gridInverval = gridInverval;
  }
  
  /**
   * 座標軸を表示するか判定します。
   * 
   * @return 座標軸を表示するならばtrue
   */
  public boolean isAxisShowing() {
    return this.isAxisShowing;
  }
  
  /**
   * 座標軸を表示するか設定します。
   * 
   * @param isAxisShowing 座標軸を表示するならばtrue
   */
  public void setAxisShowing(boolean isAxisShowing) {
    this.isAxisShowing = isAxisShowing;
  }
  
  /**
   * グリッドを表示するか判定します。
   * 
   * @return グリッドを表示するならばtrue
   */
  public boolean isGridShowing() {
    return this.isGridShowing;
  }
  
  /**
   * グリッドを表示するか設定します。
   * 
   * @param isGridShowing グリッドを表示するならばtrue
   */
  public void setGridShowing(boolean isGridShowing) {
    this.isGridShowing = isGridShowing;
  }


  /**
   * グリッドの色を返します。
   * 
   * @return グリッドの色
   */
  public ColorModel getGridColor() {
    return this.gridColor;
  }
  
  /**
   * グリッドの色を設定します。
   * 
   * @param color グリッドの色
   */
  public void setGridColor(ColorModel color) {
    this.gridColor = color;
  }
}
