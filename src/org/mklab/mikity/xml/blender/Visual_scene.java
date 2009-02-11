/*
 * $Id: Visual_scene.java,v 1.4 2007/12/13 10:13:03 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4f;
import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.xml.model.Group;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Visual_scene要素)
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/11/30
 */
public class Visual_scene {

  @XmlElement
  private List<Node> node;

  /**
   * ノードの名前リスト
   */
  private List<String> nameList;
  /**
   * 変換行列リスト
   */
  private List<Matrix4f> matrixList;

  private Group rootGroup;

  /**
   * コンストラクタ
   */
  public Visual_scene() {
    this.node = new ArrayList<Node>();
    this.nameList = new ArrayList<String>();
    this.matrixList = new ArrayList<Matrix4f>();
    this.rootGroup = new Group();
  }

  /**
   * 各ノードの名前が追加されているリストを返す
   * 
   * @return　nameList 各ノードの名前が追加されているリスト
   */
  public List<String> getNodeNameList() {
    for (int i = 0; i < this.node.size(); i++) {
      this.nameList.add(this.node.get(i).loadGeometryURL());
    }
    return this.nameList;
  }

  /**
   * 変換行列が追加されているリストを返す
   * 
   * @return　matrixList　変換行列が追加されているリスト
   */
  public List<Matrix4f> getMatrixList() {
    for (int i = 0; i < this.node.size(); i++) {
      this.matrixList.add(this.node.get(i).loadMatrix());
    }
    return this.matrixList;
  }

  /**
   * 各ノードごとに変換行列を生成する
   */
  public void createMatrix() {
    for (int i = 0; i < this.node.size(); i++) {
      this.node.get(i).createMatrix();
    }
  }

  private void createScene() {
    for (int i = 0; i < this.node.size(); i++) {
      this.node.get(i).createGroup();
      this.node.get(i).createScene();
    }
    for (int i = 0; i < this.node.size(); i++) {
      if (this.node.get(i) != null && this.node.get(i).getGroup().loadName() != null) {
        this.rootGroup.addGroup(this.node.get(i).getGroup());
      }
    }
  }

  /**
   * @return scene
   */
  public Group getScene() {
    createScene();
    return this.rootGroup;
  }
}
