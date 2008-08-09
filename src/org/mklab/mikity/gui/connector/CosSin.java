/*
 * $Id: CosSin.java,v 1.2 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import java.lang.Math;

/**
 * 正弦余弦を導出するためのクラス
 * @author SHOGO
 * @version $Revision: 1.2 $. 2006/07/13
 */
public class CosSin{
	
	/**
	 * コサインX
	 */
	private float cosx;
	/**
	 * コサインY
	 */
	private float cosy;
	/**
	 * コサインZ
	 */
	private float cosz;
	/**
	 * サインX
	 */
	private float sinx;
	/**
	 * サインY
	 */
	private float siny;
	/**
	 * サインZ
	 */
	private float sinz;

	/**
	 * コンストラクタ
	 * @param rotX　X軸回転
	 * @param rotY　Y軸回転
	 * @param rotZ　Z軸回転
	 */
	public CosSin(float rotX, float rotY, float rotZ){
		setCosX(rotX);
		setCosY(rotY);
		setCosZ(rotZ);
		setSinX(rotX);
		setSinY(rotY);
		setSinZ(rotZ);
	}

	/**
	 * コサインXを導出する
	 * @param rotX　X軸回転
	 */
	private void setCosX(float rotX){
		cosx = (float)Math.cos(Math.PI*rotX/180.0);
	}
	
	/**
	 * コサインYを導出する
	 * @param rotY　Y軸回転
	 */
	private void setCosY(float rotY){
		cosy = (float)Math.cos(Math.PI*rotY/180.0);
	}
	
	/**
	 * コサインZを導出する
	 * @param rotZ　Z軸回転
	 */
	private void setCosZ(float rotZ){
		cosz = (float)Math.cos(Math.PI*rotZ/180.0);
	}
	
	/**
	 * サインXを導出する
	 * @param rotX　X軸回転
	 */
	private void setSinX(float rotX){
		sinx = (float)Math.sin(Math.PI*rotX/180.0);
	}
	
	/**
	 * サインYを導出する
	 * @param rotY　Y軸回転
	 */
	private void setSinY(float rotY){
		siny = (float)Math.sin(Math.PI*rotY/180.0);
	}
	
	/**
	 * サインZを導出する
	 * @param rotZ　Z軸回転
	 */
	private void setSinZ(float rotZ){
		sinz = (float)Math.sin(Math.PI*rotZ/180.0);
	}
	
	/**
	 * コサインXの値を返す
	 * @return　cosx　コサインX
	 */
	public float getCosX(){
		return cosx;
	}
	
	/**
	 * コサインYの値を返す
	 * @return　cosy　コサインY
	 */
	public float getCosY(){
		return cosy;
	}
	
	/**
	 * コサインZの値を返す
	 * @return　cosz　コサインZ
	 */
	public float getCosZ(){
		return cosz;
	}
	
	/**
	 * サインXの値を返す
	 * @return　sinx　サインX
	 */
	public float getSinX(){
		return sinx;
	}
	
	/**
	 * サインYの値を返す
	 * @return　siny　サインY
	 */
	public float getSinY(){
		return siny;
	}
	
	/**
	 * サインZの値を返す
	 * @return　sinz　サインZ
	 */
	public float getSinZ(){
		return sinz;
	}
}
