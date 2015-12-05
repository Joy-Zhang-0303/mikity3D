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
 * 地面(床)(絶対座標)を表すクラスです。
 * 
 * @author eguchi
 * @version $Revision$, 2015/11/11
 */
public class GroundModel implements Serializable, Cloneable {
  private static final long serialVersionUID = 1L;
  
  /** グリッドの色 */
  @Element(name="gridColor")
  private ColorModel gridColor;
  
  /** グリッドの間隔 */
  @Element(name="gridSize")
  private float gridInverval;
  
  /** 表示するならばtrue。  */
  @Element(name="isShowing")
  private boolean isShowing;
  
  /**
   * 新しく生成された<code>GroundModel</code>オブジェクトを初期化します。
   */
  public GroundModel() {
    this.gridColor = new ColorModel(20, 20, 20);
    this.gridInverval = 0.05f;
    this.isShowing = false;
  }
  
  /**
   * 新しく生成された<code>BaseAxisModel</code>オブジェクトを初期化します。
   * @param color グリッド色
   * @param gridInverval グリッド間隔
   * @param isDisplay 表示するならばtrue
   */
  public GroundModel(ColorModel color, float gridInverval, boolean isDisplay) {
    this.gridColor = color;
    this.gridInverval = gridInverval;
    this.isShowing = isDisplay;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public GroundModel clone() {
    try {
      final GroundModel ans = (GroundModel)super.clone();
      ans.gridColor = this.gridColor.clone();
      ans.gridInverval = this.gridInverval;
      ans.isShowing = this.isShowing;
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
    result = prime * result + (this.isShowing ? 1231 : 1237);
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
    GroundModel other = (GroundModel)obj;
    if (this.gridColor == null) {
      if (other.gridColor != null) return false;
    } else if (!this.gridColor.equals(other.gridColor)) return false;
    if (Float.floatToIntBits(this.gridInverval) != Float.floatToIntBits(other.gridInverval)) return false;
    if (this.isShowing != other.isShowing) return false;
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
   * 表示するか判定します。
   * 
   * @return 表示するならばtrue
   */
  public boolean isShowing() {
    return this.isShowing;
  }
  
  /**
   * 表示するか設定します。
   * 
   * @param isShowing 表示するならばtrue
   */
  public void setShowing(boolean isShowing) {
    this.isShowing = isShowing;
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
