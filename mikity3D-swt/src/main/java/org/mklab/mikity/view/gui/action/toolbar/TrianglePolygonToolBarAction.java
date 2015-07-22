/*
 * $Id: TrianglePolygonToolBarAction.java,v 1.5 2007/12/13 10:01:55 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.action.toolbar;

import java.util.Arrays;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.Vertex;
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;
import org.mklab.mikity.view.gui.ModelingWindow;


/**
 * マウス操作による三角形ポリゴン作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.5 $. 2007/11/14
 */
public class TrianglePolygonToolBarAction extends AbstractToolBarAction {
  /**
   * 新しく生成された<code>TrianglePolygonToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public TrianglePolygonToolBarAction(final ModelingWindow window) {
    super(window, "TrianglePolygon"); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final XMLTrianglePolygon polygon = new XMLTrianglePolygon();
    final Vertex vertex0 = new Vertex(0.0f, -0.3f, 0.0f);
    final Vertex vertex1 = new Vertex(0.0f, 0.3f, 0.0f);
    final Vertex vertex2 = new Vertex(0.0f, 0.0f, 0.3f);
    final List<Vertex> vertices = Arrays.asList(vertex0, vertex1, vertex2);
    polygon.setVertices(vertices);
    polygon.setColor("red"); //$NON-NLS-1$
    
    final Group rootGroup = this.modeler.getTargetGroup();
    rootGroup.addXMLTrianglePolygon(polygon);

    update();
    
    this.window.setChanged(true);
  }
}
