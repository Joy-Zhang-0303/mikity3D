/*
 * $Id: ConnectNewRotation.java,v 1.4 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.xml.model.Rotation;

/**
 * コネクタの更新後の回転を設定するクラス
 * @author SHOGO
 * @version $Revision: 1.4 $. 2008/02/29
 */
public class ConnectNewRotation {

	/**
	 * 更新後の回転
	 */
	private Rotation newRot;
	
	
	/**
	 * プリミティブのX軸回転
	 */
	private float x;
	/**
	 * プリミティブのY軸回転
	 */
	private float y;
	/**
	 * プリミティブのZ軸回転
	 */
	private float z;
	
	/**
	 * コンストラクタ
	 */
	public ConnectNewRotation(){
		newRot = new Rotation();

	}
	
	/**
	 * 基準プリミティブの回転を取得する
	 * @param primNRot　基準プリミティブの回転
	 */
	public void setPrimitiveNRotation(Rotation primNRot){
		x=primNRot.loadXrotate();
		y=primNRot.loadYrotate();
		z=primNRot.loadZrotate();
	}
	
	/**
	 * プリミティブの移動後の回転を番号の組み合わせから更新する
	 * @param n　コネクタNの番号
	 * @param s　コネクタSの番号
	 */
	public void setNewRot(int n, int s){
			if(s == 1){
			switch(n){
			case 1:
				setRotN2();
				break;
			case 2:
				setRotN1();
				break;
			case 3:
				setRotN4();
				break;
			case 4:
				setRotN3();
				break;
			case 5:
				setRotN6();
				break;
			case 6:
				setRotN5();
				break;
			}
		}else if(s == 2){
			switch(n){
			case 1:
				setRotN1();
				break;
			case 2:
				setRotN2();
				break;
			case 3:
				setRotN3();
				break;
			case 4:
				setRotN4();
				break;
			case 5:
				setRotN5();
				break;
			case 6:
				setRotN6();
				break;
			}
		}else if(s == 3){
			switch(n){	
			case 1:
				setRotN6();
				break;
			case 2:
				setRotN5();
				break;
			case 3:
				setRotN2();
				break;
			case 4:
				setRotN1();
				break;
			case 5:
				setRotN4();
				break;
			case 6:
				setRotN3();
				break;
			}
		}else if(s == 4){
			switch(n){
			case 1:
				setRotN5();
				break;
			case 2:
				setRotN6();
				break;
			case 3:
				setRotN1();
				break;
			case 4:
				setRotN2();
				break;
			case 5:
				setRotN3();
				break;
			case 6:
				setRotN4();
				break;
			}
		}else if(s == 5){
			switch(n){
			case 1:
				setRotN3();
				break;
			case 2:
				setRotN4();
				break;
			case 3:
				setRotN6();
				break;
			case 4:
				setRotN5();
				break;
			case 5:
				setRotN2();
				break;
			case 6:
				setRotN1();
				break;
			}
		}else if(s == 6){
			switch(n){
			case 1:
				setRotN4();
				break;
			case 2:
				setRotN3();
				break;
			case 3:
				setRotN5();
				break;
			case 4:
				setRotN6();
				break;
			case 5:
				setRotN1();
				break;
			case 6:
				setRotN2();
				break;
			}
		}else{
			
		}
	}
	
	/**
	 * 移動後の回転(1)
	 */
	private void setRotN1(){
		newRot.setXrotate(x);
		newRot.setYrotate(y);
		newRot.setZrotate(z);
	}
	
	/**
	 * 移動後の回転(2)
	 */
	private void setRotN2(){
		newRot.setXrotate(x+180.0f);
		newRot.setYrotate(y);
		newRot.setZrotate(z);
	}
	
	/**
	 * 移動後の回転(3)
	 */
	private void setRotN3(){
		newRot.setXrotate(-y);
		newRot.setYrotate(0.0f);
		newRot.setZrotate(z-90.0f);
	}
	
	/**
	 * 移動後の回転(4)
	 */
	private void setRotN4(){
		newRot.setXrotate(y);
		newRot.setYrotate(0.0f);
		newRot.setZrotate(z+90.0f);
	}
	
	/**
	 * 移動後の回転(5)
	 */
	private void setRotN5(){
		newRot.setXrotate(x+90.0f);
		newRot.setYrotate(y);
		newRot.setZrotate(z);
	}
	
	/**
	 * 移動後の回転(6)
	 */
	private void setRotN6(){
		newRot.setXrotate(x-90.0f);
		newRot.setYrotate(y);
		newRot.setZrotate(z);
	}
	/**
	 * 更新後の回転を返す
	 * @return　newRot　更新後の回転
	 */
	public Rotation getNewRot(){
		return newRot;
	}
}
