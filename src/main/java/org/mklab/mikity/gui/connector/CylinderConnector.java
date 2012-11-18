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
 * Cylinderにコネクタを表示させるためのクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class CylinderConnector {
  /**
   * 円柱プリミティブにコネクタを追加する。
   * 
   * @param r 円柱の半径
   * @param h 円柱の高さ
   * @param loc 円柱の座標
   * @param rot 円柱の回転
   */
  public void createCylinderConnector(float r, float h, Location loc, Rotation rot) {
    /** コネクタ1~6 */
    final XMLConnector connectors[] = new XMLConnector[6];
    
    for (int i = 0; i < connectors.length; i++) {
      connectors[i] = new XMLConnector();
      connectors[i].setNum(i + 1);
      if (i == 0 || i == 1) {
        connectors[i].checkParameter(h);
      } else if (i == 2 || i == 3 || i == 4 || i == 5) {
        connectors[i].checkParameterR(r);
      }
    }

    connectors[0].setLengthToCenter(h * 0.5f);
    connectors[1].setLengthToCenter(h * 0.5f);
    connectors[2].setLengthToCenter(r);
    connectors[3].setLengthToCenter(r);
    connectors[4].setLengthToCenter(r);
    connectors[5].setLengthToCenter(r);

    connectors[0].setConnectorRotation(rot.loadXrotate(), rot.loadYrotate(), rot.loadZrotate());
    connectors[1].setConnectorRotation(rot.loadXrotate() + 180.0f, rot.loadYrotate(), rot.loadZrotate());
    connectors[2].setConnectorRotation(-rot.loadYrotate(), 0.0f, rot.loadZrotate() - 90.0f);
    connectors[3].setConnectorRotation(rot.loadYrotate(), 0.0f, rot.loadZrotate() + 90.0f);
    connectors[4].setConnectorRotation(rot.loadXrotate() + 90.0f, rot.loadYrotate(), rot.loadZrotate());
    connectors[5].setConnectorRotation(rot.loadXrotate() - 90.0f, rot.loadYrotate(), rot.loadZrotate());

    final TurnLocation turnLocation = new TurnLocation(r, h / 2.0f, r, rot.loadXrotate(), rot.loadYrotate(), rot.loadZrotate());

    connectors[0].setConnectorLocation(loc.loadX() + turnLocation.getNewLocation2().loadX(), loc.loadY() + turnLocation.getNewLocation2().loadY(), loc.loadZ() + turnLocation.getNewLocation2().loadZ());
    connectors[1].setConnectorLocation(loc.loadX() - turnLocation.getNewLocation2().loadX(), loc.loadY() - turnLocation.getNewLocation2().loadY(), loc.loadZ() - turnLocation.getNewLocation2().loadZ());
    connectors[2].setConnectorLocation(loc.loadX() + turnLocation.getNewLocation1().loadX(), loc.loadY() + turnLocation.getNewLocation1().loadY(), loc.loadZ() + turnLocation.getNewLocation1().loadZ());
    connectors[3].setConnectorLocation(loc.loadX() - turnLocation.getNewLocation1().loadX(), loc.loadY() - turnLocation.getNewLocation1().loadY(), loc.loadZ() - turnLocation.getNewLocation1().loadZ());
    connectors[4].setConnectorLocation(loc.loadX() + turnLocation.getNewLocation3().loadX(), loc.loadY() + turnLocation.getNewLocation3().loadY(), loc.loadZ() + turnLocation.getNewLocation3().loadZ());
    connectors[5].setConnectorLocation(loc.loadX() - turnLocation.getNewLocation3().loadX(), loc.loadY() - turnLocation.getNewLocation3().loadY(), loc.loadZ() - turnLocation.getNewLocation3().loadZ());

    /** コネクタの所属するグループ */
    final ConnectorGroupFactory group = new ConnectorGroupFactory();
    final Group cGroup = group.createConnectorGroup();
    
    for (int x = 0; x < connectors.length; x++) {
      cGroup.addXMLConnector(connectors[x]);
    }
  }
}
