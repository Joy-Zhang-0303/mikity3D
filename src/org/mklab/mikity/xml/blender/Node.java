/*
 * $Id: Node.java,v 1.6 2008/02/03 04:25:00 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.util.ArrayList;
import javax.vecmath.Matrix4f;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Linkdata;

/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Node要素)
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
public class Node {
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String type;
	@XmlElement
	private String matrix;
	@XmlElement
	private Instance_Geometry instance_geometry;
	@XmlElement
	private ArrayList<Node> node;
	
	private ArrayList<Matrix4f> matrix_node;
	
	private String url;
	
	private Group g;
	
	/**
	 * タブ・改行が含まれる位置を記録するリスト
	 */
	private ArrayList<Integer> tab;
	/**
	 * 列ベクトルリスト
	 */
	private ArrayList<String> vector;
	/**
	 * 変換行列
	 */
	private Matrix4f matrix4f;
	
	/**
	 * コンストラクタ
	 */
	public Node(){
		instance_geometry = new Instance_Geometry();
		node = new ArrayList<Node>();
		tab = new ArrayList<Integer>();
		vector = new ArrayList<String>();
		matrix4f = new Matrix4f();
		matrix_node = new ArrayList<Matrix4f>();
		g = new Group();
	}

	/**
	 * ノードの名前を返す
	 * @return name　ノードの名前
	 */
	public String loadName(){
		return name;
	}
	
	public String loadGeometryURL(){
		return url;
	}
	
	/**
	 * 変換行列を返す
	 * @return matrix4f　変換行列
	 */
	public Matrix4f loadMatrix(){
		return matrix4f;
	}
	
	/**
	 * 変換行列の要素をまとめた文字列から余分なものを省いて各要素の値を抽出して変換行列を生成する
	 */
	public void createMatrix(){
		if(name.indexOf("-N") != -1){
			name = name.substring(0, name.indexOf("-N"));
		}
		if(name.indexOf("-J") != -1){
			name = name.substring(0, name.indexOf("-J"));
		}
		for(int i=0; i<matrix.length(); i++){
			if(matrix.indexOf("\n\t\t\t\t",i) == i){
				tab.add(i);
			}
		}
		for(int i=0; i<tab.size()-1; i++){
			if(i == tab.size()-2){
				vector.add(matrix.substring(tab.get(i)+6,tab.get(tab.size()-1)-1));
			}else{
				vector.add(matrix.substring(tab.get(i)+6,tab.get(i+1)-1));
			}
		}
		for(int i=0; i<vector.size(); i++){
			ArrayList<Integer> space = new ArrayList<Integer>();
			for(int j=0; j<vector.get(i).length(); j++){
				if(vector.get(i).indexOf(" ", j) ==j){
					space.add(j);
				}
			}
			float[] elements = new float[4];
			elements[0]=Float.parseFloat(vector.get(i).substring(0,space.get(0)));
			elements[1]=Float.parseFloat(vector.get(i).substring(space.get(0)+1,space.get(1)));
			elements[2]=Float.parseFloat(vector.get(i).substring(space.get(1)+1,space.get(2)));
			elements[3]=Float.parseFloat(vector.get(i).substring(space.get(2)+1));
			matrix4f.setRow(i, elements);
		}
		if(node.size() != 0){
			for(int i=0; i<node.size(); i++){
				node.get(i).createMatrix();
				matrix_node.add(node.get(i).loadMatrix());
			}			
		}
		setURL();
	}
	
	public String loadType(){
		return type;
	}
	
	
	private void setURL(){
		url = instance_geometry.loadURL();
		if(url != null){
			if(url.indexOf("-G") != -1){
				url = url.substring(1, url.indexOf("-G"));
			}else{
				url = url.substring(1);
			}
		}
	}
	
	public void createScene(){
		for(int i=0; i<node.size(); i++){
			node.get(i).createGroup();
			node.get(i).createScene();
		}
		for(int i=0; i<node.size(); i++){
			if(node.get(i) != null){
				g.addGroup(node.get(i).getGroup());
			}
		}
	}
	
	public void createGroup(){
		if(type != null){
			if(type.equals("JOINT")){
				g.setName(name);
				Linkdata[] linkdata = new Linkdata[3];
				linkdata[0] = new Linkdata();
				linkdata[0].setTarget("locationX");
				linkdata[0].setConst(matrix4f.m03);
				linkdata[1] = new Linkdata();
				linkdata[1].setTarget("locationY");
				linkdata[1].setConst(matrix4f.m13);
				linkdata[2] = new Linkdata();
				linkdata[2].setTarget("locationZ");
				linkdata[2].setConst(matrix4f.m23);
				g.setLinkdata(linkdata);
			}
		}
	}
	
	public Group getGroup(){
		return g;
	}
}