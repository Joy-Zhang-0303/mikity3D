/*
 * $Id: XMLConnector.java,v 1.1 2007/08/03 03:30:27 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.model;

import javax.xml.bind.annotation.XmlAttribute;


/**
 * @author koga
 * @version $Revision$, 2008/08/10
 */
public class XMLConnector extends XMLCylinder {

  /**
	   * 
	   */
  private static final long serialVersionUID = 1L;

  @XmlAttribute
  private int _num;

  @XmlAttribute
  private String _flag;

  @XmlAttribute
  private float _toCenter;

  /**
   * 新しく生成された<code>XMLConnector</code>オブジェクトを初期化します。
   */
  public XMLConnector() {
    super();
    setDiv(20);
    this._color = "black"; //$NON-NLS-1$
    this._flag = "-"; //$NON-NLS-1$
    this._location = new Location();
    this._rotation = new Rotation();
  }

  /**
   * プリミティブの大きさのパラメータががコネクタに比べて小さくないか確認する。 小さいと判断した場合、コネクタのパラメータを変更する。
   * 
   * @param param
   *        　プリミティブのパラメータ
   */
  public void checkParameter(float param) {
    if (param >= 0.05f) {
      setR(0.01f);
      setHeight(0.05f);
    } else {
      setR(param * 0.2f);
      setHeight(param);
    }
  }

  /**
   * プリミティブの半径の大きさのパラメータががコネクタに比べて小さくないか確認する。 小さいと判断した場合、コネクタのパラメータを変更する。
   * 
   * @param param
   *        　プリミティブの半径のパラメータ
   */
  public void checkParameterR(float param) {
    if (param >= 0.025f) {
      setR(0.01f);
      setHeight(0.05f);
    } else {
      setR(param * 0.4f);
      setHeight(param * 2);
    }
  }

  /**
   * CylinderがコネクタNであるかどうかを返す。
   * 
   * @return このCylinderがコネクタSに指定されていることに関する真偽
   */
  public String loadFlag() {
    return this._flag;
  }

  /**
   * @return length to center
   */
  public float loadLengthToCenter() {
    return this._toCenter;
  }

  /**
   * @return number
   */
  public int loadNum() {
    return this._num;
  }

  /**
   * CylinderをコネクタNに指定する。あるいはその逆に指定する。
   * 
   * @param flag
   *        このCylinderがコネクタNに指定されていることに関する真偽
   */
  public void setFlag(String flag) {
    this._flag = flag;
    if (flag == "-") { //$NON-NLS-1$
      this._color = ""; //$NON-NLS-1$
    } else if (flag == "N") { //$NON-NLS-1$
      this._color = "magenta"; //$NON-NLS-1$
    } else if (flag == "S") { //$NON-NLS-1$
      this._color = "cyan"; //$NON-NLS-1$
    }
  }

  /**
   * @param toCenter
   */
  public void setLengthToCenter(float toCenter) {
    this._toCenter = toCenter;
  }

  /**
   * @param n
   */
  public void setNum(int n) {
    this._num = n;
  }

  /**
   * プリミティブの位置からデフォルトのコネクタの位置の基点を設定する。
   * 
   * @param x
   *        プリミティブのx座標
   * @param y
   *        プリミティブのy座標
   * @param z
   *        プリミティブのz座標
   */
  public void setConnectorLocation(float x, float y, float z) {
    this._location.setX(x);
    this._location.setY(y);
    this._location.setZ(z);
    setLocation(this._location);
  }

  /**
   * コネクタの回転を設定する。
   * 
   * @param rotX
   * @param rotY
   * @param rotZ
   */
  public void setConnectorRotation(float rotX, float rotY, float rotZ) {
    this._rotation.setXrotate(rotX);
    this._rotation.setYrotate(rotY);
    this._rotation.setZrotate(rotZ);
    setRotation(this._rotation);
  }
}
