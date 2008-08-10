/*
 * $Id: TrianglePolygonToolBarAction.java,v 1.5 2007/12/13 10:01:55 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.action.toolbar;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.MainWindow;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


/**
 * マウス操作による三角形ポリゴン作成のためのツールバーを作成する。
 * 
 * @author SHOGO
 * @version $Revision: 1.5 $. 2007/11/14
 */
public class TrianglePolygonToolBarAction extends Action {

  /**
   * プログラム実行画面クラスMainWindowのフィールド
   */
  private MainWindow window;
  private Jamast root;

  /**
   * コンストラクター
   * 
   * @param window
   */
  public TrianglePolygonToolBarAction(final MainWindow window) {
    super();
    this.window = window;
    setText("Triagle");
  }

  /**
   * 追加した三角形ポリゴンの情報をキャンバスとツリーに追加させる。
   */
  private void updateTriagle() {
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
    this.root = MainWindow.getRoot();
    Group group = this.root.loadModel(0).loadGroup(0);
    XMLTrianglePolygon triangle = new XMLTrianglePolygon();

    Location loc1 = new Location(0.3f, 0.3f, 0.0f);
    Location loc2 = new Location(-0.3f, 0.3f, 0.0f);
    Location loc3 = new Location(-0.3f, -0.3f, 0.0f);

    Location[] locs = {loc1, loc2, loc3};
    triangle.setPointLocations(locs);
    triangle.setColor("red");
    group.addXMLTrianglePolygon(triangle);

    updateTriagle();
  }
}
