/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Group.java,v 1.4 2007/12/13 10:01:55 morimune Exp $
 */

package org.mklab.mikity.xml.model;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


/**
 * Class Group.
 * 
 * @version $Revision: 1.4 $ $Date: 2007/12/13 10:01:55 $
 */
public class Group implements java.io.Serializable {

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  /**
   * Field _name
   */
  @XmlAttribute
  private java.lang.String _name;

  /**
   * Field _XMLBoxList
   */
  @XmlElement
  private java.util.ArrayList<XMLBox> _XMLBoxList;

  /**
   * Field _XMLCylinderList
   */
  @XmlElement
  private java.util.ArrayList<XMLCylinder> _XMLCylinderList;

  /**
   * Field _XMLSphereList
   */
  @XmlElement
  private java.util.ArrayList<XMLSphere> _XMLSphereList;

  /**
   * Field _XMLConeList
   */
  @XmlElement
  private java.util.ArrayList<XMLCone> _XMLConeList;

  /**
   * Field _XMLConnectorList
   */
  @XmlElement
  private java.util.ArrayList<XMLConnector> _XMLConnectorList;

  /**
   * Field _XMLTrianglePolygonList
   */
  @XmlElement
  private java.util.ArrayList<XMLTrianglePolygon> _XMLTrianglePolygonList;

  /**
   * Field _XMLQuadPolygonList
   */
  @XmlElement
  private java.util.ArrayList<XMLQuadPolygon> _XMLQuadPolygonList;

  /**
   * Field _location
   */
  @XmlElement
  private org.mklab.mikity.xml.model.Location _location;

  /**
   * Field _rotation
   */
  @XmlElement
  private org.mklab.mikity.xml.model.Rotation _rotation;

  /**
   * Field _linkdataList
   */
  @XmlElement
  private java.util.ArrayList<Linkdata> _linkdataList;

  /**
   * Field _groupList
   */
  @XmlElement
  private java.util.ArrayList<Group> _groupList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  /**
   * コンストラクター
   */
  public Group() {
    super();
    this._XMLBoxList = new ArrayList<XMLBox>();
    this._XMLCylinderList = new ArrayList<XMLCylinder>();
    this._XMLSphereList = new ArrayList<XMLSphere>();
    this._XMLConeList = new ArrayList<XMLCone>();
    this._XMLConnectorList = new ArrayList<XMLConnector>();
    this._XMLTrianglePolygonList = new ArrayList<XMLTrianglePolygon>();
    this._XMLQuadPolygonList = new ArrayList<XMLQuadPolygon>();

    this._linkdataList = new ArrayList<Linkdata>();
    this._groupList = new ArrayList<Group>();
    this._location = new Location();
    this._rotation = new Rotation();
  } // -- org.mklab.mikity.xml.Group()

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Method addGroup
   * 
   * @param vGroup
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addGroup(org.mklab.mikity.xml.model.Group vGroup) throws java.lang.IndexOutOfBoundsException {
    this._groupList.add(vGroup);
  } // -- void addGroup(org.mklab.mikity.xml.Group)

  /**
   * Method addGroup
   * 
   * @param index
   * @param vGroup
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addGroup(int index, org.mklab.mikity.xml.model.Group vGroup) throws java.lang.IndexOutOfBoundsException {
    this._groupList.add(index, vGroup);
  } // -- void addGroup(int, org.mklab.mikity.xml.Group)

  /**
   * Method addLinkdata
   * 
   * @param vLinkdata
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addLinkdata(org.mklab.mikity.xml.model.Linkdata vLinkdata) throws java.lang.IndexOutOfBoundsException {
    this._linkdataList.add(vLinkdata);
  } // -- void addLinkdata(org.mklab.mikity.xml.Linkdata)

  /**
   * Method addLinkdata
   * 
   * @param index
   * @param vLinkdata
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addLinkdata(int index, org.mklab.mikity.xml.model.Linkdata vLinkdata) throws java.lang.IndexOutOfBoundsException {
    this._linkdataList.add(index, vLinkdata);
  } // -- void addLinkdata(int, org.mklab.mikity.xml.Linkdata)

  /**
   * Method addXMLBox
   * 
   * @param vXMLBox
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLBox(org.mklab.mikity.xml.model.XMLBox vXMLBox) throws java.lang.IndexOutOfBoundsException {
    this._XMLBoxList.add(vXMLBox);
  } // -- void addXMLBox(org.mklab.mikity.xml.XMLBox)

  /**
   * Method addXMLBox
   * 
   * @param index
   * @param vXMLBox
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLBox(int index, org.mklab.mikity.xml.model.XMLBox vXMLBox) throws java.lang.IndexOutOfBoundsException {
    this._XMLBoxList.add(index, vXMLBox);
  } // -- void addXMLBox(int, org.mklab.mikity.xml.XMLBox)

  /**
   * Method addXMLCone
   * 
   * @param vXMLCone
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLCone(org.mklab.mikity.xml.model.XMLCone vXMLCone) throws java.lang.IndexOutOfBoundsException {
    this._XMLConeList.add(vXMLCone);
  } // -- void addXMLCone(org.mklab.mikity.xml.XMLCone)

  /**
   * Method addXMLCone
   * 
   * @param index
   * @param vXMLCone
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLCone(int index, org.mklab.mikity.xml.model.XMLCone vXMLCone) throws java.lang.IndexOutOfBoundsException {
    this._XMLConeList.add(index, vXMLCone);
  } // -- void addXMLCone(int, org.mklab.mikity.xml.XMLCone)

  /**
   * Method addXMLCylinder
   * 
   * @param vXMLCylinder
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLCylinder(org.mklab.mikity.xml.model.XMLCylinder vXMLCylinder) throws java.lang.IndexOutOfBoundsException {
    this._XMLCylinderList.add(vXMLCylinder);
  } // -- void addXMLCylinder(org.mklab.mikity.xml.XMLCylinder)

  /**
   * Method addXMLCylinder
   * 
   * @param index
   * @param vXMLCylinder
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLCylinder(int index, org.mklab.mikity.xml.model.XMLCylinder vXMLCylinder) throws java.lang.IndexOutOfBoundsException {
    this._XMLCylinderList.add(index, vXMLCylinder);
  } // -- void addXMLCylinder(int, org.mklab.mikity.xml.XMLCylinder)

  /**
   * Method addXMLSphere
   * 
   * @param vXMLSphere
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLSphere(org.mklab.mikity.xml.model.XMLSphere vXMLSphere) throws java.lang.IndexOutOfBoundsException {
    this._XMLSphereList.add(vXMLSphere);
  } // -- void addXMLSphere(org.mklab.mikity.xml.XMLSphere)

  /**
   * Method addXMLSphere
   * 
   * @param index
   * @param vXMLSphere
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLSphere(int index, org.mklab.mikity.xml.model.XMLSphere vXMLSphere) throws java.lang.IndexOutOfBoundsException {
    this._XMLSphereList.add(index, vXMLSphere);
  } // -- void addXMLSphere(int, org.mklab.mikity.xml.XMLSphere)

  /**
   * Method addXMLConnector
   * 
   * @param vXMLConnector
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLConnector(org.mklab.mikity.xml.model.XMLConnector vXMLConnector) throws java.lang.IndexOutOfBoundsException {
    this._XMLConnectorList.add(vXMLConnector);
  } // -- void addXMLConnector(org.mklab.mikity.xml.model.XMLConnector)

  /**
   * Method addXMLConnector
   * 
   * @param index
   * @param vXMLConnector
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLConnector(int index, org.mklab.mikity.xml.model.XMLConnector vXMLConnector) throws java.lang.IndexOutOfBoundsException {
    this._XMLConnectorList.add(index, vXMLConnector);
  } // -- void addXMLConnector(int, org.mklab.mikity.xml.XMLConnector)

  /**
   * Method addXMLTrianglePolygon
   * 
   * @param vXMLTrianglePolygon
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLTrianglePolygon(org.mklab.mikity.xml.model.XMLTrianglePolygon vXMLTrianglePolygon) throws java.lang.IndexOutOfBoundsException {
    this._XMLTrianglePolygonList.add(vXMLTrianglePolygon);
  } // -- void addXMLTrianglePolygon(int,

  // org.mklab.mikity.xml.XMLTrianglePolygon)

  /**
   * Method addXMLTrianglePolygon
   * 
   * @param index
   * @param vXMLTrianglePolygon
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLTrianglePolygon(int index, org.mklab.mikity.xml.model.XMLTrianglePolygon vXMLTrianglePolygon) throws java.lang.IndexOutOfBoundsException {
    this._XMLTrianglePolygonList.add(index, vXMLTrianglePolygon);
  } // -- void addXMLTrianglePolygon(int,

  // org.mklab.mikity.xml.XMLTrianglePolygon)

  /**
   * Method addXMLQuadPolygon
   * 
   * @param vXMLQuadPolygon
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLQuadPolygon(org.mklab.mikity.xml.model.XMLQuadPolygon vXMLQuadPolygon) throws java.lang.IndexOutOfBoundsException {
    this._XMLQuadPolygonList.add(vXMLQuadPolygon);
  } // -- void addXMLQuadPolygon(int, org.mklab.mikity.xml.XMLQuadPolygon)

  /**
   * Method addXMLQuadPolygon
   * 
   * @param index
   * @param vXMLQuadPolygon
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void addXMLQuadPolygon(int index, org.mklab.mikity.xml.model.XMLQuadPolygon vXMLQuadPolygon) throws java.lang.IndexOutOfBoundsException {
    this._XMLQuadPolygonList.add(index, vXMLQuadPolygon);
  } // -- void addXMLQuadPolygon(int, org.mklab.mikity.xml.XMLQuadPolygon)

  /**
   * Method clearGroup
   */
  public void clearGroup() {
    this._groupList.clear();
  } // -- void clearGroup()

  /**
   * Method clearLinkdata
   */
  public void clearLinkdata() {
    this._linkdataList.clear();
  } // -- void clearLinkdata()

  /**
   * Method clearXMLBox
   */
  public void clearXMLBox() {
    this._XMLBoxList.clear();
  } // -- void clearXMLBox()

  /**
   * Method clearXMLCone
   */
  public void clearXMLCone() {
    this._XMLConeList.clear();
  } // -- void clearXMLCone()

  /**
   * Method clearXMLCylinder
   */
  public void clearXMLCylinder() {
    this._XMLCylinderList.clear();
  } // -- void clearXMLCylinder()

  /**
   * Method clearXMLSphere
   */
  public void clearXMLSphere() {
    this._XMLSphereList.clear();
  } // -- void clearXMLSphere()

  /**
   * Method clearXMLConnector
   */
  public void clearXMLConnector() {
    this._XMLConnectorList.clear();
  } // -- void clearXMLConnector()

  /**
   * Method clearXMLTrianglePolygon
   */
  public void clearXMLTrianglePolygon() {
    this._XMLTrianglePolygonList.clear();
  } // -- void clearXMLTrianglePolygon()

  /**
   * Method clearXMLQuadPolygon
   */
  public void clearXMLQuadPolygon() {
    this._XMLQuadPolygonList.clear();
  } // -- void clearXMLQuadPolygon()

  /**
   * Method enumerateGroup
   * 
   * @return org.exolab.castor.util.IteratorEnumeration(_groupList.iterator())
   */
  public java.util.Enumeration<?> enumerateGroup() {
    return new org.exolab.castor.util.IteratorEnumeration(this._groupList.iterator());
  } // -- java.util.Enumeration enumerateGroup()

  /**
   * Method enumerateLinkdata
   * 
   * @return 
   *         org.exolab.castor.util.IteratorEnumeration(_linkdataList.iterator())
   */
  public java.util.Enumeration<?> enumerateLinkdata() {
    return new org.exolab.castor.util.IteratorEnumeration(this._linkdataList.iterator());
  } // -- java.util.Enumeration enumerateLinkdata()

  /**
   * Method enumerateXMLBox
   * 
   * @return org.exolab.castor.util.IteratorEnumeration(_XMLBoxList.iterator())
   */
  public java.util.Enumeration<?> enumerateXMLBox() {
    return new org.exolab.castor.util.IteratorEnumeration(this._XMLBoxList.iterator());
  } // -- java.util.Enumeration enumerateXMLBox()

  /**
   * Method enumerateXMLCone
   * 
   * @return org.exolab.castor.util.IteratorEnumeration(_XMLConeList.iterator())
   */
  public java.util.Enumeration<?> enumerateXMLCone() {
    return new org.exolab.castor.util.IteratorEnumeration(this._XMLConeList.iterator());
  } // -- java.util.Enumeration enumerateXMLCone()

  /**
   * Method enumerateXMLCylinder
   * 
   * @return 
   *         org.exolab.castor.util.IteratorEnumeration(_XMLCylinderList.iterator
   *         ())
   */
  public java.util.Enumeration<?> enumerateXMLCylinder() {
    return new org.exolab.castor.util.IteratorEnumeration(this._XMLCylinderList.iterator());
  } // -- java.util.Enumeration enumerateXMLCylinder()

  /**
   * Method enumerateXMLSphere
   * 
   * @return 
   *         org.exolab.castor.util.IteratorEnumeration(_XMLSphereList.iterator()
   *         )
   */
  public java.util.Enumeration<?> enumerateXMLSphere() {
    return new org.exolab.castor.util.IteratorEnumeration(this._XMLSphereList.iterator());
  } // -- java.util.Enumeration enumerateXMLSphere()

  /**
   * Method enumerateXMLConnector
   * 
   * @return 
   *         org.exolab.castor.util.IteratorEnumeration(_XMLConnectorList.iterator
   *         ())
   */
  public java.util.Enumeration<?> enumerateXMLConnector() {
    return new org.exolab.castor.util.IteratorEnumeration(this._XMLConnectorList.iterator());
  } // -- java.util.Enumeration enumerateXMLConnector()

  /**
   * Method enumerateXMLTrianglePolygon
   * 
   * @return org.exolab.castor.util.IteratorEnumeration(_XMLTrianglePolygonList.
   *         iterator())
   */
  public java.util.Enumeration<?> enumerateXMLTrianglePolygon() {
    return new org.exolab.castor.util.IteratorEnumeration(this._XMLTrianglePolygonList.iterator());
  } // -- java.util.Enumeration enumerateXMLTrianglePolygon()

  /**
   * Method enumerateXMLQuadPolygon
   * 
   * @return 
   *         org.exolab.castor.util.IteratorEnumeration(_XMLQuadPolygonList.iterator
   *         ())
   */
  public java.util.Enumeration<?> enumerateXMLQuadPolygon() {
    return new org.exolab.castor.util.IteratorEnumeration(this._XMLQuadPolygonList.iterator());
  } // -- java.util.Enumeration enumerateXMLQuadPolygon()

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj
   */
  @Override
  public boolean equals(java.lang.Object obj) {
    if (this == obj) return true;

    if (obj instanceof Group) {

      Group temp = (Group)obj;
      if (this._name != null) {
        if (temp._name == null) return false;
        else if (!(this._name.equals(temp._name))) return false;
      } else if (temp._name != null) return false;
      if (this._XMLBoxList != null) {
        if (temp._XMLBoxList == null) return false;
        else if (!(this._XMLBoxList.equals(temp._XMLBoxList))) return false;
      } else if (temp._XMLBoxList != null) return false;
      if (this._XMLCylinderList != null) {
        if (temp._XMLCylinderList == null) return false;
        else if (!(this._XMLCylinderList.equals(temp._XMLCylinderList))) return false;
      } else if (temp._XMLCylinderList != null) return false;
      if (this._XMLSphereList != null) {
        if (temp._XMLSphereList == null) return false;
        else if (!(this._XMLSphereList.equals(temp._XMLSphereList))) return false;
      } else if (temp._XMLSphereList != null) return false;
      if (this._XMLConeList != null) {
        if (temp._XMLConeList == null) return false;
        else if (!(this._XMLConeList.equals(temp._XMLConeList))) return false;
      } else if (temp._XMLConeList != null) return false;
      if (this._XMLConnectorList != null) {
        if (temp._XMLConnectorList == null) return false;
        else if (!(this._XMLConnectorList.equals(temp._XMLConnectorList))) return false;
      } else if (temp._XMLConnectorList != null) return false;

      if (this._XMLTrianglePolygonList != null) {
        if (temp._XMLTrianglePolygonList == null) return false;
        else if (!(this._XMLTrianglePolygonList.equals(temp._XMLTrianglePolygonList))) return false;
      } else if (temp._XMLTrianglePolygonList != null) return false;

      if (this._XMLQuadPolygonList != null) {
        if (temp._XMLQuadPolygonList == null) return false;
        else if (!(this._XMLQuadPolygonList.equals(temp._XMLQuadPolygonList))) return false;
      } else if (temp._XMLQuadPolygonList != null) return false;

      if (this._location != null) {
        if (temp._location == null) return false;
        else if (!(this._location.equals(temp._location))) return false;
      } else if (temp._location != null) return false;
      if (this._rotation != null) {
        if (temp._rotation == null) return false;
        else if (!(this._rotation.equals(temp._rotation))) return false;
      } else if (temp._rotation != null) return false;
      if (this._linkdataList != null) {
        if (temp._linkdataList == null) return false;
        else if (!(this._linkdataList.equals(temp._linkdataList))) return false;
      } else if (temp._linkdataList != null) return false;
      if (this._groupList != null) {
        if (temp._groupList == null) return false;
        else if (!(this._groupList.equals(temp._groupList))) return false;
      } else if (temp._groupList != null) return false;
      return true;
    }
    return false;
  } // -- boolean equals(java.lang.Object)

  /**
   * Method getGroup
   * 
   * @param index
   * @return _groupList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.model.Group loadGroup(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._groupList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._groupList.get(index);
  } // -- org.mklab.mikity.xml.Group getGroup(int)

  /**
   * Method getGroup
   * 
   * @return mArray
   */
  public org.mklab.mikity.xml.model.Group[] loadGroup() {
    int size = this._groupList.size();
    org.mklab.mikity.xml.model.Group[] mArray = new org.mklab.mikity.xml.model.Group[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._groupList.get(index);
    }
    return mArray;
  } // -- org.mklab.mikity.xml.Group[] getGroup()

  /**
   * Method getGroupAsReferenceReturns a reference to 'group'. No type checking
   * is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<Group> loadGroupAsReference() {
    return this._groupList;
  } // -- java.util.ArrayList getGroupAsReference()

  /**
   * Method getGroupCount
   * 
   * @return _groupList.size()
   */
  public int loadGroupCount() {
    return this._groupList.size();
  } // -- int getGroupCount()

  /**
   * Method getLinkdata
   * 
   * @param index
   * @return _linkdataList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.model.Linkdata loadLinkdata(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._linkdataList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._linkdataList.get(index);
  } // -- org.mklab.mikity.xml.Linkdata getLinkdata(int)

  /**
   * Method getLinkdata
   * 
   * @return mArray
   */
  public org.mklab.mikity.xml.model.Linkdata[] loadLinkdata() {
    int size = this._linkdataList.size();
    org.mklab.mikity.xml.model.Linkdata[] mArray = new org.mklab.mikity.xml.model.Linkdata[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._linkdataList.get(index);
    }
    return mArray;
  } // -- org.mklab.mikity.xml.Linkdata[] getLinkdata()

  /**
   * Method getLinkdataAsReferenceReturns a reference to 'linkdata'. No type
   * checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<Linkdata> loadLinkdataAsReference() {
    return this._linkdataList;
  } // -- java.util.ArrayList getLinkdataAsReference()

  /**
   * Method getLinkdataCount
   * 
   * @return _linkdataList.size()
   */
  public int loadLinkdataCount() {
    return this._linkdataList.size();
  } // -- int getLinkdataCount()

  /**
   * Returns the value of field 'location'.
   * 
   * @return the value of field 'location'.
   */
  public org.mklab.mikity.xml.model.Location loadLocation() {
    return this._location;
  } // -- org.mklab.mikity.xml.Location getLocation()

  /**
   * Returns the value of field 'name'.
   * 
   * @return the value of field 'name'.
   */
  public java.lang.String loadName() {
    return this._name;
  } // -- java.lang.String getName()

  /**
   * Returns the value of field 'rotation'.
   * 
   * @return the value of field 'rotation'.
   */
  public org.mklab.mikity.xml.model.Rotation loadRotation() {
    return this._rotation;
  } // -- org.mklab.mikity.xml.Rotation getRotation()

  /**
   * Method getXMLBox
   * 
   * @param index
   * @return _XMLBoxList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.model.XMLBox loadXMLBox(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLBoxList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLBoxList.get(index);
  } // -- org.mklab.mikity.xml.XMLBox getXMLBox(int)

  /**
   * Method getXMLBox
   * 
   * @return mArray
   */
  public org.mklab.mikity.xml.model.XMLBox[] loadXMLBox() {
    int size = this._XMLBoxList.size();
    org.mklab.mikity.xml.model.XMLBox[] mArray = new org.mklab.mikity.xml.model.XMLBox[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._XMLBoxList.get(index);
    }
    return mArray;
  } // -- org.mklab.mikity.xml.XMLBox[] getXMLBox()

  /**
   * Method getXMLBoxAsReferenceReturns a reference to 'XMLBox'. No type
   * checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<XMLBox> loadXMLBoxAsReference() {
    return this._XMLBoxList;
  } // -- java.util.ArrayList getXMLBoxAsReference()

  /**
   * Method getXMLBoxCount
   * 
   * @return _XMLBoxList.size()
   */
  public int loadXMLBoxCount() {
    return this._XMLBoxList.size();
  } // -- int getXMLBoxCount()

  /**
   * Method getXMLCone
   * 
   * @param index
   * @return _XMLConeList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.model.XMLCone loadXMLCone(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLConeList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLConeList.get(index);
  } // -- org.mklab.mikity.xml.XMLCone getXMLCone(int)

  /**
   * Method getXMLCone
   * 
   * @return mArray
   */
  public org.mklab.mikity.xml.model.XMLCone[] loadXMLCone() {
    int size = this._XMLConeList.size();
    org.mklab.mikity.xml.model.XMLCone[] mArray = new org.mklab.mikity.xml.model.XMLCone[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._XMLConeList.get(index);
    }
    return mArray;
  } // -- org.mklab.mikity.xml.XMLCone[] getXMLCone()

  /**
   * Method getXMLConeAsReferenceReturns a reference to 'XMLCone'. No type
   * checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<XMLCone> loadXMLConeAsReference() {
    return this._XMLConeList;
  } // -- java.util.ArrayList getXMLConeAsReference()

  /**
   * Method getXMLConeCount
   * 
   * @return _XMLConeList.size()
   */
  public int loadXMLConeCount() {
    return this._XMLConeList.size();
  } // -- int getXMLConeCount()

  /**
   * Method getXMLCylinder
   * 
   * @param index
   * @return _XMLCylinderList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.model.XMLCylinder loadXMLCylinder(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLCylinderList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLCylinderList.get(index);
  } // -- org.mklab.mikity.xml.XMLCylinder getXMLCylinder(int)

  /**
   * Method getXMLCylinder
   * 
   * @return _XMLCylinderList.get(index)
   */
  public org.mklab.mikity.xml.model.XMLCylinder[] loadXMLCylinder() {
    int size = this._XMLCylinderList.size();
    org.mklab.mikity.xml.model.XMLCylinder[] mArray = new org.mklab.mikity.xml.model.XMLCylinder[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._XMLCylinderList.get(index);
    }
    return mArray;
  } // -- org.mklab.mikity.xml.XMLCylinder[] getXMLCylinder()

  /**
   * Method getXMLCylinderAsReferenceReturns a reference to 'XMLCylinder'. No
   * type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<XMLCylinder> loadXMLCylinderAsReference() {
    return this._XMLCylinderList;
  } // -- java.util.ArrayList getXMLCylinderAsReference()

  /**
   * Method getXMLCylinderCount
   * 
   * @return _XMLCylinderList.size()
   */
  public int loadXMLCylinderCount() {
    return this._XMLCylinderList.size();
  } // -- int getXMLCylinderCount()

  /**
   * Method getXMLSphere
   * 
   * @param index
   * @return _XMLSphereList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.model.XMLSphere loadXMLSphere(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLSphereList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLSphereList.get(index);
  } // -- org.mklab.mikity.xml.XMLSphere getXMLSphere(int)

  /**
   * Method getXMLSphere
   * 
   * @return mArray
   */
  public org.mklab.mikity.xml.model.XMLSphere[] loadXMLSphere() {
    int size = this._XMLSphereList.size();
    org.mklab.mikity.xml.model.XMLSphere[] mArray = new org.mklab.mikity.xml.model.XMLSphere[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._XMLSphereList.get(index);
    }
    return mArray;
  } // -- org.mklab.mikity.xml.XMLSphere[] getXMLSphere()

  /**
   * Method getXMLSphereAsReferenceReturns a reference to 'XMLSphere'. No type
   * checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<XMLSphere> loadXMLSphereAsReference() {
    return this._XMLSphereList;
  } // -- java.util.ArrayList getXMLSphereAsReference()

  /**
   * Method getXMLSphereCount
   * 
   * @return _XMLSphereList.size()
   */
  public int loadXMLSphereCount() {
    return this._XMLSphereList.size();
  } // -- int getXMLSphereCount()

  /**
   * Method getXMLConnector
   * 
   * @param index
   * @return _XMLConeList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.model.XMLConnector loadXMLConnector(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLConnectorList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLConnectorList.get(index);
  } // -- org.mklab.mikity.xml.XMLConnector getXMLCone(int)

  /**
   * Method getXMLConnector
   * 
   * @return mArray
   */
  public org.mklab.mikity.xml.model.XMLConnector[] loadXMLConnector() {
    int size = this._XMLConnectorList.size();
    org.mklab.mikity.xml.model.XMLConnector[] mArray = new org.mklab.mikity.xml.model.XMLConnector[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._XMLConnectorList.get(index);
    }
    return mArray;
  } // -- org.mklab.mikity.xml.XMLConnector[] getXMLCone()

  /**
   * Method getXMLConnectorAsReferenceReturns a reference to 'XMLCone'. No type
   * checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<XMLConnector> loadXMLConnectorAsReference() {
    return this._XMLConnectorList;
  } // -- java.util.ArrayList getXMLConnectorAsReference()

  /**
   * Method getXMLConnectorCount
   * 
   * @return _XMLConnectorList.size()
   */
  public int loadXMLConnectorCount() {
    return this._XMLConnectorList.size();
  } // -- int getXMLConnectorCount()

  /**
   * Method getXMLTrianglePolygon
   * 
   * @param index
   * @return _XMLTrianglePolygonList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.model.XMLTrianglePolygon loadXMLTrianglePolygon(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLTrianglePolygonList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLTrianglePolygonList.get(index);
  } // -- org.mklab.mikity.xml.XMLTrianglePolygon getXMLTrianglePolygon(int)

  /**
   * Method getXMLTrianglePolygon
   * 
   * @return mArray
   */
  public org.mklab.mikity.xml.model.XMLTrianglePolygon[] loadXMLTrianglePolygon() {
    int size = this._XMLTrianglePolygonList.size();
    org.mklab.mikity.xml.model.XMLTrianglePolygon[] mArray = new org.mklab.mikity.xml.model.XMLTrianglePolygon[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._XMLTrianglePolygonList.get(index);
    }
    return mArray;
  } // -- org.mklab.mikity.xml.XMLTrianglePolygon[] getXMLTrianglePolygon()

  /**
   * Method getXMLTrianglePolygonAsReferenceReturns a reference to
   * 'XMLTrianglePolygon'. No type checking is performed on any modications to
   * the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<XMLTrianglePolygon> loadXMLTrianglePolygonAsReference() {
    return this._XMLTrianglePolygonList;
  } // -- java.util.ArrayList getXMLTrianglePolygonAsReference()

  /**
   * Method getXMLTrianglePolygonCount
   * 
   * @return _XMLTrianglePolygonList.size()
   */
  public int loadXMLTrianglePolygonCount() {
    return this._XMLTrianglePolygonList.size();
  } // -- int getXMLQuadPolygonCount()

  /**
   * Method getXMLQuadPolygon
   * 
   * @param index
   * @return _XMLQuadPolygonList.get(index)
   * @throws java.lang.IndexOutOfBoundsException
   */
  public org.mklab.mikity.xml.model.XMLQuadPolygon loadXMLQuadPolygon(int index) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLQuadPolygonList.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this._XMLQuadPolygonList.get(index);
  } // -- org.mklab.mikity.xml.XMLQuadPolygon getXMLQuadPolygon(int)

  /**
   * Method getXMLQuadPolygon
   * 
   * @return mArray
   */
  public org.mklab.mikity.xml.model.XMLQuadPolygon[] loadXMLQuadPolygon() {
    int size = this._XMLQuadPolygonList.size();
    org.mklab.mikity.xml.model.XMLQuadPolygon[] mArray = new org.mklab.mikity.xml.model.XMLQuadPolygon[size];
    for (int index = 0; index < size; index++) {
      mArray[index] = this._XMLQuadPolygonList.get(index);
    }
    return mArray;
  } // -- org.mklab.mikity.xml.XMLQuadPolygon[] getXMLQuadPolygon()

  /**
   * Method getXMLQuadPolygonAsReferenceReturns a reference to 'XMLQuadPolygon'.
   * No type checking is performed on any modications to the Collection.
   * 
   * @return returns a reference to the Collection.
   */
  public java.util.ArrayList<XMLQuadPolygon> loadXMLQuadPolygonAsReference() {
    return this._XMLQuadPolygonList;
  } // -- java.util.ArrayList getXMLQuadPolygonAsReference()

  /**
   * Method getXMLQuadPolygonCount
   * 
   * @return _XMLQuadPolygonList.size()
   */
  public int loadXMLQuadPolygonCount() {
    return this._XMLQuadPolygonList.size();
  } // -- int getXMLQuadPolygonCount()

  /**
   * Method removeGroup
   * 
   * @param vGroup
   * @return removed
   */
  public boolean removeGroup(org.mklab.mikity.xml.model.Group vGroup) {
    boolean removed = this._groupList.remove(vGroup);
    return removed;
  } // -- boolean removeGroup(org.mklab.mikity.xml.Group)

  /**
   * Method removeLinkdata
   * 
   * @param vLinkdata
   * @return removed
   */
  public boolean removeLinkdata(org.mklab.mikity.xml.model.Linkdata vLinkdata) {
    boolean removed = this._linkdataList.remove(vLinkdata);
    return removed;
  } // -- boolean removeLinkdata(org.mklab.mikity.xml.Linkdata)

  /**
   * Method removeXMLBox
   * 
   * @param vXMLBox
   * @return removed
   */
  public boolean removeXMLBox(org.mklab.mikity.xml.model.XMLBox vXMLBox) {
    boolean removed = this._XMLBoxList.remove(vXMLBox);
    return removed;
  } // -- boolean removeXMLBox(org.mklab.mikity.xml.XMLBox)

  /**
   * Method removeXMLCone
   * 
   * @param vXMLCone
   * @return removed
   */
  public boolean removeXMLCone(org.mklab.mikity.xml.model.XMLCone vXMLCone) {
    boolean removed = this._XMLConeList.remove(vXMLCone);
    return removed;
  } // -- boolean removeXMLCone(org.mklab.mikity.xml.XMLCone)

  /**
   * Method removeXMLCylinder
   * 
   * @param vXMLCylinder
   * @return removed
   */
  public boolean removeXMLCylinder(org.mklab.mikity.xml.model.XMLCylinder vXMLCylinder) {
    boolean removed = this._XMLCylinderList.remove(vXMLCylinder);
    return removed;
  } // -- boolean removeXMLCylinder(org.mklab.mikity.xml.XMLCylinder)

  /**
   * Method removeXMLSphere
   * 
   * @param vXMLSphere
   * @return removed
   */
  public boolean removeXMLSphere(org.mklab.mikity.xml.model.XMLSphere vXMLSphere) {
    boolean removed = this._XMLSphereList.remove(vXMLSphere);
    return removed;
  } // -- boolean removeXMLSphere(org.mklab.mikity.xml.XMLSphere)

  /**
   * Method removeXMLConnector
   * 
   * @param vXMLConnector
   * @return removed
   */
  public boolean removeXMLConnector(org.mklab.mikity.xml.model.XMLConnector vXMLConnector) {
    boolean removed = this._XMLConnectorList.remove(vXMLConnector);
    return removed;
  } // -- boolean removeXMLCone(org.mklab.mikity.xml.XMLConnector)

  /**
   * Method removeXMLTrianglePolygon
   * 
   * @param vXMLTrianglePolygon
   * @return removed
   */
  public boolean removeXMLTrianglePolygon(org.mklab.mikity.xml.model.XMLTrianglePolygon vXMLTrianglePolygon) {
    boolean removed = this._XMLTrianglePolygonList.remove(vXMLTrianglePolygon);
    return removed;
  } // -- boolean removeXMLCone(org.mklab.mikity.xml.XMLTrianglePolygon)

  /**
   * Method removeXMLQuadPolygon
   * 
   * @param vXMLQuadPolygon
   * @return removed
   */
  public boolean removeXMLQuadPolygon(org.mklab.mikity.xml.model.XMLQuadPolygon vXMLQuadPolygon) {
    boolean removed = this._XMLQuadPolygonList.remove(vXMLQuadPolygon);
    return removed;
  } // -- boolean removeXMLCone(org.mklab.mikity.xml.XMLQuadPolygon)

  /**
   * Method setGroup
   * 
   * @param index
   * @param vGroup
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setGroup(int index, org.mklab.mikity.xml.model.Group vGroup) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._groupList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._groupList.set(index, vGroup);
  } // -- void setGroup(int, org.mklab.mikity.xml.Group)

  /**
   * Method setGroup
   * 
   * @param groupArray
   */
  public void setGroup(org.mklab.mikity.xml.model.Group[] groupArray) {
    // -- copy array
    this._groupList.clear();
    for (int i = 0; i < groupArray.length; i++) {
      this._groupList.add(groupArray[i]);
    }
  } // -- void setGroup(org.mklab.mikity.xml.Group)

  /**
   * Method setGroupSets the value of 'group' by copying the given ArrayList.
   * 
   * @param groupCollection
   *        the Vector to copy.
   */
  public void setGroup(java.util.ArrayList<Group> groupCollection) {
    // -- copy collection
    this._groupList.clear();
    for (int i = 0; i < groupCollection.size(); i++) {
      this._groupList.add(groupCollection.get(i));
    }
  } // -- void setGroup(java.util.ArrayList)

  /**
   * Method setGroupAsReferenceSets the value of 'group' by setting it to the
   * given ArrayList. No type checking is performed.
   * 
   * @param groupCollection
   *        the ArrayList to copy.
   */
  public void setGroupAsReference(java.util.ArrayList<Group> groupCollection) {
    this._groupList = groupCollection;
  } // -- void setGroupAsReference(java.util.ArrayList)

  /**
   * Method setLinkdata
   * 
   * @param index
   * @param vLinkdata
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setLinkdata(int index, org.mklab.mikity.xml.model.Linkdata vLinkdata) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._linkdataList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._linkdataList.set(index, vLinkdata);
  } // -- void setLinkdata(int, org.mklab.mikity.xml.Linkdata)

  /**
   * Method setLinkdata
   * 
   * @param linkdataArray
   */
  public void setLinkdata(org.mklab.mikity.xml.model.Linkdata[] linkdataArray) {
    // -- copy array
    this._linkdataList.clear();
    for (int i = 0; i < linkdataArray.length; i++) {
      this._linkdataList.add(linkdataArray[i]);
    }
  } // -- void setLinkdata(org.mklab.mikity.xml.Linkdata)

  /**
   * Method setLinkdataSets the value of 'linkdata' by copying the given
   * ArrayList.
   * 
   * @param linkdataCollection
   *        the Vector to copy.
   */
  // public void setLinkdata(java.util.ArrayList linkdataCollection)
  // {
  // //-- copy collection
  // _linkdataList.clear();
  // for (int i = 0; i < linkdataCollection.size(); i++) {
  // _linkdataList.add((Linkdata)linkdataCollection.get(i));
  // }
  // } //-- void setLinkdata(java.util.ArrayList)
  /**
   * Method setLinkdataAsReferenceSets the value of 'linkdata' by setting it to
   * the given ArrayList. No type checking is performed.
   * 
   * @param linkdataCollection
   *        the ArrayList to copy.
   */
  public void setLinkdataAsReference(java.util.ArrayList<Linkdata> linkdataCollection) {
    this._linkdataList = linkdataCollection;
  } // -- void setLinkdataAsReference(java.util.ArrayList)

  /**
   * Sets the value of field 'location'.
   * 
   * @param location
   *        the value of field 'location'.
   */
  public void setLocation(org.mklab.mikity.xml.model.Location location) {
    this._location = location;
  } // -- void setLocation(org.mklab.mikity.xml.Location)

  /**
   * Sets the value of field 'name'.
   * 
   * @param name
   *        the value of field 'name'.
   */
  public void setName(java.lang.String name) {
    this._name = name;
  } // -- void setName(java.lang.String)

  /**
   * Sets the value of field 'rotation'.
   * 
   * @param rotation
   *        the value of field 'rotation'.
   */
  public void setRotation(org.mklab.mikity.xml.model.Rotation rotation) {
    this._rotation = rotation;
  } // -- void setRotation(org.mklab.mikity.xml.Rotation)

  /**
   * Method setXMLBox
   * 
   * @param index
   * @param vXMLBox
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setXMLBox(int index, org.mklab.mikity.xml.model.XMLBox vXMLBox) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLBoxList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLBoxList.set(index, vXMLBox);
  } // -- void setXMLBox(int, org.mklab.mikity.xml.XMLBox)

  /**
   * Method setXMLBox
   * 
   * @param XMLBoxArray
   */
  public void setXMLBox(org.mklab.mikity.xml.model.XMLBox[] XMLBoxArray) {
    // -- copy array
    this._XMLBoxList.clear();
    for (int i = 0; i < XMLBoxArray.length; i++) {
      this._XMLBoxList.add(XMLBoxArray[i]);
    }
  } // -- void setXMLBox(org.mklab.mikity.xml.XMLBox)

  /**
   * Method setXMLBoxSets the value of 'XMLBox' by copying the given ArrayList.
   * 
   * @param XMLBoxCollection
   *        the Vector to copy.
   */
  // public void setXMLBox(java.util.ArrayList XMLBoxCollection)
  // {
  // //-- copy collection
  // _XMLBoxList.clear();
  // for (int i = 0; i < XMLBoxCollection.size(); i++) {
  // _XMLBoxList.add((org.mklab.mikity.xml.model.XMLBox)
  // XMLBoxCollection.get(i));
  // }
  // } //-- void setXMLBox(java.util.ArrayList)
  /**
   * Method setXMLBoxAsReferenceSets the value of 'XMLBox' by setting it to the
   * given ArrayList. No type checking is performed.
   * 
   * @param XMLBoxCollection
   *        the ArrayList to copy.
   */
  public void setXMLBoxAsReference(java.util.ArrayList<XMLBox> XMLBoxCollection) {
    this._XMLBoxList = XMLBoxCollection;
  } // -- void setXMLBoxAsReference(java.util.ArrayList)

  /**
   * Method setXMLCone
   * 
   * @param index
   * @param vXMLCone
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setXMLCone(int index, org.mklab.mikity.xml.model.XMLCone vXMLCone) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLConeList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLConeList.set(index, vXMLCone);
  } // -- void setXMLCone(int, org.mklab.mikity.xml.XMLCone)

  /**
   * Method setXMLCone
   * 
   * @param XMLConeArray
   */
  public void setXMLCone(org.mklab.mikity.xml.model.XMLCone[] XMLConeArray) {
    // -- copy array
    this._XMLConeList.clear();
    for (int i = 0; i < XMLConeArray.length; i++) {
      this._XMLConeList.add(XMLConeArray[i]);
    }
  } // -- void setXMLCone(org.mklab.mikity.xml.XMLCone)

  /**
   * Method setXMLConeSets the value of 'XMLCone' by copying the given
   * ArrayList.
   * 
   * @param XMLConeCollection
   *        the Vector to copy.
   */
  // public void setXMLCone(java.util.ArrayList XMLConeCollection)
  // {
  // //-- copy collection
  // _XMLConeList.clear();
  // for (int i = 0; i < XMLConeCollection.size(); i++) {
  //_XMLConeList.add((org.mklab.mikity.xml.model.XMLCone)XMLConeCollection.get(i
  // ));
  // }
  // } //-- void setXMLCone(java.util.ArrayList)
  /**
   * Method setXMLConeAsReferenceSets the value of 'XMLCone' by setting it to
   * the given ArrayList. No type checking is performed.
   * 
   * @param XMLConeCollection
   *        the ArrayList to copy.
   */
  public void setXMLConeAsReference(java.util.ArrayList<XMLCone> XMLConeCollection) {
    this._XMLConeList = XMLConeCollection;
  } // -- void setXMLConeAsReference(java.util.ArrayList)

  /**
   * Method setXMLCylinder
   * 
   * @param index
   * @param vXMLCylinder
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setXMLCylinder(int index, org.mklab.mikity.xml.model.XMLCylinder vXMLCylinder) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLCylinderList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLCylinderList.set(index, vXMLCylinder);
  } // -- void setXMLCylinder(int, org.mklab.mikity.xml.XMLCylinder)

  /**
   * Method setXMLCylinder
   * 
   * @param XMLCylinderArray
   */
  public void setXMLCylinder(org.mklab.mikity.xml.model.XMLCylinder[] XMLCylinderArray) {
    // -- copy array
    this._XMLCylinderList.clear();
    for (int i = 0; i < XMLCylinderArray.length; i++) {
      this._XMLCylinderList.add(XMLCylinderArray[i]);
    }
  } // -- void setXMLCylinder(org.mklab.mikity.xml.XMLCylinder)

  /**
   * Method setXMLCylinderSets the value of 'XMLCylinder' by copying the given
   * ArrayList.
   * 
   * @param XMLCylinderCollection
   *        the Vector to copy.
   */
  // public void setXMLCylinder(java.util.ArrayList XMLCylinderCollection)
  // {
  // //-- copy collection
  // _XMLCylinderList.clear();
  // for (int i = 0; i < XMLCylinderCollection.size(); i++) {
  // _XMLCylinderList.add((org.mklab.mikity.xml.model.XMLCylinder)
  // XMLCylinderCollection.get(i));
  // }
  // } //-- void setXMLCylinder(java.util.ArrayList)
  /**
   * Method setXMLCylinderAsReferenceSets the value of 'XMLCylinder' by setting
   * it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLCylinderCollection
   *        the ArrayList to copy.
   */
  public void setXMLCylinderAsReference(java.util.ArrayList<XMLCylinder> XMLCylinderCollection) {
    this._XMLCylinderList = XMLCylinderCollection;
  } // -- void setXMLCylinderAsReference(java.util.ArrayList)

  /**
   * Method setXMLSphere
   * 
   * @param index
   * @param vXMLSphere
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setXMLSphere(int index, org.mklab.mikity.xml.model.XMLSphere vXMLSphere) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLSphereList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLSphereList.set(index, vXMLSphere);
  } // -- void setXMLSphere(int, org.mklab.mikity.xml.XMLSphere)

  /**
   * Method setXMLSphere
   * 
   * @param XMLSphereArray
   */
  public void setXMLSphere(org.mklab.mikity.xml.model.XMLSphere[] XMLSphereArray) {
    // -- copy array
    this._XMLSphereList.clear();
    for (int i = 0; i < XMLSphereArray.length; i++) {
      this._XMLSphereList.add(XMLSphereArray[i]);
    }
  } // -- void setXMLSphere(org.mklab.mikity.xml.XMLSphere)

  /**
   * Method setXMLSphereSets the value of 'XMLSphere' by copying the given
   * ArrayList.
   * 
   * @param XMLSphereCollection
   *        the Vector to copy.
   */
  // public void setXMLSphere(java.util.ArrayList XMLSphereCollection)
  // {
  // //-- copy collection
  // _XMLSphereList.clear();
  // for (int i = 0; i < XMLSphereCollection.size(); i++) {
  // _XMLSphereList.add((org.mklab.mikity.xml.model.XMLSphere)
  // XMLSphereCollection.get(i));
  // }
  // } //-- void setXMLSphere(java.util.ArrayList)
  /**
   * Method setXMLSphereAsReferenceSets the value of 'XMLSphere' by setting it
   * to the given ArrayList. No type checking is performed.
   * 
   * @param XMLSphereCollection
   *        the ArrayList to copy.
   */
  public void setXMLSphereAsReference(java.util.ArrayList<XMLSphere> XMLSphereCollection) {
    this._XMLSphereList = XMLSphereCollection;
  } // -- void setXMLSphereAsReference(java.util.ArrayList)

  /**
   * Method setXMLConnector
   * 
   * @param index
   * @param vXMLConnector
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setXMLConnector(int index, org.mklab.mikity.xml.model.XMLConnector vXMLConnector) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLConnectorList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLConnectorList.set(index, vXMLConnector);
  } // -- void setXMLConnector(int, org.mklab.mikity.xml.XMLConnector)

  /**
   * Method setXMLConnector
   * 
   * @param XMLConnectorArray
   */
  public void setXMLConnector(org.mklab.mikity.xml.model.XMLConnector[] XMLConnectorArray) {
    // -- copy array
    this._XMLConnectorList.clear();
    for (int i = 0; i < XMLConnectorArray.length; i++) {
      this._XMLConnectorList.add(XMLConnectorArray[i]);
    }
  } // -- void setXMLConnector(org.mklab.mikity.xml.XMLConnector)

  // /**
  // * Method setXMLConnectorSets the value of 'XMLConnector' by copying the
  // * given ArrayList.
  // *
  // * @param XMLConnectorCollection the Vector to copy.
  // */
  // public void setXMLConnector(java.util.ArrayList XMLConnectorCollection)
  // {
  // //-- copy collection
  // _XMLConnectorList.clear();
  // for (int i = 0; i < XMLConnectorCollection.size(); i++) {
  // _XMLConnectorList.add((org.mklab.mikity.xml.model.XMLConnector)
  // XMLConnectorCollection.get(i));
  // }
  // } //-- void setXMLConnector(java.util.ArrayList)

  /**
   * Method setXMLConnectorAsReferenceSets the value of 'XMLConnector' by
   * setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLConnectorCollection
   *        the ArrayList to copy.
   */
  public void setXMLConnectorAsReference(java.util.ArrayList<XMLConnector> XMLConnectorCollection) {
    this._XMLConnectorList = XMLConnectorCollection;
  } // -- void setXMLConnectorAsReference(java.util.ArrayList)

  /**
   * Method setXMLTrianglePolygon
   * 
   * @param index
   * @param vXMLTrianglePolygon
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setXMLTrianglePolygon(int index, org.mklab.mikity.xml.model.XMLTrianglePolygon vXMLTrianglePolygon) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLTrianglePolygonList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLTrianglePolygonList.set(index, vXMLTrianglePolygon);
  } // -- void setXMLTrianglePolygon(int,

  // org.mklab.mikity.xml.XMLTrianglePolygon)

  /**
   * Method setXMLTrianglePolygon
   * 
   * @param XMLTrianglePolygonArray
   */
  public void setXMLTrianglePolygon(org.mklab.mikity.xml.model.XMLTrianglePolygon[] XMLTrianglePolygonArray) {
    // -- copy array
    this._XMLTrianglePolygonList.clear();
    for (int i = 0; i < XMLTrianglePolygonArray.length; i++) {
      this._XMLTrianglePolygonList.add(XMLTrianglePolygonArray[i]);
    }
  } // -- void setXMLTrianglePolygon(org.mklab.mikity.xml.XMLTrianglePolygon)

  // /**
  // * Method setXMLTrianglePolygonSets the value of 'XMLTrianglePolygon' by
  // copying the
  // * given ArrayList.
  // *
  // * @param XMLTrianglePolygonCollection the Vector to copy.
  // */
  // public void setXMLTrianglePolygon(java.util.ArrayList
  // XMLTrianglePolygonCollection)
  // {
  // //-- copy collection
  // _XMLTrianglePolygonList.clear();
  // for (int i = 0; i < XMLTrianglePolygonCollection.size(); i++) {
  // _XMLTrianglePolygonList.add((org.mklab.mikity.xml.model.XMLTrianglePolygon)
  // XMLTrianglePolygonCollection.get(i));
  // }
  // } //-- void setXMLTrianglePolygon(java.util.ArrayList)

  /**
   * Method setXMLTrianglePolygonAsReferenceSets the value of
   * 'XMLTrianglePolygon' by setting it to the given ArrayList. No type checking
   * is performed.
   * 
   * @param XMLTrianglePolygonCollection
   *        the ArrayList to copy.
   */
  public void setXMLTrianglePolygonAsReference(java.util.ArrayList<XMLTrianglePolygon> XMLTrianglePolygonCollection) {
    this._XMLTrianglePolygonList = XMLTrianglePolygonCollection;
  } // -- void setXMLTrianglePolygonAsReference(java.util.ArrayList)

  /**
   * Method setXMLQuadPolygon
   * 
   * @param index
   * @param vXMLQuadPolygon
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void setXMLQuadPolygon(int index, org.mklab.mikity.xml.model.XMLQuadPolygon vXMLQuadPolygon) throws java.lang.IndexOutOfBoundsException {
    // -- check bounds for index
    if ((index < 0) || (index > this._XMLQuadPolygonList.size())) {
      throw new IndexOutOfBoundsException();
    }
    this._XMLQuadPolygonList.set(index, vXMLQuadPolygon);
  } // -- void setXMLQuadPolygon(int, org.mklab.mikity.xml.XMLQuadPolygon)

  /**
   * Method setXMLQuadPolygon
   * 
   * @param XMLQuadPolygonArray
   */
  public void setXMLQuadPolygon(org.mklab.mikity.xml.model.XMLQuadPolygon[] XMLQuadPolygonArray) {
    // -- copy array
    this._XMLQuadPolygonList.clear();
    for (int i = 0; i < XMLQuadPolygonArray.length; i++) {
      this._XMLQuadPolygonList.add(XMLQuadPolygonArray[i]);
    }
  } // -- void setXMLQuadPolygon(org.mklab.mikity.xml.XMLQuadPolygonArray)

  // /**
  // * Method setXMLQuadPolygonAsReference the value of 'XMLQuadPolygon' by
  // copying the
  // * given ArrayList.
  // *
  // * @param XMLQuadPolygonCollection the Vector to copy.
  // */
  // public void setXMLQuadPolygon(java.util.ArrayList XMLQuadPolygonCollection)
  // {
  // //-- copy collection
  // _XMLQuadPolygonList.clear();
  // for (int i = 0; i < XMLQuadPolygonCollection.size(); i++) {
  // _XMLQuadPolygonList.add((org.mklab.mikity.xml.model.XMLQuadPolygon)
  // XMLQuadPolygonCollection.get(i));
  // }
  // } //-- void setXMLQuadPolygon(java.util.ArrayList)

  /**
   * Method setXMLQuadPolygonAsReference the value of 'XMLQuadPolygon' by
   * setting it to the given ArrayList. No type checking is performed.
   * 
   * @param XMLQuadPolygonCollection
   *        the ArrayList to copy.
   */
  public void setXMLQuadPolygonAsReference(java.util.ArrayList<XMLQuadPolygon> XMLQuadPolygonCollection) {
    this._XMLQuadPolygonList = XMLQuadPolygonCollection;
  } // -- void setXMLQuadPolygonAsReference(java.util.ArrayList)
}
