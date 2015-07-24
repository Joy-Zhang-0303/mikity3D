/*
 * $Id: TrianglePolygonToolBarAction.java,v 1.5 2007/12/13 10:01:55 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.action.toolbar;

import java.util.Arrays;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;
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
    final TrianglePolygonModel polygon = new TrianglePolygonModel();
    final VertexModel vertex0 = new VertexModel(0.0f, -0.3f, 0.0f);
    final VertexModel vertex1 = new VertexModel(0.0f, 0.3f, 0.0f);
    final VertexModel vertex2 = new VertexModel(0.0f, 0.0f, 0.3f);
    final List<VertexModel> vertices = Arrays.asList(vertex0, vertex1, vertex2);
    polygon.setVertices(vertices);
    polygon.setColor("red"); //$NON-NLS-1$
    
    final GroupModel group = this.modeler.getTargetGroup();
    group.addXMLTrianglePolygon(polygon);

    update();
    
    this.modeler.setChanged(true);
  }
}
