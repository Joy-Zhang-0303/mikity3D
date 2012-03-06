/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Linkdata.java,v 1.3 2008/02/03 04:25:09 morimune Exp $
 */

package org.mklab.mikity.xml.model;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlAttribute;


/**
 * Class Linkdata.
 * 
 * @version $Revision: 1.3 $ $Date: 2008/02/03 04:25:09 $
 */
public class Linkdata implements java.io.Serializable {

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  /**
   * Field _target
   */
  @XmlAttribute
  private java.lang.String _target;

  /**
   * Field _const
   */
  @XmlAttribute
  private double _const;

  /**
   * keeps track of state for field: _const
   */
  private boolean _has_const;

  /**
   * Field _column
   */
  @XmlAttribute
  private int _column;

  /**
   * keeps track of state for field: _column
   */
  private boolean _has_column;

  private boolean _has_dh;

  private boolean _has_link;

  // ----------------/
  // - Constructors -/
  // ----------------/

  /**
   * コンストラクター
   */
  public Linkdata() {
    super();
  } // -- org.mklab.mikity.xml.Linkdata()

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Method deleteColumn
   */
  public void deleteColumn() {
    this._has_column = false;
  } // -- void deleteColumn()

  /**
   * Method deleteConst
   */
  public void deleteConst() {
    this._has_const = false;
  } // -- void deleteConst()

  /**
   * Note: hashCode() has not been overriden
   * 
   * @param obj オブジェクト
   */
  @Override
  public boolean equals(java.lang.Object obj) {
    if (this == obj) return true;

    if (obj instanceof Linkdata) {

      Linkdata temp = (Linkdata)obj;
      if (this._target != null) {
        if (temp._target == null) return false;
        else if (!(this._target.equals(temp._target))) return false;
      } else if (temp._target != null) return false;
      if (this._const != temp._const) return false;
      if (this._has_const != temp._has_const) return false;
      if (this._column != temp._column) return false;
      if (this._has_column != temp._has_column) return false;
      return true;
    }
    return false;
  } // -- boolean equals(java.lang.Object)

  /**
   * Returns the value of field 'column'.
   * 
   * @return the value of field 'column'.
   */
  public int loadColumn() {
    return this._column;
  } // -- int getColumn()

  /**
   * Returns the value of field 'const'.
   * 
   * @return the value of field 'const'.
   */
  public double loadConst() {
    return this._const;
  } // -- double getConst()

  /**
   * Returns the value of field 'target'.
   * 
   * @return the value of field 'target'.
   */
  public java.lang.String loadTarget() {
    return this._target;
  } // -- java.lang.String getTarget()

  /**
   * Method hasColumn
   * 
   * @return has_colimn
   */
  public boolean hasColumn() {
    if (this._column != 0) {
      this._has_column = true;
    }
    return this._has_column;
  } // -- boolean hasColumn()

  /**
   * Method hasConst
   * 
   * @return has_const
   */
  public boolean hasConst() {
    if (this._const != 0.0) {
      this._has_const = true;
    }
    return this._has_const;
  } // -- boolean hasConst()

  /**
   * Sets the value of field 'column'.
   * 
   * @param column the value of field 'column'.
   */
  public void setColumn(int column) {
    this._column = column;
    this._has_column = true;
  } // -- void setColumn(int)

  /**
   * Sets the value of field 'const'.
   * 
   * @param _const コンスト
   */
  public void setConst(double _const) {
    this._const = _const;
    this._has_const = true;
  } // -- void setConst(double)

  /**
   * Sets the value of field 'target'.
   * 
   * @param target the value of field 'target'.
   */
  public void setTarget(java.lang.String target) {
    this._target = target;
  } // -- void setTarget(java.lang.String)

  /**
   * @return DHを持つならばtrue
   */
  public boolean hasDH() {
    if (this._target.equals("a") || this._target.equals("alpha") || this._target.equals("d") || this._target.equals("theta")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
      this._has_dh = true;
    }
    return this._has_dh;
  }

  /**
   * @return linkをもつならばtrue
   */
  public boolean hasLink() {
    if (this._target.equals("locationX") || this._target.equals("locationY") || this._target.equals("locationZ") || this._target.equals("rotationX") || this._target.equals("rotationY") //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        || this._target.equals("rotationZ")) { //$NON-NLS-1$
      this._has_link = true;
    }
    return this._has_link;
  }
}
