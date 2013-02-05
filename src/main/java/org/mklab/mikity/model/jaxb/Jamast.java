package org.mklab.mikity.model.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Class JAMAST(JAva3DModelingAndSimulationTool).
 * 
 * @version $Revision: 1.2 $ $Date: 2007/08/03 03:30:27 $
 */
@XmlRootElement
public class Jamast implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Field _modelList
   */
  @XmlElement
  private java.util.ArrayList<JamastModel> _modelList;

  /**
   * Field _configList
   */
  @XmlElement
  private java.util.ArrayList<JamastConfig> _configList;

  /**
   * コンストラクター
   */
  public Jamast() {
    super();
    this._modelList = new java.util.ArrayList<JamastModel>();
    this._configList = new java.util.ArrayList<JamastConfig>();
  }

  /**
   * Method addConfig
   * 
   * @param vConfig コンフィグ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addConfig(org.mklab.mikity.model.jaxb.JamastConfig vConfig) throws java.lang.IndexOutOfBoundsException {
    this._configList.add(vConfig);
  }

  /**
   * Method addConfig
   * 
   * @param index インデックス
   * @param vConfig コンフィグ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addConfig(int index, org.mklab.mikity.model.jaxb.JamastConfig vConfig) throws java.lang.IndexOutOfBoundsException {
    this._configList.add(index, vConfig);
  }

  /**
   * Method addModel
   * 
   * @param vModel モデル
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addModel(org.mklab.mikity.model.jaxb.JamastModel vModel) throws java.lang.IndexOutOfBoundsException {
    this._modelList.add(vModel);
  }

  /**
   * Method addModel
   * 
   * @param index インデックス
   * @param vModel モデル
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addModel(int index, org.mklab.mikity.model.jaxb.JamastModel vModel) throws java.lang.IndexOutOfBoundsException {
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
   * Method getConfig
   * 
   * @param index インデックス
   * @return _configList.get(index)
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public org.mklab.mikity.model.jaxb.JamastConfig getConfig(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._configList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._configList.get(index);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unqualified-field-access")
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_configList == null) ? 0 : _configList.hashCode());
    result = prime * result + ((_modelList == null) ? 0 : _modelList.hashCode());
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
    Jamast other = (Jamast)obj;
    if (this._configList == null) {
      if (other._configList != null) {
        return false;
      }
    } else if (!this._configList.equals(other._configList)) {
      return false;
    }
    if (this._modelList == null) {
      if (other._modelList != null) {
        return false;
      }
    } else if (!this._modelList.equals(other._modelList)) {
      return false;
    }
    return true;
  }

  /**
   * Method getConfig
   * 
   * @return mArray
   */
  public org.mklab.mikity.model.jaxb.JamastConfig[] getConfig() {
    int size = this._configList.size();
    org.mklab.mikity.model.jaxb.JamastConfig[] mArray = new org.mklab.mikity.model.jaxb.JamastConfig[size];
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
  public java.util.ArrayList<JamastConfig> getConfigAsReference() {
    return this._configList;
  }

  /**
   * Method getConfigCount
   * 
   * @return _configList.size()
   */
  public int getConfigSize() {
    return this._configList.size();
  }

  /**
   * Method getModel
   * 
   * @param index インデックス
   * @return (org.mklab.mikity.xml.Model) _modelList.get(index)
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public org.mklab.mikity.model.jaxb.JamastModel getModel(int index) throws java.lang.IndexOutOfBoundsException {
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
  public org.mklab.mikity.model.jaxb.JamastModel[] getModel() {
    int size = this._modelList.size();
    org.mklab.mikity.model.jaxb.JamastModel[] mArray = new org.mklab.mikity.model.jaxb.JamastModel[size];
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
  public java.util.ArrayList<JamastModel> loadModelAsReference() {
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
  public boolean removeConfig(org.mklab.mikity.model.jaxb.JamastConfig vConfig) {
    boolean removed = this._configList.remove(vConfig);
    return removed;
  }

  /**
   * Method removeModel
   * 
   * @param vModel モデル
   * @return removed
   */
  public boolean removeModel(org.mklab.mikity.model.jaxb.JamastModel vModel) {
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
  public void setConfig(int index, org.mklab.mikity.model.jaxb.JamastConfig vConfig) throws java.lang.IndexOutOfBoundsException {
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
  public void setConfig(org.mklab.mikity.model.jaxb.JamastConfig[] configArray) {
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
  public void setConfigAsReference(java.util.ArrayList<JamastConfig> configCollection) {
    this._configList = configCollection;
  }

  /**
   * Method setModel
   * 
   * @param index インデックス
   * @param vModel モデル
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void setModel(int index, org.mklab.mikity.model.jaxb.JamastModel vModel) throws java.lang.IndexOutOfBoundsException {
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
  public void setModel(org.mklab.mikity.model.jaxb.JamastModel[] modelArray) {
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
  public void setModelAsReference(java.util.ArrayList<JamastModel> modelCollection) {
    this._modelList = modelCollection;
  }

  /**
   * @return XML data
   */
  public Jamast getJamastXMLData() {
    return this;
  }
}
