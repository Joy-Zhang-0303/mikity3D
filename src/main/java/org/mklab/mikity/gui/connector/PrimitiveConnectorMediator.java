/*
 * Created on 2006/01/31
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.xml.JamastModel;
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
public class PrimitiveConnectorMediator {
  /** */
  private JamastModel model;
  
  /**
   * 新しく生成された<code>PrimitiveConnectorMediator</code>オブジェクトを初期化します。
   * @param model モデル
   */
  public PrimitiveConnectorMediator(final JamastModel model) {
    this.model = model;
  }
  
  /**
   * プリミティブごとのの各パラメータを取得し、キャンバス上のプリミティブごとに適したコネクタを追加する。 
   * 
   * @param primitive コネクタを追加されるプリミティブ
   */
  public void addConnectorsTo(Object primitive) {
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

    final Group group = createConnectorGroup();
    for (int i = 0; i < connectors.length; i++) {
      group.addXMLConnector(connectors[i]);
    }
  }

  /**
   * コネクタを含んだグループを返します。
   * 
   * @return コネクタを含んだグループ
   */
  private Group createConnectorGroup() {
    //final Jamast root = ModelingWindow.getRoot();
    //final JamastModel model = root.loadModel(0);
    
    final Group rootGroup = this.model.loadGroup(0);
    
    final Group group = new Group();
    group.setName("Connector"); //$NON-NLS-1$
    rootGroup.addGroup(group);
    return group;
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
}
