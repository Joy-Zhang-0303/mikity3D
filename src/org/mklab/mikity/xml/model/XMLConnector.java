/*
 * $Id: XMLConnector.java,v 1.1 2007/08/03 03:30:27 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.model;

import javax.xml.bind.annotation.XmlAttribute;

public class XMLConnector extends XMLCylinder{
	
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
    
	
    public XMLConnector(){
    	super();
    	setDiv(20);
    	_color = "black";
    	_flag = "-";
    	_location = new Location();
    	_rotation = new Rotation();
    }
	
	  /**
	   * プリミティブの大きさのパラメータががコネクタに比べて小さくないか確認する。
	   * 小さいと判断した場合、コネクタのパラメータを変更する。
	   * @param param　プリミティブのパラメータ
	   */
	  public void checkParameter(float param){
		  if(param >= 0.05f){
			  setR(0.01f);
			  setHeight(0.05f);
		  }else{
			  setR(param*0.2f);
			  setHeight(param);
		  }
	  }
	  
	  /**
	   * プリミティブの半径の大きさのパラメータががコネクタに比べて小さくないか確認する。
	   * 小さいと判断した場合、コネクタのパラメータを変更する。
	   * @param param　プリミティブの半径のパラメータ
	   */
	  public void checkParameterR(float param){
		  if(param >= 0.025f){
			  setR(0.01f);
			  setHeight(0.05f);
		  }else{
			  setR(param*0.4f);
			  setHeight(param*2);
		  }
	  }

	  /**
	   * CylinderがコネクタNであるかどうかを返す。
	   * @return このCylinderがコネクタSに指定されていることに関する真偽
	   */
	  public String loadFlag() {
		  return _flag;
	  }

	  public float loadLengthToCenter() {
		  return _toCenter;
	  }
	  
	  public int loadNum() {
		  return _num;
	  }
	  
	  /**
	   * CylinderをコネクタNに指定する。あるいはその逆に指定する。
	   * @param flag_n このCylinderがコネクタNに指定されていることに関する真偽
	   */
	  public void setFlag(String flag){
		  this._flag = flag;
		  if(flag == "-"){
			  _color = "";
		  }else if(flag == "N"){
			  _color = "magenta";
		  }else if(flag == "S"){
			  _color = "cyan";
		  }
	  }

	  public void setLengthToCenter(float toCenter) {
		  this._toCenter = toCenter;
	  }
	  
	  public void setNum(int n){
		  _num = n;
	  }
	  
	  /**
	   * プリミティブの位置からデフォルトのコネクタの位置の基点を設定する。
	   * @param x プリミティブのx座標
	   * @param y プリミティブのy座標
	   * @param z プリミティブのz座標
	   * @return baseLoc プリミティブの基本位置
	   */
	  public void setConnectorLocation(float x, float y, float z){
	    _location.setX(x);
	    _location.setY(y);
	    _location.setZ(z);
	    setLocation(_location);
	  }
	  
	    /**
	     * コネクタの回転を設定する。
	     * @param rotX
	     * @param rotY
	     * @param rotZ
	     * @return baseRot プリミティブの基本回転
	     */
	    public void setConnectorRotation(float rotX,float rotY,float rotZ){
			_rotation.setXrotate(rotX);
			_rotation.setYrotate(rotY);
			_rotation.setZrotate(rotZ);
			setRotation(_rotation);
		}
}
