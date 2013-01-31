/*
 * Created on 2006/01/31
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.connector;

import org.mklab.mikity.model.xml.model.Location;
import org.mklab.mikity.model.xml.model.Rotation;
import org.mklab.mikity.model.xml.model.XMLConnector;
import org.mklab.mikity.model.xml.model.XMLSphere;

/**
 * Sphereにコネクタを表示させるためのクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class SphereConnectorFactory {
  /**
   * 球体プリミティブのコネクタを生成します。
   * @param sphere 球体プリミティブ
   * @return 球体プリミティブのコネクタ
   */
  public XMLConnector[] createConnectors(XMLSphere sphere) {
    final float radius = sphere.loadR();

    Location location = sphere.loadLocation();
    if (location == null) {
      location = new Location(0,0,0);
    }

    Rotation rotation = sphere.loadRotation();
    if (rotation == null) {
      rotation = new Rotation(0,0,0);
    }
    
    final XMLConnector connectors[] = new XMLConnector[6];
    
    for (int i = 0; i < connectors.length; i++) {
      connectors[i] = new XMLConnector();
      connectors[i].setNum(i + 1);
      connectors[i].checkParameterR(radius);
    }

    for (int i = 0; i < connectors.length; i++) {
      connectors[i].setLengthToCenter(radius);
    }

    connectors[0].setConnectorRotation(0.0f, 0.0f, 0.0f);
    connectors[1].setConnectorRotation(180.0f, 0.0f, 0.0f);
    connectors[2].setConnectorRotation(90.0f, 0.0f, 0.0f);
    connectors[3].setConnectorRotation(-90.0f, 0.0f, 0.0f);
    connectors[4].setConnectorRotation(0.0f, 0.0f, -90.0f);
    connectors[5].setConnectorRotation(0.0f, 0.0f, 90.0f);

    connectors[0].setConnectorLocation(location.loadX(), location.loadY() + radius, location.loadZ());
    connectors[1].setConnectorLocation(location.loadX(), location.loadY() - radius, location.loadZ());
    connectors[2].setConnectorLocation(location.loadX(), location.loadY(), location.loadZ() + radius);
    connectors[3].setConnectorLocation(location.loadX(), location.loadY(), location.loadZ() - radius);
    connectors[4].setConnectorLocation(location.loadX() + radius, location.loadY(), location.loadZ());
    connectors[5].setConnectorLocation(location.loadX() - radius, location.loadY(), location.loadZ());
    
    return connectors;
  }
}
