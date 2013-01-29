/*
 * $Id: TrianglePolygonToolBarAction.java,v 1.5 2007/12/13 10:01:55 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.action.toolbar;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.ModelingWindow;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


/**
 * マウス操作による三角形ポリゴン作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.5 $. 2007/11/14
 */
public class TrianglePolygonToolBarAction extends Action {
  /** プログラム実行画面クラスMainWindowのフィールド  */
  private ModelingWindow window;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   */
  public TrianglePolygonToolBarAction(final ModelingWindow window) {
    this.window = window;
    setText("TrianglePolygon"); //$NON-NLS-1$
  }

  /**
   * 追加した三角形ポリゴンの情報をキャンバスとツリーに追加します。
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
    final XMLTrianglePolygon polygon = new XMLTrianglePolygon();
    final Location p1 = new Location(0.3f, 0.3f, 0.0f);
    final Location p2 = new Location(-0.3f, 0.3f, 0.0f);
    final Location p3 = new Location(-0.3f, -0.3f, 0.0f);
    final Location[] locations = {p1, p2, p3};
    polygon.setPointLocations(locations);
    polygon.setColor("red"); //$NON-NLS-1$
    
    final Jamast root = this.window.getRoot();
    final Group rootGroup = root.loadModel(0).loadGroup(0);
    rootGroup.addXMLTrianglePolygon(polygon);

    updateTriagle();
  }
}
