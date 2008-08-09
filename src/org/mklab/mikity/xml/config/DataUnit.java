/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: DataUnit.java,v 1.1 2007/08/03 03:30:28 morimune Exp $
 */

package org.mklab.mikity.xml.config;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Class DataUnit.
 * 
 * @version $Revision: 1.1 $ $Date: 2007/08/03 03:30:28 $
 */
public class DataUnit implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
   * 
   */
  private static final long serialVersionUID = 1L;

    /**
     * Field _angle
     */
  @XmlAttribute
    private java.lang.String _angle;

    /**
     * Field _length
     */
  @XmlAttribute
    private java.lang.String _length;


      //----------------/
     //- Constructors -/
    //----------------/

    /**
     * コンストラクター
     */
    public DataUnit() {
        super();
    } //-- org.mklab.mikity.xml.DataUnit()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Note: hashCode() has not been overriden
     * 
     * @param obj
     */
    public boolean equals(java.lang.Object obj)
    {
        if ( this == obj )
            return true;
        
        if (obj instanceof DataUnit) {
        
            DataUnit temp = (DataUnit)obj;
            if (this._angle != null) {
                if (temp._angle == null) return false;
                else if (!(this._angle.equals(temp._angle))) 
                    return false;
            }
            else if (temp._angle != null)
                return false;
            if (this._length != null) {
                if (temp._length == null) return false;
                else if (!(this._length.equals(temp._length))) 
                    return false;
            }
            else if (temp._length != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Returns the value of field 'angle'.
     * 
     * @return the value of field 'angle'.
     */
    public java.lang.String loadAngle()
    {
        return this._angle;
    } //-- java.lang.String getAngle() 

    /**
     * Returns the value of field 'length'.
     * 
     * @return the value of field 'length'.
     */
    public java.lang.String loadLength()
    {
        return this._length;
    } //-- java.lang.String getLength() 

    /**
     * Sets the value of field 'angle'.
     * 
     * @param angle the value of field 'angle'.
     */
    public void setAngle(java.lang.String angle)
    {
        this._angle = angle;
    } //-- void setAngle(java.lang.String) 

    /**
     * Sets the value of field 'length'.
     * 
     * @param length the value of field 'length'.
     */
    public void setLength(java.lang.String length)
    {
        this._length = length;
    } //-- void setLength(java.lang.String) 

}
