/*
 * $Id: QuadPolygonToolBarAction.java,v 1.5 2007/12/13 10:01:55 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.action.toolbar;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.ModelingWindow;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.XMLQuadPolygon;


/**
 * マウス操作による四角形ポリゴン作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.5 $. 2007/11/14
 */
public class QuadPolygonToolBarAction extends Action {
  /**　プログラム実行画面クラスMainWindowのフィールド */
  private ModelingWindow window;

  private Jamast root;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   */
  public QuadPolygonToolBarAction(final ModelingWindow window) {
    super();
    this.window = window;
    setText("Quad"); //$NON-NLS-1$
  }

  /**
   * 追加した四角形ポリゴンの情報をキャンバスとツリーに追加します。
   */
  private void updateQuad() {
    this.window.fillTree();
    this.window.createViewer();
  }

  /**
   * ツールバークリック時に実行する。
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    this.root = ModelingWindow.getRoot();
    Group group = this.root.loadModel(0).loadGroup(0);
    XMLQuadPolygon quad = new XMLQuadPolygon();

    Location loc1 = new Location(0.3f, 0.3f, 0.0f);
    Location loc2 = new Location(-0.3f, 0.3f, 0.0f);
    Location loc3 = new Location(-0.3f, -0.3f, 0.0f);
    Location loc4 = new Location(0.3f, -0.3f, 0.0f);

    Location[] locs = {loc1, loc2, loc3, loc4};

    quad.setPointLocations(locs);
    quad.setColor("blue"); //$NON-NLS-1$
    group.addXMLQuadPolygon(quad);

    updateQuad();
  }
}
