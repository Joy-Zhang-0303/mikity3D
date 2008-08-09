/*
 * $Id: Visual_scene.java,v 1.4 2007/12/13 10:13:03 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.util.ArrayList;
import javax.vecmath.Matrix4f;
import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.xml.model.Group;

/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Visual_scene要素)
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/11/30
 */
public class Visual_scene {
	@XmlElement
	private ArrayList<Node> node;
	
	/**
	 * ノードの名前リスト
	 */
	private ArrayList<String> nameList;
	/**
	 * 変換行列リスト
	 */
	private ArrayList<Matrix4f> matrixList;
	
	private Group rootGroup;

	/**
	 * コンストラクタ
	 */
	public Visual_scene(){
		node = new ArrayList<Node>();
		nameList = new ArrayList<String>();
		matrixList = new ArrayList<Matrix4f>();
		rootGroup = new Group();
	}
	
	/**
	 * 各ノードの名前が追加されているリストを返す
	 * @return　nameList 各ノードの名前が追加されているリスト
	 */
	public ArrayList<String> getNodeNameList(){
		for(int i=0; i<node.size(); i++){
				nameList.add(node.get(i).loadGeometryURL());
		}
		return nameList;
	}
	
	/**
	 * 変換行列が追加されているリストを返す
	 * @return　matrixList　変換行列が追加されているリスト
	 */
	public ArrayList<Matrix4f> getMatrixList(){
		for(int i=0; i<node.size(); i++){
			matrixList.add(node.get(i).loadMatrix());
		}
		return matrixList;
	}

	/**
	 * 各ノードごとに変換行列を生成する
	 */
	public void createMatrix() {
		for(int i=0; i<node.size(); i++){
			node.get(i).createMatrix();
		}
	}
	
	private void createScene(){
		for(int i=0; i<node.size(); i++){
			node.get(i).createGroup();
			node.get(i).createScene();
		}
		for(int i=0; i<node.size(); i++){
			if(node.get(i) != null && node.get(i).getGroup().loadName() != null){
				rootGroup.addGroup(node.get(i).getGroup());
			}
		}
	}
	
	public Group getScene(){
		createScene();
		return rootGroup;
	}
}
