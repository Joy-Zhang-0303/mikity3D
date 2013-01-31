/*
 * $Id: Library_visual_scenes.java,v 1.4 2007/12/13 10:13:03 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.blender;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.util.Matrix4;


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
  public List<String> getNodeNames() {
    return this.visual_scene.getNodeNames();
  }

  /**
   * 変換行列が追加されているリストを返す
   * 
   * @return　visual_scene.getMatrixList()　変換行列が追加されているリスト
   */
  public List<Matrix4> getMatrices() {
    return this.visual_scene.getTransformMatrices();
  }

  /**
   * 変換行列をせいせいします。
   */
  public void createMatrix() {
    this.visual_scene.createTransformMatrix();
  }

  /**
   * @return scene
   */
  public Group getScene() {
    return this.visual_scene.getScene();
  }
}
