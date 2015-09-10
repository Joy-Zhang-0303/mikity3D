/*
 * $Id: TrianglePolygonToolBarAction.java,v 1.5 2007/12/13 10:01:55 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.action.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
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
    final GroupModel group = this.modeler.getTargetGroup();
    final PrimitiveModel primitive = TrianglePolygonModel.createDefault();
    group.add(primitive);

    update();
    
    this.modeler.setIsChanged(true);
  }
}
