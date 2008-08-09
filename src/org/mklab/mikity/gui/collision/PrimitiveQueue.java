/*
 * $Id: PrimitiveQueue.java,v 1.2 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.collision;

import org.mklab.mikity.xml.model.Location;

/**
 * 過去に追加したプリミティブに関するデータをまとめたクラス
 * @author SHOGO
 * @version $Revision: 1.2 $. 2008/02/29
 */
public class PrimitiveQueue {

	/**
	 * これまで追加したプリミティブの存在範囲のキュー
	 */
	private float rangeArray[] = new float[10];
	/**
	 * これまで追加したプリミティブの位置座標のキュー
	 */
	private Location locArray[] = new Location[10];
	/**
	 * これまで追加したプリミティブの総数
	 */
	private int count =0;
	
	public PrimitiveQueue(){
		for(int i=0; i<locArray.length; i++){
			locArray[i] = new Location();
		}
	}
	
	/**
	 * プリミティブの位置座標,存在範囲をキューに追加する。
	 * @param loc　プリミティブの位置座標
	 * @param range プリミティブの存在範囲
	 */
	public void enqueue(Location loc, float range){
		while(locArray[count] != null && count<locArray.length+1){
			count++;
		}
		
		if(count == 0){
			locArray[0] = loc;
			rangeArray[0] = range;
		}else{
			for(int i=0; i<count+1; i++){
				locArray[i]=locArray[i+1];
				locArray[count]=loc;
				
				rangeArray[i]=rangeArray[i+1];
				rangeArray[count]=range;
			}
		}
	}
	
	/**
	 * 現時点のプリミティブの存在範囲のリストを返す
	 * @return　rangeArray　現時点のプリミティブ
	 */
	public float[] getRangeArray(){
		return rangeArray;
	}
	
	/**
	 * 現時点のプリミティブの位置座標のリストを返す
	 * @return　locArray　現時点のプリミティブの位置座標
	 */
	public Location[] getLocationArray(){
		return locArray;
	}
	
	/**
	 * 現時点のプリミティブの総数を返す
	 * @return　count　現時点のプリミティブの総数
	 */
	public int getCount(){
		return count;
	}
}
