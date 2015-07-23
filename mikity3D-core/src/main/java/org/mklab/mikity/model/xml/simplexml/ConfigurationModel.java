package org.mklab.mikity.model.xml.simplexml;

import org.mklab.mikity.model.xml.simplexml.config.BackgroundModel;
import org.mklab.mikity.model.xml.simplexml.config.DataUnitModel;
import org.mklab.mikity.model.xml.simplexml.config.LightModel;
import org.mklab.mikity.model.xml.simplexml.config.LookAtPointModel;
import org.mklab.mikity.model.xml.simplexml.config.ModelUnitModel;
import org.mklab.mikity.model.xml.simplexml.config.EyeModel;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * 環境を表すクラスです。
 * 
 * @version $Revision: 1.9 $ $Date: 2007/08/03 03:30:27 $
 */
@Root(name="configuration")
public class ConfigurationModel implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** 背景。 */
  @Element(name="background")
  private BackgroundModel background;

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
  @Element(name="data", required=false)
  private String data;
  
  /**
   * 新しく生成された<code>Mikity3dConfiguration</code>オブジェクトを初期化します。
   */
  public ConfigurationModel() {
    this.background = new BackgroundModel();
    this.light = new LightModel();
    this.eye = new EyeModel();
    this.lookAtPoint = new LookAtPointModel();
    this.modelUnit = new ModelUnitModel();
    this.dataUnit = new DataUnitModel();
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
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.background == null) ? 0 : this.background.hashCode());
    result = prime * result + ((this.data == null) ? 0 : this.data.hashCode());
    result = prime * result + ((this.dataUnit == null) ? 0 : this.dataUnit.hashCode());
    result = prime * result + ((this.light == null) ? 0 : this.light.hashCode());
    result = prime * result + ((this.modelUnit == null) ? 0 : this.modelUnit.hashCode());
    result = prime * result + ((this.eye == null) ? 0 : this.eye.hashCode());
    result = prime * result + ((this.lookAtPoint == null) ? 0 : this.lookAtPoint.hashCode());
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
    ConfigurationModel other = (ConfigurationModel)obj;
    if (this.background == null) {
      if (other.background != null) {
        return false;
      }
    } else if (!this.background.equals(other.background)) {
      return false;
    }
    if (this.data == null) {
      if (other.data != null) {
        return false;
      }
    } else if (!this.data.equals(other.data)) {
      return false;
    }
    if (this.dataUnit == null) {
      if (other.dataUnit != null) {
        return false;
      }
    } else if (!this.dataUnit.equals(other.dataUnit)) {
      return false;
    }
    if (this.light == null) {
      if (other.light != null) {
        return false;
      }
    } else if (!this.light.equals(other.light)) {
      return false;
    }
    if (this.modelUnit == null) {
      if (other.modelUnit != null) {
        return false;
      }
    } else if (!this.modelUnit.equals(other.modelUnit)) {
      return false;
    }
    if (this.eye == null) {
      if (other.eye != null) {
        return false;
      }
    } else if (!this.eye.equals(other.eye)) {
      return false;
    }
    if (this.lookAtPoint == null) {
      if (other.lookAtPoint != null) {
        return false;
      }
    } else if (!this.lookAtPoint.equals(other.lookAtPoint)) {
      return false;
    }

    return true;
  }

  /**
   * データを返します。
   * 
   * @return データ
   */
  public String getData() {
    return this.data;
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
   * データを設定します。
   * 
   * @param data データ
   */
  public void setData(String data) {
    this.data = data;
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
