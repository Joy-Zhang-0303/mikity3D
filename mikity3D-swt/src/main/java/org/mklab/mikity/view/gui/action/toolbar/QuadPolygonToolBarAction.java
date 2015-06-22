/*
 * $Id: QuadPolygonToolBarAction.java,v 1.5 2007/12/13 10:01:55 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.action.toolbar;

import java.util.Arrays;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.Translation;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
import org.mklab.mikity.view.gui.ModelingWindow;


/**
 * マウス操作による四角形ポリゴン作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.5 $. 2007/11/14
 */
public class QuadPolygonToolBarAction extends Action {
  /**　プログラム実行画面クラスMainWindowのフィールド */
  private ModelingWindow window;

  /**
   * 新しく生成された<code>QuadPolygonToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public QuadPolygonToolBarAction(final ModelingWindow window) {
    this.window = window;
    setText("QuadPolygon"); //$NON-NLS-1$
  }

  /**
   * 追加した四角形ポリゴンの情報をキャンバスとツリーに追加します。
   */
  private void updateQuad() {
    this.window.fillTree();
    this.window.createViewer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final XMLQuadPolygon polygon = new XMLQuadPolygon();
    final Translation p1 = new Translation(0.3f, 0.3f, 0.0f);
    final Translation p2 = new Translation(-0.3f, 0.3f, 0.0f);
    final Translation p3 = new Translation(-0.3f, -0.3f, 0.0f);
    final Translation p4 = new Translation(0.3f, -0.3f, 0.0f);
    //final Location[] locations = {p1, p2, p3, p4};
    polygon.setPointLocations(Arrays.asList(p1, p2, p3, p4));
    polygon.setColor("blue"); //$NON-NLS-1$
    
    final Mikity3d root = this.window.getRoot();
    final Group rootGroup = root.getModel(0).getGroup(0);
    rootGroup.addXMLQuadPolygon(polygon);

    updateQuad();
    
    this.window.setDirty(true);
  }
}
