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

  /** コネクタの所属するグループ */
  private ConnectorGroup group;

  /** コネクタ1~2の座標 */
  private XMLConnector connectors[] = new XMLConnector[2];

  private TurnLocation turnLocation;

  /**
   * コンストラクター
   */
  public ConeConnector() {
    this.group = new ConnectorGroup();
  }

  /**
   * 円錐プリミティブにコネクタを追加する。
   * 
   * @param radius 円錐の半径
   * @param height 円錐の高さ
   * @param location 円錐の座標
   * @param rotation 円錐の回転
   */
  public void createConeConnector(float radius, float height, Location location, Rotation rotation) {
    for (int i = 0; i < this.connectors.length; i++) {
      this.connectors[i] = new XMLConnector();
      this.connectors[i].setNum(i + 1);
      if (i == 0 || i == 1) {
        this.connectors[i].checkParameter(height);
      } else if (i == 2 || i == 3 || i == 4 || i == 5) {
        this.connectors[i].checkParameterR(radius);
      }
    }

    this.connectors[0].setLengthToCenter(height * 0.5f);
    this.connectors[1].setLengthToCenter(height * 0.5f);

    final Group connectorGroup = this.group.createConnectorGroup();

    this.connectors[0].setConnectorRotation(rotation.loadXrotate(), rotation.loadYrotate(), rotation.loadZrotate());
    this.connectors[1].setConnectorRotation(rotation.loadXrotate() + 180.0f, rotation.loadYrotate(), rotation.loadZrotate());

    this.turnLocation = new TurnLocation(radius, height / 2.0f, radius, rotation.loadXrotate(), rotation.loadYrotate(), rotation.loadZrotate());

    this.connectors[0].setConnectorLocation(location.loadX() + this.turnLocation.getNewLocation2().loadX(), location.loadY() + this.turnLocation.getNewLocation2().loadY(), location.loadZ() + this.turnLocation.getNewLocation2().loadZ());
    this.connectors[1].setConnectorLocation(location.loadX() - this.turnLocation.getNewLocation2().loadX(), location.loadY() - this.turnLocation.getNewLocation2().loadY(), location.loadZ() - this.turnLocation.getNewLocation2().loadZ());

    for (int x = 0; x < this.connectors.length; x++) {
      connectorGroup.addXMLConnector(this.connectors[x]);
    }
  }
}
