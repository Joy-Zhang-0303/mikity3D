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
   * プリミティブごとのの各パラメータを取得し、キャンバス上のプリミティブごとに適したコネクタを追加する。 コネクタを追加されるプリミティブ
   * 
   * @param primitive プリミティブ
   */
  public void createPrimitiveConnector(Object primitive) {
    if (primitive instanceof XMLBox) {
      final XMLBox box = (XMLBox)primitive;
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

      final BoxConnector boxConnector = new BoxConnector();
      boxConnector.createBoxConnector(x, y, z, location, rotation);
    } else if (primitive instanceof XMLCone) {
      final XMLCone cone = (XMLCone)primitive;
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

      final ConeConnector coneConnector = new ConeConnector();
      coneConnector.createConeConnector(radius, height, location, rotation);
    } else if (primitive instanceof XMLCylinder) {
      final XMLCylinder cylinder = (XMLCylinder)primitive;
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

      final CylinderConnector cylinderConnector = new CylinderConnector();
      cylinderConnector.createCylinderConnector(raidius, height, location, rotation);
    } else if (primitive instanceof XMLSphere) {
      final XMLSphere sphere = (XMLSphere)primitive;
      final float radius = sphere.loadR();

      Location location = sphere.loadLocation();
      if (location == null) {
        location = new Location(0,0,0);
      }

      Rotation rotation = sphere.loadRotation();
      if (rotation == null) {
        rotation = new Rotation(0,0,0);
      }

      final SphereConnector sphereConnector = new SphereConnector();
      sphereConnector.createSphereConnector(radius, location, rotation);
    }
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
