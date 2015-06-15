package org.mklab.mikity.model.xml.simplexml;

import org.mklab.mikity.model.xml.simplexml.config.Background;
import org.mklab.mikity.model.xml.simplexml.config.DataUnit;
import org.mklab.mikity.model.xml.simplexml.config.Light;
import org.mklab.mikity.model.xml.simplexml.config.ModelUnit;
import org.mklab.mikity.model.xml.simplexml.config.View;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * 環境を表すクラスです。
 * 
 * @version $Revision: 1.9 $ $Date: 2007/08/03 03:30:27 $
 */
@Root(name="config")
public class Mikity3dConfiguration implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** データ */
  @Element(name="data", required=false)
  private String data;

  /** 背景 */
  @Element(name="background")
  private Background background;

  /** 光源 */
  @Element(name="light")
  private Light light;

  /** ビュー */
  @Element(name="view")
  private View view;

  /** モデルの単位 */
  @Element(name="modelUnit")
  private ModelUnit modelUnit;

  /** データの単位 */
  @Element(name="dataUnit")
  private DataUnit dataUnit;

  /**
   * 新しく生成された<code>Mikity3dConfiguration</code>オブジェクトを初期化します。
   */
  public Mikity3dConfiguration() {
    this.background = new Background();
    this.light = new Light();
    this.view = new View();
    this.modelUnit = new ModelUnit();
    this.dataUnit = new DataUnit();
  }
  
  /**
   * 背景を返します。
   * 
   * @return 背景
   */
  public Background getBackground() {
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
    result = prime * result + ((this.view == null) ? 0 : this.view.hashCode());
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
    Mikity3dConfiguration other = (Mikity3dConfiguration)obj;
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
    if (this.view == null) {
      if (other.view != null) {
        return false;
      }
    } else if (!this.view.equals(other.view)) {
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
  public DataUnit getDataUnit() {
    return this.dataUnit;
  }

  /**
   * 光源を返します。
   * 
   * @return 光源
   */
  public Light getLight() {
    return this.light;
  }

  /**
   * モデルの単位を返します。
   * 
   * @return モデルの単位
   */
  public ModelUnit getModelUnit() {
    return this.modelUnit;
  }

  /**
   * ビューを返します。
   * 
   * @return ビュー
   */
  public View getView() {
    return this.view;
  }

  /**
   * 背景を設定します。
   * 
   * @param background 背景
   */
  public void setBackground(Background background) {
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
  public void setDataUnit(DataUnit dataUnit) {
    this.dataUnit = dataUnit;
  }

  /**
   * 光源を設定します。
   * 
   * @param light 光源
   */
  public void setLight(Light light) {
    this.light = light;
  }

  /**
   * モデルの単位を設定します。
   * 
   * @param modelUnit モデル単位
   */
  public void setModelUnit(ModelUnit modelUnit) {
    this.modelUnit = modelUnit;
  }

  /**
   * ビューを設定します。
   * 
   * @param view ビュー
   */
  public void setView(View view) {
    this.view = view;
  }
}
