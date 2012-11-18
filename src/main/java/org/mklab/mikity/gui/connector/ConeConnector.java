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


/**
 * Coneにコネクタを表示させるためのクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class ConeConnector {
  /**
   * 円錐プリミティブにコネクタを追加する。
   * 
   * @param radius 円錐の半径
   * @param height 円錐の高さ
   * @param location 円錐の座標
   * @param rotation 円錐の回転
   */
  public void createConeConnector(float radius, float height, Location location, Rotation rotation) {
    /** コネクタ1~2の座標 */
    final XMLConnector connectors[] = new XMLConnector[2];
    
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


    connectors[0].setConnectorRotation(rotation.loadXrotate(), rotation.loadYrotate(), rotation.loadZrotate());
    connectors[1].setConnectorRotation(rotation.loadXrotate() + 180.0f, rotation.loadYrotate(), rotation.loadZrotate());

    final TurnLocation turnLocation = new TurnLocation(radius, height / 2.0f, radius, rotation.loadXrotate(), rotation.loadYrotate(), rotation.loadZrotate());

    connectors[0].setConnectorLocation(location.loadX() + turnLocation.getNewLocation2().loadX(), location.loadY() + turnLocation.getNewLocation2().loadY(), location.loadZ() + turnLocation.getNewLocation2().loadZ());
    connectors[1].setConnectorLocation(location.loadX() - turnLocation.getNewLocation2().loadX(), location.loadY() - turnLocation.getNewLocation2().loadY(), location.loadZ() - turnLocation.getNewLocation2().loadZ());

    final ConnectorGroupFactory group = new ConnectorGroupFactory();
    final Group connectorGroup = group.createConnectorGroup();

    for (int x = 0; x < connectors.length; x++) {
      connectorGroup.addXMLConnector(connectors[x]);
    }
  }
}
