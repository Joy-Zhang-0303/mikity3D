/*
 * $Id: QuadPolygonToolBarAction.java,v 1.5 2007/12/13 10:01:55 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.action.toolbar;

import java.util.Arrays;

import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.view.gui.ModelingWindow;


/**
 * マウス操作による四角形ポリゴン作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.5 $. 2007/11/14
 */
public class QuadPolygonToolBarAction extends AbstractToolBarAction {
  /**
   * 新しく生成された<code>QuadPolygonToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public QuadPolygonToolBarAction(final ModelingWindow window) {
    super(window, "QuadPolygon"); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final QuadPolygonModel polygon = new QuadPolygonModel();
    final VertexModel vertex0 = new VertexModel(0.0f, -0.3f, 0.0f);
    final VertexModel vertex1 = new VertexModel(0.0f, 0.3f, 0.0f);
    final VertexModel vertex2 = new VertexModel(0.0f, 0.3f, 0.3f);
    final VertexModel vertex3 = new VertexModel(0.0f, -0.3f, 0.3f);
    polygon.setVertices(Arrays.asList(vertex0, vertex1, vertex2, vertex3));
    final ColorModel color = new ColorModel("blue"); //$NON-NLS-1$
    polygon.setColor(color);
    
    final GroupModel group = this.modeler.getTargetGroup();
    group.add(polygon);

    update();
    
    this.modeler.setChanged(true);
  }
}
