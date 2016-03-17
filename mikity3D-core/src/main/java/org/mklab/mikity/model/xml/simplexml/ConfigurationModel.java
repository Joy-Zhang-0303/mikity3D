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
package org.mklab.mikity.model.xml.simplexml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.config.BackgroundModel;
import org.mklab.mikity.model.xml.simplexml.config.BaseCoordinateModel;
import org.mklab.mikity.model.xml.simplexml.config.DataUnitModel;
import org.mklab.mikity.model.xml.simplexml.config.EyeModel;
import org.mklab.mikity.model.xml.simplexml.config.LightModel;
import org.mklab.mikity.model.xml.simplexml.config.LookAtPointModel;
import org.mklab.mikity.model.xml.simplexml.config.ModelUnitModel;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


/**
 * 環境を表すクラスです。
 * 
 * @version $Revision: 1.9 $ $Date: 2007/08/03 03:30:27 $
 */
@Root(name="configuration")
public class ConfigurationModel implements Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  /** 背景。 */
  @Element(name="background")
  private BackgroundModel background;
  
  /** 絶対座標。 */
  @Element(name="baseCoordinate", required=false)
  private BaseCoordinateModel baseCoordinate;

  /** 光源。 */
  @Element(name="light")
  private LightModel light;
  
  /** 視点。 */
  @Element(name="eye")
  private EyeModel eye;

  /** 注視点。 */
  @Element(name="lookAtPoint")
  private LookAtPointModel lookAtPoint;

  /** モデルの単位。 */
  @Element(name="modelUnit")
  private ModelUnitModel modelUnit;

  /** データの単位。 */
  @Element(name="dataUnit")
  private DataUnitModel dataUnit;

  /** データ。 */
  @ElementList(type=SourceDataModel.class, inline=true, required=false)
  private List<SourceDataModel> sources;
  
  /**
   * 新しく生成された<code>Mikity3dConfiguration</code>オブジェクトを初期化します。
   */
  public ConfigurationModel() {
    this.background = new BackgroundModel();
    this.baseCoordinate = new BaseCoordinateModel();
    this.light = new LightModel();
    this.eye = new EyeModel();
    this.lookAtPoint = new LookAtPointModel();
    this.modelUnit = new ModelUnitModel();
    this.dataUnit = new DataUnitModel();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public ConfigurationModel clone() {
    try {
      final ConfigurationModel ans = (ConfigurationModel)super.clone();
      ans.background = this.background.clone();
      ans.baseCoordinate = this.baseCoordinate.clone();
      ans.light = this.light.clone();
      ans.eye = this.eye.clone();
      ans.lookAtPoint = this.lookAtPoint.clone();
      ans.modelUnit = this.modelUnit.clone();
      ans.dataUnit = this.dataUnit.clone();

      if (this.sources != null) {
        ans.sources = new ArrayList<>();
        for (SourceDataModel source : this.sources) {
          ans.sources.add(source.clone());
        }
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
    result = prime * result + ((this.background == null) ? 0 : this.background.hashCode());
    result = prime * result + ((this.baseCoordinate == null) ? 0 : this.baseCoordinate.hashCode());
    result = prime * result + ((this.dataUnit == null) ? 0 : this.dataUnit.hashCode());
    result = prime * result + ((this.eye == null) ? 0 : this.eye.hashCode());
    result = prime * result + ((this.light == null) ? 0 : this.light.hashCode());
    result = prime * result + ((this.lookAtPoint == null) ? 0 : this.lookAtPoint.hashCode());
    result = prime * result + ((this.modelUnit == null) ? 0 : this.modelUnit.hashCode());
    result = prime * result + ((this.sources == null) ? 0 : this.sources.hashCode());
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
    ConfigurationModel other = (ConfigurationModel)obj;
    if (this.background == null) {
      if (other.background != null) return false;
    } else if (!this.background.equals(other.background)) return false;
    if (this.baseCoordinate == null) {
      if (other.baseCoordinate != null) return false;
    } else if (!this.baseCoordinate.equals(other.baseCoordinate)) return false;
    if (this.dataUnit == null) {
      if (other.dataUnit != null) return false;
    } else if (!this.dataUnit.equals(other.dataUnit)) return false;
    if (this.eye == null) {
      if (other.eye != null) return false;
    } else if (!this.eye.equals(other.eye)) return false;
    if (this.light == null) {
      if (other.light != null) return false;
    } else if (!this.light.equals(other.light)) return false;
    if (this.lookAtPoint == null) {
      if (other.lookAtPoint != null) return false;
    } else if (!this.lookAtPoint.equals(other.lookAtPoint)) return false;
    if (this.modelUnit == null) {
      if (other.modelUnit != null) return false;
    } else if (!this.modelUnit.equals(other.modelUnit)) return false;
    if (this.sources == null) {
      if (other.sources != null) return false;
    } else if (!this.sources.equals(other.sources)) return false;
    return true;
  }

  /**
   * 背景を返します。
   * 
   * @return 背景
   */
  public BackgroundModel getBackground() {
    return this.background;
  }

  
  /**
   * 絶対座標を返します。
   * 
   * @return 絶対座標
   */
  public BaseCoordinateModel getBaseCoordinate() {
    return this.baseCoordinate;
  }

  
  /**
   * データを返します。
   * 
   * @return データ
   */
  public List<SourceDataModel> getSources() {
    return this.sources;
  }

  /**
   * データの単位を返します。
   * 
   * @return データの単位
   */
  public DataUnitModel getDataUnit() {
    return this.dataUnit;
  }

  /**
   * 光源を返します。
   * 
   * @return 光源
   */
  public LightModel getLight() {
    return this.light;
  }

  /**
   * モデルの単位を返します。
   * 
   * @return モデルの単位
   */
  public ModelUnitModel getModelUnit() {
    return this.modelUnit;
  }

  /**
   * 視点を返します。
   * 
   * @return 視点
   */
  public EyeModel getEye() {
    return this.eye;
  }

  /**
   * 注視点を返します。
   * 
   * @return 注視点
   */
  public LookAtPointModel getLookAtPoint() {
    return this.lookAtPoint;
  }
  
  /**
   * 背景を設定します。
   * 
   * @param background 背景
   */
  public void setBackground(BackgroundModel background) {
    this.background = background;
  }

  /**
   * 基準座標軸を設定します。
   * 
   * @param baseAxis 基準座標軸
   */
  public void setBaseAxis(BaseCoordinateModel baseAxis) {
    this.baseCoordinate = baseAxis;
  }

  /**
   * ソースを設定します。
   * 
   * @param sources ソース
   */
  public void setSources(List<SourceDataModel> sources) {
    this.sources = sources;
  }

  /**
   * データの単位を設定します。
   * 
   * @param dataUnit データの単位
   */
  public void setDataUnit(DataUnitModel dataUnit) {
    this.dataUnit = dataUnit;
  }

  /**
   * 光源を設定します。
   * 
   * @param light 光源
   */
  public void setLight(LightModel light) {
    this.light = light;
  }

  /**
   * モデルの単位を設定します。
   * 
   * @param modelUnit モデル単位
   */
  public void setModelUnit(ModelUnitModel modelUnit) {
    this.modelUnit = modelUnit;
  }

  /**
   * 視点を設定します。
   * 
   * @param eye 視点
   */
  public void setEye(EyeModel eye) {
    this.eye = eye;
  }
  
  /**
   * 注視点を設定します。
   * 
   * @param lookAtPoint 注視点
   */
  public void setLookAtPoiint(LookAtPointModel lookAtPoint) {
    this.lookAtPoint = lookAtPoint;
  }
}
