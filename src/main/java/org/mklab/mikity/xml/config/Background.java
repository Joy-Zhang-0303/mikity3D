/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Background.java,v 1.1 2007/08/03 03:30:27 morimune Exp $
 */

package org.mklab.mikity.xml.config;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlAttribute;


/**
 * Class Background.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:27 $
 */
public class Background implements java.io.Serializable {

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * Field _color
   */
  @XmlAttribute
  private java.lang.String _color;

  // ----------------/
  // - Constructors -/
  // ----------------/

  /**
   * コンストラクター
   */
  public Background() {
    super();
  } // -- org.mklab.mikity.xml.Background()

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj オブジェクト
   */
  @Override
  public boolean equals(java.lang.Object obj) {
    if (this == obj) return true;

    if (obj instanceof Background) {

      Background temp = (Background)obj;
      if (this._color != null) {
        if (temp._color == null) return false;
        else if (!(this._color.equals(temp._color))) return false;
      } else if (temp._color != null) return false;
      return true;
    }
    return false;
  } // -- boolean equals(java.lang.Object)

  /**
   * Returns the value of field 'color'.
   * 
   * @return the value of field 'color'.
   */
  public java.lang.String loadColor() {
    return this._color;
  } // -- java.lang.String getColor()

  //  /**
  //   * Method isValid
  //   * 
  //   * @return boolean
  //   */
  //  public boolean isValid() {
  //    try {
  //      validate();
  //    } catch (org.exolab.castor.xml.ValidationException vex) {
  //      return false;
  //    }
  //    return true;
  //  } // -- boolean isValid()
  //
  //  /**
  //   * Method marshal
  //   * 
  //   * @param out
  //   * @throws org.exolab.castor.xml.MarshalException
  //   * @throws org.exolab.castor.xml.ValidationException
  //   */
  //  public void marshal(java.io.Writer out) throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
  //
  //    Marshaller.marshal(this, out);
  //  } // -- void marshal(java.io.Writer)

  //  /**
  //   * Method marshal
  //   * 
  //   * @param handler
  //   * @throws java.io.IOException
  //   * @throws org.exolab.castor.xml.MarshalException
  //   * @throws org.exolab.castor.xml.ValidationException
  //   */
  //  public void marshal(org.xml.sax.ContentHandler handler) throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
  //
  //    Marshaller.marshal(this, handler);
  //  } // -- void marshal(org.xml.sax.ContentHandler)

  /**
   * Sets the value of field 'color'.
   * 
   * @param color the value of field 'color'.
   */
  public void setColor(java.lang.String color) {
    this._color = color;
  } // -- void setColor(java.lang.String)

  //  /**
  //   * Method unmarshal
  //   * 
  //   * @param reader
  //   * @return unmarshaller
  //   * @throws org.exolab.castor.xml.MarshalException
  //   * @throws org.exolab.castor.xml.ValidationException
  //   */
  //  public static java.lang.Object unmarshal(java.io.Reader reader) throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
  //    return Unmarshaller.unmarshal(org.mklab.mikity.xml.config.Background.class, reader);
  //  } // -- java.lang.Object unmarshal(java.io.Reader)
  //
  //  /**
  //   * Method validate
  //   * 
  //   * @throws org.exolab.castor.xml.ValidationException
  //   */
  //  public void validate() throws org.exolab.castor.xml.ValidationException {
  //    org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
  //    validator.validate(this);
  //  } // -- void validate()

}
