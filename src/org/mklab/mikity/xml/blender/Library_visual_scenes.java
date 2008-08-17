/*
 * $Id: Library_visual_scenes.java,v 1.4 2007/12/13 10:13:03 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.util.ArrayList;

import javax.vecmath.Matrix4f;
import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.xml.model.Group;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Library_visual_scene要素)
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/11/30
 */
public class Library_visual_scenes {

  @XmlElement
  private Visual_scene visual_scene;

  /**
   * コンストラクタ
   */
  public Library_visual_scenes() {
    this.visual_scene = new Visual_scene();
  }

  /**
   * 各ノードの名前が追加されているリストを返す
   * 
   * @return　visual_scene.getNodeNameList()　各ノードの名前が追加されているリスト
   */
  public ArrayList<String> getNodeNameList() {
    return this.visual_scene.getNodeNameList();
  }

  /**
   * 変換行列が追加されているリストを返す
   * 
   * @return　visual_scene.getMatrixList()　変換行列が追加されているリスト
   */
  public ArrayList<Matrix4f> getMatrixList() {
    return this.visual_scene.getMatrixList();
  }

  /**
   * 変換行列を生成する
   */
  public void createMatrix() {
    this.visual_scene.createMatrix();
  }

  /**
   * @return scene
   */
  public Group getScene() {
    return this.visual_scene.getScene();
  }
}
