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
  /** プリミティブの座標 */
  private Location location;
  /** プリミティブの回転 */
  private Rotation rotation;

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

      this.location = box.loadLocation();
      checkLocation(this.location);

      this.rotation = box.loadRotation();
      checkRotation(this.rotation);

      final BoxConnector boxConnector = new BoxConnector();
      boxConnector.createBoxConnector(x, y, z, this.location, this.rotation);
    } else if (primitive instanceof XMLCone) {
      final XMLCone cone = (XMLCone)primitive;
      final float radius = cone.loadR();
      final float height = cone.loadHeight();

      this.location = cone.loadLocation();
      checkLocation(this.location);

      this.rotation = cone.loadRotation();
      checkRotation(this.rotation);

      final ConeConnector coneConnector = new ConeConnector();
      coneConnector.createConeConnector(radius, height, this.location, this.rotation);
    } else if (primitive instanceof XMLCylinder) {
      final XMLCylinder cylinder = (XMLCylinder)primitive;
      final float raidius = cylinder.loadR();
      final float height = cylinder.loadHeight();

      this.location = cylinder.loadLocation();
      checkLocation(this.location);

      this.rotation = cylinder.loadRotation();
      checkRotation(this.rotation);

      final CylinderConnector cylinderConnector = new CylinderConnector();
      cylinderConnector.createCylinderConnector(raidius, height, this.location, this.rotation);
    } else if (primitive instanceof XMLSphere) {
      final XMLSphere sphere = (XMLSphere)primitive;
      final float radius = sphere.loadR();

      this.location = sphere.loadLocation();
      checkLocation(this.location);

      this.rotation = sphere.loadRotation();
      checkRotation(this.rotation);

      final SphereConnector sphereConnector = new SphereConnector();
      sphereConnector.createSphereConnector(radius, this.location, this.rotation);
    }
  }

  /**
   * プリミティブの位置座標におけるパラメータが変化していないとき、各座標に0.0の値を代入する。
   * 
   * @param location 　プリミティブの位置座標
   */
  private void checkLocation(Location location) {
    if (location == null) {
      this.location = new Location();
      this.location.setX(0.0f);
      this.location.setY(0.0f);
      this.location.setZ(0.0f);
    }
  }

  /**
   * プリミティブの各軸の回転角度におけるパラメータが変化していないとき、各軸の回転角度に0.0の値を代入する。
   * 
   * @param rotation 　プリミティブの回転角度
   */
  private void checkRotation(Rotation rotation) {
    if (rotation == null) {
      this.rotation = new Rotation();
      this.rotation.setXrotate(0.0f);
      this.rotation.setYrotate(0.0f);
      this.rotation.setZrotate(0.0f);
    }
  }

  /**
   * コネクタNを持つグループを生成します。
   * 
   * @return　newgroup　コネクタNを持つグループ
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
   * @return　newgroup　コネクタSを持つグループ
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
