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
 * Sphereにコネクタを表示させるためのクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class SphereConnector {
  /**
   * 球体プリミティブにコネクタを追加する。
   * 
   * @param r 球体の半径
   * @param loc 球体の座標
   * @param rot 球体の回転
   */
  public void createSphereConnector(float r, Location loc, Rotation rot) {
    /** コネクタ1~6 */
    final XMLConnector cnnectors[] = new XMLConnector[6];
    
    for (int i = 0; i < cnnectors.length; i++) {
      cnnectors[i] = new XMLConnector();
      cnnectors[i].setNum(i + 1);
      cnnectors[i].checkParameterR(r);
    }

    for (int i = 0; i < cnnectors.length; i++) {
      cnnectors[i].setLengthToCenter(r);
    }


    cnnectors[0].setConnectorRotation(0.0f, 0.0f, 0.0f);
    cnnectors[1].setConnectorRotation(180.0f, 0.0f, 0.0f);
    cnnectors[2].setConnectorRotation(90.0f, 0.0f, 0.0f);
    cnnectors[3].setConnectorRotation(-90.0f, 0.0f, 0.0f);
    cnnectors[4].setConnectorRotation(0.0f, 0.0f, -90.0f);
    cnnectors[5].setConnectorRotation(0.0f, 0.0f, 90.0f);

    cnnectors[0].setConnectorLocation(loc.loadX(), loc.loadY() + r, loc.loadZ());
    cnnectors[1].setConnectorLocation(loc.loadX(), loc.loadY() - r, loc.loadZ());
    cnnectors[2].setConnectorLocation(loc.loadX(), loc.loadY(), loc.loadZ() + r);
    cnnectors[3].setConnectorLocation(loc.loadX(), loc.loadY(), loc.loadZ() - r);
    cnnectors[4].setConnectorLocation(loc.loadX() + r, loc.loadY(), loc.loadZ());
    cnnectors[5].setConnectorLocation(loc.loadX() - r, loc.loadY(), loc.loadZ());

    final ConnectorGroupFactory group = new ConnectorGroupFactory();
    final Group cGroup = group.createConnectorGroup();

    for (int x = 0; x < cnnectors.length; x++) {
      cGroup.addXMLConnector(cnnectors[x]);
    }
  }
}
