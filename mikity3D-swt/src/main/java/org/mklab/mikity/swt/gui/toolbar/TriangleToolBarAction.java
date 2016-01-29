/*
 * $Id: TrianglePolygonToolBarAction.java,v 1.5 2007/12/13 10:01:55 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.swt.gui.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.TriangleModel;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * マウス操作による三角形作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.5 $. 2007/11/14
 */
public class TriangleToolBarAction extends AbstractToolBarAction {
  /**
   * 新しく生成された<code>TriangleToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public TriangleToolBarAction(final ModelingWindow window) {
    super(window, Messages.getString("TrianglePolygonToolBarAction.0")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final GroupModel group = this.modeler.getSelectedGroup();
    final ObjectModel object = TriangleModel.createDefault();
    
    this.modeler.setSelectedObject(object);
    
    group.add(object);

    update();
    
    this.modeler.setIsChanged(true);
  }
}
