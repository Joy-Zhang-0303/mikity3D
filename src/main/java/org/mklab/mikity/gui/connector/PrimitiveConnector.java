/*
 * Created on 2006/01/31
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.gui.ModelingWindow;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLSphere;


/**
 * プリミティブに適したコネクタを追加するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class PrimitiveConnector {
  /**
   * プリミティブごとのの各パラメータを取得し、キャンバス上のプリミティブごとに適したコネクタを追加する。 
   * 
   * @param primitive コネクタを追加されるプリミティブ
   */
  public void addConnectors(Object primitive) {
    if (primitive instanceof XMLBox) {
      addConnectorsToBox((XMLBox)primitive);
    } else if (primitive instanceof XMLCone) {
      addConnectorsToCone((XMLCone)primitive);
    } else if (primitive instanceof XMLCylinder) {
      addConnectorsToCylinder((XMLCylinder)primitive);
    } else if (primitive instanceof XMLSphere) {
      addConnectorsToSphere((XMLSphere)primitive);
    }
  }

  private void addConnectorsToSphere(final XMLSphere sphere) {
    final float radius = sphere.loadR();

    Location location = sphere.loadLocation();
    if (location == null) {
      location = new Location(0,0,0);
    }

    Rotation rotation = sphere.loadRotation();
    if (rotation == null) {
      rotation = new Rotation(0,0,0);
    }

    final SphereConnector connector = new SphereConnector();
    connector.addConnectors(radius, location, rotation);
  }

  private void addConnectorsToCylinder(final XMLCylinder cylinder) {
    final float raidius = cylinder.loadR();
    final float height = cylinder.loadHeight();

    Location location = cylinder.loadLocation();
    if (location == null) {
      location = new Location(0,0,0);
    }

    Rotation rotation = cylinder.loadRotation();
    if (rotation == null) {
      rotation = new Rotation(0,0,0);
    }

    final CylinderConnector connector = new CylinderConnector();
    connector.addConnectors(raidius, height, location, rotation);
  }

  private void addConnectorsToCone(final XMLCone cone) {
    final float radius = cone.loadR();
    final float height = cone.loadHeight();
    
    Location location = cone.loadLocation();
    if (location == null) {
      location = new Location(0,0,0);
    }

    Rotation rotation = cone.loadRotation();
    if (rotation == null) {
      rotation = new Rotation(0,0,0);
    }

    final ConeConnector connector = new ConeConnector();
    connector.addConnectors(radius, height, location, rotation);
  }

  private void addConnectorsToBox(final XMLBox box) {
    final float x = box.loadXsize();
    final float y = box.loadYsize();
    final float z = box.loadZsize();

    Location location = box.loadLocation();
    if (location == null) {
      location = new Location(0,0,0);
    }

    Rotation rotation = box.loadRotation();
    if (rotation == null) {
      rotation = new Rotation(0,0,0);
    }

    final BoxConnector connector = new BoxConnector();
    connector.addConnectors(x, y, z, location, rotation);
  }

  /**
   * コネクタNを持つグループを生成します。
   * 
   * @return　コネクタNを持つグループ
   */
  public Group createNorthConnectorGroup() {
    final Group northGroup = new Group();
    northGroup.setName("ConnectorN"); //$NON-NLS-1$

    final Jamast root = ModelingWindow.getRoot();
    final Group routGroup = root.loadModel(0).loadGroup(0);
    routGroup.addGroup(northGroup);
    return northGroup;
  }

  /**
   * コネクタSを持つグループを生成します。
   * 
   * @return　コネクタSを持つグループ
   */
  public Group createSouthConnectorGroup() {   
    final Group southGroup = new Group();
    southGroup.setName("ConnectorS"); //$NON-NLS-1$
    
    final Jamast root = ModelingWindow.getRoot();
    final Group rootGroup = root.loadModel(0).loadGroup(0);
    rootGroup.addGroup(southGroup);
    return southGroup;
  }
}
