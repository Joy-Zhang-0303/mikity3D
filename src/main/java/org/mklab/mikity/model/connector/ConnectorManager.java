/*
 * Created on 2006/02/03
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.connector;

import org.mklab.mikity.model.xml.model.Location;
import org.mklab.mikity.model.xml.model.Rotation;
import org.mklab.mikity.model.xml.model.XMLBox;
import org.mklab.mikity.model.xml.model.XMLCone;
import org.mklab.mikity.model.xml.model.XMLConnector;
import org.mklab.mikity.model.xml.model.XMLCylinder;
import org.mklab.mikity.model.xml.model.XMLSphere;


/**
 * コネクタを使って接続を実行するためのクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/02/03
 */
public class ConnectorManager {
  /** 接続に使用するプリミティブ  */
  private Object primitives[] = new Object[2];

  /** 各コネクタの位置座標  */
  private Location connectorLocations[] = new Location[2];
  /** 各コネクタの回転  */
  private Rotation connectorRotations[] = new Rotation[2];

  /** 各プリミティブの位置座標  */
  private Location primitiveLocations[] = new Location[2];
  /** 各プリミティブの回転 */
  private Rotation primitiveRotations[] = new Rotation[2];

  /** プリミティブ中心〜コネクタの距離 */
  private float lengthes[] = new float[2];

  /** 各コネクタ番号 */
  private int connectorNumbers[] = new int[2];

  /** 移動後のプリミティブの位置座標  */
  private Location newPrimitiveLocation;
  /** 移動後のプリミティブの回転  */
  private Rotation newPrimitiveRotation;

  private ConnectorNewRotation newConnectorRotation;

  /**
   * コンストラクター
   */
  public ConnectorManager() {
    for (int i = 0; i < 2; i++) {
      this.primitives[i] = new Object();
      this.connectorLocations[i] = new Location();
      this.connectorRotations[i] = new Rotation();

      this.primitiveLocations[i] = new Location();
      this.primitiveRotations[i] = new Rotation();

      this.connectorNumbers[i] = 0;
    }

    this.newPrimitiveLocation = new Location();
    this.newPrimitiveRotation = new Rotation();

    this.newConnectorRotation = new ConnectorNewRotation();
  }

  /**
   * 接続基点となるコネクタN,Sの位置&回転に関する情報を取得する。
   * 
   * @param connector コネクタ
   */
  public void connectorNS(XMLConnector connector) {
    int i = checkNS(connector.loadFlag());

    if (connector.loadLocation() == null) {
      this.connectorLocations[i].setX(0.0f);
      this.connectorLocations[i].setY(0.0f);
      this.connectorLocations[i].setZ(0.0f);
    } else {
      this.connectorLocations[i].setX(connector.loadLocation().loadX());
      this.connectorLocations[i].setY(connector.loadLocation().loadY());
      this.connectorLocations[i].setZ(connector.loadLocation().loadZ());
    }
    if (connector.loadRotation() == null) {
      this.connectorRotations[i].setXrotate(0.0f);
      this.connectorRotations[i].setYrotate(0.0f);
      this.connectorRotations[i].setZrotate(0.0f);
    } else {
      this.connectorRotations[i].setXrotate(connector.loadRotation().loadXrotate());
      this.connectorRotations[i].setYrotate(connector.loadRotation().loadYrotate());
      this.connectorRotations[i].setZrotate(connector.loadRotation().loadZrotate());
    }
    this.lengthes[i] = connector.loadLengthToCenter();
    this.connectorNumbers[i] = connector.loadNum();
  }

  /**
   * 選択したコネクタN,Sが所属しているプリミティブの位置情報&回転情報を取得する。
   * 
   * @param prim プリミティブ
   * @param ns 選択したコネクタがNあるいはS
   */
  public void setPrimitiveNS(Object prim, String ns) {
    int i = checkNS(ns);

    this.primitives[i] = prim;

    if (prim instanceof XMLBox) {
      XMLBox box = (XMLBox)prim;
      boxNS(box, i);
    } else if (prim instanceof XMLCone) {
      XMLCone cone = (XMLCone)prim;
      coneNS(cone, i);
    } else if (prim instanceof XMLCylinder) {
      XMLCylinder cylinder = (XMLCylinder)prim;
      cylinderNS(cylinder, i);
    } else if (prim instanceof XMLSphere) {
      XMLSphere sphere = (XMLSphere)prim;
      sphereNS(sphere, i);
    }
  }

//  /**
//   * 選択したコネクタのプリミティブを返す
//   * 
//   * @param ns コネクタの属性
//   * @return　prim　選択したコネクタのプリミティブ
//   */
//  public Object getPrimitiveNS(String ns) {
//    int i = checkNS(ns);
//    return this.primitives[i];
//  }

  /**
   * 直方体の位置情報&回転情報を取得する。
   * 
   * @param box 直方体
   * @param i 選択したコネクタがN(i=0)あるいはS(i=1)
   */
  private void boxNS(XMLBox box, int i) {
    if (box.loadLocation() == null) {
      this.primitiveLocations[i].setX(0.0f);
      this.primitiveLocations[i].setY(0.0f);
      this.primitiveLocations[i].setZ(0.0f);
    } else {
      this.primitiveLocations[i].setX(box.loadLocation().loadX());
      this.primitiveLocations[i].setY(box.loadLocation().loadY());
      this.primitiveLocations[i].setZ(box.loadLocation().loadZ());
    }
    if (box.loadRotation() == null) {
      this.primitiveRotations[i].setXrotate(0.0f);
      this.primitiveRotations[i].setYrotate(0.0f);
      this.primitiveRotations[i].setZrotate(0.0f);
    } else {
      this.primitiveRotations[i].setXrotate(box.loadRotation().loadXrotate());
      this.primitiveRotations[i].setYrotate(box.loadRotation().loadYrotate());
      this.primitiveRotations[i].setZrotate(box.loadRotation().loadZrotate());
    }
  }

  /**
   * 円錐の位置情報&回転情報を取得する。
   * 
   * @param cone 円錐
   * @param i 選択したコネクタがN(i=0)あるいはS(i=1)
   */
  private void coneNS(XMLCone cone, int i) {
    if (cone.loadLocation() == null) {
      this.primitiveLocations[i].setX(0.0f);
      this.primitiveLocations[i].setY(0.0f);
      this.primitiveLocations[i].setZ(0.0f);
    } else {
      this.primitiveLocations[i].setX(cone.loadLocation().loadX());
      this.primitiveLocations[i].setY(cone.loadLocation().loadY());
      this.primitiveLocations[i].setZ(cone.loadLocation().loadZ());
    }
    
    if (cone.loadRotation() == null) {
      this.primitiveRotations[i].setXrotate(0.0f);
      this.primitiveRotations[i].setYrotate(0.0f);
      this.primitiveRotations[i].setZrotate(0.0f);
    } else {
      this.primitiveRotations[i].setXrotate(cone.loadRotation().loadXrotate());
      this.primitiveRotations[i].setYrotate(cone.loadRotation().loadYrotate());
      this.primitiveRotations[i].setZrotate(cone.loadRotation().loadZrotate());
    }
  }

  /**
   * 円柱の位置情報&回転情報を取得する。
   * 
   * @param cylinder 円柱
   * @param i 選択したコネクタがN(i=0)あるいはS(i=1)
   */
  private void cylinderNS(XMLCylinder cylinder, int i) {
    if (cylinder.loadLocation() == null) {
      this.primitiveLocations[i].setX(0.0f);
      this.primitiveLocations[i].setY(0.0f);
      this.primitiveLocations[i].setZ(0.0f);
    } else {
      this.primitiveLocations[i].setX(cylinder.loadLocation().loadX());
      this.primitiveLocations[i].setY(cylinder.loadLocation().loadY());
      this.primitiveLocations[i].setZ(cylinder.loadLocation().loadZ());
    }
    
    if (cylinder.loadRotation() == null) {
      this.primitiveRotations[i].setXrotate(0.0f);
      this.primitiveRotations[i].setYrotate(0.0f);
      this.primitiveRotations[i].setZrotate(0.0f);
    } else {
      this.primitiveRotations[i].setXrotate(cylinder.loadRotation().loadXrotate());
      this.primitiveRotations[i].setYrotate(cylinder.loadRotation().loadYrotate());
      this.primitiveRotations[i].setZrotate(cylinder.loadRotation().loadZrotate());
    }
  }

  /**
   * 球体の位置情報&回転情報を取得する。
   * 
   * @param sphere 球体
   * @param i 選択したコネクタがN(i=0)あるいはS(i=1)
   */
  private void sphereNS(XMLSphere sphere, int i) {
    if (sphere.loadLocation() == null) {
      this.primitiveLocations[i].setX(0.0f);
      this.primitiveLocations[i].setY(0.0f);
      this.primitiveLocations[i].setZ(0.0f);
    } else {
      this.primitiveLocations[i].setX(sphere.loadLocation().loadX());
      this.primitiveLocations[i].setY(sphere.loadLocation().loadY());
      this.primitiveLocations[i].setZ(sphere.loadLocation().loadZ());
    }
    if (sphere.loadRotation() == null) {
      this.primitiveRotations[i].setXrotate(0.0f);
      this.primitiveRotations[i].setYrotate(0.0f);
      this.primitiveRotations[i].setZrotate(0.0f);
    } else {
      this.primitiveRotations[i].setXrotate(sphere.loadRotation().loadXrotate());
      this.primitiveRotations[i].setYrotate(sphere.loadRotation().loadYrotate());
      this.primitiveRotations[i].setZrotate(sphere.loadRotation().loadZrotate());
    }
  }

  /**
   * コネクタの属性を判別する
   * 
   * @param ns コネクタの属性
   * @return　Nならi=0,Sならi=1
   */
  private int checkNS(String ns) {
    if (ns == "N") { //$NON-NLS-1$
      return 0;
    } else if (ns == "S") { //$NON-NLS-1$
      return 1;
    } else {
      return 2;
    }
  }

  /**
   * 接続後のプリミティブSの座標を決定する。
   */
  public void setNewLocation() {
    float rate = (this.lengthes[0] + this.lengthes[1]) / this.lengthes[0];
    this.newPrimitiveLocation.setX((this.connectorLocations[0].loadX() - this.primitiveLocations[0].loadX()) * rate + this.primitiveLocations[0].loadX());
    this.newPrimitiveLocation.setY((this.connectorLocations[0].loadY() - this.primitiveLocations[0].loadY()) * rate + this.primitiveLocations[0].loadY());
    this.newPrimitiveLocation.setZ((this.connectorLocations[0].loadZ() - this.primitiveLocations[0].loadZ()) * rate + this.primitiveLocations[0].loadZ());
  }

  /**
   * 接続後のプリミティブSの回転を決定する。
   */
  public void setNewRotation() {
    this.newConnectorRotation.setPrimitiveNRotation(this.primitiveRotations[0]);
    this.newConnectorRotation.setNewRotation(this.connectorNumbers[0], this.connectorNumbers[1]);
    this.newPrimitiveRotation = this.newConnectorRotation.getNewRotation();
  }

  /**
   * コネクタを通じてプリミティブの接続を行う。
   */
  public void connect() {
    if (this.primitives[1] instanceof XMLBox) {
      XMLBox box = (XMLBox)this.primitives[1];
      box.setLocation(checkLocation(this.newPrimitiveLocation));
      box.setRotation(checkRotation(this.newPrimitiveRotation));
    } else if (this.primitives[1] instanceof XMLCone) {
      XMLCone cone = (XMLCone)this.primitives[1];
      cone.setLocation(checkLocation(this.newPrimitiveLocation));
      cone.setRotation(checkRotation(this.newPrimitiveRotation));
    } else if (this.primitives[1] instanceof XMLCylinder) {
      XMLCylinder cylinder = (XMLCylinder)this.primitives[1];
      cylinder.setLocation(checkLocation(this.newPrimitiveLocation));
      cylinder.setRotation(checkRotation(this.newPrimitiveRotation));
    } else if (this.primitives[1] instanceof XMLSphere) {
      XMLSphere sphere = (XMLSphere)this.primitives[1];
      sphere.setLocation(checkLocation(this.newPrimitiveLocation));
      sphere.setRotation(checkRotation(this.newPrimitiveRotation));
    }
  }

  /**
   * プリミティブの位置座標におけるパラメータが変化していないとき、各座標に0.0の値を代入する。
   * 
   * @param location プリミティブの位置座標
   * @return プリミティブの位置座標
   */
  private Location checkLocation(Location location) {
    if (location != null) {
      return location;
    }

    Location loc2 = new Location();
    loc2.setX(0.0f);
    loc2.setY(0.0f);
    loc2.setZ(0.0f);
    return loc2;
  }

  /**
   * プリミティブの各軸の回転角度におけるパラメータが変化していないとき、各軸の回転角度に0.0の値を代入する。
   * 
   * @param rotation プリミティブの回転角度
   * @return プリミティブの回転角度
   */
  private Rotation checkRotation(Rotation rotation) {
    if (rotation != null) {
      return rotation;
    }

    Rotation rot2 = new Rotation();
    rot2.setXrotate(0.0f);
    rot2.setYrotate(0.0f);
    rot2.setZrotate(0.0f);
    return rot2;
  }
}
