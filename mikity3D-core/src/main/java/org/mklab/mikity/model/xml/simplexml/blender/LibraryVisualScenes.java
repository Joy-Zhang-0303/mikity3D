/*
 * $Id: Library_visual_scenes.java,v 1.4 2007/12/13 10:13:03 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.util.Matrix4;
import org.simpleframework.xml.Element;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(LibraryVisualScene要素)です。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/11/30
 */
public class LibraryVisualScenes {

  @Element
  private VisualScene visualScene;

  /**
   * 新しく生成された<code>LibraryVisualScenes</code>オブジェクトを初期化します。
   */
  public LibraryVisualScenes() {
    this.visualScene = new VisualScene();
  }

  /**
   * 各ノードの名前が追加されているリストを返します。
   * 
   * @return　各ノードの名前が追加されているリスト
   */
  public List<String> getNodeNames() {
    return this.visualScene.getNodeNames();
  }

  /**
   * 変換行列が追加されているリストを返します。
   * 
   * @return　変換行列が追加されているリスト
   */
  public List<Matrix4> getMatrices() {
    return this.visualScene.getTransformMatrices();
  }

  /**
   * 変換行列を生成します。
   */
  public void createMatrix() {
    this.visualScene.createTransformMatrix();
  }

  /**
   * @return scene
   */
  public GroupModel getScene() {
    return this.visualScene.getScene();
  }
}
