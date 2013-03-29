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
public class Mikity3dConfig implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** data */
  @Element(name="data", required=false)
  private java.lang.String data;

  /** background */
  @Element(name="background")
  private Background background;

  /** light */
  @Element(name="light")
  private Light light;

  /** view */
  @Element(name="view")
  private View view;

  /** modelUnit */
  @Element(name="modelUnit")
  private ModelUnit modelUnit;

  /** dataUnit */
  @Element(name="dataUnit")
  private DataUnit dataUnit;

  /**
   * Returns the value of field 'background'.
   * 
   * @return the value of field 'background'.
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
    Mikity3dConfig other = (Mikity3dConfig)obj;
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
   * Returns the value of field 'data'.
   * 
   * @return the value of field 'data'.
   */
  public java.lang.String getData() {
    return this.data;
  }

  /**
   * Returns the value of field 'dataUnit'.
   * 
   * @return the value of field 'dataUnit'.
   */
  public DataUnit getDataUnit() {
    return this.dataUnit;
  }

  /**
   * Returns the value of field 'light'.
   * 
   * @return the value of field 'light'.
   */
  public Light getLight() {
    return this.light;
  }

  /**
   * Returns the value of field 'modelUnit'.
   * 
   * @return the value of field 'modelUnit'.
   */
  public ModelUnit getModelUnit() {
    return this.modelUnit;
  }

  /**
   * Returns the value of field 'view'.
   * 
   * @return the value of field 'view'.
   */
  public View getView() {
    return this.view;
  }

  /**
   * Sets the value of field 'background'.
   * 
   * @param background the value of field 'background'.
   */
  public void setBackground(Background background) {
    this.background = background;
  }

  /**
   * Sets the value of field 'data'.
   * 
   * @param data the value of field 'data'.
   */
  public void setData(java.lang.String data) {
    this.data = data;
  }

  /**
   * Sets the value of field 'dataUnit'.
   * 
   * @param dataUnit the value of field 'dataUnit'.
   */
  public void setDataUnit(DataUnit dataUnit) {
    this.dataUnit = dataUnit;
  }

  /**
   * Sets the value of field 'light'.
   * 
   * @param light the value of field 'light'.
   */
  public void setLight(Light light) {
    this.light = light;
  }

  /**
   * Sets the value of field 'modelUnit'.
   * 
   * @param modelUnit the value of field 'modelUnit'.
   */
  public void setModelUnit(ModelUnit modelUnit) {
    this.modelUnit = modelUnit;
  }

  /**
   * Sets the value of field 'view'.
   * 
   * @param view the value of field 'view'.
   */
  public void setView(View view) {
    this.view = view;
  }
}
