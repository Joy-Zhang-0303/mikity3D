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

  /** コネクタの所属するグループ */
  private ConnectorGroup group;

  /** コネクタ1~6 */
  private XMLConnector cnct[] = new XMLConnector[6];

  /**
   * コンストラクター
   */
  public SphereConnector() {
    this.group = new ConnectorGroup();
  }

  /**
   * 球体プリミティブにコネクタを追加する。
   * 
   * @param r
   *        球体の半径
   * @param loc
   *        球体の座標
   * @param rot
   *        球体の回転
   */
  public void createSphereConnector(float r, Location loc, Rotation rot) {
    for (int i = 0; i < this.cnct.length; i++) {
      this.cnct[i] = new XMLConnector();
      this.cnct[i].setNum(i + 1);
      this.cnct[i].checkParameterR(r);
    }

    for (int i = 0; i < this.cnct.length; i++) {
      this.cnct[i].setLengthToCenter(r);
    }

    Group cGroup = this.group.createConnectorGroup();

    this.cnct[0].setConnectorRotation(0.0f, 0.0f, 0.0f);
    this.cnct[1].setConnectorRotation(180.0f, 0.0f, 0.0f);
    this.cnct[2].setConnectorRotation(90.0f, 0.0f, 0.0f);
    this.cnct[3].setConnectorRotation(-90.0f, 0.0f, 0.0f);
    this.cnct[4].setConnectorRotation(0.0f, 0.0f, -90.0f);
    this.cnct[5].setConnectorRotation(0.0f, 0.0f, 90.0f);

    this.cnct[0].setConnectorLocation(loc.loadX(), loc.loadY() + r, loc.loadZ());
    this.cnct[1].setConnectorLocation(loc.loadX(), loc.loadY() - r, loc.loadZ());
    this.cnct[2].setConnectorLocation(loc.loadX(), loc.loadY(), loc.loadZ() + r);
    this.cnct[3].setConnectorLocation(loc.loadX(), loc.loadY(), loc.loadZ() - r);
    this.cnct[4].setConnectorLocation(loc.loadX() + r, loc.loadY(), loc.loadZ());
    this.cnct[5].setConnectorLocation(loc.loadX() - r, loc.loadY(), loc.loadZ());

    for (int x = 0; x < this.cnct.length; x++) {
      cGroup.addXMLConnector(this.cnct[x]);
    }
  }
}
