/*
 * Created on 2006/01/31
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;


/**
 * Cylinderにコネクタを表示させるためのクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class CylinderConnectorFactory {
  /**
   * 円柱プリミティブのコネクタを生成します。
   * @param cylinder 円柱プリミティブ
   * @return 円柱プリミティブのコネクタ
   */
  public XMLConnector[] createConnectors(XMLCylinder cylinder) {
    final float radius = cylinder.loadR();
    final float height = cylinder.loadHeight();

    Location location = cylinder.loadLocation();
    if (location == null) {
      location = new Location(0,0,0);
    }

    Rotation rotation = cylinder.loadRotation();
    if (rotation == null) {
      rotation = new Rotation(0,0,0);
    }
    
    final XMLConnector connectors[] = new XMLConnector[6];
    
    for (int i = 0; i < connectors.length; i++) {
      connectors[i] = new XMLConnector();
      connectors[i].setNum(i + 1);
      if (i == 0 || i == 1) {
        connectors[i].checkParameter(height);
      } else if (i == 2 || i == 3 || i == 4 || i == 5) {
        connectors[i].checkParameterR(radius);
      }
    }

    connectors[0].setLengthToCenter(height * 0.5f);
    connectors[1].setLengthToCenter(height * 0.5f);
    connectors[2].setLengthToCenter(radius);
    connectors[3].setLengthToCenter(radius);
    connectors[4].setLengthToCenter(radius);
    connectors[5].setLengthToCenter(radius);

    connectors[0].setConnectorRotation(rotation.loadXrotate(), rotation.loadYrotate(), rotation.loadZrotate());
    connectors[1].setConnectorRotation(rotation.loadXrotate() + 180.0f, rotation.loadYrotate(), rotation.loadZrotate());
    connectors[2].setConnectorRotation(-rotation.loadYrotate(), 0.0f, rotation.loadZrotate() - 90.0f);
    connectors[3].setConnectorRotation(rotation.loadYrotate(), 0.0f, rotation.loadZrotate() + 90.0f);
    connectors[4].setConnectorRotation(rotation.loadXrotate() + 90.0f, rotation.loadYrotate(), rotation.loadZrotate());
    connectors[5].setConnectorRotation(rotation.loadXrotate() - 90.0f, rotation.loadYrotate(), rotation.loadZrotate());

    final ConnectorLocationFactory turnLocation = new ConnectorLocationFactory(radius, height / 2.0f, radius, rotation.loadXrotate(), rotation.loadYrotate(), rotation.loadZrotate());

    connectors[0].setConnectorLocation(location.loadX() + turnLocation.getNewLocation2().loadX(), location.loadY() + turnLocation.getNewLocation2().loadY(), location.loadZ() + turnLocation.getNewLocation2().loadZ());
    connectors[1].setConnectorLocation(location.loadX() - turnLocation.getNewLocation2().loadX(), location.loadY() - turnLocation.getNewLocation2().loadY(), location.loadZ() - turnLocation.getNewLocation2().loadZ());
    connectors[2].setConnectorLocation(location.loadX() + turnLocation.getNewLocation1().loadX(), location.loadY() + turnLocation.getNewLocation1().loadY(), location.loadZ() + turnLocation.getNewLocation1().loadZ());
    connectors[3].setConnectorLocation(location.loadX() - turnLocation.getNewLocation1().loadX(), location.loadY() - turnLocation.getNewLocation1().loadY(), location.loadZ() - turnLocation.getNewLocation1().loadZ());
    connectors[4].setConnectorLocation(location.loadX() + turnLocation.getNewLocation3().loadX(), location.loadY() + turnLocation.getNewLocation3().loadY(), location.loadZ() + turnLocation.getNewLocation3().loadZ());
    connectors[5].setConnectorLocation(location.loadX() - turnLocation.getNewLocation3().loadX(), location.loadY() - turnLocation.getNewLocation3().loadY(), location.loadZ() - turnLocation.getNewLocation3().loadZ());
    
    return connectors;
  }
}
