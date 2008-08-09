/*
 * $Id: Source.java,v 1.4 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import org.mklab.mikity.xml.model.Location;

/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Source要素)
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/11/30
 */
public class Source {
	@XmlElement
	private String float_array;
	
	/**
	 * 文字列の空白の位置を記したリスト
	 */
	private ArrayList<Integer> space;
	/**
	 * 座標の値を記したリスト
	 */
	private ArrayList<Float> locationValue;
	/**
	 * 頂点座標をまとめたリスト
	 */
	private ArrayList<Location> vertexLocation;
	/**
	 * 法線ベクトルをまとめたリスト
	 */
	private ArrayList<Location> normalLocation;
	
	/**
	 * コンストラクタ
	 */
	public Source(){
		space =new ArrayList<Integer>();
		locationValue = new ArrayList<Float>();
		vertexLocation = new ArrayList<Location>();
		normalLocation = new ArrayList<Location>();
	}
	
	/**
	 * 頂点座標をまとめたリストを返す
	 * @return　vertexLocation 頂点座標をまとめたリスト
	 */
	public ArrayList<Location> getVertexLocation(){
			setVertexLocation();
		return vertexLocation;
	}
	
	/**
	 * 法線ベクトルをまとめたリストを返す
	 * @return　vertexLocation 法線ベクトルをまとめたリスト
	 */
	public ArrayList<Location> getNormalLocation(){
			setNormalLocation();
		return normalLocation;
	}
	
	/**
	 * 頂点座標一覧を記述した文字列から空白を除外し、
	 * 前から3つずつ頂点座標からLocationオブジェクトを作成してリストに加えていく。
	 */
	private void setVertexLocation(){
			divideString();

			for(int i=0; i<locationValue.size(); i+=3){
				vertexLocation.add(new Location(locationValue.get(i),locationValue.get(i+1),locationValue.get(i+2)));
			}
		
	}
	
	/**
	 * 頂法線ベクトル一覧を記述した文字列から空白を除外し、
	 * 前から3つずつ法線ベクトルからLocationオブジェクトを作成してリストに加えていく。
	 */
	private void setNormalLocation(){
			divideString();

			for(int i=0; i<locationValue.size(); i+=3){
				normalLocation.add(new Location(locationValue.get(i),locationValue.get(i+1),locationValue.get(i+2)));
			}
		
	}
	
	/**
	 * 数値の文字列をスペースの場所で分割し、数値として格納する
	 */
	private void divideString(){
		for(int j=0;j<float_array.length(); j++){
			if(float_array.charAt(j) == ' '){
				space.add(j);
			}
		}

		for(int j=0; j<space.size()+1; j++){
			if(j==0){
				locationValue.add(Float.parseFloat(float_array.substring(0, space.get(0))));
			}else if(j == space.size()){
				locationValue.add(Float.parseFloat(float_array.substring(space.get(space.size()-1)+1)));
			}
			else{
				locationValue.add(Float.parseFloat(float_array.substring(space.get(j-1)+1, space.get(j))));
			}
		}
	}
}
