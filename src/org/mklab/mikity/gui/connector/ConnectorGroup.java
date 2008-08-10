/*
 * Created on 2006/01/31
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.gui.MainWindow;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;


/**
 * コネクタを含んだグループを作成するクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class ConnectorGroup {

  /**
   * コネクタを含んだグループ
   */
  private Group group;

  /**
   * コネクタを含んだグループを返す。
   * 
   * @return コネクタを含んだグループ。
   */
  public Group createConnectorGroup() {
    Jamast root = MainWindow.getRoot();
    this.group = root.loadModel(0).loadGroup(0);
    Group newgroup = new Group();
    newgroup.setName("Connector"); //$NON-NLS-1$
    this.group.addGroup(newgroup);
    return newgroup;
  }
}
