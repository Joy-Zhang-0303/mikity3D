/*
 * Created on 2006/01/31
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.gui.ModelingWindow;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;


/**
 * コネクタを含んだグループを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class ConnectorGroupFactory {
  /**
   * コネクタを含んだグループを返します。
   * 
   * @return コネクタを含んだグループ
   */
  public Group createConnectorGroup() {
    final Jamast root = ModelingWindow.getRoot();
    final Group connectorGroup = new Group();
    connectorGroup.setName("Connector"); //$NON-NLS-1$
    
    final Group routGroup = root.loadModel(0).loadGroup(0);
    routGroup.addGroup(connectorGroup);
    return connectorGroup;
  }
}
