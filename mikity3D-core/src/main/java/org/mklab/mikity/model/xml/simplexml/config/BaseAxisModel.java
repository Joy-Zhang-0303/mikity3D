/*
 * Created on 2015/11/11
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.config;

import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.simpleframework.xml.Element;

/**
 * @author eguchi
 * @version $Revision$, 2015/11/11
 */
public class BaseAxisModel implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 1L;
  
  /** color */
  @Element(name="gridColor")
  private ColorModel gridColor;
  
  /** grid size */
  @Element(name="gridSize")
  private float gridSize;
  
  /** display swith */
  @Element(name="display")
  private boolean isDisplay;
  
  /**
   * 新しく生成された<code>BaseAxisModel</code>オブジェクトを初期化します。
   */
  public BaseAxisModel() {
    this.gridColor = new ColorModel(20, 20, 20);
    this.gridSize = 0.05f;
    this.isDisplay = true;
  }
  
  /**
   * 新しく生成された<code>BaseAxisModel</code>オブジェクトを初期化します。
   * @param color グリッド色
   * @param gridSize グリッド間隔
   * @param isDisplay 表示切替
   */
  public BaseAxisModel(ColorModel color, float gridSize, boolean isDisplay) {
    this.gridColor = color;
    this.gridSize = gridSize;
    this.isDisplay = isDisplay;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public BaseAxisModel clone() {
    try {
      final BaseAxisModel ans = (BaseAxisModel)super.clone();
      ans.gridColor = this.gridColor.clone();
      ans.gridSize = this.gridSize;
      ans.isDisplay = this.isDisplay;
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
    result = prime * result + Float.floatToIntBits(this.gridSize);
    result = prime * result + (this.isDisplay ? 1231 : 1237);
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
    BaseAxisModel other = (BaseAxisModel)obj;
    if (this.gridColor == null) {
      if (other.gridColor != null) return false;
    } else if (!this.gridColor.equals(other.gridColor)) return false;
    if (Float.floatToIntBits(this.gridSize) != Float.floatToIntBits(other.gridSize)) return false;
    if (this.isDisplay != other.isDisplay) return false;
    return true;
  }

  /**
   * グリッド間隔を返します。
   * 
   * @return グリッド間隔
   */
  public float getGridSize() {
    return this.gridSize;
  }

  
  /**
   * 
   * @param gridSize
   */
  public void setGridSize(float gridSize) {
    this.gridSize = gridSize;
  }

  
  /**
   * @return
   */
  public boolean isDisplay() {
    return this.isDisplay;
  }

  
  /**
   * @param isDisplay
   */
  public void setDisplay(boolean isDisplay) {
    this.isDisplay = isDisplay;
  }

  /**
   * 色を返します。
   * 
   * @return 色
   */
  public ColorModel getColor() {
    return this.gridColor;
  }
  
  /**
   * 色を設定します。
   * 
   * @param color 色
   */
  public void setColor(ColorModel color) {
    this.gridColor = color;
  }
}
