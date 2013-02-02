package org.mklab.mikity.model.xml;

import javax.xml.bind.annotation.XmlElement;


/**
 * Class Config.
 * 
 * @version $Revision: 1.9 $ $Date: 2007/08/03 03:30:27 $
 */
public class JamastConfig implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Field _data
   */
  @XmlElement
  private java.lang.String _data;

  /**
   * Field _background
   */
  @XmlElement
  private org.mklab.mikity.model.xml.config.Background _background;

  /**
   * Field _light
   */
  @XmlElement
  private org.mklab.mikity.model.xml.config.Light _light;

  /**
   * Field _view
   */
  @XmlElement
  private org.mklab.mikity.model.xml.config.View _view;

  /**
   * Field _modelUnit
   */
  @XmlElement
  private org.mklab.mikity.model.xml.config.ModelUnit _modelUnit;

  /**
   * Field _dataUnit
   */
  @XmlElement
  private org.mklab.mikity.model.xml.config.DataUnit _dataUnit;

  /**
   * Returns the value of field 'background'.
   * 
   * @return the value of field 'background'.
   */
  public org.mklab.mikity.model.xml.config.Background getBackground() {
    return this._background;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this._background == null) ? 0 : this._background.hashCode());
    result = prime * result + ((this._data == null) ? 0 : this._data.hashCode());
    result = prime * result + ((this._dataUnit == null) ? 0 : this._dataUnit.hashCode());
    result = prime * result + ((this._light == null) ? 0 : this._light.hashCode());
    result = prime * result + ((this._modelUnit == null) ? 0 : this._modelUnit.hashCode());
    result = prime * result + ((this._view == null) ? 0 : this._view.hashCode());
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
    JamastConfig other = (JamastConfig)obj;
    if (this._background == null) {
      if (other._background != null) {
        return false;
      }
    } else if (!this._background.equals(other._background)) {
      return false;
    }
    if (this._data == null) {
      if (other._data != null) {
        return false;
      }
    } else if (!this._data.equals(other._data)) {
      return false;
    }
    if (this._dataUnit == null) {
      if (other._dataUnit != null) {
        return false;
      }
    } else if (!this._dataUnit.equals(other._dataUnit)) {
      return false;
    }
    if (this._light == null) {
      if (other._light != null) {
        return false;
      }
    } else if (!this._light.equals(other._light)) {
      return false;
    }
    if (this._modelUnit == null) {
      if (other._modelUnit != null) {
        return false;
      }
    } else if (!this._modelUnit.equals(other._modelUnit)) {
      return false;
    }
    if (this._view == null) {
      if (other._view != null) {
        return false;
      }
    } else if (!this._view.equals(other._view)) {
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
    return this._data;
  }

  /**
   * Returns the value of field 'dataUnit'.
   * 
   * @return the value of field 'dataUnit'.
   */
  public org.mklab.mikity.model.xml.config.DataUnit getDataUnit() {
    return this._dataUnit;
  }

  /**
   * Returns the value of field 'light'.
   * 
   * @return the value of field 'light'.
   */
  public org.mklab.mikity.model.xml.config.Light getLight() {
    return this._light;
  }

  /**
   * Returns the value of field 'modelUnit'.
   * 
   * @return the value of field 'modelUnit'.
   */
  public org.mklab.mikity.model.xml.config.ModelUnit getModelUnit() {
    return this._modelUnit;
  }

  /**
   * Returns the value of field 'view'.
   * 
   * @return the value of field 'view'.
   */
  public org.mklab.mikity.model.xml.config.View getView() {
    return this._view;
  }

  /**
   * Sets the value of field 'background'.
   * 
   * @param background the value of field 'background'.
   */
  public void setBackground(org.mklab.mikity.model.xml.config.Background background) {
    this._background = background;
  }

  /**
   * Sets the value of field 'data'.
   * 
   * @param data the value of field 'data'.
   */
  public void setData(java.lang.String data) {
    this._data = data;
  }

  /**
   * Sets the value of field 'dataUnit'.
   * 
   * @param dataUnit the value of field 'dataUnit'.
   */
  public void setDataUnit(org.mklab.mikity.model.xml.config.DataUnit dataUnit) {
    this._dataUnit = dataUnit;
  }

  /**
   * Sets the value of field 'light'.
   * 
   * @param light the value of field 'light'.
   */
  public void setLight(org.mklab.mikity.model.xml.config.Light light) {
    this._light = light;
  }

  /**
   * Sets the value of field 'modelUnit'.
   * 
   * @param modelUnit the value of field 'modelUnit'.
   */
  public void setModelUnit(org.mklab.mikity.model.xml.config.ModelUnit modelUnit) {
    this._modelUnit = modelUnit;
  }

  /**
   * Sets the value of field 'view'.
   * 
   * @param view the value of field 'view'.
   */
  public void setView(org.mklab.mikity.model.xml.config.View view) {
    this._view = view;
  }
}
