/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.picker;

import org.mklab.mikity.DHParameter;
import org.mklab.mikity.LinkParameter;
import org.mklab.mikity.MyTransformGroup;
import org.mklab.mikity.gui.ModelCanvas;
import org.mklab.nfc.Matrix;


/**
 * @author miki
 * @version $Revision: 1.7 $.2005/01/17
 */
public abstract class DataPicker {

	private Matrix data;
	protected DHParameter[] params;
	protected LinkParameter[] link;
	protected MyTransformGroup trans;

	private static int dataScale = 1;
	private static int modelScale = 1;

	private static boolean dataIsRadian = true;
	static boolean modelIsRadian = true;

	/**
	 * コンストラクター
	 * @param data
	 */
	public DataPicker(Matrix data) {
		this.data = data;

		//列の数の配列を作成
		params = new DHParameter[data.getColSize()];
		for (int i = 0; i < params.length; i++) {
			params[i] = new DHParameter();
		}
		link = new LinkParameter[data.getColSize()];
		for (int i = 0; i < link.length; i++) {
			link[i] = new LinkParameter();
		}
	}

	/**
	 * @param moveType
	 * @param row
	 */
	public final void addMoveTypeDH(int moveType, int row) {
		if (data.getRowSize() < row) {

		}
		switch (moveType) {
		case DHParameter.A:
			for (int i = 0; i < params.length; i++) {
				params[i].setA(data.getRealElement(row, i + 1) / dataScale);
			}
			break;
		case DHParameter.ALPHA:
			for (int i = 0; i < params.length; i++) {
				if (dataIsRadian) {
					params[i].setAlpha(data.getRealElement(row, i + 1));
				} else {
					params[i].setAlpha(Math.toRadians(data.getRealElement(row, i + 1)));
				}
			}
			break;
		case DHParameter.D:
			for (int i = 0; i < params.length; i++) {
				params[i].setD(data.getRealElement(row, i + 1) / dataScale);
			}
			break;
		case DHParameter.THETA:
			for (int i = 0; i < params.length; i++) {
				if (dataIsRadian) {
					params[i].setTheta(data.getRealElement(row, i + 1));
				} else {
					params[i].setTheta(Math.toRadians(data.getRealElement(row, i + 1)));
				}
			}
			break;
		default:
			throw new AssertionError("a, alpha, d, theta以外の値を入れるなごら。");
		}
	}

	/**
	 * @param moveType
	 * @param row
	 */
	public final void addMoveType(int moveType, int row) {
		if (data.getRowSize() < row) {

		}
		switch (moveType) {
		case LinkParameter.LOCX:
			for (int i = 0; i < link.length; i++) {
				link[i].setLocX(data.getRealElement(row, i + 1) / dataScale);
			}
			break;
		case LinkParameter.LOCY:
			for (int i = 0; i < link.length; i++) {
				link[i].setLocY(data.getRealElement(row, i + 1) / dataScale);
			}
			break;
		case LinkParameter.LOCZ:
			for (int i = 0; i < link.length; i++) {
				link[i].setLocZ(data.getRealElement(row, i + 1) / dataScale);
			}
			break;
		case LinkParameter.ROTX:
			for (int i = 0; i < link.length; i++) {
				if (dataIsRadian) {
					link[i].setRotX(data.getRealElement(row, i + 1));
				} else {
					link[i].setRotX(Math.toRadians(data.getRealElement(row, i + 1)));
				}
			}
			break;
		case LinkParameter.ROTY:
			for (int i = 0; i < link.length; i++) {
				if (dataIsRadian) {
					link[i].setRotY(data.getRealElement(row, i + 1));
				} else {
					link[i].setRotY(Math.toRadians(data.getRealElement(row, i + 1)));
				}
			}
			break;
		case LinkParameter.ROTZ:
			for (int i = 0; i < link.length; i++) {
				if (dataIsRadian) {
					link[i].setRotZ(data.getRealElement(row, i + 1));
				} else {
					link[i].setRotZ(Math.toRadians(data.getRealElement(row, i + 1)));
				}
			}
			break;
		default:
			throw new AssertionError("locX, locY, locZ, rotX, rotY, rotZ以外の値を入れるなごら。");
		}
	}

	/**
	 * @param setType
	 * @param constantValue
	 * initialTranformに固定値をセットする
	 */
	public void setConstantValueDH(int setType, double constantValue) {
		switch (setType) {
		case DHParameter.A:
			for (int i = 0; i < params.length; i++) {
				params[i].setA(constantValue / modelScale+params[i].getA());
			}
			break;
		case DHParameter.ALPHA:
			for (int i = 0; i < params.length; i++) {
				// どうにかする。
				if(ModelCanvas.radian){
					params[i].setAlpha(constantValue+params[i].getAlpha());
				} else{
					params[i].setAlpha(Math.toRadians(constantValue)+params[i].getAlpha());
				}
			}

			break;
		case DHParameter.D:
			for (int i = 0; i < params.length; i++) {
				params[i].setD(constantValue / modelScale+params[i].getD());
			}
			break;
		case DHParameter.THETA:
			for (int i = 0; i < params.length; i++) {
				if(ModelCanvas.radian){
					params[i].setTheta(constantValue+params[i].getTheta());
				} else {
					params[i].setTheta(Math.toRadians(constantValue)+params[i].getTheta());
				}
			}
			break;
		default:
			throw new AssertionError("a, alpha, d, theta以外の値を入れるなごら。");
		}
	}

	/**
	 * @param setType
	 * @param constantValue
	 * initialTranformに固定値をセットする
	 */
	public void setConstantValue(int setType, double constantValue) {
		switch (setType) {
		case LinkParameter.LOCX:
			for (int i = 0; i < link.length; i++) {
				link[i].setLocX(constantValue / modelScale+link[i].getLocX());
			}
			break;
		case LinkParameter.LOCY:
			for (int i = 0; i < link.length; i++) {
				link[i].setLocY(constantValue / modelScale+link[i].getLocY());
			}
			break;
		case LinkParameter.LOCZ:
			for (int i = 0; i < link.length; i++) {
				link[i].setLocZ(constantValue / modelScale+link[i].getLocZ());
			}
			break;
		case LinkParameter.ROTX:
			for (int i = 0; i < link.length; i++) {
				if(ModelCanvas.radian){
					link[i].setRotX(constantValue+link[i].getRotX());
				} else {
					link[i].setRotX(Math.toRadians(constantValue)+link[i].getRotX());
				}
			}
			break;
		case LinkParameter.ROTY:
			for (int i = 0; i < link.length; i++) {
				if(ModelCanvas.radian){
					link[i].setRotY(constantValue+link[i].getRotY());
				} else {
					link[i].setRotY(Math.toRadians(constantValue)+link[i].getRotY());
				}
			}
			break;
		case LinkParameter.ROTZ:
			for (int i = 0; i < link.length; i++) {
				if(ModelCanvas.radian){
					link[i].setRotZ(constantValue+link[i].getRotZ());
				} else {
					link[i].setRotZ(Math.toRadians(constantValue)+link[i].getRotZ());
				}
			}
			break;
		default:
			throw new AssertionError("locX, locY, locZ, rotX, rotY, rotZ以外の値を入れるなごら。");
		}
	}

	/**
	 * @param time
	 *        時間を与えると、それに最も近い時間のある行rowを返す
	 * @return getValue(row, getColumn(time))
	 */
	public int getColumn(double time) {
		//時系列データからそれに最も近い時間のある行を返す。
		return data.getRowVector(1).subtractElementWise(time).absElementWise().minimum().getInteger(2);
	}

	/**
	 * @param row
	 *        データの行数（何番目のデータか）
	 * @param time
	 *        時刻
	 * @return 時刻timeのときのrow行目のデータ
	 */
	public double getValue(int row, double time) {
		return getValue(row, getColumn(time));
	}

	/**
	 * @param row
	 * @param col
	 * @return data.getRealElement(row, col)
	 */
	public double getValue(int row, int col) {
		return data.getRealElement(row, col);
	}

	/**
	 * @return data.getColSize()
	 */
	public int getDataCount() {
		return data.getColSize();
	}

	/**
	 * @return data.getRealElement(1, getDataCount())
	 */
	public double getEndTime() {
		return data.getRealElement(1, getDataCount());
	}

	/**
	 * シミュレーションの開始時刻を返す。
	 * 
	 * @return シミュレーションの開始時刻
	 */
	public double getStartTime() {
		return data.getRealElement(1, 1);
	}

	/**
	 * @param time
	 * @return unknown
	 */
	public abstract DHParameter getDHParameter(double time);

	/**
	 * @param time
	 * @return unknown
	 */
	public abstract LinkParameter getLinkParameter(double time);

	/**
	 * @param dScale
	 * @param dRadian
	 * @param mRadian
	 * @param mScale
	 */
	public static void setScale(int dScale, boolean dRadian, int mScale, boolean mRadian) {
		//dataScale = dScale;
		dataIsRadian = dRadian;
		modelScale = mScale;
		modelIsRadian = mRadian;
	}
}