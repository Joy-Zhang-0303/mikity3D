/*
 * Created on 2006/01/31
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.gui.ModelingWindow;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLSphere;


/**
 * プリミティブに適したコネクタを追加するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class PrimitiveConnector {
  /**
   * プリミティブごとのの各パラメータを取得し、キャンバス上のプリミティブごとに適したコネクタを追加する。 
   * 
   * @param primitive コネクタを追加されるプリミティブ
   */
  public void addConnectors(Object primitive) {
    final XMLConnector[] connectors;
    if (primitive instanceof XMLBox) {
      connectors = createConnectors((XMLBox)primitive);
    } else if (primitive instanceof XMLCone) {
      connectors = createConnectors((XMLCone)primitive);
    } else if (primitive instanceof XMLCylinder) {
      connectors = createConnectors((XMLCylinder)primitive);
    } else if (primitive instanceof XMLSphere) {
      connectors = createConnectors((XMLSphere)primitive);
    } else {
      throw new IllegalArgumentException();
    }
    
    final ConnectorGroupFactory groupFactory = new ConnectorGroupFactory();
    final Group group = groupFactory.createGroup();
    
    for (int i = 0; i < connectors.length; i++) {
      group.addXMLConnector(connectors[i]);
    }
  }

  private XMLConnector[] createConnectors(final XMLSphere sphere) {
    final SphereConnectorFactory factory = new SphereConnectorFactory();
    final XMLConnector[] connectors = factory.createConnectors(sphere);
    return connectors;
  }

  private XMLConnector[] createConnectors(final XMLCylinder cylinder) {
    final CylinderConnectorFactory factory = new CylinderConnectorFactory();
    final XMLConnector[] connectors = factory.createConnectors(cylinder);
    return connectors;
  }

  private XMLConnector[] createConnectors(final XMLCone cone) {
    final ConeConnectorFactory factory = new ConeConnectorFactory();
    final XMLConnector[] connectors = factory.createConnectors(cone);
    return connectors;
  }

  private XMLConnector[] createConnectors(final XMLBox box) {
    final BoxConnectorFactory factory = new BoxConnectorFactory();
    final XMLConnector[] connectors = factory.createConnectors(box);
    return connectors;
  }

  /**
   * コネクタNを持つグループを生成します。
   * 
   * @return　コネクタNを持つグループ
   */
  public Group createNorthConnectorGroup() {
    final Group northGroup = new Group();
    northGroup.setName("ConnectorN"); //$NON-NLS-1$

    final Jamast root = ModelingWindow.getRoot();
    final Group routGroup = root.loadModel(0).loadGroup(0);
    routGroup.addGroup(northGroup);
    return northGroup;
  }

  /**
   * コネクタSを持つグループを生成します。
   * 
   * @return　コネクタSを持つグループ
   */
  public Group createSouthConnectorGroup() {   
    final Group southGroup = new Group();
    southGroup.setName("ConnectorS"); //$NON-NLS-1$
    
    final Jamast root = ModelingWindow.getRoot();
    final Group rootGroup = root.loadModel(0).loadGroup(0);
    rootGroup.addGroup(southGroup);
    return southGroup;
  }
}
