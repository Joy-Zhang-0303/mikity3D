package org.mklab.mikity.model.xml.simplexml;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


/**
 * Mikity3Dのデータを表すクラスです。
 * 
 * @version $Revision: 1.2 $ $Date: 2007/08/03 03:30:27 $
 */
@Root(name="jamast")
public class Mikity3d implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** _modelList */
  @ElementList(type=Mikity3dModel.class, inline=true)
  private List<Mikity3dModel> _modelList;

  /** _configList */
  @ElementList(type=Mikity3dConfig.class, inline=true)
  private List<Mikity3dConfig> _configList;

  /**
   * コンストラクター
   */
  public Mikity3d() {
    this._modelList = new ArrayList<Mikity3dModel>();
    this._configList = new ArrayList<Mikity3dConfig>();
  }

  /**
   * Method addConfig
   * 
   * @param vConfig コンフィグ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addConfig(org.mklab.mikity.model.xml.simplexml.Mikity3dConfig vConfig) {
    this._configList.add(vConfig);
  }

  /**
   * Method addConfig
   * 
   * @param index インデックス
   * @param vConfig コンフィグ
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addConfig(int index, org.mklab.mikity.model.xml.simplexml.Mikity3dConfig vConfig) {
    this._configList.add(index, vConfig);
  }

  /**
   * Method addModel
   * 
   * @param vModel モデル
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addModel(org.mklab.mikity.model.xml.simplexml.Mikity3dModel vModel) {
    this._modelList.add(vModel);
  }

  /**
   * Method addModel
   * 
   * @param index インデックス
   * @param vModel モデル
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void addModel(int index, org.mklab.mikity.model.xml.simplexml.Mikity3dModel vModel) {
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
  public org.mklab.mikity.model.xml.simplexml.Mikity3dConfig getConfig(int index) {
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
    Mikity3d other = (Mikity3d)obj;
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
  public org.mklab.mikity.model.xml.simplexml.Mikity3dConfig[] getConfig() {
    int size = this._configList.size();
    org.mklab.mikity.model.xml.simplexml.Mikity3dConfig[] mArray = new org.mklab.mikity.model.xml.simplexml.Mikity3dConfig[size];
    for (int i = 0; i < size; i++) {
      mArray[i] = this._configList.get(i);
    }
    return mArray;
  }

  /**
   * Method getConfigAsReferenceReturns a reference to 'config'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<Mikity3dConfig> getConfigAsReference() {
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
  public org.mklab.mikity.model.xml.simplexml.Mikity3dModel getModel(int index) {
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
  public org.mklab.mikity.model.xml.simplexml.Mikity3dModel[] getModel() {
    int size = this._modelList.size();
    org.mklab.mikity.model.xml.simplexml.Mikity3dModel[] mArray = new org.mklab.mikity.model.xml.simplexml.Mikity3dModel[size];
    for (int i = 0; i < size; i++) {
      mArray[i] = this._modelList.get(i);
    }
    return mArray;
  }

  /**
   * Method getModelAsReferenceReturns a reference to 'model'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public List<Mikity3dModel> loadModelAsReference() {
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
  public boolean removeConfig(org.mklab.mikity.model.xml.simplexml.Mikity3dConfig vConfig) {
    boolean removed = this._configList.remove(vConfig);
    return removed;
  }

  /**
   * Method removeModel
   * 
   * @param vModel モデル
   * @return removed
   */
  public boolean removeModel(org.mklab.mikity.model.xml.simplexml.Mikity3dModel vModel) {
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
  public void setConfig(int index, org.mklab.mikity.model.xml.simplexml.Mikity3dConfig vConfig) {
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
  public void setConfig(org.mklab.mikity.model.xml.simplexml.Mikity3dConfig[] configArray) {
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
  public void setConfigAsReference(List<Mikity3dConfig> configCollection) {
    this._configList = configCollection;
  }

  /**
   * Method setModel
   * 
   * @param index インデックス
   * @param vModel モデル
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public void setModel(int index, org.mklab.mikity.model.xml.simplexml.Mikity3dModel vModel) {
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
  public void setModel(org.mklab.mikity.model.xml.simplexml.Mikity3dModel[] modelArray) {
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
  public void setModelAsReference(List<Mikity3dModel> modelCollection) {
    this._modelList = modelCollection;
  }

  /**
   * @return XML data
   */
  public Mikity3d getJamastXMLData() {
    return this;
  }
}
