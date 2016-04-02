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
  
  /** グリッドの色。 */
  @Element(name="gridColor")
  private ColorModel gridColor;
  
  /** グリッドの間隔。 */
  @Element(name="gridSize")
  private float gridSize;
  
  /** 床の幅(Y軸方向)。 */
  @Element(name="floorWidth", required=false)
  private float floorWidth;
  
  /** 床の奥行き(X軸方向)。 */
  @Element(name="floorDepth", required=false)
  private float floorDepth;
  
  /** 軸を表示するならばtrue。  */
  @Element(name="isAxisShowing")
  private boolean isAxisShowing;

  /** グリッドを表示するならばtrue。  */
  @Element(name="isGridShowing")
  private boolean isGridShowing;
  
  /** 床を表示するならばtrue。 */
  @Element(name="isFloorDrawing", required=false)
  private boolean isFloorDrawing;

  /**
   * 新しく生成された<code>BaseCoordinateModel</code>オブジェクトを初期化します。
   */
  public BaseCoordinateModel() {
    this.gridColor = new ColorModel(20, 20, 20);
    this.gridSize = 0.1f;
    this.floorDepth = 10;
    this.floorWidth = 10;
    this.isAxisShowing = true;
    this.isGridShowing = false;
    this.isFloorDrawing = true;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public BaseCoordinateModel clone() {
    try {
      final BaseCoordinateModel ans = (BaseCoordinateModel)super.clone();
      ans.gridColor = this.gridColor.clone();
      ans.gridSize = this.gridSize;
      ans.floorDepth = this.floorDepth;
      ans.floorWidth = this.floorWidth;
      ans.isAxisShowing = this.isAxisShowing;
      ans.isGridShowing = this.isGridShowing;
      ans.isFloorDrawing = this.isFloorDrawing;
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
    result = prime * result + Float.floatToIntBits(this.floorDepth);
    result = prime * result + Float.floatToIntBits(this.floorWidth);
    result = prime * result + (this.isAxisShowing ? 1231 : 1237);
    result = prime * result + (this.isGridShowing ? 1231 : 1237);
    result = prime * result + (this.isFloorDrawing ? 1231 : 1237);
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
    if (Float.floatToIntBits(this.gridSize) != Float.floatToIntBits(other.gridSize)) {
      return false;
    }
    if (Float.floatToIntBits(this.floorDepth) != Float.floatToIntBits(other.floorDepth)) {
      return false;
    }
    if (Float.floatToIntBits(this.floorWidth) != Float.floatToIntBits(other.floorWidth)) {
      return false;
    }
    if (this.isAxisShowing != other.isAxisShowing) {
      return false;
    }
    if (this.isGridShowing != other.isGridShowing) {
      return false;
    }
    if (this.isFloorDrawing != other.isFloorDrawing) {
      return false;
    }
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
   * グリッド間隔を設定します。
   * 
   * @param gridSize グリッド間隔
   */
  public void setGridSize(float gridSize) {
    this.gridSize = gridSize;
  }
  
  /**
   * 床の幅を返します。
   * 
   * @return 床の幅
   */
  public float getFloorWidth() {
    return this.floorWidth;
  }
  
  /**
   * 床の幅を設定します。
   * 
   * @param floorWidth 床の幅
   */
  public void setFloorWidth(float floorWidth) {
    this.floorWidth = floorWidth;
  }
  
  /**
   * 床の奥行きを返します。
   * 
   * @return 床の奥行き
   */
  public float getFloorDepth() {
    return this.floorDepth;
  }
  
  /**
   * 床の奥行きを設定します。
   * 
   * @param floorDepth 床の奥行き
   */
  public void setFloorDepth(float floorDepth) {
    this.floorDepth = floorDepth;
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
   * 床を表示するか判定します。
   * 
   * @return 床を表示するならばtrue
   */
  public boolean isFloorDrawing() {
    return this.isFloorDrawing;
  }
  
  /**
   * 床を表示するか設定します。
   * 
   * @param isFloorDrawing 床を表示するならばtrue
   */
  public void setFloorDrawing(boolean isFloorDrawing) {
    this.isFloorDrawing = isFloorDrawing;
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
