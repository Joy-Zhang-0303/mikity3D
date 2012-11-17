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

  /** プリミティブのパラメータ1 */
  private float param1;
  /** プリミティブのパラメータ2 */
  private float param2;
  /** プリミティブのパラメータ3 */
  private float param3;

  /** プリミティブの座標 */
  private Location primLoc;

  /** プリミティブの回転 */
  private Rotation primRot;

  /** 直方体のコネクタ */
  private BoxConnector boxConnector;
  /** 円錐のコネクタ */
  private ConeConnector coneConnector;
  /** 円柱のコネクタ */
  private CylinderConnector cylinderConnector;
  /** 球体のコネクタ */
  private SphereConnector sphereConnector;

  /**
   * コンストラクター
   */
  public PrimitiveConnector() {
    this.boxConnector = new BoxConnector();
    this.coneConnector = new ConeConnector();
    this.cylinderConnector = new CylinderConnector();
    this.sphereConnector = new SphereConnector();
  }

  /**
   * プリミティブごとのの各パラメータを取得し、キャンバス上のプリミティブごとに適したコネクタを追加する。 コネクタを追加されるプリミティブ
   * 
   * @param prim プリミティブ
   */
  public void createPrimitiveConnector(Object prim) {
    if (prim instanceof XMLBox) {
      XMLBox box = (XMLBox)prim;

      this.param1 = box.loadXsize();
      this.param2 = box.loadYsize();
      this.param3 = box.loadZsize();

      this.primLoc = box.loadLocation();
      checkLoc(this.primLoc);

      this.primRot = box.loadRotation();
      checkRot(this.primRot);

      this.boxConnector.createBoxConnector(this.param1, this.param2, this.param3, this.primLoc, this.primRot);
    } else if (prim instanceof XMLCone) {
      XMLCone cone = (XMLCone)prim;

      this.param1 = cone.loadR();
      this.param2 = cone.loadHeight();

      this.primLoc = cone.loadLocation();
      checkLoc(this.primLoc);

      this.primRot = cone.loadRotation();
      checkRot(this.primRot);

      this.coneConnector.createConeConnector(this.param1, this.param2, this.primLoc, this.primRot);
    } else if (prim instanceof XMLCylinder) {
      XMLCylinder cylinder = (XMLCylinder)prim;

      this.param1 = cylinder.loadR();
      this.param2 = cylinder.loadHeight();

      this.primLoc = cylinder.loadLocation();
      checkLoc(this.primLoc);

      this.primRot = cylinder.loadRotation();
      checkRot(this.primRot);

      this.cylinderConnector.createCylinderConnector(this.param1, this.param2, this.primLoc, this.primRot);
    } else if (prim instanceof XMLSphere) {
      XMLSphere sphere = (XMLSphere)prim;

      this.param1 = sphere.loadR();

      this.primLoc = sphere.loadLocation();
      checkLoc(this.primLoc);

      this.primRot = sphere.loadRotation();
      checkRot(this.primRot);

      this.sphereConnector.createSphereConnector(this.param1, this.primLoc, this.primRot);
    }
  }

  /**
   * プリミティブの位置座標におけるパラメータが変化していないとき、各座標に0.0の値を代入する。
   * 
   * @param loc 　プリミティブの位置座標
   */
  private void checkLoc(Location loc) {
    if (loc == null) {
      this.primLoc = new Location();
      this.primLoc.setX(0.0f);
      this.primLoc.setY(0.0f);
      this.primLoc.setZ(0.0f);
    }
  }

  /**
   * プリミティブの各軸の回転角度におけるパラメータが変化していないとき、各軸の回転角度に0.0の値を代入する。
   * 
   * @param rot 　プリミティブの回転角度
   */
  private void checkRot(Rotation rot) {
    if (rot == null) {
      this.primRot = new Rotation();
      this.primRot.setXrotate(0.0f);
      this.primRot.setYrotate(0.0f);
      this.primRot.setZrotate(0.0f);
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
