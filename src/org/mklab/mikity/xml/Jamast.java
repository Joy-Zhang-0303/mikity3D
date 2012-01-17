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
  } // -- org.mklab.mikity.xml.Mikity()

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Method addConfig
   * 
   * @param vConfig
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addConfig(org.mklab.mikity.xml.Config vConfig) throws java.lang.IndexOutOfBoundsException {
    this._configList.add(vConfig);
  } // -- void addConfig(org.mklab.mikity.xml.Config)

  /**
   * Method addConfig
   * 
   * @param index
   * @param vConfig
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addConfig(int index, org.mklab.mikity.xml.Config vConfig) throws java.lang.IndexOutOfBoundsException {
    this._configList.add(index, vConfig);
  } // -- void addConfig(int, org.mklab.mikity.xml.Config)

  /**
   * Method addModel
   * 
   * @param vModel
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addModel(org.mklab.mikity.xml.Model vModel) throws java.lang.IndexOutOfBoundsException {
    this._modelList.add(vModel);
  } // -- void addModel(org.mklab.mikity.xml.Model)

  /**
   * Method addModel
   * 
   * @param index
   * @param vModel
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addModel(int index, org.mklab.mikity.xml.Model vModel) throws java.lang.IndexOutOfBoundsException {
    this._modelList.add(index, vModel);
  } // -- void addModel(int, org.mklab.mikity.xml.Model)

  /**
   * Method clearConfig
   */
  public void clearConfig() {
    this._configList.clear();
  } // -- void clearConfig()

  /**
   * Method clearModel
   */
  public void clearModel() {
    this._modelList.clear();
  } // -- void clearModel()

  //  /**
  //   * Method enumerateConfig
  //   * 
  //   * @return org.exolab.castor.util.IteratorEnumeration(_configList.iterator())
  //   */
  //  public java.util.Enumeration<?> enumerateConfig() {
  //    return new org.exolab.castor.util.IteratorEnumeration(this._configList.iterator());
  //  } // -- java.util.Enumeration enumerateConfig()
  //
  //  /**
  //   * Method enumerateModel
  //   * 
  //   * @return org.exolab.castor.util.IteratorEnumeration(_modelList.iterator())
  //   */
  //  public java.util.Enumeration<?> enumerateModel() {
  //    return new org.exolab.castor.util.IteratorEnumeration(this._modelList.iterator());
  //  } // -- java.util.Enumeration enumerateModel()

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj
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
  } // -- boolean equals(java.lang.Object)

  /**
   * Method getConfig
   * 
   * @param index
   * @return _configList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.Config loadConfig(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._configList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._configList.get(index);
  } // -- org.mklab.mikity.xml.Config getConfig(int)

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
  } // -- org.mklab.mikity.xml.Config[] getConfig()

  /**
   * Method getConfigAsReferenceReturns a reference to 'config'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<Config> loadConfigAsReference() {
    return this._configList;
  } // -- java.util.ArrayList getConfigAsReference()

  /**
   * Method getConfigCount
   * 
   * @return _configList.size()
   */
  public int loadConfigCount() {
    return this._configList.size();
  } // -- int getConfigCount()

  /**
   * Method getModel
   * 
   * @param index
   * @return (org.mklab.mikity.xml.Model) _modelList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.Model loadModel(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._modelList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._modelList.get(index);
  } // -- org.mklab.mikity.xml.Model getModel(int)

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
  } // -- org.mklab.mikity.xml.Model[] getModel()

  /**
   * Method getModelAsReferenceReturns a reference to 'model'. No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<Model> loadModelAsReference() {
    return this._modelList;
  } // -- java.util.ArrayList getModelAsReference()

  /**
   * Method getModelCount
   * 
   * @return _modelList.size()
   */
  public int loadModelCount() {
    return this._modelList.size();
  } // -- int getModelCount()

  /**
   * Method removeConfig
   * 
   * @param vConfig
   * @return removed
   */
  public boolean removeConfig(org.mklab.mikity.xml.Config vConfig) {
    boolean removed = this._configList.remove(vConfig);
    return removed;
  } // -- boolean removeConfig(org.mklab.mikity.xml.Config)

  /**
   * Method removeModel
   * 
   * @param vModel
   * @return removed
   */
  public boolean removeModel(org.mklab.mikity.xml.Model vModel) {
    boolean removed = this._modelList.remove(vModel);
    return removed;
  } // -- boolean removeModel(org.mklab.mikity.xml.Model)

  /**
   * Method setConfig
   * 
   * @param index
   * @param vConfig
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setConfig(int index, org.mklab.mikity.xml.Config vConfig) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._configList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._configList.set(index, vConfig);
  } // -- void setConfig(int, org.mklab.mikity.xml.Config)

  /**
   * Method setConfig
   * 
   * @param configArray
   */
  public void setConfig(org.mklab.mikity.xml.Config[] configArray) {
    // -- copy array
    this._configList.clear();
    for (int i = 0; i < configArray.length; i++) {
      this._configList.add(configArray[i]);
    }
  } // -- void setConfig(org.mklab.mikity.xml.Config)

  /**
   * Method setConfigSets the value of 'config' by copying the given ArrayList.
   * 
   * @param configCollection the Vector to copy.
   */
  // public void setConfig(java.util.ArrayList configCollection)
  // {
  // //-- copy collection
  // _configList.clear();
  // for (int i = 0; i < configCollection.size(); i++) {
  // _configList.add((Config)configCollection.get(i));
  // }
  // } //-- void setConfig(java.util.ArrayList)
  /**
   * Method setConfigAsReferenceSets the value of 'config' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param configCollection the ArrayList to copy.
   */
  public void setConfigAsReference(java.util.ArrayList<Config> configCollection) {
    this._configList = configCollection;
  } // -- void setConfigAsReference(java.util.ArrayList)

  /**
   * Method setModel
   * 
   * @param index
   * @param vModel
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setModel(int index, org.mklab.mikity.xml.Model vModel) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._modelList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._modelList.set(index, vModel);
  } // -- void setModel(int, org.mklab.mikity.xml.Model)

  /**
   * Method setModel
   * 
   * @param modelArray
   */
  public void setModel(org.mklab.mikity.xml.Model[] modelArray) {
    // -- copy array
    this._modelList.clear();
    for (int i = 0; i < modelArray.length; i++) {
      this._modelList.add(modelArray[i]);
    }
  } // -- void setModel(org.mklab.mikity.xml.Model)

  /**
   * Method setModelSets the value of 'model' by copying the given ArrayList.
   * 
   * @param modelCollection the Vector to copy.
   */
  // public void setModel(java.util.ArrayList modelCollection)
  // {
  // //-- copy collection
  // _modelList.clear();
  // for (int i = 0; i < modelCollection.size(); i++) {
  // _modelList.add((org.mklab.mikity.xml.Model) modelCollection.get(i));
  // }
  // } //-- void setModel(java.util.ArrayList)
  /**
   * Method setModelAsReferenceSets the value of 'model' by setting it to the given ArrayList. No type checking is performed.
   * 
   * @param modelCollection the ArrayList to copy.
   */
  public void setModelAsReference(java.util.ArrayList<Model> modelCollection) {
    this._modelList = modelCollection;
  } // -- void setModelAsReference(java.util.ArrayList)

  /**
   * @return XML dta
   */
  public Jamast loadJamastXMLData() {
    return this;
  }
}
