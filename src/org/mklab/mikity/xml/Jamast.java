/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Jamast.java,v 1.2 2007/08/03 03:30:27 morimune Exp $
 */

package org.mklab.mikity.xml;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Class JAMAST(JAva3DModelingAndSimulationTool).
 * 
 * @version $Revision: 1.2 $ $Date: 2007/08/03 03:30:27 $
 */
@XmlRootElement
public class Jamast implements java.io.Serializable {

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Field _modelList
   */
  @XmlElement
  private java.util.ArrayList<Model> _modelList;

  /**
   * Field _configList
   */
  @XmlElement
  private java.util.ArrayList<Config> _configList;

  // @XmlElement
  // private org.mklab.mikity.xml.blender.Scene scene;
  // ----------------/
  // - Constructors -/
  // ----------------/

  /**
   * コンストラクター
   */
  public Jamast() {
    super();
    this._modelList = new java.util.ArrayList<Model>();
    this._configList = new java.util.ArrayList<Config>();
    // scene = new org.mklab.mikity.xml.blender.Scene();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Method addConfig
   * 
   * @param vConfig コンフィグ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addConfig(org.mklab.mikity.xml.Config vConfig) throws java.lang.IndexOutOfBoundsException {
    this._configList.add(vConfig);
  }

  /**
   * Method addConfig
   * 
   * @param index インデックス
   * @param vConfig コンフィグ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addConfig(int index, org.mklab.mikity.xml.Config vConfig) throws java.lang.IndexOutOfBoundsException {
    this._configList.add(index, vConfig);
  }

  /**
   * Method addModel
   * 
   * @param vModel モデル
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addModel(org.mklab.mikity.xml.Model vModel) throws java.lang.IndexOutOfBoundsException {
    this._modelList.add(vModel);
  }

  /**
   * Method addModel
   * 
   * @param index インデックス
   * @param vModel モデル
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addModel(int index, org.mklab.mikity.xml.Model vModel) throws java.lang.IndexOutOfBoundsException {
    this._modelList.add(index, vModel);
  }

  /**
   * Method clearConfig
   */
  public void clearConfig() {
    this._configList.clear();
  }

  /**
   * Method clearModel
   */
  public void clearModel() {
    this._modelList.clear();
  }

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj オブジェクト
   */
  @Override
  public boolean equals(java.lang.Object obj) {
    if (this == obj) return true;

    if (obj instanceof Jamast) {

      Jamast temp = (Jamast)obj;
      if (this._modelList != null) {
        if (temp._modelList == null) return false;
        else if (!(this._modelList.equals(temp._modelList))) return false;
      } else if (temp._modelList != null) return false;
      if (this._configList != null) {
        if (temp._configList == null) return false;
        else if (!(this._configList.equals(temp._configList))) return false;
      } else if (temp._configList != null) return false;
      return true;
    }
    return false;
  }

  /**
   * Method getConfig
   * 
   * @param index インデックス
   * @return _configList.get(index)
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public org.mklab.mikity.xml.Config loadConfig(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._configList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._configList.get(index);
  }

  /**
   * Method getConfig
   * 
   * @return mArray
   */
  public org.mklab.mikity.xml.Config[] loadConfig() {
    int size = this._configList.size();
    org.mklab.mikity.xml.Config[] mArray = new org.mklab.mikity.xml.Config[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._configList.get(index);
    }
    return mArray;
  }

  /**
   * Method getConfigAsReferenceReturns a reference to 'config'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<Config> loadConfigAsReference() {
    return this._configList;
  }

  /**
   * Method getConfigCount
   * 
   * @return _configList.size()
   */
  public int loadConfigCount() {
    return this._configList.size();
  }

  /**
   * Method getModel
   * 
   * @param index インデックス
   * @return (org.mklab.mikity.xml.Model) _modelList.get(index)
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public org.mklab.mikity.xml.Model loadModel(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._modelList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._modelList.get(index);
  }

  /**
   * Method getModel
   * 
   * @return mArray
   */
  public org.mklab.mikity.xml.Model[] loadModel() {
    int size = this._modelList.size();
    org.mklab.mikity.xml.Model[] mArray = new org.mklab.mikity.xml.Model[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._modelList.get(index);
    }
    return mArray;
  }

  /**
   * Method getModelAsReferenceReturns a reference to 'model'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<Model> loadModelAsReference() {
    return this._modelList;
  }

  /**
   * Method getModelCount
   * 
   * @return _modelList.size()
   */
  public int loadModelCount() {
    return this._modelList.size();
  }

  /**
   * Method removeConfig
   * 
   * @param vConfig コンフィグ
   * @return removed
   */
  public boolean removeConfig(org.mklab.mikity.xml.Config vConfig) {
    boolean removed = this._configList.remove(vConfig);
    return removed;
  }

  /**
   * Method removeModel
   * 
   * @param vModel モデル
   * @return removed
   */
  public boolean removeModel(org.mklab.mikity.xml.Model vModel) {
    boolean removed = this._modelList.remove(vModel);
    return removed;
  }

  /**
   * Method setConfig
   * 
   * @param index インデックス
   * @param vConfig コンフィグ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void setConfig(int index, org.mklab.mikity.xml.Config vConfig) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._configList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._configList.set(index, vConfig);
  }

  /**
   * Method setConfig
   * 
   * @param configArray コンフィグの配列
   */
  public void setConfig(org.mklab.mikity.xml.Config[] configArray) {
    // -- copy array
    this._configList.clear();
    for (int i = 0; i < configArray.length; i++) {
      this._configList.add(configArray[i]);
    }
  }
  
  /**
   * Method setConfigAsReferenceSets the value of 'config' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param configCollection the ArrayList to copy.
   */
  public void setConfigAsReference(java.util.ArrayList<Config> configCollection) {
    this._configList = configCollection;
  }

  /**
   * Method setModel
   * 
   * @param index インデックス
   * @param vModel モデル
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void setModel(int index, org.mklab.mikity.xml.Model vModel) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._modelList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._modelList.set(index, vModel);
  }

  /**
   * Method setModel
   * 
   * @param modelArray モデルの配列
   */
  public void setModel(org.mklab.mikity.xml.Model[] modelArray) {
    // -- copy array
    this._modelList.clear();
    for (int i = 0; i < modelArray.length; i++) {
      this._modelList.add(modelArray[i]);
    }
  }

  /**
   * Method setModelAsReferenceSets the value of 'model' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param modelCollection the ArrayList to copy.
   */
  public void setModelAsReference(java.util.ArrayList<Model> modelCollection) {
    this._modelList = modelCollection;
  }

  /**
   * @return XML data
   */
  public Jamast loadJamastXMLData() {
    return this;
  }
}
