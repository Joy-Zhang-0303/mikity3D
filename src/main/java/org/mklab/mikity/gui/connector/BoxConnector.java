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
 * Boxにコネクタを表示させるためのクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class BoxConnector {
  /**
   * 直方体プリミティブにコネクタを追加する。
   * 
   * @param xSize 直方体の幅
   * @param ySize 直方体の高さ
   * @param zSize 直方体の奥行き
   * @param location 直方体の座標
   * @param rotation 直方体の回転
   */
  public void createBoxConnector(float xSize, float ySize, float zSize, Location location, Rotation rotation) {
    final XMLConnector connectors[] = new XMLConnector[6];
    
    for (int i = 0; i < connectors.length; i++) {
      connectors[i] = new XMLConnector();
      connectors[i].setNum(i + 1);
      if (i == 0 || i == 1) {
        connectors[i].checkParameter(ySize);
      } else if (i == 2 || i == 3) {
        connectors[i].checkParameter(xSize);
      } else if (i == 4 || i == 5) {
        connectors[i].checkParameter(zSize);
      }
    }

    connectors[0].setLengthToCenter(ySize * 0.5f);
    connectors[1].setLengthToCenter(ySize * 0.5f);
    connectors[2].setLengthToCenter(xSize * 0.5f);
    connectors[3].setLengthToCenter(xSize * 0.5f);
    connectors[4].setLengthToCenter(zSize * 0.5f);
    connectors[5].setLengthToCenter(zSize * 0.5f);

    connectors[0].setConnectorRotation(rotation.loadXrotate(), rotation.loadYrotate(), rotation.loadZrotate());
    connectors[1].setConnectorRotation(rotation.loadXrotate() + 180.0f, rotation.loadYrotate(), rotation.loadZrotate());
    connectors[2].setConnectorRotation(-rotation.loadYrotate(), 0.0f, rotation.loadZrotate() - 90.0f);
    connectors[3].setConnectorRotation(rotation.loadYrotate(), 0.0f, rotation.loadZrotate() + 90.0f);
    connectors[4].setConnectorRotation(rotation.loadXrotate() + 90.0f, rotation.loadYrotate(), rotation.loadZrotate());
    connectors[5].setConnectorRotation(rotation.loadXrotate() - 90.0f, rotation.loadYrotate(), rotation.loadZrotate());

    final TurnLocation turnLocation = new TurnLocation(xSize / 2.0f, ySize / 2.0f, zSize / 2.0f, rotation.loadXrotate(), rotation.loadYrotate(), rotation.loadZrotate());

    connectors[0].setConnectorLocation(location.loadX() + turnLocation.getNewLocation2().loadX(), location.loadY() + turnLocation.getNewLocation2().loadY(), location.loadZ() + turnLocation.getNewLocation2().loadZ());
    connectors[1].setConnectorLocation(location.loadX() - turnLocation.getNewLocation2().loadX(), location.loadY() - turnLocation.getNewLocation2().loadY(), location.loadZ() - turnLocation.getNewLocation2().loadZ());
    connectors[2].setConnectorLocation(location.loadX() + turnLocation.getNewLocation1().loadX(), location.loadY() + turnLocation.getNewLocation1().loadY(), location.loadZ() + turnLocation.getNewLocation1().loadZ());
    connectors[3].setConnectorLocation(location.loadX() - turnLocation.getNewLocation1().loadX(), location.loadY() - turnLocation.getNewLocation1().loadY(), location.loadZ() - turnLocation.getNewLocation1().loadZ());
    connectors[4].setConnectorLocation(location.loadX() + turnLocation.getNewLocation3().loadX(), location.loadY() + turnLocation.getNewLocation3().loadY(), location.loadZ() + turnLocation.getNewLocation3().loadZ());
    connectors[5].setConnectorLocation(location.loadX() - turnLocation.getNewLocation3().loadX(), location.loadY() - turnLocation.getNewLocation3().loadY(), location.loadZ() - turnLocation.getNewLocation3().loadZ());

    /** コネクタの所属するグループ */
    final ConnectorGroupFactory group = new ConnectorGroupFactory();

    Group cGroup = group.createConnectorGroup();
    for (int x = 0; x < connectors.length; x++) {
      cGroup.addXMLConnector(connectors[x]);
    }
  }
}
