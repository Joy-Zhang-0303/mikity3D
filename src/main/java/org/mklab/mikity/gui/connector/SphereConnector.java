/*
 * Created on 2006/01/31
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLSphere;

/**
 * Sphereにコネクタを表示させるためのクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class SphereConnector {
  /**
   * 球体プリミティブにコネクタを追加する。
   * @param sphere 球体プリミティブ
   */
  public void addConnectors(XMLSphere sphere) {
    final float radius = sphere.loadR();

    Location location = sphere.loadLocation();
    if (location == null) {
      location = new Location(0,0,0);
    }

    Rotation rotation = sphere.loadRotation();
    if (rotation == null) {
      rotation = new Rotation(0,0,0);
    }
    
    
    final XMLConnector cnnectors[] = new XMLConnector[6];
    
    for (int i = 0; i < cnnectors.length; i++) {
      cnnectors[i] = new XMLConnector();
      cnnectors[i].setNum(i + 1);
      cnnectors[i].checkParameterR(radius);
    }

    for (int i = 0; i < cnnectors.length; i++) {
      cnnectors[i].setLengthToCenter(radius);
    }

    cnnectors[0].setConnectorRotation(0.0f, 0.0f, 0.0f);
    cnnectors[1].setConnectorRotation(180.0f, 0.0f, 0.0f);
    cnnectors[2].setConnectorRotation(90.0f, 0.0f, 0.0f);
    cnnectors[3].setConnectorRotation(-90.0f, 0.0f, 0.0f);
    cnnectors[4].setConnectorRotation(0.0f, 0.0f, -90.0f);
    cnnectors[5].setConnectorRotation(0.0f, 0.0f, 90.0f);

    cnnectors[0].setConnectorLocation(location.loadX(), location.loadY() + radius, location.loadZ());
    cnnectors[1].setConnectorLocation(location.loadX(), location.loadY() - radius, location.loadZ());
    cnnectors[2].setConnectorLocation(location.loadX(), location.loadY(), location.loadZ() + radius);
    cnnectors[3].setConnectorLocation(location.loadX(), location.loadY(), location.loadZ() - radius);
    cnnectors[4].setConnectorLocation(location.loadX() + radius, location.loadY(), location.loadZ());
    cnnectors[5].setConnectorLocation(location.loadX() - radius, location.loadY(), location.loadZ());

    final ConnectorGroupFactory factory = new ConnectorGroupFactory();
    final Group group = factory.createGroup();

    for (int x = 0; x < cnnectors.length; x++) {
      group.addXMLConnector(cnnectors[x]);
    }
  }
}
