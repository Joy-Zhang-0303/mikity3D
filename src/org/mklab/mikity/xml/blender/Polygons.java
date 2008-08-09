/*
 * $Id: Polygons.java,v 1.2 2007/11/30 10:56:15 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Polygon要素)
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/11/30
 */
public class Polygons {
	@XmlElement
	private ArrayList<String> p;
	
	/**
	 * 使用する頂点の組み合わせをまとめた配列リスト
	 */
	private ArrayList<int[]> indexNumber;
	/**
	 * 文字列の空白の位置を記したリスト
	 */
	ArrayList<int[]> spaceList;
	/**
	 * 使用する頂点の番号を記したリスト
	 */
	ArrayList<int[]> numList;
	
	/**
	 * コンストラクタ
	 */
	public Polygons(){
		p = new ArrayList<String>();
		indexNumber = new ArrayList<int[]>();
		spaceList =new ArrayList<int[]>();
		numList =new ArrayList<int[]>();
	}
	
	/**
	 * 使用する頂点の組み合わせをまとめた配列リストを返す
	 * @return　indexNumber　使用する頂点の組み合わせをまとめた配列リスト
	 */
	public ArrayList<int[]> getIndexNumber(){
		setIndex();
		return indexNumber;
	}
	
	/**
	 * 頂点座標一覧を記述した文字列から空白を除外し、
	 * 前から3つずつ頂点の組み合わせをまとめる配列リストに加えていく。
	 */
	private void setIndex(){
		for(int i=0; i<p.size(); i++){
			ArrayList<Integer> space =new ArrayList<Integer>();
			for(int j=0;j<p.get(i).length(); j++){
				if(p.get(i).charAt(j) == ' '){
					space.add(j);
				}
			}
			int[] spaceNum = new int[space.size()];
			for(int k=0; k<spaceNum.length; k++){
				spaceNum[k] = space.get(k);
			}
			spaceList.add(spaceNum);
			
			ArrayList<Integer> num =new ArrayList<Integer>();
			for(int j=0; j<spaceList.get(i).length+1; j+=2){
				if(j==0){
					num.add(Integer.parseInt(p.get(i).substring(0, spaceList.get(i)[0])));
				}
				else if(j == spaceList.get(i).length){
					num.add(Integer.parseInt(p.get(i).substring(spaceList.get(i)[spaceList.get(i).length-1]+1)));
				}
				else{
					num.add(Integer.parseInt(p.get(i).substring(spaceList.get(i)[j-1]+1, spaceList.get(i)[j])));
				}
			}
			
			int[] n = new int[num.size()];
			for(int l=0; l<num.size(); l++){
				n[l] = num.get(l);
			}
			indexNumber.add(n);
		}
	}
}
