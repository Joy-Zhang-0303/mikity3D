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

  /** models */
  @ElementList(type=Mikity3dModel.class, inline=true)
  private List<Mikity3dModel> models;

  /** configurations */
  @ElementList(type=Mikity3dConfiguration.class, inline=true)
  private List<Mikity3dConfiguration> configurations;

  /**
   * コンストラクター
   */
  public Mikity3d() {
    this.models = new ArrayList<Mikity3dModel>();
    this.configurations = new ArrayList<Mikity3dConfiguration>();
  }

  /**
   * Method addConfig
   * 
   * @param configuration コンフィグ
   */
  public void addConfiguration(Mikity3dConfiguration configuration) {
    this.configurations.add(configuration);
  }

//  /**
//   * Method addConfig
//   * 
//   * @param index インデックス
//   * @param configuration コンフィグ
//   */
//  public void addConfiguration(int index, Mikity3dConfiguration configuration) {
//    this.configurations.add(index, configuration);
//  }

  /**
   * Method addModel
   * 
   * @param model モデル
   */
  public void addModel(Mikity3dModel model) {
    this.models.add(model);
  }

//  /**
//   * Method addModel
//   * 
//   * @param index インデックス
//   * @param model モデル
//   */
//  public void addModel(int index, Mikity3dModel model) {
//    this.models.add(index, model);
//  }

//  /**
//   * Method clearConfig
//   */
//  public void clearConfiguration() {
//    this.configurations.clear();
//  }
//
//  /**
//   * Method clearModel
//   */
//  public void clearModel() {
//    this.models.clear();
//  }

  /**
   * Method getConfig
   * 
   * @param index インデックス
   * @return _configList.get(index)
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public Mikity3dConfiguration getConfiguration(int index) {
    if ((index < 0) || (index > this.configurations.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.configurations.get(index);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unqualified-field-access")
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((configurations == null) ? 0 : configurations.hashCode());
    result = prime * result + ((models == null) ? 0 : models.hashCode());
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
    if (this.configurations == null) {
      if (other.configurations != null) {
        return false;
      }
    } else if (!this.configurations.equals(other.configurations)) {
      return false;
    }
    if (this.models == null) {
      if (other.models != null) {
        return false;
      }
    } else if (!this.models.equals(other.models)) {
      return false;
    }
    return true;
  }

//  /**
//   * Method getConfig
//   * 
//   * @return mArray
//   */
//  public Mikity3dConfiguration[] getConfigurations() {
//    int size = this.configurations.size();
//    Mikity3dConfiguration[] localConfigurations = new Mikity3dConfiguration[size];
//    for (int i = 0; i < size; i++) {
//      localConfigurations[i] = this.configurations.get(i);
//    }
//    return localConfigurations;
//  }

//  /**
//   * Method getConfigAsReferenceReturns a reference to 'config'. No type checking is performed on any modications to the Collection.
//   * 
//   * @return returns a reference to the Collection.
//   */
//  public List<Mikity3dConfiguration> getConfigurationAsReference() {
//    return this.configurations;
//  }

//  /**
//   * Method getConfigCount
//   * 
//   * @return 大きさ
//   */
//  public int getConfigurationSize() {
//    return this.configurations.size();
//  }

  /**
   * Method getModel
   * 
   * @param index インデックス
   * @return (org.mklab.mikity.xml.Model) _modelList.get(index)
   * @throws java.lang.IndexOutOfBoundsException 例外
   */
  public Mikity3dModel getModel(int index) {
    if ((index < 0) || (index > this.models.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.models.get(index);
  }

//  /**
//   * Method getModel
//   * 
//   * @return mArray
//   */
//  public Mikity3dModel[] getModel() {
//    int size = this.models.size();
//    Mikity3dModel[] localModels = new Mikity3dModel[size];
//    for (int i = 0; i < size; i++) {
//      localModels[i] = this.models.get(i);
//    }
//    return localModels;
//  }

//  /**
//   * Method getModelAsReferenceReturns a reference to 'model'. No type checking is performed on any modications to the Collection.
//   * 
//   * @return returns a reference to the Collection.
//   */
//  public List<Mikity3dModel> getModelAsReference() {
//    return this.models;
//  }

//  /**
//   * Method getModelCount
//   * 
//   * @return モデルの数
//   */
//  public int loadModelSize() {
//    return this.models.size();
//  }

//  /**
//   * Method removeConfig
//   * 
//   * @param configuration コンフィグ
//   * @return removed
//   */
//  public boolean removeConfiguration(Mikity3dConfiguration configuration) {
//    boolean removed = this.configurations.remove(configuration);
//    return removed;
//  }
//
//  /**
//   * Method removeModel
//   * 
//   * @param model モデル
//   * @return removed
//   */
//  public boolean removeModel(Mikity3dModel model) {
//    boolean removed = this.models.remove(model);
//    return removed;
//  }

//  /**
//   * Method setConfig
//   * 
//   * @param index インデックス
//   * @param configuration コンフィグ
//   * @throws java.lang.IndexOutOfBoundsException 例外
//   */
//  public void setConfiguration(int index, Mikity3dConfiguration configuration) {
//    if ((index < 0) || (index > this.configurations.size())) {
//      throw new IndexOutOfBoundsException();
//    }
//    this.configurations.set(index, configuration);
//  }

//  /**
//   * Method setConfig
//   * 
//   * @param argConfigurations コンフィグの配列
//   */
//  public void setConfigurations(Mikity3dConfiguration[] argConfigurations) {
//    this.configurations.clear();
//    for (int i = 0; i < argConfigurations.length; i++) {
//      this.configurations.add(argConfigurations[i]);
//    }
//  }
  
//  /**
//   * Method setConfigAsReferenceSets the value of 'config' by setting it to the given ArrayList. No type checking is performed.
//   * 
//   * @param argConfigurations the ArrayList to copy.
//   */
//  public void setConfigurationsAsReference(List<Mikity3dConfiguration> argConfigurations) {
//    this.configurations = argConfigurations;
//  }

//  /**
//   * Method setModel
//   * 
//   * @param index インデックス
//   * @param model モデル
//   * @throws java.lang.IndexOutOfBoundsException 例外
//   */
//  public void setModel(int index, Mikity3dModel model) {
//    if ((index < 0) || (index > this.models.size())) {
//      throw new IndexOutOfBoundsException();
//    }
//    this.models.set(index, model);
//  }

//  /**
//   * Method setModel
//   * 
//   * @param argModels モデルの配列
//   */
//  public void setModel(Mikity3dModel[] argModels) {
//    this.models.clear();
//    for (int i = 0; i < argModels.length; i++) {
//      this.models.add(argModels[i]);
//    }
//  }

//  /**
//   * Method setModelAsReferenceSets the value of 'model' by setting it to the given ArrayList. No type checking is performed.
//   * 
//   * @param argModels the ArrayList to copy.
//   */
//  public void setModelAsReference(List<Mikity3dModel> argModels) {
//    this.models = argModels;
//  }

  /**
   * @return XML data
   */
  public Mikity3d getMikity3dData() {
    return this;
  }
}
