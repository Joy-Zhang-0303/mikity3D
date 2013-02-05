/*
 * $Id: Library_visual_scenes.java,v 1.4 2007/12/13 10:13:03 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.util.Matrix4;
import org.simpleframework.xml.Element;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Library_visual_scene要素)
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/11/30
 */
public class LibraryVisualScenes {

  @Element
  private VisualScene visual_scene;

  /**
   * コンストラクタ
   */
  public LibraryVisualScenes() {
    this.visual_scene = new VisualScene();
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
