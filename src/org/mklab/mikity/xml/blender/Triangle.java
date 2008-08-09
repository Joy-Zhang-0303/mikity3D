/*
 * $Id: Triangle.java,v 1.2 2007/11/30 10:56:15 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;

/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Triangle要素)
 * 頂点座標のうち、使用する頂点の番号を取得する
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/11/30
 */
public class Triangle {
	@XmlElement
	private String p;

	
	/**
	 * 文字列の空白の位置を記したリスト
	 */
	private ArrayList<Integer> space =new ArrayList<Integer>();
	/**
	 * 使用する頂点の番号を記したリスト
	 */
	private ArrayList<Integer> num =new ArrayList<Integer>();
	/**
	 * 使用する頂点の組み合わせをまとめた配列リスト
	 */
	private ArrayList<int[]> index = new ArrayList<int[]>();

	/**
	 * コンストラクタ
	 */
	public Triangle(){
		space =new ArrayList<Integer>();
		num =new ArrayList<Integer>();
		index = new ArrayList<int[]>();
	}

	/**
	 * 使用する頂点番号一覧を記述した文字列から使用しない値や空白を除外し、
	 * 前から3つずつ頂点の組み合わせをまとめる配列リストに加えていく。
	 */
	private void createTriangleIndex(){
		for(int j=0;j<p.length(); j++){
			if(p.charAt(j) == ' '){
				space.add(j);
			}
		}
		for(int i=0; i<space.size()+1; i+=2){
			if(i==0){
				num.add(Integer.parseInt(p.substring(0,space.get(0))));
			}else if(i==space.size()){
				num.add(Integer.parseInt(p.substring(space.get(space.size()-1)+1)));
			}else{
				num.add(Integer.parseInt(p.substring(space.get(i-1)+1,space.get(i))));
			}
		}
		for(int i=0; i<num.size(); i+=3){
			int[] n = new int[3];
			n[0] =num.get(i);
			n[1] =num.get(i+1);
			n[2] =num.get(i+2);
			index.add(n);
		}
	}

	/**
	 * 使用する頂点番号一覧を記述した文字列を返す
	 * @return　p　使用する頂点番号一覧を記述した文字列
	 */
	public String loadP(){
		return p;
	}
	
	/**
	 * 使用する頂点の組み合わせをまとめた配列リストを返す
	 * @return　index　使用する頂点の組み合わせをまとめた配列リスト
	 */
	public ArrayList<int[]> getTriangleIndex(){
		createTriangleIndex();
		return index;
	}
}
