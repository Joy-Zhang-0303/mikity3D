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

  /** コネクタの所属するグループ */
  private ConnectorGroup group;

  /** コネクタ1~6 */
  private XMLConnector connectors[] = new XMLConnector[6];

  private TurnLocation turnLocation;

  /**
   * コンストラクター
   */
  public CylinderConnector() {
    this.group = new ConnectorGroup();
  }

  /**
   * 円柱プリミティブにコネクタを追加する。
   * 
   * @param r 円柱の半径
   * @param h 円柱の高さ
   * @param loc 円柱の座標
   * @param rot 円柱の回転
   */
  public void createCylinderConnector(float r, float h, Location loc, Rotation rot) {
    for (int i = 0; i < this.connectors.length; i++) {
      this.connectors[i] = new XMLConnector();
      this.connectors[i].setNum(i + 1);
      if (i == 0 || i == 1) {
        this.connectors[i].checkParameter(h);
      } else if (i == 2 || i == 3 || i == 4 || i == 5) {
        this.connectors[i].checkParameterR(r);
      }
    }

    this.connectors[0].setLengthToCenter(h * 0.5f);
    this.connectors[1].setLengthToCenter(h * 0.5f);
    this.connectors[2].setLengthToCenter(r);
    this.connectors[3].setLengthToCenter(r);
    this.connectors[4].setLengthToCenter(r);
    this.connectors[5].setLengthToCenter(r);

    Group cGroup = this.group.createConnectorGroup();

    this.connectors[0].setConnectorRotation(rot.loadXrotate(), rot.loadYrotate(), rot.loadZrotate());
    this.connectors[1].setConnectorRotation(rot.loadXrotate() + 180.0f, rot.loadYrotate(), rot.loadZrotate());
    this.connectors[2].setConnectorRotation(-rot.loadYrotate(), 0.0f, rot.loadZrotate() - 90.0f);
    this.connectors[3].setConnectorRotation(rot.loadYrotate(), 0.0f, rot.loadZrotate() + 90.0f);
    this.connectors[4].setConnectorRotation(rot.loadXrotate() + 90.0f, rot.loadYrotate(), rot.loadZrotate());
    this.connectors[5].setConnectorRotation(rot.loadXrotate() - 90.0f, rot.loadYrotate(), rot.loadZrotate());

    this.turnLocation = new TurnLocation(r, h / 2.0f, r, rot.loadXrotate(), rot.loadYrotate(), rot.loadZrotate());

    this.connectors[0].setConnectorLocation(loc.loadX() + this.turnLocation.getNewLocation2().loadX(), loc.loadY() + this.turnLocation.getNewLocation2().loadY(), loc.loadZ() + this.turnLocation.getNewLocation2().loadZ());
    this.connectors[1].setConnectorLocation(loc.loadX() - this.turnLocation.getNewLocation2().loadX(), loc.loadY() - this.turnLocation.getNewLocation2().loadY(), loc.loadZ() - this.turnLocation.getNewLocation2().loadZ());
    this.connectors[2].setConnectorLocation(loc.loadX() + this.turnLocation.getNewLocation1().loadX(), loc.loadY() + this.turnLocation.getNewLocation1().loadY(), loc.loadZ() + this.turnLocation.getNewLocation1().loadZ());
    this.connectors[3].setConnectorLocation(loc.loadX() - this.turnLocation.getNewLocation1().loadX(), loc.loadY() - this.turnLocation.getNewLocation1().loadY(), loc.loadZ() - this.turnLocation.getNewLocation1().loadZ());
    this.connectors[4].setConnectorLocation(loc.loadX() + this.turnLocation.getNewLocation3().loadX(), loc.loadY() + this.turnLocation.getNewLocation3().loadY(), loc.loadZ() + this.turnLocation.getNewLocation3().loadZ());
    this.connectors[5].setConnectorLocation(loc.loadX() - this.turnLocation.getNewLocation3().loadX(), loc.loadY() - this.turnLocation.getNewLocation3().loadY(), loc.loadZ() - this.turnLocation.getNewLocation3().loadZ());

    for (int x = 0; x < this.connectors.length; x++) {
      cGroup.addXMLConnector(this.connectors[x]);
    }
  }
}
