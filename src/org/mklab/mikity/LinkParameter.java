/*
 * $Id: LinkParameter.java,v 1.2 2008/02/29 14:43:29 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity;
/**
 * リンクパラメータに関するクラス
 * @author SHOGO
 * @version $Revision: 1.2 $. 2008/02/29
 */
public class LinkParameter {

	public static final int LOCX = 1;
	public static final int LOCY = 2;
	public static final int LOCZ = 3;
	public static final int ROTX = 4;
	public static final int ROTY = 5;
	public static final int ROTZ = 6;
	
	private double locX,locY,locZ,rotX,rotY,rotZ;
	
	/**
	 * コンストラクタ
	 */
	public LinkParameter(){
		this(0, 0, 0, 0, 0, 0);
	}
	
	/**
	 * コンストラクタ
	 * @param locX　X座標並進
	 * @param locY　Y座標並進
	 * @param locZ　Z座標並進
	 * @param rotX　X座標回転
	 * @param rotY　Y座標回転
	 * @param rotZ　Z座標回転
	 */
	public LinkParameter(double locX, double locY, double locZ, double rotX, double rotY, double rotZ){
		this.locX=locX;
		this.locY=locY;
		this.locZ=locZ;
		this.rotX=rotX;
		this.rotY=rotY;
		this.locZ=rotZ;
	}

	/**
	 * X座標並進の値を取得する。
	 * @return　locX　
	 */
	public double getLocX() {
		return locX;
	}

	/**
	 * X座標並進の値を設定する。
	 * @param locX
	 */
	public void setLocX(double locX) {
		this.locX = locX;
	}

	/**
	 * Y座標並進の値を取得する。
	 * @return　locY　
	 */
	public double getLocY() {
		return locY;
	}

	/**
	 * Y座標並進の値を設定する。
	 * @param locY
	 */
	public void setLocY(double locY) {
		this.locY = locY;
	}

	/**
	 * Z座標並進の値を取得する。
	 * @return　locZ　
	 */
	public double getLocZ() {
		return locZ;
	}

	/**
	 * Z座標並進の値を設定する。
	 * @param locZ
	 */
	public void setLocZ(double locZ) {
		this.locZ = locZ;
	}

	/**
	 * X座標回転の値を取得する。
	 * @return　rotX　
	 */
	public double getRotX() {
		return rotX;
	}

	/**
	 * X座標回転の値を設定する。
	 * @param rotX
	 */
	public void setRotX(double rotX) {
		this.rotX = rotX;
	}

	/**
	 * Y座標回転の値を取得する。
	 * @return　rotY　
	 */
	public double getRotY() {
		return rotY;
	}

	/**
	 * Y座標回転の値を設定する。
	 * @param rotY
	 */
	public void setRotY(double rotY) {
		this.rotY = rotY;
	}

	/**
	 * Z座標回転の値を取得する。
	 * @return　rotZ　
	 */
	public double getRotZ() {
		return rotZ;
	}

	/**
	 * Z座標回転の値を設定する。
	 * @param rotZ
	 */
	public void setRotZ(double rotZ) {
		this.rotZ = rotZ;
	}
}
