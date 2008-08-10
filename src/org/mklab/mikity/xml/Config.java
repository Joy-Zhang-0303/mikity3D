/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Config.java,v 1.9 2007/08/03 03:30:27 morimune Exp $
 */

package org.mklab.mikity.xml;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlElement;


/**
 * Class Config.
 * 
 * @version $Revision: 1.9 $ $Date: 2007/08/03 03:30:27 $
 */
public class Config implements java.io.Serializable {

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * 
   */
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
  private org.mklab.mikity.xml.config.Background _background;

  /**
   * Field _light
   */
  @XmlElement
  private org.mklab.mikity.xml.config.Light _light;

  /**
   * Field _view
   */
  @XmlElement
  private org.mklab.mikity.xml.config.View _view;

  /**
   * Field _modelUnit
   */
  @XmlElement
  private org.mklab.mikity.xml.config.ModelUnit _modelUnit;

  /**
   * Field _dataUnit
   */
  @XmlElement
  private org.mklab.mikity.xml.config.DataUnit _dataUnit;

  // ----------------/
  // - Constructors -/
  // ----------------/

  /**
   * コンストラクター
   */
  public Config() {
    super();
  } // -- org.mklab.mikity.xml.Config()

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj
   */
  @Override
  public boolean equals(java.lang.Object obj) {
    if (this == obj) return true;

    if (obj instanceof Config) {

      Config temp = (Config)obj;
      if (this._data != null) {
        if (temp._data == null) return false;
        else if (!(this._data.equals(temp._data))) return false;
      } else if (temp._data != null) return false;
      if (this._background != null) {
        if (temp._background == null) return false;
        else if (!(this._background.equals(temp._background))) return false;
      } else if (temp._background != null) return false;
      if (this._light != null) {
        if (temp._light == null) return false;
        else if (!(this._light.equals(temp._light))) return false;
      } else if (temp._light != null) return false;
      if (this._view != null) {
        if (temp._view == null) return false;
        else if (!(this._view.equals(temp._view))) return false;
      } else if (temp._view != null) return false;
      if (this._modelUnit != null) {
        if (temp._modelUnit == null) return false;
        else if (!(this._modelUnit.equals(temp._modelUnit))) return false;
      } else if (temp._modelUnit != null) return false;
      if (this._dataUnit != null) {
        if (temp._dataUnit == null) return false;
        else if (!(this._dataUnit.equals(temp._dataUnit))) return false;
      } else if (temp._dataUnit != null) return false;
      return true;
    }
    return false;
  } // -- boolean equals(java.lang.Object)

  /**
   * Returns the value of field 'background'.
   * 
   * @return the value of field 'background'.
   */
  public org.mklab.mikity.xml.config.Background loadBackground() {
    return this._background;
  } // -- org.mklab.mikity.xml.Background getBackground()

  /**
   * Returns the value of field 'data'.
   * 
   * @return the value of field 'data'.
   */
  public java.lang.String loadData() {
    return this._data;
  } // -- java.lang.String getData()

  /**
   * Returns the value of field 'dataUnit'.
   * 
   * @return the value of field 'dataUnit'.
   */
  public org.mklab.mikity.xml.config.DataUnit loadDataUnit() {
    return this._dataUnit;
  } // -- org.mklab.mikity.xml.DataUnit getDataUnit()

  /**
   * Returns the value of field 'light'.
   * 
   * @return the value of field 'light'.
   */
  public org.mklab.mikity.xml.config.Light loadLight() {
    return this._light;
  } // -- org.mklab.mikity.xml.Light getLight()

  /**
   * Returns the value of field 'modelUnit'.
   * 
   * @return the value of field 'modelUnit'.
   */
  public org.mklab.mikity.xml.config.ModelUnit loadModelUnit() {
    return this._modelUnit;
  } // -- org.mklab.mikity.xml.ModelUnit getModelUnit()

  /**
   * Returns the value of field 'view'.
   * 
   * @return the value of field 'view'.
   */
  public org.mklab.mikity.xml.config.View loadView() {
    return this._view;
  } // -- org.mklab.mikity.xml.View getView()

  /**
   * Sets the value of field 'background'.
   * 
   * @param background
   *        the value of field 'background'.
   */
  public void setBackground(org.mklab.mikity.xml.config.Background background) {
    this._background = background;
  } // -- void setBackground(org.mklab.mikity.xml.Background)

  /**
   * Sets the value of field 'data'.
   * 
   * @param data
   *        the value of field 'data'.
   */
  public void setData(java.lang.String data) {
    this._data = data;
  } // -- void setData(java.lang.String)

  /**
   * Sets the value of field 'dataUnit'.
   * 
   * @param dataUnit
   *        the value of field 'dataUnit'.
   */
  public void setDataUnit(org.mklab.mikity.xml.config.DataUnit dataUnit) {
    this._dataUnit = dataUnit;
  } // -- void setDataUnit(org.mklab.mikity.xml.DataUnit)

  /**
   * Sets the value of field 'light'.
   * 
   * @param light
   *        the value of field 'light'.
   */
  public void setLight(org.mklab.mikity.xml.config.Light light) {
    this._light = light;
  } // -- void setLight(org.mklab.mikity.xml.Light)

  /**
   * Sets the value of field 'modelUnit'.
   * 
   * @param modelUnit
   *        the value of field 'modelUnit'.
   */
  public void setModelUnit(org.mklab.mikity.xml.config.ModelUnit modelUnit) {
    this._modelUnit = modelUnit;
  } // -- void setModelUnit(org.mklab.mikity.xml.ModelUnit)

  /**
   * Sets the value of field 'view'.
   * 
   * @param view
   *        the value of field 'view'.
   */
  public void setView(org.mklab.mikity.xml.config.View view) {
    this._view = view;
  } // -- void setView(org.mklab.mikity.xml.View)

}
